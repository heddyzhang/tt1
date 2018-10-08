package kit.jiseki.ESienList;

import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_1000YEN;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_BARA;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_CASE;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_KG;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_KL;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_KOKU;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_L;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_T;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_YEN;
import static fb.com.IKitComConst.JSK_SEL_DATA_KB_JUN_SYUKKA;
import static fb.com.IKitComConst.JSK_SEL_DATA_KB_JYUCYU_NOMI;
import static fb.com.IKitComConst.JSK_SEL_DATA_KB_REINYU_EISEI;
import static fb.com.IKitComConst.JSK_SEL_DATA_KB_REINYU_NOMI;
import static fb.com.IKitComConst.JSK_SEL_DATA_KB_SYUKKA_NOMI;
import static fb.com.IKitComConst.JSK_SEL_DATA_KB_SYUKKA_TEISEI;
import static fb.com.IKitComConst.JSK_SEL_DATA_KB_SYUKKA_YOTEI;
import static fb.com.IKitComConstHM.TBL_T_JUCYU_JSKCTG;
import static fb.com.IKitComConstHM.TBL_T_JYUCYU_DT;
import static fb.com.IKitComConstHM.TBL_T_JYUCYU_HD;
import static fb.com.IKitComConstHM.TBL_T_REINYU_DT;
import static fb.com.IKitComConstHM.TBL_T_REINYU_HD;
import static fb.com.IKitComConstHM.TBL_T_REINYU_JSKCTG;
import static fb.com.IKitComConstHM.TBL_T_SYUKA_DT;
import static fb.com.IKitComConstHM.TBL_T_SYUKA_HD;
import static fb.com.IKitComConstHM.TBL_T_SYUKA_JSKCTG;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_COUNTRY;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_JIGYOSYO;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_KA;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_SOUNISAKI;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_SYUHANTEN;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_TANTO;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_TOKUYAKU;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_UNSOTEN;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_ZENKOKU;
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
import static kit.jiseki.ESienList.IJisekiESienList.TAISYO_HYOJI_KIKAN_KB_MONTH;
import static kit.jiseki.ESienList.IJisekiESienList.TAISYO_HYOJI_KIKAN_KB_TOUGETU;
import static kit.jiseki.ESienList.IJisekiESienList.TAISYO_HYOJI_KIKAN_KB_YEAR;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fb.com.ComEigyoCalendarUtil;
import fb.inf.exception.KitException;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 検索Helperクラス
 * @author t_kimura
 *
 */
public class JisekiESienList_SearchHelper {

	/** ヘッダ見出し_事業所CD */
	static final String HEADER_CD_MIDASI_JIGYOSYO = PbsUtil.getMessageResourceString("com.text.zigyousyoCd");
	/** ヘッダ見出し_課CD */
	static final String HEADER_CD_MIDASI_KA = PbsUtil.getMessageResourceString("com.text.kaCd");
	/** ヘッダ見出し_担当者CD */
	static final String HEADER_CD_MIDASI_TANTO = PbsUtil.getMessageResourceString("com.text.tantoCd");
	/** ヘッダ見出し_都道府県CD */
	static final String HEADER_CD_MIDASI_TODOFUKN = PbsUtil.getMessageResourceString("com.text.todofuknCd");
	/** ヘッダ見出し_全国卸CD */
	static final String HEADER_CD_MIDASI_ZENKOKU = PbsUtil.getMessageResourceString("com.text.zenkokuOrosiCd");
	/** ヘッダ見出し_特約(1次店)CD */
	static final String HEADER_CD_MIDASI_OROSITEN_1JITEN = PbsUtil.getMessageResourceString("com.text.tokuyaku1jitenCd");
	/** ヘッダ見出し_最終送荷先CD */
	static final String HEADER_CD_MIDASI_OROSITEN_LAST = PbsUtil.getMessageResourceString("com.text.tokuyakuSaisyuCd");
	/** ヘッダ見出し_酒販店CD */
	static final String HEADER_CD_MIDASI_SYUHANTEN = PbsUtil.getMessageResourceString("com.text.syuhantenCd");
	/** ヘッダ見出し_運送店CD */
	static final String HEADER_CD_MIDASI_UNSOTEN = PbsUtil.getMessageResourceString("com.text.unsotenCd");
	/** ヘッダ見出し_輸出国CD */
	static final String HEADER_CD_MIDASI_SYUKA_SAKI_COUNTRY = PbsUtil.getMessageResourceString("com.text.syukaSakiCountryCd");
	/** ヘッダ見出し_酒種CD */
	static final String HEADER_CD_MIDASI_SAKADANE = PbsUtil.getMessageResourceString("com.text.sakadaneCd");
	/** ヘッダ見出し_酒質CD */
	static final String HEADER_CD_MIDASI_SAKESITU = PbsUtil.getMessageResourceString("com.text.sakesituCd");
	/** ヘッダ見出し_商品CD */
	static final String HEADER_CD_MIDASI_SHOHIN = PbsUtil.getMessageResourceString("com.text.syohinCd");
	/** ヘッダ見出し_素材CD */
	static final String HEADER_CD_MIDASI_JYUTEN_YOUKI = PbsUtil.getMessageResourceString("com.text.sozaiKbn");
	/** ヘッダ見出し_事業所名 */
	static final String HEADER_NM_MIDASI_JIGYOSYO = PbsUtil.getMessageResourceString("com.text.zigyousyoNm");
	/** ヘッダ見出し_課名 */
	static final String HEADER_NM_MIDASI_KA = PbsUtil.getMessageResourceString("com.text.kaNm");
	/** ヘッダ見出し_担当者名 */
	static final String HEADER_NM_MIDASI_TANTO = PbsUtil.getMessageResourceString("com.text.tantoNm");
	/** ヘッダ見出し_都道府県名 */
	static final String HEADER_NM_MIDASI_TODOFUKN = PbsUtil.getMessageResourceString("com.text.todofuknNm");
	/** ヘッダ見出し_全国卸名 */
	static final String HEADER_NM_MIDASI_ZENKOKU = PbsUtil.getMessageResourceString("com.text.zenkokuOrosiNm");
	/** ヘッダ見出し_特約(1次店)名 */
	static final String HEADER_NM_MIDASI_OROSITEN_1JITEN = PbsUtil.getMessageResourceString("com.text.tokuyaku1jitenNm");
	/** ヘッダ見出し_最終送荷先名 */
	static final String HEADER_NM_MIDASI_OROSITEN_LAST = PbsUtil.getMessageResourceString("com.text.saisyuSounisakiNm");
	/** ヘッダ見出し_酒販店名 */
	static final String HEADER_NM_MIDASI_SYUHANTEN = PbsUtil.getMessageResourceString("com.text.syuhantenNm");
	/** ヘッダ見出し_運送店名 */
	static final String HEADER_NM_MIDASI_UNSOTEN = PbsUtil.getMessageResourceString("com.text.unsotenNm");
	/** ヘッダ見出し_輸出国名 */
	static final String HEADER_NM_MIDASI_SYUKA_SAKI_COUNTRY = PbsUtil.getMessageResourceString("com.text.yusyutuCountryNm");
	/** ヘッダ見出し_酒種名 */
	static final String HEADER_NM_MIDASI_SAKADANE = PbsUtil.getMessageResourceString("com.text.sakadaneNm");
	/** ヘッダ見出し_酒質名 */
	static final String HEADER_NM_MIDASI_SAKESITU = PbsUtil.getMessageResourceString("com.text.sakesituNm");
	/** ヘッダ見出し_商品名 */
	static final String HEADER_NM_MIDASI_SHOHIN = PbsUtil.getMessageResourceString("com.text.syohnNm");
	/** ヘッダ見出し_容器名 */
	static final String HEADER_NM_MIDASI_YOUKI = PbsUtil.getMessageResourceString("com.text.youkinm");
	/** ヘッダ見出し_入数 */
	static final String HEADER_NM_MIDASI_IRISU = PbsUtil.getMessageResourceString("com.text.iriSu");
	/** ヘッダ見出し_容量 */
	static final String HEADER_NM_MIDASI_YOURYO = PbsUtil.getMessageResourceString("com.text.youryo");
	/** ヘッダ見出し_容器材質 */
	static final String HEADER_NM_MIDASI_YOUKIZAISITU = PbsUtil.getMessageResourceString("com.text.youkizaisituNm");
	/** ヘッダ見出し_充填容器名 */
	static final String HEADER_NM_MIDASI_JYUTEN_YOUKI = PbsUtil.getMessageResourceString("com.text.jyutenYoukiNm");
	/** ヘッダ_当月累計 */
	static final String HEADER_NM_TOGETU_RUIKEI = PbsUtil.getMessageResourceString("com.text.tougetsuRuikei");
	/** ヘッダ_10～当月累計 */
	static final String HEADER_NM_TOGETU_10_RUIKEI = PbsUtil.getMessageResourceString("com.text.10tougetu");
	/** ヘッダ_1～当月累計 */
	static final String HEADER_NM_TOGETU_1_RUIKEI = PbsUtil.getMessageResourceString("com.text.1tougetu");
	/** ヘッダ_本年 */
	static final String HEADER_NM_HONNEN  = PbsUtil.getMessageResourceString("com.text.honnen");
	/** ヘッダ_前年 */
	static final String HEADER_NM_ZENNEN  = PbsUtil.getMessageResourceString("com.text.zennen");
	/** ヘッダ_対比 */
	static final String HEADER_NM_TAIHI = PbsUtil.getMessageResourceString("com.text.Taihi");
	/** ヘッダ_前年 */
	static final String HEADER_NM_ZENNENHI  = PbsUtil.getMessageResourceString("com.text.zennenhi");
	/** ヘッダ_日計 */
	static final String HEADER_NM_NIKKEI = PbsUtil.getMessageResourceString("com.text.nikei");
	/** ヘッダ_累計 */
	static final String HEADER_NM_RUIKEI = PbsUtil.getMessageResourceString("com.text.ruikei");
	/** ヘッダ_出荷 */
	static final String HEADER_NM_SYUKA = PbsUtil.getMessageResourceString("com.text.syuka");
	/** ヘッダ_戻入 */
	static final String HEADER_NM_REINYU = PbsUtil.getMessageResourceString("com.text.reinyu");
	/** ヘッダ_本年予算 */
	static final String HEADER_NM_HONNEN_YOSAN = PbsUtil.getMessageResourceString("com.text.honnenYosan");
	/** ヘッダ_予算比 */
	static final String HEADER_NM_YOSANHI = PbsUtil.getMessageResourceString("com.text.yosanhi");
	/** ヘッダ_進捗率 */
	static final String HEADER_NM_SINCYOKURITU = PbsUtil.getMessageResourceString("com.text.statusRt");


	/** エイリアス名_メインテーブル */
	static final String ALIAS_E_SIEN_LIST = "E_SIEN_LIST";
	/** エイリアス名_メインテーブル_（ドット付） */
	static final String ALIAS_E_SIEN_LIST_DOT = ALIAS_E_SIEN_LIST + ".";
	/** エイリアス名_受注 */
	static final String ALIAS_JYUCYU = "JYUCYU";
	/** エイリアス名_出荷 */
	static final String ALIAS_SYUKA = "SYUKA";
	/** エイリアス名_戻入 */
	static final String ALIAS_REINYU = "REINYU";

	/** 列名_日計 */
	static final String SELECT_COL_NM_NIKKEI = "NIKKEI";
	/** 列名_当月累計_本年 */
	static final String SELECT_COL_NM_TOGETU_HONNEN = "TOGETU_HONNEN";
	/** 列名_当月累計_前年 */
	static final String SELECT_COL_NM_TOGETU_ZENNEN = "TOGETU_ZENNEN";
	/** 列名_当月累計_対比 */
	static final String SELECT_COL_NM_TOGETU_TAIHI = "TOGETU_TAIHI";
	/** 列名_10～当月累計_本年 */
	static final String SELECT_COL_NM_TOGETU_10_HONNEN = "TOGETU_10_HONNEN";
	/** 列名_10～当月累計_前年 */
	static final String SELECT_COL_NM_TOGETU_10_ZENNEN = "TOGETU_10_ZENNEN";
	/** 列名_10～当月累計_対比 */
	static final String SELECT_COL_NM_TOGETU_10_TAIHI = "TOGETU_10_TAIHI";
	/** 列名_1～当月累計_本年 */
	static final String SELECT_COL_NM_TOGETU_1_HONNEN = "TOGETU_1_HONNEN";
	/** 列名_1～当月累計_前年 */
	static final String SELECT_COL_NM_TOGETU_1_ZENNEN = "TOGETU_1_ZENNEN";
	/** 列名_1～当月累計_対比 */
	static final String SELECT_COL_NM_TOGETU_1_TAIHI = "TOGETU_1_TAIHI";

	/** 列名_当月累計_出荷 */
	static final String SELECT_COL_NM_TOGETU_SYUKA = "TOGETU_SYUKA";
	/** 列名_当月累計_戻入 */
	static final String SELECT_COL_NM_TOGETU_REINYU = "TOGETU_REINYU";
	/** 列名_10～当月累計_出荷 */
	static final String SELECT_COL_NM_TOGETU_10_SYUKA = "TOGETU_10_SYUKA";
	/** 列名_10～当月累計_戻入 */
	static final String SELECT_COL_NM_TOGETU_10_REINYU = "TOGETU_10_REINYU";
	/** 列名_1～当月累計_出荷 */
	static final String SELECT_COL_NM_TOGETU_1_SYUKA = "TOGETU_1_SYUKA";
	/** 列名_1～当月累計_戻入 */
	static final String SELECT_COL_NM_TOGETU_1_REINYU = "TOGETU_1_REINYU";


	/** 列名_累計 */
	static final String SELECT_COL_NM_RUIKEI = "RUIKEI";
	/** 列名_累計_出荷 */
	static final String SELECT_COL_NM_RUIKEI_SYUKA = "RUIKEI_SYUKA";
	/** 列名_累計_戻入 */
	static final String SELECT_COL_NM_RUIKEI_REINYU = "RUIKEI_REINYU";
	/** 列名_累計_本年 */
	static final String SELECT_COL_NM_RUIKEI_HONNEN = "RUIKEI_HONNEN";
	/** 列名_累計_前年 */
	static final String SELECT_COL_NM_RUIKEI_ZENNEN = "RUIKEI_ZENNEN";
	/** 列名_累計_対比 */
	static final String SELECT_COL_NM_RUIKEI_TAIHI = "RUIKEI_TAIHI";
	/** 列名_累計_本年予算 */
	static final String SELECT_COL_NM_RUIKEI_HONNEN_YOSAN = "RUIKEI_HONNEN_YOSAN";
	/** 列名_累計_本年比 */
	static final String SELECT_COL_NM_RUIKEI_YOSANHI = "RUIKEI_YOSANHI";
	/** 列名_出荷 */
	static final String SELECT_COL_NM_SYUKA = "SYUKA_%d";
	/** 列名_戻入 */
	static final String SELECT_COL_NM_REINYU = "REINYU_%d";
	/** 列名_本年 */
	static final String SELECT_COL_NM_HONNEN = "HONNEN_%d";
	/** 列名_前年 */
	static final String SELECT_COL_NM_ZENNEN = "ZENNEN_%d";
	/** 列名_対比 */
	static final String SELECT_COL_NM_TAIHI = "TAIHI_%d";

	/** 抽出条件_抽出・集計情報Map */
	static Map<String, CyusyutuSyukeiInfo[]> CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP = new HashMap<String, CyusyutuSyukeiInfo[]>();
	/** 集計内容_抽出・集計情報Map */
	static Map<String, CyusyutuSyukeiInfo[]> SYUKEI_NAIYO_CYUSYUTU_SYUKEI_MAP = new HashMap<String, CyusyutuSyukeiInfo[]>();
	/** 除外抽出条件_抽出・集計情報Map */
	static Map<String, CyusyutuSyukeiInfo> EXCLUDE_CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP = new HashMap<String, CyusyutuSyukeiInfo>();

	static {
		// 事業所
		CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_JIGYOSYO, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.JIGYOSYO});
		// 課
		CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_KA, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.KA});
		// 担当者
		CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_TANTO, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.TANTO});
		// 都道府県
		CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_COUNTRY, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.TODOFUKN});
		// 全国卸
		CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_ZENKOKU, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.ZENKOKU});
		// 特約
		CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_TOKUYAKU, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.TOKUYAKU});
		// 送荷先
		CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_SOUNISAKI, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.TOKUYAKU, CyusyutuSyukeiInfo.SOUNISAKI});
		// 酒販店
		CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_SYUHANTEN, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.SYUHANTEN});
		// 運送店
		CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_UNSOTEN, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.UNSOTEN});

		// 事業所
		SYUKEI_NAIYO_CYUSYUTU_SYUKEI_MAP.put(SYUKEI_NAIYO_KB_JIGYOSYO, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.JIGYOSYO});
		// 課
		SYUKEI_NAIYO_CYUSYUTU_SYUKEI_MAP.put(SYUKEI_NAIYO_KB_KA, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.KA});
		// 担当者
		SYUKEI_NAIYO_CYUSYUTU_SYUKEI_MAP.put(SYUKEI_NAIYO_KB_TANTO, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.TANTO});
		// 特約
		SYUKEI_NAIYO_CYUSYUTU_SYUKEI_MAP.put(SYUKEI_NAIYO_KB_TOKUYAKU, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.TOKUYAKU});
		// 送荷先
		SYUKEI_NAIYO_CYUSYUTU_SYUKEI_MAP.put(SYUKEI_NAIYO_KB_SOUNISAKI, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.TOKUYAKU, CyusyutuSyukeiInfo.SOUNISAKI});
		// 全国卸
		SYUKEI_NAIYO_CYUSYUTU_SYUKEI_MAP.put(SYUKEI_NAIYO_KB_ZENKOKU, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.ZENKOKU});
		// 都道府県
		SYUKEI_NAIYO_CYUSYUTU_SYUKEI_MAP.put(SYUKEI_NAIYO_KB_COUNTRY, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.TODOFUKN});
		// 輸出国
		SYUKEI_NAIYO_CYUSYUTU_SYUKEI_MAP.put(SYUKEI_NAIYO_KB_YUSYUTU, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.SYUKA_SAKI_COUNTRY});
		// 容器材質
		SYUKEI_NAIYO_CYUSYUTU_SYUKEI_MAP.put(SYUKEI_NAIYO_KB_YOUKIZAISITU, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.YOUKIZAISITU});
		// 酒種
		SYUKEI_NAIYO_CYUSYUTU_SYUKEI_MAP.put(SYUKEI_NAIYO_KB_SAKADANE, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.SAKADANE});
		// 酒質
		SYUKEI_NAIYO_CYUSYUTU_SYUKEI_MAP.put(SYUKEI_NAIYO_KB_SAKESITU, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.SAKESITU});
		// 商品
		SYUKEI_NAIYO_CYUSYUTU_SYUKEI_MAP.put(SYUKEI_NAIYO_KB_SYOHIN, new CyusyutuSyukeiInfo[]{CyusyutuSyukeiInfo.SHOHIN});

		// 事業所
		EXCLUDE_CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_JIGYOSYO, CyusyutuSyukeiInfo.JIGYOSYO);
		// 課
		EXCLUDE_CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_KA, CyusyutuSyukeiInfo.KA);
		// 担当者
		EXCLUDE_CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_TANTO, CyusyutuSyukeiInfo.TANTO);
		// 都道府県
		EXCLUDE_CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_COUNTRY, CyusyutuSyukeiInfo.TODOFUKN);
		// 全国卸
		EXCLUDE_CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_ZENKOKU, CyusyutuSyukeiInfo.ZENKOKU);
		// 特約
		EXCLUDE_CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_TOKUYAKU, CyusyutuSyukeiInfo.TOKUYAKU);
		// 酒販店
		EXCLUDE_CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.put(CYUSYUTU_JYOKEN_KB_SYUHANTEN, CyusyutuSyukeiInfo.SYUHANTEN);
	}

	/** 営業カレンダーUtil */
	private ComEigyoCalendarUtil eigyoCalendarUtil;

	/** 抽出・集計情報リスト */
	private List<CyusyutuSyukeiInfo> cyusyutuSyukeiInfoList;

	/** キー列リスト */
	private List<KeyCol> keyColList;

	/** 見出し用抽出・集計情報リスト */
	private List<CyusyutuSyukeiInfo> midasiCyusyutuSyukeiInfoList;

	/** 見出し用キー列リスト */
	private List<KeyCol> midasiKeyColList;

	/** 検索パターンInterface */
	private IJisekiESienList_SearchSelectPtn selectPtn;

	/** 計算Interface */
	private ICalc calc;

	/** 丸めInterface */
	private IRound round;

	/** 日付情報 */
	private DateInfo dateInfo;

	/** 受注検索フラグ */
	private boolean jucyuSearch = false;
	/** 出荷検索フラグ */
	private boolean syukaSearch = false;
	/** 戻入検索フラグ */
	private boolean reinyuSearch = false;
	/** 当月予算 */
	private Map<String, String> jigyosyoYosanMap = new HashMap<String, String>();
	/** 前年有無 */
	private boolean hasZennen;

	/**
	 * コンストラクタ
	 * @param searchForm 検索Form
	 * @param eigyoCalendarUtil 営業カレンダーUtil
	 * @param gymDate 業務日付
	 * @throws KitException
	 */
	public JisekiESienList_SearchHelper(JisekiESienList_SearchForm searchForm, ComEigyoCalendarUtil eigyoCalendarUtil, String gymDate) throws KitException {
		// 営業カレンダーUtil
		this.eigyoCalendarUtil = eigyoCalendarUtil;
		// 前年有無
		this.hasZennen = PbsUtil.isIncluded(searchForm.getTaihiUmu(), TAIHI_UMU_KB_JISSEKI, TAIHI_UMU_KB_KADOBI);
		// 検索パターン取得
		this.selectPtn = getSearchPtn(searchForm);
		// 計算方法
		this.calc = getCalc(searchForm.getTaisyoData());
		// 丸め方法
		this.round = getRound(searchForm.getHyojiTani());
		// 検索対象トランザクションテーブル判定
		setSearchTran(searchForm.getTaisyoData(), searchForm.getTaihiUmu());
		// 抽出・集計情報リスト取得
		this.cyusyutuSyukeiInfoList = CyusyutuSyukeiInfo.getCyusyutuSyukeiInfo(searchForm);
		// キー列リスト取得
		this.keyColList = createKeyColList(cyusyutuSyukeiInfoList);
		// 除外抽出・集計情報
		CyusyutuSyukeiInfo excludeSyukeiInfo = EXCLUDE_CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.get(searchForm.getCyusyutuJyoken());
		// 見出し用抽出・集計情報リスト
		this.midasiCyusyutuSyukeiInfoList = createMidasiCyusyutuSyukeiInfoList(this.cyusyutuSyukeiInfoList, excludeSyukeiInfo);
		// 見出し用キー列リスト取得
		this.midasiKeyColList = createKeyColList(midasiCyusyutuSyukeiInfoList);
		// 日付情報取得
		this.dateInfo = getDateInfo(searchForm, gymDate);
	}

	/**
	 * 日付フォーマット
	 * @param taisyoHyojiKikan 対象表示期間
	 * @param date 日付
	 * @return フォーマット日付
	 */
	public String getFormatDate(String taisyoHyojiKikan, String date) {
		if(PbsUtil.isEqual(taisyoHyojiKikan, TAISYO_HYOJI_KIKAN_KB_YEAR)) {
			// 対象表示期間 指定年
			// yyyy
			return PbsUtil.getYYYY(date);
		} else if(PbsUtil.isEqual(taisyoHyojiKikan, TAISYO_HYOJI_KIKAN_KB_MONTH)) {
			// 対象表示期間 指定月
			// yyyy/MM
			return PbsUtil.getYYYY(date) + "/" + PbsUtil.getMM(date);
		} else {
			// 対象表示期間 指定日
			// yyyy/MM/dd
			return PbsUtil.toDateFormat(date);
		}
	}

	/**
	 * 見出しコード・名称の列名リスト取得
	 * @return 見出しコード・名称の列名リスト
	 */
	public List<MidasiColInfo> getMidasiColNmList() {
		List<MidasiColInfo> midasiDataList = new ArrayList<MidasiColInfo>();
		for (CyusyutuSyukeiInfo cyusyutuSyukeiInfo : midasiCyusyutuSyukeiInfoList) {
			MidasiColInfo midasiColInfo = new MidasiColInfo();
			// １件目をコード値とする
			midasiColInfo.setCodeCol(cyusyutuSyukeiInfo.getKeyCols()[0].getCol());
			for (int j = 0; j < cyusyutuSyukeiInfo.getNmCols().length; j++) {
				midasiColInfo.getNameColList().add(new MidasiNameClass(cyusyutuSyukeiInfo.getNmCols()[j], cyusyutuSyukeiInfo.getClassNms()[j]));
			}
			midasiDataList.add(midasiColInfo);
		}
		return midasiDataList;
	}

	/**
	 * テーブルヘッダ項目取得
	 * @param searchForm 検索Form
	 * @return テーブルヘッダ項目
	 * @throws KitException
	 */
	public TableHeaderInfo getTableHeaderTagInfo(JisekiESienList_SearchForm searchForm) throws KitException {
		TableHeaderInfo tableTagInfo = new TableHeaderInfo();
		// ヘッダの行数
		tableTagInfo.setHeader1Line(selectPtn.isHeader1Line());
		// 見出し部
		tableTagInfo.setMidasiHeaderList(getMidasiHeaderList());
		// 累計行数
		tableTagInfo.setRuikeiHeader1Line(selectPtn.isRuikeiHeader1Line());
		// 累計部
		tableTagInfo.setRuikeiHeader(selectPtn.getRuikeiHeader(this, searchForm));
		// 日付部
		tableTagInfo.setDateHeaderList(selectPtn.getDateHeaderList(this, searchForm, dateInfo.getDateRange()));
		return tableTagInfo;
	}

	/**
	 * BigDecimal変換
	 * @param val 値
	 * @return BigDecimal
	 */
	public BigDecimal convertBigDecimal(String val) {
		if(PbsUtil.isEmpty(val)) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(val);
	}

	/**
	 * 対比計算
	 * @param val1 値
	 * @param val2 値
	 * @return 計算結果
	 */
	public BigDecimal calcTaihi(BigDecimal val1, BigDecimal val2) {
		try {
			return val1.divide(val2, 3, BigDecimal.ROUND_DOWN).multiply(new BigDecimal("100")).setScale(1, BigDecimal.ROUND_DOWN);
		} catch (ArithmeticException e) {
			return BigDecimal.ZERO.setScale(1);
		}
	}

	/**
	 * 丸めと対比計算
	 * @param val1 値
	 * @param val2 値
	 * @return 計算結果
	 */
	public BigDecimal calcRoundAndTaihi(BigDecimal val1, BigDecimal val2) {
		BigDecimal roundVal1 = this.round.round(val1);
		BigDecimal roundVal2 = this.round.round(val2);
		try {
			return roundVal1.divide(roundVal2, 3, BigDecimal.ROUND_DOWN).multiply(new BigDecimal("100")).setScale(1, BigDecimal.ROUND_DOWN);
		} catch (ArithmeticException e) {
			return BigDecimal.ZERO.setScale(1);
		}
	}

	/**
	 * 計算
	 * @param jucyuVal 受注の値
	 * @param syukaVal 出荷の値
	 * @param reinyuVal 戻入の値
	 * @return 計算結果
	 */
	public BigDecimal calc(String jucyu, String syuka , String reinyu) {
		return calc.calc(jucyu, syuka, reinyu);
	}

	/**
	 * 丸め
	 * @param val 値
	 * @return 丸目後の値
	 */
	public BigDecimal round(BigDecimal val) {
		return round.round(val);
	}

	/**
	 * ディテール検索有無取得
	 * @return 検索するかどうか
	 */
	public boolean isSearchDt() {
		return !isSearchCtg();
	}

	/**
	 * カテゴリ検索有無取得
	 * @return 検索するかどうか
	 */
	public boolean isSearchCtg() {
		// 容器材質別の場合は、カテゴリを検索
		return this.cyusyutuSyukeiInfoList.contains(CyusyutuSyukeiInfo.YOUKIZAISITU);
	}

	/**
	 * 検索パターン取得処理
	 * @param searchForm 検索Form
	 * @return 検索パターンクラス
	 */
	private IJisekiESienList_SearchSelectPtn getSearchPtn(JisekiESienList_SearchForm searchForm) {
		// 予算対比の場合
		if(PbsUtil.isEqual(searchForm.getYosanTaihi(), TAIHI_UMU_KB_YOSAN_TAIHI_ON)) {
			return new JisekiESienList_SearchSelectPtn_Yosan();
		}
		if(PbsUtil.isEqual(searchForm.getTaisyoHyojiKikan(), TAISYO_HYOJI_KIKAN_KB_TOUGETU)) {
			if(PbsUtil.isEqual(searchForm.getTaihiUmu(), TAIHI_UMU_KB_JISSEKI)) {
				// 当月・10～当月・1～当月
				return new JisekiESienList_SearchSelectPtn_Tougetu();
			} else if(PbsUtil.isIncluded(searchForm.getTaihiUmu(), TAIHI_UMU_KB_SYUKAREINYU, TAIHI_UMU_KB_SYUKAREINYU_FURIKAE)) {
				// 当月・10～当月・1～当月(出荷・戻入対比)
				return new JisekiESienList_SearchSelectPtn_TougetuSyukaReinyu();
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			if(PbsUtil.isEqual(searchForm.getTaihiUmu(), TAIHI_UMU_KB_NASI)) {
				// 対比有無 指定なし
				return new JisekiESienList_SearchSelectPtn_TaihiNasi();
			} else if(PbsUtil.isIncluded(searchForm.getTaihiUmu(), TAIHI_UMU_KB_SYUKAREINYU, TAIHI_UMU_KB_SYUKAREINYU_FURIKAE)) {
				// 対比有無 出荷・戻入対比
				return new JisekiESienList_SearchSelectPtn_SyukaReinyu();
			} else {
				// 対比有無 実績対比 or 稼働日対比
				return new JisekiESienList_SearchSelectPtn_JissekiKado();
			}
		}
	}

	/**
	 * 計算方法取得
	 * @param taisyoData 対象データ
	 * @return 計算方法
	 */
	private ICalc getCalc(String taisyoData) {
		ICalc calc;
		if(PbsUtil.isIncluded(taisyoData,JSK_SEL_DATA_KB_SYUKKA_NOMI, JSK_SEL_DATA_KB_SYUKKA_TEISEI)) {
			// 出荷のみ/出荷訂正のみ
			calc = new ICalc() {
				@Override
				public BigDecimal calc(String jucyu, String syuka , String reinyu) {
					return convertBigDecimal(syuka);
				}
			};
		} else if(PbsUtil.isEqual(taisyoData,JSK_SEL_DATA_KB_SYUKKA_YOTEI)) {
			// 出荷予定
			calc = new ICalc() {
				@Override
				public BigDecimal calc(String jucyu, String syuka , String reinyu) {
					return convertBigDecimal(jucyu).add(convertBigDecimal(syuka));
				}
			};
		} else if(PbsUtil.isEqual(taisyoData,JSK_SEL_DATA_KB_JYUCYU_NOMI)) {
			// 受注のみ
			calc = new ICalc() {
				@Override
				public BigDecimal calc(String jucyu, String syuka , String reinyu) {
					return convertBigDecimal(jucyu);
				}
			};
		} else if(PbsUtil.isIncluded(taisyoData,JSK_SEL_DATA_KB_REINYU_NOMI, JSK_SEL_DATA_KB_REINYU_EISEI)) {
			// 戻入のみ/戻入訂正のみ
			calc = new ICalc() {
				@Override
				public BigDecimal calc(String jucyu, String syuka , String reinyu) {
					return convertBigDecimal(reinyu);
				}
			};
		} else if(PbsUtil.isEqual(taisyoData,JSK_SEL_DATA_KB_JUN_SYUKKA)) {
			// 純出荷
			calc = new ICalc() {
				@Override
				public BigDecimal calc(String jucyu, String syuka , String reinyu) {
					return convertBigDecimal(syuka).subtract(convertBigDecimal(reinyu));
				}
			};
		} else {
			throw new IllegalArgumentException();
		}
		return calc;
	}

	/**
	 * 丸め方法取得
	 * @param hyojiTani 表示単位
	 * @return 丸め方法
	 */
	private IRound getRound(String hyojiTani) {
		IRound round;
		if(PbsUtil.isEqual(hyojiTani,JSK_HYOJI_TANI_KB_YEN)) {
			// 円
			round = new IRound() {
				@Override
				public BigDecimal round(BigDecimal value) {
					return value.setScale(0, BigDecimal.ROUND_DOWN);
				}
			};
		} else if(PbsUtil.isEqual(hyojiTani,JSK_HYOJI_TANI_KB_1000YEN)) {
			// 千円
			round = new IRound() {
				@Override
				public BigDecimal round(BigDecimal value) {
					try {
						return value.divide(new BigDecimal("1000"), 0, BigDecimal.ROUND_DOWN);
					} catch (ArithmeticException e) {
						return BigDecimal.ZERO.setScale(0);
					}
				}
			};
		} else if(PbsUtil.isEqual(hyojiTani,JSK_HYOJI_TANI_KB_KOKU)) {
			// 石数
			round = new IRound() {
				@Override
				public BigDecimal round(BigDecimal value) {
					try {
						return value.divide(new BigDecimal("180"), 3, BigDecimal.ROUND_DOWN);
					} catch (ArithmeticException e) {
						return BigDecimal.ZERO.setScale(3);
					}
				}
			};
		} else if(PbsUtil.isEqual(hyojiTani,JSK_HYOJI_TANI_KB_L)) {
			// L数
			round = new IRound() {
				@Override
				public BigDecimal round(BigDecimal value) {
					return value.setScale(3, BigDecimal.ROUND_DOWN);
				}
			};
		} else if(PbsUtil.isEqual(hyojiTani,JSK_HYOJI_TANI_KB_KL)) {
			// KL数
			round = new IRound() {
				@Override
				public BigDecimal round(BigDecimal value) {
					try {
						return value.divide(new BigDecimal("1000"), 3, BigDecimal.ROUND_DOWN);
					} catch (ArithmeticException e) {
						return BigDecimal.ZERO.setScale(3);
					}
				}
			};
		} else if(PbsUtil.isEqual(hyojiTani,JSK_HYOJI_TANI_KB_BARA)) {
			// 本数
			round = new IRound() {
				@Override
				public BigDecimal round(BigDecimal value) {
					return value.setScale(0, BigDecimal.ROUND_DOWN);
				}
			};
		} else if(PbsUtil.isEqual(hyojiTani,JSK_HYOJI_TANI_KB_CASE)) {
			// 箱数
			round = new IRound() {
				@Override
				public BigDecimal round(BigDecimal value) {
					return value.setScale(0, BigDecimal.ROUND_DOWN);
				}
			};
		} else if(PbsUtil.isEqual(hyojiTani,JSK_HYOJI_TANI_KB_KG)) {
			// kg数
			round = new IRound() {
				@Override
				public BigDecimal round(BigDecimal value) {
					return value.setScale(3, BigDecimal.ROUND_DOWN);
				}
			};
		} else if(PbsUtil.isEqual(hyojiTani,JSK_HYOJI_TANI_KB_T)) {
			// t数
			round = new IRound() {
				@Override
				public BigDecimal round(BigDecimal value) {
					try {
						return value.divide(new BigDecimal(("1000")), 3, BigDecimal.ROUND_DOWN);
					} catch (ArithmeticException e) {
						return BigDecimal.ZERO.setScale(3);
					}
				}
			};
		} else {
			throw new IllegalArgumentException();
		}
		return round;
	}

	/**
	 * 検索対象トランザクションテーブル判定処理
	 * @param taisyoData 対象データ
	 * @param taihiUmu 対比有無
	 */
	private void setSearchTran(String taisyoData, String taihiUmu) {
		if(PbsUtil.isIncluded(taisyoData,JSK_SEL_DATA_KB_SYUKKA_NOMI,JSK_SEL_DATA_KB_SYUKKA_TEISEI)) {
			// 対象データ 出荷のみ/出荷訂正のみ
			this.syukaSearch = true;
		} else if(PbsUtil.isEqual(taisyoData,JSK_SEL_DATA_KB_SYUKKA_YOTEI)) {
			// 対象データ 出荷予定
			this.jucyuSearch = true;
			this.syukaSearch = true;
		} else if(PbsUtil.isEqual(taisyoData,JSK_SEL_DATA_KB_JYUCYU_NOMI)) {
			// 対象データ 受注のみ
			this.jucyuSearch = true;
		} else if(PbsUtil.isIncluded(taisyoData,JSK_SEL_DATA_KB_REINYU_NOMI,JSK_SEL_DATA_KB_REINYU_EISEI)) {
			// 対象データ 戻入のみ/戻入訂正のみ
			this.reinyuSearch = true;
		} else if(PbsUtil.isEqual(taisyoData,JSK_SEL_DATA_KB_JUN_SYUKKA)) {
			// 純出荷
			this.syukaSearch = true;
			this.reinyuSearch = true;
		}
		if(PbsUtil.isIncluded(taihiUmu,TAIHI_UMU_KB_SYUKAREINYU, TAIHI_UMU_KB_SYUKAREINYU_FURIKAE)) {
			// 対比有無 出荷・戻入対比
			this.reinyuSearch = true;
		}
	}

	/**
	 * キー列リスト取得
	 * @param cyusyutuSyukeiInfoList 抽出・集計情報リスト取得
	 * @return キー列リスト取得
	 */
	private List<KeyCol> createKeyColList(List<CyusyutuSyukeiInfo> cyusyutuSyukeiInfoList) {
		Set<KeyCol> keyColList = new LinkedHashSet<KeyCol>();
		for (CyusyutuSyukeiInfo cyusyutuSyukeiInfo : cyusyutuSyukeiInfoList) {
			keyColList.addAll(Arrays.asList(cyusyutuSyukeiInfo.getKeyCols()));
		}
		return new ArrayList<KeyCol>(keyColList);
	}

	/**
	 * 見出し用抽出・集計情報リスト作成
	 * @param cyusyutuSyukeiInfoList 抽出・集計情報リスト
	 * @param excludeSyukeiInfo 除外抽出・集計情報
	 * @return 見出し用抽出・集計情報リスト
	 */
	private List<CyusyutuSyukeiInfo> createMidasiCyusyutuSyukeiInfoList(List<CyusyutuSyukeiInfo> cyusyutuSyukeiInfoList, CyusyutuSyukeiInfo excludeSyukeiInfo) {
		if(excludeSyukeiInfo == null) return cyusyutuSyukeiInfoList;
		List<CyusyutuSyukeiInfo> midasiCyusyutuSyukeiInfoList = new ArrayList<CyusyutuSyukeiInfo>();
		for (CyusyutuSyukeiInfo cyusyutuSyukeiInfo : cyusyutuSyukeiInfoList) {
			if(cyusyutuSyukeiInfo != excludeSyukeiInfo) {
				midasiCyusyutuSyukeiInfoList.add(cyusyutuSyukeiInfo);
			}
		}
		return midasiCyusyutuSyukeiInfoList;
	}

	/**
	 * 日付情報取得
	 * @param searchForm 検索Form
	 * @param gymDate 業務日付
	 * @return 日付情報
	 * @throws KitException
	 */
	private DateInfo getDateInfo(JisekiESienList_SearchForm searchForm, String gymDate) throws KitException {

		// 予算対比の場合
		if(PbsUtil.isEqual(searchForm.getYosanTaihi(), TAIHI_UMU_KB_YOSAN_TAIHI_ON)) {
			return getDateInfoForYosan(searchForm.getTaihiUmu(), gymDate);
		}

		// 対象表示期間
		String taisyoHyojiKikan = searchForm.getTaisyoHyojiKikan();
		if(PbsUtil.isEqual(taisyoHyojiKikan, TAISYO_HYOJI_KIKAN_KB_TOUGETU)) {
			// 対象表示期間_当月・10～当月・1～当月
			return getDateInfoForTogetu(searchForm.getTaisyoDate(), searchForm.getTaihiUmu());
		} else if(PbsUtil.isEqual(taisyoHyojiKikan, TAISYO_HYOJI_KIKAN_KB_YEAR)) {
			// 対象表示期間_指定年
			return getDateInfoForYear(searchForm.getTaisyoYearFrom(), searchForm.getTaisyoYearTo(), searchForm.getTaihiUmu());
		} else if(PbsUtil.isEqual(taisyoHyojiKikan, TAISYO_HYOJI_KIKAN_KB_MONTH)) {
			// 対象表示期間_指定月
			return getDateInfoForMonth(searchForm.getTaisyoMonthFrom(), searchForm.getTaisyoMonthTo(), searchForm.getTaihiUmu());
		} else {
			// 対象表示期間_指定日
			return getDateInfoForDate(searchForm.getTaisyoDateFrom(), searchForm.getTaisyoDateTo(), searchForm.getTaihiUmu());
		}
	}

	/**
	 * 当月・10～当月・1～当月用の日付情報取得
	 * @param taisyoDate 対象日
	 * @param taihiUmu 対比有無
	 * @return 日付情報
	 * @throws KitException
	 */
	private DateInfo getDateInfoForTogetu(String taisyoDate, String taihiUmu) throws KitException {
		if(PbsUtil.isEqual(taihiUmu, TAIHI_UMU_KB_JISSEKI)) {
			return getDateInfoForTogetuJisseki(taisyoDate);
		} else {
			return getDateInfoForTogetuSyukaReinyu(taisyoDate);
		}
	}

	/**
	 * 当月・10～当月・1～当月(実績対比)用の日付情報取得
	 * @param taisyoDate 対象日
	 * @return 日付情報
	 * @throws KitException
	 */
	private DateInfo getDateInfoForTogetuJisseki(String taisyoDate) throws KitException {
		DateInfo dateInfo = new DateInfo();
		// 前年
		String zennen = PbsUtil.getYMD(getZennenCalendar(convertCal(taisyoDate)));
		// 当月累計
		dateInfo.getDateRange().add(new HonnenZennenDateRange(PbsUtil.getYMD(getFirstDayOfMonthCal(taisyoDate)), taisyoDate, PbsUtil.getYMD(PbsUtil.getYMD(getFirstDayOfMonthCal(zennen))), eigyoCalendarUtil.getCalEndMonthDt(zennen)));
		// 10～当月累計
		dateInfo.getDateRange().add(new HonnenZennenDateRange(eigyoCalendarUtil.getBeginningOfFiscalYear(taisyoDate), taisyoDate, eigyoCalendarUtil.getBeginningOfFiscalAYearAgo(taisyoDate), eigyoCalendarUtil.getCalEndMonthDt(zennen)));
		// 1～当月累計
		dateInfo.getDateRange().add(new HonnenZennenDateRange(PbsUtil.getYMD(getFirstDayOfYearCal(taisyoDate)), taisyoDate, PbsUtil.getYMD(getFirstDayOfYearCal(zennen)), eigyoCalendarUtil.getCalEndMonthDt(zennen)));
		// 検索条件用の日付範囲設定
		for (HonnenZennenDateRange dateRange : dateInfo.getDateRange()) {
			dateInfo.getWhereDateRange().add(new DateRange(dateRange.getFrom(), dateRange.getTo()));
			dateInfo.getWhereDateRange().add(new DateRange(dateRange.getZennenFrom(), dateRange.getZennenTo()));
		}
		return dateInfo;
	}

	/**
	 * 当月・10～当月・1～当月(出荷・戻入対比)用の日付情報取得
	 * @param taisyoDate 対象日
	 * @return 日付情報
	 * @throws KitException
	 */
	private DateInfo getDateInfoForTogetuSyukaReinyu(String taisyoDate) throws KitException {
		DateInfo dateInfo = new DateInfo();
		// 当月累計
		dateInfo.getDateRange().add(new HonnenZennenDateRange(PbsUtil.getYMD(getFirstDayOfMonthCal(taisyoDate)), taisyoDate));
		// 10～当月累計
		dateInfo.getDateRange().add(new HonnenZennenDateRange(eigyoCalendarUtil.getBeginningOfFiscalYear(taisyoDate), taisyoDate));
		// 1～当月累計
		dateInfo.getDateRange().add(new HonnenZennenDateRange(PbsUtil.getYMD(getFirstDayOfYearCal(taisyoDate)), taisyoDate));
		// 検索条件用の日付範囲設定
		for (HonnenZennenDateRange dateRange : dateInfo.getDateRange()) {
			dateInfo.getWhereDateRange().add(new DateRange(dateRange.getFrom(), dateRange.getTo()));
		}
		return dateInfo;
	}

	/**
	 * 指定年用の日付情報取得
	 * @param taisyoYearFrom 指定年From
	 * @param taisyoYearTo 指定年To
	 * @param taihiUmu 対比有無
	 * @return 日付情報
	 */
	private DateInfo getDateInfoForYear(String taisyoYearFrom, String taisyoYearTo, String taihiUmu) {
		DateInfo dateInfo = new DateInfo();
		// 前年有無
		Calendar from = getFirstDayOfYearCal(taisyoYearFrom);
		Calendar to =getFirstDayOfYearCal(taisyoYearTo);

		while(from.compareTo(to) <= 0) {

			String honnen = PbsUtil.getYMD(from);

			if(!this.hasZennen) {
				// 本年
				dateInfo.getDateRange().add(new HonnenZennenDateRange(PbsUtil.getYMD(getFirstDayOfYearCal(honnen)), PbsUtil.getYMD(getLastDatOfYearCal(honnen)), honnen));
			} else {
				String zennen = PbsUtil.getYMD(getZennenCalendar(from));
				// 本年+前年
				dateInfo.getDateRange().add(new HonnenZennenDateRange(PbsUtil.getYMD(getFirstDayOfYearCal(honnen)), PbsUtil.getYMD(getLastDatOfYearCal(honnen)), PbsUtil.getYMD(getFirstDayOfYearCal(zennen)), PbsUtil.getYMD(getLastDatOfYearCal(zennen)), honnen));
			}
			from = addCalendar(from, Calendar.YEAR, 1);
		}

		// 1件目の日付範囲
		HonnenZennenDateRange dateRange = dateInfo.getDateRange().get(0);
		// 最後の日付範囲
		HonnenZennenDateRange lastDateRange = dateInfo.getDateRange().get(dateInfo.getDateRange().size() - 1);
		// 検索条件用の日付範囲設定
		if(!this.hasZennen) {
			dateInfo.getWhereDateRange().add(new DateRange(dateRange.getFrom(), lastDateRange.getTo()));
		} else {
			dateInfo.getWhereDateRange().add(new DateRange(dateRange.getZennenFrom(), lastDateRange.getTo()));
		}

		// 累計
		if(this.hasZennen) {
			dateInfo.setRuikeiDateRange(new HonnenZennenDateRange(dateRange.getFrom(), lastDateRange.getTo(), dateRange.getZennenFrom(), lastDateRange.getZennenTo()));
		} else {
			dateInfo.setRuikeiDateRange(new HonnenZennenDateRange(dateRange.getFrom(), lastDateRange.getTo()));
		}


		return dateInfo;
	}

	/**
	 * 指定月用の日付情報取得
	 * @param taisyoMonthFrom 指定月From
	 * @param taisyoMonthTo 指定月To
	 * @param taihiUmu 対比有無
	 * @return 日付情報
	 * @throws KitException
	 */
	private DateInfo getDateInfoForMonth(String taisyoMonthFrom, String taisyoMonthTo, String taihiUmu) throws KitException {
		DateInfo dateInfo = new DateInfo();
		Calendar from = getFirstDayOfMonthCal(taisyoMonthFrom);
		Calendar to = getFirstDayOfMonthCal(taisyoMonthTo);

		while(from.compareTo(to) <= 0) {

			String honnen = PbsUtil.getYMD(from);
			if(!this.hasZennen) {
				// 本年
				dateInfo.getDateRange().add(new HonnenZennenDateRange(honnen, eigyoCalendarUtil.getCalEndMonthDt(honnen), honnen));
			} else {
				String zennen = PbsUtil.getYMD(getZennenCalendar(from));
				// 本年+前年
				dateInfo.getDateRange().add(new HonnenZennenDateRange(honnen, eigyoCalendarUtil.getCalEndMonthDt(honnen), zennen, eigyoCalendarUtil.getCalEndMonthDt(zennen), honnen));
			}
			from = addCalendar(from, Calendar.MONTH, 1);
		}

		// 1件目の日付範囲
		HonnenZennenDateRange firstDateRange = dateInfo.getDateRange().get(0);
		// 最後の日付範囲
		HonnenZennenDateRange lastDateRange = dateInfo.getDateRange().get(dateInfo.getDateRange().size() - 1);
		// 検索条件用の日付範囲設定
		dateInfo.getWhereDateRange().add(new DateRange(firstDateRange.getFrom(), lastDateRange.getTo()));
		if(this.hasZennen) {
			dateInfo.getWhereDateRange().add(new DateRange(firstDateRange.getZennenFrom(), lastDateRange.getZennenTo()));
		}

		// 累計
		if(this.hasZennen) {
			dateInfo.setRuikeiDateRange(new HonnenZennenDateRange(firstDateRange.getFrom(), lastDateRange.getTo(), firstDateRange.getZennenFrom(), lastDateRange.getZennenTo()));
		} else {
			dateInfo.setRuikeiDateRange(new HonnenZennenDateRange(firstDateRange.getFrom(), lastDateRange.getTo()));
		}

		return dateInfo;
	}

	/**
	 * 指定日用の日付情報取得
	 * @param taisyoDateFrom 指定日From
	 * @param taisyoDateTo 指定日To
	 * @param taihiUmu 対比有無
	 * @return 日付情報
	 * @throws KitException
	 */
	private DateInfo getDateInfoForDate(String taisyoDateFrom, String taisyoDateTo, String taihiUmu) throws KitException {
		// 本年・前年営業カレンダー情報
		HonnenZennenEigyoCalendarInfo eigyoCalendarInfo = new HonnenZennenEigyoCalendarInfo(taisyoDateFrom, eigyoCalendarUtil);

		if(PbsUtil.isEqual(taihiUmu, TAIHI_UMU_KB_JISSEKI)) {
			// 実績対比
			return getDateInfoForJissekiDate(taisyoDateFrom, taisyoDateTo, eigyoCalendarInfo);
		} else if (PbsUtil.isEqual(taihiUmu, TAIHI_UMU_KB_KADOBI)) {
			// 稼働日対比
			return getDateInfoForKadobiDate(taisyoDateFrom, taisyoDateTo, eigyoCalendarInfo);
		} else {
			// 対比なし・出荷戻入対比
			return getDateInfoForHonnenZennenTaihiNasiDate(taisyoDateFrom, taisyoDateTo, eigyoCalendarInfo);
		}
	}

	/**
	 * 指定日用の実績対比日付情報取得
	 * @param taisyoDateFrom 指定日From
	 * @param taisyoDateTo 指定日To
	 * @param eigyoCalendarInfo 本年・前年営業カレンダー情報
	 * @return 日付情報
	 */
	private DateInfo getDateInfoForJissekiDate(String taisyoDateFrom, String taisyoDateTo, HonnenZennenEigyoCalendarInfo eigyoCalendarInfo) {
		DateInfo dateInfo = new DateInfo();

		// 累計集計期間
		dateInfo.setRuikeiDateRange(new HonnenZennenDateRange(eigyoCalendarInfo.honnenFirstDt, eigyoCalendarInfo.honnenLastDt, eigyoCalendarInfo.zennenFirstDt, eigyoCalendarInfo.zennenLastDt));

		Calendar from = convertCal(taisyoDateFrom);
		Calendar to = convertCal(taisyoDateTo);

		while(from.compareTo(to) <= 0) {
			// 本年
			String honnen = PbsUtil.getYMD(from);
			// 前年
			String zennen = PbsUtil.getYMD(getZennenCalendar(from));

			// 前年取得
			zennen = getZennenDateForJisseki(eigyoCalendarInfo, from, zennen);

//			// 本年の営業日（休業日の場合は前の営業日）を取得
//			honnen = eigyoCalendarInfo.getEigyoDt(honnen, true);
//
//			if(eigyoCalendarInfo.isLastEigyoDt(honnen, true)) {
//				// 対象の日が営業最終日の場合、前年も営業最終日にする
//				zennen = eigyoCalendarInfo.zennenLastEigyoDt;
//			} else {
//				// 前年の営業日（休業日の場合は前の営業日）を取得
//				zennen = eigyoCalendarInfo.getEigyoDt(zennen, false);
//			}

			// 集計期間
			dateInfo.getDateRange().add(new HonnenZennenDateRange(eigyoCalendarInfo.honnenFirstDt, honnen, eigyoCalendarInfo.zennenFirstDt, zennen, PbsUtil.getYMD(from)));

			from = addCalendar(from, Calendar.DATE, 1);
		}

		// 検索条件期間
		dateInfo.getWhereDateRange().add(new DateRange(eigyoCalendarInfo.honnenFirstDt, eigyoCalendarInfo.honnenLastDt));
		dateInfo.getWhereDateRange().add(new DateRange(eigyoCalendarInfo.zennenFirstDt, eigyoCalendarInfo.zennenLastDt));
		return dateInfo;
	}

	/**
	 * 指定日用の稼働日対比日付情報取得
	 * @param taisyoDateFrom 指定日From
	 * @param taisyoDateTo 指定日To
	 * @param eigyoCalendarInfo 本年・前年営業カレンダー情報
	 * @return 日付情報
	 */
	private DateInfo getDateInfoForKadobiDate(String taisyoDateFrom, String taisyoDateTo, HonnenZennenEigyoCalendarInfo eigyoCalendarInfo) {
		DateInfo dateInfo = new DateInfo();

		// 累計集計期間
		dateInfo.setRuikeiDateRange(new HonnenZennenDateRange(eigyoCalendarInfo.honnenFirstDt, eigyoCalendarInfo.honnenLastDt, eigyoCalendarInfo.zennenFirstDt, eigyoCalendarInfo.zennenLastDt));

		Calendar from = convertCal(taisyoDateFrom);
		Calendar to = convertCal(taisyoDateTo);

		while(from.compareTo(to) <= 0) {
			// 本年
			String honnen = PbsUtil.getYMD(from);
			// 前年
			String zennen = null;

			// 本年の営業日（休業日の場合は前の営業日）を取得
			honnen = eigyoCalendarInfo.getEigyoDt(honnen, true);

			if(honnen != null) {
				// 本年営業日から前年営業日を取得
				zennen = eigyoCalendarInfo.getZennenEigyoDtByHonnenEigyoDt(honnen);
			}
			// 集計期間
			dateInfo.getDateRange().add(new HonnenZennenDateRange(eigyoCalendarInfo.honnenFirstDt, honnen, eigyoCalendarInfo.zennenFirstDt, zennen, PbsUtil.getYMD(from)));

			from = addCalendar(from, Calendar.DATE, 1);
		}

		// 検索条件期間
		dateInfo.getWhereDateRange().add(new DateRange(eigyoCalendarInfo.honnenFirstDt, eigyoCalendarInfo.honnenLastDt));
		dateInfo.getWhereDateRange().add(new DateRange(eigyoCalendarInfo.zennenFirstDt, eigyoCalendarInfo.zennenLastDt));
		return dateInfo;
	}

	/**
	 * 指定日用の本年前年の対比なし日付情報取得
	 * @param taisyoDateFrom 指定日From
	 * @param taisyoDateTo 指定日To
	 * @param eigyoCalendarInfo 本年・前年営業カレンダー情報
	 * @return 日付情報
	 */
	private DateInfo getDateInfoForHonnenZennenTaihiNasiDate(String taisyoDateFrom, String taisyoDateTo, HonnenZennenEigyoCalendarInfo eigyoCalendarInfo) {
		DateInfo dateInfo = new DateInfo();

		// 累計
		dateInfo.setRuikeiDateRange(new HonnenZennenDateRange(eigyoCalendarInfo.honnenFirstDt, eigyoCalendarInfo.honnenLastDt));

		Calendar from = convertCal(taisyoDateFrom);
		Calendar to = convertCal(taisyoDateTo);

		while(from.compareTo(to) <= 0) {
			// 本年
			String honnen = PbsUtil.getYMD(from);

			// 本年の営業日（休業日の場合は前の営業日）を取得
			//honnen = eigyoCalendarInfo.getEigyoDt(honnen, true);

			// 集計期間
			dateInfo.getDateRange().add(new HonnenZennenDateRange(eigyoCalendarInfo.honnenFirstDt, honnen, PbsUtil.getYMD(from)));

			from = addCalendar(from, Calendar.DATE, 1);
		}

		// 検索条件期間
		dateInfo.getWhereDateRange().add(new DateRange(eigyoCalendarInfo.honnenFirstDt, eigyoCalendarInfo.honnenLastDt));
		return dateInfo;
	}

	/**
	 * 予算対比用の日付情報取得
	 * @param taihiUmu 対比有無
	 * @param gymDate 業務日付
	 * @return 日付情報
	 * @throws KitException
	 */
	private DateInfo getDateInfoForYosan(String taihiUmu, String gymDate) throws KitException {
		// 本年・前年営業カレンダー情報
		HonnenZennenEigyoCalendarInfo eigyoCalendarInfo = new HonnenZennenEigyoCalendarInfo(gymDate, eigyoCalendarUtil);
		if(PbsUtil.isEqual(taihiUmu, TAIHI_UMU_KB_JISSEKI)) {
			// 実績対比
			return getDateInfoForYosanJisseki(gymDate, eigyoCalendarInfo);
		} else {
			// 稼働日対比
			return getDateInfoForYosanKadobi(gymDate, eigyoCalendarInfo);
		}
	}

	/**
	 * 予算対比用の実績対比日付情報取得
	 * @param gymDate 業務日付
	 * @param eigyoCalendarInfo 本年・前年営業カレンダー情報
	 * @return 日付情報
	 * @throws KitException
	 */
	private DateInfo getDateInfoForYosanJisseki(String gymDate, HonnenZennenEigyoCalendarInfo eigyoCalendarInfo) {
		DateInfo dateInfo = new DateInfo();

		// 本年カレンダー
		Calendar honnenCal = convertCal(gymDate);
		// 本年
		String honnen = PbsUtil.getYMD(gymDate);
		// 前年
		String zennen = PbsUtil.getYMD(getZennenCalendar(honnenCal));
		// 本年翌営業日
		String honnenNextDay = eigyoCalendarInfo.getNextEigyoDt(honnen, true);

		// 累計本年・前年
		dateInfo.setRuikeiDateRange(new HonnenZennenDateRange(eigyoCalendarInfo.honnenFirstDt, eigyoCalendarInfo.honnenLastDt, eigyoCalendarInfo.zennenFirstDt, eigyoCalendarInfo.zennenLastDt));

		// 前年取得
		zennen = getZennenDateForJisseki(eigyoCalendarInfo, honnenCal, zennen);

//		// 本年の営業日（休業日の場合は前の営業日）を取得
//		honnen = eigyoCalendarInfo.getEigyoDt(honnen, true);
//
//		if(eigyoCalendarInfo.isLastEigyoDt(honnen, true)) {
//			// 対象の日が営業最終日の場合、前年も営業最終日にする
//			zennen = eigyoCalendarInfo.zennenLastEigyoDt;
//		} else {
//			// 前年の営業日（休業日の場合は前の営業日）を取得
//			zennen = eigyoCalendarInfo.getEigyoDt(zennen, false);
//		}

		// 集計期間_業務日
		dateInfo.getDateRange().add(new HonnenZennenDateRange(eigyoCalendarInfo.honnenFirstDt, honnen, eigyoCalendarInfo.zennenFirstDt, zennen, PbsUtil.getYMD(gymDate)));

		// 翌営業日がある場合
		if(honnenNextDay != null) {

			// 本年翌営業日カレンダー
			Calendar honnenNextCal = convertCal(honnenNextDay);
			// 前年翌営業日
			String zennenNextDay = PbsUtil.getYMD(getZennenCalendar(honnenNextCal));

			// 前年取得
			zennenNextDay = getZennenDateForJisseki(eigyoCalendarInfo, honnenNextCal, zennenNextDay);

//			if(eigyoCalendarInfo.isLastEigyoDt(honnenNextDay, true)) {
//				// 対象の日が営業最終日の場合、前年も営業最終日にする
//				zennenNextDay = eigyoCalendarInfo.zennenLastEigyoDt;
//			} else {
//				// 前年の営業日（休業日の場合は前の営業日）を取得
//				zennenNextDay = eigyoCalendarInfo.getEigyoDt(zennenNextDay, false);
//			}

			// 集計期間_業務日の翌営業日
			dateInfo.getDateRange().add(new HonnenZennenDateRange(eigyoCalendarInfo.honnenFirstDt, honnenNextDay, eigyoCalendarInfo.zennenFirstDt, zennenNextDay, honnenNextDay));
		}

		// 検索条件用の日付範囲設定
		dateInfo.getWhereDateRange().add(new DateRange(eigyoCalendarInfo.honnenFirstDt, eigyoCalendarInfo.honnenLastDt));
		dateInfo.getWhereDateRange().add(new DateRange(eigyoCalendarInfo.zennenFirstDt, eigyoCalendarInfo.zennenLastDt));
		return dateInfo;
	}

	/**
	 * 予算対比用の実績対比日付情報取得
	 * @param gymDate 業務日付
	 * @param eigyoCalendarInfo 本年・前年営業カレンダー情報
	 * @return 日付情報
	 */
	private DateInfo getDateInfoForYosanKadobi(String gymDate, HonnenZennenEigyoCalendarInfo eigyoCalendarInfo) {
		DateInfo dateInfo = new DateInfo();

		// 本年
		String honnen = PbsUtil.getYMD(gymDate);
		// 前年
		String zennen = null;
		// 本年翌営業日
		String honnenNextDay = eigyoCalendarInfo.getNextEigyoDt(honnen, true);

		// 累計本年・前年
		dateInfo.setRuikeiDateRange(new HonnenZennenDateRange(eigyoCalendarInfo.honnenFirstDt, eigyoCalendarInfo.honnenLastDt, eigyoCalendarInfo.zennenFirstDt, eigyoCalendarInfo.zennenLastDt));

		// 本年の営業日（休業日の場合は前の営業日）を取得
		honnen = eigyoCalendarInfo.getEigyoDt(honnen, true);

		if(honnen != null) {
			// 本年営業日から前年営業日を取得
			zennen = eigyoCalendarInfo.getZennenEigyoDtByHonnenEigyoDt(honnen);
		}
		// 集計期間_業務日
		dateInfo.getDateRange().add(new HonnenZennenDateRange(eigyoCalendarInfo.honnenFirstDt, honnen, eigyoCalendarInfo.zennenFirstDt, zennen, PbsUtil.getYMD(gymDate)));

		// 翌営業日がある場合
		if(honnenNextDay != null) {
			// 本年翌営業日から前年翌営業日を取得
			String zennenNextDay = eigyoCalendarInfo.getZennenEigyoDtByHonnenEigyoDt(honnenNextDay);
			dateInfo.getDateRange().add(new HonnenZennenDateRange(eigyoCalendarInfo.honnenFirstDt, honnenNextDay, eigyoCalendarInfo.zennenFirstDt, zennenNextDay, honnenNextDay));
		}

		// 検索条件用の日付範囲設定
		dateInfo.getWhereDateRange().add(new DateRange(eigyoCalendarInfo.honnenFirstDt, eigyoCalendarInfo.honnenLastDt));
		dateInfo.getWhereDateRange().add(new DateRange(eigyoCalendarInfo.zennenFirstDt, eigyoCalendarInfo.zennenLastDt));
		return dateInfo;
	}

	/**
	 * 実績対比用の前年日付取得処理
	 * @param eigyoCalendarInfo 本年・前年営業カレンダー情報
	 * @param targetCal 対象日カレンダー
	 * @param zennen 前年日付
	 * @return 前年日付
	 */
	private String getZennenDateForJisseki(HonnenZennenEigyoCalendarInfo eigyoCalendarInfo, Calendar targetCal, String zennen) {

		int honnenLastEigyoDay = 0;
		int zennenLastEigyoDay = 0;
		if(eigyoCalendarInfo.honnenLastEigyoCal != null) {
			honnenLastEigyoDay = eigyoCalendarInfo.honnenLastEigyoCal.get(Calendar.DAY_OF_MONTH);
		}
		if(eigyoCalendarInfo.zennenLastEigyoCal != null) {
			zennenLastEigyoDay = eigyoCalendarInfo.zennenLastEigyoCal.get(Calendar.DAY_OF_MONTH);
		}

		// 最終の営業日を比較
		if(honnenLastEigyoDay > zennenLastEigyoDay) {
			// 本年 > 前年の場合
			// 対象日が前年最終営業日を超えたら前年は最終営業日にする、
			if(targetCal.get(Calendar.DAY_OF_MONTH) > zennenLastEigyoDay) {
				//return eigyoCalendarInfo.zennenLastEigyoDt != null ? eigyoCalendarInfo.zennenLastEigyoDt : eigyoCalendarInfo.zennenFirstDt;
				return eigyoCalendarInfo.zennenLastEigyoDt;
			}
		} else if(honnenLastEigyoDay < zennenLastEigyoDay) {
			// 本年 < 前年の場合
			// 対象の日が本年最終営業日以上なら前年は営業最終日にする
			if(targetCal.get(Calendar.DAY_OF_MONTH) >= honnenLastEigyoDay) {
				//return eigyoCalendarInfo.zennenLastEigyoDt != null ? eigyoCalendarInfo.zennenLastEigyoDt : eigyoCalendarInfo.zennenFirstDt;
				return eigyoCalendarInfo.zennenLastEigyoDt;
			}
		}
		return zennen;
	}

	/**
	 * カレンダー変換
	 * @param ymd 日付
	 * @return カレンダー
	 */
	private Calendar convertCal(String ymd) {
		Calendar cal = Calendar.getInstance();
		cal.set(PbsUtil.toint(PbsUtil.getYYYY(ymd)), PbsUtil.toint(PbsUtil.getMM(ymd)) - 1, PbsUtil.toint(PbsUtil.getDD(ymd)));
		return cal;
	}

	/**
	 * 月の最初の日のカレンダー取得
	 * @param ymd 日付
	 * @return カレンダー
	 */
	private Calendar getFirstDayOfMonthCal(String ymd) {
		Calendar cal = Calendar.getInstance();
		cal.set(PbsUtil.toint(PbsUtil.getYYYY(ymd)), PbsUtil.toint(PbsUtil.getMM(ymd)) - 1, 1);
		return cal;
	}
	/**
	 * 年の最初の日のカレンダー取得
	 * @param ymd 日付
	 * @return カレンダー
	 */
	private Calendar getFirstDayOfYearCal(String ymd) {
		Calendar cal = Calendar.getInstance();
		cal.set(PbsUtil.toint(PbsUtil.getYYYY(ymd)), Calendar.JANUARY, 1);
		return cal;
	}
	/**
	 * 年の最後の日のカレンダー取得
	 * @param ymd 日付
	 * @return カレンダー
	 */
	private Calendar getLastDatOfYearCal(String ymd) {
		Calendar cal = Calendar.getInstance();
		cal.set(PbsUtil.toint(PbsUtil.getYYYY(ymd)), Calendar.DECEMBER, 31);
		return cal;
	}

	/**
	 * 前年日取得
	 * @param cal カレンダー
	 * @return 前年日
	 */
	private Calendar getZennenCalendar(Calendar cal) {
		return addCalendar(cal, Calendar.YEAR, -1);
	}

	/**
	 * カレンダー日付の指定フィールド加算
	 * @param cal カレンダー
	 * @param field カレンダフィールド
	 * @param amount 加算値
	 * @return カレンダー
	 */
	private Calendar addCalendar(Calendar cal, int field, int amount) {
		Calendar addCal = Calendar.getInstance();
		addCal.setTime(cal.getTime());
		addCal.add(field, amount);
		return addCal;
	}

	/**
	 * 年月取得
	 * @param date 日付
	 * @return 年月
	 */
	private String getYm(String date) {
		return PbsUtil.getYYYY(date) + PbsUtil.getMM(date);
	}

	/**
	 * テーブルヘッダ見出し項目取得
	 * @return ヘッダ見出し項目
	 */
	private List<MidasiColInfo> getMidasiHeaderList() {
		List<MidasiColInfo> midasiHeaderList = new ArrayList<MidasiColInfo>();
		for (CyusyutuSyukeiInfo cyusyutuSyukeiInfo : midasiCyusyutuSyukeiInfoList) {
			MidasiColInfo midasiColInfo = new MidasiColInfo();
			midasiColInfo.setCodeCol(cyusyutuSyukeiInfo.getHeaderCdNm());

			for (int i = 0; i < cyusyutuSyukeiInfo.getHeaderNms().length; i++) {
				String headerNm = cyusyutuSyukeiInfo.getHeaderNms()[i];
				MidasiClass classNm = cyusyutuSyukeiInfo.getClassNms()[i];
				midasiColInfo.getNameColList().add(new MidasiNameClass(headerNm, classNm));
			}
			midasiHeaderList.add(midasiColInfo);
		}
		return midasiHeaderList;
	}

	/**
	 * @return dateInfo
	 */
	public DateInfo getDateInfo() {
		return dateInfo;
	}

	/**
	 * @return cyusyutuSyukeiInfoList
	 */
	public List<CyusyutuSyukeiInfo> getCyusyutuSyukeiInfoList() {
		return cyusyutuSyukeiInfoList;
	}

	/**
	 * @return keyColList
	 */
	public List<KeyCol> getKeyColList() {
		return keyColList;
	}

	/**
	 * @return midasiCyusyutuSyukeiInfoList
	 */
	public List<CyusyutuSyukeiInfo> getMidasiCyusyutuSyukeiInfoList() {
		return midasiCyusyutuSyukeiInfoList;
	}


	/**
	 * @return midasiKeyColList
	 */
	public List<KeyCol> getMidasiKeyColList() {
		return midasiKeyColList;
	}

	/**
	 * @return selectPtn
	 */
	public IJisekiESienList_SearchSelectPtn getSelectPtn() {
		return selectPtn;
	}

	/**
	 * @return jucyuSearch
	 */
	public boolean isJucyuSearch() {
		return jucyuSearch;
	}

	/**
	 * @return syukaSearch
	 */
	public boolean isSyukaSearch() {
		return syukaSearch;
	}

	/**
	 * @return reinyuSearch
	 */
	public boolean isReinyuSearch() {
		return reinyuSearch;
	}

	/**
	 * @return jigyosyoYosanMap
	 */
	public Map<String, String> getJigyosyoYosanMap() {
		return jigyosyoYosanMap;
	}

	/**
	 * @param jigyosyoYosanMap セットする jigyosyoYosanMap
	 */
	public void setJigyosyoYosanMap(Map<String, String> jigyosyoYosanMap) {
		this.jigyosyoYosanMap = jigyosyoYosanMap;
	}

	/**
	 * 丸めinterface
	 */
	private interface IRound {
		BigDecimal round(BigDecimal value);
	}

	/**
	 * 計算interface
	 */
	private interface ICalc {
		BigDecimal calc(String jucyu, String syuka , String reinyu);
	}

	/**
	 * 抽出・集計情報定義
	 */
	public enum CyusyutuSyukeiInfo {
		// 事業所
		JIGYOSYO(new KeyCol[]{KeyCol.JIGYOSYO_CD}, HEADER_CD_MIDASI_JIGYOSYO, new String[]{"JIGYOSYO_NM"}, new String[]{HEADER_NM_MIDASI_JIGYOSYO}, new MidasiClass[]{MidasiClass.MEDIUM_WIDTH_MJ}),
		// 課
		KA(new KeyCol[]{KeyCol.KA_CD}, HEADER_CD_MIDASI_KA, new String[]{"KA_NM"}, new String[]{HEADER_NM_MIDASI_KA}, new MidasiClass[]{MidasiClass.MEDIUM_WIDTH_MJ}),
		// 担当者
		TANTO(new KeyCol[]{KeyCol.TANTO_CD}, HEADER_CD_MIDASI_TANTO, new String[]{"TANTO_NM"}, new String[]{HEADER_NM_MIDASI_TANTO}, new MidasiClass[]{MidasiClass.MEDIUM_WIDTH_MJ}),
		// 都道府県
		TODOFUKN(new KeyCol[]{KeyCol.TODOFUKN_CD}, HEADER_CD_MIDASI_TODOFUKN, new String[]{"TODOFUKN_NM"}, new String[]{HEADER_NM_MIDASI_TODOFUKN}, new MidasiClass[]{MidasiClass.MEDIUM_WIDTH_MJ}),
		// 全国卸
		ZENKOKU(new KeyCol[]{KeyCol.HJISEKI_SYUKEI_CD}, HEADER_CD_MIDASI_ZENKOKU, new String[]{"ZENKOKU_NM"}, new String[]{HEADER_NM_MIDASI_ZENKOKU}, new MidasiClass[]{MidasiClass.MEDIUM_WIDTH_MJ}),
		// 特約
		TOKUYAKU(new KeyCol[]{KeyCol.OROSITEN_CD_1JITEN}, HEADER_CD_MIDASI_OROSITEN_1JITEN, new String[]{"OROSITEN_1JITEN_NM"}, new String[]{HEADER_NM_MIDASI_OROSITEN_1JITEN}, new MidasiClass[]{MidasiClass.LARGE_WIDTH_MJ}),
		// 送荷先
		SOUNISAKI(new KeyCol[]{KeyCol.OROSITEN_CD_LAST}, HEADER_CD_MIDASI_OROSITEN_LAST, new String[]{"OROSITEN_LAST_NM"}, new String[]{HEADER_NM_MIDASI_OROSITEN_LAST}, new MidasiClass[]{MidasiClass.LARGE_WIDTH_MJ}),
		// 酒販店
		SYUHANTEN(new KeyCol[]{KeyCol.SYUHANTEN_CD}, HEADER_CD_MIDASI_SYUHANTEN, new String[]{"SYUHANTEN_NM"}, new String[]{HEADER_NM_MIDASI_SYUHANTEN}, new MidasiClass[]{MidasiClass.LARGE_WIDTH_MJ}),
		// 運送店
		UNSOTEN(new KeyCol[]{KeyCol.UNSOTEN_CD}, HEADER_CD_MIDASI_UNSOTEN, new String[]{"UNSOTEN_NM"}, new String[]{HEADER_NM_MIDASI_UNSOTEN}, new MidasiClass[]{MidasiClass.MEDIUM_WIDTH_MJ}),
		// 輸出国
		SYUKA_SAKI_COUNTRY(new KeyCol[]{KeyCol.SYUKA_SAKI_COUNTRY_CD}, HEADER_CD_MIDASI_SYUKA_SAKI_COUNTRY, new String[]{"SYUKA_SAKI_COUNTRY_NM"}, new String[]{HEADER_NM_MIDASI_SYUKA_SAKI_COUNTRY}, new MidasiClass[]{MidasiClass.MEDIUM_WIDTH_MJ}),
		// 容器材質
		YOUKIZAISITU(new KeyCol[]{KeyCol.SOZAI_KBN, KeyCol.TNPN_VOL}, HEADER_CD_MIDASI_JYUTEN_YOUKI, new String[]{"YOUKIZAISITU_NM","YOURYO"}, new String[]{HEADER_NM_MIDASI_YOUKIZAISITU, HEADER_NM_MIDASI_YOURYO}, new MidasiClass[]{MidasiClass.MEDIUM_WIDTH_MJ, MidasiClass.SMALL_WIDTH_NM}),
		// 酒種
		SAKADANE(new KeyCol[]{KeyCol.TANE_CD}, HEADER_CD_MIDASI_SAKADANE, new String[]{"SAKADANE_NM"}, new String[]{HEADER_NM_MIDASI_SAKADANE}, new MidasiClass[]{MidasiClass.MEDIUM_WIDTH_MJ}),
		// 酒質
		SAKESITU(new KeyCol[]{KeyCol.HANBAI_BUNRUI_CD}, HEADER_CD_MIDASI_SAKESITU, new String[]{"SAKESITU_NM"}, new String[]{HEADER_NM_MIDASI_SAKESITU}, new MidasiClass[]{MidasiClass.MEDIUM_WIDTH_MJ}),
		// 商品
		SHOHIN(new KeyCol[]{KeyCol.SHOHIN_CD}, HEADER_CD_MIDASI_SHOHIN, new String[]{"SHOHIN_NM","YOUKI_NM","IRISU","YOURYO"}, new String[]{HEADER_NM_MIDASI_SHOHIN, HEADER_NM_MIDASI_YOUKI, HEADER_NM_MIDASI_IRISU, HEADER_NM_MIDASI_YOURYO}, new MidasiClass[]{MidasiClass.MEDIUM_WIDTH_MJ, MidasiClass.SMALL_WIDTH_MJ, MidasiClass.SMALL_WIDTH_NM, MidasiClass.SMALL_WIDTH_NM}),
		// 容器
		YOUKI(new KeyCol[]{KeyCol.SOZAI_KBN, KeyCol.JYUTEN_YOURYO}, HEADER_CD_MIDASI_JYUTEN_YOUKI, new String[]{"JYUTEN_YOUKI_NM"}, new String[]{HEADER_NM_MIDASI_JYUTEN_YOUKI}, new MidasiClass[]{MidasiClass.MEDIUM_WIDTH_MJ});

		/** キー列 */
		private KeyCol[] keyCols;
		/** ヘッダコード名称 */
		private String headerCdNm;
		/** 名称列 */
		private String[] nmCols;
		/** ヘッダ名称 */
		private String[] headerNms;
		/** クラス名称 */
		private MidasiClass[] classNms;
		/**
		 * コンストラクタ
		 * @param keyCols キー列
		 * @param headerCdNm ヘッダコード名称
		 * @param nmCols 名称列
		 * @param headerNms ヘッダ名称
		 * @param classNms クラス名称
		 */
		CyusyutuSyukeiInfo(KeyCol[] keyCols, String headerCdNm, String[] nmCols, String[] headerNms, MidasiClass[] classNms) {
			this.keyCols = keyCols;
			this.headerCdNm = headerCdNm;
			this.nmCols = nmCols;
			this.headerNms = headerNms;
			this.classNms = classNms;
		}

		/**
		 * @return keyCols
		 */
		public KeyCol[] getKeyCols() {
			return keyCols;
		}

		/**
		 * @return headerCdNm
		 */
		public String getHeaderCdNm() {
			return headerCdNm;
		}

		/**
		 * @return nmCols
		 */
		public String[] getNmCols() {
			return nmCols;
		}

		/**
		 * @return headerNms
		 */
		public String[] getHeaderNms() {
			return headerNms;
		}

		/**
		 * @return classNms
		 */
		public MidasiClass[] getClassNms() {
			return classNms;
		}

		/**
		 * 抽出・集計情報リスト取得
		 * @param searchForm 検索フォーム
		 * @return 抽出・集計情報リスト
		 */
		static List<CyusyutuSyukeiInfo> getCyusyutuSyukeiInfo(JisekiESienList_SearchForm searchForm) {
			// 重複防ぐためset
			Set<CyusyutuSyukeiInfo> cyusyutuSyukeiInfoSet = new LinkedHashSet<CyusyutuSyukeiInfo>();
			// 集計内容
			String syukeiNaiyo = searchForm.getSyukeiNaiyo();
			// 抽出条件
			String cyusyutuJyoken = searchForm.getCyusyutuJyoken();

			// 選択した抽出条件に紐付く抽出・集計情報リスト
			CyusyutuSyukeiInfo[] cyusyutuJyokenCyusyutuSyukeiInfos = CYUSYUTU_JOKEN_CYUSYUTU_SYUKEI_MAP.get(cyusyutuJyoken);
			if(cyusyutuJyokenCyusyutuSyukeiInfos != null) {
				cyusyutuSyukeiInfoSet.addAll(Arrays.asList(cyusyutuJyokenCyusyutuSyukeiInfos));
			}

			// 選択した集計内容に紐付く抽出・集計情報リスト
			CyusyutuSyukeiInfo[] syukeiNaiyoCyusyutuSyukeiInfo = SYUKEI_NAIYO_CYUSYUTU_SYUKEI_MAP.get(syukeiNaiyo);
			if(syukeiNaiyoCyusyutuSyukeiInfo != null) {
				cyusyutuSyukeiInfoSet.addAll(Arrays.asList(syukeiNaiyoCyusyutuSyukeiInfo));
			}

			// 集計内容op
			// 酒質
			if(PbsUtil.isEqual(searchForm.getSakesituOp(),SYUKEI_NAIYO_OP_KB_SAKESITU_ON)) {
				cyusyutuSyukeiInfoSet.add(SAKESITU);
			}
			// 充填容器
			if(PbsUtil.isEqual(searchForm.getJyutenyoukiOp(),SYUKEI_NAIYO_OP_KB_JUTENYOUKI_ON)) {
				cyusyutuSyukeiInfoSet.add(YOUKI);
			}
			if(cyusyutuSyukeiInfoSet.isEmpty()) {
				throw new IllegalArgumentException();
			}
			return new ArrayList<CyusyutuSyukeiInfo>(cyusyutuSyukeiInfoSet);
		}
	}

	/**
	 * 見出しクラス定義
	 */
	public enum MidasiClass {
		LARGE_WIDTH_MJ("midasiLargeWidth", "mjView"),
		MEDIUM_WIDTH_MJ("midasiMediumWidth", "mjView"),
		SMALL_WIDTH_MJ("midasiSmallWidth", "mjView"),
		SMALL_WIDTH_NM("midasiSmallWidth", "nmView");

		/** 幅 */
		private String width;
		/** 表示形式 */
		private String view;

		/**
		 * コンストラクタ
		 * @param width 幅
		 * @param view 表示形式
		 */
		MidasiClass(String width, String view) {
			this.width = width;
			this.view = view;
		}

		/**
		 * @return width
		 */
		public String getWidth() {
			return width;
		}

		/**
		 * @return view
		 */
		public String getView() {
			return view;
		}
	}

	/**
	 * キー列定義
	 */
	public enum KeyCol {
		// 事業所
		JIGYOSYO_CD("JIGYOSYO_CD", true),
		// 課
		KA_CD("KA_CD", true),
		// 担当者
		TANTO_CD("TANTO_CD", true),
		// 都道府県
		TODOFUKN_CD("TODOFUKN_CD", true),
		// 全国卸
		HJISEKI_SYUKEI_CD("HJISEKI_SYUKEI_CD", true),
		// 特約
		OROSITEN_CD_1JITEN("OROSITEN_CD_1JITEN", true),
		// 送荷先
		OROSITEN_CD_LAST("OROSITEN_CD_LAST", true),
		// 酒販店
		SYUHANTEN_CD("SYUHANTEN_CD", true),
		// 運送店
		UNSOTEN_CD("UNSOTEN_CD", true),
		// 輸出国
		SYUKA_SAKI_COUNTRY_CD("SYUKA_SAKI_COUNTRY_CD", true),
		// 酒種
		TANE_CD("TANE_CD", true),
		// 酒質
		HANBAI_BUNRUI_CD("HANBAI_BUNRUI_CD", true),
		// 商品
		SHOHIN_CD("SHOHIN_CD", true),
		// 素材区分
		SOZAI_KBN("SOZAI_KBN", true),
		// 充填容量
		JYUTEN_YOURYO("JYUTEN_YOURYO", false),
		// 容量
		TNPN_VOL("TNPN_VOL", false);

		/** 列名 */
		private String col;
		/** ソートAscフラグ */
		private boolean asc;

		/**
		 * コンストラクタ
		 * @param col 列名
		 * @param asc ソートAscフラグ
		 */
		KeyCol(String col, boolean asc) {
			this.col = col;
			this.asc = asc;
		}

		/**
		 * @return col
		 */
		public String getCol() {
			return col;
		}

		/**
		 * @return asc
		 */
		public boolean isAsc() {
			return asc;
		}
	}

	/**
	 * トランザクションテーブル情報
	 */
	public enum TranTable {
		// 受注
		JYUCYU(TranTableHd.JYUCYU_HD, TranTableDt.JYUCYU_DT, TranTableCtg.JYUCYU_CTG, ALIAS_JYUCYU),
		// 出荷
		SYUKA(TranTableHd.SYUKA_HD, TranTableDt.SYUKA_DT, TranTableCtg.SYUKA_CTG, ALIAS_SYUKA),
		// 戻入
		REINYU(TranTableHd.REINYU_HD, TranTableDt.REINYU_DT, TranTableCtg.REINYU_CTG, ALIAS_REINYU);

		/**
		 * コンストラクタ
		 * @param hd ヘッダテーブル
		 * @param dt ディテールテーブル
		 * @param ctg カテゴリテーブル
		 * @param alias エイリアス名
		 */
		private TranTable(final TranTableHd hd, final TranTableDt dt, final TranTableCtg ctg, final String alias) {
			this.hd = hd;
			this.dt = dt;
			this.ctg = ctg;
			this.alias = alias;
		}
		/** ヘッダテーブル */
		private final TranTableHd hd;
		/** ディテールテーブル */
		private final TranTableDt dt;
		/** カテゴリテーブル */
		private final TranTableCtg ctg;
		/** エイリアス名 */
		private final String alias;

		/**
		 * @return hd
		 */
		public TranTableHd getHd() {
			return hd;
		}
		/**
		 * @return dt
		 */
		public TranTableDt getDt() {
			return dt;
		}
		/**
		 * @return ctg
		 */
		public TranTableCtg getCtg() {
			return ctg;
		}
		/**
		 * @return alias
		 */
		public String getAlias() {
			return alias;
		}
		/**
		 * エイリアス付の列名取得
		 * @param col 列名
		 * @return エイリアス付の列名
		 */
		public String withAlias(String col) {
			return this.alias + "." + col;
		}
		/**
		 * エイリアス付の列名取得(列名指定用)
		 * @return エイリアス付の列名
		 */
		public String withAliasForColNm() {
			return this.alias + "_";
		}
		/**
		 * エイリアス付の列名取得(列名指定用)
		 * @param col 列名
		 * @return エイリアス付の列名
		 */
		public String withAliasForColNm(String col) {
			return this.alias + "_" + col;
		}
	}

	/**
	 * トランザクションヘッダテーブル情報
	 */
	public enum TranTableHd {
		// 受注
		JYUCYU_HD(TBL_T_JYUCYU_HD, "JH", "KAISYA_CD", "JYUCYU_NO", "", "SYUKA_DT", "MINASI_DT", "UNSOTEN_CD", "TATESN_CD", "TOKUYAKUTEN_CD", "SYUHANTEN_CD", "SYUKA_SAKI_COUNTRY_CD"),
		// 出荷
		SYUKA_HD(TBL_T_SYUKA_HD , "SH", "KAISYA_CD", "URIDEN_NO", "SYUBETU_CD", "SYUKA_DT", "MINASI_DT", "UNSOTEN_CD", "TATESN_CD", "TOKUYAKUTEN_CD", "SYUHANTEN_CD", "SYUKA_SAKI_COUNTRY_CD"),
		// 戻入
		REINYU_HD(TBL_T_REINYU_HD,  "RH", "KAISYA_CD", "REINYUDEN_NO", "SYUBETU_CD", "UKEIRE_DT", "MINASI_DT", "UNSOTEN_CD", "TATESN_CD", "TOKUYAKUTEN_CD", "SYUHANTEN_CD", "SYUKA_SAKI_COUNTRY_CD");
		/**
		 * コンストラクタ
		 * @param tableNm テーブル名
		 * @param alias エイリアス
		 * @param kaisyaCd 会社CD
		 * @param no NO
		 * @param syubetuCd ﾃﾞｰﾀ種別CD
		 * @param denpyoHakkoDt 伝票発行日
		 * @param minasiDt ﾐﾅｼ日付
		 * @param unsotenCd 運送店CD
		 * @param tatesenCd 縦線CD
		 * @param tokuyakutenCd 特約店CD
		 * @param syuhantenCd 酒販店CD
		 * @param syukaSakiCountryd 出荷先国CD
		 */
		private TranTableHd(final String tableNm, final String alias, final  String kaisyaCd, final String no, final String syubetuCd, final String denpyoHakkoDt, final String minasiDt, final String unsotenCd, final String tatesenCd, final String tokuyakutenCd, final String syuhantenCd, final String syukaSakiCountryd) {
			this.tableNm = tableNm;
			this.alias = alias;
			this.kaisyaCd = kaisyaCd;
			this.no = no;
			this.syubetuCd = syubetuCd;
			this.denpyoHakkoDt = denpyoHakkoDt;
			this.minasiDt = minasiDt;
			this.unsotenCd = unsotenCd;
			this.tatesenCd = tatesenCd;
			this.tokuyakutenCd = tokuyakutenCd;
			this.syuhantenCd = syuhantenCd;
			this.syukaSakiCountryd = syukaSakiCountryd;
		}
		/** テーブル名 */
		private final String tableNm;
		/** エイリアス */
		private final String alias;
		/** 会社CD */
		private final String kaisyaCd;
		/** NO */
		private final String no;
		/** ﾃﾞｰﾀ種別CD */
		private final String syubetuCd;
		/** 伝票発行日 */
		private final String denpyoHakkoDt;
		/** ﾐﾅｼ日付 */
		private final String minasiDt;
		/** 運送店CD */
		private final String unsotenCd;
		/** 縦線CD */
		private final String tatesenCd;
		/** 特約店CD */
		private final String tokuyakutenCd;
		/** 酒販店CD */
		private final String syuhantenCd;
		/** 出荷先国CD */
		private final String syukaSakiCountryd;

		/**
		 * エイリアス付の列名取得
		 * @param col 列名
		 * @return エイリアス付の列名
		 */
		public String withAlias(String col) {
			return this.alias + "." + col;
		}

		/**
		 * @return tableNm
		 */
		public String getTableNm() {
			return tableNm;
		}

		/**
		 * @return alias
		 */
		public String getAlias() {
			return alias;
		}

		/**
		 * @return kaisyaCd
		 */
		public String getKaisyaCd() {
			return kaisyaCd;
		}

		/**
		 * @return no
		 */
		public String getNo() {
			return no;
		}

		/**
		 * @return syubetuCd
		 */
		public String getSyubetuCd() {
			return syubetuCd;
		}

		/**
		 * @return denpyoHakkoDt
		 */
		public String getDenpyoHakkoDt() {
			return denpyoHakkoDt;
		}

		/**
		 * @return minasiDt
		 */
		public String getMinasiDt() {
			return minasiDt;
		}

		/**
		 * @return unsotenCd
		 */
		public String getUnsotenCd() {
			return unsotenCd;
		}

		/**
		 * @return tatesenCd
		 */
		public String getTatesenCd() {
			return tatesenCd;
		}

		/**
		 * @return tokuyakutenCd
		 */
		public String getTokuyakutenCd() {
			return tokuyakutenCd;
		}

		/**
		 * @return syuhantenCd
		 */
		public String getSyuhantenCd() {
			return syuhantenCd;
		}

		/**
		 * @return syukaSakiCountryd
		 */
		public String getSyukaSakiCountryd() {
			return syukaSakiCountryd;
		}
	}

	/**
	 * トランザクションディテールテーブル情報
	 */
	public enum TranTableDt {
		// 受注
		JYUCYU_DT(TBL_T_JYUCYU_DT, "JD", "KAISYA_CD", "JYUCYU_NO", "INPUT_LINE_NO", "SHOHIN_CD", "BUPIN_KBN", "SYUKA_HANBAI_KINGAKU", "SYUKA_SOUYOURYO", "SYUKA_ALL_BARA", "SYUKA_SU_CASE", "SYUKA_ALL_WEIGTH"),
		// 出荷
		SYUKA_DT(TBL_T_SYUKA_DT, "SD", "KAISYA_CD", "URIDEN_NO", "URIDEN_LINE_NO", "SHOHIN_CD", "BUPIN_KBN", "SYUKA_HANBAI_KINGAKU", "SYUKA_SOUYOURYO", "SYUKA_ALL_BARA", "SYUKA_SU_CASE", "SYUKA_ALL_WEIGTH"),
		// 戻入
		REINYU_DT(TBL_T_REINYU_DT, "RD", "KAISYA_CD", "REINYUDEN_NO", "INPUT_LINE_NO", "SHOHIN_CD", "BUPIN_KBN", "UKEIRE_KINGAKU", "SYUKA_SOUYOURYO", "UKEIRE_ALL_BARA", "UKEIRE_SU_CASE", "UKEIRE_SU_WEIGTH");
		/**
		 * コンストラクタ
		 * @param tableNm テーブル名
		 * @param alias エイリアス
		 * @param kaisyaCd 会社CD
		 * @param no NO
		 * @param lineNo 行NO
		 * @param shohinCd 商品CD
		 * @param bupinKbn 物品区分
		 * @param kingaku 販売額
		 * @param youryo 容量（L）
		 * @param bara 出荷数量_換算総ｾｯﾄ数
		 * @param caseSu 出荷数量_箱数
		 * @param weight 出荷重量（KG)
		 */
		private TranTableDt(final String tableNm, final String alias, final String kaisyaCd, final String no, final String lineNo, final String shohinCd, final String bupinKbn, final String kingaku, final String youryo, final String bara, final String caseSu, final String weight) {
			this.tableNm = tableNm;
			this.alias = alias;
			this.kaisyaCd = kaisyaCd;
			this.no = no;
			this.lineNo = lineNo;
			this.shohinCd = shohinCd;
			this.bupinKbn = bupinKbn;
			this.kingaku = kingaku;
			this.youryo = youryo;
			this.bara = bara;
			this.caseSu = caseSu;
			this.weight = weight;
		}
		/** テーブル名 */
		private final String tableNm;
		/** エイリアス */
		private final String alias;
		/** 会社CD */
		private final String kaisyaCd;
		/** NO */
		private final String no;
		/** 行NO */
		private final String lineNo;
		/** 商品CD */
		private final String shohinCd;
		/** 物品区分 */
		private final String bupinKbn;
		/** 販売額 */
		private final String kingaku;
		/** 容量（L） */
		private final String youryo;
		/** 出荷数量_換算総ｾｯﾄ数 */
		private final String bara;
		/** 出荷数量_箱数 */
		private final String caseSu;
		/** 出荷重量（KG) */
		private final String weight;

		/**
		 * エイリアス付の列名取得
		 * @param col 列名
		 * @return エイリアス付の列名
		 */
		public String withAlias(String col) {
			return this.alias + "." + col;
		}

		/**
		 * @return tableNm
		 */
		public String getTableNm() {
			return tableNm;
		}

		/**
		 * @return alias
		 */
		public String getAlias() {
			return alias;
		}

		/**
		 * @return kaisyaCd
		 */
		public String getKaisyaCd() {
			return kaisyaCd;
		}

		/**
		 * @return no
		 */
		public String getNo() {
			return no;
		}

		/**
		 * @return lineNo
		 */
		public String getLineNo() {
			return lineNo;
		}

		/**
		 * @return shohinCd
		 */
		public String getShohinCd() {
			return shohinCd;
		}

		/**
		 * @return bupinKbn
		 */
		public String getBupinKbn() {
			return bupinKbn;
		}

		/**
		 * @return kingaku
		 */
		public String getKingaku() {
			return kingaku;
		}

		/**
		 * @return youryo
		 */
		public String getYouryo() {
			return youryo;
		}

		/**
		 * @return bara
		 */
		public String getBara() {
			return bara;
		}

		/**
		 * @return caseSu
		 */
		public String getCaseSu() {
			return caseSu;
		}

		/**
		 * @return weight
		 */
		public String getWeight() {
			return weight;
		}
	}

	/**
	 * トランザクションカテゴリテーブル情報
	 */
	public enum TranTableCtg {
		// 受注
		JYUCYU_CTG(TBL_T_JUCYU_JSKCTG, "JC", "KAISYA_CD", "JYUCYU_NO", "SHN_CTGR_LINE_NO", "KTKSY_CD", "SEIHIN_CD", "HANBAI_KINGAKU", "SYUKA_VOL", "SYUKA_HON"),
		// 出荷
		SYUKA_CTG(TBL_T_SYUKA_JSKCTG, "SC", "KAISYA_CD", "URIDEN_NO", "URI_CTGR_LINE_NO", "KTKSY_CD", "SEIHIN_CD", "HANBAI_KINGAKU", "SYUKA_VOL", "SYUKA_HON"),
		// 戻入
		REINYU_CTG(TBL_T_REINYU_JSKCTG, "RC", "KAISYA_CD", "REINYUDEN_NO", "INPUT_LINE_NO", "KTKSY_CD", "SEIHIN_CD", "UKEIRE_KINGAKU", "UKEIRE_VOL", "UKEIRE_HON");

		/**
		 * コンストラクタ
		 * @param tableNm テーブル名
		 * @param alias エイリアス
		 * @param kaisyaCd 会社CD
		 * @param no NO
		 * @param lineNo 行NO
		 * @param ktksyCd 寄託者CD
		 * @param seihinCd 製品CD
		 * @param hanbaiBunruiCd 販売分類CD
		 * @param sozaiKbn 素材区分
		 * @param kingaku 販売額
		 * @param youryo 容量（L）
		 * @param bara バラ数
		 */
		private TranTableCtg(final String tableNm, final String alias, final String kaisyaCd, final String no, final String lineNo, final String ktksyCd, final String seihinCd, final String kingaku, final String youryo, final String bara) {
			this.tableNm = tableNm;
			this.alias = alias;
			this.kaisyaCd = kaisyaCd;
			this.no = no;
			this.lineNo = lineNo;
			this.ktksyCd = ktksyCd;
			this.seihinCd = seihinCd;
			this.kingaku = kingaku;
			this.youryo = youryo;
			this.bara = bara;
		}
		/** テーブル名 */
		private final String tableNm;
		/** エイリアス */
		private final String alias;
		/** 会社CD */
		private final String kaisyaCd;
		/** NO */
		private final String no;
		/** 行NO */
		private final String lineNo;
		/** 寄託者CD */
		private final String ktksyCd;
		/** 製品CD */
		private final String seihinCd;
		/** 販売額 */
		private final String kingaku;
		/** 容量（L） */
		private final String youryo;
		/** バラ数 */
		private final String bara;

		/**
		 * エイリアス付の列名取得
		 * @param col 列名
		 * @return エイリアス付の列名
		 */
		public String withAlias(String col) {
			return this.alias + "." + col;
		}

		/**
		 * @return tableNm
		 */
		public String getTableNm() {
			return tableNm;
		}
		/**
		 * @return alias
		 */
		public String getAlias() {
			return alias;
		}
		/**
		 * @return kaisyaCd
		 */
		public String getKaisyaCd() {
			return kaisyaCd;
		}
		/**
		 * @return no
		 */
		public String getNo() {
			return no;
		}
		/**
		 * @return lineNo
		 */
		public String getLineNo() {
			return lineNo;
		}
		/**
		 * @return ktksyCd
		 */
		public String getKtksyCd() {
			return ktksyCd;
		}
		/**
		 * @return seihinCd
		 */
		public String getSeihinCd() {
			return seihinCd;
		}
		/**
		 * @return kingaku
		 */
		public String getKingaku() {
			return kingaku;
		}
		/**
		 * @return youryo
		 */
		public String getYouryo() {
			return youryo;
		}
		/**
		 * @return bara
		 */
		public String getBara() {
			return bara;
		}
	}

	/**
	 * 日付情報
	 */
	public class DateInfo {
		/** 日付範囲 */
		private List<HonnenZennenDateRange> dateRange = new ArrayList<HonnenZennenDateRange>();
		/** 条件用日付範囲 */
		private List<DateRange> whereDateRange = new ArrayList<DateRange>();
		/** 予算対比用の累計日付範囲 */
		private HonnenZennenDateRange ruikeiDateRange;

		/**
		 * @return dateRange
		 */
		public List<HonnenZennenDateRange> getDateRange() {
			return dateRange;
		}

		/**
		 * @param dateRange セットする dateRange
		 */
		public void setDateRange(List<HonnenZennenDateRange> dateRange) {
			this.dateRange = dateRange;
		}

		/**
		 * @return whereDateRange
		 */
		public List<DateRange> getWhereDateRange() {
			return whereDateRange;
		}
		/**
		 * @param whereDateRange セットする whereDateRange
		 */
		public void setWhereDateRange(List<DateRange> whereDateRange) {
			this.whereDateRange = whereDateRange;
		}

		/**
		 * @return ruikeiDateRange
		 */
		public HonnenZennenDateRange getRuikeiDateRange() {
			return ruikeiDateRange;
		}

		/**
		 * @param ruikeiDateRange セットする ruikeiDateRange
		 */
		public void setRuikeiDateRange(HonnenZennenDateRange ruikeiDateRange) {
			this.ruikeiDateRange = ruikeiDateRange;
		}
	}

	/**
	 * 日付範囲
	 */
	public class DateRange {
		/**
		 * コンストラクタ
		 * @param from 日付（From）
		 * @param to 日付（To）
		 */
		public DateRange(String from, String to) {
			this.from = from;
			this.to = to;
		}
		/**
		 * コンストラクタ
		 * @param from 日付（From）
		 * @param to 日付（To）
		 * @param dispDt 表示日付
		 */
		public DateRange(String from, String to, String dispDt) {
			this.from = from;
			this.to = to;
			this.dispDt = dispDt;
		}
		/** 日付（From） */
		private String from;
		/** 日付（To） */
		private String to;
		/** 表示日付 */
		private String dispDt;

		/**
		 * @return from
		 */
		public String getFrom() {
			return from;
		}
		/**
		 * @param from セットする from
		 */
		public void setFrom(String from) {
			this.from = from;
		}
		/**
		 * @return to
		 */
		public String getTo() {
			return to;
		}
		/**
		 * @param to セットする to
		 */
		public void setTo(String to) {
			this.to = to;
		}
		/**
		 * @return dispDt
		 */
		public String getDispDt() {
			return dispDt;
		}
		/**
		 * @param dispDt セットする dispDt
		 */
		public void setDispDt(String dispDt) {
			this.dispDt = dispDt;
		}

	}

	/**
	 * 本年前年日付範囲
	 */
	public class  HonnenZennenDateRange extends DateRange {
		/**
		 * コンストラクタ
		 * @param from 日付（From）
		 * @param to 日付（To）
		 */
		public HonnenZennenDateRange(String from, String to) {
			super(from, to);
		}

		/**
		 * コンストラクタ
		 * @param from 日付（From）
		 * @param to 日付（To）
		 * @param dispDt 表示日付
		 */
		public HonnenZennenDateRange(String from, String to, String dispDt) {
			super(from, to, dispDt);
		}

		/**
		 * コンストラクタ
		 * @param from 日付（From）
		 * @param to 日付（To）
		 * @param zennenFrom 前年日付（From）
		 * @param zennenTo 前年日付（To）
		 */
		public HonnenZennenDateRange(String from, String to,String zennenFrom, String zennenTo) {
			super(from, to);
			this.zennenFrom = zennenFrom;
			this.zennenTo = zennenTo;
		}

		/**
		 * コンストラクタ
		 * @param from 日付（From）
		 * @param to 日付（To）
		 * @param zennenFrom 前年日付（From）
		 * @param zennenTo 前年日付（To）
		 * @param dispDt 表示日付
		 *
		 */
		public HonnenZennenDateRange(String from, String to,String zennenFrom, String zennenTo, String dispDt) {
			super(from, to, dispDt);
			this.zennenFrom = zennenFrom;
			this.zennenTo = zennenTo;
		}

		/** 前年日付（From） */
		String zennenFrom;
		/** 前年日付（To） */
		String zennenTo;

		/**
		 * 本年保持判定
		 * @return 本年保持フラグ
		 */
		public boolean hasHonnen() {
			return getFrom() != null && getTo() != null;
		}

		/**
		 * 前年保持判定
		 * @return 前年保持フラグ
		 */
		public boolean hasZennen() {
			return zennenFrom != null && zennenTo != null;
		}

		/**
		 * @return zennenFrom
		 */
		public String getZennenFrom() {
			return zennenFrom;
		}
		/**
		 * @return zennenTo
		 */
		public String getZennenTo() {
			return zennenTo;
		}
	}

	/**
	 * HtmlのTable（一覧表示）ヘッダ情報
	 */
	public class TableHeaderInfo {

		/** ヘッダ行数1行フラグ */
		private boolean header1Line;

		/** 見出しヘッダ */
		private List<MidasiColInfo> midasiHeaderList = new ArrayList<MidasiColInfo>();

		/** 累計ヘッダ行数1行フラグ */
		private boolean ruikeiHeader1Line;

		/** 累計ヘッダ */
		private ColInfo ruikeiHeader = new ColInfo();

		/** 日付ヘッダ */
		private List<ColInfo> dateHeaderList = new ArrayList<ColInfo>();

		/**
		 * @return header1Line
		 */
		public boolean isHeader1Line() {
			return header1Line;
		}

		/**
		 * @param header1Line セットする header1Line
		 */
		public void setHeader1Line(boolean header1Line) {
			this.header1Line = header1Line;
		}

		/**
		 * @return midasiHeaderList
		 */
		public List<MidasiColInfo> getMidasiHeaderList() {
			return midasiHeaderList;
		}

		/**
		 * @param midasiHeaderList セットする midasiHeaderList
		 */
		public void setMidasiHeaderList(List<MidasiColInfo> midasiHeaderList) {
			this.midasiHeaderList = midasiHeaderList;
		}

		/**
		 * @return ruikeiHeader1Line
		 */
		public boolean isRuikeiHeader1Line() {
			return ruikeiHeader1Line;
		}

		/**
		 * @param ruikeiHeader1Line セットする ruikeiHeader1Line
		 */
		public void setRuikeiHeader1Line(boolean ruikeiHeader1Line) {
			this.ruikeiHeader1Line = ruikeiHeader1Line;
		}

		/**
		 * @return ruikeiHeader
		 */
		public ColInfo getRuikeiHeader() {
			return ruikeiHeader;
		}

		/**
		 * @param ruikeiHeader セットする ruikeiHeader
		 */
		public void setRuikeiHeader(ColInfo ruikeiHeader) {
			this.ruikeiHeader = ruikeiHeader;
		}

		/**
		 * @return dateHeaderList
		 */
		public List<ColInfo> getDateHeaderList() {
			return dateHeaderList;
		}

		/**
		 * @param dateHeaderList セットする dateHeaderList
		 */
		public void setDateHeaderList(List<ColInfo> dateHeaderList) {
			this.dateHeaderList = dateHeaderList;
		}
	}

	/**
	 * 見出し列情報
	 */
	public class MidasiColInfo {

		/** コード列 */
		private String codeCol;

		/** 名称列 */
		private List<MidasiNameClass> nameColList = new ArrayList<MidasiNameClass>();

		/**
		 * @return codeCol
		 */
		public String getCodeCol() {
			return codeCol;
		}

		/**
		 * @param codeCol セットする codeCol
		 */
		public void setCodeCol(String codeCol) {
			this.codeCol = codeCol;
		}

		/**
		 * @return nameColList
		 */
		public List<MidasiNameClass> getNameColList() {
			return nameColList;
		}

		/**
		 * @param nameColList セットする nameColList
		 */
		public void setNameColList(List<MidasiNameClass> nameColList) {
			this.nameColList = nameColList;
		}
	}

	/**
	 * 見出し名称・クラス名情報
	 */
	public class MidasiNameClass {
		/** 名称 */
		private String nm;

		/** クラス名 */
		private MidasiClass midasiClass;

		/**
		 * コンストラクタ
		 * @param nm 名称
		 * @param midasiClass クラス名
		 */
		public MidasiNameClass(String nm, MidasiClass midasiClass) {
			this.nm = nm;
			this.midasiClass = midasiClass;
		}

		/**
		 * @return nm
		 */
		public String getNm() {
			return nm;
		}

		/**
		 * @param nm セットする nm
		 */
		public void setNm(String nm) {
			this.nm = nm;
		}

		/**
		 * @return midasiClass
		 */
		public MidasiClass getMidasiClass() {
			return midasiClass;
		}

		/**
		 * @param midasiClass セットする midasiClass
		 */
		public void setMidasiClass(MidasiClass midasiClass) {
			this.midasiClass = midasiClass;
		}
	}

	/**
	 * 列情報
	 */
	public class ColInfo {

		/** 1行目 */
		private String row1Col;

		/** 2行目 */
		private List<String> row2ColList = new ArrayList<String>();

		/**
		 * @return row1Col
		 */
		public String getRow1Col() {
			return row1Col;
		}

		/**
		 * @param row1Col セットする row1Col
		 */
		public void setRow1Col(String row1Col) {
			this.row1Col = row1Col;
		}

		/**
		 * @return row2ColList
		 */
		public List<String> getRow2ColList() {
			return row2ColList;
		}

		/**
		 * @param row2ColList セットする row2ColList
		 */
		public void setRow2ColList(List<String> row2ColList) {
			this.row2ColList = row2ColList;
		}

		/**
		 * colspan数取得
		 * @return colspan数
		 */
		public int getHeaderColspan() {
			return row2ColList.size();
		}
	}

	/**
	 * 本年・前年営業カレンダー情報
	 */
	class HonnenZennenEigyoCalendarInfo {
		/** 本年年月 */
		String honnenYm;
		/** 前年年月 */
		String zennenYm;
		/** 本年年月の1日 */
		String honnenFirstDt;
		/** 前年年月の1日 */
		String zennenFirstDt;
		/** 本年年月の末日 */
		String honnenLastDt;
		/** 本年年月の末日 */
		String zennenLastDt;
		/** 本年年月の最終営業日 */
		String honnenLastEigyoDt;
		/** 前年年月の最終営業日 */
		String zennenLastEigyoDt;
		/** 本年最終営業日カレンダー */
		Calendar honnenLastEigyoCal;
		/** 前年最終営業日カレンダー */
		Calendar zennenLastEigyoCal;
		/** 本年年月の営業カレンダー */
		List<EigyoCalendarInfo> honnenEigyoCalendarList;
		/** 前年年月の営業カレンダー */
		List<EigyoCalendarInfo> zennenEigyoCalendarList;

		/**
		 * コンストラクタ
		 * @param honnenDate 本年基準日
		 * @param eigyoCalendarUtil 営業カレンダーUtil
		 * @throws KitException
		 */
		HonnenZennenEigyoCalendarInfo(String honnenDate, ComEigyoCalendarUtil eigyoCalendarUtil) throws KitException {
			// 本年年月
			honnenYm = getYm(honnenDate);
			// 前年年月
			zennenYm =getYm(PbsUtil.getYMD(getZennenCalendar(getFirstDayOfMonthCal(honnenDate))));

			// 本年年月の1日
			honnenFirstDt = PbsUtil.getYMD(getFirstDayOfMonthCal(honnenYm));
			// 前年年月の1日
			zennenFirstDt = PbsUtil.getYMD(getFirstDayOfMonthCal(zennenYm));

			// 本年年月の末日
			honnenLastDt = eigyoCalendarUtil.getCalEndMonthDt(honnenFirstDt);
			// 前年年月の末日
			zennenLastDt = eigyoCalendarUtil.getCalEndMonthDt(zennenFirstDt);

			// 本年年月の営業カレンダー
			honnenEigyoCalendarList = convert2EigyoCalendarInfoList(eigyoCalendarUtil.getEigyobiCalendarMonth(honnenYm));
			// 前年年月の営業カレンダー
			zennenEigyoCalendarList = convert2EigyoCalendarInfoList(eigyoCalendarUtil.getEigyobiCalendarMonth(zennenYm));

			// 本年年月の最終営業日
			if(!isEigyoDt(honnenLastDt, true)) {
				// 末日が休業日の場合は、前の営業日
				honnenLastEigyoDt = getPreEigyoDt(honnenLastDt, true);
			} else {
				honnenLastEigyoDt = honnenLastDt;
			}

			// 前年年月の最終営業日
			if(!isEigyoDt(zennenLastDt, false)) {
				// 末日が休業日の場合は、前の営業日
				zennenLastEigyoDt = getPreEigyoDt(zennenLastDt, false);
			} else {
				zennenLastEigyoDt = zennenLastDt;
			}

			if(honnenLastEigyoDt != null) {
				// 本年最終営業日カレンダー
				honnenLastEigyoCal = convertCal(honnenLastEigyoDt);
			}
			if(zennenLastEigyoDt != null) {
				// 前年最終営業日カレンダー
				zennenLastEigyoCal = convertCal(zennenLastEigyoDt);
			}
		}

		/**
		 * 営業カレンダー情報リストへ変換
		 * @param eigyoCalRecs 営業カレンダーレコード
		 * @return 営業カレンダー情報リスト
		 */
		List<EigyoCalendarInfo> convert2EigyoCalendarInfoList(PbsRecord[] eigyoCalRecs) {
			List<EigyoCalendarInfo> eigyoCalendarList = new ArrayList<EigyoCalendarInfo>();
			if(eigyoCalRecs == null) return eigyoCalendarList;
			for (PbsRecord rec : eigyoCalRecs) {
				EigyoCalendarInfo eigyoCalendar = new EigyoCalendarInfo();
				eigyoCalendar.dt = rec.getString("CALENDAR_DT");
				eigyoCalendar.eigyobiSu = Integer.parseInt(rec.getString("ROW_NUMBER"));
				eigyoCalendarList.add(eigyoCalendar);
			}
			return eigyoCalendarList;
		}

		/**
		 * 営業日判定
		 * @param dt 日付
		 * @param honnen 本年前年フラグ
		 * @return 営業日かどうか
		 */
		boolean isEigyoDt(String dt, boolean honnen) {
			if(dt == null) return false;
			List<EigyoCalendarInfo> eigyoCalendarList = honnen ? honnenEigyoCalendarList : zennenEigyoCalendarList;
			for (EigyoCalendarInfo eigyoCalendar : eigyoCalendarList) {
				if(PbsUtil.isEqual(dt, eigyoCalendar.dt)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * 前営業日取得
		 * @param dt 日付
		 * @param honnen 本年前年フラグ
		 * @return 前営業日
		 */
		String getPreEigyoDt(String dt, boolean honnen) {
			if(dt == null) return null;
			List<EigyoCalendarInfo> eigyoCalendarList = honnen ? honnenEigyoCalendarList : zennenEigyoCalendarList;
			for (int i = eigyoCalendarList.size() - 1; i >= 0; i--) {
				if(eigyoCalendarList.get(i).dt.compareTo(dt) < 0) {
					return eigyoCalendarList.get(i).dt;
				}
			}
			return null;
		}

		/**
		 * 翌営業日取得
		 * @param dt 日付
		 * @param honnen 本年前年フラグ
		 * @return 翌営業日
		 */
		String getNextEigyoDt(String dt, boolean honnen) {
			if(dt == null) return null;
			List<EigyoCalendarInfo> eigyoCalendarList = honnen ? honnenEigyoCalendarList : zennenEigyoCalendarList;
			for (EigyoCalendarInfo eigyoCalendar : eigyoCalendarList) {
				if(eigyoCalendar.dt.compareTo(dt) > 0) {
					return eigyoCalendar.dt;
				}
			}
			return null;
		}

//		/**
//		 * 最終営業日判定
//		 * @param dt 日付
//		 * @param honnen 本年前年フラグ
//		 * @return 最終営業日かどうか
//		 */
//		boolean isLastEigyoDt(String dt, boolean honnen) {
//			String lastEigyoDt = honnen ? honnenLastEigyoDt : zennenLastEigyoDt;
//			return PbsUtil.isEqual(lastEigyoDt, dt);
//		}


		/**
		 * 営業日数取得
		 * @param dt 日付
		 * @param honnen 本年前年フラグ
		 * @return 営業日数
		 */
		Integer getEigyobiSuByDt(String dt, boolean honnen) {
			if(dt == null) return null;
			List<EigyoCalendarInfo> eigyoCalendarList = honnen ? honnenEigyoCalendarList : zennenEigyoCalendarList;
			for (EigyoCalendarInfo eigyoCalendar : eigyoCalendarList) {
				if(PbsUtil.isEqual(dt, eigyoCalendar.dt)) {
					return eigyoCalendar.eigyobiSu;
				}
			}
			return null;
		}

		/**
		 * 営業日取得
		 * @param eigyobiSu 営業日数
		 * @param honnen 本年前年フラグ
		 * @return 営業日
		 */
		String getDtByEigyobiSu(Integer eigyobiSu, boolean honnen) {
			if(eigyobiSu == null) return null;
			List<EigyoCalendarInfo> eigyoCalendarList = honnen ? honnenEigyoCalendarList : zennenEigyoCalendarList;
			for (EigyoCalendarInfo eigyoCalendar : eigyoCalendarList) {
				if(eigyobiSu == eigyoCalendar.eigyobiSu) {
					return eigyoCalendar.dt;
				}
			}
			return null;
		}

		/**
		 * 営業日取得
		 * @param dt 日付
		 * @param honnen 本年前年フラグ
		 * @return 営業日
		 */
		String getEigyoDt(String dt, boolean honnen) {
			if(!isEigyoDt(dt, honnen)) {
				// 休業日の場合は、前の営業日を取得
				String beforeEigyoDt = getPreEigyoDt(dt, honnen);
				return beforeEigyoDt;
//				if(beforeEigyoDt == null) {
//					// 前の営業日がない場合は、1日
//					return honnen ?  honnenFirstDt : zennenFirstDt;
//				}
			} else {
				return dt;
			}
		}

		/**
		 * 本年日付から前年の同営業日を取得
		 * @param honnenDt 本年日付
		 * @return 前年日付
		 */
		String getZennenEigyoDtByHonnenEigyoDt(String honnenDt) {
			Integer eigyobiSu = getEigyobiSuByDt(honnenDt, true);
			String zennenDt = getDtByEigyobiSu(eigyobiSu, false);
			return zennenDt;
//			if(zennenDt == null) {
//				// 前の営業日がない場合は、1日
//				return zennenFirstDt;
//			}
		}
	}

	/**
	 * 営業カレンダー情報
	 */
	class EigyoCalendarInfo {
		/** 日付 */
		String dt;
		/** 営業日日数 */
		int eigyobiSu;
	}
}
