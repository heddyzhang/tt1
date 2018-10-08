package kit.jiseki.Nenpou;

import fb.com.IKitComConst;
import fb.inf.pbs.PbsUtil;

/**
 * パッケージ固有の定数を宣言するインターフェイスです<br/>
 * セッション、リクエストの格納呼出しキーは、ここで宣言します。<br/>
 * ※ 共通化できるものは、可能な限りfbパッケージ配下に記載するようにします。
 *
 * @author nishikawa@kz
 * @version 2015/11/12
 *
 */
public interface IJisekiNenpou extends IKitComConst {

	// ---------------------------------------------------
	// セッションのキー
	static final String KEY_SS_SEARCHFORM = "jisekiNenpou_Search";			// 検索部
	static final String KEY_SS_EDITFORM = "jisekiNenpou_Edit";				// 明細部
	static final String KEY_SS_SEARCHEDLIST = "JisekiNenpou_SearchedList";	// 明細リスト
	static final String KEY_SS_INITLIST = "JisekiNenpou_InitList";			// 初期リスト
	static final String KEY_SS_EDITLIST = "JisekiNenpou_EditList";			// 編集用明細リスト




	// ---------------------------------------------------
	// 選択リストのキー
	/** 事業所 */
	static final String KEY_SS_KBN_HJS_SITEN = "jisekiNenpou_HjsSitenList";
	/** 課 */
	static final String KEY_SS_KBN_HJS_KA = "jisekiNenpou_HjsKaList";
	/** 都道府県 */
	static final String KEY_SS_KBN_COUNTY_CD = "jisekiNenpou_CountyCdList";

	/** 担当者(TBL販売実績担当区分マスター) */
	static final String KEY_SS_KBN_TANTO_CD = "jisekiNenpou_TantoCdList";
	/** 輸出国(TBL出荷先国マスター) */
	static final String KEY_SS_KBN_SYUKA_SAKI_COUNTRY = "jisekiNenpou_SyukaSakiCountryList";

	/** 全国卸 */
	static final String KEY_SS_KBN_TOKUYAKU_ZENKOKU = "jisekiNenpou_TokuyakuZenkokuList";

	// 画面固有の選択リストのキー
	/** 期間 */
	static final String KEY_SS_KBN_SEL_NEN = "jisekiNenpou_SelNenList";
	/** データ */
	static final String KEY_SS_KBN_SEL_DATA = "jisekiNenpou_SelDataList";
	/** 集権単位 */
	static final String KEY_SS_KBN_SEL_SYUKEI = "jisekiNenpou_SelSyukeiList";



	/** 期間（画面固有）*/
	static final String SEL_NEN_TOUNEN = "1"; // 当年のみ
	static final String SEL_NEN_TOUNEN_ZENNEN = "2"; // 当年前年

	/** データ（画面固有）*/
	static final String SEL_DATA_SYUKA = "1"; // 出荷
	static final String SEL_DATA_REINYU = "2"; // 戻入

	/** 集計単位（画面固有）*/
	static final String SEL_SYUKEI_KINGAKU = "1"; // 金額
	static final String SEL_SYUKEI_HONSU = "2"; // 本数
	static final String SEL_SYUKEI_YOURYO = "3"; // 容量



	/** 各項目の最大文字列長(バイト換算) */
	// 検索部
	static final int MAX_LEN_KIKAN  = 6;					// 期間日付
	static final int MAX_LEN_NEN4KETA  = 4;				// 期間日付4桁NG

	/** 表示フォーマット */
	static final String FORMAT_KINGAKU = "999,999,999"; // 金額
	static final String FORMAT_HONSU = "999,999,999"; // 本数
	static final String FORMAT_YOURYO = "99,999,990.000"; // 容量


}	// -- interface
