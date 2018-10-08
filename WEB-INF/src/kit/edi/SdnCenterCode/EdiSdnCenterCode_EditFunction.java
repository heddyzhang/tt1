package kit.edi.SdnCenterCode;

import static kit.edi.SdnCenterCode.IEdiSdnCenterCode.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComBatchUtil;
import fb.com.exception.KitComException;
import fb.inf.KitFunction;
import fb.inf.exception.KitException;

/**
 * 99911_1_1　SDNｾﾝﾀｰｺｰﾄﾞ送受信
 *
 * @author Tuneduka
 *
 */
public class EdiSdnCenterCode_EditFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = 4356305412461654327L;

	/** 編集フォーム */
	private EdiSdnCenterCode_EditForm editForm = null;

	/** クラス名. */
	private static String className__ = EdiSdnCenterCode_EditFunction.class.getName();

	/**
	 * トランザクション制御を行うかどうかを示すフラグ. trueの場合トランザクション制御を行う. falseの場合トランザクション制御を行なわない.
	 *
	 * @see #isTransactionalFunction ()
	 */
	private static final boolean isTransactionalFunction = true;

	/**
	 * Transaction制御フラグを返す.
	 *
	 * @return true(制御を行う) or false(制御を行わない)
	 */
	@Override
	protected boolean isTransactionalFunction() {
		return isTransactionalFunction;
	}

	/**
	 * クラス名を取得.
	 *
	 * @return クラス名
	 */
	@Override
	protected String getFunctionName() {
		return className__;
	}

	/**
	 * コンストラクタです.
	 *
	 * @param mapping
	 *            paramName
	 * @param form
	 *            paramName
	 * @param request
	 *            paramName
	 * @param response
	 *            paramName
	 */
	public EdiSdnCenterCode_EditFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		super(mapping, form, request, response);
		reset();
	}

	/**
	 * フィールドを初期化する
	 */
	private void reset(){
		this.editForm = null;
	}

	// -----------------------------------------
	// search & set page data
	// -----------------------------------------
	@Override
	public String execute_() throws KitException {

		String sForwardPage = FORWARD_SUCCESS;

		// 編集フォームを取得する
		this.editForm = (EdiSdnCenterCode_EditForm) form_;

		// 呪文：リクエストタイプが無い場合にExceptionを発行させる
		this.editForm.checkHasReqType();

		// バッチ起動共通クラス
		ComBatchUtil comBatchUtil = new ComBatchUtil(getComUserSession(), getDatabase(), isTransactionalFunction(), getActionErrors());

		// --------------------------------------------------------
		// ボタン押下(リクエストタイプ)に応じた処理を
		// 実装し、結果に応じてeditFormを設定する
		// -------------------------------------------------------


		// ----------------------------------------------------
		// SDNｾﾝﾀｰｺｰﾄﾞ送受信
		// ----------------------------------------------------
		if (REQ_TYPE_OROSISENTACD.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　卸店センターコード ファイル作成
				comBatchUtil.startJobnet(JOB_TYPE_OROSISENTACD_JOBNET,
						                              JOB_TYPE_OROSISENTACD_MSG,
						                             "orosiCenterCcode="+this.editForm.getOrosiCenterCode());
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_OROSISENTAEDI.equals(this.editForm.getReqType())) {

			try {

				// チェックサービスを初期化
				EdiSdnCenterCode_CheckService checkServ = new EdiSdnCenterCode_CheckService(
						getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

				// 登録時チェックを実行
				if (!checkServ.validateCodeInsert(editForm)) {

					// エラーがあった場合
					return sForwardPage;
				}

				// 更新サービス呼び出し
				EdiSdnCenterCode_UpdateService updateServ = new EdiSdnCenterCode_UpdateService(
						getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

				// センターコードEDI 登録
				if (!updateServ.insertCenterCodeEdi(editForm)) {

					// エラーがあった場合
					return sForwardPage;
				}

				//ジョブネット起動　センターコード EDI　登録
				comBatchUtil.startJobnet(JOB_TYPE_OROSISENTAEDI_JOBNET,
						                              JOB_TYPE_OROSISENTAEDI_MSG);
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_ROGUEDI.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　ログ　EDI　受信
				comBatchUtil.startJobnet(JOB_TYPE_ROGUEDI_JOBNET,
						                              JOB_TYPE_ROGUEDI_MSG);
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_ROGUFILE.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　　ログ ファイル作成　
				comBatchUtil.startJobnet(JOB_TYPE_ROGUFILE_JOBNET,
						                              JOB_TYPE_ROGUFILE_MSG);
			} catch (Exception e) {
				return FORWARD_FAIL;
			}


		// ----------------------------------------------------
		// その他エラー
		// ----------------------------------------------------
		} else {
			throw new KitComException("$$$ This form has no reqType . $$$");
		}

		setRequest(ONLOAD_POPUP_URL,JOB_STATUS_POPUP_URL);

		return sForwardPage;
	}


}
