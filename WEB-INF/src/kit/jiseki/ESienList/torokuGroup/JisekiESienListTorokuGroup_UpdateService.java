package kit.jiseki.ESienList.torokuGroup;

import static fb.com.IKitComConstHM.TBL_T_TOROKU_GROUP;
import static fb.inf.pbs.IPbsConst.CHAR_LF;
import static fb.inf.pbs.IPbsConst.SQL_CRLF;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComUserSession;
import fb.com.IKitComConst;
import fb.com.exception.KitComException;
import fb.inf.KitRrkUpdateService;
import fb.inf.exception.DeadLockException;
import fb.inf.exception.UniqueKeyViolatedException;
import fb.inf.exception.UpdateRowException;
import fb.inf.pbs.PbsDatabase;

/**
 * 各種登録グループの編集サービス
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroup_UpdateService extends KitRrkUpdateService {

	/** serialVersionUID */
	private static final long serialVersionUID = -8585433769298656695L;

	/** クラス名 */
	private static String className__ = JisekiESienListTorokuGroup_UpdateService.class.getName();

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
	public JisekiESienListTorokuGroup_UpdateService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
		super(cus, db_, isTran, ae);
		this.db_ = db_;
		db_.setTranLog(true);
	}

	/**
	 * 登録更新処理
	 * @param searchForm 検索フォーム
	 * @param editForm 編集フォーム
	 * @throws SQLException
	 * @throws KitComException
	 */
	public void execute(JisekiESienListTorokuGroup_SearchForm searchForm, JisekiESienListTorokuGroup_EditForm editForm) throws SQLException, KitComException {
		ComUserSession comUserSession = getComUserSession();
		// 削除
		delete(comUserSession, searchForm);
		// 登録
		insert(comUserSession, searchForm, editForm);
	}

	/**
	 * 削除処理
	 * @param comUserSession ログインユーザセッション情報
	 * @param searchForm 検索フォーム
	 * @throws SQLException
	 * @throws KitComException
	 */
	private void delete(ComUserSession comUserSession, JisekiESienListTorokuGroup_SearchForm searchForm) throws SQLException, KitComException{
		List<String> bindList = new ArrayList<String>();
		try {
			// 削除
			executeUpdate(getDeleteSql(comUserSession, searchForm, bindList), bindList, true);
		} catch (UpdateRowException e) {
		} catch (DeadLockException e) {
			setErrorMessageId(e, db_.getDbFileNm());
			category__.warn(e.getMessage());
			throw new KitComException();
		} catch (UniqueKeyViolatedException e) {
			setErrorMessageId(e, db_.getDbFileNm());
			category__.warn(e.getMessage());
			throw new KitComException();
		} catch (SQLException e) {
			setErrorMessageId(e, db_.getDbFileNm());
			category__.warn(e.getMessage());
			throw e; // そのまま例外を送出
		}
	}

	/**
	 * 削除SQL取得
	 * @param comUserSession ログインユーザセッション情報
	 * @param searchForm 検索フォーム
	 * @param bindList バインドリスト
	 * @return 削除SQL
	 */
	private String getDeleteSql(ComUserSession comUserSession, JisekiESienListTorokuGroup_SearchForm searchForm, List<String> bindList) {
		StringBuilder sql = new StringBuilder(SQL_CRLF);
		sql.append("DELETE FROM T_TOROKU_GROUP \n");
		sql.append("WHERE \n");
		sql.append("    KAISYA_CD = ? \n");
		sql.append("    AND SGYOSYA_CD = ? \n");
		sql.append("    AND TOROKU_MASTER = ? \n");
		sql.append("    AND TOROKU_PTN = ? \n");
		bindList.add(comUserSession.getKaisyaCd());
		bindList.add(searchForm.getSgyosyaCd());
		bindList.add(searchForm.getTorokuMaster());
		bindList.add(searchForm.getTorokuPtn());
		return sql.toString();
	}

	/**
	 * 登録処理
	 * @param comUserSession ログインユーザセッション情報
	 * @param searchForm 検索フォーム
	 * @param torokuNo 登録No
	 * @param torokuCd 登録CD
	 * @return
	 * @throws SQLException
	 * @throws KitComException
	 */
	private void insert(ComUserSession comUserSession, JisekiESienListTorokuGroup_SearchForm searchForm, JisekiESienListTorokuGroup_EditForm editForm) throws SQLException, KitComException {
		int torokuNo = 0;
		for (String torokuCd : editForm.getTorokuCdList()) {
			List<String> bindList = new ArrayList<String>();
			try {
				// 登録
				executeUpdate(getInsertSql(comUserSession, searchForm, ++torokuNo, torokuCd, bindList), bindList, false);
			} catch (UpdateRowException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				throw new KitComException();
			} catch (DeadLockException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				throw new KitComException();
			} catch (UniqueKeyViolatedException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				throw new KitComException();
			} catch (SQLException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				throw e; // そのまま例外を送出
			}
		}
	}

	private String getInsertSql(ComUserSession comUserSession, JisekiESienListTorokuGroup_SearchForm searchForm, int torokuNo, String torokuCd, List<String> bindList) {
		StringBuilder sql = new StringBuilder(CHAR_LF);
		sql.append("INSERT INTO T_TOROKU_GROUP ( \n");
		sql.append("  RIYOU_KBN \n");
		sql.append(" ,KAISYA_CD \n");
		sql.append(" ,SGYOSYA_CD \n");
		sql.append(" ,TOROKU_MASTER \n");
		sql.append(" ,TOROKU_PTN \n");
		sql.append(" ,TOROKU_NO \n");
		sql.append(" ,TOROKU_CD \n");
		sql.append(") VALUES ( \n");
		sql.append("   ? \n");
		sql.append(" , ? \n");
		sql.append(" , ? \n");
		sql.append(" , ? \n");
		sql.append(" , ? \n");
		sql.append(" , ? \n");
		sql.append(" , ? \n");
		sql.append(") \n");
		bindList.add(IKitComConst.AVAILABLE_KB_RIYO_KA);
		bindList.add(comUserSession.getKaisyaCd());
		bindList.add(searchForm.getSgyosyaCd());
		bindList.add(searchForm.getTorokuMaster());
		bindList.add(searchForm.getTorokuPtn());
		bindList.add(String.valueOf(torokuNo));
		bindList.add(torokuCd);
		return sql.toString();
	}

	/**
	 * @param sql SQL
	 * @param bindList バインドリスと
	 * @param updateMultiRowFlg 複数行更新対象フラグ
	 * @throws UpdateRowException
	 * @throws DeadLockException
	 * @throws UniqueKeyViolatedException
	 * @throws SQLException
	 */
	private void executeUpdate(String sql, List<String> bindList, boolean updateMultiRowFlg) throws UpdateRowException, DeadLockException, UniqueKeyViolatedException, SQLException   {
		this.db_.prepare(sql);
		int i = 0;
		for (String bind : bindList) {
			db_.setString(++i, bind);
		}
		db_.setDbFileNm(TBL_T_TOROKU_GROUP);
		db_.executeUpdate(updateMultiRowFlg);
	}
}
