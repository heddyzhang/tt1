package kit.kurac.KuraTyokuGyomu;


/**
 * 96105_1_1 蔵直業務
 *
 * @author kurosaki
 *
 */
public interface IKuraTyokuGyomu{

	// ---------------------------------------------------
	// セッションのキー
	static final String KEY_SS_SEARCHFORM = "KuraTyokuGyomu_Search";	// 検索部
	static final String KEY_SS_EDITFORM = "KuraTyokuGyomu_Edit";	// 明細部

	// ---------------------------------------------------
	// リクエストタイプ ＆ ジョブネット定義
	static final String REQ_TYPE_IKKATU = "Ikkatu";    // 一括
	static final String JOB_TYPE_IKKATU_JOBNET = "JN_KURACYOKU_IKKATU";
	static final String JOB_TYPE_IKKATU_MSG = "1208";

	static final String REQ_TYPE_NYURYOKUSYOSAI = "nyuryokuSyosai";    // 入力詳細 (UpLoad)
	static final String JOB_TYPE_NYURYOKUSYOSAI_JOBNET = "JN_KURACYOKU_NYUSYOSAI";
	static final String JOB_TYPE_NYURYOKUSYOSAI_MSG = "1209";

	static final String REQ_TYPE_KURAERRORCHECK = "KuraErrorCheck";    // 入力エラーチェック
	static final String JOB_TYPE_KURAERRORCHECK_JOBNET = "JN_KURACYOKU_NYUSYOSAI";
	static final String JOB_TYPE_KURAERRORCHECK_MSG = "1253";

	static final String REQ_TYPE_TOKURATBL = "ToKuraTBL";    // 蔵直テーブルへ登録
	static final String JOB_TYPE_TOKURATBL_JOBNET = "JN_KURACYOKU_TOKURACTBL";
	static final String JOB_TYPE_TOKURATBL_MSG = "1254";

	static final String REQ_TYPE_KURAGOKEI = "KuraGokei";    // 合計表
	static final String JOB_TYPE_KURAGOKEI_JOBNET = "JN_KURACYOKU_KURAGOKEI";
	static final String JOB_TYPE_KURAGOKEI_MSG = "1210";

	static final String REQ_TYPE_KURACL = "KuraCL";    // チェックリスト
	static final String JOB_TYPE_KURACL_JOBNET = "JN_KURACYOKU_KURACL";
	static final String JOB_TYPE_KURACL_MSG = "1212";

	static final String REQ_TYPE_KURAKENS = "KurakenS";    // 蔵直検索
	static final String JOB_TYPE_KURAKENS_JOBNET = "JN_KURACYOKU_KURAKENS";
	static final String JOB_TYPE_KURAKENS_MSG = "1213";

	static final String REQ_TYPE_KURAKENSDUMMY = "KurakenSDummy";    // 蔵直検索用更新ﾀﾞﾐｰ
	static final String JOB_TYPE_KURAKENSDUMMY_JOBNET = "JN_KURACYOKU_KURAKENSDUMMY";
	static final String JOB_TYPE_KURAKENSDUMMY_MSG = "1214";

	static final String REQ_TYPE_SYUTURYOKUSYOSAI = "syuturyokuSyosai";    // 出力詳細
	static final String JOB_TYPE_SYUTURYOKUSYOSAI_JOBNET = "JN_KURACYOKU_SYUTUSYOSAI";
	static final String JOB_TYPE_SYUTURYOKUSYOSAI_MSG = "1215";

	static final String REQ_TYPE_KURANOSI = "KuraNosi";    // のしシール
	static final String JOB_TYPE_KURANOSI_JOBNET = "JN_KURACYOKU_KURANOSI";
	static final String JOB_TYPE_KURANOSI_MSG = "1216";

	static final String REQ_TYPE_TOJYUCYUTBL = "ToJyucyuTBL";    // 受注テーブルへ追加
	static final String JOB_TYPE_TOJYUCYUTBL_JOBNET = "JN_KURACYOKU_KURANOSI";
	static final String JOB_TYPE_TOJYUCYUTBL_MSG = "1255";

	static final String REQ_TYPE_KURACANCYUSEN= "KuraCanCyusen";    // ｷｬﾝﾍﾟｰﾝ抽選ﾌｧｲﾙ
	static final String JOB_TYPE_KURACANCYUSEN_JOBNET = "JN_KURACYOKU_TOJYUCYUTBL";
	static final String JOB_TYPE_KURACANCYUSEN_MSG = "1217";


}
