package kit.juchu.JuchuAddOnly;

import fb.inf.pbs.PbsUtil;


/**
 * パッケージ固有の定数を宣言するインターフェイスです<br/>
 * セッション、リクエストの格納呼出しキーは、ここで宣言します。<br/>
 * ※ 共通化できるものは、可能な限りfbパッケージ配下に記載するようにします。
 */
public interface IJuchuJuchuAddOnly{

	// ---------------------------------------------------
	// セッションのキー
	static final String KEY_SS_SEARCHFORM = "juchuJuchuAddOnly_Search";			// 検索部
	static final String KEY_SS_EDITFORM = "juchuJuchuAddOnly_Edit";				// 明細部
	static final String KEY_SS_SEARCHEDLIST = "JuchuJuchuAddOnly_SearchedList";	// 明細リスト
	static final String KEY_SS_EDITLIST = "JuchuJuchuAddOnly_EditList";			// 編集用リスト（ヘッダー部）
	static final String KEY_SS_INITLIST = "JuchuJuchuAddOnly_InitList";			// 初期リスト（ヘッダー部）
	static final String KEY_SS_EDITLIST_DT = "JuchuJuchuAddOnly_EditDtList";		// 編集用リスト（ディテール部）
	static final String KEY_SS_INITLIST_DT = "JuchuJuchuAddOnly_InitDtList";		// 初期リスト（ディテール部）
	static final String KEY_SS_CAUTIONLIST = "JuchuJuchuAddOnly_CautionList";		// 注意商品リスト
	static final String KEY_SS_INITLIST_CAT = "JuchuJuchuAddOnly_InitCatList";	// 初期リスト（カテゴリデータ）

	// ---------------------------------------------------
	// 選択リストのキー

	/** 利用状態区分（フラグ）リストのキー */
	static final String KEY_SS_RIYOU_JOUTAI_KBN = "juchuJuchuAddOnly_RiyouJoutaiKbnList";
	/** 利用区分リストのキー */
	static final String KEY_SS_RIYOU_KBN = "juchuJuchuAddOnly_RiyouKbnList";
	/** 都道府県リストのキー */
	static final String KEY_SS_TODOFUKN = "juchuJuchuAddOnly_TodofuknList";
	/** 識別（データ種別CD）リストのキー */
	static final String KEY_SS_SIKIBETU = "juchuJuchuAddOnly_SikibetuList";
	/** 識別（データ種別CDの一部）リストのキー */
	static final String KEY_SS_SIKIBETU_LIMIT = "juchuJuchuAddOnly_SikibetuLimitList";
	/** 運賃リストのキー */
	static final String KEY_SS_UNTIN = "juchuJuchuAddOnly_UntinList";
	/** 出荷場所リストのキー */
	static final String KEY_SS_SYUKA_BASYO = "juchuJuchuAddOnly_SyukaBasyoList";
	/** 荷受区分リストのキー */
	static final String KEY_SS_NIUKE_KBN = "juchuJuchuAddOnly_NiukeKbnList";
	/** 出荷対応（略称）リストのキー */
	static final String KEY_SS_SYUKA_TAIO_SHORT = "juchuJuchuAddOnly_SyukaTaioShortList";
	/** 出荷対応（正式）リストのキー */
	static final String KEY_SS_SYUKA_TAIO = "juchuJuchuAddOnly_SyukaTaioList";
	/** 扱い区分（略称）リストのキー */
	static final String KEY_SS_ATUKAI_KBN_SHORT = "juchuJuchuAddOnly_AtukaiKbnShortList";
	/** 扱い区分（正式）リストのキー */
	static final String KEY_SS_ATUKAI_KBN = "juchuJuchuAddOnly_AtukaiKbnList";
	/** 縦線卸店リストのキー */
	static final String KEY_SS_ORS_TATESEN = "juchuJuchuAddOnly_OrsTatesenList";
	/** 倉庫種類区分リストのキー */
	static final String KEY_SS_SOUKO_SYURUI_KBN = "juchuJuchuAddOnly_SoukoSyuruiKbnList";
	/** 出荷先区分（略称）リストのキー */
	static final String KEY_SS_SYUKA_KBN_SHORT = "juchuJuchuAddOnly_SyukaKbnShortList";
	/** 出荷先区分（正式）リストのキー */
	static final String KEY_SS_SYUKA_KBN = "juchuJuchuAddOnly_SyukaKbnList";
	/** 倉入・直送区分リストのキー */
	static final String KEY_SS_DELIVERY_KBN = "juchuJuchuAddOnly_DeliveryKbnList";

	// ---------------------------------------------------
	// 画面固有の定義

	/** 新規時の行当たりの入力項目数 */
	static final int ELEMENTS_ON_ADD = 2;		// ヘッダー部
	static final int ELEMENTS_ON_ADD_DT = 3;	// ディテール部

	/** 修正時の行当たりの入力項目数 */
	static final int ELEMENTS_ON_EDIT = 2;		// ヘッダー部
	static final int ELEMENTS_ON_EDIT_DT = 3;	// ディテール部

	/** 参照追加時の行当たりの入力項目数 */
	static final int ELEMENTS_ON_REFERENCE = 2;	// ヘッダー部
	static final int ELEMENTS_ON_REFERENCE_DT = 3;	// ディテール部

	// TIPS:商品コード、得意先コードなど頻繁に使用するコードの属性値は、
	// 共通定数化します。NSHでは、厳密な切り分けをしていません。印象で分けました。

	/** 各項目の最大文字列長 */
	static final int MAX_LEN_JYUCYU_NO = 8;			// 受注NO
	static final int MAX_LEN_TANTO_CD = 6;			// 担当者CD
	static final int MAX_LEN_HACYU_NO = 10;			// 発注No.
	static final int MAX_LEN_SYUKA_SYONIN_NO = 12;	// 小ロット出荷承認申請No.
	static final int MAX_LEN_MINASI = 8;				// ミナシ
	static final int MAX_LEN_TEKIYO_KBN = 3;		// 摘要区分１～５
	static final int MAX_LEN_TEKIYO_NAIYO = 12;		// 摘要内容１～５
	static final int MAX_LEN_SYUKA_HAKO_SU = 5;		// 出荷数量_箱数
	static final int MAX_LEN_SYUKA_SET_SU = 3;		// 出荷数量_セット数
	static final int MAX_LEN_TANKA = 10;				// 単価
	static final int MAX_LEN_TANKA_INT = 7;			// 単価（整数部）
	static final int MAX_LEN_TANKA_DEC = 2;			// 単価（小数部）

	/** 削除済みのマーク */
	static final String CST_DEL_FLG_MARK = PbsUtil.getMessageResourceString("com.text.delFlg.mark");	// ●

	/** その他定数 */
	static final String NO_VALUE = "-";				// -

	static final String HTANKA_CHG_INP_TRUE = "true";   // 販売単価変更入力フラグ（変更する）
	static final String HTANKA_CHG_INP_FALSE = "false"; // 販売単価変更入力フラグ（変更しない）

	static final String TOKUCYU_NASI = "1"; // 特注指示区分（指示なし）
	static final String TOKUCYU_ARI = "2"; // 特注指示区分（指示あり）

	static final String ZIK_DT_END = "1"; // 対象日取得期間

}	// -- interface
