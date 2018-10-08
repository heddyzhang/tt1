package kit.juchu.TuminiDenpyoHako;

import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;
import static fb.inf.IKitConst.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComKitUtil;
import fb.com.ComRecordUtil;
import fb.com.ComUserSession;
import fb.com.ComValidateUtil;
import fb.inf.KitService;
import fb.inf.exception.KitException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 入力内容をチェックするビジネスロジッククラスです
 */
public class JuchuTuminiDenpyoHako_CheckService extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = -4840655334525505015L;

	/** クラス名. */
	private static String className__ = JuchuTuminiDenpyoHako_CheckService.class.getName();

	/** カテゴリ. */
	public static Category category__ = Category.getInstance(className__);

	/** データベースオブジェクト */
	private PbsDatabase db_;

	/** 選択されたレコード */
	private JuchuTuminiDenpyoHakoRecord recSelected;

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
	public JuchuTuminiDenpyoHako_CheckService(ComUserSession cus, PbsDatabase db_, boolean isTran,
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
	public JuchuTuminiDenpyoHakoRecord getRecSelected() {
		return new JuchuTuminiDenpyoHakoRecord(recSelected.getHashMap());
	}


	/**
	 * 伝票発行ボタン押下時チェック
	 *
	 * @param editForm
	 * @param editList
	 * @param syuyakuList
	 * @return TRUE / FALSE
	 */
	public boolean validatePrint(JuchuTuminiDenpyoHako_EditForm editForm,
			JuchuTuminiDenpyoHakoList editList,
			JuchuTuminiDenpyoHakoList syuyakuList) throws KitException {

		/* *******************************
		 * 選択有無チェック
		 * ****************************** */
		if (!inputCheckRequiredForPrint(editForm, editList, syuyakuList)) {
			return false;
		}

		/* ******************************
		 * TODO:権限チェック
		 ********************************/

		// 戻るボタンのTabIndexを設定する
		editForm.setTabIndexBack(TAB_INDEX_HEAD);

		return true;

	}


	/**
	 * 伝票発行前必須チェック
	 *
	 * @param editForm
	 * @param editList
	 * @param syuyakuList
	 * @return TRUE / FALSE
	 */
	private boolean inputCheckRequiredForPrint(JuchuTuminiDenpyoHako_EditForm editForm,
			JuchuTuminiDenpyoHakoList editList,
			JuchuTuminiDenpyoHakoList syuyakuList) throws KitException {

		// プリンタ選択を確認（選択リストなので、値なしは使用可能プリンタなしでエラーとする）
		if (PbsUtil.isEmpty(editForm.getPrinterId())) {
			// 使用可能なプリンタがありません。
			setErrorMessageId("errors.noPrinter");
			return false;
		}

		// 伝票発行対象を確認
		if (editList.isEmpty()) {
			// なにも選択されていません。
			setErrorMessageId("errors.empty.selectedRecord");
			return false;
		}

		return true;
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
	public boolean validateSearch(JuchuTuminiDenpyoHako_SearchForm searchForm) {

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		if (!inputCheckKengen(searchForm)) {
			return false;
		}

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


	// === private =============================

	/**
	 * 呼出時権限チェックを行う
	 *
	 * @param searchForm
	 * @return
	 */
	private boolean inputCheckKengen(JuchuTuminiDenpyoHako_SearchForm searchForm) {
		boolean res = true;

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
	private boolean inputCheckRequiredForSearch(JuchuTuminiDenpyoHako_SearchForm searchForm) {

		String msgKey_itemNm = "";

		// 出荷日（必須チェックあり）
		msgKey_itemNm = "com.text.syukaDt";
		if (!vUtil.validateDateSimple(searchForm.getSyukaDt(), msgKey_itemNm)) {
			searchForm.setSyukaDtClass(STYLE_CLASS_ERROR);
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
	private boolean inputCheckForSearch(JuchuTuminiDenpyoHako_SearchForm searchForm) {

		return true;
	}


// ==================================
// ==== パーツごとの単体チェック ====
// ==================================


// ==============================
// ====    その他の値設定    ====
// ==============================


// =============================
// ====    関連チェック     ====
// =============================

	/**
	 * 伝票発行対象の運送店CD別重量計算を行う
	 *
	 * @param kobetuList 個別リスト
	 * @param syuyakuList 集約リスト
	 * @return 運送店CD別重量計リスト
	 */
	public JuchuTuminiDenpyoHakoList calcWeight(JuchuTuminiDenpyoHakoList kobetuList, JuchuTuminiDenpyoHakoList syuyakuList) {
		JuchuTuminiDenpyoHakoList wList = new JuchuTuminiDenpyoHakoList();
		JuchuTuminiDenpyoHakoRecord wRec = null;

		// ----------
		// 個別
		// ----------
		for (PbsRecord tmp : kobetuList) {
			JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord) tmp;

			wRec = new JuchuTuminiDenpyoHakoRecord();
			wRec.setSyuyakuKbn(CHECKBOX_ON); // 集約区分
			wRec.setUnsotenCd(rec.getUnsotenCd()); // 運送店CD
			wRec.setUnsotenNm(rec.getUnsotenNm()); // 運送店名
			wRec.setJyuryoTot(rec.getJyuryoTot()); // 重量計(KG)
			wRec.setJyucyuNo(rec.getJyucyuNo()); // 対象受注NO

			wList.add(wRec);
		}

		// ----------
		// 集約
		// ----------
		Map<String,String> wMap = new HashMap<String,String>(); // key：運送店CD、value：重量計(KG)
		Map<String,String> nMap = new HashMap<String,String>(); // key：運送店CD、value：運送店名
		Map<String,String> jMap = new HashMap<String,String>(); // key：運送店CD、value：対象受注NO
		String unsotenCd = "";
		String weight = "";
		String sumWeight = SU_ZERO;
		StringBuilder sbJyucyuNos = null;
		for (PbsRecord tmp : syuyakuList) {
			JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord) tmp;

			// 運送店CD
			unsotenCd = rec.getUnsotenCd();

			// 受注NO毎の重量計(KG)
			weight = rec.getJyuryoTot();
			if (PbsUtil.isEmpty(weight)) { weight = SU_ZERO; }

			// 運送店毎の重量計(KG)
			sumWeight = wMap.get(unsotenCd); // 現在の重量計(KG)
			if (PbsUtil.isEmpty(sumWeight)) { sumWeight = SU_ZERO; }

			sumWeight = PbsUtil.sToSAddition(sumWeight, weight);

			// 対象受注NO
			sbJyucyuNos = new StringBuilder();
			if (PbsUtil.isEmpty(jMap.get(unsotenCd))) { // 現在の対象受注NO
				sbJyucyuNos.append(rec.getJyucyuNo());
			} else {
				sbJyucyuNos.append(jMap.get(unsotenCd));
				sbJyucyuNos.append("," + rec.getJyucyuNo());
			}

			wMap.put(unsotenCd, sumWeight);
			nMap.put(unsotenCd, rec.getUnsotenNm());
			jMap.put(unsotenCd, sbJyucyuNos.toString());
		}

		List<String> uList = getUniqueUnsotenCds(syuyakuList);

		for (String tmp : uList) {
			unsotenCd = (String) tmp;

			wRec = new JuchuTuminiDenpyoHakoRecord();
			wRec.setSyuyakuKbn(CHAR_BLANK); // 集約区分
			wRec.setUnsotenCd(unsotenCd); // 運送店CD
			wRec.setUnsotenNm(nMap.get(unsotenCd)); // 運送店名
			wRec.setJyuryoTot(wMap.get(unsotenCd)); // 重量計(KG)
			wRec.setJyucyuNo(jMap.get(unsotenCd)); // 対象受注NO

			wList.add(wRec);
		}

		// 運送店CDでソート
		ComKitUtil.sortPbsRecordListAsc(wList, "UNSOTEN_CD");

		return wList;
	}

	/**
	 * 集約リストから一意な運送店CDを取得する
	 *
	 * @param syuyakuList 集約リスト
	 * @return 一意な運送店CDリスト
	 */
	protected List<String> getUniqueUnsotenCds(JuchuTuminiDenpyoHakoList syuyakuList) {

		Set<String> set = new HashSet<String>();
		for (PbsRecord tmp : syuyakuList) {
			JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord) tmp;
			set.add(rec.getUnsotenCd()); // セットに運送店CDが無い場合に追加
		}

		List<String> uniqueUnsotenCdList = new ArrayList<String>();
		uniqueUnsotenCdList.addAll(set);

		return uniqueUnsotenCdList;
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
