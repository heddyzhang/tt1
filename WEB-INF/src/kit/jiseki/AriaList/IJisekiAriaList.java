package kit.jiseki.AriaList;

import fb.com.IKitComConst;

/**
 * パッケージ固有の定数を宣言するインターフェイスです<br/>
 * セッション、リクエストの格納呼出しキーは、ここで宣言します。<br/>
 * ※ 共通化できるものは、可能な限りfbパッケージ配下に記載するようにします。
 */
public interface IJisekiAriaList extends IKitComConst {

	// ---------------------------------------------------
	// セッションのキー
	static final String KEY_SS_SEARCHFORM = "jisekiAriaList_Search";						// 検索部
	static final String KEY_SS_SEARCHEDJGYOSYOLIST = "jisekiAriaJigyosyo_SearchList";		// 明細リスト(事業所レベル)
	static final String KEY_SS_SEARCHEDKALIST = "jisekiAriaKa_SearchList";					// 明細リスト(課レベル)

	/** 表示単位（画面固有）*/
	static final String RDO_TANI_KINGAKU = "1";	// 千円
	static final String RDO_TANI_KOKUSU = "2";	// 石数


	/** 集計期間（画面固有）*/
	static final String SYUKEI_KIKAN_TOGETSU = "0";		// 当月
	static final String SYUKEI_KIKAN_JYUGATSU = "1";	// 10月累計
	static final String SYUKEI_KIKAN_ICHIGATSU= "2";	// 1月累計

	/** アリア区分（画面固有）*/
	static final String AREA_KBN_HIGASHINIHON = "A";	//東日本
	static final String AREA_KBN_NISHINIHON = "B";		//西日本
	static final String AREA_KBN_KOURI= "C";			//小売
	static final String AREA_KBN_MINOZEI= "D";			//未納税
	static final String AREA_KBN_KA= "09";				//課レベル

	/** 合計行のタイトル */
	static final String HIGASHI_NIHON_KEI = "―東日本計―";
	static final String NISHI_NIHON_KEI = "―西日本計―";
	static final String SHITEN_GOKEI ="支店営業合計";
	static final String GOKEI = "（合計）";
	static final String SOGOKEI = "【総合計】";

	/** 検索文区分 */
	static final String SEARCH_KBN_SITEN = "0";			//事業所レベル検索
	static final String SEARCH_KBN_KA = "1";			//課レベル検索

	/** その他）*/
	static final String FIRST_DAY_OF_MONTH = "01";			//初日
	static final String FIRST_DAY_OF_ICHIGATSU = "0101";	//年初日

	// 検索部
	static final int MAX_SU_SITEN_LIST  = 18;			// 支店合計表示最大行数
	static final int MAX_SU_KA_LIST  = 8;				// 課表示最大行数
}	// -- interface


