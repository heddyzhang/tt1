package kit.edi.EdiJuchuMainte;

import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComHaitaService;
import fb.com.ComUserSession;
import fb.inf.KitRrkUpdateService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.DeadLockException;
import fb.inf.exception.ResourceBusyNowaitException;
import fb.inf.exception.UniqueKeyViolatedException;
import fb.inf.exception.UpdateRowException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsUtil;

public class EdiJuchuMainte_UpdateService extends KitRrkUpdateService {

	/** シリアルID */
	private static final long serialVersionUID = 4458581851616691090L;
	/** クラス名. */
	private static String className__ = EdiJuchuMainte_UpdateService.class.getName();
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
	public EdiJuchuMainte_UpdateService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
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
//		haitaServ = null;
	}

	/**
	 * 修正処理
	 * EDI受発注受信データ処理（EDI受発注受信データ／ﾍｯﾀﾞｰ部）・（EDI受発注受信データ／ﾃﾞｨﾃｰﾙー部）
	 * を同一トランザクションで処理
	 *
	 * @param editList
	 * @param editDtList
	 * @param initDtList
	 * @param initCatList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	public boolean update(EdiJuchuMainteRecord hdRec, EdiJuchuMainteDtList editDtList) {

		category__.info("EDI受発注受信データ修正処理 START");

		boolean res = true;

		db_.setTransaction();
		// 基盤側で操作ログを出力する際にはtrueを与える
		db_.setTranLog(true);

		try {
			// 更新用のヘッダ情報に変換
			convertToUpdHdInfo(hdRec);
			// 更新用のﾃﾞｨﾃｰﾙー情報に変換
			convertToUpdDtInfo(editDtList);

			// ==================================================
			// EDI受発注受信データ処理（EDI受発注データ／ﾍｯﾀﾞｰ部）
			// ==================================================
			// DB処理
			this.updateHd(hdRec); // UPDATE

			// ==================================================
			// EDI受発注受信データ処理（EDI受発注受信データ／ﾃﾞｨﾃｰﾙ部）
			// ==================================================
			// DB処理
			this.updateDt(editDtList); // UPDATE

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

		category__.info("EDI受発注受信データ修正処理 END");

		return res;
	}

	/**
	 * 修正処理（UPDATE）
	 * EDI受発注受信データ処理（EDI受発注受信データ／ﾍｯﾀﾞｰ部）
	 *
	 * @param editList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean updateHd(EdiJuchuMainteRecord rec)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("EDI受発注受信データ処理（EDI受発注受信データ／ﾍｯﾀﾞｰ部）修正処理（UPDATE） START");

		boolean res = true;

		try {

			// 行ロック実行
			lockHd(rec);

			List<String> bindList = new ArrayList<String>();

			// SQLを取得
			category__.info("EDI受発注受信データ処理（EDI受発注受信データ／ﾍｯﾀﾞｰ部） >> 修正処理（UPDATE） >> SQL文生成");
			String strSql = getSqlUpdateHd(rec, bindList);
			category__.info("EDI受発注受信データ処理（EDI受発注受信データ／ﾍｯﾀﾞｰ部） >> 修正処理（UPDATE） >> prepare");
			this.db_.prepare(strSql);

			int ii = 0;
			for (String bindStr : bindList) {
				db_.setString(++ii, bindStr);
			}

			// UPDATE実行
			db_.setDbFileNm(TBL_T_EDI_JYUHACYU_HD);
			category__.debug("EDI受発注受信データ処理（EDI受発注受信データ／ﾍｯﾀﾞｰ部） >> 修正処理（UPDATE） >> update");
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

		category__.info("EDI受発注受信データ処理（EDI受発注受信データ／ﾍｯﾀﾞｰ部）修正処理（UPDATE） END");

		return res;
	}

	/**
	 * 修正処理（UPDATE）
	 * EDI受発注受信データ処理（EDI受発注受信データ／ﾃﾞｨﾃｰﾙ部）
	 *
	 * @param editList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean updateDt(EdiJuchuMainteDtList editDtList)
		throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("EDI受発注受信データ処理（EDI受発注受信データ／ﾃﾞｨﾃｰﾙ部）修正処理（UPDATE） START");

		boolean res = true;

		for (int i = 0; i < editDtList.size(); i++) {

			EdiJuchuMainteDtRecord rec = (EdiJuchuMainteDtRecord) editDtList.get(i);

//			// 未入力行は無視する
//			if (rec.isEmpty()) {
//				continue;
//			}

			try {
				// 行ロック実行
				lockDt(rec);

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("EDI受発注受信データ処理（EDI受発注受信データ／ﾃﾞｨﾃｰﾙ部） >> 修正処理（UPDATE） >> SQL文生成");
				String strSql = getSqlUpdateDt(rec, bindList);
				category__.info("EDI受発注受信データ処理（EDI受発注受信データ／ﾃﾞｨﾃｰﾙ部） >> 修正処理（UPDATE） >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// UPDATE実行d
				db_.setDbFileNm(TBL_T_EDI_JYUHACYU_DT);
				category__.debug("EDI受発注受信データ処理（EDI受発注受信データ／ﾃﾞｨﾃｰﾙ部） >> 修正処理（UPDATE） >> update");
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

		category__.info("EDI受発注受信データ処理（EDI受発注受信データ／ﾃﾞｨﾃｰﾙ部）修正処理（UPDATE） END");

		return res;
	}

	/**
	 * EDI受発注受信データ処理（EDI受発注受信データ／ﾍｯﾀﾞｰ部）の行ロックを実行する。
	 *
	 * @param ccGyoLock
	 * @param rec EdiJuchuMainteRecord
	 * @throws ResourceBusyNowaitException
	 * @throws DeadLockException
	 * @throws DataNotFoundException
	 * @throws SQLException
	 */
	private void lockHd(EdiJuchuMainteRecord rec)
			throws ResourceBusyNowaitException, DeadLockException, DataNotFoundException {
		int i = 0;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLockHd());
		category__.info("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		// ロック処理
		db_.setDbFileNm(TBL_T_EDI_JYUHACYU_HD);
		category__.info("EDI受発注受信データ処理（EDI受発注受信データ／ﾍｯﾀﾞｰ部） >> 修正処理(行ロック) >> bind");

		db_.setString(ccGyoLock, ++i, rec.getEdiJyuhacyuId()); 	// ID

		category__.info("EDI受発注受信データ処理（EDI受発注受信データ／ﾍｯﾀﾞｰ部） >> 修正処理(行ロック) >> executeForLock");

		// 行ロック実行
		db_.executeForLock(ccGyoLock);
		if (!db_.next(ccGyoLock)) {
			db_.setGeneralErrorMsg("DataNotFoundException", ccGyoLock);
			throw new DataNotFoundException("Data Not Found!");
		}
	}

	/**
	 * EDI受発注受信データ処理（EDI受発注受信データ／ﾃﾞｨﾃｰﾙー部）の行ロックを実行する。
	 *
	 * @param ccGyoLock
	 * @param rec EdiJuchuMainteRecord
	 * @throws ResourceBusyNowaitException
	 * @throws DeadLockException
	 * @throws DataNotFoundException
	 * @throws SQLException
	 */
	private void lockDt(EdiJuchuMainteDtRecord rec)
			throws ResourceBusyNowaitException, DeadLockException, DataNotFoundException {
		int i = 0;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLockDt());
		category__.info("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		// ロック処理
		db_.setDbFileNm(TBL_T_EDI_JYUHACYU_DT);
		category__.info("EDI受発注受信データ処理（EDI受発注受信データ／ﾃﾞｨﾃｰﾙｰ部） >> 修正処理(行ロック) >> bind");

		db_.setString(ccGyoLock, ++i, rec.getTEdiJyuhacyuId()); 	// ID
		db_.setString(ccGyoLock, ++i, rec.getJyuhacyuLineNo()); 	// 受発注行NO

		category__.info("EDI受発注受信データ処理（EDI受発注受信データ／ﾃﾞｨﾃｰﾙｰ部） >> 修正処理(行ロック) >> executeForLock");

		// 行ロック実行
		db_.executeForLock(ccGyoLock);
		if (!db_.next(ccGyoLock)) {
			db_.setGeneralErrorMsg("DataNotFoundException", ccGyoLock);
			throw new DataNotFoundException("Data Not Found!");
		}
	}

	/**
	 * 行ロック用SQLを取得する
	 * EDI受発注受信データ処理（EDI受発注受信データ／ﾍｯﾀﾞｰ部）
	 *
	 * @return SQL
	 */
	private String sqlGyoLockHd() {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("SELECT \n");
		sSql.append("  JYUHACYUHD.KAISYA_CD          \n");		// 会社CD
		sSql.append(" ,JYUHACYUHD.T_EDI_JYUHACYU_ID  \n");		// ID
		sSql.append(" ,JYUHACYUHD.KOUSIN_NO          \n");		// 更新回数
		sSql.append("FROM T_EDI_JYUHACYU_HD JYUHACYUHD     \n");
		sSql.append("WHERE                           \n");
		sSql.append("  JYUHACYUHD.T_EDI_JYUHACYU_ID   = ? \n");	// ID
		sSql.append("FOR UPDATE NOWAIT         \n");

		return sSql.toString();
	}

	/**
	 * 行ロック用SQLを取得する
	 * EDI受発注受信データ処理（EDI受発注受信データ／ﾃﾞｨﾃｰﾙ部）
	 *
	 * @return SQL
	 */
	private String sqlGyoLockDt() {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("SELECT \n");
		sSql.append("  JYUHACYUDT.T_EDI_JYUHACYU_ID        \n");		// ID
		sSql.append(" ,JYUHACYUDT.JYUHACYU_LINE_NO         \n");		// 受発注行NO
		sSql.append(" ,JYUHACYUDT.KOUSIN_NO                \n");		// 更新回数
		sSql.append("FROM T_EDI_JYUHACYU_DT JYUHACYUDT     \n");
		sSql.append("WHERE                           \n");
		sSql.append("  JYUHACYUDT.T_EDI_JYUHACYU_ID   = ? \n");			// ID
		sSql.append("AND JYUHACYU_LINE_NO = ?  \n"); 					// 受発注行NO
		sSql.append("FOR UPDATE NOWAIT         \n");

		return sSql.toString();
	}

	/**
	 * EDI受発注受信データ処理（EDI受発注受信データ／ﾍｯﾀﾞｰ部）修正SQL
	 *
	 * @param rec JuchuJuchuDataInRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlUpdateHd(EdiJuchuMainteRecord rec, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_EDI_JYUHACYU_HD     \n");
		sSql.append("SET                          \n");
		sSql.append("  SYUKA_YOTEI_DT    = ?      \n");	// 【変換後】KZ出荷予定日
		sSql.append(" ,MINASI_DT         = ?      \n");	// 【変換後】ﾐﾅｼ日付
		sSql.append(" ,TATESN_CD         = ?      \n");	// 【変換後】KZ縦線CD
		sSql.append(" ,OROSITEN_CD_LAST  = ?      \n");	// 【変換後】KZ最終送荷先卸CD
		sSql.append(" ,JYUCYU_NO         = ?      \n");	// 【変換後】KZ受注NO
		sSql.append(" ,SYORI_KBN         = ?      \n");	//  処理区分
		sSql.append(" ,ERROR_KBN         = ?      \n");	//  エラー区分

		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(PbsUtil.getChar(rec.getSyukaYoteiDt()));	//【変換後】KZ出荷予定日
		bindList.add(PbsUtil.getChar(rec.getMinasiDt()));		// 【変換後】ﾐﾅｼ日付
		bindList.add(PbsUtil.getChar(rec.getTatesnCd()));		// 【変換後】KZ縦線CD
		bindList.add(PbsUtil.getChar(rec.getOrositenCdLast()));	// 【変換後】KZ最終送荷先卸CD
		bindList.add(PbsUtil.getChar(rec.getJyucyuNo()));		// 【変換後】KZ受注NO
		bindList.add(PbsUtil.getChar(rec.getSyoriKbn()));		//  処理区分
		bindList.add(PbsUtil.getChar(rec.getErrorKbn()));		//  エラー区分

		sSql.append("WHERE \n");
		sSql.append(" T_EDI_JYUHACYU_ID  = ? \n"); 	// ID
		bindList.add(rec.getEdiJyuhacyuId());

		return sSql.toString();
	}


	/**
	 * EDI受発注受信データ処理（EDI受発注受信データ／ﾃﾞｨﾃｰﾙー部）修正SQL
	 *
	 * @param rec JuchuJuchuDataInRecord
	 * @param bindList
	 * @return SQL
	 */
	private String getSqlUpdateDt(EdiJuchuMainteDtRecord rec, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_EDI_JYUHACYU_DT     \n");
		sSql.append("SET                          \n");
		sSql.append("  SHOHIN_CD        = ?       \n"); // 【変換後】Kz商品CD
		sSql.append(" ,KTKSY_CD         = ?       \n"); // 【変換後】Kz寄託者CD
		sSql.append(" ,SYUKA_SU_CASE    = ?       \n"); // 【変換後】KZ発注数（ｹｰｽ）
		sSql.append(" ,SYUKA_SU_BARA    = ?       \n"); // 【変換後】kz発注数（ﾊﾞﾗ）
		sSql.append(" ,SYORI_KBN        = ?       \n"); //  処理区分
		sSql.append(" ,ERROR_KBN        = ?       \n"); //  エラー区分
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(PbsUtil.getChar(rec.getShohinCd())); 		// 【変換後】Kz商品CD
		bindList.add(PbsUtil.getChar(rec.getKtksyCd()));     	// 【変換後】Kz寄託者CD
		bindList.add(PbsUtil.getChar(rec.getSyukaSuCase()));	// 【変換後】KZ発注数（ｹｰｽ）
		bindList.add(PbsUtil.getChar(rec.getSyukaSuBara()));	// 【変換後】kz発注数（ﾊﾞﾗ））
		bindList.add(PbsUtil.getChar(rec.getSyoriKbn()));		//  処理区分
		bindList.add(PbsUtil.getChar(rec.getErrorKbn()));		//  エラー区分

		sSql.append("WHERE \n");
		sSql.append(" T_EDI_JYUHACYU_ID = ? \n"); 	// ID
		bindList.add(rec.getTEdiJyuhacyuId());
		sSql.append("AND JYUHACYU_LINE_NO = ? \n"); // 受発注行NO
		bindList.add(rec.getJyuhacyuLineNo());

		return sSql.toString();
	}


	/**
	 * ﾍｯﾀﾞｰ部情報を更新用EDI受発注ﾃﾞｰﾀ_ﾍｯﾀﾞｰ情報に変換
	 * @param EdiJuchuMainteRecord
	 */
	private void convertToUpdHdInfo(EdiJuchuMainteRecord rec) {

		String errKbn = rec.getErrorKbn();
		if (!PbsUtil.isEmpty(errKbn)) {
			// エラー区分
			rec.setErrorKbn(errKbn.replace(CHAR_SPACE, ""));
		}

		// 【変換後】ﾐﾅｼ日付
		rec.setMinasiDt(rec.getSyukaYoteiDt());
	}


	/**
	 * ﾃﾞｨﾃｰﾙー部情報を更新用EDI受発注ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ情報に変換
	 * @param hdRec
	 * @param editDtList
	 */
	private void convertToUpdDtInfo(EdiJuchuMainteDtList editDtList) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		for (int i = 0; i < editDtList.size(); i++) {
			EdiJuchuMainteDtRecord rec = (EdiJuchuMainteDtRecord) editDtList.get(i);

//			// 未入力行は無視する
//			if (rec.isEmpty()) {
//				continue;
//			}

			String errKbn = rec.getErrorKbn();
			if (!PbsUtil.isEmpty(errKbn)) {
				// エラー区分
				rec.setErrorKbn(errKbn.replace(CHAR_SPACE, ""));
			}

			//【変換後】KZ寄託者
			rec.setKtksyCd(cus.getKtksyCd());

		}
	}

}
