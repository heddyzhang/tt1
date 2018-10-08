package kit.edi.JuchuDataJusin;


/**
 * 99905_1_1　受注データ受信
 *
 * @author Tuneduka
 *
 */
public interface IEdiJuchuDataJusin{

	// ---------------------------------------------------
	// セッションのキー
	static final String KEY_SS_SEARCHFORM = "ediJuchuDataJusin_Search";	// 検索部
	static final String KEY_SS_EDITFORM = "ediJuchuDataJusin_Edit";	// 明細部

	// ---------------------------------------------------
	// リクエストタイプ ＆ ジョブネット定義
	static final String REQ_TYPE_ONLINEEDI = "JyucyuEDI";    // オンライン受注　EDI　受信

	static final String JOB_TYPE_ONLINEEDI_JOBNET = "JN_JYUCYUDATA_ONLINEEDI";    // オンライン受注　EDI　受信
	static final String JOB_TYPE_ONLINEEDI_MSG = "1466";

//	static final String REQ_TYPE_SDNEDI = "Jyucyu905";    // オンライン受注　SDN　受信
	static final String JOB_TYPE_SDNEDI_JOBNET = "JN_JYUCYUDATA_SDNEDI";    // オンライン受注　SDN　受信
	static final String JOB_TYPE_SDNEDI_MSG = "1467";

//	static final String REQ_TYPE_FINETEDI = "Jyucyu905";    // オンライン受注　FINET　受信
	static final String JOB_TYPE_FINETEDI_JOBNET = "JN_JYUCYUDATA_FINETEDI";    // オンライン受注　FINET　受信
	static final String JOB_TYPE_FINETEDI_MSG = "1468";

	static final String REQ_TYPE_GATTAI = "JyucyuGattai";    // ファイル 合体
	static final String JOB_TYPE_GATTAI_JOBNET = "JN_JYUCYUDATA_GATTAI";
	static final String JOB_TYPE_GATTAI_MSG = "1470";

	static final String REQ_TYPE_KOKUBU = "JyucyuKokubu";    // 国分許容日
	static final String JOB_TYPE_KOKUBU_JOBNET = "JN_JYUCYUDATA_KOKUBU";
	static final String JOB_TYPE_KOKUBU_MSG = "1471";

	static final String REQ_TYPE_CHECK = "JyucyuCheck";    // チェック閲覧
	static final String JOB_TYPE_CHECK_JOBNET = "JN_JYUCYUDATA_CHECK";
	static final String JOB_TYPE_CHECK_MSG = "1472";

	static final String REQ_TYPE_KOSIN = "JyucyuHenkanKosin";    // 受注　更新
	static final String JOB_TYPE_KOSIN_JOBNET = "JN_JYUCYUDATA_HENKAN_KOSIN";
	static final String JOB_TYPE_KOSIN_MSG = "1469";

	// ---------------------------------------------------
	// 選択リストのキー

	/** デフォルトフラグリストのキー */
	static final String KEY_SS_DEFAULT_FL = "ediJuchuDataJusin_DEFAULT_FL";
	/** 受信先区分のキー */
	static final String KEY_SS_KBN_EDI_JYUCYU_PARTNER = "ediJuchuDataJusin_EDI_JYUSIN_PARTNER";



}
