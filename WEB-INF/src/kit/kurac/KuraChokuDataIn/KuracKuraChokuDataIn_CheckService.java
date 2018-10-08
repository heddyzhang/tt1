package kit.kurac.KuraChokuDataIn;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.inf.IKitConst.*;
import static fb.inf.pbs.IPbsConst.*;
import static kit.kurac.KuraChokuDataIn.IKuracKuraChokuDataIn.*;
import kit.mastr.TatesenOrositen.MastrTatesenOrositenRecord;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import fb.com.ComRecordUtil;
import fb.com.ComUserSession;
import fb.com.ComUtil;
import fb.com.ComValidateUtil;
import fb.com.Records.FbMastrOrosisyosaiHdRecord;
import fb.com.Records.FbMastrOrositenRecord;
import fb.com.Records.FbMastrSyuhantenRecord;
import fb.com.Records.FbMastrUnsotenRecord;
import fb.inf.KitService;
import fb.inf.exception.KitException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 入力内容をチェックするビジネスロジッククラスです
 */
public class KuracKuraChokuDataIn_CheckService extends KitService {

	/**
	 *
	 */

	/** シリアルID */
	private static final long serialVersionUID = 6565726213940476817L;

	/** クラス名. */
	private static String className__ = KuracKuraChokuDataIn_CheckService.class.getName();

	/** カテゴリ. */
	public static Category category__ = Category.getInstance(className__);

	/** データベースオブジェクト */
	private PbsDatabase db_;

	/** 選択されたレコード */
	private KuracKuraChokuDataInRecord recSelected;

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
	public KuracKuraChokuDataIn_CheckService(ComUserSession cus, PbsDatabase db_, boolean isTran,
			ActionErrors ae) {

		super(cus, db_, isTran, ae);
		//_reset();
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
	public KuracKuraChokuDataInRecord getRecSelected() {
		return new KuracKuraChokuDataInRecord(recSelected.getHashMap());
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
	public boolean validateSearch(KuracKuraChokuDataIn_SearchForm searchForm) {

		// フラグ初期化
		searchForm.initClasses();

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


	// ==============================
	// ==== 必須チェックシリーズ ====
	// ==============================
	/**
	 * 必須チェックを行う（呼出用）
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean inputCheckRequiredForSearch(KuracKuraChokuDataIn_SearchForm searchForm) {

		// 必須項目なし

		return true;
	}

	/**
	 * 必須チェックを行う（新規開始用）
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean inputCheckRequiredForAdd(KuracKuraChokuDataIn_SearchForm searchForm) {

		String msgKey_itemNm = "";
		boolean ret = true;

		// 事業所
		msgKey_itemNm = "com.text.zigyousyo";
		// 必須チェック
		if(!vUtil.validateRequired(searchForm.getJigyousyo(), msgKey_itemNm)){
			searchForm.setJigyousyoClass(STYLE_CLASS_ERROR);
			ret = false;
		} else {
			searchForm.setJigyousyoClass(STYLE_CLASS_INIT);
		}

		// 商品GRP
		msgKey_itemNm = "com.text.shohinGrp";
		// 必須チェック
		if(!vUtil.validateRequired(searchForm.getShohinGrp(), msgKey_itemNm)){
			searchForm.setShohinGrpClass(STYLE_CLASS_ERROR);
			ret = false;
		} else {
			searchForm.setShohinGrpClass(STYLE_CLASS_INIT);
		}

		// 申込日
		if (!checkMoshikomiDt(searchForm.getMousikomibi())) {
			searchForm.setMousikomibiClass(STYLE_CLASS_ERROR);
			ret = false;
		} else {
			searchForm.setMousikomibiClass(STYLE_CLASS_INIT);
		}

		// 発送予定日
		if (!checkHasoYoteiDt(searchForm.getHasoyoteibi())) {
			searchForm.setHasoyoteibiClass(STYLE_CLASS_ERROR);
			ret = false;
		} else {
			searchForm.setHasoyoteibiClass(STYLE_CLASS_INIT);
		}

		return ret;
	}

	/**
	 * 属性チェックを行う（呼出用）
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean inputCheckForSearch(KuracKuraChokuDataIn_SearchForm searchForm) {
		String msgKey_itemNm = "";
		boolean ret = true;

		// メーカNo.
		msgKey_itemNm = "com.text.makerNo";
		if (!vUtil.validateHankakuSimple(searchForm.getMakerNo(), IS_REQUIRED_FALSE, MAX_LEN_MOSHIKOMI_NO, msgKey_itemNm)) {
			ret = false;
		}

		// 送荷先
		msgKey_itemNm = "com.text.sounisaki";
		if (!vUtil.validateMojiSimple(searchForm.getSounisakiNm(), IS_REQUIRED_FALSE, MAX_LEN_SOUNISKI_NM, msgKey_itemNm)) {
			ret = false;
		}

		// 酒販店名
		msgKey_itemNm = "com.text.syuhantenNm";
		if (!vUtil.validateMojiSimple(searchForm.getSyuhantenNm(), IS_REQUIRED_FALSE, MAX_LEN_SYUHANTEN_NM, msgKey_itemNm)) {
			ret = false;
		}

		// 依頼主
		msgKey_itemNm = "com.text.irainusi";
		if (!vUtil.validateMojiSimple(searchForm.getIrainusi(), IS_REQUIRED_FALSE, MAX_LEN_IRAISYU_NM, msgKey_itemNm)) {
			ret = false;
		}

		// 酒販店コード
		msgKey_itemNm = "com.text.syuhantenCd";
		if (!vUtil.validateCodeSimple(searchForm.getSyuhantenCd(), IS_REQUIRED_FALSE, CODE_LEN_SYUHANTEN, msgKey_itemNm)) {
			ret = false;
		}

		// 届け先
		msgKey_itemNm = "com.text.tdksk";
		if (!vUtil.validateMojiSimple(searchForm.getTdksk(), IS_REQUIRED_FALSE, MAX_LEN_TODOKESAKI_NM, msgKey_itemNm)) {
			ret = false;
		}

		return ret;
	}

	/**
	 * 検索フォームの入力チェックを行う（CSV出力）
	 *
	 * @param searchForm
	 * @return
	 */
	public boolean validateCsvMake(KuracKuraChokuDataIn_SearchForm searchForm) {

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		// kit:buttonタグでボタン表示制御済み、ここではチェックなし

		// ==================================================
		// 必須チェックを行う。
		// ==================================================
//		if (!inputCheckRequiredForCsvMake(searchForm)) {
//			return false;
//		}
		return true;
	}


	/**
	 * 新規ボタン押下時のチェックを行う(on SearchForm)
	 *
	 * @param searchForm
	 * @return TRUE:正常 / FALSE:検索条件に誤りあり
	 */
	public boolean validateSearchForAdd(KuracKuraChokuDataIn_SearchForm searchForm) {

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		// kit:buttonタグでボタン表示制御済み、ここではチェックなし

		// ==================================================
		// 必須チェックを行う。
		// ==================================================
		if ( !inputCheckRequiredForAdd(searchForm) ) {
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
	public boolean validateSelect(KuracKuraChokuDataIn_EditForm editForm, 	KuracKuraChokuDataInList searchedList) throws KitException {

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

		return true;

	}


	/**
	 * セットボタン押下時チェック
	 * ViewJSPで検索部のボタンを非活性にするための値セット
	 *
	 * @param editList
	 * @param searchForm
	 */
	public void validateSelectBtnAvailFlg(KuracKuraChokuDataInList editList, KuracKuraChokuDataInDtList editDtList, KuracKuraChokuDataIn_SearchForm searchForm) {

		KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) editList.getFirstRecord();

		// ボタンの初期化
		// 修正ボタン
		searchForm.setEditBtnAvailFlg(BUTTON_AVAILABLE_TRUE);

		// 参照追加ボタン
		searchForm.setReferrerBtnAvailFlg(BUTTON_AVAILABLE_TRUE);

		// 訂正(+)ボタン
		searchForm.setRefplusBtnAvailFlg(BUTTON_AVAILABLE_TRUE);

		// 訂正(-)ボタン
		searchForm.setRefminusBtnAvailFlg(BUTTON_AVAILABLE_TRUE);

		// 抹消ボタン
		searchForm.setDeleteBtnAvailFlg(BUTTON_AVAILABLE_TRUE);

		// ====================================================================================================
		// 　修正　　：利用区分＝利用可　かつ　蔵本ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ．黄桜出荷伝票NOに入力がない、使用可
		// 　訂正(+) ：利用区分＝利用可　かつ　蔵本ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ．黄桜出荷伝票NOに入力がある、使用可
		// 　訂正(-) ：利用区分＝利用可　かつ　蔵本ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ．黄桜出荷伝票NOに入力がある、使用可
		// 　抹消　　：利用区分＝利用可　かつ　蔵本ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ．黄桜出荷伝票NOに入力がない、使用可
		// ====================================================================================================

		// 利用区分＝利用可の場合
		if (PbsUtil.isEqual(rec.getRiyouKbn(), AVAILABLE_KB_RIYO_KA)) {
			// 蔵本ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ．黄桜出荷伝票NOが入力されている場合
			if (checkHasSyukadenNo(editDtList)) {
				// 修正ボタン
				searchForm.setEditBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性
				// 抹消ボタン
				searchForm.setDeleteBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性
			}
			// 蔵本ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ．黄桜出荷伝票NO]が入力されていない場合
			if (checkHasNotSyukadenNo(editDtList)) {
				// 訂正(+)ボタン
				searchForm.setRefplusBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性
				// 訂正(-)ボタン
				searchForm.setRefminusBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性
			}

		// 利用区分＝利用停止の場合
		} else if (PbsUtil.isEqual(rec.getRiyouKbn(), AVAILABLE_KB_RIYO_TEISI)) {
			// 修正ボタン
			searchForm.setEditBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性

			// 参照追加ボタン
			searchForm.setReferrerBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性

			// 訂正(+)ボタン
			searchForm.setRefplusBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性

			// 訂正(-)ボタン
			searchForm.setRefminusBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性

			// 抹消ボタン
			searchForm.setDeleteBtnAvailFlg(BUTTON_DISABLED_FALSE); // 非活性

		}
	}


	/**
	 * すべて黄桜出荷伝票NOに入力があるかどうかのチェック
	 * @param editDtList
	 * @return TRUE / FALSE
	 * @throws KitException
	 */
	public boolean checkHasSyukadenNo(KuracKuraChokuDataInDtList editDtList) {
		boolean ret = true;

		for (int i = 0; i < editDtList.size(); i++) {
			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

			// 未入力行はスキップ
			if (rec.isEmpty()) {
				continue;
			}

			// 蔵本ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ．黄桜出荷伝票NOに入力があるデータがなった場合
			if (PbsUtil.isEmpty(rec.getSyukadenNo())) {
				ret = false;
			}
		}

		return ret;
	}

	/**
	 * すべて黄桜出荷伝票NOに入力がないかどうかのチェック
	 * @param editDtList
	 * @return TRUE / FALSE
	 * @throws KitException
	 */
	public boolean checkHasNotSyukadenNo(KuracKuraChokuDataInDtList editDtList) {
		boolean ret = true;

		for (int i = 0; i < editDtList.size(); i++) {
			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

			// 未入力行はスキップ
			if (rec.isEmpty()) {
				continue;
			}

			// 蔵本ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ．黄桜出荷伝票NOに入力がある場合
			if (!PbsUtil.isEmpty(rec.getSyukadenNo())) {
				ret = false;
			}
		}

		return ret;
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
	public boolean validateManipulationEdit(KuracKuraChokuDataIn_EditForm editForm,
			KuracKuraChokuDataInList editList, KuracKuraChokuDataInDtList editDtList,
			KuracKuraChokuDataInList selectedList) throws KitException {

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
	public boolean validateManipulationReference(KuracKuraChokuDataIn_EditForm editForm,
			KuracKuraChokuDataInList editList, KuracKuraChokuDataInDtList editDtList,
			KuracKuraChokuDataInList searchedList) {

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
	 * 操作系画面（参照追加）呼出時チェック(on SearchForm)
	 *
	 * @param editForm
	 * @param editList
	 * @param editDtList
	 * @param selectedList
	 * @return TRUE / FALSE
	 */
	public boolean validateManipulationRefplus(KuracKuraChokuDataIn_EditForm editForm,
			KuracKuraChokuDataInList editList, KuracKuraChokuDataInDtList editDtList,
			KuracKuraChokuDataInList searchedList) {

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
	public boolean validateManipulationDelete(KuracKuraChokuDataIn_EditForm editForm,
			KuracKuraChokuDataInList editList, KuracKuraChokuDataInDtList editDtList, KuracKuraChokuDataInList searchedList) {

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
	 * 新規時チェック
	 *
	 * @param editForm
	 * @param editList
	 * @param editDtList
	 * @param tatesenRec
	 * @return true(エラー無) or false(エラー有)
	 */
	public boolean validateConfirmAsInsert(KuracKuraChokuDataIn_EditForm editForm,
			KuracKuraChokuDataInList editList, KuracKuraChokuDataInDtList editDtList) throws KitException {

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
		if (!inputCheckXForInsert(editList)) {
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
		// 関連チェックを行う（ヘッダ部×ディテール部）
		// ==================================================
		if (!inputCheckXForInsert(editList, editDtList)) {
			return false;
		}

		// ==================================================
		// その他の値設定
		// ==================================================
		if (!setOthers(editForm, editList, editDtList)) {
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
	public boolean validateConfirmAsUpdate(KuracKuraChokuDataIn_EditForm editForm,
			KuracKuraChokuDataInList initList, KuracKuraChokuDataInDtList initDtList,
			KuracKuraChokuDataInList editList, KuracKuraChokuDataInDtList editDtList) throws KitException {

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
				KuracKuraChokuDataInRecord eRec = (KuracKuraChokuDataInRecord) editList.get(i);
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
		if (!inputCheckXForEdit(editList)) {
			return false;
		}

		// ==================================================
		// 項目の単体チェックを行う（ディテール部）
		// ==================================================
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
		// 関連チェックを行う（ヘッダ部×ディテール部）
		// ==================================================
		if (!inputCheckXForInsert(editList, editDtList)) {
			return false;
		}

		// ==================================================
		// その他の値設定
		// ==================================================
		if (!setOthers(editForm, editList, editDtList)) {
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
	public boolean validateConfirmAsReference(KuracKuraChokuDataIn_EditForm editForm,
			KuracKuraChokuDataInList initList, KuracKuraChokuDataInDtList initDtList,
			KuracKuraChokuDataInList editList, KuracKuraChokuDataInDtList editDtList) throws KitException {

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
		if (!inputCheckXForInsert(editList)) {
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
		// 関連チェックを行う（ヘッダ部×ディテール部）
		// ==================================================
		if (!inputCheckXForInsert(editList, editDtList)) {
			return false;
		}

		// ==================================================
		// その他の値設定
		// ==================================================
		// その他
		if (!setOthers(editForm, editList, editDtList)) {
			return false;
		}

		return true;
	}

	/**
	 * 訂正時チェック
	 *
	 * @param editForm
	 * @param initList
	 * @param initDtList
	 * @param editList
	 * @param editDtList
	 * @param tatesenRec
	 * @return true(エラー無) or false(エラー有)
	 */
	public boolean validateConfirmAsRefPlusMinus(KuracKuraChokuDataIn_EditForm editForm,
			KuracKuraChokuDataInList initList, KuracKuraChokuDataInDtList initDtList,
			KuracKuraChokuDataInList editList, KuracKuraChokuDataInDtList editDtList) throws KitException {

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
		if (!inputCheckForRefPlusMinus(initList, editList)) {
			return false;
		}

		// ==================================================
		// 関連チェックを行う（ヘッダー部）
		// ==================================================
		if (!inputCheckXForRefPlusMinus(editList)) {
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
		if (!inputCheckForRefPlusMinus(editForm, initDtList, editDtList)) {
			return false;
		}

		// ==================================================
		// 関連チェックを行う（ディテール部）
		// ==================================================
		if (!inputCheckXForInsert(editDtList)) { // ディテール部
			return false;
		}

		// ==================================================
		// 関連チェックを行う（ヘッダ部×ディテール部）
		// ==================================================
		if (!inputCheckXForInsert(editList, editDtList)) {
			return false;
		}

		// ==================================================
		// その他の値設定
		// ==================================================
		// その他
		if (!setOthers(editForm, editList, editDtList)) {
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
	public boolean validateConfirmAsDelete(KuracKuraChokuDataInList editList, KuracKuraChokuDataInDtList editDtList) {

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
		KuracKuraChokuDataInRecord rec = null;
		for (PbsRecord tmp : editList) {
			rec = (KuracKuraChokuDataInRecord) tmp;
			rec.setModified();
		}
		// ディテール部
		KuracKuraChokuDataInDtRecord recDt = null;
		for (PbsRecord tmp : editDtList) {
			recDt = (KuracKuraChokuDataInDtRecord) tmp;
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
	private boolean inputCheckForManipulation(KuracKuraChokuDataInList editList,
			KuracKuraChokuDataInDtList editDtList, KuracKuraChokuDataInList selectedList) {

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
	private boolean inputCheckNoEntryForInsert(KuracKuraChokuDataInList editList) {

		boolean res = false;

		KuracKuraChokuDataInRecord rec;
		for (PbsRecord tmp : editList) {
			rec = (KuracKuraChokuDataInRecord) tmp;

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
			setErrorMessageId("errors.noInputAll",PbsUtil.getMessageResourceString("com.text.kuraChk"));
			return false;
		}
	}


	/**
	 * 追加時入力有無チェックを実施する（ディテール部）
	 *
	 * @param editDtList
	 * @return TRUE:なんらかの入力がある / FALSE:なにも入力されていない
	 */
	private boolean inputCheckNoEntryForInsert(KuracKuraChokuDataInDtList editDtList) {

		boolean res = false;

		KuracKuraChokuDataInDtRecord rec;
		for (PbsRecord tmp : editDtList) {
			rec = (KuracKuraChokuDataInDtRecord) tmp;

			// 入力行を発見したらフラグにTRUEを立てる
			if ((!rec.isEmpty()) && (!PbsUtil.isEmpty(rec.getShohinCd())) ) {
				res = true;
				rec.setModified();
			}

			// 商品コードがある場合
			if (!PbsUtil.isEmpty(rec.getShohinCd())) {
				// // セット数が未入力の場合は0をセットする
				if (PbsUtil.isEmpty(rec.getShohinSet())) {
					rec.setShohinSet(SU_ZERO);
				}
			}
		}

		if (res) {
			return true;
		} else {
			// 先頭入力欄にエラーフラグを設定
			rec = (KuracKuraChokuDataInDtRecord) editDtList.getFirstRecord();
			rec.setShohinCdClass(STYLE_CLASS_ERROR);
			// エラーメッセージを出力
			setErrorMessageId("errors.noInputAll.at",PbsUtil.getMessageResourceString("com.text.kuraChk"));
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
	private boolean inputCheckNoEntryForEdit(KuracKuraChokuDataInList initList, KuracKuraChokuDataInList editList) {

		boolean res = false;

		for (int i = 0; i < editList.size(); i++) {
			KuracKuraChokuDataInRecord iRec = (KuracKuraChokuDataInRecord) initList.get(i);
			KuracKuraChokuDataInRecord eRec = (KuracKuraChokuDataInRecord) editList.get(i);

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
			// エラーメッセージを出力
			//setErrorMessageId("errors.NothingUpdateTarget.at",PbsUtil.getMessageResourceString("com.text.kuraChk"));
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
	private boolean inputCheckNoEntryForEdit(KuracKuraChokuDataInDtList initDtList, KuracKuraChokuDataInDtList editDtList) {

		boolean res = false;

		for (int i = 0; i < editDtList.size(); i++) {
			KuracKuraChokuDataInDtRecord iRec = (KuracKuraChokuDataInDtRecord) initDtList.get(i);
			KuracKuraChokuDataInDtRecord eRec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

			// 変更行を発見したらTRUEを返す。
			if (!eRec.equals(iRec)) {
				res = true;
				eRec.setModified();
			}

			// 商品コードがある場合
			if (!PbsUtil.isEmpty(eRec.getShohinCd())) {
				// // セット数が未入力の場合は0をセットする
				if (PbsUtil.isEmpty(eRec.getShohinSet())) {
					eRec.setShohinSet(SU_ZERO);
				}
			}
		}

		if (res) {
			return true;
		} else {
// ヘッダー部とディテール部のどちらかが変更されていればOKとするため、ここではエラーメッセージを表示しない
//			// エラーメッセージを出力
//			setErrorMessageId("errors.NothingUpdateTarget.at",PbsUtil.getMessageResourceString("com.text.kuraChk"));
			return false;
		}
	}

	/**
	 * 追加時：項目の単体チェックをする（ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckForInsert(KuracKuraChokuDataInList editList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) editList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// 発注NO
			if (!checkHacyuNo(rec.getHacyuNo())) {
				rec.setHacyuNoClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 整理No
			if (!checkKuradenNo(rec.getKuradenNo())) {
				rec.setKuradenNoClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 共通項目の単体チェック
			lineRes = checkItemCommon(rec, kaisyaCd, lineRes);

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
	private boolean inputCheckForInsert(KuracKuraChokuDataInDtList editDtList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		for (int i = 0; i < editDtList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// ディテール部共通項目の単体チェック
			lineRes = checkDtItemCommon(rec, lineRes);

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
//
	/**
	 * 変更時：項目の単体チェックをする（ヘッダー部）
	 *
	 * @param initList
	 * @param editList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckForEdit(KuracKuraChokuDataInList initList, KuracKuraChokuDataInList editList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) editList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// 発注NO
			if (!checkHacyuNo(rec.getHacyuNo())) {
				rec.setHacyuNoClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 申込み受付日
			if (!checkMoshikomiDt(rec.getUketukeDt())) {
				rec.setUketukeDtClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}


			// 発送予定日
			if (!checkHasoYoteiDt(rec.getHasoYoteiDt())) {
				rec.setHasoYoteiDtClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 共通項目の単体チェック
			lineRes = checkItemCommon(rec, kaisyaCd, lineRes);

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
	private boolean inputCheckForEdit(KuracKuraChokuDataInDtList initDtList, KuracKuraChokuDataInDtList editDtList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		for (int i = 0; i < editDtList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

			// 未入力行はスキップ
			if (rec.isEmpty()) {
				continue;
			}

			// ディテール部共通項目の単体チェック
			lineRes = checkDtItemCommon(rec, lineRes);

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
	private boolean inputCheckForReference(KuracKuraChokuDataInList initList, KuracKuraChokuDataInList editList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) editList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// 発注NO
			if (!checkHacyuNo(rec.getHacyuNo())) {
				rec.setHacyuNoClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 整理No
			if (!checkKuradenNo(rec.getKuradenNo())) {
				rec.setKuradenNoClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 申込み受付日
			if (!checkMoshikomiDt(rec.getUketukeDt())) {
				rec.setUketukeDtClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 発送予定日
			if (!checkHasoYoteiDt(rec.getHasoYoteiDt())) {
				rec.setHasoYoteiDtClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 共通項目の単体チェック
			lineRes = checkItemCommon(rec, kaisyaCd, lineRes);

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
	private boolean inputCheckForReference(KuracKuraChokuDataInDtList initDtList, KuracKuraChokuDataInDtList editDtList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		for (int i = 0; i < editDtList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// ディテール部共通項目の単体チェック
			lineRes = checkDtItemCommon(rec, lineRes);

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
	 * 訂正時：項目の単体チェックをする（ヘッダー部）
	 *
	 * @param initList
	 * @param editList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckForRefPlusMinus(KuracKuraChokuDataInList initList, KuracKuraChokuDataInList editList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) editList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// 発送予定日
			if (!checkHasoYoteiDt(rec.getHasoYoteiDt())) {
				rec.setHasoYoteiDtClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// 共通項目の単体チェック
			lineRes = checkItemCommon(rec, kaisyaCd, lineRes);

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
	 * 訂正時：項目の単体チェックをする（ディテール部）
	 *
	 * @param initDtList
	 * @param editDtList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckForRefPlusMinus(KuracKuraChokuDataIn_EditForm editForm, KuracKuraChokuDataInDtList initDtList, KuracKuraChokuDataInDtList editDtList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		for (int i = 0; i < editDtList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// 訂正（＋）時　正の整数のみ入力可
			if(PbsUtil.isEqual(editForm.getMode(), REQ_TYPE_REFPLUS)){
				// セット数
				if (!checkSetSu(rec.getShohinSet())) {
					rec.setShohinSetClass(STYLE_CLASS_ERROR);
					lineRes = false;
				}

			// 訂正（－）時　マイナス符号入力可, 整数のみ入力可
			} else if(PbsUtil.isEqual(editForm.getMode(), REQ_TYPE_REFMINUS)){
				// セット数
				if (!checkSetSuMinus(rec.getShohinSet())) {
					rec.setShohinSetClass(STYLE_CLASS_ERROR);
					lineRes = false;
				}
			}

			// 販売単価
			if (!checkHanbaiTanka(rec.getHanbaiTanka())) {
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
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForInsert(KuracKuraChokuDataInList editList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) editList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			// 申込み受付チェック
			if (!checkXMoshikomiNo(rec)) {

				rec.setTodokesakiLineNoClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			//  ヘッダー部共通関連チェック
			lineRes = checkXItemCommon(rec, kaisyaCd, lineRes);

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
	private boolean inputCheckXForInsert(KuracKuraChokuDataInDtList editDtList) {

		boolean res = true;
		boolean setFlg = false;

		for (int i = 0; i < editDtList.size(); i++) {

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

			// 未入力行はスキップ
			if (rec.isEmpty()) {
				continue;
			}

			if (!PbsUtil.isZeroOrBlank(rec.getShohinSet())) {
				// 商品出荷数が入力しました
				setFlg = true;
			}
		}

		// 商品出荷数は1項目には必ず要値
		if (!setFlg) {
			// 先頭入力欄にエラーフラグを設定
			KuracKuraChokuDataInDtRecord dtRec = (KuracKuraChokuDataInDtRecord) editDtList.getFirstRecord();
			dtRec.setShohinCdClass(STYLE_CLASS_ERROR);
			dtRec.setShohinSetClass(STYLE_CLASS_ERROR);
			// エラーメッセージを出力
			setErrorMessageId("errors.noInputAll.at",PbsUtil.getMessageResourceString("com.text.setSu"));
			res = false;
		}

		return res;
	}

	/**
	 * 追加時：関連チェックをする（ヘッダ部×ディテール部）
	 * @param editList
	 * @param editDtList
	 * @return
	 */
	private boolean inputCheckXForInsert(KuracKuraChokuDataInList editList,KuracKuraChokuDataInDtList editDtList) {

		boolean res = true;

		// ヘッダー部レコード
		KuracKuraChokuDataInRecord hdRec = (KuracKuraChokuDataInRecord) editList.getFirstRecord();

		// セット（本）数 チェック
		if (!checkXSetSu(hdRec.getShoninGrpCd(), editDtList)) {

			setErrorMessageId("errors.found.mistake",PbsUtil.getMessageResourceString("com.text.setSu"));
			res = false;
		}

		return res;

	}

	/**
	 * 変更時：関連チェックをする（ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForEdit(KuracKuraChokuDataInList editList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) editList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			//  ヘッダー部共通関連チェック
			lineRes = checkXItemCommon(rec, kaisyaCd, lineRes);

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
	private boolean inputCheckXForEdit(KuracKuraChokuDataInDtList editDtList) {

		boolean res = true;
		boolean setFlg = false;

		for (int i = 0; i < editDtList.size(); i++) {

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

			// 未入力行はスキップ
			if (rec.isEmpty()) {
				continue;
			}

			if (!PbsUtil.isZero(rec.getShohinSet())) {
				// 商品出荷数が入力しました
				setFlg = true;
			}
		}

		if (!setFlg) {
			// 先頭入力欄にエラーフラグを設定
			KuracKuraChokuDataInDtRecord dtRec = (KuracKuraChokuDataInDtRecord) editDtList.getFirstRecord();
			dtRec.setShohinCdClass(STYLE_CLASS_ERROR);
			dtRec.setShohinSetClass(STYLE_CLASS_ERROR);
			// エラーメッセージを出力
			setErrorMessageId("errors.noInputAll.at",PbsUtil.getMessageResourceString("com.text.setSu"));
			res = false;
		}

		return res;

	}


	/**
	 * 訂正時：関連チェックをする（ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForRefPlusMinus(KuracKuraChokuDataInList editList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) editList.get(i);

			// 変更無しは、スキップ
			if (!rec.getIsModified()) {
				continue;
			}

			//  ヘッダー部共通関連チェック
			lineRes = checkXItemCommon(rec, kaisyaCd, lineRes);

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
	 * 抹消時：関連チェックをする（ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForDelete(KuracKuraChokuDataInList editList) {

		boolean res = true;
		boolean lineRes = true;

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、対象外のためチェック不要
			KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) editList.get(i);

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
	 * 抹消時：関連チェックをする（ディテール部）
	 *
	 * @param editDtList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForDelete(KuracKuraChokuDataInDtList editDtList) {

		boolean res = true;
		boolean lineRes = true;

		for (int i = 0; i < editDtList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、対象外のためチェック不要
			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

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
	 * 呼出後：編集ボタン押下時操作可能チェック
	 *
	 * @param editList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean searchCheckXForEdit(KuracKuraChokuDataInList editList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		for (int i = 0; i < editList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) editList.get(i);

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
	 * セット(選択時)必須チェック
	 *
	 * @param editForm
	 * @param editList
	 * @return TRUE / FALSE
	 */
	private boolean inputCheckRequiredForSelect(KuracKuraChokuDataIn_EditForm editForm,
			KuracKuraChokuDataInList editList) throws KitException {

		/* *******************************
		 * 選択有無チェック
		 * 1. editFormセット欄
		 * 2. 行選択ID
		 * 3. 更新対象フラグ
		 * ****************************** */

		for (;;) {
			// 1. editFormセット欄
			// 入力されていたら最優先でチェック
			if (!PbsUtil.isEmpty(editForm.getKuradataNo())) {

				/* =============================
				 * 要注意ポイント(一覧から明細を取得するとき)
				 * 条件１、２のときは、前回選択された位置を
				 * リセットしないと複数行選択されてしまう。
				 * 条件３のときは、そのまま同じ行を取得して使用する
				 ============================= */

				// 更新対象フラグを初期化
				editList.initFlgs();

				// 蔵直データ連番チェック
				if (!checkKuradataNo(editForm.getKuradataNo())) {
					editForm.setKuradataNoClass(STYLE_CLASS_ERROR);
					return false;
				}

				// 受注NOでレコードを取得する
				recSelected = editList.getRecByCode(editForm.getKuradataNo());

				// レコードを確認
				if (recSelected == null) {
					// 指定されたコードは、呼び出されていません。
					setErrorMessageId("errors.not.selected");
					editForm.setKuradataNoClass(STYLE_CLASS_ERROR);
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
				recSelected = (KuracKuraChokuDataInRecord) editList.getRecSelectedRow(editForm.getSelectedRowId());

				break;
			}

			// 3. 更新対象フラグ
			KuracKuraChokuDataInRecord rec = null;
			for (PbsRecord tmp : editList) {
				rec = (KuracKuraChokuDataInRecord) tmp;
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
			editForm.setKuradataNoClass(STYLE_CLASS_ERROR);
			return false;
		}

		// レコードがセットされたのでTRUEで抜ける
		recSelected.setModified(IS_MODIFIED_TRUE);
		return true;

	}

// ==================================
// ==== パーツごとの単体チェック ====
// ==================================

	// ----- ヘッダー部 -----

	/**
	 * 蔵直データ連番の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkKuradataNo(String data) {
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
		if (!vUtil.validateLength(data, MAX_LEN_KURADATA_NO, msgKey_itemNm)) {
			return false;
		}

		return true;
	}

	/**
	 * 申込日の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkMoshikomiDt(String data) {
		String msgKey_itemNm = "com.text.mousikomibi";

		// 基本チェック（必須チェック含む）
		if (!vUtil.validateDateSimple(data, IS_REQUIRED_TRUE, msgKey_itemNm)) {
			return false;
		}

		return true;
	}


	/**
	 * 発送予定日の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkHasoYoteiDt(String data) {
		String msgKey_itemNm = "com.text.hasoyoteibi";

		// 基本チェック（必須チェック含む）
		if (!vUtil.validateDateSimple(data, IS_REQUIRED_TRUE, msgKey_itemNm)) {
			return false;
		}

		return true;
	}

	/**
	 * 縦線CDの単体チェック
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @param kaisyaCd 会社コード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTatesnCd(KuracKuraChokuDataInRecord rec, String kaisyaCd, boolean isView) {
		String msgKey_itemNm = "com.text.tatesenCd";
        String data = rec.getTatesnCd();
		// 基本チェック（必須チェック含む）
		if (!isView && !vUtil.validateCodeSimple(data, CODE_LEN_ORS_TATESN, msgKey_itemNm)) {
			return false;
		}

		if (!PbsUtil.isEmpty(data)) {
			// 存在チェック なければエラー
			FbMastrOrosisyosaiHdRecord zRec = rUtil.getRecMastrOrosisyosaiHd(IS_DELETED_HELD_TRUE, kaisyaCd, data);
			if (!vUtil.validateMstExistence(zRec, msgKey_itemNm)) {
				return false;
			}

			// 利用状態チェック
			String riyouJotaiFlg = ComUtil.getRiyouJotaiFlg(getComUserSession().getGymDate(), zRec.getNouhinKaisiDt(),
					zRec.getNouhinSyuryoDt(), zRec.getRiyouTeisiDt());
			// 1：仮登録、2：利用可能のみ
			if (!PbsUtil.isIncluded(riyouJotaiFlg, DATA_JYOTAI_KB_KARI_TOROKU, DATA_JYOTAI_KB_RIYO_KA)) {

				setErrorMessageId("errors.input.riyouFuka.teisiOrDelete", PbsUtil.getMessageResourceString(msgKey_itemNm));
				return false;
			}


		}

		return true;
	}

	/**
	 * 酒販店CDの単体チェック
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @param kaisyaCd 会社コード
	 * @param isView View用フラグ（true：property=xxView用、false：property=xx用）
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkSyuhantenCd(KuracKuraChokuDataInRecord rec, String kaisyaCd, boolean isView) {
		String msgKey_itemNm = "com.text.syuhanten";
		String data = rec.getSyuhantenCd();

		// 蔵直商品ｸﾞﾙｰﾌﾟCDはT(夏 単品) or P(冬 単品) or F(父の日) or M(母の日) のときは
		// 酒販店コード必須チェック
		if (PbsUtil.isIncluded(rec.getShoninGrpCd(), KURAC_SHOHINGRP_KB_TANPIN_SUMMER,
				KURAC_SHOHINGRP_KB_TANPIN_WINTER, KURAC_SHOHINGRP_KB_FATHER_DAY,
						KURAC_SHOHINGRP_KB_MOTHER_DAY)) {
			// 酒販店コード必須チェック
			if (!vUtil.validateRequired(data, msgKey_itemNm)) {
				return false;
			}
		}

		// 基本チェック
		if (!isView && !vUtil.validateCodeSimple(data, IS_REQUIRED_FALSE, CODE_LEN_SYUHANTEN, msgKey_itemNm)) {
			// 初期化
			rec.setSyuhantenNm(CHAR_BLANK);			// 酒販店名
			rec.setSyuhantenTel(CHAR_BLANK);		// ＴＥＬ
			rec.setSyuhantenAddress(CHAR_BLANK);	// 住所
			rec.setSyuhantenZip(CHAR_BLANK);		// 郵便番号

			return false;
		}

		// 酒販店マスタの存在チェック
		if (!PbsUtil.isEmpty(data)) {
			// 存在チェック なければエラー
			FbMastrSyuhantenRecord zRec = rUtil.getRecMastrSyuhanten(IS_DELETED_HELD_TRUE, data);
			if (!vUtil.validateMstExistence(zRec, msgKey_itemNm)) {
				// 初期化
				rec.setSyuhantenNm(CHAR_BLANK);			// 酒販店名
				rec.setSyuhantenTel(CHAR_BLANK);		// ＴＥＬ
				rec.setSyuhantenAddress(CHAR_BLANK);	// 住所
				rec.setSyuhantenZip(CHAR_BLANK);		// 郵便番号

				return false;
			}

		}

		return true;
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
	private boolean checkUnsotenCd(KuracKuraChokuDataInRecord rec, String kaisyaCd, boolean isView) {
		String msgKey_itemNm = "com.text.unsoten";
		String data = rec.getUnsotenCd();

		// 基本チェック
		if (!isView && !vUtil.validateCodeSimple(data, IS_REQUIRED_TRUE, CODE_LEN_UNSOTEN, msgKey_itemNm)) {
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
		}

		return true;
	}


	/**
	 * 発注NOの単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkHacyuNo(String data) {
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
	 * 整理NOの単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkKuradenNo(String data) {
		String msgKey_itemNm = "com.text.kuradenNo";

		// 必須チェック
		if (!vUtil.validateRequired(data, msgKey_itemNm)) {
			return false;
		}

		// 基本チェック
		if (!vUtil.validateNumeric(data, msgKey_itemNm)) {
			return false;
		}

		// 長さチェック
		if (!vUtil.validateLength(data, MAX_LEN_KURADEN_NO, msgKey_itemNm)) {
			return false;
		}

		return true;
	}

	/**
	 * 届け先内容の氏名の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTodokesakiShimei(String data) {
		String msgKey_itemNm = "com.text.shimei";

		// 基本チェック
		if (!vUtil.validateMojiSimple(data,IS_REQUIRED_TRUE, MAX_LEN_TODOKESAKI_NM, msgKey_itemNm)) {
			return false;
		}

		return true;
	}

	/**
	 * 届け先内容の住所の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTodokesakiAdress(String data) {
		String msgKey_itemNm = "com.text.jyusyo";

		// 基本チェック
		if (!vUtil.validateMojiSimple(data,IS_REQUIRED_TRUE, MAX_LEN_TODOKESAKI_ADRESS, msgKey_itemNm)) {
			return false;
		}

		return true;
	}

	/** 届け先内容のTELの単体チェック
	 *
	 *  @param チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTodokesakiTel(String data){
		boolean res = true;
		String msgKey_itemNm = "com.text.tel";

		// 必須チェックする
		if(!vUtil.validateRequired(data, msgKey_itemNm)){
			res = false;
		}

		// 電話番号チェック
		if (!vUtil.validateTelephone(data, msgKey_itemNm)) {
			res = false;
		}
		return res;
	}

	/** 届け先内容の郵便番号の単体チェック
	 *
	 *  @param チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTodokesakiZip(String data){
		boolean res = true;
		String msgKey_itemNm = "com.text.postalCd";

		// 基本チェック
		if (!vUtil.validateZipCd(data, msgKey_itemNm)) {
			res = false;
		}
		return res;
	}

	/**
	 * 依頼主内容の氏名の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkIraisyuShimei(String data) {
		String msgKey_itemNm = "com.text.shimei";

		// 基本チェック
		if (!vUtil.validateMojiSimple(data,IS_REQUIRED_TRUE, MAX_LEN_IRAISYU_NM,msgKey_itemNm)) {
			return false;
		}

		return true;
	}

	/**
	 * 依頼主内容の住所の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkIraisyuAdress(String data) {
		String msgKey_itemNm = "com.text.jyusyo";

		// 基本チェック
		if (!vUtil.validateMojiSimple(data,IS_REQUIRED_TRUE, MAX_LEN_IRAISYU_ADRESS, msgKey_itemNm)) {
			return false;
		}

		return true;
	}

	/** 依頼主内容のTELの単体チェック
	 *
	 *  @param チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkIraisyuTel(String data){
		boolean res = true;
		String msgKey_itemNm = "com.text.tel";

		// 必須チェックする
		if(!vUtil.validateRequired(data, msgKey_itemNm)){
			res = false;
		}

		// 電話番号チェック
		if (!vUtil.validateTelephone(data, msgKey_itemNm)) {
			res = false;
		}
		return res;
	}

	/** 依頼主内容の郵便番号の単体チェック
	 *
	 *  @param チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkIraisyuZip(String data){
		boolean res = true;
		String msgKey_itemNm = "com.text.postalCd";

		// 基本チェック
		if (!vUtil.validateZipCd(data, msgKey_itemNm)) {
			res = false;
		}
		return res;
	}

	/**
	 * のし名前記載の単体チェック
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkNoshiKbn(KuracKuraChokuDataInRecord rec) {
		Boolean ret = true;

		String nosiComment1 = rec.getNosiComment1();
		String nosiComment2 = rec.getNosiComment2();
		String nosiComment3 = rec.getNosiComment3();

		String msgKey_itemNm1 = "com.text.kinoichiLeft";
		String msgKey_itemNm2 = "com.text.kinoichiMiddle";
		String msgKey_itemNm3 = "com.text.kinoichiRight";

		// のし名前記載がなしの場合
		if (KURAC_NOSINAME_YN_KB_NASI.equals(rec.getNosiKbn())) {
			return true;
		}

		// 基本チェック
		if (!vUtil.validateMojiSimple(nosiComment1, IS_REQUIRED_FALSE, MAX_LEN_NOSHI_COMMENT,msgKey_itemNm1)) {
			rec.setNosiComment1Class(STYLE_CLASS_ERROR);
			ret = false;
		}
		if (!vUtil.validateMojiSimple(nosiComment2, IS_REQUIRED_FALSE, MAX_LEN_NOSHI_COMMENT,msgKey_itemNm2)) {
			rec.setNosiComment2Class(STYLE_CLASS_ERROR);
			ret = false;
		}
		if (!vUtil.validateMojiSimple(nosiComment3, IS_REQUIRED_FALSE, MAX_LEN_NOSHI_COMMENT,msgKey_itemNm3)) {
			rec.setNosiComment3Class(STYLE_CLASS_ERROR);
			ret = false;
		}
		return ret;
	}

	/**
	 * 販売単価の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkHanbaiTanka(String data) {
		String msgKey_itemNm = "com.text.tanka";
		data = data.replace(",", "");
		// 必須チェック
		if (!vUtil.validateRequired(data, msgKey_itemNm)) {
			return false;
		}

		// 型チェック
		if (!vUtil.validateNumberFormat(data, 8, 2, msgKey_itemNm)) {
			return false;
		}

		// 長さチェック
		if (!vUtil.validateLength(data, MAX_LEN_TANKA, msgKey_itemNm)) {
			return false;
		}

		//
		return true;
	}

	/**
	 * セット数の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkSetSu(String data) {
		String msgKey_itemNm = "com.text.setSu";

		// 型チェック
		if (!vUtil.validateNumeric(data, msgKey_itemNm)) {
			return false;
		}

		// 長さチェック
		if (!vUtil.validateLength(data, MAX_LEN_SET_SU, msgKey_itemNm)) {
			return false;
		}

		return true;
	}

	/**
	 * セット数（－）の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkSetSuMinus(String data) {
		String setSu = null;
		String msgKey_itemNm = "com.text.setSu";

		// 型チェック
		if (!vUtil.validateInteger(data, msgKey_itemNm)) {
			return false;
		}
		// 符号チェック(負の値であればＯＫ)
		if (!vUtil.validateNegativeDecimal(data, msgKey_itemNm)) {
			return false;
		}
		// 入力値の絶対値　⇒　ケタ数チェック
		setSu = PbsUtil.sToAbs(data);
		// 長さチェック
		if (!PbsUtil.isEmpty(setSu)) {
			if (!vUtil.validateLength(setSu, MAX_LEN_SET_SU, msgKey_itemNm)) {
				return false;
			}
			return true;
		}
		return false;
	}

	// =============================
	// ====    関連チェック     ====
	// =============================
	/**
	 * 申込み受付No	チェック
	 * @param rec
	 * @param kaisyaCd
	 * @return
	 */
	private boolean checkXMoshikomiNo(KuracKuraChokuDataInRecord rec) {

		// 申込み受付No上7桁
		// [黄桜事業所区分]+[蔵直商品ｸﾞﾙｰﾌﾟCD]+【ﾃﾞｰﾀ種別CD】+[整理No]
		String moshikoiNo7 = rec.getJigyosyoKbn() + rec.getShoninGrpCd() + rec.getSyubetuCd() + rec.getKuradenNo();

		// 届先ﾗｲﾝNo - 1
		String todokesakiLineNo = String.valueOf(Integer.parseInt(rec.getTodokesakiLineNo()) - 1);

		// 申込み受付No
		String moshikoiNo =moshikoiNo7 + todokesakiLineNo;

		KuracKuraChokuDataIn_SearchService searchServ = new KuracKuraChokuDataIn_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 届先ﾗｲﾝNoが1以外の場合
		if (!("1").equals(rec.getTodokesakiLineNo())) {

			// 申込み受付Noをキーとして蔵元ﾃﾞｰﾀ_ﾍｯﾀﾞｰを検索
			KuracKuraChokuDataInList searchList1 = searchServ.getKurachokuDataInListByMoshiNo(moshikoiNo);
			// データが存在しないときエラーとする
			if (searchList1 == null) {
				setErrorMessageId("errors.found.mistake",PbsUtil.getMessageResourceString("com.text.tdkskNo"));
				return false;
			}
		}

		// 申込受付日の同年
		String YYYY = PbsUtil.getYYYY(rec.getUketukeDt());
		// 申込受付日の同年/01/01
		String startYmd = YYYY + "0101";
		// 申込受付日の同年/12/31
		String endYmd = YYYY + "1231";

		// 申込み受付No、申込受付日の同年/01/01との同年/12/31をキーとして蔵元ﾃﾞｰﾀ_ﾍｯﾀﾞｰを検索
		KuracKuraChokuDataInList searchList2 = searchServ.getKurachokuDataInListByMoshiNoDate(moshikoiNo7 + rec.getTodokesakiLineNo(), startYmd, endYmd);
		// データが存在したときエラーとする
		if (searchList2 != null) {
			setErrorMessageId("errors.check.existMaster",PbsUtil.getMessageResourceString("com.text.mousikomiNo"));
			return false;
		}

		return true;
	}

//	/**
//	 * 発送予定日	チェック
//	 * @param rec
//	 * @return
//	 * @throws KitException
//	 */
//	private boolean checkXHasoYoteiDt(KuracKuraChokuDataInRecord rec) throws KitException {
//
//		// 発送予定日
//		String hasoYoteDt = PbsUtil.getYMD(rec.getHasoYoteiDt());
//		// 今日
//		String today = PbsUtil.getTodayYYYYMMDD();
//
//		//入力パラメータを設定
//		ComEigyoCalendarUtil calUtil = new ComEigyoCalendarUtil(getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
//
//		// 翌営業日
//		String eigyoDate = calUtil.getEigyoCalendarDt(today, "1");
//
//		// 発送予定日は[申込受付日]の翌営業日であること
//		if (hasoYoteDt.equals(eigyoDate)) {
//			return true;
//		} else {
//			return false;
//		}
//
//	}

	/**
	 * 卸店ﾏｽﾀｰ チェック
	 * @param rec 対象レコード
	 * @return
	 */
	private boolean checkXOrositenInfo(KuracKuraChokuDataInRecord rec, String kaisyaCd) {

		// 特約店CD
		String tokuyakuCd = rec.getTokuyakutenCd();
		// ﾃﾞﾎﾟ店CD
		String depoCd = rec.getDepoCd();
		// 2次店CD
		String nijitenCd = rec.getNijitenCd();
		// 3次店CD
		String sanjitenCd = rec.getSanjitenCd();
		// 酒販店コード
		String syuhantenCd = rec.getSyuhantenCd();

		// 酒販店コードが何も入力しない場合、エラーチェックを不要
		if (PbsUtil.isEmpty(syuhantenCd)) {
			return true;
		}

		// 特約店CD
		if (!PbsUtil.isEmpty(tokuyakuCd)) {
			// 存在チェック
			ComRecordUtil rUtil = new ComRecordUtil(db_);
			// 特約店CDをキーとして卸店ﾏｽﾀｰを検索
			FbMastrOrositenRecord zRec = rUtil.getRecMastrOrositen(IS_DELETED_HELD_TRUE, kaisyaCd, tokuyakuCd);
			if (!ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在した場合
				// 酒販店コード＝対象卸店ﾏｽﾀｰの酒販店（統一）CDのときエラーとする
				if (syuhantenCd.equals(zRec.getSyuhantenCd())) {
					return false;
				}
			}
		}

		// デポ店CD
		if (!PbsUtil.isEmpty(depoCd)) {
			// 存在チェック
			ComRecordUtil rUtil = new ComRecordUtil(db_);
			// デポ店CDをキーとして卸店ﾏｽﾀｰを検索
			FbMastrOrositenRecord zRec = rUtil.getRecMastrOrositen(IS_DELETED_HELD_TRUE, kaisyaCd, depoCd);
			if (!ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在した場合
				// 酒販店コード＝対象卸店ﾏｽﾀｰの酒販店（統一）CDのときエラーとする
				if (syuhantenCd.equals(zRec.getSyuhantenCd())) {
					return false;
				}
			}
		}

		// 二次店CD
		if (!PbsUtil.isEmpty(nijitenCd)) {
			// 存在チェック
			ComRecordUtil rUtil = new ComRecordUtil(db_);
			// 二次店CDをキーとして卸店ﾏｽﾀｰを検索
			FbMastrOrositenRecord zRec = rUtil.getRecMastrOrositen(IS_DELETED_HELD_TRUE, kaisyaCd, nijitenCd);
			if (!ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在した場合
				// 酒販店コード＝対象卸店ﾏｽﾀｰの酒販店（統一）CDのときエラーとする
				if (syuhantenCd.equals(zRec.getSyuhantenCd())) {
					return false;
				}
			}
		}

		// 三次店CD
		if (!PbsUtil.isEmpty(sanjitenCd)) {
			// 存在チェック
			ComRecordUtil rUtil = new ComRecordUtil(db_);
			// 三次店CDをキーとして卸店ﾏｽﾀｰを検索
			FbMastrOrositenRecord zRec = rUtil.getRecMastrOrositen(IS_DELETED_HELD_TRUE, kaisyaCd, sanjitenCd);
			if (!ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在した場合
				// 酒販店コード＝対象卸店ﾏｽﾀｰの酒販店（統一）CDのときエラーとする
				if (syuhantenCd.equals(zRec.getSyuhantenCd())) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * のし名前記載の関連チェック
	 *
	 * @param rec 対象レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXNoshiKbn(KuracKuraChokuDataInRecord rec) {

		String nosiComment1 = rec.getNosiComment1();
		String nosiComment2 = rec.getNosiComment2();
		String nosiComment3 = rec.getNosiComment3();

		//String msgKey_itemNm = "com.text.nosikisai";

		// のし名前記載がなしの場合
		if (KURAC_NOSINAME_YN_KB_NASI.equals(rec.getNosiKbn())) {
			if (PbsUtil.isEmpty(nosiComment1) && PbsUtil.isEmpty(nosiComment2)
			&& PbsUtil.isEmpty(nosiComment3)) {
				return true;
			}else {
				return false;
			}
		}

		// のし区分＝”A：あり”かつ、のし名１～３すべてに入力がないときエラーとする
		if (PbsUtil.isEmpty(nosiComment1) && PbsUtil.isEmpty(nosiComment2)
				&& PbsUtil.isEmpty(nosiComment3)) {
			return false;
		}

		return true;
	}

	/**
	 * のし名用途区分の関連チェック
	 *
	 * @param rec 対象レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXYotoKbn(KuracKuraChokuDataInRecord rec) {

		// のし区分＝”A：あり”かつ、のし名用途区分＝”1：不要”のときエラーとする
		if ((KURAC_NOSINAME_YN_KB_ARI).equals(rec.getNosiKbn())
				&& (KURAC_NOSIYOUTO_KB_FUYOU).equals(rec.getYoutoKbn())) {

			return false;
		}

		return true;
	}

	/**
	 * 酒販店 の関連チェック
	 * @param rec
	 * @return
	 */
	private boolean checkXSyuhanten(KuracKuraChokuDataInRecord rec) {
		// 酒販店名称禁止文字
		String kinshiNm[] = SYUHANTEN_KINSIMOJI.split(",");

		String syuhantenNm = rec.getSyuhantenNm();

		if (!PbsUtil.isEmpty(syuhantenNm)) {
			for (String name: kinshiNm) {
				// 酒販店名がに下記の文字が含まれた時
				if (syuhantenNm.contains(name)) {

					return false;
				}
			}
		}

		return true;
	}
	/**
	 * 届先住所 の関連チェック
	 *
	 * @param rec 対象レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXTodokesakiAddress(KuracKuraChokuDataInRecord rec) {
		// 届先住所
		String todokesakiAddress = rec.getTodokesakiAddress();

		if (todokesakiAddress.length() >= 2) {

			//  届先住所 先頭２文字
			String fisrtNiMojiAddress = rec.getTodokesakiAddress().substring(0, 2);

			String fisrstNiMoji[] = FISRT_NIMOJI_ADDRESS.split(",");

			for (String niMoji: fisrstNiMoji) {
				// 届先住所先頭２文字に下記の文字が含まれていたときエラーとする
				if (niMoji.equals(fisrtNiMojiAddress)) {

					return false;
				}
			}

		}

		if (todokesakiAddress.length() >= 3) {

			//  届先住所 先頭３文字
			String fisrtSanMojiAddress = rec.getTodokesakiAddress().substring(0, 3);

			String firstSanMoji[] = FISRT_SANMOJI_ADDRESS.split(",");
			for (String sanMoji: firstSanMoji) {
				// 届先住所先頭３文字に下記の文字が含まれていたときエラーとする
				if (sanMoji.equals(fisrtSanMojiAddress)) {

					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 特約店コード の関連チェック
	 *
	 * @param rec 対象レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXTokuyakutenCd(KuracKuraChokuDataInRecord rec) {
		//  特約店コード
		String tokuyakutenCd = rec.getTokuyakutenCd();
		String tokuteiCd[] = TOKUTEI_TOKUYAKUTEN_CODES.split(",");

		for (String code: tokuteiCd) {
			// 特約店ｺｰﾄﾞが下記に該当するときエラーとする
			if (code.equals(tokuyakutenCd)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 包装 の関連チェック
	 *
	 * @param rec 対象レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXHousou(KuracKuraChokuDataInRecord rec) {

		// 依頼主名＝届け先名かつ商品区分、黄桜事業所区分、用途区分が以下の場合
		if (rec.getIrainusiNm().equals(rec.getTodokesakiNm())) {
			// 依頼主名と届け先名が同じで商品区分が T、P以外 かつ 営業所区分が Y以外 かつ 用途区分が 6以外 の時
			if  (!PbsUtil.isIncluded(rec.getShoninGrpCd(),KURAC_SHOHINGRP_KB_TANPIN_SUMMER, KURAC_SHOHINGRP_KB_TANPIN_WINTER)
			&& (!KURAC_SITEN_KB_KOURI.equals(rec.getJigyosyoKbn()))
			&& (!KURAC_NOSIYOUTO_KB_OTHER.equals(rec.getYoutoKbn()))) {

				return false;
			}
		}

		return true;
	}

//	/**
//	 * 依頼主、届け先名称の関連チェック
//	 *
//	 * @param rec 対象レコード
//	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
//	 */
//	private boolean checkXIraisyu(KuracKuraChokuDataInRecord rec) {
//		// 依頼主名＝届け先名かつ用途区分が以下の場合
//		if (rec.getIrainusiNm().equals(rec.getTodokesakiNm())
//				&& PbsUtil.isIncluded(rec.getYoutoKbn(),
//						KURAC_NOSIYOUTO_KB_MUJI,
//						KURAC_NOSIYOUTO_KB_CYUGEN_SEIBO,
//						KURAC_NOSIYOUTO_KB_SOSINA,
//						KURAC_NOSIYOUTO_KB_SYOCYU_ONREI,
//						KURAC_NOSIYOUTO_KB_OTHER)) {
//
//			return false;
//		}
//
//		return true;
//	}

	/**
	 * セット（本）数 チェック
	 *
	 * @param rec 対象レコード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkXSetSu(String shohinGrp, KuracKuraChokuDataInDtList editDtList) {

//		// 商品グループ区分が以下の場合
//		if (PbsUtil.isIncluded(shohinGrp, KURAC_SHOHINGRP_KB_TUJYO_SUMMER, KURAC_SHOHINGRP_KB_TANPIN_SUMMER,
//				KURAC_SHOHINGRP_KB_TUJYO_WINTER, KURAC_SHOHINGRP_KB_TANPIN_WINTER, KURAC_SHOHINGRP_KB_FATHER_DAY,
//				KURAC_SHOHINGRP_KB_MOTHER_DAY, KURAC_SHOHINGRP_KB_TANPIN_OTHER)) {
//
//			// アイテム３セット
//			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(2);
//			// アイテム３セット（本）数≠０のときエラーとする
//			if (!PbsUtil.isZeroOrBlank(rec.getShohinSet())) {
//
//				rec.setShohinSetClass(STYLE_CLASS_ERROR);
//
//				return false;
//			}
//		}
//
//		// 商品グループ区分が以下の場合
//		if (PbsUtil.isIncluded(shohinGrp, KURAC_SHOHINGRP_KB_BEER_SUMMER_U2, KURAC_SHOHINGRP_KB_BEER_WINTER_U2)) {
//
//			// アイテム２セット
//			KuracKuraChokuDataInDtRecord rec2 = (KuracKuraChokuDataInDtRecord) editDtList.get(1);
//			// アイテム３セット
//			KuracKuraChokuDataInDtRecord rec3 = (KuracKuraChokuDataInDtRecord) editDtList.get(2);
//
//			// アイテム２セット（本）数≠０又はアイテム３セット（本）数≠０のときエラーとする
//			if (!PbsUtil.isZeroOrBlank(rec2.getShohinSet()) || !PbsUtil.isZeroOrBlank(rec3.getShohinSet())) {
//
//				rec2.setShohinSetClass(STYLE_CLASS_ERROR);
//
//				return false;
//			}
//		}
		Integer sumSetsu = 0;

		// 商品グループ区分が以下の場合
		if (PbsUtil.isIncluded(shohinGrp, KURAC_SHOHINGRP_KB_TANPIN_SUMMER, KURAC_SHOHINGRP_KB_TANPIN_WINTER,
								 KURAC_SHOHINGRP_KB_FATHER_DAY, KURAC_SHOHINGRP_KB_MOTHER_DAY)) {

			for (int i = 0; i < editDtList.size(); i++) {
				KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

				// 商品コードがある場合
				if (!PbsUtil.isEmpty(rec.getShohinCd())) {
					if (!PbsUtil.isZeroOrBlank(rec.getShohinSet())) {
						// セット数の合計
						sumSetsu = sumSetsu + Integer.parseInt(rec.getShohinSet());
					}
				}
			}

			// アイテムセット（本）数の合計）≧10のときエラーとする
			if (sumSetsu >= 10) {
				KuracKuraChokuDataInDtRecord rec1 = (KuracKuraChokuDataInDtRecord) editDtList.get(0);
				rec1.setShohinSetClass(STYLE_CLASS_ERROR);

				return false;
			}
		}

		return true;
	}


// ==============================
// ====    その他の値設定    ====
// ==============================

	/**
	 * その他の値を設定する。
	 *
	 * @param editForm 編集フォーム
	 * @param editList ヘッダー部リスト
	 * @param editDtList ディテール部リスト
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean setOthers(KuracKuraChokuDataIn_EditForm editForm, KuracKuraChokuDataInList editList,
			KuracKuraChokuDataInDtList editDtList) {

		KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) editList.getFirstRecord();

		// ディテール部の表示用情報を設定する
		editDtList.setDtInfo();

		// 参照追加の場合
		if (PbsUtil.isIncluded(editForm.getMode(), MODE_ADD, MODE_REFERENCE)) {
			// 商品区分
			String shohinGrp = rec.getShoninGrpCd();

			// 商品区分がT,P,F,Mの時、酒販店の内容をセットする
			if (PbsUtil.isIncluded(shohinGrp, KURAC_SHOHINGRP_KB_TANPIN_SUMMER,
						KURAC_SHOHINGRP_KB_TANPIN_WINTER, KURAC_SHOHINGRP_KB_FATHER_DAY,
								KURAC_SHOHINGRP_KB_MOTHER_DAY)) {
				// 依頼主 依頼主名
				rec.setIrainusiNm(rec.getSyuhantenNm());
				// 依頼主 TEL
				rec.setIrainusiTel(rec.getSyuhantenTel());
				// 依頼主 住所
				rec.setIrainusiAddress(rec.getSyuhantenAddress());
				// 依頼主 郵便番号
				rec.setIrainusiZip(rec.getSyuhantenZip());
			}
		}

		// 訂正の場合
		if (PbsUtil.isIncluded(editForm.getMode(), MODE_REFPLUS, MODE_REFMINUS)) {

			// 元ﾃﾞｰﾀの訂正時訂正元蔵直ﾃﾞｰﾀ連番＝NULLの時、元ﾃﾞｰﾀの蔵直ﾃﾞｰﾀ連番をセット
			if (PbsUtil.isEmpty(rec.getTeiseiKuradataNo())) {
				// 訂正時訂正元蔵直ﾃﾞｰﾀ連番
				rec.setTeiseiKuradataNo(rec.getKuradataNo());
			}

			// 訂正用情報を設定する
			setDtTeiseiInfo(rec, editDtList);
		}

		return true;
	}

	/**
	 * ヘッダー部共通項目の単体チェック
	 * @param rec
	 * @param kaisyaCd
	 * @param lineRes
	 * @return
	 */
	private boolean checkItemCommon(KuracKuraChokuDataInRecord rec, String kaisyaCd, boolean lineRes) {

		// 蔵直商品ｸﾞﾙｰﾌﾟCD
		String shohinGrpCd = rec.getShoninGrpCd();

		// 縦線CD
		if (!checkTatesnCd(rec, kaisyaCd, false)) {
			rec.setTatesnCdClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}

		// 酒販店（統一）CD
		if (!checkSyuhantenCd(rec, kaisyaCd, false)) {
			rec.setSyuhantenCdClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}

		// 運送店CD
		if (!checkUnsotenCd(rec, kaisyaCd, false)) {
			rec.setUnsotenCdClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}

		// のし名前記載をチェック
		if (!checkNoshiKbn(rec)) {
			//rec.setNosikbnClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}


		// 届け先内容のチェック
		// 氏名
		if (!checkTodokesakiShimei(rec.getTodokesakiNm())) {
			rec.setTodokesakiNmClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}
		// TEL
		if (!checkTodokesakiTel(rec.getTodokesakiTel())) {
			rec.setTodokesakiTelClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}
		// 住所
		if (!checkTodokesakiAdress(rec.getTodokesakiAddress())) {
			rec.setTodokesakiAddressClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}
		// 郵便番号
		if (!checkTodokesakiZip(rec.getTodokesakiZip())) {
			rec.setTodokesakiZipClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}

		// 蔵直商品ｸﾞﾙｰﾌﾟCDはT(夏 単品) or P(冬 単品) or F(父の日) or M(母の日) 以外のときは
		// 依頼主内容のチェック
		if (!PbsUtil.isIncluded(shohinGrpCd, KURAC_SHOHINGRP_KB_TANPIN_SUMMER,
				KURAC_SHOHINGRP_KB_TANPIN_WINTER, KURAC_SHOHINGRP_KB_FATHER_DAY,
						KURAC_SHOHINGRP_KB_MOTHER_DAY)) {

			// 氏名
			if (!checkIraisyuShimei(rec.getIrainusiNm())) {
				rec.setIrainusiNmClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}
			// TEL
			if (!checkIraisyuTel(rec.getIrainusiTel())) {
				rec.setIrainusiTelClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}
			// 住所
			if (!checkIraisyuAdress(rec.getIrainusiAddress())) {
				rec.setIrainusiAddressClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}
			// 郵便番号
			if (!checkIraisyuZip(rec.getIrainusiZip())) {
				rec.setIrainusiZipClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}
		}

		return lineRes;
	}

	/**
	 * ディテール部共通項目の単体チェック
	 * @param rec
	 * @param lineRes
	 * @return
	 */
	private boolean checkDtItemCommon(KuracKuraChokuDataInDtRecord rec, Boolean lineRes) {

		// セット数
		if (!checkSetSu(rec.getShohinSet())) {
			rec.setShohinSetClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}

		// 販売単価
		if (!checkHanbaiTanka(rec.getHanbaiTanka())) {
			rec.setHanbaiTankaClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}

		return lineRes;
	}

	/**
	 * ヘッダー部共通関連チェック
	 * @param rec
	 * @param kaisyaCd
	 * @param lineRes
	 * @return
	 */
	private boolean checkXItemCommon(KuracKuraChokuDataInRecord rec, String kaisyaCd, Boolean lineRes) throws KitException{

		// 発送予定日 チェック
//		if (!checkXHasoYoteiDt(rec)) {
//			setErrorMessageId("errors.found.mistake",PbsUtil.getMessageResourceString("com.text.hasoyoteibi"));
//
//			rec.setHasoYoteiDtClass(STYLE_CLASS_ERROR);
//			lineRes = false;
//		}

		// 卸店ﾏｽﾀｰ チェック
		if (!checkXOrositenInfo(rec, kaisyaCd)) {
			setErrorMessageId("errors.found.mistake",PbsUtil.getMessageResourceString("com.text.syuhantenCd"));

			rec.setSyuhantenCdClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}

		// 酒販店マスタ チェック
		if (!checkXSyuhanten(rec)) {
			setErrorMessageId("errors.found.mistake",PbsUtil.getMessageResourceString("com.text.syuhantenNm"));

			rec.setSyuhantenCdClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}

		// のし区分 チェック
		if (!checkXNoshiKbn(rec)) {
			setErrorMessageId("errors.found.mistake",PbsUtil.getMessageResourceString("com.text.nosikisai"));

			rec.setNosikbnClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}

		// のし名用途区分 チェック
		if (!checkXYotoKbn(rec)) {
			setErrorMessageId("errors.found.mistake",PbsUtil.getMessageResourceString("com.text.youtoKbn"));

			rec.setNosikbnClass(STYLE_CLASS_ERROR);
			rec.setYoutoKbnClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}

//		// 依頼主：届け先名称比較 チェック
//		if (!checkXIraisyu(rec)) {
//			setErrorMessageId("errors.found.mistake",PbsUtil.getMessageResourceString("com.text.youtoKbn"));
//
//			rec.setIrainusiNmClass(STYLE_CLASS_ERROR);
//			lineRes = false;
//		}

		// 届先住所チェック
		if (!checkXTodokesakiAddress(rec)) {
			setErrorMessageId("errors.found.mistake",PbsUtil.getMessageResourceString("com.text.tdksk") + PbsUtil.getMessageResourceString("com.text.jyusyo"));

			rec.setTodokesakiAddressClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}

		// 特約店コードチェック
		if (!checkXTokuyakutenCd(rec)) {
			setErrorMessageId("errors.found.mistake",PbsUtil.getMessageResourceString("com.text.tokuyaku1jitenCd"));

			rec.setTokuyakutenCdClass(STYLE_CLASS_ERROR);
			lineRes = false;
		}

		// 包装 チェック
		if (!checkXHousou(rec)) {
			setErrorMessageId("errors.found.mistake",PbsUtil.getMessageResourceString("com.text.shimei"));

			rec.setTodokesakiNmClass(STYLE_CLASS_ERROR);
			rec.setIrainusiNmClass(STYLE_CLASS_ERROR);

			lineRes = false;
		}

		return lineRes;
	}

	// =========================================================
	// ==== 情報取得（AJAX用）====
	// =========================================================
	/**
	  * [縦線CD]から縦線情報を取得する
	  *
	  * @param tatesnCd 縦線CD
	  * @param kaisyaCd 会社CD
	  * @return 縦線情報
	  */
	 public MastrTatesenOrositenRecord getOrositenInfoByTatesnCd(String tatesnCd, String kaisyaCd) {

	  // 縦線卸店検索サービス
	 KuracKuraChokuDataIn_SearchService searchServ = new KuracKuraChokuDataIn_SearchService(
	    getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		MastrTatesenOrositenRecord tatesenRec = searchServ.getRecMastrTatesenOrositen(tatesnCd, false);
		if (tatesenRec == null) {
			tatesenRec = new MastrTatesenOrositenRecord();
			String noVal = PbsUtil.getMessageResourceString("com.text.noValue"); // ＊該当なし＊
			tatesenRec.setOrositenNm1JitenRyaku(noVal);			// 特約店名
			tatesenRec.setOrositenNmDepoRyaku(noVal);			// デポ店名
			tatesenRec.setOrositenNm2JitenRyaku(noVal);			// 二次店名
			tatesenRec.setOrositenNm3JitenRyaku(noVal);			// 三次店名
		}
		return tatesenRec;
	}

	/**
	 * 酒販店CDから酒販店情報を取得する。
	 *
	 * @param syuhantenCd 酒販店CD
	 * @param kaisyaCd 会社CD
	 * @param blankAtErr 取得不可時に空白で返すフラグ（true：空白、false：＊不可理由文字列＊）
	 * @return 酒販店名
	 */
	protected FbMastrSyuhantenRecord getSyuhantenInfoByCd(String syuhantenCd, boolean blankAtErr) {

		String ret = "";

		// 存在チェック
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrSyuhantenRecord zRec = rUtil.getRecMastrSyuhanten(IS_DELETED_HELD_TRUE, syuhantenCd);
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			if (!blankAtErr) {
				ret = PbsUtil.getMessageResourceString("com.text.noValue"); // ＊該当なし＊
				// 店名を設定
				zRec.setTenNmYago(ret);
				zRec.setTel(CHAR_BLANK);
				zRec.setAddress(CHAR_BLANK);
				zRec.setZip(CHAR_BLANK);
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
					// 店名を設定
					zRec.setTenNmYago(ret);
					zRec.setTel(CHAR_BLANK);
					zRec.setAddress(CHAR_BLANK);
					zRec.setZip(CHAR_BLANK);
				}
			}
		}

		return zRec;
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
	 * ディテール部の訂正用情報を設定する。
	 * @param hdRec
	 * @param editDtList
	 */
	public void setDtTeiseiInfo(KuracKuraChokuDataInRecord hdRec, KuracKuraChokuDataInDtList editDtList) {

		for (int i = 0; i < editDtList.size(); i++) {

			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

			// 未入力行はスキップ
			if (rec.isEmpty()) {
				continue;
			}

			// 元明細ﾃﾞｰﾀの訂正元伝票No＝NULLの時、元ﾃﾞｰﾀの蔵直ﾃﾞｰﾀ連番をセット
			if (PbsUtil.isEmpty(rec.getTeiseiUridenNo())) {
				rec.setTeiseiUridenNo(hdRec.getKuradataNo());
			}

			// 訂正元伝票日付
			rec.setTeiseiSyukaDt(PbsUtil.getYMD(PbsUtil.getCurrentDate()));
		}

	}

	/**
	 * nullを空文字に変換
	 * @param obj
	 * @return
	 */
	public String nullToString(String obj) {
		return obj == null ? "" :  obj;
	}
} // -- class
