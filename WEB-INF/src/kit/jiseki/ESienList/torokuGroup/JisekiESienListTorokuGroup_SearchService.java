package kit.jiseki.ESienList.torokuGroup;

import static kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroup.TOROKU_NO_MAX;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComUserSession;
import fb.inf.KitService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.MaxRowsException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;

/**
 * 各種登録グループの検索サービス
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroup_SearchService extends KitService {

	/** serialVersionUID */
	private static final long serialVersionUID = -3067150818947859341L;

	/** クラス名 */
	private static String className__ = JisekiESienListTorokuGroup_SearchService.class.getName();

	/** カテゴリ */
	private static Category category__ = Category.getInstance(className__);

	/** データベース操作クラス */
	protected PbsDatabase db_;

	/**
	 * コンストラクタ.
	 *
	 * @param cus getComUserSession()を渡すこと。
	 * @param db_ 呼び出すときにはgetDatabase()を渡すこと。
	 * @param isTran isTransaction()を渡すこと。
	 * @param ae getActionErrors()を渡すこと。
	 */
	public JisekiESienListTorokuGroup_SearchService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
		super(cus, db_, isTran, ae);
		this.db_ = db_;
	}

	/**
	 * 検索処理
	 * @param searchForm 検索フォーム
	 * @return 登録グループリスト
	 */
	public JisekiESienListTorokuGroupList execute(JisekiESienListTorokuGroup_SearchForm searchForm, IJisekiESienListTorokuGroupMaster torokuGroupMaster) {

		ComUserSession comUserSession = getComUserSession();

		// SQLへ渡す変数を格納するオブジェクト
		List<String> bindList= new ArrayList<String>();

		// SQL文を生成する
		String selectSql = torokuGroupMaster.getSearchSql(comUserSession, searchForm, bindList);

		// SQL文をデータベース操作オブジェクトに設定する
		db_.prepare(selectSql);

		// SQLに検索条件値をセットする
		int i = 0;
		for (String bindStr : bindList) {
			db_.setString(++i, bindStr);
		}

		// SQLを実行する
		db_.execute();

		JisekiESienListTorokuGroupList searchedList;
		try {
			// 検索結果を取得する
			PbsRecord[] records_ = db_.getPbsRecords();
			searchedList = new JisekiESienListTorokuGroupList(records_);
			// レコード数を20件へ調整
			if(searchedList.size() < TOROKU_NO_MAX) {
				searchedList.addAll(new JisekiESienListTorokuGroupList(TOROKU_NO_MAX - searchedList.size()));
			} else if(searchedList.size() > TOROKU_NO_MAX) {
				// 21件以上は20件まで表示
				while(searchedList.size() > TOROKU_NO_MAX){
					searchedList.removeRecord(searchedList.size() - 1);
				}
			}
		} catch (DataNotFoundException e) {
			// レコード数を20件へ調整
			searchedList = new JisekiESienListTorokuGroupList(TOROKU_NO_MAX);
		} catch (MaxRowsException e) {
			throw new RuntimeException();
		}
		return searchedList;
	}
}
