package kit.kurac.KuraChokuDataUp;

import static fb.com.IKitComConst.*;
import static kit.kurac.KuraChokuDataUp.IKuracKuraChokuDataUp.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComBatchUtil;
import fb.com.exception.KitComException;
import fb.inf.KitFunction;
import fb.inf.exception.KitException;

/**
 *
 *
 * @author Tuneduka
 *
 */
public class KuracKuraChokuDataUp_EditFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = -1075768262671807313L;

	/** 編集フォーム */
	private KuracKuraChokuDataUp_EditForm editForm = null;

	/** クラス名. */
	private static String className__ = KuracKuraChokuDataUp_EditFunction.class.getName();

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
	public KuracKuraChokuDataUp_EditFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
		this.editForm = (KuracKuraChokuDataUp_EditForm) form_;

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
		KuracKuraChokuDataUp_CheckService checkServ = new KuracKuraChokuDataUp_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 呼出時チェックを実行
		if (!checkServ.validateSearch(editForm)) {
			return sForwardPage;
		}


		// ----------------------------------------------------
		// 蔵元レポート
		// ----------------------------------------------------

		if(editForm.getSakuseitaisyouKbn().equals(KURAC_CSVMAKE_KB_TUUCHI_MITEISEI) ||
			editForm.getSakuseitaisyouKbn().equals(KURAC_CSVMAKE_KB_TUUCHI_TEISEI)    ||
			editForm.getSakuseitaisyouKbn().equals(KURAC_CSVMAKE_KB_SEIKYU_MEISAI)) {

			try {
				// 発送通知書、請求明細書
				comBatchUtil.startJobnet(JOB_TYPE_HASSOTUCHI_JOBNET,
						                 JOB_TYPE_HASSOTUCHI_MSG,
					                     "from_date="+this.editForm.getFromDt(),
					                     "to_date="+this.editForm.getToDt(),
					                     "report_kbn="+this.editForm.getSakuseitaisyouKbn());
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if(editForm.getSakuseitaisyouKbn().equals(KURAC_CSVMAKE_KB_GOUKEIHYO)){

			try {
				// 出荷商品合計書
				comBatchUtil.startJobnet(JOB_TYPE_GOUKEIHYO_JOBNET,
										 JOB_TYPE_GOUKEIHYO_MSG,
										 "from_date="+this.editForm.getFromDt(),
										 "to_date="+this.editForm.getToDt());
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if(editForm.getSakuseitaisyouKbn().equals(KURAC_CSVMAKE_KB_URIAGE_SYUKEI)){

			try {
				// 部署別売上集計（小売のみ）
				comBatchUtil.startJobnet(JOB_TYPE_BUSYOSYUKEI_JOBNET,
						 				 JOB_TYPE_BUSYOSYUKEI_MSG,
						 				 "from_date="+this.editForm.getFromDt(),
						 				 "to_date="+this.editForm.getToDt());
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if(editForm.getSakuseitaisyouKbn().equals(KURAC_CSVMAKE_KB_NOSI_SEAL)){

			try {
				// 蔵直のしシール
				comBatchUtil.startJobnet(JOB_TYPE_NOSISEAL_JOBNET,
						 				 JOB_TYPE_NOSISEAL_MSG,
						 				 "from_date="+this.editForm.getFromDt(),
						 				 "to_date="+this.editForm.getToDt());
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if(editForm.getSakuseitaisyouKbn().equals(KURAC_CSVMAKE_KB_CAMPAIGN)){

			try {
				// 蔵直キャンペーン抽選
				comBatchUtil.startJobnet(JOB_TYPE_CAMPAIGN_JOBNET,
						 				 JOB_TYPE_CAMPAIGN_MSG,
						 				 "from_date="+this.editForm.getFromDt(),
						 				 "to_date="+this.editForm.getToDt());
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else {
			throw new KitComException("$$$ This form has no reqType . $$$");
		}


		setRequest(ONLOAD_POPUP_URL,JOB_STATUS_POPUP_URL);

		return sForwardPage;
	}


}
