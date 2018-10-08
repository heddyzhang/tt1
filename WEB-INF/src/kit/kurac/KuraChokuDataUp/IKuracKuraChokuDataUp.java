package kit.kurac.KuraChokuDataUp;


/**
 *
 *
 * @author Tuneduka
 *
 */
public interface IKuracKuraChokuDataUp{

	// ---------------------------------------------------
	// セッションのキー
	static final String KEY_SS_SEARCHFORM = "kuracKuraChokuDataUp_Search";	// 検索部
	static final String KEY_SS_EDITFORM = "kuracKuraChokuDataUp_Edit";	// 明細部

	// ---------------------------------------------------
	// リクエストタイプ ＆ ジョブネット定義
	static final String REQ_TYPE_KURACCSVMAKE = "KuracCsvmake";    // 作成

	// 発送通知書、請求明細書
	static final String JOB_TYPE_HASSOTUCHI_JOBNET = "JN_KURACYOKU_KURACL";
	static final String JOB_TYPE_HASSOTUCHI_MSG = "1212";

	// 出荷商品合計書
	static final String JOB_TYPE_GOUKEIHYO_JOBNET = "JN_KURACYOKU_KURAGOKEI";
	static final String JOB_TYPE_GOUKEIHYO_MSG = "1210";

	// 部署別売上集計（小売のみ）
	static final String JOB_TYPE_BUSYOSYUKEI_JOBNET = "JN_URIAGENIPPO_KURAKOSE";
	static final String JOB_TYPE_BUSYOSYUKEI_MSG = "1549";

	// のしシール
	static final String JOB_TYPE_NOSISEAL_JOBNET = "JN_KURACYOKU_KURANOSI";
	static final String JOB_TYPE_NOSISEAL_MSG = "1216";

	// ｷｬﾝﾍﾟｰﾝ抽選ﾌｧｲﾙ
	static final String JOB_TYPE_CAMPAIGN_JOBNET = "JN_KURACYOKU_KURACANCYUSEN";
	static final String JOB_TYPE_CAMPAIGN_MSG = "1217";

	// ---------------------------------------------------
	// 選択リストのキー

	/** デフォルトフラグリストのキー */
	static final String KEY_SS_DEFAULT_FL = "KuracKuraChokuDataUp_DEFAULT_FL";
	/** 作成対象区分のキー */
	static final String KEY_SS_KBN_KURAC_CSVMAKE = "kuracKuraChokuDataUp_KuracCsvmake";


}
