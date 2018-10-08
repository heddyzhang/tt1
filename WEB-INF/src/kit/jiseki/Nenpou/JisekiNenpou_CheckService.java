package kit.jiseki.Nenpou;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;
import static kit.jiseki.Nenpou.IJisekiNenpou.*;

import java.text.ParseException;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import fb.com.ComKengenUtil;
import fb.com.ComRecordUtil;
import fb.com.ComUserSession;
import fb.com.ComUtil;
import fb.com.ComValidateUtil;
import fb.com.SubRoutine.ComDaikoUtil;
import fb.inf.KitService;
import fb.inf.exception.KitException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 入力内容をチェックするビジネスロジッククラスです
 *
 * @author nishikawa@kz
 * @version 2015/11/16
 *
 */
public class JisekiNenpou_CheckService extends KitService {


	/** シリアルID */
	private static final long serialVersionUID = 4787790827408808612L;


	/** クラス名. */
	private static String className__ = JisekiNenpou_CheckService.class.getName();

	/** カテゴリ. */
	public static Category category__ = Category.getInstance(className__);




	/** データベースオブジェクト */
	private PbsDatabase db_;

	/** 選択されたレコード */
	private JisekiNenpouRecord recSelected;

	/** 存在チェックユーティリティー */
	private ComRecordUtil rUtil = null;

	/** チェックユーティリティー */
	private ComValidateUtil vUtil = null;

	/**
	 * コンストラクタ.<br>
	 * ユーティリティーオブジェクトを初期化する。
	 *
	 * @param cus
	 *            ユーザセッション
	 * @param db_
	 *            DBオブジェクト
	 * @param isTran
	 *            トランザクション情報
	 * @param ae
	 *            アクションエラー情報
	 */
	public JisekiNenpou_CheckService(ComUserSession cus, PbsDatabase db_, boolean isTran,
			ActionErrors ae) {

		super(cus, db_, isTran, ae);
		_reset();
		// 評価ユーティリティーを初期化する
		this.vUtil = new ComValidateUtil(ae);
		// 存在チェックユーティリティーを初期化する
		this.rUtil = new ComRecordUtil(db_);
		// データベースオブジェクトを初期化する
		this.db_ = db_;

	}

	// === public =============================

	/**
	 * XXX:選択済みレコードを取得する<br/>
	 * ※ EditListをSearchedListの参照から切り離すためにnewする
	 *
	 * @return recSelected
	 */
	public JisekiNenpouRecord getRecSelected() {
		return new JisekiNenpouRecord(recSelected.getHashMap());
	}


// ===================================
// ===== 呼出時のチェックシリーズ ====
// ===================================

	/**
	 * 呼出ボタン押下時のチェックを行う(on SearchForm)
	 *
	 * @param editList
	 * @return true(エラー無) or false(エラー有)
	 * @throws ParseException
	 */
	public boolean validateSearch(JisekiNenpou_SearchForm searchForm) throws ParseException {

		// 検索実行
		JisekiNenpou_SearchService searchServ = new JisekiNenpou_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// ==========================================
		// 権限チェックチェックを行う。
		// ==========================================
		if (!inputCheckKengen(searchForm)) {
			return false;
		}

		// ==========================================
		// 必須チェックチェックを行う。
		// ⇒inputCheckRequiredForSearchメソッド呼び出し
		// ==========================================
		if (!inputCheckRequiredForSearch(searchForm)) {
			return false;
		}

		// ==========================================
		// 属性チェックチェックを行う。
		// ⇒inputCheckForSearchメソッド呼び出し
		// ==========================================
		if (!inputCheckForSearch(searchForm)) {
			return false;
		}

		// ==================================================
		// 日付桁チェックを行う 入力値4桁の場合、6桁成型
		// ==================================================
			inputCheckKeta(searchForm);


		// ==================================================
		// 日付空白チェックを行う　未入力箇所同年月セット
		// ==================================================
//			inputCheckEmpty(searchForm);


		// ==================================================
		// 日付関連チェックを行う 期間開始日　≦　期間終了日
		// ==================================================
			if (!inputCheckXSyuryoDt(searchForm)) {
				return false;
			}

		// ==================================================
		// 日付関連チェックを行う 期間12ヶ月以内
		// ==================================================
				if (!inputCheckKikanMonth(searchServ,searchForm)) {
				return false;
			}



		// ==================================
		// その他の設定 値セット
		// ⇒メソッド呼び出し
		// ==================================

	//	setOthers(searchForm);


		return true;
	}


	/**
	 * 検索フォームの入力チェックを行う（CSV出力）
	 *
	 * @param searchForm
	 * @return
	 */
	public boolean validateCsvMake(JisekiNenpou_SearchForm searchForm) {

		// ==========================================
		// 権限チェックチェックを行う。
		// ==========================================
		// XXX: 権限チェック作成後実装する

		// ==========================================
		// 必須チェックチェックを行う。
		// ⇒inputCheckRequiredForCsvMakeメソッド呼び出し
		// ==========================================
		if (!inputCheckRequiredForCsvMake(searchForm)) {
			return false;
		}
		return true;
	}


	// === private =============================


	/**
	 * 呼出時権限チェックを行う
	 *
	 * @param searchForm
	 * @return return / false
	 */
	private boolean inputCheckKengen(JisekiNenpou_SearchForm searchForm) {
		boolean res = true;

		return res;
	}



// ==============================
// ==== 必須チェックシリーズ ====
// ==============================
	/**
	 * 必須チェックを行う（CSV出力用）
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean inputCheckRequiredForCsvMake(JisekiNenpou_SearchForm searchForm) {

		// 必須入力があった場合適時入力。

		return true;
	}

	/**
	 * 必須チェックを行う（呼出用）
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean inputCheckRequiredForSearch(JisekiNenpou_SearchForm searchForm) {


		// 必須項目なし 期間はjspでチェックする

		return true;
	}


	/**
	 * 属性チェックを行う（呼出用）
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean inputCheckForSearch(JisekiNenpou_SearchForm searchForm) {

		String msgKey_itemNm = "";

		// ### 特約店コード #########################################
//		msgKey_itemNm = "com.text.tokuyakutenCd";
//		if (!vUtil.validateCodeSimple(searchForm.getTokuyakutenCd(), IS_REQUIRED_FALSE, MAX_LEN_TOKUYAKU_CD, msgKey_itemNm)) {
//			return false;
//		}


		return true;
	}


// ==================================
// ==== パーツごとの単体チェック ====
// ==================================

// 編集画面がない為　不要


// =============================
// ====    関連チェック     ====
// =============================

	/**
	 * 期間6桁成型(4桁で入力された場合)　
	 *
	 * @param
	 * @return
	 */

	private void inputCheckKeta(JisekiNenpou_SearchForm searchForm)  {
		String kikanStart = searchForm.getKikanStart();
		String kikanEnd = searchForm.getKikanEnd();
		if (PbsUtil.isEmpty(kikanStart) || PbsUtil.checkStringLen(kikanStart, MAX_LEN_NEN4KETA , MAX_LEN_NEN4KETA)==0) {
			searchForm.setKikanStart(kikanStart + "01");
			searchForm.setKikanEnd(kikanEnd + "12");
		}
	}


	/**
	 * 期間片方のみ入力された場合、同日をもう片方にセットする
	 *
	 * @param
	 * @return
	 */

	private void inputCheckEmpty(JisekiNenpou_SearchForm searchForm) {
		String kikanStart = searchForm.getKikanStart();
		String kikanEnd = searchForm.getKikanEnd();
		if (PbsUtil.isEmpty(kikanStart)) {
			searchForm.setKikanStart(kikanEnd);
		}
		if (PbsUtil.isEmpty(kikanEnd)) {
			searchForm.setKikanEnd(kikanStart);
		}
	}




	/**
	 * 期間開始年月　≦　期間終了年月　
	 *
	 * @param rec レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXSyuryoDt(JisekiNenpou_SearchForm rec) {
		if (!PbsUtil.isFutureYm(rec.getKikanStart(), rec.getKikanEnd(), true)) {
			// エラーメッセージ
			setErrorMessageId("errors.input.after",
				PbsUtil.getMessageResourceString("com.text.kikanEnd"),
				PbsUtil.getMessageResourceString("com.text.kikanStart"));
			return false;
		}
		return true;
	}


	/**
	 * 表示月数　≦　１２ヶ月
	 *
	 * @param rec 表示月数
	 * @param searchForm
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 * @throws ParseException
	 */
	private boolean inputCheckKikanMonth(JisekiNenpou_SearchService rec , JisekiNenpou_SearchForm searchForm) throws ParseException {
		// 表示月数取得
			if (ComUtil.decimalCompareTo(rec.getKikanMonth(searchForm.getKikanStart(), searchForm.getKikanEnd()),"12") > 0) { // 検索可能期間超え
			// エラーメッセージ
			setErrorMessageId("errors.kikanMax12",PbsUtil.getMessageResourceString("com.text.kikan"));
			return false;
		}
		return true;
	}


	// === protected =============================

	/**
	 * 入出力パラメータの初期化を行う.
	 */
	protected void _reset() {
		this.vUtil = null;
		this.rUtil = null;
		this.db_ = null;

		// 選択済みレコードを初期化
		this.recSelected = null;

	}


} // -- class
