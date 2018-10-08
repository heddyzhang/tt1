package kit.juchu.JuchuAddOnly;

import static fb.inf.IKitConst.*;
import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;
import static kit.juchu.JuchuAddOnly.IJuchuJuchuAddOnly.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kit.mastr.EigyoCalendar.MastrEigyoCalendar_CheckService;
import kit.mastr.OrositenSyosai.MastrOrositenSyosaiDtList;
import kit.mastr.OrositenSyosai.MastrOrositenSyosaiDtRecord;
import kit.mastr.Seikyusaki.MastrSeikyusakiList;
import kit.mastr.Seikyusaki.MastrSeikyusakiRecord;
import kit.mastr.Seikyusaki.MastrSeikyusaki_SearchForm;
import kit.mastr.Seikyusaki.MastrSeikyusaki_SearchService;
import kit.mastr.TatesenOrositen.MastrTatesenOrositenRecord;
import kit.mastr.TatesenOrositen.MastrTatesenOrositen_SearchService;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import fb.com.ComRecordUtil;
import fb.com.ComUserSession;
import fb.com.ComUtil;
import fb.com.ComValidateUtil;
import fb.com.Records.FbMastrEigyoCalendarRecord;
import fb.com.Records.FbMastrKbnHanbaiBumonRecord;
import fb.com.Records.FbMastrKbnHanbaiBunruiRecord;
import fb.com.Records.FbMastrKbnHanbaiSyubetuRecord;
import fb.com.Records.FbMastrKbnJisekiTantoRecord;
import fb.com.Records.FbMastrKbnTaneRecord;
import fb.com.Records.FbMastrKbnTekiyoRecord;
import fb.com.Records.FbMastrOrositenRecord;
import fb.com.Records.FbMastrSeihinRecord;
import fb.com.Records.FbMastrSgyosyaRecord;
import fb.com.Records.FbMastrSiirehinRecord;
import fb.com.Records.FbMastrSyohinRecord;
import fb.com.Records.FbMastrSyuhantenRecord;
import fb.com.Records.FbMastrUnsotenRecord;
import fb.com.SerialNumber.ComJuchuRnbnKanri;
import fb.com.zik.ComCallZik_ZikSearchService;
import fb.inf.KitSearchForm;
import fb.inf.KitService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.KitException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 入力内容をチェックするビジネスロジッククラスです
 */
public class JuchuJuchuAddOnly_CheckService extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = 5044009508145277035L;

	/** クラス名. */
	private static String className__ = JuchuJuchuAddOnly_CheckService.class.getName();

	/** カテゴリ. */
	public static Category category__ = Category.getInstance(className__);

	/** データベースオブジェクト */
	private PbsDatabase db_;

	/** 選択されたレコード */
	private JuchuJuchuAddOnlyRecord recSelected;

	/** 存在チェックユーティリティー */
	private ComRecordUtil rUtil = null;

	/** チェックユーティリティー */
	private ComValidateUtil vUtil = null;


	/**
	 * コンストラクタ.<br>
	 * ユーティリティーオブジェクトを初期化する。
	 *
	 * @param cus ユーザセッション
	 * @param db_ DBオブジェクト
	 * @param isTran トランザクション情報
	 * @param ae アクションエラー情報
	 */
	public JuchuJuchuAddOnly_CheckService(ComUserSession cus, PbsDatabase db_, boolean isTran,
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
	 * 選択済みレコードを取得する<br/>
	 * ※ EditListをSearchedListの参照から切り離すためにnewする
	 *
	 * @return recSelected
	 */
	public JuchuJuchuAddOnlyRecord getRecSelected() {
		return new JuchuJuchuAddOnlyRecord(recSelected.getHashMap());
	}


// ===================================
// ===== 呼出時のチェックシリーズ ====
// ===================================

	/**
	 * 呼出ボタン押下時のチェックを行う(on SearchForm)
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	public boolean validateSearch(JuchuJuchuAddOnly_SearchForm searchForm) {

		// フラグ初期化
		searchForm.setOrositenCdLastClass(STYLE_CLASS_INIT);

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		// kit:buttonタグでボタン表示制御済み、ここではチェックなし

		// ==================================================
		// 必須チェックを行う。
		// ==================================================
		if (!inputCheckRequiredForSearch(searchForm)) {
			return false;
		}

		// ==================================================
		// 属性チェックを行う。
		// ==================================================
		if (!inputCheckForSearch(searchForm)) {
			return false;
		}

		return true;
	}


	/**
	 * 検索フォームの入力チェックを行う（CSV出力）
	 *
	 * @param searchForm
	 * @return
	 */
	public boolean validateCsvMake(JuchuJuchuAddOnly_SearchForm searchForm) {

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		// kit:buttonタグでボタン表示制御済み、ここではチェックなし

		// ==================================================
		// 必須チェックを行う。
		// ==================================================
		if (!inputCheckRequiredForCsvMake(searchForm)) {
			return false;
		}
		return true;
	}


	/**
	 * 新規ボタン押下時のチェックを行う(on SearchForm)
	 *
	 * @param searchForm
	 * @return TRUE:正常 / FALSE:検索条件に誤りあり
	 */
	public boolean validateSearchForAdd(JuchuJuchuAddOnly_SearchForm searchForm) {

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		// kit:buttonタグでボタン表示制御済み、ここではチェックなし

		// ==================================================
		// 必須チェックを行う。
		// ==================================================
		if ( !inputCheckRequiredForInsert(searchForm) ) {
			return false;
		}

		return true;
	}

	/**
	 * セットボタン押下時チェック
	 *
	 * @param editForm
	 * @param searchedList
	 * @return TRUE / FALSE
	 */
	public boolean validateSelect(JuchuJuchuAddOnly_EditForm editForm, 	JuchuJuchuAddOnlyList searchedList) throws KitException {

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		// kit:buttonタグでボタン表示制御済み、ここではチェックなし

		/* *******************************
		 * 選択有無チェック
		 * 1. editFormセット欄
		 * 2. 行選択ID
		 * 3. 更新対象フラグ
		 * ****************************** */
		if (!inputCheckRequiredForSelect(editForm, searchedList)) {
			return false;
		}

		// 戻るボタンのTabIndexを設定する
		editForm.setTabIndexBack("1");

		return true;

	}


	/**
	 * セットボタン押下時チェック2
	 *  ViewJSPで検索部のボタンを非活性にするための値セット
	 *
	 * @param editList
	 * @param searchForm
	 */
	public void validateSelectBtnAvailFlg(JuchuJuchuAddOnlyList editList, JuchuJuchuAddOnly_SearchForm searchForm) {

		JuchuJuchuAddOnlyRecord rec = (JuchuJuchuAddOnlyRecord) editList.getFirstRecord();
		String jyucyuNo = rec.getJyucyuNo(); // 受注NO
		String syukaDt = rec.getSyukaDt(); // 出荷日

		// ====================================================================================================
		// 【配送受付窓口用】
		// 　修正　　：利用区分＝利用可　かつ　データ種別CD＝通常伝票/通常(単価違)伝票　かつ　受注NO末尾≠0
		// 　参照追加：データ種別CD＝受注キャンセル/売伝キャンセルのみ･･･利用区分＝利用停止
		// 　抹消　　：利用区分＝利用可　かつ　データ種別CD＝通常伝票/通常(単価違)伝票
		// （復活　　：利用区分＝利用停止　かつ　受注NO末尾＝≠0）
		// ====================================================================================================

		// 出荷日が過去（編集不可）
		if (!PbsUtil.isFutureDate(PbsUtil.getTodayYYYYMMDD(), syukaDt)) {
			// 修正ボタン
			searchForm.setEditBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性
			// 参照追加ボタン
			searchForm.setReferrerBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性
			// 抹消ボタン
			searchForm.setDeleteBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性
			// 復活ボタン
			searchForm.setRebirthBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性

		// 出荷日が本日以降
		} else {
			// 利用区分＝利用可の場合
			if (PbsUtil.isEqual(rec.getRiyouKbn(), AVAILABLE_KB_RIYO_KA)) {
				// 修正ボタン
				if (PbsUtil.isIncluded(rec.getSyubetuCd(), JDATAKIND_KB_TUJO, JDATAKIND_KB_TANKA_TIGAI)
						&& !jyucyuNo.matches(".*0$")) {
					searchForm.setEditBtnAvailFlg(BUTTON_AVAILABLE_TRUE); // 活性
				} else {
					searchForm.setEditBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性
				}

				// 参照追加ボタン
				searchForm.setReferrerBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性

				// 抹消ボタン
				if (PbsUtil.isIncluded(rec.getSyubetuCd(), JDATAKIND_KB_TUJO, JDATAKIND_KB_TANKA_TIGAI)) {
					searchForm.setDeleteBtnAvailFlg(BUTTON_AVAILABLE_TRUE); // 活性
				} else {
					searchForm.setDeleteBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性
				}

				// 復活ボタン
				searchForm.setRebirthBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性

			// 利用区分＝利用停止の場合
			} else if (PbsUtil.isEqual(rec.getRiyouKbn(), AVAILABLE_KB_RIYO_TEISI)) {
				// 修正ボタン
				searchForm.setEditBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性

				// 参照追加ボタン
				searchForm.setReferrerBtnAvailFlg(BUTTON_AVAILABLE_TRUE); // 活性

				// 抹消ボタン
				searchForm.setDeleteBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性

				// 復活ボタン
				if (!jyucyuNo.matches(".*0$")) {
					searchForm.setRebirthBtnAvailFlg(BUTTON_AVAILABLE_TRUE); // 活性
				} else {
					searchForm.setRebirthBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性
				}
			}
		}
	}


	/**
	 * 操作系画面（修正）呼出時チェック(on SearchForm)
	 *
	 * @param editForm
	 * @param editList
	 * @param editDtList
	 * @param selectedList
	 * @return TRUE / FALSE
	 */
	public boolean validateManipulationEdit(JuchuJuchuAddOnly_EditForm editForm,
			JuchuJuchuAddOnlyList editList, JuchuJuchuAddOnlyDtList editDtList,
			JuchuJuchuAddOnlyList selectedList) throws KitException {

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		// kit:buttonタグでボタン表示制御済み、ここではチェックなし

		// ==================================================
		// 必須チェックを行う。
		// ==================================================
		if (!inputCheckForManipulation(editList, editDtList, selectedList)) {
			return false;
		}

		// ==================================================
		// ボタン押下時チェックを行う。
		// ==================================================
		if (!searchCheckXForEdit(editList)) {
			return false;
		}

		return true;
	}

	/**
	 * 操作系画面（参照追加）呼出時チェック(on SearchForm)
	 *
	 * @param editForm
	 * @param editList
	 * @param editDtList
	 * @param selectedList
	 * @return TRUE / FALSE
	 */
	public boolean validateManipulationReference(JuchuJuchuAddOnly_EditForm editForm,
			JuchuJuchuAddOnlyList editList, JuchuJuchuAddOnlyDtList editDtList,
			JuchuJuchuAddOnlyList searchedList) {

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		// kit:buttonタグでボタン表示制御済み、ここではチェックなし

		// ==================================================
		// 必須チェックを行う。
		// ==================================================
		if (!inputCheckForManipulation(editList, editDtList, searchedList)) {
			return false;
		}

		return true;
	}

	/**
	 * 操作系画面（抹消）呼出時チェック(on SearchForm)
	 * 同時に確認も行う
	 *
	 * @param editForm
	 * @param editList
	 * @param editDtList
	 * @param searchedList
	 * @return TRUE / FALSE
	 */
	public boolean validateManipulationDelete(JuchuJuchuAddOnly_EditForm editForm,
			JuchuJuchuAddOnlyList editList, JuchuJuchuAddOnlyDtList editDtList, JuchuJuchuAddOnlyList searchedList) {

		// ==================================================
		// 必須チェックを行う。
		// ==================================================
		if (!inputCheckForManipulation(editList, editDtList, searchedList)) {
			return false;
		}

		// ==================================================
		// 関連チェックを行う。
		// ==================================================
		if (!validateConfirmAsDelete(editList, editDtList)) {
			return false;
		}

		return true;
	}

	/**
	 * 操作系画面（復活）呼出時チェック(on SearchForm)
	 * 同時に確認も行う
	 *
	 * @param editForm
	 * @param editList
	 * @param editDtList
	 * @param searchedList
	 * @return TRUE / FALSE
	 */
	public boolean validateManipulationRebirth(JuchuJuchuAddOnly_EditForm editForm,
			JuchuJuchuAddOnlyList editList, JuchuJuchuAddOnlyDtList editDtList, JuchuJuchuAddOnlyList searchedList) {

		// ==================================================
		// 必須チェックを行う。
		// ==================================================
		if (!inputCheckForManipulation(editList, editDtList, searchedList)) {
			return false;
		}

		// ==================================================
		// 関連チェックを行う。
		// ==================================================
		if (!validateConfirmAsRebirth(editList, editDtList)) {
			return false;
		}

		return true;
	}

	/**
	 * 新規時チェック
	 *
	 * @param editForm
	 * @param editList
	 * @param editDtList
	 * @param tatesenRec
	 * @return true(エラー無) or false(エラー有)
	 */
	public boolean validateConfirmAsInsert(JuchuJuchuAddOnly_EditForm editForm,
			JuchuJuchuAddOnlyList editList, JuchuJuchuAddOnlyDtList editDtList,
			MastrTatesenOrositenRecord tatesenRec) throws KitException {

		// ==================================================
		// フラグを初期化
		// ==================================================
		editList.initFlgs(); // ヘッダー部
		editDtList.initFlgs(); // ディテール部

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		// kit:buttonタグでボタン表示制御済み、ここではチェックなし

		// ==================================================
		// 入力有無チェックを行う（ヘッダー部）
		// ==================================================
		if (!inputCheckNoEntryForInsert(editList)) {
			return false;
		}

		// ==================================================
		// 項目の単体チェックを行う（ヘッダー部）
		// ==================================================
		if (!inputCheckForInsert(editList)) {
			return false;
		}

		// ==================================================
		// 関連チェックを行う（ヘッダー部）
		// ==================================================
		if (!inputCheckXForInsert(editList, tatesenRec)) {
			return false;
		}

		// ==================================================
		// 入力有無チェックを行う（ディテール部）
		// ==================================================
		if (!inputCheckNoEntryForInsert(editDtList)) {
			return false;
		}

		// ==================================================
		// 項目の単体チェックを行う（ディテール部）
		// ==================================================
		setDtListInfo(editList, editDtList); // 受注行NO・特注指示情報
		if (!inputCheckForInsert(editDtList)) {
			return false;
		}

		// ==================================================
		// 関連チェックを行う（ディテール部）
		// ==================================================
		if (!inputCheckXForInsert(editDtList)) {
			return false;
		}

		// ==================================================
		// その他の値設定
		// ==================================================

		// 受注NOの自動採番（オンライン受信時間：手入力）
		if (!setNextJyucyuNo(editList, tatesenRec.getJuchuMngKey(), "0")) {
			return false;
		}

		// その他
		if (!setOthers(editForm, editList, editDtList, tatesenRec)) {
			return false;
		}

		// ==================================================
		// 関連チェックを行う（ヘッダー部Ｘディテール部）
		// ==================================================
		inputCheckXForConfirm(editList, editDtList, tatesenRec);

		// 計算結果の桁数チェック
		if (!inputCheckXForCalc(editList, editDtList)) {
			return false;
		}

		return true;
	}

	/**
	 * 修正時チェック
	 *
	 * @param editForm
	 * @param initList
	 * @param initDtList
	 * @param editList
	 * @param editDtList
	 * @param tatesenRec
	 * @return true(エラー無) or false(エラー有)
	 */
	public boolean validateConfirmAsUpdate(JuchuJuchuAddOnly_EditForm editForm,
			JuchuJuchuAddOnlyList initList, JuchuJuchuAddOnlyDtList initDtList,
			JuchuJuchuAddOnlyList editList, JuchuJuchuAddOnlyDtList editDtList,
			MastrTatesenOrositenRecord tatesenRec) throws KitException {

		// ==================================================
		// フラグを初期化
		// ==================================================
		editList.initFlgs(); // ヘッダー部
		editDtList.initFlgs(); // ディテール部

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		// kit:buttonタグでボタン表示制御済み、ここではチェックなし

		// ==================================================
		// 入力有無チェック
		// ==================================================
		boolean noModHd = false; // ヘッダー部
		boolean noModDt = false; // ディテール部

		// ヘッダー部
		if (!inputCheckNoEntryForEdit(initList, editList)) {
			noModHd = true;
		}
		// ディテール部
		if (!inputCheckNoEntryForEdit(initDtList, editDtList)) {
			noModDt = true;
		}
		// エラーメッセージを出力
		if (noModHd && noModDt) {
			setErrorMessageId("errors.NothingUpdateTarget");
			return false;
		}
		// ディテール部のみ変更時、ヘッダー部にも変更フラグを立てる
		if (!noModDt) {
			for (int i = 0; i < editList.size(); i++) {
				JuchuJuchuAddOnlyRecord eRec = (JuchuJuchuAddOnlyRecord) editList.get(i);
				eRec.setModified();
			}
		}

		// ==================================================
		// 項目の単体チェックを行う（ヘッダー部）
		// ==================================================
		if (!inputCheckForEdit(initList, editList)) {
			return false;
		}

		// ==================================================
		// 関連チェックを行う（ヘッダー部）
		// ==================================================
//		if (!inputCheckXForEdit(editList)) {
//			return false;
//		}

		// ==================================================
		// 項目の単体チェックを行う（ディテール部）
		// ==================================================
		setDtListInfo(editList, editDtList); // 受注行NO・特注指示情報
		if (!inputCheckForEdit(initDtList, editDtList)) {
			return false;
		}

		// ==================================================
		// 関連チェックを行う（ディテール部）
		// ==================================================
		if (!inputCheckXForEdit(editDtList)) {
			return false;
		}

		// ==================================================
		// その他の値設定
		// ==================================================

		// その他
		if (!setOthers(editForm, editList, editDtList, tatesenRec)) {
			return false;
		}

		// 合計（計算結果）の変更部分に色付け
		checkForCalc(initList, editList);

		// ==================================================
		// 関連チェックを行う（ヘッダー部Ｘディテール部）
		// ==================================================
		inputCheckXForConfirm(editList, editDtList, tatesenRec);

		// 計算結果の桁数チェック
		if (!inputCheckXForCalc(editList, editDtList)) {
			return false;
		}

		return true;
	}


	/**
	 * 参照追加時チェック
	 *
	 * @param editForm
	 * @param initList
	 * @param initDtList
	 * @param editList
	 * @param editDtList
	 * @param tatesenRec
	 * @return true(エラー無) or false(エラー有)
	 */
	public boolean validateConfirmAsReference(JuchuJuchuAddOnly_EditForm editForm,
			JuchuJuchuAddOnlyList initList, JuchuJuchuAddOnlyDtList initDtList,
			JuchuJuchuAddOnlyList editList, JuchuJuchuAddOnlyDtList editDtList,
			MastrTatesenOrositenRecord tatesenRec) throws KitException {

		// ==================================================
		// フラグを初期化
		// ==================================================
		editList.initFlgs(); // ヘッダー部
		editDtList.initFlgs(); // ディテール部

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		// kit:buttonタグでボタン表示制御済み、ここではチェックなし

		// ==================================================
		// 入力有無チェックを行う（ヘッダー部）
		// ==================================================
		if (!inputCheckNoEntryForInsert(editList)) {
			return false;
		}

		// ==================================================
		// 項目の単体チェックを行う（ヘッダー部）
		// ==================================================
		if (!inputCheckForReference(initList, editList)) {
			return false;
		}

		// ==================================================
		// 関連チェックを行う（ヘッダー部）
		// ==================================================
		if (!inputCheckXForInsert(editList, tatesenRec)) {
			return false;
		}

		// ==================================================
		// 入力有無チェックを行う（ディテール部）
		// ==================================================
		if (!inputCheckNoEntryForInsert(editDtList)) {
			return false;
		}

		// ==================================================
		// 項目の単体チェックを行う（ディテール部）
		// ==================================================
		setDtListInfo(editList, editDtList); // 受注行NO・特注指示情報
		if (!inputCheckForReference(initDtList, editDtList)) {
			return false;
		}

		// ==================================================
		// 関連チェックを行う（ディテール部）
		// ==================================================
		if (!inputCheckXForInsert(editDtList)) { // ディテール部
			return false;
		}

		// ==================================================
		// その他の値設定
		// ==================================================

		// 受注NOの自動採番（枝番インクリメント）
		JuchuJuchuAddOnlyRecord iRec = (JuchuJuchuAddOnlyRecord) initList.getFirstRecord();
		if (!setIncrementedJyucyuNo(editList, iRec.getJyucyuNo())) {
			return false;
		}

		// その他
		if (!setOthers(editForm, editList, editDtList, tatesenRec)) {
			return false;
		}

		// ==================================================
		// 関連チェックを行う（ヘッダー部Ｘディテール部）
		// ==================================================
		inputCheckXForConfirm(editList, editDtList, tatesenRec);

		// 計算結果の桁数チェック
		if (!inputCheckXForCalc(editList, editDtList)) {
			return false;
		}

		return true;
	}

	/**
	 * 抹消時チェック
	 *
	 * @param editList
	 * @param editDtList
	 * @return true(エラー無) or false(エラー有)
	 */
	public boolean validateConfirmAsDelete(JuchuJuchuAddOnlyList editList, JuchuJuchuAddOnlyDtList editDtList) {

		// ==================================================
		// フラグを初期化
		// ==================================================
		editList.initFlgs(); // ヘッダー部
		editDtList.initFlgs(); // ディテール部

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		// kit:buttonタグでボタン表示制御済み、ここではチェックなし

		// ==================================================
		// 関連チェックを行う
		// ==================================================
		if (!inputCheckXForDelete(editList)) { // ヘッダー部
			return false;
		}
		if (!inputCheckXForDelete(editDtList)) { // ディテール部
			return false;
		}

		// ==================================================
		// 論理削除のフラグをするので特にチェック無し
		// ==================================================

		// 更新対象フラグを立てる
		// ヘッダー部
		JuchuJuchuAddOnlyRecord rec = null;
		for (PbsRecord tmp : editList) {
			rec = (JuchuJuchuAddOnlyRecord) tmp;
			rec.setModified();
		}
		// ディテール部
		JuchuJuchuAddOnlyDtRecord recDt = null;
		for (PbsRecord tmp : editDtList) {
			recDt = (JuchuJuchuAddOnlyDtRecord) tmp;
			recDt.setModified();
		}

		return true;
	}

	/**
	 * 復活時チェック
	 *
	 * @param editList
	 * @param editDtList
	 * @return true(エラー無) or false(エラー有)
	 */
	public boolean validateConfirmAsRebirth(JuchuJuchuAddOnlyList editList, JuchuJuchuAddOnlyDtList editDtList) {

		// ==================================================
		// フラグを初期化
		// ==================================================
		editList.initFlgs(); // ヘッダー部
		editDtList.initFlgs(); // ディテール部

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		// kit:buttonタグでボタン表示制御済み、ここではチェックなし

		// ==================================================
		// 関連チェックを行う
		// ==================================================
		if (!inputCheckXForRebirth(editList)) { // ヘッダー部
			return false;
		}
		if (!inputCheckXForRebirth(editDtList)) { // ディテール部
			return false;
		}
		inputCheckXForRebirth(editList, editDtList); // ヘッダー部Ｘディテール部

		// ==================================================
		// 論理削除のフラグを元に戻すだけなので特にチェック無し
		// ==================================================

		// 更新対象フラグを立てる
		// ヘッダー部
		JuchuJuchuAddOnlyRecord rec = null;
		for (PbsRecord tmp : editList) {
			rec = (JuchuJuchuAddOnlyRecord) tmp;
			rec.setModified();
		}
		// ディテール部
		JuchuJuchuAddOnlyDtRecord recDt = null;
		for (PbsRecord tmp : editDtList) {
			recDt = (JuchuJuchuAddOnlyDtRecord) tmp;
			recDt.setModified();
		}

		return true;
	}


	// === private =============================

	/**
	 * 操作系画面呼出し時のチェックを行う
	 *
	 * @param editList … 修正対象のリスト
	 * @param editDtList … 修正対象のリスト
	 * @param selectedList … 呼び出した結果のリスト
	 * @return TRUE / FALSE
	 */
	private boolean inputCheckForManipulation(JuchuJuchuAddOnlyList editList,
			JuchuJuchuAddOnlyDtList editDtList, JuchuJuchuAddOnlyList selectedList) {

		if (PbsUtil.isListEmpty(editList) || PbsUtil.isListEmpty(editDtList) || PbsUtil.isListEmpty(selectedList)) {
			// 予期せぬエラーが発生しました。呼出しからやり直してください。
			setErrorMessageId("errors.TryAgainFromTheSearching");
			return false;
		}

		return true;
	}

	/**
	 * 追加時入力有無チェックを実施する（ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:なんらかの入力がある / FALSE:なにも入力されていない
	 */
	private boolean inputCheckNoEntryForInsert(JuchuJuchuAddOnlyList editList) {

		boolean res = false;

		JuchuJuchuAddOnlyRecord rec;
		for (PbsRecord tmp : editList) {
			rec = (JuchuJuchuAddOnlyRecord) tmp;

			// 荷受時間区分により不要な荷受時間を除去
			arrangeNiukeTimeKbn(rec);

			// 入力行を発見したらフラグにTRUEを立てる
			if (!rec.isEmpty()) {
				res = true;
				rec.setModified();
			}
		}

		if (res) {
			return true;
		} else {
			// エラーメッセージを出力
			setErrorMessageId("errors.noInputAll",PbsUtil.getMessageResourceString("com.text.kakkoJuchuJoho"));
			return false;
		}
	}

	/**
	 * 追加時入力有無チェックを実施する（ディテール部）
	 *
	 * @param editDtList
	 * @return TRUE:なんらかの入力がある / FALSE:なにも入力されていない
	 */
	private boolean inputCheckNoEntryForInsert(JuchuJuchuAddOnlyDtList editDtList) {

		boolean res = false;

		JuchuJuchuAddOnlyDtRecord rec;
		for (PbsRecord tmp : editDtList) {
			rec = (JuchuJuchuAddOnlyDtRecord) tmp;

			// 商品CDが空の場合（削除）
			if (PbsUtil.isEmpty(rec.getShohinCd())) {
				rec.reset();
			} else {
				// 出荷数量_箱数が未入力の場合は0をセットする
				if (PbsUtil.isEmpty(rec.getSyukaSuCase())) {
					rec.setSyukaSuCase(SU_ZERO);
				}

				// 出荷数量_セット数が未入力の場合は0をセットする
				if (PbsUtil.isEmpty(rec.getSyukaSuBara())) {
					rec.setSyukaSuBara(SU_ZERO);
				}
			}

			// 入力行を発見したらフラグにTRUEを立てる
			if (!rec.isEmpty()) {
				res = true;
				rec.setModified();
			}
		}

		if (res) {
			return true;
		} else {
			// 先頭入力欄にエラーフラグを設定
			rec = (JuchuJuchuAddOnlyDtRecord) editDtList.getFirstRecord();
			rec.setShohinCdClass(STYLE_CLASS_ERROR);
			// エラーメッセージを出力
			setErrorMessageId("errors.noInputAll.at",PbsUtil.getMessageResourceString("com.text.kakkoJuchuAddOnly"));
			return false;
		}
	}

	/**
	 * 変更時、変更入力有無チェック（ヘッダー部）
	 *
	 * @param initList
	 * @param editList
	 * @return TRUE:どこか変更されました。 / FALSE:何も変更されていません。
	 */
	private boolean inputCheckNoEntryForEdit(JuchuJuchuAddOnlyList initList, JuchuJuchuAddOnlyList editList) {

		boolean res = false;

		for (int i = 0; i < editList.size(); i++) {
			JuchuJuchuAddOnlyRecord iRec = (JuchuJuchuAddOnlyRecord) initList.get(i);
			JuchuJuchuAddOnlyRecord eRec = (JuchuJuchuAddOnlyRecord) editList.get(i);

			// 荷受時間区分により不要な荷受時間を除去
			arrangeNiukeTimeKbn(eRec);

			// 変更行を発見したらTRUEを返す。
			if (!eRec.equals(iRec)) {
				res = true;
				eRec.setModified();
			}
		}

		if (res) {
			return true;
		} else {
// ヘッダー部とディテール部のどちらかが変更されていればOKとするため、ここではエラーメッセージを表示しない
//			// エラーメッセージを出力
//			setErrorMessageId("errors.NothingUpdateTarget.at",PbsUtil.getMessageResourceString("com.text.kakkoJuchuJoho"));
			return false;
		}
	}

	/**
	 * 変更時、変更入力有無チェック（ディテール部）
	 *
	 * @param initDtList
	 * @param editDtList
	 * @return TRUE:どこか変更されました。 / FALSE:何も変更されていません。
	 */
	private boolean inputCheckNoEntryForEdit(JuchuJuchuAddOnlyDtList initDtList, JuchuJuchuAddOnlyDtList editDtList) {

		boolean res = false;

		for (int i = 0; i < editDtList.size(); i++) {
			JuchuJuchuAddOnlyDtRecord iRec = (JuchuJuchuAddOnlyDtRecord) initDtList.get(i);
			JuchuJuchuAddOnlyDtRecord eRec = (JuchuJuchuAddOnlyDtRecord) editDtList.get(i);

			// View用
			this.setViews(initDtList, editDtList);

			// 商品CDが空の場合（削除）
			if (PbsUtil.isEmpty(eRec.getShohinCd())) {
				eRec.reset();
			} else {
				// 出荷数量_箱数が未入力の場合は0をセットする
				if (PbsUtil.isEmpty(eRec.getSyukaSuCase())) {
					eRec.setSyukaSuCase(SU_ZERO);
				}

				// 出荷数量_セット数が未入力の場合は0をセットする
				if (PbsUtil.isEmpty(eRec.getSyukaSuBara())) {
					eRec.setSyukaSuBara(SU_ZERO);
				}
			}

			// 変更行を発見したらTRUEを返す。
			if (!eRec.equals(iRec)) {
				res = true;
				eRec.setModified();
			}
		}

		if (res) {
			return true;
		} else {
// ヘッダー部とディテール部のどちらかが変更されていればOKとするため、ここではエラーメッセージを表示しない
//			// エラーメッセージを出力
//			setErrorMessageId("errors.NothingUpdateTarget.at",PbsUtil.getMessageResourceString("com.text.kakkoJuchuAddOnly"));
			return false;
		}
	}

	/**
	 * 追加時：項目の単体チェックをする（ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckForInsert(JuchuJuchuAddOnlyList editList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			JuchuJuchuAddOnlyRecord rec = (JuchuJuchuAddOnlyRecord) editList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// 縦線CD (FK)
			if (!checkOrsTatesnCd(rec, kaisyaCd, false)) {
				rec.setOrsTatesnCdClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 先方発注NO
			if (!checkSenpoHacyuNo(rec.getSenpoHacyuNo())) {
				rec.setSenpoHacyuNoClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 出荷日(売上伝票発行予定日)
			if (!checkSyukaDt(rec.getSyukaDt())) {
				rec.setSyukaDtClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 着荷予定日
			if (!checkChacuniYoteiDt(rec.getChacuniYoteiDt())) {
				rec.setChacuniYoteiDtClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 酒販店（統一）CD
			if (PbsUtil.isEmpty(rec.getSyuhantenCd())) {
				rec.setSyuhantenNm(CHAR_BLANK);
			} else if (!checkSyuhantenCd(rec, false)) {
				rec.setSyuhantenCdClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 運賃区分
			// セレクトボックスのため単体チェックなし

			// 運送店CD
			if (PbsUtil.isEmpty(rec.getUnsotenCd())) {
				rec.setUnsotenNm(CHAR_BLANK);
			} else if (!checkUnsotenCd(rec, kaisyaCd, false)) {
				rec.setUnsotenCdClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 小ロット出荷承認申請NO
			if (!checkSyukaSyoninNo(rec.getSyukaSyoninNo())) {
				rec.setSyukaSyoninNoClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 蔵CD (FK)
			// セレクトボックスのため単体チェックなし

			// ミナシ日付
			if (!checkMinasiDt(rec.getMinasiDt())) {
				rec.setMinasiDtClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 荷受時間区分
			// セレクトボックスのため単体チェックなし

			// 荷受時間_開始
			if (!checkNiukeBiginTime(rec.getNiukeBiginTime())) {
				rec.setNiukeBiginTimeClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 荷受時間_終了
			if (!checkNiukeEndTime(rec.getNiukeEndTime())) {
				rec.setNiukeEndTimeClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 摘要区分 (01)
			if (PbsUtil.isEmpty(rec.getTekiyoKbn1())) {
				rec.setTekiyoNm1(CHAR_BLANK);
			} else if (!checkTekiyoKbn1(rec, false)) {
				rec.setTekiyoKbn1Class(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 摘要内容 (01)
			if (TEKIYO_KBN_TEGAKI.equals(rec.getTekiyoKbn1())
					&& !checkTekiyoNm1(rec.getTekiyoNm1())) {
				rec.setTekiyoNm1Class(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 摘要区分 (02)
			if (PbsUtil.isEmpty(rec.getTekiyoKbn2())) {
				rec.setTekiyoNm2(CHAR_BLANK);
			} else if (!checkTekiyoKbn2(rec, false)) {
				rec.setTekiyoKbn2Class(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 摘要内容 (02)
			if (TEKIYO_KBN_TEGAKI.equals(rec.getTekiyoKbn2())
					&& !checkTekiyoNm2(rec.getTekiyoNm2())) {
				rec.setTekiyoNm2Class(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 摘要区分 (03)
			if (PbsUtil.isEmpty(rec.getTekiyoKbn3())) {
				rec.setTekiyoNm3(CHAR_BLANK);
			} else if (!checkTekiyoKbn3(rec, false)) {
				rec.setTekiyoKbn3Class(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 摘要内容 (03)
			if (TEKIYO_KBN_TEGAKI.equals(rec.getTekiyoKbn3())
					&& !checkTekiyoNm3(rec.getTekiyoNm3())) {
				rec.setTekiyoNm3Class(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 摘要区分 (04)
			if (PbsUtil.isEmpty(rec.getTekiyoKbn4())) {
				rec.setTekiyoNm4(CHAR_BLANK);
			} else if (!checkTekiyoKbn4(rec, false)) {
				rec.setTekiyoKbn4Class(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 摘要内容 (04)
			if (TEKIYO_KBN_TEGAKI.equals(rec.getTekiyoKbn4())
					&& !checkTekiyoNm4(rec.getTekiyoNm4())) {
				rec.setTekiyoNm4Class(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 摘要区分 (05)
			if (PbsUtil.isEmpty(rec.getTekiyoKbn5())) {
				rec.setTekiyoNm5(CHAR_BLANK);
			} else if (!checkTekiyoKbn5(rec, false)) {
				rec.setTekiyoKbn5Class(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 摘要内容 (05)
			if (TEKIYO_KBN_TEGAKI.equals(rec.getTekiyoKbn5())
					&& !checkTekiyoNm5(rec.getTekiyoNm5())) {
				rec.setTekiyoNm5Class(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				rec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}

	/**
	 * 追加時：項目の単体チェックをする（ディテール部）
	 *
	 * @param editDtList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckForInsert(JuchuJuchuAddOnlyDtList editDtList) throws KitException {

		boolean res = true;
		boolean lineRes = true;
		int line = 0;

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD
		String ktksyCd = cus.getKtksyCd(); // 寄託者CD

		for (int i = 0; i < editDtList.size(); i++) {

			lineRes = true;
			line = i + 1;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			JuchuJuchuAddOnlyDtRecord rec = (JuchuJuchuAddOnlyDtRecord) editDtList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// 商品CD
			if (!checkShohinCd(rec, kaisyaCd, ktksyCd, true, false, line)) {
				rec.setShohinCdClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 出荷数量_箱数
			if (!checkSyukaSuCase(rec.getSyukaSuCase(), line)) {
				rec.setSyukaSuCaseClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 出荷数量_セット数
			if (!checkSyukaSuBara(rec.getSyukaSuBara(), line)) {
				rec.setSyukaSuBaraClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 販売単価
			if (!checkHanbaiTanka(rec.getHanbaiTanka(), line)) {
				rec.setHanbaiTankaClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				rec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}

	/**
	 * 変更時：項目の単体チェックをする（ヘッダー部）
	 *
	 * @param initList
	 * @param editList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckForEdit(JuchuJuchuAddOnlyList initList, JuchuJuchuAddOnlyList editList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			JuchuJuchuAddOnlyRecord rec = (JuchuJuchuAddOnlyRecord) editList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// View用名称セット
			JuchuJuchuAddOnlyRecord iRec = (JuchuJuchuAddOnlyRecord) initList.get(i);
			this.setCdToNmView(iRec);

			// 縦線CD (FK)
//			if (!checkOrsTatesnCd(rec, kaisyaCd, false)) {
//				rec.setOrsTatesnCdClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 先方発注NO
//			if (!checkSenpoHacyuNo(rec.getSenpoHacyuNo())) {
//				rec.setSenpoHacyuNoClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 出荷日(売上伝票発行予定日)
//			if (!checkSyukaDt(rec.getSyukaDt())) {
//				rec.setSyukaDtClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 着荷予定日
//			if (!checkChacuniYoteiDt(rec.getChacuniYoteiDt())) {
//				rec.setChacuniYoteiDtClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 酒販店（統一）CD
//			if (PbsUtil.isEmpty(rec.getSyuhantenCd())) {
//				rec.setSyuhantenNm(CHAR_BLANK);
//			} else if (!checkSyuhantenCd(rec, false)) {
//				rec.setSyuhantenCdClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 運賃区分
			// セレクトボックスのため単体チェックなし

			// 運送店CD
			if (PbsUtil.isEmpty(rec.getUnsotenCd())) {
				rec.setUnsotenNm(CHAR_BLANK);
			} else if (!checkUnsotenCd(rec, kaisyaCd, false)) {
				rec.setUnsotenCdClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 小ロット出荷承認申請NO
//			if (!checkSyukaSyoninNo(rec.getSyukaSyoninNo())) {
//				rec.setSyukaSyoninNoClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 蔵CD (FK)
			// セレクトボックスのため単体チェックなし

			// ミナシ日付
//			if (!checkMinasiDt(rec.getMinasiDt())) {
//				rec.setMinasiDtClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 荷受時間区分
			// セレクトボックスのため単体チェックなし

			// 荷受時間_開始
//			if (!checkNiukeBiginTime(rec.getNiukeBiginTime())) {
//				rec.setNiukeBiginTimeClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 荷受時間_終了
//			if (!checkNiukeEndTime(rec.getNiukeEndTime())) {
//				rec.setNiukeEndTimeClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要区分 (01)
//			if (PbsUtil.isEmpty(rec.getTekiyoKbn1())) {
//				rec.setTekiyoNm1(CHAR_BLANK);
//			} else if (!checkTekiyoKbn1(rec, false)) {
//				rec.setTekiyoKbn1Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要内容 (01)
//			if (TEKIYO_KBN_TEGAKI.equals(rec.getTekiyoKbn1())
//					&& !checkTekiyoNm1(rec.getTekiyoNm1())) {
//				rec.setTekiyoNm1Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要区分 (02)
//			if (PbsUtil.isEmpty(rec.getTekiyoKbn2())) {
//				rec.setTekiyoNm2(CHAR_BLANK);
//			} else if (!checkTekiyoKbn2(rec, false)) {
//				rec.setTekiyoKbn2Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要内容 (02)
//			if (TEKIYO_KBN_TEGAKI.equals(rec.getTekiyoKbn2())
//					&& !checkTekiyoNm2(rec.getTekiyoNm2())) {
//				rec.setTekiyoNm2Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要区分 (03)
//			if (PbsUtil.isEmpty(rec.getTekiyoKbn3())) {
//				rec.setTekiyoNm3(CHAR_BLANK);
//			} else if (!checkTekiyoKbn3(rec, false)) {
//				rec.setTekiyoKbn3Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要内容 (03)
//			if (TEKIYO_KBN_TEGAKI.equals(rec.getTekiyoKbn3())
//					&& !checkTekiyoNm3(rec.getTekiyoNm3())) {
//				rec.setTekiyoNm3Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要区分 (04)
//			if (PbsUtil.isEmpty(rec.getTekiyoKbn4())) {
//				rec.setTekiyoNm4(CHAR_BLANK);
//			} else if (!checkTekiyoKbn4(rec, false)) {
//				rec.setTekiyoKbn4Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要内容 (04)
//			if (TEKIYO_KBN_TEGAKI.equals(rec.getTekiyoKbn4())
//					&& !checkTekiyoNm4(rec.getTekiyoNm4())) {
//				rec.setTekiyoNm4Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要区分 (05)
//			if (PbsUtil.isEmpty(rec.getTekiyoKbn5())) {
//				rec.setTekiyoNm5(CHAR_BLANK);
//			} else if (!checkTekiyoKbn5(rec, false)) {
//				rec.setTekiyoKbn5Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要内容 (05)
//			if (TEKIYO_KBN_TEGAKI.equals(rec.getTekiyoKbn5())
//					&& !checkTekiyoNm5(rec.getTekiyoNm5())) {
//				rec.setTekiyoNm5Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				rec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}

	/**
	 * 変更時：項目の単体チェックをする（ディテール部）
	 *
	 * @param initDtList
	 * @param editDtList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckForEdit(JuchuJuchuAddOnlyDtList initDtList, JuchuJuchuAddOnlyDtList editDtList) throws KitException {

		boolean res = true;
		boolean lineRes = true;
		int line = 0;

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD
		String ktksyCd = cus.getKtksyCd(); // 寄託者CD

		for (int i = 0; i < editDtList.size(); i++) {

			lineRes = true;
			line = i + 1;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			JuchuJuchuAddOnlyDtRecord rec = (JuchuJuchuAddOnlyDtRecord) editDtList.get(i);

			// 未入力行はスキップ
			if (rec.isEmpty()) {
				continue;
			}

			// View用
			this.setViews(initDtList, editDtList);

			// 商品CD
			if (!checkShohinCd(rec, kaisyaCd, ktksyCd, true, false, line)) {
				rec.setShohinCdClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 出荷数量_箱数
			if (!checkSyukaSuCase(rec.getSyukaSuCase(), line)) {
				rec.setSyukaSuCaseClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 出荷数量_セット数
			if (!checkSyukaSuBara(rec.getSyukaSuBara(), line)) {
				rec.setSyukaSuBaraClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 販売単価
			if (!checkHanbaiTanka(rec.getHanbaiTanka(), line)) {
				rec.setHanbaiTankaClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				rec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}


	/**
	 * 参照追加時：項目の単体チェックをする（ヘッダー部）
	 *
	 * @param initList
	 * @param editList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckForReference(JuchuJuchuAddOnlyList initList, JuchuJuchuAddOnlyList editList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			JuchuJuchuAddOnlyRecord rec = (JuchuJuchuAddOnlyRecord) editList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// View用名称セット
			JuchuJuchuAddOnlyRecord iRec = (JuchuJuchuAddOnlyRecord) initList.get(i);
			this.setCdToNmView(iRec);

			// 縦線CD (FK)
//			if (!checkOrsTatesnCd(rec, kaisyaCd, false)) {
//				rec.setOrsTatesnCdClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 先方発注NO
//			if (!checkSenpoHacyuNo(rec.getSenpoHacyuNo())) {
//				rec.setSenpoHacyuNoClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 出荷日(売上伝票発行予定日)
//			if (!checkSyukaDt(rec.getSyukaDt())) {
//				rec.setSyukaDtClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 着荷予定日
//			if (!checkChacuniYoteiDt(rec.getChacuniYoteiDt())) {
//				rec.setChacuniYoteiDtClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 酒販店（統一）CD
//			if (PbsUtil.isEmpty(rec.getSyuhantenCd())) {
//				rec.setSyuhantenNm(CHAR_BLANK);
//			} else if (!checkSyuhantenCd(rec, false)) {
//				rec.setSyuhantenCdClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 運賃区分
			// セレクトボックスのため単体チェックなし

			// 運送店CD
			if (PbsUtil.isEmpty(rec.getUnsotenCd())) {
				rec.setUnsotenNm(CHAR_BLANK);
			} else if (!checkUnsotenCd(rec, kaisyaCd, false)) {
				rec.setUnsotenCdClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 小ロット出荷承認申請NO
//			if (!checkSyukaSyoninNo(rec.getSyukaSyoninNo())) {
//				rec.setSyukaSyoninNoClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 蔵CD (FK)
			// セレクトボックスのため単体チェックなし

			// ミナシ日付
//			if (!checkMinasiDt(rec.getMinasiDt())) {
//				rec.setMinasiDtClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 荷受時間区分
			// セレクトボックスのため単体チェックなし

			// 荷受時間_開始
//			if (!checkNiukeBiginTime(rec.getNiukeBiginTime())) {
//				rec.setNiukeBiginTimeClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 荷受時間_終了
//			if (!checkNiukeEndTime(rec.getNiukeEndTime())) {
//				rec.setNiukeEndTimeClass(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要区分 (01)
//			if (PbsUtil.isEmpty(rec.getTekiyoKbn1())) {
//				rec.setTekiyoNm1(CHAR_BLANK);
//			} else if (!checkTekiyoKbn1(rec, false)) {
//				rec.setTekiyoKbn1Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要内容 (01)
//			if (TEKIYO_KBN_TEGAKI.equals(rec.getTekiyoKbn1())
//					&& !checkTekiyoNm1(rec.getTekiyoNm1())) {
//				rec.setTekiyoNm1Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要区分 (02)
//			if (PbsUtil.isEmpty(rec.getTekiyoKbn2())) {
//				rec.setTekiyoNm2(CHAR_BLANK);
//			} else if (!checkTekiyoKbn2(rec, false)) {
//				rec.setTekiyoKbn2Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要内容 (02)
//			if (TEKIYO_KBN_TEGAKI.equals(rec.getTekiyoKbn2())
//					&& !checkTekiyoNm2(rec.getTekiyoNm2())) {
//				rec.setTekiyoNm2Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要区分 (03)
//			if (PbsUtil.isEmpty(rec.getTekiyoKbn3())) {
//				rec.setTekiyoNm3(CHAR_BLANK);
//			} else if (!checkTekiyoKbn3(rec, false)) {
//				rec.setTekiyoKbn3Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要内容 (03)
//			if (TEKIYO_KBN_TEGAKI.equals(rec.getTekiyoKbn3())
//					&& !checkTekiyoNm3(rec.getTekiyoNm3())) {
//				rec.setTekiyoNm3Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要区分 (04)
//			if (PbsUtil.isEmpty(rec.getTekiyoKbn4())) {
//				rec.setTekiyoNm4(CHAR_BLANK);
//			} else if (!checkTekiyoKbn4(rec, false)) {
//				rec.setTekiyoKbn4Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要内容 (04)
//			if (TEKIYO_KBN_TEGAKI.equals(rec.getTekiyoKbn4())
//					&& !checkTekiyoNm4(rec.getTekiyoNm4())) {
//				rec.setTekiyoNm4Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要区分 (05)
//			if (PbsUtil.isEmpty(rec.getTekiyoKbn5())) {
//				rec.setTekiyoNm5(CHAR_BLANK);
//			} else if (!checkTekiyoKbn5(rec, false)) {
//				rec.setTekiyoKbn5Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// 摘要内容 (05)
//			if (TEKIYO_KBN_TEGAKI.equals(rec.getTekiyoKbn5())
//					&& !checkTekiyoNm5(rec.getTekiyoNm5())) {
//				rec.setTekiyoNm5Class(STYLE_CLASS_ERROR);
//				lineRes = false;
//			}

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				rec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}

	/**
	 * 参照追加時：項目の単体チェックをする（ディテール部）
	 *
	 * @param initDtList
	 * @param editDtList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckForReference(JuchuJuchuAddOnlyDtList initDtList, JuchuJuchuAddOnlyDtList editDtList) throws KitException {

		boolean res = true;
		boolean lineRes = true;
		int line = 0;

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD
		String ktksyCd = cus.getKtksyCd(); // 寄託者CD

		for (int i = 0; i < editDtList.size(); i++) {

			lineRes = true;
			line = i + 1;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			JuchuJuchuAddOnlyDtRecord rec = (JuchuJuchuAddOnlyDtRecord) editDtList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// View用名称セット
			JuchuJuchuAddOnlyDtRecord iRec = (JuchuJuchuAddOnlyDtRecord) initDtList.get(i);
			this.setCdToNmView(iRec, line);

			// 商品CD
			if (!checkShohinCd(rec, kaisyaCd, ktksyCd, true, false, line)) {
				rec.setShohinCdClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 出荷数量_箱数
			if (!checkSyukaSuCase(rec.getSyukaSuCase(), line)) {
				rec.setSyukaSuCaseClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 出荷数量_セット数
			if (!checkSyukaSuBara(rec.getSyukaSuBara(), line)) {
				rec.setSyukaSuBaraClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 販売単価
			if (!checkHanbaiTanka(rec.getHanbaiTanka(), line)) {
				rec.setHanbaiTankaClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				rec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}

	/**
	 * 追加時：関連チェックをする（ヘッダー部）
	 *
	 * @param editList
	 * @param tatesenRec
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForInsert(JuchuJuchuAddOnlyList editList, MastrTatesenOrositenRecord tatesenRec) {

		boolean res = true;
		boolean lineRes = true;

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			JuchuJuchuAddOnlyRecord rec = (JuchuJuchuAddOnlyRecord) editList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// 出荷日　≦　着日　（着日が入力されている場合のみチェック）
			if (!PbsUtil.isEmpty(rec.getChacuniYoteiDt())) {
				if (!checkXChakubi(rec)) {
					rec.setSyukaDtClass(STYLE_CLASS_ERROR);
					rec.setChacuniYoteiDtClass(STYLE_CLASS_ERROR);
					lineRes = false;
				} else {
					try {
						if (!checkXLeadTime(rec, tatesenRec)) {
							rec.setSyukaDtClass(STYLE_CLASS_ERROR);
							rec.setChacuniYoteiDtClass(STYLE_CLASS_ERROR);
							lineRes = false;
						}
					} catch (ParseException e) {
						rec.setSyukaDtClass(STYLE_CLASS_ERROR);
						rec.setChacuniYoteiDtClass(STYLE_CLASS_ERROR);
						lineRes = false;
					}
				}
			}

			// 荷受区分＝先方指定の場合、荷受時間必須
			if (!checkXNiuke(rec)) {
				rec.setNiukeTimeKbnClass(STYLE_CLASS_ERROR);
				rec.setNiukeBiginTimeClass(STYLE_CLASS_ERROR);
				rec.setNiukeEndTimeClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 荷受時間（始） ＜　荷受時間（終）
			if (!checkXNiukeTime(rec)) {
				rec.setNiukeBiginTimeClass(STYLE_CLASS_ERROR);
				rec.setNiukeEndTimeClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				rec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}

	/**
	 * 追加時：関連チェックをする（ディテール部）
	 *
	 * @param editDtList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForInsert(JuchuJuchuAddOnlyDtList editDtList) {

		boolean res = true;
		boolean lineRes = true;
		int line = 0;

		for (int i = 0; i < editDtList.size(); i++) {

			lineRes = true;
			line = i + 1;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			JuchuJuchuAddOnlyDtRecord rec = (JuchuJuchuAddOnlyDtRecord) editDtList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// 商品CDの重複チェック
			if (!checkXDuplicationShohinCd(editDtList, rec.getShohinCd(), i, line)) {
				rec.setShohinCdClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 出荷数量（箱数）と出荷数量（セット数）が両方0の場合はエラー
			if (!checkXSyukaSu(rec, line)) {
				rec.setSyukaSuCaseClass(STYLE_CLASS_ERROR);
				rec.setSyukaSuBaraClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				rec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}

	/**
	 * 変更時：関連チェックをする（ヘッダー部）
	 *
	 * @param editList
	 * @param tatesenRec
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForEdit(JuchuJuchuAddOnlyList editList, MastrTatesenOrositenRecord tatesenRec) {

		boolean res = true;
		boolean lineRes = true;

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			JuchuJuchuAddOnlyRecord rec = (JuchuJuchuAddOnlyRecord) editList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// 出荷日　≦　着日　（着日が入力されている場合のみチェック）
			if (!PbsUtil.isEmpty(rec.getChacuniYoteiDt())) {
				if (!checkXChakubi(rec)) {
					rec.setSyukaDtClass(STYLE_CLASS_ERROR);
					rec.setChacuniYoteiDtClass(STYLE_CLASS_ERROR);
					lineRes = false;
				} else {
					try {
						if (!checkXLeadTime(rec, tatesenRec)) {
							rec.setSyukaDtClass(STYLE_CLASS_ERROR);
							rec.setChacuniYoteiDtClass(STYLE_CLASS_ERROR);
							lineRes = false;
						}
					} catch (ParseException e) {
						rec.setSyukaDtClass(STYLE_CLASS_ERROR);
						rec.setChacuniYoteiDtClass(STYLE_CLASS_ERROR);
						lineRes = false;
					}
				}
			}

			// 荷受区分＝先方指定の場合、荷受時間必須
			if (!checkXNiuke(rec)) {
				rec.setNiukeTimeKbnClass(STYLE_CLASS_ERROR);
				rec.setNiukeBiginTimeClass(STYLE_CLASS_ERROR);
				rec.setNiukeEndTimeClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 荷受時間（始） ＜　荷受時間（終）
			if (!checkXNiukeTime(rec)) {
				rec.setNiukeBiginTimeClass(STYLE_CLASS_ERROR);
				rec.setNiukeEndTimeClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				rec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}

	/**
	 * 変更時：関連チェックをする（ディテール部）
	 *
	 * @param editDtList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForEdit(JuchuJuchuAddOnlyDtList editDtList) {

		boolean res = true;
		boolean lineRes = true;
		int line = 0;

		for (int i = 0; i < editDtList.size(); i++) {

			lineRes = true;
			line = i + 1;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			JuchuJuchuAddOnlyDtRecord rec = (JuchuJuchuAddOnlyDtRecord) editDtList.get(i);

			// 未入力行はスキップ
			if (rec.isEmpty()) {
				continue;
			}

			// 商品CDの重複チェック
			if (!checkXDuplicationShohinCd(editDtList, rec.getShohinCd(), i, line)) {
				rec.setShohinCdClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 出荷数量（箱数）と出荷数量（セット数）が両方0の場合はエラー
			if (!checkXSyukaSu(rec, line)) {
				rec.setSyukaSuCaseClass(STYLE_CLASS_ERROR);
				rec.setSyukaSuBaraClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				rec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}

	/**
	 * 関連チェックをする（ヘッダー部Ｘディテール部）
	 * メッセージ表示のみ行う
	 *
	 * @param editList
	 * @param editDtList
	 * @param tatesenRec
	 */
	private void inputCheckXForConfirm(JuchuJuchuAddOnlyList editList, JuchuJuchuAddOnlyDtList editDtList,
			MastrTatesenOrositenRecord tatesenRec) {

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD
		String ktksyCd = cus.getKtksyCd(); // 寄託者CD

		// ヘッダー部レコード
		JuchuJuchuAddOnlyRecord hdRec = (JuchuJuchuAddOnlyRecord) editList.getFirstRecord();

		// ============================================================
		// 単価が変更された商品が含まれている場合はメッセージ表示
		// ============================================================
		boolean hasHtankaDiff = false;

		hasHtankaDiff = this.hasXDiffHtanka(editDtList, kaisyaCd, ktksyCd, true);

		if (hasHtankaDiff) {
			// メッセージ表示
			setErrorMessageId("warn.htankaConfirm");
			hdRec.setDiffHtankaFlg(STYLE_CLASS_WARN);
		} else {
			hdRec.setDiffHtankaFlg(STYLE_CLASS_INIT);
		}


		// ============================================================
		// 注意商品が含まれている場合はメッセージ表示
		// ============================================================
		boolean hasCautionItem = false;

		hasCautionItem = this.hasXDiffCautionItem(editDtList, hdRec);

		if (hasCautionItem) {
			// メッセージ表示
			setErrorMessageId("warn.cautionConfirm");
			hdRec.setDiffCautionItemFlg(STYLE_CLASS_WARN);
		} else {
			hdRec.setDiffCautionItemFlg(STYLE_CLASS_INIT);
		}


		// ============================================================
		// 要冷商品が含まれていて要冷配送未対応の場合はメッセージ表示
		// ============================================================
		boolean checkYoreiHaisoSyohinDiff = true;

		checkYoreiHaisoSyohinDiff = this.checkXDiffYoreiHaisoSyohin(editDtList, hdRec);

		if (!checkYoreiHaisoSyohinDiff) {
			// メッセージ表示
			setErrorMessageId("warn.yoreiHaisoSyohinDiffConfirm");
			hdRec.setDiffYoreiHaisoSyohinFlg(STYLE_CLASS_WARN);
		} else {
			hdRec.setDiffYoreiHaisoSyohinFlg(STYLE_CLASS_INIT);
		}


		// ============================================================
		// 要冷商品が含まれていて元払（常温）の場合はメッセージ表示
		// ============================================================
		boolean checkYoreiUnchinSyohinDiff = true;

		checkYoreiUnchinSyohinDiff = this.checkXDiffYoreiUnchinSyohin(editDtList, hdRec);

		if (!checkYoreiUnchinSyohinDiff) {
			// メッセージ表示
			setErrorMessageId("warn.yoreiUnchinSyohinDiffConfirm");
			hdRec.setDiffYoreiUnchinSyohinFlg(STYLE_CLASS_WARN);
		} else {
			hdRec.setDiffYoreiUnchinSyohinFlg(STYLE_CLASS_INIT);
		}


		// ============================================================
		// 元払（要冷）で要冷配送未対応の場合はメッセージ表示
		// ============================================================
		boolean checkYoreiHaisoUnchinDiff = true;

		checkYoreiHaisoUnchinDiff = this.checkXDiffYoreiHaisoUnchin(hdRec);

		if (!checkYoreiHaisoUnchinDiff) {
			// メッセージ表示
			setErrorMessageId("warn.yoreiHaisoUnchinDiffConfirm");
			hdRec.setDiffYoreiHaisoUnchinFlg(STYLE_CLASS_WARN);
		} else {
			hdRec.setDiffYoreiHaisoUnchinFlg(STYLE_CLASS_INIT);
		}


		// ============================================================
		// 当日出荷不可で出荷日が当日指定の場合はメッセージ表示
		// ============================================================
//		boolean checkTojituSyukaDiff = true;
//
//		checkTojituSyukaDiff = this.checkXTojituSyuka(hdRec);
//
//		if (!checkTojituSyukaDiff) {
//			// メッセージ表示
//			setErrorMessageId("warn.toujituSyukaDiffConfirm");
//			hdRec.setDiffTojituSyukaFlg(STYLE_CLASS_WARN);
//		} else {
//			hdRec.setDiffTojituSyukaFlg(STYLE_CLASS_INIT);
//		}


		// ============================================================
		// 運送店と運賃の関係が不正な場合はメッセージ表示
		// ============================================================
		boolean checkUnsotenUnchinDiff = true;

		checkUnsotenUnchinDiff = this.checkXUnsotenUnchin(hdRec, tatesenRec);

		if (!checkUnsotenUnchinDiff) {
			hdRec.setDiffUnsotenUnchinFlg(STYLE_CLASS_WARN);
		} else {
			hdRec.setDiffUnsotenUnchinFlg(STYLE_CLASS_INIT);
		}


		// ============================================================
		// 着日が荷受不可曜日の場合はメッセージ表示
		// （着日が入力されている場合のみチェック）
		// ============================================================
//		if (!PbsUtil.isEmpty(hdRec.getChacuniYoteiDt())) {
//			boolean checkNiukeYoubiDiff = true;
//
//			checkNiukeYoubiDiff = this.checkXNiukeYoubi(hdRec);
//
//			if (!checkNiukeYoubiDiff) {
//				hdRec.setDiffNiukeYoubiFlg(STYLE_CLASS_WARN);
//			} else {
//				hdRec.setDiffNiukeYoubiFlg(STYLE_CLASS_INIT);
//			}
//		}


		// ============================================================
		// 受注数が予定在庫数を超える場合はメッセージ表示
		// ============================================================
		boolean checkZaikoSuOver = false;

		int line = 0;

		for (int i = 0; i < editDtList.size(); i++) {

			line = i + 1;

			JuchuJuchuAddOnlyDtRecord rec = (JuchuJuchuAddOnlyDtRecord) editDtList.get(i);

			// 未入力行はスキップ
			if (rec.isEmpty()) {
				continue;
			}

			checkZaikoSuOver = this.checkXZaikoSu(rec, line);

			// 1件でも在庫不足がある場合は警告
			if (!checkZaikoSuOver) {
				hdRec.setOverZaikoFlg(STYLE_CLASS_WARN);
			}
		}


		// ============================================================
		// 出荷金額計が特約店の受注可能金額を超える場合はメッセージ表示
		// ============================================================
		boolean checkKingakuOver = true;

		checkKingakuOver = this.inputCheckXForYosin(hdRec);

		if (!checkKingakuOver) {
			// メッセージ表示
			setErrorMessageId("warn.over.jyucyuKanoGaku");
			hdRec.setOverKingakuFlg(STYLE_CLASS_WARN);
		} else {
			hdRec.setOverKingakuFlg(STYLE_CLASS_INIT);
		}


		// ============================================================
		// 補償金徴収対象があるが樽保証金未登録の場合はメッセージ表示
		// ============================================================
		boolean checkTaruHosyoKinDiff = true;

		checkTaruHosyoKinDiff = this.checkXDiffTaruHosyoKin(editDtList);

		if (!checkTaruHosyoKinDiff) {
			// メッセージ表示
			setErrorMessageId("warn.noInput.taruHosyoKin");
			hdRec.setDiffTaruHosyoKinFlg(STYLE_CLASS_WARN);
		} else {
			hdRec.setDiffTaruHosyoKinFlg(STYLE_CLASS_INIT);
		}


		// ============================================================
		// 引上手数料対象があるが樽取扱手数料未登録の場合はメッセージ表示
		// ============================================================
//		boolean checkTaruTesuryoDiff = true;
//
//		checkTaruTesuryoDiff = this.checkXDiffTaruTesuryo(editDtList);
//
//		if (!checkTaruTesuryoDiff) {
//			// メッセージ表示
//			setErrorMessageId("warn.noInput.taruTesuryo");
//			hdRec.setDiffTaruTesuryoFlg(STYLE_CLASS_WARN);
//		} else {
//			hdRec.setDiffTaruTesuryoFlg(STYLE_CLASS_INIT);
//		}
	}

	/**
	 * 与信チェック
	 *
	 * @param rec ヘッダー部レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForYosin(JuchuJuchuAddOnlyRecord rec) {
		// [HD]出荷金額計 VS 特約店の受注可能金額（与信情報）
		// 与信要注意区分が「0：問題無」以外の場合にチェック
		if (!PbsUtil.isEmpty(rec.getYosinCyuiKbn()) && !PbsUtil.isEqual(rec.getYosinCyuiKbn(), YSN_CAREF_KB_MONDAINASI)) {
			if (ComUtil.decimalCompareTo(rec.getSyukaKingakuTot(), rec.getJyucyuKanouKingakuKei()) == 1) {
				rec.setSyukaKingakuTotClass(STYLE_CLASS_WARN);
				return false;
			}
		}

		return true;
	}

	/**
	 * 計算結果の桁数チェックをする（ヘッダー部Ｘディテール部）
	 *
	 * @param editList
	 * @param editDtList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForCalc(JuchuJuchuAddOnlyList editList, JuchuJuchuAddOnlyDtList editDtList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		// ヘッダー部でオーバーフローの場合、ディテール部を制御するためのフラグ
		boolean setErrDtCase = false; // ケース数
		boolean setErrDtBara = false; // バラ数
		boolean setErrDtTanka = false; // 単価
		boolean setErrDtRebate1 = false; // リベートⅠ類
		boolean setErrDtRebate2 = false; // リベートⅡ類
		boolean setErrDtRebate3 = false; // リベートⅢ類
		boolean setErrDtRebate4 = false; // リベートⅣ類
		boolean setErrDtRebate5 = false; // リベートⅤ類
		boolean setErrDtRebateo = false; // リベート対象外

		String msgKey_itemNm = "";

		// ヘッダー部レコード
		JuchuJuchuAddOnlyRecord hdRec = (JuchuJuchuAddOnlyRecord) editList.getFirstRecord();

		// [HD]出荷数量計_箱数
		msgKey_itemNm = "com.text.caseKei";
		if (!vUtil.validateLen(hdRec.getSyukaSuryoBox(),
				MAX_LEN_TOT_CASE_SU, msgKey_itemNm)) {
			hdRec.setSyukaSuryoBoxClass(STYLE_CLASS_ERROR);
			setErrDtCase = true; // [DT]出荷数量_箱数
			lineRes = false;
		}

		// [HD]出荷数量計_セット数
		msgKey_itemNm = "com.text.sumBara";
		if (!vUtil.validateLen(hdRec.getSyukaSuryoSet(),
				MAX_LEN_TOT_BARA_SU, msgKey_itemNm)) {
			hdRec.setSyukaSuryoSetClass(STYLE_CLASS_ERROR);
			setErrDtBara = true; // [DT]出荷数量_セット数
			lineRes = false;
		}

		// [HD]重量計(KG)
		msgKey_itemNm = "com.text.sumWeightKG";
		if (!vUtil.validateLen(hdRec.getJyuryoTot(),
				MAX_LEN_TOT_JYURYO_INT, MAX_LEN_TOT_JYURYO_DEC, msgKey_itemNm)) {
			hdRec.setJyuryoTotClass(STYLE_CLASS_ERROR);
			setErrDtCase = true; // [DT]出荷数量_箱数
			setErrDtBara = true; // [DT]出荷数量_セット数
			lineRes = false;
		}

		// [HD]出荷金額計
		msgKey_itemNm = "com.text.kingakuKei";
		if (!vUtil.validateLen(hdRec.getSyukaKingakuTot(),
				MAX_LEN_TOT_KINGAKU_INT, MAX_LEN_TOT_KINGAKU_DEC, msgKey_itemNm)) {
			hdRec.setSyukaKingakuTotClass(STYLE_CLASS_ERROR);
			setErrDtCase = true; // [DT]出荷数量_箱数
			setErrDtBara = true; // [DT]出荷数量_セット数
			setErrDtTanka = true; // [DT]販売単価
			lineRes = false;
		}

		// [HD]容量計（L）_出荷容量計
		msgKey_itemNm = "com.text.syukaYouryoTot";
		if (!vUtil.validateLen(hdRec.getSyukaYouryoTot(),
				MAX_LEN_TOT_YOURYO_INT, MAX_LEN_TOT_YOURYO_DEC, msgKey_itemNm)) {
			hdRec.setSyukaYouryoTotClass(STYLE_CLASS_ERROR);
			setErrDtCase = true; // [DT]出荷数量_箱数
			setErrDtBara = true; // [DT]出荷数量_セット数
			lineRes = false;
		}

		// [HD]容量計（L）_リベートⅠ類対象容量計
		msgKey_itemNm = "com.text.rebate1YouryoTot";
		if (!vUtil.validateLen(hdRec.getRebate1YouryoTot(),
				MAX_LEN_TOT_REBATE_YOURYO_INT, MAX_LEN_TOT_REBATE_YOURYO_DEC, msgKey_itemNm)) {
			hdRec.setRebate1YouryoTotClass(STYLE_CLASS_ERROR);
			setErrDtRebate1 = true;
			lineRes = false;
		}

		// [HD]容量計（L）_リベートⅡ類対象容量計
		msgKey_itemNm = "com.text.rebate2YouryoTot";
		if (!vUtil.validateLen(hdRec.getRebate2YouryoTot(),
				MAX_LEN_TOT_REBATE_YOURYO_INT, MAX_LEN_TOT_REBATE_YOURYO_DEC, msgKey_itemNm)) {
			hdRec.setRebate2YouryoTotClass(STYLE_CLASS_ERROR);
			setErrDtRebate2 = true;
			lineRes = false;
		}

		// [HD]容量計（L）_リベートⅢ類対象容量計
		msgKey_itemNm = "com.text.rebate3YouryoTot";
		if (!vUtil.validateLen(hdRec.getRebate3YouryoTot(),
				MAX_LEN_TOT_REBATE_YOURYO_INT, MAX_LEN_TOT_REBATE_YOURYO_DEC, msgKey_itemNm)) {
			hdRec.setRebate3YouryoTotClass(STYLE_CLASS_ERROR);
			setErrDtRebate3 = true;
			lineRes = false;
		}

		// [HD]容量計（L）_リベートⅣ類対象容量計
		msgKey_itemNm = "com.text.rebate4YouryoTot";
		if (!vUtil.validateLen(hdRec.getRebate4YouryoTot(),
				MAX_LEN_TOT_REBATE_YOURYO_INT, MAX_LEN_TOT_REBATE_YOURYO_DEC, msgKey_itemNm)) {
			hdRec.setRebate4YouryoTotClass(STYLE_CLASS_ERROR);
			setErrDtRebate4 = true;
			lineRes = false;
		}

		// [HD]容量計（L）_リベートⅤ類対象容量計
		msgKey_itemNm = "com.text.rebate5YouryoTot";
		if (!vUtil.validateLen(hdRec.getRebate5YouryoTot(),
				MAX_LEN_TOT_REBATE_YOURYO_INT, MAX_LEN_TOT_REBATE_YOURYO_DEC, msgKey_itemNm)) {
			hdRec.setRebate5YouryoTotClass(STYLE_CLASS_ERROR);
			setErrDtRebate5 = true;
			lineRes = false;
		}

		// [HD]容量計（L）_リベート対象外容量計
		msgKey_itemNm = "com.text.rebateoYouryoTot";
		if (!vUtil.validateLen(hdRec.getRebateoYouryoTot(),
				MAX_LEN_TOT_REBATE_YOURYO_INT, MAX_LEN_TOT_REBATE_YOURYO_DEC, msgKey_itemNm)) {
			hdRec.setRebateoYouryoTotClass(STYLE_CLASS_ERROR);
			setErrDtRebateo = true;
			lineRes = false;
		}

		// @@@ 結果判定 @@@
		if (!lineRes) {
			// 行の更新対象フラグを降ろす
			hdRec.setModified(IS_MODIFIED_FALSE);
			// 戻り値をセットする
			res = false;
		}

		int line = 0;
		for (int i = 0; i < editDtList.size(); i++) {

			lineRes = true;
			line = i + 1;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			JuchuJuchuAddOnlyDtRecord dtRec = (JuchuJuchuAddOnlyDtRecord) editDtList.get(i);

			// 変更無しは、スキップ
			if (!dtRec.getIsModified()) {
				continue;
			}

			// [DT]出荷重量（KG)
			msgKey_itemNm = "com.text.syukaAllWeigth";
			if (!vUtil.validateLen(dtRec.getSyukaAllWeigth(),
					MAX_LEN_JYURYO_INT, MAX_LEN_JYURYO_DEC, msgKey_itemNm, line)) {
				dtRec.setSyukaAllWeigthClass(STYLE_CLASS_ERROR);
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
				lineRes = false;
			}

			// [DT]出荷数量_換算総セット数
			msgKey_itemNm = "com.text.syukaAllBara";
			if (!vUtil.validateLen(dtRec.getSyukaAllBara(),
					MAX_LEN_SOBARA_SU, msgKey_itemNm, line)) {
				dtRec.setSyukaAllBaraClass(STYLE_CLASS_ERROR);
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
				lineRes = false;
			}

			// [DT]販売額
			msgKey_itemNm = "com.text.syukaHanbaiKingaku";
			if (!vUtil.validateLen(dtRec.getSyukaHanbaiKingaku(),
					MAX_LEN_KINGAKU_INT, MAX_LEN_KINGAKU_DEC, msgKey_itemNm, line)) {
				dtRec.setSyukaHanbaiKingakuClass(STYLE_CLASS_ERROR);
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
				dtRec.setHanbaiTankaClass(STYLE_CLASS_ERROR); // [DT]販売単価
				lineRes = false;
			}

			// [DT]容量（L）_出荷総容量
			msgKey_itemNm = "com.text.syukaSouyouryo";
			if (!vUtil.validateLen(dtRec.getSyukaSouyouryo(),
					MAX_LEN_YOURYO_INT, MAX_LEN_YOURYO_DEC, msgKey_itemNm, line)) {
				dtRec.setSyukaSouyouryoClass(STYLE_CLASS_ERROR);
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
				lineRes = false;
			}

			// [DT]容量（L）_リベートⅠ類対象総容量
			msgKey_itemNm = "com.text.rebate1Souyouryo";
			if (!vUtil.validateLen(dtRec.getRebate1Souyouryo(),
					MAX_LEN_REBATE_YOURYO_INT, MAX_LEN_REBATE_YOURYO_DEC, msgKey_itemNm, line)) {
				dtRec.setRebate1SouyouryoClass(STYLE_CLASS_ERROR);
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
				lineRes = false;
			}

			// [DT]容量（L）_リベートⅡ類対象総容量
			msgKey_itemNm = "com.text.rebate2Souyouryo";
			if (!vUtil.validateLen(dtRec.getRebate2Souyouryo(),
					MAX_LEN_REBATE_YOURYO_INT, MAX_LEN_REBATE_YOURYO_DEC, msgKey_itemNm, line)) {
				dtRec.setRebate2SouyouryoClass(STYLE_CLASS_ERROR);
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
				lineRes = false;
			}

			// [DT]容量（L）_リベートⅢ類対象総容量
			msgKey_itemNm = "com.text.rebate3Souyouryo";
			if (!vUtil.validateLen(dtRec.getRebate3Souyouryo(),
					MAX_LEN_REBATE_YOURYO_INT, MAX_LEN_REBATE_YOURYO_DEC, msgKey_itemNm, line)) {
				dtRec.setRebate3SouyouryoClass(STYLE_CLASS_ERROR);
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
				lineRes = false;
			}

			// [DT]容量（L）_リベートⅣ類対象総容量
			msgKey_itemNm = "com.text.rebate4Souyouryo";
			if (!vUtil.validateLen(dtRec.getRebate4Souyouryo(),
					MAX_LEN_REBATE_YOURYO_INT, MAX_LEN_REBATE_YOURYO_DEC, msgKey_itemNm, line)) {
				dtRec.setRebate4SouyouryoClass(STYLE_CLASS_ERROR);
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
				lineRes = false;
			}

			// [DT]容量（L）_リベートⅤ類対象総容量
			msgKey_itemNm = "com.text.rebate5Souyouryo";
			if (!vUtil.validateLen(dtRec.getRebate5Souyouryo(),
					MAX_LEN_REBATE_YOURYO_INT, MAX_LEN_REBATE_YOURYO_DEC, msgKey_itemNm, line)) {
				dtRec.setRebate5SouyouryoClass(STYLE_CLASS_ERROR);
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
				lineRes = false;
			}

			// [DT]容量（L）_リベート対象外総容量
			msgKey_itemNm = "com.text.rebateoSouyouryo";
			if (!vUtil.validateLen(dtRec.getRebateoSouyouryo(),
					MAX_LEN_REBATE_YOURYO_INT, MAX_LEN_REBATE_YOURYO_DEC, msgKey_itemNm, line)) {
				dtRec.setRebateoSouyouryoClass(STYLE_CLASS_ERROR);
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
				lineRes = false;
			}

			// ----------------------------------------------
			// ヘッダー部でオーバーフローの場合、
			// 関係するディテール部にエラーフラグをセットする
			// ----------------------------------------------
			// [DT]出荷数量_箱数
			if (setErrDtCase) {
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR);
			}
			// [DT]出荷数量_セット数
			if (setErrDtBara) {
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR);
			}
			// [DT]販売単価
			if (setErrDtTanka) {
				dtRec.setHanbaiTankaClass(STYLE_CLASS_ERROR);
			}
			// [DT]リベートⅠ類
			if (setErrDtRebate1
					&& PbsUtil.isEqual(PbsUtil.strCompare(dtRec.getRebate1Souyouryo(), YOURYO_ZERO), "1")) {
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
			}
			// [DT]リベートⅡ類
			if (setErrDtRebate2
					&& PbsUtil.isEqual(PbsUtil.strCompare(dtRec.getRebate2Souyouryo(), YOURYO_ZERO), "1")) {
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
			}
			// [DT]リベートⅢ類
			if (setErrDtRebate3
					&& PbsUtil.isEqual(PbsUtil.strCompare(dtRec.getRebate3Souyouryo(), YOURYO_ZERO), "1")) {
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
			}
			// [DT]リベートⅣ類
			if (setErrDtRebate4
					&& PbsUtil.isEqual(PbsUtil.strCompare(dtRec.getRebate4Souyouryo(), YOURYO_ZERO), "1")) {
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
			}
			// [DT]リベートⅤ類
			if (setErrDtRebate5
					&& PbsUtil.isEqual(PbsUtil.strCompare(dtRec.getRebate5Souyouryo(), YOURYO_ZERO), "1")) {
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
			}
			// [DT]リベート対象外
			if (setErrDtRebateo
					&& PbsUtil.isEqual(PbsUtil.strCompare(dtRec.getRebateoSouyouryo(), YOURYO_ZERO), "1")) {
				dtRec.setSyukaSuCaseClass(STYLE_CLASS_ERROR); // [DT]出荷数量_箱数
				dtRec.setSyukaSuBaraClass(STYLE_CLASS_ERROR); // [DT]出荷数量_セット数
			}

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				dtRec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}

	/**
	 * 合計（計算結果）の変更部分に色付け（ヘッダー部）
	 *
	 * @param initList
	 * @param editList
	 */
	private void checkForCalc(JuchuJuchuAddOnlyList initList, JuchuJuchuAddOnlyList editList) {

		for (int i = 0; i < editList.size(); i++) {
			JuchuJuchuAddOnlyRecord iRec = (JuchuJuchuAddOnlyRecord) initList.get(i);
			JuchuJuchuAddOnlyRecord eRec = (JuchuJuchuAddOnlyRecord) editList.get(i);

			// 出荷数量計_箱数
			if (!PbsUtil.isEqual(iRec.getSyukaSuryoBox(), eRec.getSyukaSuryoBox())) {
				eRec.setSyukaSuryoBoxClass(STYLE_CLASS_MODIFIED);
			} else if (!eRec.getModified()) {
				eRec.setSyukaSuryoBoxClass(STYLE_CLASS_NO_EDIT);
			}

			// 出荷数量計_セット数
			if (!PbsUtil.isEqual(iRec.getSyukaSuryoSet(), eRec.getSyukaSuryoSet())) {
				eRec.setSyukaSuryoSetClass(STYLE_CLASS_MODIFIED);
			} else if (!eRec.getModified()) {
				eRec.setSyukaSuryoSetClass(STYLE_CLASS_NO_EDIT);
			}

			// 重量計(KG)
			if (!PbsUtil.isEqual(iRec.getJyuryoTot(), eRec.getJyuryoTot())) {
				eRec.setJyuryoTotClass(STYLE_CLASS_MODIFIED);
			} else if (!eRec.getModified()) {
				eRec.setJyuryoTotClass(STYLE_CLASS_NO_EDIT);
			}

			// 出荷金額計
			if (!PbsUtil.isEqual(iRec.getSyukaKingakuTot(), eRec.getSyukaKingakuTot())) {
				eRec.setSyukaKingakuTotClass(STYLE_CLASS_MODIFIED);
			} else if (!eRec.getModified()) {
				eRec.setSyukaKingakuTotClass(STYLE_CLASS_NO_EDIT);
			}
		}

	}

	/**
	 * 抹消時：関連チェックをする（ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForDelete(JuchuJuchuAddOnlyList editList) {

		boolean res = true;
		boolean lineRes = true;

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、対象外のためチェック不要
			JuchuJuchuAddOnlyRecord rec = (JuchuJuchuAddOnlyRecord) editList.get(i);

			// 利用区分が利用停止の場合は、すでに抹消済なのでエラー
			if (AVAILABLE_KB_RIYO_TEISI.equals(rec.getRiyouKbn())) {
				setErrorMessageId(new ActionError("errors.check.deleteMaster"));
				return false;
			}

			// 利用区分を利用停止にする
			rec.setRiyouKbn(AVAILABLE_KB_RIYO_TEISI);

			// データ種別CDを受注キャンセルにする
			rec.setSyubetuCd(JDATAKIND_KB_JYUCYU_CANCEL);

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				rec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}

	/**
	 * 抹消時：関連チェックをする（ディテール部）
	 *
	 * @param editDtList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForDelete(JuchuJuchuAddOnlyDtList editDtList) {

		boolean res = true;
		boolean lineRes = true;

		for (int i = 0; i < editDtList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、対象外のためチェック不要
			JuchuJuchuAddOnlyDtRecord rec = (JuchuJuchuAddOnlyDtRecord) editDtList.get(i);

			// 利用区分が利用停止の場合は、すでに抹消済なのでエラー
			if (AVAILABLE_KB_RIYO_TEISI.equals(rec.getRiyouKbn())) {
				setErrorMessageId(new ActionError("errors.check.deleteMaster"));
				return false;
			}

			// 利用区分を利用停止にする
			rec.setRiyouKbn(AVAILABLE_KB_RIYO_TEISI);

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				rec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}

	/**
	 * 復活時：関連チェックをする（ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForRebirth(JuchuJuchuAddOnlyList editList) {

		boolean res = true;
		boolean lineRes = true;

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、対象外のためチェック不要
			JuchuJuchuAddOnlyRecord rec = (JuchuJuchuAddOnlyRecord) editList.get(i);

			// 利用区分が利用可の場合は、抹消されていない（復活不可）なのでエラー
			if (AVAILABLE_KB_RIYO_KA.equals(rec.getRiyouKbn())) {
				setErrorMessageId(new ActionError("errors.check.notDeleteMaster"));
				return false;
			}

			// 利用区分を利用可にする
			rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA);

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				rec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}

	/**
	 * 復活時：関連チェックをする（ディテール部）
	 *
	 * @param editDtList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForRebirth(JuchuJuchuAddOnlyDtList editDtList) {

		boolean res = true;
		boolean lineRes = true;

		for (int i = 0; i < editDtList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、対象外のためチェック不要
			JuchuJuchuAddOnlyDtRecord rec = (JuchuJuchuAddOnlyDtRecord) editDtList.get(i);

			// 利用区分が利用可の場合は、抹消されていない（復活不可）なのでエラー
			if (AVAILABLE_KB_RIYO_KA.equals(rec.getRiyouKbn())) {
				setErrorMessageId(new ActionError("errors.check.notDeleteMaster"));
				return false;
			}

			// 利用区分を利用可にする
			rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA);

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				rec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}

	/**
	 * 復活時：関連チェックをする（ヘッダー部Ｘディテール部）
	 *
	 * @param editList
	 * @param editDtList
	 */
	private void inputCheckXForRebirth(JuchuJuchuAddOnlyList editList, JuchuJuchuAddOnlyDtList editDtList) {

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD
		String ktksyCd = cus.getKtksyCd(); // 寄託者CD

		// ヘッダー部レコード
		JuchuJuchuAddOnlyRecord hdRec = (JuchuJuchuAddOnlyRecord) editList.getFirstRecord();

		// データ種別CDを設定する
		hdRec.setSyubetuCd(getDataSyubetuCd(MODE_REBIRTH, editDtList, kaisyaCd, ktksyCd, false));

	}

	/**
	 * 呼出後：編集ボタン押下時操作可能チェック
	 *
	 * @param editList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean searchCheckXForEdit(JuchuJuchuAddOnlyList editList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			JuchuJuchuAddOnlyRecord rec = (JuchuJuchuAddOnlyRecord) editList.get(i);

			// 利用区分が利用停止の場合は、すでに抹消済なのでエラー
			if (AVAILABLE_KB_RIYO_TEISI.equals(rec.getRiyouKbn())) {
				setErrorMessageId(new ActionError("errors.check.deleteMaster"));
				return false;
			}

			// @@@ 結果判定 @@@
			if (!lineRes) {
				// 行の更新対象フラグを降ろす
				rec.setModified(IS_MODIFIED_FALSE);
				// 戻り値をセットする
				res = false;
				continue;
			}
		}

		return res;

	}


// ==============================
// ==== 必須チェックシリーズ ====
// ==============================

	/**
	 * 必須チェックを行う（呼出用）
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean inputCheckRequiredForSearch(JuchuJuchuAddOnly_SearchForm searchForm) {

		// 必須項目なし

		return true;
	}

	/**
	 * 必須チェックを行う（CSV出力用）
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean inputCheckRequiredForCsvMake(JuchuJuchuAddOnly_SearchForm searchForm) {

		// 必須入力があった場合適時入力。
		return true;
	}

	/**
	 * 属性チェックを行う（呼出用）
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean inputCheckForSearch(JuchuJuchuAddOnly_SearchForm searchForm) {

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD
		String ktksyCd = cus.getKtksyCd(); // 寄託者CD

		String msgKey_itemNm = "";

		// 最終送荷先卸
		msgKey_itemNm = "com.text.orositenCdLast";
		if (!vUtil.validateCodeSimple(searchForm.getOrositenCdLast(), IS_REQUIRED_FALSE, CODE_LEN_OROSITEN, msgKey_itemNm)) {
			return false;
		}
		searchForm.setOrositenNmLast(this.getOrositenNmByCd(searchForm.getOrositenCdLast(), kaisyaCd, true, true)); // 略称

		// 出荷日
		msgKey_itemNm = "com.text.syukaDt";
		if (!vUtil.validateDateSimple(searchForm.getSyukaDt(), IS_REQUIRED_FALSE, msgKey_itemNm)) {
			return false;
		}

		// 受注NO
		msgKey_itemNm = "com.text.juchuNo";
		if (!vUtil.validateHankakuSimple(searchForm.getJyucyuNo(), IS_REQUIRED_FALSE, MAX_LEN_JYUCYU_NO, msgKey_itemNm)) {
			return false;
		}

		// 運送店
		msgKey_itemNm = "com.text.unsoten";
		if (!vUtil.validateCodeSimple(searchForm.getUnsotenCd(), IS_REQUIRED_FALSE, CODE_LEN_UNSOTEN, msgKey_itemNm)) {
			return false;
		}
		searchForm.setUnsotenNm(this.getUnsotenNmByCd(searchForm.getUnsotenCd(), kaisyaCd, true, true)); // 略称

		// 商品
		msgKey_itemNm = "com.text.syohn";
		if (!vUtil.validateCodeSimple(searchForm.getShohinCd(), IS_REQUIRED_FALSE, CODE_LEN_SHOHIN, msgKey_itemNm)) {
			return false;
		}
		searchForm.setShohinNm(this.getShohinNmByCd(searchForm.getShohinCd(), kaisyaCd, ktksyCd, true, true)); // 略称

		return true;
	}

	/**
	 * 必須チェックを行う（新規開始用）
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean inputCheckRequiredForInsert(JuchuJuchuAddOnly_SearchForm searchForm) {
		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		// 最終送荷先卸
		if (!checkOrositenCdLast(searchForm.getOrositenCdLast(), searchForm, kaisyaCd)) {
			searchForm.setOrositenCdLastClass(STYLE_CLASS_ERROR);
			return false;
		} else {
			searchForm.setOrositenCdLastClass(STYLE_CLASS_INIT);
		}

		return true;
	}

	/**
	 * セット(選択時)必須チェック
	 *
	 * @param editForm
	 * @param editList
	 * @return TRUE / FALSE
	 */
	private boolean inputCheckRequiredForSelect(JuchuJuchuAddOnly_EditForm editForm,
			JuchuJuchuAddOnlyList editList) throws KitException {

		/* *******************************
		 * 選択有無チェック
		 * 1. editFormセット欄
		 * 2. 行選択ID
		 * 3. 更新対象フラグ
		 * ****************************** */

		for (;;) {
			// 1. editFormセット欄
			// 入力されていたら最優先でチェック
			if (!PbsUtil.isEmpty(editForm.getJyucyuNo())) {

				/* =============================
				 * 要注意ポイント(一覧から明細を取得するとき)
				 * 条件１、２のときは、前回選択された位置を
				 * リセットしないと複数行選択されてしまう。
				 * 条件３のときは、そのまま同じ行を取得して使用する
				 ============================= */

				// 更新対象フラグを初期化
				editList.initFlgs();

				// 受注NOチェック
				if (!checkJyucyuNo(editForm.getJyucyuNo())) {
					editForm.setJyucyuNoClass(STYLE_CLASS_ERROR);
					return false;
				}

				// 受注NOでレコードを取得する
				recSelected = editList.getRecByCode(editForm.getJyucyuNo());

				// レコードを確認
				if (recSelected == null) {
					// 指定されたコードは、呼び出されていません。
					setErrorMessageId("errors.not.selected");
					editForm.setJyucyuNoClass(STYLE_CLASS_ERROR);
					return false;
				}

				break;
			}

			// 2. 行選択ID
			if (!PbsUtil.isEmpty(editForm.getSelectedRowId())) {

				// 更新対象フラグを初期化
				editList.initFlgs();

				if (!PbsUtil.isNumber(editForm.getSelectedRowId())) {
					// 予期せぬエラーが発生しました。情報システム室へ連絡してください。
					// ※ jspの記述が正しければ発生しないはず = ユーザー操作では発生しない
					setErrorMessageId("errors.not.forseen");
					return false;
				}

				// 選択行IDでレコードを取得
				recSelected = (JuchuJuchuAddOnlyRecord) editList.getRecSelectedRow(editForm.getSelectedRowId());

				break;
			}

			// 3. 更新対象フラグ
			JuchuJuchuAddOnlyRecord rec = null;
			for (PbsRecord tmp : editList) {
				rec = (JuchuJuchuAddOnlyRecord) tmp;
				if (!rec.getIsModified()) {
					continue;
				}
				// 更新対象フラグレコードを取得
				recSelected = rec;
				break;
			}

			break;
		}

		// レコードを確認
		if (recSelected == null) {
			// なにも選択されていません。
			setErrorMessageId("errors.empty.selectedRecord");
			editForm.setJyucyuNoClass(STYLE_CLASS_ERROR);
			return false;
		}

		// 表示に必要な情報をセットする（コード→名称）
		this.setCdToNmSelect(recSelected);

		// レコードがセットされたのでTRUEで抜ける
		recSelected.setModified(IS_MODIFIED_TRUE);
		return true;

	}

	/**
	 * コード→名称変換（property=xx用）（ヘッダー部）
	 *
	 * @param rec JuchuJuchuAddOnlyRecord
	 */
	protected void setCdToNmSelect(JuchuJuchuAddOnlyRecord rec) throws KitException {
		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		// 最終送荷先卸（縦線CDから）
		rec.setOrositenNmLast(this.getOrositenNmByCd(rec.getOrositenCdLast(), kaisyaCd, true, true));

		// 酒販店
		rec.setSyuhantenNm(this.getSyuhantenNmByCd(rec.getSyuhantenCd(), true));

		// 運送店
		rec.setUnsotenNm(this.getUnsotenNmByCd(rec.getUnsotenCd(), kaisyaCd, true, true));

		// 担当者
		rec.setTantosyaNm(this.getTantosyaNmByCd(rec.getTantosyaCd(), kaisyaCd, true));

		// 入力者
		rec.setNyuryokusyaNm(this.getNyuryokusyaNmByCd(rec.getNyuryokusyaCd(), kaisyaCd, true));
	}

	/**
	 * コード→名称変換（property=xxView用）（ヘッダー部）
	 *
	 * @param rec JuchuJuchuAddOnlyRecord
	 */
	protected void setCdToNmView(JuchuJuchuAddOnlyRecord rec) throws KitException {
		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		// 最終送荷先卸（縦線CDから）
		if (!checkOrsTatesnCd(rec, kaisyaCd, true)) {
			// View用
		}
		// 酒販店
		if (!checkSyuhantenCd(rec, true)) {
			// View用
		}
		// 運送店
		if (!checkUnsotenCd(rec, kaisyaCd, true)) {
			// View用
		}
		// 担当者
		if (!checkTantosyaCd(rec, kaisyaCd, true)) {
			// View用
		}
		// 入力者
		if (!checkNyuryokusyaCd(rec, kaisyaCd, true)) {
			// View用
		}
	}

	/**
	 * コード→名称変換（property=xx用）（ディテール部）
	 *
	 * @param rec JuchuJuchuAddOnlyDtRecord
	 */
	protected void setCdToNmSelect(JuchuJuchuAddOnlyDtRecord rec) {
		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD
		String ktksyCd = cus.getKtksyCd(); // 寄託者CD

		// 商品名
		rec.setShohinNm(this.getShohinNmByCd(rec.getShohinCd(), kaisyaCd, ktksyCd, true, true));
	}

	/**
	 * コード→名称変換（property=xxView用）（ディテール部）
	 *
	 * @param rec JuchuJuchuAddOnlyDtRecord
	 * @param line 行番号
	 */
	protected void setCdToNmView(JuchuJuchuAddOnlyDtRecord rec, int line) {
		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD
		String ktksyCd = cus.getKtksyCd(); // 寄託者CD

		// 商品名
		if (!checkShohinCd(rec, kaisyaCd, ktksyCd, true, true, line)) {
			// View用
		}
	}

	/**
	 * View部処理（ディテール部）
	 *
	 * @param initDtList
	 * @param editDtList
	 */
	protected void setViews(JuchuJuchuAddOnlyDtList initDtList, JuchuJuchuAddOnlyDtList editDtList) {
		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD
		String ktksyCd = cus.getKtksyCd(); // 寄託者CD

		int line = 0;

		for (int i = 0; i < initDtList.size(); i++) {
			JuchuJuchuAddOnlyDtRecord iRec = (JuchuJuchuAddOnlyDtRecord) initDtList.get(i);
			JuchuJuchuAddOnlyDtRecord eRec = (JuchuJuchuAddOnlyDtRecord) editDtList.get(i);

			line = i + 1;

			// 商品CD
			eRec.setShohinCdView(iRec.getShohinCd());

			// 商品名
			if (!checkShohinCd(iRec.getShohinCd(), eRec, kaisyaCd, ktksyCd, true, true, line)) {
				// View用
			}

			// 容量(ml)
			eRec.setTnpnVolMlView(iRec.getTnpnVolMl());

			// 入数
			eRec.setCaseIrisuView(iRec.getCaseIrisu());

			// 扱い区分
			eRec.setAtukaiKbnView(iRec.getAtukaiKbn());

			// 出荷数量_箱数
			eRec.setSyukaSuCaseView(iRec.getSyukaSuCase());

			// 出荷数量_セット数
			eRec.setSyukaSuBaraView(iRec.getSyukaSuBara());

			// 単価
			eRec.setHanbaiTankaView(iRec.getHanbaiTanka());
		}
	}


// ==================================
// ==== パーツごとの単体チェック ====
// ==================================

	// ----- ヘッダー部 -----

	/**
	 * 受注NOの単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkJyucyuNo(String data) {
		String msgKey_itemNm = "com.text.juchuNo";

		// 必須チェック
		if (!vUtil.validateRequired(data, msgKey_itemNm)) {
			return false;
		}

		// 基本チェック
		if (!vUtil.validateHankaku(data, msgKey_itemNm)) {
			return false;
		}

		// 長さチェック
		if (!vUtil.validateLength(data, MAX_LEN_JYUCYU_NO, msgKey_itemNm)) {
			return false;
		}

		return true;
	}

	/**
	 * 最終送荷先卸CDの単体チェック（名称セット含む）
	 *
	 * @param data チェック対象値
	 * @param searchForm 検索フォーム
	 * @param kaisyaCd 会社コード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkOrositenCdLast(String data, JuchuJuchuAddOnly_SearchForm searchForm, String kaisyaCd) {
		String msgKey_itemNm = "com.text.orositenCdLast";

		// 基本チェック（必須チェック含む）
		if (!vUtil.validateCodeSimple(data, CODE_LEN_OROSITEN, msgKey_itemNm)) {
			// 初期化
			searchForm.setOrositenNmLast(CHAR_BLANK); // 最終送荷先卸名

			return false;
		}

		// 存在チェック なければエラー
		FbMastrOrositenRecord zRec = rUtil.getRecMastrOrositen(IS_DELETED_HELD_TRUE, kaisyaCd, data);
		if (!vUtil.validateMstExistence(zRec, msgKey_itemNm)) {
			// 初期化
			searchForm.setOrositenNmLast(CHAR_BLANK); // 最終送荷先卸名

			return false;
		}

		// 名称セット
		setOrositenCdLastToNm(searchForm, zRec);

		return true;
	}

	/**
	 * 最終送荷先卸CDから名称をセット
	 *
	 * @param searchForm 検索フォーム
	 * @param zRec 変換用レコード
	 */
	private void setOrositenCdLastToNm(JuchuJuchuAddOnly_SearchForm searchForm, FbMastrOrositenRecord zRec) {
		boolean isShortName = true; // 略称or正式

		// 初期化
		searchForm.setOrositenNmLast(CHAR_BLANK); // 最終送荷先卸名

		// 最終送荷先卸名をセット
		if (isShortName) {
			searchForm.setOrositenNmLast(zRec.getTenNm1Jisya()); // 略称
		} else {
			searchForm.setOrositenNmLast(zRec.getTenNm1Uriden()); // 正式
		}
	}


	/**
	 * 縦線CDの単体チェック（名称セット含む）
	 *
	 * @param rec 名称セット対象レコード
	 * @param kaisyaCd 会社コード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkOrsTatesnCd(JuchuJuchuAddOnlyRecord rec, String kaisyaCd, boolean isView) {
       return checkOrsTatesnCd(rec.getOrsTatesnCd(), rec, kaisyaCd, isView);
	}

	/**
	 * 縦線CDの単体チェック（名称セット含む）
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @param kaisyaCd 会社コード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkOrsTatesnCd(String data, JuchuJuchuAddOnlyRecord rec, String kaisyaCd, boolean isView) {
		String msgKey_itemNm = "com.text.tatesenCd";

		// 基本チェック（必須チェック含む）
		if (!isView && !vUtil.validateCodeSimple(data, CODE_LEN_ORS_TATESN, msgKey_itemNm)) {
			// 初期化
			rec.setOrositenNmLast(CHAR_BLANK); // 最終送荷先卸名

			return false;
		}

		if (!PbsUtil.isEmpty(data)) {
			// 存在チェック なければエラー
			FbMastrOrositenRecord zRec = rUtil.getRecMastrOrositen(IS_DELETED_HELD_TRUE, kaisyaCd, data.substring(0,6));
			if (!vUtil.validateMstExistence(zRec, msgKey_itemNm)) {
				// 初期化
				rec.setOrositenNmLast(CHAR_BLANK); // 最終送荷先卸名

				return false;
			}

			// 名称セット
			setOrsTatesnCdToNm(rec, zRec, isView);
		}

		return true;
	}

	/**
	 * 縦線CDから名称をセット
	 *
	 * @param rec 対象レコード
	 * @param zRec 変換用レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 */
	private void setOrsTatesnCdToNm(JuchuJuchuAddOnlyRecord rec, FbMastrOrositenRecord zRec, boolean isView) {
		boolean isShortName = true; // 略称or正式

		if (isView) {
			// 初期化
			rec.setOrositenNmLastView(CHAR_BLANK); // 最終送荷先卸名

			// 最終送荷先卸名をセット
			if (isShortName) {
				rec.setOrositenNmLastView(zRec.getTenNm1Jisya()); // 略称
			} else {
				rec.setOrositenNmLastView(zRec.getTenNm1Uriden()); // 正式
			}
		} else {
			// 初期化
			rec.setOrositenNmLast(CHAR_BLANK); // 最終送荷先卸名

			// 最終送荷先卸名をセット
			if (isShortName) {
				rec.setOrositenNmLast(zRec.getTenNm1Jisya()); // 略称
			} else {
				rec.setOrositenNmLast(zRec.getTenNm1Uriden()); // 正式
			}
		}
	}


	/**
	 * 酒販店CDの単体チェック（名称セット含む）
	 *
	 * @param rec 名称セット対象レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkSyuhantenCd(JuchuJuchuAddOnlyRecord rec, boolean isView) {
      return checkSyuhantenCd(rec.getSyuhantenCd(), rec, isView);
	}

	/**
	 * 酒販店CDの単体チェック（名称セット含む）
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkSyuhantenCd(String data, JuchuJuchuAddOnlyRecord rec, boolean isView) {
		String msgKey_itemNm = "com.text.syuhanten";

		// 基本チェック
		if (!isView && !vUtil.validateCodeSimple(data, IS_REQUIRED_FALSE, CODE_LEN_SYUHANTEN, msgKey_itemNm)) {
			// 初期化
			rec.setSyuhantenNm(CHAR_BLANK); // 酒販店名

			return false;
		}

		if (!PbsUtil.isEmpty(data)) {
			// 存在チェック なければエラー
			FbMastrSyuhantenRecord zRec = rUtil.getRecMastrSyuhanten(IS_DELETED_HELD_TRUE, data);
			if (!vUtil.validateMstExistence(zRec, msgKey_itemNm)) {
				// 初期化
				rec.setSyuhantenNm(CHAR_BLANK); // 酒販店名

				return false;
			}

			// 名称セット
			setSyuhantenCdToNm(rec, zRec, isView);
		}

		return true;
	}

	/**
	 * 酒販店CDから名称をセット
	 *
	 * @param rec 対象レコード
	 * @param zRec 変換用レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 */
	private void setSyuhantenCdToNm(JuchuJuchuAddOnlyRecord rec, FbMastrSyuhantenRecord zRec, boolean isView) {

		if (isView) {
			// 初期化
			rec.setSyuhantenNmView(CHAR_BLANK); // 酒販店名

			// 酒販店名をセット
			rec.setSyuhantenNmView(zRec.getTenNmYago());
		} else {
			// 初期化
			rec.setSyuhantenNm(CHAR_BLANK); // 酒販店名

			// 酒販店名をセット
			rec.setSyuhantenNm(zRec.getTenNmYago());
		}
	}


	/**
	 * 運送店CDの単体チェック（名称セット含む）
	 *
	 * @param rec 名称セット対象レコード
	 * @param kaisyaCd 会社コード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkUnsotenCd(JuchuJuchuAddOnlyRecord rec, String kaisyaCd, boolean isView) {
      return checkUnsotenCd(rec.getUnsotenCd(), rec, kaisyaCd, isView);
	}

	/**
	 * 運送店CDの単体チェック（名称セット含む）
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @param kaisyaCd 会社コード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkUnsotenCd(String data, JuchuJuchuAddOnlyRecord rec, String kaisyaCd, boolean isView) {
		String msgKey_itemNm = "com.text.unsoten";

		// 基本チェック（必須チェック含む）
		if (!isView && !vUtil.validateCodeSimple(data, CODE_LEN_UNSOTEN, msgKey_itemNm)) {
			// 初期化
			rec.setUnsotenNm(CHAR_BLANK); // 運送店名

			return false;
		}

		if (!PbsUtil.isEmpty(data)) {
			// 存在チェック なければエラー
			FbMastrUnsotenRecord zRec = rUtil.getRecMastrUnsoten(IS_DELETED_HELD_TRUE, kaisyaCd, data);
			if (!vUtil.validateMstExistence(zRec, msgKey_itemNm)) {
				// 初期化
				rec.setUnsotenNm(CHAR_BLANK); // 運送店名

				return false;
			}

			// 名称セット
			setUnsotenCdToNm(rec, zRec, isView);
		}

		return true;
	}

	/**
	 * 運送店CDから名称をセット
	 *
	 * @param rec 対象レコード
	 * @param zRec 変換用レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 */
	private void setUnsotenCdToNm(JuchuJuchuAddOnlyRecord rec, FbMastrUnsotenRecord zRec, boolean isView) {
		boolean isShortName = true; // 略称or正式

		if (isView) {
			// 初期化
			rec.setUnsotenNmView(CHAR_BLANK); // 運送店名

			// 運送店名をセット
			if (isShortName) {
				rec.setUnsotenNmView(zRec.getUnsotenNmPrint()); // 略称
			} else {
				rec.setUnsotenNmView(zRec.getUnsotenNm()); // 正式
			}
		} else {
			// 初期化
			rec.setUnsotenNm(CHAR_BLANK); // 運送店名

			// 運送店名をセット
			if (isShortName) {
				rec.setUnsotenNm(zRec.getUnsotenNmPrint()); // 略称
			} else {
				rec.setUnsotenNm(zRec.getUnsotenNm()); // 正式
			}
		}
	}


	/**
	 * 担当者CDの単体チェック（名称セット含む）
	 *
	 * @param rec 名称セット対象レコード
	 * @param kaisyaCd 会社コード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTantosyaCd(JuchuJuchuAddOnlyRecord rec, String kaisyaCd, boolean isView) {
    	return checkTantosyaCd(rec.getTantosyaCd(), rec, kaisyaCd, isView);
	}

	/**
	 * 担当者CDの単体チェック（名称セット含む）
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @param kaisyaCd 会社コード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTantosyaCd(String data, JuchuJuchuAddOnlyRecord rec, String kaisyaCd, boolean isView) {
		String msgKey_itemNm = "com.text.tanto";

		// 基本チェック（必須チェック含む）
		if (!isView && !vUtil.validateCodeSimple(data, MAX_LEN_TANTO_CD, msgKey_itemNm)) {
			// 初期化
			rec.setTantosyaNm(CHAR_BLANK); // 担当者名

			return false;
		}

		if (!PbsUtil.isEmpty(data)) {
			// 存在チェック なければエラー
			FbMastrKbnJisekiTantoRecord zRec = rUtil.getRecMastrKbnJisekiTanto(IS_DELETED_HELD_TRUE, kaisyaCd, data);
			if (!vUtil.validateMstExistence(zRec, msgKey_itemNm)) {
				// 初期化
				rec.setTantosyaNm(CHAR_BLANK); // 担当者名

				return false;
			}

			// 名称セット
			setTantosyaCdToNm(rec, zRec, isView);
		}

		return true;
	}

	/**
	 * 担当者CDから名称をセット
	 *
	 * @param rec 対象レコード
	 * @param zRec 変換用レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 */
	private void setTantosyaCdToNm(JuchuJuchuAddOnlyRecord rec, FbMastrKbnJisekiTantoRecord zRec, boolean isView) {
		boolean isShortName = true; // 略称or正式

		if (isView) {
			// 初期化
			rec.setTantosyaNmView(CHAR_BLANK); // 担当者名

			// 担当者名をセット
			if (isShortName) {
				rec.setTantosyaNmView(zRec.getTantoNmRyaku()); // 略称
			} else {
				rec.setTantosyaNmView(zRec.getTantoNm()); // 正式
			}
		} else {
			// 初期化
			rec.setTantosyaNm(CHAR_BLANK); // 担当者名

			// 担当者名をセット
			if (isShortName) {
				rec.setTantosyaNm(zRec.getTantoNmRyaku()); // 略称
			} else {
				rec.setTantosyaNm(zRec.getTantoNm()); // 正式
			}
		}
	}


	/**
	 * 入力者CDの単体チェック（名称セット含む）
	 *
	 * @param rec 名称セット対象レコード
	 * @param kaisyaCd 会社コード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkNyuryokusyaCd(JuchuJuchuAddOnlyRecord rec, String kaisyaCd, boolean isView) {
    	return checkNyuryokusyaCd(rec.getNyuryokusyaCd(), rec, kaisyaCd, isView);
	}

	/**
	 * 入力者CDの単体チェック（名称セット含む）
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @param kaisyaCd 会社コード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkNyuryokusyaCd(String data, JuchuJuchuAddOnlyRecord rec, String kaisyaCd, boolean isView) {
		String msgKey_itemNm = "com.text.nyuryokusya";

		if (!PbsUtil.isEmpty(data)) {
			// 存在チェック なければエラー
			FbMastrSgyosyaRecord zRec = rUtil.getRecMastrSgyosya(IS_DELETED_HELD_TRUE, kaisyaCd, data);
			if (!vUtil.validateMstExistence(zRec, msgKey_itemNm)) {
				// 初期化
				rec.setNyuryokusyaNm(CHAR_BLANK); // 入力者名

				return false;
			}

			// 名称セット
			setNyuryokusyaCdToNm(rec, zRec, isView);
		}

		return true;
	}

	/**
	 * 入力者CDから名称をセット
	 *
	 * @param rec 対象レコード
	 * @param zRec 変換用レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 */
	private void setNyuryokusyaCdToNm(JuchuJuchuAddOnlyRecord rec, FbMastrSgyosyaRecord zRec, boolean isView) {

		if (isView) {
			// 初期化
			rec.setNyuryokusyaNmView(CHAR_BLANK); // 入力者名

			// 入力者名をセット
			rec.setNyuryokusyaNmView(zRec.getSgyosyaNm());
		} else {
			// 初期化
			rec.setNyuryokusyaNm(CHAR_BLANK); // 入力者名

			// 入力者名をセット
			rec.setNyuryokusyaNm(zRec.getSgyosyaNm());
		}
	}


	// 蔵CD(出荷場所)
	// セレクトボックスのため単体チェックなし


	// 運賃区分
	// セレクトボックスのため単体チェックなし


	/**
	 * 出荷日(売上伝票発行予定日)の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkSyukaDt(String data) {
		String msgKey_itemNm = "com.text.syukaDt";

		// 基本チェック（必須チェック含む）
		if (!vUtil.validateDateSimple(data, IS_REQUIRED_TRUE, msgKey_itemNm)) {
			return false;
		}

		// 当日以降であること
		if (!PbsUtil.isFutureDate(PbsUtil.getTodayYYYYMMDD(), data, true)) {
			// エラーメッセージを出力
			setErrorMessageId("errors.pastDateError", PbsUtil.getMessageResourceString("com.text.syukaDt"));
			return false;
		}

		return true;
	}


	/**
	 * ミナシ日付の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkMinasiDt(String data) {
		String msgKey_itemNm = "com.text.minasi";

		// 基本チェック（必須チェック含む）
		if (!vUtil.validateDateSimple(data, IS_REQUIRED_TRUE, msgKey_itemNm)) {
			return false;
		}

		return true;
	}


	/**
	 * 着荷予定日の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkChacuniYoteiDt(String data) {
		String msgKey_itemNm = "com.text.chakubi";

		// 基本チェック
		if (!vUtil.validateDateSimple(data, IS_REQUIRED_FALSE, msgKey_itemNm)) {
			return false;
		}

		return true;
	}


	// 荷受時間区分
	// セレクトボックスのため単体チェックなし


	/**
	 * 荷受時間_開始の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkNiukeBiginTime(String data) {
		String msgKey_itemNm = "com.text.niukeTimeStart";

		// 基本チェック
		if (!vUtil.validateTime(data, msgKey_itemNm)) {
			return false;
		}

		return true;
	}


	/**
	 * 荷受時間_終了の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkNiukeEndTime(String data) {
		String msgKey_itemNm = "com.text.niukeTimeEnd";

		// 基本チェック
		if (!vUtil.validateTime(data, msgKey_itemNm)) {
			return false;
		}

		return true;
	}


	/**
	 * 先方発注NOの単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkSenpoHacyuNo(String data) {
		String msgKey_itemNm = "com.text.hacyuNo";

		// 基本チェック
		if (!vUtil.validateHankaku(data, msgKey_itemNm)) {
			return false;
		}

		// 長さチェック
		if (!vUtil.validateLength(data, MAX_LEN_HACYU_NO, msgKey_itemNm)) {
			return false;
		}

		return true;
	}


	/**
	 * 摘要区分 (01)の単体チェック（名称セット含む）
	 *
	 * @param rec 名称セット対象レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTekiyoKbn1(JuchuJuchuAddOnlyRecord rec, boolean isView) throws KitException {
		return checkTekiyoKbn1(rec.getTekiyoKbn1(), rec, isView);
	}

	/**
	 * 摘要区分 (01)の単体チェック（名称セット含む）
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTekiyoKbn1(String data, JuchuJuchuAddOnlyRecord rec, boolean isView) throws KitException {
		String msgKey_itemNm = "com.text.tekiyo1";

		// 手書き
		if (TEKIYO_KBN_TEGAKI.equals(data)) {
			return true;
		}

		// 基本チェック
		if (!isView && !vUtil.validateCodeSimple(data, IS_REQUIRED_FALSE, MAX_LEN_TEKIYO_KBN, msgKey_itemNm)) {
			// 初期化
			rec.setTekiyoNm1(CHAR_BLANK); // 摘要内容 (01)

			return false;
		}

		if (!PbsUtil.isEmpty(data)) {
			// 存在チェック なければエラー
			FbMastrKbnTekiyoRecord zRec = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, data);
			if (!vUtil.validateMstExistence(zRec, msgKey_itemNm)) {
				// 初期化
				rec.setTekiyoNm1(CHAR_BLANK); // 摘要内容 (01)

				return false;
			}

			// 名称セット
			setTekiyoKbn1ToNm(rec, zRec, isView);
		}

		return true;
	}

	/**
	 * 摘要区分 (01)から名称をセット
	 *
	 * @param rec 対象レコード
	 * @param zRec 変換用レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 */
	private void setTekiyoKbn1ToNm(JuchuJuchuAddOnlyRecord rec, FbMastrKbnTekiyoRecord zRec, boolean isView) throws KitException {

		if (isView) {
			// 初期化
			rec.setTekiyoNm1View(CHAR_BLANK); // 摘要内容 (01)

			// 摘要内容 (01)をセット
			rec.setTekiyoNm1View(zRec.getTekiyoNm());
		} else {
			// 初期化
			rec.setTekiyoNm1(CHAR_BLANK); // 摘要内容 (01)

			// 摘要内容 (01)をセット
			rec.setTekiyoNm1(zRec.getTekiyoNm());
		}
	}


	/**
	 * 摘要内容 (01)の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTekiyoNm1(String data) {
		String msgKey_itemNm = "com.text.tekiyo1";

		// 基本チェック（必須チェック含む）
		if (!vUtil.validateMojiSimple(data, MAX_LEN_TEKIYO_NAIYO, msgKey_itemNm)) {
			return false;
		}

		return true;
	}


	/**
	 * 摘要区分 (02)の単体チェック（名称セット含む）
	 *
	 * @param rec 名称セット対象レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTekiyoKbn2(JuchuJuchuAddOnlyRecord rec, boolean isView) throws KitException {
		return checkTekiyoKbn2(rec.getTekiyoKbn2(), rec, isView);
	}

	/**
	 * 摘要区分 (02)の単体チェック（名称セット含む）
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTekiyoKbn2(String data, JuchuJuchuAddOnlyRecord rec, boolean isView) throws KitException {
		String msgKey_itemNm = "com.text.tekiyo2";

		// 手書き
		if (TEKIYO_KBN_TEGAKI.equals(data)) {
			return true;
		}

		// 基本チェック
		if (!isView && !vUtil.validateCodeSimple(data, IS_REQUIRED_FALSE, MAX_LEN_TEKIYO_KBN, msgKey_itemNm)) {
			// 初期化
			rec.setTekiyoNm2(CHAR_BLANK); // 摘要内容 (02)

			return false;
		}

		if (!PbsUtil.isEmpty(data)) {
			// 存在チェック なければエラー
			FbMastrKbnTekiyoRecord zRec = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, data);
			if (!vUtil.validateMstExistence(zRec, msgKey_itemNm)) {
				// 初期化
				rec.setTekiyoNm2(CHAR_BLANK); // 摘要内容 (02)

				return false;
			}

			// 名称セット
			setTekiyoKbn2ToNm(rec, zRec, isView);
		}

		return true;
	}

	/**
	 * 摘要区分 (02)から名称をセット
	 *
	 * @param rec 対象レコード
	 * @param zRec 変換用レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 */
	private void setTekiyoKbn2ToNm(JuchuJuchuAddOnlyRecord rec, FbMastrKbnTekiyoRecord zRec, boolean isView) throws KitException {

		if (isView) {
			// 初期化
			rec.setTekiyoNm2View(CHAR_BLANK); // 摘要内容 (02)

			// 摘要内容 (02)をセット
			rec.setTekiyoNm2View(zRec.getTekiyoNm());
		} else {
			// 初期化
			rec.setTekiyoNm2(CHAR_BLANK); // 摘要内容 (02)

			// 摘要内容 (02)をセット
			rec.setTekiyoNm2(zRec.getTekiyoNm());
		}
	}


	/**
	 * 摘要内容 (02)の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTekiyoNm2(String data) {
		String msgKey_itemNm = "com.text.tekiyo2";

		// 基本チェック（必須チェック含む）
		if (!vUtil.validateMojiSimple(data, MAX_LEN_TEKIYO_NAIYO, msgKey_itemNm)) {
			return false;
		}

		return true;
	}


	/**
	 * 摘要区分 (03)の単体チェック（名称セット含む）
	 *
	 * @param rec 名称セット対象レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTekiyoKbn3(JuchuJuchuAddOnlyRecord rec, boolean isView) throws KitException {
		return checkTekiyoKbn3(rec.getTekiyoKbn3(), rec, isView);
	}

	/**
	 * 摘要区分 (03)の単体チェック（名称セット含む）
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTekiyoKbn3(String data, JuchuJuchuAddOnlyRecord rec, boolean isView) throws KitException {
		String msgKey_itemNm = "com.text.tekiyo3";

		// 手書き
		if (TEKIYO_KBN_TEGAKI.equals(data)) {
			return true;
		}

		// 基本チェック
		if (!isView && !vUtil.validateCodeSimple(data, IS_REQUIRED_FALSE, MAX_LEN_TEKIYO_KBN, msgKey_itemNm)) {
			// 初期化
			rec.setTekiyoNm3(CHAR_BLANK); // 摘要内容 (03)

			return false;
		}

		if (!PbsUtil.isEmpty(data)) {
			// 存在チェック なければエラー
			FbMastrKbnTekiyoRecord zRec = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, data);
			if (!vUtil.validateMstExistence(zRec, msgKey_itemNm)) {
				// 初期化
				rec.setTekiyoNm3(CHAR_BLANK); // 摘要内容 (03)

				return false;
			}

			// 名称セット
			setTekiyoKbn3ToNm(rec, zRec, isView);
		}

		return true;
	}

	/**
	 * 摘要区分 (03)から名称をセット
	 *
	 * @param rec 対象レコード
	 * @param zRec 変換用レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 */
	private void setTekiyoKbn3ToNm(JuchuJuchuAddOnlyRecord rec, FbMastrKbnTekiyoRecord zRec, boolean isView) throws KitException {

		if (isView) {
			// 初期化
			rec.setTekiyoNm3View(CHAR_BLANK); // 摘要内容 (03)

			// 摘要内容 (03)をセット
			rec.setTekiyoNm3View(zRec.getTekiyoNm());
		} else {
			// 初期化
			rec.setTekiyoNm3(CHAR_BLANK); // 摘要内容 (03)

			// 摘要内容 (03)をセット
			rec.setTekiyoNm3(zRec.getTekiyoNm());
		}
	}


	/**
	 * 摘要内容 (03)の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTekiyoNm3(String data) {
		String msgKey_itemNm = "com.text.tekiyo3";

		// 基本チェック（必須チェック含む）
		if (!vUtil.validateMojiSimple(data, MAX_LEN_TEKIYO_NAIYO, msgKey_itemNm)) {
			return false;
		}

		return true;
	}


	/**
	 * 摘要区分 (04)の単体チェック（名称セット含む）
	 *
	 * @param rec 名称セット対象レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTekiyoKbn4(JuchuJuchuAddOnlyRecord rec, boolean isView) throws KitException {
		return checkTekiyoKbn4(rec.getTekiyoKbn4(), rec, isView);
	}

	/**
	 * 摘要区分 (04)の単体チェック（名称セット含む）
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTekiyoKbn4(String data, JuchuJuchuAddOnlyRecord rec, boolean isView) throws KitException {
		String msgKey_itemNm = "com.text.tekiyo4";

		// 手書き
		if (TEKIYO_KBN_TEGAKI.equals(data)) {
			return true;
		}

		// 基本チェック
		if (!isView && !vUtil.validateCodeSimple(data, IS_REQUIRED_FALSE, MAX_LEN_TEKIYO_KBN, msgKey_itemNm)) {
			// 初期化
			rec.setTekiyoNm4(CHAR_BLANK); // 摘要内容 (04)

			return false;
		}

		if (!PbsUtil.isEmpty(data)) {
			// 存在チェック なければエラー
			FbMastrKbnTekiyoRecord zRec = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, data);
			if (!vUtil.validateMstExistence(zRec, msgKey_itemNm)) {
				// 初期化
				rec.setTekiyoNm4(CHAR_BLANK); // 摘要内容 (04)

				return false;
			}

			// 名称セット
			setTekiyoKbn4ToNm(rec, zRec, isView);
		}

		return true;
	}

	/**
	 * 摘要区分 (04)から名称をセット
	 *
	 * @param rec 対象レコード
	 * @param zRec 変換用レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 */
	private void setTekiyoKbn4ToNm(JuchuJuchuAddOnlyRecord rec, FbMastrKbnTekiyoRecord zRec, boolean isView) throws KitException {

		if (isView) {
			// 初期化
			rec.setTekiyoNm4View(CHAR_BLANK); // 摘要内容 (04)

			// 摘要内容 (04)をセット
			rec.setTekiyoNm4View(zRec.getTekiyoNm());
		} else {
			// 初期化
			rec.setTekiyoNm4(CHAR_BLANK); // 摘要内容 (04)

			// 摘要内容 (04)をセット
			rec.setTekiyoNm4(zRec.getTekiyoNm());
		}
	}


	/**
	 * 摘要内容 (04)の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTekiyoNm4(String data) {
		String msgKey_itemNm = "com.text.tekiyo4";

		// 基本チェック（必須チェック含む）
		if (!vUtil.validateMojiSimple(data, MAX_LEN_TEKIYO_NAIYO, msgKey_itemNm)) {
			return false;
		}

		return true;
	}


	/**
	 * 摘要区分 (05)の単体チェック（名称セット含む）
	 *
	 * @param rec 名称セット対象レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTekiyoKbn5(JuchuJuchuAddOnlyRecord rec, boolean isView) throws KitException {
		return checkTekiyoKbn5(rec.getTekiyoKbn5(), rec, isView);
	}

	/**
	 * 摘要区分 (05)の単体チェック（名称セット含む）
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTekiyoKbn5(String data, JuchuJuchuAddOnlyRecord rec, boolean isView) throws KitException {
		String msgKey_itemNm = "com.text.tekiyo5";

		// 手書き
		if (TEKIYO_KBN_TEGAKI.equals(data)) {
			return true;
		}

		// 基本チェック
		if (!isView && !vUtil.validateCodeSimple(data, IS_REQUIRED_FALSE, MAX_LEN_TEKIYO_KBN, msgKey_itemNm)) {
			// 初期化
			rec.setTekiyoNm5(CHAR_BLANK); // 摘要内容 (05)

			return false;
		}

		if (!PbsUtil.isEmpty(data)) {
			// 存在チェック なければエラー
			FbMastrKbnTekiyoRecord zRec = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, data);
			if (!vUtil.validateMstExistence(zRec, msgKey_itemNm)) {
				// 初期化
				rec.setTekiyoNm5(CHAR_BLANK); // 摘要内容 (05)

				return false;
			}

			// 名称セット
			setTekiyoKbn5ToNm(rec, zRec, isView);
		}

		return true;
	}

	/**
	 * 摘要区分 (05)から名称をセット
	 *
	 * @param rec 対象レコード
	 * @param zRec 変換用レコード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 */
	private void setTekiyoKbn5ToNm(JuchuJuchuAddOnlyRecord rec, FbMastrKbnTekiyoRecord zRec, boolean isView) throws KitException {

		if (isView) {
			// 初期化
			rec.setTekiyoNm5View(CHAR_BLANK); // 摘要内容 (05)

			// 摘要内容 (05)をセット
			rec.setTekiyoNm5View(zRec.getTekiyoNm());
		} else {
			// 初期化
			rec.setTekiyoNm5(CHAR_BLANK); // 摘要内容 (05)

			// 摘要内容 (05)をセット
			rec.setTekiyoNm5(zRec.getTekiyoNm());
		}
	}


	/**
	 * 摘要内容 (05)の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTekiyoNm5(String data) {
		String msgKey_itemNm = "com.text.tekiyo5";

		// 基本チェック（必須チェック含む）
		if (!vUtil.validateMojiSimple(data, MAX_LEN_TEKIYO_NAIYO, msgKey_itemNm)) {
			return false;
		}

		return true;
	}


	/**
	 * 小ロット出荷承認申請NOの単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkSyukaSyoninNo(String data) {
		String msgKey_itemNm = "com.text.syukaSyoninNo";

		// 基本チェック
		if (!vUtil.validateHankaku(data, msgKey_itemNm)) {
			return false;
		}

		// 長さチェック
		if (!vUtil.validateLength(data, MAX_LEN_SYUKA_SYONIN_NO, msgKey_itemNm)) {
			return false;
		}

		return true;
	}


	// ----- ディテール部 -----

	/**
	 * 商品CDの単体チェック（名称セット含む）
	 *
	 * @param rec 名称セット対象レコード
	 * @param kaisyaCd 会社コード
	 * @param ktksyCd 寄託者コード
	 * @param isShortName 略称フラグ
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @param line 行番号
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkShohinCd(JuchuJuchuAddOnlyDtRecord rec, String kaisyaCd, String ktksyCd,
			boolean isShortName, boolean isView, int line) {
     return checkShohinCd(rec.getShohinCd(), rec, kaisyaCd, ktksyCd, isShortName, isView, line);
	}

	/**
	 * 商品CDの単体チェック（名称セット含む）
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @param kaisyaCd 会社コード
	 * @param ktksyCd 寄託者コード
	 * @param isShortName 略称フラグ
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @param line 行番号
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkShohinCd(String data, JuchuJuchuAddOnlyDtRecord rec, String kaisyaCd,
			String ktksyCd, boolean isShortName, boolean isView, int line) {
		String msgKey_itemNm = "com.text.syohinCd";

		// 基本チェック（必須チェック含む）
		if (!isView && !vUtil.validateCodeSimple(data, CODE_LEN_SHOHIN, line, msgKey_itemNm)) {
			// 初期化
			rec.setShohinNm(CHAR_BLANK); // 商品名

			return false;
		}

		if (!PbsUtil.isEmpty(data)) {
			// 存在チェック なければエラー
			FbMastrSyohinRecord zRec = rUtil.getRecMastrSyohin(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, data);
			if (!vUtil.validateMstExistence(zRec, line, msgKey_itemNm)) {
				// 初期化
				rec.setShohinNm(CHAR_BLANK); // 商品名

				return false;
			}

			// 名称セット
			setShohinCdToNm(rec, zRec, isShortName, isView);
		}

		return true;
	}

	/**
	 * 商品CDから名称をセット
	 *
	 * @param rec 対象レコード
	 * @param zRec 変換用レコード
	 * @param isShortName 略称フラグ
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 */
	private void setShohinCdToNm(JuchuJuchuAddOnlyDtRecord rec, FbMastrSyohinRecord zRec, boolean isShortName,
			boolean isView) {

		if (isView) {
			// 初期化
			rec.setShohinNmView(CHAR_BLANK); // 商品名

			// 商品名をセット
			if (isShortName) {
				rec.setShohinNmView(zRec.getShohinNmUriden()); // 略称
			} else {
				rec.setShohinNmView(zRec.getShohinNm()); // 正式
			}
		} else {
			// 初期化
			rec.setShohinNm(CHAR_BLANK); // 商品名

			// 商品名をセット
			if (isShortName) {
				rec.setShohinNm(zRec.getShohinNmUriden()); // 略称
			} else {
				rec.setShohinNm(zRec.getShohinNm()); // 正式
			}
		}
	}


	/**
	 * 販売単価の単体チェック
	 *
	 * @param data チェック対象値
	 * @param line 行番号
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkHanbaiTanka(String data, int line) {
		String msgKey_itemNm = "com.text.tanka";

		// 必須チェック
		if (!vUtil.validateRequired(data, line, msgKey_itemNm)) {
			return false;
		}

		// 型チェック
		if (!vUtil.validateNumberFormat(data, MAX_LEN_TANKA_INT, MAX_LEN_TANKA_DEC, line, msgKey_itemNm)) {
			return false;
		}

		// 長さチェック
		if (!vUtil.validateLength(data, MAX_LEN_TANKA, line, msgKey_itemNm)) {
			return false;
		}

		//
		return true;
	}


	/**
	 * 出荷数量_箱数の単体チェック
	 *
	 * @param data チェック対象値
	 * @param line 行番号
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkSyukaSuCase(String data, int line) {
		String msgKey_itemNm = "com.text.hakoSu";

		// 型チェック
		if (!vUtil.validateNumeric(data, line, msgKey_itemNm)) {
			return false;
		}

		// 長さチェック
		if (!vUtil.validateLength(data, MAX_LEN_SYUKA_HAKO_SU, line, msgKey_itemNm)) {
			return false;
		}

		return true;
	}


	/**
	 * 出荷数量_セット数の単体チェック
	 *
	 * @param data チェック対象値
	 * @param line 行番号
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkSyukaSuBara(String data, int line) {
		String msgKey_itemNm = "com.text.setSu";

		// 型チェック
		if (!vUtil.validateNumeric(data, line, msgKey_itemNm)) {
			return false;
		}

		// 長さチェック
		if (!vUtil.validateLength(data, MAX_LEN_SYUKA_SET_SU, line, msgKey_itemNm)) {
			return false;
		}

		return true;
	}


// =========================================================
// ==== 付加情報取得（取得できなくてもエラーは返さない）====
// =========================================================

	/**
	 * 担当者CDから担当者名を取得する。
	 *
	 * @param tantosyaCd 担当者CD
	 * @param kaisyaCd 会社CD
	 * @param blankAtErr 取得不可時に空白で返すフラグ（true：空白、false：＊不可理由文字列＊）
	 * @return 担当者名
	 */
	protected String getTantosyaNmByCd(String tantosyaCd, String kaisyaCd, boolean blankAtErr) {
		String ret = "";

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrKbnJisekiTantoRecord zRec = rUtil.getRecMastrKbnJisekiTanto(IS_DELETED_HELD_TRUE, kaisyaCd, tantosyaCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			if (!blankAtErr) {
				ret = PbsUtil.getMessageResourceString("com.text.noValue"); // ＊該当なし＊
			}
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				ret = zRec.getTantoNmRyaku();
			// 利用不可
			} else {
				if (!blankAtErr) {
					ret = PbsUtil.getMessageResourceString("com.text.dontUse"); // ＊利用不可＊
				}
			}
		}

		return ret;
	}

	/**
	 * 入力者CDから入力者名を取得する。
	 *
	 * @param nyuryokusyaCd 入力者CD
	 * @param kaisyaCd 会社CD
	 * @param blankAtErr 取得不可時に空白で返すフラグ（true：空白、false：＊不可理由文字列＊）
	 * @return 入力者名
	 */
	protected String getNyuryokusyaNmByCd(String nyuryokusyaCd, String kaisyaCd, boolean blankAtErr) {
		String ret = "";

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrSgyosyaRecord zRec = rUtil.getRecMastrSgyosya(IS_DELETED_HELD_TRUE, kaisyaCd, nyuryokusyaCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			if (!blankAtErr) {
				ret = PbsUtil.getMessageResourceString("com.text.noValue"); // ＊該当なし＊
			}
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				ret = zRec.getSgyosyaNm();
			// 利用不可
			} else {
				if (!blankAtErr) {
					ret = PbsUtil.getMessageResourceString("com.text.dontUse"); // ＊利用不可＊
				}
			}
		}

		return ret;
	}

	/**
	 * 卸店CDから卸店名を取得する。
	 *
	 * @param orositenCd 卸店CD
	 * @param kaisyaCd 会社CD
	 * @param isShortName 略称フラグ
	 * @param blankAtErr 取得不可時に空白で返すフラグ（true：空白、false：＊不可理由文字列＊）
	 * @return 卸店名
	 */
	protected String getOrositenNmByCd(String orositenCd, String kaisyaCd, boolean isShortName, boolean blankAtErr) {
		String ret = "";

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrOrositenRecord zRec = rUtil.getRecMastrOrositen(IS_DELETED_HELD_TRUE, kaisyaCd, orositenCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			if (!blankAtErr) {
				ret = PbsUtil.getMessageResourceString("com.text.noValue"); // ＊該当なし＊
			}
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				if (isShortName) {
					if (!PbsUtil.isEmpty(zRec.getTenNm1Jisya())) {
						ret = zRec.getTenNm1Jisya(); // 略称
					}
				} else {
					if (!PbsUtil.isEmpty(zRec.getTenNm1Uriden())) {
						ret = zRec.getTenNm1Uriden(); // 正式
					}
				}
			// 利用不可
			} else {
				if (!blankAtErr) {
					ret = PbsUtil.getMessageResourceString("com.text.dontUse"); // ＊利用不可＊
				}
			}
		}

		return ret;
	}

	/**
	 * 酒販店CDから酒販店名を取得する。
	 *
	 * @param syuhantenCd 酒販店CD
	 * @param blankAtErr 取得不可時に空白で返すフラグ（true：空白、false：＊不可理由文字列＊）
	 * @return 酒販店名
	 */
	protected String getSyuhantenNmByCd(String syuhantenCd, boolean blankAtErr) {
		String ret = "";

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrSyuhantenRecord zRec = rUtil.getRecMastrSyuhanten(IS_DELETED_HELD_TRUE, syuhantenCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			if (!blankAtErr) {
				ret = PbsUtil.getMessageResourceString("com.text.noValue"); // ＊該当なし＊
			}
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				if (!PbsUtil.isEmpty(zRec.getTenNmYago())) {
					ret = zRec.getTenNmYago();
				}
			// 利用不可
			} else {
				if (!blankAtErr) {
					ret = PbsUtil.getMessageResourceString("com.text.dontUse"); // ＊利用不可＊
				}
			}
		}

		return ret;
	}

	/**
	 * 運送店CDから運送店名を取得する。
	 *
	 * @param unsotenCd 運送店CD
	 * @param kaisyaCd 会社CD
	 * @param isShortName 略称フラグ
	 * @param blankAtErr 取得不可時に空白で返すフラグ（true：空白、false：＊不可理由文字列＊）
	 * @return 運送店名
	 */
	protected String getUnsotenNmByCd(String unsotenCd, String kaisyaCd, boolean isShortName, boolean blankAtErr) {
		String ret = "";

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrUnsotenRecord zRec = rUtil.getRecMastrUnsoten(IS_DELETED_HELD_TRUE, kaisyaCd, unsotenCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			if (!blankAtErr) {
				ret = PbsUtil.getMessageResourceString("com.text.noValue"); // ＊該当なし＊
			}
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				if (isShortName) {
					if (!PbsUtil.isEmpty(zRec.getUnsotenNmPrint())) {
						ret = zRec.getUnsotenNmPrint(); // 略称
					}
				} else {
					if (!PbsUtil.isEmpty(zRec.getUnsotenNm())) {
						ret = zRec.getUnsotenNm(); // 正式
					}
				}
			// 利用不可
			} else {
				if (!blankAtErr) {
					ret = PbsUtil.getMessageResourceString("com.text.dontUse"); // ＊利用不可＊
				}
			}
		}

		return ret;
	}

	/**
	 * 摘要区分から摘要内容を取得する。
	 *
	 * @param tekiyoKbn 摘要区分
	 * @param blankAtErr 取得不可時に空白で返すフラグ（true：空白、false：＊不可理由文字列＊）
	 * @return 摘要内容
	 */
	protected String getTekiyoNmByKbn(String tekiyoKbn, boolean blankAtErr) {
		String ret = "";

		// 手書きの場合は空白を返す
		if (TEKIYO_KBN_TEGAKI.equals(tekiyoKbn)) {
			return ret;
		}

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrKbnTekiyoRecord zRec = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, tekiyoKbn);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			if (!blankAtErr) {
				ret = PbsUtil.getMessageResourceString("com.text.noValue"); // ＊該当なし＊
			}
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				if (!PbsUtil.isEmpty(zRec.getTekiyoNm())) {
					ret = zRec.getTekiyoNm();
				}
			// 利用不可
			} else {
				if (!blankAtErr) {
					ret = PbsUtil.getMessageResourceString("com.text.dontUse"); // ＊利用不可＊
				}
			}
		}

		return ret;
	}

	/**
	 * 商品CDから物品区分を取得する。
	 *
	 * @param shohinCd 商品CD
	 * @param kaisyaCd 会社CD
	 * @param ktksyCd 寄託者CD
	 * @return 物品区分
	 */
	protected String getBupinKbnByShohinCd(String shohinCd, String kaisyaCd, String ktksyCd) {
		String ret = "";

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrSyohinRecord zRec = rUtil.getRecMastrSyohin(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, shohinCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			// ＊該当なし＊
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				if (!PbsUtil.isEmpty(zRec.getBupinKbn())) {
					ret = zRec.getBupinKbn();
				}
			// 利用不可
			} else {
				// ＊利用不可＊
			}
		}

		return ret;
	}

	/**
	 * 商品CDから製品CDを取得する。
	 *
	 * @param shohinCd 商品CD
	 * @param kaisyaCd 会社CD
	 * @param ktksyCd 寄託者CD
	 * @return 製品CD
	 */
	protected String getSeihinCdByShohinCd(String shohinCd, String kaisyaCd, String ktksyCd) {
		String ret = "";

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrSyohinRecord zRec = rUtil.getRecMastrSyohin(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, shohinCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			// ＊該当なし＊
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				if (!PbsUtil.isEmpty(zRec.getSeihinCd())) {
					ret = zRec.getSeihinCd();
				}
			// 利用不可
			} else {
				// ＊利用不可＊
			}
		}

		return ret;
	}

	/**
	 * 商品CDから仕入先CDを取得する。
	 *
	 * @param shohinCd 商品CD
	 * @param kaisyaCd 会社CD
	 * @param ktksyCd 寄託者CD
	 * @return 仕入先CD
	 */
	protected String getSiiresakiCdByShohinCd(String shohinCd, String kaisyaCd, String ktksyCd) {
		String ret = "";

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrSyohinRecord zRec = rUtil.getRecMastrSyohin(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, shohinCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			// ＊該当なし＊
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				if (!PbsUtil.isEmpty(zRec.getSiiresakiCd())) {
					ret = zRec.getSiiresakiCd();
				}
			// 利用不可
			} else {
				// ＊利用不可＊
			}
		}

		return ret;
	}

	/**
	 * 商品CDから仕入品CDを取得する。
	 *
	 * @param shohinCd 商品CD
	 * @param kaisyaCd 会社CD
	 * @param ktksyCd 寄託者CD
	 * @return 仕入品CD
	 */
	protected String getSiirehinCdByShohinCd(String shohinCd, String kaisyaCd, String ktksyCd) {
		String ret = "";

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrSyohinRecord zRec = rUtil.getRecMastrSyohin(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, shohinCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			// ＊該当なし＊
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				if (!PbsUtil.isEmpty(zRec.getSiirehinCd())) {
					ret = zRec.getSiirehinCd();
				}
			// 利用不可
			} else {
				// ＊利用不可＊
			}
		}

		return ret;
	}

	/**
	 * 商品CDから商品名を取得する。
	 *
	 * @param shohinCd 商品CD
	 * @param kaisyaCd 会社CD
	 * @param ktksyCd 寄託者CD
	 * @param isShortName 略称フラグ
	 * @param blankAtErr 取得不可時に空白で返すフラグ（true：空白、false：＊不可理由文字列＊）
	 * @return 商品名
	 */
	protected String getShohinNmByCd(String shohinCd, String kaisyaCd, String ktksyCd, boolean isShortName, boolean blankAtErr) {
		String ret = "";

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrSyohinRecord zRec = rUtil.getRecMastrSyohin(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, shohinCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			if (!blankAtErr) {
				ret = PbsUtil.getMessageResourceString("com.text.noValue"); // ＊該当なし＊
			}
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				if (isShortName) {
					if (!PbsUtil.isEmpty(zRec.getShohinNmUriden())) {
						ret = zRec.getShohinNmUriden(); // 略称
					}
				} else {
					if (!PbsUtil.isEmpty(zRec.getShohinNm())) {
						ret = zRec.getShohinNm(); // 正式
					}
				}
			// 利用不可
			} else {
				if (!blankAtErr) {
					ret = PbsUtil.getMessageResourceString("com.text.dontUse"); // ＊利用不可＊
				}
			}
		}

		return ret;
	}

	/**
	 * 商品CDから容量(L)を取得する。
	 *
	 * @param shohinCd 商品CD
	 * @param kaisyaCd 会社CD
	 * @param ktksyCd 寄託者CD
	 * @return 容量(L)
	 */
	protected String getTnpnVolByShohinCd(String shohinCd, String kaisyaCd, String ktksyCd) {
		String ret = YOURYO_ZERO;

		// 商品CDから製品CDを取得
		String seihinCd = getSeihinCdByShohinCd(shohinCd, kaisyaCd, ktksyCd);

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrSeihinRecord zRec = rUtil.getRecMastrSeihin(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, seihinCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			// ＊該当なし＊
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				if (!PbsUtil.isEmpty(zRec.getTnpnVol())) {
					ret = zRec.getTnpnVol();
				}
			// 利用不可
			} else {
				// ＊利用不可＊
			}
		}

		return ret;
	}

	/**
	 * 商品CDから容量(ml)を取得する。
	 *
	 * @param shohinCd 商品CD
	 * @param kaisyaCd 会社CD
	 * @param ktksyCd 寄託者CD
	 * @return 容量(ml)
	 */
	protected String getTnpnVolMlByShohinCd(String shohinCd, String kaisyaCd, String ktksyCd) {
		String ret = YOURYO_ZERO;

		// 商品CDから製品CDを取得
		String seihinCd = getSeihinCdByShohinCd(shohinCd, kaisyaCd, ktksyCd);

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrSeihinRecord zRec = rUtil.getRecMastrSeihin(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, seihinCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			// ＊該当なし＊
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				// 1000倍して小数点以下を四捨五入
				BigDecimal tnpnVol = new BigDecimal(this.getTnpnVolByShohinCd(shohinCd, kaisyaCd, ktksyCd)); // 単品総容量(L)@1バラ当り
				BigDecimal tnpnVolMl = (tnpnVol.multiply(new BigDecimal(1000))).setScale(0, BigDecimal.ROUND_HALF_UP);
				if (!PbsUtil.isEmpty(tnpnVolMl.toString())) {
					ret = tnpnVolMl.toString(); // 単品総容量(ML)@1バラ当り
				}
			// 利用不可
			} else {
				// ＊利用不可＊
			}
		}

		return ret;
	}

	/**
	 * 商品CDから入数を取得する。
	 *
	 * @param shohinCd 商品CD
	 * @param kaisyaCd 会社CD
	 * @param ktksyCd 寄託者CD
	 * @return 入数
	 */
	protected String getCaseIrisuByShohinCd(String shohinCd, String kaisyaCd, String ktksyCd) {
		String ret = SU_ZERO;

		// 商品CDから製品CDを取得
		String seihinCd = getSeihinCdByShohinCd(shohinCd, kaisyaCd, ktksyCd);

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrSeihinRecord zRec = rUtil.getRecMastrSeihin(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, seihinCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			// ＊該当なし＊
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				if (!PbsUtil.isEmpty(zRec.getCaseIrisu())) {
					ret = zRec.getCaseIrisu();
				}
			// 利用不可
			} else {
				// ＊利用不可＊
			}
		}

		return ret;
	}

	/**
	 * 商品CDから出荷対応を取得する。
	 *
	 * @param shohinCd 商品CD
	 * @param kaisyaCd 会社CD
	 * @param ktksyCd 寄託者CD
	 * @return 出荷対応
	 */
	protected String getSyukaTaioKbnByShohinCd(String shohinCd, String kaisyaCd, String ktksyCd) {
		String ret = NO_VALUE;

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrSyohinRecord zRec = rUtil.getRecMastrSyohin(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, shohinCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			// ＊該当なし＊
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				if (!PbsUtil.isEmpty(zRec.getSyukaTaioKbn())) {
					ret = zRec.getSyukaTaioKbn();
				}
			// 利用不可
			} else {
				// ＊利用不可＊
			}
		}

		return ret;
	}

	/**
	 * 商品CDから扱い区分を取得する。
	 *
	 * @param shohinCd 商品CD
	 * @param kaisyaCd 会社CD
	 * @param ktksyCd 寄託者CD
	 * @return 扱い区分
	 */
	protected String getAtukaiKbnByShohinCd(String shohinCd, String kaisyaCd, String ktksyCd) {
		String ret = NO_VALUE;

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrSyohinRecord zRec = rUtil.getRecMastrSyohin(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, shohinCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			// ＊該当なし＊
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				if (!PbsUtil.isEmpty(zRec.getAtukaiKbn())) {
					ret = zRec.getAtukaiKbn();
				}
			// 利用不可
			} else {
				// ＊利用不可＊
			}
		}

		return ret;
	}

	/**
	 * 商品CDから販売単価を取得する。
	 *
	 * @param shohinCd 商品CD
	 * @param kaisyaCd 会社CD
	 * @param ktksyCd 寄託者CD
	 * @return 販売単価
	 */
	protected String getHanbaiTankaByShohinCd(String shohinCd, String kaisyaCd, String ktksyCd) {
		String ret = TNK_ZERO;

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrSyohinRecord zRec = rUtil.getRecMastrSyohin(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, shohinCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			// ＊該当なし＊
		} else {
			// 利用可
			if (AVAILABLE_KB_RIYO_KA.equals(zRec.getRiyouKbn())) {
				if (!PbsUtil.isEmpty(zRec.getHkakakuSeisansya())) {
					ret = zRec.getHkakakuSeisansya();
				}
			// 利用不可
			} else {
				// ＊利用不可＊
			}
		}

		return ret;
	}

	/**
	 * 商品CDと蔵CDと出荷日から在庫残数（当日残：箱数、セット数、総バラ数、取得日時）を取得する。
	 *
	 * @param shohinCd 商品CD
	 * @param kuraCd 蔵CD
	 * @param syukaDt 出荷日
	 * @param kaisyaCd 会社CD
	 * @param ktksyCd 寄託者CD
	 * @return 在庫残数（当日残）_箱数,セット数,総バラ数,取得日時（YYYYMMDDHHMMSS）
	 */
	protected String getZaikoZansu(String shohinCd, String kuraCd, String syukaDt, String kaisyaCd, String ktksyCd) {
		// 初期化
		String execDtTm = PbsUtil.getCurrentDateTime(true); // 実行日時
		String caseIriSu = SU_ZERO; // ケース入数
		String caseSu = SU_ZERO; // 残予定 ケース数
		String baraSu = SU_ZERO; // 残予定 バラ数
		String soBaraSu = SU_ZERO; // 総バラ数

		String zeroStr = caseSu + "," + baraSu + "," + soBaraSu + "," + execDtTm;
		String ret = zeroStr;

		// 商品CDから倉庫CDを取得
		String soukoCd = getSoukoCdByShohinCd(shohinCd, kaisyaCd, ktksyCd, kuraCd);

		// 在庫共通モジュールを使う
		ComCallZik_ZikSearchService callZik = new ComCallZik_ZikSearchService(getComUserSession(), db_, isTransaction(), getActionErrors());

		// ----------------
		// 検索条件をセット
		// ----------------
		KitSearchForm argForm = new KitSearchForm();
		// 在庫検索区分
		argForm.setZikSearchKbn(ZAIKO_KENSAKU_KB_YOTEI_ZAIKO); // 1:現在在庫 2:予定在庫
		// 検索種別区分
		argForm.setSearchSybtKbn(KENSAKU_SYUBETU_KB_SYOSAI); // 1:一覧 2:詳細
		// 在庫区分
		argForm.setZaikoKbn(ZAI_CATEGORY_KB_TUJO); // 1:通常品 2:戻入品 3:見本品
		// 会社コード
		argForm.setKaisyaCd(kaisyaCd);
		// 対象日付＝出荷日
		argForm.setZikDt(syukaDt); // 在庫情報を取得したい日付を指定（YYYYMMDD 形式）
		// 対象日取得期間＝出荷日
		argForm.setZikDtEnd(syukaDt); // 在庫情報を取得したい期間を指定（指定がない場合は最高3ヶ月後残まで取得）
		// 蔵コード
		argForm.setKuraCd(kuraCd);
		// 倉庫コード
		argForm.setSoukoCd(soukoCd);
		// 寄託者コード
		argForm.setKtksyCd(ktksyCd);

		// ------------------
		// 物品区分による分岐
		// ------------------
		// 商品CDから物品区分を取得
		String bupinKbn = getBupinKbnByShohinCd(shohinCd, kaisyaCd, ktksyCd);

		// 製品在庫
		if (ComUtil.isSeihin(bupinKbn)) {
			// 商品CDから製品CDを取得
			String seihinCd = getSeihinCdByShohinCd(shohinCd, kaisyaCd, ktksyCd);

			// 在庫種別区分
			argForm.setZikSybtKbn(ZAIKO_SYUBETU_KB_SEIHIN); // SZ:製品在庫 SS:製品資材在庫 JR:醸造原料米在庫 JL:醸造酒粕在庫
			// 製品コード
			argForm.setSeihinCd(seihinCd);

		// 製品資材在庫
		} else if (ComUtil.isBupin(bupinKbn)) {
			// 商品CDから仕入先CD・仕入品CDを取得
			String siiresakiCd = getSiiresakiCdByShohinCd(shohinCd, kaisyaCd, ktksyCd);
			String siirehinCd = getSiirehinCdByShohinCd(shohinCd, kaisyaCd, ktksyCd);

			// 在庫種別区分
			argForm.setZikSybtKbn(ZAIKO_SYUBETU_KB_SIZAI); // SZ:製品在庫 SS:製品資材在庫 JR:醸造原料米在庫 JL:醸造酒粕在庫
			// 仕入先コード
			argForm.setSiiresakiCd(siiresakiCd);
			// 仕入品コード
			argForm.setSiirehinCd(siirehinCd);
		}

		PbsRecord[] zikRecords = null;

		try {
			// 在庫数取得
			callZik.execute(argForm);
			zikRecords = callZik.getZikRecs();

			if (zikRecords != null) {
				for (PbsRecord rec : zikRecords) {
					execDtTm = rec.getString("ACTION_TIME"); // 実行日時
					caseIriSu = rec.getString("CASE_IRISU"); // ケース入数
					caseSu = rec.getString("ZAN_YOTEI_CASE"); // 残予定 ケース数
					baraSu = rec.getString("ZAN_YOTEI_BARA"); // 残予定 バラ数
					soBaraSu = ComUtil.sobaraKeisan(caseSu, baraSu, caseIriSu); // 総バラ数

					ret = caseSu + "," + baraSu + "," + soBaraSu + "," + execDtTm;
				}
			}
		} catch (Exception e) {
			ret = zeroStr;
//			e.printStackTrace(); // デバッグ用
		} finally {
			db_.close();
		}

		// XXX:【テスト用】在庫数
		boolean debug = false;
		// ------------------------------------------------------------デバッグ用
		if (debug) {
			if (PbsUtil.isEmpty(shohinCd)) {
				ret = zeroStr;
			} else {
				ret = "99999,999,999999999," + execDtTm;
			}
		}
		// ------------------------------------------------------------デバッグ用

		return ret;
	}

	/**
	 * 商品CDから製品日付を取得する。
	 *
	 * @param shohinCd 商品CD
	 * @param kaisyaCd 会社CD
	 * @param ktksyCd 寄託者CD
	 * @return 製品日付
	 */
	protected String getSeihinDtByShohinCd(String shohinCd, String kaisyaCd, String ktksyCd) {
		String ret = CHAR_BLANK;

		// 現在未使用のため固定値

		return ret;
	}

	/**
	 * 商品CDから倉庫CDを取得する。
	 *
	 * @param shohinCd 商品CD
	 * @param kaisyaCd 会社CD
	 * @param ktksyCd 寄託者CD
	 * @param kuraCd 蔵CD
	 * @return 倉庫CD
	 */
	protected String getSoukoCdByShohinCd(String shohinCd, String kaisyaCd, String ktksyCd, String kuraCd) {
		String ret = EMPTY_SOUKO_CD;

		// 現在未使用のため固定値

		return ret;
	}

	/**
	 * 商品CDと販売単価（入力値）から販売単価変更フラグを取得する。
	 *
	 * @param shohinCd 商品CD
	 * @param hanbaiTanka 販売単価（入力値）
	 * @param kaisyaCd 会社コード
	 * @param ktksyCd 寄託者コード
	 * @return 0:単価変更なし / 1:単価変更あり
	 */
	protected String getHtankaChgFlg(String shohinCd, String hanbaiTanka, String kaisyaCd, String ktksyCd) {
		String res = "";

		// 存在チェック
		FbMastrSyohinRecord zRec = rUtil.getRecMastrSyohin(IS_DELETED_HELD_FALSE, kaisyaCd, ktksyCd, shohinCd);
		if (vUtil.validateMstExistence(zRec, "", IS_MESSAGE_OUTPUT_FALSE)) {
			// 販売単価のマスター値と入力値を比較
			if (hanbaiTanka.equals(zRec.getHkakakuSeisansya())) {
				res = JDATAKIND_KB_TUJO; // 単価変更なし
			} else {
				res = JDATAKIND_KB_TANKA_TIGAI; // 単価変更あり
			}
		}

		return res;
	}

	/**
	 * データ種別コードを取得する。
	 *
	 * @param mode モード
	 * @param editDtList ディテール部編集リスト
	 * @param kaisyaCd 会社コード
	 * @param ktksyCd 寄託者コード
	 * @param doSetStyleClass スタイルクラス設定フラグ
	 * @return 0:通常 / 1:単価違 / 9:抹消
	 */
	public String getDataSyubetuCd(String mode, JuchuJuchuAddOnlyDtList editDtList, String kaisyaCd, String ktksyCd, boolean doSetStyleClass) {
		String ret = "";

		if (MODE_DELETE.equals(mode)) {
			ret = JDATAKIND_KB_JYUCYU_CANCEL; // 抹消
		} else {
			if (hasXDiffHtanka(editDtList, kaisyaCd, ktksyCd, doSetStyleClass)) {
				ret = JDATAKIND_KB_TANKA_TIGAI; // 単価違
			} else {
				ret = JDATAKIND_KB_TUJO; // 通常
			}
		}

		return ret;
	}


// ==============================
// ====    その他の値設定    ====
// ==============================

	/**
	 * 自動採番した受注NOを設定する。
	 *
	 * @param editList
	 * @param symbol 管轄事業所キー
	 * @param online オンライン受信時間
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean setNextJyucyuNo(JuchuJuchuAddOnlyList editList, String symbol, String online) {
		JuchuJuchuAddOnlyRecord rec = (JuchuJuchuAddOnlyRecord) editList.getFirstRecord();

		// 既に受注No.がある場合はそのまま使う
		if (PbsUtil.isEmpty(rec.getJyucyuNo())) {
			String nextJyucyuNo = getNextJyucyuNo(symbol, online); // 自動採番した受注NO

			// 受注NOが無い場合はエラー
			if (PbsUtil.isEmpty(nextJyucyuNo)) {
				// エラーメッセージを出力
				setErrorMessageId("errors.juchuNo.fail");
				rec.setJyucyuNoClass(STYLE_CLASS_ERROR);
				return false;
			}

			rec.setJyucyuNo(nextJyucyuNo); // 受注NOセット
			rec.setJyucyuNoFormatted(formatJyucyuNo(nextJyucyuNo)); // 整形済み受注NOセット
		}

		return true;
	}

	/**
	 * 自動採番した受注NOを取得する。
	 *
	 * @param symbol 管轄事業所キー
	 * @param online オンライン受信時間
	 * @return 受注NO（採番エラー時は空白）
	 */
	public String getNextJyucyuNo(String symbol, String online) {
		String nextJyucyuNo = "";

		JuchuJuchuAddOnly_SearchService searchServ = new JuchuJuchuAddOnly_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		try {
			for (;;) {
				// 次の番号を取得
				nextJyucyuNo = ComJuchuRnbnKanri.nextNumber(getDatabase(), kaisyaCd, symbol, online);

    			// 既にあったら初期化してリトライ、なければ採番終了して戻る
    			if (searchServ.isExistJuchuNo(kaisyaCd, nextJyucyuNo)) {
    				nextJyucyuNo = CHAR_BLANK;
    			} else {
    				break;
    			}
			}
		} catch (Exception e) {
			nextJyucyuNo = CHAR_BLANK; // 採番エラー
		}

		// XXX:【テスト用】自動採番
		boolean debug = false;
		// -------------------------------------------------------------------------------------デバッグ用
		if (debug) {
			JuchuJuchuAddOnly_NumberClient numberClient = JuchuJuchuAddOnly_NumberClient.getInstance();
			nextJyucyuNo = numberClient.getNextJyucyuNoForTest(getDatabase(), "X", "0"); // 事業所：X、手入力
		}
		// -------------------------------------------------------------------------------------デバッグ用

		return nextJyucyuNo;
	}

	/**
	 * 枝番をインクリメントした受注NOを設定する。
	 *
	 * @param editList
	 * @param baseJyucyuNo ベースとなる受注NO（例：T0123450）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean setIncrementedJyucyuNo(JuchuJuchuAddOnlyList editList, String baseJyucyuNo) {
		JuchuJuchuAddOnlyRecord rec = (JuchuJuchuAddOnlyRecord) editList.getFirstRecord();

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		// 既に受注No.がある場合はそのまま使う
		if (PbsUtil.isEmpty(rec.getJyucyuNo())) {
			// 採番クライアント
			JuchuJuchuAddOnly_NumberClient numberClient = JuchuJuchuAddOnly_NumberClient.getInstance();

			String nextJyucyuNo = numberClient.getIncrementedJyucyuNo(kaisyaCd, baseJyucyuNo); // 受注NO取得

			// 枝番が最大値に達していたらエラー
			if (nextJyucyuNo.matches(".*_$")) { // エラー判別用文字列："_"
				// エラーメッセージを出力
				setErrorMessageId("errors.juchuNoEdaban.fail");
				rec.setJyucyuNoClass(STYLE_CLASS_ERROR);
				return false;
			// 受注NOが無い場合はエラー
			} else if (PbsUtil.isEmpty(nextJyucyuNo)) {
				// エラーメッセージを出力
				setErrorMessageId("errors.juchuNo.fail");
				rec.setJyucyuNoClass(STYLE_CLASS_ERROR);
				return false;
			}

			rec.setJyucyuNo(nextJyucyuNo); // 受注NOセット
			rec.setJyucyuNoFormatted(formatJyucyuNo(nextJyucyuNo)); // 整形済み受注NOセット
		}

		return true;
	}

	/**
	 * 受注NOをフォーマットする。
	 *
	 * @param jyucyuNo 元の受注NO（例：T0123450）
	 * @return フォーマットした受注NO（例：T0-12345-0）
	 */
	public String formatJyucyuNo(String jyucyuNo) {
		return ComUtil.getFormattedJuchuNo(jyucyuNo);
	}

	/**
	 * ディテール部の受注行NOと特注指示情報（特注指示区分、PB OR 特注指示内容）を設定する。
	 *
	 * @param editList ヘッダー部リスト
	 * @param editDtList ディテール部リスト
	 */
	private void setDtListInfo(JuchuJuchuAddOnlyList editList, JuchuJuchuAddOnlyDtList editDtList) {
		// ヘッダー部レコード
		JuchuJuchuAddOnlyRecord hdRec = (JuchuJuchuAddOnlyRecord) editList.getFirstRecord();

		// [DT]受注行NO
		editDtList.setInputLineNos();

		// 注意商品リストを取得
		MastrTatesenOrositen_SearchService mstSearchServ = new MastrTatesenOrositen_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		MastrOrositenSyosaiDtList cautionList = mstSearchServ.getListMastrOrositenSyosaiDt(hdRec.getOrsTatesnCd());

		// [DT]特注指示情報（特注指示区分、PB OR 特注指示内容）
		editDtList.setCautionInfos(cautionList);
	}

	/**
	 * その他の値を設定する。
	 *
	 * @param editForm 編集フォーム
	 * @param editList ヘッダー部リスト
	 * @param editDtList ディテール部リスト
	 * @param tatesenRec 縦線卸店情報
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean setOthers(JuchuJuchuAddOnly_EditForm editForm, JuchuJuchuAddOnlyList editList, JuchuJuchuAddOnlyDtList editDtList
			, MastrTatesenOrositenRecord tatesenRec) {
		// ----------------------
		// ユーザーセッション情報
		// ----------------------
		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD
		String ktksyCd = cus.getKtksyCd(); // 寄託者CD

		// ----------
		// ヘッダー部
		// ----------
		String syukaSuryoBox = SU_ZERO; // [HD]出荷数量計_箱数
		String syukaSuryoSet = SU_ZERO; // [HD]出荷数量計_セット数
		String syukaKingakuTot = SU_ZERO; // [HD]出荷金額計
		String jyuryoTot = SU_ZERO; // [HD]重量計(KG)
		String syukaYouryoTot = SU_ZERO; // [HD]容量計（L）_出荷容量計
		String rebate1YouryoTot = SU_ZERO; // [HD]容量計（L）_リベートⅠ類対象容量計
		String rebate2YouryoTot = SU_ZERO; // [HD]容量計（L）_リベートⅡ類対象容量計
		String rebate3YouryoTot = SU_ZERO; // [HD]容量計（L）_リベートⅢ類対象容量計
		String rebate4YouryoTot = SU_ZERO; // [HD]容量計（L）_リベートⅣ類対象容量計
		String rebate5YouryoTot = SU_ZERO; // [HD]容量計（L）_リベートⅤ類対象容量計
		String rebateoYouryoTot = SU_ZERO; // [HD]容量計（L）_リベート対象外容量計
		String jyucyuKanouKingakuKei = SU_ZERO; // 受注可能金額計

		// ------------
		// ディテール部
		// ------------
		String unchinKakeritu = SU_ZERO; // [DT]運賃掛率(%)
		String hanbaiTanka = SU_ZERO; // [DT]販売単価
		String syukaHanbaiKingaku = SU_ZERO; // [DT]販売額
		String caseIrisu = SU_ZERO; // [DT]ケース入数
		String baraYouryo = SU_ZERO; // [DT]バラ容量(L)
		String syukaSuCase = SU_ZERO; // [DT]出荷数量_箱数
		String syukaSuBara = SU_ZERO; // [DT]出荷数量_セット数
		String syukaAllBara = SU_ZERO; // [DT]出荷数量_換算総セット数
		String miiriWeightBox = SU_ZERO; // [DT]身入重量(KG)_箱
		String miiriWeightBara = SU_ZERO; // [DT]身入重量(KG)_バラ
		String syukaAllWeigth = SU_ZERO; // [DT]出荷重量（KG)
		String syukaSouyouryo = SU_ZERO; // [DT]容量（L）_出荷総容量
		String rebate1Youryo = SU_ZERO; // [DT]リベート対象容量（L)_Ⅰ類
		String rebate2Youryo = SU_ZERO; // [DT]リベート対象容量（L)_Ⅱ類
		String rebate3Youryo = SU_ZERO; // [DT]リベート対象容量（L)_Ⅲ類
		String rebate4Youryo = SU_ZERO; // [DT]リベート対象容量（L)_Ⅳ類
		String rebate5Youryo = SU_ZERO; // [DT]リベート対象容量（L)_Ⅴ類
		String rebate1Souyouryo = SU_ZERO; // [DT]容量（L）_リベートⅠ類対象総容量
		String rebate2Souyouryo = SU_ZERO; // [DT]容量（L）_リベートⅡ類対象総容量
		String rebate3Souyouryo = SU_ZERO; // [DT]容量（L）_リベートⅢ類対象総容量
		String rebate4Souyouryo = SU_ZERO; // [DT]容量（L）_リベートⅣ類対象総容量
		String rebate5Souyouryo = SU_ZERO; // [DT]容量（L）_リベートⅤ類対象総容量
		String rebateoSouyouryo = SU_ZERO; // [DT]容量（L）_リベート対象外総容量

		// ヘッダー部レコード
		JuchuJuchuAddOnlyRecord hdRec = (JuchuJuchuAddOnlyRecord) editList.getFirstRecord();

		// ------------
		// 縦線情報更新
		// ------------

		// 倉庫種類区分
		hdRec.setSoukoSyuruiKbn(tatesenRec.getSoukoSyuruiKbn());

		// 出荷先区分
		// 【課税倉庫】倉庫種類区分＝課税倉庫 or 自社倉庫(課税)　かつ　国際エリア区分＝日本の場合
		if (PbsUtil.isIncluded(tatesenRec.getSoukoSyuruiKbn(), SOUKO_SYURUI_KB_KAZEI, SOUKO_SYURUI_KB_JISYA_KAZEI)
				&& PbsUtil.isEqual(tatesenRec.getWorldAreaKbn(), WORLD_AREA_KB_NIHON)) {
			hdRec.setSyukaKbn(SHIPMENT_KB_KAZEI);

		// 【未納税倉庫】倉庫種類区分＝未納税倉庫 or 自社倉庫(未納税)　かつ　国際エリア区分＝日本の場合
		} else if (PbsUtil.isIncluded(tatesenRec.getSoukoSyuruiKbn(), SOUKO_SYURUI_KB_MINOZEI, SOUKO_SYURUI_KB_JISYA_MINOZEI)
				&& PbsUtil.isEqual(tatesenRec.getWorldAreaKbn(), WORLD_AREA_KB_NIHON)) {
			hdRec.setSyukaKbn(SHIPMENT_KB_MINOZEI);

		// 【未納税（輸出）】倉庫種類区分＝未納税倉庫　かつ　国際エリア区分≠日本の場合
		} else if (PbsUtil.isIncluded(tatesenRec.getSoukoSyuruiKbn(), SOUKO_SYURUI_KB_MINOZEI)
				&& !PbsUtil.isEqual(tatesenRec.getWorldAreaKbn(), WORLD_AREA_KB_NIHON)) {
			hdRec.setSyukaKbn(SHIPMENT_KB_YUSYUTU);

		// 【課税倉庫】上記以外
		} else {
			hdRec.setSyukaKbn(SHIPMENT_KB_KAZEI);
		}

		// 倉入・直送区分
		// 酒販店入力なしの場合、卸店詳細マスター設定値（縦線卸店情報）を使う
		if (PbsUtil.isEmpty(hdRec.getSyuhantenCd())) {
			hdRec.setDeliveryKbn(tatesenRec.getDeliveryKbn());
		// 酒販店入力ありの場合、酒販店直送とする
		} else {
			hdRec.setDeliveryKbn(DELIVERY2_KB_SYUHANTEN_CHOKUSO);
		}

		// 担当者CD
		hdRec.setTantosyaCd(tatesenRec.getTantosyaCd());

		// 特約店CD
		hdRec.setTokuyakutenCd(tatesenRec.getOrositenCd1Jiten());

		// デポCD
		hdRec.setDepoCd(tatesenRec.getOrositenCdDepo());

		// 二次店CD
		hdRec.setNijitenCd(tatesenRec.getOrositenCd2Jiten());

		// 三次店CD
		hdRec.setSanjitenCd(tatesenRec.getOrositenCd3Jiten());

		// 出荷先国CD
		hdRec.setSyukaSakiCountryCd(tatesenRec.getSyukaSakiCountryCd());

		// JISCD
		hdRec.setJisCd(tatesenRec.getJisCd());

		// 引取運賃換算単価（着払の場合）
		if (PbsUtil.isIncluded(hdRec.getUnchinKbn(), UNTIN_SEQ_KB_CHAKU_L, UNTIN_SEQ_KB_CHAKU_KG)) {
			hdRec.setUnchinCnvTanka(tatesenRec.getUnchinCnvTanka());
		} else {
			hdRec.setUnchinCnvTanka(SU_ZERO);
		}

		// 最低配送ロット数
		hdRec.setLowHaisoLot(tatesenRec.getLowHaisoLot());

		// 最終送荷先卸CD
		hdRec.setOrositenCdLast(tatesenRec.getOrositenCdLast());

		// 最終送荷先卸名
		hdRec.setOrositenNmLast(tatesenRec.getOrositenNmLast());

		// 担当者名
		hdRec.setTantosyaNm(tatesenRec.getTantosyaNm());

		// 特約店名
		hdRec.setTokuyakutenNm(tatesenRec.getOrositenNm1Jiten());

		// デポ名
		hdRec.setDepoNm(tatesenRec.getOrositenNmDepo());

		// 二次店名
		hdRec.setNijitenNm(tatesenRec.getOrositenNm2Jiten());

		// 三次店名
		hdRec.setSanjitenNm(tatesenRec.getOrositenNm3Jiten());

		// 当日出荷可能区分
		hdRec.setToujituSyukkaKbn(tatesenRec.getToujituSyukkaKbn());

		// 出荷日注意区分
		hdRec.setSyukaDtCyuiKbn(tatesenRec.getSyukaDtCyuiKbn());

		// 荷受不可曜日_月
		hdRec.setNiukeFukaMon(tatesenRec.getNiukeFukaMon());

		// 荷受不可曜日_火
		hdRec.setNiukeFukaTue(tatesenRec.getNiukeFukaTue());

		// 荷受不可曜日_水
		hdRec.setNiukeFukaWed(tatesenRec.getNiukeFukaWed());

		// 荷受不可曜日_木
		hdRec.setNiukeFukaThu(tatesenRec.getNiukeFukaThu());

		// 荷受不可曜日_金
		hdRec.setNiukeFukaFri(tatesenRec.getNiukeFukaFri());

		// 荷受不可曜日_土
		hdRec.setNiukeFukaSat(tatesenRec.getNiukeFukaSat());

		// 荷受不可曜日_日
		hdRec.setNiukeFukaSun(tatesenRec.getNiukeFukaSun());

		// 荷受不可曜日_祝
		hdRec.setNiukeFukaHol(tatesenRec.getNiukeFukaHol());

		// 請求明細グループCD_特約店CD
		hdRec.setSmgpTokuyakutenCd(tatesenRec.getTokuyakutenCdSmgp());

		// 与信要注意区分
		hdRec.setYosinCyuiKbn(tatesenRec.getYosinCyuiKbn());

		// 与信限度額
		hdRec.setYosinGendoGaku(tatesenRec.getYosinGendoGaku());

		// 売掛未入金額
		hdRec.setUrikakeMinyukinGaku(tatesenRec.getUrikakeMinyukinGaku());

		// --------------
		// 特約店マスター
		// --------------
		// 特約店マスター検索条件設定
		MastrSeikyusaki_SearchForm sqSearchForm = new MastrSeikyusaki_SearchForm();
		sqSearchForm.setTokuyakutenCd(hdRec.getSmgpTokuyakutenCd()); // 請求明細グループCD_特約店CD

		// 特約店マスター検索
		MastrSeikyusaki_SearchService sqSearchServ = new MastrSeikyusaki_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		PbsRecord[] records = null;
		MastrSeikyusakiList sqList = null;
		MastrSeikyusakiRecord sqRecord = new MastrSeikyusakiRecord();

		try {
			// 検索実行
			records = sqSearchServ.execute(sqSearchForm);

			// 検索結果をリストクラスに変換
			sqList = new MastrSeikyusakiList(records);

			// 検索結果レコード
			sqRecord = (MastrSeikyusakiRecord) sqList.getFirstRecord();
		} catch (DataNotFoundException e) {
		}

		// 受注可能金額計
		jyucyuKanouKingakuKei = sqSearchServ.getJyucyuKanouKingakuKei(sqRecord);
		hdRec.setJyucyuKanouKingakuKei(jyucyuKanouKingakuKei);

		// ディテール部リスト
		for (int i = 0; i < editDtList.size(); i++) {

			// ディテール部レコード
			JuchuJuchuAddOnlyDtRecord dtRec = (JuchuJuchuAddOnlyDtRecord) editDtList.get(i);

			// 未入力行はスキップ
			if (dtRec.isEmpty()) {
				continue;
			}

			// [HD]利用区分→[DT]利用区分
			dtRec.setRiyouKbn(hdRec.getRiyouKbn());

			// [HD]受注NO→[DT]受注NO
			dtRec.setJyucyuNo(hdRec.getJyucyuNo());

			// [HD]蔵CD→[DT]蔵CD
			dtRec.setKuraCd(hdRec.getKuraCd());

			// [DT]販売単価変更フラグ
			hanbaiTanka = dtRec.getHanbaiTanka(); // 販売単価
			dtRec.setHtankaChgFlg(getHtankaChgFlg(dtRec.getShohinCd(), hanbaiTanka, kaisyaCd, ktksyCd));

			// [DT]出荷数量_箱数
			syukaSuCase = dtRec.getSyukaSuCase();

			// [HD]出荷数量計_箱数＝[DT]出荷数量_箱数の合計
			syukaSuryoBox = PbsUtil.sToSAddition(syukaSuryoBox, syukaSuCase);

			// [DT]出荷数量_セット数
			syukaSuBara = dtRec.getSyukaSuBara();

			// [HD]出荷数量計_セット数＝[DT]出荷数量_セット数の合計
			syukaSuryoSet = PbsUtil.sToSAddition(syukaSuryoSet, syukaSuBara);

			// [DT]EDI配送依頼(集約)送信区分
			dtRec.setEdiHaisougSendKb(YN_12_KB_MISOSIN); // 未送信

			// ------------
			// 商品マスター
			// ------------
			FbMastrSyohinRecord zRecSyohin = rUtil.getRecMastrSyohin(IS_DELETED_HELD_FALSE, kaisyaCd, ktksyCd, dtRec.getShohinCd());
			if (!vUtil.validateMstExistence(zRecSyohin, "", IS_MESSAGE_OUTPUT_FALSE)) {
				setErrorMessageId("errors.data.ng",
						PbsUtil.getMessageResourceString("com.text.syohnMst")
						+ PbsUtil.getMessageResourceString("com.text.leftKakko")
						+ PbsUtil.getMessageResourceString("com.text.syohinCd")
						+ PbsUtil.getMessageResourceString("com.text.colon")
						+ dtRec.getShohinCd()
						+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
				return false;
			} else {
				// [DT]倉庫CD
				dtRec.setSoukoCd(getSoukoCdByShohinCd(dtRec.getShohinCd(), kaisyaCd, ktksyCd, hdRec.getKuraCd()));

				// [DT]製品CD
				dtRec.setSeihinCd(zRecSyohin.getSeihinCd());

				// [DT]仕入先CD
				dtRec.setSiiresakiCd(zRecSyohin.getSiiresakiCd());

				// [DT]仕入品CD
				dtRec.setSiirehinCd(zRecSyohin.getSiirehinCd());

				// [DT]製品日付
				dtRec.setSeihinDt(getSeihinDtByShohinCd(dtRec.getShohinCd(), kaisyaCd, ktksyCd));

				// [DT]積荷伝票用ラインNO
				dtRec.setTumidenLineNo(zRecSyohin.getTumidenLineNo());

				// [DT]重量(KG)_ケース
				miiriWeightBox = zRecSyohin.getMiiriWeightBox(); // 身入重量(KG)_箱
				if (PbsUtil.isEmpty(miiriWeightBox)) {
					miiriWeightBox = SU_ZERO;
				}
				dtRec.setWeightCase(miiriWeightBox);

				// [DT]重量(KG)_バラ
				miiriWeightBara = zRecSyohin.getMiiriWeightBara(); // 身入重量(KG)_バラ
				if (PbsUtil.isEmpty(miiriWeightBara)) {
					miiriWeightBara = SU_ZERO;
				}
				dtRec.setWeightBara(miiriWeightBara);

				// [DT]出荷重量（KG)＝[出荷数量_箱数]x[身入重量(KG)_箱]+[出荷数量_セット数]x[身入重量(KG)_バラ]
				syukaAllWeigth = ComUtil.calcJyuryo(syukaSuCase, miiriWeightBox, syukaSuBara, miiriWeightBara);

				// 小数点第4位で四捨五入
				dtRec.setSyukaAllWeigth(
						(PbsUtil.getZeroSuppress(((new BigDecimal(syukaAllWeigth)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

				// [HD]重量計(KG)＝[DT]出荷重量（KG)の合計
				jyuryoTot = PbsUtil.sToSAddition(jyuryoTot, syukaAllWeigth);

				// [DT]運賃掛率(%)
				unchinKakeritu = zRecSyohin.getUnchinKeigenRate();
				if (PbsUtil.isEmpty(unchinKakeritu)) {
					unchinKakeritu = SU_ZERO;
				}
				dtRec.setUnchinKakeritu(unchinKakeritu);

				// [DT]出荷対応区分
				dtRec.setSyukaTaioKbn(zRecSyohin.getSyukaTaioKbn());

				// [DT]扱い区分
				dtRec.setAtukaiKbn(zRecSyohin.getAtukaiKbn());

				// [DT]物品区分
				dtRec.setBupinKbn(zRecSyohin.getBupinKbn());

				// [DT]補償金徴収対象区分
				dtRec.setIndemnityKbn(zRecSyohin.getIndemnityKbn());

				// [DT]引上手数料対象区分
				dtRec.setServiceKbn(zRecSyohin.getServiceKbn());

				// ------------------
				// 物品区分による分岐
				// ------------------
				// 製品の場合
				if (ComUtil.isSeihin(zRecSyohin.getBupinKbn())) {
					// ------------
					// 製品マスター
					// ------------
					FbMastrSeihinRecord zRecSeihin = rUtil.getRecMastrSeihin(IS_DELETED_HELD_FALSE, kaisyaCd, ktksyCd, zRecSyohin.getSeihinCd());
					if (!vUtil.validateMstExistence(zRecSeihin, "", IS_MESSAGE_OUTPUT_FALSE)) {
						setErrorMessageId("errors.data.ng",
								PbsUtil.getMessageResourceString("com.text.seihinMst")
								+ PbsUtil.getMessageResourceString("com.text.leftKakko")
								+ PbsUtil.getMessageResourceString("com.text.seihinCd")
								+ PbsUtil.getMessageResourceString("com.text.colon")
								+ zRecSyohin.getSeihinCd()
								+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
						return false;
					} else {

						// --------------
						// 種区分マスター
						// --------------
						FbMastrKbnTaneRecord zRecTane = rUtil.getRecMastrKbnTane(IS_DELETED_HELD_FALSE, kaisyaCd, zRecSeihin.getTaneCd());
						if (!vUtil.validateMstExistence(zRecTane, "", IS_MESSAGE_OUTPUT_FALSE)) {
							setErrorMessageId("errors.data.ng",
									PbsUtil.getMessageResourceString("com.text.taneKbMst")
									+ PbsUtil.getMessageResourceString("com.text.leftKakko")
									+ PbsUtil.getMessageResourceString("com.text.taneCd")
									+ PbsUtil.getMessageResourceString("com.text.colon")
									+ zRecSeihin.getTaneCd()
									+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
							return false;
						} else {
							// 販売分類・販売種別・販売部門の情報をセット
							this.setHanbaiInfo(dtRec, zRecTane.getHanbaiBunruiCd());
						}

						// [DT]ケース入数
						caseIrisu = zRecSeihin.getCaseIrisu(); // ケース入数
						if (PbsUtil.isEmpty(caseIrisu)) {
							caseIrisu = SU_ZERO;
						}
						dtRec.setCaseIrisu(caseIrisu);

						// [DT]バラ容量(L)＝[単品総容量（L)@1バラ当り]
						baraYouryo = zRecSeihin.getTnpnVol(); // 単品総容量（L)@1バラ当り
						if (PbsUtil.isEmpty(baraYouryo)) {
							baraYouryo = SU_ZERO;
						}
						dtRec.setBaraYouryo(baraYouryo);
					}

				// 物品の場合
				} else if (ComUtil.isBupin(zRecSyohin.getBupinKbn())) {
					// ------------------
					// 仕入品管理マスター
					// ------------------
					FbMastrSiirehinRecord zRecSiirehin = rUtil.getRecMastrSiirehinRecord(IS_DELETED_HELD_FALSE, kaisyaCd, ktksyCd, zRecSyohin.getSiiresakiCd(), zRecSyohin.getSiirehinCd());
					if (!vUtil.validateMstExistence(zRecSiirehin, "", IS_MESSAGE_OUTPUT_FALSE)) {
						setErrorMessageId("errors.data.ng",
								PbsUtil.getMessageResourceString("com.text.siirehinMst")
								+ PbsUtil.getMessageResourceString("com.text.leftKakko")
								+ PbsUtil.getMessageResourceString("com.text.siiresakiCd")
								+ PbsUtil.getMessageResourceString("com.text.colon")
								+ zRecSyohin.getSiiresakiCd()
								+ PbsUtil.getMessageResourceString("com.text.rightKakko")
								+ PbsUtil.getMessageResourceString("com.text.leftKakko")
								+ PbsUtil.getMessageResourceString("com.text.siirehinCd")
								+ PbsUtil.getMessageResourceString("com.text.colon")
								+ zRecSyohin.getSiirehinCd()
								+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
						return false;
					} else {
						// 販売分類・販売種別・販売部門の情報をセット
						this.setHanbaiInfo(dtRec, zRecSiirehin.getHanbaiBunruiCd());
					}

					// [DT]ケース入数
					// ケース
					if (PbsUtil.isEqual(zRecSiirehin.getHacyuLotTani(), LOT_TANI_KB_CASE)) {
						caseIrisu = zRecSiirehin.getCaseIrisu(); // ケース入数

					// バラ
					} else if (PbsUtil.isEqual(zRecSiirehin.getHacyuLotTani(), LOT_TANI_KB_BARA)) {
						caseIrisu = "1"; // 固定値：1

					// ブロック
					} else if (PbsUtil.isEqual(zRecSiirehin.getHacyuLotTani(), LOT_TANI_KB_BLOCK)) {
						caseIrisu = zRecSiirehin.getBlockIrisu(); // ブロック入数

					}
					if (PbsUtil.isEmpty(caseIrisu)) {
						caseIrisu = SU_ZERO;
					}
					dtRec.setCaseIrisu(caseIrisu);

					// [DT]バラ容量(L)
					baraYouryo = SU_ZERO; // 固定値：0
					dtRec.setBaraYouryo(baraYouryo);

				// その他の場合
				} else {
					// [DT]ケース入数
					caseIrisu = "1"; // 固定値：1
					dtRec.setCaseIrisu(caseIrisu);

					// [DT]バラ容量(L)
					baraYouryo = SU_ZERO; // 固定値：0
					dtRec.setBaraYouryo(baraYouryo);
				}

				// ------------------------
				// 物品区分によらない共通部
				// ------------------------
				// [DT]出荷数量_換算総セット数＝[ケース入数]x[出荷数量_箱数]+[出荷数量_セット数]
				syukaAllBara = ComUtil.sobaraKeisan(syukaSuCase, syukaSuBara, caseIrisu);
				// 小数点第1位で四捨五入
				dtRec.setSyukaAllBara(
						(PbsUtil.getZeroSuppress(((new BigDecimal(syukaAllBara)).setScale(0, BigDecimal.ROUND_HALF_UP)).toString())));

				// [DT]販売額＝[出荷数量_換算総セット数]x[販売単価]
				syukaHanbaiKingaku = PbsUtil.sToSMultiplication(syukaAllBara, hanbaiTanka);
				// 小数点第3位で四捨五入
				dtRec.setSyukaHanbaiKingaku(
						(PbsUtil.getZeroSuppress(((new BigDecimal(syukaHanbaiKingaku)).setScale(2, BigDecimal.ROUND_HALF_UP)).toString())));

				// [HD]出荷金額計＝[DT]販売額の合計
				syukaKingakuTot = PbsUtil.sToSAddition(syukaKingakuTot, syukaHanbaiKingaku);

				// [DT]容量（L）_出荷総容量＝[出荷数量_換算総セット数]x[DT]バラ容量(L)
				syukaSouyouryo = PbsUtil.sToSMultiplication(syukaAllBara, baraYouryo);
				// 小数点第4位で四捨五入
				dtRec.setSyukaSouyouryo(
						(PbsUtil.getZeroSuppress(((new BigDecimal(syukaSouyouryo)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

				// [HD]容量計（L）_出荷容量計＝[DT]容量（L）_出荷総容量の合計
				syukaYouryoTot = PbsUtil.sToSAddition(syukaYouryoTot, syukaSouyouryo);

				// [DT]容量（L）_リベートⅠ類対象総容量＝[出荷数量_換算総セット数]x[リベート対象容量（L)_Ⅰ類]
				rebate1Youryo = zRecSyohin.getRebate1Youryo(); // リベート対象容量（L)_Ⅰ類
				if (PbsUtil.isEmpty(rebate1Youryo)) {
					rebate1Youryo = SU_ZERO;
				}
				rebate1Souyouryo = PbsUtil.sToSMultiplication(syukaAllBara, rebate1Youryo);
				// 小数点第4位で四捨五入
				dtRec.setRebate1Souyouryo(
						(PbsUtil.getZeroSuppress(((new BigDecimal(rebate1Souyouryo)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

				// [HD]容量計（L）_リベートⅠ類対象容量計＝[DT]容量（L）_リベートⅠ類対象総容量の合計
				rebate1YouryoTot = PbsUtil.sToSAddition(rebate1YouryoTot, rebate1Souyouryo);

				// [DT]容量（L）_リベートⅡ類対象総容量＝[出荷数量_換算総セット数]x[リベート対象容量（L)_Ⅱ類]
				rebate2Youryo = zRecSyohin.getRebate2Youryo(); // リベート対象容量（L)_Ⅱ類
				if (PbsUtil.isEmpty(rebate2Youryo)) {
					rebate2Youryo = SU_ZERO;
				}
				rebate2Souyouryo = PbsUtil.sToSMultiplication(syukaAllBara, rebate2Youryo);
				// 小数点第4位で四捨五入
				dtRec.setRebate2Souyouryo(
						(PbsUtil.getZeroSuppress(((new BigDecimal(rebate2Souyouryo)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

				// [HD]容量計（L）_リベートⅡ類対象容量計＝[DT]容量（L）_リベートⅡ類対象総容量の合計
				rebate2YouryoTot = PbsUtil.sToSAddition(rebate2YouryoTot, rebate2Souyouryo);

				// [DT]容量（L）_リベートⅢ類対象総容量＝[出荷数量_換算総セット数]x[リベート対象容量（L)_Ⅲ類]
				rebate3Youryo = zRecSyohin.getRebate3Youryo(); // リベート対象容量（L)_Ⅲ類
				if (PbsUtil.isEmpty(rebate3Youryo)) {
					rebate3Youryo = SU_ZERO;
				}
				rebate3Souyouryo = PbsUtil.sToSMultiplication(syukaAllBara, rebate3Youryo);
				// 小数点第4位で四捨五入
				dtRec.setRebate3Souyouryo(
						(PbsUtil.getZeroSuppress(((new BigDecimal(rebate3Souyouryo)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

				// [HD]容量計（L）_リベートⅢ類対象容量計＝[DT]容量（L）_リベートⅢ類対象総容量の合計
				rebate3YouryoTot = PbsUtil.sToSAddition(rebate3YouryoTot, rebate3Souyouryo);

				// [DT]容量（L）_リベートⅣ類対象総容量＝[出荷数量_換算総セット数]x[リベート対象容量（L)_Ⅳ類]
				rebate4Youryo = zRecSyohin.getRebate4Youryo(); // リベート対象容量（L)_Ⅳ類
				if (PbsUtil.isEmpty(rebate4Youryo)) {
					rebate4Youryo = SU_ZERO;
				}
				rebate4Souyouryo = PbsUtil.sToSMultiplication(syukaAllBara, rebate4Youryo);
				// 小数点第4位で四捨五入
				dtRec.setRebate4Souyouryo(
						(PbsUtil.getZeroSuppress(((new BigDecimal(rebate4Souyouryo)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

				// [HD]容量計（L）_リベートⅣ類対象容量計＝[DT]容量（L）_リベートⅣ類対象総容量
				rebate4YouryoTot = PbsUtil.sToSAddition(rebate4YouryoTot, rebate4Souyouryo);

				// [DT]容量（L）_リベートⅤ類対象総容量＝[出荷数量_換算総セット数]x[リベート対象容量（L)_Ⅴ類]
				rebate5Youryo = zRecSyohin.getRebate5Youryo(); // リベート対象容量（L)_Ⅴ類
				if (PbsUtil.isEmpty(rebate5Youryo)) {
					rebate5Youryo = SU_ZERO;
				}
				rebate5Souyouryo = PbsUtil.sToSMultiplication(syukaAllBara, rebate5Youryo);
				// 小数点第4位で四捨五入
				dtRec.setRebate5Souyouryo(
						(PbsUtil.getZeroSuppress(((new BigDecimal(rebate5Souyouryo)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

				// [HD]容量計（L）_リベートⅤ類対象容量計＝[DT]容量（L）_リベートⅤ類対象総容量の合計
				rebate5YouryoTot = PbsUtil.sToSAddition(rebate5YouryoTot, rebate5Souyouryo);

				// [DT]容量（L）_リベート対象外総容量＝[出荷総容量]-[Ⅰ類計]-[Ⅱ類計]-[Ⅲ類計]-[Ⅳ類計]-[Ⅴ類計]
				rebateoSouyouryo = PbsUtil.sToSSubtraction(
						syukaSouyouryo, rebate1Souyouryo, rebate2Souyouryo, rebate3Souyouryo, rebate4Souyouryo, rebate5Souyouryo);
				// 小数点第4位で四捨五入
				dtRec.setRebateoSouyouryo(
						(PbsUtil.getZeroSuppress(((new BigDecimal(rebateoSouyouryo)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

				// [HD]容量計（L）_リベート対象外容量計＝[DT]容量（L）_リベート対象外総容量の合計
				rebateoYouryoTot = PbsUtil.sToSAddition(rebateoYouryoTot, rebateoSouyouryo);
			}
		}

		// [HD]データ種別コード
		hdRec.setSyubetuCd(getDataSyubetuCd(editForm.getMode(), editDtList, kaisyaCd, ktksyCd, true));

		// [HD]出荷数量計_箱数
		// 小数点第1位で四捨五入
		hdRec.setSyukaSuryoBox(
				(PbsUtil.getZeroSuppress(((new BigDecimal(syukaSuryoBox)).setScale(0, BigDecimal.ROUND_HALF_UP)).toString())));

		// [HD]出荷数量計_セット数
		// 小数点第1位で四捨五入
		hdRec.setSyukaSuryoSet(
				(PbsUtil.getZeroSuppress(((new BigDecimal(syukaSuryoSet)).setScale(0, BigDecimal.ROUND_HALF_UP)).toString())));

		// [HD]出荷金額計
		// 小数点第3位で四捨五入
		hdRec.setSyukaKingakuTot(
				(PbsUtil.getZeroSuppress(((new BigDecimal(syukaKingakuTot)).setScale(2, BigDecimal.ROUND_HALF_UP)).toString())));

		// [HD]重量計(KG)
		// 小数点第4位で四捨五入
		hdRec.setJyuryoTot(
				(PbsUtil.getZeroSuppress(((new BigDecimal(jyuryoTot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

		// [HD]容量計（L）_出荷容量計
		// 小数点第4位で四捨五入
		hdRec.setSyukaYouryoTot(
				(PbsUtil.getZeroSuppress(((new BigDecimal(syukaYouryoTot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

		// [HD]容量計（L）_リベートⅠ類対象容量計
		// 小数点第4位で四捨五入
		hdRec.setRebate1YouryoTot(
				(PbsUtil.getZeroSuppress(((new BigDecimal(rebate1YouryoTot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

		// [HD]容量計（L）_リベートⅡ類対象容量計
		// 小数点第4位で四捨五入
		hdRec.setRebate2YouryoTot(
				(PbsUtil.getZeroSuppress(((new BigDecimal(rebate2YouryoTot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

		// [HD]容量計（L）_リベートⅢ類対象容量計
		// 小数点第4位で四捨五入
		hdRec.setRebate3YouryoTot(
				(PbsUtil.getZeroSuppress(((new BigDecimal(rebate3YouryoTot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

		// [HD]容量計（L）_リベートⅣ類対象容量計
		// 小数点第4位で四捨五入
		hdRec.setRebate4YouryoTot(
				(PbsUtil.getZeroSuppress(((new BigDecimal(rebate4YouryoTot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

		// [HD]容量計（L）_リベートⅤ類対象容量計
		// 小数点第4位で四捨五入
		hdRec.setRebate5YouryoTot(
				(PbsUtil.getZeroSuppress(((new BigDecimal(rebate5YouryoTot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

		// [HD]容量計（L）_リベート対象外容量計
		// 小数点第4位で四捨五入
		hdRec.setRebateoYouryoTot(
				(PbsUtil.getZeroSuppress(((new BigDecimal(rebateoYouryoTot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

		// [HD]受注重量計に応じた重量別運送店CDを設定（運送店CDが未入力の場合、入力ありの場合はそれを優先）
		if (PbsUtil.isEmpty(hdRec.getUnsotenCd())) {
			// ------------
			// 卸店マスター
			// ------------
			FbMastrOrositenRecord zRecOrositen = rUtil.getRecMastrOrositen(IS_DELETED_HELD_FALSE, kaisyaCd, hdRec.getOrositenCdLast());
			if (!vUtil.validateMstExistence(zRecOrositen, "", IS_MESSAGE_OUTPUT_FALSE)) {
				setErrorMessageId("errors.data.ng",
						PbsUtil.getMessageResourceString("com.text.orositenMst")
						+ PbsUtil.getMessageResourceString("com.text.leftKakko")
						+ PbsUtil.getMessageResourceString("com.text.orositenCd")
						+ PbsUtil.getMessageResourceString("com.text.colon")
						+ hdRec.getOrositenCdLast()
						+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
				return false;
			} else {
				// 着払（引取）の場合
				if (PbsUtil.isIncluded(hdRec.getUnchinKbn(), UNTIN_SEQ_KB_CHAKU_L, UNTIN_SEQ_KB_CHAKU_KG)
						&& !PbsUtil.isEmpty(tatesenRec.getUnsotenCdHikitori())) {
					hdRec.setUnsotenCd(tatesenRec.getUnsotenCdHikitori()); // 運送店(引取)
				// 当日出荷の場合
				} else if (PbsUtil.isEqual(PbsUtil.getTodayYYYYMMDD(), hdRec.getSyukaDt())) {
					hdRec.setUnsotenCd(zRecOrositen.getUnsotenCdToujitu()); // 運送店(当日)
				// 翌日以降出荷（重量別運送店）の場合
				} else {
					if (ComUtil.decimalCompareTo(jyuryoTot, "101") == -1) { // 重量計(KG)=1～100
						hdRec.setUnsotenCd(zRecOrositen.getWeithUnsotenCd1());
					} else if (ComUtil.decimalCompareTo(jyuryoTot, "501") == -1) { // 重量計(KG)=101～500
						hdRec.setUnsotenCd(zRecOrositen.getWeithUnsotenCd101());
					} else if (ComUtil.decimalCompareTo(jyuryoTot, "1001") == -1) { // 重量計(KG)=501～1000
						hdRec.setUnsotenCd(zRecOrositen.getWeithUnsotenCd501());
					} else if (ComUtil.decimalCompareTo(jyuryoTot, "1501") == -1) { // 重量計(KG)=1001～1500
						hdRec.setUnsotenCd(zRecOrositen.getWeithUnsotenCd1001());
					} else if (ComUtil.decimalCompareTo(jyuryoTot, "2001") == -1) { // 重量計(KG)=1501～2000
						hdRec.setUnsotenCd(zRecOrositen.getWeithUnsotenCd1501());
					} else if (ComUtil.decimalCompareTo(jyuryoTot, "2301") == -1) { // 重量計(KG)=2001～2300
						hdRec.setUnsotenCd(zRecOrositen.getWeithUnsotenCd2001());
					} else if (ComUtil.decimalCompareTo(jyuryoTot, "2501") == -1) { // 重量計(KG)=2301～2500
						hdRec.setUnsotenCd(zRecOrositen.getWeithUnsotenCd2301());
					} else if (ComUtil.decimalCompareTo(jyuryoTot, "3001") == -1) { // 重量計(KG)=2501～3000
						hdRec.setUnsotenCd(zRecOrositen.getWeithUnsotenCd2501());
					} else if (ComUtil.decimalCompareTo(jyuryoTot, "3501") == -1) { // 重量計(KG)=3001～3500
						hdRec.setUnsotenCd(zRecOrositen.getWeithUnsotenCd3001());
					} else if (ComUtil.decimalCompareTo(jyuryoTot, "4001") == -1) { // 重量計(KG)=3501～4000
						hdRec.setUnsotenCd(zRecOrositen.getWeithUnsotenCd3501());
					} else if (ComUtil.decimalCompareTo(jyuryoTot, "5201") == -1) { // 重量計(KG)=4001～5200
						hdRec.setUnsotenCd(zRecOrositen.getWeithUnsotenCd4001());
					} else { // 重量計(KG)=5201～
						hdRec.setUnsotenCd(zRecOrositen.getWeithUnsotenCd5201());
					}
				}
			}
			// 名称をセット
			hdRec.setUnsotenNm(this.getUnsotenNmByCd(hdRec.getUnsotenCd(), kaisyaCd, true, true));
		}

		// --------------
		// 運送店マスター
		// --------------
		FbMastrUnsotenRecord zRecUnsoten = rUtil.getRecMastrUnsoten(IS_DELETED_HELD_FALSE, kaisyaCd, hdRec.getUnsotenCd());
		if (!vUtil.validateMstExistence(zRecUnsoten, "", IS_MESSAGE_OUTPUT_FALSE)) {
			setErrorMessageId("errors.data.ng",
					PbsUtil.getMessageResourceString("com.text.unsotenMst")
					+ PbsUtil.getMessageResourceString("com.text.leftKakko")
					+ PbsUtil.getMessageResourceString("com.text.unsotenCd")
					+ PbsUtil.getMessageResourceString("com.text.colon")
					+ hdRec.getUnsotenCd()
					+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
			return false;
		} else {
			// [HD]扱い区分
			if (PbsUtil.isEqual(zRecUnsoten.getHaisoCoolKbn(), YN_04_KB_TAIO)) {
				hdRec.setAtukaiKbn(SHN_DEAL_KB_YOREI); // 要冷
			} else {
				hdRec.setAtukaiKbn(SHN_DEAL_KB_JYOON); // 常温
			}
		}

		return true;
	}

		/**
		 * 販売分類・販売種別・販売部門の情報をセットする
		 *
		 * @param dtRec ディテール部レコード
		 * @param hanbaiBunruiCd 販売分類CD
		 * @return true:エラーなし / false:エラーあり
		 */
		private boolean setHanbaiInfo(JuchuJuchuAddOnlyDtRecord dtRec, String hanbaiBunruiCd) {
			// --------------------
			// 販売分類区分マスター
			// --------------------
			FbMastrKbnHanbaiBunruiRecord zRecHanbaiBunrui = rUtil.getRecMastrKbnHanbaiBunruiRec(IS_DELETED_HELD_FALSE, hanbaiBunruiCd);
			if (!vUtil.validateMstExistence(zRecHanbaiBunrui, "", IS_MESSAGE_OUTPUT_FALSE)) {
				setErrorMessageId("errors.data.ng",
						PbsUtil.getMessageResourceString("com.text.hanbaiBunruiKbMst")
						+ PbsUtil.getMessageResourceString("com.text.leftKakko")
						+ PbsUtil.getMessageResourceString("com.text.hanbaiBunruiCd")
						+ PbsUtil.getMessageResourceString("com.text.colon")
						+ hanbaiBunruiCd
						+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
				return false;
			} else {
				// [DT]販売分類CD
				dtRec.setHanbaiBunruiCd(zRecHanbaiBunrui.getHanbaiBunruiCd());

				// [DT]販売分類名（略式）
				dtRec.setHanbaiBunruiRnm(zRecHanbaiBunrui.getHanbaiBunruiRnm());

				// --------------------
				// 販売種別区分マスター
				// --------------------
				FbMastrKbnHanbaiSyubetuRecord zRecHanbaiSyubetu = rUtil.getMastrKbnHanbaiSyubetuRec(IS_DELETED_HELD_FALSE, zRecHanbaiBunrui.getHanbaiSyubetuCd());
				if (!vUtil.validateMstExistence(zRecHanbaiSyubetu, "", IS_MESSAGE_OUTPUT_FALSE)) {
					setErrorMessageId("errors.data.ng",
							PbsUtil.getMessageResourceString("com.text.hanbaiSyubetuKbMst")
							+ PbsUtil.getMessageResourceString("com.text.leftKakko")
							+ PbsUtil.getMessageResourceString("com.text.hanbaiSyubetuCd2")
							+ PbsUtil.getMessageResourceString("com.text.colon")
							+ zRecHanbaiBunrui.getHanbaiSyubetuCd()
							+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
					return false;
				} else {
					// [DT]販売種別CD
					dtRec.setHanbaiSyubetuCd(zRecHanbaiSyubetu.getHanbaiSyubetuCd());

					// [DT]販売種別名（略式）
					dtRec.setHanbaiSyubetuRnm(zRecHanbaiSyubetu.getHanbaiSyubetuRnm());

					// --------------------
					// 販売部門区分マスター
					// --------------------
					FbMastrKbnHanbaiBumonRecord zRecHanbaiBumon = rUtil.getRecMastrKbnHanbaiBumon(IS_DELETED_HELD_FALSE, zRecHanbaiSyubetu.getHanbaiBumonCd());
					if (!vUtil.validateMstExistence(zRecHanbaiBumon, "", IS_MESSAGE_OUTPUT_FALSE)) {
						setErrorMessageId("errors.data.ng",
								PbsUtil.getMessageResourceString("com.text.hanbaiBumonKbMst")
								+ PbsUtil.getMessageResourceString("com.text.leftKakko")
								+ PbsUtil.getMessageResourceString("com.text.hanbaiBumonCd2")
								+ PbsUtil.getMessageResourceString("com.text.colon")
								+ zRecHanbaiSyubetu.getHanbaiBumonCd()
								+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
						return false;
					} else {
						// [DT]販売部門CD
						dtRec.setHanbaiBumonCd(zRecHanbaiBumon.getHanbaiBumonCd());

						// [DT]販売部門名（略式）
						dtRec.setHanbaiBumonRnm(zRecHanbaiBumon.getHanbaiBumonRnm());
					}
				}
			}

			return true;
		}


// =============================
// ====    関連チェック     ====
// =============================

	/**
	 * 荷受時間区分により不要な荷受時間を除去
	 *
	 * @param rec ヘッダー部レコード
	 */
	private void arrangeNiukeTimeKbn(JuchuJuchuAddOnlyRecord rec) {
		// 不要な指定は空白に置き換える
		if (!NIUKE_TIME_KB_SENPO_SITEI.equals(rec.getNiukeTimeKbn())) { // 先方指定以外
			rec.setNiukeBiginTime(CHAR_BLANK); // 荷受時間_開始
			rec.setNiukeEndTime(CHAR_BLANK); // 荷受時間_終了
		}
	}

	/**
	 * 出荷日　≦　着日　（ヘッダー部）
	 *
	 * @param rec ヘッダー部レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXChakubi(JuchuJuchuAddOnlyRecord rec) {
		if (!PbsUtil.isFutureDate(rec.getSyukaDt(), rec.getChacuniYoteiDt(), true)) {
			// エラーメッセージ
			setErrorMessageId("errors.input.after",
				PbsUtil.getMessageResourceString("com.text.chakubi"),
				PbsUtil.getMessageResourceString("com.text.syukaDt"));
			return false;
		}
		return true;
	}

	/**
	 * リードタイム　≦　（着日－出荷日）　（ヘッダー部）
	 *
	 * @param rec ヘッダー部レコード
	 * @param tatesenRec 縦線情報レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 * @throws ParseException
	 */
	private boolean checkXLeadTime(JuchuJuchuAddOnlyRecord rec, MastrTatesenOrositenRecord tatesenRec) throws ParseException {
		// 着日－出荷日
		String diffDt = Integer.toString(ComUtil.defferenceDays(rec.getSyukaDt(), rec.getChacuniYoteiDt()));

		// （着日－出荷日）がリードタイム以上あるか
		if (PbsUtil.isEqual(PbsUtil.strCompare(diffDt, tatesenRec.getLeadTime()), "2")) {
			// エラーメッセージ
			setErrorMessageId("errors.leadTime");
			return false;
		}
		return true;
	}

	/**
	 * 荷受区分＝先方指定の場合、荷受時間必須　（ヘッダー部）
	 *
	 * @param rec ヘッダー部レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXNiuke(JuchuJuchuAddOnlyRecord rec) {
		if (PbsUtil.isIncluded(rec.getNiukeTimeKbn(), NIUKE_TIME_KB_SENPO_SITEI)) {
			// 荷受時間（始）と荷受時間（終）の両方が未入力はエラー
			if (PbsUtil.isEmpty(rec.getNiukeBiginTime()) && PbsUtil.isEmpty(rec.getNiukeEndTime())) {
				// エラーメッセージ
				setErrorMessageId("errors.request", PbsUtil.getMessageResourceString("com.text.niukeTime"));
				return false;
			}
		}
		return true;
	}

	/**
	 * 荷受時間（始） ＜　荷受時間（終）　（ヘッダー部）
	 *
	 * @param rec ヘッダー部レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXNiukeTime(JuchuJuchuAddOnlyRecord rec) {
		// 荷受時間（始）と荷受時間（終）のどちらかが入力されていればチェック不要
		// 荷受時間（始）と荷受時間（終）の両方が入力されている場合は、時間の矛盾チェック
		if (!PbsUtil.isEmpty(rec.getNiukeBiginTime()) && !PbsUtil.isEmpty(rec.getNiukeEndTime())) {
			if (!("2".equals(PbsUtil.strCompare(rec.getNiukeBiginTime(),rec.getNiukeEndTime())))) {
				// エラーメッセージ
				setErrorMessageId("errors.input.after",
						PbsUtil.getMessageResourceString("com.text.niukeTimeEnd"),
						PbsUtil.getMessageResourceString("com.text.niukeTimeStart"));
				return false;
			}
		}
		return true;
	}

	/**
	 * 当日出荷可否のチェック　（ヘッダー部）
	 *
	 * @param rec ヘッダー部レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXTojituSyuka(JuchuJuchuAddOnlyRecord rec) {
		// 当日出荷＝不可　または　出荷日注意＝当日不可　の場合
		if (PbsUtil.isEqual(YN_02_KB_FUKA, rec.getToujituSyukkaKbn())
				|| PbsUtil.isEqual(SYUK_CAREF_KB_TOJITU_FUKA, rec.getSyukaDtCyuiKbn())) {
			// 出荷日≠当日であること（出荷日＝当日なら警告）
			if (PbsUtil.isEqual(PbsUtil.getTodayYYYYMMDD(), rec.getSyukaDt())) {
				rec.setSyukaDtClass(STYLE_CLASS_WARN);
				return false;
			}
		}
		return true;
	}

	/**
	 * 運送店と運賃の整合性チェック　（ヘッダー部）
	 *
	 * @param rec ヘッダー部レコード
	 * @param tatesenRec 縦線卸店情報レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXUnsotenUnchin(JuchuJuchuAddOnlyRecord rec, MastrTatesenOrositenRecord tatesenRec) {
		boolean res = true;

		// 重量別運送店リスト
		List<String> wtUnsotenList = this.getWtUnsotenList(tatesenRec);

		// 運賃＝元払
		if (PbsUtil.isIncluded(rec.getUnchinKbn(), UNTIN_SEQ_KB_MOTO_JYOON, UNTIN_SEQ_KB_MOTO_YOREI)) {
			// 出荷日＝当日
			if (PbsUtil.isEqual(PbsUtil.getTodayYYYYMMDD(), rec.getSyukaDt())) {
				// 運送店（当日）
				if (PbsUtil.isEqual(tatesenRec.getUnsotenCdToujitu(), rec.getUnsotenCd())) {
					// OK

				// 運送店（引取）
				} else if (PbsUtil.isEqual(tatesenRec.getUnsotenCdHikitori(), rec.getUnsotenCd())) {
					// メッセージ表示
					setErrorMessageId("warn.unsotenUnchinDiffMotoConfirm");
					rec.setDiffUnsotenUnchinMsg(PbsUtil.getMessageResourceString("warn.unsotenUnchinDiffMotoConfirm"));
					rec.setUnchinKbnClass(STYLE_CLASS_WARN);
					rec.setUnsotenCdClass(STYLE_CLASS_WARN);
					res = false;

				// その他
				} else {
					// メッセージ表示
					setErrorMessageId("warn.unsotenDiffConfirm",
							PbsUtil.getMessageResourceString("com.text.unsotenToujitu"));
					rec.setDiffUnsotenUnchinMsg(PbsUtil.getMessageResourceString("warn.unsotenDiffConfirm",
							PbsUtil.getMessageResourceString("com.text.unsotenToujitu")));
					rec.setUnsotenCdClass(STYLE_CLASS_WARN);
					res = false;
				}

			// 出荷日≠当日
			} else {
				// 運送店（通常）、運送店（重量別）
				if (PbsUtil.isEqual(tatesenRec.getUnsotenCdTujyo(), rec.getUnsotenCd())
						|| wtUnsotenList.contains(rec.getUnsotenCd())) {
					// OK

				// 運送店（引取）
				} else if (PbsUtil.isEqual(tatesenRec.getUnsotenCdHikitori(), rec.getUnsotenCd())) {
					// メッセージ表示
					setErrorMessageId("warn.unsotenUnchinDiffMotoConfirm");
					rec.setDiffUnsotenUnchinMsg(PbsUtil.getMessageResourceString("warn.unsotenUnchinDiffMotoConfirm"));
					rec.setUnchinKbnClass(STYLE_CLASS_WARN);
					rec.setUnsotenCdClass(STYLE_CLASS_WARN);
					res = false;

				// その他
				} else {
					// メッセージ表示
					setErrorMessageId("warn.unsotenDiffConfirm",
							PbsUtil.getMessageResourceString("com.text.unsotenTujyoOrJyuryo"));
					rec.setDiffUnsotenUnchinMsg(PbsUtil.getMessageResourceString("warn.unsotenDiffConfirm",
							PbsUtil.getMessageResourceString("com.text.unsotenTujyoOrJyuryo")));
					rec.setUnsotenCdClass(STYLE_CLASS_WARN);
					res = false;
				}
			}

		// 運賃＝着払
		} else if (PbsUtil.isIncluded(rec.getUnchinKbn(), UNTIN_SEQ_KB_CHAKU_L, UNTIN_SEQ_KB_CHAKU_KG)) {
			// 出荷日＝当日
			if (PbsUtil.isEqual(PbsUtil.getTodayYYYYMMDD(), rec.getSyukaDt())) {
				// 運送店（当日）
				if (PbsUtil.isEqual(tatesenRec.getUnsotenCdToujitu(), rec.getUnsotenCd())) {
					// メッセージ表示
					setErrorMessageId("warn.unsotenUnchinDiffChakuConfirm");
					rec.setDiffUnsotenUnchinMsg(PbsUtil.getMessageResourceString("warn.unsotenUnchinDiffChakuConfirm"));
					rec.setUnchinKbnClass(STYLE_CLASS_WARN);
					rec.setUnsotenCdClass(STYLE_CLASS_WARN);
					res = false;

				// 運送店（引取）
				} else if (PbsUtil.isEqual(tatesenRec.getUnsotenCdHikitori(), rec.getUnsotenCd())) {
					// OK

				// その他
				} else {
					// メッセージ表示
					setErrorMessageId("warn.unsotenDiffConfirm",
							PbsUtil.getMessageResourceString("com.text.unsotenHikitori"));
					rec.setDiffUnsotenUnchinMsg(PbsUtil.getMessageResourceString("warn.unsotenDiffConfirm",
							PbsUtil.getMessageResourceString("com.text.unsotenHikitori")));
					rec.setUnsotenCdClass(STYLE_CLASS_WARN);
					res = false;
				}

			// 出荷日≠当日
			} else {
				// 運送店（通常）、運送店（重量別）
				if (PbsUtil.isEqual(tatesenRec.getUnsotenCdTujyo(), rec.getUnsotenCd())
						|| wtUnsotenList.contains(rec.getUnsotenCd())) {
					// メッセージ表示
					setErrorMessageId("warn.unsotenUnchinDiffChakuConfirm");
					rec.setDiffUnsotenUnchinMsg(PbsUtil.getMessageResourceString("warn.unsotenUnchinDiffChakuConfirm"));
					rec.setUnchinKbnClass(STYLE_CLASS_WARN);
					rec.setUnsotenCdClass(STYLE_CLASS_WARN);
					res = false;

				// 運送店（引取）
				} else if (PbsUtil.isEqual(tatesenRec.getUnsotenCdHikitori(), rec.getUnsotenCd())) {
					// OK

				// その他
				} else {
					// メッセージ表示
					setErrorMessageId("warn.unsotenDiffConfirm",
							PbsUtil.getMessageResourceString("com.text.unsotenHikitori"));
					rec.setDiffUnsotenUnchinMsg(PbsUtil.getMessageResourceString("warn.unsotenDiffConfirm",
							PbsUtil.getMessageResourceString("com.text.unsotenHikitori")));
					rec.setUnsotenCdClass(STYLE_CLASS_WARN);
					res = false;
				}
			}
		}

		return res;
	}

	/**
	 * 荷受曜日可否のチェック　（ヘッダー部）
	 *
	 * @param rec ヘッダー部レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXNiukeYoubi(JuchuJuchuAddOnlyRecord rec) {
		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		// 営業日カレンダーマスターから着日の曜日区分と営業日区分を取得
		int youbiKbn = 0; // 曜日区分（日曜日～土曜日）
		String eigyoKbn = ""; // 営業日区分（営業日、休業日）
		FbMastrEigyoCalendarRecord zRec = rUtil.getRecMastrEigyoCalendar(IS_DELETED_HELD_FALSE, kaisyaCd, rec.getChacuniYoteiDt());
		if (!vUtil.validateMstExistence(zRec, "", IS_MESSAGE_OUTPUT_FALSE)) {
			setErrorMessageId("errors.data.ng",
					PbsUtil.getMessageResourceString("com.text.eigyoCalendarMst")
					+ PbsUtil.getMessageResourceString("com.text.leftKakko")
					+ PbsUtil.getMessageResourceString("com.text.chacuniYoteiDt")
					+ PbsUtil.getMessageResourceString("com.text.colon")
					+ rec.getChacuniYoteiDt()
					+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
			return false;
		} else {
			if (PbsUtil.isNumeric(zRec.getYoubiKbn())) {
				youbiKbn = Integer.parseInt(zRec.getYoubiKbn());
			}
			eigyoKbn = zRec.getEigyoKbn();
		}

		// 着日＝祝日の場合
		MastrEigyoCalendar_CheckService eigyoCheckServ
			= new MastrEigyoCalendar_CheckService(getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		if (eigyoCheckServ.isSyukujitu(rec.getChacuniYoteiDt())) {
			// 荷受不可曜日_祝
			if (PbsUtil.isIncluded(rec.getNiukeFukaHol(), CHECKBOX_ON, CHECKBOX_TRUE, CHECKBOX_YES)) {
				// メッセージ表示
				setErrorMessageId("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.hols"));
				rec.setDiffNiukeYoubiMsg(PbsUtil.getMessageResourceString("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.hols")));
				rec.setChacuniYoteiDtClass(STYLE_CLASS_WARN);
				return false;
			}

		// 着日≠祝日の場合
		} else {
			// 荷受不可曜日_日
			if (Calendar.SUNDAY == youbiKbn && PbsUtil.isIncluded(rec.getNiukeFukaSun(), CHECKBOX_ON, CHECKBOX_TRUE, CHECKBOX_YES)) {
				// メッセージ表示
				setErrorMessageId("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.KuradasiTeikiYobiSun"));
				rec.setDiffNiukeYoubiMsg(PbsUtil.getMessageResourceString("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.KuradasiTeikiYobiSun")));
				rec.setChacuniYoteiDtClass(STYLE_CLASS_WARN);
				return false;

			// 荷受不可曜日_月
			} else if (Calendar.MONDAY == youbiKbn && PbsUtil.isIncluded(rec.getNiukeFukaMon(), CHECKBOX_ON, CHECKBOX_TRUE, CHECKBOX_YES)) {
				// メッセージ表示
				setErrorMessageId("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.KuradasiTeikiYobiMon"));
				rec.setDiffNiukeYoubiMsg(PbsUtil.getMessageResourceString("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.KuradasiTeikiYobiMon")));
				rec.setChacuniYoteiDtClass(STYLE_CLASS_WARN);
				return false;

			// 荷受不可曜日_火
			} else if (Calendar.TUESDAY == youbiKbn && PbsUtil.isIncluded(rec.getNiukeFukaTue(), CHECKBOX_ON, CHECKBOX_TRUE, CHECKBOX_YES)) {
				// メッセージ表示
				setErrorMessageId("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.KuradasiTeikiYobiTue"));
				rec.setDiffNiukeYoubiMsg(PbsUtil.getMessageResourceString("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.KuradasiTeikiYobiTue")));
				rec.setChacuniYoteiDtClass(STYLE_CLASS_WARN);
				return false;

			// 荷受不可曜日_水
			} else if (Calendar.WEDNESDAY == youbiKbn && PbsUtil.isIncluded(rec.getNiukeFukaWed(), CHECKBOX_ON, CHECKBOX_TRUE, CHECKBOX_YES)) {
				// メッセージ表示
				setErrorMessageId("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.KuradasiTeikiYobiWed"));
				rec.setDiffNiukeYoubiMsg(PbsUtil.getMessageResourceString("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.KuradasiTeikiYobiWed")));
				rec.setChacuniYoteiDtClass(STYLE_CLASS_WARN);
				return false;

			// 荷受不可曜日_木
			} else if (Calendar.THURSDAY == youbiKbn && PbsUtil.isIncluded(rec.getNiukeFukaThu(), CHECKBOX_ON, CHECKBOX_TRUE, CHECKBOX_YES)) {
				// メッセージ表示
				setErrorMessageId("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.KuradasiTeikiYobiThu"));
				rec.setDiffNiukeYoubiMsg(PbsUtil.getMessageResourceString("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.KuradasiTeikiYobiThu")));
				rec.setChacuniYoteiDtClass(STYLE_CLASS_WARN);
				return false;

			// 荷受不可曜日_金
			} else if (Calendar.FRIDAY == youbiKbn && PbsUtil.isIncluded(rec.getNiukeFukaFri(), CHECKBOX_ON, CHECKBOX_TRUE, CHECKBOX_YES)) {
				// メッセージ表示
				setErrorMessageId("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.KuradasiTeikiYobiFri"));
				rec.setDiffNiukeYoubiMsg(PbsUtil.getMessageResourceString("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.KuradasiTeikiYobiFri")));
				rec.setChacuniYoteiDtClass(STYLE_CLASS_WARN);
				return false;

			// 荷受不可曜日_土
			} else if (Calendar.SATURDAY == youbiKbn && PbsUtil.isIncluded(rec.getNiukeFukaSat(), CHECKBOX_ON, CHECKBOX_TRUE, CHECKBOX_YES)) {
				// メッセージ表示
				setErrorMessageId("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.KuradasiTeikiYobiSat"));
				rec.setDiffNiukeYoubiMsg(PbsUtil.getMessageResourceString("warn.niukeYoubiDiffConfirm",
						PbsUtil.getMessageResourceString("com.text.KuradasiTeikiYobiSat")));
				rec.setChacuniYoteiDtClass(STYLE_CLASS_WARN);
				return false;
			}
		}

		return true;
	}

	/**
	 * 商品CDの重複チェック　（ディテール部）
	 *
	 * @param editDtList ディテール部リスト
	 * @param shohinCd 商品CD
	 * @param idx ループインデックス
	 * @param line 行番号
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXDuplicationShohinCd(JuchuJuchuAddOnlyDtList editDtList, String shohinCd, int idx, int line) {
		boolean res = true;

		for (int i = 0; i < editDtList.size(); i++) {
			JuchuJuchuAddOnlyDtRecord rec = (JuchuJuchuAddOnlyDtRecord) editDtList.get(i);
			if (!PbsUtil.isEmpty(rec.getShohinCd())
					&& i != idx
					&& (rec.getShohinCd()).equals(shohinCd)) {
				res = false; // 商品CDが重複
			}
		}
		if (!res) {
			// コード重複のためエラーメッセージ
			setErrorMessageId("errors.duplicationCd",
					vUtil.getRecordStr(line) + PbsUtil.getMessageResourceString("com.text.syohinCd"));
		}

		return res;
	}

	/**
	 * 出荷数量（総バラ数） ≦　在庫残数（総バラ数）　（ディテール部）
	 *
	 * @param rec ディテール部レコード
	 * @param line 対象行数
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXZaikoSu(JuchuJuchuAddOnlyDtRecord rec, int line) {
		// 在庫管理対象（製品または物品）のみ判定
		if (ComUtil.isSeihin(rec.getBupinKbn()) || ComUtil.isBupin(rec.getBupinKbn())) {
			// 総バラ計算
			String syukaSobaraSu = ComUtil.sobaraKeisan(rec.getSyukaSuCase(), rec.getSyukaSuBara(), rec.getCaseIrisu());

			if (ComUtil.decimalCompareTo(syukaSobaraSu, rec.getZaikoZansu()) > 0) { // 在庫不足
				// エラーメッセージ
				setErrorMessageId("warn.noZaiko"
					, vUtil.getRecordStr(line) + PbsUtil.getMessageResourceString("com.text.colon") + rec.getShohinNm());
				rec.setSyukaSuCaseClass(STYLE_CLASS_WARN);
				rec.setSyukaSuBaraClass(STYLE_CLASS_WARN);
				rec.setZaikoZansuCaseClass(STYLE_CLASS_WARN);
				rec.setZaikoZansuBaraClass(STYLE_CLASS_WARN);
				return false;
			}
		}

		return true;
	}

	/**
	 * 出荷数量（箱数）と出荷数量（セット数）が両方0の場合はエラー
	 *
	 * @param rec ディテール部レコード
	 * @param line 対象行数
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXSyukaSu(JuchuJuchuAddOnlyDtRecord rec, int line) {
		if (PbsUtil.isZero(rec.getSyukaSuCase(), rec.getSyukaSuBara())) {
			// エラーメッセージ
			setErrorMessageId("errors.syukaSuryo0.checkAgain"
				, vUtil.getRecordStr(line) + PbsUtil.getMessageResourceString("com.text.colon") + rec.getShohinNm());
			return false;
		} else {
			return true;
		}
	}


	/**
	 * 単価が変更された商品が含まれているかどうかをチェック
	 *
	 * @param editDtList ディテール部編集リスト
	 * @param kaisyaCd 会社コード
	 * @param ktksyCd 寄託者コード
	 * @param doSetStyleClass スタイルクラス設定フラグ
	 * @return TRUE:単価変更あり / FALSE:単価変更なし
	 */
	private boolean hasXDiffHtanka(JuchuJuchuAddOnlyDtList editDtList, String kaisyaCd, String ktksyCd, boolean doSetStyleClass) {
		boolean res = false; // 単価変更なし

		for (int i = 0; i < editDtList.size(); i++) {

			JuchuJuchuAddOnlyDtRecord rec = (JuchuJuchuAddOnlyDtRecord) editDtList.get(i);

			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(rec.getShohinCd())) {
				// 単価変更あり
				if (PbsUtil.isEqual(JDATAKIND_KB_TANKA_TIGAI, getHtankaChgFlg(rec.getShohinCd(), rec.getHanbaiTanka(), kaisyaCd, ktksyCd))) {
					if (doSetStyleClass) {
						rec.setHanbaiTankaClass(STYLE_CLASS_WARN);
					}
					res = true;
				// 単価変更なし
				} else {
					if (!PbsUtil.isEmpty(rec.getHanbaiTankaView())
							&& !PbsUtil.isEqual(rec.getHanbaiTanka(), rec.getHanbaiTankaView())) {
						if (doSetStyleClass) {
							rec.setHanbaiTankaClass(STYLE_CLASS_MODIFIED);
						}
					} else {
						if (doSetStyleClass) {
							if (!rec.getIsModified()) {
								rec.setHanbaiTankaClass(STYLE_CLASS_NO_EDIT);
							}
						}
					}
				}
			}
		}

		return res;
	}

	/**
	 * 注意商品が含まれているかどうかをチェック
	 *
	 * @param editDtList ディテール部編集リスト
	 * @param hdRec ヘッダー部レコード
	 * @return TRUE:含まれている / FALSE:含まれていない
	 */
	private boolean hasXDiffCautionItem(JuchuJuchuAddOnlyDtList editDtList, JuchuJuchuAddOnlyRecord hdRec) {
		boolean res = false;

		// 注意商品リストを取得
		MastrTatesenOrositen_SearchService mstSearchServ = new MastrTatesenOrositen_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		MastrOrositenSyosaiDtList cautionList = mstSearchServ.getListMastrOrositenSyosaiDt(hdRec.getOrsTatesnCd());

		for (PbsRecord cTmp : cautionList) {
			MastrOrositenSyosaiDtRecord cDtRec = (MastrOrositenSyosaiDtRecord) cTmp;
			for (PbsRecord eTmp : editDtList) {
				JuchuJuchuAddOnlyDtRecord eDtRec = (JuchuJuchuAddOnlyDtRecord) eTmp;
				// 商品CDがある場合のみ
				if (!PbsUtil.isEmpty(eDtRec.getShohinCd())) {
					// 注意商品リストに存在する
					if (PbsUtil.isEqual(cDtRec.getShohinCd(), eDtRec.getShohinCd())) {
						eDtRec.setPbTokucyuKbnClass(STYLE_CLASS_WARN);
						res = true;
					} else {
						if (!eDtRec.getIsModified()) {
							eDtRec.setPbTokucyuKbnClass(STYLE_CLASS_NO_EDIT);
						}
					}
				}
			}
		}

		return res;
	}

	/**
	 * 要冷商品が含まれている場合に要冷配送可能かどうかをチェック
	 *
	 * @param editDtList ディテール部編集リスト
	 * @param hdRec ヘッダー部レコード
	 * @return TRUE:要冷配送可能 / FALSE:要冷配送不可能
	 */
	private boolean checkXDiffYoreiHaisoSyohin(JuchuJuchuAddOnlyDtList editDtList, JuchuJuchuAddOnlyRecord hdRec) {
		boolean res = true;

		for (PbsRecord eTmp : editDtList) {
			JuchuJuchuAddOnlyDtRecord eDtRec = (JuchuJuchuAddOnlyDtRecord) eTmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(eDtRec.getShohinCd())) {
				// 要冷商品（＝ディテール部の扱い区分）の場合、運送店が要冷配送（＝ヘッダー部の扱い区分）に対応しているか
				if (PbsUtil.isEqual(eDtRec.getAtukaiKbn(), SHN_DEAL_KB_YOREI)
						&& !PbsUtil.isEqual(hdRec.getAtukaiKbn(), eDtRec.getAtukaiKbn())) {
					eDtRec.setAtukaiKbnClass(STYLE_CLASS_WARN);
					res = false;
				} else {
					if (!PbsUtil.isEmpty(eDtRec.getAtukaiKbnView())
							&& !PbsUtil.isEqual(eDtRec.getAtukaiKbn(), eDtRec.getAtukaiKbnView())) {
						eDtRec.setAtukaiKbnClass(STYLE_CLASS_MODIFIED);
					} else {
						if (!eDtRec.getIsModified()) {
							eDtRec.setAtukaiKbnClass(STYLE_CLASS_NO_EDIT);
						}
					}
				}
			}
		}

		if (!res) {
			hdRec.setUnsotenCdClass(STYLE_CLASS_WARN);
		} else {
			if (!PbsUtil.isEmpty(hdRec.getUnsotenCdView())
					&& !PbsUtil.isEqual(hdRec.getUnsotenCd(), hdRec.getUnsotenCdView())) {
				hdRec.setUnsotenCdClass(STYLE_CLASS_MODIFIED);
			} else {
				if (!hdRec.getIsModified()) {
					hdRec.setUnsotenCdClass(STYLE_CLASS_NO_EDIT);
				}
			}
		}

		return res;
	}

	/**
	 * 要冷商品が含まれている場合に運賃が適切かどうかをチェック
	 *
	 * @param editDtList ディテール部編集リスト
	 * @param hdRec ヘッダー部レコード
	 * @return TRUE:運賃は適切 / FALSE:運賃が適切でない
	 */
	private boolean checkXDiffYoreiUnchinSyohin(JuchuJuchuAddOnlyDtList editDtList, JuchuJuchuAddOnlyRecord hdRec) {
		boolean res = true;

		for (PbsRecord eTmp : editDtList) {
			JuchuJuchuAddOnlyDtRecord eDtRec = (JuchuJuchuAddOnlyDtRecord) eTmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(eDtRec.getShohinCd())) {
				// 要冷商品（＝ディテール部の扱い区分）の場合、運賃が元払（要冷）か
				if (PbsUtil.isEqual(eDtRec.getAtukaiKbn(), SHN_DEAL_KB_YOREI)
						&& PbsUtil.isEqual(hdRec.getUnchinKbn(), UNTIN_SEQ_KB_MOTO_JYOON)) {
					eDtRec.setAtukaiKbnClass(STYLE_CLASS_WARN);
					res = false;
				} else {
					if (!PbsUtil.isEmpty(eDtRec.getAtukaiKbnView())
							&& !PbsUtil.isEqual(eDtRec.getAtukaiKbn(), eDtRec.getAtukaiKbnView())) {
						eDtRec.setAtukaiKbnClass(STYLE_CLASS_MODIFIED);
					} else {
						if (!eDtRec.getIsModified()) {
							eDtRec.setAtukaiKbnClass(STYLE_CLASS_NO_EDIT);
						}
					}
				}
			}
		}

		if (!res) {
			hdRec.setUnchinKbnClass(STYLE_CLASS_WARN);
		} else {
			if (!PbsUtil.isEmpty(hdRec.getUnchinKbnView())
					&& !PbsUtil.isEqual(hdRec.getUnchinKbn(), hdRec.getUnchinKbnView())) {
				hdRec.setUnchinKbnClass(STYLE_CLASS_MODIFIED);
			} else {
				if (!hdRec.getIsModified()) {
					hdRec.setUnchinKbnClass(STYLE_CLASS_NO_EDIT);
				}
			}
		}

		return res;
	}

	/**
	 * 元払（要冷）の場合に要冷配送可能かどうかをチェック
	 *
	 * @param hdRec ヘッダー部レコード
	 * @return TRUE:要冷配送可能 / FALSE:要冷配送不可能
	 */
	private boolean checkXDiffYoreiHaisoUnchin(JuchuJuchuAddOnlyRecord hdRec) {
		boolean res = true;

		// 運賃＝元払（要冷）の場合、運送店が要冷配送（＝扱い区分）に対応しているか
		if (PbsUtil.isEqual(hdRec.getUnchinKbn(), UNTIN_SEQ_KB_MOTO_YOREI)
				&& !PbsUtil.isEqual(hdRec.getAtukaiKbn(), YN_04_KB_TAIO)) {
			hdRec.setUnchinKbnClass(STYLE_CLASS_WARN);
			hdRec.setUnsotenCdClass(STYLE_CLASS_WARN);
			res = false;
		} else {
			if (!PbsUtil.isEmpty(hdRec.getUnchinKbnView())
					&& !PbsUtil.isEqual(hdRec.getUnchinKbn(), hdRec.getUnchinKbnView())) {
				hdRec.setUnchinKbnClass(STYLE_CLASS_MODIFIED);
			} else {
				if (!hdRec.getIsModified()) {
					hdRec.setUnchinKbnClass(STYLE_CLASS_NO_EDIT);
				}
			}
			if (!PbsUtil.isEmpty(hdRec.getUnsotenCdView())
					&& !PbsUtil.isEqual(hdRec.getUnsotenCd(), hdRec.getUnsotenCdView())) {
				hdRec.setUnsotenCdClass(STYLE_CLASS_MODIFIED);
			} else {
				if (!hdRec.getIsModified()) {
					hdRec.setUnsotenCdClass(STYLE_CLASS_NO_EDIT);
				}
			}
		}

		return res;
	}

	/**
	 * 補償金徴収対象が含まれている場合に樽保証金が登録されているかどうかをチェック
	 *
	 * @param editDtList ディテール部編集リスト
	 * @param hdRec ヘッダー部レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXDiffTaruHosyoKin(JuchuJuchuAddOnlyDtList editDtList) {
		boolean res = false;

		// 補償金徴収対象が含まれている場合に樽保証金が登録されているか
		if (hasHosyoKinTaisyo(editDtList)) {
			if (hasShohinAtBupinKbn(editDtList, SYOHIN_KB_TARU_HOSYOKIN)) {
				res = true;
			} else {
				// 補償金徴収対象に警告色
				for (PbsRecord eTmp : editDtList) {
					JuchuJuchuAddOnlyDtRecord eDtRec = (JuchuJuchuAddOnlyDtRecord) eTmp;
					// 商品CDがある場合のみ
					if (!PbsUtil.isEmpty(eDtRec.getShohinCd())
							&& PbsUtil.isEqual(eDtRec.getIndemnityKbn(), YN_06_KB_TAISYO)) {
						eDtRec.setShohinCdClass(STYLE_CLASS_WARN);
						eDtRec.setShohinNmClass(STYLE_CLASS_WARN);
					}
				}
			}
		} else {
			res = true;
		}

		return res;
	}

	/**
	 * 引上手数料対象が含まれている場合に樽取扱手数料が登録されているかどうかをチェック
	 *
	 * @param editDtList ディテール部編集リスト
	 * @param hdRec ヘッダー部レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXDiffTaruTesuryo(JuchuJuchuAddOnlyDtList editDtList) {
		boolean res = false;

		// 引上手数料対象が含まれている場合に樽取扱手数料が登録されているか
		if (hasTesuryoTaisyo(editDtList)) {
			if (hasShohinAtBupinKbn(editDtList, SYOHIN_KB_TARU_TESURYO)) {
				res = true;
			} else {
				// 引上手数料対象に警告色
				for (PbsRecord eTmp : editDtList) {
					JuchuJuchuAddOnlyDtRecord eDtRec = (JuchuJuchuAddOnlyDtRecord) eTmp;
					// 商品CDがある場合のみ
					if (!PbsUtil.isEmpty(eDtRec.getShohinCd())
							&& PbsUtil.isEqual(eDtRec.getServiceKbn(), YN_06_KB_TAISYO)) {
						eDtRec.setShohinCdClass(STYLE_CLASS_WARN);
						eDtRec.setShohinNmClass(STYLE_CLASS_WARN);
					}
				}
			}
		} else {
			res = true;
		}

		return res;
	}

	/**
	 * 補償金徴収対象が含まれているか
	 *
	 * @param editDtList ディテール部編集リスト
	 * @return TRUE:含まれている / FALSE:含まれていない
	 */
	private boolean hasHosyoKinTaisyo(JuchuJuchuAddOnlyDtList editDtList) {
		boolean res = false;

		for (PbsRecord eTmp : editDtList) {
			JuchuJuchuAddOnlyDtRecord eDtRec = (JuchuJuchuAddOnlyDtRecord) eTmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(eDtRec.getShohinCd())) {
				// 一つでも補償金徴収対象があればtrue
				if (PbsUtil.isEqual(eDtRec.getIndemnityKbn(), YN_06_KB_TAISYO)) {
					res = true;
				}
			}
		}

		return res;
	}

	/**
	 * 引上手数料対象が含まれているか
	 *
	 * @param editDtList ディテール部編集リスト
	 * @return TRUE:含まれている / FALSE:含まれていない
	 */
	private boolean hasTesuryoTaisyo(JuchuJuchuAddOnlyDtList editDtList) {
		boolean res = false;

		for (PbsRecord eTmp : editDtList) {
			JuchuJuchuAddOnlyDtRecord eDtRec = (JuchuJuchuAddOnlyDtRecord) eTmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(eDtRec.getShohinCd())) {
				// 一つでも引上手数料対象があればtrue
				if (PbsUtil.isEqual(eDtRec.getServiceKbn(), YN_06_KB_TAISYO)) {
					res = true;
				}
			}
		}

		return res;
	}

	/**
	 * 指定した物品区分の商品が含まれているか
	 *
	 * @param editDtList ディテール部編集リスト
	 * @return TRUE:含まれている / FALSE:含まれていない
	 */
	private boolean hasShohinAtBupinKbn(JuchuJuchuAddOnlyDtList editDtList, String ... bupinKbn) {
		boolean res = false;

		for (PbsRecord eTmp : editDtList) {
			JuchuJuchuAddOnlyDtRecord eDtRec = (JuchuJuchuAddOnlyDtRecord) eTmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(eDtRec.getShohinCd())) {
				// 一つでも指定した物品区分の商品があればtrue
				if (PbsUtil.isIncluded(eDtRec.getBupinKbn(), bupinKbn)) {
					res = true;
				}
			}
		}

		return res;
	}

	/**
	 * 縦線卸店情報から重量別運送店リストを取得します。
	 *
	 * @param tatesenRec
	 * @return wtUnsotenList
	 */
	private List<String> getWtUnsotenList(MastrTatesenOrositenRecord tatesenRec) {
		// 重量別運送店リスト
		List<String> wtUnsotenList = new ArrayList<String>();

		// 重量別_運送店CD_1～100
		if (!PbsUtil.isEmpty(tatesenRec.getWeithUnsotenCd1())) {
			wtUnsotenList.add(tatesenRec.getWeithUnsotenCd1());
		}
		// 重量別_運送店CD_101～500
		if (!PbsUtil.isEmpty(tatesenRec.getWeithUnsotenCd101())) {
			wtUnsotenList.add(tatesenRec.getWeithUnsotenCd101());
		}
		// 重量別_運送店CD_501～1000
		if (!PbsUtil.isEmpty(tatesenRec.getWeithUnsotenCd501())) {
			wtUnsotenList.add(tatesenRec.getWeithUnsotenCd501());
		}
		// 重量別_運送店CD_1001～1500
		if (!PbsUtil.isEmpty(tatesenRec.getWeithUnsotenCd1001())) {
			wtUnsotenList.add(tatesenRec.getWeithUnsotenCd1001());
		}
		// 重量別_運送店CD_1501～2000
		if (!PbsUtil.isEmpty(tatesenRec.getWeithUnsotenCd1501())) {
			wtUnsotenList.add(tatesenRec.getWeithUnsotenCd1501());
		}
		// 重量別_運送店CD_2001～2300
		if (!PbsUtil.isEmpty(tatesenRec.getWeithUnsotenCd2001())) {
			wtUnsotenList.add(tatesenRec.getWeithUnsotenCd2001());
		}
		// 重量別_運送店CD_2301～2500
		if (!PbsUtil.isEmpty(tatesenRec.getWeithUnsotenCd2301())) {
			wtUnsotenList.add(tatesenRec.getWeithUnsotenCd2301());
		}
		// 重量別_運送店CD_2501～3000
		if (!PbsUtil.isEmpty(tatesenRec.getWeithUnsotenCd2501())) {
			wtUnsotenList.add(tatesenRec.getWeithUnsotenCd2501());
		}
		// 重量別_運送店CD_3001～3500
		if (!PbsUtil.isEmpty(tatesenRec.getWeithUnsotenCd3001())) {
			wtUnsotenList.add(tatesenRec.getWeithUnsotenCd3001());
		}
		// 重量別_運送店CD_3501～4000
		if (!PbsUtil.isEmpty(tatesenRec.getWeithUnsotenCd3501())) {
			wtUnsotenList.add(tatesenRec.getWeithUnsotenCd3501());
		}
		// 重量別_運送店CD_4001～5200
		if (!PbsUtil.isEmpty(tatesenRec.getWeithUnsotenCd4001())) {
			wtUnsotenList.add(tatesenRec.getWeithUnsotenCd4001());
		}
		// 重量別_運送店CD_5201～
		if (!PbsUtil.isEmpty(tatesenRec.getWeithUnsotenCd5201())) {
			wtUnsotenList.add(tatesenRec.getWeithUnsotenCd5201());
		}

		return wtUnsotenList;
	}

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
