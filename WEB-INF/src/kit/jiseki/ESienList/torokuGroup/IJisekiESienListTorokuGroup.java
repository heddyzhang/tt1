package kit.jiseki.ESienList.torokuGroup;

/**
 * 各種登録グループ定数定義クラス
 * @author t_kimura
 *
 */
public interface IJisekiESienListTorokuGroup {

	/** セッションキー 登録グループマスタ */
	static final String KEY_SS_TOROKU_GROUP_MASTER = "jisekiESienListTorokuGroup_Master";
	/** セッションキー 検索 */
	static final String KEY_SS_SEARCH = "jisekiESienListTorokuGroup_Search";
	/** セッションキー 編集 */
	static final String KEY_SS_EDIT = "jisekiESienListTorokuGroup_Edit";

	/** 登録マスター 酒種 */
	static final String TOROKU_MASTER_SAKETANE = "1";
	/** 登録マスター 酒質 */
	static final String TOROKU_MASTER_SYUSITU = "2";
	/** 登録マスター 商品 */
	static final String TOROKU_MASTER_SYOHIN = "3";

	/** 登録パターン A */
	static final String TOROKU_PTN_A = "A";
	/** 登録パターン B */
	static final String TOROKU_PTN_B = "B";
	/** 登録パターン C */
	static final String TOROKU_PTN_C = "C";

	/** 登録No 最大数 */
	static final int TOROKU_NO_MAX = 20;

	/** XMLタグ名 */
	static final String XML_TAG_TOROKU_GROUP = "jisekiESienListTorokuGroup";
}
