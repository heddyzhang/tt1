package kit.edi.EdiJuchuMainte;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.com.IKitComErrorConst.*;
import static fb.com.SerialNumber.ComJuchuRnbnKanri.*;
import static fb.inf.pbs.IPbsConst.*;
import static kit.edi.EdiJuchuMainte.IEdiJuchuMainte.*;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComRecordUtil;
import fb.com.ComUserSession;
import fb.com.ComUtil;
import fb.com.ComValidateUtil;
import fb.com.Records.FbMastrOrosisyosaiHdRecord;
import fb.com.Records.FbMastrOrositenRecord;
import fb.com.Records.FbMastrSyohinRecord;
import fb.inf.KitService;
import fb.inf.exception.KitException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 入力内容をチェックするビジネスロジッククラスです
 */
public class EdiJuchuMainte_CheckService extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = 1517992228429564650L;

	/** クラス名. */
	private static String className__ = EdiJuchuMainte_CheckService.class.getName();

	/** カテゴリ. */
	public static Category category__ = Category.getInstance(className__);

	/** データベースオブジェクト */
	private PbsDatabase db_;

	/** 選択されたレコード */
	private EdiJuchuMainteRecord recSelected;

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
	public EdiJuchuMainte_CheckService(ComUserSession cus, PbsDatabase db_, boolean isTran,
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
	public EdiJuchuMainteRecord getRecSelected() {
		return new EdiJuchuMainteRecord(recSelected.getHashMap());
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
	public boolean validateSearch(EdiJuchuMainte_SearchForm searchForm) {

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

	/**
	 * セットボタン押下時チェック
	 *
	 * @param editForm
	 * @param searchedList
	 * @return TRUE / FALSE
	 */
	public boolean validateSelect(EdiJuchuMainte_EditForm editForm, EdiJuchuMainteList searchedList) throws KitException {

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
	public void validateSelectBtnAvailFlg(EdiJuchuMainteList editList, EdiJuchuMainte_SearchForm searchForm) {

		EdiJuchuMainteRecord rec = (EdiJuchuMainteRecord) editList.getFirstRecord();

		// EDI受発注ﾃﾞｰﾀ_ﾍｯﾀﾞｰの処理区分が"2"（取込済）の時、非活性とする
		if (PbsUtil.isEqual(rec.getSyoriKbn(), EDI_SHORI_KB_TORIZUMI)) {
			// 修正ボタン
			searchForm.setEditBtnAvailFlg(BUTTON_DISABLED_FALSE); // 活性
		} else {
			// 修正ボタン
			searchForm.setEditBtnAvailFlg(BUTTON_AVAILABLE_TRUE); // 活性
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
	public boolean validateManipulationEdit(EdiJuchuMainteList editList, EdiJuchuMainteDtList editDtList,
			EdiJuchuMainteList selectedList) throws KitException {

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

		return true;
	}

	/**
	 * 修正時チェック
	 *
	 * @param editList
	 * @param editDtList
	 * @return true(エラー無) or false(エラー有)
	 */
	public boolean validateConfirmAsUpdate(EdiJuchuMainteList editList, EdiJuchuMainteDtList editDtList,
			EdiJuchuMainteList initList, EdiJuchuMainteDtList initDtList) throws KitException {

		// ==================================================
		// フラグを初期化
		// ==================================================
		editList.initFlgs(); // ヘッダー部
		editDtList.initFlgs(); // ディテール部

		EdiJuchuMainteRecord hdRec = (EdiJuchuMainteRecord) editList.getFirstRecord();
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
				hdRec.setModified();
			}
		}

		// ==================================================
		// 項目の単体チェックを行う（ヘッダー部）
		// ==================================================
		if (!inputCheckForEdit(hdRec)) {
			return false;
		}

		// ==================================================
		// 項目の単体チェックを行う（ディテール部）
		// ==================================================
		if (!inputCheckForEdit(editDtList)) {
			return false;
		}

		// ==================================================
		// 関連チェックを行う（ヘッダー部）
		// ==================================================
		if (!inputCheckXForEdit(hdRec)) {
			return false;
		}

		// ==================================================
		// その他の値設定
		// ==================================================
		if (!setOthers(hdRec, editDtList)) {
			return false;
		}

		return true;
	}

	// === private =============================

	// ==============================
	// ==== 必須チェックシリーズ ====
	// ==============================
	/**
	 * 必須チェックを行う（呼出用）
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean inputCheckRequiredForSearch(EdiJuchuMainte_SearchForm searchForm) {

		// 受信日
		String msgKey_itemNm = "";

		// 受信日
		msgKey_itemNm = "com.text.jyusinbi";
		if (!vUtil.validateDateSimple(searchForm.getJyusinDt(), msgKey_itemNm)) {
			return false;
		}

		return true;
	}

	/**
	 * 属性チェックを行う（呼出用）
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean inputCheckForSearch(EdiJuchuMainte_SearchForm searchForm) {
		// 受信開始時間
		String jyusinTmForm = searchForm.getJyusinTmFrom();
		// 受信終了時間
		String jyusinTmTo = searchForm.getJyusinTmTo();

		// 受信終了時間が入力した場合
		if (!PbsUtil.isEmpty(jyusinTmTo)) {
			if (!checkJyusinTmForm(jyusinTmForm)) {
				return false;
			}
		}

		// 受信開始時間と終了時間の両方が入力されている場合は、時間の矛盾チェック
		if (!PbsUtil.isEmpty(jyusinTmForm) && !PbsUtil.isEmpty(jyusinTmTo)) {
			if (!(Long.valueOf(jyusinTmTo) >= Long.valueOf(jyusinTmForm))) {
				// エラーメッセージ
				setErrorMessageId("errors.input.after",
						PbsUtil.getMessageResourceString("com.text.jyusinEndTime"),
						PbsUtil.getMessageResourceString("com.text.jyusinStartTime"));
				return false;
			}
		}

		return true;
	}

	/**
	 * 受信開始時間の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkJyusinTmForm(String data) {
		String msgKey_itemNm = "com.text.jyusinStartTime";

		// 必須チェック
		if (!vUtil.validateRequired(data, msgKey_itemNm)) {
			return false;
		}

//		// 型チェック 必須項目
//		if(!vUtil.validateTime(data, msgKey_itemNm)){
//			return false;
//		}

		return true;
	}

	/**
	 * 操作系画面呼出し時のチェックを行う
	 *
	 * @param editList … 修正対象のリスト
	 * @param editDtList … 修正対象のリスト
	 * @param selectedList … 呼び出した結果のリスト
	 * @return TRUE / FALSE
	 */
	private boolean inputCheckForManipulation(EdiJuchuMainteList editList,
			EdiJuchuMainteDtList editDtList, EdiJuchuMainteList selectedList) {

		if (PbsUtil.isListEmpty(editList) || PbsUtil.isListEmpty(editDtList) || PbsUtil.isListEmpty(selectedList)) {
			// 予期せぬエラーが発生しました。呼出しからやり直してください。
			setErrorMessageId("errors.TryAgainFromTheSearching");
			return false;
		}

		return true;
	}

	/**
	 * 変更時、変更入力有無チェック（ヘッダー部）
	 *
	 * @param initList
	 * @param editList
	 * @return TRUE:どこか変更されました。 / FALSE:何も変更されていません。
	 */
	private boolean inputCheckNoEntryForEdit(EdiJuchuMainteList initList, EdiJuchuMainteList editList) {

		boolean res = false;

		for (int i = 0; i < editList.size(); i++) {
			EdiJuchuMainteRecord iRec = (EdiJuchuMainteRecord) initList.get(i);
			EdiJuchuMainteRecord eRec = (EdiJuchuMainteRecord) editList.get(i);

			// 変更行を発見したらTRUEを返す。
			if (!eRec.equals(iRec)) {
				res = true;
				eRec.setModified();
			}
		}

		return res;
	}

	/**
	 * 変更時、変更入力有無チェック（ディテール部）
	 *
	 * @param initDtList
	 * @param editDtList
	 * @return TRUE:どこか変更されました。 / FALSE:何も変更されていません。
	 */
	private boolean inputCheckNoEntryForEdit(EdiJuchuMainteDtList initDtList, EdiJuchuMainteDtList editDtList) {

		boolean res = false;

		for (int i = 0; i < editDtList.size(); i++) {
			EdiJuchuMainteDtRecord iRec = (EdiJuchuMainteDtRecord) initDtList.get(i);
			EdiJuchuMainteDtRecord eRec = (EdiJuchuMainteDtRecord) editDtList.get(i);

			// 変更行を発見したらTRUEを返す。
			if (!eRec.equals(iRec)) {
				res = true;
				eRec.setModified();
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
	private boolean inputCheckForEdit(EdiJuchuMainteRecord rec) throws KitException {

		boolean res = true;

		// 【変換後】kz出荷予定日
		if (!checkSyukaYoteiDt(rec.getSyukaYoteiDt())) {
			rec.setSyukaYoteiDtClass(STYLE_CLASS_ERROR);
			res = false;
		}

//		// 【変換後】ﾐﾅｼ日付
//		if (!checkMinasiDt(rec.getMinasiDt())) {
//			rec.setMinasiDtClass(STYLE_CLASS_ERROR);
//			res = false;
//		}

		// Kz縦線CD
		if (!checkTatesnCd(rec.getTatesnCd())) {
			rec.setTatesnCdClass(STYLE_CLASS_ERROR);
			res = false;
		}

		// @@@ 結果判定 @@@
		if (!res) {
			// 行の更新対象フラグを降ろす
			rec.setModified(IS_MODIFIED_FALSE);
		}

		return res;
	}

	/**
	 * 変更時：関連チェックをする（ヘッダー部）
	 *
	 * @param initList
	 * @param editList
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean inputCheckXForEdit(EdiJuchuMainteRecord rec) throws KitException {

		boolean res = true;

		// 処理区分の関連チェック
		if (!checkXSyoriKbn(rec.getSyoriKbnView(), rec.getSyoriKbn())) {

			String msgKey_itemNm = "com.text.syoriKb";

			rec.setSyoriKbnClass(STYLE_CLASS_ERROR);
			setErrorMessageId("errors.inputDataIsWrong",PbsUtil.getMessageResourceString(msgKey_itemNm));
			res = false;
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
	private boolean inputCheckForEdit(EdiJuchuMainteDtList editDtList) throws KitException {

		boolean res = true;
		boolean lineRes = true;

		for (int i = 0; i < editDtList.size(); i++) {

			lineRes = true;

			// 1行全体が未入力の場合は、追加対象外のためチェック不要
			EdiJuchuMainteDtRecord rec = (EdiJuchuMainteDtRecord) editDtList.get(i);

//			// 未入力行はスキップ
//			if (rec.isEmpty()) {
//				continue;
//			}

			// 商品CD
			if (!checkShohinCd(rec.getShohinCd())) {
				rec.setShohinCdClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// ケース
			if (!checkCaseSu(rec.getSyukaSuCase())) {
				rec.setSyukaSuCaseClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// バラ
			if (!checkBaraSu(rec.getSyukaSuBara())) {
				rec.setSyukaSuBaraClass(STYLE_CLASS_ERROR);
				lineRes = false;
			}

			// ケースとバラーの関連チェック
			if (!checkCaseBaraSu(rec, i + 1)) {
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
	private boolean inputCheckRequiredForSelect(EdiJuchuMainte_EditForm editForm,
								EdiJuchuMainteList editList) throws KitException {

		/* *******************************
		 * 選択有無チェック
		 * 1. editFormセット欄
		 * 2. 行選択ID
		 * 3. 更新対象フラグ
		 * ****************************** */

		for (;;) {
			// 1. editFormセット欄
			// 入力されていたら最優先でチェック
			if (!PbsUtil.isEmpty(editForm.getEdiJyuhacyuId())) {

				/* =============================
				 * 要注意ポイント(一覧から明細を取得するとき)
				 * 条件１、２のときは、前回選択された位置を
				 * リセットしないと複数行選択されてしまう。
				 * 条件３のときは、そのまま同じ行を取得して使用する
				 ============================= */

				// 更新対象フラグを初期化
				editList.initFlgs();

				// IDチェック
				if (!checkEdiJyuhacyuId(editForm.getEdiJyuhacyuId())) {
					editForm.setEdiJyuhacyuIdClass(STYLE_CLASS_ERROR);
					return false;
				}

				// IDでレコードを取得する
				recSelected = editList.getRecByCode(editForm.getEdiJyuhacyuId());

				// レコードを確認
				if (recSelected == null) {

					// 指定されたコードは、呼び出されていません。
					setErrorMessageId("errors.not.selected");
					editForm.setEdiJyuhacyuIdClass(STYLE_CLASS_ERROR);
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
				recSelected = (EdiJuchuMainteRecord) editList.getRecSelectedRow(editForm.getSelectedRowId());

				break;
			}

			// 3. 更新対象フラグ
			EdiJuchuMainteRecord rec = null;
			for (PbsRecord tmp : editList) {
				rec = (EdiJuchuMainteRecord) tmp;
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
			editForm.setEdiJyuhacyuIdClass(STYLE_CLASS_ERROR);
			return false;
		}

		// レコードがセットされたのでTRUEで抜ける
		recSelected.setModified(IS_MODIFIED_TRUE);

		return true;

	}

	// ==============================
	// ====    その他の値設定    ====
	// ==============================
	/**
	 * その他の値を設定する。
	 * @param hdRec
	 * @return
	 */
	private boolean setOthers(EdiJuchuMainteRecord hdRec, EdiJuchuMainteDtList ediJuchuMainteDtList) {

		// 縦線CD
		String tatesenCd = hdRec.getTatesnCd();
		// 【変換後】KZ受注NO
		String jyucyuNo = hdRec.getJyucyuNo();
		// 採番後のKZ受注NO
		String nextJyucyuNo = "";

		try {

			// KZ受注NOが空白または縦線CDが更新した場合
			if (PbsUtil.isEmpty(jyucyuNo) || !PbsUtil.isEqual(tatesenCd, hdRec.getTatesnCdView())) {
				// 検索サービス
				EdiJuchuMainte_SearchService searchServ = new EdiJuchuMainte_SearchService(
						getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

				// ユーザー情報
				ComUserSession cus = getComUserSession();
				// 会社CD
				String kaisyaCd = cus.getKaisyaCd();
				// 受注No採番用管轄事業所ｷｰ
				String juchuMngKey = searchServ.getJuchuMngKey(tatesenCd, kaisyaCd);

				if (PbsUtil.isEmpty(jyucyuNo) || !PbsUtil.isEqual(jyucyuNo.substring(0, 1), juchuMngKey)) {
					// 受注NO採番
					nextJyucyuNo = nextNumber(getDatabase(), cus.getKaisyaCd(), juchuMngKey, hdRec.getOnlineJyusinKaisu());
					hdRec.setJyucyuNo(nextJyucyuNo);
				}
			}


			// 検索サービス
			EdiJuchuMainte_SearchService searchServ = new EdiJuchuMainte_SearchService(
					getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
			// 卸店情報を取得する
			EdiJuchuMainteRecord orosiRec = searchServ.getOrosiInfoByTatesnCd(tatesenCd);

			// 最終送荷先卸CD
			hdRec.setOrositenCdLast(orosiRec.getOrositenCdLast());
			// EDI受発注商品摘要欄確認区分
			hdRec.setEdiJsyohinTekiyochekKbn(nullToString(orosiRec.getEdiJsyohinTekiyochekKbn()));

			// 許容日に関するディテール部の情報を更新
			updKyobiLifeDt(hdRec, ediJuchuMainteDtList);

			// 処理区分が１：未取込の場合エラー区分をクリア
			if (EDI_SHORI_KB_MITORI.equals(hdRec.getSyoriKbn())) {

				// ヘッダ部のエラー情報を更新
				hdRec.setErrorKbn(CHAR_BLANK);

				// ディテール部のエラー情報を更新
				for (PbsRecord rec: ediJuchuMainteDtList) {

					EdiJuchuMainteDtRecord dtRec = (EdiJuchuMainteDtRecord) rec;
					// エラー区分をクリア
					dtRec.setErrorKbn(CHAR_BLANK);
				}
			}


		}  catch (Exception e) {

		}

		return true;
	}

	// ==================================
	// ==== パーツごとの単体チェック ====
	// ==================================

	// ----- ヘッダー部 -----
	/**
	 * IDの単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkEdiJyuhacyuId(String data) {

		String msgKey_itemNm = "com.text.renban";

		// 必須チェック
		if (!vUtil.validateRequired(data, msgKey_itemNm)) {
			return false;
		}

		// 基本チェック
		if (!vUtil.validateNumeric(data, msgKey_itemNm)) {
			return false;
		}

		// 長さチェック
		if (!vUtil.validateLength(data, MAX_LEN_EDI_JYUHACYU_ID, msgKey_itemNm)) {
			return false;
		}

		return true;
	}

	/**
	 * 【変換後】kz出荷予定日の単体チェック
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @param kaisyaCd 会社コード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkSyukaYoteiDt(String data) {

		String msgKey_itemNm = "com.text.syukaYoteibi";

		// 基本チェック（必須チェック含む）
		if (!vUtil.validateDateSimple(data, IS_REQUIRED_TRUE, msgKey_itemNm)) {
			return false;
		}

		return true;
	}

//	/**
//	 * 【変換後】ﾐﾅｼ日付の単体チェック
//	 *
//	 * @param data チェック対象値
//	 * @param rec 名称セット対象レコード
//	 * @param kaisyaCd 会社コード
//	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
//	 */
//	private boolean checkMinasiDt(String data) {
//
//		String msgKey_itemNm = "com.text.minashibi";
//
//		// 基本チェック（必須チェック含む）
//		if (!vUtil.validateDateSimple(data, IS_REQUIRED_TRUE, msgKey_itemNm)) {
//			return false;
//		}
//
//		return true;
//	}

	/**
	 * 縦線CDの単体チェック
	 *
	 * @param data チェック対象値
	 * @param rec 名称セット対象レコード
	 * @param kaisyaCd 会社コード
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkTatesnCd(String data) {

		String msgKey_itemNm = "com.text.tatesenCd";
		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

        // 基本チェック（必須チェック含む）
		if (!vUtil.validateCodeSimple(data, CODE_LEN_ORS_TATESN, msgKey_itemNm)) {
			return false;
		}


		// 存在チェック なければエラー
		FbMastrOrosisyosaiHdRecord zRec = rUtil.getRecMastrOrosisyosaiHd(IS_DELETED_HELD_TRUE, kaisyaCd, data);
		if (!vUtil.validateMstExistence(zRec, msgKey_itemNm)) {
			return false;
		}

		// 利用状態チェック
		String riyouJotaiFlg = ComUtil.getRiyouJotaiFlg(cus.getGymDate(), zRec.getNouhinKaisiDt(),
				zRec.getNouhinSyuryoDt(), zRec.getRiyouTeisiDt());
		// 1：仮登録、2：利用可能のみ
		if (!PbsUtil.isIncluded(riyouJotaiFlg, DATA_JYOTAI_KB_KARI_TOROKU, DATA_JYOTAI_KB_RIYO_KA)) {

			setErrorMessageId("errors.input.riyouFuka.teisiOrDelete", PbsUtil.getMessageResourceString(msgKey_itemNm));
			return false;
		}

		return true;
	}


	// =============================
	// ====    関連チェック     ====
	// =============================
	/**
	 * 処理区分の関連チェック
	 * @param data
	 * @return
	 */
	private boolean checkXSyoriKbn(String oldData, String data) {

		// 処理区分がｴﾗｰの場合
		if (EDI_SHORI_KB_ERROR.equals(oldData)) {
			if (!PbsUtil.isIncluded(data, EDI_SHORI_KB_MITORI, EDI_SHORI_KB_ERROR)) {
				return false;
			}
		}

		// 処理区分が未取込の場合
		if (EDI_SHORI_KB_MITORI.equals(oldData)) {
			if (!PbsUtil.isIncluded(data, EDI_SHORI_KB_MITORI, EDI_SHORI_KB_ERROR)) {
				return false;
			}
		}

		return true;
	}


	/**
	 * 商品CDの単体チェック
	 * @param data
	 * @return
	 */
	private boolean checkShohinCd(String data) {

		String msgKey_itemNm = "com.text.kzShohinCD";
		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD
		String ktksyCd = cus.getKtksyCd(); // 寄託者コード

		// 基本チェック（必須チェック含む）
		if (!vUtil.validateCodeSimple(data, CODE_LEN_SHOHIN, msgKey_itemNm)) {
			return false;
		}

		// 存在チェック なければエラー
		FbMastrSyohinRecord zRec = rUtil.getRecMastrSyohin(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, data);
		if (!vUtil.validateMstExistence(zRec, msgKey_itemNm)) {
			return false;
		}

		// 利用状態チェック
		String riyouJotaiFlg = ComUtil.getRiyouJotaiFlg(cus.getGymDate(), zRec.getJyucyuKaisiDt(),
				zRec.getJyucyuSyuryoDt(), zRec.getRiyouTeisiDt());

		// 1：仮登録、2：利用可能のみ
		if (!PbsUtil.isIncluded(riyouJotaiFlg, DATA_JYOTAI_KB_KARI_TOROKU, DATA_JYOTAI_KB_RIYO_KA)) {

			setErrorMessageId("errors.input.riyouFuka.teisiOrDelete", PbsUtil.getMessageResourceString(msgKey_itemNm));
			return false;
		}

		return true;
	}

	/**
	 * ケース数の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkCaseSu(String data) {
		String msgKey_itemNm = "com.text.case";

		// 必須チェック
		if (!vUtil.validateRequired(data, msgKey_itemNm)) {
			return false;
		}

		// 型チェック
		if (!vUtil.validateNumeric(data, msgKey_itemNm)) {
			return false;
		}

		// 長さチェック
		if (!vUtil.validateLength(data, MAX_LEN_CASE_SU, msgKey_itemNm)) {
			return false;
		}

		return true;
	}

	/**
	 * バラ数の単体チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkBaraSu(String data) {
		String msgKey_itemNm = "com.text.bara";

		// 必須チェック
		if (!vUtil.validateRequired(data, msgKey_itemNm)) {
			return false;
		}

		// 型チェック
		if (!vUtil.validateNumeric(data, msgKey_itemNm)) {
			return false;
		}

		// 長さチェック
		if (!vUtil.validateLength(data, MAX_LEN_BARA_SU, msgKey_itemNm)) {
			return false;
		}

		return true;
	}

	/**
	 * ケース数とバラ数の関連チェック
	 *
	 * @param data チェック対象値
	 * @return TRUE:エラー無し / FALSE:何らかのエラー有り
	 */
	private boolean checkCaseBaraSu(EdiJuchuMainteDtRecord rec, int lineNo) {

		// ケース数とバラ数が全て0の場合
		if (PbsUtil.isZero(rec.getSyukaSuCase()) && PbsUtil.isZero(rec.getSyukaSuBara())) {

			String msgKey_itemNm = PbsUtil.getMessageResourceString(ERRORS_RECORD, lineNo);

			rec.setSyukaSuCaseClass(STYLE_CLASS_ERROR);
			rec.setSyukaSuBaraClass(STYLE_CLASS_ERROR);
			setErrorMessageId("errors.check.caseOrBaraRequired",msgKey_itemNm);

			return false;
		}

		return true;
	}

	/**
	 * 許容日に関するディテール部の情報を更新
	 * @param hdRec
	 * @param editDtList
	 */
	public void updKyobiLifeDt(EdiJuchuMainteRecord hdRec, EdiJuchuMainteDtList editDtList) {

		// 伝票番号
		String dhDenpyoNo = nullToString(hdRec.getDhDenpyoNo());
		// EDI受発注商品摘要欄確認区分
		String ediJsyohinTekiyochekKbnrec = hdRec.getEdiJsyohinTekiyochekKbn();

		for (int i = 0; i < editDtList.size(); i++) {

			EdiJuchuMainteDtRecord rec = (EdiJuchuMainteDtRecord) editDtList.get(i);

			// 摘要
			String tekiyou = nullToString(rec.getMrTekiyouView());

			// 許容日
			rec.setKyoyobi(CHAR_BLANK);
			// 摘要
			rec.setMrTekiyou(CHAR_BLANK);

			// ==================================================
			// 許容日とライフを設定
			// ==================================================
			// 伝票ヘッダーレコードの二次店コードが'27250760' OR '27250779'
			// もしくは伝票番号の先頭４桁が'6559'の場合
			if ("27250760".equals(hdRec.getDhNijitenCd()) || "27250779".equals(hdRec.getDhNijitenCd())
					|| (dhDenpyoNo.length() >=4  && "6559".equals(dhDenpyoNo.substring(0, 4)))) {
				// 摘要の８、９桁目を表示
				if (tekiyou.length() >= 9) {
					rec.setKyoyobi(tekiyou.substring(7,9));
				}

			// 卸店ﾏｽﾀｰ．EDI受発注商品摘要欄確認区分=1かつ
			// EDI受発注ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ．明細行レコード：摘要欄文字最後から3文字＝"ｲｺｳ"の場合
			} else if ("1".equals(ediJsyohinTekiyochekKbnrec) && tekiyou.length() >= 9
					&& TEKIYO_LAST_SANMOJI.equals(tekiyou.substring(tekiyou.length() - 3))) {

				// 許容日(6桁)
				String kyoyobi = tekiyou.substring(tekiyou.length() - 9, tekiyou.length() - 3);
				// 許容日を8桁に変換
				String yyyy = PbsUtil.toYYYY(kyoyobi.substring(0, 2));
				rec.setKyoyobi(yyyy + kyoyobi.substring(2));
			}

			// ==================================================
			// 摘要を設定
			// ==================================================
			// 卸店ﾏｽﾀｰ．EDI受発注商品摘要欄確認区分=1かつ
			// EDI受発注ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ．明細行レコード：摘要欄の最後から3文字≠"ｲｺｳ"の時、
			if ("1".equals(ediJsyohinTekiyochekKbnrec) && tekiyou.length() >= 3
					&& !TEKIYO_LAST_SANMOJI.equals(tekiyou.substring(tekiyou.length() - 3))) {
					// 摘要
					rec.setMrTekiyou(tekiyou);
			}
		}
	}

	/**
	 * 卸店CDから卸店情報を取得する。
	 *
	 * @param orosiCd 卸店CD
	 * @return FbMastrOrositenRecord 卸店情報
	 */
	protected FbMastrOrositenRecord getMastrOrosiInfoByCd(String orosiCd) {
		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD

		// 卸店マスターーを検索
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrOrositenRecord zRec = rUtil.getRecMastrOrositen(IS_DELETED_HELD_TRUE, kaisyaCd, orosiCd);

		return zRec;
	}

	/**
	 * nullを空文字に変換
	 * @param obj
	 * @return
	 */
	public String nullToString(String obj) {
		return obj == null ? "" :  obj;
	}
}


