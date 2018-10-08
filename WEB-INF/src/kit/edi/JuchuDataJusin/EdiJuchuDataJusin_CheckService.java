package kit.edi.JuchuDataJusin;


import static fb.com.IKitComErrorConst.*;
import static kit.edi.JuchuDataJusin.IEdiJuchuDataJusin.*;

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
 * 摘要区分マスタのチェックサービスクラスです。
 * @author mori
 *
 */
public class EdiJuchuDataJusin_CheckService extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = -4278551509177893180L;
	/** クラス名. */
	private static String className__ = EdiJuchuDataJusin_CheckService.class.getName();
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
	public EdiJuchuDataJusin_CheckService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
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
	public boolean validateSearch(EdiJuchuDataJusin_EditForm editForm) {

		// ==========================================
		// 必須フォーマットチェックを行う。
		// ⇒inputCheckFormatForSearchメソッド呼び出し
		// ==========================================
		if (REQ_TYPE_ONLINEEDI.equals(editForm.getReqType())){

			if(PbsUtil.isEmpty(editForm.getJyusinsakiKbn())) {
				setErrorMessageId(new ActionError(ERRORS_MISTAKE, "受信先"));
				return false;
			}
		}

		return true;
	}


} // -- class