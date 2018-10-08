package kit.edi.SyuhantenReceiveUpdate;


/**
 * 99915_1_1　酒販店ﾏｽﾀｰ受信更新
 *
 * @author Tuneduka
 *
 */
public interface IEdiSyuhantenReceiveUpdate{

	// ---------------------------------------------------
	// セッションのキー
	static final String KEY_SS_SEARCHFORM = "ediSyuhantenReceiveUpdate_Search";	// 検索部
	static final String KEY_SS_EDITFORM = "ediSyuhantenReceiveUpdate_Edit";	// 明細部

	// ---------------------------------------------------
	// リクエストタイプ ＆ ジョブネット定義
	static final String REQ_TYPE_SYUHANTENEDI = "SyuhantenEDI";    // 酒販店マスター　EDI　受信
	static final String JOB_TYPE_SYUHANTENEDI_JOBNET = "JN_SYUHANTENMST_SYUHANTENEDI";
	static final String JOB_TYPE_SYUHANTENEDI_MSG = "1489";

	static final String REQ_TYPE_IKKATU = "Ikkatu";    // 一括
	static final String JOB_TYPE_IKKATU_JOBNET = "JN_SYUHANTENMST_IKKATU";
	static final String JOB_TYPE_IKKATU_MSG = "1490";

	static final String REQ_TYPE_SYUHANTENKOUSIN = "SyuhantenKousin";    // 酒販店マスター更新
	static final String JOB_TYPE_SYUHANTENKOUSIN_JOBNET = "JN_SYUHANTENMST_SYUHANTENUD";
	static final String JOB_TYPE_SYUHANTENKOUSIN_MSG = "1491";

	static final String REQ_TYPE_EISIENFILE = "EisienFILE";    // 営業支援用ファイル
	static final String JOB_TYPE_EISIENFILE_JOBNET = "JN_SYUHANTENMST_EISIENFILE";
	static final String JOB_TYPE_EISIENFILE_MSG = "1492";

}
