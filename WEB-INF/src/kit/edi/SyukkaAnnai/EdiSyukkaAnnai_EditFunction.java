package kit.edi.SyukkaAnnai;

import static fb.com.IKitComConst.*;
import static kit.edi.SyukkaAnnai.IEdiSyukkaAnnai.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComBatchUtil;
import fb.com.exception.KitComException;
import fb.inf.KitFunction;
import fb.inf.exception.KitException;

/**
 * 99901_1_1　出荷案内送信
 *
 * @author Tuneduka
 *
 */
public class EdiSyukkaAnnai_EditFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = -1075768262671807313L;

	/** 編集フォーム */
	private EdiSyukkaAnnai_EditForm editForm = null;

	/** クラス名. */
	private static String className__ = EdiSyukkaAnnai_EditFunction.class.getName();

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
	public EdiSyukkaAnnai_EditFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
		this.editForm = (EdiSyukkaAnnai_EditForm) form_;

		// 呪文：リクエストタイプが無い場合にExceptionを発行させる
		this.editForm.checkHasReqType();

		// バッチ起動共通クラス
		ComBatchUtil comBatchUtil = new ComBatchUtil(getComUserSession(), getDatabase(), isTransactionalFunction(), getActionErrors());

		// --------------------------------------------------------
		// ボタン押下(リクエストタイプ)に応じた処理を
		// 実装し、結果に応じてeditFormを設定する
		// -------------------------------------------------------


		// --------------------------------------------------------
		// 入力チェック
		// -------------------------------------------------------
		// チェックサービスを初期化
		EdiSyukkaAnnai_CheckService checkServ = new EdiSyukkaAnnai_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 呼出時チェックを実行
		if (!checkServ.validateSearch(editForm)) {
			return sForwardPage;
		}


		// ----------------------------------------------------
		// 出荷案内送信
		// ----------------------------------------------------
		if (REQ_TYPE_IKKATU.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　一括
				comBatchUtil.startJobnet(JOB_TYPE_IKKATU_JOBNET,
						                 JOB_TYPE_IKKATU_MSG,
					                     "from_date="+this.editForm.getFromDt(),
					                     "to_date="+this.editForm.getToDt(),
					                     "edi_group_cd="+this.editForm.getSakuseitaisyouKbn());
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_SYUKKAANNAIEDI.equals(this.editForm.getReqType())) {

			//ジョブネット起動　出荷案内 送信
			if(editForm.getSousinsakiKbn().equals(EDI_SYUKKA_PARTNER_KB_IKKATU)){

				try {
					comBatchUtil.startJobnet(JOB_TYPE_SYUKKAANNAIEDI_JOBNET,
											 JOB_TYPE_SYUKKAANNAIEDI_MSG);
				} catch (Exception e) {
					return FORWARD_FAIL;
				}

			} else if(editForm.getSousinsakiKbn().equals(EDI_SYUKKA_PARTNER_KB_SDN)){

				try {
					//ジョブネット起動　SDN 送信
					comBatchUtil.startJobnet(JOB_TYPE_SYUKKASDNEDI_JOBNET,
											 JOB_TYPE_SYUKKASDNEDI_MSG);
				} catch (Exception e) {
					return FORWARD_FAIL;
				}

			} else if(editForm.getSousinsakiKbn().equals(EDI_SYUKKA_PARTNER_KB_FINET)){

				try {
					//ジョブネット起動　FINET 送信
					comBatchUtil.startJobnet(JOB_TYPE_SYUKKAFINETEDI_JOBNET,
											 JOB_TYPE_SYUKKAFINETEDI_MSG);
				} catch (Exception e) {
					return FORWARD_FAIL;
				}

			} else {
				throw new KitComException("$$$ This form has no reqType . $$$");
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
