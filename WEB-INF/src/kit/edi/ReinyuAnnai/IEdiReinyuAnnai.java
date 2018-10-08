package kit.edi.ReinyuAnnai;


/**
 * 99902_1_1　戻入案内送信
 *
 * @author Tuneduka
 *
 */
public interface IEdiReinyuAnnai{

	// ---------------------------------------------------
	// セッションのキー
	static final String KEY_SS_SEARCHFORM = "ediReinyuAnnai_Search";	// 検索部
	static final String KEY_SS_EDITFORM = "ediReinyuAnnai_Edit";	// 明細部

	// ---------------------------------------------------
	// リクエストタイプ ＆ ジョブネット定義
	static final String REQ_TYPE_REINYUANNAIEDI = "ReinyuAnnaiEDI";    // 戻入案内 送信
	static final String JOB_TYPE_REINYUANNAIEDI_JOBNET = "JN_REINYUANNAI_REIANNAIEDI";
	static final String JOB_TYPE_REINYUANNAIEDI_MSG = "1436";

	static final String REQ_TYPE_IKKATU = "ReinyuAnnaiMake";    // 一括
	static final String JOB_TYPE_IKKATU_JOBNET = "JN_REINYUANNAI_FILEMAKE";
	static final String JOB_TYPE_IKKATU_MSG = "1437";


	// ---------------------------------------------------
	// 選択リストのキー

	/** デフォルトフラグリストのキー */
	static final String KEY_SS_DEFAULT_FL = "ediReinyuAnnai_DEFAULT_FL";
	/** 作成対象区分のキー */
	static final String KEY_SS_KBN_EDI_REINYU_MAKE = "ediReinyuAnnai_EDI_REINYU_MAKE";
	/** 送信先区分のキー */
	static final String KEY_SS_KBN_EDI_REINYU_PARTNER = "ediReinyuAnnai_EDI_REINYU_PARTNER";


}
