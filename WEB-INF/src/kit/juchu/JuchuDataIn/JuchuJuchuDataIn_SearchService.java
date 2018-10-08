package kit.juchu.JuchuDataIn;

import static fb.inf.pbs.IPbsConst.*;

import java.util.ArrayList;
import java.util.List;

import kit.pop.ComPopupUtil;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComUserSession;
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
public class JuchuJuchuDataIn_SearchService extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = 195711663489094126L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JuchuJuchuDataIn_SearchService.class.getName();
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
	public JuchuJuchuDataIn_SearchService(ComUserSession cus, PbsDatabase db_, boolean isTran,
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
	 * 受注入力（受注データ／ヘッダー部）検索のSQL生成と実行.
	 *
	 * @param searchForm 検索フォーム
	 * @return 検索結果
	 * @throws DataNotFoundException
	 */
	public PbsRecord[] execute(JuchuJuchuDataIn_SearchForm searchForm)
			throws DataNotFoundException {

		category__.info("受注入力（受注データ／ヘッダー部）検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("受注入力（受注データ／ヘッダー部） >> 検索処理 >> SQL文生成");
		String searchSql = getSqlSearch(searchForm, bindList);

		category__.info("受注入力（受注データ／ヘッダー部） >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("受注入力（受注データ／ヘッダー部） >> execute");
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
		category__.info("受注入力（受注データ／ヘッダー部）検索処理 終了");

		return records_;
	}

	/**
	 * 受注入力（受注データ／ヘッダー部）の検索SQLの作成
	 *
	 * @param searchForm
	 * @param bindList
	 * @return sSql
	 */
	private String getSqlSearch(JuchuJuchuDataIn_SearchForm searchForm, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT DISTINCT \n");
		PbsSQL.setCommonColumns(sSql, "JCUHD"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
		sSql.append("  JCUHD.RIYOU_KBN AS RIYOU_KBN \n"); // 利用区分
		sSql.append(" ,JCUHD.KAISYA_CD AS KAISYA_CD \n"); // 会社CD
		sSql.append(" ,JCUHD.JYUCYU_NO AS JYUCYU_NO \n"); // 受注NO
		sSql.append(" ,JCUHD.SYUKA_KBN AS SYUKA_KBN \n"); // 出荷先区分
		sSql.append(" ,JCUHD.SYUBETU_CD AS SYUBETU_CD \n"); // データ種別CD
		sSql.append(" ,JCUHD.KURA_CD AS KURA_CD \n"); // 蔵CD
		sSql.append(" ,JCUHD.UNCHIN_KBN AS UNCHIN_KBN \n"); // 運賃区分
		sSql.append(" ,JCUHD.UNSOTEN_CD AS UNSOTEN_CD \n"); // 運送店CD
		sSql.append(" ,UNS.UNSOTEN_NM_PRINT AS UNSOTEN_NM \n"); // 運送店名（略称）
		sSql.append(" ,JCUHD.SYUKA_DT AS SYUKA_DT \n"); // 出荷日(売上伝票発行予定日)
		sSql.append(" ,JCUHD.MINASI_DT AS MINASI_DT \n"); // ミナシ日付
		sSql.append(" ,F_RTRIM(JCUHD.CHACUNI_YOTEI_DT) AS CHACUNI_YOTEI_DT \n"); // 着荷予定日
		sSql.append(" ,F_RTRIM(JCUHD.NIUKE_TIME_KBN) AS NIUKE_TIME_KBN \n"); // 荷受時間区分
		sSql.append(" ,F_RTRIM(JCUHD.NIUKE_BIGIN_TIME) AS NIUKE_BIGIN_TIME \n"); // 荷受時間_開始
		sSql.append(" ,F_RTRIM(JCUHD.NIUKE_END_TIME) AS NIUKE_END_TIME \n"); // 荷受時間_終了
		sSql.append(" ,F_RTRIM(JCUHD.SENPO_HACYU_NO) AS SENPO_HACYU_NO \n"); // 先方発注NO
		sSql.append(" ,F_RTRIM(JCUHD.SDN_HDERR_KBN) AS SDN_HDERR_KBN \n"); // SDN受注ヘッダーエラー区分
		sSql.append(" ,JCUHD.TATESN_CD AS ORS_TATESN_CD \n"); // 縦線CD
		sSql.append(" ,JCUHD.TANTOSYA_CD AS TANTOSYA_CD \n"); // 担当者CD
		sSql.append(" ,JCUHD.TOKUYAKUTEN_CD AS TOKUYAKUTEN_CD \n"); // 特約店CD
		sSql.append(" ,ORS1JI.TEN_NM1_JISYA AS TOKUYAKUTEN_NM \n"); // 特約店名（略称）
		sSql.append(" ,JCUHD.DEPO_CD AS DEPO_CD \n"); // デポCD
		sSql.append(" ,ORSDEP.TEN_NM1_JISYA AS DEPO_NM \n"); // デポ店名（略称）
		sSql.append(" ,F_RTRIM(JCUHD.NIJITEN_CD) AS NIJITEN_CD \n"); // 二次店CD
		sSql.append(" ,F_RTRIM(ORS2JI.TEN_NM1_JISYA) AS NIJITEN_NM \n"); // 二次店名（略称）
		sSql.append(" ,F_RTRIM(JCUHD.SANJITEN_CD) AS SANJITEN_CD \n"); // 三次店CD
		sSql.append(" ,F_RTRIM(ORS3JI.TEN_NM1_JISYA) AS SANJITEN_NM \n"); // 三次店名（略称）
		sSql.append(" ,F_RTRIM(JCUHD.SYUHANTEN_CD) AS SYUHANTEN_CD \n"); // 酒販店（統一）CD
		sSql.append(" ,F_RTRIM(SYH.TEN_NM_YAGO) AS SYUHANTEN_NM \n"); // 酒販店名
		sSql.append(" ,JCUHD.DELIVERY_KBN AS DELIVERY_KBN \n"); // 倉入・直送区分
		sSql.append(" ,JCUHD.SYUKA_SAKI_COUNTRY_CD AS SYUKA_SAKI_COUNTRY_CD \n"); // 出荷先国CD
		sSql.append(" ,JCUHD.JIS_CD AS JIS_CD \n"); // JISCD
		sSql.append(" ,JCUHD.UNCHIN_CNV_TANKA AS UNCHIN_CNV_TANKA \n"); // 引取運賃換算単価
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN1) AS TEKIYO_KBN1 \n"); // 摘要区分 (01)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM1) AS TEKIYO_NM1 \n"); // 摘要内容 (01)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN2) AS TEKIYO_KBN2 \n"); // 摘要区分 (02)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM2) AS TEKIYO_NM2 \n"); // 摘要内容 (02)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN3) AS TEKIYO_KBN3 \n"); // 摘要区分 (03)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM3) AS TEKIYO_NM3 \n"); // 摘要内容 (03)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN4) AS TEKIYO_KBN4 \n"); // 摘要区分 (04)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM4) AS TEKIYO_NM4 \n"); // 摘要内容 (04)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN5) AS TEKIYO_KBN5 \n"); // 摘要区分 (05)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM5) AS TEKIYO_NM5 \n"); // 摘要内容 (05)
		sSql.append(" ,JCUHD.ATUKAI_KBN AS ATUKAI_KBN \n"); // 扱い区分
		sSql.append(" ,JCUHD.LOW_HAISO_LOT AS LOW_HAISO_LOT \n"); // 最低配送ロット数
		sSql.append(" ,F_RTRIM(JCUHD.SYUKA_SYONIN_NO) AS SYUKA_SYONIN_NO \n"); // 小ロット出荷承認申請NO
		sSql.append(" ,JCUHD.SYUKA_SURYO_BOX AS SYUKA_SURYO_BOX \n"); // 出荷数量計_箱数
		sSql.append(" ,JCUHD.SYUKA_SURYO_SET AS SYUKA_SURYO_SET \n"); // 出荷数量計_セット数
		sSql.append(" ,JCUHD.SYUKA_KINGAKU_TOT AS SYUKA_KINGAKU_TOT \n"); // 出荷金額計
		sSql.append(" ,JCUHD.JYURYO_TOT AS JYURYO_TOT \n"); // 重量計(KG)
		sSql.append(" ,JCUHD.SYUKA_YOURYO_TOT AS SYUKA_YOURYO_TOT \n"); // 容量計（L）_出荷容量計
		sSql.append(" ,JCUHD.REBATE1_YOURYO_TOT AS REBATE1_YOURYO_TOT \n"); // 容量計（L）_リベートⅠ類対象容量計
		sSql.append(" ,JCUHD.REBATE2_YOURYO_TOT AS REBATE2_YOURYO_TOT \n"); // 容量計（L）_リベートⅡ類対象容量計
		sSql.append(" ,JCUHD.REBATE3_YOURYO_TOT AS REBATE3_YOURYO_TOT \n"); // 容量計（L）_リベートⅢ類対象容量計
		sSql.append(" ,JCUHD.REBATE4_YOURYO_TOT AS REBATE4_YOURYO_TOT \n"); // 容量計（L）_リベートⅣ類対象容量計
		sSql.append(" ,JCUHD.REBATE5_YOURYO_TOT AS REBATE5_YOURYO_TOT \n"); // 容量計（L）_リベートⅤ類対象容量計
		sSql.append(" ,JCUHD.REBATEO_YOURYO_TOT AS REBATEO_YOURYO_TOT \n"); // 容量計（L）_リベート対象外容量計
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_NO) AS TUMIDEN_NO \n"); // 積荷伝票NO
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_SM_KBN) AS TUMIDEN_SM_KBN \n"); // 積荷累積対象区分
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_HAKO_DT) AS TUMIDEN_HAKO_DT \n"); // 積荷伝票発行日
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_HAKO_TM) AS TUMIDEN_HAKO_TM \n"); // 積荷伝票発行時刻
		sSql.append(" ,JCUHD.TUMIDEN_HAKO_CNT AS TUMIDEN_HAKO_CNT \n"); // 積荷伝票発行回数
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_HAKOSYA) AS TUMIDEN_HAKOSYA \n"); // 積荷伝票発行者ID

		sSql.append(" ,ORSHD.OROSITEN_CD_LAST AS OROSITEN_CD_LAST \n"); // 最終送荷先卸CD
		sSql.append(" ,ORS.TEN_NM1_JISYA AS OROSITEN_NM_LAST \n"); // 最終送荷先卸名（略称）
		sSql.append(" ,SUBSTR(JCUHD.JIS_CD,0,2) AS TODOFUKN_CD \n"); // 都道府県CD
		sSql.append(" ,(CASE WHEN F_RTRIM(JCUHD.KOUSIN_SGYOSYA_CD) IS NULL THEN F_RTRIM(JCUHD.TOROKU_SGYOSYA_CD) ELSE F_RTRIM(JCUHD.KOUSIN_SGYOSYA_CD) END) AS NYURYOKUSYA_CD \n"); // 入力者CD
		sSql.append(" ,(CASE WHEN F_RTRIM(JCUHD.KOUSIN_SGYOSYA_CD) IS NULL THEN F_RTRIM(TSG.SGYOSYA_NM) ELSE F_RTRIM(KSG.SGYOSYA_NM) END) AS NYURYOKUSYA_NM \n"); // 入力者名
		sSql.append(" ,ORS.SOUKO_SYURUI_KBN AS SOUKO_SYURUI_KBN \n"); // 倉庫種類区分
		sSql.append(" ,ORSHD.TOUJITU_SYUKKA_KBN AS TOUJITU_SYUKKA_KBN \n"); // 当日出荷可能区分
		sSql.append(" ,ORSHD.SYUKA_DT_CYUI_KBN AS SYUKA_DT_CYUI_KBN \n"); // 出荷日注意区分
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_MON) AS NIUKE_FUKA_MON \n"); // 荷受不可曜日_月
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_TUE) AS NIUKE_FUKA_TUE \n"); // 荷受不可曜日_火
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_WED) AS NIUKE_FUKA_WED \n"); // 荷受不可曜日_水
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_THU) AS NIUKE_FUKA_THU \n"); // 荷受不可曜日_木
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_FRI) AS NIUKE_FUKA_FRI \n"); // 荷受不可曜日_金
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_SAT) AS NIUKE_FUKA_SAT \n"); // 荷受不可曜日_土
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_SUN) AS NIUKE_FUKA_SUN \n"); // 荷受不可曜日_日
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_HOL) AS NIUKE_FUKA_HOL \n"); // 荷受不可曜日_祝

		sSql.append(" ,F_RTRIM(ORSHD.TOKUYAKUTEN_CD_SMGP) AS SMGP_TOKUYAKUTEN_CD \n"); // 請求明細グループCD_特約店CD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_SMGP_DEPO_CD) AS SMGP_DEPO_CD \n"); // 請求明細グループCD_デポCD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_SMGP_NIJITEN_CD) AS SMGP_NIJITEN_CD \n"); // 請求明細グループCD_二次店CD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_SMGP_SANJITEN_CD) AS SMGP_SANJITEN_CD \n"); // 請求明細グループCD_三次店CD
		sSql.append(" ,SMGP1JI.SEIKYUSAKI_NM01 AS SEIKYUSAKI_NM01 \n"); // 請求先名称(正) 1/2
		sSql.append(" ,F_RTRIM(SMGP1JI.SEIKYUSAKI_NM02) AS SEIKYUSAKI_NM02 \n"); // 請求先名称(正) 2/2

		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_1_TX) AS BIKOU_1_TX \n"); // 備考1
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_2_TX) AS BIKOU_2_TX \n"); // 備考2
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_3_TX) AS BIKOU_3_TX \n"); // 備考3
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_4_TX) AS BIKOU_4_TX \n"); // 備考4
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_5_TX) AS BIKOU_5_TX \n"); // 備考5

		// 表示用
		sSql.append(" ,JCUHD.RIYOU_KBN AS RIYOU_KBN_VIEW \n"); // 利用区分
		sSql.append(" ,JCUHD.KAISYA_CD AS KAISYA_CD_VIEW \n"); // 会社CD
		sSql.append(" ,JCUHD.JYUCYU_NO AS JYUCYU_NO_VIEW \n"); // 受注NO
		sSql.append(" ,JCUHD.SYUKA_KBN AS SYUKA_KBN_VIEW \n"); // 出荷先区分
		sSql.append(" ,JCUHD.SYUBETU_CD AS SYUBETU_CD_VIEW \n"); // データ種別CD
		sSql.append(" ,JCUHD.KURA_CD AS KURA_CD_VIEW \n"); // 蔵CD
		sSql.append(" ,JCUHD.UNCHIN_KBN AS UNCHIN_KBN_VIEW \n"); // 運賃区分
		sSql.append(" ,JCUHD.UNSOTEN_CD AS UNSOTEN_CD_VIEW \n"); // 運送店CD
		sSql.append(" ,UNS.UNSOTEN_NM_PRINT AS UNSOTEN_NM_VIEW \n"); // 運送店名（略称）
		sSql.append(" ,JCUHD.SYUKA_DT AS SYUKA_DT_VIEW \n"); // 出荷日(売上伝票発行予定日)
		sSql.append(" ,JCUHD.MINASI_DT AS MINASI_DT_VIEW \n"); // ミナシ日付
		sSql.append(" ,F_RTRIM(JCUHD.CHACUNI_YOTEI_DT) AS CHACUNI_YOTEI_DT_VIEW \n"); // 着荷予定日
		sSql.append(" ,F_RTRIM(JCUHD.NIUKE_TIME_KBN) AS NIUKE_TIME_KBN_VIEW \n"); // 荷受時間区分
		sSql.append(" ,F_RTRIM(JCUHD.NIUKE_BIGIN_TIME) AS NIUKE_BIGIN_TIME_VIEW \n"); // 荷受時間_開始
		sSql.append(" ,F_RTRIM(JCUHD.NIUKE_END_TIME) AS NIUKE_END_TIME_VIEW \n"); // 荷受時間_終了
		sSql.append(" ,F_RTRIM(JCUHD.SENPO_HACYU_NO) AS SENPO_HACYU_NO_VIEW \n"); // 先方発注NO
		sSql.append(" ,F_RTRIM(JCUHD.SDN_HDERR_KBN) AS SDN_HDERR_KBN_VIEW \n"); // SDN受注ヘッダーエラー区分
		sSql.append(" ,JCUHD.TATESN_CD AS ORS_TATESN_CD_VIEW \n"); // 縦線CD
		sSql.append(" ,JCUHD.TANTOSYA_CD AS TANTOSYA_CD_VIEW \n"); // 担当者CD
		sSql.append(" ,JCUHD.TOKUYAKUTEN_CD AS TOKUYAKUTEN_CD_VIEW \n"); // 特約店CD
		sSql.append(" ,ORS1JI.TEN_NM1_JISYA AS TOKUYAKUTEN_NM_VIEW \n"); // 特約店名（略称）
		sSql.append(" ,JCUHD.DEPO_CD AS DEPO_CD_VIEW \n"); // デポCD
		sSql.append(" ,ORSDEP.TEN_NM1_JISYA AS DEPO_NM_VIEW \n"); // デポ店名（略称）
		sSql.append(" ,F_RTRIM(JCUHD.NIJITEN_CD) AS NIJITEN_CD_VIEW \n"); // 二次店CD
		sSql.append(" ,F_RTRIM(ORS2JI.TEN_NM1_JISYA) AS NIJITEN_NM_VIEW \n"); // 二次店名（略称）
		sSql.append(" ,F_RTRIM(JCUHD.SANJITEN_CD) AS SANJITEN_CD_VIEW \n"); // 三次店CD
		sSql.append(" ,F_RTRIM(ORS3JI.TEN_NM1_JISYA) AS SANJITEN_NM_VIEW \n"); // 三次店名（略称）
		sSql.append(" ,F_RTRIM(JCUHD.SYUHANTEN_CD) AS SYUHANTEN_CD_VIEW \n"); // 酒販店（統一）CD
		sSql.append(" ,F_RTRIM(SYH.TEN_NM_YAGO) AS SYUHANTEN_NM_VIEW \n"); // 酒販店名
		sSql.append(" ,JCUHD.DELIVERY_KBN AS DELIVERY_KBN_VIEW \n"); // 倉入・直送区分
		sSql.append(" ,JCUHD.SYUKA_SAKI_COUNTRY_CD AS SYUKA_SAKI_COUNTRY_CD_VIEW \n"); // 出荷先国CD
		sSql.append(" ,JCUHD.JIS_CD AS JIS_CD_VIEW \n"); // JISCD
		sSql.append(" ,JCUHD.UNCHIN_CNV_TANKA AS UNCHIN_CNV_TANKA_VIEW \n"); // 引取運賃換算単価
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN1) AS TEKIYO_KBN1_VIEW \n"); // 摘要区分 (01)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM1) AS TEKIYO_NM1_VIEW \n"); // 摘要内容 (01)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN2) AS TEKIYO_KBN2_VIEW \n"); // 摘要区分 (02)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM2) AS TEKIYO_NM2_VIEW \n"); // 摘要内容 (02)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN3) AS TEKIYO_KBN3_VIEW \n"); // 摘要区分 (03)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM3) AS TEKIYO_NM3_VIEW \n"); // 摘要内容 (03)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN4) AS TEKIYO_KBN4_VIEW \n"); // 摘要区分 (04)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM4) AS TEKIYO_NM4_VIEW \n"); // 摘要内容 (04)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN5) AS TEKIYO_KBN5_VIEW \n"); // 摘要区分 (05)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM5) AS TEKIYO_NM5_VIEW \n"); // 摘要内容 (05)
		sSql.append(" ,JCUHD.ATUKAI_KBN AS ATUKAI_KBN_VIEW \n"); // 扱い区分
		sSql.append(" ,JCUHD.LOW_HAISO_LOT AS LOW_HAISO_LOT_VIEW \n"); // 最低配送ロット数
		sSql.append(" ,F_RTRIM(JCUHD.SYUKA_SYONIN_NO) AS SYUKA_SYONIN_NO_VIEW \n"); // 小ロット出荷承認申請NO
		sSql.append(" ,JCUHD.SYUKA_SURYO_BOX AS SYUKA_SURYO_BOX_VIEW \n"); // 出荷数量計_箱数
		sSql.append(" ,JCUHD.SYUKA_SURYO_SET AS SYUKA_SURYO_SET_VIEW \n"); // 出荷数量計_セット数
		sSql.append(" ,JCUHD.SYUKA_KINGAKU_TOT AS SYUKA_KINGAKU_TOT_VIEW \n"); // 出荷金額計
		sSql.append(" ,JCUHD.JYURYO_TOT AS JYURYO_TOT_VIEW \n"); // 重量計(KG)
		sSql.append(" ,JCUHD.SYUKA_YOURYO_TOT AS SYUKA_YOURYO_TOT_VIEW \n"); // 容量計（L）_出荷容量計
		sSql.append(" ,JCUHD.REBATE1_YOURYO_TOT AS REBATE1_YOURYO_TOT_VIEW \n"); // 容量計（L）_リベートⅠ類対象容量計
		sSql.append(" ,JCUHD.REBATE2_YOURYO_TOT AS REBATE2_YOURYO_TOT_VIEW \n"); // 容量計（L）_リベートⅡ類対象容量計
		sSql.append(" ,JCUHD.REBATE3_YOURYO_TOT AS REBATE3_YOURYO_TOT_VIEW \n"); // 容量計（L）_リベートⅢ類対象容量計
		sSql.append(" ,JCUHD.REBATE4_YOURYO_TOT AS REBATE4_YOURYO_TOT_VIEW \n"); // 容量計（L）_リベートⅣ類対象容量計
		sSql.append(" ,JCUHD.REBATE5_YOURYO_TOT AS REBATE5_YOURYO_TOT_VIEW \n"); // 容量計（L）_リベートⅤ類対象容量計
		sSql.append(" ,JCUHD.REBATEO_YOURYO_TOT AS REBATEO_YOURYO_TOT_VIEW \n"); // 容量計（L）_リベート対象外容量計
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_NO) AS TUMIDEN_NO_VIEW \n"); // 積荷伝票NO
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_SM_KBN) AS TUMIDEN_SM_KBN_VIEW \n"); // 積荷累積対象区分
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_HAKO_DT) AS TUMIDEN_HAKO_DT_VIEW \n"); // 積荷伝票発行日
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_HAKO_TM) AS TUMIDEN_HAKO_TM_VIEW \n"); // 積荷伝票発行時刻
		sSql.append(" ,JCUHD.TUMIDEN_HAKO_CNT AS TUMIDEN_HAKO_CNT_VIEW \n"); // 積荷伝票発行回数
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_HAKOSYA) AS TUMIDEN_HAKOSYA_VIEW \n"); // 積荷伝票発行者ID

		sSql.append(" ,ORSHD.OROSITEN_CD_LAST AS OROSITEN_CD_LAST_VIEW \n"); // 最終送荷先卸CD
		sSql.append(" ,ORS.TEN_NM1_JISYA AS OROSITEN_NM_LAST_VIEW \n"); // 最終送荷先卸名（略称）
		sSql.append(" ,SUBSTR(JCUHD.JIS_CD,0,2) AS TODOFUKN_CD_VIEW \n"); // 都道府県CD
		sSql.append(" ,(CASE WHEN F_RTRIM(JCUHD.KOUSIN_SGYOSYA_CD) IS NULL THEN F_RTRIM(JCUHD.TOROKU_SGYOSYA_CD) ELSE F_RTRIM(JCUHD.KOUSIN_SGYOSYA_CD) END) AS NYURYOKUSYA_CD_VIEW \n"); // 入力者CD
		sSql.append(" ,(CASE WHEN F_RTRIM(JCUHD.KOUSIN_SGYOSYA_CD) IS NULL THEN F_RTRIM(TSG.SGYOSYA_NM) ELSE F_RTRIM(KSG.SGYOSYA_NM) END) AS NYURYOKUSYA_NM_VIEW \n"); // 入力者名
		sSql.append(" ,ORS.SOUKO_SYURUI_KBN AS SOUKO_SYURUI_KBN_VIEW \n"); // 倉庫種類区分
		sSql.append(" ,ORSHD.TOUJITU_SYUKKA_KBN AS TOUJITU_SYUKKA_KBN_VIEW \n"); // 当日出荷可能区分
		sSql.append(" ,ORSHD.SYUKA_DT_CYUI_KBN AS SYUKA_DT_CYUI_KBN_VIEW \n"); // 出荷日注意区分
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_MON) AS NIUKE_FUKA_MON_VIEW \n"); // 荷受不可曜日_月
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_TUE) AS NIUKE_FUKA_TUE_VIEW \n"); // 荷受不可曜日_火
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_WED) AS NIUKE_FUKA_WED_VIEW \n"); // 荷受不可曜日_水
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_THU) AS NIUKE_FUKA_THU_VIEW \n"); // 荷受不可曜日_木
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_FRI) AS NIUKE_FUKA_FRI_VIEW \n"); // 荷受不可曜日_金
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_SAT) AS NIUKE_FUKA_SAT_VIEW \n"); // 荷受不可曜日_土
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_SUN) AS NIUKE_FUKA_SUN_VIEW \n"); // 荷受不可曜日_日
		sSql.append(" ,F_RTRIM(ORSHD.NIUKE_FUKA_HOL) AS NIUKE_FUKA_HOL_VIEW \n"); // 荷受不可曜日_祝

		sSql.append(" ,F_RTRIM(ORSHD.TOKUYAKUTEN_CD_SMGP) AS SMGP_TOKUYAKUTEN_CD_VIEW \n"); // 請求明細グループCD_特約店CD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_SMGP_DEPO_CD) AS SMGP_DEPO_CD_VIEW \n"); // 請求明細グループCD_デポCD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_SMGP_NIJITEN_CD) AS SMGP_NIJITEN_CD_VIEW \n"); // 請求明細グループCD_二次店CD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_SMGP_SANJITEN_CD) AS SMGP_SANJITEN_CD_VIEW \n"); // 請求明細グループCD_三次店CD
		sSql.append(" ,SMGP1JI.SEIKYUSAKI_NM01 AS SEIKYUSAKI_NM01_VIEW \n"); // 請求先名称(正) 1/2
		sSql.append(" ,F_RTRIM(SMGP1JI.SEIKYUSAKI_NM02) AS SEIKYUSAKI_NM02_VIEW \n"); // 請求先名称(正) 2/2

		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_1_TX) AS BIKOU_1_TX_VIEW \n"); // 備考1
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_2_TX) AS BIKOU_2_TX_VIEW \n"); // 備考2
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_3_TX) AS BIKOU_3_TX_VIEW \n"); // 備考3
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_4_TX) AS BIKOU_4_TX_VIEW \n"); // 備考4
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_5_TX) AS BIKOU_5_TX_VIEW \n"); // 備考5

		// マスター上の運送店情報
		sSql.append(" ,F_RTRIM(ORS.UNSOTEN_CD_TUJYO) AS UNSOTEN_CD_TUJYO \n"); // 運送店CD（通常）
		sSql.append(" ,F_RTRIM(UNSTU.UNSOTEN_NM_PRINT) AS UNSOTEN_NM_TUJYO \n"); // 運送店名（通常）（略称）
		sSql.append(" ,F_RTRIM(ORS.UNSOTEN_CD_TOUJITU) AS UNSOTEN_CD_TOUJITU \n"); // 運送店CD（当日）
		sSql.append(" ,F_RTRIM(UNSTO.UNSOTEN_NM_PRINT) AS UNSOTEN_NM_TOUJITU \n"); // 運送店名（当日）（略称）
		sSql.append(" ,F_RTRIM(ORSHD.UNSOTEN_CD_HIKITORI) AS UNSOTEN_CD_HIKITORI \n"); // 運送店CD（引取）
		sSql.append(" ,F_RTRIM(UNSHI.UNSOTEN_NM_PRINT) AS UNSOTEN_NM_HIKITORI \n"); // 運送店名（引取）（略称）

		// 与信情報
		sSql.append(" ,SMGP1JI.YOSIN_CYUI_KBN AS YOSIN_CYUI_KBN \n"); // 与信要注意区分
		sSql.append(" ,SMGP1JI.YOSIN_GENDO_GAKU AS YOSIN_GENDO_GAKU \n"); // 与信限度額
		sSql.append(" ,SMGP1JI.URIKAKE_MINYUKIN_GAKU AS URIKAKE_MINYUKIN_GAKU \n"); // 売掛未入金額

		sSql.append("FROM T_JYUCYU_HD JCUHD \n");
		sSql.append("INNER JOIN T_JYUCYU_DT JCUDT ON (JCUHD.KAISYA_CD = JCUDT.KAISYA_CD AND JCUHD.JYUCYU_NO = JCUDT.JYUCYU_NO) \n");
		sSql.append("LEFT JOIN M_OROSISYOSAI_HD ORSHD ON (JCUHD.KAISYA_CD = ORSHD.KAISYA_CD AND JCUHD.TATESN_CD = ORSHD.TATESN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORS ON (ORSHD.KAISYA_CD = ORS.KAISYA_CD AND ORSHD.OROSITEN_CD_LAST = ORS.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_UNSOTEN UNS ON (JCUHD.KAISYA_CD = UNS.KAISYA_CD AND JCUHD.UNSOTEN_CD = UNS.UNSOTEN_CD) \n");
		sSql.append("LEFT JOIN M_SGYOSYA KSG ON (JCUHD.KAISYA_CD = KSG.KAISYA_CD AND JCUHD.KOUSIN_SGYOSYA_CD = KSG.SGYOSYA_CD) \n");
		sSql.append("LEFT JOIN M_SGYOSYA TSG ON (JCUHD.KAISYA_CD = TSG.KAISYA_CD AND JCUHD.TOROKU_SGYOSYA_CD = TSG.SGYOSYA_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORS1JI ON (JCUHD.KAISYA_CD = ORS1JI.KAISYA_CD AND JCUHD.TOKUYAKUTEN_CD = ORS1JI.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORSDEP ON (JCUHD.KAISYA_CD = ORSDEP.KAISYA_CD AND JCUHD.DEPO_CD = ORSDEP.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORS2JI ON (JCUHD.KAISYA_CD = ORS2JI.KAISYA_CD AND JCUHD.NIJITEN_CD = ORS2JI.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORS3JI ON (JCUHD.KAISYA_CD = ORS3JI.KAISYA_CD AND JCUHD.SANJITEN_CD = ORS3JI.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_UNSOTEN UNSTU ON (ORS.KAISYA_CD = UNSTU.KAISYA_CD AND ORS.UNSOTEN_CD_TUJYO = UNSTU.UNSOTEN_CD) \n");
		sSql.append("LEFT JOIN M_UNSOTEN UNSTO ON (ORS.KAISYA_CD = UNSTO.KAISYA_CD AND ORS.UNSOTEN_CD_TOUJITU = UNSTO.UNSOTEN_CD) \n");
		sSql.append("LEFT JOIN M_UNSOTEN UNSHI ON (ORSHD.KAISYA_CD = UNSHI.KAISYA_CD AND ORSHD.UNSOTEN_CD_HIKITORI = UNSHI.UNSOTEN_CD) \n");
		sSql.append("LEFT JOIN M_SYUHANTEN SYH ON (JCUHD.SYUHANTEN_CD = SYH.SYUHANTEN_CD) \n");
		sSql.append("LEFT JOIN M_TOKUYAKUTEN SMGP1JI ON (ORSHD.KAISYA_CD = SMGP1JI.KAISYA_CD AND ORSHD.TOKUYAKUTEN_CD_SMGP = SMGP1JI.TOKUYAKUTEN_CD) \n");

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" JCUHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 最終送荷先卸
		if (!PbsUtil.isEmpty(searchForm.getOrositenCdLast())) {
			sSql.append("AND ORSHD.OROSITEN_CD_LAST = ? \n");
			bindList.add(searchForm.getOrositenCdLast());
		}

		// 識別
		if (!PbsUtil.isEmpty(searchForm.getSyubetuCd())) {
			sSql.append("AND JCUHD.SYUBETU_CD = ? \n");
			bindList.add(searchForm.getSyubetuCd());
		}

		// 受注NO枝番が"0"のみ対象・・・受注センター用
		sSql.append("AND JCUHD.JYUCYU_NO LIKE ? \n");
		bindList.add(ComPopupUtil.getWildCardLeft("0"));

		// 受注NO指定（前方一致）
		if (!PbsUtil.isEmpty(searchForm.getJyucyuNo())) {
			sSql.append("AND JCUHD.JYUCYU_NO LIKE ? \n");
			bindList.add(ComPopupUtil.getwildCardRight(searchForm.getJyucyuNo()));
		}

		// 出荷日
		if (!PbsUtil.isEmpty(searchForm.getSyukaDt())) {
			sSql.append("AND JCUHD.SYUKA_DT = ? \n");
			bindList.add(searchForm.getSyukaDt());
		}

		// 運送店
		if (!PbsUtil.isEmpty(searchForm.getUnsotenCd())) {
			sSql.append("AND JCUHD.UNSOTEN_CD = ? \n");
			bindList.add(searchForm.getUnsotenCd());
		}

		// 商品
		if (!PbsUtil.isEmpty(searchForm.getShohinCd())) {
			sSql.append("AND JCUDT.SHOHIN_CD = ? \n");
			bindList.add(searchForm.getShohinCd());
		}

		// 都道府県
		if (!PbsUtil.isEmpty(searchForm.getTodofuknCd())) {
			sSql.append("AND JCUHD.JIS_CD LIKE ? \n");
			bindList.add(ComPopupUtil.getwildCardRight(searchForm.getTodofuknCd()));
		}

		sSql.append("ORDER BY \n");
		sSql.append(" JCUHD.SYUKA_DT DESC \n"); // 出荷日

		return sSql.toString();
	}


	/**
	 * 受注入力（受注データ／ヘッダー部）リストを取得する
	 *
	 * @param jyucyuNo 受注NO
	 * @return JuchuJuchuDataInList 検索結果
	 */
	public JuchuJuchuDataInList getListJuchuJuchuDataIn(String jyucyuNo) {
		category__.info("受注入力（受注データ／ヘッダー部）リスト検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("受注入力（受注データ／ヘッダー部）リスト >> 検索処理 >> SQL文生成");
		String searchSql = getJuchuDataInListSearchSql(jyucyuNo, bindList);

		category__.info("受注入力（受注データ／ヘッダー部）リスト >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("受注入力（受注データ／ヘッダー部）リスト >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("受注入力（受注データ／ヘッダー部）リスト検索処理 終了");

		// 検索結果を変換
		JuchuJuchuDataInList searchedList = new JuchuJuchuDataInList();
		if (records_ != null) {
			searchedList = new JuchuJuchuDataInList(records_);
		}

		return searchedList;
	}

	/**
	 * 受注NOから受注入力（受注データ／ヘッダー部）リストを取得するSQL
	 *
	 * @param jyucyuNo
	 * @param bindList
	 * @return sSql
	 */
	private String getJuchuDataInListSearchSql(String jyucyuNo, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT \n");
		PbsSQL.setCommonColumns(sSql, "JCUHD"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
		sSql.append("  JCUHD.RIYOU_KBN AS RIYOU_KBN \n"); // 利用区分
		sSql.append(" ,JCUHD.KAISYA_CD AS KAISYA_CD \n"); // 会社CD
		sSql.append(" ,JCUHD.JYUCYU_NO AS JYUCYU_NO \n"); // 受注NO
		sSql.append(" ,JCUHD.SYUKA_KBN AS SYUKA_KBN \n"); // 出荷先区分
		sSql.append(" ,JCUHD.SYUBETU_CD AS SYUBETU_CD \n"); // データ種別CD
		sSql.append(" ,JCUHD.KURA_CD AS KURA_CD \n"); // 蔵CD
		sSql.append(" ,JCUHD.UNCHIN_KBN AS UNCHIN_KBN \n"); // 運賃区分
		sSql.append(" ,JCUHD.UNSOTEN_CD AS UNSOTEN_CD \n"); // 運送店CD
		sSql.append(" ,JCUHD.SYUKA_DT AS SYUKA_DT \n"); // 出荷日(売上伝票発行予定日)
		sSql.append(" ,JCUHD.MINASI_DT AS MINASI_DT \n"); // ミナシ日付
		sSql.append(" ,F_RTRIM(JCUHD.CHACUNI_YOTEI_DT) AS CHACUNI_YOTEI_DT \n"); // 着荷予定日
		sSql.append(" ,F_RTRIM(JCUHD.NIUKE_TIME_KBN) AS NIUKE_TIME_KBN \n"); // 荷受時間区分
		sSql.append(" ,F_RTRIM(JCUHD.NIUKE_BIGIN_TIME) AS NIUKE_BIGIN_TIME \n"); // 荷受時間_開始
		sSql.append(" ,F_RTRIM(JCUHD.NIUKE_END_TIME) AS NIUKE_END_TIME \n"); // 荷受時間_終了
		sSql.append(" ,F_RTRIM(JCUHD.SENPO_HACYU_NO) AS SENPO_HACYU_NO \n"); // 先方発注NO
		sSql.append(" ,F_RTRIM(JCUHD.SDN_HDERR_KBN) AS SDN_HDERR_KBN \n"); // SDN受注ヘッダーエラー区分
		sSql.append(" ,JCUHD.TATESN_CD AS ORS_TATESN_CD \n"); // 縦線CD
		sSql.append(" ,JCUHD.TANTOSYA_CD AS TANTOSYA_CD \n"); // 担当者CD
		sSql.append(" ,JCUHD.TOKUYAKUTEN_CD AS TOKUYAKUTEN_CD \n"); // 特約店CD
		sSql.append(" ,JCUHD.DEPO_CD AS DEPO_CD \n"); // デポCD
		sSql.append(" ,F_RTRIM(JCUHD.NIJITEN_CD) AS NIJITEN_CD \n"); // 二次店CD
		sSql.append(" ,F_RTRIM(JCUHD.SANJITEN_CD) AS SANJITEN_CD \n"); // 三次店CD
		sSql.append(" ,F_RTRIM(JCUHD.SYUHANTEN_CD) AS SYUHANTEN_CD \n"); // 酒販店（統一）CD
		sSql.append(" ,JCUHD.SYUKA_SAKI_COUNTRY_CD AS SYUKA_SAKI_COUNTRY_CD \n"); // 出荷先国CD
		sSql.append(" ,JCUHD.JIS_CD AS JIS_CD \n"); // JISCD
		sSql.append(" ,JCUHD.UNCHIN_CNV_TANKA AS UNCHIN_CNV_TANKA \n"); // 引取運賃換算単価
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN1) AS TEKIYO_KBN1 \n"); // 摘要区分 (01)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM1) AS TEKIYO_NM1 \n"); // 摘要内容 (01)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN2) AS TEKIYO_KBN2 \n"); // 摘要区分 (02)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM2) AS TEKIYO_NM2 \n"); // 摘要内容 (02)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN3) AS TEKIYO_KBN3 \n"); // 摘要区分 (03)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM3) AS TEKIYO_NM3 \n"); // 摘要内容 (03)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN4) AS TEKIYO_KBN4 \n"); // 摘要区分 (04)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM4) AS TEKIYO_NM4 \n"); // 摘要内容 (04)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN5) AS TEKIYO_KBN5 \n"); // 摘要区分 (05)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM5) AS TEKIYO_NM5 \n"); // 摘要内容 (05)
		sSql.append(" ,JCUHD.ATUKAI_KBN AS ATUKAI_KBN \n"); // 扱い区分
		sSql.append(" ,JCUHD.LOW_HAISO_LOT AS LOW_HAISO_LOT \n"); // 最低配送ロット数
		sSql.append(" ,F_RTRIM(JCUHD.SYUKA_SYONIN_NO) AS SYUKA_SYONIN_NO \n"); // 小ロット出荷承認申請NO
		sSql.append(" ,JCUHD.SYUKA_SURYO_BOX AS SYUKA_SURYO_BOX \n"); // 出荷数量計_箱数
		sSql.append(" ,JCUHD.SYUKA_SURYO_SET AS SYUKA_SURYO_SET \n"); // 出荷数量計_セット数
		sSql.append(" ,JCUHD.SYUKA_KINGAKU_TOT AS SYUKA_KINGAKU_TOT \n"); // 出荷金額計
		sSql.append(" ,JCUHD.JYURYO_TOT AS JYURYO_TOT \n"); // 重量計(KG)
		sSql.append(" ,JCUHD.SYUKA_YOURYO_TOT AS SYUKA_YOURYO_TOT \n"); // 容量計（L）_出荷容量計
		sSql.append(" ,JCUHD.REBATE1_YOURYO_TOT AS REBATE1_YOURYO_TOT \n"); // 容量計（L）_リベートⅠ類対象容量計
		sSql.append(" ,JCUHD.REBATE2_YOURYO_TOT AS REBATE2_YOURYO_TOT \n"); // 容量計（L）_リベートⅡ類対象容量計
		sSql.append(" ,JCUHD.REBATE3_YOURYO_TOT AS REBATE3_YOURYO_TOT \n"); // 容量計（L）_リベートⅢ類対象容量計
		sSql.append(" ,JCUHD.REBATE4_YOURYO_TOT AS REBATE4_YOURYO_TOT \n"); // 容量計（L）_リベートⅣ類対象容量計
		sSql.append(" ,JCUHD.REBATE5_YOURYO_TOT AS REBATE5_YOURYO_TOT \n"); // 容量計（L）_リベートⅤ類対象容量計
		sSql.append(" ,JCUHD.REBATEO_YOURYO_TOT AS REBATEO_YOURYO_TOT \n"); // 容量計（L）_リベート対象外容量計
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_NO) AS TUMIDEN_NO \n"); // 積荷伝票NO
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_SM_KBN) AS TUMIDEN_SM_KBN \n"); // 積荷累積対象区分
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_HAKO_DT) AS TUMIDEN_HAKO_DT \n"); // 積荷伝票発行日
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_HAKO_TM) AS TUMIDEN_HAKO_TM \n"); // 積荷伝票発行時刻
		sSql.append(" ,JCUHD.TUMIDEN_HAKO_CNT AS TUMIDEN_HAKO_CNT \n"); // 積荷伝票発行回数
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_HAKOSYA) AS TUMIDEN_HAKOSYA \n"); // 積荷伝票発行者ID

		sSql.append(" ,ORS1JI.TEN_NM1_JISYA AS TOKUYAKUTEN_NM \n"); // 特約店名（略称）
		sSql.append(" ,ORSDEP.TEN_NM1_JISYA AS DEPO_NM \n"); // デポ店名（略称）
		sSql.append(" ,F_RTRIM(ORS2JI.TEN_NM1_JISYA) AS NIJITEN_NM \n"); // 二次店名（略称）
		sSql.append(" ,F_RTRIM(ORS3JI.TEN_NM1_JISYA) AS SANJITEN_NM \n"); // 三次店名（略称）
		sSql.append(" ,ORS.TEN_NM1_JISYA AS OROSITEN_NM_LAST \n"); // 最終送荷先卸名（略称）
		sSql.append(" ,F_RTRIM(SYH.TEN_NM_YAGO) AS SYUHANTEN_NM \n"); // 酒販店名
		sSql.append(" ,JCUHD.DELIVERY_KBN AS DELIVERY_KBN \n"); // 倉入・直送区分
		sSql.append(" ,SMGP1JI.SEIKYUSAKI_NM01 AS SEIKYUSAKI_NM01 \n"); // 請求先名称(正) 1/2
		sSql.append(" ,F_RTRIM(SMGP1JI.SEIKYUSAKI_NM02) AS SEIKYUSAKI_NM02 \n"); // 請求先名称(正) 2/2
		sSql.append(" ,F_RTRIM(ORSHD.TOKUYAKUTEN_CD_SMGP) AS SMGP_TOKUYAKUTEN_CD \n"); // 請求明細グループCD_特約店CD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_SMGP_DEPO_CD) AS SMGP_DEPO_CD \n"); // 請求明細グループCD_デポCD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_SMGP_NIJITEN_CD) AS SMGP_NIJITEN_CD \n"); // 請求明細グループCD_二次店CD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_SMGP_SANJITEN_CD) AS SMGP_SANJITEN_CD \n"); // 請求明細グループCD_三次店CD

		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_1_TX) AS BIKOU_1_TX \n"); // 備考1
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_2_TX) AS BIKOU_2_TX \n"); // 備考2
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_3_TX) AS BIKOU_3_TX \n"); // 備考3
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_4_TX) AS BIKOU_4_TX \n"); // 備考4
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_5_TX) AS BIKOU_5_TX \n"); // 備考5

		// 表示用
		sSql.append(" ,JCUHD.RIYOU_KBN AS RIYOU_KBN_VIEW \n"); // 利用区分
		sSql.append(" ,JCUHD.KAISYA_CD AS KAISYA_CD_VIEW \n"); // 会社CD
		sSql.append(" ,JCUHD.JYUCYU_NO AS JYUCYU_NO_VIEW \n"); // 受注NO
		sSql.append(" ,JCUHD.SYUKA_KBN AS SYUKA_KBN_VIEW \n"); // 出荷先区分
		sSql.append(" ,JCUHD.SYUBETU_CD AS SYUBETU_CD_VIEW \n"); // データ種別CD
		sSql.append(" ,JCUHD.KURA_CD AS KURA_CD_VIEW \n"); // 蔵CD
		sSql.append(" ,JCUHD.UNCHIN_KBN AS UNCHIN_KBN_VIEW \n"); // 運賃区分
		sSql.append(" ,JCUHD.UNSOTEN_CD AS UNSOTEN_CD_VIEW \n"); // 運送店CD
		sSql.append(" ,JCUHD.SYUKA_DT AS SYUKA_DT_VIEW \n"); // 出荷日(売上伝票発行予定日)
		sSql.append(" ,JCUHD.MINASI_DT AS MINASI_DT_VIEW \n"); // ミナシ日付
		sSql.append(" ,F_RTRIM(JCUHD.CHACUNI_YOTEI_DT) AS CHACUNI_YOTEI_DT_VIEW \n"); // 着荷予定日
		sSql.append(" ,F_RTRIM(JCUHD.NIUKE_TIME_KBN) AS NIUKE_TIME_KBN_VIEW \n"); // 荷受時間区分
		sSql.append(" ,F_RTRIM(JCUHD.NIUKE_BIGIN_TIME) AS NIUKE_BIGIN_TIME_VIEW \n"); // 荷受時間_開始
		sSql.append(" ,F_RTRIM(JCUHD.NIUKE_END_TIME) AS NIUKE_END_TIME_VIEW \n"); // 荷受時間_終了
		sSql.append(" ,F_RTRIM(JCUHD.SENPO_HACYU_NO) AS SENPO_HACYU_NO_VIEW \n"); // 先方発注NO
		sSql.append(" ,F_RTRIM(JCUHD.SDN_HDERR_KBN) AS SDN_HDERR_KBN_VIEW \n"); // SDN受注ヘッダーエラー区分
		sSql.append(" ,JCUHD.TATESN_CD AS ORS_TATESN_CD_VIEW \n"); // 縦線CD
		sSql.append(" ,JCUHD.TANTOSYA_CD AS TANTOSYA_CD_VIEW \n"); // 担当者CD
		sSql.append(" ,JCUHD.TOKUYAKUTEN_CD AS TOKUYAKUTEN_CD_VIEW \n"); // 特約店CD
		sSql.append(" ,JCUHD.DEPO_CD AS DEPO_CD_VIEW \n"); // デポCD
		sSql.append(" ,F_RTRIM(JCUHD.NIJITEN_CD) AS NIJITEN_CD_VIEW \n"); // 二次店CD
		sSql.append(" ,F_RTRIM(JCUHD.SANJITEN_CD) AS SANJITEN_CD_VIEW \n"); // 三次店CD
		sSql.append(" ,F_RTRIM(JCUHD.SYUHANTEN_CD) AS SYUHANTEN_CD_VIEW \n"); // 酒販店（統一）CD
		sSql.append(" ,JCUHD.SYUKA_SAKI_COUNTRY_CD AS SYUKA_SAKI_COUNTRY_CD_VIEW \n"); // 出荷先国CD
		sSql.append(" ,JCUHD.JIS_CD AS JIS_CD_VIEW \n"); // JISCD
		sSql.append(" ,JCUHD.UNCHIN_CNV_TANKA AS UNCHIN_CNV_TANKA_VIEW \n"); // 引取運賃換算単価
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN1) AS TEKIYO_KBN1_VIEW \n"); // 摘要区分 (01)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM1) AS TEKIYO_NM1_VIEW \n"); // 摘要内容 (01)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN2) AS TEKIYO_KBN2_VIEW \n"); // 摘要区分 (02)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM2) AS TEKIYO_NM2_VIEW \n"); // 摘要内容 (02)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN3) AS TEKIYO_KBN3_VIEW \n"); // 摘要区分 (03)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM3) AS TEKIYO_NM3_VIEW \n"); // 摘要内容 (03)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN4) AS TEKIYO_KBN4_VIEW \n"); // 摘要区分 (04)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM4) AS TEKIYO_NM4_VIEW \n"); // 摘要内容 (04)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_KBN5) AS TEKIYO_KBN5_VIEW \n"); // 摘要区分 (05)
		sSql.append(" ,F_RTRIM(JCUHD.TEKIYO_NM5) AS TEKIYO_NM5_VIEW \n"); // 摘要内容 (05)
		sSql.append(" ,JCUHD.ATUKAI_KBN AS ATUKAI_KBN_VIEW \n"); // 扱い区分
		sSql.append(" ,JCUHD.LOW_HAISO_LOT AS LOW_HAISO_LOT_VIEW \n"); // 最低配送ロット数
		sSql.append(" ,F_RTRIM(JCUHD.SYUKA_SYONIN_NO) AS SYUKA_SYONIN_NO_VIEW \n"); // 小ロット出荷承認申請NO
		sSql.append(" ,JCUHD.SYUKA_SURYO_BOX AS SYUKA_SURYO_BOX_VIEW \n"); // 出荷数量計_箱数
		sSql.append(" ,JCUHD.SYUKA_SURYO_SET AS SYUKA_SURYO_SET_VIEW \n"); // 出荷数量計_セット数
		sSql.append(" ,JCUHD.SYUKA_KINGAKU_TOT AS SYUKA_KINGAKU_TOT_VIEW \n"); // 出荷金額計
		sSql.append(" ,JCUHD.JYURYO_TOT AS JYURYO_TOT_VIEW \n"); // 重量計(KG)
		sSql.append(" ,JCUHD.SYUKA_YOURYO_TOT AS SYUKA_YOURYO_TOT_VIEW \n"); // 容量計（L）_出荷容量計
		sSql.append(" ,JCUHD.REBATE1_YOURYO_TOT AS REBATE1_YOURYO_TOT_VIEW \n"); // 容量計（L）_リベートⅠ類対象容量計
		sSql.append(" ,JCUHD.REBATE2_YOURYO_TOT AS REBATE2_YOURYO_TOT_VIEW \n"); // 容量計（L）_リベートⅡ類対象容量計
		sSql.append(" ,JCUHD.REBATE3_YOURYO_TOT AS REBATE3_YOURYO_TOT_VIEW \n"); // 容量計（L）_リベートⅢ類対象容量計
		sSql.append(" ,JCUHD.REBATE4_YOURYO_TOT AS REBATE4_YOURYO_TOT_VIEW \n"); // 容量計（L）_リベートⅣ類対象容量計
		sSql.append(" ,JCUHD.REBATE5_YOURYO_TOT AS REBATE5_YOURYO_TOT_VIEW \n"); // 容量計（L）_リベートⅤ類対象容量計
		sSql.append(" ,JCUHD.REBATEO_YOURYO_TOT AS REBATEO_YOURYO_TOT_VIEW \n"); // 容量計（L）_リベート対象外容量計
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_NO) AS TUMIDEN_NO_VIEW \n"); // 積荷伝票NO
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_SM_KBN) AS TUMIDEN_SM_KBN_VIEW \n"); // 積荷累積対象区分
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_HAKO_DT) AS TUMIDEN_HAKO_DT_VIEW \n"); // 積荷伝票発行日
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_HAKO_TM) AS TUMIDEN_HAKO_TM_VIEW \n"); // 積荷伝票発行時刻
		sSql.append(" ,JCUHD.TUMIDEN_HAKO_CNT AS TUMIDEN_HAKO_CNT_VIEW \n"); // 積荷伝票発行回数
		sSql.append(" ,F_RTRIM(JCUHD.TUMIDEN_HAKOSYA) AS TUMIDEN_HAKOSYA_VIEW \n"); // 積荷伝票発行者ID

		sSql.append(" ,ORS1JI.TEN_NM1_JISYA AS TOKUYAKUTEN_NM_VIEW \n"); // 特約店名（略称）
		sSql.append(" ,ORSDEP.TEN_NM1_JISYA AS DEPO_NM_VIEW \n"); // デポ店名（略称）
		sSql.append(" ,F_RTRIM(ORS2JI.TEN_NM1_JISYA) AS NIJITEN_NM_VIEW \n"); // 二次店名（略称）
		sSql.append(" ,F_RTRIM(ORS3JI.TEN_NM1_JISYA) AS SANJITEN_NM_VIEW \n"); // 三次店名（略称）
		sSql.append(" ,ORS.TEN_NM1_JISYA AS OROSITEN_NM_LAST_VIEW \n"); // 最終送荷先卸名（略称）
		sSql.append(" ,F_RTRIM(SYH.TEN_NM_YAGO) AS SYUHANTEN_NM_VIEW \n"); // 酒販店名
		sSql.append(" ,JCUHD.DELIVERY_KBN AS DELIVERY_KBN_VIEW \n"); // 倉入・直送区分
		sSql.append(" ,SMGP1JI.SEIKYUSAKI_NM01 AS SEIKYUSAKI_NM01_VIEW \n"); // 請求先名称(正) 1/2
		sSql.append(" ,F_RTRIM(SMGP1JI.SEIKYUSAKI_NM02) AS SEIKYUSAKI_NM02_VIEW \n"); // 請求先名称(正) 2/2
		sSql.append(" ,F_RTRIM(ORSHD.TOKUYAKUTEN_CD_SMGP) AS SMGP_TOKUYAKUTEN_CD_VIEW \n"); // 請求明細グループCD_特約店CD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_SMGP_DEPO_CD) AS SMGP_DEPO_CD_VIEW \n"); // 請求明細グループCD_デポCD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_SMGP_NIJITEN_CD) AS SMGP_NIJITEN_CD_VIEW \n"); // 請求明細グループCD_二次店CD
		sSql.append(" ,F_RTRIM(ORSHD.OROSITEN_CD_SMGP_SANJITEN_CD) AS SMGP_SANJITEN_CD_VIEW \n"); // 請求明細グループCD_三次店CD

		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_1_TX) AS BIKOU_1_TX_VIEW \n"); // 備考1
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_2_TX) AS BIKOU_2_TX_VIEW \n"); // 備考2
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_3_TX) AS BIKOU_3_TX_VIEW \n"); // 備考3
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_4_TX) AS BIKOU_4_TX_VIEW \n"); // 備考4
		sSql.append(" ,F_RTRIM(JCUHD.BIKOU_5_TX) AS BIKOU_5_TX_VIEW \n"); // 備考5

		sSql.append("FROM T_JYUCYU_HD JCUHD \n");
		sSql.append("LEFT JOIN M_OROSISYOSAI_HD ORSHD ON (JCUHD.KAISYA_CD = ORSHD.KAISYA_CD AND JCUHD.TATESN_CD = ORSHD.TATESN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORS ON (ORSHD.KAISYA_CD = ORS.KAISYA_CD AND ORSHD.OROSITEN_CD_LAST = ORS.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORS1JI ON (JCUHD.KAISYA_CD = ORS1JI.KAISYA_CD AND JCUHD.TOKUYAKUTEN_CD = ORS1JI.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORSDEP ON (JCUHD.KAISYA_CD = ORSDEP.KAISYA_CD AND JCUHD.DEPO_CD = ORSDEP.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORS2JI ON (JCUHD.KAISYA_CD = ORS2JI.KAISYA_CD AND JCUHD.NIJITEN_CD = ORS2JI.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORS3JI ON (JCUHD.KAISYA_CD = ORS3JI.KAISYA_CD AND JCUHD.SANJITEN_CD = ORS3JI.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_SYUHANTEN SYH ON (JCUHD.SYUHANTEN_CD = SYH.SYUHANTEN_CD) \n");
		sSql.append("LEFT JOIN M_TOKUYAKUTEN SMGP1JI ON (ORSHD.KAISYA_CD = SMGP1JI.KAISYA_CD AND ORSHD.TOKUYAKUTEN_CD_SMGP = SMGP1JI.TOKUYAKUTEN_CD) \n");

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" JCUHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 受注NO
		sSql.append("AND JCUHD.JYUCYU_NO = ? \n");
		bindList.add(jyucyuNo);

		return sSql.toString();
	}


	/**
	 * 受注入力（受注データ／ディテール部）リストを取得する
	 *
	 * @param jyucyuNo 受注NO
	 * @return JuchuJuchuDataInDtList 検索結果
	 */
	public JuchuJuchuDataInDtList getListJuchuJuchuDataInDt(String jyucyuNo) {
		category__.info("受注入力（受注データ／ディテール部）リスト検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("受注入力（受注データ／ディテール部）リスト >> 検索処理 >> SQL文生成");
		String searchSql = getJuchuDataInDtListSearchSql(jyucyuNo, bindList);

		category__.info("受注入力（受注データ／ディテール部）リスト >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("受注入力（受注データ／ディテール部）リスト >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("受注入力（受注データ／ディテール部）リスト検索処理 終了");

		// 検索結果を変換
		JuchuJuchuDataInDtList searchedList = new JuchuJuchuDataInDtList();
		if (records_ != null) {
			searchedList = new JuchuJuchuDataInDtList(records_);
		}

		return searchedList;
	}


	/**
	 * 受注NOから受注入力（受注データ／ディテール部）リストを取得するSQL
	 *
	 * @param jyucyuNo
	 * @param bindList
	 * @return sSql
	 */
	private String getJuchuDataInDtListSearchSql(String jyucyuNo, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT \n");
		PbsSQL.setCommonColumns(sSql, "JCUDT"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
		sSql.append("  JCUDT.RIYOU_KBN AS RIYOU_KBN \n"); // 利用区分
		sSql.append(" ,JCUDT.KAISYA_CD AS KAISYA_CD \n"); // 会社CD
		sSql.append(" ,JCUDT.JYUCYU_NO AS JYUCYU_NO \n"); // 受注NO
		sSql.append(" ,JCUDT.INPUT_LINE_NO AS INPUT_LINE_NO \n"); // 受注行NO
		sSql.append(" ,JCUDT.KURA_CD AS KURA_CD \n"); // 蔵CD
		sSql.append(" ,JCUDT.SOUKO_CD AS SOUKO_CD \n"); // 倉庫CD
		sSql.append(" ,JCUDT.KTKSY_CD AS KTKSY_CD \n"); // 寄託者CD
		sSql.append(" ,JCUDT.SHOHIN_CD AS SHOHIN_CD \n"); // 商品CD
		sSql.append(" ,F_RTRIM(JCUDT.SEIHIN_CD) AS SEIHIN_CD \n"); // 製品CD
		sSql.append(" ,F_RTRIM(JCUDT.SIIRESAKI_CD) AS SIIRESAKI_CD \n"); // 仕入先CD
		sSql.append(" ,F_RTRIM(JCUDT.SIIREHIN_CD) AS SIIREHIN_CD \n"); // 仕入品CD
		sSql.append(" ,F_RTRIM(JCUDT.SEIHIN_DT) AS SEIHIN_DT \n"); // 製品日付
		sSql.append(" ,JCUDT.TUMIDEN_LINE_NO AS TUMIDEN_LINE_NO \n"); // 積荷伝票用ラインNO
		sSql.append(" ,SYO.SHOHIN_NM_URIDEN AS SHNNM_REPORT1 \n"); // 商品名称_自社各帳票用(1)
		sSql.append(" ,SYO.YOUKI_KIGO_NM1 AS YOUKI_NM_REPORT \n"); // 容器名称_自社各帳票用
		sSql.append(" ,JCUDT.CASE_IRISU AS CASE_IRISU \n"); // ケース入数
		sSql.append(" ,JCUDT.BARA_YOURYO AS BARA_YOURYO \n"); // バラ容量(L)
		sSql.append(" ,JCUDT.WEIGHT_CASE AS WEIGHT_CASE \n"); // 重量(KG)_ケース
		sSql.append(" ,JCUDT.WEIGHT_BARA AS WEIGHT_BARA \n"); // 重量(KG)_バラ
		sSql.append(" ,JCUDT.UNCHIN_KAKERITU AS UNCHIN_KAKERITU \n"); // 運賃掛率(%)
		sSql.append(" ,JCUDT.HANBAI_TANKA AS HANBAI_TANKA \n"); // 販売単価
		sSql.append(" ,F_RTRIM(JCUDT.HOSOKURAN) AS HOSOKURAN \n"); // 補足欄
		sSql.append(" ,JCUDT.SYUKA_SU_CASE AS SYUKA_SU_CASE \n"); // 出荷数量_箱数
		sSql.append(" ,JCUDT.SYUKA_SU_BARA AS SYUKA_SU_BARA \n"); // 出荷数量_セット数
		sSql.append(" ,JCUDT.SYUKA_ALL_BARA AS SYUKA_ALL_BARA \n"); // 出荷数量_換算総セット数
		sSql.append(" ,JCUDT.SYUKA_ALL_WEIGTH AS SYUKA_ALL_WEIGTH \n"); // 出荷重量（KG)
		sSql.append(" ,JCUDT.SYUKA_HANBAI_KINGAKU AS SYUKA_HANBAI_KINGAKU \n"); // 販売額
		sSql.append(" ,JCUDT.SYUKA_TAIO_KBN AS SYUKA_TAIO_KBN \n"); // 出荷対応区分
		sSql.append(" ,JCUDT.ATUKAI_KBN AS ATUKAI_KBN \n"); // 扱い区分
		sSql.append(" ,JCUDT.BUPIN_KBN AS BUPIN_KBN \n"); // 物品区分
		sSql.append(" ,JCUDT.HTANKA_CHG_FLG AS HTANKA_CHG_FLG \n"); // 販売単価変更フラグ
		sSql.append(" ,JCUDT.SYUKA_SOUYOURYO AS SYUKA_SOUYOURYO \n"); // 容量（L）_出荷総容量
		sSql.append(" ,JCUDT.REBATE1_SOUYOURYO AS REBATE1_SOUYOURYO \n"); // 容量（L）_リベートⅠ類対象総容量
		sSql.append(" ,JCUDT.REBATE2_SOUYOURYO AS REBATE2_SOUYOURYO \n"); // 容量（L）_リベートⅡ類対象総容量
		sSql.append(" ,JCUDT.REBATE3_SOUYOURYO AS REBATE3_SOUYOURYO \n"); // 容量（L）_リベートⅢ類対象総容量
		sSql.append(" ,JCUDT.REBATE4_SOUYOURYO AS REBATE4_SOUYOURYO \n"); // 容量（L）_リベートⅣ類対象総容量
		sSql.append(" ,JCUDT.REBATE5_SOUYOURYO AS REBATE5_SOUYOURYO \n"); // 容量（L）_リベートⅤ類対象総容量
		sSql.append(" ,JCUDT.REBATEO_SOUYOURYO AS REBATEO_SOUYOURYO \n"); // 容量（L）_リベート対象外総容量
		sSql.append(" ,F_RTRIM(JCUDT.PB_TOKUCYU_KBN) AS PB_TOKUCYU_KBN \n"); // 特注指示区分
		sSql.append(" ,F_RTRIM(JCUDT.PB_TOKUCYU) AS PB_TOKUCYU \n"); // PB OR 特注指示内容
		sSql.append(" ,F_RTRIM(JCUDT.HANBAI_BUMON_CD) AS HANBAI_BUMON_CD \n"); // 販売部門CD
		sSql.append(" ,F_RTRIM(JCUDT.HANBAI_BUMON_RNM) AS HANBAI_BUMON_RNM \n"); // 販売部門名（略式）
		sSql.append(" ,F_RTRIM(JCUDT.HANBAI_SYUBETU_CD) AS HANBAI_SYUBETU_CD \n"); // 販売種別CD
		sSql.append(" ,F_RTRIM(JCUDT.HANBAI_SYUBETU_RNM) AS HANBAI_SYUBETU_RNM \n"); // 販売種別名（略式）
		sSql.append(" ,F_RTRIM(JCUDT.HANBAI_BUNRUI_CD) AS HANBAI_BUNRUI_CD \n"); // 販売分類CD
		sSql.append(" ,F_RTRIM(JCUDT.HANBAI_BUNRUI_RNM) AS HANBAI_BUNRUI_RNM \n"); // 販売分類名（略式）
		sSql.append(" ,F_RTRIM(JCUDT.SDN_DTERR_KBN) AS SDN_DTERR_KBN \n"); // SDN受注ディテールエラー区分
		sSql.append(" ,F_RTRIM(JCUDT.EDI_HAISOUG_SEND_KB) AS EDI_HAISOUG_SEND_KB \n"); // EDI配送依頼(集約)送信区分
		sSql.append(" ,SYO.INDEMNITY_KBN AS INDEMNITY_KBN \n"); // 補償金徴収対象区分
		sSql.append(" ,SYO.SERVICE_KBN AS SERVICE_KBN \n"); // 引上手数料対象区分

		sSql.append(" ,F_RTRIM(JCUDT.BIKOU_1_TX) AS BIKOU_1_TX \n"); // 備考1
		sSql.append(" ,F_RTRIM(JCUDT.BIKOU_2_TX) AS BIKOU_2_TX \n"); // 備考2
		sSql.append(" ,F_RTRIM(JCUDT.BIKOU_3_TX) AS BIKOU_3_TX \n"); // 備考3
		sSql.append(" ,F_RTRIM(JCUDT.BIKOU_4_TX) AS BIKOU_4_TX \n"); // 備考4
		sSql.append(" ,F_RTRIM(JCUDT.BIKOU_5_TX) AS BIKOU_5_TX \n"); // 備考5

		// 表示用
		sSql.append(" ,JCUDT.RIYOU_KBN AS RIYOU_KBN_VIEW \n"); // 利用区分
		sSql.append(" ,JCUDT.KAISYA_CD AS KAISYA_CD_VIEW \n"); // 会社CD
		sSql.append(" ,JCUDT.JYUCYU_NO AS JYUCYU_NO_VIEW \n"); // 受注NO
		sSql.append(" ,JCUDT.INPUT_LINE_NO AS INPUT_LINE_NO_VIEW \n"); // 受注行NO
		sSql.append(" ,JCUDT.KURA_CD AS KURA_CD_VIEW \n"); // 蔵CD
		sSql.append(" ,JCUDT.SOUKO_CD AS SOUKO_CD_VIEW \n"); // 倉庫CD
		sSql.append(" ,JCUDT.KTKSY_CD AS KTKSY_CD_VIEW \n"); // 寄託者CD
		sSql.append(" ,JCUDT.SHOHIN_CD AS SHOHIN_CD_VIEW \n"); // 商品CD
		sSql.append(" ,F_RTRIM(JCUDT.SEIHIN_CD) AS SEIHIN_CD_VIEW \n"); // 製品CD
		sSql.append(" ,F_RTRIM(JCUDT.SIIRESAKI_CD) AS SIIRESAKI_CD_VIEW \n"); // 仕入先CD
		sSql.append(" ,F_RTRIM(JCUDT.SIIREHIN_CD) AS SIIREHIN_CD_VIEW \n"); // 仕入品CD
		sSql.append(" ,F_RTRIM(JCUDT.SEIHIN_DT) AS SEIHIN_DT_VIEW \n"); // 製品日付
		sSql.append(" ,JCUDT.TUMIDEN_LINE_NO AS TUMIDEN_LINE_NO_VIEW \n"); // 積荷伝票用ラインNO
		sSql.append(" ,SYO.SHOHIN_NM_URIDEN AS SHNNM_REPORT1_VIEW \n"); // 商品名称_自社各帳票用(1)
		sSql.append(" ,SYO.YOUKI_KIGO_NM1 AS YOUKI_NM_REPORT_VIEW \n"); // 容器名称_自社各帳票用
		sSql.append(" ,JCUDT.CASE_IRISU AS CASE_IRISU_VIEW \n"); // ケース入数
		sSql.append(" ,JCUDT.BARA_YOURYO AS BARA_YOURYO_VIEW \n"); // バラ容量(L)
		sSql.append(" ,JCUDT.WEIGHT_CASE AS WEIGHT_CASE_VIEW \n"); // 重量(KG)_ケース
		sSql.append(" ,JCUDT.WEIGHT_BARA AS WEIGHT_BARA_VIEW \n"); // 重量(KG)_バラ
		sSql.append(" ,JCUDT.UNCHIN_KAKERITU AS UNCHIN_KAKERITU_VIEW \n"); // 運賃掛率(%)
		sSql.append(" ,JCUDT.HANBAI_TANKA AS HANBAI_TANKA_VIEW \n"); // 販売単価
		sSql.append(" ,F_RTRIM(JCUDT.HOSOKURAN) AS HOSOKURAN_VIEW \n"); // 補足欄
		sSql.append(" ,JCUDT.SYUKA_SU_CASE AS SYUKA_SU_CASE_VIEW \n"); // 出荷数量_箱数
		sSql.append(" ,JCUDT.SYUKA_SU_BARA AS SYUKA_SU_BARA_VIEW \n"); // 出荷数量_セット数
		sSql.append(" ,JCUDT.SYUKA_ALL_BARA AS SYUKA_ALL_BARA_VIEW \n"); // 出荷数量_換算総セット数
		sSql.append(" ,JCUDT.SYUKA_ALL_WEIGTH AS SYUKA_ALL_WEIGTH_VIEW \n"); // 出荷重量（KG)
		sSql.append(" ,JCUDT.SYUKA_HANBAI_KINGAKU AS SYUKA_HANBAI_KINGAKU_VIEW \n"); // 販売額
		sSql.append(" ,JCUDT.SYUKA_TAIO_KBN AS SYUKA_TAIO_KBN_VIEW \n"); // 出荷対応区分
		sSql.append(" ,JCUDT.ATUKAI_KBN AS ATUKAI_KBN_VIEW \n"); // 扱い区分
		sSql.append(" ,JCUDT.BUPIN_KBN AS BUPIN_KBN_VIEW \n"); // 物品区分
		sSql.append(" ,JCUDT.HTANKA_CHG_FLG AS HTANKA_CHG_FLG_VIEW \n"); // 販売単価変更フラグ
		sSql.append(" ,JCUDT.SYUKA_SOUYOURYO AS SYUKA_SOUYOURYO_VIEW \n"); // 容量（L）_出荷総容量
		sSql.append(" ,JCUDT.REBATE1_SOUYOURYO AS REBATE1_SOUYOURYO_VIEW \n"); // 容量（L）_リベートⅠ類対象総容量
		sSql.append(" ,JCUDT.REBATE2_SOUYOURYO AS REBATE2_SOUYOURYO_VIEW \n"); // 容量（L）_リベートⅡ類対象総容量
		sSql.append(" ,JCUDT.REBATE3_SOUYOURYO AS REBATE3_SOUYOURYO_VIEW \n"); // 容量（L）_リベートⅢ類対象総容量
		sSql.append(" ,JCUDT.REBATE4_SOUYOURYO AS REBATE4_SOUYOURYO_VIEW \n"); // 容量（L）_リベートⅣ類対象総容量
		sSql.append(" ,JCUDT.REBATE5_SOUYOURYO AS REBATE5_SOUYOURYO_VIEW \n"); // 容量（L）_リベートⅤ類対象総容量
		sSql.append(" ,JCUDT.REBATEO_SOUYOURYO AS REBATEO_SOUYOURYO_VIEW \n"); // 容量（L）_リベート対象外総容量
		sSql.append(" ,F_RTRIM(JCUDT.PB_TOKUCYU_KBN) AS PB_TOKUCYU_KBN_VIEW \n"); // 特注指示区分
		sSql.append(" ,F_RTRIM(JCUDT.PB_TOKUCYU) AS PB_TOKUCYU_VIEW \n"); // PB OR 特注指示内容
		sSql.append(" ,F_RTRIM(JCUDT.HANBAI_BUMON_CD) AS HANBAI_BUMON_CD_VIEW \n"); // 販売部門CD
		sSql.append(" ,F_RTRIM(JCUDT.HANBAI_BUMON_RNM) AS HANBAI_BUMON_RNM_VIEW \n"); // 販売部門名（略式）
		sSql.append(" ,F_RTRIM(JCUDT.HANBAI_SYUBETU_CD) AS HANBAI_SYUBETU_CD_VIEW \n"); // 販売種別CD
		sSql.append(" ,F_RTRIM(JCUDT.HANBAI_SYUBETU_RNM) AS HANBAI_SYUBETU_RNM_VIEW \n"); // 販売種別名（略式）
		sSql.append(" ,F_RTRIM(JCUDT.HANBAI_BUNRUI_CD) AS HANBAI_BUNRUI_CD_VIEW \n"); // 販売分類CD
		sSql.append(" ,F_RTRIM(JCUDT.HANBAI_BUNRUI_RNM) AS HANBAI_BUNRUI_RNM_VIEW \n"); // 販売分類名（略式）
		sSql.append(" ,F_RTRIM(JCUDT.SDN_DTERR_KBN) AS SDN_DTERR_KBN_VIEW \n"); // SDN受注ディテールエラー区分
		sSql.append(" ,F_RTRIM(JCUDT.EDI_HAISOUG_SEND_KB) AS EDI_HAISOUG_SEND_KB_VIEW \n"); // EDI配送依頼(集約)送信区分
		sSql.append(" ,SYO.INDEMNITY_KBN AS INDEMNITY_KBN_VIEW \n"); // 補償金徴収対象区分
		sSql.append(" ,SYO.SERVICE_KBN AS SERVICE_KBN_VIEW \n"); // 引上手数料対象区分

		sSql.append(" ,F_RTRIM(JCUDT.BIKOU_1_TX) AS BIKOU_1_TX_VIEW \n"); // 備考1
		sSql.append(" ,F_RTRIM(JCUDT.BIKOU_2_TX) AS BIKOU_2_TX_VIEW \n"); // 備考2
		sSql.append(" ,F_RTRIM(JCUDT.BIKOU_3_TX) AS BIKOU_3_TX_VIEW \n"); // 備考3
		sSql.append(" ,F_RTRIM(JCUDT.BIKOU_4_TX) AS BIKOU_4_TX_VIEW \n"); // 備考4
		sSql.append(" ,F_RTRIM(JCUDT.BIKOU_5_TX) AS BIKOU_5_TX_VIEW \n"); // 備考5

		sSql.append("FROM T_JYUCYU_DT JCUDT \n");
		sSql.append("LEFT JOIN M_SYOHIN SYO ON (JCUDT.KAISYA_CD = SYO.KAISYA_CD AND JCUDT.KTKSY_CD = SYO.KTKSY_CD AND JCUDT.SHOHIN_CD = SYO.SHOHIN_CD) \n");

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" JCUDT.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 受注NO
		sSql.append("AND JCUDT.JYUCYU_NO = ? \n");
		bindList.add(jyucyuNo);

		sSql.append("ORDER BY	\n");
		sSql.append(" JCUDT.INPUT_LINE_NO \n"); // 受注行NO

		return sSql.toString();
	}


	/**
	 * 受注入力（予定出荷先別商品カテゴリデータ）リストを取得する
	 *
	 * @param jyucyuNo 受注NO
	 * @return JuchuJuchuDataInDtList 検索結果
	 */
	public JuchuJuchuDataInCatList getListJuchuJuchuDataInCat(String jyucyuNo) {
		category__.info("受注入力（予定出荷先別商品カテゴリデータ）リスト検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("受注入力（予定出荷先別商品カテゴリデータ）リスト >> 検索処理 >> SQL文生成");
		String searchSql = getJuchuDataInCatListSearchSql(jyucyuNo, bindList);

		category__.info("受注入力（予定出荷先別商品カテゴリデータ）リスト >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("受注入力（予定出荷先別商品カテゴリデータ）リスト >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("受注入力（予定出荷先別商品カテゴリデータ）リスト検索処理 終了");

		// 検索結果を変換
		JuchuJuchuDataInCatList searchedList = new JuchuJuchuDataInCatList();
		if (records_ != null) {
			searchedList = new JuchuJuchuDataInCatList(records_);
		}

		return searchedList;
	}


	/**
	 * 受注NOから受注入力（予定出荷先別商品カテゴリデータ）リストを取得するSQL
	 *
	 * @param jyucyuNo
	 * @param bindList
	 * @return sSql
	 */
	private String getJuchuDataInCatListSearchSql(String jyucyuNo, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT \n");
		PbsSQL.setCommonColumns(sSql, "JCUCAT"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
		sSql.append("  JCUCAT.RIYOU_KBN AS RIYOU_KBN \n"); // 利用区分
		sSql.append(" ,JCUCAT.KAISYA_CD AS KAISYA_CD \n"); // 会社CD
		sSql.append(" ,JCUCAT.JYUCYU_NO AS JYUCYU_NO \n"); // 受注NO
		sSql.append(" ,JCUCAT.SHN_CTGR_LINE_NO AS SHN_CTGR_LINE_NO \n"); // 受注カテゴリ行NO
		sSql.append(" ,JCUCAT.KTKSY_CD AS KTKSY_CD \n"); // 寄託者CD
		sSql.append(" ,JCUCAT.SEIHIN_CD AS SEIHIN_CD \n"); // 製品CD
		sSql.append(" ,JCUCAT.HANBAI_BUMON_CD AS HANBAI_BUMON_CD \n"); // 販売部門CD
		sSql.append(" ,JCUCAT.HANBAI_BUMON_RNM AS HANBAI_BUMON_RNM \n"); // 販売部門名（略式）
		sSql.append(" ,JCUCAT.HANBAI_SYUBETU_CD AS HANBAI_SYUBETU_CD \n"); // 販売種別CD
		sSql.append(" ,JCUCAT.HANBAI_SYUBETU_RNM AS HANBAI_SYUBETU_RNM \n"); // 販売種別名（略式）
		sSql.append(" ,JCUCAT.HANBAI_BUNRUI_CD AS HANBAI_BUNRUI_CD \n"); // 販売分類CD
		sSql.append(" ,JCUCAT.HANBAI_BUNRUI_RNM AS HANBAI_BUNRUI_RNM \n"); // 販売分類名（略式）
		sSql.append(" ,JCUCAT.SYUZEI_CD AS SYUZEI_CD \n"); // 酒税分類CD
		sSql.append(" ,JCUCAT.SYUZEI_NM_RYAKU AS SYUZEI_NM_RYAKU \n"); // 酒税分類名（略式）
		sSql.append(" ,JCUCAT.TANE_CD AS TANE_CD \n"); // 種CD
		sSql.append(" ,JCUCAT.TANE_NM_RYAKU AS TANE_NM_RYAKU \n"); // 種名称（略式）
		sSql.append(" ,JCUCAT.SOZAI_KBN AS SOZAI_KBN \n"); // 素材区分
		sSql.append(" ,JCUCAT.COLOR_KBN AS COLOR_KBN \n"); // 色区分
		sSql.append(" ,JCUCAT.HJISEKI_BUNRUI_KBN AS HJISEKI_BUNRUI_KBN \n"); // 販売実績分類区分
		sSql.append(" ,JCUCAT.VOL AS VOL \n"); // 容量（L)
		sSql.append(" ,JCUCAT.TANKA AS TANKA \n"); // 単価（円）
		sSql.append(" ,JCUCAT.SYUKA_HON AS SYUKA_HON \n"); // 出荷本数計
		sSql.append(" ,JCUCAT.SYUKA_VOL AS SYUKA_VOL \n"); // 出荷容量計（L)
		sSql.append(" ,JCUCAT.HANBAI_KINGAKU AS HANBAI_KINGAKU \n"); // 販売金額計（円）

		sSql.append(" ,F_RTRIM(JCUCAT.BIKOU_1_TX) AS BIKOU_1_TX \n"); // 備考1
		sSql.append(" ,F_RTRIM(JCUCAT.BIKOU_2_TX) AS BIKOU_2_TX \n"); // 備考2
		sSql.append(" ,F_RTRIM(JCUCAT.BIKOU_3_TX) AS BIKOU_3_TX \n"); // 備考3
		sSql.append(" ,F_RTRIM(JCUCAT.BIKOU_4_TX) AS BIKOU_4_TX \n"); // 備考4
		sSql.append(" ,F_RTRIM(JCUCAT.BIKOU_5_TX) AS BIKOU_5_TX \n"); // 備考5

		// 表示用
		sSql.append(" ,JCUCAT.RIYOU_KBN AS RIYOU_KBN_VIEW \n"); // 利用区分
		sSql.append(" ,JCUCAT.KAISYA_CD AS KAISYA_CD_VIEW \n"); // 会社CD
		sSql.append(" ,JCUCAT.JYUCYU_NO AS JYUCYU_NO_VIEW \n"); // 受注NO
		sSql.append(" ,JCUCAT.SHN_CTGR_LINE_NO AS SHN_CTGR_LINE_NO_VIEW \n"); // 受注カテゴリ行NO
		sSql.append(" ,JCUCAT.KTKSY_CD AS KTKSY_CD_VIEW \n"); // 寄託者CD
		sSql.append(" ,JCUCAT.SEIHIN_CD AS SEIHIN_CD_VIEW \n"); // 製品CD
		sSql.append(" ,JCUCAT.HANBAI_BUMON_CD AS HANBAI_BUMON_CD_VIEW \n"); // 販売部門CD
		sSql.append(" ,JCUCAT.HANBAI_BUMON_RNM AS HANBAI_BUMON_RNM_VIEW \n"); // 販売部門名（略式）
		sSql.append(" ,JCUCAT.HANBAI_SYUBETU_CD AS HANBAI_SYUBETU_CD_VIEW \n"); // 販売種別CD
		sSql.append(" ,JCUCAT.HANBAI_SYUBETU_RNM AS HANBAI_SYUBETU_RNM_VIEW \n"); // 販売種別名（略式）
		sSql.append(" ,JCUCAT.HANBAI_BUNRUI_CD AS HANBAI_BUNRUI_CD_VIEW \n"); // 販売分類CD
		sSql.append(" ,JCUCAT.HANBAI_BUNRUI_RNM AS HANBAI_BUNRUI_RNM_VIEW \n"); // 販売分類名（略式）
		sSql.append(" ,JCUCAT.SYUZEI_CD AS SYUZEI_CD_VIEW \n"); // 酒税分類CD
		sSql.append(" ,JCUCAT.SYUZEI_NM_RYAKU AS SYUZEI_NM_RYAKU_VIEW \n"); // 酒税分類名（略式）
		sSql.append(" ,JCUCAT.TANE_CD AS TANE_CD_VIEW \n"); // 種CD
		sSql.append(" ,JCUCAT.TANE_NM_RYAKU AS TANE_NM_RYAKU_VIEW \n"); // 種名称（略式）
		sSql.append(" ,JCUCAT.SOZAI_KBN AS SOZAI_KBN_VIEW \n"); // 素材区分
		sSql.append(" ,JCUCAT.COLOR_KBN AS COLOR_KBN_VIEW \n"); // 色区分
		sSql.append(" ,JCUCAT.HJISEKI_BUNRUI_KBN AS HJISEKI_BUNRUI_KBN_VIEW \n"); // 販売実績分類区分
		sSql.append(" ,JCUCAT.VOL AS VOL_VIEW \n"); // 容量（L)
		sSql.append(" ,JCUCAT.TANKA AS TANKA_VIEW \n"); // 単価（円）
		sSql.append(" ,JCUCAT.SYUKA_HON AS SYUKA_HON_VIEW \n"); // 出荷本数計
		sSql.append(" ,JCUCAT.SYUKA_VOL AS SYUKA_VOL_VIEW \n"); // 出荷容量計（L)
		sSql.append(" ,JCUCAT.HANBAI_KINGAKU AS HANBAI_KINGAKU_VIEW \n"); // 販売金額計（円）

		sSql.append(" ,F_RTRIM(JCUCAT.BIKOU_1_TX) AS BIKOU_1_TX_VIEW \n"); // 備考1
		sSql.append(" ,F_RTRIM(JCUCAT.BIKOU_2_TX) AS BIKOU_2_TX_VIEW \n"); // 備考2
		sSql.append(" ,F_RTRIM(JCUCAT.BIKOU_3_TX) AS BIKOU_3_TX_VIEW \n"); // 備考3
		sSql.append(" ,F_RTRIM(JCUCAT.BIKOU_4_TX) AS BIKOU_4_TX_VIEW \n"); // 備考4
		sSql.append(" ,F_RTRIM(JCUCAT.BIKOU_5_TX) AS BIKOU_5_TX_VIEW \n"); // 備考5

		sSql.append("FROM T_JYUCYU_JSKCTG JCUCAT \n");

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" JCUCAT.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 受注NO
		sSql.append("AND JCUCAT.JYUCYU_NO = ? \n");
		bindList.add(jyucyuNo);

		sSql.append("ORDER BY	\n");
		sSql.append(" JCUCAT.SHN_CTGR_LINE_NO \n"); // 受注カテゴリ行NO

		return sSql.toString();
	}

	/**
	 * 受注NO枝番の次の番号を取得する（NumberClientで使用）
	 *
	 * @param kaisyaCd 会社CD
	 * @param jyucyuNo 受注NO
	 * @return 受注NO枝番の次の番号
	 */
	public String getNextEdaban(String kaisyaCd, String jyucyuNo) {
		String ret = "";

		category__.info("受注NO枝番の次の番号検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("受注NO枝番の次の番号 >> 検索処理 >> SQL文生成");
		String searchSql = getNextEdabanSearchSql(kaisyaCd, jyucyuNo, bindList);

		category__.info("受注NO枝番の次の番号 >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("受注NO枝番の次の番号 >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("受注NO枝番の次の番号検索処理 終了");

		if (records_ != null) {
			ret = records_[0].getString("NEXTEDABAN");
			if ("2".equals(PbsUtil.strCompare("9", ret))) { // 枝番2桁以上はエラー
				ret = "_"; // エラー判別用文字列："_"をセット
			}
		}

		return ret;
	}


	/**
	 * 受注NO枝番の次の番号を取得するSQL（NumberClientで使用）
	 *
	 * @param kaisyaCd
	 * @param jyucyuNo
	 * @param bindList
	 * @return sSql
	 */
	private String getNextEdabanSearchSql(String kaisyaCd, String jyucyuNo, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("SELECT \n");
		sSql.append(" MAX(SUBSTR(JCUHD.JYUCYU_NO, -1))+1 AS NEXTEDABAN \n"); // 受注NO枝番の次の番号
		sSql.append("FROM T_JYUCYU_HD JCUHD \n");

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" JCUHD.KAISYA_CD = ? \n");
		bindList.add(kaisyaCd);

		// 受注NO
		sSql.append("AND JCUHD.JYUCYU_NO LIKE ? \n");
		bindList.add(ComPopupUtil.getwildCardRight(jyucyuNo.substring(0,7))); // 受注NO（WX99999C）の前7桁で検索

		return sSql.toString();
	}

	/**
	 * 受注NOの存在チェック（NumberClientで使用）
	 *
	 * @param kaisyaCd 会社CD
	 * @param jyucyuNo 受注NO
	 * @return TRUE:既に存在する / FALSE:存在しない
	 */
	public boolean isExistJuchuNo(String kaisyaCd, String jyucyuNo) {
		boolean ret = false;

		category__.info("受注NOの存在チェック検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("受注NOの存在チェック >> 検索処理 >> SQL文生成");
		String searchSql = getExistJuchuNoSearchSql(kaisyaCd, jyucyuNo, bindList);

		category__.info("受注NOの存在チェック >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("受注NOの存在チェック >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("受注NOの存在チェック検索処理 終了");

		if (records_ != null) {
			ret = true;
		}

		return ret;
	}


	/**
	 * 受注NOの存在チェックSQL（NumberClientで使用）
	 *
	 * @param kaisyaCd
	 * @param jyucyuNo
	 * @param bindList
	 * @return sSql
	 */
	private String getExistJuchuNoSearchSql(String kaisyaCd, String jyucyuNo, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		sSql.append("SELECT \n");
		sSql.append(" JCUHD.JYUCYU_NO \n"); // 受注NO
		sSql.append("FROM T_JYUCYU_HD JCUHD \n");

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" JCUHD.KAISYA_CD = ? \n");
		bindList.add(kaisyaCd);

		// 受注NO
		sSql.append("AND JCUHD.JYUCYU_NO = ? \n");
		bindList.add(jyucyuNo);

		return sSql.toString();
	}

}
