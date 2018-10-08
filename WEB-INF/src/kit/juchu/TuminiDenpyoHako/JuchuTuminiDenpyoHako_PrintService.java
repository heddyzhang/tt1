package kit.juchu.TuminiDenpyoHako;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComCsvConst.*;
import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComUserSession;
import fb.com.exception.KitComException;
import fb.inf.KitService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.MaxRowsException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;


/**
 * 伝票発行を行う
 */
public class JuchuTuminiDenpyoHako_PrintService extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = 4626940765923324528L;

	/** クラス名. */
	private static String className__ = JuchuTuminiDenpyoHako_PrintService.class.getName();

	/** カテゴリ. */
	public static Category category__ = Category.getInstance(className__);


	/**
	 * コンストラクタ.
	 *
	 * @param cus getComUserSession()を渡すこと。
	 * @param db_ 呼び出すときにはgetDatabase()を渡すこと。
	 * @param isTran isTransaction()を渡すこと。
	 * @param ae getActionErrors()を渡すこと。
	 */
	public JuchuTuminiDenpyoHako_PrintService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
		super(cus, db_, isTran, ae);
	}


	/**
	 * 個別発行処理
	 * データ取得SQLの発行と実行
	 *
	 * @return
	 */
	public PbsRecord[] searchKobetu(JuchuTuminiDenpyoHakoRecord rec) throws DataNotFoundException {

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		ComUserSession cus = getComUserSession();

		StringBuilder sSql = new StringBuilder();
		sSql.append("SELECT \n");

		// ■伝票ヘッダー部■
		sSql.append("  TUMHD.TUMIDEN_NO AS TUMIDEN_NO  \n"); // 積荷伝票NO
		sSql.append(" ,TUMHD.DENPYO_HAKO_DT AS DENPYO_HAKO_DT  \n"); // 伝票発行日
		sSql.append(" ,TUMHD.DENPYO_HAKO_TM AS DENPYO_HAKO_TM  \n"); // 伝票発行時刻
		sSql.append(" ,TUMHD.UNSOTEN_CD AS UNSOTEN_CD  \n"); // 運送店CD
		sSql.append(" ,UST.UNSOTEN_NM AS UNSOTEN_NM  \n");// 運送店名
		sSql.append(" ,JYUHD.TATESN_CD AS TATESN_CD \n"); // 縦線CD
		sSql.append(" ,ORSHD.OROSITEN_CD_LAST AS OROSITEN_CD \n"); // 最終送荷先卸CD
		sSql.append(" ,ORS.TEN_NM1_JISYA AS OROSITEN_NM \n"); // 最終送荷先卸名
		sSql.append(" ,F_RTRIM(JYUHD.SYUHANTEN_CD) AS SYUHANTEN_CD \n"); // 酒販店（統一）CD
		sSql.append(" ,F_RTRIM(SYU.TEN_NM_YAGO) AS SYUHANTEN_NM \n"); // 酒販店名
		sSql.append(" ,F_RTRIM(JYUHD.TEKIYO_KBN1) AS TEKIYO_KBN1 \n"); // 摘要区分(01)
		sSql.append(" ,F_RTRIM(JYUHD.TEKIYO_NM1) AS TEKIYO_NM1 \n"); // 摘要内容(01)
		sSql.append(" ,F_RTRIM(JYUHD.TEKIYO_KBN2) AS TEKIYO_KBN2 \n"); // 摘要区分(02)
		sSql.append(" ,F_RTRIM(JYUHD.TEKIYO_NM2) AS TEKIYO_NM2 \n"); // 摘要内容(02)
		sSql.append(" ,F_RTRIM(JYUHD.TEKIYO_KBN3) AS TEKIYO_KBN3 \n"); // 摘要区分(03)
		sSql.append(" ,F_RTRIM(JYUHD.TEKIYO_NM3) AS TEKIYO_NM3 \n"); // 摘要内容(03)
		sSql.append(" ,F_RTRIM(JYUHD.TEKIYO_KBN4) AS TEKIYO_KBN4 \n"); // 摘要区分(04)
		sSql.append(" ,F_RTRIM(JYUHD.TEKIYO_NM4) AS TEKIYO_NM4 \n"); // 摘要内容(04)
		sSql.append(" ,F_RTRIM(JYUHD.TEKIYO_KBN5) AS TEKIYO_KBN5 \n"); // 摘要区分(05)
		sSql.append(" ,F_RTRIM(JYUHD.TEKIYO_NM5) AS TEKIYO_NM5 \n"); // 摘要内容(05)

		// ■受注NO部■
		sSql.append(" ,JYUHD.JYUCYU_NO AS JYUCYU_NO_1 \n"); // 受注NO

		// ■商品リスト部■
		sSql.append(" ,JYUDT.SHOHIN_CD AS SHOHIN_CD \n"); // 商品CD
		sSql.append(" ,F_RTRIM(SYO.TUMIDEN_LINE_NO) AS TUMIDEN_LINE_NO \n"); // 積荷伝票用ラインNO
		sSql.append(" ,SYO.SHOHIN_NM_TUMIDEN AS SHOHIN_NM_TUMIDEN \n"); // 商品名_積荷伝票用
		sSql.append(" ,SYO.YOUKI_KIGO_NM2 AS YOUKI_KIGO_NM2 \n"); // 積伝用 ｾｯﾄ記号/容量名(2)
		sSql.append(" ,JYUDT.SYUKA_SU_CASE AS SYUKA_SU_CASE \n"); // 出荷数量_箱数
		sSql.append(" ,JYUDT.SYUKA_SU_BARA AS SYUKA_SU_BARA \n"); // 出荷数量_セット数
		sSql.append(" ,JYUDT.SYUKA_ALL_WEIGTH AS SYUKA_ALL_WEIGTH \n"); // 出荷重量（KG)

		sSql.append("FROM T_TUMINI_HD TUMHD  \n");

		sSql.append("INNER JOIN T_TUMINI_DT TUMDT ON (TUMHD.KAISYA_CD = TUMDT.KAISYA_CD AND TUMHD.TUMIDEN_NO = TUMDT.TUMIDEN_NO)  \n");
		sSql.append("LEFT JOIN M_UNSOTEN UST ON (TUMHD.KAISYA_CD = UST.KAISYA_CD AND TUMHD.UNSOTEN_CD = UST.UNSOTEN_CD)  \n");
		sSql.append("INNER JOIN T_JYUCYU_HD JYUHD ON (TUMDT.KAISYA_CD = JYUHD.KAISYA_CD AND TUMDT.JYUCYU_NO = JYUHD.JYUCYU_NO)  \n");
		sSql.append("INNER JOIN T_JYUCYU_DT JYUDT ON (JYUHD.KAISYA_CD = JYUDT.KAISYA_CD AND JYUHD.JYUCYU_NO = JYUDT.JYUCYU_NO)  \n");
		sSql.append("LEFT JOIN M_OROSISYOSAI_HD ORSHD ON (ORSHD.KAISYA_CD = JYUHD.KAISYA_CD AND ORSHD.TATESN_CD = JYUHD.TATESN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORS ON (ORS.KAISYA_CD = ORSHD.KAISYA_CD AND ORS.OROSITEN_CD = ORSHD.OROSITEN_CD_LAST) \n");
		sSql.append("LEFT JOIN M_SYUHANTEN SYU ON (SYU.SYUHANTEN_CD = JYUHD.SYUHANTEN_CD) \n");
		sSql.append("LEFT JOIN M_SYOHIN SYO ON (JYUDT.KAISYA_CD = SYO.KAISYA_CD AND JYUDT.KTKSY_CD = SYO.KTKSY_CD AND JYUDT.SHOHIN_CD = SYO.SHOHIN_CD) \n");

		// 抽出条件
		sSql.append("WHERE \n");
		sSql.append(" TUMHD.KAISYA_CD = ? \n");
		sSql.append(" AND TUMHD.TUMIDEN_NO = ? \n");

		// 物品区分により伝票記載対象外の商品を除外
		sSql.append(" AND JYUDT.BUPIN_KBN NOT IN (?) \n");

		// ソート
		sSql.append("ORDER BY \n");
		sSql.append(" JYUCYU_NO_1,TUMIDEN_LINE_NO,SHOHIN_CD\n");

		int iCc = db.prepare(true, sSql.toString());

		int i=0;
		db.setString(iCc, ++i, cus.getKaisyaCd());
		db.setString(iCc, ++i, rec.getTumidenNo());
		db.setString(iCc, ++i, SYOHIN_KB_UNTIN);

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] records_ = null;
		try {
			records_ = db.getPbsRecords(iCc,false);
		} catch (MaxRowsException e) {
			category__.warn(e.getMessage());

			// 最大行数を超えたときは、
			// 上限以上のレコードを切り捨ててデータを取得する
			records_ = e.getReturnValues();
			// 最大件数を超えている旨をエラーメッセージ出力
			setErrorMessageId(e.GUIDE_MAXROWS_KEY, e.getLimit());

		} catch (DataNotFoundException e) {
			category__.warn(e.getMessage());
			throw e;
		}

		return records_;
	}


	/**
	 * 集約発行処理
	 * データ取得SQLの発行と実行
	 *
	 * @return
	 */
	public PbsRecord[] searchSyuyaku(JuchuTuminiDenpyoHakoRecord rec, JuchuTuminiDenpyoHakoList list, JuchuTuminiDenpyoHakoItemList editItemList) throws DataNotFoundException {

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		ComUserSession cus = getComUserSession();

		StringBuilder sSql = new StringBuilder();
		sSql.append("SELECT DISTINCT  \n");

		// ■伝票ヘッダー部■
		sSql.append("  TUMHD.TUMIDEN_NO AS TUMIDEN_NO  \n"); // 積荷伝票NO
		sSql.append(" ,TUMHD.DENPYO_HAKO_DT AS DENPYO_HAKO_DT  \n"); // 伝票発行日
		sSql.append(" ,TUMHD.DENPYO_HAKO_TM AS DENPYO_HAKO_TM  \n"); // 伝票発行時刻
		sSql.append(" ,TUMHD.UNSOTEN_CD AS UNSOTEN_CD  \n"); // 運送店CD
		sSql.append(" ,UST.UNSOTEN_NM AS UNSOTEN_NM  \n");// 運送店名

		// ■商品リスト部■
		sSql.append(" ,JYUDT.SHOHIN_CD AS SHOHIN_CD \n"); // 商品CD
		sSql.append(" ,F_RTRIM(SYO.TUMIDEN_LINE_NO) AS TUMIDEN_LINE_NO \n"); // 積荷伝票用ラインNO
		sSql.append(" ,SYO.SHOHIN_NM_TUMIDEN AS SHOHIN_NM_TUMIDEN \n"); // 商品名_積荷伝票用
		sSql.append(" ,SYO.YOUKI_KIGO_NM2 AS YOUKI_KIGO_NM2 \n"); // 積伝用 ｾｯﾄ記号/容量名(2)
		sSql.append(" ,SUM(CASE WHEN JYUDT.SYUKA_SU_CASE IS NULL THEN 0 ELSE JYUDT.SYUKA_SU_CASE END) OVER(PARTITION BY JYUDT.SHOHIN_CD) AS SYUKA_SU_CASE \n"); // 出荷数量_箱数
		sSql.append(" ,SUM(CASE WHEN JYUDT.SYUKA_SU_BARA IS NULL THEN 0 ELSE JYUDT.SYUKA_SU_BARA END) OVER(PARTITION BY JYUDT.SHOHIN_CD) AS SYUKA_SU_BARA \n"); // 出荷数量_セット数
		sSql.append(" ,SUM(CASE WHEN JYUDT.SYUKA_ALL_WEIGTH IS NULL THEN 0 ELSE JYUDT.SYUKA_ALL_WEIGTH END) OVER(PARTITION BY JYUDT.SHOHIN_CD) AS SYUKA_ALL_WEIGTH \n"); // 出荷重量（KG)

		sSql.append("FROM T_TUMINI_HD TUMHD  \n");

		sSql.append("INNER JOIN T_TUMINI_DT TUMDT ON (TUMHD.KAISYA_CD = TUMDT.KAISYA_CD AND TUMHD.TUMIDEN_NO = TUMDT.TUMIDEN_NO)  \n");
		sSql.append("LEFT JOIN M_UNSOTEN UST ON (TUMHD.KAISYA_CD = UST.KAISYA_CD AND TUMHD.UNSOTEN_CD = UST.UNSOTEN_CD)  \n");
		sSql.append("INNER JOIN T_JYUCYU_HD JYUHD ON (TUMDT.KAISYA_CD = JYUHD.KAISYA_CD AND TUMDT.JYUCYU_NO = JYUHD.JYUCYU_NO)  \n");
		sSql.append("INNER JOIN T_JYUCYU_DT JYUDT ON (JYUHD.KAISYA_CD = JYUDT.KAISYA_CD AND JYUHD.JYUCYU_NO = JYUDT.JYUCYU_NO)  \n");
		sSql.append("LEFT JOIN M_OROSISYOSAI_HD ORSHD ON (ORSHD.KAISYA_CD = JYUHD.KAISYA_CD AND ORSHD.TATESN_CD = JYUHD.TATESN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORS ON (ORS.KAISYA_CD = ORSHD.KAISYA_CD AND ORS.OROSITEN_CD = ORSHD.OROSITEN_CD_LAST) \n");
		sSql.append("LEFT JOIN M_SYUHANTEN SYU ON (SYU.SYUHANTEN_CD = JYUHD.SYUHANTEN_CD) \n");
		sSql.append("LEFT JOIN M_SYOHIN SYO ON (JYUDT.KAISYA_CD = SYO.KAISYA_CD AND JYUDT.KTKSY_CD = SYO.KTKSY_CD AND JYUDT.SHOHIN_CD = SYO.SHOHIN_CD) \n");

		// 抽出条件
		sSql.append("WHERE \n");
		sSql.append(" TUMHD.KAISYA_CD = ? \n");
		sSql.append(" AND TUMHD.TUMIDEN_NO = ? \n");

		// 物品区分により伝票記載対象外の商品を除外
		sSql.append(" AND JYUDT.BUPIN_KBN NOT IN (?) \n");

		// ソート
		sSql.append("ORDER BY \n");
		sSql.append(" TUMIDEN_LINE_NO,SHOHIN_CD\n");

		int iCc = db.prepare(true, sSql.toString());

		int i=0;
		db.setString(iCc, ++i, cus.getKaisyaCd());
		db.setString(iCc, ++i, rec.getTumidenNo());
		db.setString(iCc, ++i, SYOHIN_KB_UNTIN);

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] records_ = null;
		try {
			records_ = db.getPbsRecords(iCc,false);
		} catch (MaxRowsException e) {
			category__.warn(e.getMessage());

			// 最大行数を超えたときは、
			// 上限以上のレコードを切り捨ててデータを取得する
			records_ = e.getReturnValues();
			// 最大件数を超えている旨をエラーメッセージ出力
			setErrorMessageId(e.GUIDE_MAXROWS_KEY, e.getLimit());

		} catch (DataNotFoundException e) {
			category__.warn(e.getMessage());
			throw e;
		}

		return records_;
	}


	/**
	 * 個別発行データ取得
	 *
	 * @return
	 */
	public PbsRecord[] executeKobetu(JuchuTuminiDenpyoHakoRecord rec) throws DataNotFoundException {

		PbsRecord[] returnRec = null;

		/**=================================================================
		 *  データ取得
		 *==================================================================**/
		returnRec = searchKobetu(rec);

		return returnRec;
	}


	/**
	 * 集約発行データ取得
	 *
	 * @return
	 */
	public PbsRecord[] executeSyuyaku(JuchuTuminiDenpyoHakoRecord rec, JuchuTuminiDenpyoHakoList list, JuchuTuminiDenpyoHakoItemList editItemList) throws DataNotFoundException {

		PbsRecord[] returnRec = null;

		/**=================================================================
		 *  データ取得
		 *==================================================================**/
		returnRec = searchSyuyaku(rec, list, editItemList);

		return returnRec;
	}


	/**
	 * 印刷処理
	 *
	 * @param recs
	 */
	public boolean executePrintHako(PbsRecord[] recs, String printerId) throws KitComException {
		try {
			executePrint(CSV_NO_LS_SYUKA_TUMINI_DENPYO_HAKO, recs, printerId);
		} catch (KitComException e) {
			throw e;
		}

		return true;
	}

}
