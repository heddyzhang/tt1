package kit.edi.SyukkaAnnai;


/**
 * 99901_1_1　出荷案内送信
 *
 * @author Tuneduka
 *
 */
public interface IEdiSyukkaAnnai{

	// ---------------------------------------------------
	// セッションのキー
	static final String KEY_SS_SEARCHFORM = "ediSyukkaAnnai_Search";	// 検索部
	static final String KEY_SS_EDITFORM = "ediSyukkaAnnai_Edit";	// 明細部

	// ---------------------------------------------------
	// リクエストタイプ ＆ ジョブネット定義
	static final String REQ_TYPE_SYUKKAANNAIEDI = "SyukkaAnnaiEDI";    // 出荷案内 送信
	static final String JOB_TYPE_SYUKKAANNAIEDI_JOBNET = "JN_SYUKKAANNAI_SYUANNAIEDI";
	static final String JOB_TYPE_SYUKKAANNAIEDI_MSG = "1401";

//	static final String REQ_TYPE_SYUKKASDNEDI = "SyukkaSdnEDI";	// SDN 送信
	static final String JOB_TYPE_SYUKKASDNEDI_JOBNET = "JN_SYUKKAANNAI_SYUSDNEDI";
	static final String JOB_TYPE_SYUKKASDNEDI_MSG = "1402";

//	static final String REQ_TYPE_SYUKKAFINETEDI = "SyukkaFinetEDI";	// FINET 送信
	static final String JOB_TYPE_SYUKKAFINETEDI_JOBNET = "JN_SYUKKAANNAI_SYUFINETEDI";
	static final String JOB_TYPE_SYUKKAFINETEDI_MSG = "1403";

	static final String REQ_TYPE_IKKATU = "SyukkaAnnaiMake";    // 一括
	static final String JOB_TYPE_IKKATU_JOBNET = "JN_SYUKKAANNAI_FILEMAKE";
	static final String JOB_TYPE_IKKATU_MSG = "1404";

	// ---------------------------------------------------
	// 選択リストのキー

	/** デフォルトフラグリストのキー */
	static final String KEY_SS_DEFAULT_FL = "ediSyukkaAnnai_DEFAULT_FL";
	/** 作成対象区分のキー */
	static final String KEY_SS_KBN_EDI_SYUKKA_MAKE = "ediSyukkaAnnai_EDI_SYUKKA_MAKE";
	/** 送信先区分のキー */
	static final String KEY_SS_KBN_EDI_SYUKKA_PARTNER = "ediSyukkaAnnai_EDI_SYUKKA_PARTNER";


}
