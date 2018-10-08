package kit.juchu.TuminiDenpyoHako;

import static fb.com.IKitComConst.*;
import static fb.inf.pbs.IPbsConst.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kit.pop.ComPopupUtil;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComUserSession;
import fb.inf.KitService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.KitException;
import fb.inf.exception.MaxRowsException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsSQL;
import fb.inf.pbs.PbsUtil;

/**
 * データベースへ問い合わせを実行するビジネスロジッククラスです
 */
public class JuchuTuminiDenpyoHako_SearchService extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = -3509098864859073098L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JuchuTuminiDenpyoHako_SearchService.class.getName();
	private static Category category__ = Category.getInstance(className__);

	// ============================================
	// 必要な変数はここに作成する。
	// ============================================
	private PbsDatabase db_ = null;

	/**
	 * コンストラクタ.
	 *
	 * @param cus getComUserSession()を渡すこと。
	 * @param db_ 呼び出すときにはgetDatabase()を渡すこと。
	 * @param isTran isTransaction()を渡すこと。
	 * @param ae getActionErrors()を渡すこと。
	 */
	public JuchuTuminiDenpyoHako_SearchService(ComUserSession cus, PbsDatabase db_, boolean isTran,
			ActionErrors ae) {

		super(cus, db_, isTran, ae);
		_reset();
		this.db_ = db_;
	}

	/**
	 * プロパティをリセットします.
	 */
	protected void _reset() {
		db_ = null;
	}


	/**
	 * 積荷伝票発行
	 * 　受注データ／HD部　検索SQL生成と実行
	 *
	 * @param searchForm 検索フォーム
	 * @return 検索結果
	 * @throws DataNotFoundException
	 */
	public PbsRecord[] execute(JuchuTuminiDenpyoHako_SearchForm searchForm)
			throws DataNotFoundException {

		category__.info("積荷伝票発行　受注データ／HD部　検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("積荷伝票発行　受注データ／HD部 >> 検索処理 >> SQL文生成");
		String searchSql = getSqlSearch(searchForm, bindList);

		category__.info("積荷伝票発行　受注データ／HD部 >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("積荷伝票発行　受注データ／HD部 >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

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
		category__.info("積荷伝票発行　受注データ／HD部　検索処理 終了");

		return records_;
	}


	/**
	 * 積荷伝票発行
	 * 　受注データ／HD部　検索SQL
	 *
	 * @param searchForm
	 * @param bindList
	 * @return sSql
	 */
	private String getSqlSearch(JuchuTuminiDenpyoHako_SearchForm searchForm, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		sSql.append("SELECT \n");
		PbsSQL.setCommonColumns(sSql, "JCUHD"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
		sSql.append("  JCUHD.RIYOU_KBN AS RIYOU_KBN \n"); // 利用区分
		sSql.append(" ,JCUHD.KAISYA_CD AS KAISYA_CD \n"); // 会社CD
		sSql.append(" ,JCUHD.JYUCYU_NO AS JYUCYU_NO \n"); // 受注NO
		sSql.append(" ,JCUHD.UNSOTEN_CD AS UNSOTEN_CD \n"); // 運送店CD
		sSql.append(" ,UNS.UNSOTEN_NM AS UNSOTEN_NM \n"); // 運送店名
		sSql.append(" ,JCUHD.SYUKA_DT AS SYUKA_DT \n"); // 出荷日
		sSql.append(" ,JCUHD.ATUKAI_KBN AS SYUKA_TAIO_KBN \n"); // 出荷対応区分（扱い区分）
		sSql.append(" ,JCUHD.SYUKA_SURYO_BOX AS SYUKA_SURYO_CASE \n"); // 出荷数量 ケース
		sSql.append(" ,JCUHD.SYUKA_SURYO_SET AS SYUKA_SURYO_BARA \n"); // 出荷数量 バラ
		sSql.append(" ,JCUHD.JYURYO_TOT AS JYURYO_TOT \n"); // 重量計(KG)
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_NO) AS TUMIDEN_NO \n"); // 積荷伝票NO
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_HAKO_DT) AS TUMIDEN_HAKO_DT \n"); // 積荷伝票発行日
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_HAKO_TM) AS TUMIDEN_HAKO_TM \n"); // 積荷伝票発行時刻
		sSql.append(" ,JCUHD.TUMIDEN_HAKO_CNT AS TUMIDEN_HAKO_CNT \n"); // 積荷伝票発行回数
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_HAKOSYA) AS TUMIDEN_HAKOSYA \n"); // 積荷伝票発行者ID
		sSql.append(" ,JCUHD.TATESN_CD AS ORS_TATESN_CD \n"); // 縦線CD
		sSql.append(" ,F_RTRIM(JCUHD.SYUHANTEN_CD) AS SYUHANTEN_CD \n"); // 酒販店（統一）CD
		sSql.append(" ,F_RTRIM(SHN.TEN_NM_YAGO) AS SYUHANTEN_NM \n"); // 酒販店名
		sSql.append(" ,ORSHD.OROSITEN_CD_LAST AS OROSITEN_CD_LAST \n"); // 最終送荷先卸CD
		sSql.append(" ,ORS.TEN_NM1_JISYA AS OROSITEN_NM_LAST \n"); // 最終送荷先卸名
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN1) AS TEKIYO_KBN1 \n"); // 摘要区分(01)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM1) AS TEKIYO_NM1 \n"); // 摘要内容(01)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN2) AS TEKIYO_KBN2 \n"); // 摘要区分(02)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM2) AS TEKIYO_NM2 \n"); // 摘要内容(02)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN3) AS TEKIYO_KBN3 \n"); // 摘要区分(03)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM3) AS TEKIYO_NM3 \n"); // 摘要内容(03)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN4) AS TEKIYO_KBN4 \n"); // 摘要区分(04)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM4) AS TEKIYO_NM4 \n"); // 摘要内容(04)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN5) AS TEKIYO_KBN5 \n"); // 摘要区分(05)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM5) AS TEKIYO_NM5 \n"); // 摘要内容(05)
		sSql.append("FROM T_JYUCYU_HD JCUHD \n");
		sSql.append("LEFT JOIN M_OROSISYOSAI_HD ORSHD ON (JCUHD.KAISYA_CD = ORSHD.KAISYA_CD AND JCUHD.TATESN_CD = ORSHD.TATESN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORS ON (ORSHD.KAISYA_CD = ORS.KAISYA_CD AND ORSHD.OROSITEN_CD_LAST = ORS.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_UNSOTEN UNS ON (JCUHD.KAISYA_CD = UNS.KAISYA_CD AND JCUHD.UNSOTEN_CD = UNS.UNSOTEN_CD) \n");
		sSql.append("LEFT JOIN M_SYUHANTEN SHN ON (JCUHD.SYUHANTEN_CD = SHN.SYUHANTEN_CD) \n");
		// 会社CD
		sSql.append("WHERE \n");
		sSql.append("    JCUHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// データ種別CD ＝ 通常伝票 または 通常(単価違)伝票
		sSql.append("AND JCUHD.SYUBETU_CD IN (?, ?) \n");
		bindList.add(JDATAKIND_KB_TUJO);
		bindList.add(JDATAKIND_KB_TANKA_TIGAI);

		// 出荷日
		if (!PbsUtil.isEmpty(searchForm.getSyukaDt())) {
			sSql.append("AND JCUHD.SYUKA_DT = ? \n");
			bindList.add(searchForm.getSyukaDt());
		}

		// 運送店CD（前方一致）
		if (!PbsUtil.isEmpty(searchForm.getUnsotenCd())) {
			sSql.append("AND JCUHD.UNSOTEN_CD LIKE ? \n");
			bindList.add(ComPopupUtil.getwildCardRight(searchForm.getUnsotenCd()));
		}

		// ソート
		sSql.append("ORDER BY JCUHD.UNSOTEN_CD,JCUHD.JYUCYU_NO \n"); // 運送店CD,受注NO

		return sSql.toString();
	}


	/**
	 * 積荷伝票発行
	 * 　受注データ／DT部リストを取得する
	 *
	 * @param jyucyuNo 受注NO
	 * @return 検索結果
	 */
	public JuchuTuminiDenpyoHakoDtList getListJuchuTuminiDenpyoHakoDt(String jyucyuNo) {
		category__.info("積荷伝票発行　受注データ／DT部　リスト検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("積荷伝票発行　受注データ／DT部　リスト >> 検索処理 >> SQL文生成");
		String searchSql = getSqlSearchDt(jyucyuNo, bindList);

		category__.info("積荷伝票発行　受注データ／DT部　リスト >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("積荷伝票発行　受注データ／DT部　リスト >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("積荷伝票発行　受注データ／DT部　リスト検索処理 終了");

		// 検索結果を変換
		JuchuTuminiDenpyoHakoDtList searchedList = new JuchuTuminiDenpyoHakoDtList();
		if (records_ != null) {
			searchedList = new JuchuTuminiDenpyoHakoDtList(records_);
		}

		return searchedList;
	}


	/**
	 * 受注NOから積荷伝票発行　受注データ／DT部リストを取得するSQL
	 *
	 * @param jyucyuNo
	 * @param bindList
	 * @return sSql
	 */
	private String getSqlSearchDt(String jyucyuNo, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		sSql.append("SELECT \n");
		PbsSQL.setCommonColumns(sSql, "JCUDT"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
		sSql.append("  JCUDT.RIYOU_KBN AS RIYOU_KBN \n"); // 利用区分
		sSql.append(" ,JCUDT.KAISYA_CD AS KAISYA_CD \n"); // 会社CD
		sSql.append(" ,JCUDT.JYUCYU_NO AS JYUCYU_NO \n"); // 受注NO
		sSql.append(" ,JCUDT.INPUT_LINE_NO AS INPUT_LINE_NO \n"); // 受注行NO
		sSql.append(" ,JCUDT.TUMIDEN_LINE_NO AS TUMIDEN_LINE_NO \n"); // 積荷伝票用ラインNO
		sSql.append(" ,JCUDT.SHOHIN_CD AS SHOHIN_CD \n"); // 商品CD
		sSql.append(" ,SYO.SHOHIN_NM_TUMIDEN AS SHOHIN_NM \n"); // 商品名_積荷伝票用
		sSql.append(" ,SYO.YOUKI_KIGO_NM2 AS YOUKI_KIGO_NM \n"); // 積伝用 ｾｯﾄ記号/容量名(2)
		sSql.append(" ,JCUDT.SYUKA_SU_CASE AS SYUKA_SU_CASE \n"); // 出荷数量_箱数
		sSql.append(" ,JCUDT.SYUKA_SU_BARA AS SYUKA_SU_BARA \n"); // 出荷数量_セット数
		sSql.append(" ,JCUDT.SYUKA_ALL_WEIGTH AS SYUKA_ALL_WEIGTH \n"); // 出荷重量（KG)
		sSql.append(" ,JCUDT.CASE_IRISU AS CASE_IRISU \n"); // ケース入数
		sSql.append(" ,JCUDT.SYUKA_ALL_BARA AS SYUKA_ALL_BARA \n"); // 出荷数量_換算総セット数

		sSql.append("FROM T_JYUCYU_DT JCUDT \n");
		sSql.append("LEFT JOIN M_SYOHIN SYO ON (JCUDT.KAISYA_CD = SYO.KAISYA_CD AND JCUDT.KTKSY_CD = SYO.KTKSY_CD AND JCUDT.SHOHIN_CD = SYO.SHOHIN_CD) \n");

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append("    JCUDT.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 受注NO
		sSql.append("AND JCUDT.JYUCYU_NO = ? \n");
		bindList.add(jyucyuNo);

		// ソート
		sSql.append("ORDER BY JCUDT.INPUT_LINE_NO \n"); // 受注行NO

		return sSql.toString();
	}


	/**
	 * 集約発行対象の受注データ／HD部を運送店CDをキーに再検索するSQL生成と実行
	 *
	 * @param syuyakuList
	 * @param inStr
	 * @param pRec
	 * @return 検索結果
	 */
	public PbsRecord[] getSyukeiHdByUnsotenCd(JuchuTuminiDenpyoHakoList syuyakuList, String inStr, JuchuTuminiDenpyoHakoRecord pRec)
			throws DataNotFoundException, SQLException, KitException {

		category__.info("集約済受注データ／HD部　運送店CDをキーに再検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("集約済受注データ／HD部　運送店CDをキーに再検索処理 >> SQL文生成");
		String searchSql = getSqlSyukeiHdByUnsotenCd(syuyakuList, inStr, bindList, pRec);

		category__.info("集約済受注データ／HD部　運送店CDをキーに再検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("集約済受注データ／HD部　運送店CDをキーに再検索処理 >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("集約済受注データ／HD部　運送店CDをキーに再検索処理　終了");

		// 検索結果を変換
		JuchuTuminiDenpyoHakoList searchedList = new JuchuTuminiDenpyoHakoList();
		if (records_ != null) {
			searchedList = new JuchuTuminiDenpyoHakoList(records_);
		}

		return records_;
	}


	/**
	 * 集約発行対象の受注データ／HD部を運送店CDをキーに再検索するSQL
	 *
	 * @param syuyakuList
	 * @param inStr
	 * @param bindList
	 * @param pRec
	 * @return sSql
	 */
	private String getSqlSyukeiHdByUnsotenCd(JuchuTuminiDenpyoHakoList syuyakuList, String inStr, List<String> bindList, JuchuTuminiDenpyoHakoRecord pRec) {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		sSql.append("SELECT \n");
		PbsSQL.setCommonColumns(sSql, "JCUHD"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
		sSql.append("  JCUHD.RIYOU_KBN AS RIYOU_KBN \n"); // 利用区分
		sSql.append(" ,JCUHD.KAISYA_CD AS KAISYA_CD \n"); // 会社CD
		sSql.append(" ,JCUHD.JYUCYU_NO AS JYUCYU_NO \n"); // 受注NO
		sSql.append(" ,JCUHD.UNSOTEN_CD AS UNSOTEN_CD \n"); // 運送店CD
		sSql.append(" ,JCUHD.SYUKA_SURYO_BOX AS SYUKA_SURYO_CASE \n"); // 出荷数量 ケース
		sSql.append(" ,JCUHD.SYUKA_SURYO_SET AS SYUKA_SURYO_BARA \n"); // 出荷数量 バラ
		sSql.append(" ,JCUHD.JYURYO_TOT AS JYURYO_TOT \n"); // 重量計(KG)
		sSql.append("FROM T_JYUCYU_HD JCUHD \n");
		sSql.append("WHERE \n");
		// 利用区分
		sSql.append("	JCUHD.RIYOU_KBN = ? \n");
		bindList.add(AVAILABLE_KB_RIYO_KA);
		// 会社コード
		sSql.append("	AND JCUHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());
		// 受注NO
		sSql.append("	AND JCUHD.JYUCYU_NO");
		sSql.append(inStr);
		for (int i = 0; i < syuyakuList.size(); i++) {
			JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord)syuyakuList.get(i);
			bindList.add(rec.getJyucyuNo());
		}
		// 運送店CD
		sSql.append("	AND JCUHD.UNSOTEN_CD = ? \n");
		bindList.add(pRec.getUnsotenCd());

		return sSql.toString();
	}


	/**
	 * 集約発行対象の受注データ／HD部から運送店CDを検索するSQL生成と実行
	 *
	 * @param syuyakuList
	 * @param inStr
	 * @return 検索結果
	 */
	public PbsRecord[] getUnsotenFromSyukeiHd(JuchuTuminiDenpyoHakoList syuyakuList, String inStr)
			throws DataNotFoundException, SQLException {

		category__.info("運送店CD取得　集約済受注データ／HD部　検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("運送店CD取得　集約済受注データ／HD部 >> 検索処理 >> SQL文生成");
		String searchSql = getSqlUnsotenCdFromReconstHd(syuyakuList, inStr, bindList);

		category__.info("運送店CD取得　集約済受注データ／HD部 >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("運送店CD取得　集約済受注データ／HD部 >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("集約済受注データ／HD部より運送店CD取得　終了");

		// 検索結果を変換
		JuchuTuminiDenpyoHakoList searchedList = new JuchuTuminiDenpyoHakoList();
		if (records_ != null) {
			searchedList = new JuchuTuminiDenpyoHakoList(records_);
		}

		return records_;
	}


	/**
	 * 集約発行対象の受注データ／HD部から運送店CDを検索するSQL
	 *
	 * @param syuyakuList
	 * @param inStr
	 * @param bindList
	 * @return sSql
	 */
	private String getSqlUnsotenCdFromReconstHd(JuchuTuminiDenpyoHakoList syuyakuList, String inStr, List<String> bindList) {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		sSql.append("SELECT DISTINCT \n");
		sSql.append("  JCUHD.UNSOTEN_CD AS UNSOTEN_CD \n"); // 運送店CD
		sSql.append(" ,JCUHD.SYUKA_DT AS SYUKA_DT \n"); // 出荷日
		sSql.append("FROM T_JYUCYU_HD JCUHD \n");
		sSql.append("WHERE \n");
		// 利用区分
		sSql.append("	JCUHD.RIYOU_KBN = ? \n");
		bindList.add(AVAILABLE_KB_RIYO_KA);
		// 会社コード
		sSql.append("	AND JCUHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());
		// 受注NO
		sSql.append("	AND JCUHD.JYUCYU_NO");
		sSql.append(inStr);
		for (int i = 0; i < syuyakuList.size(); i++) {
			JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord)syuyakuList.get(i);
			bindList.add(rec.getJyucyuNo());
		}
		return sSql.toString();
	}



	/**
	 * 積荷伝票（個別）発行処理
	 * 　積荷伝票データ／HD部　生成処理
	 * 　受注データ／HD部　検索SQL生成と実行
	 *
	 * @param jyucyuNo
	 * @return 検索結果
	 */
	public PbsRecord[] tuminiKobetuHd(JuchuTuminiDenpyoHakoRecord rec)
			throws DataNotFoundException, SQLException {

		category__.info("積荷伝票（個別）発行処理　積荷伝票データ／HD部生成　受注データ／HD部検索処理　開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("積荷伝票（個別）発行処理　積荷伝票データ／HD部生成　受注データ／HD部検索処理 >> SQL文生成");
		String searchSql = getSqlSelectHd(rec, bindList);

		category__.info("積荷伝票（個別）発行処理　積荷伝票データ／HD部生成　受注データ／HD部検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("積荷伝票（個別）発行処理　積荷伝票データ／HD部生成　受注データ／HD部検索処理 >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("積荷伝票（個別）発行処理　積荷伝票データ／HD部生成　終了");

		// 検索結果を変換
		JuchuTuminiDenpyoHakoHdList searchedList = new JuchuTuminiDenpyoHakoHdList();
		if (records_ != null) {
			searchedList = new JuchuTuminiDenpyoHakoHdList(records_);
		}

		return records_;
	}

	/**
	 * 積荷伝票（個別）発行処理
	 * 　積荷伝票データ／HD部　生成処理
	 * 　選択した受注NOでもって受注データ／HD部を検索するSQL
	 *
	 * @param rec
	 * @param bindList
	 * @return sSql
	 */
	private String getSqlSelectHd(JuchuTuminiDenpyoHakoRecord rec, List<String> bindList) {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		sSql.append("SELECT		\n");
		sSql.append("	 JCUHD.SYUKA_SURYO_BOX AS SOU_SURYO_CASE \n"); // 出荷数量計_箱数
		sSql.append("	,JCUHD.SYUKA_SURYO_SET AS SOU_SURYO_BARA \n"); // 出荷数量計_ｾｯﾄ数
		sSql.append("	,JCUHD.JYURYO_TOT AS SOU_JYURYO \n"); // 重量計(KG)

		sSql.append("FROM T_JYUCYU_HD JCUHD \n");

		sSql.append("WHERE \n");
		// 利用区分
		sSql.append("	JCUHD.RIYOU_KBN = ? \n");
		bindList.add(AVAILABLE_KB_RIYO_KA);
		// 会社コード
		sSql.append("	AND JCUHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());
		// 受注NO
		sSql.append("	AND JCUHD.JYUCYU_NO = ? \n");
		bindList.add(rec.getJyucyuNo());

		return sSql.toString();
	}


	/**
	 * 積荷伝票（個別）発行処理
	 * 　伝票表示用アイテムリスト生成処理
	 * 　受注データ／DT部　検索SQL生成と実行
	 *
	 * @param rec
	 * @return 検索結果
	 */
	public PbsRecord[] tuminiKobetuDtItem(JuchuTuminiDenpyoHakoRecord rec)
			throws DataNotFoundException, SQLException {

		category__.info("積荷伝票（個別）発行処理　伝票表示用アイテムリスト生成　受注データ／DT部　検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("積荷伝票（個別）発行処理　伝票表示用アイテムリスト生成　受注データ／DT部 >> 検索処理 >> SQL文生成");
		String searchSql = getSqlSelectDtItem(rec, bindList);

		category__.info("積荷伝票（個別）発行処理　伝票表示用アイテムリスト生成　受注データ／DT部 >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("積荷伝票（個別）発行処理　伝票表示用アイテムリスト生成　受注データ／DT部 >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("積荷伝票（個別）発行処理　伝票表示用アイテムリスト生成　終了");

		// 検索結果を変換
		JuchuTuminiDenpyoHakoItemList searchedList = new JuchuTuminiDenpyoHakoItemList();
		if (records_ != null) {
			searchedList = new JuchuTuminiDenpyoHakoItemList(records_);
		}

		return records_;
	}


	/**
	 * 積荷伝票（個別）発行処理
	 * 　伝票表示用アイテムリスト生成
	 * 　受注NOから受注データ／DT部リストを検索し集計するSQL
	 *
	 * @param rec
	 * @param bindList
	 * @return sSql
	 */
	private String getSqlSelectDtItem(JuchuTuminiDenpyoHakoRecord rec, List<String> bindList) {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		sSql.append("SELECT		\n");
		sSql.append("	 JCUDT.RIYOU_KBN AS RIYOU_KBN \n"); // 利用区分
		sSql.append("	,JCUDT.KAISYA_CD AS KAISYA_CD \n"); // 会社CD
		sSql.append("	,JCUDT.TUMIDEN_LINE_NO AS TUMIDEN_LINE_NO \n"); // 積荷伝票用ラインNO
		sSql.append("	,JCUDT.SHOHIN_CD AS SHOHIN_CD \n"); // 商品CD
		sSql.append("	,SYO.SHOHIN_NM_TUMIDEN AS SHOHIN_NM_TUMIDEN \n"); // 商品名_積荷伝票用
		sSql.append("	,SYO.YOUKI_KIGO_NM2 AS YOUKI_KIGO_NM2 \n"); // 積伝用 ｾｯﾄ記号/容量名(2)
		sSql.append("	,JCUDT.SYUKA_SU_CASE AS SYUKA_SU_CASE \n"); // 出荷数量_箱数
		sSql.append("	,JCUDT.SYUKA_SU_BARA AS SYUKA_SU_BARA \n"); // 出荷数量_セット数
		sSql.append("	,JCUDT.SYUKA_ALL_WEIGTH AS SYUKA_ALL_WEIGTH \n"); // 出荷重量（KG)

		sSql.append("FROM T_JYUCYU_DT JCUDT \n");
		sSql.append("LEFT JOIN M_SYOHIN SYO ON (JCUDT.KAISYA_CD = SYO.KAISYA_CD AND JCUDT.KTKSY_CD = SYO.KTKSY_CD AND JCUDT.SHOHIN_CD = SYO.SHOHIN_CD) \n");

		sSql.append("WHERE \n");
		// 利用区分
		sSql.append("	JCUDT.RIYOU_KBN = ? \n");
		bindList.add(AVAILABLE_KB_RIYO_KA);
		// 会社コード
		sSql.append("	AND JCUDT.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());
		// 受注NO
		sSql.append("	AND JCUDT.JYUCYU_NO = ? \n");
		bindList.add(rec.getJyucyuNo());
		// ソート
		sSql.append("ORDER BY JCUDT.SHOHIN_CD \n");

		return sSql.toString();
	}


	/**
	 * 積荷伝票（個別）発行処理
	 * 　積荷伝票データ／DT部　生成処理
	 * 　受注データ／HD部　検索SQL生成と実行
	 *
	 * @param rec
	 * @return 検索結果
	 */
	public PbsRecord[] tuminiKobetuDt(JuchuTuminiDenpyoHakoRecord rec)
			throws DataNotFoundException, SQLException {

		category__.info("積荷伝票（個別）発行処理　積荷伝票データ／DT部生成　受注データ／HD部検索処理　開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("積荷伝票（個別）発行処理　積荷伝票データ／DT部生成　受注データ／HD部検索処理 >> SQL文生成");
		String searchSql = getSqlSelectDt(rec, bindList);

		category__.info("積荷伝票（個別）発行処理　積荷伝票データ／DT部生成　受注データ／HD部検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("積荷伝票（個別）発行処理　積荷伝票データ／DT部生成　受注データ／HD部検索処理 >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("積荷伝票（個別）発行処理　積荷伝票データ／DT部生成　終了");

		// 検索結果を変換
		JuchuTuminiDenpyoHakoDtList searchedList = new JuchuTuminiDenpyoHakoDtList();
		if (records_ != null) {
			searchedList = new JuchuTuminiDenpyoHakoDtList(records_);
		}

		return records_;
	}


	/**
	 * 積荷伝票（個別）発行処理
	 * 　積荷伝票データ／DT部　生成処理
	 * 　受注NOから受注データ／HD部を検索するSQL
	 *
	 * @param rec
	 * @param bindList
	 * @return sSql
	 */
	private String getSqlSelectDt(JuchuTuminiDenpyoHakoRecord rec, List<String> bindList) {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		sSql.append("SELECT \n");
		sSql.append("	 JCUHD.RIYOU_KBN AS RIYOU_KBN \n"); // 利用区分
		sSql.append("	,JCUHD.KAISYA_CD AS KAISYA_CD \n"); // 会社CD
		sSql.append("	,JCUHD.JYUCYU_NO AS JYUCYU_NO \n"); // 受注NO
		sSql.append("	,JCUHD.SYUKA_SURYO_BOX AS SYUKA_SURYO_CASE \n"); // 出荷数量 ケース
		sSql.append("	,JCUHD.SYUKA_SURYO_SET AS SYUKA_SURYO_BARA \n"); // 出荷数量 バラ
		sSql.append("	,JCUHD.JYURYO_TOT AS JYURYO_TOT \n"); // 重量計(KG)

		sSql.append("FROM T_JYUCYU_HD JCUHD \n");

		sSql.append("WHERE \n");
		// 利用区分
		sSql.append("	JCUHD.RIYOU_KBN = ? \n");
		bindList.add(AVAILABLE_KB_RIYO_KA);
		// 会社コード
		sSql.append("	AND JCUHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());
		// 受注NO
		sSql.append("	AND JCUHD.JYUCYU_NO = ? \n");
		bindList.add(rec.getJyucyuNo());
		// ソート
		sSql.append("ORDER BY JCUHD.JYUCYU_NO \n");

		return sSql.toString();
	}



	/**
	 * 積荷伝票（集約）発行処理
	 * 　積荷伝票データ／HD部　生成処理
	 * 　受注データ／HD部　検索SQL生成と実行
	 *
	 * @param syuyakuListByUnsotenCd
	 * @param inStr
	 * @return 検索結果
	 */
	public PbsRecord[] tuminiSyukeiHd(JuchuTuminiDenpyoHakoList syuyakuListByUnsotenCd, String inStrU)
			throws DataNotFoundException, SQLException {

		category__.info("積荷伝票（集約）発行処理　積荷伝票データ／HD部生成　受注データ／HD部検索処理　開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("積荷伝票（集約）発行処理　積荷伝票データ／HD部生成　受注データ／HD部検索処理 >> SQL文生成");
		String searchSql = getSqlReconstHd(syuyakuListByUnsotenCd, inStrU, bindList);

		category__.info("積荷伝票（集約）発行処理　積荷伝票データ／HD部生成　受注データ／HD部検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("積荷伝票（集約）発行処理　積荷伝票データ／HD部生成　受注データ／HD部検索処理 >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("積荷伝票（集約）発行処理　積荷伝票データ／HD部生成　終了");

		// 検索結果を変換
		JuchuTuminiDenpyoHakoHdList searchedList = new JuchuTuminiDenpyoHakoHdList();
		if (records_ != null) {
			searchedList = new JuchuTuminiDenpyoHakoHdList(records_);
		}

		return records_;
	}

	/**
	 * 積荷伝票（集約）発行処理
	 * 　積荷伝票データ／HD部　生成処理
	 * 　選択した受注NOでもって受注データ／HD部を集計するSQL
	 *
	 * @param syuyakuListByUnsotenCd
	 * @param inStr
	 * @param bindList
	 * @return sSql
	 */
	private String getSqlReconstHd(JuchuTuminiDenpyoHakoList syuyakuListByUnsotenCd, String inStrU, List<String> bindList) {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		sSql.append("SELECT DISTINCT \n");
		sSql.append("	 SUM(CASE WHEN JCUHD.SYUKA_SURYO_BOX IS NULL THEN 0 ELSE JCUHD.SYUKA_SURYO_BOX END) AS SOU_SURYO_CASE \n"); // 出荷数量計_箱数
		sSql.append("	,SUM(CASE WHEN JCUHD.SYUKA_SURYO_SET IS NULL THEN 0 ELSE JCUHD.SYUKA_SURYO_SET END) AS SOU_SURYO_BARA \n"); // 出荷数量計_ｾｯﾄ数
		sSql.append("	,SUM(CASE WHEN JCUHD.JYURYO_TOT IS NULL THEN 0 ELSE JCUHD.JYURYO_TOT END) AS SOU_JYURYO \n"); // 重量計(KG)

		sSql.append("FROM T_JYUCYU_HD JCUHD \n");

		sSql.append("WHERE \n");
		// 利用区分
		sSql.append("	JCUHD.RIYOU_KBN = ? \n");
		bindList.add(AVAILABLE_KB_RIYO_KA);
		// 会社コード
		sSql.append("	AND JCUHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 受注NO
		sSql.append("	AND JCUHD.JYUCYU_NO");
		sSql.append(inStrU);
		for (int i = 0; i < syuyakuListByUnsotenCd.size(); i++) {
			JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord)syuyakuListByUnsotenCd.get(i);
			bindList.add(rec.getJyucyuNo());
		}

		return sSql.toString();
	}

	/**
	 * 積荷伝票（集約）発行処理
	 * 　伝票表示用アイテムリスト生成処理
	 * 　受注データ／DT部　検索SQL生成と実行
	 *
	 * @param syuyakuListByUnsotenCd
	 * @param inStr
	 * @return 検索結果
	 */
	public PbsRecord[] tuminiSyukeiDtItem(JuchuTuminiDenpyoHakoList syuyakuListByUnsotenCd, String inStr)
			throws DataNotFoundException, SQLException {

		category__.info("積荷伝票（集約）発行処理　伝票表示用アイテムリスト生成　受注データ／DT部　検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("積荷伝票（集約）発行処理　伝票表示用アイテムリスト生成　受注データ／DT部 >> 検索処理 >> SQL文生成");
		String searchSql = getSqlReconstDtItem(syuyakuListByUnsotenCd, inStr, bindList);

		category__.info("積荷伝票（集約）発行処理　伝票表示用アイテムリスト生成　受注データ／DT部 >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("積荷伝票（集約）発行処理　伝票表示用アイテムリスト生成　受注データ／DT部 >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("積荷伝票（集約）発行処理　伝票表示用アイテムリスト生成　終了");

		// 検索結果を変換
		JuchuTuminiDenpyoHakoDtList searchedList = new JuchuTuminiDenpyoHakoDtList();
		if (records_ != null) {
			searchedList = new JuchuTuminiDenpyoHakoDtList(records_);
		}

		return records_;
	}


	/**
	 * 積荷伝票（集約）発行処理
	 * 　伝票表示用アイテムリスト生成
	 * 　受注NOから受注データ／DT部リストを検索し集計するSQL
	 *
	 * @param syuyakuListByUnsotenCd
	 * @param inStr
	 * @param bindList
	 * @return sSql
	 */
	private String getSqlReconstDtItem(JuchuTuminiDenpyoHakoList syuyakuListByUnsotenCd, String inStr, List<String> bindList) {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		sSql.append("SELECT DISTINCT \n");
		sSql.append("	 JCUDT.RIYOU_KBN AS RIYOU_KBN \n"); // 利用区分
		sSql.append("	,JCUDT.KAISYA_CD AS KAISYA_CD \n"); // 会社CD
		sSql.append("	,FIRST_VALUE(F_RTRIM(JCUDT.TUMIDEN_LINE_NO)) OVER(PARTITION BY JCUDT.SHOHIN_CD ORDER BY JCUDT.JYUCYU_NO DESC) AS TUMIDEN_LINE_NO \n"); // 積荷伝票用ラインNO
		sSql.append("	,JCUDT.SHOHIN_CD AS SHOHIN_CD \n"); // 商品CD
		sSql.append("	,FIRST_VALUE(F_RTRIM(SYO.SHOHIN_NM_TUMIDEN)) OVER(PARTITION BY JCUDT.SHOHIN_CD ORDER BY JCUDT.JYUCYU_NO DESC) AS SHOHIN_NM_TUMIDEN \n"); // 商品名_積荷伝票用
		sSql.append("	,FIRST_VALUE(F_RTRIM(SYO.YOUKI_KIGO_NM2)) OVER(PARTITION BY JCUDT.SHOHIN_CD ORDER BY JCUDT.JYUCYU_NO DESC) AS YOUKI_KIGO_NM2 \n"); // 積伝用 ｾｯﾄ記号/容量名(2)
		sSql.append("	,SUM(CASE WHEN JCUDT.SYUKA_SU_CASE IS NULL THEN 0 ELSE JCUDT.SYUKA_SU_CASE END) OVER(PARTITION BY JCUDT.SHOHIN_CD) AS SYUKA_SU_CASE \n"); // 出荷数量_箱数
		sSql.append("	,SUM(CASE WHEN JCUDT.SYUKA_SU_BARA IS NULL THEN 0 ELSE JCUDT.SYUKA_SU_BARA END) OVER(PARTITION BY JCUDT.SHOHIN_CD) AS SYUKA_SU_BARA \n"); // 出荷数量_セット数
		sSql.append("	,SUM(CASE WHEN JCUDT.SYUKA_ALL_WEIGTH IS NULL THEN 0 ELSE JCUDT.SYUKA_ALL_WEIGTH END) OVER(PARTITION BY JCUDT.SHOHIN_CD) AS SYUKA_ALL_WEIGTH \n"); // 出荷重量（KG)

		sSql.append("FROM T_JYUCYU_DT JCUDT \n");
		sSql.append("LEFT JOIN M_SYOHIN SYO ON (JCUDT.KAISYA_CD = SYO.KAISYA_CD AND JCUDT.KTKSY_CD = SYO.KTKSY_CD AND JCUDT.SHOHIN_CD = SYO.SHOHIN_CD) \n");

		sSql.append("WHERE \n");
		// 利用区分
		sSql.append("	JCUDT.RIYOU_KBN = ? \n");
		bindList.add(AVAILABLE_KB_RIYO_KA);
		// 会社コード
		sSql.append("	AND JCUDT.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 受注NO
		sSql.append("	AND JCUDT.JYUCYU_NO");
		sSql.append(inStr);
		for (int i = 0; i < syuyakuListByUnsotenCd.size(); i++) {
			JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord)syuyakuListByUnsotenCd.get(i);
			bindList.add(rec.getJyucyuNo());
		}
		// ソート
		sSql.append("ORDER BY JCUDT.SHOHIN_CD \n");

		return sSql.toString();
	}


	/**
	 * 積荷伝票（集約）発行処理
	 * 　積荷伝票データ／DT部　生成処理
	 * 　受注データ／HD部　検索SQL生成と実行
	 *
	 * @param syuyakuListByUnsotenCd
	 * @param inStr
	 * @return 検索結果
	 */
	public PbsRecord[] tuminiSyukeiDt(JuchuTuminiDenpyoHakoList syuyakuListByUnsotenCd, String inStr)
			throws DataNotFoundException, SQLException {

		category__.info("積荷伝票（集約）発行処理　積荷伝票データ／DT部生成　受注データ／HD部検索処理　開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("積荷伝票（集約）発行処理　積荷伝票データ／DT部生成　受注データ／HD部検索処理 >> SQL文生成");
		String searchSql = getSqlReconstDt(syuyakuListByUnsotenCd, inStr, bindList);

		category__.info("積荷伝票（集約）発行処理　積荷伝票データ／DT部生成　受注データ／HD部検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("積荷伝票（集約）発行処理　積荷伝票データ／DT部生成　受注データ／HD部検索処理 >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("積荷伝票（集約）発行処理　積荷伝票データ／DT部生成　終了");

		// 検索結果を変換
		JuchuTuminiDenpyoHakoDtList searchedList = new JuchuTuminiDenpyoHakoDtList();
		if (records_ != null) {
			searchedList = new JuchuTuminiDenpyoHakoDtList(records_);
		}

		return records_;
	}


	/**
	 * 積荷伝票（集約）発行処理
	 * 　積荷伝票データ／DT部　生成処理
	 * 　受注NOから受注データ／HD部を検索するSQL
	 *
	 * @param syuyakuListByUnsotenCd
	 * @param inStr
	 * @param bindList
	 * @return sSql
	 */
	private String getSqlReconstDt(JuchuTuminiDenpyoHakoList syuyakuListByUnsotenCd, String inStr, List<String> bindList) {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		sSql.append("SELECT \n");
		sSql.append("	 JCUHD.RIYOU_KBN AS RIYOU_KBN \n"); // 利用区分
		sSql.append("	,JCUHD.KAISYA_CD AS KAISYA_CD \n"); // 会社CD
		sSql.append("	,JCUHD.JYUCYU_NO AS JYUCYU_NO \n"); // 受注NO
		sSql.append("	,JCUHD.SYUKA_SURYO_BOX AS SYUKA_SURYO_CASE \n"); // 出荷数量 ケース
		sSql.append("	,JCUHD.SYUKA_SURYO_SET AS SYUKA_SURYO_BARA \n"); // 出荷数量 バラ
		sSql.append("	,JCUHD.JYURYO_TOT AS JYURYO_TOT \n"); // 重量計(KG)

		sSql.append("FROM T_JYUCYU_HD JCUHD \n");

		sSql.append("WHERE \n");
		// 利用区分
		sSql.append("	JCUHD.RIYOU_KBN = ? \n");
		bindList.add(AVAILABLE_KB_RIYO_KA);
		// 会社コード
		sSql.append("	AND JCUHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 受注NO
		sSql.append("	AND JCUHD.JYUCYU_NO");
		sSql.append(inStr);
		for (int i = 0; i < syuyakuListByUnsotenCd.size(); i++) {
			JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord)syuyakuListByUnsotenCd.get(i);
			bindList.add(rec.getJyucyuNo());
		}
		// ソート
		sSql.append("ORDER BY JCUHD.JYUCYU_NO \n");

		return sSql.toString();
	}




}


