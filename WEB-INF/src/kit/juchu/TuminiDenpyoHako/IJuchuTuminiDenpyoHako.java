package kit.juchu.TuminiDenpyoHako;

/**
 * パッケージ固有の定数を宣言するインターフェイスです<br/>
 * セッション、リクエストの格納呼出しキーは、ここで宣言します。<br/>
 * ※ 共通化できるものは、可能な限りfbパッケージ配下に記載するようにします。
 */
public interface IJuchuTuminiDenpyoHako{

	// ---------------------------------------------------
	// セッションのキー

	static final String KEY_SS_SEARCHFORM = "juchuTuminiDenpyoHako_Search";	// 検索部
	static final String KEY_SS_EDITFORM = "juchuTuminiDenpyoHako_Edit";				// 明細部

	static final String KEY_SS_SEARCHEDLIST = "JuchuTuminiDenpyoHako_SearchedList";		// 呼出リスト
	static final String KEY_SS_EDITLIST = "JuchuTuminiDenpyoHako_EditList";						// 明細リスト（受注データ／ヘッダー部）
	static final String KEY_SS_EDITLIST_HD = "JuchuTuminiDenpyoHako_EditHdList";			// 編集用リスト（積荷データ／HD部）
	static final String KEY_SS_EDITLIST_DT = "JuchuTuminiDenpyoHako_EditDtList";				// 編集用リスト（積荷データ／DT部）
	static final String KEY_SS_WEIGHT_LIST = "JuchuTuminiDenpyoHako_WeightList";	 // 運送店CD別重量計リスト

	// Listのページ当たり表示最大行数のアプリケーションリソースキー
	static final String KEY_MAX_SU = "com.juchuTuminiDenpyoHako.maxSu";

	// ---------------------------------------------------
	// 選択リストのキー

	/** 利用区分リストのキー */
	static final String KEY_SS_RIYOU_KBN = "juchuTuminiDenpyoHako_RiyouKbnList";
	/** データ種別CDリストのキー */
	static final String KEY_SS_SYUBETU_CD = "juchuTuminiDenpyoHako_SyubetuCdList";
	/** 扱い区分（略称）リストのキー */
	static final String KEY_SS_ATUKAI_KBN_SHORT = "juchuTuminiDenpyoHako_AtukaiKbnShortList";
	/** 扱い区分（正式）リストのキー */
	static final String KEY_SS_ATUKAI_KBN = "juchuTuminiDenpyoHako_AtukaiKbnList";
	/** 集約区分リストのキー */
	static final String KEY_SS_SYUYAKU_KBN = "juchuTuminiDenpyoHako_SyuyakuKbnList";
	/** 積み残しフラグリストのキー */
	static final String KEY_SS_TUMINOKOSI_FLG = "juchuTuminiDenpyoHako_TuminokosiFlgList";
	/** プリンター選択リストのキー */
	static final String KEY_SS_TARGET_PRINTER = "juchuTuminiDenpyoHako_TargetPrinterList";

	// ---------------------------------------------------
	// 画面固有の定義

	/** 呼出時の行当たりの入力項目数 */
	static final int ELEMENTS_ON_SEARCH = 3;

	/** 積荷伝票NOの桁数 */
	static final int MAX_LEN_TUMIDEN_NO = 8;
	/** 積荷伝票発行回数の桁数 */
	static final int MAX_LEN_TUMIDEN_HAKO_CNT = 3;
	/** 積荷伝票1枚に表示する商品アイテム数 */
	static final int MAX_ITEM_CNT = 30;
	/** 積荷伝票1枚に表示する受注NO数 */
	static final int MAX_JYUCYU_NO_CNT = 40;

	/** 処理分岐用 */
	public static String REQ_TYPE_CHECK = "check";
	public static String MODE_CHECK = "check";

}	// -- interface
