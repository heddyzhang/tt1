package kit.edi.SdnCenterCode;


/**
 * 99911_1_1　SDNｾﾝﾀｰｺｰﾄﾞ送受信
 *
 * @author Tuneduka
 *
 */
public interface IEdiSdnCenterCode{

	// ---------------------------------------------------
	// セッションのキー
	static final String KEY_SS_SEARCHFORM = "ediSdnCenterCode_Search";	// 検索部
	static final String KEY_SS_EDITFORM = "ediSdnCenterCode_Edit";	// 明細部

	// ---------------------------------------------------
	// リクエストタイプ ＆ ジョブネット定義
	static final String REQ_TYPE_OROSISENTACD = "OrosiSentaCD";    // 卸店センターコード ファイル作成
	static final String JOB_TYPE_OROSISENTACD_JOBNET = "JN_SDNSENTACD_OROSISENTACD";
	static final String JOB_TYPE_OROSISENTACD_MSG = "1493";

	static final String REQ_TYPE_OROSISENTAEDI = "OrosiSentaEDI";    // センターコード EDI　登録
	static final String JOB_TYPE_OROSISENTAEDI_JOBNET = "JN_SDNSENTACD_OROSISENTAEDI";
	static final String JOB_TYPE_OROSISENTAEDI_MSG = "1494";

	static final String REQ_TYPE_ROGUEDI = "RoguEDI";    // ログ　EDI　受信
	static final String JOB_TYPE_ROGUEDI_JOBNET = "JN_SDNSENTACD_ROGUEDI";
	static final String JOB_TYPE_ROGUEDI_MSG = "1495";

	static final String REQ_TYPE_ROGUFILE = "RoguFILE";    // 　ログ ファイル作成　
	static final String JOB_TYPE_ROGUFILE_JOBNET = "N_SDNSENTACD_ROGUFILE";
	static final String JOB_TYPE_ROGUFILE_MSG = "1496";

	static final String MI_SOUSIN = "1";			// 未送信
	static final String SOSIN_ZUMI = "2";			// 送信済み
	static final String EDA_NO = "1";				// 枝番号

}
