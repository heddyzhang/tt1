package kit.edi.EdiJuchuMainte;

public class IEdiJuchuMainte {

	// ---------------------------------------------------
	// セッションのキー
	static final String KEY_SS_SEARCHFORM = "ediJuchuMainte_Search";			// 検索部
	static final String KEY_SS_EDITFORM = "ediJuchuMainte_Edit";				// 明細部
	static final String KEY_SS_SEARCHEDLIST = "ediJuchuMainte_SearchedList";	// 検索結果リスト
	static final String KEY_SS_EDITLIST = "ediJuchuMainte_EditList";			// 編集用リスト（ヘッダー部）
	static final String KEY_SS_INITLIST = "ediJuchuMainte_InitList";			// 初期リスト（ヘッダー部）
	static final String KEY_SS_EDITLIST_DT = "ediJuchuMainte_EditDtList";	// 編集用リスト（ディテール部）
	static final String KEY_SS_INITLIST_DT = "ediJuchuMainte_InitDtList";	// 初期リスト（ディテール部）

	// ---------------------------------------------------
	// 選択リストのキー

	/** 処理区分リストのキー */
	static final String KEY_SS_SHORI_KBN = "ediJuchuMainte_ShoriKbnList";
	/** 出荷日注意リストのキー */
	static final String KEY_SS_SYUK_CAREF	="ediJuchuMainte_SyukCarefList";
	/** データ有無のキー */
	static final String KEY_SS_DATA_ARINASHI	="ediJuchuMainte_DataAriNashiList";
	/** 納品時間区分のキー */
	static final String KEY_SS_NOHIN_JIKAN	="ediJuchuMainte_NohinJikanList";
	/** 蔵入/直送区分のキー */
	static final String KEY_SS_KURA_CYOKUSO	="ediJuchuMainte_KuraCyokusoList";
	/** 重複区分のキー */
	static final String KEY_SS_ARI_NASI ="ediJuchuMainte_AriNasiList";
	/** 単位区分のキー */
	static final String KEY_SS_EDI_TANI = "ediJuchuMainte_EdiTaniList";
	// ---------------------------------------------------

	// 画面固有の定義

	/** 修正時の行当たりの入力項目数 */
	static final int ELEMENTS_ON_EDIT = 3;		// ヘッダー部
	static final int ELEMENTS_ON_EDIT_DT = 3;	// ディテール部

	/** 各項目の最大文字列長(バイト換算) */
	static final int MAX_LEN_EDI_JYUHACYU_ID = 15;	// 入力連番
	static final int MAX_LEN_CASE_SU = 5;			// ケース数
	static final int MAX_LEN_BARA_SU = 5;			// バラ数


	/** その他定数 */
	static final String XML_TAG = "edijuchumainte";
	/** 摘要欄文字最後から3文字  */
	static final String TEKIYO_LAST_SANMOJI = "ｲｺｳ";

	static final String REQ_TYPE_JYUCYUDATA_KOSIN = "JyucyuDataKosin";    // 受注データ
	static final String JOB_TYPE_JYUCYUDATA_KOSIN_JOBNET = "JN_JYUCYUDATA_KOSIN";
	static final String JOB_TYPE_JYUCYUDATA_KOSIN_MSG = "1470";

}
