package kit.kurac.KuraChokuDataUp;


import static fb.com.IKitComErrorConst.*;
import static kit.kurac.KuraChokuDataUp.IKuracKuraChokuDataUp.*;
import static fb.com.IKitComConst.*;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import fb.com.ComRecordUtil;
import fb.com.ComUserSession;
import fb.com.ComValidateUtil;
import fb.inf.KitService;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsUtil;

/**
 *
 * @author mori
 *
 */
public class KuracKuraChokuDataUp_CheckService extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = -4278551509177893180L;
	/** クラス名. */
	private static String className__ = KuracKuraChokuDataUp_CheckService.class.getName();
	/** カテゴリ. */
	public static Category category__ = Category.getInstance(className__);

	/** チェックユーティリティー */
	private ComValidateUtil vUtil = null;
	/** 存在チェックユーティリティー */
	private ComRecordUtil rUtil = null;

	/** データベースオブジェクト */
	private PbsDatabase db_;

	/**
	 * コンストラクタ.<br>ユーティリティーオブジェクトを初期化する。
	 * @param cus ユーザセッション
	 * @param db_ DBオブジェクト
	 * @param isTran トランザクション情報
	 * @param ae アクションエラー情報
	 */
	public KuracKuraChokuDataUp_CheckService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
		super(cus, db_, isTran, ae);
		// データベースオブジェクトを初期化する
		this.db_ = db_;


	}

	// ##  SearchForm  ################################################
	// ### 検索 ######################################################

	/**
	 * 検索フォームの入力チェックを行う.(共通)
	 *
	 * @param editList
	 * @return true(エラー無) or false(エラー有)
	 */
	public boolean validateSearch(KuracKuraChokuDataUp_EditForm editForm) {

		// ==========================================
		// 必須フォーマットチェックを行う。
		// ⇒inputCheckFormatForSearchメソッド呼び出し
		// ==========================================

		if (REQ_TYPE_KURACCSVMAKE.equals(editForm.getReqType())){

			// 日付期間指定チェックを行う
			if(!PbsUtil.isEmpty(editForm.getFromDt()) && !PbsUtil.isEmpty(editForm.getToDt())){
				if(editForm.getFromDt().compareTo(editForm.getToDt()) > 0){
					setErrorMessageId(new ActionError(ERRORS_DATE_ERROR, "日付期間指定"));
					return false;
				}
			}

			// 作成対象入力チェックを行う
			if(PbsUtil.isEmpty(editForm.getSakuseitaisyouKbn())) {
				setErrorMessageId(new ActionError(ERRORS_MISTAKE, "作成対象"));
				return false;
			}
		}

		// 作成対象01,02の時の日付期間指定チェックを行う
		if(editForm.getSakuseitaisyouKbn().equals(KURAC_CSVMAKE_KB_TUUCHI_MITEISEI) ||
			editForm.getSakuseitaisyouKbn().equals(KURAC_CSVMAKE_KB_TUUCHI_TEISEI)){
			if(!(editForm.getFromDt().compareTo(editForm.getToDt()) == 0)){
				setErrorMessageId(new ActionError(ERRORS_DATE_ERROR, "日付期間指定"));
				return false;
			}
		}

		return true;
	}



} // -- class