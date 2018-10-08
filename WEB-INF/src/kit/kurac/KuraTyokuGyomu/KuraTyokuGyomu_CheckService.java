package kit.kurac.KuraTyokuGyomu;

import static fb.com.IKitComErrorConst.*;
import static kit.kurac.KuraTyokuGyomu.IKuraTyokuGyomu.*;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import fb.com.ComUserSession;
import fb.inf.KitService;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsUtil;

public class KuraTyokuGyomu_CheckService  extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = -7709484067901673756L;

	/** クラス名. */
	private static String className__ = KuraTyokuGyomu_CheckService.class.getName();

	/** カテゴリ. */
	public static Category category__ = Category.getInstance(className__);

	/** データベースオブジェクト */
	private PbsDatabase db_;


	/**
	 * コンストラクタ.<br>
	 * ユーティリティーオブジェクトを初期化する。
	 *
	 * @param cus ユーザセッション
	 * @param db_ DBオブジェクト
	 * @param isTran トランザクション情報
	 * @param ae アクションエラー情報
	 */
	public KuraTyokuGyomu_CheckService(ComUserSession cus, PbsDatabase db_, boolean isTran,
			ActionErrors ae) {

		super(cus, db_, isTran, ae);
		// データベースオブジェクトを初期化する
		this.db_ = db_;

	}

	/**
	 * 呼出ボタン押下時のチェックを行う(on SearchForm)
	 *
	 * @param editForm
	 * @return true(エラー無) or false(エラー有)
	 */
	public boolean validateSearch(KuraTyokuGyomu_EditForm editForm) {

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		if (!inputCheckKengen(editForm)) {
			return false;
		}

		// ==================================================
		// 必須チェックを行う。
		// ==================================================
		if (!inputCheckRequiredForSearch(editForm)) {
			return false;
		}

		// ==================================================
		// 属性チェックを行う。
		// ==================================================
		if (!inputCheckForSearch(editForm)) {
			return false;
		}

		return true;
	}



	/**
	 * 呼出時権限チェックを行う
	 *
	 * @param searchForm
	 * @return
	 */
	private boolean inputCheckKengen(KuraTyokuGyomu_EditForm editForm) {
		boolean res = true;

		return res;
	}


	/**
	 * 必須チェックを行う（呼出用）
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean inputCheckRequiredForSearch(KuraTyokuGyomu_EditForm editForm) {

		if (REQ_TYPE_KURAGOKEI.equals(editForm.getReqType()) ||
			REQ_TYPE_KURAERRORCHECK.equals(editForm.getReqType()) ||
			REQ_TYPE_KURAKENS.equals(editForm.getReqType()) ||
			REQ_TYPE_SYUTURYOKUSYOSAI.equals(editForm.getReqType()) ||
			REQ_TYPE_KURANOSI.equals(editForm.getReqType()) ||
			REQ_TYPE_TOJYUCYUTBL.equals(editForm.getReqType())){

			if(!PbsUtil.isEqual(editForm.getFromDt(),editForm.getToDt()) ){

				setErrorMessageId("errors.hidukekikan.check");
				return false;

			}
		}

		if (REQ_TYPE_KURACL.equals(editForm.getReqType()) ||
			REQ_TYPE_KURACANCYUSEN.equals(editForm.getReqType())){

			if(PbsUtil.isEmpty(editForm.getFromDt()) || PbsUtil.isEmpty(editForm.getToDt())){
				setErrorMessageId(new ActionError(ERRORS_DATE_ERROR, "日付期間指定"));
				return false;
			} else {
				if(editForm.getFromDt().compareTo(editForm.getToDt()) > 0){
					setErrorMessageId(new ActionError(ERRORS_DATE_ERROR, "日付期間指定"));
					return false;
				}
			}
		}

		return true;
	}


	/**
	 * 属性チェックを行う（呼出用）
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean inputCheckForSearch(KuraTyokuGyomu_EditForm editForm) {

		return true;
	}

}
