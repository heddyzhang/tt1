package kit.edi.EdiJuchuMainte;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.com.IKitComCsvConst.*;
import static fb.inf.pbs.IPbsConst.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComRecordUtil;
import fb.com.ComUserSession;
import fb.com.ComUtil;
import fb.com.Records.FbMastrSyohinRecord;
import fb.com.exception.KitComException;
import fb.inf.KitService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.MaxRowsException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsSQL;
import fb.inf.pbs.PbsUtil;

/**
 * データベースへ問い合わせを実行するビジネスロジッククラスです
 */
public class EdiJuchuMainte_SearchService extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = -5403566015985102682L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = EdiJuchuMainte_SearchService.class.getName();
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
	public EdiJuchuMainte_SearchService(ComUserSession cus, PbsDatabase db_, boolean isTran,
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
	 * EDI受発注受信データ処理（EDI受発注データ／ﾍｯﾀﾞｰ部）検索のSQL生成と実行.
	 *
	 * @param searchForm 検索フォーム
	 * @return 検索結果
	 * @throws DataNotFoundException
	 */
	public PbsRecord[] execute(EdiJuchuMainte_SearchForm searchForm)
			throws DataNotFoundException {

		category__.info("EDI受発注受信データ処理（EDI受発注データ／ﾍｯﾀﾞｰ部）検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("EDI受発注受信データ処理（EDI受発注データ／ﾍｯﾀﾞｰ部）>> 検索処理 >> SQL文生成");
		String searchSql = getEdiJyuhacyuHdListSearchSql(searchForm, bindList);

		category__.info("EDI受発注受信データ処理（EDI受発注データ／ﾍｯﾀﾞｰ部）>> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("EDI受発注受信データ処理（EDI受発注データ／ﾍｯﾀﾞｰ部）>> execute");
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
		category__.info("EDI受発注受信データ処理（移動データ／ヘッダー部）検索処理 終了");

		return records_;
	}


	/**
	 * EDI受発注受信データ処理（EDI受発注データ／ﾃﾞｨﾃｰﾙ部）リストを取得する
	 * @param editForm
	 * @return ZaikoSeihinMoveDataInDtList 検索結果
	 */
	public EdiJuchuMainteDtList getEdiJyuhacyuDtList(EdiJuchuMainte_EditForm editForm) {
		category__.info("EDI受発注受信データ処理（EDI受発注データ／ﾃﾞｨﾃｰﾙ部）リスト検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("EDI受発注受信データ処理（EDI受発注データ／ﾃﾞｨﾃｰﾙ部）リスト >> 検索処理 >> SQL文生成");
		String searchSql = getEdiJyuhacyuDtListSearchSql(editForm, bindList);

		category__.info("EDI受発注受信データ処理（EDI受発注データ／ﾃﾞｨﾃｰﾙ部）リスト >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("EDI受発注受信データ処理（EDI受発注データ／ﾃﾞｨﾃｰﾙ部）リスト >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("EDI受発注受信データ処理（EDI受発注データ／ﾃﾞｨﾃｰﾙ部）リスト検索処理 終了");

		// 検索結果を変換
		EdiJuchuMainteDtList searchedList = new EdiJuchuMainteDtList();
		if (records_ != null) {
			searchedList = new EdiJuchuMainteDtList(records_);
		}

		return searchedList;
	}

	/**
	 * 商品CDから商品情報を取得する。
	 *
	 * @param shohinCd 商品CD
	 * @return FbMastrSyohinRecord 商品情報
	 */
	protected FbMastrSyohinRecord getMastrShohinInfoByCd(String shohinCd) {

		String ret = "";
		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD
		String ktksyCd = cus.getKtksyCd(); // 寄託者コード

		// 商品マスターを検索
		ComRecordUtil rUtil = new ComRecordUtil(db_);
		FbMastrSyohinRecord zRec = rUtil.getRecMastrSyohin(IS_DELETED_HELD_FALSE, kaisyaCd, ktksyCd, shohinCd);

		// 利用状態を取得
		String riyouJotaiFlg = ComUtil.getRiyouJotaiFlg(cus.getGymDate(), zRec.getJyucyuKaisiDt(),
				zRec.getJyucyuSyuryoDt(), zRec.getRiyouTeisiDt());
		if (ComUtil.isMstExistence(zRec, MST_REC_NOT_EXISTENCE)) { // 存在しない
			ret = PbsUtil.getMessageResourceString("com.text.noValue"); // ＊該当なし＊
			// 商品名を設定
			zRec.setShohinNmUriden(ret);
			// 容器名
			zRec.setYoukiKigoNm1(CHAR_BLANK);
		// 利用不可
		} else if (!PbsUtil.isIncluded(riyouJotaiFlg, DATA_JYOTAI_KB_KARI_TOROKU, DATA_JYOTAI_KB_RIYO_KA)) {
			ret = PbsUtil.getMessageResourceString("com.text.dontUse"); // ＊利用不可＊
			// 商品名を設定
			zRec.setShohinNmUriden(ret);
			// 容器名
			zRec.setYoukiKigoNm1(CHAR_BLANK);
		}

		return zRec;
	}


	/**
	 * [縦線CD]から縦線情報を取得する(AJAX用)
	 * @param tatesnCd
	 * @return
	 */
	public EdiJuchuMainteRecord getTatesenInfoByTatesnCd(String tatesnCd) {

		category__.info("縦線卸店情報検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("縦線卸店情報 >> 検索処理 >> SQL文生成");
		String searchSql = getTatesenInfoSearchSql(tatesnCd, bindList);

		category__.info("縦線卸店情報 >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("縦線卸店情報 >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("縦線卸店情報検索処理 終了");

		// 検索結果を変換
		EdiJuchuMainteList searchedList = new EdiJuchuMainteList();
		EdiJuchuMainteRecord tatesenRec = null;
		if (records_ != null) {
			searchedList = new EdiJuchuMainteList(records_);
			tatesenRec = (EdiJuchuMainteRecord) searchedList.getFirstRecord();
		} else {
			tatesenRec = new EdiJuchuMainteRecord();
			String noVal = PbsUtil.getMessageResourceString("com.text.noValue"); // ＊該当なし＊
			tatesenRec.setOrositenNm1JitenRyaku(noVal);			// 特約店名
			tatesenRec.setOrositenNmDepoRyaku(noVal);			// デポ店名
			tatesenRec.setOrositenNm2JitenRyaku(noVal);			// 二次店名
			tatesenRec.setOrositenNm3JitenRyaku(noVal);			// 三次店名
		}

		return tatesenRec;

	}

	/**
	 * [縦線CD]から卸店情報を取得する(確認画面用)
	 * @param tatesnCd
	 * @return
	 */
	public EdiJuchuMainteRecord getOrosiInfoByTatesnCd(String tatesnCd) {

		category__.info("卸店情報検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("卸店情報 >> 検索処理 >> SQL文生成");
		String searchSql = getOrosiInfoSearchSql(tatesnCd, bindList);

		category__.info("卸店情報 >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("卸店情報 >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("卸店情報検索処理 終了");

		// 検索結果を変換
		EdiJuchuMainteList searchedList = new EdiJuchuMainteList();
		EdiJuchuMainteRecord orosiRec = null;
		if (records_ != null) {
			searchedList = new EdiJuchuMainteList(records_);
			orosiRec = (EdiJuchuMainteRecord) searchedList.getFirstRecord();
		} else {
			orosiRec = new EdiJuchuMainteRecord();
		}

		return orosiRec;

	}

	/**
	  * [縦線CD]から受注No採番用管轄事業所ｷｰを取得する
	  *
	  * @param tatesnCd 縦線CD
	  * @return 受注No採番用管轄事業所ｷｰ
	 */
	 public String getJuchuMngKey(String tatesnCd, String kaisyaCd) {

		String juchuMngKey = "";

		category__.info("受注No採番用管轄事業所ｷｰ検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("受注No採番用管轄事業所ｷｰ >> 検索処理 >> SQL文生成");
		String searchSql = getJuchuMngKeySql(tatesnCd, kaisyaCd, bindList);

		category__.info("受注No採番用管轄事業所ｷｰ >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true, searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("受注No採番用管轄事業所ｷｰ検索 >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("受注No採番用管轄事業所ｷｰ検索処理 終了");

		// 検索値
		if (records_ != null) {
			juchuMngKey = records_[0].getString("JUCHU_MNG_KEY");
		}

		return juchuMngKey;
	}

	/**
	 * EDI受発注受信データ処理検索のCSV出力用SQL生成と実行.
	 * @param searchForm 検索フォーム
	 * @param kengenLevel 権限レベル
	 * @return 検索結果
	 * @throws SQLException
	 * @throws KitComException
	 */
	public void executeCsvDownload(EdiJuchuMainte_SearchForm searchForm) throws SQLException, KitComException{
		PbsDatabase db_ = getDatabase();
		List<String> bindList = new ArrayList<String>();
		db_.prepare(getCsvOutSql(searchForm, bindList));

		// バインド
		int i = 0;
		for (String bindStr : bindList) {
			db_.setString(++i, bindStr);
		}

		db_.execute();

		String csvNo =  CSV_NO_CSV_EDI_JUCHU_MAINTE;

		executeDownloadBulkData(csvNo, db_, PbsDatabase.DEFAULT_CLAIM_CHECK, searchForm.getOutputType());

	}


	/**
	 * EDI受発注受信データ処理（EDI受発注データ／ﾍｯﾀﾞｰ部）の検索SQLの作成
	 *
	 * @param searchForm
	 * @param bindList
	 * @return sSql
	 */
	private String getEdiJyuhacyuHdListSearchSql(EdiJuchuMainte_SearchForm searchForm, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT DISTINCT \n");
		PbsSQL.setCommonColumns(sSql, "JYUHACYUHD"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
		sSql.append("  CASE \n");
		sSql.append("    WHEN ORS5.SYUHANTEN_CD IS NOT NULL \n");
		sSql.append("    THEN DH_GOJITEN_CD || '：' || HO_KAISYA_NM5 \n");
		sSql.append("    WHEN ORS4.SYUHANTEN_CD IS NOT NULL \n");
		sSql.append("    THEN DH_YOJITEN_CD || '：'  || HO_KAISYA_NM4 \n");
		sSql.append("    WHEN ORS3.SYUHANTEN_CD IS NOT NULL \n");
		sSql.append("    THEN DH_SANJITEN_CD || '：' || HO_KAISYA_NM3 \n");
		sSql.append("    WHEN ORS2.SYUHANTEN_CD IS NOT NULL \n");
		sSql.append("    THEN DH_NIJITEN_CD || '：' || HO_KAISYA_NM2 \n");
		sSql.append("    WHEN ORS1.SYUHANTEN_CD IS NOT NULL \n");
		sSql.append("    THEN DH_ICHIJITEN_CD || '：' || HO_KAISYA_NM1 \n");
		sSql.append("    ELSE NULL \n");
		sSql.append("   END AS LAST_SOUNI_SAKI  \n"); // 最終送荷先
		sSql.append("  ,F_RTRIM(FH_TEIKYO_KIGYO_CD) || '：' || F_RTRIM(FH_TEIKYOU_KIGYO_NM) AS DATA_HASIN_MOTO \n"); // ﾃﾞｰﾀ発信元
		sSql.append("  ,KSG.SGYOSYA_NM AS SGYOSYA_NM  \n"); // 更新者
		sSql.append("  ,F_RTRIM(JYUHACYUHD.CYOFUKU_KBN) AS CYOFUKU_KBN \n"); // 重複区分
		//sSql.append("  ,ORSHD.SYUKA_DT_CYUI_KBN AS SYUKA_DT_CYUI_KBN  \n"); // 出荷日注意
		sSql.append(" ,F_RTRIM(ORSHD.SYUKA_DT_CYUI_KBN) || '：' || F_RTRIM(MEI.CD_NM) AS SYUKA_DT_CYUI_KBN \n"); // 出荷日注意
		sSql.append("  ,ORSHD.HACYU_TANTOSYA_NM AS HACYU_TANTOSYA_NM  \n"); //発注担当者名
		sSql.append("  ,ORSHD.HACYUMOTO_TEL AS HACYUMOTO_TEL \n"); //発注元TEL
		sSql.append("  ,F_RTRIM(ORSHD.OROSITEN_CD_1JITEN) AS OROSITEN_CD_1JITEN \n"); // 特約店CD
		sSql.append("  ,ORS1JI.TEN_NM1_JISYA     AS OROSITEN_NM_1JITEN_RYAKU \n"); // 特約店名
		sSql.append("  ,ORS1JI.ADDRESS1_URIDEN || ORS1JI.ADDRESS2_URIDEN || ORS1JI.ADDRESS3_URIDEN || ORS1JI.ADDRESS4_URIDEN  AS ADDRESS_1JITEN \n"); // 特約店住所
		sSql.append("  ,F_RTRIM(ORS1JI.TEL)               AS TEL_1JITEN \n"); // 特約店TEL
		sSql.append("  ,F_RTRIM(ORSHD.OROSITEN_CD_DEPO)   AS OROSITEN_CD_DEPO \n"); // デポ店CD
		sSql.append("  ,ORSDEP.TEN_NM1_JISYA     AS OROSITEN_NM_DEPO_RYAKU \n"); // デポ店名
		sSql.append("  ,ORSDEP.ADDRESS1_URIDEN || ORSDEP.ADDRESS2_URIDEN || ORSDEP.ADDRESS3_URIDEN || ORSDEP.ADDRESS4_URIDEN AS ADDRESS_DEPO \n"); // デポ店住所
		sSql.append("  ,F_RTRIM(ORSDEP.TEL)               AS TEL_DEPO \n"); // デポ店TEL
		sSql.append("  ,F_RTRIM(ORSHD.OROSITEN_CD_2JITEN) AS OROSITEN_CD_2JITEN \n"); // 二次店CD
		sSql.append("  ,ORS2JI.TEN_NM1_JISYA     AS OROSITEN_NM_2JITEN_RYAKU \n"); // 二次店名
		sSql.append("  ,ORS2JI.ADDRESS1_URIDEN || ORS2JI.ADDRESS2_URIDEN || ORS2JI.ADDRESS3_URIDEN || ORS2JI.ADDRESS4_URIDEN AS ADDRESS_2JITEN \n"); // 二次店住所
		sSql.append("  ,F_RTRIM(ORS2JI.TEL)               AS TEL_2JITEN \n"); // 二次店TEL
		sSql.append("  ,F_RTRIM(ORSHD.OROSITEN_CD_3JITEN) AS OROSITEN_CD_3JITEN \n"); // 三次店CD
		sSql.append("  ,ORS3JI.TEN_NM1_JISYA     AS OROSITEN_NM_3JITEN_RYAKU \n"); // 三次店名
		sSql.append("  ,ORS3JI.ADDRESS1_URIDEN || ORS3JI.ADDRESS2_URIDEN || ORS3JI.ADDRESS3_URIDEN || ORS3JI.ADDRESS4_URIDEN AS ADDRESS_3JITEN \n"); // 三次店住所
		sSql.append("  ,F_RTRIM(ORS3JI.TEL)               AS TEL_3JITEN \n"); // 三次店TEL
		sSql.append("  ,F_RTRIM(ORS.EDI_JSYOHIN_TEKIYOCHEK_KBN) AS EDI_JSYOHIN_TEKIYOCHEK_KBN  \n"); // EDI受発注商品摘要欄確認区分
		sSql.append("  ,F_RTRIM(JYUHACYUHD.SYORI_KBN)     AS SYORI_KBN \n"); // 処理区分
		sSql.append("  ,JYUHACYUHD.T_EDI_JYUHACYU_ID AS T_EDI_JYUHACYU_ID  \n"); // ID
		sSql.append("  ,F_RTRIM(JYUHACYUHD.ONLINE_JYUSIN_KAISU) AS ONLINE_JYUSIN_KAISU  \n"); // ｵﾝﾗｲﾝ受信回数
		sSql.append("  ,F_RTRIM(JYUHACYUHD.JYUSIN_DT) AS JYUSIN_DT  \n"); // 受信日
		sSql.append("  ,F_RTRIM(JYUHACYUHD.JYUSIN_TM) AS JYUSIN_TM  \n"); // 受信時間
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DENPYO_GROUP_NO) AS DENPYO_GROUP_NO  \n"); // 入力伝票グループNO
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_RECORD_KBN) AS FH_RECORD_KBN  \n"); // ファイルヘッダーレコード：レコード区分
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DATA_SERIAL_NO) AS FH_DATA_SERIAL_NO  \n"); // ファイルヘッダーレコード：データシリアルNo
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DATE_SYUBETU) AS FH_DATE_SYUBETU  \n"); // ファイルヘッダーレコード：データ種別
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DATA_SAKUSEI_DT) AS FH_DATA_SAKUSEI_DT  \n"); // ファイルヘッダーレコード：データ作成日
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DATA_SAKUSEI_TM) AS FH_DATA_SAKUSEI_TM  \n"); // ファイルヘッダーレコード：データ作成時刻
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_FILE_NO) AS FH_FILE_NO  \n"); // ファイルヘッダーレコード：ファイルNO
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DATA_SYORI_DT) AS FH_DATA_SYORI_DT  \n"); // ファイルヘッダーレコード：データ処理日
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_RIYOU_KIGYO_CD) AS FH_RIYOU_KIGYO_CD  \n"); // ファイルヘッダーレコード：利用者企業コード（受け手）荷主
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_CENTER_CD) AS FH_CENTER_CD  \n"); // ファイルヘッダーレコード：データ送信元センターコード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_CENTER_CD_YOBI) AS FH_CENTER_CD_YOBI  \n"); // ファイルヘッダーレコード：データ送信元センターコード（予備）
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_LAST_SEND_CD) AS FH_LAST_SEND_CD  \n"); // ファイルヘッダーレコード：最終送信先コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_LAST_STATION_ADDR) AS FH_LAST_STATION_ADDR  \n"); // ファイルヘッダーレコード：最終ステーションアドレス
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DIRECT_SEND_CD) AS FH_DIRECT_SEND_CD  \n"); // ファイルヘッダーレコード：直接送信先コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DIRECT_STATION_ADDR) AS FH_DIRECT_STATION_ADDR  \n"); // ファイルヘッダーレコード：直接ステーションアドレス
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_TEIKYO_KIGYO_CD) AS FH_TEIKYO_KIGYO_CD  \n"); // ファイルヘッダーレコード：提供企業コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_TEIKYO_JIGYOSYO_CD) AS FH_TEIKYO_JIGYOSYO_CD  \n"); // ファイルヘッダーレコード：提供企業事業所コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_TEIKYOU_KIGYO_NM) AS FH_TEIKYOU_KIGYO_NM  \n"); // ファイルヘッダーレコード：提供企業名
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_TEIKYOU_BUSYO_NM) AS FH_TEIKYOU_BUSYO_NM  \n"); // ファイルヘッダーレコード：提供企業照会部署名
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DATA_COUNT) AS FH_DATA_COUNT  \n"); // ファイルヘッダーレコード：送信データ件数
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_RECORD_SIZE) AS FH_RECORD_SIZE  \n"); // ファイルヘッダーレコード：レコードサイズ
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DATA_UMU) AS FH_DATA_UMU  \n"); // ファイルヘッダーレコード：データ有無サイン
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_FORMAT_VERSION) AS FH_FORMAT_VERSION  \n"); // ファイルヘッダーレコード：フォーマットバージョン
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_YOHAKU) AS FH_YOHAKU  \n"); // ファイルヘッダーレコード：余白
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_RECORD_KBN) AS DH_RECORD_KBN  \n"); // 伝票ヘッダーレコード：レコード区分
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_DATA_SERIAL_NO) AS DH_DATA_SERIAL_NO  \n"); // 伝票ヘッダーレコード：データシリアルNo
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_DATA_KBN) AS DH_DATA_KBN  \n"); // 伝票ヘッダーレコード：データ区分
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_DENPYO_NO) AS DH_DENPYO_NO  \n"); // 伝票ヘッダーレコード：伝票番号
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_HACHU_DT) AS DH_HACHU_DT  \n"); // 伝票ヘッダーレコード：発注日
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_ICHIJITEN_CD) AS DH_ICHIJITEN_CD  \n"); // 伝票ヘッダーレコード：一次店コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_NIJITEN_CD) AS DH_NIJITEN_CD  \n"); // 伝票ヘッダーレコード：二次店コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_SANJITEN_CD) AS DH_SANJITEN_CD  \n"); // 伝票ヘッダーレコード：三次店コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_YOJITEN_CD) AS DH_YOJITEN_CD  \n"); // 伝票ヘッダーレコード：四次店コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_GOJITEN_CD) AS DH_GOJITEN_CD  \n"); // 伝票ヘッダーレコード：五次店コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_NONYU_DT) AS DH_NONYU_DT  \n"); // 伝票ヘッダーレコード：納入日又は取引日
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_TEGATA) AS DH_TEGATA  \n"); // 伝票ヘッダーレコード：手形情報
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_KURA_KBN) AS DH_KURA_KBN  \n"); // 伝票ヘッダーレコード：蔵直区分
		sSql.append("  ,DECODE(F_RTRIM(JYUHACYUHD.DH_TORIKESI_SIGN),'1','1','2') AS DH_TORIKESI_SIGN  \n"); // 伝票ヘッダーレコード：定期発注取消サイン
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_NONYU_TM) AS DH_NONYU_TM  \n"); // 伝票ヘッダーレコード：納入希望時間
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_BIKOU) AS DH_BIKOU  \n"); // 伝票ヘッダーレコード：備考
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_RECORD_KBN1) AS HO_RECORD_KBN1  \n"); // 伝票ヘッダーオプションレコード：レコード区分1
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_DATA_SERIAL_NO1) AS HO_DATA_SERIAL_NO1  \n"); // 伝票ヘッダーオプションレコード：データシリアルNo1
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_VIEW_NO1) AS HO_VIEW_NO1  \n"); // 伝票ヘッダーオプションレコード：伝票ヘッダー参照№1
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_NM1 AS HO_KAISYA_NM1  \n"); // 伝票ヘッダーオプションレコード：社名、店名、取引先名1
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_ADDR1 AS HO_KAISYA_ADDR1  \n"); // 伝票ヘッダーオプションレコード：住所1
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_KAISYA_TEL1) AS HO_KAISYA_TEL1 \n"); // 伝票ヘッダーオプションレコード：電話番号1
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_YOHAKU1) AS HO_YOHAKU1  \n"); // 伝票ヘッダーオプションレコード：余白1
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_TORIHIKI_CD1) AS HO_TORIHIKI_CD1  \n"); // 伝票ヘッダーオプションレコード：取引先対応コード1
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_NIHONGO_KBN1) AS HO_NIHONGO_KBN1 \n"); // 伝票ヘッダーオプションレコード：日本語区分1
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_RECORD_KBN2) AS HO_RECORD_KBN2 \n"); // 伝票ヘッダーオプションレコード：レコード区分2
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_DATA_SERIAL_NO2) AS HO_DATA_SERIAL_NO2  \n"); // 伝票ヘッダーオプションレコード：データシリアルNo2
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_VIEW_NO2) AS HO_VIEW_NO2  \n"); // 伝票ヘッダーオプションレコード：伝票ヘッダー参照№2
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_NM2 AS HO_KAISYA_NM2  \n"); // 伝票ヘッダーオプションレコード：社名、店名、取引先名2
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_ADDR2 AS HO_KAISYA_ADDR2  \n"); // 伝票ヘッダーオプションレコード：住所2
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_KAISYA_TEL2) AS HO_KAISYA_TEL2  \n"); // 伝票ヘッダーオプションレコード：電話番号2
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_YOHAKU2) AS HO_YOHAKU2  \n"); // 伝票ヘッダーオプションレコード：余白2
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_TORIHIKI_CD2) AS HO_TORIHIKI_CD2  \n"); // 伝票ヘッダーオプションレコード：取引先対応コード2
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_NIHONGO_KBN2) AS HO_NIHONGO_KBN2  \n"); // 伝票ヘッダーオプションレコード：日本語区分2
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_RECORD_KBN3) AS HO_RECORD_KBN3  \n"); // 伝票ヘッダーオプションレコード：レコード区分3
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_DATA_SERIAL_NO3) AS HO_DATA_SERIAL_NO3 \n");  // 伝票ヘッダーオプションレコード：データシリアルNo3
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_VIEW_NO3) AS HO_VIEW_NO3  \n"); // 伝票ヘッダーオプションレコード：伝票ヘッダー参照№3
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_NM3 AS HO_KAISYA_NM3  \n"); // 伝票ヘッダーオプションレコード：社名、店名、取引先名3
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_ADDR3 AS HO_KAISYA_ADDR3  \n"); // 伝票ヘッダーオプションレコード：住所3
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_KAISYA_TEL3) AS HO_KAISYA_TEL3  \n"); // 伝票ヘッダーオプションレコード：電話番号3
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_YOHAKU3) AS HO_YOHAKU3  \n"); // 伝票ヘッダーオプションレコード：余白3
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_TORIHIKI_CD3) AS HO_TORIHIKI_CD3  \n"); // 伝票ヘッダーオプションレコード：取引先対応コード3
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_NIHONGO_KBN3) AS HO_NIHONGO_KBN3  \n"); // 伝票ヘッダーオプションレコード：日本語区分3
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_RECORD_KBN4) AS HO_RECORD_KBN4  \n"); // 伝票ヘッダーオプションレコード：レコード区分4
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_DATA_SERIAL_NO4) AS HO_DATA_SERIAL_NO4  \n"); // 伝票ヘッダーオプションレコード：データシリアルNo4
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_VIEW_NO4) AS HO_VIEW_NO4 \n"); // 伝票ヘッダーオプションレコード：伝票ヘッダー参照№4
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_NM4 AS HO_KAISYA_NM4 \n");  // 伝票ヘッダーオプションレコード：社名、店名、取引先名4
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_ADDR4 AS HO_KAISYA_ADDR4  \n"); // 伝票ヘッダーオプションレコード：住所4
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_KAISYA_TEL4) AS HO_KAISYA_TEL4 \n");// 伝票ヘッダーオプションレコード：電話番号4
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_YOHAKU4) AS HO_YOHAKU4 \n"); // 伝票ヘッダーオプションレコード：余白4
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_TORIHIKI_CD4) AS HO_TORIHIKI_CD4 \n"); // 伝票ヘッダーオプションレコード：取引先対応コード4
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_NIHONGO_KBN4) AS HO_NIHONGO_KBN4  \n"); // 伝票ヘッダーオプションレコード：日本語区分4
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_RECORD_KBN5) AS HO_RECORD_KBN5  \n"); // 伝票ヘッダーオプションレコード：レコード区分5
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_DATA_SERIAL_NO5) AS HO_DATA_SERIAL_NO5  \n"); // 伝票ヘッダーオプションレコード：データシリアルNo5
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_VIEW_NO5) AS HO_VIEW_NO5  \n"); // 伝票ヘッダーオプションレコード：伝票ヘッダー参照№5
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_NM5 AS HO_KAISYA_NM5  \n"); // 伝票ヘッダーオプションレコード：社名、店名、取引先名5
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_ADDR5 AS HO_KAISYA_ADDR5 \n"); // 伝票ヘッダーオプションレコード：住所5
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_KAISYA_TEL5) AS HO_KAISYA_TEL5  \n"); // 伝票ヘッダーオプションレコード：電話番号5
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_YOHAKU5) AS HO_YOHAKU5  \n"); // 伝票ヘッダーオプションレコード：余白5
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_TORIHIKI_CD5) AS HO_TORIHIKI_CD5  \n"); // 伝票ヘッダーオプションレコード：取引先対応コード5
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_NIHONGO_KBN5) AS HO_NIHONGO_KBN5  \n"); // 伝票ヘッダーオプションレコード：日本語区分5
		sSql.append("  ,F_RTRIM(JYUHACYUHD.ED_RECORD_KBN) AS ED_RECORD_KBN  \n"); // エンドレコード：レコード区分
		sSql.append("  ,F_RTRIM(JYUHACYUHD.ED_DATA_SERIAL_NO) AS ED_DATA_SERIAL_NO  \n"); // エンドレコード：データシリアルNo
		sSql.append("  ,F_RTRIM(JYUHACYUHD.ED_LINE_COUNT) AS ED_LINE_COUNT  \n"); // エンドレコード：レコード件数
		sSql.append("  ,F_RTRIM(JYUHACYUHD.ED_YOHAKU) AS ED_YOHAKU  \n"); // エンドレコード：余白
		sSql.append("  ,F_RTRIM(JYUHACYUHD.SYUKA_YOTEI_DT) AS SYUKA_YOTEI_DT  \n"); // 【変換後】kz出荷予定日
		sSql.append("  ,F_RTRIM(JYUHACYUHD.MINASI_DT) AS MINASI_DT  \n"); // 【変換後】ﾐﾅｼ日付
		sSql.append("  ,F_RTRIM(JYUHACYUHD.TATESN_CD) AS TATESN_CD  \n"); // 【変換後】kz縦線CD
		sSql.append("  ,F_RTRIM(JYUHACYUHD.OROSITEN_CD_LAST) AS OROSITEN_CD_LAST  \n"); // 【変換後】KZ最終送荷先卸CD
		sSql.append("  ,F_RTRIM(JYUHACYUHD.SYUHANTEN_CD) AS SYUHANTEN_CD  \n"); // 【変換後】kz酒販店CD
		sSql.append("  ,F_RTRIM(JYUHACYUHD.JYUCYU_NO) AS JYUCYU_NO  \n"); // 【変換後】KZ受注NO
		sSql.append("  ,F_RTRIM(JYUHACYUHD.ERROR_KBN) AS ERROR_KBN  \n"); // エラー区分

		// 表示用
		sSql.append("  ,F_RTRIM(JYUHACYUHD.SYORI_KBN) AS SYORI_KBN_VIEW \n"); // 処理区分
		sSql.append("  ,F_RTRIM(JYUHACYUHD.TATESN_CD) AS TATESN_CD_VIEW  \n"); // 【変換後】kz縦線CD

		// ****From句****
		// EDI受発注ﾃﾞｰﾀ_ﾍｯﾀﾞﾞｰ部
		sSql.append("FROM T_EDI_JYUHACYU_HD JYUHACYUHD \n");
		// 認証マスタ
		sSql.append("LEFT JOIN M_SGYOSYA KSG ON (JYUHACYUHD.KAISYA_CD = KSG.KAISYA_CD AND JYUHACYUHD.KOUSIN_SGYOSYA_CD = KSG.SGYOSYA_CD) \n");
		// 卸店ﾏｽﾀｰ(酒販店コードと照合用)
		sSql.append("LEFT JOIN M_OROSITEN ORS5 \n");
		sSql.append("ON (JYUHACYUHD.KAISYA_CD = ORS5.KAISYA_CD AND JYUHACYUHD.DH_GOJITEN_CD = ORS5.SYUHANTEN_CD) \n");
		// 卸店ﾏｽﾀｰ(酒販店コードと照合用)
		sSql.append("LEFT JOIN M_OROSITEN ORS4 \n");
		sSql.append("ON (JYUHACYUHD.KAISYA_CD = ORS4.KAISYA_CD AND JYUHACYUHD.DH_YOJITEN_CD = ORS4.SYUHANTEN_CD) \n");
		// 卸店ﾏｽﾀｰ(酒販店コードと照合用)
		sSql.append("LEFT JOIN M_OROSITEN ORS3 \n");
		sSql.append("ON (JYUHACYUHD.KAISYA_CD = ORS3.KAISYA_CD AND JYUHACYUHD.DH_SANJITEN_CD = ORS3.SYUHANTEN_CD) \n");
		// 卸店ﾏｽﾀｰ(酒販店コードと照合用)
		sSql.append("LEFT JOIN M_OROSITEN ORS2 \n");
		sSql.append("ON (JYUHACYUHD.KAISYA_CD = ORS2.KAISYA_CD AND JYUHACYUHD.DH_NIJITEN_CD = ORS2.SYUHANTEN_CD) \n");
		// 卸店ﾏｽﾀｰ(酒販店コードと照合用)
		sSql.append("LEFT JOIN M_OROSITEN ORS1 \n");
		sSql.append("ON (JYUHACYUHD.KAISYA_CD       = ORS1.KAISYA_CD AND JYUHACYUHD.DH_ICHIJITEN_CD = ORS1.SYUHANTEN_CD) \n");
		// 卸店詳細ﾏｽﾀｰ_ﾍｯﾀﾞｰ
		sSql.append("LEFT JOIN M_OROSISYOSAI_HD ORSHD \n");
		sSql.append("ON (JYUHACYUHD.KAISYA_CD = ORSHD.KAISYA_CD AND JYUHACYUHD.TATESN_CD = ORSHD.TATESN_CD) \n");
		// 卸店ﾏｽﾀｰ(許容日用)
		sSql.append("LEFT JOIN M_OROSITEN ORS ON (ORSHD.KAISYA_CD = ORS.KAISYA_CD AND ORSHD.OROSITEN_CD_LAST = ORS.OROSITEN_CD) \n");				// 卸店マスター（最終送荷先卸店）
		// 卸店ﾏｽﾀｰ(特約店用)
		sSql.append("LEFT JOIN M_OROSITEN ORS1JI \n");
		sSql.append("ON (ORSHD.KAISYA_CD = ORS1JI.KAISYA_CD AND ORSHD.OROSITEN_CD_1JITEN = ORS1JI.OROSITEN_CD) \n");
		// 卸店ﾏｽﾀｰデポ店用)
		sSql.append("LEFT JOIN M_OROSITEN ORSDEP \n");
		sSql.append("ON (ORSHD.KAISYA_CD = ORSDEP.KAISYA_CD AND ORSHD.OROSITEN_CD_DEPO = ORSDEP.OROSITEN_CD) \n");
		// 卸店ﾏｽﾀｰ(2次店用)
		sSql.append("LEFT JOIN M_OROSITEN ORS2JI \n");
		sSql.append("ON (ORSHD.KAISYA_CD = ORS2JI.KAISYA_CD AND ORSHD.OROSITEN_CD_2JITEN = ORS2JI.OROSITEN_CD) \n");
		// 卸店ﾏｽﾀｰ(3次店用)
		sSql.append("LEFT JOIN M_OROSITEN ORS3JI \n");
		sSql.append("ON (ORSHD.KAISYA_CD = ORS3JI.KAISYA_CD AND ORSHD.OROSITEN_CD_3JITEN = ORS3JI.OROSITEN_CD) \n");
		// コード名称マスター(出荷日注意用)
		sSql.append("LEFT JOIN M_CD_MEISYOU MEI  ON (MEI.KBN_ID = 'KBN_SYUK_CAREF' AND ORSHD.SYUKA_DT_CYUI_KBN = MEI.CODE_CD) \n");

		// ****Where句****
		String sWhereSql = createCommCondSql(searchForm, bindList);
		sSql.append(sWhereSql);

		// **OrderBy ソート**
		sSql.append("ORDER BY \n");
		sSql.append("  JYUHACYUHD.T_EDI_JYUHACYU_ID DESC \n"); // ID

		return sSql.toString();
	}

	/**
	 *  EDI受発注受信データ処理（EDI受発注データ／ﾃﾞｨﾃｰﾙ部）リストを取得するSQLを作成
	 *
	 * @param ZaikoSeihinMoveDataIn_EditForm
	 * @param bindList
	 * @return sSql
	 */
	private String getEdiJyuhacyuDtListSearchSql(EdiJuchuMainte_EditForm editForm, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		//ComUserSession cus = getComUserSession();

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT \n");
		PbsSQL.setCommonColumns(sSql, "JYUHACYUDT"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
		sSql.append("  JYUHACYUDT.SYORI_KBN AS SYORI_KBN \n"); // 処理区分
		sSql.append(" ,JYUHACYUDT.T_EDI_JYUHACYU_ID AS T_EDI_JYUHACYU_ID \n"); // ID
		sSql.append(" ,JYUHACYUDT.JYUHACYU_LINE_NO AS JYUHACYU_LINE_NO \n"); // 受発注行NO
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_RECORD_KBN) AS MR_RECORD_KBN \n"); // 明細行レコード：レコード区分
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_DATA_SERIAL_NO) AS MR_DATA_SERIAL_NO \n"); // 明細行レコード：データシリアルNo
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_LINE_NO) AS MR_LINE_NO \n"); // 明細行レコード：伝票行№
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_SYOHIN_CD) AS MR_SYOHIN_CD \n"); // 明細行レコード：商品コード
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_IRISU) AS MR_IRISU \n"); // 明細行レコード：入数
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_SURYO) AS MR_SURYO \n"); // 明細行レコード：数量
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_TANI) AS MR_TANI \n"); // 明細行レコード：単位
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_TEKIYOU) AS MR_TEKIYOU \n"); // 明細行レコード：摘要
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_YOHAKU) AS MR_YOHAKU \n"); // 明細行レコード：余白
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MO_RECORD_KBN) AS MO_RECORD_KBN \n"); // 明細行オプションレコード：レコード区分
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MO_DATA_SERIAL_NO) AS MO_DATA_SERIAL_NO \n"); // 明細行オプションレコード：データシリアルNo
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MO_LINE_NO) AS MO_LINE_NO \n"); // 明細行オプションレコード：伝票行№
		sSql.append(" ,JYUHACYUDT.MO_SYOHIN_NM AS MO_SYOHIN_NM \n"); // 明細行オプションレコード：商品名
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MO_YOHAKU) AS MO_YOHAKU \n"); // 明細行オプションレコード：余白
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MO_NIHONGO_KBN) AS MO_NIHONGO_KBN \n"); // 明細行オプションレコード：日本語区分
		sSql.append(" ,F_RTRIM(JYUHACYUDT.SHOHIN_CD) AS SHOHIN_CD \n"); // 【変換後】Kz商品CD
		sSql.append(" ,SYO.SHOHIN_NM_URIDEN AS SHOHIN_NM_URIDEN \n"); // Kz商品名
		sSql.append(" ,SYO.YOUKI_KIGO_NM1 AS YOUKI_KIGO_NM1 \n"); // 容器名
		sSql.append(" ,F_RTRIM(JYUHACYUDT.KTKSY_CD) AS KTKSY_CD \n"); // 【変換後】Kz寄託者CD
		sSql.append(" ,F_RTRIM(JYUHACYUDT.SYUKA_SU_CASE) AS SYUKA_SU_CASE \n"); // 変換後】KZ発注数（ｹｰｽ）
		sSql.append(" ,F_RTRIM(JYUHACYUDT.SYUKA_SU_BARA) AS SYUKA_SU_BARA \n"); // 変換後】kz発注数（ﾊﾞﾗ）
		sSql.append(" ,F_RTRIM(JYUHACYUDT.ERROR_KBN) AS ERROR_KBN \n"); // エラー区分

		// 表示用
		sSql.append(" ,F_RTRIM(JYUHACYUDT. MR_TEKIYOU) AS MR_TEKIYOU_VIEW \n"); // 摘要

		// ****From句****
		// EDI受発注ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ
		sSql.append("FROM T_EDI_JYUHACYU_DT JYUHACYUDT \n");
		// EDI受発注ﾃﾞｰﾀ_ﾍｯﾀﾞﾞｰ部
		sSql.append("INNER JOIN T_EDI_JYUHACYU_HD JYUHACYUHD ON (JYUHACYUDT.T_EDI_JYUHACYU_ID = JYUHACYUHD.T_EDI_JYUHACYU_ID) \n");
		// 商品ﾏｽﾀｰ
		sSql.append("LEFT JOIN M_SYOHIN SYO ON (JYUHACYUHD.KAISYA_CD = SYO.KAISYA_CD AND JYUHACYUDT.KTKSY_CD = SYO.KTKSY_CD AND JYUHACYUDT.SHOHIN_CD = SYO.SHOHIN_CD) \n");

		// ****Where句****
		sSql.append("WHERE \n");

		// ID
		sSql.append(" JYUHACYUDT.T_EDI_JYUHACYU_ID = ? \n");
		bindList.add(editForm.getEdiJyuhacyuId());


		sSql.append("ORDER BY	\n");
		sSql.append(" JYUHACYUDT.JYUHACYU_LINE_NO  \n"); // 受発注行NO

		return sSql.toString();
	}

	/**
	 * EDI受発注受信データ処理CSV出力用の検索SQLの作成
	 *
	 * @param searchForm
	 * @param bindList
	 * @return sSql
	 */
	private String getCsvOutSql(EdiJuchuMainte_SearchForm searchForm, List<String> bindList) {
		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT DISTINCT \n");
		// EDI受発注ﾃﾞｰﾀ_ﾍｯﾀﾞｰ
		sSql.append("   F_RTRIM(JYUHACYUHD.SYORI_KBN)     AS SYORI_KBN \n"); // 処理区分
		sSql.append("  ,JYUHACYUHD.T_EDI_JYUHACYU_ID AS T_EDI_JYUHACYU_ID  \n"); // ID
		sSql.append("  ,JYUHACYUHD.KAISYA_CD AS KAISYA_CD  \n"); // 会社CD
		sSql.append("  ,F_RTRIM(JYUHACYUHD.ONLINE_JYUSIN_KAISU) AS ONLINE_JYUSIN_KAISU  \n"); // ｵﾝﾗｲﾝ受信回数
		sSql.append("  ,F_RTRIM(JYUHACYUHD.JYUSIN_DT) AS JYUSIN_DT  \n"); // 受信日
		sSql.append("  ,F_RTRIM(JYUHACYUHD.JYUSIN_TM) AS JYUSIN_TM  \n"); // 受信時間
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DENPYO_GROUP_NO) AS DENPYO_GROUP_NO  \n"); // 入力伝票グループNO
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_RECORD_KBN) AS FH_RECORD_KBN  \n"); // ファイルヘッダーレコード：レコード区分
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DATA_SERIAL_NO) AS FH_DATA_SERIAL_NO  \n"); // ファイルヘッダーレコード：データシリアルNo
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DATE_SYUBETU) AS FH_DATE_SYUBETU  \n"); // ファイルヘッダーレコード：データ種別
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DATA_SAKUSEI_DT) AS FH_DATA_SAKUSEI_DT  \n"); // ファイルヘッダーレコード：データ作成日
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DATA_SAKUSEI_TM) AS FH_DATA_SAKUSEI_TM  \n"); // ファイルヘッダーレコード：データ作成時刻
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_FILE_NO) AS FH_FILE_NO  \n"); // ファイルヘッダーレコード：ファイルNO
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DATA_SYORI_DT) AS FH_DATA_SYORI_DT  \n"); // ファイルヘッダーレコード：データ処理日
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_RIYOU_KIGYO_CD) AS FH_RIYOU_KIGYO_CD  \n"); // ファイルヘッダーレコード：利用者企業コード（受け手）荷主
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_CENTER_CD) AS FH_CENTER_CD  \n"); // ファイルヘッダーレコード：データ送信元センターコード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_CENTER_CD_YOBI) AS FH_CENTER_CD_YOBI  \n"); // ファイルヘッダーレコード：データ送信元センターコード（予備）
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_LAST_SEND_CD) AS FH_LAST_SEND_CD  \n"); // ファイルヘッダーレコード：最終送信先コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_LAST_STATION_ADDR) AS FH_LAST_STATION_ADDR  \n"); // ファイルヘッダーレコード：最終ステーションアドレス
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DIRECT_SEND_CD) AS FH_DIRECT_SEND_CD  \n"); // ファイルヘッダーレコード：直接送信先コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DIRECT_STATION_ADDR) AS FH_DIRECT_STATION_ADDR  \n"); // ファイルヘッダーレコード：直接ステーションアドレス
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_TEIKYO_KIGYO_CD) AS FH_TEIKYO_KIGYO_CD  \n"); // ファイルヘッダーレコード：提供企業コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_TEIKYO_JIGYOSYO_CD) AS FH_TEIKYO_JIGYOSYO_CD  \n"); // ファイルヘッダーレコード：提供企業事業所コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_TEIKYOU_KIGYO_NM) AS FH_TEIKYOU_KIGYO_NM  \n"); // ファイルヘッダーレコード：提供企業名
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_TEIKYOU_BUSYO_NM) AS FH_TEIKYOU_BUSYO_NM  \n"); // ファイルヘッダーレコード：提供企業照会部署名
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DATA_COUNT) AS FH_DATA_COUNT  \n"); // ファイルヘッダーレコード：送信データ件数
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_RECORD_SIZE) AS FH_RECORD_SIZE  \n"); // ファイルヘッダーレコード：レコードサイズ
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_DATA_UMU) AS FH_DATA_UMU  \n"); // ファイルヘッダーレコード：データ有無サイン
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_FORMAT_VERSION) AS FH_FORMAT_VERSION  \n"); // ファイルヘッダーレコード：フォーマットバージョン
		sSql.append("  ,F_RTRIM(JYUHACYUHD.FH_YOHAKU) AS FH_YOHAKU  \n"); // ファイルヘッダーレコード：余白
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_RECORD_KBN) AS DH_RECORD_KBN  \n"); // 伝票ヘッダーレコード：レコード区分
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_DATA_SERIAL_NO) AS DH_DATA_SERIAL_NO  \n"); // 伝票ヘッダーレコード：データシリアルNo
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_DATA_KBN) AS DH_DATA_KBN  \n"); // 伝票ヘッダーレコード：データ区分
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_DENPYO_NO) AS DH_DENPYO_NO  \n"); // 伝票ヘッダーレコード：伝票番号
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_HACHU_DT) AS DH_HACHU_DT  \n"); // 伝票ヘッダーレコード：発注日
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_ICHIJITEN_CD) AS DH_ICHIJITEN_CD  \n"); // 伝票ヘッダーレコード：一次店コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_NIJITEN_CD) AS DH_NIJITEN_CD  \n"); // 伝票ヘッダーレコード：二次店コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_SANJITEN_CD) AS DH_SANJITEN_CD  \n"); // 伝票ヘッダーレコード：三次店コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_YOJITEN_CD) AS DH_YOJITEN_CD  \n"); // 伝票ヘッダーレコード：四次店コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_GOJITEN_CD) AS DH_GOJITEN_CD  \n"); // 伝票ヘッダーレコード：五次店コード
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_NONYU_DT) AS DH_NONYU_DT  \n"); // 伝票ヘッダーレコード：納入日又は取引日
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_TEGATA) AS DH_TEGATA  \n"); // 伝票ヘッダーレコード：手形情報
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_KURA_KBN) AS DH_KURA_KBN  \n"); // 伝票ヘッダーレコード：蔵直区分
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_TORIKESI_SIGN) AS DH_TORIKESI_SIGN  \n"); // 伝票ヘッダーレコード：定期発注取消サイン
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_NONYU_TM) AS DH_NONYU_TM  \n"); // 伝票ヘッダーレコード：納入希望時間
		sSql.append("  ,F_RTRIM(JYUHACYUHD.DH_BIKOU) AS DH_BIKOU  \n"); // 伝票ヘッダーレコード：備考
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_RECORD_KBN1) AS HO_RECORD_KBN1  \n"); // 伝票ヘッダーオプションレコード：レコード区分1
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_DATA_SERIAL_NO1) AS HO_DATA_SERIAL_NO1  \n"); // 伝票ヘッダーオプションレコード：データシリアルNo1
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_VIEW_NO1) AS HO_VIEW_NO1  \n"); // 伝票ヘッダーオプションレコード：伝票ヘッダー参照№1
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_NM1 AS HO_KAISYA_NM1  \n"); // 伝票ヘッダーオプションレコード：社名、店名、取引先名1
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_ADDR1 AS HO_KAISYA_ADDR1  \n"); // 伝票ヘッダーオプションレコード：住所1
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_KAISYA_TEL1) AS HO_KAISYA_TEL1 \n"); // 伝票ヘッダーオプションレコード：電話番号1
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_YOHAKU1) AS HO_YOHAKU1  \n"); // 伝票ヘッダーオプションレコード：余白1
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_TORIHIKI_CD1) AS HO_TORIHIKI_CD1  \n"); // 伝票ヘッダーオプションレコード：取引先対応コード1
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_NIHONGO_KBN1) AS HO_NIHONGO_KBN1 \n"); // 伝票ヘッダーオプションレコード：日本語区分1
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_RECORD_KBN2) AS HO_RECORD_KBN2 \n"); // 伝票ヘッダーオプションレコード：レコード区分2
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_DATA_SERIAL_NO2) AS HO_DATA_SERIAL_NO2  \n"); // 伝票ヘッダーオプションレコード：データシリアルNo2
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_VIEW_NO2) AS HO_VIEW_NO2  \n"); // 伝票ヘッダーオプションレコード：伝票ヘッダー参照№2
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_NM2 AS HO_KAISYA_NM2  \n"); // 伝票ヘッダーオプションレコード：社名、店名、取引先名2
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_ADDR2 AS HO_KAISYA_ADDR2  \n"); // 伝票ヘッダーオプションレコード：住所2
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_KAISYA_TEL2) AS HO_KAISYA_TEL2  \n"); // 伝票ヘッダーオプションレコード：電話番号2
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_YOHAKU2) AS HO_YOHAKU2  \n"); // 伝票ヘッダーオプションレコード：余白2
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_TORIHIKI_CD2) AS HO_TORIHIKI_CD2  \n"); // 伝票ヘッダーオプションレコード：取引先対応コード2
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_NIHONGO_KBN2) AS HO_NIHONGO_KBN2  \n"); // 伝票ヘッダーオプションレコード：日本語区分2
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_RECORD_KBN3) AS HO_RECORD_KBN3  \n"); // 伝票ヘッダーオプションレコード：レコード区分3
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_DATA_SERIAL_NO3) AS HO_DATA_SERIAL_NO3 \n");  // 伝票ヘッダーオプションレコード：データシリアルNo3
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_VIEW_NO3) AS HO_VIEW_NO3  \n"); // 伝票ヘッダーオプションレコード：伝票ヘッダー参照№3
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_NM3 AS HO_KAISYA_NM3  \n"); // 伝票ヘッダーオプションレコード：社名、店名、取引先名3
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_ADDR3 AS HO_KAISYA_ADDR3  \n"); // 伝票ヘッダーオプションレコード：住所3
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_KAISYA_TEL3) AS HO_KAISYA_TEL3  \n"); // 伝票ヘッダーオプションレコード：電話番号3
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_YOHAKU3) AS HO_YOHAKU3  \n"); // 伝票ヘッダーオプションレコード：余白3
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_TORIHIKI_CD3) AS HO_TORIHIKI_CD3  \n"); // 伝票ヘッダーオプションレコード：取引先対応コード3
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_NIHONGO_KBN3) AS HO_NIHONGO_KBN3  \n"); // 伝票ヘッダーオプションレコード：日本語区分3
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_RECORD_KBN4) AS HO_RECORD_KBN4  \n"); // 伝票ヘッダーオプションレコード：レコード区分4
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_DATA_SERIAL_NO4) AS HO_DATA_SERIAL_NO4  \n"); // 伝票ヘッダーオプションレコード：データシリアルNo4
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_VIEW_NO4) AS HO_VIEW_NO4 \n"); // 伝票ヘッダーオプションレコード：伝票ヘッダー参照№4
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_NM4 AS HO_KAISYA_NM4 \n");  // 伝票ヘッダーオプションレコード：社名、店名、取引先名4
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_ADDR4 AS HO_KAISYA_ADDR4  \n"); // 伝票ヘッダーオプションレコード：住所4
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_KAISYA_TEL4) AS HO_KAISYA_TEL4 \n");// 伝票ヘッダーオプションレコード：電話番号4
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_YOHAKU4) AS HO_YOHAKU4 \n"); // 伝票ヘッダーオプションレコード：余白4
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_TORIHIKI_CD4) AS HO_TORIHIKI_CD4 \n"); // 伝票ヘッダーオプションレコード：取引先対応コード4
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_NIHONGO_KBN4) AS HO_NIHONGO_KBN4  \n"); // 伝票ヘッダーオプションレコード：日本語区分4
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_RECORD_KBN5) AS HO_RECORD_KBN5  \n"); // 伝票ヘッダーオプションレコード：レコード区分5
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_DATA_SERIAL_NO5) AS HO_DATA_SERIAL_NO5  \n"); // 伝票ヘッダーオプションレコード：データシリアルNo5
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_VIEW_NO5) AS HO_VIEW_NO5  \n"); // 伝票ヘッダーオプションレコード：伝票ヘッダー参照№5
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_NM5 AS HO_KAISYA_NM5  \n"); // 伝票ヘッダーオプションレコード：社名、店名、取引先名5
		sSql.append("  ,JYUHACYUHD.HO_KAISYA_ADDR5 AS HO_KAISYA_ADDR5 \n"); // 伝票ヘッダーオプションレコード：住所5
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_KAISYA_TEL5) AS HO_KAISYA_TEL5  \n"); // 伝票ヘッダーオプションレコード：電話番号5
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_YOHAKU5) AS HO_YOHAKU5  \n"); // 伝票ヘッダーオプションレコード：余白5
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_TORIHIKI_CD5) AS HO_TORIHIKI_CD5  \n"); // 伝票ヘッダーオプションレコード：取引先対応コード5
		sSql.append("  ,F_RTRIM(JYUHACYUHD.HO_NIHONGO_KBN5) AS HO_NIHONGO_KBN5  \n"); // 伝票ヘッダーオプションレコード：日本語区分5
		sSql.append("  ,F_RTRIM(JYUHACYUHD.ED_RECORD_KBN) AS ED_RECORD_KBN  \n"); // エンドレコード：レコード区分
		sSql.append("  ,F_RTRIM(JYUHACYUHD.ED_DATA_SERIAL_NO) AS ED_DATA_SERIAL_NO  \n"); // エンドレコード：データシリアルNo
		sSql.append("  ,F_RTRIM(JYUHACYUHD.ED_LINE_COUNT) AS ED_LINE_COUNT  \n"); // エンドレコード：レコード件数
		sSql.append("  ,F_RTRIM(JYUHACYUHD.ED_YOHAKU) AS ED_YOHAKU  \n"); // エンドレコード：余白
		sSql.append("  ,F_RTRIM(JYUHACYUHD.SYUKA_YOTEI_DT) AS SYUKA_YOTEI_DT  \n"); // 【変換後】kz出荷予定日
		sSql.append("  ,F_RTRIM(JYUHACYUHD.MINASI_DT) AS MINASI_DT  \n"); // 【変換後】ﾐﾅｼ日付
		sSql.append("  ,F_RTRIM(JYUHACYUHD.TATESN_CD) AS TATESN_CD  \n"); // 【変換後】kz縦線CD
		sSql.append("  ,F_RTRIM(JYUHACYUHD.OROSITEN_CD_LAST) AS OROSITEN_CD_LAST  \n"); // 【変換後】KZ最終送荷先卸CD
		sSql.append("  ,F_RTRIM(JYUHACYUHD.SYUHANTEN_CD) AS SYUHANTEN_CD  \n"); // 【変換後】kz酒販店CD
		sSql.append("  ,F_RTRIM(JYUHACYUHD.JYUCYU_NO) AS JYUCYU_NO  \n"); // 【変換後】KZ受注NO
		sSql.append("  ,F_RTRIM(JYUHACYUHD.ERROR_KBN) AS ERROR_KBN  \n"); // エラー区分

		// EDI受発注ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ
		sSql.append(" ,F_RTRIM(JYUHACYUDT.SYORI_KBN) AS DT_SYORI_KBN \n"); // 処理区分
		sSql.append(" ,JYUHACYUDT.T_EDI_JYUHACYU_ID AS DT_T_EDI_JYUHACYU_ID \n"); // ID
		sSql.append(" ,JYUHACYUDT.JYUHACYU_LINE_NO AS JYUHACYU_LINE_NO \n"); // 受発注行NO
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_RECORD_KBN) AS MR_RECORD_KBN \n"); // 明細行レコード：レコード区分
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_DATA_SERIAL_NO) AS MR_DATA_SERIAL_NO \n"); // 明細行レコード：データシリアルNo
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_LINE_NO) AS MR_LINE_NO \n"); // 明細行レコード：伝票行№
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_SYOHIN_CD) AS MR_SYOHIN_CD \n"); // 明細行レコード：商品コード
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_IRISU) AS MR_IRISU \n"); // 明細行レコード：入数
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_SURYO) AS MR_SURYO \n"); // 明細行レコード：数量
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_TANI) AS MR_TANI \n"); // 明細行レコード：単位
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_TEKIYOU) AS MR_TEKIYOU \n"); // 明細行レコード：摘要
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MR_YOHAKU) AS MR_YOHAKU \n"); // 明細行レコード：余白
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MO_RECORD_KBN) AS MO_RECORD_KBN \n"); // 明細行オプションレコード：レコード区分
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MO_DATA_SERIAL_NO) AS MO_DATA_SERIAL_NO \n"); // 明細行オプションレコード：データシリアルNo
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MO_LINE_NO) AS MO_LINE_NO \n"); // 明細行オプションレコード：伝票行№
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MO_SYOHIN_NM) AS MO_SYOHIN_NM \n"); // 明細行オプションレコード：商品名
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MO_YOHAKU) AS MO_YOHAKU \n"); // 明細行オプションレコード：余白
		sSql.append(" ,F_RTRIM(JYUHACYUDT.MO_NIHONGO_KBN) AS MO_NIHONGO_KBN \n"); // 明細行オプションレコード：日本語区分
		sSql.append(" ,F_RTRIM(JYUHACYUDT.SHOHIN_CD) AS SHOHIN_CD \n"); // 【変換後】Kz商品CD
		sSql.append(" ,F_RTRIM(JYUHACYUDT.KTKSY_CD) AS KTKSY_CD \n"); // 【変換後】Kz寄託者CD
		sSql.append(" ,F_RTRIM(JYUHACYUDT.SYUKA_SU_CASE) AS SYUKA_SU_CASE \n"); // 変換後】KZ発注数（ｹｰｽ）
		sSql.append(" ,F_RTRIM(JYUHACYUDT.SYUKA_SU_BARA) AS SYUKA_SU_BARA \n"); // 変換後】kz発注数（ﾊﾞﾗ）
		sSql.append(" ,F_RTRIM(JYUHACYUDT.ERROR_KBN) AS DT_ERROR_KBN \n"); // エラー区分

		// ****From句****
		// EDI受発注ﾃﾞｰﾀ_ﾍｯﾀﾞﾞｰ部
		sSql.append("FROM T_EDI_JYUHACYU_HD JYUHACYUHD \n");
		// 認証マスタ
		sSql.append("LEFT JOIN T_EDI_JYUHACYU_DT JYUHACYUDT ON (JYUHACYUHD.T_EDI_JYUHACYU_ID = JYUHACYUDT.T_EDI_JYUHACYU_ID) \n");

		// ****Where句****
		String sWhereSql = createCommCondSql(searchForm, bindList);
		sSql.append(sWhereSql);

		// **OrderBy ソート**
		sSql.append("ORDER BY \n");
		sSql.append("  JYUHACYUHD.T_EDI_JYUHACYU_ID DESC, JYUHACYU_LINE_NO ASC \n"); // ID

		return sSql.toString();
	}

	/**
	 * 検索条件を作成する
	 * @param searchForm
	 * @param bindList
	 * @return
	 */
	private String createCommCondSql(EdiJuchuMainte_SearchForm searchForm, List<String> bindList) {
		StringBuilder sWhereSql = new StringBuilder(CHAR_LF);
		// ユーザー情報
		ComUserSession cus = getComUserSession();

		// 会社コード
		sWhereSql.append("WHERE \n");
		sWhereSql.append(" JYUHACYUHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 受信日
		if (!PbsUtil.isEmpty(searchForm.getJyusinDt())) {
			sWhereSql.append(" AND JYUHACYUHD.JYUSIN_DT = ? \n");
			bindList.add(searchForm.getJyusinDt());
		}

		// 受信時間TOが未入力の場合
		if (PbsUtil.isEmpty(searchForm.getJyusinTmTo())) {
			// 受信時間の値を設定
			searchForm.setJyusinTmTo(searchForm.getJyusinTmFrom());
		}

		// 受信時間
		if (!PbsUtil.isEmpty(searchForm.getJyusinTmFrom())) {
			sWhereSql.append(" AND JYUHACYUHD.JYUSIN_TM BETWEEN ? AND ? \n");
			bindList.add(searchForm.getJyusinTmFrom() + "00");
			bindList.add(searchForm.getJyusinTmTo() + "59");
		}

		// 処理区分
		if (!PbsUtil.isEmpty(searchForm.getSyoriKbn())) {
			sWhereSql.append("AND JYUHACYUHD.SYORI_KBN = ? \n");
			bindList.add(searchForm.getSyoriKbn());
		}

		return sWhereSql.toString();
	}

	/**
	 * 縦線CDから縦線卸店情報を取得するSQL
	 *
	 * @param tatesenCd
	 * @param bindList
	 * @param isDeletedHeld
	 * @return sSql
	 */
	private String getTatesenInfoSearchSql(String tatesenCd, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT \n");
		// 縦線基本情報
		sSql.append("  F_RTRIM(ORSHD.RIYOU_KBN) AS RIYOU_KBN \n"); 								// 利用区分
		sSql.append(" ,F_RTRIM(ORSHD.RIYOU_TEISI_DT) AS RIYOU_TEISI_DT \n"); 					// 利用停止日
		sSql.append(" ,F_RTRIM(ORSHD.TATESN_CD) AS TATESN_CD \n"); 								// 縦線CD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_LAST) AS OROSITEN_CD_LAST \n"); 				// 最終送荷先卸CD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_LAST_POSTION) AS OROSITEN_CD_LAST_POSTION \n");// 最終送荷先卸CD位置
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_1JITEN) AS OROSITEN_CD_1JITEN \n");			// 特約店CD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_DEPO) AS OROSITEN_CD_DEPO \n"); 				// デポ店CD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_2JITEN) AS OROSITEN_CD_2JITEN \n"); 			// 二次店CD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_3JITEN) AS OROSITEN_CD_3JITEN \n"); 			// 三次店CD
		sSql.append(" ,ORS.TEN_NM1_URIDEN AS OROSITEN_NM \n"); 									// 卸店名
		sSql.append(" ,ORS.TEN_NM1_URIDEN AS OROSITEN_NM_LAST \n"); 							// 最終送荷先卸名
		sSql.append(" ,ORS1JI.TEN_NM1_URIDEN AS OROSITEN_NM_1JITEN \n"); 						// 特約店名
		sSql.append(" ,ORSDEP.TEN_NM1_URIDEN AS OROSITEN_NM_DEPO \n"); 							// デポ店名
		sSql.append(" ,ORS2JI.TEN_NM1_URIDEN AS OROSITEN_NM_2JITEN \n"); 						// 二次店名
		sSql.append(" ,ORS3JI.TEN_NM1_URIDEN AS OROSITEN_NM_3JITEN \n"); 						// 三次店名
		sSql.append(" ,ORS.TEN_NM1_JISYA AS OROSITEN_NM_RYAKU \n"); 							// 卸店名（略称）
		sSql.append(" ,ORS.TEN_NM1_JISYA AS OROSITEN_NM_LAST_RYAKU \n"); 						// 最終送荷先卸名（略称）
		sSql.append(" ,ORS1JI.TEN_NM1_JISYA AS OROSITEN_NM_1JITEN_RYAKU \n");					// 特約店名（略称）
		sSql.append(" ,ORSDEP.TEN_NM1_JISYA AS OROSITEN_NM_DEPO_RYAKU \n");						// デポ店名（略称）
		sSql.append(" ,ORS2JI.TEN_NM1_JISYA AS OROSITEN_NM_2JITEN_RYAKU \n");					// 二次店名（略称）
		sSql.append(" ,ORS3JI.TEN_NM1_JISYA AS OROSITEN_NM_3JITEN_RYAKU \n");					// 三次店名（略称）
		sSql.append(" ,ORS1JI.ADDRESS1_URIDEN || ORS1JI.ADDRESS2_URIDEN || ORS1JI.ADDRESS3_URIDEN || ORS1JI.ADDRESS4_URIDEN  AS ADDRESS_1JITEN \n"); // 特約店住所
		sSql.append(" ,F_RTRIM(ORS1JI.TEL)           AS TEL_1JITEN \n"); // 特約店TEL
		sSql.append(" ,ORSDEP.ADDRESS1_URIDEN || ORSDEP.ADDRESS2_URIDEN || ORSDEP.ADDRESS3_URIDEN || ORSDEP.ADDRESS4_URIDEN AS ADDRESS_DEPO \n"); // デポ店住所
		sSql.append(" ,F_RTRIM(ORSDEP.TEL)           AS TEL_DEPO \n"); // デポ店TEL
		sSql.append(" ,ORS2JI.ADDRESS1_URIDEN || ORS2JI.ADDRESS2_URIDEN || ORS2JI.ADDRESS3_URIDEN || ORS2JI.ADDRESS4_URIDEN AS ADDRESS_2JITEN \n"); // 二次店住所
		sSql.append(" ,F_RTRIM(ORS2JI.TEL)           AS TEL_2JITEN \n"); // 二次店TEL
		sSql.append(" ,ORS3JI.ADDRESS1_URIDEN || ORS3JI.ADDRESS2_URIDEN || ORS3JI.ADDRESS3_URIDEN || ORS3JI.ADDRESS4_URIDEN AS ADDRESS_3JITEN \n"); // 三次店住所
		sSql.append(" ,F_RTRIM(ORS3JI.TEL)           AS TEL_3JITEN \n"); // 三次店TEL
		sSql.append(" ,F_RTRIM(ORSHD.SYUKA_DT_CYUI_KBN) || '：' || F_RTRIM(MEI.CD_NM) AS SYUKA_DT_CYUI_KBN \n"); // 出荷日注意
		sSql.append(" ,ORSHD.HACYU_TANTOSYA_NM  AS HACYU_TANTOSYA_NM \n"); // 発注担当者
		sSql.append(" ,F_RTRIM(ORSHD.HACYUMOTO_TEL)      AS HACYUMOTO_TEL \n"); // 発注元TEL
		sSql.append(" ,F_RTRIM(ORS.EDI_JSYOHIN_TEKIYOCHEK_KBN) AS EDI_JSYOHIN_TEKIYOCHEK_KBN \n"); // EDI受発注商品摘要欄確認区分

		// 縦線
		sSql.append(" ,NVL(F_RTRIM(ORSHD.OROSITEN_CD_1JITEN),'')||NVL(F_RTRIM(ORSHD.OROSITEN_CD_DEPO),'')||NVL(F_RTRIM(ORSHD.OROSITEN_CD_2JITEN),'')||NVL(F_RTRIM(ORSHD.OROSITEN_CD_3JITEN),'') AS TATESEN_LINE \n"); // 縦線ライン

		sSql.append("FROM M_OROSISYOSAI_HD ORSHD \n");	// 卸店詳細マスターヘッダー部

		sSql.append("LEFT JOIN M_OROSITEN ORS ON (ORSHD.KAISYA_CD = ORS.KAISYA_CD AND ORSHD.OROSITEN_CD_LAST = ORS.OROSITEN_CD) \n");				// 卸店マスター（最終送荷先卸店）
		sSql.append("LEFT JOIN M_OROSITEN ORS1JI ON (ORSHD.KAISYA_CD = ORS1JI.KAISYA_CD AND ORSHD.OROSITEN_CD_1JITEN = ORS1JI.OROSITEN_CD) \n");	// 卸店マスター（特約店）
		sSql.append("LEFT JOIN M_OROSITEN ORSDEP ON (ORSHD.KAISYA_CD = ORSDEP.KAISYA_CD AND ORSHD.OROSITEN_CD_DEPO = ORSDEP.OROSITEN_CD) \n");		// 卸店マスター（デポ店）
		sSql.append("LEFT JOIN M_OROSITEN ORS2JI ON (ORSHD.KAISYA_CD = ORS2JI.KAISYA_CD AND ORSHD.OROSITEN_CD_2JITEN = ORS2JI.OROSITEN_CD) \n");	// 卸店マスター（二次店）
		sSql.append("LEFT JOIN M_OROSITEN ORS3JI ON (ORSHD.KAISYA_CD = ORS3JI.KAISYA_CD AND ORSHD.OROSITEN_CD_3JITEN = ORS3JI.OROSITEN_CD) \n");	// 卸店マスター（三次店）
		sSql.append("LEFT JOIN M_CD_MEISYOU MEI  ON (MEI.KBN_ID = 'KBN_SYUK_CAREF' AND ORSHD.SYUKA_DT_CYUI_KBN = MEI.CODE_CD) \n");	// コード名称マスター

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" ORSHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 縦線CD
		sSql.append("AND ORSHD.TATESN_CD = ? \n");
		bindList.add(tatesenCd);

//		// 削除済を含めない（利用可のみ）
//		//if (!isDeletedHeld) {
//		sSql.append("AND ORSHD.RIYOU_KBN = ? \n");
//		bindList.add(AVAILABLE_KB_RIYO_KA);
		//}

		// 利用状態は1：仮登録、2：利用可能
		sSql.append("AND F_GET_RIYOU_JOTAI_FLG(?, ORSHD.NOUHIN_KAISI_DT, ORSHD.NOUHIN_SYURYO_DT, ORSHD.RIYOU_TEISI_DT) IN ('1', '2') \n");
		bindList.add(cus.getGymDate());
		return sSql.toString();
	}

	/**
	 * 縦線CDから卸店情報を取得するSQL
	 *
	 * @param tatesenCd
	 * @param bindList
	 * @param isDeletedHeld
	 * @return sSql
	 */
	private String getOrosiInfoSearchSql(String tatesenCd, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT \n");
		// 卸店基本情報
		sSql.append(" F_RTRIM(ORSHD.OROSITEN_CD_LAST) AS OROSITEN_CD_LAST \n"); // 最終送荷先卸CD
		sSql.append(" ,F_RTRIM(ORS.EDI_JSYOHIN_TEKIYOCHEK_KBN) AS EDI_JSYOHIN_TEKIYOCHEK_KBN \n"); // EDI受発注商品摘要欄確認区分

		sSql.append("FROM M_OROSISYOSAI_HD ORSHD \n");	// 卸店詳細マスターヘッダー部
		sSql.append("LEFT JOIN M_OROSITEN ORS ON (ORSHD.KAISYA_CD = ORS.KAISYA_CD AND ORSHD.OROSITEN_CD_LAST = ORS.OROSITEN_CD) \n"); // 卸店マスター（最終送荷先卸店）

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" ORSHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 縦線CD
		sSql.append("AND ORSHD.TATESN_CD = ? \n");
		bindList.add(tatesenCd);

		// 削除済を含めない（利用可のみ）
		//if (!isDeletedHeld) {
		sSql.append("AND ORSHD.RIYOU_KBN = ? \n");
		bindList.add(AVAILABLE_KB_RIYO_KA);
		//}

		return sSql.toString();
	}

	/**
	 * 受注No採番用管轄事業所ｷｰを取得するSQLを返す.
	 * @param tatesenCd
	 * @param bindList
	 * @return
	 */
	public String getJuchuMngKeySql(String tatesenCd, String kaisyaCd, List<String> bindList) {
		StringBuilder sSql = new StringBuilder(SQL_CRLF);

		sSql.append("SELECT \n");
		sSql.append("  B.JUCHU_MNG_KEY \n");// 受注No採番用管轄事業所ｷｰ
		sSql.append("FROM M_OROSISYOSAI_HD A \n");	// 卸店詳細ﾏｽﾀｰ_ﾍｯﾀﾞｰ部
		sSql.append("LEFT JOIN M_KBN_JISEKI_TANTO B ON (A.KAISYA_CD = B.KAISYA_CD AND A.TANTOSYA_CD = B.TANTO_CD) \n");		// 販売実績担当区分ﾏｽﾀｰ
		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" A.KAISYA_CD = ? \n");
		bindList.add(kaisyaCd);

		// 縦線CD
		sSql.append("  AND A.TATESN_CD = ? \n");
		bindList.add(tatesenCd);

		return sSql.toString();
	}
}
