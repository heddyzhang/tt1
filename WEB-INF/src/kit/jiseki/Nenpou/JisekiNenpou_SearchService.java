package kit.jiseki.Nenpou;

import static fb.inf.pbs.IPbsConst.*;
import static kit.jiseki.Nenpou.IJisekiNenpou.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import kit.pop.ComPopupUtil;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComUserSession;
import fb.com.ComUtil;
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
public class JisekiNenpou_SearchService extends KitService {


	/** シリアルID */
	private static final long serialVersionUID = -706457154905572413L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JisekiNenpou_SearchService.class.getName();
	private static Category category__ = Category.getInstance(className__);

	// ============================================
	// 必要な変数はここに作成する。
	// ============================================
	private PbsDatabase db_ = null;


	/**
	 * コンストラクタ.
	 *
	 * @param cus
	 *            getComUserSession()を渡すこと。
	 * @param db_
	 *            呼び出すときにはgetDatabase()を渡すこと。
	 * @param isTran
	 *            isTransaction()を渡すこと。
	 * @param ae
	 *            getActionErrors()を渡すこと。
	 */
	public JisekiNenpou_SearchService(ComUserSession cus, PbsDatabase db_, boolean isTran,
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
	 * SQLで使う期間年月用の文字列を生成
	 *
	 * @param kikanCol   対象カラム
	 * @param kikanStart 期間開始年月YYYYMM
	 * @param kikanEnd   期間終了年月YYYYMM
	 * @return SQLで使う期間年月用の文字列
	 */
	private String getSqlStrRangeYyyymm(String kikanCol, String kikanStart, String kikanEnd) {
		String ret = "";

		if (!PbsUtil.isEmpty(kikanCol)) {
			ret = "(SUBSTR(" + kikanCol + ",1,6) >= '" + kikanStart + "' AND SUBSTR(" + kikanCol + ",1,6) <= '" + kikanEnd + "')";
		} else {
			ret = "(SUBSTR('',1,6) >= '' AND SUBSTR('',1,6) <= '')";
		}

		return ret;
	}


	/**
	 * 基準年月から差分年違いの年月を取得
	 *
	 * @param base 基準年月YYYYMM
	 * @param defY 差分年
	 * @return 基準年月から差分年違いの年月
	 */
	private String getDateDefYear(String base, int defY) {
		return (PbsUtil.getDateAddYear(base + "01", defY)).substring(0, 6);
	}




	/**
	 * SQLで使う集計用の文字列を生成
	 *
	 * @param kikanStart 期間開始年月YYYYMM
	 * @param kikanEnd   期間終了年月YYYYMM
	 * @param selNen     年フラグ
	 * @param selSyukei  集計単位
	 * @return SQLで使う集計用の文字列
	 */
	private String getSqlStrSum(String kikanStart, String kikanEnd, String selNen, String selSyukei) throws ParseException {
		String ret = "";

		int kikanMonth = Integer.parseInt(getKikanMonth(kikanStart, kikanEnd));
		String yyyymmTounen = kikanStart;
		String yyyymmZennen = getDateDefYear(kikanStart, -1);
		String numStr = "";
		String syukeiStr = "";
		String asStr = "";
		String formatStr = "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < kikanMonth; i++) {
			numStr = PbsUtil.getCode(Integer.toString(i + 1), 2);

			// 集計単位
			if (PbsUtil.isEqual(selSyukei, SEL_SYUKEI_KINGAKU)) { // 金額
				syukeiStr = "SOKINGAKU";
				formatStr = FORMAT_KINGAKU;
			} else if (PbsUtil.isEqual(selSyukei, SEL_SYUKEI_HONSU)) { // 本数
				syukeiStr = "SOBARA";
				formatStr = FORMAT_HONSU;
			} else if (PbsUtil.isEqual(selSyukei, SEL_SYUKEI_YOURYO)) { // 容量
				syukeiStr = "SOYOURYO";
				formatStr = FORMAT_YOURYO;
			}

			// 年フラグ
			if (PbsUtil.isEqual(selNen, SEL_NEN_TOUNEN)) { // 当年のみ
				// 当年
				asStr = "SUM_" + numStr + "_TOUNEN";
				sb.append(" ,TO_CHAR(SUM(CASE WHEN NENGETU='" + yyyymmTounen + "' THEN " + syukeiStr + " ELSE 0 END) OVER(PARTITION BY SHOHIN_CD),'" + formatStr + "')  AS " + asStr + " \n");
			} else if (PbsUtil.isEqual(selNen, SEL_NEN_TOUNEN_ZENNEN)) { // 当年前年
				// 当年
				asStr = "SUM_" + numStr + "_TOUNEN";
				sb.append(" ,TO_CHAR(SUM(CASE WHEN NENGETU='" + yyyymmTounen + "' THEN " + syukeiStr + " ELSE 0 END) OVER(PARTITION BY SHOHIN_CD),'" + formatStr + "')  AS " + asStr + " \n");
				// 前年
				asStr = "SUM_" + numStr + "_ZENNEN";
				sb.append(" ,TO_CHAR(SUM(CASE WHEN NENGETU='" + yyyymmZennen + "' THEN " + syukeiStr + " ELSE 0 END) OVER(PARTITION BY SHOHIN_CD),'" + formatStr + "')  AS " + asStr + " \n");
			}

			// 次月を取得
			yyyymmTounen = PbsUtil.getNextMonth(yyyymmTounen);
			yyyymmZennen = PbsUtil.getNextMonth(yyyymmZennen);
		}

		ret = sb.toString();

		return ret;
	}

	/**
	 * 期間から表示月数を取得
	 *
	 * @param kikanStart 期間開始年月YYYYMM
	 * @param kikanEnd 期間終了年月YYYYMM
	 * @return 表示月数
	 * @throws ParseException
	 */
	protected String getKikanMonth(String kikanStart, String kikanEnd) throws ParseException {
		int ret = 0;

		// 期間終了年月と期間開始年月の月差
		int diffM = ComUtil.differenceMonth(PbsUtil.String2DateString(kikanEnd + "01"), PbsUtil.String2DateString(kikanStart + "01"));

		ret = diffM + 1;

		return Integer.toString(ret);
	}




	/**
	 * 年報検索のSQL生成と実行
	 *
	 * @param searchForm
	 *            検索フォーム
	 * @return 検索結果
	 * @throws DataNotFoundException
	 * @throws ParseException
	 */
	public PbsRecord[] execute(JisekiNenpou_SearchForm searchForm)
			throws DataNotFoundException, ParseException {

		category__.info("年報検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("年報 >> 検索処理 >> SQL文生成");
		//String searchSql = getsqlSearch(searchForm,bindList);

		String kbnData = searchForm.getKbnData(); //データ区分取得
		String searchSql = "";					  //初期化

		// 出荷SQLor戻入SQL
		if (PbsUtil.isEqual(kbnData, SEL_DATA_SYUKA)) { // 出荷データ
			// 出荷データ
			searchSql = getsqlSearchSyuka(searchForm,bindList);

		} else if (PbsUtil.isEqual(kbnData, SEL_DATA_REINYU)) { // 戻入データ
			// 戻入データ
			searchSql = getsqlSearchReinyu(searchForm,bindList);
		}

		category__.info("年報 >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("年報 >> execute  ");
			db_.execute(iCc);
			records_ = db_.getPbsRecords(iCc,false); //false(最大行数リミット解除)

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
		category__.info("年報検索処理 終了");

		return records_;
	}



	/**
	 * 年報の出荷データ検索SQLを取得
	 *
	 * @param searchForm
	 * @param bindList
	 * @return sSql
	 * @throws ParseException
	 */
	private String getsqlSearchSyuka(JisekiNenpou_SearchForm searchForm, List<String> bindList) throws ParseException {

		String kikanCol = "SHD.SYUKA_DT"; // 出荷日
		String kikanStart = searchForm.getKikanStart(); // 期間開始日（当年）
		String kikanEnd = searchForm.getKikanEnd(); // 期間終了日（当年）

		String selNen = searchForm.getSelNen(); // 年フラグ
		String kikanStartZennen = getDateDefYear(kikanStart, -1); // 期間開始日（前年）
		String kikanEndZennen = getDateDefYear(kikanEnd, -1); // 期間終了日（前年）
		String selSyukei = searchForm.getSelSyukei(); // 集計単位

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
		sSql.append("WITH WORK AS (\n");
		sSql.append("SELECT                               \n");
		PbsSQL.setCommonColumns(sSql, "SDT"); 						//<== 共通項目取得 セットされる文字列の最後にカンマが入ります。

		sSql.append("  SDT.SHOHIN_CD 					AS SHOHIN_CD 	\n");// 商品CD
		sSql.append(" ,SDT.SHNNM_REPORT1 				AS SHOHIN_NM 	\n");// 商品名称（自社各帳票用(1))
		sSql.append(" ,SDT.YOUKI_NM_REPORT 				AS YOUKI_NM 	\n");// 容器名称（自社各帳票用)
		sSql.append(" ,SDT.CASE_IRISU 					AS IRISU 		\n");// 入数
		sSql.append(" ,SUBSTR(SHD.SYUKA_DT,1,6) 		AS NENGETU 		\n");// 年月(出荷日8桁の前6桁YYYYMM)
		sSql.append(" ,(CASE WHEN " + getSqlStrRangeYyyymm("SHD.SYUKA_DT",searchForm.getKikanStart(),searchForm.getKikanEnd()) + "THEN 1 ELSE 2 END) AS NENFLG 	\n");// 当年前年フラグ
		sSql.append(" ,SHD.SYUKA_DT 					AS SYUKA_DT 	\n");// 出荷日
		sSql.append(" ,SDT.SYUKA_ALL_BARA 				AS SOBARA 		\n");// 出荷数量本数計
		sSql.append(" ,SDT.SYUKA_HANBAI_KINGAKU 		AS SOKINGAKU 	\n");// 出荷金額計
		sSql.append(" ,SDT.SYUKA_SOUYOURYO 				AS SOYOURYO 	\n");// 出荷総容量


		// ****From句****
		sSql.append("FROM T_SYUKA_DT SDT\n");
		sSql.append("      LEFT JOIN T_SYUKA_HD SHD\n");
		sSql.append("      ON (SDT.KAISYA_CD = SHD.KAISYA_CD AND SDT.URIDEN_NO = SHD.URIDEN_NO)\n");
		sSql.append("      LEFT JOIN M_KBN_JISEKI_TANTO TAN\n");
		sSql.append("      ON (SHD.KAISYA_CD = TAN.KAISYA_CD AND SHD.TANTOSYA_CD = TAN.TANTO_CD)\n");
		sSql.append("      LEFT JOIN M_TOKUYAKUTEN TOK\n");
		sSql.append("      ON (SHD.KAISYA_CD = TOK.KAISYA_CD AND SHD.SMGP_TOKUYAKUTEN_CD = TOK.TOKUYAKUTEN_CD)\n");

		// ****Where句****

		// SQLのバインド変数に渡す値をbindListに設定する

		sSql.append("WHERE \n");

		// 会社コード
		sSql.append("  SHD.KAISYA_CD = ?        \n");
		bindList.add( cus.getKaisyaCd());

		//期間 ※AND(バインドのAND)
		if (PbsUtil.isEqual(selNen, SEL_NEN_TOUNEN)) { // 当年のみ
			sSql.append(" AND ("  + getSqlStrRangeYyyymm(kikanCol, kikanStart, kikanEnd) + ") \n");
		} else if (PbsUtil.isEqual(selNen, SEL_NEN_TOUNEN_ZENNEN)) { // 当年前年
			sSql.append(" AND ((" + getSqlStrRangeYyyymm(kikanCol, kikanStart, kikanEnd) + ") \n");
			sSql.append(" OR (" + getSqlStrRangeYyyymm(kikanCol, kikanStartZennen, kikanEndZennen) + ")) \n");
		}

		// 期間　別メソッドSqlStrRangeYyyymmを使用する
		//sSql.append(" AND SUBSTR(SHD.SYUKA_DT,1,6) >= ? AND SUBSTR(SHD.SYUKA_DT,1,6) <= ? \n");
		//bindList.add( searchForm.getSyukaDt());

		// フラグ判定 別メソッドSqlStrRangeYyyymmを使用する
		//sSql.append(" AND (CASE WHEN " + getSqlStrRangeYyyymm("SHD.SYUKA_DT",searchForm.getKikanStart(),searchForm.getKikanEnd())
		//			+ "THEN 1 ELSE 2 END) AS NENFLG \n");// フラグ判定

		// 総本数・総金額・総容量　(NULLではないデータだけを取得)
		sSql.append("     AND SDT.SYUKA_ALL_BARA IS NOT NULL 			\n");
		sSql.append("     AND SDT.SYUKA_HANBAI_KINGAKU  IS NOT NULL 	\n");
		sSql.append("     AND SDT.SYUKA_SOUYOURYO  IS NOT NULL 			\n");

		//テスト
		//sSql.append("WHERE SUBSTR(SHD.SYUKA_DT,1,6) >= '201506' AND SUBSTR(SHD.SYUKA_DT,1,6) <= '201508'\n");//期間
		//sSql.append("     AND SHD.SYUKA_SAKI_COUNTRY_CD = 'MMR'\n"); //輸出国
		//sSql.append("     AND SHD.JIS_CD LIKE '26%'\n"); //都道府県
		//sSql.append("     AND SHD.TANTOSYA_CD = '010101'\n"); //担当者
		//sSql.append("     AND TAN.JIGYOSYO_CD = '010000'\n"); //事業所
		//sSql.append("     AND TAN.KA_CD = '010100'\n");　//課
		//sSql.append("     AND SHD.TOKUYAKUTEN_CD = '000004'\n"); //特約店CD
		//sSql.append("     AND SHD.TATESN_CD = '00000215'\n"); //最終送荷先（縦線コード）
		//sSql.append("	    AND SHD.TATESN_CD LIKE '000002%'\n"); //卸店（縦線コード前6桁)

		// 事業所
		if (!PbsUtil.isEmpty(searchForm.getJigyosyoCd())){
			sSql.append("  AND TAN.JIGYOSYO_CD = ?      \n");
			bindList.add( searchForm.getJigyosyoCd()); // 事業所
		}

		// 課
		if (!PbsUtil.isEmpty(searchForm.getKaCd())){
			sSql.append("  AND TAN.KA_CD = ?      \n");
			bindList.add( searchForm.getKaCd()); // 課
		}

		// 担当者
		if (!PbsUtil.isEmpty(searchForm.getTantosyaCd())){
			sSql.append("  AND SHD.TANTOSYA_CD = ?      \n");
			bindList.add( searchForm.getTantosyaCd()); // 担当者
		}

		// 都道府県区分  JISCDの前2ケタを前方一致で引き当てるのだ
		if (!PbsUtil.isEmpty(searchForm.getCountryCd())){
			sSql.append("  AND SHD.JIS_CD LIKE ?      \n");
			bindList.add(ComPopupUtil.getwildCardRight(searchForm.getCountryCd())); // 都道府県
		}

		// 輸出国
		if (!PbsUtil.isEmpty(searchForm.getSyukaSakiCountryCd())){
			sSql.append("  AND SYUKA_SAKI_COUNTRY_CD = ?      \n");
			bindList.add( searchForm.getSyukaSakiCountryCd()); // 出荷先国
		}


		// 全国卸or特約店コードor最終送荷先(縦線コード）
		if (!PbsUtil.isEmpty(searchForm.getZenkokuOrosi())){
			// 全国卸CD毎に該当した特約店CDを取得
			sSql.append(" AND TOK.TOKUYAKUTEN_CD IN(SELECT DISTINCT TOK.TOKUYAKUTEN_CD \n");
			sSql.append(" 							FROM  M_TOKUYAKUTEN TOK \n");
			sSql.append(" 							WHERE HJISEKI_SYUKEI_CD = ?)\n");
			bindList.add( searchForm.getZenkokuOrosi()); // 全国卸

		  } else if
			(!PbsUtil.isEmpty(searchForm.getTokuyakutenCd())){
				sSql.append("  AND SHD.TOKUYAKUTEN_CD      = ?      \n");
				bindList.add( searchForm.getTokuyakutenCd());  // 特約店
		  } else if
		 	(!PbsUtil.isEmpty(searchForm.getOrositenCdLast())){
				sSql.append("  AND SHD.TATESN_CD      = ?      \n");
				bindList.add( searchForm.getOrositenCdLast()); //(縦線コード)

		}


		// 特約店コード
//		if (!PbsUtil.isEmpty(searchForm.getTokuyakutenCd())){
//			sSql.append("  AND SHD.TOKUYAKUTEN_CD      = ?      \n");
//			bindList.add( searchForm.getTokuyakutenCd());  // 特約店
//		  } else if
//			(!PbsUtil.isEmpty(searchForm.getZenkokuOrosi())){
//				sSql.append("  AND TOK.TOKUYAKUTEN_CD  = ?      \n");
//				bindList.add( searchForm.getZenkokuOrosi()); // 全国卸→販売実績集計CDの為
//		}

		// 最終送荷先(縦線コード)
//		if (!PbsUtil.isEmpty(searchForm.getTatesnCd())){
//			sSql.append("  AND SHD.TATESN_CD      = ?      \n");
//			bindList.add( searchForm.getTatesnCd()); //(縦線コード)
//		}


		// 卸店  TATESN_CDの前6ケタを前方一致で引き当てるのだ
		if (!PbsUtil.isEmpty(searchForm.getOrositenCd())){
			sSql.append("  AND SHD.TATESN_CD LIKE ?      \n");
			bindList.add(ComPopupUtil.getwildCardRight(searchForm.getOrositenCd())); // 卸店（縦線コード前6桁）
		}

		sSql.append(")\n");

		sSql.append("SELECT\n");

		sSql.append("  DISTINCT SHOHIN_CD																			         \n");
		sSql.append(" ,FIRST_VALUE(SHOHIN_NM IGNORE NULLS) OVER(PARTITION BY SHOHIN_CD ORDER BY SYUKA_DT DESC) 	AS SHOHIN_NM \n");// 商品名
		sSql.append(" ,FIRST_VALUE(YOUKI_NM IGNORE NULLS) OVER(PARTITION BY SHOHIN_CD ORDER BY SYUKA_DT DESC) 	AS YOUKI_NM  \n");// 容器名
		sSql.append(" ,FIRST_VALUE(IRISU IGNORE NULLS) OVER(PARTITION BY SHOHIN_CD ORDER BY SYUKA_DT DESC) 		AS IRISU	 \n");// 入数

		if (PbsUtil.isEqual(selSyukei, SEL_SYUKEI_KINGAKU)) {
			sSql.append(" ,TO_CHAR(SUM(CASE WHEN NENFLG= ? THEN SOKINGAKU ELSE 0 END) OVER(PARTITION BY SHOHIN_CD),'" + FORMAT_KINGAKU + "') AS SUM_TOUNEN \n");// 当年累計(総金額)
			sSql.append(" ,TO_CHAR(SUM(CASE WHEN NENFLG= ? THEN SOKINGAKU ELSE 0 END) OVER(PARTITION BY SHOHIN_CD),'" + FORMAT_KINGAKU + "') AS SUM_ZENNEN \n");// 前年累計(総金額)
		 } else if
		 	(PbsUtil.isEqual(selSyukei, SEL_SYUKEI_HONSU)) {
			 sSql.append(" ,TO_CHAR(SUM(CASE WHEN NENFLG= ? THEN SOBARA ELSE 0 END) OVER(PARTITION BY SHOHIN_CD),'" + FORMAT_HONSU + "') AS SUM_TOUNEN \n");// 当年累計(総本数)
			 sSql.append(" ,TO_CHAR(SUM(CASE WHEN NENFLG= ? THEN SOBARA ELSE 0 END) OVER(PARTITION BY SHOHIN_CD),'" + FORMAT_HONSU + "') AS SUM_ZENNEN \n");// 前年累計(総本数)
		  } else if
		  (PbsUtil.isEqual(selSyukei, SEL_SYUKEI_YOURYO)) {
				 sSql.append(" ,TO_CHAR(SUM(CASE WHEN NENFLG= ? THEN SOYOURYO ELSE 0 END) OVER(PARTITION BY SHOHIN_CD),'" + FORMAT_YOURYO + "') AS SUM_TOUNEN \n");// 当年累計(総容量)
				 sSql.append(" ,TO_CHAR(SUM(CASE WHEN NENFLG= ? THEN SOYOURYO ELSE 0 END) OVER(PARTITION BY SHOHIN_CD),'" + FORMAT_YOURYO + "') AS SUM_ZENNEN \n");// 前年累計(総容量)
		}

		bindList.add( SEL_NEN_TOUNEN ); //(当年用フラグ）
		bindList.add( SEL_NEN_TOUNEN_ZENNEN ); //(当年前年用フラグ）


		sSql.append(getSqlStrSum(kikanStart, kikanEnd, selNen, selSyukei)); //指定された（期間,年,集計単位）に応じた集計をする。


		sSql.append("FROM WORK\n");


		// **GroupBy 集計(商品CD)**　SQL内で集計済み
		//sSql.append("GROUP BY SDT.SHOHIN_CD,SUBSTR(SHD.SYUKA_DT,1,6)\n");

		// **OrderBy ソート**
		sSql.append("ORDER BY SHOHIN_CD\n");


		return sSql.toString();
	}

	/**
		 * 年報の戻入データ検索SQLを取得
		 *
		 * @param searchForm
		 * @param bindList
		 * @return sSql
		 * @throws ParseException
		 */
		private String getsqlSearchReinyu(JisekiNenpou_SearchForm searchForm, List<String> bindList) throws ParseException {

			String kikanCol = "RHD.UKEIRE_DT"; // 受入日付
			String kikanStart = searchForm.getKikanStart(); // 期間開始日（当年）
			String kikanEnd = searchForm.getKikanEnd(); // 期間終了日（当年）

			String selNen = searchForm.getSelNen(); // 年フラグ
			String kikanStartZennen = getDateDefYear(kikanStart, -1); // 期間開始日（前年）
			String kikanEndZennen = getDateDefYear(kikanEnd, -1); // 期間終了日（前年）
			String selSyukei = searchForm.getSelSyukei(); // 集計単位

			StringBuilder sSql = new StringBuilder(CHAR_LF);

			// ユーザー情報
			ComUserSession cus = getComUserSession();

			// F_RTRIMを書ける理由：NULL更新をやめたため表示するときに空白１文字をトリムする
			sSql.append("WITH WORK AS (\n");
			sSql.append("SELECT                               \n");
			PbsSQL.setCommonColumns(sSql, "RDT"); 						//<== 共通項目取得 セットされる文字列の最後にカンマが入ります。

			sSql.append("  RDT.SHOHIN_CD 					AS SHOHIN_CD 	\n");// 商品CD
			sSql.append(" ,RDT.SHNNM_REPORT1 				AS SHOHIN_NM 	\n");// 商品名称（自社各帳票用(1))
			sSql.append(" ,RDT.YOUKI_NM_REPORT 				AS YOUKI_NM 	\n");// 容器名称（自社各帳票用)
			sSql.append(" ,RDT.CASE_IRISU 					AS IRISU 		\n");// 入数
			sSql.append(" ,SUBSTR(RHD.UKEIRE_DT,1,6) 		AS NENGETU 		\n");// 年月(出荷日8桁の前6桁YYYYMM)
			sSql.append(" ,(CASE WHEN " + getSqlStrRangeYyyymm("RHD.UKEIRE_DT",searchForm.getKikanStart(),searchForm.getKikanEnd()) + "THEN 1 ELSE 2 END) AS NENFLG 	\n");// 当年前年フラグ
			sSql.append(" ,RHD.UKEIRE_DT 					AS UKEIRE_DT 	\n");// 受入日付
			sSql.append(" ,RDT.UKEIRE_ALL_BARA 				AS SOBARA 		\n");// 受入数量本数計
			sSql.append(" ,RDT.UKEIRE_KINGAKU		 		AS SOKINGAKU 	\n");// 受入金額計
			sSql.append(" ,RDT.SYUKA_SOUYOURYO 				AS SOYOURYO 	\n");// 受入容量(L)出荷総容量


			// ****From句****
			sSql.append("FROM T_REINYU_DT RDT\n");
			sSql.append("      LEFT JOIN T_REINYU_HD RHD\n");
			sSql.append("      ON (RDT.KAISYA_CD = RHD.KAISYA_CD AND RDT.REINYUDEN_NO = RHD.REINYUDEN_NO)\n");
			sSql.append("      LEFT JOIN M_KBN_JISEKI_TANTO TAN\n");
			sSql.append("      ON (RHD.KAISYA_CD = TAN.KAISYA_CD AND RHD.TANTOSYA_CD = TAN.TANTO_CD)\n");
			sSql.append("      LEFT JOIN M_OROSISYOSAI_HD OHD\n");
			sSql.append("      ON (RHD.KAISYA_CD = OHD.KAISYA_CD AND RHD.TATESN_CD = OHD.TATESN_CD)\n");
			sSql.append("      LEFT JOIN M_TOKUYAKUTEN TOK\n");
			sSql.append("      ON (OHD.KAISYA_CD = TOK.KAISYA_CD AND OHD.TOKUYAKUTEN_CD_SMGP = TOK.TOKUYAKUTEN_CD)\n");

			// ****Where句****

			// SQLのバインド変数に渡す値をbindListに設定する

			sSql.append("WHERE \n");

			// 会社コード
			sSql.append("  RHD.KAISYA_CD = ?        \n");
			bindList.add( cus.getKaisyaCd());

			//期間 ※AND(バインドのAND)
			if (PbsUtil.isEqual(selNen, SEL_NEN_TOUNEN)) { // 当年のみ
				sSql.append(" AND ("  + getSqlStrRangeYyyymm(kikanCol, kikanStart, kikanEnd) + ") \n");
			} else if (PbsUtil.isEqual(selNen, SEL_NEN_TOUNEN_ZENNEN)) { // 当年前年
				sSql.append(" AND ((" + getSqlStrRangeYyyymm(kikanCol, kikanStart, kikanEnd) + ") \n");
				sSql.append(" OR (" + getSqlStrRangeYyyymm(kikanCol, kikanStartZennen, kikanEndZennen) + ")) \n");
			}

			// 期間　別メソッドSqlStrRangeYyyymmを使用する
			//sSql.append(" AND SUBSTR(RHD.UKEIRE_DT,1,6) >= ? AND SUBSTR(RHD.UKEIRE_DT,1,6) <= ? \n");
			//bindList.add( searchForm.getSyukaDt());

			// フラグ判定 別メソッドSqlStrRangeYyyymmを使用する
			//sSql.append(" AND (CASE WHEN " + getSqlStrRangeYyyymm("RHD.UKEIRE_DT",searchForm.getKikanStart(),searchForm.getKikanEnd())
			//			+ "THEN 1 ELSE 2 END) AS NENFLG \n");// フラグ判定

			// 総本数・総金額・総容量　(NULLではないデータだけを取得)
			sSql.append("     AND RDT.UKEIRE_ALL_BARA IS NOT NULL 			\n");
			sSql.append("     AND RDT.UKEIRE_KINGAKU  IS NOT NULL 	\n");
			sSql.append("     AND RDT.SYUKA_SOUYOURYO  IS NOT NULL 			\n");

			//テスト
			//sSql.append("WHERE SUBSTR(RHD.UKEIR_DT,1,6) >= '201506' AND SUBSTR(RHD.UKEIR_DT,1,6) <= '201508'\n");//期間
			//sSql.append("     AND RHD.SYUKA_SAKI_COUNTRY_CD = 'MMR'\n"); //輸出国
			//sSql.append("     AND RHD.JIS_CD LIKE '26%'\n"); //都道府県
			//sSql.append("     AND RHD.TANTOSYA_CD = '010101'\n"); //担当者
			//sSql.append("     AND TAN.JIGYOSYO_CD = '010000'\n"); //事業所
			//sSql.append("     AND TAN.KA_CD = '010100'\n");　//課
			//sSql.append("     AND RHD.TOKUYAKUTEN_CD = '000004'\n"); //特約店CD
			//sSql.append("     AND RHD.TATESN_CD = '00000215'\n"); //最終送荷先（縦線コード）
			//sSql.append("	    AND RHD.TATESN_CD LIKE '000002%'\n"); //卸店（縦線コード前6桁)

			// 事業所
			if (!PbsUtil.isEmpty(searchForm.getJigyosyoCd())){
				sSql.append("  AND TAN.JIGYOSYO_CD = ?      \n");
				bindList.add( searchForm.getJigyosyoCd()); // 事業所
			}

			// 課
			if (!PbsUtil.isEmpty(searchForm.getKaCd())){
				sSql.append("  AND TAN.KA_CD = ?      \n");
				bindList.add( searchForm.getKaCd()); // 課
			}

			// 担当者
			if (!PbsUtil.isEmpty(searchForm.getTantosyaCd())){
				sSql.append("  AND RHD.TANTOSYA_CD = ?      \n");
				bindList.add( searchForm.getTantosyaCd()); // 担当者
			}

			// 都道府県区分  JISCDの前2ケタを前方一致で引き当てるのだ
			if (!PbsUtil.isEmpty(searchForm.getCountryCd())){
				sSql.append("  AND RHD.JISCD LIKE ?      \n");
				bindList.add(ComPopupUtil.getwildCardRight(searchForm.getCountryCd())); // 都道府県
			}

			// 輸出国
			if (!PbsUtil.isEmpty(searchForm.getSyukaSakiCountryCd())){
				sSql.append("  AND SYUKA_SAKI_COUNTRY_CD = ?      \n");
				bindList.add( searchForm.getSyukaSakiCountryCd()); // 出荷先国
			}


			// 全国卸or特約店コードor最終送荷先(縦線コード）
			if (!PbsUtil.isEmpty(searchForm.getZenkokuOrosi())){
				// 全国卸CD毎に該当した特約店CDを取得
				sSql.append(" AND TOK.TOKUYAKUTEN_CD IN(SELECT DISTINCT TOK.TOKUYAKUTEN_CD \n");
				sSql.append(" 							FROM  M_TOKUYAKUTEN TOK \n");
				sSql.append(" 							WHERE HJISEKI_SYUKEI_CD = ?)\n");
				bindList.add( searchForm.getZenkokuOrosi()); // 全国卸

			  } else if
				(!PbsUtil.isEmpty(searchForm.getTokuyakutenCd())){
					sSql.append("  AND RHD.TOKUYAKUTEN_CD      = ?      \n");
					bindList.add( searchForm.getTokuyakutenCd());  // 特約店
			  } else if
			 	(!PbsUtil.isEmpty(searchForm.getOrositenCdLast())){
					sSql.append("  AND RHD.TATESN_CD      = ?      \n");
					bindList.add( searchForm.getOrositenCdLast()); //(縦線コード)

			}


			// 特約店コード
	//		if (!PbsUtil.isEmpty(searchForm.getTokuyakutenCd())){
	//			sSql.append("  AND RHD.TOKUYAKUTEN_CD      = ?      \n");
	//			bindList.add( searchForm.getTokuyakutenCd());  // 特約店
	//		  } else if
	//			(!PbsUtil.isEmpty(searchForm.getZenkokuOrosi())){
	//				sSql.append("  AND TOK.TOKUYAKUTEN_CD  = ?      \n");
	//				bindList.add( searchForm.getZenkokuOrosi()); // 全国卸→販売実績集計CDの為
	//		}

			// 最終送荷先(縦線コード)
	//		if (!PbsUtil.isEmpty(searchForm.getTatesnCd())){
	//			sSql.append("  AND RHD.TATESN_CD      = ?      \n");
	//			bindList.add( searchForm.getTatesnCd()); //(縦線コード)
	//		}


			// 卸店  TATESN_CDの前6ケタを前方一致で引き当てるのだ
			if (!PbsUtil.isEmpty(searchForm.getOrositenCd())){
				sSql.append("  AND RHD.TATESN_CD LIKE ?      \n");
				bindList.add(ComPopupUtil.getwildCardRight(searchForm.getOrositenCd())); // 卸店（縦線コード前6桁）
			}

			sSql.append(")\n");

			sSql.append("SELECT\n");

			sSql.append("  DISTINCT SHOHIN_CD																			         \n");
			sSql.append(" ,FIRST_VALUE(SHOHIN_NM IGNORE NULLS) OVER(PARTITION BY SHOHIN_CD ORDER BY UKEIRE_DT DESC) 	AS SHOHIN_NM \n");// 商品名
			sSql.append(" ,FIRST_VALUE(YOUKI_NM IGNORE NULLS) OVER(PARTITION BY SHOHIN_CD ORDER BY UKEIRE_DT DESC) 	AS YOUKI_NM  \n");// 容器名
			sSql.append(" ,FIRST_VALUE(IRISU IGNORE NULLS) OVER(PARTITION BY SHOHIN_CD ORDER BY UKEIRE_DT DESC) 		AS IRISU	 \n");// 入数

			if (PbsUtil.isEqual(selSyukei, SEL_SYUKEI_KINGAKU)) {
				sSql.append(" ,TO_CHAR(SUM(CASE WHEN NENFLG= ? THEN SOKINGAKU ELSE 0 END) OVER(PARTITION BY SHOHIN_CD),'" + FORMAT_KINGAKU + "') AS SUM_TOUNEN \n");// 当年累計(総金額)
				sSql.append(" ,TO_CHAR(SUM(CASE WHEN NENFLG= ? THEN SOKINGAKU ELSE 0 END) OVER(PARTITION BY SHOHIN_CD),'" + FORMAT_KINGAKU + "') AS SUM_ZENNEN \n");// 前年累計(総金額)
			 } else if
			 	(PbsUtil.isEqual(selSyukei, SEL_SYUKEI_HONSU)) {
				 sSql.append(" ,TO_CHAR(SUM(CASE WHEN NENFLG= ? THEN SOBARA ELSE 0 END) OVER(PARTITION BY SHOHIN_CD),'" + FORMAT_HONSU + "') AS SUM_TOUNEN \n");// 当年累計(総本数)
				 sSql.append(" ,TO_CHAR(SUM(CASE WHEN NENFLG= ? THEN SOBARA ELSE 0 END) OVER(PARTITION BY SHOHIN_CD),'" + FORMAT_HONSU + "') AS SUM_ZENNEN \n");// 前年累計(総本数)
			  } else if
			  (PbsUtil.isEqual(selSyukei, SEL_SYUKEI_YOURYO)) {
					 sSql.append(" ,TO_CHAR(SUM(CASE WHEN NENFLG= ? THEN SOYOURYO ELSE 0 END) OVER(PARTITION BY SHOHIN_CD),'" + FORMAT_YOURYO + "') AS SUM_TOUNEN \n");// 当年累計(総容量)
					 sSql.append(" ,TO_CHAR(SUM(CASE WHEN NENFLG= ? THEN SOYOURYO ELSE 0 END) OVER(PARTITION BY SHOHIN_CD),'" + FORMAT_YOURYO + "') AS SUM_ZENNEN \n");// 前年累計(総容量)
			}

			bindList.add( SEL_NEN_TOUNEN ); //(当年用フラグ）
			bindList.add( SEL_NEN_TOUNEN_ZENNEN ); //(当年前年用フラグ）


			sSql.append(getSqlStrSum(kikanStart, kikanEnd, selNen, selSyukei)); //指定された（期間,年,集計単位）に応じた集計をする。


			sSql.append("FROM WORK\n");


			// **GroupBy 集計(商品CD)**　SQL内で集計済み
			//sSql.append("GROUP BY SDT.SHOHIN_CD,SUBSTR(RHD.UKEIRE_DT,1,6)\n");

			// **OrderBy ソート**
			sSql.append("ORDER BY SHOHIN_CD\n");


			return sSql.toString();
		}





}
