package kit.edi.SdnCenterCode;

import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;
import static kit.edi.SdnCenterCode.IEdiSdnCenterCode.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComDecode;
import fb.com.ComUserSession;
import fb.inf.KitRrkUpdateService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.DeadLockException;
import fb.inf.exception.UniqueKeyViolatedException;
import fb.inf.exception.UpdateRowException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;

public class EdiSdnCenterCode_UpdateService extends KitRrkUpdateService {

	/** シリアルID */
	private static final long serialVersionUID = -2208967868790187364L;

	/** クラス名. */
	private static String className__ = EdiSdnCenterCode_UpdateService.class.getName();
	/** カテゴリ. */
	private static Category category__ = Category.getInstance(className__);

	/** databaseオブジェクト */
	private PbsDatabase db_ = null;


	/**
	 * コンストラクタです.
	 * @param cus
	 * @param db_
	 * @param isTran
	 * @param ae
	 */
	public EdiSdnCenterCode_UpdateService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
		super(cus, db_, isTran, ae);
		_reset();
		this.db_ = db_;
	}

	/**
	 * 入出力パラメータの初期化を行う.
	 */
	protected void _reset() {
		db_ = null;
	}


	/**
	 * 卸センターコード(EDI)登録処理
	 * @param editForm
	 * @return boolean
	 */
	protected boolean insertCenterCodeEdi(EdiSdnCenterCode_EditForm editForm) {

		// センターコード
		String ediCenterCode = editForm.getOrosiCenterCode();

		// 卸センターコード(EDI)登録用のレコードを取得
		PbsRecord wkEdiRec = getInsPbsRecord(ediCenterCode);

		// 卸センターコード(EDI)の登録処理
		boolean res = insert(wkEdiRec);

		return res;

	}


	/**
	 * 卸センターコード(EDI)登録用のレコードを作成
	 * @param ediCenterCode
	 * @return PbsRecord
	 */
	private PbsRecord getInsPbsRecord(String ediCenterCode) {

		PbsRecord wEdiCenterCdRec = new PbsRecord();

		// ID自動採番
		String wEdiCenterCodeID = ComDecode.getSequence(db_, SEQ_W_EDI_CENTER_CODE_ID);

		//処理区分
		wEdiCenterCdRec.put("SYORI_KBN"            , MI_SOUSIN);
		// ID
		wEdiCenterCdRec.put("W_EDI_CENTER_CODE_ID" ,wEdiCenterCodeID);
		// 枝番号
		wEdiCenterCdRec.put("EDA_NO"               , EDA_NO);
		// 卸店センターコード
		wEdiCenterCdRec.put("CENTER_CD"            , ediCenterCode);

		return wEdiCenterCdRec;
	}


	/**
	 * 卸センターコード(EDI)登録処理
	 * @param rec
	 * @return
	 */
	private boolean insert(PbsRecord rec) {
		category__.info("卸センターコード(EDI)登録処理 START");

		boolean res = true;

		db_.setTransaction();
		// 基盤側で操作ログを出力する際にはtrueを与える
		db_.setTranLog(true);

		try {

			List<String> bindList = new ArrayList<String>();

			category__.info("SDNｾﾝﾀｰｺｰﾄﾞ送受信（卸センターコード(EDI)） >> 登録処理（INSERT） >> SQL文作成");
			String strSql = getSqlInsertWEdi(rec, bindList);
			category__.info("SDNｾﾝﾀｰｺｰﾄﾞ送受信（卸センターコード(EDI)） >> 登録処理（INSERT） >> prepare");
			this.db_.prepare(strSql);

			int ii = 0;
			for (String bindStr : bindList) {
				db_.setString(++ii, bindStr);
			}
			// INSERT実行
			db_.setDbFileNm(TBL_W_EDI_CENTER_CODE);
			category__.debug("SDNｾﾝﾀｰｺｰﾄﾞ送受信（卸センターコード(EDI)） >> 登録処理（INSERT） >> insert");
			db_.executeUpdate();

		} catch (DataNotFoundException e) {
			setErrorMessageId(e);
			category__.warn(e.getMessage());
			res = false;
		} catch (UpdateRowException e) {
			setErrorMessageId(e, db_.getDbFileNm());
			category__.warn(e.getMessage());
			res = false;
		} catch (DeadLockException e) {
			setErrorMessageId(e, db_.getDbFileNm());
			category__.warn(e.getMessage());
			res = false;
		} catch (UniqueKeyViolatedException e) {
			setErrorMessageId(e, db_.getDbFileNm());
			category__.warn(e.getMessage());
			res = false;
		} catch (SQLException e) {
			setErrorMessageId(e, db_.getDbFileNm());
			category__.warn(e.getMessage());
			res = false;
		} finally {
			if (res) {
				db_.commit();
			} else {
				db_.rollback();
			}
		}

		category__.info("卸センターコード(EDI)登録処理 END");

		return res;
	}


	/**
	 * 卸センターコード(EDI)の登録SQL
	 * @param wRec
	 * @param bindList
	 * @return
	 */
	private String getSqlInsertWEdi(PbsRecord wRec, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("INSERT \n");
		sSql.append(" INTO W_EDI_CENTER_CODE  ( \n");
		sSql.append("    SYORI_KBN              \n");		// 処理区分
		sSql.append("   ,W_EDI_CENTER_CODE_ID	\n");		// ID
		sSql.append("   ,EDA_NO               	\n");		// 枝番号
		sSql.append("   ,CENTER_CD              \n");		// 卸店センターコード
		sSql.append("  ) VALUES ( \n");
		sSql.append("   ? \n"); 		// 利用区分
		sSql.append(" , ? \n"); 		// ID
		sSql.append(" , ? \n"); 		// 枝番号
		sSql.append(" , ? \n"); 		// 卸店センターコード
		sSql.append(") \n");

		bindList.add(wRec.getString("SYORI_KBN"));  			// 処理区分
		bindList.add(wRec.getString("W_EDI_CENTER_CODE_ID")); 	// ID
		bindList.add(wRec.getString("EDA_NO")); 				// 枝番号
		bindList.add(wRec.getString("CENTER_CD")); 				// 卸店センターコード

		return sSql.toString();
	}
}
