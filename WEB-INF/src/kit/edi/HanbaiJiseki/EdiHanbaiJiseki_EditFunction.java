package kit.edi.HanbaiJiseki;

import static kit.edi.HanbaiJiseki.IEdiHanbaiJiseki.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComBatchUtil;
import fb.com.exception.KitComException;
import fb.inf.KitFunction;
import fb.inf.exception.KitException;

/**
 * 99903_1_1　販売実績受信
 *
 * @author Tuneduka
 *
 */
public class EdiHanbaiJiseki_EditFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = 2800839068655951683L;

	/** 編集フォーム */
	private EdiHanbaiJiseki_EditForm editForm = null;

	/** クラス名. */
	private static String className__ = EdiHanbaiJiseki_EditFunction.class.getName();

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
	public EdiHanbaiJiseki_EditFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
		this.editForm = (EdiHanbaiJiseki_EditForm) form_;

		// 呪文：リクエストタイプが無い場合にExceptionを発行させる
		this.editForm.checkHasReqType();

		// バッチ起動共通クラス
		ComBatchUtil comBatchUtil = new ComBatchUtil(getComUserSession(), getDatabase(), isTransactionalFunction(), getActionErrors());

		// --------------------------------------------------------
		// ボタン押下(リクエストタイプ)に応じた処理を
		// 実装し、結果に応じてeditFormを設定する
		// -------------------------------------------------------


		// ----------------------------------------------------
		// 販売実績受信
		// ----------------------------------------------------
		if (REQ_TYPE_ITOCYUEDI.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　伊藤忠　販売実績　EDI　受信
				comBatchUtil.startJobnet(JOB_TYPE_ITOCYUEDI_JOBNET,
						                              JOB_TYPE_ITOCYUEDI_MSG);
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_ITOCYUFILE.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　伊藤忠販売実績データ格納
				comBatchUtil.startJobnet(JOB_TYPE_ITOCYUFILE_JOBNET,
						                              JOB_TYPE_ITOCYUFILE_MSG);
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_OKAMURAEDI.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　岡村　販売実績　EDI　受信
				comBatchUtil.startJobnet(JOB_TYPE_OKAMURAEDI_JOBNET,
						                              JOB_TYPE_OKAMURAEDI_MSG);
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_OKAMURAPBCK.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　岡村　PBコードチェック
				comBatchUtil.startJobnet(JOB_TYPE_OKAMURAPBCK_JOBNET,
						                              JOB_TYPE_OKAMURAPBCK_MSG);
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_OKAMURAFILE.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　岡村販売実績データ格納
				comBatchUtil.startJobnet(JOB_TYPE_OKAMURAFILE_JOBNET,
						                              JOB_TYPE_OKAMURAFILE_MSG);
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
