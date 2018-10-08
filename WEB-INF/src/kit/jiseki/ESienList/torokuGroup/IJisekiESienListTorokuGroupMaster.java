package kit.jiseki.ESienList.torokuGroup;

import java.util.List;

import fb.com.ComUserSession;
import kit.jiseki.ESienList.JisekiESienList_CheckService;

/**
 * 登録グループマスタインターフェース
 * @author t_kimura
 *
 */
public interface IJisekiESienListTorokuGroupMaster {

	/**
	 * タイトル取得
	 * @return タイトル
	 */
	public String getTitle();

	/**
	 * 検索SQL取得
	 * @param comUserSession ログインユーザセッション情報
	 * @param searchForm 検索フォーム
	 * @param bindList バインドリスト
	 * @return 検索SQL
	 */
	public String getSearchSql(ComUserSession comUserSession, JisekiESienListTorokuGroup_SearchForm searchForm, List<String> bindList);

	/**
	 * 登録コードチェック処理
	 * @param checkService チェックサービス
	 * @param comUserSession ログインユーザセッション情報
	 * @param torokuCd 登録コード
	 * @param index インデックス
	 * @param parent 親画面か子画面かのフラグ
	 * @return チェック結果
	 */
	public boolean checkTorokuCd(JisekiESienList_CheckService checkService, ComUserSession comUserSession, String torokuCd, int index, boolean parent);

	/**
	 * Ajax用のXMLタグを作成する
	 * @param checkService チェックサービス
	 * @param comUserSession ログインユーザセッション情報
	 * @param torokuCd 登録コード
	 * @return XMLタグ
	 */
	public String createXmlTagByTorokuCd(JisekiESienList_CheckService checkService, ComUserSession comUserSession, String torokuCd);

	/**
	 * 登録名設定
	 * @param checkService チェックサービス
	 * @param comUserSession ログインユーザセッション情報
	 * @param torokuGroupList 登録グループリスト
	 */
	public void setTorokuNms(JisekiESienList_CheckService checkService, ComUserSession comUserSession, JisekiESienListTorokuGroupList torokuGroupList);
}
