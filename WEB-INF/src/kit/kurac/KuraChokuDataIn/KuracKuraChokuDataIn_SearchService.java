package kit.kurac.KuraChokuDataIn;

import static fb.com.IKitComConst.*;
import static fb.inf.pbs.IPbsConst.*;

import java.util.ArrayList;
import java.util.List;

import kit.mastr.TatesenOrositen.MastrTatesenOrositenList;
import kit.mastr.TatesenOrositen.MastrTatesenOrositenRecord;
import kit.pop.ComPopupUtil;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.BindList;
import fb.com.BindString;
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
public class KuracKuraChokuDataIn_SearchService extends KitService {

	/**
	 *
	 */
	/** シリアルID */
	private static final long serialVersionUID = 7321674836518886720L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = KuracKuraChokuDataIn_SearchService.class.getName();
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
	public KuracKuraChokuDataIn_SearchService(ComUserSession cus, PbsDatabase db_, boolean isTran,
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
	 * 蔵元入力（蔵元データ／ヘッダー部）検索のSQL生成と実行.
	 *
	 * @param searchForm 検索フォーム
	 * @return 検索結果
	 * @throws DataNotFoundException
	 */
	public PbsRecord[] execute(KuracKuraChokuDataIn_SearchForm searchForm)
			throws DataNotFoundException {

		category__.info("蔵元入力（蔵元データ／ヘッダー部）検索処理 開始");

		BindList bindList = new BindList();

		category__.info("蔵元入力（蔵元データ／ヘッダー部） >> 検索処理 >> SQL文生成");
		String searchSql = getSqlSearch(searchForm, bindList);

		category__.info("蔵元入力（蔵元データ／ヘッダー部） >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (BindString binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("蔵元入力（蔵元データ／ヘッダー部） >> execute");
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
		category__.info("蔵元入力（蔵元データ／ヘッダー部）検索処理 終了");

		return records_;
	}

	/**
	 * 蔵元入力（蔵元データ／ヘッダー部）の検索SQLの作成
	 *
	 * @param searchForm
	 * @param bindList
	 * @return sSql
	 */
	private String getSqlSearch(KuracKuraChokuDataIn_SearchForm searchForm, BindList bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT DISTINCT \n");
		PbsSQL.setCommonColumns(sSql, "KURAHD"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
		sSql.append("  KURAHD.RIYOU_KBN AS RIYOU_KBN \n"); 								// 利用区分
		sSql.append(" ,KURAHD.KAISYA_CD AS KAISYA_CD \n"); 								// 会社CD (FK)
		sSql.append(" ,KURAHD.KURADATA_NO AS KURADATA_NO \n"); 							// 蔵直ﾃﾞｰﾀ連番
		sSql.append(" ,F_RTRIM(KURAHD.SYUBETU_CD) AS SYUBETU_CD \n"); 					// ﾃﾞｰﾀ種別CD
		sSql.append(" ,F_RTRIM(KURAHD.MOUSIKOMI_NO) AS MOUSIKOMI_NO \n"); 				// 申込み受付NO
		sSql.append(" ,F_RTRIM(KSG.SGYOSYA_NM) AS SGYOSYA_NM  \n"); 					// 更新者
		sSql.append(" ,TO_CHAR(KURAHD.KOUSIN_NITIZI_DT, 'YYYY/MM/DD HH24:MI:SS') AS KOUSIN_DT  \n"); 	// 更新日
		sSql.append(" ,F_RTRIM(KURAHD.JIGYOSYO_KBN) AS JIGYOSYO_KBN \n"); 				// 黄桜事業所区分
		sSql.append(" ,F_RTRIM(KURAHD.SHOHIN_GRP_CD) AS SHOHIN_GRP_CD \n"); 			// 蔵直商品ｸﾞﾙｰﾌﾟCD
		sSql.append(" ,F_RTRIM(KURAHD.TODOKESAKI_LINE_NO) AS TODOKESAKI_LINE_NO \n"); 	// 届先ﾗｲﾝNO
		sSql.append(" ,F_RTRIM(KURAHD.TEISEI_KURADATA_NO) AS TEISEI_KURADATA_NO \n"); 	// 訂正時訂正元蔵直ﾃﾞｰﾀ連番
		sSql.append(" ,F_RTRIM(KURAHD.JYUCYU_NO) AS JYUCYU_NO \n"); 					// 黄桜受注NO
		sSql.append(" ,F_RTRIM(KURAHD.UKETUKE_DT) AS UKETUKE_DT \n"); 					// 申込み受付日
		sSql.append(" ,F_RTRIM(KURAHD.TAKUHAI_BILL_KBN) AS TAKUHAI_BILL_KBN \n"); 		// 宅配伝票区分
		sSql.append(" ,F_RTRIM(KURAHD.HACYU_NO) AS HACYU_NO \n"); 						// 発注NO
		sSql.append(" ,F_RTRIM(KURAHD.HASO_YOTEI_DT) AS HASO_YOTEI_DT \n"); 			// 発送予定日
		sSql.append(" ,F_RTRIM(KURAHD.CHECK_LIST_PRT_KBN) AS CHECK_LIST_PRT_KBN \n"); 	// 酒販店ﾁｪｯｸﾘｽﾄ打出区分
		sSql.append(" ,F_RTRIM(KURAHD.KURADEN_NO) AS KURADEN_NO \n"); 					// 整理NO
		sSql.append(" ,F_RTRIM(KURAHD.UNCHIN_KBN) AS UNCHIN_KBN \n");				 	// 運賃区分
		sSql.append(" ,F_RTRIM(KURAHD.YOUTO_KBN) AS YOUTO_KBN \n"); 					// 用途区分

		sSql.append(" ,F_RTRIM(KURAHD.NOSI_KBN) AS NOSI_KBN \n"); 						// 【のし】名前記載有無区分
		sSql.append(" ,KURAHD.NOSI_COMMENT1 AS NOSI_COMMENT1 \n"); 						// のし内容 1行目
		sSql.append(" ,KURAHD.NOSI_COMMENT2 AS NOSI_COMMENT2 \n"); 						// のし内容 2行目
		sSql.append(" ,KURAHD.NOSI_COMMENT3 AS NOSI_COMMENT3 \n"); 						// のし内容 3行目

		sSql.append(" ,F_RTRIM(KURAHD.TATESN_CD) AS TATESN_CD \n"); 					// 縦線CD
		sSql.append(" ,F_RTRIM(KURAHD.TOKUYAKUTEN_CD) AS TOKUYAKUTEN_CD \n"); 			// 特約店CD
		sSql.append(" ,KURAHD.TOKUYAKUTEN_NM_RYAKU AS TOKUYAKUTEN_NM \n"); 				// 特約店名（略称）
		sSql.append(" ,F_RTRIM(KURAHD.DEPO_CD) AS DEPO_CD \n"); 						// デポCD
		sSql.append(" ,KURAHD.DEPO_NM AS DEPO_NM \n"); 									// デポ店名（略称）
		sSql.append(" ,F_RTRIM(KURAHD.NIJITEN_CD) AS NIJITEN_CD \n"); 					// 二次店CD
		sSql.append(" ,KURAHD.NIJITEN_NM AS NIJITEN_NM \n"); 							// 二次店名（略称）
		sSql.append(" ,F_RTRIM(KURAHD.SANJITEN_CD) AS SANJITEN_CD \n"); 				// 三次店CD
		sSql.append(" ,KURAHD.SANJITEN_NM AS SANJITEN_NM \n"); 							// 三次店名（略称）

		sSql.append(" ,F_RTRIM(KURAHD.SYUHANTEN_CD) AS SYUHANTEN_CD \n"); 				// 酒販店（統一）CD
		sSql.append(" ,SYH.TEN_NM_YAGO AS SYUHANTEN_NM \n"); 							// 酒販店名
		sSql.append(" ,F_RTRIM(SYH.TEL) AS SYUHANTEN_TEL \n"); 							// 酒販店 TEL
		sSql.append(" ,SYH.ADDRESS AS SYUHANTEN_ADDRESS \n"); 							// 酒販店 住所
		sSql.append(" ,F_RTRIM(SYH.ZIP) AS SYUHANTEN_ZIP \n"); 							// 酒販店 郵便番号

		sSql.append(" ,F_RTRIM(KURAHD.UNSOTEN_CD) AS UNSOTEN_CD \n"); 					// 運送店CD
		sSql.append(" ,UNS.UNSOTEN_NM_PRINT AS UNSOTEN_NM \n"); 						// 運送店名（略称）

		sSql.append(" ,KURAHD.TODOKESAKI_NM AS TODOKESAKI_NM \n"); 						// 届け先 依頼主名
		sSql.append(" ,F_RTRIM(KURAHD.TODOKESAKI_TEL) AS TODOKESAKI_TEL \n"); 			// 届け先 TEL
		sSql.append(" ,KURAHD.TODOKESAKI_ADDRESS AS TODOKESAKI_ADDRESS \n"); 			// 届け先 住所
		sSql.append(" ,F_RTRIM(KURAHD.TODOKESAKI_ZIP) AS TODOKESAKI_ZIP \n"); 			// 届け先 郵便番号
		sSql.append(" ,KURAHD.IRAINUSI_NM AS IRAINUSI_NM \n"); 							// 依頼主 依頼主名
		sSql.append(" ,F_RTRIM(KURAHD.IRAINUSI_TEL) AS IRAINUSI_TEL \n"); 				// 依頼主 TEL
		sSql.append(" ,KURAHD.IRAINUSI_ADDRESS AS IRAINUSI_ADDRESS \n"); 				// 依頼主 住所
		sSql.append(" ,F_RTRIM(KURAHD.IRAINUSI_ZIP) AS IRAINUSI_ZIP \n"); 				// 依頼主 郵便番号

		// 表示用
		sSql.append(" ,F_RTRIM(KURAHD.JIGYOSYO_KBN) AS JIGYOSYO_KBN_VIEW \n"); 				// 黄桜事業所区分
		sSql.append(" ,F_RTRIM(KURAHD.SHOHIN_GRP_CD) AS SHOHIN_GRP_CD_VIEW \n"); 			// 蔵直商品ｸﾞﾙｰﾌﾟCD
		sSql.append(" ,F_RTRIM(KURAHD.TODOKESAKI_LINE_NO) AS TODOKESAKI_LINE_NO_VIEW \n"); 	// 届先ﾗｲﾝNO
		sSql.append(" ,F_RTRIM(KURAHD.JYUCYU_NO) AS JYUCYU_NO_VIEW \n"); 					// 黄桜受注NO
		sSql.append(" ,F_RTRIM(KURAHD.UKETUKE_DT) AS UKETUKE_DT_VIEW \n"); 					// 申込み受付日
		sSql.append(" ,F_RTRIM(KURAHD.TAKUHAI_BILL_KBN) AS TAKUHAI_BILL_KBN_VIEW \n"); 		// 宅配伝票区分
		sSql.append(" ,F_RTRIM(KURAHD.HACYU_NO) AS HACYU_NO_VIEW \n"); 						// 発注NO
		sSql.append(" ,F_RTRIM(KURAHD.HASO_YOTEI_DT) AS HASO_YOTEI_DT_VIEW \n"); 			// 発送予定日
		sSql.append(" ,F_RTRIM(KURAHD.CHECK_LIST_PRT_KBN) AS CHECK_LIST_PRT_KBN_VIEW \n"); 	// 酒販店ﾁｪｯｸﾘｽﾄ打出区分
		sSql.append(" ,F_RTRIM(KURAHD.KURADEN_NO) AS KURADEN_NO_VIEW \n"); 					// 整理NO
		sSql.append(" ,F_RTRIM(KURAHD.UNCHIN_KBN) AS UNCHIN_KBN_VIEW \n");				 	// 運賃区分
		sSql.append(" ,F_RTRIM(KURAHD.YOUTO_KBN) AS YOUTO_KBN_VIEW \n"); 					// 用途区分

		sSql.append(" ,F_RTRIM(KURAHD.NOSI_KBN) AS NOSI_KBN_VIEW \n"); 						// 【のし】名前記載有無区分
		sSql.append(" ,KURAHD.NOSI_COMMENT1 AS NOSI_COMMENT1_VIEW \n"); 					// のし内容 1行目
		sSql.append(" ,KURAHD.NOSI_COMMENT2 AS NOSI_COMMENT2_VIEW \n"); 					// のし内容 2行目
		sSql.append(" ,KURAHD.NOSI_COMMENT3 AS NOSI_COMMENT3_VIEW \n"); 					// のし内容 3行目

		sSql.append(" ,F_RTRIM(KURAHD.TATESN_CD) AS TATESN_CD_VIEW \n"); 					// 縦線CD
		sSql.append(" ,F_RTRIM(KURAHD.TOKUYAKUTEN_CD) AS TOKUYAKUTEN_CD_VIEW \n"); 			// 特約店CD
		sSql.append(" ,KURAHD.TOKUYAKUTEN_NM_RYAKU AS TOKUYAKUTEN_NM_VIEW \n"); 			// 特約店名（略称）
		sSql.append(" ,F_RTRIM(KURAHD.DEPO_CD) AS DEPO_CD_VIEW \n"); 						// デポCD
		sSql.append(" ,KURAHD.DEPO_NM AS DEPO_NM_VIEW \n"); 								// デポ店名（略称）
		sSql.append(" ,F_RTRIM(KURAHD.NIJITEN_CD) AS NIJITEN_CD_VIEW \n"); 					// 二次店CD
		sSql.append(" ,KURAHD.NIJITEN_NM AS NIJITEN_NM_VIEW \n"); 							// 二次店名（略称）
		sSql.append(" ,F_RTRIM(KURAHD.SANJITEN_CD) AS SANJITEN_CD_VIEW \n"); 				// 三次店CD
		sSql.append(" ,KURAHD.SANJITEN_NM AS SANJITEN_NM_VIEW \n"); 						// 三次店名（略称）

		sSql.append(" ,F_RTRIM(KURAHD.SYUHANTEN_CD) AS SYUHANTEN_CD_VIEW \n"); 				// 酒販店（統一）CD
		sSql.append(" ,SYH.TEN_NM_YAGO AS SYUHANTEN_NM_VIEW \n"); 							// 酒販店名
		sSql.append(" ,F_RTRIM(SYH.TEL) AS SYUHANTEN_TEL_VIEW \n"); 						// 酒販店 TEL
		sSql.append(" ,SYH.ADDRESS AS SYUHANTEN_ADDRESS_VIEW \n"); 							// 酒販店 住所
		sSql.append(" ,F_RTRIM(SYH.ZIP) AS SYUHANTEN_ZIP_VIEW \n"); 						// 酒販店 郵便番号


		sSql.append(" ,F_RTRIM(KURAHD.UNSOTEN_CD) AS UNSOTEN_CD_VIEW \n"); 					// 運送店CD
		sSql.append(" ,UNS.UNSOTEN_NM_PRINT AS UNSOTEN_NM_VIEW \n"); 						// 運送店名（略称）

		sSql.append(" ,KURAHD.TODOKESAKI_NM AS TODOKESAKI_NM_VIEW \n"); 					// 届け先 依頼主名
		sSql.append(" ,F_RTRIM(KURAHD.TODOKESAKI_TEL) AS TODOKESAKI_TEL_VIEW \n"); 			// 届け先 TEL
		sSql.append(" ,KURAHD.TODOKESAKI_ADDRESS AS TODOKESAKI_ADDRESS_VIEW \n"); 			// 届け先 住所
		sSql.append(" ,F_RTRIM(KURAHD.TODOKESAKI_ZIP) AS TODOKESAKI_ZIP_VIEW \n"); 			// 届け先 郵便番号
		sSql.append(" ,KURAHD.IRAINUSI_NM AS IRAINUSI_NM_VIEW \n"); 						// 依頼主 依頼主名
		sSql.append(" ,F_RTRIM(KURAHD.IRAINUSI_TEL) AS IRAINUSI_TEL_VIEW \n"); 				// 依頼主 TEL
		sSql.append(" ,KURAHD.IRAINUSI_ADDRESS AS IRAINUSI_ADDRESS_VIEW \n"); 				// 依頼主 住所
		sSql.append(" ,F_RTRIM(KURAHD.IRAINUSI_ZIP) AS IRAINUSI_ZIP_VIEW \n"); 				// 依頼主 郵便番号

		sSql.append("FROM T_KURAC_HD KURAHD \n");
		sSql.append("LEFT JOIN M_OROSISYOSAI_HD ORSSHD ON (KURAHD.KAISYA_CD = ORSSHD.KAISYA_CD AND KURAHD.TATESN_CD = ORSSHD.TATESN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORSS ON (KURAHD.KAISYA_CD = ORSS.KAISYA_CD AND ORSSHD.OROSITEN_CD_LAST = ORSS.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_SGYOSYA KSG ON (KURAHD.KAISYA_CD = KSG.KAISYA_CD AND KURAHD.KOUSIN_SGYOSYA_CD = KSG.SGYOSYA_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORS1JI ON (KURAHD.KAISYA_CD = ORS1JI.KAISYA_CD AND KURAHD.TOKUYAKUTEN_CD = ORS1JI.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORSDEP ON (KURAHD.KAISYA_CD = ORSDEP.KAISYA_CD AND KURAHD.DEPO_CD = ORSDEP.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORS2JI ON (KURAHD.KAISYA_CD = ORS2JI.KAISYA_CD AND KURAHD.NIJITEN_CD = ORS2JI.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_OROSITEN ORS3JI ON (KURAHD.KAISYA_CD = ORS3JI.KAISYA_CD AND KURAHD.SANJITEN_CD = ORS3JI.OROSITEN_CD) \n");
		sSql.append("LEFT JOIN M_SYUHANTEN SYH ON (KURAHD.SYUHANTEN_CD = SYH.SYUHANTEN_CD) \n");
		sSql.append("LEFT JOIN M_UNSOTEN UNS ON (KURAHD.KAISYA_CD = UNS.KAISYA_CD AND KURAHD.UNSOTEN_CD = UNS.UNSOTEN_CD) \n");

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" KURAHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 利用区分
		if (!PbsUtil.isEmpty(searchForm.getRiyouKbn())) {
			sSql.append("AND KURAHD.RIYOU_KBN = ? \n");
			bindList.add(searchForm.getRiyouKbn());
		}

		// ﾃﾞｰﾀ種別CD
		if (!PbsUtil.isEmpty(searchForm.getSyubetuCd())) {
			sSql.append("AND KURAHD.SYUBETU_CD = ? \n");
			bindList.add(searchForm.getSyubetuCd());
		}

		// ﾒｰｶｰNo
		if (!PbsUtil.isEmpty(searchForm.getMakerNo())) {
			sSql.append("AND KURAHD.MOUSIKOMI_NO = ? \n");
			bindList.add(searchForm.getMakerNo());
		}

		// （部分一致）
		// 送荷先
		if (!PbsUtil.isEmpty(searchForm.getSounisakiNm())) {
			sSql.append("AND ORSS.TEN_NM1_URIDEN LIKE ? \n");
			bindList.add(ComPopupUtil.getWildCardBoth(searchForm.getSounisakiNm()), true);
		}

		// 酒販店名（部分一致）
		if (!PbsUtil.isEmpty(searchForm.getSyuhantenNm())) {
			sSql.append("AND KURAHD.SYUHANTEN_NM LIKE ? \n");
			bindList.add(ComPopupUtil.getWildCardBoth(searchForm.getSyuhantenNm()), true);
		}

		// 依頼主（部分一致）
		if (!PbsUtil.isEmpty(searchForm.getIrainusi())) {
			sSql.append("AND KURAHD.IRAINUSI_NM LIKE ? \n");
			bindList.add(ComPopupUtil.getWildCardBoth(searchForm.getIrainusi()), true);
		}

		// 申込日
		if (!PbsUtil.isEmpty(searchForm.getMousikomibi())) {
			sSql.append("AND KURAHD.UKETUKE_DT = ? \n");
			bindList.add(searchForm.getMousikomibi());
		}

		// 事業所区分
		if (!PbsUtil.isEmpty(searchForm.getJigyousyo())) {
			sSql.append("AND KURAHD.JIGYOSYO_KBN = ? \n");
			bindList.add(searchForm.getJigyousyo());
		}

		// 商品GRP
		if (!PbsUtil.isEmpty(searchForm.getShohinGrp())) {
			sSql.append("AND KURAHD.SHOHIN_GRP_CD = ? \n");
			bindList.add(searchForm.getShohinGrp());
		}

		// 酒販店CD
		if (!PbsUtil.isEmpty(searchForm.getSyuhantenCd())) {
			sSql.append("AND KURAHD.SYUHANTEN_CD = ? \n");
			bindList.add(searchForm.getSyuhantenCd());
		}

		// 届け先（部分一致）
		if (!PbsUtil.isEmpty(searchForm.getTdksk())) {
			sSql.append("AND KURAHD.TODOKESAKI_NM LIKE ? \n");
			bindList.add(ComPopupUtil.getWildCardBoth(searchForm.getTdksk()), true);
		}

		// 発送予定日
		if (!PbsUtil.isEmpty(searchForm.getHasoyoteibi())) {
			sSql.append("AND KURAHD.HASO_YOTEI_DT = ? \n");
			bindList.add(searchForm.getHasoyoteibi());
		}

		// 未連携
		if (!PbsUtil.isEmpty(searchForm.getMiJyucyuOnly())) {
			sSql.append("AND F_RTRIM(KURAHD.JYUCYU_NO) IS NULL \n");
			sSql.append("AND KURAHD.KURADATA_NO IN ( \n");
			sSql.append("    SELECT KURADT.KURADATA_NO FROM T_KURAC_DT KURADT \n");
			sSql.append("    WHERE F_RTRIM(KURADT.SYUKADEN_NO) IS NULL) \n");
		}

		sSql.append("ORDER BY \n");
		sSql.append(" F_RTRIM(KURAHD.MOUSIKOMI_NO) DESC \n"); // 申込受付No

		return sSql.toString();
	}

	/**
	 * 蔵元入力（蔵元データ／ディテール部）リストを取得する
	 *
	 * @param jyucyuNo 蔵元NO
	 * @return JuchuJuchuDataInDtList 検索結果
	 */
	public KuracKuraChokuDataInDtList getKurachokuDataInDtList(KuracKuraChokuDataIn_EditForm editForm) {
		category__.info("蔵元入力（蔵元データ／ディテール部）リスト検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("蔵元入力（蔵元データ／ディテール部）リスト >> 検索処理 >> SQL文生成");
		String searchSql = getKurachokuDataInDtListSearchSql(editForm, bindList);

		category__.info("蔵元入力（蔵元データ／ディテール部）リスト >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("蔵元入力（蔵元データ／ディテール部）リスト >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("蔵元入力（蔵元データ／ディテール部）リスト検索処理 終了");

		// 検索結果を変換
		KuracKuraChokuDataInDtList searchedList = new KuracKuraChokuDataInDtList();
		if (records_ != null) {
			searchedList = new KuracKuraChokuDataInDtList(records_);
		}

		return searchedList;
	}


	/**
	 * 蔵元入力（蔵元データ／ディテール部）リストを取得する
	 *
	 * @param jigyoushoKbn 事業所区分
	 * @param shohinKbn 商品区分
	 * @return KuracKuraChokuDataInDtList 検索結果
	 */
	public KuracKuraChokuDataInDtList getKurachokuDataInDtListByKbn(String jigyoushoKbn, String shohinKbn) {
		category__.info("蔵元入力（蔵元データ／ディテール部）リスト検索処理 開始");

		List<String> bindList = new ArrayList<String>();
		String searchSql = "";

		category__.info("蔵元入力（蔵元データ／ディテール部）リスト >> 検索処理 >> SQL文生成");
		if (KURAC_SITEN_KB_KOURI.equals(jigyoushoKbn)) {
			searchSql = getKurachokuDataInDtListByKbnSql1(shohinKbn, bindList);
		} else {
			searchSql = getKurachokuDataInDtListByKbnSql2(shohinKbn, bindList);
		}


		category__.info("蔵元入力（蔵元データ／ディテール部）リスト >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("蔵元入力（蔵元データ／ディテール部）リスト >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("蔵元入力（蔵元データ／ディテール部）リスト検索処理 終了");

		// 検索結果を変換
		KuracKuraChokuDataInDtList searchedList = new KuracKuraChokuDataInDtList();
		if (records_ != null) {
			searchedList = new KuracKuraChokuDataInDtList(records_);
		}

		return searchedList;
	}


	/**
	 * 蔵元入力（蔵元データ／ディテール部）情報を取得する）(AJAX用)
	 *
	 * @param jigyoushoKbn 事業所区分
	 * @param shohinKbn 商品区分
	 * @param linNo 表記順位
	 * @return KuracKuraChokuDataInDtRecord 検索結果
	 */
	public KuracKuraChokuDataInDtRecord getKurachokuDataInDtListByKbnLine(String jigyoushoKbn, String shohinKbn, String lineNo) {
		category__.info("蔵元入力（蔵元データ／ディテール部）リスト検索処理 開始");

		List<String> bindList = new ArrayList<String>();
		String searchSql = "";

		category__.info("蔵元入力（蔵元データ／ディテール部）リスト >> 検索処理 >> SQL文生成");
		if ("K".equals(jigyoushoKbn)) {
			searchSql = getKurachokuDataInDtListByKbnLineSql1(shohinKbn,lineNo,  bindList);
		} else {
			searchSql = getKurachokuDataInDtListByKbnLineSql2(shohinKbn, lineNo, bindList);
		}


		category__.info("蔵元入力（蔵元データ／ディテール部）リスト >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("蔵元入力（蔵元データ／ディテール部）リスト >> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("蔵元入力（蔵元データ／ディテール部）リスト検索処理 終了");

		// 検索結果を変換
		KuracKuraChokuDataInDtList searchedList = new KuracKuraChokuDataInDtList();
		KuracKuraChokuDataInDtRecord searchedRec = new KuracKuraChokuDataInDtRecord();
		if (records_ != null) {
			searchedList = new KuracKuraChokuDataInDtList(records_);
			searchedRec = (KuracKuraChokuDataInDtRecord) searchedList.getFirstRecord();
		}

		return searchedRec;
	}


	/**
	 * 申込み受付Noから蔵元データヘッダー情報を取得する
	 *
	 * @param moshikomiNo 申込みNo
	 * @return KuracKuraChokuDataInList 検索結果
	 */
	public KuracKuraChokuDataInList getKurachokuDataInListByMoshiNo(String moshikomiNo) {
		category__.info("蔵元入力（蔵元データ）リスト検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("蔵元入力（蔵元データ）リスト >> 検索処理 >> SQL文生成");
		String searchSql = getKurachokuDataInfoByMoshiNoSql(moshikomiNo, bindList);

		category__.info("蔵元入力（蔵元データ）リスト  >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("蔵元入力（蔵元データ）リスト>> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("蔵元入力（蔵元データ）リスト検索処理 終了");

		// 検索結果を変換
		KuracKuraChokuDataInList searchedList = null;

		if (records_ != null) {
			searchedList = new KuracKuraChokuDataInList(records_);
		}

		return searchedList;
	}

	/**
	 * 申込み受付No、日付から蔵元データヘッダー情報を取得する
	 * @param moshikomiNo	申込みNo
	 * @param startDate	申込受付日の同年/01/01
	 * @param endDate	申込受付日の同年/12/31
	 * @return
	 */
	public KuracKuraChokuDataInList getKurachokuDataInListByMoshiNoDate(String moshikomiNo, String startDate, String endDate) {
		category__.info("蔵元入力（蔵元データ）リスト検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("蔵元入力（蔵元データ）リスト >> 検索処理 >> SQL文生成");
		String searchSql = getKurachokuDataInfoByMoshiNoDateSql(moshikomiNo, startDate, endDate, bindList);

		category__.info("蔵元入力（蔵元データ）リスト  >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("蔵元入力（蔵元データ）リスト>> execute");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc);

		} catch (Exception e) {
		}
		category__.info("蔵元入力（蔵元データ）リスト検索処理 終了");

		// 検索結果を変換
		KuracKuraChokuDataInList searchedList = null;

		if (records_ != null) {
			searchedList = new KuracKuraChokuDataInList(records_);
		}

		return searchedList;
	}



	/**
	 * 縦線CDから縦線卸店情報を取得する
	 *
	 * @param tatesenCd 縦線CD
	 * @param isDeletedHeld 削除済を含めるかどうか
	 * @return MastrTatesenOrositenRecord 検索結果
	 */
	public MastrTatesenOrositenRecord getRecMastrTatesenOrositen(String tatesenCd, boolean isDeletedHeld) {
		category__.info("縦線卸店情報検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("縦線卸店情報 >> 検索処理 >> SQL文生成");
		String searchSql = getTatesenInfoSearchSql(tatesenCd, bindList, isDeletedHeld);

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
		MastrTatesenOrositenList searchedList = new MastrTatesenOrositenList();
		MastrTatesenOrositenRecord searchedRec = null;
		if (records_ != null) {
			searchedList = new MastrTatesenOrositenList(records_);
			searchedRec = (MastrTatesenOrositenRecord) searchedList.getFirstRecord();
		}

		return searchedRec;
	}


	/**
	 * 蔵元NOから蔵元入力（蔵元データ／ディテール部）リストを取得するSQL
	 *
	 * @param jyucyuNo
	 * @param bindList
	 * @return sSql
	 */
	private String getKurachokuDataInDtListSearchSql(KuracKuraChokuDataIn_EditForm editForm, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT \n");
		PbsSQL.setCommonColumns(sSql, "KURADT"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
		sSql.append("  KURADT.RIYOU_KBN AS RIYOU_KBN \n"); // 利用区分
		sSql.append(" ,KURADT.KAISYA_CD AS KAISYA_CD \n"); // 会社CD
		sSql.append(" ,KURADT.KURADATA_NO AS KURADATA_NO \n"); // 蔵元NO
		sSql.append(" ,KURADT.KURADEN_LINE_NO AS KURADEN_LINE_NO \n"); // 蔵元行NO
		sSql.append(" ,F_RTRIM(KURADT.SHOHIN_CD) AS SHOHIN_CD \n"); // 商品CD
		sSql.append(" ,SYO.SHOHIN_NM_URIDEN AS SHOHIN_NM  \n"); // 商品名
		sSql.append(" ,F_RTRIM(SYO.YOUKI_KIGO_NM1) AS YOUKI_KIGO_NM \n"); // 容量名
		sSql.append(" ,F_RTRIM(KURADT.SHOHIN_SET) AS SHOHIN_SET \n"); // 商品ｾｯﾄ数
		sSql.append(" ,F_RTRIM(KURADT.HANBAI_TANKA) AS HANBAI_TANKA \n"); // 販売価格
		sSql.append(" ,F_RTRIM(KURADT.CP_KBN) AS CP_KBN \n"); // ｷｬﾝﾍﾟｰﾝ対象区分
		sSql.append(" ,F_RTRIM(KURADT.SYUKADEN_NO) AS SYUKADEN_NO \n"); // 黄桜出荷伝票NO
		sSql.append(" ,F_RTRIM(KURADT.TEISEI_SYUKA_DT) AS TEISEI_SYUKA_DT \n"); // 訂正時訂正元出荷日
		sSql.append(" ,F_RTRIM(KURADT.TEISEI_URIDEN_NO) AS TEISEI_URIDEN_NO \n"); // 訂正時訂正元売上伝票NO

		// 表示用
		sSql.append(" ,F_RTRIM(KURADT.SHOHIN_CD) AS SHOHIN_CD_VIEW \n"); // 商品CD
		sSql.append(" ,SYO.SHOHIN_NM_URIDEN AS SHOHIN_NM_VIEW  \n"); // 商品名
		sSql.append(" ,F_RTRIM(SYO.YOUKI_KIGO_NM1) AS YOUKI_KIGO_NM_VIEW \n"); // 容量名
		sSql.append(" ,F_RTRIM(KURADT.SHOHIN_SET) AS SHOHIN_SET_VIEW \n"); // 商品ｾｯﾄ数
		sSql.append(" ,F_RTRIM(KURADT.HANBAI_TANKA) AS HANBAI_TANKA_VIEW \n"); // 販売価格
		sSql.append(" ,F_RTRIM(KURADT.CP_KBN) AS CP_KBN_VIEW \n"); // ｷｬﾝﾍﾟｰﾝ対象区分
		sSql.append(" ,F_RTRIM(KURADT.SYUKADEN_NO) AS SYUKADEN_NO_VIEW \n"); // 黄桜出荷伝票NO
		sSql.append(" ,F_RTRIM(KURADT.TEISEI_SYUKA_DT) AS TEISEI_SYUKA_DT_VIEW \n"); // 訂正時訂正元出荷日
		sSql.append(" ,F_RTRIM(KURADT.TEISEI_URIDEN_NO) AS TEISEI_URIDEN_NO_VIEW \n"); // 訂正時訂正元売上伝票NO

		sSql.append("FROM T_KURAC_DT KURADT \n");
		sSql.append("LEFT JOIN M_SYOHIN SYO ON (KURADT.KAISYA_CD = SYO.KAISYA_CD AND KURADT.SHOHIN_CD = SYO.SHOHIN_CD) \n");

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" KURADT.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 寄託者CD
		sSql.append("AND SYO.KTKSY_CD = ? \n");
		bindList.add(cus.getKtksyCd());

		// 蔵元NO
		sSql.append("AND KURADT.KURADATA_NO = ? \n");
		bindList.add(editForm.getKuradataNo());

		sSql.append("ORDER BY	\n");
		sSql.append(" KURADT.KURADEN_LINE_NO  \n"); // 蔵元行NO

		return sSql.toString();
	}

	/**
	 * 事業所区分(Y)、商品区分から蔵元入力（蔵元データ／ディテール部）リストを取得するSQL
	 *
	 * @param shohinKbn 商品区分
	 * @param bindList
	 * @return sSql
	 */
	private String getKurachokuDataInDtListByKbnSql1(String shohinKbn, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT \n");
		//PbsSQL.setCommonColumns(sSql, "KURADT"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
		sSql.append("  SYOHINGRP.KR_SYOHIN_NO AS KURADEN_LINE_NO \n"); 			// 商品行No
		sSql.append(" ,SYOHINGRP.SHOHIN_CD_02 AS SHOHIN_CD \n"); 				// 商品コード
		sSql.append(" ,SYO.SHOHIN_NM_URIDEN AS SHOHIN_NM \n"); 					// 商品名
		sSql.append(" ,F_RTRIM(SYO.YOUKI_KIGO_NM1) AS YOUKI_KIGO_NM  \n"); 		// 容器名
		sSql.append(" ,F_RTRIM(SYOHINGRP.OTHER_TANKA_01) AS HANBAI_TANKA  \n"); // 販売単価
		sSql.append(" ,F_RTRIM(SYOHINGRP.CP_KBN) AS CP_KBN  \n"); 				// ｷｬﾝﾍﾟｰﾝ対象区分

		sSql.append("FROM M_KBN_KURAC_SYOHIN_GRP SYOHINGRP \n");
		sSql.append("LEFT JOIN M_SYOHIN SYO ON (SYOHINGRP.KAISYA_CD = SYO.KAISYA_CD AND SYOHINGRP.SHOHIN_CD_02 = SYO.SHOHIN_CD) \n");

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" SYO.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 寄託者CD
		sSql.append("AND SYO.KTKSY_CD = ? \n");
		bindList.add(cus.getKtksyCd());

		// 蔵直商品ｸﾞﾙｰﾌﾟ区分
		sSql.append("AND SYOHINGRP.KR_SHOHIN_KBN = ? \n");
		bindList.add(shohinKbn);

		sSql.append("ORDER BY	\n");
		sSql.append(" SYOHINGRP.KR_SYOHIN_NO  \n"); // 商品行No

		return sSql.toString();
	}


	/**
	 * 事業所区分(Y以外)、商品区分から蔵元入力（蔵元データ／ディテール部）リストを取得するSQL
	 *
	 * @param shohinKbn 商品区分
	 * @param bindList
	 * @return sSql
	 */
	private String getKurachokuDataInDtListByKbnSql2(String shohinKbn, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT \n");
		//PbsSQL.setCommonColumns(sSql, "KURADT"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
		sSql.append("  SYOHINGRP.KR_SYOHIN_NO AS KURADEN_LINE_NO \n"); 			// 商品行No
		sSql.append(" ,SYOHINGRP.SHOHIN_CD_01 AS SHOHIN_CD \n");				// 商品コード
		sSql.append(" ,SYO.SHOHIN_NM_URIDEN AS SHOHIN_NM \n"); 					// 商品名
		sSql.append(" ,F_RTRIM(SYO.YOUKI_KIGO_NM1) AS YOUKI_KIGO_NM  \n");		// 容器名
		sSql.append(" ,F_RTRIM(SYO.HKAKAKU_SEISANSYA) AS HANBAI_TANKA  \n"); 	// 販売単価
		sSql.append(" ,F_RTRIM(SYOHINGRP.CP_KBN) AS CP_KBN  \n"); 				// ｷｬﾝﾍﾟｰﾝ対象区分

		sSql.append("FROM M_KBN_KURAC_SYOHIN_GRP SYOHINGRP \n");
		sSql.append("LEFT JOIN M_SYOHIN SYO ON (SYOHINGRP.KAISYA_CD = SYO.KAISYA_CD AND SYOHINGRP.SHOHIN_CD_01 = SYO.SHOHIN_CD) \n");

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" SYO.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 寄託者CD
		sSql.append("AND SYO.KTKSY_CD = ? \n");
		bindList.add(cus.getKtksyCd());

		// 蔵直商品ｸﾞﾙｰﾌﾟ区分
		sSql.append("AND SYOHINGRP.KR_SHOHIN_KBN = ? \n");
		bindList.add(shohinKbn);

		sSql.append("ORDER BY	\n");
		sSql.append(" SYOHINGRP.KR_SYOHIN_NO  \n"); // 商品行No

		return sSql.toString();
	}


	/**
	 * 事業所区分(K)の場合、商品区分、申込用紙表記順位から蔵元入力（蔵元データ／ディテール部）情報を取得するSQL(AJAX用)
	 *
	 * @param shohinKbn 商品区分
	 * @param lineNo 行No
	 * @param bindList
	 * @return sSql
	 */
	private String getKurachokuDataInDtListByKbnLineSql1(String shohinKbn, String lineNo, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT \n");
		//PbsSQL.setCommonColumns(sSql, "KURADT"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
		sSql.append("  SYOHINGRP.KR_SYOHIN_NO AS KURADEN_LINE_NO \n"); 				// 商品行No
		sSql.append(" ,SYOHINGRP.SHOHIN_CD_02 AS SHOHIN_CD \n"); 					// 商品コード
		sSql.append(" ,SYO.SHOHIN_NM_URIDEN AS SHOHIN_NM \n"); 						// 商品名
		sSql.append(" ,F_RTRIM(SYO.YOUKI_KIGO_NM1) AS YOUKI_KIGO_NM  \n"); 			// 容器名
		sSql.append(" ,F_RTRIM(SYOHINGRP.OTHER_TANKA_01) AS HANBAI_TANKA  \n"); 	// 販売単価
		sSql.append(" ,F_RTRIM(SYOHINGRP.CP_KBN) AS CP_KBN  \n"); 					// ｷｬﾝﾍﾟｰﾝ対象区分

		sSql.append("FROM M_KBN_KURAC_SYOHIN_GRP SYOHINGRP \n");
		sSql.append("LEFT JOIN M_SYOHIN SYO ON (SYOHINGRP.KAISYA_CD = SYO.KAISYA_CD AND SYOHINGRP.SHOHIN_CD_02 = SYO.SHOHIN_CD) \n");

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" SYO.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 寄託者CD
		sSql.append("AND SYO.KTKSY_CD = ? \n");
		bindList.add(cus.getKtksyCd());

		// 蔵直商品ｸﾞﾙｰﾌﾟ区分
		sSql.append("AND SYOHINGRP.KR_SHOHIN_KBN = ? \n");
		bindList.add(shohinKbn);

		// 申込用紙表記順位
		sSql.append("AND SYOHINGRP.KR_SYOHIN_NO = ? \n");
		bindList.add(lineNo);

		sSql.append("ORDER BY	\n");
		sSql.append(" SYOHINGRP.KR_SYOHIN_NO  \n"); // 商品行No

		return sSql.toString();
	}


	/**
	 * 事業所区分(K以外)、商品区分、申込用紙表記順位から蔵元入力（蔵元データ／ディテール部）情報を取得するSQL(AJAX用)
	 *
	 * @param shohinKbn 商品区分
	 * @param lineNo 行No
	 * @param bindList
	 * @return sSql
	 */
	private String getKurachokuDataInDtListByKbnLineSql2(String shohinKbn, String lineNo, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT \n");
		//PbsSQL.setCommonColumns(sSql, "KURADT"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
		sSql.append("  SYOHINGRP.KR_SYOHIN_NO AS KURADEN_LINE_NO \n"); 				// 商品行No
		sSql.append(" ,SYOHINGRP.SHOHIN_CD_01 AS SHOHIN_CD \n");					// 商品コード
		sSql.append(" ,SYO.SHOHIN_NM_URIDEN AS SHOHIN_NM \n"); 						// 商品名
		sSql.append(" ,F_RTRIM(SYO.YOUKI_KIGO_NM1) AS YOUKI_KIGO_NM  \n");			// 容器名
		sSql.append(" ,F_RTRIM(SYO.HKAKAKU_SEISANSYA) AS HANBAI_TANKA  \n"); 		// 販売単価
		sSql.append(" ,F_RTRIM(SYOHINGRP.CP_KBN) AS CP_KBN  \n"); 					// ｷｬﾝﾍﾟｰﾝ対象区分

		sSql.append("FROM M_KBN_KURAC_SYOHIN_GRP SYOHINGRP \n");
		sSql.append("LEFT JOIN M_SYOHIN SYO ON (SYOHINGRP.KAISYA_CD = SYO.KAISYA_CD AND SYOHINGRP.SHOHIN_CD_01 = SYO.SHOHIN_CD) \n");

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" SYO.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 寄託者CD
		sSql.append("AND SYO.KTKSY_CD = ? \n");
		bindList.add(cus.getKtksyCd());

		// 蔵直商品ｸﾞﾙｰﾌﾟ区分
		sSql.append("AND SYOHINGRP.KR_SHOHIN_KBN = ? \n");
		bindList.add(shohinKbn);

		// 申込用紙表記順位
		sSql.append("AND SYOHINGRP.KR_SYOHIN_NO = ? \n");
		bindList.add(lineNo);

		sSql.append("ORDER BY	\n");
		sSql.append(" SYOHINGRP.KR_SYOHIN_NO  \n"); // 商品行No

		return sSql.toString();
	}

	/**
	 * 申込み受付Noをキーとして蔵元ﾃﾞｰﾀ_ﾍｯﾀﾞｰを検索
	 *
	 * @param moshikomiNo 申込み受付No
	 * @param bindList
	 * @return sSql
	 */
	private String getKurachokuDataInfoByMoshiNoSql(String moshikomiNo, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		sSql.append("SELECT \n");
		sSql.append("  KURAHD.MOUSIKOMI_NO AS MOUSIKOMI_NO \n"); 			// 申込み受付NO
		sSql.append(" ,KURAHD.KURADATA_NO AS KURADATA_NO \n"); 				// 蔵直ﾃﾞｰﾀ連番

		sSql.append("FROM T_KURAC_HD KURAHD \n");

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" KURAHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 申込み受付NO
		sSql.append("AND KURAHD.MOUSIKOMI_NO = ? \n");
		bindList.add(moshikomiNo);

		return sSql.toString();
	}

	/**
	 * 申込み受付Noをキーとして蔵元ﾃﾞｰﾀ_ﾍｯﾀﾞｰを検索
	 *
	 * @param moshikomiNo 申込み受付No
	 * @param bindList
	 * @return sSql
	 */
	private String getKurachokuDataInfoByMoshiNoDateSql(String moshikomiNo, String startDate, String endDate, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		sSql.append("SELECT \n");
		sSql.append("  KURAHD.MOUSIKOMI_NO AS MOUSIKOMI_NO \n"); 			// 申込み受付NO

		sSql.append("FROM T_KURAC_HD KURAHD \n");

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" KURAHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 申込み受付NO
		sSql.append("AND KURAHD.MOUSIKOMI_NO = ? \n");
		bindList.add(moshikomiNo);

		// 申込み受付日
		sSql.append("AND KURAHD.UKETUKE_DT BETWEEN ? AND ?\n");
		bindList.add(startDate);
		bindList.add(endDate);

		return sSql.toString();
	}

	/**
	 * 縦線CDから縦線卸店情報を取得するSQL
	 *
	 * @param tatesenCd
	 * @param bindList
	 * @param isDeletedHeld
	 * @return sSql
	 */
	private String getTatesenInfoSearchSql(String tatesenCd, List<String> bindList, boolean isDeletedHeld) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("SELECT \n");
		PbsSQL.setCommonColumns(sSql, "ORSHD"); //<== 共通項目取得 セットされる文字列の最後にカンマが入ります。
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
		sSql.append(" ,ORS.TEN_NM1_URIDEN AS OROSITEN_NM \n"); 							// 卸店名
		sSql.append(" ,ORS.TEN_NM1_URIDEN AS OROSITEN_NM_LAST \n"); 					// 最終送荷先卸名
		sSql.append(" ,ORS1JI.TEN_NM1_URIDEN AS OROSITEN_NM_1JITEN \n"); 				// 特約店名
		sSql.append(" ,ORSDEP.TEN_NM1_URIDEN AS OROSITEN_NM_DEPO \n"); 					// デポ店名
		sSql.append(" ,ORS2JI.TEN_NM1_URIDEN AS OROSITEN_NM_2JITEN \n"); 				// 二次店名
		sSql.append(" ,ORS3JI.TEN_NM1_URIDEN AS OROSITEN_NM_3JITEN \n"); 				// 三次店名
		sSql.append(" ,ORS.TEN_NM1_JISYA AS OROSITEN_NM_RYAKU \n"); 					// 卸店名（略称）
		sSql.append(" ,ORS.TEN_NM1_JISYA AS OROSITEN_NM_LAST_RYAKU \n"); 				// 最終送荷先卸名（略称）
		sSql.append(" ,ORS1JI.TEN_NM1_JISYA AS OROSITEN_NM_1JITEN_RYAKU \n");			// 特約店名（略称）
		sSql.append(" ,ORSDEP.TEN_NM1_JISYA AS OROSITEN_NM_DEPO_RYAKU \n");				// デポ店名（略称）
		sSql.append(" ,ORS2JI.TEN_NM1_JISYA AS OROSITEN_NM_2JITEN_RYAKU \n");			// 二次店名（略称）
		sSql.append(" ,ORS3JI.TEN_NM1_JISYA AS OROSITEN_NM_3JITEN_RYAKU \n");			// 三次店名（略称）

		// 縦線
		sSql.append(" ,NVL(F_RTRIM(ORSHD.OROSITEN_CD_1JITEN),'')||NVL(F_RTRIM(ORSHD.OROSITEN_CD_DEPO),'')||NVL(F_RTRIM(ORSHD.OROSITEN_CD_2JITEN),'')||NVL(F_RTRIM(ORSHD.OROSITEN_CD_3JITEN),'') AS TATESEN_LINE \n"); // 縦線ライン

		sSql.append("FROM M_OROSISYOSAI_HD ORSHD \n");	// 卸店詳細マスターヘッダー部

		sSql.append("LEFT JOIN M_OROSITEN ORS ON (ORSHD.KAISYA_CD = ORS.KAISYA_CD AND ORSHD.OROSITEN_CD_LAST = ORS.OROSITEN_CD) \n");				// 卸店マスター（最終送荷先卸店）
		sSql.append("LEFT JOIN M_OROSITEN ORS1JI ON (ORSHD.KAISYA_CD = ORS1JI.KAISYA_CD AND ORSHD.OROSITEN_CD_1JITEN = ORS1JI.OROSITEN_CD) \n");	// 卸店マスター（特約店）
		sSql.append("LEFT JOIN M_OROSITEN ORSDEP ON (ORSHD.KAISYA_CD = ORSDEP.KAISYA_CD AND ORSHD.OROSITEN_CD_DEPO = ORSDEP.OROSITEN_CD) \n");		// 卸店マスター（デポ店）
		sSql.append("LEFT JOIN M_OROSITEN ORS2JI ON (ORSHD.KAISYA_CD = ORS2JI.KAISYA_CD AND ORSHD.OROSITEN_CD_2JITEN = ORS2JI.OROSITEN_CD) \n");	// 卸店マスター（二次店）
		sSql.append("LEFT JOIN M_OROSITEN ORS3JI ON (ORSHD.KAISYA_CD = ORS3JI.KAISYA_CD AND ORSHD.OROSITEN_CD_3JITEN = ORS3JI.OROSITEN_CD) \n");	// 卸店マスター（三次店）

		// 会社コード
		sSql.append("WHERE \n");
		sSql.append(" ORSHD.KAISYA_CD = ? \n");
		bindList.add(cus.getKaisyaCd());

		// 縦線CD
		sSql.append("AND ORSHD.TATESN_CD = ? \n");
		bindList.add(tatesenCd);

//		// 削除済を含めない（利用可のみ）
//		if (!isDeletedHeld) {
//			sSql.append("AND ORSHD.RIYOU_KBN = ? \n");
//			bindList.add(AVAILABLE_KB_RIYO_KA);
//		}
		// 利用状態は1：仮登録、2：利用可能
		sSql.append("AND F_GET_RIYOU_JOTAI_FLG(?, ORSHD.NOUHIN_KAISI_DT, ORSHD.NOUHIN_SYURYO_DT, ORSHD.RIYOU_TEISI_DT) IN ('1', '2') \n");
		bindList.add(cus.getGymDate());

		return sSql.toString();
	}

}
