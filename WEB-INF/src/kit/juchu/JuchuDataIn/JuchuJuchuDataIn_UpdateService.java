package kit.juchu.JuchuDataIn;

import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;
import static kit.syuka.JuchuUriageDenpyoHako.ISyukaJuchuUriageDenpyoHako.KURAC_CHAR;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kit.juchu.CategoryDataCommonService;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComHaitaService;
import fb.com.ComUserSession;
import fb.com.ComUtil;
import fb.inf.KitRrkUpdateService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.DeadLockException;
import fb.inf.exception.KitException;
import fb.inf.exception.ResourceBusyNowaitException;
import fb.inf.exception.UniqueKeyViolatedException;
import fb.inf.exception.UpdateRowException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * データベースへの書き込みを実装するビジネスロジッククラスです。
 *
 */
public class JuchuJuchuDataIn_UpdateService extends KitRrkUpdateService {

	/** シリアルID */
	private static final long serialVersionUID = -8943228840878303470L;

	/** クラス名. */
	private static String className__ = JuchuJuchuDataIn_UpdateService.class.getName();
	/** カテゴリ. */
	private static Category category__ = Category.getInstance(className__);

	/** databaseオブジェクト */
	private PbsDatabase db_ = null;

	/** 排他サービス */
	private ComHaitaService haitaServ = null;


	/**
	 * コンストラクタです.
	 *
	 * @param request paramName
	 */
	public JuchuJuchuDataIn_UpdateService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
		super(cus, db_, isTran, ae);
		_reset();
		this.db_ = db_;
		// 排他サービスを生成する
		haitaServ = new ComHaitaService(cus, db_, isTran, ae);
	}

	/**
	 * 入出力パラメータの初期化を行う.
	 */
	protected void _reset() {
		db_ = null;
		haitaServ = null;
	}

	/**
	 * 新規処理
	 * 　受注入力（受注データ／ヘッダー部）・受注入力（受注データ／ディテール部）・予定出荷先別商品カテゴリデータ
	 * 　を同一トランザクションで処理
	 *
	 * @param editList
	 * @param editDtList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	public boolean insert(JuchuJuchuDataInList editList, JuchuJuchuDataInDtList editDtList) {

		category__.info("受注入力新規処理 START");

		boolean res = true;

		db_.setTransaction();
		// 基盤側で操作ログを出力する際にはtrueを与える
		db_.setTranLog(true);

		try {
			// ==================================================
			// 受注入力（受注データ／ヘッダー部）
			// ==================================================
			// DB処理
			this.insertHd(editList);

			// ==================================================
			// 受注入力（受注データ／ディテール部）
			// ==================================================
			// DB処理
			this.insertDt(editDtList);

			// ==================================================
			// 受注入力（予定出荷先別商品カテゴリデータ）
			// ==================================================
			// 入力情報から実績用リストを生成
			JuchuJuchuDataInCatList editCatList = createCatList(editDtList);

			// DB処理
			this.insertCat(editCatList);

		} catch (DataNotFoundException e) {
			res = false;
		} catch (UpdateRowException e) {
			res = false;
		} catch (DeadLockException e) {
			res = false;
		} catch (UniqueKeyViolatedException e) {
			res = false;
		} catch (SQLException e) {
			res = false;
		} catch (KitException e) {
			res = false;
		} finally {
			if (res) {
				db_.commit();
			} else {
				db_.rollback();
			}
		}

		category__.info("受注入力新規処理 END");

		return res;
	}

	/**
	 * 新規処理（INSERT）
	 * 　受注入力（受注データ／ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean insertHd(JuchuJuchuDataInList editList)
		throws DataNotFoundException, UpdateRowException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("受注入力（受注データ／ヘッダー部）新規処理（INSERT） START");

		boolean res = true;

		for (int i = 0; i < editList.size(); i++) {
			JuchuJuchuDataInRecord rec = (JuchuJuchuDataInRecord) editList.get(i);

			// 未入力行は無視する
			if (!rec.getIsModified()) {
				continue;
			}

			try {

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("受注入力（受注データ／ヘッダー部） >> 新規処理（INSERT） >> SQL文生成");
				String strSql = getSqlInsertHd(rec, bindList);
				category__.info("受注入力（受注データ／ヘッダー部） >> 新規処理（INSERT） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// INSERT実行
				db_.setDbFileNm(TBL_T_JYUCYU_HD);
				category__.info("受注入力（受注データ／ヘッダー部） >> 新規処理（INSERT） >> insert");
				db_.executeUpdate();

			} catch (DataNotFoundException e) {
				setErrorMessageId(e);
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (UpdateRowException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (DeadLockException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (UniqueKeyViolatedException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (SQLException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			}
		}

		category__.info("受注入力（受注データ／ヘッダー部）新規処理（INSERT） END");

		return res;
	}

	/**
	 * 新規処理（INSERT）
	 * 　受注入力（受注データ／ディテール部）
	 *
	 * @param editDtList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean insertDt(JuchuJuchuDataInDtList editDtList)
		throws DataNotFoundException, UpdateRowException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("受注入力（受注データ／ディテール部）新規処理（INSERT） START");

		boolean res = true;

		for (int i = 0; i < editDtList.size(); i++) {
			JuchuJuchuDataInDtRecord rec = (JuchuJuchuDataInDtRecord) editDtList.get(i);

			// 未入力行は無視する
			if (rec.isEmpty()) {
				continue;
			}

			try {

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("受注入力（受注データ／ディテール部） >> 新規処理（INSERT） >> SQL文生成");
				String strSql = getSqlInsertDt(rec, bindList);
				category__.info("受注入力（受注データ／ディテール部） >> 新規処理（INSERT） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// INSERT実行
				db_.setDbFileNm(TBL_T_JYUCYU_DT);
				category__.info("受注入力（受注データ／ディテール部） >> 新規処理（INSERT） >> insert");
				db_.executeUpdate();

			} catch (DataNotFoundException e) {
				setErrorMessageId(e);
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (UpdateRowException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (DeadLockException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (UniqueKeyViolatedException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (SQLException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			}
		}

		category__.info("受注入力（受注データ／ディテール部）新規処理（INSERT） END");

		return res;
	}

	/**
	 * 新規処理（INSERT）
	 * 　受注入力（予定出荷先別商品カテゴリデータ）
	 *
	 * @param editCatList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean insertCat(JuchuJuchuDataInCatList editCatList)
		throws DataNotFoundException, UpdateRowException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("受注入力（予定出荷先別商品カテゴリデータ）新規処理（INSERT） START");

		boolean res = true;

		for (int i = 0; i < editCatList.size(); i++) {
			JuchuJuchuDataInCatRecord rec = (JuchuJuchuDataInCatRecord) editCatList.get(i);

			try {

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("受注入力（予定出荷先別商品カテゴリデータ） >> 新規処理（INSERT） >> SQL文生成");
				String strSql = getSqlInsertCat(rec, bindList);
				category__.info("受注入力（予定出荷先別商品カテゴリデータ） >> 新規処理（INSERT） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// INSERT実行
				db_.setDbFileNm(TBL_T_JUCYU_JSKCTG);
				category__.info("受注入力（予定出荷先別商品カテゴリデータ） >> 新規処理（INSERT） >> insert");
				db_.executeUpdate();

			} catch (DataNotFoundException e) {
				setErrorMessageId(e);
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (UpdateRowException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (DeadLockException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (UniqueKeyViolatedException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (SQLException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			}
		}

		category__.info("受注入力（予定出荷先別商品カテゴリデータ）新規処理（INSERT） END");

		return res;
	}


	/**
	 * 抹消処理
	 * 　受注入力（受注データ／ヘッダー部）・受注入力（受注データ／ディテール部）・
	 * 　予定出荷先別商品カテゴリデータ・蔵本データ／ヘッダー部
	 * 　を同一トランザクションで処理
	 *
	 * @param editList
	 * @param editDtList
	 * @param initCatList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	public boolean erasure(JuchuJuchuDataInList editList, JuchuJuchuDataInDtList editDtList
			, JuchuJuchuDataInCatList initCatList) {

		category__.info("受注入力抹消処理 START");

		boolean res = true;

		db_.setTransaction();
		// 基盤側で操作ログを出力する際にはtrueを与える
		db_.setTranLog(true);

		try {
			// ==================================================
			// 受注入力（受注データ／ヘッダー部）
			// ==================================================
			// DB処理
			this.erasureHd(editList);

			// ==================================================
			// 受注入力（受注データ／ディテール部）
			// ==================================================
			// DB処理
			this.erasureDt(editDtList);

			// ==================================================
			// 受注入力（予定出荷先別商品カテゴリデータ）
			// ==================================================
			// DB処理
			this.erasureCat(initCatList);

			// ==================================================
			// 蔵直（蔵本データ／ヘッダー部）
			// ==================================================
			// DB処理
			this.updateKuracHd(editList);

		} catch (DataNotFoundException e) {
			res = false;
		} catch (UpdateRowException e) {
			res = false;
		} catch (ResourceBusyNowaitException e) {
			res = false;
		} catch (DeadLockException e) {
			res = false;
		} catch (UniqueKeyViolatedException e) {
			res = false;
		} catch (SQLException e) {
			res = false;
		} finally {
			if (res) {
				db_.commit();
			} else {
				db_.rollback();
			}
		}

		category__.info("受注入力抹消処理 END");

		return res;
	}


	/**
	 * 抹消処理（UPDATE）
	 * 　受注入力（受注データ／ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean erasureHd(JuchuJuchuDataInList editList)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("受注入力（受注データ／ヘッダー部）抹消処理（UPDATE） START");

		boolean res = true;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLockHd());
		category__.debug("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		for (int i = 0; i < editList.size(); i++) {

			// 「行目」取得：メッセージ表示用
			JuchuJuchuDataInRecord rec = (JuchuJuchuDataInRecord) editList.get(i);

			try {

				// 行ロック実行
				lockHd(ccGyoLock, rec);

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("受注入力（受注データ／ヘッダー部） >> 抹消処理（UPDATE） >> SQL文生成");
				String strSql = getSqlErasureHd(rec, bindList);
				category__.info("受注入力（受注データ／ヘッダー部） >> 抹消処理（UPDATE） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// UPDATE実行
				db_.setDbFileNm(TBL_T_JYUCYU_HD);
				category__.debug("受注入力（受注データ／ヘッダー部） >> 抹消処理（UPDATE） >> update");
				db_.executeUpdate();

				// 排他を解放する
				haitaServ.setPkCodes(rec.getPrimaryCodes());
				haitaServ.unLock();

			} catch (DataNotFoundException e) {
				setErrorMessageId(e);
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (UpdateRowException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (ResourceBusyNowaitException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (DeadLockException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (SQLException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			}
		}

		category__.info("受注入力（受注データ／ヘッダー部）抹消処理（UPDATE） END");

		return res;
	}

	/**
	 * 抹消処理（UPDATE）
	 * 　受注入力（受注データ／ディテール部）
	 *
	 * @param editDtList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean erasureDt(JuchuJuchuDataInDtList editDtList)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("受注入力（受注データ／ディテール部）抹消処理（UPDATE） START");

		boolean res = true;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLockDt());
		category__.debug("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		for (int i = 0; i < editDtList.size(); i++) {

			// 「行目」取得：メッセージ表示用
			JuchuJuchuDataInDtRecord rec = (JuchuJuchuDataInDtRecord) editDtList.get(i);

			// 未入力行は無視する
			if (rec.isEmpty()) {
				continue;
			}

			try {

				// 行ロック実行
				lockDt(ccGyoLock, rec);

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("受注入力（受注データ／ディテール部） >> 抹消処理（UPDATE） >> SQL文生成");
				String strSql = getSqlErasureDt(rec, bindList);
				category__.info("受注入力（受注データ／ディテール部） >> 抹消処理（UPDATE） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// UPDATE実行
				db_.setDbFileNm(TBL_T_JYUCYU_DT);
				category__.debug("受注入力（受注データ／ディテール部） >> 抹消処理（UPDATE） >> update");
				db_.executeUpdate();

			} catch (DataNotFoundException e) {
				setErrorMessageId(e);
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (UpdateRowException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (ResourceBusyNowaitException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (DeadLockException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (SQLException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			}
		}

		category__.info("受注入力（受注データ／ディテール部）抹消処理（UPDATE） END");

		return res;
	}

	/**
	 * 抹消処理（UPDATE）
	 * 　受注入力（予定出荷先別商品カテゴリデータ）
	 *
	 * @param editCatList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean erasureCat(JuchuJuchuDataInCatList editCatList)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("受注入力（予定出荷先別商品カテゴリデータ）抹消処理（UPDATE） START");

		boolean res = true;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLockCat());
		category__.debug("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		for (int i = 0; i < editCatList.size(); i++) {

			// 「行目」取得：メッセージ表示用
			JuchuJuchuDataInCatRecord rec = (JuchuJuchuDataInCatRecord) editCatList.get(i);

			// 未入力行は無視する
			if (rec.isEmpty()) {
				continue;
			}

			try {

				// 行ロック実行
				lockCat(ccGyoLock, rec);

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("受注入力（予定出荷先別商品カテゴリデータ） >> 抹消処理（UPDATE） >> SQL文生成");
				String strSql = getSqlErasureCat(rec, bindList);
				category__.info("受注入力（予定出荷先別商品カテゴリデータ） >> 抹消処理（UPDATE） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// UPDATE実行
				db_.setDbFileNm(TBL_T_JUCYU_JSKCTG);
				category__.debug("受注入力（予定出荷先別商品カテゴリデータ） >> 抹消処理（UPDATE） >> update");
				db_.executeUpdate();

			} catch (DataNotFoundException e) {
				setErrorMessageId(e);
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (UpdateRowException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (ResourceBusyNowaitException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (DeadLockException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (SQLException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			}
		}

		category__.info("受注入力（予定出荷先別商品カテゴリデータ）抹消処理（UPDATE） END");

		return res;
	}


	/**
	 * 復活処理
	 * 　受注入力（受注データ／ヘッダー部）・受注入力（受注データ／ディテール部）・予定出荷先別商品カテゴリデータ
	 * 　を同一トランザクションで処理
	 *
	 * @param editList
	 * @param editDtList
	 * @param initCatList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	public boolean rebirth(JuchuJuchuDataInList editList, JuchuJuchuDataInDtList editDtList
			, JuchuJuchuDataInCatList initCatList) {

		category__.info("受注入力復活処理 START");

		boolean res = true;

		db_.setTransaction();
		// 基盤側で操作ログを出力する際にはtrueを与える
		db_.setTranLog(true);

		try {
			// ==================================================
			// 受注入力（受注データ／ヘッダー部）
			// ==================================================
			// DB処理
			this.rebirthHd(editList);

			// ==================================================
			// 受注入力（受注データ／ディテール部）
			// ==================================================
			// DB処理
			this.rebirthDt(editDtList);

			// ==================================================
			// 受注入力（予定出荷先別商品カテゴリデータ）
			// ==================================================
			// DB処理
			this.rebirthCat(initCatList);

		} catch (DataNotFoundException e) {
			res = false;
		} catch (UpdateRowException e) {
			res = false;
		} catch (ResourceBusyNowaitException e) {
			res = false;
		} catch (DeadLockException e) {
			res = false;
		} catch (UniqueKeyViolatedException e) {
			res = false;
		} catch (SQLException e) {
			res = false;
		} finally {
			if (res) {
				db_.commit();
			} else {
				db_.rollback();
			}
		}

		category__.info("受注入力復活処理 END");

		return res;
	}


	/**
	 * 復活処理（UPDATE）
	 * 　受注入力（受注データ／ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean rebirthHd(JuchuJuchuDataInList editList)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("受注入力（受注データ／ヘッダー部）復活処理（UPDATE） START");

		boolean res = true;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLockHd());
		category__.debug("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		for (int i = 0; i < editList.size(); i++) {

			// 「行目」取得：メッセージ表示用
			JuchuJuchuDataInRecord rec = (JuchuJuchuDataInRecord) editList.get(i);

			try {

				// 行ロック実行
				lockHd(ccGyoLock, rec);

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("受注入力（受注データ／ヘッダー部） >> 復活処理（UPDATE） >> SQL文生成");
				String strSql = getSqlRebirthHd(rec, bindList);
				category__.info("受注入力（受注データ／ヘッダー部） >> 復活処理（UPDATE） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// UPDATE実行
				db_.setDbFileNm(TBL_T_JYUCYU_HD);
				category__.debug("受注入力（受注データ／ヘッダー部） >> 復活処理（UPDATE） >> update");
				db_.executeUpdate();

				// 排他を解放する
				haitaServ.setPkCodes(rec.getPrimaryCodes());
				haitaServ.unLock();

			} catch (DataNotFoundException e) {
				setErrorMessageId(e);
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (UpdateRowException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (ResourceBusyNowaitException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (DeadLockException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (SQLException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			}
		}

		category__.info("受注入力（受注データ／ヘッダー部）復活処理（UPDATE） END");

		return res;
	}

	/**
	 * 復活処理（UPDATE）
	 * 　受注入力（受注データ／ディテール部）
	 *
	 * @param editDtList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean rebirthDt(JuchuJuchuDataInDtList editDtList)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("受注入力（受注データ／ディテール部）復活処理（UPDATE） START");

		boolean res = true;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLockDt());
		category__.debug("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		for (int i = 0; i < editDtList.size(); i++) {

			// 「行目」取得：メッセージ表示用
			JuchuJuchuDataInDtRecord rec = (JuchuJuchuDataInDtRecord) editDtList.get(i);

			// 未入力行は無視する
			if (rec.isEmpty()) {
				continue;
			}

			try {

				// 行ロック実行
				lockDt(ccGyoLock, rec);

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("受注入力（受注データ／ディテール部） >> 復活処理（UPDATE） >> SQL文生成");
				String strSql = getSqlRebirthDt(rec, bindList);
				category__.info("受注入力（受注データ／ディテール部） >> 復活処理（UPDATE） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// UPDATE実行
				db_.setDbFileNm(TBL_T_JYUCYU_DT);
				category__.debug("受注入力（受注データ／ディテール部） >> 復活処理（UPDATE） >> update");
				db_.executeUpdate();

			} catch (DataNotFoundException e) {
				setErrorMessageId(e);
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (UpdateRowException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (ResourceBusyNowaitException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (DeadLockException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (SQLException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			}
		}

		category__.info("受注入力（受注データ／ディテール部）復活処理（UPDATE） END");

		return res;
	}

	/**
	 * 復活処理（UPDATE）
	 * 　受注入力（予定出荷先別商品カテゴリデータ）
	 *
	 * @param editCatList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean rebirthCat(JuchuJuchuDataInCatList editCatList)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("受注入力（予定出荷先別商品カテゴリデータ）復活処理（UPDATE） START");

		boolean res = true;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLockCat());
		category__.debug("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		for (int i = 0; i < editCatList.size(); i++) {

			// 「行目」取得：メッセージ表示用
			JuchuJuchuDataInCatRecord rec = (JuchuJuchuDataInCatRecord) editCatList.get(i);

			// 未入力行は無視する
			if (rec.isEmpty()) {
				continue;
			}

			try {

				// 行ロック実行
				lockCat(ccGyoLock, rec);

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("受注入力（予定出荷先別商品カテゴリデータ） >> 復活処理（UPDATE） >> SQL文生成");
				String strSql = getSqlRebirthCat(rec, bindList);
				category__.info("受注入力（予定出荷先別商品カテゴリデータ） >> 復活処理（UPDATE） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// UPDATE実行
				db_.setDbFileNm(TBL_T_JUCYU_JSKCTG);
				category__.debug("受注入力（予定出荷先別商品カテゴリデータ） >> 復活処理（UPDATE） >> update");
				db_.executeUpdate();

			} catch (DataNotFoundException e) {
				setErrorMessageId(e);
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (UpdateRowException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (ResourceBusyNowaitException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (DeadLockException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (SQLException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			}
		}

		category__.info("受注入力（予定出荷先別商品カテゴリデータ）復活処理（UPDATE） END");

		return res;
	}


	/**
	 * 修正処理
	 * 　受注入力（受注データ／ヘッダー部）・受注入力（受注データ／ディテール部）・予定出荷先別商品カテゴリデータ
	 * 　を同一トランザクションで処理
	 *
	 * @param editList
	 * @param editDtList
	 * @param initDtList
	 * @param initCatList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	public boolean update(JuchuJuchuDataInList editList, JuchuJuchuDataInDtList editDtList,
			JuchuJuchuDataInDtList initDtList, JuchuJuchuDataInCatList initCatList) {

		category__.info("受注入力修正処理 START");

		boolean res = true;

		db_.setTransaction();
		// 基盤側で操作ログを出力する際にはtrueを与える
		db_.setTranLog(true);

		try {
			// ==================================================
			// 受注入力（受注データ／ヘッダー部）
			// ==================================================
			// DB処理
			this.updateHd(editList); // UPDATE

			// 受注NO
			JuchuJuchuDataInRecord hdRec = (JuchuJuchuDataInRecord) editList.getFirstRecord();
			String jyucyuNo = hdRec.getJyucyuNo();

			// ==================================================
			// 受注入力（受注データ／ディテール部）
			// ==================================================
			// UPDATEをDELETE/INSERTで行うための準備
			// DELETE用チェック
			boolean hasOldData = false;
			for (PbsRecord tmp : initDtList) {
				JuchuJuchuDataInDtRecord rec = (JuchuJuchuDataInDtRecord) tmp;
				if (!rec.isEmpty()) {
					hasOldData = true;
				}
			}
			// INSERT用チェック
			boolean hasNewData = false;
			for (PbsRecord tmp : editDtList) {
				JuchuJuchuDataInDtRecord rec = (JuchuJuchuDataInDtRecord) tmp;
				if (!rec.isEmpty()) {
					hasNewData = true;
				}
			}

			// DB処理
			if (hasOldData) {
				this.deleteDt(jyucyuNo); // DELETE
			}
			if (hasNewData) {
				this.insertDt(editDtList); // INSERT
			}

			// ==================================================
			// 受注入力（予定出荷先別商品カテゴリデータ）
			// ==================================================
			// 入力情報から実績用リストを生成
			JuchuJuchuDataInCatList editCatList = createCatList(editDtList);

			// UPDATEをDELETE/INSERTで行うための準備
			// DELETE用チェック
			hasOldData = false;
			for (PbsRecord tmp : initCatList) {
				JuchuJuchuDataInCatRecord rec = (JuchuJuchuDataInCatRecord) tmp;
				if (!rec.isEmpty()) {
					hasOldData = true;
				}
			}
			// INSERT用チェック
			hasNewData = false;
			for (PbsRecord tmp : editCatList) {
				JuchuJuchuDataInCatRecord rec = (JuchuJuchuDataInCatRecord) tmp;
				if (!rec.isEmpty()) {
					hasNewData = true;
				}
			}
			// DB処理
			if (hasOldData) {
				this.deleteCat(jyucyuNo); // DELETE
			}
			if (hasNewData) {
				this.insertCat(editCatList); // INSERT
			}

		} catch (DataNotFoundException e) {
			res = false;
		} catch (UpdateRowException e) {
			res = false;
		} catch (ResourceBusyNowaitException e) {
			res = false;
		} catch (DeadLockException e) {
			res = false;
		} catch (UniqueKeyViolatedException e) {
			res = false;
		} catch (SQLException e) {
			res = false;
		} catch (KitException e) {
			res = false;
		} finally {
			if (res) {
				db_.commit();
			} else {
				db_.rollback();
			}
		}

		category__.info("受注入力修正処理 END");

		return res;
	}


	/**
	 * 修正処理（UPDATE）
	 * 　受注入力（受注データ／ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean updateHd(JuchuJuchuDataInList editList)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("受注入力（受注データ／ヘッダー部）修正処理（UPDATE） START");

		boolean res = true;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLockHd());
		category__.info("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		for (int i = 0; i < editList.size(); i++) {

			// 「行目」取得：メッセージ表示用
			JuchuJuchuDataInRecord rec = (JuchuJuchuDataInRecord) editList.get(i);

			try {

				// 行ロック実行
				lockHd(ccGyoLock, rec);

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("受注入力（受注データ／ヘッダー部） >> 修正処理（UPDATE） >> SQL文生成");
				String strSql = getSqlUpdateHd(rec, bindList);
				category__.info("受注入力（受注データ／ヘッダー部） >> 修正処理（UPDATE） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// UPDATE実行
				db_.setDbFileNm(TBL_T_JYUCYU_HD);
				category__.debug("受注入力（受注データ／ヘッダー部） >> 修正処理（UPDATE） >> update");
				db_.executeUpdate();

				// 排他を解放する
				haitaServ.setPkCodes(rec.getPrimaryCodes());
				haitaServ.unLock();

			} catch (DataNotFoundException e) {
				setErrorMessageId(e);
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (UpdateRowException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (ResourceBusyNowaitException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (DeadLockException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			} catch (SQLException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(HAS_ERROR_TRUE);
				res = false;
				throw e; // そのまま例外を送出
			}
		}

		category__.info("受注入力（受注データ／ヘッダー部）修正処理（UPDATE） END");

		return res;
	}


	/**
	 * 修正処理（DELETE）
	 * 　受注入力（受注データ／ディテール部）
	 *
	 * @param jyucyuNo
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean deleteDt(String jyucyuNo)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("受注入力（受注データ／ディテール部）修正処理（DELETE） START");

		boolean res = true;

		try {

			List<String> bindList = new ArrayList<String>();

			// SQLを取得
			category__.info("受注入力（受注データ／ディテール部） >> 修正処理（DELETE） >> SQL文生成");
			String strSql = getSqlDeleteDt(jyucyuNo, bindList);
			category__.info("受注入力（受注データ／ディテール部） >> 修正処理（DELETE） >> prepare");
			this.db_.prepare(strSql);

			int ii = 0;
			for (String bindStr : bindList) {
				db_.setString(++ii, bindStr);
			}

			// DELETE実行
			db_.setDbFileNm(TBL_T_JYUCYU_DT);
			category__.info("受注入力（受注データ／ディテール部） >> 修正処理（DELETE） >> delete");
			db_.executeUpdate(true); // 複数行の処理を有効

		} catch (DataNotFoundException e) {
			setErrorMessageId(e);
			category__.warn(e.getMessage());
			res = false;
			throw e; // そのまま例外を送出
		} catch (UpdateRowException e) {
			setErrorMessageId(e, db_.getDbFileNm());
			category__.warn(e.getMessage());
			res = false;
			throw e; // そのまま例外を送出
		} catch (DeadLockException e) {
			setErrorMessageId(e, db_.getDbFileNm());
			category__.warn(e.getMessage());
			res = false;
			throw e; // そのまま例外を送出
		} catch (UniqueKeyViolatedException e) {
			setErrorMessageId(e, db_.getDbFileNm());
			category__.warn(e.getMessage());
			res = false;
			throw e; // そのまま例外を送出
		} catch (SQLException e) {
			setErrorMessageId(e, db_.getDbFileNm());
			category__.warn(e.getMessage());
			res = false;
			throw e; // そのまま例外を送出
		}

		category__.info("受注入力（受注データ／ディテール部）修正処理（DELETE） END");

		return res;
	}

	/**
	 * 修正処理（DELETE）
	 * 　受注入力（予定出荷先別商品カテゴリデータ）
	 *
	 * @param jyucyuNo
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean deleteCat(String jyucyuNo)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("受注入力（予定出荷先別商品カテゴリデータ）修正処理（DELETE） START");

		boolean res = true;

		try {

			List<String> bindList = new ArrayList<String>();

			// SQLを取得
			category__.info("受注入力（予定出荷先別商品カテゴリデータ） >> 修正処理（DELETE） >> SQL文生成");
			String strSql = getSqlDeleteCat(jyucyuNo, bindList);
			category__.info("受注入力（予定出荷先別商品カテゴリデータ） >> 修正処理（DELETE） >> prepare");
			this.db_.prepare(strSql);

			int ii = 0;
			for (String bindStr : bindList) {
				db_.setString(++ii, bindStr);
			}

			// DELETE実行
			db_.setDbFileNm(TBL_T_JUCYU_JSKCTG);
			category__.info("受注入力（予定出荷先別商品カテゴリデータ） >> 修正処理（DELETE） >> delete");
			db_.executeUpdate(true); // 複数行の処理を有効

		} catch (DataNotFoundException e) {
			setErrorMessageId(e);
			category__.warn(e.getMessage());
			res = false;
			throw e; // そのまま例外を送出
		} catch (UpdateRowException e) {
			setErrorMessageId(e, db_.getDbFileNm());
			category__.warn(e.getMessage());
			res = false;
			throw e; // そのまま例外を送出
		} catch (DeadLockException e) {
			setErrorMessageId(e, db_.getDbFileNm());
			category__.warn(e.getMessage());
			res = false;
			throw e; // そのまま例外を送出
		} catch (UniqueKeyViolatedException e) {
			setErrorMessageId(e, db_.getDbFileNm());
			category__.warn(e.getMessage());
			res = false;
			throw e; // そのまま例外を送出
		} catch (SQLException e) {
			setErrorMessageId(e, db_.getDbFileNm());
			category__.warn(e.getMessage());
			res = false;
			throw e; // そのまま例外を送出
		}

		category__.info("受注入力（予定出荷先別商品カテゴリデータ）修正処理（DELETE） END");

		return res;
	}


	/**
	 * 受注入力（受注データ／ヘッダー部）新規SQL
	 *
	 * @param rec JuchuJuchuDataInRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlInsertHd(JuchuJuchuDataInRecord rec, List<String> bindList) {

		ComUserSession cus = getComUserSession();

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("INSERT INTO T_JYUCYU_HD ( \n");
		sSql.append("  RIYOU_KBN \n"); // 利用区分
		sSql.append(" ,KAISYA_CD \n"); // 会社CD (FK)
		sSql.append(" ,JYUCYU_NO \n"); // 受注NO
		sSql.append(" ,SYUKA_KBN \n"); // 出荷先区分
		sSql.append(" ,SYUBETU_CD \n"); // データ種別CD
		sSql.append(" ,KURA_CD \n"); // 蔵CD (FK)
		sSql.append(" ,UNCHIN_KBN \n"); // 運賃区分
		sSql.append(" ,UNSOTEN_CD \n"); // 運送店CD
		sSql.append(" ,SYUKA_DT \n"); // 出荷日(売上伝票発行予定日)
		sSql.append(" ,MINASI_DT \n"); // ミナシ日付
		sSql.append(" ,CHACUNI_YOTEI_DT \n"); // 着荷予定日
		sSql.append(" ,NIUKE_TIME_KBN \n"); // 荷受時間区分
		sSql.append(" ,NIUKE_BIGIN_TIME \n"); // 荷受時間_開始
		sSql.append(" ,NIUKE_END_TIME \n"); // 荷受時間_終了
		sSql.append(" ,SENPO_HACYU_NO \n"); // 先方発注NO
		sSql.append(" ,TATESN_CD \n"); // 縦線CD (FK)
		sSql.append(" ,TANTOSYA_CD \n"); // 担当者CD
		sSql.append(" ,TOKUYAKUTEN_CD \n"); // 特約店CD
		sSql.append(" ,DEPO_CD \n"); // デポCD
		sSql.append(" ,NIJITEN_CD \n"); // 二次店CD
		sSql.append(" ,SANJITEN_CD \n"); // 三次店CD
		sSql.append(" ,SYUHANTEN_CD \n"); // 酒販店（統一）CD
		sSql.append(" ,DELIVERY_KBN \n"); // 倉入・直送区分
		sSql.append(" ,SYUKA_SAKI_COUNTRY_CD \n"); // 出荷先国CD
		sSql.append(" ,JIS_CD \n"); // JISCD
		sSql.append(" ,UNCHIN_CNV_TANKA \n"); // 引取運賃換算単価
		sSql.append(" ,TEKIYO_KBN1 \n"); // 摘要区分 (01)
		sSql.append(" ,TEKIYO_NM1 \n"); // 摘要内容 (01)
		sSql.append(" ,TEKIYO_KBN2 \n"); // 摘要区分 (02)
		sSql.append(" ,TEKIYO_NM2 \n"); // 摘要内容 (02)
		sSql.append(" ,TEKIYO_KBN3 \n"); // 摘要区分 (03)
		sSql.append(" ,TEKIYO_NM3 \n"); // 摘要内容 (03)
		sSql.append(" ,TEKIYO_KBN4 \n"); // 摘要区分 (04)
		sSql.append(" ,TEKIYO_NM4 \n"); // 摘要内容 (04)
		sSql.append(" ,TEKIYO_KBN5 \n"); // 摘要区分 (05)
		sSql.append(" ,TEKIYO_NM5 \n"); // 摘要内容 (05)
		sSql.append(" ,ATUKAI_KBN \n"); // 扱い区分
		sSql.append(" ,LOW_HAISO_LOT \n"); // 最低配送ロット数
		sSql.append(" ,SYUKA_SYONIN_NO \n"); // 小ロット出荷承認申請NO
		sSql.append(" ,SYUKA_SURYO_BOX \n"); // 出荷数量計_箱数
		sSql.append(" ,SYUKA_SURYO_SET \n"); // 出荷数量計_セット数
		sSql.append(" ,SYUKA_KINGAKU_TOT \n"); // 出荷金額計
		sSql.append(" ,JYURYO_TOT \n"); // 重量計(KG)
		sSql.append(" ,SYUKA_YOURYO_TOT \n"); // 容量計（L）_出荷容量計
		sSql.append(" ,REBATE1_YOURYO_TOT \n"); // 容量計（L）_リベートⅠ類対象容量計
		sSql.append(" ,REBATE2_YOURYO_TOT \n"); // 容量計（L）_リベートⅡ類対象容量計
		sSql.append(" ,REBATE3_YOURYO_TOT \n"); // 容量計（L）_リベートⅢ類対象容量計
		sSql.append(" ,REBATE4_YOURYO_TOT \n"); // 容量計（L）_リベートⅣ類対象容量計
		sSql.append(" ,REBATE5_YOURYO_TOT \n"); // 容量計（L）_リベートⅤ類対象容量計
		sSql.append(" ,REBATEO_YOURYO_TOT \n"); // 容量計（L）_リベート対象外容量計
		sSql.append(") VALUES ( \n");
		sSql.append("   ? \n"); // 利用区分
		sSql.append(" , ? \n"); // 会社CD (FK)
		sSql.append(" , ? \n"); // 受注NO
		sSql.append(" , ? \n"); // 出荷先区分
		sSql.append(" , ? \n"); // データ種別CD
		sSql.append(" , ? \n"); // 蔵CD (FK)
		sSql.append(" , ? \n"); // 運賃区分
		sSql.append(" , ? \n"); // 運送店CD
		sSql.append(" , ? \n"); // 出荷日(売上伝票発行予定日)
		sSql.append(" , ? \n"); // ミナシ日付
		sSql.append(" , ? \n"); // 着荷予定日
		sSql.append(" , ? \n"); // 荷受時間区分
		sSql.append(" , ? \n"); // 荷受時間_開始
		sSql.append(" , ? \n"); // 荷受時間_終了
		sSql.append(" , ? \n"); // 先方発注NO
		sSql.append(" , ? \n"); // 縦線CD (FK)
		sSql.append(" , ? \n"); // 担当者CD
		sSql.append(" , ? \n"); // 特約店CD
		sSql.append(" , ? \n"); // デポCD
		sSql.append(" , ? \n"); // 二次店CD
		sSql.append(" , ? \n"); // 三次店CD
		sSql.append(" , ? \n"); // 酒販店（統一）CD
		sSql.append(" , ? \n"); // 倉入・直送区分
		sSql.append(" , ? \n"); // 出荷先国CD
		sSql.append(" , ? \n"); // JISCD
		sSql.append(" , ? \n"); // 引取運賃換算単価
		sSql.append(" , ? \n"); // 摘要区分 (01)
		sSql.append(" , ? \n"); // 摘要内容 (01)
		sSql.append(" , ? \n"); // 摘要区分 (02)
		sSql.append(" , ? \n"); // 摘要内容 (02)
		sSql.append(" , ? \n"); // 摘要区分 (03)
		sSql.append(" , ? \n"); // 摘要内容 (03)
		sSql.append(" , ? \n"); // 摘要区分 (04)
		sSql.append(" , ? \n"); // 摘要内容 (04)
		sSql.append(" , ? \n"); // 摘要区分 (05)
		sSql.append(" , ? \n"); // 摘要内容 (05)
		sSql.append(" , ? \n"); // 扱い区分
		sSql.append(" , ? \n"); // 最低配送ロット数
		sSql.append(" , ? \n"); // 小ロット出荷承認申請NO
		sSql.append(" , ? \n"); // 出荷数量計_箱数
		sSql.append(" , ? \n"); // 出荷数量計_セット数
		sSql.append(" , ? \n"); // 出荷金額計
		sSql.append(" , ? \n"); // 重量計(KG)
		sSql.append(" , ? \n"); // 容量計（L）_出荷容量計
		sSql.append(" , ? \n"); // 容量計（L）_リベートⅠ類対象容量計
		sSql.append(" , ? \n"); // 容量計（L）_リベートⅡ類対象容量計
		sSql.append(" , ? \n"); // 容量計（L）_リベートⅢ類対象容量計
		sSql.append(" , ? \n"); // 容量計（L）_リベートⅣ類対象容量計
		sSql.append(" , ? \n"); // 容量計（L）_リベートⅤ類対象容量計
		sSql.append(" , ? \n"); // 容量計（L）_リベート対象外容量計
		sSql.append(") \n");
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); // 利用区分（必須項目）
		bindList.add(cus.getKaisyaCd()); // 会社CD (FK)（必須項目）
		bindList.add(rec.getJyucyuNo()); // 受注NO（必須項目）
		bindList.add(rec.getSyukaKbn()); // 出荷先区分（必須項目）
		bindList.add(rec.getSyubetuCd()); // データ種別CD（必須項目）
		bindList.add(rec.getKuraCd()); // 蔵CD (FK)（必須項目）
		bindList.add(rec.getUnchinKbn()); // 運賃区分（必須項目）
		bindList.add(rec.getUnsotenCd()); // 運送店CD（必須項目）
		bindList.add(rec.getSyukaDt()); // 出荷日(売上伝票発行予定日)（必須項目）
		bindList.add(rec.getMinasiDt()); // ミナシ日付（必須項目）
		bindList.add(PbsUtil.getChar(rec.getChacuniYoteiDt())); // 着荷予定日（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getNiukeTimeKbn())); // 荷受時間区分（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getNiukeBiginTime())); // 荷受時間_開始（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getNiukeEndTime())); // 荷受時間_終了（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getSenpoHacyuNo())); // 先方発注NO（NULLあり）
		bindList.add(rec.getOrsTatesnCd()); // 縦線CD (FK)（必須項目）
		bindList.add(rec.getTantosyaCd()); // 担当者CD（必須項目）
		bindList.add(rec.getTokuyakutenCd()); // 特約店CD（必須項目）
		bindList.add(rec.getDepoCd()); // デポCD（必須項目）
		bindList.add(PbsUtil.getChar(rec.getNijitenCd())); // 二次店CD（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getSanjitenCd())); // 三次店CD（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getSyuhantenCd())); // 酒販店（統一）CD（NULLあり）
		bindList.add(rec.getDeliveryKbn()); // 倉入・直送区分（必須項目）
		bindList.add(rec.getSyukaSakiCountryCd()); // 出荷先国CD（必須項目）
		bindList.add(rec.getJisCd()); // JISCD（必須項目）
		bindList.add(rec.getUnchinCnvTanka()); // 引取運賃換算単価（必須項目）
		bindList.add(PbsUtil.getChar(rec.getTekiyoKbn1())); // 摘要区分 (01)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoNm1())); // 摘要内容 (01)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoKbn2())); // 摘要区分 (02)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoNm2())); // 摘要内容 (02)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoKbn3())); // 摘要区分 (03)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoNm3())); // 摘要内容 (03)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoKbn4())); // 摘要区分 (04)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoNm4())); // 摘要内容 (04)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoKbn5())); // 摘要区分 (05)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoNm5())); // 摘要内容 (05)（NULLあり）
		bindList.add(rec.getAtukaiKbn()); // 扱い区分（必須項目）
		bindList.add(rec.getLowHaisoLot()); // 最低配送ロット数（必須項目）
		bindList.add(PbsUtil.getChar(rec.getSyukaSyoninNo())); // 小ロット出荷承認申請NO（NULLあり）
		bindList.add(rec.getSyukaSuryoBox()); // 出荷数量計_箱数（必須項目）
		bindList.add(rec.getSyukaSuryoSet()); // 出荷数量計_セット数（必須項目）
		bindList.add(rec.getSyukaKingakuTot()); // 出荷金額計（必須項目）
		bindList.add(rec.getJyuryoTot()); // 重量計(KG)（必須項目）
		bindList.add(rec.getSyukaYouryoTot()); // 容量計（L）_出荷容量計（必須項目）
		bindList.add(rec.getRebate1YouryoTot()); // 容量計（L）_リベートⅠ類対象容量計（必須項目）
		bindList.add(rec.getRebate2YouryoTot()); // 容量計（L）_リベートⅡ類対象容量計（必須項目）
		bindList.add(rec.getRebate3YouryoTot()); // 容量計（L）_リベートⅢ類対象容量計（必須項目）
		bindList.add(rec.getRebate4YouryoTot()); // 容量計（L）_リベートⅣ類対象容量計（必須項目）
		bindList.add(rec.getRebate5YouryoTot()); // 容量計（L）_リベートⅤ類対象容量計（必須項目）
		bindList.add(rec.getRebateoYouryoTot()); // 容量計（L）_リベート対象外容量計（必須項目）

		return sSql.toString();
	}

	/**
	 * 受注入力（受注データ／ディテール部）新規SQL
	 *
	 * @param rec JuchuJuchuDataInDtRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlInsertDt(JuchuJuchuDataInDtRecord rec, List<String> bindList) {

		ComUserSession cus = getComUserSession();

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("INSERT INTO T_JYUCYU_DT ( \n");
		sSql.append("  RIYOU_KBN \n"); // 利用区分
		sSql.append(" ,KAISYA_CD \n"); // 会社CD
		sSql.append(" ,JYUCYU_NO \n"); // 受注NO
		sSql.append(" ,INPUT_LINE_NO \n"); // 受注行NO
		sSql.append(" ,KURA_CD \n"); // 蔵CD
		sSql.append(" ,SOUKO_CD \n"); // 倉庫CD
		sSql.append(" ,KTKSY_CD \n"); // 寄託者CD
		sSql.append(" ,SHOHIN_CD \n"); // 商品CD
		sSql.append(" ,SEIHIN_CD \n"); // 製品CD
		sSql.append(" ,SIIRESAKI_CD \n"); // 仕入先CD
		sSql.append(" ,SIIREHIN_CD \n"); //  仕入品CD
		sSql.append(" ,SEIHIN_DT \n"); // 製品日付
		sSql.append(" ,TUMIDEN_LINE_NO \n"); // 積荷伝票用ラインNO
		sSql.append(" ,CASE_IRISU \n"); // ケース入数
		sSql.append(" ,BARA_YOURYO \n"); // バラ容量(L)
		sSql.append(" ,WEIGHT_CASE \n"); // 重量(KG)_ケース
		sSql.append(" ,WEIGHT_BARA \n"); // 重量(KG)_バラ
		sSql.append(" ,UNCHIN_KAKERITU \n"); // 運賃掛率(%)
		sSql.append(" ,HANBAI_TANKA \n"); // 販売単価
		sSql.append(" ,SYUKA_SU_CASE \n"); // 出荷数量_箱数
		sSql.append(" ,SYUKA_SU_BARA \n"); // 出荷数量_セット数
		sSql.append(" ,SYUKA_ALL_BARA \n"); // 出荷数量_換算総セット数
		sSql.append(" ,SYUKA_ALL_WEIGTH \n"); // 出荷重量（KG)
		sSql.append(" ,SYUKA_HANBAI_KINGAKU \n"); // 販売額
		sSql.append(" ,SYUKA_TAIO_KBN \n"); // 出荷対応区分
		sSql.append(" ,ATUKAI_KBN \n"); // 扱い区分
		sSql.append(" ,BUPIN_KBN \n"); // 物品区分
		sSql.append(" ,HTANKA_CHG_FLG \n"); // 販売単価変更フラグ
		sSql.append(" ,SYUKA_SOUYOURYO \n"); // 容量（L）_出荷総容量
		sSql.append(" ,REBATE1_SOUYOURYO \n"); // 容量（L）_リベートⅠ類対象総容量
		sSql.append(" ,REBATE2_SOUYOURYO \n"); // 容量（L）_リベートⅡ類対象総容量
		sSql.append(" ,REBATE3_SOUYOURYO \n"); // 容量（L）_リベートⅢ類対象総容量
		sSql.append(" ,REBATE4_SOUYOURYO \n"); // 容量（L）_リベートⅣ類対象総容量
		sSql.append(" ,REBATE5_SOUYOURYO \n"); // 容量（L）_リベートⅤ類対象総容量
		sSql.append(" ,REBATEO_SOUYOURYO \n"); // 容量（L）_リベート対象外総容量
		sSql.append(" ,PB_TOKUCYU_KBN \n"); // 特注指示区分
		sSql.append(" ,PB_TOKUCYU \n"); // PB OR 特注指示内容
		sSql.append(" ,HANBAI_BUMON_CD \n"); // 販売部門CD
		sSql.append(" ,HANBAI_BUMON_RNM \n"); // 販売部門名（略式）
		sSql.append(" ,HANBAI_SYUBETU_CD \n"); // 販売種別CD
		sSql.append(" ,HANBAI_SYUBETU_RNM \n"); // 販売種別名（略式）
		sSql.append(" ,HANBAI_BUNRUI_CD \n"); // 販売分類CD
		sSql.append(" ,HANBAI_BUNRUI_RNM \n"); // 販売分類名（略式）
		sSql.append(" ,EDI_HAISOUG_SEND_KB \n"); // EDI配送依頼(集約)送信区分
		sSql.append(") VALUES ( \n");
		sSql.append("   ? \n"); // 利用区分
		sSql.append(" , ? \n"); // 会社CD
		sSql.append(" , ? \n"); // 受注NO
		sSql.append(" , ? \n"); // 受注行NO
		sSql.append(" , ? \n"); // 蔵CD
		sSql.append(" , ? \n"); // 倉庫CD
		sSql.append(" , ? \n"); // 寄託者CD
		sSql.append(" , ? \n"); // 商品CD
		sSql.append(" , ? \n"); // 製品CD
		sSql.append(" , ? \n"); // 仕入先CD
		sSql.append(" , ? \n"); // 仕入品CD
		sSql.append(" , ? \n"); // 製品日付
		sSql.append(" , ? \n"); // 積荷伝票用ラインNO
		sSql.append(" , ? \n"); // ケース入数
		sSql.append(" , ? \n"); // バラ容量(L)
		sSql.append(" , ? \n"); // 重量(KG)_ケース
		sSql.append(" , ? \n"); // 重量(KG)_バラ
		sSql.append(" , ? \n"); // 運賃掛率(%)
		sSql.append(" , ? \n"); // 販売単価
		sSql.append(" , ? \n"); // 出荷数量_箱数
		sSql.append(" , ? \n"); // 出荷数量_セット数
		sSql.append(" , ? \n"); // 出荷数量_換算総セット数
		sSql.append(" , ? \n"); // 出荷重量（KG)
		sSql.append(" , ? \n"); // 販売額
		sSql.append(" , ? \n"); // 出荷対応区分
		sSql.append(" , ? \n"); // 扱い区分
		sSql.append(" , ? \n"); // 物品区分
		sSql.append(" , ? \n"); // 販売単価変更フラグ
		sSql.append(" , ? \n"); // 容量（L）_出荷総容量
		sSql.append(" , ? \n"); // 容量（L）_リベートⅠ類対象総容量
		sSql.append(" , ? \n"); // 容量（L）_リベートⅡ類対象総容量
		sSql.append(" , ? \n"); // 容量（L）_リベートⅢ類対象総容量
		sSql.append(" , ? \n"); // 容量（L）_リベートⅣ類対象総容量
		sSql.append(" , ? \n"); // 容量（L）_リベートⅤ類対象総容量
		sSql.append(" , ? \n"); // 容量（L）_リベート対象外総容量
		sSql.append(" , ? \n"); // 特注指示区分
		sSql.append(" , ? \n"); // PB OR 特注指示内容
		sSql.append(" , ? \n"); // 販売部門CD
		sSql.append(" , ? \n"); // 販売部門名（略式）
		sSql.append(" , ? \n"); // 販売種別CD
		sSql.append(" , ? \n"); // 販売種別名（略式）
		sSql.append(" , ? \n"); // 販売分類CD
		sSql.append(" , ? \n"); // 販売分類名（略式）
		sSql.append(" , ? \n"); // EDI配送依頼(集約)送信区分
		sSql.append(") \n");
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); // 利用区分（必須項目）
		bindList.add(cus.getKaisyaCd()); // 会社CD（必須項目）
		bindList.add(rec.getJyucyuNo()); // 受注NO（必須項目）
		bindList.add(rec.getInputLineNo()); // 受注行NO（必須項目）
		bindList.add(rec.getKuraCd()); // 蔵CD（必須項目）
		bindList.add(rec.getSoukoCd()); // 倉庫CD（固定値）
		bindList.add(cus.getKtksyCd()); // 寄託者CD（必須項目）
		bindList.add(rec.getShohinCd()); // 商品CD（必須項目）
		bindList.add(PbsUtil.getChar(rec.getSeihinCd())); // 製品CD（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getSiiresakiCd())); // 仕入先CD（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getSiirehinCd())); // 仕入品CD（NULLあり）
		bindList.add(rec.getSeihinDt()); // 製品日付（固定値）
		bindList.add(rec.getTumidenLineNo()); // 積荷伝票用ラインNO（必須項目）
		bindList.add(rec.getCaseIrisu()); // ケース入数（必須項目）
		bindList.add(rec.getBaraYouryo()); // バラ容量(L)（必須項目）
		bindList.add(rec.getWeightCase()); // 重量(KG)_ケース（必須項目）
		bindList.add(rec.getWeightBara()); // 重量(KG)_バラ（必須項目）
		bindList.add(rec.getUnchinKakeritu()); // 運賃掛率(%)（必須項目）
		bindList.add(rec.getHanbaiTanka()); // 販売単価（必須項目）
		bindList.add(rec.getSyukaSuCase()); // 出荷数量_箱数（必須項目）
		bindList.add(rec.getSyukaSuBara()); // 出荷数量_セット数（必須項目）
		bindList.add(rec.getSyukaAllBara()); // 出荷数量_換算総セット数（必須項目）
		bindList.add(rec.getSyukaAllWeigth()); // 出荷重量（KG)（必須項目）
		bindList.add(rec.getSyukaHanbaiKingaku()); // 販売額（必須項目）
		bindList.add(rec.getSyukaTaioKbn()); // 出荷対応区分（必須項目）
		bindList.add(rec.getAtukaiKbn()); // 扱い区分（必須項目）
		bindList.add(rec.getBupinKbn()); // 物品区分（必須項目）
		bindList.add(rec.getHtankaChgFlg()); // 販売単価変更フラグ（必須項目）
		bindList.add(rec.getSyukaSouyouryo()); // 容量（L）_出荷総容量（必須項目）
		bindList.add(rec.getRebate1Souyouryo()); // 容量（L）_リベートⅠ類対象総容量（必須項目）
		bindList.add(rec.getRebate2Souyouryo()); // 容量（L）_リベートⅡ類対象総容量（必須項目）
		bindList.add(rec.getRebate3Souyouryo()); // 容量（L）_リベートⅢ類対象総容量（必須項目）
		bindList.add(rec.getRebate4Souyouryo()); // 容量（L）_リベートⅣ類対象総容量（必須項目）
		bindList.add(rec.getRebate5Souyouryo()); // 容量（L）_リベートⅤ類対象総容量（必須項目）
		bindList.add(rec.getRebateoSouyouryo()); // 容量（L）_リベート対象外総容量（必須項目）
		bindList.add(PbsUtil.getChar(rec.getPbTokucyuKbn())); // 特注指示区分（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getPbTokucyu())); // PB OR 特注指示内容（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getHanbaiBumonCd())); // 販売部門CD（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getHanbaiBumonRnm())); // 販売部門名（略式）（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getHanbaiSyubetuCd())); // 販売種別CD（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getHanbaiSyubetuRnm())); // 販売種別名（略式）（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getHanbaiBunruiCd())); // 販売分類CD（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getHanbaiBunruiRnm())); // 販売分類名（略式）（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getEdiHaisougSendKb())); // EDI配送依頼(集約)送信区分（NULLあり）

		return sSql.toString();
	}

	/**
	 * 受注入力（予定出荷先別商品カテゴリデータ）新規SQL
	 *
	 * @param rec JuchuJuchuDataInCatRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlInsertCat(JuchuJuchuDataInCatRecord rec, List<String> bindList) {

		ComUserSession cus = getComUserSession();

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("INSERT INTO T_JYUCYU_JSKCTG ( \n");
		sSql.append("  RIYOU_KBN \n"); // 利用区分
		sSql.append(" ,KAISYA_CD \n"); // 会社CD
		sSql.append(" ,JYUCYU_NO \n"); // 受注NO
		sSql.append(" ,SHN_CTGR_LINE_NO \n"); // 受注カテゴリ行NO
		sSql.append(" ,KTKSY_CD \n"); // 寄託者CD
		sSql.append(" ,SEIHIN_CD \n"); // 製品CD
		sSql.append(" ,HANBAI_BUMON_CD \n"); // 販売部門CD
		sSql.append(" ,HANBAI_BUMON_RNM \n"); // 販売部門名（略式）
		sSql.append(" ,HANBAI_SYUBETU_CD \n"); // 販売種別CD
		sSql.append(" ,HANBAI_SYUBETU_RNM \n"); // 販売種別名（略式）
		sSql.append(" ,HANBAI_BUNRUI_CD \n"); // 販売分類CD
		sSql.append(" ,HANBAI_BUNRUI_RNM \n"); // 販売分類名（略式）
		sSql.append(" ,SYUZEI_CD \n"); // 酒税分類CD
		sSql.append(" ,SYUZEI_NM_RYAKU \n"); // 酒税分類名（略式）
		sSql.append(" ,TANE_CD \n"); // 種CD
		sSql.append(" ,TANE_NM_RYAKU \n"); // 種名称（略式）
		sSql.append(" ,SOZAI_KBN \n"); // 素材区分
		sSql.append(" ,COLOR_KBN \n"); // 色区分
		sSql.append(" ,HJISEKI_BUNRUI_KBN \n"); // 販売実績分類区分
		sSql.append(" ,VOL \n"); // 容量（L)
		sSql.append(" ,TANKA \n"); // 単価（円）
		sSql.append(" ,SYUKA_HON \n"); // 出荷本数計
		sSql.append(" ,SYUKA_VOL \n"); // 出荷容量計（L)
		sSql.append(" ,HANBAI_KINGAKU \n"); // 販売金額計（円）
		sSql.append(") VALUES ( \n");
		sSql.append("   ? \n"); // 利用区分
		sSql.append(" , ? \n"); // 会社CD
		sSql.append(" , ? \n"); // 受注NO
		sSql.append(" , ? \n"); // 受注カテゴリ行NO
		sSql.append(" , ? \n"); // 寄託者CD
		sSql.append(" , ? \n"); // 製品CD
		sSql.append(" , ? \n"); // 販売部門CD
		sSql.append(" , ? \n"); // 販売部門名（略式）
		sSql.append(" , ? \n"); // 販売種別CD
		sSql.append(" , ? \n"); // 販売種別名（略式）
		sSql.append(" , ? \n"); // 販売分類CD
		sSql.append(" , ? \n"); // 販売分類名（略式）
		sSql.append(" , ? \n"); // 酒税分類CD
		sSql.append(" , ? \n"); // 酒税分類名（略式）
		sSql.append(" , ? \n"); // 種CD
		sSql.append(" , ? \n"); // 種名称（略式）
		sSql.append(" , ? \n"); // 素材区分
		sSql.append(" , ? \n"); // 色区分
		sSql.append(" , ? \n"); // 販売実績分類区分
		sSql.append(" , ? \n"); // 容量（L)
		sSql.append(" , ? \n"); // 単価（円）
		sSql.append(" , ? \n"); // 出荷本数計
		sSql.append(" , ? \n"); // 出荷容量計（L)
		sSql.append(" , ? \n"); // 販売金額計（円）
		sSql.append(") \n");
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); // 利用区分（必須項目）
		bindList.add(cus.getKaisyaCd()); // 会社CD（必須項目）
		bindList.add(rec.getJyucyuNo()); // 受注NO（必須項目）
		bindList.add(rec.getShnCtgrLineNo()); // 受注カテゴリ行NO（必須項目）
		bindList.add(cus.getKtksyCd()); // 寄託者CD（必須項目）
		bindList.add(rec.getSeihinCd()); // 製品CD（必須項目）
		bindList.add(rec.getHanbaiBumonCd()); // 販売部門CD（必須項目）
		bindList.add(rec.getHanbaiBumonRnm()); // 販売部門名（略式）（必須項目）
		bindList.add(rec.getHanbaiSyubetuCd()); // 販売種別CD（必須項目）
		bindList.add(rec.getHanbaiSyubetuRnm()); // 販売種別名（略式）（必須項目）
		bindList.add(rec.getHanbaiBunruiCd()); // 販売分類CD（必須項目）
		bindList.add(rec.getHanbaiBunruiRnm()); // 販売分類名（略式）（必須項目）
		bindList.add(rec.getSyuzeiCd()); // 酒税分類CD（必須項目）
		bindList.add(rec.getSyuzeiNmRyaku()); // 酒税分類名（略式）（必須項目）
		bindList.add(rec.getTaneCd()); // 種CD（必須項目）
		bindList.add(rec.getTaneNmRyaku()); // 種名称（略式）（必須項目）
		bindList.add(rec.getSozaiKbn()); // 素材区分（必須項目）
		bindList.add(rec.getColorKbn()); // 色区分（必須項目）
		bindList.add(rec.getHjisekiBunruiKbn()); // 販売実績分類区分（必須項目）
		bindList.add(rec.getVol()); // 容量（L)（必須項目）
		bindList.add(rec.getTanka()); // 単価（円）（必須項目）
		bindList.add(rec.getSyukaHon()); // 出荷本数計（必須項目）
		bindList.add(rec.getSyukaVol()); // 出荷容量計（L)（必須項目）
		bindList.add(rec.getHanbaiKingaku()); // 販売金額計（円）（必須項目）

		return sSql.toString();
	}


	/**
	 * 受注入力（受注データ／ヘッダー部）修正SQL
	 *
	 * @param rec JuchuJuchuDataInRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlUpdateHd(JuchuJuchuDataInRecord rec, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_JYUCYU_HD \n");
		sSql.append("SET \n");
		sSql.append("  RIYOU_KBN = ? \n"); // 利用区分
		sSql.append(" ,SYUKA_KBN = ? \n"); // 出荷先区分
		sSql.append(" ,SYUBETU_CD = ? \n"); // データ種別CD
		sSql.append(" ,KURA_CD = ? \n"); // 蔵CD (FK)
		sSql.append(" ,UNCHIN_KBN = ? \n"); // 運賃区分
		sSql.append(" ,UNSOTEN_CD = ? \n"); // 運送店CD
		sSql.append(" ,SYUKA_DT = ? \n"); // 出荷日(売上伝票発行予定日)
		sSql.append(" ,MINASI_DT = ? \n"); // ミナシ日付
		sSql.append(" ,CHACUNI_YOTEI_DT = ? \n"); // 着荷予定日
		sSql.append(" ,NIUKE_TIME_KBN = ? \n"); // 荷受時間区分
		sSql.append(" ,NIUKE_BIGIN_TIME = ? \n"); // 荷受時間_開始
		sSql.append(" ,NIUKE_END_TIME = ? \n"); // 荷受時間_終了
		sSql.append(" ,SENPO_HACYU_NO = ? \n"); // 先方発注NO
		sSql.append(" ,TATESN_CD = ? \n"); // 縦線CD (FK)
		sSql.append(" ,TANTOSYA_CD = ? \n"); // 担当者CD
		sSql.append(" ,TOKUYAKUTEN_CD = ? \n"); // 特約店CD
		sSql.append(" ,DEPO_CD = ? \n"); // デポCD
		sSql.append(" ,NIJITEN_CD = ? \n"); // 二次店CD
		sSql.append(" ,SANJITEN_CD = ? \n"); // 三次店CD
		sSql.append(" ,SYUHANTEN_CD = ? \n"); // 酒販店（統一）CD
		sSql.append(" ,DELIVERY_KBN = ? \n"); // 倉入・直送区分
		sSql.append(" ,SYUKA_SAKI_COUNTRY_CD = ? \n"); // 出荷先国CD
		sSql.append(" ,JIS_CD = ? \n"); // JISCD
		sSql.append(" ,UNCHIN_CNV_TANKA = ? \n"); // 引取運賃換算単価
		sSql.append(" ,TEKIYO_KBN1 = ? \n"); // 摘要区分 (01)
		sSql.append(" ,TEKIYO_NM1 = ? \n"); // 摘要内容 (01)
		sSql.append(" ,TEKIYO_KBN2 = ? \n"); // 摘要区分 (02)
		sSql.append(" ,TEKIYO_NM2 = ? \n"); // 摘要内容 (02)
		sSql.append(" ,TEKIYO_KBN3 = ? \n"); // 摘要区分 (03)
		sSql.append(" ,TEKIYO_NM3 = ? \n"); // 摘要内容 (03)
		sSql.append(" ,TEKIYO_KBN4 = ? \n"); // 摘要区分 (04)
		sSql.append(" ,TEKIYO_NM4 = ? \n"); // 摘要内容 (04)
		sSql.append(" ,TEKIYO_KBN5 = ? \n"); // 摘要区分 (05)
		sSql.append(" ,TEKIYO_NM5 = ? \n"); // 摘要内容 (05)
		sSql.append(" ,ATUKAI_KBN = ? \n"); // 扱い区分
		sSql.append(" ,LOW_HAISO_LOT = ? \n"); // 最低配送ロット数
		sSql.append(" ,SYUKA_SYONIN_NO = ? \n"); // 小ロット出荷承認申請NO
		sSql.append(" ,SYUKA_SURYO_BOX = ? \n"); // 出荷数量計_箱数
		sSql.append(" ,SYUKA_SURYO_SET = ? \n"); // 出荷数量計_セット数
		sSql.append(" ,SYUKA_KINGAKU_TOT = ? \n"); // 出荷金額計
		sSql.append(" ,JYURYO_TOT = ? \n"); // 重量計(KG)
		sSql.append(" ,SYUKA_YOURYO_TOT = ? \n"); // 容量計（L）_出荷容量計
		sSql.append(" ,REBATE1_YOURYO_TOT = ? \n"); // 容量計（L）_リベートⅠ類対象容量計
		sSql.append(" ,REBATE2_YOURYO_TOT = ? \n"); // 容量計（L）_リベートⅡ類対象容量計
		sSql.append(" ,REBATE3_YOURYO_TOT = ? \n"); // 容量計（L）_リベートⅢ類対象容量計
		sSql.append(" ,REBATE4_YOURYO_TOT = ? \n"); // 容量計（L）_リベートⅣ類対象容量計
		sSql.append(" ,REBATE5_YOURYO_TOT = ? \n"); // 容量計（L）_リベートⅤ類対象容量計
		sSql.append(" ,REBATEO_YOURYO_TOT = ? \n"); // 容量計（L）_リベート対象外容量計
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); // 利用区分（必須項目）
		bindList.add(rec.getSyukaKbn()); // 出荷先区分（必須項目）
		bindList.add(rec.getSyubetuCd()); // データ種別CD（必須項目）
		bindList.add(rec.getKuraCd()); // 蔵CD (FK)（必須項目）
		bindList.add(rec.getUnchinKbn()); // 運賃区分（必須項目）
		bindList.add(rec.getUnsotenCd()); // 運送店CD（必須項目）
		bindList.add(rec.getSyukaDt()); // 出荷日(売上伝票発行予定日)（必須項目）
		bindList.add(rec.getMinasiDt()); // ミナシ日付（必須項目）
		bindList.add(PbsUtil.getChar(rec.getChacuniYoteiDt())); // 着荷予定日（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getNiukeTimeKbn())); // 荷受時間区分（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getNiukeBiginTime())); // 荷受時間_開始（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getNiukeEndTime())); // 荷受時間_終了（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getSenpoHacyuNo())); // 先方発注NO（NULLあり）
		bindList.add(rec.getOrsTatesnCd()); // 縦線CD (FK)（必須項目）
		bindList.add(rec.getTantosyaCd()); // 担当者CD（必須項目）
		bindList.add(rec.getTokuyakutenCd()); // 特約店CD（必須項目）
		bindList.add(rec.getDepoCd()); // デポCD（必須項目）
		bindList.add(PbsUtil.getChar(rec.getNijitenCd())); // 二次店CD（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getSanjitenCd())); // 三次店CD（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getSyuhantenCd())); // 酒販店（統一）CD（NULLあり）
		bindList.add(rec.getDeliveryKbn()); // 倉入・直送区分（必須項目）
		bindList.add(rec.getSyukaSakiCountryCd()); // 出荷先国CD（必須項目）
		bindList.add(rec.getJisCd()); // JISCD（必須項目）
		bindList.add(rec.getUnchinCnvTanka()); // 引取運賃換算単価（必須項目）
		bindList.add(PbsUtil.getChar(rec.getTekiyoKbn1())); // 摘要区分 (01)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoNm1())); // 摘要内容 (01)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoKbn2())); // 摘要区分 (02)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoNm2())); // 摘要内容 (02)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoKbn3())); // 摘要区分 (03)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoNm3())); // 摘要内容 (03)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoKbn4())); // 摘要区分 (04)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoNm4())); // 摘要内容 (04)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoKbn5())); // 摘要区分 (05)（NULLあり）
		bindList.add(PbsUtil.getChar(rec.getTekiyoNm5())); // 摘要内容 (05)（NULLあり）
		bindList.add(rec.getAtukaiKbn()); // 扱い区分（必須項目）
		bindList.add(rec.getLowHaisoLot()); // 最低配送ロット数（必須項目）
		bindList.add(PbsUtil.getChar(rec.getSyukaSyoninNo())); // 小ロット出荷承認申請NO（NULLあり）
		bindList.add(rec.getSyukaSuryoBox()); // 出荷数量計_箱数（必須項目）
		bindList.add(rec.getSyukaSuryoSet()); // 出荷数量計_セット数（必須項目）
		bindList.add(rec.getSyukaKingakuTot()); // 出荷金額計（必須項目）
		bindList.add(rec.getJyuryoTot()); // 重量計(KG)（必須項目）
		bindList.add(rec.getSyukaYouryoTot()); // 容量計（L）_出荷容量計（必須項目）
		bindList.add(rec.getRebate1YouryoTot()); // 容量計（L）_リベートⅠ類対象容量計（必須項目）
		bindList.add(rec.getRebate2YouryoTot()); // 容量計（L）_リベートⅡ類対象容量計（必須項目）
		bindList.add(rec.getRebate3YouryoTot()); // 容量計（L）_リベートⅢ類対象容量計（必須項目）
		bindList.add(rec.getRebate4YouryoTot()); // 容量計（L）_リベートⅣ類対象容量計（必須項目）
		bindList.add(rec.getRebate5YouryoTot()); // 容量計（L）_リベートⅤ類対象容量計（必須項目）
		bindList.add(rec.getRebateoYouryoTot()); // 容量計（L）_リベート対象外容量計（必須項目）

		sSql.append("WHERE \n");
		sSql.append(" KAISYA_CD = ? \n"); // 会社CD
		sSql.append("AND JYUCYU_NO = ? \n"); // 受注NO
		bindList.add(rec.getKaisyaCd()); // 会社CD
		bindList.add(rec.getJyucyuNo()); // 受注NO

		return sSql.toString();
	}

	/**
	 * 受注入力（受注データ／ディテール部）修正SQL
	 *
	 * @param jyucyuNo
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlDeleteDt(String jyucyuNo, List<String> bindList) {

		ComUserSession cus = getComUserSession();

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("DELETE \n");
		sSql.append("FROM T_JYUCYU_DT JCUDT \n");
		sSql.append("WHERE \n");

		// 会社CD
		sSql.append(" JCUDT.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 受注NO
		sSql.append("AND JCUDT.JYUCYU_NO = ? \n");
		bindList.add(jyucyuNo);

		return sSql.toString();
	}

	/**
	 * 受注入力（予定出荷先別商品カテゴリデータ）修正SQL
	 *
	 * @param jyucyuNo
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlDeleteCat(String jyucyuNo, List<String> bindList) {

		ComUserSession cus = getComUserSession();

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("DELETE \n");
		sSql.append("FROM T_JYUCYU_JSKCTG JCUCAT \n");
		sSql.append("WHERE \n");

		// 会社CD
		sSql.append(" JCUCAT.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 受注NO
		sSql.append("AND JCUCAT.JYUCYU_NO = ? \n");
		bindList.add(jyucyuNo);

		return sSql.toString();
	}


	/**
	 * 受注入力（受注データ／ヘッダー部）抹消SQL
	 *
	 * @param rec JuchuJuchuDataInRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlErasureHd(JuchuJuchuDataInRecord rec, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_JYUCYU_HD \n");
		sSql.append("SET \n");
		sSql.append("  RIYOU_KBN = ? \n"); // 利用区分
		sSql.append(" ,SYUBETU_CD = ? \n"); // データ種別CD
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); // 利用区分（必須項目）
		bindList.add(rec.getSyubetuCd()); // データ種別CD（必須項目）

		sSql.append("WHERE \n");
		sSql.append(" KAISYA_CD = ? \n"); // 会社CD
		sSql.append("AND JYUCYU_NO = ? \n"); // 受注NO
		bindList.add(rec.getKaisyaCd()); // 会社CD
		bindList.add(rec.getJyucyuNo()); // 受注NO

		return sSql.toString();
	}

	/**
	 * 受注入力（受注データ／ディテール部）抹消SQL
	 *
	 * @param rec JuchuJuchuDataInDtRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlErasureDt(JuchuJuchuDataInDtRecord rec, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_JYUCYU_DT \n");
		sSql.append("SET \n");
		sSql.append("  RIYOU_KBN = ? \n"); // 利用区分
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); // 利用区分（必須項目）

		sSql.append("WHERE \n");
		sSql.append(" KAISYA_CD = ? \n"); // 会社CD
		sSql.append("AND JYUCYU_NO = ? \n"); // 受注NO
		sSql.append("AND INPUT_LINE_NO = ? \n"); // 受注行NO
		bindList.add(rec.getKaisyaCd()); // 会社CD
		bindList.add(rec.getJyucyuNo()); // 受注NO
		bindList.add(rec.getInputLineNo()); // 受注行NO

		return sSql.toString();
	}

	/**
	 * 受注入力（予定出荷先別商品カテゴリデータ）抹消SQL
	 *
	 * @param rec JuchuJuchuDataInCatRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlErasureCat(JuchuJuchuDataInCatRecord rec, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_JYUCYU_JSKCTG \n");
		sSql.append("SET \n");
		sSql.append("  RIYOU_KBN = ? \n"); // 利用区分
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); // 利用区分（必須項目）

		sSql.append("WHERE \n");
		sSql.append(" KAISYA_CD = ? \n"); // 会社CD
		sSql.append("AND JYUCYU_NO = ? \n"); // 受注NO
		sSql.append("AND SHN_CTGR_LINE_NO = ? \n"); // 受注カテゴリ行NO
		bindList.add(rec.getKaisyaCd()); // 会社CD
		bindList.add(rec.getJyucyuNo()); // 受注NO
		bindList.add(rec.getShnCtgrLineNo()); // 受注カテゴリ行NO

		return sSql.toString();
	}


	/**
	 * 受注入力（受注データ／ヘッダー部）復活SQL
	 *
	 * @param rec JuchuJuchuDataInRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlRebirthHd(JuchuJuchuDataInRecord rec, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_JYUCYU_HD \n");
		sSql.append("SET \n");
		sSql.append("  RIYOU_KBN = ? \n"); // 利用区分
		sSql.append(" ,SYUBETU_CD = ? \n"); // データ種別CD
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); // 利用区分（必須項目）
		bindList.add(rec.getSyubetuCd()); // データ種別CD（必須項目）

		sSql.append("WHERE \n");
		sSql.append(" KAISYA_CD = ? \n"); // 会社CD
		sSql.append("AND JYUCYU_NO = ? \n"); // 受注NO
		bindList.add(rec.getKaisyaCd()); // 会社CD
		bindList.add(rec.getJyucyuNo()); // 受注NO

		return sSql.toString();
	}

	/**
	 * 受注入力（受注データ／ディテール部）復活SQL
	 *
	 * @param rec JuchuJuchuDataInDtRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlRebirthDt(JuchuJuchuDataInDtRecord rec, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_JYUCYU_DT \n");
		sSql.append("SET \n");
		sSql.append("  RIYOU_KBN = ? \n"); // 利用区分
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); // 利用区分（必須項目）

		sSql.append("WHERE \n");
		sSql.append(" KAISYA_CD = ? \n"); // 会社CD
		sSql.append("AND JYUCYU_NO = ? \n"); // 受注NO
		sSql.append("AND INPUT_LINE_NO = ? \n"); // 受注行NO
		bindList.add(rec.getKaisyaCd()); // 会社CD
		bindList.add(rec.getJyucyuNo()); // 受注NO
		bindList.add(rec.getInputLineNo()); // 受注行NO

		return sSql.toString();
	}

	/**
	 * 受注入力（予定出荷先別商品カテゴリデータ）復活SQL
	 *
	 * @param rec JuchuJuchuDataInCatRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlRebirthCat(JuchuJuchuDataInCatRecord rec, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_JYUCYU_JSKCTG \n");
		sSql.append("SET \n");
		sSql.append("  RIYOU_KBN = ? \n"); // 利用区分
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); // 利用区分（必須項目）

		sSql.append("WHERE \n");
		sSql.append(" KAISYA_CD = ? \n"); // 会社CD
		sSql.append("AND JYUCYU_NO = ? \n"); // 受注NO
		sSql.append("AND SHN_CTGR_LINE_NO = ? \n"); // 受注カテゴリ行NO
		bindList.add(rec.getKaisyaCd()); // 会社CD
		bindList.add(rec.getJyucyuNo()); // 受注NO
		bindList.add(rec.getShnCtgrLineNo()); // 受注カテゴリ行NO

		return sSql.toString();
	}


	/**
	 * 受注入力（受注データ／ヘッダー部）の行ロックを実行する。
	 *
	 * @param ccGyoLock
	 * @param rec JuchuJuchuDataInRecord
	 * @throws ResourceBusyNowaitException
	 * @throws DeadLockException
	 * @throws DataNotFoundException
	 * @throws SQLException
	 */
	private void lockHd(Integer ccGyoLock, JuchuJuchuDataInRecord rec)
			throws ResourceBusyNowaitException, DeadLockException, DataNotFoundException {

		// ロック処理
		db_.setDbFileNm(TBL_T_JYUCYU_HD);
		category__.info("受注入力（受注データ／ヘッダー部） >> 修正処理(行ロック) >> bind");
		lockBindHd(ccGyoLock, rec);
		category__.info("受注入力（受注データ／ヘッダー部） >> 修正処理(行ロック) >> executeForLock");

		// 行ロック実行
		db_.executeForLock(ccGyoLock);
		if (!db_.next(ccGyoLock)) {
			db_.setGeneralErrorMsg("DataNotFoundException", ccGyoLock);
			throw new DataNotFoundException("Data Not Found!");
		}
	}

	/**
	 * 受注入力（受注データ／ディテール部）の行ロックを実行する。
	 *
	 * @param ccGyoLock
	 * @param rec JuchuJuchuDataInDtRecord
	 * @throws ResourceBusyNowaitException
	 * @throws DeadLockException
	 * @throws DataNotFoundException
	 * @throws SQLException
	 */
	private void lockDt(Integer ccGyoLock, JuchuJuchuDataInDtRecord rec)
			throws ResourceBusyNowaitException, DeadLockException, DataNotFoundException {

		// ロック処理
		db_.setDbFileNm(TBL_T_JYUCYU_DT);
		category__.info("受注入力（受注データ／ディテール部） >> 修正処理(行ロック) >> bind");
		lockBindDt(ccGyoLock, rec);
		category__.info("受注入力（受注データ／ディテール部） >> 修正処理(行ロック) >> executeForLock");

		// 行ロック実行
		db_.executeForLock(ccGyoLock);
		if (!db_.next(ccGyoLock)) {
			db_.setGeneralErrorMsg("DataNotFoundException", ccGyoLock);
			throw new DataNotFoundException("Data Not Found!");
		}
	}

	/**
	 * 受注入力（予定出荷先別商品カテゴリデータ）の行ロックを実行する。
	 *
	 * @param ccGyoLock
	 * @param rec JuchuJuchuDataInCatRecord
	 * @throws ResourceBusyNowaitException
	 * @throws DeadLockException
	 * @throws DataNotFoundException
	 * @throws SQLException
	 */
	private void lockCat(Integer ccGyoLock, JuchuJuchuDataInCatRecord rec)
			throws ResourceBusyNowaitException, DeadLockException, DataNotFoundException {

		// ロック処理
		db_.setDbFileNm(TBL_T_JUCYU_JSKCTG);
		category__.info("受注入力（受注データ／カテゴリデータ） >> 修正処理(行ロック) >> bind");
		lockBindCat(ccGyoLock, rec);
		category__.info("受注入力（受注データ／カテゴリデータ） >> 修正処理(行ロック) >> executeForLock");

		// 行ロック実行
		db_.executeForLock(ccGyoLock);
		if (!db_.next(ccGyoLock)) {
			db_.setGeneralErrorMsg("DataNotFoundException", ccGyoLock);
			throw new DataNotFoundException("Data Not Found!");
		}
	}


	/**
	 * 行ロック用SQLを取得する
	 * 　受注入力（受注データ／ヘッダー部）
	 *
	 * @return SQL
	 */
	private String sqlGyoLockHd() {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("SELECT \n");
		sSql.append("  JCUHD.KAISYA_CD \n"); // 会社CD
		sSql.append(" ,JCUHD.JYUCYU_NO \n"); // 受注NO
		sSql.append(" ,JCUHD.KOUSIN_NO \n"); // 更新回数
		sSql.append("FROM T_JYUCYU_HD JCUHD \n");
		sSql.append("WHERE \n");
		sSql.append("    JCUHD.KAISYA_CD = ? \n"); // 会社CD
		sSql.append("AND JCUHD.JYUCYU_NO = ? \n"); // 受注NO
		sSql.append("AND JCUHD.KOUSIN_NO = ? \n"); // 更新回数
		sSql.append("FOR UPDATE NOWAIT \n");
		return sSql.toString();
	}

	/**
	 * 行ロック用SQLを取得する
	 * 　受注入力（受注データ／ディテール部）
	 *
	 * @return SQL
	 */
	private String sqlGyoLockDt() {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("SELECT \n");
		sSql.append("  JCUDT.KAISYA_CD \n"); // 会社CD
		sSql.append(" ,JCUDT.JYUCYU_NO \n"); // 受注NO
		sSql.append(" ,JCUDT.INPUT_LINE_NO \n"); // 受注行NO
		sSql.append(" ,JCUDT.KOUSIN_NO \n"); // 更新回数
		sSql.append("FROM T_JYUCYU_DT JCUDT \n");
		sSql.append("WHERE \n");
		sSql.append("    JCUDT.KAISYA_CD = ? \n"); // 会社CD
		sSql.append("AND JCUDT.JYUCYU_NO = ? \n"); // 受注NO
		sSql.append("AND JCUDT.INPUT_LINE_NO = ? \n"); // 受注行NO
		sSql.append("AND JCUDT.KOUSIN_NO = ? \n"); // 更新回数
		sSql.append("FOR UPDATE NOWAIT \n");
		return sSql.toString();
	}

	/**
	 * 行ロック用SQLを取得する
	 * 　受注入力（予定出荷先別商品カテゴリデータ）
	 *
	 * @return SQL
	 */
	private String sqlGyoLockCat() {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("SELECT \n");
		sSql.append("  JCUCAT.KAISYA_CD \n"); // 会社CD
		sSql.append(" ,JCUCAT.JYUCYU_NO \n"); // 受注NO
		sSql.append(" ,JCUCAT.SHN_CTGR_LINE_NO \n"); // 受注カテゴリ行NO
		sSql.append(" ,JCUCAT.KOUSIN_NO \n"); // 更新回数
		sSql.append("FROM T_JYUCYU_JSKCTG JCUCAT \n");
		sSql.append("WHERE \n");
		sSql.append("    JCUCAT.KAISYA_CD = ? \n"); // 会社CD
		sSql.append("AND JCUCAT.JYUCYU_NO = ? \n"); // 受注NO
		sSql.append("AND JCUCAT.SHN_CTGR_LINE_NO = ? \n"); // 受注カテゴリ行NO
		sSql.append("AND JCUCAT.KOUSIN_NO = ? \n"); // 更新回数
		sSql.append("FOR UPDATE NOWAIT \n");
		return sSql.toString();
	}



	/**
	 * 行ロックSQLにバインド変数を設定する
	 * 　受注入力（受注データ／ヘッダー部）
	 *
	 * @param ccGyoLock
	 * @param rec JuchuJuchuDataInRecord
	 */
	private void lockBindHd(Integer ccGyoLock, JuchuJuchuDataInRecord rec) {
		int i = 0;

		db_.setString(ccGyoLock, ++i, rec.getKaisyaCd()); // 会社CD
		db_.setString(ccGyoLock, ++i, rec.getJyucyuNo()); // 受注NO
		db_.setString(ccGyoLock, ++i, rec.getKosnNo()); // 更新回数
	}

	/**
	 * 行ロックSQLにバインド変数を設定する
	 * 　受注入力（受注データ／ディテール部）
	 *
	 * @param ccGyoLock
	 * @param rec JuchuJuchuDataInDtRecord
	 */
	private void lockBindDt(Integer ccGyoLock, JuchuJuchuDataInDtRecord rec) {
		int i = 0;

		db_.setString(ccGyoLock, ++i, rec.getKaisyaCd()); // 会社CD
		db_.setString(ccGyoLock, ++i, rec.getJyucyuNo()); // 受注NO
		db_.setString(ccGyoLock, ++i, rec.getInputLineNo()); // 受注行NO
		db_.setString(ccGyoLock, ++i, rec.getKosnNo()); // 更新回数
	}

	/**
	 * 行ロックSQLにバインド変数を設定する
	 * 　受注入力（予定出荷先別商品カテゴリデータ）
	 *
	 * @param ccGyoLock
	 * @param rec JuchuJuchuDataInCatRecord
	 */
	private void lockBindCat(Integer ccGyoLock, JuchuJuchuDataInCatRecord rec) {
		int i = 0;

		db_.setString(ccGyoLock, ++i, rec.getKaisyaCd()); // 会社CD
		db_.setString(ccGyoLock, ++i, rec.getJyucyuNo()); // 受注NO
		db_.setString(ccGyoLock, ++i, rec.getShnCtgrLineNo()); // 受注カテゴリ行NO
		db_.setString(ccGyoLock, ++i, rec.getKosnNo()); // 更新回数
	}

	/**
	 * 製品構成マスターを使って分解したリストを取得
	 *
	 * @param editDtList
	 * @return JuchuJuchuDataInCatList
	 * @throws KitException
	 * @throws DataNotFoundException
	 */
	private JuchuJuchuDataInCatList createCatList(JuchuJuchuDataInDtList editDtList) throws DataNotFoundException, KitException {
		// カテゴリデータリスト
		JuchuJuchuDataInCatList catList = new JuchuJuchuDataInCatList();
		JuchuJuchuDataInCatList tmpCatList = new JuchuJuchuDataInCatList(); // 作業用

		// 検索結果を格納するレコード
		PbsRecord[] records_ = null;

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD
		String ktksyCd = cus.getKtksyCd(); // 寄託者CD

		CategoryDataCommonService commonServ = new CategoryDataCommonService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		for (PbsRecord dtTmp : editDtList) {
			// ディテール部レコード
			JuchuJuchuDataInDtRecord dtRec = (JuchuJuchuDataInDtRecord) dtTmp;

			// 未入力行は無視する
			if (dtRec.isEmpty()) {
				continue;
			}

			// 製品の場合のみカテゴリデータを作成する
			if (ComUtil.isSeihin(dtRec.getBupinKbn())) {
				// 共通部リスト（構成リストに分解）
				tmpCatList = new JuchuJuchuDataInCatList();
				records_ = commonServ.getListCommonCategoryData(dtRec.getRiyouKbn(), kaisyaCd, ktksyCd, dtRec.getSeihinCd());
				if (records_ != null) {
					tmpCatList = new JuchuJuchuDataInCatList(records_);
					catList.addAll(tmpCatList);
				}

				for (PbsRecord catTmp : tmpCatList) {
					JuchuJuchuDataInCatRecord catRec = (JuchuJuchuDataInCatRecord) catTmp;

					// 受注NO
					catRec.setJyucyuNo(dtRec.getJyucyuNo());

					// 出荷本数計＝[DT:出荷数量_換算総セット数]x[CAT:構成製品利用定数]
					String syukaHon = SU_ZERO;
					syukaHon = PbsUtil.sToSMultiplication(dtRec.getSyukaAllBara(), catRec.getRiyouTeisu());
					// 小数点第1位で四捨五入
					catRec.setSyukaHon(
						(PbsUtil.getZeroSuppress(((new BigDecimal(syukaHon)).setScale(0, BigDecimal.ROUND_HALF_UP)).toString())));

					// 出荷容量計（L）＝[CAT:出荷本数計]x[CAT:容量（L）]
					String syukaVol = YOURYO_ZERO;
					syukaVol = PbsUtil.sToSMultiplication(syukaHon, catRec.getVol());
					// 小数点第4位で四捨五入
					catRec.setSyukaVol(
						(PbsUtil.getZeroSuppress(((new BigDecimal(syukaVol)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString())));

					// 販売金額計（円）＝[CAT:出荷本数計]x[CAT:単価（円）]
					String hanbaiKingaku = GK_ZERO;
					hanbaiKingaku = PbsUtil.sToSMultiplication(syukaHon, catRec.getTanka());
					// 小数点第3位で四捨五入
					catRec.setHanbaiKingaku(
						(PbsUtil.getZeroSuppress(((new BigDecimal(hanbaiKingaku)).setScale(2, BigDecimal.ROUND_HALF_UP)).toString())));
				}
			}
		}

		// 受注カテゴリ行NO
		catList.setShnCtgrLineNos();

		return catList;
	}


	/**
	 * 更新処理
	 * 　蔵本データ／ヘッダー部の受注NOを削除する（受注キャンセル時）
	 *
	 * @param editList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean updateKuracHd(JuchuJuchuDataInList editList)
		throws DataNotFoundException, UpdateRowException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("蔵本データ／ヘッダー部更新処理 START");

		boolean res = true;

		for (int i = 0; i < editList.size(); i++) {
			JuchuJuchuDataInRecord rec = (JuchuJuchuDataInRecord) editList.get(i);

			// 受注NO
			String jyucyuNo = rec.getJyucyuNo();

			// 蔵直データでない場合はスキップ
			if (!PbsUtil.isEqual(KURAC_CHAR, jyucyuNo.substring(1, 2))) {
				continue;
			}

			try {

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("蔵本データ／ヘッダー部 >> 更新処理 >> SQL文生成");
				String strSql = getSqlUpdateKuracHd(rec, bindList, jyucyuNo);
				category__.info("蔵本データ／ヘッダー部 >> 更新処理 >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// UPDATE実行
				db_.setDbFileNm(TBL_T_KURAC_HD);
				category__.info("蔵本データ／ヘッダー部 >> 更新処理 >> insert");
				db_.executeUpdate(true); // 複数行の処理を有効

			} catch (DataNotFoundException e) {
				setErrorMessageId(e);
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (UpdateRowException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (DeadLockException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (UniqueKeyViolatedException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			} catch (SQLException e) {
				setErrorMessageId(e, db_.getDbFileNm());
				category__.warn(e.getMessage());
				rec.setHasError(true);
				res = false;
				throw e; // そのまま例外を送出
			}
		}

		category__.info("蔵本データ／ヘッダー部更新処理 END");

		return res;
	}


	/**
	 * 蔵本データ／ヘッダー部更新SQL
	 *
	 * @param rec JuchuJuchuDataInRecord
	 * @param bindList
	 * @param jyucyuNo
	 * @return SQL
	 */
	private String getSqlUpdateKuracHd(JuchuJuchuDataInRecord rec, List<String> bindList, String jyucyuNo) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_KURAC_HD \n");
		sSql.append("SET \n");
		sSql.append(" JYUCYU_NO = NULL \n"); // 黄桜受注NO
		sSql.append("WHERE \n");
		sSql.append(" KAISYA_CD = ? \n"); // 会社CD
		sSql.append("AND JYUCYU_NO = ? \n"); // 黄桜受注NO
		bindList.add(rec.getKaisyaCd()); // 会社CD
		bindList.add(jyucyuNo); // 受注NO

		return sSql.toString();
	}
}
