package kit.kurac.KuraChokuDataIn;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.BindList;
import fb.com.BindString;
import fb.com.ComHaitaService;
import fb.com.ComUserSession;
import fb.inf.KitRrkUpdateService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.DeadLockException;
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
public class KuracKuraChokuDataIn_UpdateService extends KitRrkUpdateService {

	/**
	 *
	 */
	/** シリアルID */
	private static final long serialVersionUID = -6910161067077368282L;

	/** クラス名. */
	private static String className__ = KuracKuraChokuDataIn_UpdateService.class.getName();
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
	public KuracKuraChokuDataIn_UpdateService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
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
	 * 　蔵元入力（蔵元データ／ヘッダー部）・蔵元入力（蔵元データ／ディテール部）
	 * 　を同一トランザクションで処理
	 *
	 * @param editList
	 * @param editDtList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	public boolean insert(KuracKuraChokuDataInList editList, KuracKuraChokuDataInDtList editDtList) {

		category__.info("蔵元入力新規処理 START");

		boolean res = true;

		db_.setTransaction();
		// 基盤側で操作ログを出力する際にはtrueを与える
		db_.setTranLog(true);

		try {
			// ==================================================
			// 蔵元入力（蔵元データ／ヘッダー部）
			// ==================================================
			// DB処理
			this.insertHd(editList);

			// ==================================================
			// 蔵元入力（蔵元データ／ディテール部）
			// ==================================================
			// DB処理
			this.insertDt(editDtList);


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
		} finally {
			if (res) {
				db_.commit();
			} else {
				db_.rollback();
			}
		}

		category__.info("蔵元入力新規処理 END");

		return res;
	}


	/**
	 * 新規処理（INSERT）
	 * 　蔵元入力（蔵元データ／ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean insertHd(KuracKuraChokuDataInList editList)
		throws DataNotFoundException, UpdateRowException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("蔵元入力（蔵元データ／ヘッダー部）新規処理（INSERT） START");

		boolean res = true;

		for (int i = 0; i < editList.size(); i++) {
			KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) editList.get(i);

			// 未入力行は無視する
			if (!rec.getIsModified()) {
				continue;
			}

			try {

				BindList bindList = new BindList();

				// SQLを取得
				category__.info("蔵元入力（蔵元データ／ヘッダー部） >> 新規処理（INSERT） >> SQL文生成");
				String strSql = getSqlInsertHd(rec, bindList);
				category__.info("蔵元入力（蔵元データ／ヘッダー部） >> 新規処理（INSERT） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (BindString bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// INSERT実行
				db_.setDbFileNm(TBL_T_KURAC_HD);
				category__.info("蔵元入力（蔵元データ／ヘッダー部） >> 新規処理（INSERT） >> insert");
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

		category__.info("蔵元入力（蔵元データ／ヘッダー部）新規処理（INSERT） END");

		return res;
	}


	/**
	 * 新規処理（INSERT）
	 * 　蔵元入力（蔵元データ／ディテール部）
	 *
	 * @param editDtList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean insertDt(KuracKuraChokuDataInDtList editDtList)
		throws DataNotFoundException, UpdateRowException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("蔵元入力（蔵元データ／ディテール部）新規処理（INSERT） START");

		boolean res = true;

		for (int i = 0; i < editDtList.size(); i++) {
			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

			// 未入力行は無視する
			if (rec.isEmpty() || PbsUtil.isEmpty(rec.getShohinCd())) {
				continue;
			}

			try {

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("蔵元入力（蔵元データ／ディテール部） >> 新規処理（INSERT） >> SQL文生成");
				String strSql = getSqlInsertDt(rec, bindList);
				category__.info("蔵元入力（蔵元データ／ディテール部） >> 新規処理（INSERT） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// INSERT実行
				db_.setDbFileNm(TBL_T_KURAC_DT);
				category__.info("蔵元入力（蔵元データ／ディテール部） >> 新規処理（INSERT） >> insert");
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

		category__.info("蔵元入力（蔵元データ／ディテール部）新規処理（INSERT） END");

		return res;
	}


	/**
	 * 抹消処理
	 * 　蔵元入力（蔵元データ／ヘッダー部）・蔵元入力（蔵元データ／ディテール部）
	 * 　を同一トランザクションで処理
	 *
	 * @param editList
	 * @param editDtList
	 * @param initCatList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	public boolean erasure(KuracKuraChokuDataInList editList, KuracKuraChokuDataInDtList editDtList) {

		category__.info("蔵元入力抹消処理 START");

		boolean res = true;

		db_.setTransaction();
		// 基盤側で操作ログを出力する際にはtrueを与える
		db_.setTranLog(true);

		try {
			// ==================================================
			// 蔵元入力（蔵元データ／ヘッダー部）
			// ==================================================
			// DB処理
			this.erasureHd(editList);

			// ==================================================
			// 蔵元入力（蔵元データ／ディテール部）
			// ==================================================
			// DB処理
			this.erasureDt(editDtList);

			KuracKuraChokuDataInRecord hdRec = (KuracKuraChokuDataInRecord) editList.getFirstRecord();
			// 受注No
			String jyuchuNo = hdRec.getJyucyuNo();
			if (!PbsUtil.isEmpty(jyuchuNo)) {
				// 受注情報を更新
				this.updateJyuchuInfo(hdRec);
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
		} finally {
			if (res) {
				db_.commit();
			} else {
				db_.rollback();
			}
		}

		category__.info("蔵元入力抹消処理 END");

		return res;
	}


	/**
	 * 抹消処理（UPDATE）
	 * 　蔵元入力（蔵元データ／ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean erasureHd(KuracKuraChokuDataInList editList)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("蔵元入力（蔵元データ／ヘッダー部）抹消処理（UPDATE） START");

		boolean res = true;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLockHd());
		category__.debug("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		for (int i = 0; i < editList.size(); i++) {

			// 「行目」取得：メッセージ表示用
			KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) editList.get(i);

			try {

				// 行ロック実行
				lockHd(ccGyoLock, rec);

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("蔵元入力（蔵元データ／ヘッダー部） >> 抹消処理（UPDATE） >> SQL文生成");
				String strSql = getSqlErasureHd(rec, bindList);
				category__.info("蔵元入力（蔵元データ／ヘッダー部） >> 抹消処理（UPDATE） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// UPDATE実行
				db_.setDbFileNm(TBL_T_JYUCYU_HD);
				category__.debug("蔵元入力（蔵元データ／ヘッダー部） >> 抹消処理（UPDATE） >> update");
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

		category__.info("蔵元入力（蔵元データ／ヘッダー部）抹消処理（UPDATE） END");

		return res;
	}


	/**
	 * 抹消処理（UPDATE）
	 * 　蔵元入力（蔵元データ／ディテール部）
	 *
	 * @param editDtList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean erasureDt(KuracKuraChokuDataInDtList editDtList)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("蔵元入力（蔵元データ／ディテール部）抹消処理（UPDATE） START");

		boolean res = true;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLockDt());
		category__.debug("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		for (int i = 0; i < editDtList.size(); i++) {

			// 「行目」取得：メッセージ表示用
			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

			// 未入力行は無視する
			if (rec.isEmpty()) {
				continue;
			}

			try {

				// 行ロック実行
				lockDt(ccGyoLock, rec);

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("蔵元入力（蔵元データ／ディテール部） >> 抹消処理（UPDATE） >> SQL文生成");
				String strSql = getSqlErasureDt(rec, bindList);
				category__.info("蔵元入力（蔵元データ／ディテール部） >> 抹消処理（UPDATE） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// UPDATE実行
				db_.setDbFileNm(TBL_T_JYUCYU_DT);
				category__.debug("蔵元入力（蔵元データ／ディテール部） >> 抹消処理（UPDATE） >> update");
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

		category__.info("蔵元入力（蔵元データ／ディテール部）抹消処理（UPDATE） END");

		return res;
	}


	/**
	 * 受注情報を更新
	 * @param rec
	 */
	private void updateJyuchuInfo(KuracKuraChokuDataInRecord rec)
			throws DataNotFoundException, UpdateRowException,ResourceBusyNowaitException, DeadLockException,
					UniqueKeyViolatedException, SQLException {

		// 受注データヘッダー部を更新
		this.updateJyuChuHd(rec);
		// 受注データディテール部を更新
		this.updateJyuChuDt(rec);
		// 受注データｶﾃｺﾞﾘﾃﾞｰﾀを更新
		this.updateJyuChuJskCtg(rec);
		// 蔵直ヘッダー部受注関連データを更新
		this.updateHdByJyuchu(rec);
	}

	/**
	 * 修正処理（UPDATE）
	 * 　蔵元入力（受注データ／ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean updateJyuChuHd(KuracKuraChokuDataInRecord rec)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("蔵元入力（受注データ／ヘッダー部）修正処理（UPDATE） START");

		boolean res = true;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLockJyuchuHd());
		category__.info("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		try {

			// 行ロック実行
			lockJyuchuHd(ccGyoLock, rec);

			List<String> bindList = new ArrayList<String>();

			// SQLを取得
			category__.info("蔵元入力（受注データ／ヘッダー部） >> 修正処理（UPDATE） >> SQL文生成");
			String strSql = getSqlUpdateJyuchuHd(rec, bindList);
			category__.info("蔵元入力（受注データ／ヘッダー部） >> 修正処理（UPDATE） >> prepare");
			this.db_.prepare(strSql);

			int ii = 0;
			for (String bindStr : bindList) {
				db_.setString(++ii, bindStr);
			}

			// UPDATE実行
			db_.setDbFileNm(TBL_T_JYUCYU_HD);
			category__.debug("蔵元入力（受注データ／ヘッダー部） >> 修正処理（UPDATE） >> update");
			db_.executeUpdate();

		} catch (DataNotFoundException e) {
//			setErrorMessageId(e);
//			category__.warn(e.getMessage());
//			rec.setHasError(HAS_ERROR_TRUE);
//			res = false;
//			throw e; // そのまま例外を送出
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

		category__.info("蔵元入力（受注データ／ヘッダー部）修正処理（UPDATE） END");

		return res;
	}


	/**
	 * 修正処理（UPDATE）
	 * 　蔵元入力（受注データ／ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean updateJyuChuDt(KuracKuraChokuDataInRecord rec)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("蔵元入力（受注データ／ヘッダー部）修正処理（UPDATE） START");

		boolean res = true;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLockJyuchuDt());
		category__.info("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		try {

			// 行ロック実行
			lockJyuchuDt(ccGyoLock, rec);

			List<String> bindList = new ArrayList<String>();

			// SQLを取得
			category__.info("蔵元入力（受注データ／ヘッダー部） >> 修正処理（UPDATE） >> SQL文生成");
			String strSql = getSqlUpdateJyuchuDt(rec, bindList);
			category__.info("蔵元入力（受注データ／ディテール部） >> 修正処理（UPDATE） >> prepare");
			this.db_.prepare(strSql);

			int ii = 0;
			for (String bindStr : bindList) {
				db_.setString(++ii, bindStr);
			}

			// UPDATE実行
			db_.setDbFileNm(TBL_T_JYUCYU_DT);
			category__.debug("蔵元入力（受注データ／ディテール部） >> 修正処理（UPDATE） >> update");
			db_.executeUpdate();

		} catch (DataNotFoundException e) {
//			setErrorMessageId(e);
//			category__.warn(e.getMessage());
//			rec.setHasError(HAS_ERROR_TRUE);
//			res = false;
//			throw e; // そのまま例外を送出
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

		category__.info("蔵元入力（受注データ／ディテール部）修正処理（UPDATE） END");

		return res;
	}


	/**
	 * 修正処理（UPDATE）
	 * 　蔵元入力（受注データ／ｶﾃｺﾞﾘﾃﾞｰﾀ）
	 *
	 * @param editList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean updateJyuChuJskCtg(KuracKuraChokuDataInRecord rec)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("蔵元入力（受注データ／ｶﾃｺﾞﾘﾃﾞｰﾀ）修正処理（UPDATE） START");

		boolean res = true;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLockJyuchuJskCtg());
		category__.info("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		try {

			// 行ロック実行
			lockJyuchuJskCtg(ccGyoLock, rec);

			List<String> bindList = new ArrayList<String>();

			// SQLを取得
			category__.info("蔵元入力（受注データ／ｶﾃｺﾞﾘﾃﾞｰﾀ） >> 修正処理（UPDATE） >> SQL文生成");
			String strSql = getSqlUpdateJyuchuJskCtg(rec, bindList);
			category__.info("蔵元入力（受注データ／ｶﾃｺﾞﾘﾃﾞｰﾀ） >> 修正処理（UPDATE） >> prepare");
			this.db_.prepare(strSql);

			int ii = 0;
			for (String bindStr : bindList) {
				db_.setString(++ii, bindStr);
			}

			// UPDATE実行
			db_.setDbFileNm(TBL_T_JUCYU_JSKCTG);
			category__.debug("蔵元入力（受注データ／ｶﾃｺﾞﾘﾃﾞｰﾀ） >> 修正処理（UPDATE） >> update");
			db_.executeUpdate();

		} catch (DataNotFoundException e) {
//			setErrorMessageId(e);
//			category__.warn(e.getMessage());
//			rec.setHasError(HAS_ERROR_TRUE);
//			res = false;
//			throw e; // そのまま例外を送出
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

		category__.info("蔵元入力（蔵元データ／ｶﾃｺﾞﾘﾃﾞｰﾀ）修正処理（UPDATE） END");

		return res;
	}

	/**
	 * 修正処理
	 * 　蔵元入力（蔵元データ／ヘッダー部）・蔵元入力（蔵元データ／ディテール部）
	 * 　を同一トランザクションで処理
	 *
	 * @param editList
	 * @param editDtList
	 * @param initDtList
	 * @param initCatList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	public boolean update(KuracKuraChokuDataInList editList, KuracKuraChokuDataInDtList editDtList,
			KuracKuraChokuDataInDtList initDtList) {

		category__.info("蔵元入力修正処理 START");

		boolean res = true;

		db_.setTransaction();
		// 基盤側で操作ログを出力する際にはtrueを与える
		db_.setTranLog(true);

		try {
			// ==================================================
			// 蔵元入力（蔵元データ／ヘッダー部）
			// ==================================================
			// DB処理
			this.updateHd(editList); // UPDATE

			// 蔵元NO
			KuracKuraChokuDataInRecord hdRec = (KuracKuraChokuDataInRecord) editList.getFirstRecord();
			String kuradataNo = hdRec.getKuradataNo();

			// ==================================================
			// 蔵元入力（蔵元データ／ディテール部）
			// ==================================================
			// UPDATEをDELETE/INSERTで行うための準備
			// DELETE用チェック
			boolean hasOldData = false;
			for (PbsRecord tmp : initDtList) {
				KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) tmp;
				if (!rec.isEmpty()) {
					hasOldData = true;
				}
			}
			// INSERT用チェック
			boolean hasNewData = false;
			for (PbsRecord tmp : editDtList) {
				KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) tmp;
				if (!rec.isEmpty()) {
					hasNewData = true;
				}
			}

			// DB処理
			if (hasOldData) {
				this.deleteDt(kuradataNo); // DELETE
			}
			if (hasNewData) {
				this.insertDt(editDtList); // INSERT
			}

			// 受注No
			String jyuchuNo = hdRec.getJyucyuNo();
			if (!PbsUtil.isEmpty(jyuchuNo)) {
				// 受注情報を更新
				this.updateJyuchuInfo(hdRec);
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
		} finally {
			if (res) {
				db_.commit();
			} else {
				db_.rollback();
			}
		}

		category__.info("蔵元入力修正処理 END");

		return res;
	}


	/**
	 * 修正処理（UPDATE）
	 * 　蔵元入力（蔵元データ／ヘッダー部）
	 *
	 * @param editList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean updateHd(KuracKuraChokuDataInList editList)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("蔵元入力（蔵元データ／ヘッダー部）修正処理（UPDATE） START");

		boolean res = true;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLockHd());
		category__.info("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		for (int i = 0; i < editList.size(); i++) {

			// 「行目」取得：メッセージ表示用
			KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) editList.get(i);

			try {

				// 行ロック実行
				lockHd(ccGyoLock, rec);

				BindList bindList = new BindList();

				// SQLを取得
				category__.info("蔵元入力（蔵元データ／ヘッダー部） >> 修正処理（UPDATE） >> SQL文生成");
				String strSql = getSqlUpdateHd(rec, bindList);
				category__.info("蔵元入力（蔵元データ／ヘッダー部） >> 修正処理（UPDATE） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (BindString bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// UPDATE実行
				db_.setDbFileNm(TBL_T_KURAC_HD);
				category__.debug("蔵元入力（蔵元データ／ヘッダー部） >> 修正処理（UPDATE） >> update");
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

		category__.info("蔵元入力（蔵元データ／ヘッダー部）修正処理（UPDATE） END");

		return res;
	}



	/**
	 * 修正処理（UPDATE）
	 * 　蔵元入力（蔵元データ／ヘッダー部）
	 * 　蔵直ヘッダー部受注関連データを更新
	 * @param editList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean updateHdByJyuchu(KuracKuraChokuDataInRecord rec)
			throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("蔵元入力（蔵元データ／ヘッダー部）受注関連修正処理（UPDATE） START");

		boolean res = true;

		// 行ロックSQLを取得
//		category__.info("prepare ⇒ ccGyoLock");
//		Integer ccGyoLock = db_.prepare(true, sqlGyoLockHd());
//		category__.info("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		try {

			// 行ロック実行
			// lockHd(ccGyoLock, rec);

			List<String> bindList = new ArrayList<String>();

			// SQLを取得
			category__.info("蔵元入力（蔵元データ／ヘッダー部） >> 受注関連修正処理（UPDATE） >> SQL文生成");
			String strSql = getSqlUpdateHdByJyuchu(rec, bindList);
			category__.info("蔵元入力（蔵元データ／ヘッダー部） >> 受注関連修正処理（UPDATE） >> prepare");
			this.db_.prepare(strSql);

			int ii = 0;
			for (String bindStr : bindList) {
				db_.setString(++ii, bindStr);
			}

			// UPDATE実行
			db_.setDbFileNm(TBL_T_KURAC_HD);
			category__.debug("蔵元入力（蔵元データ／ヘッダー部） >> 受注関連修正処理（UPDATE） >> update");
			// 複数行の処理
			db_.executeUpdate(true);

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
//		} catch (ResourceBusyNowaitException e) {
//			setErrorMessageId(e, db_.getDbFileNm());
//			category__.warn(e.getMessage());
//			rec.setHasError(HAS_ERROR_TRUE);
//			res = false;
//			throw e; // そのまま例外を送出
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

		category__.info("蔵元入力（蔵元データ／ヘッダー部）受注関連修正処理（UPDATE） END");

		return res;
	}
	/**
	 * 修正処理（DELETE）
	 * 　蔵元入力（蔵元データ／ディテール部）
	 *
	 * @param jyucyuNo
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean deleteDt(String kuradataNo)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("蔵元入力（蔵元データ／ディテール部）修正処理（DELETE） START");

		boolean res = true;

		try {

			List<String> bindList = new ArrayList<String>();

			// SQLを取得
			category__.info("蔵元入力（蔵元データ／ディテール部） >> 修正処理（DELETE） >> SQL文生成");
			String strSql = getSqlDeleteDt(kuradataNo, bindList);
			category__.info("蔵元入力（蔵元データ／ディテール部） >> 修正処理（DELETE） >> prepare");
			this.db_.prepare(strSql);

			int ii = 0;
			for (String bindStr : bindList) {
				db_.setString(++ii, bindStr);
			}

			// DELETE実行
			db_.setDbFileNm(TBL_T_KURAC_DT);
			category__.info("蔵元入力（蔵元データ／ディテール部） >> 修正処理（DELETE） >> delete");
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

		category__.info("蔵元入力（蔵元データ／ディテール部）修正処理（DELETE） END");

		return res;
	}

	/**
	 * 蔵元入力（蔵元データ／ヘッダー部）新規SQL
	 *
	 * @param rec JuchuJuchuDataInRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlInsertHd(KuracKuraChokuDataInRecord rec, BindList bindList) {

		ComUserSession cus = getComUserSession();

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("INSERT INTO T_KURAC_HD  ( \n");
		sSql.append("  RIYOU_KBN               \n"); // 利用区分
		sSql.append(" ,KAISYA_CD               \n"); // 会社CD (FK)
		sSql.append(" ,KURADATA_NO             \n"); // 蔵直ﾃﾞｰﾀ連番
		sSql.append(" ,MOUSIKOMI_NO            \n"); // 申込み受付NO
		sSql.append(" ,JIGYOSYO_KBN            \n"); // 黄桜事業所区分
		sSql.append(" ,SHOHIN_GRP_CD           \n"); // 蔵直商品ｸﾞﾙｰﾌﾟCD
		sSql.append(" ,SYUBETU_CD              \n"); // データ種別CD
		sSql.append(" ,KURADEN_NO              \n"); // 整理NO
		sSql.append(" ,TODOKESAKI_LINE_NO      \n"); // 届先ﾗｲﾝNO
		sSql.append(" ,TEISEI_KURADATA_NO      \n"); // 訂正時訂正元蔵直ﾃﾞｰﾀ連番
		sSql.append(" ,TAKUHAI_BILL_KBN        \n"); // 宅配伝票区分
		sSql.append(" ,INPUT_KBN               \n"); // 入力情報区分
		sSql.append(" ,UKETUKE_DT              \n"); // 申込み受付日
		sSql.append(" ,HASO_YOTEI_DT           \n"); // 発送予定日
		sSql.append(" ,TATESN_CD               \n"); // 縦線CD
		sSql.append(" ,TOKUYAKUTEN_CD          \n"); // 特約店CD
		sSql.append(" ,TOKUYAKUTEN_NM_RYAKU    \n"); // 特約店名(略)
		sSql.append(" ,DEPO_CD                 \n"); // デポ（支店）デポCD
		sSql.append(" ,DEPO_NM                 \n"); // デポ（支店）デポ名
		sSql.append(" ,NIJITEN_CD              \n"); // 二次店CD
		sSql.append(" ,NIJITEN_NM              \n"); // 二次店名
		sSql.append(" ,SANJITEN_CD             \n"); // 三次店CD
		sSql.append(" ,SANJITEN_NM             \n"); // 三次店名
		sSql.append(" ,SYUHANTEN_CD            \n"); // 酒販店（統一）CD
		sSql.append(" ,SYUHANTEN_NM            \n"); // 酒販店名
		sSql.append(" ,SYUHANTEN_ZIP           \n"); // 酒販店 郵便番号
		sSql.append(" ,SYUHANTEN_ADDRESS       \n"); // 酒販店 住所
		sSql.append(" ,SYUHANTEN_TEL           \n"); // 酒販店 TEL
		sSql.append(" ,UNCHIN_KBN              \n"); // 運賃区分
		sSql.append(" ,UNSOTEN_CD              \n"); // 運送店CD
		sSql.append(" ,JYUCYU_NO               \n"); // 黄桜受注No
		sSql.append(" ,HACYU_NO                \n"); // 発注NO
		sSql.append(" ,IRAINUSI_NM             \n"); // 依頼主 依頼主名
		sSql.append(" ,IRAINUSI_ZIP            \n"); // 依頼主 郵便番号
		sSql.append(" ,IRAINUSI_ADDRESS        \n"); // 依頼主 住所
		sSql.append(" ,IRAINUSI_TEL            \n"); // 依頼主 TEL
		sSql.append(" ,TODOKESAKI_NM           \n"); // 届け先 依頼主名
		sSql.append(" ,TODOKESAKI_ZIP          \n"); // 届け先 郵便番号
		sSql.append(" ,TODOKESAKI_ADDRESS      \n"); // 届け先 住所
		sSql.append(" ,TODOKESAKI_TEL          \n"); // 届け先 TEL
		sSql.append(" ,YOUTO_KBN               \n"); // 用途区分
		sSql.append(" ,NOSI_KBN                \n"); // 【のし】名前記載有無区分
		sSql.append(" ,NOSI_COMMENT1           \n"); // のし内容 1行目
		sSql.append(" ,NOSI_COMMENT2           \n"); // のし内容 2行目
		sSql.append(" ,NOSI_COMMENT3           \n"); // のし内容 3行目
		sSql.append(" ,CHECK_LIST_PRT_KBN      \n"); // 酒販店ﾁｪｯｸﾘｽﾄ打出区分
		sSql.append(") VALUES ( \n");
		sSql.append("   ? \n"); // 利用区分
		sSql.append(" , ? \n"); // 会社CD (FK)
		sSql.append(" , ? \n"); // 蔵直ﾃﾞｰﾀ連番
		sSql.append(" , ? \n"); // 申込み受付NO
		sSql.append(" , ? \n"); // 黄桜事業所区分蔵直
		sSql.append(" , ? \n"); // 商品ｸﾞﾙｰﾌﾟCD
		sSql.append(" , ? \n"); // データ種別CD
		sSql.append(" , ? \n"); // 整理NO
		sSql.append(" , ? \n"); // 届先ﾗｲﾝNO
		sSql.append(" , ? \n"); // 訂正時訂正元蔵直ﾃﾞｰﾀ連番
		sSql.append(" , ? \n"); // 宅配伝票区分
		sSql.append(" , ? \n"); // 入力情報区分
		sSql.append(" , ? \n"); // 申込み受付日
		sSql.append(" , ? \n"); // 発送予定日
		sSql.append(" , ? \n"); // 縦線CD
		sSql.append(" , ? \n"); // 特約店CD
		sSql.append(" , ? \n"); // 特約店名(略)
		sSql.append(" , ? \n"); // デポ（支店）デポCD
		sSql.append(" , ? \n"); // デポ（支店）デポ名
		sSql.append(" , ? \n"); // 二次店CD
		sSql.append(" , ? \n"); // 二次店名
		sSql.append(" , ? \n"); // 三次店CD
		sSql.append(" , ? \n"); // 三次店名
		sSql.append(" , ? \n"); // 酒販店（統一）CD
		sSql.append(" , ? \n"); // 酒販店名
		sSql.append(" , ? \n"); // 酒販店 郵便番号
		sSql.append(" , ? \n"); // 酒販店 住所
		sSql.append(" , ? \n"); // 酒販店 TEL
		sSql.append(" , ? \n"); // 運賃区分
		sSql.append(" , ? \n"); // 運送店CD
		sSql.append(" , ? \n"); // 黄桜受注No
		sSql.append(" , ? \n"); // 発注NO
		sSql.append(" , ? \n"); // 依頼主 依頼主名
		sSql.append(" , ? \n"); // 依頼主 郵便番号
		sSql.append(" , ? \n"); // 依頼主 住所
		sSql.append(" , ? \n"); // 依頼主 TEL
		sSql.append(" , ? \n"); // 届け先 依頼主名
		sSql.append(" , ? \n"); // 届け先 郵便番号
		sSql.append(" , ? \n"); // 届け先 住所
		sSql.append(" , ? \n"); // 届け先 TEL
		sSql.append(" , ? \n"); // 用途区分
		sSql.append(" , ? \n"); // 【のし】名前記載有無区分
		sSql.append(" , ? \n"); // のし内容 1行目
		sSql.append(" , ? \n"); // のし内容 2行目
		sSql.append(" , ? \n"); // のし内容 3行目
		sSql.append(" , ? \n"); // 酒販店ﾁｪｯｸﾘｽﾄ打出区分
		sSql.append(") \n");
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); 								// 利用区分（必須項目）
		bindList.add(cus.getKaisyaCd()); 								// 会社CD (FK)（必須項目）
		bindList.add(rec.getKuradataNo()); 								// 蔵直ﾃﾞｰﾀ連番（必須項目）
		bindList.add(rec.getMousikomiNo());     						// 申込み受付NO
		bindList.add(rec.getJigyosyoKbn());								// 黄桜事業所区分
		bindList.add(rec.getShoninGrpCd());             				// 蔵直商品ｸﾞﾙｰﾌﾟCD
		bindList.add(rec.getSyubetuCd());              					// データ種別CD
		bindList.add(rec.getKuradenNo());               				// 整理NO
		bindList.add(rec.getTodokesakiLineNo());       					// 届先ﾗｲﾝNO
		bindList.add(PbsUtil.getChar(rec.getTeiseiKuradataNo()));       // 訂正時訂正元蔵直ﾃﾞｰﾀ連番
		bindList.add(rec.getTakuhaiBillKbn());          				// 宅配伝票区分
		bindList.add(rec.getInputKbn());        						// 入力情報区分
		bindList.add(rec.getUketukeDt());								// 申込み受付日
		bindList.add(rec.getHasoYoteiDt());	            				// 発送予定日
		bindList.add(rec.getTatesnCd());             					// 縦線CD
		bindList.add(PbsUtil.getChar(rec.getTokuyakutenCd()));          // 特約店CD
		bindList.add(PbsUtil.getChar(rec.getTokuyakutenNm()), true);    // 特約店名(略)
		bindList.add(PbsUtil.getChar(rec.getDepoCd()));              	// デポ（支店）デポCD
		bindList.add(PbsUtil.getChar(rec.getDepoNm()), true);           // デポ（支店）デポ名
		bindList.add(PbsUtil.getChar(rec.getNijitenCd()));             	// 二次店CD
		bindList.add(PbsUtil.getChar(rec.getNijitenNm()), true);        // 二次店名
		bindList.add(PbsUtil.getChar(rec.getSanjitenCd()));     		// 三次店CD
		bindList.add(PbsUtil.getChar(rec.getSanjitenNm()), true);		// 三次店名
		bindList.add(PbsUtil.getChar(rec.getSyuhantenCd()));            // 酒販店（統一）CD
		bindList.add(PbsUtil.getChar(rec.getSyuhantenNm()), true);		// 酒販店名
		bindList.add(PbsUtil.getChar(rec.getSyuhantenZip()));			// 酒販店 郵便番号
		bindList.add(PbsUtil.getChar(rec.getSyuhantenAddress()), true);	// 酒販店 住所
		bindList.add(PbsUtil.getChar(rec.getSyuhantenTel()));			// 酒販店 TEL
		bindList.add(rec.getUnchinKbn());               				// 運賃区分
		bindList.add(rec.getUnsotenCd());               				// 運送店CD
		bindList.add(PbsUtil.getChar(rec.getJyucyuNo()));				// 黄桜受注No
		bindList.add(PbsUtil.getChar(rec.getHacyuNo()));				// 発注NO
		bindList.add(rec.getIrainusiNm(), true);             			// 依頼主 依頼主名
		bindList.add(PbsUtil.getChar(rec.getIrainusiZip()));			// 依頼主 郵便番号
		bindList.add(rec.getIrainusiAddress(), true);         			// 依頼主 住所
		bindList.add(rec.getIrainusiTel());								// 依頼主 TEL
		bindList.add(rec.getTodokesakiNm(), true);						// 届け先 依頼主名
		bindList.add(PbsUtil.getChar(rec.getTodokesakiZip()));			// 届け先 郵便番号
		bindList.add(rec.getTodokesakiAddress(), true);					// 届け先 住所
		bindList.add(rec.getTodokesakiTel());							// 届け先 TEL
		bindList.add(rec.getYoutoKbn());          						// 用途区分
		bindList.add(rec.getNosiKbn());									// 【のし】名前記載有無区分
		bindList.add(PbsUtil.getChar(rec.getNosiComment1()), true);		// のし内容 1行目
		bindList.add(PbsUtil.getChar(rec.getNosiComment2()), true);		// のし内容 2行目
		bindList.add(PbsUtil.getChar(rec.getNosiComment3()), true);		// のし内容 3行目
		bindList.add(rec.getCheckListPrtKbn());		    				// 酒販店ﾁｪｯｸﾘｽﾄ打出区分

		return sSql.toString();
	}


	/**
	 * 蔵元入力（蔵元データ／ディテール部）新規SQL
	 *
	 * @param rec JuchuJuchuDataInDtRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlInsertDt(KuracKuraChokuDataInDtRecord rec, List<String> bindList) {

		ComUserSession cus = getComUserSession();

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("INSERT INTO T_KURAC_DT  ( \n");
		sSql.append("  RIYOU_KBN               \n"); // 利用区分
		sSql.append(" ,KAISYA_CD               \n"); // 会社CD
		sSql.append(" ,KURADATA_NO             \n"); // 蔵直ﾃﾞｰﾀ連番
		sSql.append(" ,KURADEN_LINE_NO         \n"); // 商品行No
		sSql.append(" ,SHOHIN_CD               \n"); // 商品CD
		sSql.append(" ,SHOHIN_SET              \n"); // 商品申込ｾｯﾄ数
		sSql.append(" ,HANBAI_TANKA            \n"); // 販売単価
		sSql.append(" ,CP_KBN                  \n"); // ｷｬﾝﾍﾟｰﾝ対象区分
		sSql.append(" ,SYUKADEN_NO             \n"); // 黄桜出荷伝票NO
		sSql.append(" ,TEISEI_SYUKA_DT         \n"); // 訂正時訂正元出荷日
		sSql.append(" ,TEISEI_URIDEN_NO        \n"); // 訂正時訂正元売上伝票NO
		sSql.append(") VALUES ( \n");
		sSql.append("   ? \n"); // 利用区分
		sSql.append(" , ? \n"); // 会社CD
		sSql.append(" , ? \n"); // 蔵直ﾃﾞｰﾀ連番
		sSql.append(" , ? \n"); // 商品行No
		sSql.append(" , ? \n"); // 商品CD
		sSql.append(" , ? \n"); // 商品申込ｾｯﾄ数
		sSql.append(" , ? \n"); // 販売単価
		sSql.append(" , ? \n"); // ｷｬﾝﾍﾟｰﾝ対象区分
		sSql.append(" , ? \n"); // 黄桜出荷伝票NO
		sSql.append(" , ? \n"); // 訂正時訂正元出荷日
		sSql.append(" , ? \n"); // 訂正時訂正元売上伝票NO
		sSql.append(") \n");
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); 						// 利用区分（必須項目）
		bindList.add(cus.getKaisyaCd()); 						// 会社CD（必須項目）
		bindList.add(rec.getKuradataNo());						// 蔵直ﾃﾞｰﾀ連番（必須項目）
		bindList.add(rec.getKuradenLineNo()); 					// 商品行No（必須項目）
		bindList.add(rec.getShohinCd()); 						// 商品CD
		bindList.add(rec.getShohinSet()); 						// 商品申込ｾｯﾄ数
		bindList.add(rec.getHanbaiTanka()); 					// 販売単価
		bindList.add(PbsUtil.getChar(rec.getCpKbn())); 			// ｷｬﾝﾍﾟｰﾝ対象区分
		bindList.add(PbsUtil.getChar(rec.getSyukadenNo())); 	// 黄桜出荷伝票NO
		bindList.add(PbsUtil.getChar(rec.getTeiseiSyukaDt())); 	// 訂正時訂正元出荷日
		bindList.add(PbsUtil.getChar(rec.getTeiseiUridenNo())); // 訂正時訂正元売上伝票NO

		return sSql.toString();
	}


	/**
	 * 蔵元入力（蔵元データ／ヘッダー部）修正SQL
	 *
	 * @param rec JuchuJuchuDataInRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlUpdateHd(KuracKuraChokuDataInRecord rec, BindList bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_KURAC_HD            \n");
		sSql.append("SET                          \n");
		sSql.append("  RIYOU_KBN            = ?   \n"); // 利用区分
		sSql.append(" ,MOUSIKOMI_NO         = ?   \n"); // 申込み受付NO
		sSql.append(" ,JIGYOSYO_KBN         = ?   \n"); // 黄桜事業所区分
		sSql.append(" ,SHOHIN_GRP_CD        = ?   \n"); // 蔵直商品ｸﾞﾙｰﾌﾟCD
		sSql.append(" ,SYUBETU_CD           = ?   \n"); // データ種別CD
		sSql.append(" ,KURADEN_NO           = ?   \n"); // 整理NO
		sSql.append(" ,TODOKESAKI_LINE_NO   = ?   \n"); // 届先ﾗｲﾝNO
		sSql.append(" ,TEISEI_KURADATA_NO   = ?   \n"); // 訂正時訂正元蔵直ﾃﾞｰﾀ連番
		sSql.append(" ,TAKUHAI_BILL_KBN     = ?   \n"); // 宅配伝票区分
		//sSql.append(" ,INPUT_KBN            = ?   \n"); // 入力情報区分
		sSql.append(" ,UKETUKE_DT           = ?   \n"); // 申込み受付日
		sSql.append(" ,HASO_YOTEI_DT        = ?   \n"); // 発送予定日
		sSql.append(" ,TATESN_CD            = ?   \n"); // 縦線CD
		sSql.append(" ,TOKUYAKUTEN_CD       = ?   \n"); // 特約店CD
		sSql.append(" ,TOKUYAKUTEN_NM_RYAKU = ?   \n"); // 特約店名(略)
		sSql.append(" ,DEPO_CD              = ?   \n"); // デポ（支店）デポCD
		sSql.append(" ,DEPO_NM              = ?   \n"); // デポ（支店）デポ名
		sSql.append(" ,NIJITEN_CD           = ?   \n"); // 二次店CD
		sSql.append(" ,NIJITEN_NM           = ?   \n"); // 二次店名
		sSql.append(" ,SANJITEN_CD          = ?   \n"); // 三次店CD
		sSql.append(" ,SANJITEN_NM          = ?   \n"); // 三次店名
		sSql.append(" ,SYUHANTEN_CD         = ?   \n"); // 酒販店（統一）CD
		sSql.append(" ,SYUHANTEN_NM         = ?   \n"); // 酒販店名
		sSql.append(" ,SYUHANTEN_ZIP        = ?   \n"); // 酒販店 郵便番号
		sSql.append(" ,SYUHANTEN_ADDRESS    = ?   \n"); // 酒販店 住所
		sSql.append(" ,SYUHANTEN_TEL        = ?   \n"); // 酒販店 TEL
		sSql.append(" ,UNCHIN_KBN           = ?   \n"); // 運賃区分
		sSql.append(" ,UNSOTEN_CD           = ?   \n"); // 運送店CD
		sSql.append(" ,JYUCYU_NO            = ?   \n"); // 黄桜受注No
		sSql.append(" ,HACYU_NO             = ?   \n"); // 発注NO
		sSql.append(" ,IRAINUSI_NM          = ?   \n"); // 依頼主 依頼主名
		sSql.append(" ,IRAINUSI_ZIP         = ?   \n"); // 依頼主 郵便番号
		sSql.append(" ,IRAINUSI_ADDRESS     = ?   \n"); // 依頼主 住所
		sSql.append(" ,IRAINUSI_TEL         = ?   \n"); // 依頼主 TEL
		sSql.append(" ,TODOKESAKI_NM        = ?   \n"); // 届け先 依頼主名
		sSql.append(" ,TODOKESAKI_ZIP       = ?   \n"); // 届け先 郵便番号
		sSql.append(" ,TODOKESAKI_ADDRESS   = ?   \n"); // 届け先 住所
		sSql.append(" ,TODOKESAKI_TEL       = ?   \n"); // 届け先 TEL
		sSql.append(" ,YOUTO_KBN            = ?   \n"); // 用途区分
		sSql.append(" ,NOSI_KBN             = ?   \n"); // 【のし】名前記載有無区分
		sSql.append(" ,NOSI_COMMENT1        = ?   \n"); // のし内容 1行目
		sSql.append(" ,NOSI_COMMENT2        = ?   \n"); // のし内容 2行目
		sSql.append(" ,NOSI_COMMENT3        = ?   \n"); // のし内容 3行目
		sSql.append(" ,CHECK_LIST_PRT_KBN   = ?   \n"); // 酒販店ﾁｪｯｸﾘｽﾄ打出区分
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); 								// 利用区分（必須項目）
		bindList.add(rec.getMousikomiNo());     						// 申込み受付NO
		bindList.add(rec.getJigyosyoKbn());								// 黄桜事業所区分
		bindList.add(rec.getShoninGrpCd());								// 蔵直商品ｸﾞﾙｰﾌﾟCD
		bindList.add(rec.getSyubetuCd());								// データ種別CD
		bindList.add(rec.getKuradenNo());								// 整理NO
		bindList.add(rec.getTodokesakiLineNo());						// 届先ﾗｲﾝNO
		bindList.add(PbsUtil.getChar(rec.getTeiseiKuradataNo()));       // 訂正時訂正元蔵直ﾃﾞｰﾀ連番
		bindList.add(rec.getTakuhaiBillKbn());          				// 宅配伝票区分
		//bindList.add(KURAC_INPUT_KB_HISEIKI);        					// 入力情報区分
		bindList.add(rec.getUketukeDt());								// 申込み受付日
		bindList.add(rec.getHasoYoteiDt());								// 発送予定日
		bindList.add(rec.getTatesnCd()); 								// 縦線CD
		bindList.add(PbsUtil.getChar(rec.getTokuyakutenCd()));			// 特約店CD
		bindList.add(PbsUtil.getChar(rec.getTokuyakutenNm()), true);	// 特約店名(略)
		bindList.add(PbsUtil.getChar(rec.getDepoCd()));					// デポ（支店）デポCD
		bindList.add(PbsUtil.getChar(rec.getDepoNm()), true);			// デポ（支店）デポ名
		bindList.add(PbsUtil.getChar(rec.getNijitenCd()));				// 二次店CD
		bindList.add(PbsUtil.getChar(rec.getNijitenNm()), true);		// 二次店名
		bindList.add(PbsUtil.getChar(rec.getSanjitenCd()));     		// 三次店CD
		bindList.add(PbsUtil.getChar(rec.getSanjitenNm()), true); 		// 三次店名
		bindList.add(PbsUtil.getChar(rec.getSyuhantenCd()));			// 酒販店（統一）CD
		bindList.add(PbsUtil.getChar(rec.getSyuhantenNm()), true);		// 酒販店名
		bindList.add(PbsUtil.getChar(rec.getSyuhantenZip()));			// 酒販店 郵便番号
		bindList.add(PbsUtil.getChar(rec.getSyuhantenAddress()), true);	// 酒販店 住所
		bindList.add(PbsUtil.getChar(rec.getSyuhantenTel()));           // 酒販店 TEL
		bindList.add(rec.getUnchinKbn());								// 運賃区分
		bindList.add(rec.getUnsotenCd());								// 運送店CD
		bindList.add(PbsUtil.getChar(rec.getJyucyuNo()));               // 黄桜受注No
		bindList.add(PbsUtil.getChar(rec.getHacyuNo()));				// 発注NO
		bindList.add(PbsUtil.getChar(rec.getIrainusiNm()), true);		// 依頼主 依頼主名
		bindList.add(PbsUtil.getChar(rec.getIrainusiZip()));            // 依頼主 郵便番号
		bindList.add(PbsUtil.getChar(rec.getIrainusiAddress()), true); 	// 依頼主 住所
		bindList.add(PbsUtil.getChar(rec.getIrainusiTel()));			// 依頼主 TEL
		bindList.add(PbsUtil.getChar(rec.getTodokesakiNm()), true);		// 届け先 依頼主名
		bindList.add(PbsUtil.getChar(rec.getTodokesakiZip()));			// 届け先 郵便番号
		bindList.add(PbsUtil.getChar(rec.getTodokesakiAddress()), true);// 届け先 住所
		bindList.add(PbsUtil.getChar(rec.getTodokesakiTel()));			// 届け先 TEL
		bindList.add(rec.getYoutoKbn());								// 用途区分
		bindList.add(rec.getNosiKbn());									// 【のし】名前記載有無区分
		bindList.add(PbsUtil.getChar(rec.getNosiComment1()), true);		// のし内容 1行目
		bindList.add(PbsUtil.getChar(rec.getNosiComment2()), true);		// のし内容 2行目
		bindList.add(PbsUtil.getChar(rec.getNosiComment3()), true);		// のし内容 3行目
		bindList.add(rec.getCheckListPrtKbn());							// 酒販店ﾁｪｯｸﾘｽﾄ打出区分

		sSql.append("WHERE \n");
		sSql.append(" KAISYA_CD      = ? \n"); 	// 会社CD
		sSql.append("AND KURADATA_NO = ? \n"); 	// 蔵直ﾃﾞｰﾀ連番
		bindList.add(rec.getKaisyaCd());		// 会社CD
		bindList.add(rec.getKuradataNo());		// 蔵直ﾃﾞｰﾀ連番

		return sSql.toString();
	}


	/**
	 * 蔵元入力（受注データ／ヘッダー部）修正SQL
	 *
	 * @param rec JuchuJuchuDataInRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlUpdateHdByJyuchu(KuracKuraChokuDataInRecord rec, List<String> bindList) {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_KURAC_HD       \n");
		sSql.append("SET                     \n");
		sSql.append("    JYUCYU_NO  = NULL   \n");	// 受注No
		//sSql.append("   ,SYUBETU_CD = ?    \n");	// データ種別CD
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		//bindList.add(rec.getSyubetuCd());			// データ種別CD（必須項目）

		sSql.append("WHERE                 \n");
		sSql.append("    KAISYA_CD   = ?   \n");	// 会社CD
		sSql.append("AND JYUCYU_NO = ?     \n");	// 受注No
		//sSql.append("AND KURADATA_NO = ?     \n");	// 受注No

		bindList.add(rec.getKaisyaCd());			// 会社CD
		bindList.add(rec.getJyucyuNo());			// 受注No

		return sSql.toString();

	}

	/**
	 * 蔵元入力（受注データ／ヘッダー部）修正SQL
	 *
	 * @param rec JuchuJuchuDataInRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlUpdateJyuchuHd(KuracKuraChokuDataInRecord rec, List<String> bindList) {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_JYUCYU_HD    \n");
		sSql.append("SET                   \n");
		sSql.append("    RIYOU_KBN  = ?    \n");	// 利用区分
		//sSql.append("   ,SYUBETU_CD = ?    \n");	// データ種別CD
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(AVAILABLE_KB_RIYO_TEISI);		// 利用区分（必須項目）
		//bindList.add(rec.getSyubetuCd());			// データ種別CD（必須項目）

		sSql.append("WHERE                 \n");
		sSql.append("    KAISYA_CD   = ?   \n");	// 会社CD
		sSql.append("AND JYUCYU_NO = ?   \n");		// 受注No

		bindList.add(rec.getKaisyaCd());			// 会社CD
		bindList.add(rec.getJyucyuNo());			// 受注No

		return sSql.toString();

	}


	/**
	 * 蔵元入力（受注データ／ディテール部）修正SQL
	 *
	 * @param rec JuchuJuchuDataInRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlUpdateJyuchuDt(KuracKuraChokuDataInRecord rec, List<String> bindList) {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_JYUCYU_DT    \n");
		sSql.append("SET                   \n");
		sSql.append("    RIYOU_KBN  = ?    \n");	// 利用区分
		//sSql.append("   ,SYUBETU_CD = ?    \n");	// データ種別CD
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(AVAILABLE_KB_RIYO_TEISI);		// 利用区分（必須項目）
		//bindList.add(rec.getSyubetuCd());			// データ種別CD（必須項目）

		sSql.append("WHERE                 \n");
		sSql.append("    KAISYA_CD   = ?   \n");	// 会社CD
		sSql.append("AND JYUCYU_NO = ?   \n");		// 受注No

		bindList.add(rec.getKaisyaCd());			// 会社CD
		bindList.add(rec.getJyucyuNo());			// 受注No

		return sSql.toString();

	}


	/**
	 * 蔵元入力（受注データ／ｶﾃｺﾞﾘﾃﾞｰﾀ）修正SQL
	 *
	 * @param rec JuchuJuchuDataInRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlUpdateJyuchuJskCtg(KuracKuraChokuDataInRecord rec, List<String> bindList) {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_JYUCYU_JSKCTG    \n");
		sSql.append("SET                   \n");
		sSql.append("    RIYOU_KBN  = ?    \n");	// 利用区分
		//sSql.append("   ,SYUBETU_CD = ?    \n");	// データ種別CD
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(AVAILABLE_KB_RIYO_TEISI);		// 利用区分（必須項目）
		//bindList.add(rec.getSyubetuCd());			// データ種別CD（必須項目）

		sSql.append("WHERE                 \n");
		sSql.append("    KAISYA_CD   = ?   \n");	// 会社CD
		sSql.append("AND JYUCYU_NO = ?   \n");		// 受注No

		bindList.add(rec.getKaisyaCd());			// 会社CD
		bindList.add(rec.getJyucyuNo());			// 受注No

		return sSql.toString();

	}


	/**
	 * 蔵元入力（蔵元データ／ディテール部）修正SQL
	 *
	 * @param jyucyuNo
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlDeleteDt(String kuradataNo, List<String> bindList) {

		ComUserSession cus = getComUserSession();

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("DELETE                     \n");
		sSql.append("FROM T_KURAC_DT KURDT      \n");
		sSql.append("WHERE                      \n");

		// 会社CD
		sSql.append("    KURDT.KAISYA_CD   = ?  \n");
		bindList.add(cus.getKaisyaCd());

		// 蔵直ﾃﾞｰﾀ連番
		sSql.append("AND KURDT.KURADATA_NO = ? \n");
		bindList.add(kuradataNo);

		return sSql.toString();
	}


	/**
	 * 蔵元入力（蔵元データ／ヘッダー部）抹消SQL
	 *
	 * @param rec JuchuJuchuDataInRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlErasureHd(KuracKuraChokuDataInRecord rec, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_KURAC_HD     \n");
		sSql.append("SET                   \n");
		sSql.append("    RIYOU_KBN  = ?    \n");	// 利用区分
		//sSql.append("   ,SYUBETU_CD = ?    \n");	// データ種別CD
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn());			// 利用区分（必須項目）
		//bindList.add(rec.getSyubetuCd());			// データ種別CD（必須項目）

		sSql.append("WHERE                 \n");
		sSql.append("    KAISYA_CD   = ?   \n");	// 会社CD
		sSql.append("AND KURADATA_NO = ?   \n");	// 蔵直ﾃﾞｰﾀ連番

		bindList.add(rec.getKaisyaCd());			// 会社CD
		bindList.add(rec.getKuradataNo());			// 蔵直ﾃﾞｰﾀ連番

		return sSql.toString();
	}


	/**
	 * 蔵元入力（蔵元データ／ディテール部）抹消SQL
	 *
	 * @param rec JuchuJuchuDataInDtRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlErasureDt(KuracKuraChokuDataInDtRecord rec, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_KURAC_DT       \n");
		sSql.append("SET                     \n");
		sSql.append("    RIYOU_KBN       = ? \n");  // 利用区分
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); // 利用区分（必須項目）

		sSql.append("WHERE                   \n");
		sSql.append("    KAISYA_CD       = ? \n");  // 会社CD
		sSql.append("AND KURADATA_NO     = ? \n");  // 蔵直ﾃﾞｰﾀ連番
		sSql.append("AND KURADEN_LINE_NO = ? \n");  // 商品行No

		bindList.add(rec.getKaisyaCd());			// 会社CD
		bindList.add(rec.getKuradataNo()); 			// 蔵直ﾃﾞｰﾀ連番
		bindList.add(rec.getKuradenLineNo()); 		// 商品行No

		return sSql.toString();
	}

	/**
	 * 蔵元入力（受注データ／ヘッダー部）の行ロックを実行する。
	 *
	 * @param ccGyoLock
	 * @param rec JuchuJuchuDataInRecord
	 * @throws ResourceBusyNowaitException
	 * @throws DeadLockException
	 * @throws DataNotFoundException
	 * @throws SQLException
	 */
	private void lockJyuchuHd(Integer ccGyoLock, KuracKuraChokuDataInRecord rec)
			throws ResourceBusyNowaitException, DeadLockException, DataNotFoundException {

		// ロック処理
		db_.setDbFileNm(TBL_T_JYUCYU_HD);
		category__.info("蔵元入力（受注データ／ヘッダー部） >> 修正処理(行ロック) >> bind");

		int i = 0;

		db_.setString(ccGyoLock, ++i, rec.getKaisyaCd());	 	// 会社CD
		db_.setString(ccGyoLock, ++i, rec.getJyucyuNo()); 		// 受注No
		//db_.setString(ccGyoLock, ++i, rec.getKosnNo()); 		// 更新回数

		category__.info("蔵元入力（受注データ／ヘッダー部） >> 修正処理(行ロック) >> executeForLock");

		// 行ロック実行
		db_.executeForLock(ccGyoLock);
		if (!db_.next(ccGyoLock)) {
			db_.setGeneralErrorMsg("DataNotFoundException", ccGyoLock);
			throw new DataNotFoundException("Data Not Found!");
		}
	}


	/**
	 * 蔵元入力（受注データ／ディテール部）の行ロックを実行する。
	 *
	 * @param ccGyoLock
	 * @param rec JuchuJuchuDataInRecord
	 * @throws ResourceBusyNowaitException
	 * @throws DeadLockException
	 * @throws DataNotFoundException
	 * @throws SQLException
	 */
	private void lockJyuchuDt(Integer ccGyoLock, KuracKuraChokuDataInRecord rec)
			throws ResourceBusyNowaitException, DeadLockException, DataNotFoundException {

		// ロック処理
		db_.setDbFileNm(TBL_T_JYUCYU_DT);
		category__.info("蔵元入力（受注データ／ディテール部） >> 修正処理(行ロック) >> bind");

		int i = 0;

		db_.setString(ccGyoLock, ++i, rec.getKaisyaCd());	 	// 会社CD
		db_.setString(ccGyoLock, ++i, rec.getJyucyuNo()); 		// 受注No
		//db_.setString(ccGyoLock, ++i, rec.getKosnNo()); 		// 更新回数

		category__.info("蔵元入力（受注データ／ディテール部） >> 修正処理(行ロック) >> executeForLock");

		// 行ロック実行
		db_.executeForLock(ccGyoLock);
		if (!db_.next(ccGyoLock)) {
			db_.setGeneralErrorMsg("DataNotFoundException", ccGyoLock);
			throw new DataNotFoundException("Data Not Found!");
		}
	}


	/**
	 * 蔵元入力（受注データ／ｶﾃｺﾞﾘﾃﾞｰﾀ）の行ロックを実行する。
	 *
	 * @param ccGyoLock
	 * @param rec JuchuJuchuDataInRecord
	 * @throws ResourceBusyNowaitException
	 * @throws DeadLockException
	 * @throws DataNotFoundException
	 * @throws SQLExceptionJ
	 */
	private void lockJyuchuJskCtg(Integer ccGyoLock, KuracKuraChokuDataInRecord rec)
			throws ResourceBusyNowaitException, DeadLockException, DataNotFoundException {

		// ロック処理
		db_.setDbFileNm(TBL_T_JUCYU_JSKCTG);
		category__.info("蔵元入力（受注データ／ｶﾃｺﾞﾘﾃﾞｰﾀ） >> 修正処理(行ロック) >> bind");
		int i = 0;

		db_.setString(ccGyoLock, ++i, rec.getKaisyaCd());	 	// 会社CD
		db_.setString(ccGyoLock, ++i, rec.getJyucyuNo()); 		// 受注No
		//db_.setString(ccGyoLock, ++i, rec.getKosnNo()); 		// 更新回数

		category__.info("蔵元入力（受注データ／ｶﾃｺﾞﾘﾃﾞｰﾀ） >> 修正処理(行ロック) >> executeForLock");

		// 行ロック実行
		db_.executeForLock(ccGyoLock);
		if (!db_.next(ccGyoLock)) {
			db_.setGeneralErrorMsg("DataNotFoundException", ccGyoLock);
			throw new DataNotFoundException("Data Not Found!");
		}
	}

	/**
	 * 蔵元入力（蔵元データ／ヘッダー部）の行ロックを実行する。
	 *
	 * @param ccGyoLock
	 * @param rec JuchuJuchuDataInRecord
	 * @throws ResourceBusyNowaitException
	 * @throws DeadLockException
	 * @throws DataNotFoundException
	 * @throws SQLException
	 */
	private void lockHd(Integer ccGyoLock, KuracKuraChokuDataInRecord rec)
			throws ResourceBusyNowaitException, DeadLockException, DataNotFoundException {

		// ロック処理
		db_.setDbFileNm(TBL_T_KURAC_HD);
		category__.info("蔵元入力（蔵元データ／ヘッダー部） >> 修正処理(行ロック) >> bind");
		lockBindHd(ccGyoLock, rec);
		category__.info("蔵元入力（蔵元データ／ヘッダー部） >> 修正処理(行ロック) >> executeForLock");

		// 行ロック実行
		db_.executeForLock(ccGyoLock);
		if (!db_.next(ccGyoLock)) {
			db_.setGeneralErrorMsg("DataNotFoundException", ccGyoLock);
			throw new DataNotFoundException("Data Not Found!");
		}
	}


	/**
	 * 蔵元入力（蔵元データ／ディテール部）の行ロックを実行する。
	 *
	 * @param ccGyoLock
	 * @param rec JuchuJuchuDataInDtRecord
	 * @throws ResourceBusyNowaitException
	 * @throws DeadLockException
	 * @throws DataNotFoundException
	 * @throws SQLException
	 */
	private void lockDt(Integer ccGyoLock, KuracKuraChokuDataInDtRecord rec)
			throws ResourceBusyNowaitException, DeadLockException, DataNotFoundException {

		// ロック処理
		db_.setDbFileNm(TBL_T_KURAC_DT);
		category__.info("蔵元入力（蔵元データ／ディテール部） >> 修正処理(行ロック) >> bind");
		lockBindDt(ccGyoLock, rec);
		category__.info("蔵元入力（蔵元データ／ディテール部） >> 修正処理(行ロック) >> executeForLock");

		// 行ロック実行
		db_.executeForLock(ccGyoLock);
		if (!db_.next(ccGyoLock)) {
			db_.setGeneralErrorMsg("DataNotFoundException", ccGyoLock);
			throw new DataNotFoundException("Data Not Found!");
		}
	}


	/**
	 * 行ロック用SQLを取得する
	 * 　蔵元入力（蔵元データ／ヘッダー部）
	 *
	 * @return SQL
	 */
	private String sqlGyoLockHd() {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("SELECT \n");
		sSql.append("  KURHD.KAISYA_CD         \n"); // 会社CD
		sSql.append(" ,KURHD.KURADATA_NO       \n"); // 蔵元NO
		sSql.append(" ,KURHD.KOUSIN_NO         \n"); // 更新回数
		sSql.append("FROM T_KURAC_HD KURHD     \n");
		sSql.append("WHERE                     \n");
		sSql.append("    KURHD.KAISYA_CD   = ? \n"); // 会社CD
		sSql.append("AND KURHD.KURADATA_NO = ? \n"); // 蔵元NO
		sSql.append("AND KURHD.KOUSIN_NO   = ? \n"); // 更新回数
		sSql.append("FOR UPDATE NOWAIT         \n");
		return sSql.toString();
	}


	/**
	 * 行ロック用SQLを取得する
	 * 　蔵元入力（受注データ／ヘッダー部）
	 *
	 * @return SQL
	 */
	private String sqlGyoLockJyuchuHd() {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("SELECT \n");
		sSql.append("  JYUCYUHD.KAISYA_CD         \n"); // 会社CD
		sSql.append(" ,JYUCYUHD.JYUCYU_NO         \n"); // 受注No
		sSql.append("FROM T_JYUCYU_HD JYUCYUHD     \n");
		sSql.append("WHERE                     \n");
		sSql.append("    JYUCYUHD.KAISYA_CD   = ? \n"); // 会社CD
		sSql.append("AND JYUCYUHD.JYUCYU_NO = ? \n"); 	// 受注No
		//sSql.append("AND JYUCYUHD.KOUSIN_NO = ? \n"); 	// 更新回数
		sSql.append("FOR UPDATE NOWAIT         \n");
		return sSql.toString();
	}


	/**
	 * 行ロック用SQLを取得する
	 * 　蔵元入力（受注データ／ディテール部）
	 *
	 * @return SQL
	 */
	private String sqlGyoLockJyuchuDt() {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("SELECT \n");
		sSql.append("  JYUCYUDT.KAISYA_CD         \n"); // 会社CD
		sSql.append(" ,JYUCYUDT.JYUCYU_NO         \n"); // 受注No
		sSql.append("FROM T_JYUCYU_DT JYUCYUDT     \n");
		sSql.append("WHERE                     \n");
		sSql.append("    JYUCYUDT.KAISYA_CD   = ? \n"); // 会社CD
		sSql.append("AND JYUCYUDT.JYUCYU_NO = ? \n"); 	// 受注No
		//sSql.append("AND JYUCYUHD.KOUSIN_NO = ? \n"); 	// 更新回数
		sSql.append("FOR UPDATE NOWAIT         \n");
		return sSql.toString();
	}


	/**
	 * 行ロック用SQLを取得する
	 * 　蔵元入力（受注データ／ｶﾃｺﾞﾘﾃﾞｰﾀ）
	 *
	 * @return SQL
	 */
	private String sqlGyoLockJyuchuJskCtg() {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("SELECT \n");
		sSql.append("  JYUCYUCTG.KAISYA_CD         \n"); // 会社CD
		sSql.append(" ,JYUCYUCTG.JYUCYU_NO         \n"); // 受注No
		sSql.append("FROM T_JYUCYU_JSKCTG JYUCYUCTG     \n");
		sSql.append("WHERE                     \n");
		sSql.append("    JYUCYUCTG.KAISYA_CD   = ? \n"); // 会社CD
		sSql.append("AND JYUCYUCTG.JYUCYU_NO = ? \n"); 	// 受注No
		//sSql.append("AND JYUCYUHD.KOUSIN_NO = ? \n"); 	// 更新回数
		sSql.append("FOR UPDATE NOWAIT         \n");
		return sSql.toString();
	}

	/**
	 * 行ロック用SQLを取得する
	 * 　蔵元入力（蔵元データ／ディテール部）
	 *
	 * @return SQL
	 */
	private String sqlGyoLockDt() {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("SELECT                        \n");
		sSql.append("  KURDT.KAISYA_CD             \n"); // 会社CD
		sSql.append(" ,KURDT.KURADATA_NO           \n"); // 蔵直ﾃﾞｰﾀ連番
		sSql.append(" ,KURDT.KURADEN_LINE_NO       \n"); // 商品行No
		sSql.append(" ,KURDT.KOUSIN_NO             \n"); // 更新回数
		sSql.append("FROM T_KURAC_DT KURDT         \n");
		sSql.append("WHERE                         \n");
		sSql.append("    KURDT.KAISYA_CD       = ? \n"); // 会社CD
		sSql.append("AND KURDT.KURADATA_NO     = ? \n"); // 蔵直ﾃﾞｰﾀ連番
		sSql.append("AND KURDT.KURADEN_LINE_NO = ? \n"); // 商品行No
		sSql.append("AND KURDT.KOUSIN_NO       = ? \n"); // 更新回数
		sSql.append("FOR UPDATE NOWAIT             \n");
		return sSql.toString();
	}


	/**
	 * 行ロックSQLにバインド変数を設定する
	 * 　蔵元入力（蔵元データ／ヘッダー部）
	 *
	 * @param ccGyoLock
	 * @param rec JuchuJuchuDataInRecord
	 */
	private void lockBindHd(Integer ccGyoLock, KuracKuraChokuDataInRecord rec) {
		int i = 0;

		db_.setString(ccGyoLock, ++i, rec.getKaisyaCd());	 	// 会社CD
		db_.setString(ccGyoLock, ++i, rec.getKuradataNo()); 	// 蔵直ﾃﾞｰﾀ連番
		db_.setString(ccGyoLock, ++i, rec.getKosnNo()); 		// 更新回数
	}



	/**
	 * 行ロックSQLにバインド変数を設定する
	 * 　蔵元入力（蔵元データ／ディテール部）
	 *
	 * @param ccGyoLock
	 * @param rec JuchuJuchuDataInDtRecord
	 */
	private void lockBindDt(Integer ccGyoLock, KuracKuraChokuDataInDtRecord rec) {
		int i = 0;

		db_.setString(ccGyoLock, ++i, rec.getKaisyaCd()); 		// 会社CD
		db_.setString(ccGyoLock, ++i, rec.getKuradataNo()); 	// 蔵直ﾃﾞｰﾀ連番
		db_.setString(ccGyoLock, ++i, rec.getKuradenLineNo()); 	// 商品行No
		db_.setString(ccGyoLock, ++i, rec.getKosnNo()); 		// 更新回数
	}

}
