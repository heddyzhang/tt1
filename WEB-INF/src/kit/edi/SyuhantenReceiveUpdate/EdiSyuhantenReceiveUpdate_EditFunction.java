package kit.edi.SyuhantenReceiveUpdate;

import static kit.edi.SyuhantenReceiveUpdate.IEdiSyuhantenReceiveUpdate.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComBatchUtil;
import fb.com.exception.KitComException;
import fb.inf.KitFunction;
import fb.inf.exception.KitException;

/**
 * 99915_1_1　酒販店ﾏｽﾀｰ受信更新
 *
 * @author Tuneduka
 *
 */
public class EdiSyuhantenReceiveUpdate_EditFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = -2448347834439008818L;

	/** 編集フォーム */
	private EdiSyuhantenReceiveUpdate_EditForm editForm = null;

	/** クラス名. */
	private static String className__ = EdiSyuhantenReceiveUpdate_EditFunction.class.getName();

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
	public EdiSyuhantenReceiveUpdate_EditFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
		this.editForm = (EdiSyuhantenReceiveUpdate_EditForm) form_;

		// 呪文：リクエストタイプが無い場合にExceptionを発行させる
		this.editForm.checkHasReqType();

		// バッチ起動共通クラス
		ComBatchUtil comBatchUtil = new ComBatchUtil(getComUserSession(), getDatabase(), isTransactionalFunction(), getActionErrors());

		// --------------------------------------------------------
		// ボタン押下(リクエストタイプ)に応じた処理を
		// 実装し、結果に応じてeditFormを設定する
		// -------------------------------------------------------


		// ----------------------------------------------------
		// 酒販店ﾏｽﾀｰ受信更新
		// ----------------------------------------------------
		if (REQ_TYPE_SYUHANTENEDI.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　酒販店マスター　EDI　受信
				comBatchUtil.startJobnet(JOB_TYPE_SYUHANTENEDI_JOBNET,
						                              JOB_TYPE_SYUHANTENEDI_MSG);
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_IKKATU.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　一括
				comBatchUtil.startJobnet(JOB_TYPE_IKKATU_JOBNET,
						                              JOB_TYPE_IKKATU_MSG);
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_SYUHANTENKOUSIN.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　酒販店マスター更新
				comBatchUtil.startJobnet(JOB_TYPE_SYUHANTENKOUSIN_JOBNET,
						                              JOB_TYPE_SYUHANTENKOUSIN_MSG);
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_EISIENFILE.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　営業支援用ファイル
				comBatchUtil.startJobnet(JOB_TYPE_EISIENFILE_JOBNET,
						                              JOB_TYPE_EISIENFILE_MSG);
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
