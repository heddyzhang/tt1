package kit.edi.JuchuDataJusin;

import static kit.edi.JuchuDataJusin.IEdiJuchuDataJusin.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import static fb.com.IKitComConst.*;
import fb.com.ComBatchUtil;
import fb.com.exception.KitComException;
import fb.inf.KitFunction;
import fb.inf.exception.KitException;

/**
 * 99905_1_1　受注データ受信
 *
 * @author Tuneduka
 *
 */
public class EdiJuchuDataJusin_EditFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = -6303145162565861759L;

	/** 編集フォーム */
	private EdiJuchuDataJusin_EditForm editForm = null;

	/** クラス名. */
	private static String className__ = EdiJuchuDataJusin_EditFunction.class.getName();

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
	public EdiJuchuDataJusin_EditFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
		this.editForm = (EdiJuchuDataJusin_EditForm) form_;

		// 呪文：リクエストタイプが無い場合にExceptionを発行させる
		this.editForm.checkHasReqType();

		// バッチ起動共通クラス
		ComBatchUtil comBatchUtil = new ComBatchUtil(getComUserSession(), getDatabase(), isTransactionalFunction(), getActionErrors());


		// --------------------------------------------------------
		// 入力チェック
		// -------------------------------------------------------
		// チェックサービスを初期化
		EdiJuchuDataJusin_CheckService checkServ = new EdiJuchuDataJusin_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 呼出時チェックを実行
		if (!checkServ.validateSearch(editForm)) {
			return sForwardPage;
		}

		// --------------------------------------------------------
		// ボタン押下(リクエストタイプ)に応じた処理を
		// 実装し、結果に応じてeditFormを設定する
		// -------------------------------------------------------
		// =================================================================

		// ----------------------------------------------------
		// 受注データ受信
		// ----------------------------------------------------
		if (REQ_TYPE_ONLINEEDI.equals(this.editForm.getReqType())) {

				//ジョブネット起動　オンライン受注　EDI　受信
				if(editForm.getJyusinsakiKbn().equals(EDI_JYUCYU_PARTNER_KB_IKKATU)){

					try {
						comBatchUtil.startJobnet(JOB_TYPE_ONLINEEDI_JOBNET,
								                              JOB_TYPE_ONLINEEDI_MSG);
					} catch (Exception e) {
						return FORWARD_FAIL;
					}

				} else if(editForm.getJyusinsakiKbn().equals(EDI_JYUCYU_PARTNER_KB_SDN)){

					try {
						//ジョブネット起動　オンライン受注　SDN　受信
						comBatchUtil.startJobnet(JOB_TYPE_SDNEDI_JOBNET,
							                              JOB_TYPE_SDNEDI_MSG);
					} catch (Exception e) {
						return FORWARD_FAIL;
					}

				} else if(editForm.getJyusinsakiKbn().equals(EDI_JYUCYU_PARTNER_KB_FINET)){

					try {
						//ジョブネット起動　オンライン受注　FINET　受信
						comBatchUtil.startJobnet(JOB_TYPE_FINETEDI_JOBNET,
								                              JOB_TYPE_FINETEDI_MSG);
					} catch (Exception e) {
						return FORWARD_FAIL;
					}

				} else {
					throw new KitComException("$$$ This form has no reqType . $$$");
				}

		} else if (REQ_TYPE_GATTAI.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　ファイル 合体
				comBatchUtil.startJobnet(JOB_TYPE_GATTAI_JOBNET,
						                              JOB_TYPE_GATTAI_MSG);
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_KOKUBU.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　国分許容日
				comBatchUtil.startJobnet(JOB_TYPE_KOKUBU_JOBNET,
						                              JOB_TYPE_KOKUBU_MSG);
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_CHECK.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　チェック閲覧
				comBatchUtil.startJobnet(JOB_TYPE_CHECK_JOBNET,
						                              JOB_TYPE_CHECK_MSG);
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_KOSIN.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　受注　更新
				comBatchUtil.startJobnet(JOB_TYPE_KOSIN_JOBNET,
						                              JOB_TYPE_KOSIN_MSG);
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
