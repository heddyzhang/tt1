package kit.kurac.KuraChokuDataIn;

import fb.inf.pbs.PbsUtil;


/**
 * パッケージ固有の定数を宣言するインターフェイスです<br/>
 * セッション、リクエストの格納呼出しキーは、ここで宣言します。<br/>
 * ※ 共通化できるものは、可能な限りfbパッケージ配下に記載するようにします。
 */
public interface IKuracKuraChokuDataIn{

	// ---------------------------------------------------
	// セッションのキー
	static final String KEY_SS_SEARCHFORM = "kuracKuraChokuDataIn_Search";			// 検索部
	static final String KEY_SS_EDITFORM = "kuracKuraChokuDataIn_Edit";				// 明細部
	static final String KEY_SS_SEARCHEDLIST = "kuracKuraChokuDataIn_SearchedList";	// 明細リスト
	static final String KEY_SS_EDITLIST = "kuracKuraChokuDataIn_EditList";			// 編集用リスト（ヘッダー部）
	static final String KEY_SS_INITLIST = "kuracKuraChokuDataIn_InitList";			// 初期リスト（ヘッダー部）
	static final String KEY_SS_EDITLIST_DT = "kuracKuraChokuDataIn_EditDtList";		// 編集用リスト（ディテール部）
	static final String KEY_SS_INITLIST_DT = "kuracKuraChokuDataIn_InitDtList";		// 初期リスト（ディテール部）

	// ---------------------------------------------------
	// 選択リストのキー

	/** 利用状態区分（フラグ）リストのキー */
	static final String KEY_SS_RIYOU_JOUTAI_KBN = "kuracKuraChokuDataIn_RiyouJoutaiKbnList";
	/** 利用区分リストのキー */
	static final String KEY_SS_RIYOU_KBN = "kuracKuraChokuDataIn_RiyouKbnList";
	/** 事業所リストのキー */
	static final String KEY_SS_KURAC_SITEN = "kuracKuraChokuDataIn_KuracSitenList";
	/** 商品ｸﾞﾙｰﾌﾟリストのキー */
	static final String KEY_SS_KURAC_SHOHINGRP= "kuracKuraChokuDataIn_KuracShohingrpList";
	/** 運賃リストのキー */
	static final String KEY_SS_UNTIN = "kuracKuraChokuDataIn_UntinList";
	/** 宅配伝票区分リストのキー */
	static final String KEY_SS_KURAC_BILL = "kuracKuraChokuDataIn_KuracBillList";
	/** ﾃﾞｰﾀ種別リストのキー */
	static final String KEY_SS_KURAC_DATA = "kuracKuraChokuDataIn_KuracDataList";
	/** のし用途リストのキー */
	static final String KEY_SS_KURAC_NOSIYOUTO = "kuracKuraChokuDataIn_KuracNosiYoutoList";
	/** のし用途リストのキー */
	static final String KEY_SS_KURAC_NOSINAME_YN = "kuracKuraChokuDataIn_KuracNosiNameYnList";
	/** 酒販店chkﾘｽﾄ打出区分リストのキー */
	static final String KEY_SS_KURAC_CHKSLIST_YN = "kuracKuraChokuDataIn_KuracChklistYnList";
	/** 届先Noリストのキー */
	static final String KEY_SS_KURAC_UPLOAD_FLG = "kuracKuraChokuDataIn_KuracUploadFlgList";

	// ---------------------------------------------------
	// 画面固有の定義

	/** 新規時の行当たりの入力項目数 */
	static final int ELEMENTS_ON_ADD = 22;		// ヘッダー部
	static final int ELEMENTS_ON_ADD_DT = 2;	// ディテール部

	/** 修正時の行当たりの入力項目数 */
	static final int ELEMENTS_ON_EDIT = 22;		// ヘッダー部
	static final int ELEMENTS_ON_EDIT_DT = 2;	// ディテール部

	/** 参照追加時の行当たりの入力項目数 */
	static final int ELEMENTS_ON_REFERENCE = 26;	// ヘッダー部
	static final int ELEMENTS_ON_REFERENCE_DT = 2;	// ディテール部

	/** 訂正(+)時の行当たりの入力項目数 */
	static final int ELEMENTS_ON_REFPLUS = 19;	// ヘッダー部
	static final int ELEMENTS_ON_REFPLUS_DT = 2;	// ディテール部

	/** 訂正(-)時の行当たりの入力項目数 */
	static final int ELEMENTS_ON_REFMINUS = 19;	// ヘッダー部
	static final int ELEMENTS_ON_REFMINUS_DT = 2;	// ディテール部

	// TIPS:商品コード、得意先コードなど頻繁に使用するコードの属性値は、
	// 共通定数化します。NSHでは、厳密な切り分けをしていません。印象で分けました。

	/** 各項目の最大文字列長(バイト換算) */
	static final int MAX_LEN_KURADATA_NO = 8;		// 蔵直データ連番
	static final int MAX_LEN_MOSHIKOMI_NO = 8;		// 申込受付NO
	static final int MAX_LEN_SOUNISKI_NM = 24;		// 送荷先名
	static final int MAX_LEN_NOSHI_COMMENT = 8;		// のし名
	static final int MAX_LEN_SYUHANTEN_NM = 20;		// 酒販店名
	static final int MAX_LEN_IRAISYU_NM = 28;		// 依頼主名
	static final int MAX_LEN_TODOKESAKI_NM = 28;	// 届け先名
	static final int MAX_LEN_IRAISYU_ADRESS = 42;	// 依頼主住所
	static final int MAX_LEN_TODOKESAKI_ADRESS =42;	// 届け先住所
	static final int MAX_LEN_JYUCYU_NO = 8;			// 受注NO
	static final int MAX_LEN_HACYU_NO = 10;			// 発注No.
	static final int MAX_LEN_KURADEN_NO = 4;		// 整理No.
	static final int MAX_LEN_SET_SU = 3;			// セット数
	static final int MAX_LEN_TANKA = 10;			// 単価

	/** 削除済みのマーク */
	static final String CST_DEL_FLG_MARK = PbsUtil.getMessageResourceString("com.text.delFlg.mark");	// ●

	/** その他定数 */

	static final String XML_TAG = "kuraChokuDataIn";

	/** 売上伝票の１ページあたりの行数 */
	static final int MAX_LINE_URIDEN = 7;
	/** 売上伝票NOの最大桁数 */
	static final int MAX_LEN_URIDEN_NO = 8;


}	// -- interface
