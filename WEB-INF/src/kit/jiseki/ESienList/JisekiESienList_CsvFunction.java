package kit.jiseki.ESienList;

import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_COUNTRY;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_JIGYOSYO;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_KA;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_SOUNISAKI;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_SYUHANTEN;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_TANTO;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_TOKUYAKU;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_UNSOTEN;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_ZENKOKU;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_ZENSYA;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_COUNTY_CD;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_HJS_KA;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_HJS_SITEN;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_JISEKI_TANTO_CD;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_JSK_HYOJI_TANI;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_JSK_KEISAN_DATE;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_JSK_OP1_UMU;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_JSK_SEL_DATA;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_TOKUYAKU_ZENKOKU;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_SEARCH;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_SEARCHLIST;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_COUNTRY;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_JIGYOSYO;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_KA;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_SAKADANE;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_SAKESITU;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_SOUNISAKI;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_SYOHIN;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_TANTO;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_TOKUYAKU;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_YOUKIZAISITU;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_YUSYUTU;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_ZENKOKU;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_OP_KB_JUTENYOUKI_ON;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_OP_KB_SAKESITU_ON;
import static kit.jiseki.ESienList.IJisekiESienList.TAIHI_UMU_KB_JISSEKI;
import static kit.jiseki.ESienList.IJisekiESienList.TAIHI_UMU_KB_KADOBI;
import static kit.jiseki.ESienList.IJisekiESienList.TAIHI_UMU_KB_NASI;
import static kit.jiseki.ESienList.IJisekiESienList.TAIHI_UMU_KB_SYUKAREINYU;
import static kit.jiseki.ESienList.IJisekiESienList.TAIHI_UMU_KB_SYUKAREINYU_FURIKAE;
import static kit.jiseki.ESienList.IJisekiESienList.TAIHI_UMU_KB_YOSAN_TAIHI_ON;
import static kit.jiseki.ESienList.IJisekiESienList.TAISYO_HYOJI_KIKAN_KB_DATE;
import static kit.jiseki.ESienList.IJisekiESienList.TAISYO_HYOJI_KIKAN_KB_MONTH;
import static kit.jiseki.ESienList.IJisekiESienList.TAISYO_HYOJI_KIKAN_KB_TOUGETU;
import static kit.jiseki.ESienList.IJisekiESienList.TAISYO_HYOJI_KIKAN_KB_YEAR;
import static kit.jiseki.ESienList.IJisekiESienList.TOROKU_NO_MAX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.inf.KitFunction;
import fb.inf.KitSelectList;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;
import kit.jiseki.ESienList.torokuGroup.JisekiESienListTorokuGroupList;
import kit.jiseki.ESienList.torokuGroup.JisekiESienListTorokuGroupRecord;

/**
 * 営業支援実績一覧のファイル出力ファンクション
 * @author t_kimura
 *
 */
public class JisekiESienList_CsvFunction extends KitFunction {

	/** serialVersionUID  */
	private static final long serialVersionUID = 8207842997197995873L;

	/** クラス名 */
	private static String className__ = JisekiESienList_CsvFunction.class.getName();
	/** 抽出条件Map */
	private static Map<String, String> CYUSYUTU_JOKEN_MAP = new HashMap<String, String>();
	/** 対象表示期間Map */
	private static Map<String, String> TAISYO_HYOJI_KIKAN_MAP = new HashMap<String, String>();
	/** 対比有無Map */
	private static Map<String, String> TAIHI_UMU_MAP = new HashMap<String, String>();
	/** 集計内容Map */
	private static Map<String, String> SYUKEINAIYO_MAP = new HashMap<String, String>();
	/** 検索条件ヘッダ1 */
	private static List<String> HEADER_SEARCH_CONDITION_1 = new ArrayList<String>();
	/** 検索条件ヘッダ2 */
	private static List<String> HEADER_SEARCH_CONDITION_2 = new ArrayList<String>();
	/** 検索条件ヘッダ3 */
	private static List<String> HEADER_SEARCH_CONDITION_3 = new ArrayList<String>();
	/** 検索条件ヘッダ4 */
	private static List<String> HEADER_SEARCH_CONDITION_4 = new ArrayList<String>();
	/** チェックあり */
	private static final String CHECK_ARI = PbsUtil.getMessageResourceString("com.text.ari");
	/** チェックなし */
	private static final String CHECK_NASI = PbsUtil.getMessageResourceString("com.text.nasi");

	static {
		// 抽出条件
		CYUSYUTU_JOKEN_MAP.put(CYUSYUTU_JYOKEN_KB_ZENSYA, PbsUtil.getMessageResourceString("com.text.zensyaLebel"));			// 全社レベル
		CYUSYUTU_JOKEN_MAP.put(CYUSYUTU_JYOKEN_KB_JIGYOSYO, PbsUtil.getMessageResourceString("com.text.jigyosyoLebel"));		// 事業所レベル
		CYUSYUTU_JOKEN_MAP.put(CYUSYUTU_JYOKEN_KB_KA, PbsUtil.getMessageResourceString("com.text.kaLebel"));					// 課レベル
		CYUSYUTU_JOKEN_MAP.put(CYUSYUTU_JYOKEN_KB_TANTO, PbsUtil.getMessageResourceString("com.text.tantosyaLebel"));			// 担当者レベル
		CYUSYUTU_JOKEN_MAP.put(CYUSYUTU_JYOKEN_KB_COUNTRY, PbsUtil.getMessageResourceString("com.text.todofukenLebel"));		// 都道府県レベル
		CYUSYUTU_JOKEN_MAP.put(CYUSYUTU_JYOKEN_KB_ZENKOKU, PbsUtil.getMessageResourceString("com.text.zenkokuorosiLebel"));		// 全国卸レベル
		CYUSYUTU_JOKEN_MAP.put(CYUSYUTU_JYOKEN_KB_TOKUYAKU, PbsUtil.getMessageResourceString("com.text.tokuyakuLebel"));		// 特約レベル
		CYUSYUTU_JOKEN_MAP.put(CYUSYUTU_JYOKEN_KB_SOUNISAKI, PbsUtil.getMessageResourceString("com.text.sounisakiLebel"));		// 送荷先レベル
		CYUSYUTU_JOKEN_MAP.put(CYUSYUTU_JYOKEN_KB_SYUHANTEN, PbsUtil.getMessageResourceString("com.text.syuhantenLebel"));		// 酒販店レベル
		CYUSYUTU_JOKEN_MAP.put(CYUSYUTU_JYOKEN_KB_UNSOTEN, PbsUtil.getMessageResourceString("com.text.unsotenLebel"));			// 運送店レベル

		// 対象表示期間
		TAISYO_HYOJI_KIKAN_MAP.put(TAISYO_HYOJI_KIKAN_KB_TOUGETU, PbsUtil.getMessageResourceString("com.text.tougetu10"));		// 当月・10～当月・1～当月
		TAISYO_HYOJI_KIKAN_MAP.put(TAISYO_HYOJI_KIKAN_KB_YEAR, PbsUtil.getMessageResourceString("com.text.siteinen"));			// 指定年
		TAISYO_HYOJI_KIKAN_MAP.put(TAISYO_HYOJI_KIKAN_KB_MONTH, PbsUtil.getMessageResourceString("com.text.siteituki"));		// 指定月
		TAISYO_HYOJI_KIKAN_MAP.put(TAISYO_HYOJI_KIKAN_KB_DATE, PbsUtil.getMessageResourceString("com.text.siteibi"));			// 指定日
		// 対比有無
		TAIHI_UMU_MAP.put(TAIHI_UMU_KB_NASI, PbsUtil.getMessageResourceString("com.text.siteinasi"));							// 指定なし
		TAIHI_UMU_MAP.put(TAIHI_UMU_KB_SYUKAREINYU, PbsUtil.getMessageResourceString("com.text.syukkareinyutaihi"));			// 出荷・戻入対比
		TAIHI_UMU_MAP.put(TAIHI_UMU_KB_SYUKAREINYU_FURIKAE, PbsUtil.getMessageResourceString("com.text.syukkareinyutaihiFurikae"));			// 出荷・戻入（振替除外）対比
		TAIHI_UMU_MAP.put(TAIHI_UMU_KB_JISSEKI, PbsUtil.getMessageResourceString("com.text.jisekitaihi"));						// 実績対比
		TAIHI_UMU_MAP.put(TAIHI_UMU_KB_KADOBI, PbsUtil.getMessageResourceString("com.text.kadoubitaihi"));						// 稼働日対比
		// 集計内容
		SYUKEINAIYO_MAP.put(SYUKEI_NAIYO_KB_JIGYOSYO,PbsUtil.getMessageResourceString("com.text.jigyosyobetu"));				// 事業所別
		SYUKEINAIYO_MAP.put(SYUKEI_NAIYO_KB_KA,PbsUtil.getMessageResourceString("com.text.kabetu"));							// 課別
		SYUKEINAIYO_MAP.put(SYUKEI_NAIYO_KB_TANTO,PbsUtil.getMessageResourceString("com.text.tantobetu"));						// 担当別
		SYUKEINAIYO_MAP.put(SYUKEI_NAIYO_KB_TOKUYAKU,PbsUtil.getMessageResourceString("com.text.tokuyakubetu"));				// 特約別
		SYUKEINAIYO_MAP.put(SYUKEI_NAIYO_KB_SOUNISAKI,PbsUtil.getMessageResourceString("com.text.sounisakibetu"));				// 送荷先別
		SYUKEINAIYO_MAP.put(SYUKEI_NAIYO_KB_ZENKOKU,PbsUtil.getMessageResourceString("com.text.zenkokuorosibetu"));				// 全国別
		SYUKEINAIYO_MAP.put(SYUKEI_NAIYO_KB_COUNTRY,PbsUtil.getMessageResourceString("com.text.todoufukenbetu"));				// 都道府県別
		SYUKEINAIYO_MAP.put(SYUKEI_NAIYO_KB_YUSYUTU,PbsUtil.getMessageResourceString("com.text.yusitukokubetu"));				// 輸出国別
		SYUKEINAIYO_MAP.put(SYUKEI_NAIYO_KB_YOUKIZAISITU,PbsUtil.getMessageResourceString("com.text.youkizaisitubetu"));		// 容器材質別
		SYUKEINAIYO_MAP.put(SYUKEI_NAIYO_KB_SAKADANE,PbsUtil.getMessageResourceString("com.text.sakadanebetu"));				// 酒種別
		SYUKEINAIYO_MAP.put(SYUKEI_NAIYO_KB_SAKESITU,PbsUtil.getMessageResourceString("com.text.sakesitubetu"));				// 酒質別
		SYUKEINAIYO_MAP.put(SYUKEI_NAIYO_KB_SYOHIN,PbsUtil.getMessageResourceString("com.text.syohincdbetu"));					// 商品CD別
		// 検索条件ヘッダ
		HEADER_SEARCH_CONDITION_1.add(PbsUtil.getMessageResourceString("com.text.taisyodata"));									// 対象データ
		HEADER_SEARCH_CONDITION_1.add(PbsUtil.getMessageResourceString("com.text.keisandatekijyun"));							// 計算日付基準
		HEADER_SEARCH_CONDITION_1.add(PbsUtil.getMessageResourceString("com.text.jikakouriumu"));								// 自家用・小売有無
		HEADER_SEARCH_CONDITION_1.add(PbsUtil.getMessageResourceString("com.text.hyojitani"));									// 表示単位
		HEADER_SEARCH_CONDITION_1.add(PbsUtil.getMessageResourceString("com.text.yosanCheck"));									// 予算対比_チェック
		HEADER_SEARCH_CONDITION_1.add(PbsUtil.getMessageResourceString("com.text.cyusitujyoukenRadio"));						// 抽出条件_ラジオボタン名
		HEADER_SEARCH_CONDITION_1.add(PbsUtil.getMessageResourceString("com.text.cyusitujyoukenCd"));							// 抽出条件_CD
		HEADER_SEARCH_CONDITION_1.add(PbsUtil.getMessageResourceString("com.text.cyusitujyoukenNm"));							// 抽出条件_名称
		HEADER_SEARCH_CONDITION_1.add(PbsUtil.getMessageResourceString("com.text.taisyohyojikikanRadio"));						// 対象表示期間_ラジオボタン名
		// 検索条件ヘッダ(当月用)
		HEADER_SEARCH_CONDITION_2.add(PbsUtil.getMessageResourceString("com.text.siteibi"));									// 指定日
		// 検索条件ヘッダ(当月以外用)
		HEADER_SEARCH_CONDITION_3.add(PbsUtil.getMessageResourceString("com.text.taisyohyojikikanFrom"));						// 対象表示期間_From
		HEADER_SEARCH_CONDITION_3.add(PbsUtil.getMessageResourceString("com.text.taisyohyojikikanTo"));							// 対象表示期間_To
		// 検索条件ヘッダ
		HEADER_SEARCH_CONDITION_4.add(PbsUtil.getMessageResourceString("com.text.taihiumuRadio"));								// 対比有無_ラジオボタン名
		HEADER_SEARCH_CONDITION_4.add(PbsUtil.getMessageResourceString("com.text.syukeinaiyoRadio"));							// 集計内容_ラジオボタン名
		HEADER_SEARCH_CONDITION_4.add(PbsUtil.getMessageResourceString("com.text.syukeinaiyoOpSakesituCheck"));					// 集計内容op_酒質_チェック
		HEADER_SEARCH_CONDITION_4.add(PbsUtil.getMessageResourceString("com.text.syukeinaiyoOpJyutenyoukiCheck"));				// 集計内容op_充填容器_チェック
		for (int i = 0; i < TOROKU_NO_MAX; i++) {
			HEADER_SEARCH_CONDITION_4.add(String.format(PbsUtil.getMessageResourceString("com.text.codeSiteiCd"), i+1));		// 指定CD_CD
			HEADER_SEARCH_CONDITION_4.add(String.format(PbsUtil.getMessageResourceString("com.text.codeSiteiNm"), i+1));		// 指定CD_名称
		}
	}

	/**
	 * コンストラクタ
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpサーブレットRequest
	 * @param response HttpサーブレットResponse
	 */
	public JisekiESienList_CsvFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		super(mapping, form, request, response);
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#execute_()
	 */
	@Override
	protected String execute_() throws Exception {
		// 検索サービス
		JisekiESienList_SearchService searchService = new JisekiESienList_SearchService(getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// セッションから検索条件・検索結果を取得
		JisekiESienList_SearchForm searchForm = (JisekiESienList_SearchForm)getSession(KEY_SS_SEARCH);
		JisekiESienListList list = (JisekiESienListList)getSession(KEY_SS_SEARCHLIST);

		// エクセル出力
		searchService.outputExcel(list, createSearchConditionHeaderList(searchForm), createSearchCondition(searchForm));

		// ダウンロードリクエスト設定
		setRequest(ONLOAD_POPUP_URL, CSV_DOWNLOAD_POPUP_URL);

		return FORWARD_SUCCESS;
	}

	/**
	 * 検索条件ヘッダリスト作成
	 * @param searchForm 検索Form
	 * @return ヘッダリスト
	 */
	private List<String> createSearchConditionHeaderList(JisekiESienList_SearchForm searchForm){

		List<String> headerList = new ArrayList<>();
		// 検索条件
		headerList.addAll(HEADER_SEARCH_CONDITION_1);
		if(PbsUtil.isEqual(searchForm.getTaisyoHyojiKikan(), TAISYO_HYOJI_KIKAN_KB_TOUGETU)) {
			// 当月の場合
			headerList.addAll(HEADER_SEARCH_CONDITION_2);
		} else {
			// 当月以外
			headerList.addAll(HEADER_SEARCH_CONDITION_3);
		}
		headerList.addAll(HEADER_SEARCH_CONDITION_4);
		return headerList;
	}

	/**
	 * 検索条件リスト作成
	 * @param searchForm 検索Form
	 * @return 検索条件リスト
	 */
	private List<String> createSearchCondition(JisekiESienList_SearchForm searchForm) {
		// 対象データ
		KitSelectList taisyoDataSelectList = (KitSelectList)getSession(KEY_SS_KBN_JSK_SEL_DATA);
		// 計算日付基準
		KitSelectList keisanSelectList = (KitSelectList)getSession(KEY_SS_KBN_JSK_KEISAN_DATE);
		// 自家用・小売有無
		KitSelectList opSelectList = (KitSelectList)getSession(KEY_SS_KBN_JSK_OP1_UMU);
		// 表示単位
		KitSelectList taniSelectList = (KitSelectList)getSession(KEY_SS_KBN_JSK_HYOJI_TANI);
		// 事業所
		KitSelectList sitenSelectList = (KitSelectList)getSession(KEY_SS_KBN_HJS_SITEN);
		// 課
		KitSelectList kaSelectList = (KitSelectList)getSession(KEY_SS_KBN_HJS_KA);
		// 担当者
		KitSelectList tantoSelectList = (KitSelectList)getSession(KEY_SS_KBN_JISEKI_TANTO_CD);
		// 都道府県
		KitSelectList countrySelectList = (KitSelectList)getSession(KEY_SS_KBN_COUNTY_CD);
		// 全国卸ﾚﾍﾞﾙ
		KitSelectList zenkokuSelectList = (KitSelectList)getSession(KEY_SS_KBN_TOKUYAKU_ZENKOKU);

		List<String> condition = new ArrayList<>();
		// 対象データ
		condition.add(taisyoDataSelectList.decodeCode(searchForm.getTaisyoData()));
		// 計算日付基準
		condition.add(keisanSelectList.decodeCode(searchForm.getKeisanDateKijun()));
		// 自家用・小売有無
		condition.add(opSelectList.decodeCode(searchForm.getJikakouriUmu()));
		// 表示単位
		condition.add(taniSelectList.decodeCode(searchForm.getHyojiTani()));
		// 対比有無 予算対比
		condition.add(PbsUtil.isEqual(searchForm.getYosanTaihi(), TAIHI_UMU_KB_YOSAN_TAIHI_ON) ? CHECK_ARI : CHECK_NASI);

		// 抽出条件
		String cyusyutuJyoken = searchForm.getCyusyutuJyoken();
		condition.add(CYUSYUTU_JOKEN_MAP.get(searchForm.getCyusyutuJyoken()));
		if(PbsUtil.isEqual(cyusyutuJyoken,CYUSYUTU_JYOKEN_KB_JIGYOSYO)) {
			// 事業所（コード・名称）
			condition.add(searchForm.getJigyosyoCd());
			condition.add(sitenSelectList.decodeCode(searchForm.getJigyosyoCd()));
		} else if(PbsUtil.isEqual(cyusyutuJyoken,CYUSYUTU_JYOKEN_KB_KA)) {
			// 課（コード・名称）
			condition.add(searchForm.getKaCd());
			condition.add(kaSelectList.decodeCode(searchForm.getKaCd()));
		} else if(PbsUtil.isEqual(cyusyutuJyoken,CYUSYUTU_JYOKEN_KB_TANTO)) {
			// 担当（コード・名称）
			condition.add(searchForm.getTantosyaCd());
			condition.add(tantoSelectList.decodeCode(searchForm.getTantosyaCd()));
		} else if(PbsUtil.isEqual(cyusyutuJyoken,CYUSYUTU_JYOKEN_KB_COUNTRY)) {
			// 都道府県（コード・名称）
			condition.add(searchForm.getCountryCd());
			condition.add(countrySelectList.decodeCode(searchForm.getCountryCd()));
		} else if(PbsUtil.isEqual(cyusyutuJyoken,CYUSYUTU_JYOKEN_KB_ZENKOKU)) {
			// 全国卸（コード・名称）
			condition.add(searchForm.getZenkokuOrosiCd());
			condition.add(zenkokuSelectList.decodeCode(searchForm.getZenkokuOrosiCd()));
		} else if(PbsUtil.isEqual(cyusyutuJyoken,CYUSYUTU_JYOKEN_KB_TOKUYAKU)) {
			// 特約（コード・名称）
			condition.add(searchForm.getTokuyakuCd());
			condition.add(searchForm.getTokuyakuNm());
		} else if(PbsUtil.isEqual(cyusyutuJyoken,CYUSYUTU_JYOKEN_KB_SOUNISAKI)) {
			// 送荷先（コード・名称）
			condition.add(searchForm.getSounisakiCd());
			condition.add(searchForm.getSounisakiNm());
		} else if(PbsUtil.isEqual(cyusyutuJyoken,CYUSYUTU_JYOKEN_KB_SYUHANTEN)) {
			// 酒販店（コード・名称）
			condition.add(searchForm.getSyuhantenCd());
			condition.add(searchForm.getSyuhantenNm());
		} else {
			condition.add("");
			condition.add("");
		}

		// 対象表示期間
		condition.add(TAISYO_HYOJI_KIKAN_MAP.get(searchForm.getTaisyoHyojiKikan()));
		if(PbsUtil.isEqual(searchForm.getTaisyoHyojiKikan(), TAISYO_HYOJI_KIKAN_KB_TOUGETU)) {
			// 当月
			condition.add(searchForm.getTaisyoDate());
		} else if(PbsUtil.isEqual(searchForm.getTaisyoHyojiKikan(), TAISYO_HYOJI_KIKAN_KB_YEAR)) {
			// 年
			condition.add(searchForm.getTaisyoYearFrom());
			condition.add(searchForm.getTaisyoYearTo());
		} else if(PbsUtil.isEqual(searchForm.getTaisyoHyojiKikan(), TAISYO_HYOJI_KIKAN_KB_MONTH)) {
			// 月
			condition.add(PbsUtil.getYYYY(searchForm.getTaisyoMonthFrom()) + "/" + PbsUtil.getMM(searchForm.getTaisyoMonthFrom()));
			condition.add(PbsUtil.getYYYY(searchForm.getTaisyoMonthTo()) + "/" + PbsUtil.getMM(searchForm.getTaisyoMonthTo()));
		} else if(PbsUtil.isEqual(searchForm.getTaisyoHyojiKikan(), TAISYO_HYOJI_KIKAN_KB_DATE)) {
			// 日
			condition.add(PbsUtil.toDateFormat(searchForm.getTaisyoDateFrom()));
			condition.add(PbsUtil.toDateFormat(searchForm.getTaisyoDateTo()));
		}

		// 対比有無
		condition.add(TAIHI_UMU_MAP.get(searchForm.getTaihiUmu()));

		// 集計内容
		condition.add(SYUKEINAIYO_MAP.get(searchForm.getSyukeiNaiyo()));
		// 集計内容op
		condition.add(PbsUtil.isEqual(searchForm.getSakesituOp(), SYUKEI_NAIYO_OP_KB_SAKESITU_ON) ? CHECK_ARI : CHECK_NASI);
		condition.add(PbsUtil.isEqual(searchForm.getJyutenyoukiOp(), SYUKEI_NAIYO_OP_KB_JUTENYOUKI_ON) ? CHECK_ARI : CHECK_NASI);

		// 登録グループ
		JisekiESienListTorokuGroupList torokuGroupLit = searchForm.getTorokuGroupList();
		if(torokuGroupLit != null) {
			for (PbsRecord rec : searchForm.getTorokuGroupList()) {
				JisekiESienListTorokuGroupRecord record = (JisekiESienListTorokuGroupRecord)rec;
				condition.add(record.getTorokuCd());
				condition.add(record.getTorokuNm());
			}
		} else {
			for (int i = 0; i < TOROKU_NO_MAX; i++) {
				condition.add("");
				condition.add("");
			}
		}
		return condition;
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#isTransactionalFunction()
	 */
	@Override
	protected boolean isTransactionalFunction() {
		return false;
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#getFunctionName()
	 */
	@Override
	protected String getFunctionName() {
		return className__;
	}

}
