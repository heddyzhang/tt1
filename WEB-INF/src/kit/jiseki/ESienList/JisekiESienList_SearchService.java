package kit.jiseki.ESienList;

import static fb.com.IKitComConst.AVAILABLE_KB_RIYO_KA;
import static fb.com.IKitComConst.DATAKIND_KB_FURIKAE;
import static fb.com.IKitComConst.DATAKIND_KB_TEISEI_MINUS;
import static fb.com.IKitComConst.DATAKIND_KB_TEISEI_PLUS;
import static fb.com.IKitComConst.GYOTAI_KB_OROSI_ZENKOKU;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_1000YEN;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_BARA;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_CASE;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_KL;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_KOKU;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_L;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_YEN;
import static fb.com.IKitComConst.JSK_KEISAN_DATE_KB_HAKKO_DATE;
import static fb.com.IKitComConst.JSK_OP1_UMU_KB_JIKAYO_JYOGAI;
import static fb.com.IKitComConst.JSK_OP1_UMU_KB_JIKAYO_NOMI;
import static fb.com.IKitComConst.JSK_OP1_UMU_KB_JIKA_KOURI_JYOGAI;
import static fb.com.IKitComConst.JSK_OP1_UMU_KB_KOURI_NOMI;
import static fb.com.IKitComConst.JSK_OP1_UMU_KB_NONE;
import static fb.com.IKitComConst.JSK_SEL_DATA_KB_REINYU_EISEI;
import static fb.com.IKitComConst.JSK_SEL_DATA_KB_SYUKKA_TEISEI;
import static fb.com.IKitComConst.JSK_SEL_DATA_KB_SYUKKA_YOTEI;
import static fb.com.IKitComConst.KBN_COUNTY_CD;
import static fb.com.IKitComConst.KBN_HJS_KA;
import static fb.com.IKitComConst.KBN_HJS_SITEN;
import static fb.com.IKitComConst.KBN_VESSEL;
import static fb.com.IKitComConst.SYOHIN_KB_BUPPIN;
import static fb.com.IKitComConst.SYOHIN_KB_SYOHIN;
import static fb.com.IKitComConst.SYOHIN_KB_SYOKUHIN;
import static fb.com.IKitComConst.SYOHIN_KB_SYURUI;
import static fb.inf.pbs.IPbsConst.SQL_CRLF;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_COUNTRY;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_JIGYOSYO;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_KA;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_SOUNISAKI;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_SYUHANTEN;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_TANTO;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_TOKUYAKU;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_ZENKOKU;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_SAKADANE;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_SAKESITU;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_SYOHIN;
import static kit.jiseki.ESienList.IJisekiESienList.TAIHI_UMU_KB_SYUKAREINYU_FURIKAE;
import static kit.jiseki.ESienList.IJisekiESienList.TAIHI_UMU_KB_YOSAN_TAIHI_ON;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.ALIAS_E_SIEN_LIST;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.ALIAS_E_SIEN_LIST_DOT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComEigyoCalendarUtil;
import fb.com.ComUserSession;
import fb.com.exception.KitComException;
import fb.inf.KitService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.KitException;
import fb.inf.exception.MaxRowsException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.CyusyutuSyukeiInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.DateRange;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.KeyCol;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.MidasiColInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.TranTable;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.TranTableCtg;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.TranTableDt;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.TranTableHd;

/**
 * 営業支援実績一覧の検索サービス
 * @author t_kimura
 *
 */
public class JisekiESienList_SearchService extends KitService {

	/** serialVersionUID */
	private static final long serialVersionUID = 6376803716006753047L;

	/** クラス名 */
	private static String className__ = JisekiESienList_SearchService.class.getName();

	/** カテゴリ */
	private static Category category__ = Category.getInstance(className__);

	/** データベース操作クラス */
	protected PbsDatabase db_;

	/**
	 * コンストラクタ.
	 *
	 * @param cus getComUserSession()を渡すこと。
	 * @param db_ 呼び出すときにはgetDatabase()を渡すこと。
	 * @param isTran isTransaction()を渡すこと。
	 * @param ae getActionErrors()を渡すこと。
	 */
	public JisekiESienList_SearchService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
		super(cus, db_, isTran, ae);
		this.db_ = db_;
	}

	/**
	 * 検索処理
	 * @param searchForm 検索フォーム
	 * @throws Exception
	 */
	public JisekiESienListList execute(JisekiESienList_SearchForm searchForm) throws Exception {
		// ログインユーザセッション情報
		ComUserSession comUserSession = getComUserSession();

		// SQLへ渡す変数を格納するオブジェクト
		List<String> bindList = new ArrayList<String>();

		// 営業カレンダーUtil
		ComEigyoCalendarUtil eigyoCalendarUtil = new ComEigyoCalendarUtil(comUserSession, db_, isTransaction(), getActionErrors());

		// 検索Helper
		JisekiESienList_SearchHelper searchHelper = new JisekiESienList_SearchHelper(searchForm, eigyoCalendarUtil, getComUserSession().getGymDate());

		// 予算対比の場合
		if(PbsUtil.isEqual(searchForm.getYosanTaihi(), TAIHI_UMU_KB_YOSAN_TAIHI_ON)) {
			// 当月の予算を取得
			searchHelper.setJigyosyoYosanMap(searchYosanTaihi(comUserSession, searchForm));
		}

		// SQL文を生成する
		String selectSql = getSearchSql(comUserSession, searchHelper, searchForm, bindList);

		// SQL文をデータベース操作オブジェクトに設定する
		db_.prepare(selectSql);

		// SQLに検索条件値をセットする
		int i = 0;
		for (String bindStr : bindList) {
			db_.setString(++i, bindStr);
		}

		// SQLを実行する
		db_.execute();

		JisekiESienListList list;
		PbsRecord[] records_;
		try {
			// 検索結果を取得する
			records_ = db_.getPbsRecords(false);

		} catch (DataNotFoundException e) {
			throw e;
		} catch (MaxRowsException e) {
			category__.warn(e.getMessage());
			// 通らない
			throw new RuntimeException();
		}

		// リスト変換
		list = new JisekiESienListList(records_, searchHelper.getTableHeaderTagInfo(searchForm));

		// レコード設定
		setupRecord(searchHelper, searchForm, list);

		return list;
	}


	/**
	 * 予算取得
	 * @param comUserSession ログインユーザセッション情報
	 * @param searchForm 検索Form
	 * @return 予算
	 */
	private Map<String, String> searchYosanTaihi(ComUserSession comUserSession, JisekiESienList_SearchForm searchForm) {

		Map<String, String> jigyosyoYsan = new HashMap<String, String>();

		// 表示単位
		String hyojiTani = searchForm.getHyojiTani();

		String yosanCol;
		if(PbsUtil.isIncluded(hyojiTani,JSK_HYOJI_TANI_KB_YEN, JSK_HYOJI_TANI_KB_1000YEN)) {
			yosanCol = "YOSAN_URI_KINGAKU";
		} else if(PbsUtil.isIncluded(hyojiTani,JSK_HYOJI_TANI_KB_KOKU, JSK_HYOJI_TANI_KB_L, JSK_HYOJI_TANI_KB_KL)) {
			yosanCol = "YOSAN_KOKU_SU";
		} else {
			throw new IllegalArgumentException();
		}

		PbsRecord[] records_ = null;

		String gymDate = getComUserSession().getGymDate();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT                                         \n");
		sb.append(" JIGYOSYO_CD AS JIGYOSYO_CD                    \n");
		sb.append(" ,").append(yosanCol).append(" AS YOSAN        \n");
		sb.append("FROM                                           \n");
		sb.append("  T_SALES_SCHEDULE                             \n");
		sb.append("WHERE                                          \n");
		sb.append("  KAISYA_CD = ?                                \n");
		sb.append("  AND YOSAN_TEKIYO_YM = ?                      \n");

		db_.prepare(sb.toString());

		int i=0;
		db_.setString(++i, getComUserSession().getKaisyaCd());
		db_.setString(++i, PbsUtil.getYYYY(gymDate) + PbsUtil.getMM(gymDate));

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db_.execute();

		// 検索結果を取得する
		try {
			records_ = db_.getPbsRecords(false);
			for (PbsRecord record : records_) {
				jigyosyoYsan.put(record.getString("JIGYOSYO_CD"), record.getString("YOSAN"));
			}
		} catch (MaxRowsException e) {
			category__.error(e);
			// 通らない
			throw new RuntimeException();
		} catch (DataNotFoundException e) {
		}
		return jigyosyoYsan;
	}


	/**
	 * 検索Sql取得
	 * @param comUserSession ログインユーザセッション情報
	 * @param searchHelper 検索Helper
	 * @param searchForm 検索Form
	 * @param bindList バインドリスト
	 * @return 検索Sql
	 * @throws KitException
	 */
	private String getSearchSql(ComUserSession comUserSession, JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, List<String> bindList) throws KitException {

		StringBuilder sql = new StringBuilder(SQL_CRLF);

		sql.append("SELECT \n");

		// Select句取得
		sql.append(getMainSelectSql(searchHelper, searchForm));

		sql.append("FROM (\n");

		// 各トランザクション検索Sql取得
		sql.append(getTransSearchSql(comUserSession, searchHelper, searchForm, bindList)).append(" \n");

		sql.append(") ").append(ALIAS_E_SIEN_LIST).append(" \n");

		// マスタ結合
		sql.append(getMainMasterJoinSql(comUserSession, searchHelper, bindList));

		// WHEREなし

		sql.append("ORDER BY \n");

		// Order
		sql.append(getMainOrderSql(searchHelper));

		sql.append("\n");

		return sql.toString();
	}

	/**
	 * メイン検索SqlのSelect句取得
	 * @param searchHelper 検索Helper
	 * @param searchForm 検索Form
	 * @return Sql
	 */
	private String getMainSelectSql(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm) {
		StringBuilder sql = new StringBuilder();

		// 検索パターン
		IJisekiESienList_SearchSelectPtn selectPtn = searchHelper.getSelectPtn();

		// 見出し用抽出・集計情報リスト
		List<CyusyutuSyukeiInfo> midasiCyusyutuSyukeiInfoList = searchHelper.getMidasiCyusyutuSyukeiInfoList();

		// 見出し部分
		for (CyusyutuSyukeiInfo cyusyutuSyukeiInfo : midasiCyusyutuSyukeiInfoList) {
			// コード
			for (KeyCol keyCol : cyusyutuSyukeiInfo.getKeyCols()) {
				if(sql.length() > 0) {
					sql.append("  ,");
				} else {
					sql.append("   ");
				}
				sql.append(ALIAS_E_SIEN_LIST_DOT).append(keyCol.getCol()).append(" AS ").append(keyCol.getCol()).append(" \n");
			}
			// 名称
			switch (cyusyutuSyukeiInfo) {
			case JIGYOSYO:
				// 事業所
				sql.append("  ,MEISYOU_JIGYOSYO.CD_RN AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
				break;
			case KA:
				// 課
				sql.append("  ,MEISYOU_KA.CD_RN AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
				break;
			case TANTO:
				// 担当者
				sql.append("  ,TANTO.TANTO_NM_RYAKU AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
				break;
			case TODOFUKN:
				// 都道府県
				sql.append("  ,MEISYOU_TODOFUKEN.CD_RN AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
				break;
			case ZENKOKU:
				// 全国卸
				// sql2.append(" ,OROSITEN_1.TEN_NM2_JISYA AS MIDASI_NM_").append(index).append("_1").append(" \n");
				sql.append("  ,TOKU.KEIYAKU_NAIYOU_NM_RYAKU AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
				break;
			case TOKUYAKU:
				// 特約
				sql.append("  ,OROSITEN_1JITEN.TEN_NM2_JISYA AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
				break;
			case SOUNISAKI:
				// 送荷先
				sql.append("  ,OROSITEN_LAST.TEN_NM2_JISYA AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
				break;
			case SYUHANTEN:
				// 酒販店
				sql.append("  ,SYUHANTEN.TEN_NM_YAGO AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
				break;
			case UNSOTEN:
				// 運送店
				sql.append("  ,UNSOTEN.UNSOTEN_NM_PRINT AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
				break;
			case SYUKA_SAKI_COUNTRY:
				// 輸出国
				sql.append("  ,COUNTRY.SYUKA_SAKI_COUNTRY_NM_RYAKU AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
				break;
			case SAKADANE:
				// 酒種
				sql.append("  ,TANE.TANE_NM_RYAKU AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
				break;
			case SAKESITU:
				// 酒質
				sql.append("  ,BUNRUI.HANBAI_BUNRUI_RNM AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
				break;
			case SHOHIN:
				// 商品
				sql.append("  ,CASE WHEN SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYURUI).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOKUHIN).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOHIN).append("' THEN SEIHIN.SEIHIN_NM_REPORT2 ELSE SIIREHIN.SIIREHIN_NM_REPORT2 END AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
				sql.append("  ,CASE WHEN SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYURUI).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOKUHIN).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOHIN).append("' THEN SEIHIN.YOUKI_KIGO_NM2 ELSE NULL END AS ").append(cyusyutuSyukeiInfo.getNmCols()[1]).append(" \n");
				sql.append("  ,CASE WHEN SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYURUI).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOKUHIN).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOHIN).append("' THEN SEIHIN.CASE_IRISU ELSE SIIREHIN.CASE_IRISU END AS ").append(cyusyutuSyukeiInfo.getNmCols()[2]).append(" \n");
				sql.append("  ,CASE WHEN SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYURUI).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOKUHIN).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOHIN).append("' THEN LTRIM(TO_CHAR(SEIHIN.TNPN_VOL,'99990.000')) ELSE NULL END AS ").append(cyusyutuSyukeiInfo.getNmCols()[3]).append(" \n");
//				sql.append("  ,SEIHIN.SEIHIN_NM_REPORT2 AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
//				sql.append("  ,SEIHIN.YOUKI_KIGO_NM2 AS ").append(cyusyutuSyukeiInfo.getNmCols()[1]).append(" \n");
//				sql.append("  ,SEIHIN.CASE_IRISU AS ").append(cyusyutuSyukeiInfo.getNmCols()[2]).append(" \n");
//				sql.append("  ,LTRIM(TO_CHAR(SEIHIN.TNPN_VOL,'99990.000')) AS ").append(cyusyutuSyukeiInfo.getNmCols()[3]).append(" \n");
				break;
			case YOUKIZAISITU:
				// 容器材質
				sql.append("  ,MEISYOU_YOUK.CD_RN AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
				sql.append("  ,LTRIM(TO_CHAR(").append(ALIAS_E_SIEN_LIST_DOT).append("TNPN_VOL,'99990.000')) AS ").append(cyusyutuSyukeiInfo.getNmCols()[1]).append(" \n");
				break;
			case YOUKI:
				// 容器（素材区分・充填容量）
				sql.append("  ,DECODE(MEISYOU_YOUK.CD_RN, NULL, LTRIM(TO_CHAR(").append(ALIAS_E_SIEN_LIST_DOT).append("JYUTEN_YOURYO,'99990.000')), CONCAT(CONCAT(MEISYOU_YOUK.CD_RN,' '),LTRIM(TO_CHAR(").append(ALIAS_E_SIEN_LIST_DOT).append("JYUTEN_YOURYO,'99990.000')))) AS ").append(cyusyutuSyukeiInfo.getNmCols()[0]).append(" \n");
				break;
			}
		}

		// 累計・日付のSelect
		List<String> colList = selectPtn.getSelectColList(searchForm, searchHelper.getDateInfo().getDateRange().size());
		for (String col : colList) {
			// 受注
			if(searchHelper.isJucyuSearch()) {
				sql.append("  ,").append(ALIAS_E_SIEN_LIST_DOT).append(TranTable.JYUCYU.withAliasForColNm(col)).append(" \n");
			}
			// 出荷
			if(searchHelper.isSyukaSearch()) {
				sql.append("  ,").append(ALIAS_E_SIEN_LIST_DOT).append(TranTable.SYUKA.withAliasForColNm(col)).append(" \n");
			}
			// 戻入
			if(searchHelper.isReinyuSearch()) {
				sql.append("  ,").append(ALIAS_E_SIEN_LIST_DOT).append(TranTable.REINYU.withAliasForColNm(col)).append(" \n");
			}
		}
		return sql.toString();
	}

	/**
	 * メイン検索Sqlのマスタ結合取得
	 * @param comUserSession ログインユーザセッション情報
	 * @param searchHelper 検索Helper
	 * @param bindList バインドリスト
	 * @return Sql
	 */
	private String getMainMasterJoinSql(ComUserSession comUserSession, JisekiESienList_SearchHelper searchHelper, List<String> bindList) {
		StringBuilder sql = new StringBuilder();

		// 見出し用抽出・集計情報リスト
		List<CyusyutuSyukeiInfo> midasiCyusyutuSyukeiInfoList = searchHelper.getMidasiCyusyutuSyukeiInfoList();

		// マスタ結合

		// 事業所
		if(midasiCyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.JIGYOSYO)) {
			sql.append("  LEFT OUTER JOIN M_CD_MEISYOU MEISYOU_JIGYOSYO ON ").append(ALIAS_E_SIEN_LIST_DOT).append("JIGYOSYO_CD = MEISYOU_JIGYOSYO.CODE_CD AND MEISYOU_JIGYOSYO.KBN_ID = ? \n");
			bindList.add(KBN_HJS_SITEN);
		}
		// 課
		if(midasiCyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.KA)) {
			sql.append("  LEFT OUTER JOIN M_CD_MEISYOU MEISYOU_KA ON ").append(ALIAS_E_SIEN_LIST_DOT).append("KA_CD = MEISYOU_KA.CODE_CD AND MEISYOU_KA.KBN_ID = ? \n");
			bindList.add(KBN_HJS_KA);
		}
		// 担当者
		if(midasiCyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.TANTO)) {
			sql.append("  LEFT OUTER JOIN M_KBN_JISEKI_TANTO TANTO ON ").append(ALIAS_E_SIEN_LIST_DOT).append("TANTO_CD = TANTO.TANTO_CD AND TANTO.KAISYA_CD = ? \n");
			bindList.add(comUserSession.getKaisyaCd());
		}
		// 都道府県
		if(midasiCyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.TODOFUKN)) {
			sql.append("  LEFT OUTER JOIN M_CD_MEISYOU MEISYOU_TODOFUKEN ON ").append(ALIAS_E_SIEN_LIST_DOT).append("TODOFUKN_CD = MEISYOU_TODOFUKEN.CODE_CD AND MEISYOU_TODOFUKEN.KBN_ID = ? \n");
			bindList.add(KBN_COUNTY_CD);
		}
		// 全国卸
		if(midasiCyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.ZENKOKU)) {
			sql.append("  LEFT OUTER JOIN M_TOKUYAKUTEN TOKU ON ").append(ALIAS_E_SIEN_LIST_DOT).append("HJISEKI_SYUKEI_CD = TOKU.TOKUYAKUTEN_CD AND TOKU.KAISYA_CD = ? AND TOKU.SEIKYU_GYOTAI_KBN = ? \n");
			bindList.add(comUserSession.getKaisyaCd());
			bindList.add(GYOTAI_KB_OROSI_ZENKOKU);
			//sql.append(" LEFT OUTER JOIN M_OROSITEN OROSITEN_1 ON ").append(first).append("HJISEKI_SYUKEI_CD = OROSITEN_1.OROSITEN_CD AND OROSITEN_1.KAISYA_CD = ? \n");
			//bindList.add(comUserSession.getKaisyaCd());
		}
		// 特約
		if(midasiCyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.TOKUYAKU)) {
			sql.append("  LEFT OUTER JOIN M_OROSITEN OROSITEN_1JITEN ON ").append(ALIAS_E_SIEN_LIST_DOT).append("OROSITEN_CD_1JITEN = OROSITEN_1JITEN.OROSITEN_CD AND OROSITEN_1JITEN.KAISYA_CD = ? \n");
			bindList.add(comUserSession.getKaisyaCd());
		}
		// 送荷先
		if(midasiCyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.SOUNISAKI)) {
			sql.append("  LEFT OUTER JOIN M_OROSITEN OROSITEN_LAST ON ").append(ALIAS_E_SIEN_LIST_DOT).append("OROSITEN_CD_LAST = OROSITEN_LAST.OROSITEN_CD AND OROSITEN_LAST.KAISYA_CD = ? \n");
			bindList.add(comUserSession.getKaisyaCd());
		}
		// 酒販店
		if(midasiCyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.SYUHANTEN)) {
			sql.append("  LEFT OUTER JOIN M_SYUHANTEN SYUHANTEN ON ").append(ALIAS_E_SIEN_LIST_DOT).append("SYUHANTEN_CD = SYUHANTEN.SYUHANTEN_CD \n");
		}
		// 運送店
		if(midasiCyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.UNSOTEN)) {
			sql.append("  LEFT OUTER JOIN M_UNSOTEN UNSOTEN ON ").append(ALIAS_E_SIEN_LIST_DOT).append("UNSOTEN_CD = UNSOTEN.UNSOTEN_CD AND UNSOTEN.KAISYA_CD = ? \n");
			bindList.add(comUserSession.getKaisyaCd());
		}
		// 輸出国
		if(midasiCyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.SYUKA_SAKI_COUNTRY)) {
			sql.append("  LEFT OUTER JOIN M_SYUKA_SAKI_COUNTRY COUNTRY ON ").append(ALIAS_E_SIEN_LIST_DOT).append("SYUKA_SAKI_COUNTRY_CD = COUNTRY.SYUKA_SAKI_COUNTRY_CD \n");
		}
		// 酒種
		if(midasiCyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.SAKADANE)) {
			sql.append("  LEFT OUTER JOIN M_KBN_TANE TANE ON ").append(ALIAS_E_SIEN_LIST_DOT).append("TANE_CD = TANE.TANE_CD AND TANE.KAISYA_CD = ? \n");
			bindList.add(comUserSession.getKaisyaCd());
		}
		// 酒質
		if(midasiCyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.SAKESITU)) {
			sql.append("  LEFT OUTER JOIN M_KBN_HANBAI_BUNRUI BUNRUI ON ").append(ALIAS_E_SIEN_LIST_DOT).append("HANBAI_BUNRUI_CD = BUNRUI.HANBAI_BUNRUI_CD \n");
		}
		// 商品
		if(midasiCyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.SHOHIN)) {
			sql.append("  LEFT OUTER JOIN M_SYOHIN SYOHIN ON ").append(ALIAS_E_SIEN_LIST_DOT).append("SHOHIN_CD = SYOHIN.SHOHIN_CD AND SYOHIN.KAISYA_CD = ? AND SYOHIN.KTKSY_CD = ? \n");
			sql.append("  LEFT OUTER JOIN M_SEIHIN SEIHIN ON SYOHIN.KAISYA_CD = SEIHIN.KAISYA_CD AND SYOHIN.KTKSY_CD = SEIHIN.KTKSY_CD AND SYOHIN.SEIHIN_CD = SEIHIN.SEIHIN_CD \n");
			sql.append("  LEFT OUTER JOIN M_SIIREHIN SIIREHIN ON SYOHIN.KAISYA_CD = SIIREHIN.KAISYA_CD AND SYOHIN.KTKSY_CD = SIIREHIN.KTKSY_CD AND SYOHIN.SIIRESAKI_CD = SIIREHIN.SIIRESAKI_CD AND SYOHIN.SIIREHIN_CD = SIIREHIN.SIIREHIN_CD \n");
			bindList.add(comUserSession.getKaisyaCd());
			bindList.add(comUserSession.getKtksyCd());
		}
		// 容器材質,容器（素材区分・充填容量）
		if(midasiCyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.YOUKIZAISITU) || midasiCyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.YOUKI)) {
			//sql.append(" LEFT OUTER JOIN M_SEIHINYOUK YOUK ON ").append(ALIAS_E_SIEN_LIST_DOT).append("YOUKI_CD = YOUK.YOUKI_CD \n");
			sql.append("  LEFT OUTER JOIN M_CD_MEISYOU MEISYOU_YOUK ON ").append(ALIAS_E_SIEN_LIST_DOT).append("SOZAI_KBN = MEISYOU_YOUK.CODE_CD AND MEISYOU_YOUK.KBN_ID = ? \n");
			bindList.add(KBN_VESSEL);
		}
		return sql.toString();
	}

	/**
	 * メイン検索SqlのOrder句取得
	 * @param searchHelper 検索Helper
	 * @return Sql
	 */
	private String getMainOrderSql(JisekiESienList_SearchHelper searchHelper) {
		StringBuilder sql = new StringBuilder();

		// 見出し用キー列リスト
		List<KeyCol> midasiKeyColList = searchHelper.getMidasiKeyColList();

		for (KeyCol keyCol : midasiKeyColList) {
			if(sql.length() != 0) {
				sql.append("  ,");
			} else {
				sql.append("   ");
			}
			if(keyCol == KeyCol.JIGYOSYO_CD) {
				// 事業所
				sql.append("TO_NUMBER(MEISYOU_JIGYOSYO.BIKOU_A)");
			} else if(keyCol == KeyCol.KA_CD) {
				// 課
				sql.append("TO_NUMBER(MEISYOU_KA.BIKOU_A)");
			} else {
				sql.append(ALIAS_E_SIEN_LIST_DOT).append(keyCol.getCol());
			}
			// ASC・DESC
			if(keyCol.isAsc()) {
				sql.append(" ASC \n");
			} else {
				sql.append(" DESC NULLS LAST \n");
			}
		}
		return sql.toString();
	}

	/**
	 * 各トランザクション検索Sql取得
	 * @param comUserSession ログインユーザセッション情報
	 * @param searchHelper 検索Helper
	 * @param searchForm 検索Form
	 * @param bindList バインドリスト
	 * @return sql
	 */
	private String getTransSearchSql(ComUserSession comUserSession, JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, List<String> bindList) {
		StringBuilder fromSql = new StringBuilder();

		fromSql.append("  FROM \n");
		List<TranTable> joinList = new ArrayList<TranTable>();

		// 受注
		if(searchHelper.isJucyuSearch()) {
			fromSql.append(getTransSearchSql(comUserSession, searchHelper, searchForm, TranTable.JYUCYU, joinList, bindList));
			joinList.add(TranTable.JYUCYU);
		}

		// 出荷
		if(searchHelper.isSyukaSearch()) {
			fromSql.append(getTransSearchSql(comUserSession, searchHelper, searchForm, TranTable.SYUKA, joinList, bindList));
			joinList.add(TranTable.SYUKA);
		}

		// 戻入
		if(searchHelper.isReinyuSearch()) {
			fromSql.append(getTransSearchSql(comUserSession, searchHelper, searchForm, TranTable.REINYU, joinList, bindList));
			joinList.add(TranTable.REINYU);
		}

		StringBuilder sql = new StringBuilder();

		sql.append("  SELECT \n");

		// 各トランザクション検索のSelect句取得
		sql.append(getTransSelectSql(searchHelper, searchForm, joinList));

		// from以降
		sql.append(fromSql);

		return sql.toString();

	}

	/**
	 * 各トランザクション検索Sql取得
	 * @param comUserSession ログインユーザセッション情報
	 * @param searchHelper 検索Helper
	 * @param searchForm 検索Form
	 * @param targetTable トランザクションTable
	 * @param joinList 結合リスト
	 * @param bindList バインドリスト
	 * @return sql
	 */
	private String getTransSearchSql(ComUserSession comUserSession, JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, TranTable targetTable, List<TranTable> joinList, List<String> bindList) {
		StringBuilder sql = new StringBuilder();

		// キー列
		List<KeyCol> keyColList = searchHelper.getKeyColList();

		if(!joinList.isEmpty()) {
			//// 戻入とはfull
			//if(targetTable == TranTable.REINYU) {
				sql.append("    FULL OUTER JOIN ");
			//} else {
			//	sql.append("    LEFT OUTER JOIN ");
			//}
		}
		sql.append("    ( \n");
		// トランザクション検索Sql
		sql.append(getTranSearchSql(comUserSession, searchHelper, searchForm, targetTable, bindList));
		sql.append("    ) ").append(targetTable.getAlias()).append("\n");

		if(!joinList.isEmpty()) {
			boolean and = false;
			TranTable beforeTable = joinList.get(joinList.size() - 1);
			sql.append("      ON ");
			for (KeyCol keyCol : keyColList) {
				if(and) {
					sql.append(" AND ");
				}
				if(keyCol == KeyCol.JYUTEN_YOURYO || keyCol == KeyCol.TNPN_VOL) {
					sql.append("NVL(TO_CHAR(").append(beforeTable.withAlias(keyCol.getCol())).append("),' ') = NVL(TO_CHAR(").append(targetTable.withAlias(keyCol.getCol())).append("),' ')");
				} else {
					sql.append("NVL(").append(beforeTable.withAlias(keyCol.getCol())).append(",' ') = NVL(").append(targetTable.withAlias(keyCol.getCol())).append(",' ')");
				}
				and = true;
			}
			sql.append(" \n");
		}
		return sql.toString();
	}

	/**
	 * 各トランザクション検索のSelect句取得
	 * @param searchHelper 検索Helper
	 * @param searchForm 検索Form
	 * @param joinList 結合リスト
	 * @return sql
	 */
	private StringBuilder getTransSelectSql(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, List<TranTable> joinList) {
		StringBuilder sql = new StringBuilder();

		// キー列
		List<KeyCol> keyColList = searchHelper.getKeyColList();

		int joinTableCnt = joinList.size();

		// コード
		for (KeyCol keyCol : keyColList) {
			if(sql.length() != 0) {
				sql.append("    ,");
			} else {
				sql.append("     ");
			}
			if(joinTableCnt == 1) {
				TranTable tran = joinList.get(0);
				sql.append(tran.withAlias(keyCol.getCol())).append(" AS ").append(keyCol.getCol()).append(" \n");
			} else {
				sql.append("COALESCE(");
				for (int i = 0; i < joinTableCnt; i++) {
					TranTable tran = joinList.get(i);
					if(i == 0) {
						sql.append(tran.withAlias(keyCol.getCol()));
					} else {
						sql.append(" ,").append(tran.withAlias(keyCol.getCol()));
					}
				}
				sql.append(") AS ").append(keyCol.getCol()).append(" \n");
			}
		}

		IJisekiESienList_SearchSelectPtn selectPtn = searchHelper.getSelectPtn();
		// 累計・日付項目
		List<String> colList = selectPtn.getSelectColList(searchForm,searchHelper.getDateInfo().getDateRange().size());
		for (String col : colList) {
			// 受注
			if(searchHelper.isJucyuSearch()) {
				sql.append("    ,").append(TranTable.JYUCYU.withAlias(col)).append(" AS ").append(TranTable.JYUCYU.withAliasForColNm()).append(col).append(" \n");
			} else {
				// sql.append("    , 0 AS ").append(TranTable.JYUCYU.getColAsWithAlias()).append(col).append(" \n");
			}
			// 出荷
			if(searchHelper.isSyukaSearch()) {
				sql.append("    ,").append(TranTable.SYUKA.withAlias(col)).append(" AS ").append(TranTable.SYUKA.withAliasForColNm()).append(col).append(" \n");
			} else {
				// sql.append("    , 0 AS ").append(TranTable.SYUKA.getColAsWithAlias()).append(col).append(" \n");
			}
			// 戻入
			if(searchHelper.isReinyuSearch()) {
				sql.append("    ,").append(TranTable.REINYU.withAlias(col)).append(" AS ").append(TranTable.REINYU.withAliasForColNm()).append(col).append(" \n");
			} else {
				// sql.append("    , 0 AS ").append(TranTable.REINYU.getColAsWithAlias()).append(col).append(" \n");
			}
		}
		return sql;
	}

	/**
	 * トランザクション検索Sql取得
	 * @param comUserSession ログインユーザセッション情報
	 * @param searchHelper 検索Helper
	 * @param searchForm 検索Form
	 * @param targetTable トランザクションTable
	 * @param bindList バインドリスト
	 * @return sql
	 */
	private String getTranSearchSql(ComUserSession comUserSession, JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, TranTable tranTable, List<String> bindList) {
		StringBuilder sql = new StringBuilder();

		// 計算日付基準の列名
		String keisanDateKijunCol = getKeisanDateKijunCol(searchForm.getKeisanDateKijun(), tranTable);

		sql.append("      SELECT \n");

		// select句
		sql.append(getTranSelectSql(searchHelper, searchForm, tranTable, keisanDateKijunCol, bindList));

		sql.append("      FROM \n");

		// ヘッダ・ディテール or カテゴリ結合
		sql.append(getJoinHdSql(searchHelper, tranTable));

		// マスタ結合
		sql.append(getTranMasterJoinSql(comUserSession, searchHelper, searchForm, tranTable, bindList));

		sql.append("      WHERE \n");

		// where
		sql.append(getTranWhereSql(comUserSession, searchHelper, searchForm, tranTable, keisanDateKijunCol, bindList));

		sql.append("      GROUP BY \n");

		// group
		sql.append(getTranGroupSql(searchHelper, tranTable));

		return sql.toString();
	}

	/**
	 * 計算日付基準の列名取得
	 * @param keisanDateKijun 計算日付基準
	 * @param tranTable トランザクションTable
	 * @return 集計対象日付の列名
	 */
	private String getKeisanDateKijunCol(String keisanDateKijun, TranTable tranTable) {
		TranTableHd hd = tranTable.getHd();
		if(PbsUtil.isEqual(keisanDateKijun,JSK_KEISAN_DATE_KB_HAKKO_DATE)) {
			// 伝票発行日
			return hd.withAlias(hd.getDenpyoHakkoDt());
		} else {
			// ﾐﾅｼ日付
			return hd.withAlias(hd.getMinasiDt());
		}
	}

	/**
	 * ヘッダ結合SQL取得
	 * @param searchHelper 検索Helper
	 * @param tranTable トランザクションTable
	 * @return SQL
	 */
	private String getJoinHdSql(JisekiESienList_SearchHelper searchHelper, TranTable tranTable) {
		StringBuilder sql = new StringBuilder();
		// ヘッダ
		TranTableHd hd = tranTable.getHd();

		if(searchHelper.isSearchDt()) {
			// ディテール検索時
			TranTableDt dt = tranTable.getDt();
			sql.append("        ").append(hd.getTableNm()).append(" ").append(hd.getAlias()).append(" \n");
			sql.append("        INNER JOIN ").append(dt.getTableNm()).append(" ").append(dt.getAlias()).append(" ON ")
				.append(hd.withAlias(hd.getKaisyaCd())).append(" = ").append(dt.withAlias(dt.getKaisyaCd())).append(" AND ")
				.append(hd.withAlias(hd.getNo())).append(" = ").append(dt.withAlias(dt.getNo())).append(" \n");
		} else {
			// カテゴリ検索時
			TranTableCtg ctg = tranTable.getCtg();
			sql.append("        ").append(hd.getTableNm()).append(" ").append(hd.getAlias()).append(" \n");
			sql.append("        INNER JOIN ").append(getCtgSeihinSql(ctg)).append(" ").append(ctg.getAlias()).append(" ON ")
				.append(hd.withAlias(hd.getKaisyaCd())).append(" = ").append(ctg.withAlias(ctg.getKaisyaCd())).append(" AND ")
				.append(hd.withAlias(hd.getNo())).append(" = ").append(ctg.withAlias(ctg.getNo())).append(" \n");
		}

		return sql.toString();

	}

	/**
	 * カテゴリ・製品テーブルSQL取得
	 * @param ctg カテゴリTable
	 * @return SQL
	 */
	private String getCtgSeihinSql(TranTableCtg ctg) {
		StringBuilder sql = new StringBuilder();
		sql.append("(");
		sql.append(" SELECT ");
		sql.append(ctg.withAlias("*"));
		sql.append(",F_CALC_CASE_KANZAN(SEIHIN.CASE_IRISU,").append(ctg.withAlias(ctg.getBara())).append(") AS CASE_SU ");
		sql.append(",SEIHIN.TNPN_VOL");
		sql.append(" FROM ").append(ctg.getTableNm()).append(" ").append(ctg.getAlias());
		sql.append(" LEFT OUTER JOIN M_SEIHIN SEIHIN ON ");
		sql.append(ctg.withAlias(ctg.getKaisyaCd())).append(" = SEIHIN.KAISYA_CD AND ");
		sql.append(ctg.withAlias(ctg.getKtksyCd())).append(" = SEIHIN.KTKSY_CD AND ");
		sql.append(ctg.withAlias(ctg.getSeihinCd())).append(" = SEIHIN.SEIHIN_CD");
		sql.append(")");
		return sql.toString();
	}


	/**
	 * トランザクション検索sqlのSelect句取得
	 * @param searchHelper 検索Helper
	 * @param searchForm 検索Form
	 * @param tranTable トランザクションTable
	 * @param keisanDateKijunCol 計算日付基準の列名
	 * @param bindList バインドリスト
	 * @return sql
	 */
	private String getTranSelectSql(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, TranTable tranTable, String keisanDateKijunCol, List<String> bindList) {
		StringBuilder sql = new StringBuilder();

		// キー列
		List<KeyCol> keyColList = searchHelper.getKeyColList();

		// 各キー列
		for (KeyCol keyCol : keyColList) {
			if(sql.length() != 0) {
				sql.append("        ,");
			} else {
				sql.append("         ");
			}
			sql.append(getTranSelectGroupColSql(searchHelper, keyCol, tranTable)).append(" AS ").append(keyCol.getCol()).append(" \n");
		}

		// 集計対象の列名
		String syukeiCol = getSyukeiCol(searchForm.getHyojiTani(), searchHelper, tranTable);

		// 検索パターン
		IJisekiESienList_SearchSelectPtn selectPtn = searchHelper.getSelectPtn();
		// 累計・日付項目
		sql.append(selectPtn.getTranSelectSql(searchForm, searchHelper.getDateInfo(), tranTable, syukeiCol, keisanDateKijunCol, bindList));

		return sql.toString();
	}

	/**
	 * 集計対象の列名取得
	 * @param hyojiTani 表示単位
	 * @param searchHelper 検索Helper
	 * @param tranTable トランザクションTable
	 * @return 集計対象の列名
	 */
	private String getSyukeiCol(String hyojiTani, JisekiESienList_SearchHelper searchHelper, TranTable tranTable) {

		if(searchHelper.isSearchDt()) {
			// ディテール検索時
			TranTableDt dt = tranTable.getDt();
			if(PbsUtil.isIncluded(hyojiTani,JSK_HYOJI_TANI_KB_YEN, JSK_HYOJI_TANI_KB_1000YEN)) {
				// 円・千円の場合、金額
				return dt.withAlias(dt.getKingaku());
			} else if(PbsUtil.isIncluded(hyojiTani,JSK_HYOJI_TANI_KB_KOKU, JSK_HYOJI_TANI_KB_L, JSK_HYOJI_TANI_KB_KL)) {
				// 石・L数・KL数の場合、出荷総容量
				return dt.withAlias(dt.getYouryo());
			} else if(PbsUtil.isIncluded(hyojiTani,JSK_HYOJI_TANI_KB_BARA)) {
				// 本数の場合、換算総ｾｯﾄ数
				return dt.withAlias(dt.getBara());
			} else if(PbsUtil.isIncluded(hyojiTani,JSK_HYOJI_TANI_KB_CASE)) {
				// 箱数の場合、出荷数量_箱数
				return dt.withAlias(dt.getCaseSu());
			} else {
				// kg数・t数の場合、出荷重量
				return dt.withAlias(dt.getWeight());
			}
		} else {
			// カテゴリ検索時
			TranTableCtg ctg = tranTable.getCtg();
			if(PbsUtil.isIncluded(hyojiTani,JSK_HYOJI_TANI_KB_YEN, JSK_HYOJI_TANI_KB_1000YEN)) {
				// 円・千円の場合、金額
				return ctg.withAlias(ctg.getKingaku());
			} else if(PbsUtil.isIncluded(hyojiTani,JSK_HYOJI_TANI_KB_KOKU, JSK_HYOJI_TANI_KB_L, JSK_HYOJI_TANI_KB_KL)) {
				// 石・L数・KL数の場合、出荷総容量
				return ctg.withAlias(ctg.getYouryo());
			} else if(PbsUtil.isIncluded(hyojiTani,JSK_HYOJI_TANI_KB_BARA)) {
				// 本数の場合、換算総ｾｯﾄ数
				return ctg.withAlias(ctg.getBara());
			} else {
				// 箱数の場合
				return ctg.withAlias("CASE_SU");
			}
		}
	}

	/**
	 * トランザクション検索sqlのマスタ結合取得
	 * @param comUserSession ログインユーザセッション情報
	 * @param searchHelper 検索Helper
	 * @param searchForm 検索Form
	 * @param tranTable トランザクションTable
	 * @param bindList バインドリスト
	 * @return sql
	 */
	private String getTranMasterJoinSql(ComUserSession comUserSession, JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, TranTable tranTable, List<String> bindList) {
		StringBuilder sql = new StringBuilder();

		// ヘッダ
		TranTableHd hd = tranTable.getHd();

		// 抽出・集計情報リスト
		List<CyusyutuSyukeiInfo> cyusyutuSyukeiInfoList = searchHelper.getCyusyutuSyukeiInfoList();

		// 営業支援自家用・小売有無_指定なし以外
		// 事業所・課・担当者
		// 全国卸・特約・送荷先
		// 都道府県
		if(!PbsUtil.isEqual(searchForm.getJikakouriUmu(),JSK_OP1_UMU_KB_NONE) ||
				cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.JIGYOSYO) || cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.KA) || cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.TANTO) ||
				cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.ZENKOKU) || cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.TOKUYAKU) || cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.SOUNISAKI) ||
				cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.TODOFUKN)) {

			sql.append("        LEFT OUTER JOIN M_OROSISYOSAI_HD OROSIH ON ")
				.append(hd.withAlias(hd.getKaisyaCd())).append(" = OROSIH.KAISYA_CD AND ")
				.append(hd.withAlias(hd.getTatesenCd())).append(" = OROSIH.TATESN_CD \n");

			// 営業支援自家用・小売有無_指定なし以外
			// 事業所・課・担当者
			if(!PbsUtil.isEqual(searchForm.getJikakouriUmu(),JSK_OP1_UMU_KB_NONE) ||
					cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.JIGYOSYO) || cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.KA) || cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.TANTO)) {
				sql.append("        LEFT OUTER JOIN M_KBN_JISEKI_TANTO TANTO ON OROSIH.KAISYA_CD = TANTO.KAISYA_CD AND OROSIH.TANTOSYA_CD = TANTO.TANTO_CD \n");
			}

			// 全国卸
			if(cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.ZENKOKU)) {
				// 全国卸に紐付くデータと紐付かないデータを対象とする場合
				//sql.append("        LEFT OUTER JOIN M_TOKUYAKUTEN TOKU ON OROSIH.KAISYA_CD = TOKU.KAISYA_CD AND OROSIH.TOKUYAKUTEN_CD_SMGP = TOKU.TOKUYAKUTEN_CD AND TOKU.SEIKYU_GYOTAI_KBN = ? \n");
				//bindList.add(GYOTAI_KB_OROSI_ZENKOKU);
				// 全国卸に紐付くデータのみを対象とする場合
				sql.append("        LEFT OUTER JOIN M_TOKUYAKUTEN TOKU ON OROSIH.KAISYA_CD = TOKU.KAISYA_CD AND OROSIH.TOKUYAKUTEN_CD_SMGP = TOKU.TOKUYAKUTEN_CD \n");
			}

			// 都道府県
			if(cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.TODOFUKN)) {
				sql.append("        LEFT OUTER JOIN M_OROSITEN OROSITEN ON OROSIH.KAISYA_CD = OROSITEN.KAISYA_CD AND OROSIH.OROSITEN_CD_LAST = OROSITEN.OROSITEN_CD \n");
				sql.append("        LEFT OUTER JOIN M_KBN_JIS JIS ON OROSITEN.JIS_CD = JIS.JIS_CD \n");
			}
		}

		if(searchHelper.isSearchDt()) {
			// ディテール検索時
			TranTableDt dt = tranTable.getDt();
			// 酒種・酒質・容器（素材区分）
			if(cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.SAKADANE) || cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.SAKESITU) || cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.YOUKI)) {
				sql.append("        LEFT OUTER JOIN M_SYOHIN SYOHIN ON ")
				.append(dt.withAlias(dt.getKaisyaCd())).append(" = SYOHIN.KAISYA_CD AND SYOHIN.KTKSY_CD = ? AND ")
				.append(dt.withAlias(dt.getShohinCd())).append(" = SYOHIN.SHOHIN_CD \n");
				bindList.add(comUserSession.getKtksyCd());
				sql.append("        LEFT OUTER JOIN M_SEIHIN SEIHIN ON SYOHIN.KAISYA_CD = SEIHIN.KAISYA_CD AND SYOHIN.KTKSY_CD = SEIHIN.KTKSY_CD AND SYOHIN.SEIHIN_CD = SEIHIN.SEIHIN_CD \n");
				// 酒質
				if(cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.SAKESITU)) {
					sql.append("        LEFT OUTER JOIN M_KBN_TANE TANE ON SEIHIN.KAISYA_CD = TANE.KAISYA_CD AND SEIHIN.TANE_CD = TANE.TANE_CD \n");
				}
				// 容器（素材区分）
				if(cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.YOUKI)) {
					// 容器有り・容器無しを対象とする場合
					sql.append("        LEFT OUTER JOIN ( \n");
					sql.append("          SELECT  \n");
					sql.append("             SIZAI_SIIRE.KAISYA_CD \n");
					sql.append("            ,SIZAI_SIIRE.KTKSY_CD \n");
					sql.append("            ,SIZAI_SIIRE.SEIHIN_CD \n");
					sql.append("            ,SIZAI_SIIRE.YOUKI_CD \n");
					sql.append("          FROM ( \n");
					sql.append("            SELECT \n");
					sql.append("               SIZAI.KAISYA_CD \n");
					sql.append("              ,SIZAI.KTKSY_CD \n");
					sql.append("              ,SIZAI.SEIHIN_CD \n");
					sql.append("              ,SIZAI.KTKSY_CD_SIIREHIN \n");
					sql.append("              ,SIZAI.SIIRESAKI_CD \n");
					sql.append("              ,SIZAI.SIIREHIN_CD \n");
					sql.append("              ,SIIRE.YOUKI_CD \n");
					sql.append("              ,ROW_NUMBER() OVER (PARTITION BY SIZAI.KAISYA_CD, SIZAI.KTKSY_CD, SIZAI.SEIHIN_CD, SIZAI.KTKSY_CD_SIIREHIN ORDER BY SIIRE.YOUKI_CD) RNUM \n");
					sql.append("            FROM \n");
					sql.append("              M_SSIZAI_GRP SIZAI \n");
					sql.append("              LEFT OUTER JOIN M_SIIREHIN SIIRE ON SIZAI.KAISYA_CD = SIIRE.KAISYA_CD AND SIZAI.KTKSY_CD_SIIREHIN = SIIRE.KTKSY_CD AND SIZAI.SIIRESAKI_CD = SIIRE.SIIRESAKI_CD AND SIZAI.SIIREHIN_CD = SIIRE.SIIREHIN_CD \n");
					sql.append("            WHERE \n");
					sql.append("              SIZAI.KAISYA_CD = ? \n");
					sql.append("              AND SIZAI.KTKSY_CD = ? \n");
					sql.append("              AND SIZAI.KTKSY_CD_SIIREHIN = ? \n");
					sql.append("              AND SIIRE.KAISYA_CD = ? \n");
					sql.append("              AND SIIRE.KTKSY_CD = ? \n");
					sql.append("            ) SIZAI_SIIRE \n");
					sql.append("          WHERE  \n");
					sql.append("            SIZAI_SIIRE.RNUM = 1 \n");
					sql.append("        ) SIZAI_SIIRE ON SEIHIN.KAISYA_CD = SIZAI_SIIRE.KAISYA_CD AND SEIHIN.KTKSY_CD = SIZAI_SIIRE.KTKSY_CD AND SEIHIN.SEIHIN_CD = SIZAI_SIIRE.SEIHIN_CD \n");
					sql.append("        LEFT OUTER JOIN M_SEIHINYOUK YOUK ON SIZAI_SIIRE.YOUKI_CD = YOUK.YOUKI_CD \n");
					bindList.add(comUserSession.getKaisyaCd());
					bindList.add(comUserSession.getKtksyCd());
					bindList.add(comUserSession.getKtksyCd());
					bindList.add(comUserSession.getKaisyaCd());
					bindList.add(comUserSession.getKtksyCd());
				}
			}
		} else {
			// カテゴリ検索時
			TranTableCtg ctg = tranTable.getCtg();
			// 容器材質
			if (cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.YOUKIZAISITU)) {
				sql.append("        LEFT OUTER JOIN M_SSIZAI_GRP SIZAI ON ")
					.append(ctg.withAlias(ctg.getKaisyaCd())).append(" = SIZAI.KAISYA_CD AND ")
					.append(ctg.withAlias(ctg.getKtksyCd())).append(" = SIZAI.KTKSY_CD AND ")
					.append(ctg.withAlias(ctg.getSeihinCd())).append(" = SIZAI.SEIHIN_CD AND ")
					.append("SIZAI.KTKSY_CD_SIIREHIN = ? \n");
				bindList.add(comUserSession.getKtksyCd());
				sql.append("        LEFT OUTER JOIN M_SIIREHIN SIIRE ON SIZAI.KAISYA_CD = SIIRE.KAISYA_CD AND SIZAI.KTKSY_CD_SIIREHIN = SIIRE.KTKSY_CD AND SIZAI.SIIRESAKI_CD = SIIRE.SIIRESAKI_CD AND SIZAI.SIIREHIN_CD = SIIRE.SIIREHIN_CD \n");
				sql.append("        LEFT OUTER JOIN M_SEIHINYOUK YOUK ON SIIRE.YOUKI_CD = YOUK.YOUKI_CD \n");
			}
		}
		return sql.toString();
	}

	/**
	 * トランザクション検索sqlのWhere句取得
	 * @param comUserSession ログインユーザセッション情報
	 * @param searchHelper 検索Helper
	 * @param searchForm 検索Form
	 * @param tranTable トランザクションTable
	 * @param keisanDateKijunCol 計算日付基準の列名
	 * @param bindList バインドリスト
	 * @return sql
	 */
	private String getTranWhereSql(ComUserSession comUserSession, JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, TranTable tranTable, String keisanDateKijunCol, List<String> bindList) {
		StringBuilder sql = new StringBuilder();
		// ヘッダ
		TranTableHd hd = tranTable.getHd();
		// ディテール検索時
		TranTableDt dt = tranTable.getDt();
		// カテゴリ検索時
		TranTableCtg ctg = tranTable.getCtg();

		// 抽出・集計情報リスト
		List<CyusyutuSyukeiInfo> cyusyutuSyukeiInfoList = searchHelper.getCyusyutuSyukeiInfoList();
		// 抽出条件
		String cyusyutuJyoken = searchForm.getCyusyutuJyoken();
		// 集計内容
		String syukeiNaiyo = searchForm.getSyukeiNaiyo();
		// 会社コード
		sql.append("        ").append(hd.withAlias(hd.getKaisyaCd())).append(" = ? \n");
		bindList.add(comUserSession.getKaisyaCd());
		// 利用区分
		sql.append("        AND ").append(hd.withAlias("RIYOU_KBN")).append(" = ? \n");
		bindList.add(AVAILABLE_KB_RIYO_KA);

		if(searchHelper.isSearchDt()) {
			// 会社コード
			sql.append("        AND ").append(dt.withAlias(dt.getKaisyaCd())).append(" = ? \n");
			bindList.add(comUserSession.getKaisyaCd());
			// 利用区分
			sql.append("        AND ").append(dt.withAlias("RIYOU_KBN")).append(" = ? \n");
			bindList.add(AVAILABLE_KB_RIYO_KA);
			// 物品区分
			sql.append("        AND ").append(dt.withAlias(dt.getBupinKbn())).append(" IN (?, ?, ?, ?) \n");
			bindList.add(SYOHIN_KB_SYURUI);
			bindList.add(SYOHIN_KB_SYOKUHIN);
			bindList.add(SYOHIN_KB_SYOHIN);
			bindList.add(SYOHIN_KB_BUPPIN);

		} else {
			// 会社コード
			sql.append("        AND ").append(ctg.withAlias(ctg.getKaisyaCd())).append(" = ? \n");
			bindList.add(comUserSession.getKaisyaCd());
			// 利用区分
			sql.append("        AND ").append(ctg.withAlias("RIYOU_KBN")).append(" = ? \n");
			bindList.add(AVAILABLE_KB_RIYO_KA);
		}

		// 出荷・戻入の場合
		if(tranTable == TranTable.SYUKA || tranTable == TranTable.REINYU) {
			// 訂正のみ
			if(PbsUtil.isIncluded(searchForm.getTaisyoData(), JSK_SEL_DATA_KB_SYUKKA_TEISEI, JSK_SEL_DATA_KB_REINYU_EISEI)) {
				sql.append("        AND ").append(hd.withAlias(hd.getSyubetuCd())).append(" IN (?, ?) \n");
				bindList.add(DATAKIND_KB_TEISEI_PLUS);
				bindList.add(DATAKIND_KB_TEISEI_MINUS);
			}
		}
		// 戻入の場合
		if(tranTable == TranTable.REINYU) {
			// 出荷・戻入（振替除外）対比
			if (PbsUtil.isEqual(searchForm.getTaihiUmu(), TAIHI_UMU_KB_SYUKAREINYU_FURIKAE)) {
				sql.append("        AND ").append(hd.withAlias(hd.getSyubetuCd())).append(" <> ? \n");
				bindList.add(DATAKIND_KB_FURIKAE);
			}
		}

		// 営業支援自家用・小売有無_指定なし以外
		if(!PbsUtil.isEqual(searchForm.getJikakouriUmu(),JSK_OP1_UMU_KB_NONE)) {
			sql.append("        AND SUBSTR(TANTO.JIGYOSYO_CD,1,1) ");
			if(PbsUtil.isEqual(searchForm.getJikakouriUmu(),JSK_OP1_UMU_KB_JIKA_KOURI_JYOGAI)) {
				// 自家用・小売 除外
				sql.append(" NOT IN ('8','9')");
			} else if(PbsUtil.isEqual(searchForm.getJikakouriUmu(),JSK_OP1_UMU_KB_JIKAYO_JYOGAI)) {
				// 自家用　除外
				sql.append(" <> '9'");
			} else if(PbsUtil.isEqual(searchForm.getJikakouriUmu(),JSK_OP1_UMU_KB_JIKAYO_NOMI)) {
				// 自家用のみ
				sql.append(" = '9'");
			} else if(PbsUtil.isEqual(searchForm.getJikakouriUmu(),JSK_OP1_UMU_KB_KOURI_NOMI)) {
				// 小売のみ
				sql.append(" = '8'");
			}
			sql.append(" \n");
		}

		// 指定した検索条件
		if(PbsUtil.isEqual(cyusyutuJyoken, CYUSYUTU_JYOKEN_KB_JIGYOSYO)) {
			// 事業所
			sql.append("        AND TANTO.JIGYOSYO_CD = ? \n");
			bindList.add(searchForm.getJigyosyoCd());
		} else if(PbsUtil.isEqual(cyusyutuJyoken, CYUSYUTU_JYOKEN_KB_KA)) {
			// 課
			sql.append("        AND TANTO.KA_CD = ? \n");
			bindList.add(searchForm.getKaCd());
		} else if(PbsUtil.isEqual(cyusyutuJyoken, CYUSYUTU_JYOKEN_KB_TANTO)) {
			// 担当者
			sql.append("        AND TANTO.TANTO_CD = ? \n");
			bindList.add(searchForm.getTantosyaCd());
		} else if(PbsUtil.isEqual(cyusyutuJyoken, CYUSYUTU_JYOKEN_KB_COUNTRY)) {
			// 都道府県
			sql.append("        AND JIS.TODOFUKN_CD = ? \n");
			bindList.add(searchForm.getCountryCd());
		} else if(PbsUtil.isEqual(cyusyutuJyoken, CYUSYUTU_JYOKEN_KB_ZENKOKU)) {
			// 全国卸
			sql.append("        AND TOKU.HJISEKI_SYUKEI_CD = ? \n");
			bindList.add(searchForm.getZenkokuOrosiCd());
			//sql.append("        AND TOKU.SEIKYU_GYOTAI_KBN = ? \n");
			//bindList.add(GYOTAI_KB_OROSI_ZENKOKU);
		} else if(PbsUtil.isEqual(cyusyutuJyoken, CYUSYUTU_JYOKEN_KB_TOKUYAKU)) {
			// 特約
			sql.append("        AND OROSIH.OROSITEN_CD_1JITEN = ? \n");
			bindList.add(searchForm.getTokuyakuCd());
		} else if(PbsUtil.isEqual(cyusyutuJyoken, CYUSYUTU_JYOKEN_KB_SOUNISAKI)) {
			// 送荷先
			sql.append("        AND OROSIH.OROSITEN_CD_LAST = ? \n");
			bindList.add(searchForm.getSounisakiCd());
		} else if(PbsUtil.isEqual(cyusyutuJyoken, CYUSYUTU_JYOKEN_KB_SYUHANTEN)) {
			// 酒販店
			sql.append("        AND ").append(hd.withAlias(hd.getSyuhantenCd())).append(" = ? \n");
			bindList.add(searchForm.getSyuhantenCd());
		}

		if(searchHelper.isSearchDt()) {
			// ディテール検索時
			// 登録グループ
			List<String> torokuGroupCdList = searchForm.getTorokuGroupCdList();
			if(!torokuGroupCdList.isEmpty()) {
				if(PbsUtil.isEqual(syukeiNaiyo, SYUKEI_NAIYO_KB_SAKADANE)) {
					// 酒種
					sql.append("        AND SEIHIN.TANE_CD ").append(getInForTorokuGroup(torokuGroupCdList, bindList)).append(" \n");;
				} else if(PbsUtil.isEqual(syukeiNaiyo, SYUKEI_NAIYO_KB_SAKESITU)) {
					// 酒質
					sql.append("        AND TANE.HANBAI_BUNRUI_CD ").append(getInForTorokuGroup(torokuGroupCdList, bindList)).append(" \n");
				} else if(PbsUtil.isEqual(syukeiNaiyo, SYUKEI_NAIYO_KB_SYOHIN)) {
					// 商品
					sql.append("        AND ").append(dt.withAlias(dt.getShohinCd())).append(getInForTorokuGroup(torokuGroupCdList, bindList)).append(" \n");
				}
			}
		}

		// 結合した場合の条件
		// 全国卸
		if(cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.ZENKOKU)) {
			// 全国卸に紐付くデータのみを対象とする場合
			sql.append("        AND TOKU.SEIKYU_GYOTAI_KBN = ? \n");
			bindList.add(GYOTAI_KB_OROSI_ZENKOKU);
		}

		// 容器材質
		if(cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.YOUKIZAISITU)) {
			// 容器がある商品のみを対象とする場合
			 sql.append("        AND SIIRE.YOUKI_CD IS NOT NULL \n");
		}

		// 受注の場合
		if(tranTable == TranTable.JYUCYU) {
			// 出荷予定の場合
			if(PbsUtil.isEqual(searchForm.getTaisyoData(),JSK_SEL_DATA_KB_SYUKKA_YOTEI)) {
				// 出荷にデータが存在しないものが対象
				TranTableHd syukaHd = TranTable.SYUKA.getHd();
				sql.append("        AND NOT EXISTS (SELECT 'X' FROM ").append(syukaHd.getTableNm()).append(" ").append(syukaHd.getAlias()).append(" WHERE ")
					.append(hd.withAlias(hd.getKaisyaCd())).append(" = ").append(syukaHd.withAlias(syukaHd.getKaisyaCd())).append(" AND ")
					.append(hd.withAlias("JYUCYU_NO")).append(" = ").append(syukaHd.withAlias("JYUCYU_NO")).append(" ) \n");
			}
		}

		// 日付
		sql.append(getWhereDateSql(searchHelper.getDateInfo().getWhereDateRange(), keisanDateKijunCol, bindList));

		return sql.toString();
	}

	/**
	 * トランザクション検索sqlのGroup句取得
	 * @param searchHelper 検索Helper
	 * @param tranTable トランザクションTable
	 * @return sql
	 */
	private String getTranGroupSql(JisekiESienList_SearchHelper searchHelper, TranTable tranTable) {
		StringBuilder sql = new StringBuilder();

		// キー列
		List<KeyCol> keyColList = searchHelper.getKeyColList();

		for (KeyCol keyCol : keyColList) {
			if(sql.length() != 0) {
				sql.append("        ,");
			} else {
				sql.append("         ");
			}
			sql.append(getTranSelectGroupColSql(searchHelper, keyCol, tranTable)).append(" \n");
		}
		return sql.toString();
	}

	/**
	 * トランザクションのセレクト・グループ列名取得
	 * @param searchHelper 検索Helper
	 * @param keyCol キー列
	 * @param tranTable トランザクションTable
	 * @return 列名
	 */
	private String getTranSelectGroupColSql(JisekiESienList_SearchHelper searchHelper, KeyCol keyCol, TranTable tranTable) {
		// ヘッダ
		TranTableHd hd = tranTable.getHd();
		// ディテール
		TranTableDt dt = tranTable.getDt();
		// カテゴリ
		TranTableCtg ctg = tranTable.getCtg();

		switch (keyCol) {
		case JIGYOSYO_CD:
			// 事業所
			return "TANTO.JIGYOSYO_CD";
		case KA_CD:
			// 課
			return "TANTO.KA_CD";
		case TANTO_CD:
			// 担当者
			return "OROSIH.TANTOSYA_CD";
		case TODOFUKN_CD:
			// 都道府県
			return "JIS.TODOFUKN_CD";
		case HJISEKI_SYUKEI_CD:
			// 全国卸
			return "TOKU.HJISEKI_SYUKEI_CD";
		case OROSITEN_CD_1JITEN:
			// 特約
			return "OROSIH.OROSITEN_CD_1JITEN";
		case OROSITEN_CD_LAST:
			// 送荷先
			return "OROSIH.OROSITEN_CD_LAST";
		case SYUHANTEN_CD:
			// 酒販店
			return hd.withAlias(hd.getSyuhantenCd());
		case UNSOTEN_CD:
			// 運送店
			return hd.withAlias(hd.getUnsotenCd());
		case SYUKA_SAKI_COUNTRY_CD:
			// 輸出国
			return hd.withAlias(hd.getSyukaSakiCountryd());
		case TANE_CD:
			// 酒種
			return "SEIHIN.TANE_CD";
		case HANBAI_BUNRUI_CD:
			// 酒質
			return "TANE.HANBAI_BUNRUI_CD";
		case SHOHIN_CD:
			// 商品
			return dt.withAlias(dt.getShohinCd());
		case SOZAI_KBN:
			// 素材区分
			return "YOUK.SOZAI_KBN";
		case TNPN_VOL:
			// 容量
			return ctg.withAlias("TNPN_VOL");
		default:
			// 充填容量
			return "YOUK.JYUTEN_YOURYO";
		}
	}

	/**
	 * 日付条件sql取得
	 * @param dateRangeList 日付範囲リスト
	 * @param keisanDateKijunCol 計算日付基準の列名
	 * @param bindList バインドリスト
	 * @return sql
	 */
	private String getWhereDateSql(List<DateRange> dateRangeList, String keisanDateKijunCol, List<String> bindList) {
		StringBuilder sql = new StringBuilder();
		for (DateRange dateRange : dateRangeList) {
			if(sql.length() == 0) {
				sql.append("        AND ( \n");
				sql.append("          ");
			} else {
				sql.append("          OR ");
			}
			sql.append(keisanDateKijunCol).append(" BETWEEN ? AND ? \n");
			bindList.add(dateRange.getFrom());
			bindList.add(dateRange.getTo());
		}
		sql.append("        ) \n");
		return sql.toString();
	}

	/**
	 * 登録グループ条件のIn句作成
	 * @param torokuGroupCdList 登録グループリスト
	 * @param bindList バインドリスト
	 * @return sql
	 */
	private String getInForTorokuGroup(List<String> torokuGroupCdList, List<String> bindList) {
		StringBuilder sql = new StringBuilder();
		for (String torokuGroupCd : torokuGroupCdList) {
			if(sql.length() == 0) {
				sql.append(" IN ( ?");
			} else {
				sql.append(", ?");
			}
			bindList.add(torokuGroupCd);
		}
		sql.append(") ");
		return sql.toString();
	}

	/**
	 * レコード設定処理
	 * @param searchHelper 検索Helper
	 * @param searchForm 検索Form
	 * @param list リスト
	 */
	private void setupRecord(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, JisekiESienListList list) {
		// 見出しコード・名称の列名リスト取得
		List<MidasiColInfo> midasiColNmList = searchHelper.getMidasiColNmList();

		// 検索パターン
		IJisekiESienList_SearchSelectPtn selectPtn = searchHelper.getSelectPtn();

		// レコード件数分、各項目（見出し・累計・日付）の値・計算結果値・列名をMapへ設定
		for (PbsRecord record : list) {
			JisekiESienListRecord rec = (JisekiESienListRecord)record;
			rec.setMidasiColInfo(midasiColNmList);
			selectPtn.setupRecord(searchHelper, searchForm, rec);
		}
	}

	/**
	 * エクセル出力
	 * @param list 営業支援実績一覧リスト
	 * @param searchConditionHeaderList 検索条件ヘッダリスト
	 * @param searchConditionList 検索条件リスト
	 * @throws KitComException
	 */
	public void outputExcel(JisekiESienListList list, List<String> searchConditionHeaderList, List<String> searchConditionList) throws KitComException{
		// PrintWriter
		JisekiESienList_KitPrintWriter writer = new JisekiESienList_KitPrintWriter(getComUserSession());
		try {
			// 出力
			writer.addOutputRecordAndExecutePoi(list, searchConditionHeaderList, searchConditionList);
		} catch (DataNotFoundException e) {
			// 通らない
			throw new RuntimeException("DataNotFound", e);
		}
	}
}
