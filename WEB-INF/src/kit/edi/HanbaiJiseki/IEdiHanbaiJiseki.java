package kit.edi.HanbaiJiseki;


/**
 * 99903_1_1　販売実績受信
 *
 * @author Tuneduka
 *
 */
public interface IEdiHanbaiJiseki{

	// ---------------------------------------------------
	// セッションのキー
	static final String KEY_SS_SEARCHFORM = "ediHanbaiJiseki_Search";	// 検索部
	static final String KEY_SS_EDITFORM = "ediHanbaiJiseki_Edit";	// 明細部

	// ---------------------------------------------------
	// リクエストタイプ ＆ ジョブネット定義
	static final String REQ_TYPE_ITOCYUEDI = "HanbaiItocyuEDI";    // 伊藤忠　販売実績　EDI　受信
	static final String JOB_TYPE_ITOCYUEDI_JOBNET = "JN_HANBAI_ITOCYUEDI";
	static final String JOB_TYPE_ITOCYUEDI_MSG = "1461";

	static final String REQ_TYPE_ITOCYUFILE = "HanbaiItocyuFILE";    // 伊藤忠販売実績データ格納
	static final String JOB_TYPE_ITOCYUFILE_JOBNET = "JN_HANBAI_ITOCYUFILE";
	static final String JOB_TYPE_ITOCYUFILE_MSG = "1462";

	static final String REQ_TYPE_OKAMURAEDI = "HanbaiOkamuraEDI";    // 岡村　販売実績　EDI　受信
	static final String JOB_TYPE_OKAMURAEDI_JOBNET = "JN_HANBAI_OKAMURAEDI";
	static final String JOB_TYPE_OKAMURAEDI_MSG = "1463";

	static final String REQ_TYPE_OKAMURAPBCK = "HanbaiOkamuraPBCK";    // 岡村　PBコードチェック
	static final String JOB_TYPE_OKAMURAPBCK_JOBNET = "JN_HANBAI_OKAMURAPBCK";
	static final String JOB_TYPE_OKAMURAPBCK_MSG = "1464";

	static final String REQ_TYPE_OKAMURAFILE = "HanbaiOkamuraFILE";    // 岡村販売実績データ格納
	static final String JOB_TYPE_OKAMURAFILE_JOBNET = "JN_HANBAI_OKAMURAFILE";
	static final String JOB_TYPE_OKAMURAFILE_MSG = "1465";

}
