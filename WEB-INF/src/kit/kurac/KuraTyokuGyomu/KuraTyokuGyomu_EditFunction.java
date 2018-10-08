package kit.kurac.KuraTyokuGyomu;

import static kit.kurac.KuraTyokuGyomu.IKuraTyokuGyomu.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComBatchUtil;
import fb.com.exception.KitComException;
import fb.inf.KitFunction;
import fb.inf.exception.KitException;

/**
 * 96105_1_1 蔵直業務
 *
 * @author tuneduka
 *
 */
public class KuraTyokuGyomu_EditFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = -8363470122360732135L;

	/** 編集フォーム */
	private KuraTyokuGyomu_EditForm editForm = null;

	/** クラス名. */
	private static String className__ = KuraTyokuGyomu_EditFunction.class.getName();

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
	public KuraTyokuGyomu_EditFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
		this.editForm = (KuraTyokuGyomu_EditForm) form_;

		// 呪文：リクエストタイプが無い場合にExceptionを発行させる
		this.editForm.checkHasReqType();

		// --------------------------------------------------------
		// 入力チェック
		// -------------------------------------------------------
		// チェックサービスを初期化
		KuraTyokuGyomu_CheckService checkServ = new KuraTyokuGyomu_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 呼出時チェックを実行
		if (!checkServ.validateSearch(editForm)) {
			return sForwardPage;
		}

		// --------------------------------------------------------
		// ボタン押下(リクエストタイプ)に応じた処理を
		// 実装し、結果に応じてeditFormを設定する
		// -------------------------------------------------------
		// バッチ起動共通クラス
		ComBatchUtil comBatchUtil = new ComBatchUtil(getComUserSession(), getDatabase(), isTransactionalFunction(), getActionErrors());


		// ----------------------------------------------------
		// 蔵直業務
		// ----------------------------------------------------
		if (REQ_TYPE_IKKATU.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動  一括
				comBatchUtil.startJobnet(JOB_TYPE_IKKATU_JOBNET,
						                              JOB_TYPE_IKKATU_MSG,
													  "from_date="+this.editForm.getFromDt(),
													  "to_date="+this.editForm.getToDt());
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_NYURYOKUSYOSAI.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　入力詳細 (UpLoad)
				comBatchUtil.startJobnet(JOB_TYPE_NYURYOKUSYOSAI_JOBNET,
						                              JOB_TYPE_NYURYOKUSYOSAI_MSG);
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_KURAERRORCHECK.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　入力エラーチェック
				comBatchUtil.startJobnet(JOB_TYPE_KURAERRORCHECK_JOBNET,
													  JOB_TYPE_KURAERRORCHECK_MSG,
													  "from_date="+this.editForm.getFromDt(),
													  "to_date="+this.editForm.getToDt());

			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_TOKURATBL.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　蔵直テーブルへ登録
				comBatchUtil.startJobnet(JOB_TYPE_TOKURATBL_JOBNET,
													  JOB_TYPE_TOKURATBL_MSG,
													  "from_date="+this.editForm.getFromDt(),
													  "to_date="+this.editForm.getToDt());
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_KURAGOKEI.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　合計表
				comBatchUtil.startJobnet(JOB_TYPE_KURAGOKEI_JOBNET,
													  JOB_TYPE_KURAGOKEI_MSG,
													  "from_date="+this.editForm.getFromDt(),
													  "to_date="+this.editForm.getToDt());
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_KURACL.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　チェックリスト
				comBatchUtil.startJobnet(JOB_TYPE_KURACL_JOBNET,
													  JOB_TYPE_KURACL_MSG,
													  "from_date="+this.editForm.getFromDt(),
													  "to_date="+this.editForm.getToDt(),
													  "hassou_date="+this.editForm.getHassouDt());
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_KURAKENS.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　蔵直検索
				comBatchUtil.startJobnet(JOB_TYPE_KURAKENS_JOBNET,
													  JOB_TYPE_KURAKENS_MSG,
													  "from_date="+this.editForm.getFromDt(),
													  "to_date="+this.editForm.getToDt());
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_KURAKENSDUMMY.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　蔵直検索用更新ﾀﾞﾐｰ
				comBatchUtil.startJobnet(JOB_TYPE_KURAKENSDUMMY_JOBNET,
													  JOB_TYPE_KURAKENSDUMMY_MSG);
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_SYUTURYOKUSYOSAI.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　出力詳細
				comBatchUtil.startJobnet(JOB_TYPE_SYUTURYOKUSYOSAI_JOBNET,
													  JOB_TYPE_SYUTURYOKUSYOSAI_MSG,
													  "from_date="+this.editForm.getFromDt(),
													  "to_date="+this.editForm.getToDt());
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_KURANOSI.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　のしシール
				comBatchUtil.startJobnet(JOB_TYPE_KURANOSI_JOBNET,
													  JOB_TYPE_KURANOSI_MSG,
													  "from_date="+this.editForm.getFromDt(),
													  "to_date="+this.editForm.getToDt());
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_TOJYUCYUTBL.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　受注テーブルへ追加
				comBatchUtil.startJobnet(JOB_TYPE_TOJYUCYUTBL_JOBNET,
													  JOB_TYPE_TOJYUCYUTBL_MSG,
													  "from_date="+this.editForm.getFromDt(),
													  "to_date="+this.editForm.getToDt());
			} catch (Exception e) {
				return FORWARD_FAIL;
			}

		} else if (REQ_TYPE_KURACANCYUSEN.equals(this.editForm.getReqType())) {

			try {
				//ジョブネット起動　ｷｬﾝﾍﾟｰﾝ抽選ﾌｧｲﾙ
				comBatchUtil.startJobnet(JOB_TYPE_KURACANCYUSEN_JOBNET,
													  JOB_TYPE_KURACANCYUSEN_MSG,
													  "from_date="+this.editForm.getFromDt(),
													  "to_date="+this.editForm.getToDt());
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
