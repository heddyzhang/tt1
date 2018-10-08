package kit.jiseki.ESienList;

import fb.com.IKitComConst;

/**
 * 営業支援実績一覧定数定義クラス
 * @author t_kimura
 *
 */
public interface IJisekiESienList extends IKitComConst {

	/** セッションキー 検索 */
	static final String KEY_SS_SEARCH = "jisekiESienList_Search";
	/** セッションキー 検索結果リスト */
	static final String KEY_SS_SEARCHLIST = "jisekiESienList_SearchList";
	/** セッションキー 検索結果 */
	static final String KEY_SS_EDIT = "jisekiESienList_Edit";

	/** セッションキー 対象データ（プルダウン） */
	static final String KEY_SS_KBN_JSK_SEL_DATA = "jisekiESienList_taisyoDataList";
	/** セッションキー 計算日付基準（プルダウン） */
	static final String KEY_SS_KBN_JSK_KEISAN_DATE = "jisekiESienList_keisanDateKijunList";
	/** セッションキー 自家用・小売有無（プルダウン） */
	static final String KEY_SS_KBN_JSK_OP1_UMU = "jisekiESienList_jikakouriUmuList";
	/** セッションキー 表示単位（プルダウン） */
	static final String KEY_SS_KBN_JSK_HYOJI_TANI = "jisekiESienList_hyojiTaniList";
	/** セッションキー 事業所（プルダウン） */
	static final String KEY_SS_KBN_HJS_SITEN = "jisekiESienList_jigyosyoList";
	/** セッションキー 課（プルダウン） */
	static final String KEY_SS_KBN_HJS_KA = "jisekiESienList_kaList";
	/** セッションキー 担当者（プルダウン） */
	static final String KEY_SS_KBN_JISEKI_TANTO_CD = "jisekiESienList_tantosyaList";
	/** セッションキー 都道府県（プルダウン） */
	static final String KEY_SS_KBN_COUNTY_CD = "jisekiESienList_countryList";
	/** セッションキー 全国卸（プルダウン） */
	static final String KEY_SS_KBN_TOKUYAKU_ZENKOKU = "jisekiESienList_zenkokuOrosiList";

	/** 登録No 最大数 */
	static final int TOROKU_NO_MAX = 20;

	/** 抽出条件 全社 */
	static final String CYUSYUTU_JYOKEN_KB_ZENSYA = "01";
	/** 抽出条件 事業所 */
	static final String CYUSYUTU_JYOKEN_KB_JIGYOSYO = "02";
	/** 抽出条件 課 */
	static final String CYUSYUTU_JYOKEN_KB_KA = "03";
	/** 抽出条件 担当者 */
	static final String CYUSYUTU_JYOKEN_KB_TANTO = "04";
	/** 抽出条件 都道府県 */
	static final String CYUSYUTU_JYOKEN_KB_COUNTRY = "05";
	/** 抽出条件 全国 */
	static final String CYUSYUTU_JYOKEN_KB_ZENKOKU = "06";
	/** 抽出条件 特約 */
	static final String CYUSYUTU_JYOKEN_KB_TOKUYAKU = "07";
	/** 抽出条件 送荷先 */
	static final String CYUSYUTU_JYOKEN_KB_SOUNISAKI = "08";
	/** 抽出条件 酒販店 */
	static final String CYUSYUTU_JYOKEN_KB_SYUHANTEN = "09";
	/** 抽出条件 運送店 */
	static final String CYUSYUTU_JYOKEN_KB_UNSOTEN = "10";


	/** 対象表示期間 当月・10～当月・1～当月 */
	static final String TAISYO_HYOJI_KIKAN_KB_TOUGETU = "1";
	/** 対象表示期間 指定年 */
	static final String TAISYO_HYOJI_KIKAN_KB_YEAR = "2";
	/** 対象表示期間 指定月 */
	static final String TAISYO_HYOJI_KIKAN_KB_MONTH = "3";
	/** 対象表示期間 指定日 */
	static final String TAISYO_HYOJI_KIKAN_KB_DATE = "4";

	/** 対比有無 指定なし */
	static final String TAIHI_UMU_KB_NASI = "1";
	/** 対比有無 出荷・戻入対比 */
	static final String TAIHI_UMU_KB_SYUKAREINYU = "2";
	/** 対比有無 出荷・戻入（振替除外）対比 */
	static final String TAIHI_UMU_KB_SYUKAREINYU_FURIKAE = "3";
	/** 対比有無 実績対比 */
	static final String TAIHI_UMU_KB_JISSEKI = "4";
	/** 対比有無 稼働日対比 */
	static final String TAIHI_UMU_KB_KADOBI = "5";

	/** 対比有無 予算対比 */
	static final String TAIHI_UMU_KB_YOSAN_TAIHI_ON = "on";

	/** 集計内容 事業所 */
	static final String SYUKEI_NAIYO_KB_JIGYOSYO = "01";
	/** 集計内容 課 */
	static final String SYUKEI_NAIYO_KB_KA = "02";
	/** 集計内容 担当 */
	static final String SYUKEI_NAIYO_KB_TANTO = "03";
	/** 集計内容 特約 */
	static final String SYUKEI_NAIYO_KB_TOKUYAKU = "04";
	/** 集計内容 送荷先 */
	static final String SYUKEI_NAIYO_KB_SOUNISAKI = "05";
	/** 集計内容 全国 */
	static final String SYUKEI_NAIYO_KB_ZENKOKU = "06";
	/** 集計内容 都道府県 */
	static final String SYUKEI_NAIYO_KB_COUNTRY = "07";
	/** 集計内容 輸出国 */
	static final String SYUKEI_NAIYO_KB_YUSYUTU = "08";
	/** 集計内容 容器材質 */
	static final String SYUKEI_NAIYO_KB_YOUKIZAISITU = "09";
	/** 集計内容 酒種 */
	static final String SYUKEI_NAIYO_KB_SAKADANE = "10";
	/** 集計内容 酒質 */
	static final String SYUKEI_NAIYO_KB_SAKESITU = "11";
	/** 集計内容 商品 */
	static final String SYUKEI_NAIYO_KB_SYOHIN = "12";

	/** 集計内容OP 酒質 */
	static final String SYUKEI_NAIYO_OP_KB_SAKESITU_ON = "on";
	/** 集計内容OP 充填容器 */
	static final String SYUKEI_NAIYO_OP_KB_JUTENYOUKI_ON  = "on";

	/** XMLタグ名 */
	static final String XML_TAG = "jisekiESienList";
}
