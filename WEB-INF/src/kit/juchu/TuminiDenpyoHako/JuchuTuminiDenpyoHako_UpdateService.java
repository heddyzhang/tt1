package kit.juchu.TuminiDenpyoHako;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;
import static kit.juchu.TuminiDenpyoHako.IJuchuTuminiDenpyoHako.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComHaitaService;
import fb.com.ComRecordUtil;
import fb.com.ComUserSession;
import fb.com.Records.FbMastrKbnTekiyoRecord;
import fb.com.exception.KitComException;
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
public class JuchuTuminiDenpyoHako_UpdateService extends KitRrkUpdateService {

	/** シリアルID */
	private static final long serialVersionUID = 8486054266083288172L;

	/** クラス名. */
	private static String className__ = JuchuTuminiDenpyoHako_UpdateService.class.getName();
	/** カテゴリ. */
	private static Category category__ = Category.getInstance(className__);

	/** databaseオブジェクト */
	private PbsDatabase db_ = null;

	/** 排他サービス */
	private ComHaitaService haitaServ = null;
	private String[] pkCodes = null;

	/** 存在チェックユーティリティー */
	private ComRecordUtil rUtil = null;

	/** 積荷伝票発行印刷サービス */
	private JuchuTuminiDenpyoHako_PrintService printServ = null;

	// 積荷データ作業用リスト（伝票１ページ分）
	JuchuTuminiDenpyoHakoHdList editHdList = null; // 積荷データ／HD部
	JuchuTuminiDenpyoHakoDtList editDtList = null; // 積荷データ／DT部
	JuchuTuminiDenpyoHakoItemList editItemList = null; // 受注データ／DT部


	/**
	 * コンストラクタです.
	 *
	 * @param request paramName
	 */
	public JuchuTuminiDenpyoHako_UpdateService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
		super(cus, db_, isTran, ae);
		_reset();
		this.db_ = db_;
		// 排他サービスを生成する
		haitaServ = new ComHaitaService(cus, db_, isTran, ae);
		// 売上伝票発行印刷サービスを生成する
		printServ = new JuchuTuminiDenpyoHako_PrintService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		// 存在チェックユーティリティーを初期化する
		this.rUtil = new ComRecordUtil(db_);
	}

	/**
	 * 入出力パラメータの初期化を行う.
	 */
	protected void _reset() {
		db_ = null;
		haitaServ = null;
	}



	/**
	 * 積荷伝票発行処理
	 * 　個別発行処理
	 * 　集約発行処理
	 * を同一トランザクションで処理
	 *
	 * @param editForm
	 * @param kobetuList
	 * @param editList
	 * @param syuyakuList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	public boolean print(
			JuchuTuminiDenpyoHako_EditForm editForm,
			JuchuTuminiDenpyoHakoList kobetuList,
			JuchuTuminiDenpyoHakoList editList,
			JuchuTuminiDenpyoHakoList syuyakuList) {

		boolean res = true;

		db_.setTransaction();
		// 基盤側で操作ログを出力する際にはtrueを与える
		db_.setTranLog(true);

		category__.info("積荷伝票発行処理　START =========");
		try {
			// ==================================================
			// 個別発行処理
			// ==================================================
			this.printKobetu(editForm, kobetuList);

			// ==================================================
			// 集約発行処理
			// ==================================================
			if (!PbsUtil.isListEmpty(syuyakuList)) {
				this.printSyuyaku(editForm, editList, syuyakuList);
			}

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
			try {
				// 排他を解放する
				for (PbsRecord tmp : editList) {
					JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord) tmp;
					pkCodes = rec.getPrimaryCodes();
					haitaServ.setPkCodes(pkCodes);
					haitaServ.unLock();
				}
			} catch (Exception e) {
			}
		}
		category__.info("積荷伝票発行処理　END =========");

		return res;
	}



	/**
	 * 積荷伝票（個別）発行処理
	 * 　受注データ／HD部　更新処理
	 * 　積荷データ／HD部　新規処理
	 * 　積荷データ／DT部　新規処理
	 * 　帳票印刷（TSV出力）
	 * を同一トランザクションで処理
	 *
	 * @param editForm
	 * @param kobetuList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	public boolean printKobetu(JuchuTuminiDenpyoHako_EditForm editForm, JuchuTuminiDenpyoHakoList kobetuList)
			throws DataNotFoundException, UpdateRowException, DeadLockException, UniqueKeyViolatedException, SQLException, KitException {

		ComUserSession cus = getComUserSession();

		boolean res = true;

		category__.info("積荷伝票（個別）伝票発行処理　START =========");
		try {
			// 伝票発行対象
			for (PbsRecord tmp : kobetuList) {
				JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord) tmp;

				// ==================================================
				// 出荷データの存在チェック（受注NOで検索）
				// ==================================================
				if (isExistSyukaData(cus.getKaisyaCd(), rec)) {
				}

				// ==================================================
				// 積荷伝票NOを自動採番してセット
				// ==================================================
				rec.setTumidenNo(this.getDenNo());
				rec.setSyuyakuKbn(YN_07_KB_KOBETU); // 集約区分 = 1:個別出力対象

				// ==================================================
				// 積荷データの作成
				// ==================================================
				JuchuTuminiDenpyoHako_SearchService searchServ = new JuchuTuminiDenpyoHako_SearchService(
						getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
				// 検索実行
				// HD部
				PbsRecord[] recordsHd = searchServ.tuminiKobetuHd(rec);
				// 検索結果をリストクラスに変換
				editHdList = new JuchuTuminiDenpyoHakoHdList(recordsHd);

				// 受注データ／DT部（伝票表示用アイテムリスト）
				PbsRecord[] recordsDtItem = searchServ.tuminiKobetuDtItem(rec);
				// 検索結果をリストクラスに変換
				editItemList = new JuchuTuminiDenpyoHakoItemList(recordsDtItem);

				// DT部
				PbsRecord[] recordsDt = searchServ.tuminiKobetuDt(rec);
				// 検索結果をリストクラスに変換
				editDtList = new JuchuTuminiDenpyoHakoDtList(recordsDt);

				// [DT]積荷伝票行NOを設定する
				this.editDtList.setInputLineNos();

				// [DT]積み残しフラグを設定する
				this.editDtList.setTuminokosiFlgs(kobetuList);

				// ==================================================
				// 受注データ／HD部　積荷伝票発行回数の取得
				// ==================================================
				this.hakoCntUp(rec);

				// ==================================================
				// 受注データ／HD部　更新処理
				// ==================================================
				// DB処理
				this.updateKobetu(rec);

				// ==================================================
				// 積荷データ／HD部　新規処理
				// ==================================================
				// DB処理
				this.insertHdKobetu(this.editHdList, rec);

				// ==================================================
				// 積荷データ／DT部　新規処理
				// ==================================================
				// DB処理
				this.insertDtKobetu(this.editDtList, rec);

				// ==================================================
				// 帳票印刷（TSV出力）
				// ==================================================
				// 伝票発行用データを生成
				if (!this.createDenpyoHakoDataKobetu(rec, editForm.getPrinterId())) {
					res = false;
				}
			}
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
		}
		category__.info("積荷伝票（個別）伝票発行処理　END =========");

		return res;
	}



	/**
	 * 積荷伝票（集約）発行処理
	 * 　受注データ／HD部　更新処理
	 * 　積荷データ／HD部　新規処理
	 * 　積荷データ／DT部　新規処理
	 * 　帳票印刷（TSV出力）
	 * を同一トランザクションで処理
	 *
	 * @param editForm
	 * @param editList
	 * @param syuyakuList
	 * @return TRUE:成功 / FALSE:失敗
	 */
	public boolean printSyuyaku(JuchuTuminiDenpyoHako_EditForm editForm, JuchuTuminiDenpyoHakoList editList, JuchuTuminiDenpyoHakoList syuyakuList)
			throws DataNotFoundException, UpdateRowException, DeadLockException, UniqueKeyViolatedException, SQLException, KitException {

		ComUserSession cus = getComUserSession();

		boolean res = true;

		// 集約発行対象の受注NOをSQLのIN句に渡すカッコを作成・・・IN(?, ?, ... , ?)
		String inStr = editList.getJyucyuNoList(syuyakuList);

		JuchuTuminiDenpyoHako_SearchService searchServ = new JuchuTuminiDenpyoHako_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 集約発行対象の受注データ／HD部から運送店CDを検索
		PbsRecord[] records = searchServ.getUnsotenFromSyukeiHd(syuyakuList, inStr);
		// 運送店CDリストを作成
		JuchuTuminiDenpyoHakoList syuyakuUnsotenCdList = new JuchuTuminiDenpyoHakoList(records);

		// 運送店CDごとに
		for (int i = 0; i < syuyakuUnsotenCdList.size(); i++) {
			JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord)syuyakuUnsotenCdList.get(i);

			// 集約発行対象の受注データ／HD部を運送店CDをキーに再検索
			PbsRecord[] uRecords = searchServ.getSyukeiHdByUnsotenCd(syuyakuList, inStr, rec);
			// 検索結果をリストに変換
			JuchuTuminiDenpyoHakoList syuyakuListByUnsotenCd = new JuchuTuminiDenpyoHakoList(uRecords);
			// 運送店CD単位の集約発行対象の受注NOをSQLのIN句に渡すカッコを作成・・・IN(?, ?, ... , ?)
			String inStrU = editList.getJyucyuNoListByUnsotenCd(syuyakuListByUnsotenCd);

			category__.info("積荷伝票（集約）伝票発行処理　START =========");
			try {
				// ==================================================
				// 積荷伝票NOを自動採番してセット
				// ==================================================
				rec.setTumidenNo(this.getDenNo());
				rec.setSyuyakuKbn(YN_07_KB_SYUYAKU); // 集約区分 = 2:集約出力対象

				// 伝票発行対象
				for (PbsRecord tmp : syuyakuListByUnsotenCd) {
					JuchuTuminiDenpyoHakoRecord iRec = (JuchuTuminiDenpyoHakoRecord) tmp;
					// ==================================================
					// 出荷データの存在チェック（受注NOで検索）
					// ==================================================
					if (isExistSyukaData(cus.getKaisyaCd(), iRec)) {
					}

					// ==================================================
					// 受注データ／HD部　積荷伝票発行回数の取得
					// ==================================================
					this.hakoCntUp(iRec);

					// ==================================================
					// 受注データ／HD部　更新処理
					// ==================================================
					// DB処理
					this.updateSyuyaku(iRec, rec);

				}

				// ==================================================
				// 積荷データの作成
				// ==================================================
				// 検索実行
				// HD部
				PbsRecord[] recordsHd = searchServ.tuminiSyukeiHd(syuyakuListByUnsotenCd, inStrU);
				// 検索結果をリストクラスに変換
				editHdList = new JuchuTuminiDenpyoHakoHdList(recordsHd);

				// 受注データ／DT部（伝票表示用アイテムリスト）
				PbsRecord[] recordsDtItem = searchServ.tuminiSyukeiDtItem(syuyakuListByUnsotenCd, inStrU);
				// 検索結果をリストクラスに変換
				editItemList = new JuchuTuminiDenpyoHakoItemList(recordsDtItem);

				// DT部
				PbsRecord[] recordsDt = searchServ.tuminiSyukeiDt(syuyakuListByUnsotenCd, inStrU);
				// 検索結果をリストクラスに変換
				editDtList = new JuchuTuminiDenpyoHakoDtList(recordsDt);

				// [DT]積荷伝票行NOを設定する
				this.editDtList.setInputLineNos();

				// [DT]積み残しフラグを設定する
				this.editDtList.setTuminokosiFlgs(syuyakuList);

				// ==================================================
				// 積荷データ／HD部　新規処理
				// ==================================================
				// DB処理
				this.insertHdSyuyaku(this.editHdList, rec);

				// ==================================================
				// 積荷データ／DT部　新規処理
				// ==================================================
				// DB処理
				this.insertDtSyuyaku(this.editDtList, rec);

				// ==================================================
				// 帳票印刷（TSV出力）
				// ==================================================
				// 伝票発行用データを生成
				if (!this.createDenpyoHakoDataSyuyaku(rec, syuyakuListByUnsotenCd, editItemList, editList.getJyucyuNos(syuyakuListByUnsotenCd), editForm.getPrinterId())) {
					res = false;
				}

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
			}
			category__.info("積荷伝票（集約）伝票発行処理　END =========");

		}
		return res;
	}




//	/**
//	 * 受注データから積荷データを作成（個別）
//	 *
//	 * @param rec JuchuTuminiDenpyoHakoRecord
//	 * @throws KitException
//	 * @throws DataNotFoundException
//	 */
//	private void createDataByPageKobetu(JuchuTuminiDenpyoHakoRecord rec) throws DataNotFoundException, KitException {
//
//		// ページ用リストの初期化
//		this.editHdList = new JuchuTuminiDenpyoHakoHdList(); // 積荷データ／ヘッダー部
//		this.editDtList = new JuchuTuminiDenpyoHakoDtList(); 	// 積荷データ／ディテール部
//
//		// ==================================================
//		// 計算用変数の初期化
//		// ==================================================
//		String souSuryoCase = SU_ZERO; 	// [HD]総数量 ケース
//		String souSuryoBara = SU_ZERO; 		// [HD]総数量 バラ
//		String souJyuryo = SU_ZERO; 			// [HD]総重量(KG)
//
//		String syukaSuryoCase = SU_ZERO; 	// [DT]出荷数量 ケース
//		String syukaSuryoBara = SU_ZERO; 	// [DT]出荷数量 バラ
//		String jyuryoTot = SU_ZERO; 			// [DT]重量計(KG)
//
//		// ==================================================
//		// 積荷データ／ヘッダー部
//		// ==================================================
//		// 受注データ（ヘッダー部）を取得
//		JuchuJuchuDataInList listHdFm = juchuServ.getListJuchuJuchuDataIn(rec.getJyucyuNo());
//
//		// 受注データ（ヘッダー部）レコード
//		JuchuJuchuDataInRecord recHdFm = (JuchuJuchuDataInRecord) listHdFm.getFirstRecord();
//
//		// 積荷データ（ヘッダー部）を作成
//		JuchuTuminiDenpyoHakoHdRecord recHdTo = new JuchuTuminiDenpyoHakoHdRecord();
//		recHdTo.setRiyouKbn(recHdFm.getRiyouKbn()); 		// [HD]利用区分
//		recHdTo.setKaisyaCd(recHdFm.getKaisyaCd()); 		// [HD]会社CD
//		recHdTo.setTumidenNo(rec.getTumidenNo()); 		// [HD]積荷伝票NO・・・ページ単位で自動採番した番号
//		recHdTo.setUnsotenCd(recHdFm.getUnsotenCd()); 	// [HD]運送店CD
//		recHdTo.setSyuyakuKbn(rec.getSyuyakuKbn()); 		// [HD]集約区分（2:集約）
//		recHdTo.setSyukaDt(recHdFm.getSyukaDt()); 		// [HD]出荷日
//		// [HD]伝票発行日・・・SQLでセット
//		// [HD]伝票発行時刻・・・SQLでセット
//		// [HD]総数量 ケース・・・ディテール部ループ内でページ内合計を計算
//		// [HD]総数量 バラ・・・ディテール部ループ内でページ内合計を計算
//		// [HD]総重量(KG)・・・ディテール部ループ内でページ内合計を計算
//
//		// ==================================================
//		// 積荷データ／ディテール部
//		// ==================================================
//		// 受注データ（ディテール部）を取得
//		JuchuJuchuDataInDtList listDtFm = juchuServ.getListJuchuJuchuDataInDt(rec.getJyucyuNo());
//
//		for (PbsRecord tmp : listDtFm) {
//			// 受注データ（ディテール部）レコード
//			JuchuJuchuDataInDtRecord recDtFm = (JuchuJuchuDataInDtRecord) tmp;
//
//			// 積荷データ（ディテール部）を作成
//			JuchuTuminiDenpyoHakoDtRecord recDtTo = new JuchuTuminiDenpyoHakoDtRecord();
//			recDtTo.setRiyouKbn(recDtFm.getRiyouKbn()); 			// [DT]利用区分
//			recDtTo.setKaisyaCd(recDtFm.getKaisyaCd()); 			// [DT]会社CD
//			recDtTo.setTumidenNo(rec.getTumidenNo()); 				// [DT]積荷伝票NO
//			recDtTo.setInputLineNo(recDtFm.getInputLineNo()); 	// [DT]レコードNO
//			recDtTo.setTuminokosiFlg(rec.getTuminokosiFlg());		// [DT]積み残しフラグ
//			recDtTo.setJyucyuNo(recDtFm.getJyucyuNo()); 			// [DT]受注NO
//
//			// [DT]出荷数量 ケース
//			syukaSuryoCase = recDtFm.getSyukaSuCase();
//			// [HD]総数量 ケース＝[DT]出荷数量 ケースの合計
//			souSuryoCase = PbsUtil.sToSAddition(souSuryoCase, syukaSuryoCase);
//
//			// [DT]出荷数量 バラ
//			syukaSuryoBara = recDtFm.getSyukaSuBara();
//			// [HD]総数量 バラ＝[DT]出荷数量 バラの合計
//			souSuryoBara = PbsUtil.sToSAddition(souSuryoBara, syukaSuryoBara);
//
//			// [DT]重量計(KG)
//			jyuryoTot = recDtFm.getSyukaAllWeigth();
//			// [HD]総重量(KG)＝[DT]重量計(KG)の合計
//			souJyuryo = PbsUtil.sToSAddition(souJyuryo, jyuryoTot);
//
//			recDtTo.setSyukaSuryoCase(syukaSuryoCase); 	// [DT]出荷数量 ケース
//			recDtTo.setSyukaSuryoBara(syukaSuryoBara); 	// [DT]出荷数量 バラ
//			recDtTo.setJyuryoTot(jyuryoTot); 						// [DT]重量計(KG)
//
//			// 積荷データ（ディテール部）リストに追加
//			this.editDtList.add(recDtTo);
//		}
//
//		// [DT]積荷伝票行NOを設定する（ページ単位で1から行NOを設定）
//		this.editDtList.setInputLineNos();
//
//		// 積荷データ（ヘッダー部）を作成（ディテール部のページ内合計）
//		// [HD]総数量 ケース
//		// 小数点第1位で四捨五入
//		recHdTo.setSouSuryoCase(
//				((new BigDecimal(syukaSuryoCase)).setScale(0, BigDecimal.ROUND_HALF_UP)).toString());
//
//		// [HD]総数量 バラ
//		// 小数点第1位で四捨五入
//		recHdTo.setSouSuryoBara(
//				((new BigDecimal(syukaSuryoBara)).setScale(0, BigDecimal.ROUND_HALF_UP)).toString());
//
//		// [HD]総重量(KG)
//		// 小数点第4位で四捨五入
//		recHdTo.setSouJyuryo(
//				((new BigDecimal(jyuryoTot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());
//
//		// 積荷データ（ヘッダー部）リストに追加
//		this.editHdList.add(recHdTo);
//	}


	/**
	 * 受注データ／HD部　積荷伝票発行回数をカウントアップ
	 *
	 * @param rec JuchuTuminiDenpyoHakoRecord
	 */
	private void hakoCntUp(JuchuTuminiDenpyoHakoRecord rec) {

		int prtCnt = 0;

		if (!PbsUtil.isEmpty(rec.getTumidenHakoCnt())) {
			prtCnt = Integer.parseInt(rec.getTumidenHakoCnt());
			prtCnt = prtCnt + 1;

		} else {
			prtCnt = 1;
		}
		rec.setTumidenHakoCnt(String.valueOf(prtCnt));
	}


	/**
	 * 摘要区分　積荷伝票記載フラグの取得処理
	 *
	 * @param searchedList
	 */
	public void setTumidenKisaiFlgs(JuchuTuminiDenpyoHakoList searchedList) {

		for (int i = 0; i < searchedList.size(); i++) {
			JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord)searchedList.get(i);

			// 摘要区分マスターを検索して記載フラグを取得
			if (!PbsUtil.isEmpty(rec.getTekiyoKbn1())) { // 摘要区分(01)に値あり
				FbMastrKbnTekiyoRecord zRecKbnTekiyo = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, rec.getTekiyoKbn1());
				rec.setTumidenKisaiFlg1(zRecKbnTekiyo.getTumidenKisaiFlg());
			}
			if (!PbsUtil.isEmpty(rec.getTekiyoKbn2())) { // 摘要区分(02)に値あり
				FbMastrKbnTekiyoRecord zRecKbnTekiyo = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, rec.getTekiyoKbn2());
				rec.setTumidenKisaiFlg2(zRecKbnTekiyo.getTumidenKisaiFlg());
			}
			if (!PbsUtil.isEmpty(rec.getTekiyoKbn3())) { // 摘要区分(03)に値あり
				FbMastrKbnTekiyoRecord zRecKbnTekiyo = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, rec.getTekiyoKbn3());
				rec.setTumidenKisaiFlg3(zRecKbnTekiyo.getTumidenKisaiFlg());
			}
			if (!PbsUtil.isEmpty(rec.getTekiyoKbn4())) { // 摘要区分(04)に値あり
				FbMastrKbnTekiyoRecord zRecKbnTekiyo = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, rec.getTekiyoKbn4());
				rec.setTumidenKisaiFlg4(zRecKbnTekiyo.getTumidenKisaiFlg());
			}
			if (!PbsUtil.isEmpty(rec.getTekiyoKbn5())) { // 摘要区分(05)に値あり
				FbMastrKbnTekiyoRecord zRecKbnTekiyo = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, rec.getTekiyoKbn5());
				rec.setTumidenKisaiFlg5(zRecKbnTekiyo.getTumidenKisaiFlg());
			}
		}
	}


	/**
	 * 摘要区分の成形処理
	 *
	 * @param searchedList
	 */
	public void constTekiyouKbn(JuchuTuminiDenpyoHakoList searchedList) {

		for (int i = 0; i < searchedList.size(); i++) {
			JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord)searchedList.get(i);

			String tekiyo1 = "";
			String tekiyo2 = "";
			String tekiyo3 = "";
			String tekiyo4 = "";
			String tekiyo5 = "";

			if (!PbsUtil.isEmpty(rec.getTumidenKisaiFlg1()) && PbsUtil.isEqual(rec.getTumidenKisaiFlg1(), KISAI_FLG_KB_YES)) {
				if (!PbsUtil.isEmpty(rec.getTekiyoNm1())) {
					tekiyo1 = rec.getTekiyoNm1();
				}
			}
			if (!PbsUtil.isEmpty(rec.getTumidenKisaiFlg2()) && PbsUtil.isEqual(rec.getTumidenKisaiFlg2(), KISAI_FLG_KB_YES)) {
				if (!PbsUtil.isEmpty(rec.getTekiyoNm2())) {
					tekiyo2 = "／" + rec.getTekiyoNm2();
				}
			}
			if (!PbsUtil.isEmpty(rec.getTumidenKisaiFlg3()) && PbsUtil.isEqual(rec.getTumidenKisaiFlg3(), KISAI_FLG_KB_YES)) {
				if (!PbsUtil.isEmpty(rec.getTekiyoNm3())) {
					tekiyo3 = "／" + rec.getTekiyoNm3();
				}
			}
			if (!PbsUtil.isEmpty(rec.getTumidenKisaiFlg4()) && PbsUtil.isEqual(rec.getTumidenKisaiFlg4(), KISAI_FLG_KB_YES)) {
				if (!PbsUtil.isEmpty(rec.getTekiyoNm4())) {
					tekiyo4 = "／" + rec.getTekiyoNm4();
				}
			}
			if (!PbsUtil.isEmpty(rec.getTumidenKisaiFlg5()) && PbsUtil.isEqual(rec.getTumidenKisaiFlg5(), KISAI_FLG_KB_YES)) {
				if (!PbsUtil.isEmpty(rec.getTekiyoNm5())) {
					tekiyo5 = "／" + rec.getTekiyoNm5();
				}
			}
			rec.setTekiyoNmAll(tekiyo1 + tekiyo2 + tekiyo3 + tekiyo4 + tekiyo5);
		}
	}


	/**
	 * 摘要区分　積荷伝票記載フラグの取得処理
	 *
	 * @param recs
	 */
	public void setTumidenKisaiFlgs(PbsRecord[] recs) {

		for (int i = 0; i < recs.length; i++) {
			// 摘要区分マスターを検索して記載フラグを取得
			if (!PbsUtil.isEmpty(recs[i].getString("TEKIYO_KBN1"))) { // 摘要区分(01)に値あり
				FbMastrKbnTekiyoRecord zRecKbnTekiyo = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, recs[i].getString("TEKIYO_KBN1"));
				recs[i].put("TUMIDEN_KISAI_FLG1", zRecKbnTekiyo.getTumidenKisaiFlg());
			}
			if (!PbsUtil.isEmpty(recs[i].getString("TEKIYO_KBN2"))) { // 摘要区分(02)に値あり
				FbMastrKbnTekiyoRecord zRecKbnTekiyo = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, recs[i].getString("TEKIYO_KBN2"));
				recs[i].put("TUMIDEN_KISAI_FLG2", zRecKbnTekiyo.getTumidenKisaiFlg());
			}
			if (!PbsUtil.isEmpty(recs[i].getString("TEKIYO_KBN3"))) { // 摘要区分(03)に値あり
				FbMastrKbnTekiyoRecord zRecKbnTekiyo = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, recs[i].getString("TEKIYO_KBN3"));
				recs[i].put("TUMIDEN_KISAI_FLG3", zRecKbnTekiyo.getTumidenKisaiFlg());
			}
			if (!PbsUtil.isEmpty(recs[i].getString("TEKIYO_KBN4"))) { // 摘要区分(04)に値あり
				FbMastrKbnTekiyoRecord zRecKbnTekiyo = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, recs[i].getString("TEKIYO_KBN4"));
				recs[i].put("TUMIDEN_KISAI_FLG4", zRecKbnTekiyo.getTumidenKisaiFlg());
			}
			if (!PbsUtil.isEmpty(recs[i].getString("TEKIYO_KBN5"))) { // 摘要区分(05)に値あり
				FbMastrKbnTekiyoRecord zRecKbnTekiyo = rUtil.getRecMastrKbnTekiyo(IS_DELETED_HELD_TRUE, recs[i].getString("TEKIYO_KBN5"));
				recs[i].put("TUMIDEN_KISAI_FLG5", zRecKbnTekiyo.getTumidenKisaiFlg());
			}
		}
	}


	/**
	 * 摘要区分の成形処理
	 *
	 * @param recs
	 */
	public void constTekiyouKbn(PbsRecord[] recs) {

		for (int i = 0; i < recs.length; i++) {
			String tekiyo1 = "";
			String tekiyo2 = "";
			String tekiyo3 = "";
			String tekiyo4 = "";
			String tekiyo5 = "";
			String tekiyoAll = "";

			if (!PbsUtil.isEmpty(recs[i].getString("TUMIDEN_KISAI_FLG1")) && PbsUtil.isEqual(recs[i].getString("TUMIDEN_KISAI_FLG1"), KISAI_FLG_KB_YES)) {
				if (!PbsUtil.isEmpty(recs[i].getString("TEKIYO_NM1"))) {
					tekiyo1 = recs[i].getString("TEKIYO_NM1");
				}
			}
			if (!PbsUtil.isEmpty(recs[i].getString("TUMIDEN_KISAI_FLG2")) && PbsUtil.isEqual(recs[i].getString("TUMIDEN_KISAI_FLG2"), KISAI_FLG_KB_YES)) {
				if (!PbsUtil.isEmpty(recs[i].getString("TEKIYO_NM2"))) {
					tekiyo2 = "／" + recs[i].getString("TEKIYO_NM2");
				}
			}
			if (!PbsUtil.isEmpty(recs[i].getString("TUMIDEN_KISAI_FLG3")) && PbsUtil.isEqual(recs[i].getString("TUMIDEN_KISAI_FLG3"), KISAI_FLG_KB_YES)) {
				if (!PbsUtil.isEmpty(recs[i].getString("TEKIYO_NM3"))) {
					tekiyo3 = "／" + recs[i].getString("TEKIYO_NM3");
				}
			}
			if (!PbsUtil.isEmpty(recs[i].getString("TUMIDEN_KISAI_FLG4")) && PbsUtil.isEqual(recs[i].getString("TUMIDEN_KISAI_FLG4"), KISAI_FLG_KB_YES)) {
				if (!PbsUtil.isEmpty(recs[i].getString("TEKIYO_NM4"))) {
					tekiyo4 = "／" + recs[i].getString("TEKIYO_NM4");
				}
			}
			if (!PbsUtil.isEmpty(recs[i].getString("TUMIDEN_KISAI_FLG5")) && PbsUtil.isEqual(recs[i].getString("TUMIDEN_KISAI_FLG5"), KISAI_FLG_KB_YES)) {
				if (!PbsUtil.isEmpty(recs[i].getString("TEKIYO_NM5"))) {
					tekiyo5 = "／" + recs[i].getString("TEKIYO_NM5");
				}
			}
			tekiyoAll = tekiyo1 + tekiyo2 + tekiyo3 + tekiyo4 + tekiyo5;
			recs[i].put("TEKIYO_NM_ALL", tekiyoAll);
		}
	}


	/**
	 *
	 * 積荷伝票発行（個別）
	 * 　受注データ／HD部　更新処理
	 *
	 * @param rec
	 * @return TRUE:成功 / FALSE:失敗
	 */
	public boolean updateKobetu(JuchuTuminiDenpyoHakoRecord rec)
			throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("積荷伝票発行（個別）受注データ／HD部　更新処理　START");

		boolean res = true;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLock());
		category__.info("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		try {

			// 行ロック実行
			lock(ccGyoLock, rec);

			List<String> bindList = new ArrayList<String>();

			// 受注データ／ヘッダー部 更新用SQLを取得
			category__.info("積荷伝票発行（個別）受注データ／HD部　更新処理 >> SQL文生成");
			String strSql = getSqlUpdateKobetu(rec, bindList);
			category__.info("積荷伝票発行（個別）受注データ／HD部　更新処理 >> prepare");
			this.db_.prepare(strSql);

			int ii = 0;
			for (String bindStr : bindList) {
				db_.setString(++ii, bindStr);
			}

			// UPDATE実行
			db_.setDbFileNm(TBL_T_JYUCYU_HD);
			category__.debug("積荷伝票発行（個別）受注データ／HD部　更新処理 >> update");
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
		category__.info("積荷伝票発行（個別）受注データ／HD部　更新処理　END");
		return res;
	}


	/**
	 *
	 * 積荷伝票発行（集約）
	 * 　受注データ／HD部　更新処理
	 *
	 * @param rec
	 * @param pRec
	 * @return TRUE:成功 / FALSE:失敗
	 */
	public boolean updateSyuyaku(JuchuTuminiDenpyoHakoRecord rec, JuchuTuminiDenpyoHakoRecord pRec)
			throws DataNotFoundException, UpdateRowException, ResourceBusyNowaitException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("積荷伝票発行（集約）受注データ／HD部　更新処理　START");

		boolean res = true;

		// 行ロックSQLを取得
		category__.info("prepare ⇒ ccGyoLock");
		Integer ccGyoLock = db_.prepare(true, sqlGyoLock());
		category__.info("ccGyoLock SQL :" + db_.getSql(ccGyoLock));

		try {

			// 行ロック実行
			lock(ccGyoLock, rec);

			List<String> bindList = new ArrayList<String>();

			// 受注データ／ヘッダー部 更新用SQLを取得
			category__.info("積荷伝票発行（集約）受注データ／HD部　更新処理 >> SQL文生成");
			String strSql = getSqlUpdateSyuyaku(rec, bindList, pRec);
			category__.info("積荷伝票発行（集約）受注データ／HD部　更新処理 >> prepare");
			this.db_.prepare(strSql);

			int ii = 0;
			for (String bindStr : bindList) {
				db_.setString(++ii, bindStr);
			}

			// UPDATE実行
			db_.setDbFileNm(TBL_T_JYUCYU_HD);
			category__.debug("積荷伝票発行（集約）受注データ／HD部　更新処理 >> update");
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
		category__.info("積荷伝票発行（集約）受注データ／HD部　更新処理　END");
		return res;
	}


	/**
	 * 　積荷データ／ヘッダー部（個別）　追加処理
	 *
	 * @param editHdList
	 * @param pRec
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean insertHdKobetu(JuchuTuminiDenpyoHakoHdList editHdList, JuchuTuminiDenpyoHakoRecord pRec)
		throws DataNotFoundException, UpdateRowException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("積荷データ／HD部（個別）　追加処理　START");

		boolean res = true;

		for (int i = 0; i < editHdList.size(); i++) {
			JuchuTuminiDenpyoHakoHdRecord rec = (JuchuTuminiDenpyoHakoHdRecord) editHdList.get(i);

//			// 未入力行は無視する
//			if (!rec.getIsModified()) {
//				continue;
//			}

			try {

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("積荷データ／HD部（個別）　追加処理 >> SQL文生成");
				String strSql = getSqlInsertHdKobetu(rec, bindList, pRec);
				category__.info("積荷データ／HD部（個別）　追加処理 >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// INSERT実行
				db_.setDbFileNm(TBL_T_TUMINI_HD);
				category__.info("積荷データ／HD部（個別）　追加処理 >> insert");
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

		category__.info("積荷データ／HD部（個別）　追加処理　END");

		return res;
	}


	/**
	 * 　積荷データ／DT部（個別）　追加処理
	 *
	 * @param editDtList
	 * @param pRec
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean insertDtKobetu(JuchuTuminiDenpyoHakoDtList editDtList, JuchuTuminiDenpyoHakoRecord pRec)
		throws DataNotFoundException, UpdateRowException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("積荷データ／DT部（個別）　追加処理　START");

		boolean res = true;

		for (int i = 0; i < editDtList.size(); i++) {
			JuchuTuminiDenpyoHakoDtRecord rec = (JuchuTuminiDenpyoHakoDtRecord) editDtList.get(i);

//			// 未入力行は無視する
//			if (rec.isEmpty()) {
//				continue;
//			}

			try {

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("積荷データ／DT部（個別）　追加処理 >> SQL文生成");
				String strSql = getSqlInsertDtKobetu(rec, bindList, pRec);
				category__.info("積荷データ／DT部（個別）　追加処理 >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// INSERT実行
				db_.setDbFileNm(TBL_T_TUMINI_DT);
				category__.info("積荷データ／DT部（個別）　追加処理 >> insert");
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

		category__.info("積荷データ／DT部（個別）　追加処理　END");

		return res;
	}


	/**
	 * 　積荷データ／HD部（集約）　追加処理
	 *
	 * @param editHdList
	 * @param pRec
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean insertHdSyuyaku(JuchuTuminiDenpyoHakoHdList editHdList, JuchuTuminiDenpyoHakoRecord pRec)
		throws DataNotFoundException, UpdateRowException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("積荷データ／HD部（集約）　追加処理　START");

		boolean res = true;

		for (int i = 0; i < editHdList.size(); i++) {
			JuchuTuminiDenpyoHakoHdRecord rec = (JuchuTuminiDenpyoHakoHdRecord) editHdList.get(i);

			try {

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("積荷データ／HD部（集約）　追加処理 >> SQL文生成");
				String strSql = getSqlInsertHdSyuyaku(rec, bindList, pRec);
				category__.info("積荷データ／HD部（集約）　追加処理 >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// INSERT実行
				db_.setDbFileNm(TBL_T_TUMINI_HD);
				category__.info("積荷データ／HD部（集約）　追加処理 >> insert");
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

		category__.info("積荷データ／HD部（集約）　追加処理　END");

		return res;
	}


	/**
	 * 　積荷データ／DT部（集約）　追加処理
	 *
	 * @param editDtList
	 * @param pRec
	 * @return TRUE:成功 / FALSE:失敗
	 */
	private boolean insertDtSyuyaku(JuchuTuminiDenpyoHakoDtList editDtList, JuchuTuminiDenpyoHakoRecord pRec)
		throws DataNotFoundException, UpdateRowException, DeadLockException, UniqueKeyViolatedException, SQLException {

		category__.info("積荷データ／DT部（集約）　追加処理　START");

		boolean res = true;

		for (int i = 0; i < editDtList.size(); i++) {
			JuchuTuminiDenpyoHakoDtRecord rec = (JuchuTuminiDenpyoHakoDtRecord) editDtList.get(i);

			try {

				List<String> bindList = new ArrayList<String>();

				// SQLを取得
				category__.info("積荷データ／DT部（集約）　追加処理 >> SQL文生成");
				String strSql = getSqlInsertDtSyuyaku(rec, bindList, pRec);
				category__.info("積荷データ／DT部（集約）　追加処理 >> prepare");
				this.db_.prepare(strSql);

				int ii = 0;
				for (String bindStr : bindList) {
					db_.setString(++ii, bindStr);
				}

				// INSERT実行
				db_.setDbFileNm(TBL_T_TUMINI_DT);
				category__.info("積荷データ／DT部（集約）　追加処理 >> insert");
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

		category__.info("積荷データ／DT部（集約）　追加処理　END");

		return res;
	}


	/**
	 * 積荷伝票発行（個別）
	 * 　受注データ／HD部　更新SQL
	 *
	 * @return SQL
	 */
	private String getSqlUpdateKobetu(JuchuTuminiDenpyoHakoRecord rec, List<String> bindList) {

		ComUserSession cus = getComUserSession();

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_JYUCYU_HD			\n");
		sSql.append("SET												\n");
		sSql.append(" 	 TUMIDEN_HAKO_DT = TO_CHAR(SYSDATE,'YYYYMMDD') 	\n");	// 積荷伝票発行日
		sSql.append(" 	,TUMIDEN_HAKO_TM = TO_CHAR(SYSDATE,'HH24MI') 	\n");	// 積荷伝票発行時刻
		sSql.append(" 	,TUMIDEN_NO = ?									\n");	// 積荷伝票NO
		sSql.append(" 	,TUMIDEN_SM_KBN = ? 							\n");	// 積荷累積対象区分
		sSql.append(" 	,TUMIDEN_HAKO_CNT = ? 							\n");	// 積荷伝票発行回数
		sSql.append(" 	,TUMIDEN_HAKOSYA = ? 							\n");	// 積荷伝票発行者ID
		bindList.add(rec.getTumidenNo()			); // 積荷伝票NO（必須項目）
		bindList.add(YN_06_KB_TAISYOGAI		); // 積荷累積対象区分
		bindList.add(rec.getTumidenHakoCnt()	); // 積荷伝票発行回数（必須項目）
		bindList.add(cus.getSgyosyaCd()			); // 積荷伝票発行者ID（必須項目）

		sSql.append("WHERE												\n");
		sSql.append("	KAISYA_CD = ?									\n");
		sSql.append("	AND JYUCYU_NO = ?								\n");
		bindList.add(cus.getKaisyaCd()			); // 会社コード（主キー）
		bindList.add(rec.getJyucyuNo()			); // 受注NO（主キー）

		return sSql.toString();
	}



	/**
	 * 積荷伝票発行（集約）
	 * 　受注データ／HD部　更新SQL
	 *
	 * @return SQL
	 */
	private String getSqlUpdateSyuyaku(JuchuTuminiDenpyoHakoRecord rec, List<String> bindList, JuchuTuminiDenpyoHakoRecord pRec) {

		ComUserSession cus = getComUserSession();

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("UPDATE T_JYUCYU_HD			\n");
		sSql.append("SET												\n");
		sSql.append(" 	 TUMIDEN_HAKO_DT = TO_CHAR(SYSDATE,'YYYYMMDD') 	\n");	// 積荷伝票発行日
		sSql.append(" 	,TUMIDEN_HAKO_TM = TO_CHAR(SYSDATE,'HH24MI') 	\n");	// 積荷伝票発行時刻
		sSql.append(" 	,TUMIDEN_NO = ?									\n");	// 積荷伝票NO
		sSql.append(" 	,TUMIDEN_SM_KBN = ? 							\n");	// 積荷累積対象区分
		sSql.append(" 	,TUMIDEN_HAKO_CNT = ? 							\n");	// 積荷伝票発行回数
		sSql.append(" 	,TUMIDEN_HAKOSYA = ? 							\n");	// 積荷伝票発行者ID
		bindList.add(pRec.getTumidenNo()		); // 積荷伝票NO（必須項目）
		bindList.add(YN_06_KB_TAISYOGAI		); // 積荷累積対象区分
		bindList.add(rec.getTumidenHakoCnt()	); // 積荷伝票発行回数（必須項目）
		bindList.add(cus.getSgyosyaCd()			); // 積荷伝票発行者ID（必須項目）

		sSql.append("WHERE												\n");
		sSql.append("	KAISYA_CD = ?									\n");
		sSql.append("	AND JYUCYU_NO = ?								\n");
		bindList.add(cus.getKaisyaCd()			); // 会社コード（主キー）
		bindList.add(rec.getJyucyuNo()			); // 受注NO（主キー）

		return sSql.toString();
	}


	/**
	 * 積荷データ（ヘッダー部）新規SQL
	 *
	 * @param rec JuchuTuminiDenpyoHakoHdRecord
	 * @param bindList
	 * @param pRec JuchuTuminiDenpyoHakoRecord
	 * @return SQL
	 */
	private String getSqlInsertHdKobetu(JuchuTuminiDenpyoHakoHdRecord rec, List<String> bindList, JuchuTuminiDenpyoHakoRecord pRec) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		sSql.append("INSERT INTO T_TUMINI_HD ( 		\n");
		sSql.append("	 RIYOU_KBN 					\n");// 利用区分
		sSql.append("	,KAISYA_CD 					\n");// 会社CD
		sSql.append("	,TUMIDEN_NO 				\n");// 積荷伝票NO
		sSql.append("	,UNSOTEN_CD 				\n");// 運送店CD
		sSql.append("	,SYUYAKU_KBN 				\n");// 集約区分
		sSql.append("	,SYUKA_DT 					\n");// 出荷日
		sSql.append("	,DENPYO_HAKO_DT 			\n");// 伝票発行日
		sSql.append("	,DENPYO_HAKO_TM 			\n");// 伝票発行時刻
		sSql.append("	,SOU_JYURYO 				\n");// 総重量(KG)
		sSql.append("	,SOU_SURYO_CASE 			\n");// 総数量 ケース
		sSql.append("	,SOU_SURYO_BARA 			\n");// 総数量 バラ
		sSql.append(") VALUES ( \n");
		sSql.append(" 	 ? \n"); // 利用区分
		sSql.append("	,? \n"); // 会社CD
		sSql.append(" 	,? \n"); // 積荷伝票NO
		sSql.append(" 	,? \n"); // 運送店CD
		sSql.append(" 	,? \n"); // 集約区分
		sSql.append(" 	,? \n"); // 出荷日
		sSql.append(" 	,TO_CHAR(SYSDATE,'YYYYMMDD') \n"); // 伝票発行日
		sSql.append(" 	,TO_CHAR(SYSDATE,'HH24MI') \n"); // 伝票発行時刻
		sSql.append(" 	,? \n"); // 総重量(KG)
		sSql.append(" 	,? \n"); // 総数量 ケース
		sSql.append(" 	,? \n"); // 総数量 バラ
		sSql.append(") \n");
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(AVAILABLE_KB_RIYO_KA); 		// 利用区分（必須項目）
		bindList.add(cus.getKaisyaCd()); 			// 会社CD (FK)（必須項目）
		bindList.add(pRec.getTumidenNo()); 			// 積荷伝票NO（必須項目）
		bindList.add(pRec.getUnsotenCd()); 			// 運送店CD（必須項目）
		bindList.add(pRec.getSyuyakuKbn()); 		// 集約区分（必須項目）
		bindList.add(pRec.getSyukaDt()); 			// 出荷日(売上伝票発行日)（必須項目）
		// 伝票発行日　→　SQLでセット
		// 伝票発行時刻　→　SQLでセット
		bindList.add(rec.getSouJyuryo()); 			// 総重量(KG)（必須項目）
		bindList.add(rec.getSouSuryoCase()); 		// 総数量 ケース（必須項目）
		bindList.add(rec.getSouSuryoBara()); 		// 総数量 バラ（必須項目）

		return sSql.toString();
	}


	/**
	 * 積荷データ（ディテール部）新規SQL
	 *
	 * @param rec JuchuTuminiDenpyoHakoDtRecord
	 * @param bindList
	 * @param pRec JuchuTuminiDenpyoHakoRecord
	 * @return SQL
	 */
	private String getSqlInsertDtKobetu(JuchuTuminiDenpyoHakoDtRecord rec, List<String> bindList, JuchuTuminiDenpyoHakoRecord pRec) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("INSERT INTO T_TUMINI_DT ( 		\n");
		sSql.append("	 RIYOU_KBN 					\n");// 利用区分
		sSql.append("	,KAISYA_CD 					\n");// 会社CD
		sSql.append("	,TUMIDEN_NO 				\n");// 積荷伝票NO
		sSql.append(" 	,INPUT_LINE_NO 				\n");// レコードNO
		sSql.append(" 	,TUMINOKOSI_FLG 			\n");// 積み残しフラグ
		sSql.append(" 	,JYUCYU_NO 					\n");// 受注NO
		sSql.append(" 	,SYUKA_SURYO_CASE 			\n");// 出荷数量 ケース
		sSql.append(" 	,SYUKA_SURYO_BARA 			\n");// 出荷数量 バラ
		sSql.append(" 	,JYURYO_TOT 				\n");// 重量計(KG)
		sSql.append(") VALUES ( \n");
		sSql.append("	 ? \n"); 	// 利用区分
		sSql.append("	,? \n"); 	// 会社CD
		sSql.append("	,? \n"); 	// 積荷伝票NO
		sSql.append("	,? \n"); 	// レコードNO
		sSql.append("	,? \n"); 	// 積み残しフラグ
		sSql.append("	,? \n"); 	// 受注NO
		sSql.append("	,? \n"); 	// 出荷数量 ケース
		sSql.append("	,? \n"); 	// 出荷数量 バラ
		sSql.append("	,? \n"); 	// 重量計(KG)
		sSql.append(") \n");

		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); 			// 利用区分（必須項目）
		bindList.add(rec.getKaisyaCd()); 			// 会社CD（必須項目）
		bindList.add(pRec.getTumidenNo()); 			// 積荷伝票NO（必須項目）
		bindList.add(rec.getInputLineNo()); 		// レコードNO（必須項目）
		bindList.add(rec.getTuminokosiFlg()); 		// 積み残しフラグ（必須項目）
		bindList.add(rec.getJyucyuNo()); 			// 受注NO（必須項目）
		bindList.add(rec.getSyukaSuryoCase()); 		// 出荷数量 ケース（必須項目）
		bindList.add(rec.getSyukaSuryoBara()); 		// 出荷数量 バラ（必須項目）
		bindList.add(rec.getJyuryoTot()); 			// 重量計(KG)（必須項目）

		return sSql.toString();
	}


	/**
	 * 積荷データ／HD部（集約）追加SQL
	 *
	 * @param rec JuchuTuminiDenpyoHakoHdRecord
	 * @param bindList
	 * @param pRec
	 * @return SQL
	 */
	private String getSqlInsertHdSyuyaku(JuchuTuminiDenpyoHakoHdRecord rec, List<String> bindList, JuchuTuminiDenpyoHakoRecord pRec) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		sSql.append("INSERT INTO T_TUMINI_HD ( 		\n");
		sSql.append("	 RIYOU_KBN 					\n");// 利用区分
		sSql.append("	,KAISYA_CD 					\n");// 会社CD
		sSql.append("	,TUMIDEN_NO 				\n");// 積荷伝票NO
		sSql.append("	,UNSOTEN_CD 				\n");// 運送店CD
		sSql.append("	,SYUYAKU_KBN 				\n");// 集約区分
		sSql.append("	,SYUKA_DT 					\n");// 出荷日
		sSql.append("	,DENPYO_HAKO_DT 			\n");// 伝票発行日
		sSql.append("	,DENPYO_HAKO_TM 			\n");// 伝票発行時刻
		sSql.append("	,SOU_JYURYO 				\n");// 総重量(KG)
		sSql.append("	,SOU_SURYO_CASE 			\n");// 総数量 ケース
		sSql.append("	,SOU_SURYO_BARA 			\n");// 総数量 バラ
		sSql.append(") VALUES ( \n");
		sSql.append(" 	 ? \n"); // 利用区分
		sSql.append("	,? \n"); // 会社CD
		sSql.append(" 	,? \n"); // 積荷伝票NO
		sSql.append(" 	,? \n"); // 運送店CD
		sSql.append(" 	,? \n"); // 集約区分
		sSql.append(" 	,? \n"); // 出荷日
		sSql.append(" 	,TO_CHAR(SYSDATE,'YYYYMMDD') \n"); // 伝票発行日
		sSql.append(" 	,TO_CHAR(SYSDATE,'HH24MI') \n"); // 伝票発行時刻
		sSql.append(" 	,? \n"); // 総重量(KG)
		sSql.append(" 	,? \n"); // 総数量 ケース
		sSql.append(" 	,? \n"); // 総数量 バラ
		sSql.append(") \n");
		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(AVAILABLE_KB_RIYO_KA); 		// 利用区分（必須項目）
		bindList.add(cus.getKaisyaCd()); 			// 会社CD (FK)（必須項目）
		bindList.add(pRec.getTumidenNo()); 			// 積荷伝票NO（必須項目）
		bindList.add(pRec.getUnsotenCd()); 			// 運送店CD（必須項目）
		bindList.add(pRec.getSyuyakuKbn()); 		// 集約区分（必須項目）
		bindList.add(pRec.getSyukaDt()); 			// 出荷日(売上伝票発行日)（必須項目）
		// 伝票発行日　→　SQLでセット
		// 伝票発行時刻　→　SQLでセット
		bindList.add(rec.getSouJyuryo()); 			// 総重量(KG)（必須項目）
		bindList.add(rec.getSouSuryoCase()); 		// 総数量 ケース（必須項目）
		bindList.add(rec.getSouSuryoBara()); 		// 総数量 バラ（必須項目）

		return sSql.toString();
	}


	/**
	 * 積荷データ／DT部（集約）追加SQL
	 *
	 * @param hdRec
	 * @param bindList
	 * @param pRec
	 * @return SQL
	 */
	private String getSqlInsertDtSyuyaku(JuchuTuminiDenpyoHakoDtRecord rec, List<String> bindList, JuchuTuminiDenpyoHakoRecord pRec) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("INSERT INTO T_TUMINI_DT ( 		\n");
		sSql.append("	 RIYOU_KBN 					\n");// 利用区分
		sSql.append("	,KAISYA_CD 					\n");// 会社CD
		sSql.append("	,TUMIDEN_NO 				\n");// 積荷伝票NO
		sSql.append(" 	,INPUT_LINE_NO 				\n");// レコードNO
		sSql.append(" 	,TUMINOKOSI_FLG 			\n");// 積み残しフラグ
		sSql.append(" 	,JYUCYU_NO 					\n");// 受注NO
		sSql.append(" 	,SYUKA_SURYO_CASE 			\n");// 出荷数量 ケース
		sSql.append(" 	,SYUKA_SURYO_BARA 			\n");// 出荷数量 バラ
		sSql.append(" 	,JYURYO_TOT 				\n");// 重量計(KG)
		sSql.append(") VALUES ( \n");
		sSql.append("	 ? \n"); 	// 利用区分
		sSql.append("	,? \n"); 	// 会社CD
		sSql.append("	,? \n"); 	// 積荷伝票NO
		sSql.append("	,? \n"); 	// レコードNO
		sSql.append("	,? \n"); 	// 積み残しフラグ
		sSql.append("	,? \n"); 	// 受注NO
		sSql.append("	,? \n"); 	// 出荷数量 ケース
		sSql.append("	,? \n"); 	// 出荷数量 バラ
		sSql.append("	,? \n"); 	// 重量計(KG)
		sSql.append(") \n");

		// ""(0長)の場合には、BindList作成時にPbsUtil.getChar関数(空白１)を入れます（NULL項目）
		bindList.add(rec.getRiyouKbn()); 			// 利用区分（必須項目）
		bindList.add(rec.getKaisyaCd()); 			// 会社CD（必須項目）
		bindList.add(pRec.getTumidenNo()); 			// 積荷伝票NO（必須項目）
		bindList.add(rec.getInputLineNo()); 		// レコードNO（必須項目）
		bindList.add(rec.getTuminokosiFlg()); 		// 積み残しフラグ（必須項目）
		bindList.add(rec.getJyucyuNo()); 			// 受注NO（必須項目）
		bindList.add(rec.getSyukaSuryoCase()); 		// 出荷数量 ケース（必須項目）
		bindList.add(rec.getSyukaSuryoBara()); 		// 出荷数量 バラ（必須項目）
		bindList.add(rec.getJyuryoTot()); 			// 重量計(KG)（必須項目）

		return sSql.toString();
	}



	/**
	 * XXX:受注データ／HD部の行ロックを実行する。
	 *
	 * @param ccNo
	 * @param record
	 * @throws ResourceBusyNowaitException
	 * @throws DeadLockException
	 * @throws DataNotFoundException
	 * @throws SQLException
	 */
	private void lock(Integer ccGyoLock, JuchuTuminiDenpyoHakoRecord rec)
			throws ResourceBusyNowaitException, DeadLockException, DataNotFoundException {

		// ロック処理
		db_.setDbFileNm(TBL_T_JYUCYU_HD);
		category__.info("受注データ／HD部 >> 修正処理(行ロック) >> bind ");
		lockBind(ccGyoLock, rec);
		category__.info("受注データ／HD部 >> 修正処理(行ロック) >> executeForLock");

		// 行ロック実行
		db_.executeForLock(ccGyoLock);
		if (!db_.next(ccGyoLock)) {
			db_.setGeneralErrorMsg("DataNotFoundException", ccGyoLock);
			throw new DataNotFoundException("Data Not Found!");
		}
	}


	/**
	 * XXX:行ロックSQLにバインド変数を設定する
	 * @param ccUpdate
	 * @param rec
	 */
	private void lockBind(Integer ccGyoLock, JuchuTuminiDenpyoHakoRecord rec){
		int i = 0;

		db_.setString(ccGyoLock, ++i, rec.getKaisyaCd());   // 会社CD
		db_.setString(ccGyoLock, ++i, rec.getJyucyuNo()); 	// 受注No
		db_.setString(ccGyoLock, ++i, rec.getKosnNo());     // 更新回数
	}


	/**
	 * 行ロック用SQLを取得する
	 * @return SQL
	 */
	private String sqlGyoLock() {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("SELECT							\n");
		sSql.append("	 JYUHD.KAISYA_CD			\n");
		sSql.append("	,JYUHD.JYUCYU_NO			\n");
		sSql.append("	,JYUHD.KOUSIN_NO			\n");
		sSql.append("FROM T_JYUCYU_HD JYUHD			\n");
		sSql.append("WHERE							\n");
		sSql.append("	JYUHD.KAISYA_CD = ?			\n");
		sSql.append("	AND JYUHD.JYUCYU_NO = ?		\n");
		sSql.append("	AND JYUHD.KOUSIN_NO = ?		\n");
		sSql.append("	FOR UPDATE NOWAIT			\n");
		return sSql.toString();
	}


	/**
	 * 積荷伝票発行処理
	 * 出荷データの存在チェック（受注NOで検索）
	 *
	 * @param kaisyaCd 会社CD
	 * @param rec
	 * @return TRUE:既に存在する / FALSE:存在しない
	 */
	public boolean isExistSyukaData(String kaisyaCd, JuchuTuminiDenpyoHakoRecord rec)
			throws KitException {

		boolean ret = false;

		category__.info("出荷データの存在チェック検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("出荷データの存在チェック >> 検索処理 >> SQL文生成");
		String searchSql = getExistSyukaDataSearchSql(kaisyaCd, rec, bindList);

		category__.info("出荷データの存在チェック >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("出荷データの存在チェック >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("出荷データの存在チェック検索処理 終了");

		if (records_ != null) {
			ret = true;
		}

		return ret;
	}


	/**
	 * 個別伝票発行処理
	 * 出荷データの存在チェックSQL
	 *
	 * @param kaisyaCd
	 * @param rec
	 * @param bindList
	 * @return sSql
	 */
	private String getExistSyukaDataSearchSql(String kaisyaCd, JuchuTuminiDenpyoHakoRecord rec, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("SELECT \n");
		sSql.append(" A.URIDEN_NO \n"); // 売上伝票NO
		sSql.append(",A.JYUCYU_NO \n"); // 受注NO
		sSql.append("FROM T_SYUKA_HD A \n");

		sSql.append("WHERE \n");

		// 利用区分
		sSql.append(" A.RIYOU_KBN = ? \n");
		bindList.add(AVAILABLE_KB_RIYO_KA);

		// 会社コード
		sSql.append("AND A.KAISYA_CD = ? \n");
		bindList.add(kaisyaCd);

		// 受注NO
		sSql.append("AND A.JYUCYU_NO = ? \n");
		bindList.add(rec.getJyucyuNo());

		return sSql.toString();
	}


	/**
	 * 集約伝票発行処理
	 * 出荷データの存在チェック（受注NOで検索）
	 *
	 * @param syuyakuList
	 * @param inStr
	 * @return TRUE:既に存在する / FALSE:存在しない
	 */
	public boolean isExistSyukaDataSyuyaku(JuchuTuminiDenpyoHakoList syuyakuList, String inStr) {
		boolean ret = false;

		category__.info("出荷データの存在チェック検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("出荷データの存在チェック >> 検索処理 >> SQL文生成");
		String searchSql = getExistSyukaDataSearchSqlSyuyaku(syuyakuList, inStr, bindList);

		category__.info("出荷データの存在チェック >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("出荷データの存在チェック >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("出荷データの存在チェック検索処理 終了");

		if (records_ != null) {
			ret = true;
		}

		return ret;
	}


	/**
	 * 集約伝票発行処理
	 * 出荷データの存在チェックSQL
	 *
	 * @param kaisyaCd
	 * @param jyucyuNo
	 * @param bindList
	 * @return sSql
	 */
	private String getExistSyukaDataSearchSqlSyuyaku(JuchuTuminiDenpyoHakoList syuyakuList, String inStr, List<String> bindList) {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		sSql.append("SELECT \n");
		sSql.append(" A.URIDEN_NO \n"); // 売上伝票NO
		sSql.append(",A.JYUCYU_NO \n"); // 受注NO
		sSql.append("FROM T_SYUKA_HD A \n");

		sSql.append("WHERE \n");

		// 利用区分
		sSql.append(" A.RIYOU_KBN = ? \n");
		bindList.add(AVAILABLE_KB_RIYO_KA);

		// 会社コード
		sSql.append("AND A.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 受注NO
		sSql.append("	AND JCUDT.JYUCYU_NO");
		sSql.append(inStr);
		for (int i = 0; i < syuyakuList.size(); i++) {
			JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord)syuyakuList.get(i);
			bindList.add(rec.getJyucyuNo());
		}

		return sSql.toString();
	}


	/**
	 * 個別伝票発行処理
	 * 伝票発行用データを生成
	 *
	 * @param rec JuchuTuminiDenpyoHakoRecord
	 * @param printerId プリンタID
	 * @return 実行結果
	 * @throws DataNotFoundException
	 * @throws KitComException
	 */
	private boolean createDenpyoHakoDataKobetu(JuchuTuminiDenpyoHakoRecord rec, String printerId)
			throws DataNotFoundException, KitComException {

		try {
			// データ取得
			PbsRecord[] recs = printServ.executeKobetu(rec);

			if (recs == null) {
				return false;
			}

			// 帳票出力TSV用データ生成
			String[] jyucyuNos = {rec.getJyucyuNo()}; // 個別なので受注NOは一つ
			recs = createDataForTsv(recs, jyucyuNos);

			// 印刷処理
			boolean ret = printServ.executePrintHako(recs, printerId);
			if (!ret) {
				return false;
			}
		} catch (DataNotFoundException e) {
			throw e;
		} catch (KitComException e) {
			throw e;
		}

		return true;
	}


	/**
	 * 集約伝票発行処理
	 * 伝票発行用データを生成
	 *
	 * @param rec JuchuTuminiDenpyoHakoRecord
	 * @param list JuchuTuminiDenpyoHakoList
	 * @param editItemList JuchuTuminiDenpyoHakoItemList
	 * @param jyucyuNos
	 * @param printerId プリンタID
	 * @return 実行結果
	 * @throws DataNotFoundException
	 * @throws KitComException
	 */
	private boolean createDenpyoHakoDataSyuyaku(JuchuTuminiDenpyoHakoRecord rec, JuchuTuminiDenpyoHakoList list, JuchuTuminiDenpyoHakoItemList editItemList, String[] jyucyuNos, String printerId)
			throws DataNotFoundException, KitComException {

		try {
			// データ取得
			PbsRecord[] recs = printServ.executeSyuyaku(rec, list, editItemList);

			if (recs == null) {
				return false;
			}

			// 帳票出力TSV用データ生成
			recs = createDataForTsv(recs, jyucyuNos);

			// 印刷処理
			boolean ret = printServ.executePrintHako(recs, printerId);
			if (!ret) {
				return false;
			}
		} catch (DataNotFoundException e) {
			throw e;
		} catch (KitComException e) {
			throw e;
		}

		return true;
	}


	/**
	 * 帳票出力TSV用データ生成
	 *
	 * @param recs
	 * @param jyucyuNos
	 * @return
	 */
	private PbsRecord[] createDataForTsv(PbsRecord[] recs, String[] jyucyuNos) {

		// 作業用リストに変換
		List<PbsRecord> listRecs = new ArrayList<PbsRecord>();
		listRecs.addAll(Arrays.asList(recs));

		// ディテール部の行数
		int dtLen = listRecs.size();

		// 受注NOの数
		int jLen = jyucyuNos.length;

		// ディテール部の必要ページ数
		int dtPage = (dtLen - 1) / MAX_ITEM_CNT + 1;

		// 受注NO部の必要ページ数
		int jPage = (jLen - 1) / MAX_JYUCYU_NO_CNT + 1;

		// 伝票のページ数
		int maxPage = Math.max(dtPage, jPage);

		// TSVファイルの行数
		int tsvLen = Math.max((maxPage - 1) * MAX_ITEM_CNT + 1, dtLen);

		// 摘要区分　積荷伝票記載フラグの取得処理
		this.setTumidenKisaiFlgs(recs);
		// 摘要内容の整形
		this.constTekiyouKbn(recs);

		// recsのサイズをtsvLenに合わせる
		for (int i = 0; i < tsvLen; i++) {
			if (i > (dtLen - 1)) {
				// ヘッダー情報をセット
				PbsRecord tsvRec = new PbsRecord();
				tsvRec.put("TUMIDEN_NO", recs[0].get("TUMIDEN_NO")); // 積荷伝票NO
				tsvRec.put("DENPYO_HAKO_DT", recs[0].get("DENPYO_HAKO_DT")); // 伝票発行日
				tsvRec.put("DENPYO_HAKO_TM", recs[0].get("DENPYO_HAKO_TM")); // 伝票発行時刻
				tsvRec.put("UNSOTEN_CD", recs[0].get("UNSOTEN_CD")); // 運送店CD
				tsvRec.put("UNSOTEN_NM", recs[0].get("UNSOTEN_NM")); // 運送店名
				tsvRec.put("TATESN_CD", recs[0].get("TATESN_CD")); // 縦線CD
				tsvRec.put("OROSITEN_CD", recs[0].get("OROSITEN_CD")); // 最終送荷先卸CD
				tsvRec.put("OROSITEN_NM", recs[0].get("OROSITEN_NM")); // 最終送荷先卸名
				tsvRec.put("SYUHANTEN_CD", recs[0].get("SYUHANTEN_CD")); // 酒販店（統一）CD
				tsvRec.put("SYUHANTEN_NM", recs[0].get("SYUHANTEN_NM")); // 酒販店名
				tsvRec.put("TEKIYO_NM_ALL", recs[0].get("TEKIYO_NM_ALL")); // 整形した摘要内容
				listRecs.add(tsvRec);
			}
		}

		// 受注NOを追加
		for (int i = 0; i < listRecs.size(); i++) {
			for (int j = 0; j < jLen; j++) {
				int idx_j = i / MAX_ITEM_CNT * MAX_JYUCYU_NO_CNT + j;
				if (idx_j < jLen) {
					(listRecs.get(i)).put("JYUCYU_NO_" + (j + 1), jyucyuNos[idx_j]);
				}
			}
		}

		// 作業用リストからPbsRecord[]に変換
		recs = (PbsRecord[]) listRecs.toArray(new PbsRecord[listRecs.size()]);

		return recs;
	}

	/**
	 * 伝票NOを自動採番して返す
	 */
	private String getDenNo() {
		// 自動採番
		JuchuTuminiDenpyoHako_NumberClient numberClient = JuchuTuminiDenpyoHako_NumberClient.getInstance();
		String nextDenNo = numberClient.getNextDenNo(getDatabase(), SEQ_T_TUMINI_DEN_NO, MAX_LEN_TUMIDEN_NO);

		return nextDenNo;
	}




}
