package kit.jiseki.AriaList;

import static fb.inf.pbs.IPbsConst.*;
import static kit.jiseki.AriaList.IJisekiAriaList.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComUserSession;
import fb.com.exception.OverMaxDataException;
import fb.inf.KitService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.MaxRowsException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * データベースへ問い合わせを実行するビジネスロジッククラスです
 */
public class JisekiAriaList_SearchService extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = 6411688081205821496L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JisekiAriaList_SearchService.class.getName();
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
	public JisekiAriaList_SearchService(ComUserSession cus, PbsDatabase db_, boolean isTran,
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
	 * エリア事業所実績リストを検索のSQL生成と実行.
	 *
	 * @param searchForm
	 *            検索フォーム
	 * @return 検索結果
	 * @throws DataNotFoundException
	 * @throws OverMaxDataException
	 */
	public PbsRecord[] executeShiten(JisekiAriaList_SearchForm searchForm)
			throws DataNotFoundException {

		category__.info("エリア事業所実績リスト 検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("エリア事業所実績リスト >> 検索処理 >> SQL文生成");
		String searchSql = getSqlSearchSyukaByKbn(searchForm, bindList);

		category__.info("エリア事業所実績リスト >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true, searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}


		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("エリア事業所実績リスト >> execute  ");
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
		category__.info("エリア事業所実績リスト 検索処理 終了");

		return records_;
	}

	/**
	 * エリア課実績リストを検索のSQL生成と実行.
	 *
	 * @param searchForm
	 *            検索フォーム
	 * @return 検索結果
	 * @throws DataNotFoundException
	 * @throws OverMaxDataException
	 */
	public PbsRecord[] executeKa(JisekiAriaList_SearchForm searchForm)
			throws DataNotFoundException {

		category__.info("エリア課実績リスト 検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("エリア課実績リスト >> 検索処理 >> SQL文生成");
		String searchSql = getSqlSearchSyukaByKbn(searchForm, bindList);

		category__.info("エリア課実績リスト >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}


		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("エリア課実績リスト >> execute  ");
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
			// マスタに課の情報がなくても、事業所の情報があった場合表示したい
			 category__.warn(e.getMessage());
			// throw e;
		}
		category__.info("エリア課実績リスト 検索処理 終了");

		return records_;
	}

	/**

	/**
	 * エリア実績リストの検索SQLを取得
	 *
	 * @param searchForm
	 * @param bindList
	 * @return sSql
	 */
	private String getSqlSearchSyukaByKbn(JisekiAriaList_SearchForm searchForm, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// 集計コラム

		sSql.append(" SELECT                                           \n");
		sSql.append("   A.CD_NM                                        \n");
		sSql.append(" , A.BIKOU_B                                      \n");
		sSql.append(" , A.CODE_CD                                      \n");

		// 集計カラムを取得
		String syukeiColumn = getSyukeiColumn(searchForm);

		// 日計
		sSql.append(" , SUM(CASE WHEN C.SYUKA_DT = ?                               THEN " + syukeiColumn + " ELSE 0 END ) AS NIKEI                 \n");
		// 指定日を設定
		bindList.add(searchForm.getShiteibi());

		// 当月累計
		sSql.append(" , SUM(CASE WHEN " + getSyukeiCondToki(searchForm, SYUKEI_KIKAN_TOGETSU) + " THEN " + syukeiColumn + " ELSE 0 END ) AS TOGETSU_RUIKEI        \n");

		// 前年同日
		sSql.append(" , SUM(CASE WHEN C.SYUKA_DT >= ? AND C.SYUKA_DT <= ? THEN " + syukeiColumn + " ELSE 0 END ) AS ZENNEN_DOJITSU        \n");
		// 前年同日月初を設定
		bindList.add(searchForm.getZennenDojitsuGetsusyo());
		// 前年同日を設定
		bindList.add(searchForm.getZennenDojitsu());

		// 前年月末
		sSql.append(" , SUM(CASE WHEN " + getSyukeiCondZenki(searchForm, SYUKEI_KIKAN_TOGETSU) + " THEN " + syukeiColumn + " ELSE 0 END ) AS ZENNEN_GETSUMATSU     \n");

		// 前前年月末
		sSql.append(" , SUM(CASE WHEN C.SYUKA_DT >= ? AND C.SYUKA_DT <= ? THEN " + syukeiColumn + " ELSE 0 END ) AS ZENZENNEN_GETSUMATSU  \n");
		// 前前年同日月初を設定
		bindList.add(searchForm.getZenzennenDojitsuGetsusyo());
		// 前前年同日月末を設定
		bindList.add(searchForm.getZenzennenDojitsuGetsumatsu());

		// 当期(10月)
		sSql.append(" , SUM(CASE WHEN " + getSyukeiCondToki(searchForm, SYUKEI_KIKAN_JYUGATSU) + " THEN " + syukeiColumn + " ELSE 0 END ) AS JYUGATSU_TOKI         \n");
		// 前期(10月)
		sSql.append(" , SUM(CASE WHEN " + getSyukeiCondZenki(searchForm, SYUKEI_KIKAN_JYUGATSU) + " THEN " + syukeiColumn + " ELSE 0 END ) AS JYUGATSU_ZENKI        \n");

		// 当期(1月)
		sSql.append(" , SUM(CASE WHEN " + getSyukeiCondToki(searchForm, SYUKEI_KIKAN_ICHIGATSU) + " THEN " + syukeiColumn + " ELSE 0 END ) AS ICHIGATSU_TOKI        \n");
		// 前期(1月)
		sSql.append(" , SUM(CASE WHEN " + getSyukeiCondZenki(searchForm, SYUKEI_KIKAN_ICHIGATSU) + " THEN " + syukeiColumn + " ELSE 0 END ) AS ICHIGATSU_ZENKI       \n");

		// ****From句****
		sSql.append(" FROM M_CD_MEISYOU A                                                   \n");
		// 事業所の場合
		if (SEARCH_KBN_SITEN.equals(searchForm.getSearchKbn())) {
			sSql.append("   LEFT JOIN M_KBN_JISEKI_TANTO B ON (A.CODE_CD = B.JIGYOSYO_CD)   \n");
		} else {
			sSql.append("   LEFT JOIN M_KBN_JISEKI_TANTO B ON (A.CODE_CD = B.KA_CD)         \n");
		}
		sSql.append("   LEFT JOIN M_OROSISYOSAI_HD E ON (B.KAISYA_CD = E.KAISYA_CD AND B.TANTO_CD = E.TANTOSYA_CD) \n");
		sSql.append("   LEFT JOIN                                                                                  \n");
		sSql.append("     (SELECT H.TANTOSYA_CD,                                                                   \n");
		sSql.append("        H.KAISYA_CD,                                                                          \n");
		sSql.append("        H.TATESN_CD,                                                                          \n");
		sSql.append("        H.SYUKA_DT,                                                                           \n");
		sSql.append("        D.SYUKA_HANBAI_KINGAKU,                                                               \n");
		sSql.append("        D.SYUKA_SOUYOURYO                                                                     \n");
		sSql.append("      FROM T_SYUKA_HD H                                                                       \n");
		sSql.append("        INNER JOIN T_SYUKA_DT D ON (H.KAISYA_CD = D.KAISYA_CD AND H.URIDEN_NO = D.URIDEN_NO   \n");
		sSql.append("                                    AND D.BUPIN_KBN IN ('0','1','2'))                         \n");
		sSql.append("      ) C ON (E.KAISYA_CD = C.KAISYA_CD AND E.TATESN_CD = C.TATESN_CD)                        \n");

		// ****Where句****
		if (SEARCH_KBN_SITEN.equals(searchForm.getSearchKbn())) {
			// 事業所の場合
			sSql.append(" WHERE A.KBN_ID = 'KBN_HJS_SITEN'                                      \n");
		} else {
			// 課の場合
			sSql.append(" WHERE A.KBN_ID = 'KBN_HJS_KA' AND A.BIKOU_B = '" + AREA_KBN_KA + "' \n");
		}

		// **GroupBy 集計(エリアコード)
		sSql.append(" GROUP BY A.CODE_CD,A.CD_NM,A.BIKOU_A,A.BIKOU_B                        \n");

		// **OrderBy ソート**
		sSql.append(" ORDER BY TO_NUMBER(A.BIKOU_A)                                         \n");

		return sSql.toString();
	}

	/**
	 * SQLで使う集計用のカラム文字列を生成
	 *
	 * @param searchForm 検索条件用のフォーム
	 * @return SQLで使う集計用のカラム文字列
	 */
	private String getSyukeiColumn(JisekiAriaList_SearchForm searchForm){
		String syukeiColumn = "";
		if (RDO_TANI_KINGAKU.equals(searchForm.getHyoujiTanyi())) {
			syukeiColumn = "C.SYUKA_HANBAI_KINGAKU / 1000";
		} else {
			syukeiColumn = "C.SYUKA_SOUYOURYO / 180";
		}
		return syukeiColumn;
	}

	/**
	 * SQLで使う集計用期間の当期条件文字列を生成
	 *
	 * @param searchForm 検索条件用のフォーム
	 * @param searchForm 集計期間区分
	 * @return SQLで集計用期間の当期条件文字列
	 */
	private String getSyukeiCondToki(JisekiAriaList_SearchForm searchForm, String syukeiKiKan){
		// 期間開始日
		String kikanStartDate = "";
		// 期間終了日(指定日を設定)
		String kikanEndDate = searchForm.getShiteibi();

		if (SYUKEI_KIKAN_TOGETSU.equals(syukeiKiKan)) {
			// 当月の場合、指定日月初を設定
			kikanStartDate = searchForm.getShiteibiGetsusyo();
		} else if (SYUKEI_KIKAN_JYUGATSU.equals(syukeiKiKan)) {
			// 10月の場合、今期初を設定
			kikanStartDate = searchForm.getKonkisyo();
		} else {
			// 1月の場合、今年初を設定
			kikanStartDate = searchForm.getKotoshisyo();
		}

		// 当期条件文字列
		String syukeiCond = "C.SYUKA_DT >= '"  + kikanStartDate + "' AND C.SYUKA_DT <= '" + kikanEndDate + "'";

		return syukeiCond;
	}

	/**
	 * SQLで使う集計用期間の前期条件文字列を生成
	 *
	 * @param searchForm 検索条件用のフォーム
	 * @param searchForm 集計期間区分
	 * @return SQLで集計用期間の前期条件文字列
	 */
	private String getSyukeiCondZenki(JisekiAriaList_SearchForm searchForm, String syukeiKiKan){
		// 期間開始日
		String kikanStartDate = "";
		// 期間終了日(前年同日月末を設定)
		String kikanEndDate = searchForm.getZennenDojitsuGetsumatsu();

		if (SYUKEI_KIKAN_TOGETSU.equals(syukeiKiKan)) {
			// 当月の場合、前年同日月初を設定
			kikanStartDate = searchForm.getZennenDojitsuGetsusyo();
		} else if (SYUKEI_KIKAN_JYUGATSU.equals(syukeiKiKan)) {
			// 10月の場合、前期初を設定
			kikanStartDate = searchForm.getZenkisyo();
		} else {
			// 1月の場合、前年初を設定
			kikanStartDate = searchForm.getZennensyo();
		}

		// 当期条件文字列
		String syukeiCond = "C.SYUKA_DT >= '"  + kikanStartDate + "' AND C.SYUKA_DT <= '" + kikanEndDate + "'";

		return syukeiCond;
	}

	/**
	 * DBから取得したエリア支店情報から実績用合計リストを変換します。
	 *
	 * @param jisekiAriaList DBから取得した実績情報リスト
	 * @return jisekiAriaList 実績用合計リスト
	 */
	public JisekiAriaList convertToGokeiList(JisekiAriaList jisekiAriaList) {
		// 合計用リスト
		JisekiAriaList jisekiAriaGokeiList = new JisekiAriaList();
		// 東日本計合計行
		JisekiAriaListRecord jsekiAriaHigashiGokeiRecord = new JisekiAriaListRecord();
		// 西日本計合計行
		JisekiAriaListRecord jisekiAriaNishiGokeiRecord = new JisekiAriaListRecord();
		// 支店営業合計行
		JisekiAriaListRecord jisekiAriaSitenGokeiRecord = new JisekiAriaListRecord();
		// 合計行
		JisekiAriaListRecord jisekiAriaGokeiRecord = new JisekiAriaListRecord();
		// 総合計行
		JisekiAriaListRecord jisekiAriaSoGokeiRecord = new JisekiAriaListRecord();

		// 累計用変数の初期化
		BigDecimal sumNikei = BigDecimal.ZERO;					// 日計(累計用)
		BigDecimal sumTogetsuRuikei = BigDecimal.ZERO;			// 当月累計(累計用)
		BigDecimal sumZennenDojitsu = BigDecimal.ZERO;			// 前年同日(累計用)
		BigDecimal sumZennenGetsumatsu = BigDecimal.ZERO;		// 前年月末(累計用)
		BigDecimal sumZenzennenGetsumatsu = BigDecimal.ZERO;	// 前々年月末(累計用)
		BigDecimal sumJyugatsuToki = BigDecimal.ZERO;			// 当期(10月)(累計用)
		BigDecimal sumJyugatsuZenki = BigDecimal.ZERO;			// 前期(10月)(累計用)
		BigDecimal sumIchigatsuToki = BigDecimal.ZERO;			// 当期(1月)(累計用)
		BigDecimal sumIchigatsuZenki = BigDecimal.ZERO;			// 前期(1月)(累計用)

		// エリアリスト分の情報を繰り返し
		for (int i = 0; i < jisekiAriaList.size() - 1; i++) {
			// DBから取ったレコード
			JisekiAriaListRecord jisekiAriaRecord = (JisekiAriaListRecord)jisekiAriaList.get(i);
			// エリア別の実績情報分を合計用リストに追加
			jisekiAriaGokeiList.add(createDispRecord(jisekiAriaRecord));

			// 日計を累計
			sumNikei = sumNikei.add(new BigDecimal(jisekiAriaRecord.getNikei()));
			// 当月累計を累計
			sumTogetsuRuikei = sumTogetsuRuikei.add(new BigDecimal(jisekiAriaRecord.getTogetsuRuikei()));
			// 前年同日を累計
			sumZennenDojitsu = sumZennenDojitsu.add(new BigDecimal(jisekiAriaRecord.getZennenDojitsu()));
			// 前年月末を累計
			sumZennenGetsumatsu = sumZennenGetsumatsu.add(new BigDecimal(jisekiAriaRecord.getZennenGetsumatsu()));
			// 前々年月末を累計
			sumZenzennenGetsumatsu = sumZenzennenGetsumatsu.add(new BigDecimal(jisekiAriaRecord.getZenzennenGetsumatsu()));
			// 当期(10月)を累計
			sumJyugatsuToki = sumJyugatsuToki.add(new BigDecimal(jisekiAriaRecord.getJyugatsuToki()));
			// 前期(10月)を累計
			sumJyugatsuZenki = sumJyugatsuZenki.add(new BigDecimal(jisekiAriaRecord.getJyugatsuZenki()));
			// 当期(1月)を累計
			sumIchigatsuToki = sumIchigatsuToki.add(new BigDecimal(jisekiAriaRecord.getIchigatsuToki()));
			// 前期(1月)を累計
			sumIchigatsuZenki = sumIchigatsuZenki.add(new BigDecimal(jisekiAriaRecord.getIchigatsuZenki()));

			// 該当レコードの備考B
			String bikouB1 = jisekiAriaRecord.getBikouB();
			// 直後レコートの備考B
			String bikouB2 = ((JisekiAriaListRecord) jisekiAriaList.get(i + 1)).getBikouB();
			// 該当レコードの備考Bと直後レコートの備考Bを比較
			if (!PbsUtil.isEqual(bikouB1, bikouB2)) {
				switch (bikouB1){
				// 東日本の場合
				case AREA_KBN_HIGASHINIHON :
					// 東日本計合計行を作成
					jsekiAriaHigashiGokeiRecord.setAriaNm(HIGASHI_NIHON_KEI);
					// 日計の合計
					jsekiAriaHigashiGokeiRecord.setNikei(sumNikei.toString());
					// 当月累計の合計
					jsekiAriaHigashiGokeiRecord.setTogetsuRuikei(sumTogetsuRuikei.toString());
					// 前年同日の合計
					jsekiAriaHigashiGokeiRecord.setZennenDojitsu(sumZennenDojitsu.toString());
					// 前年月末の合計
					jsekiAriaHigashiGokeiRecord.setZennenGetsumatsu(sumZennenGetsumatsu.toString());
					// 前々年月末の合計
					jsekiAriaHigashiGokeiRecord.setZenzennenGetsumatsu(sumZenzennenGetsumatsu.toString());
					// 当期(10月)の合計
					jsekiAriaHigashiGokeiRecord.setJyugatsuToki(sumJyugatsuToki.toString());
					// 前期(10月)の合計
					jsekiAriaHigashiGokeiRecord.setJyugatsuZenki(sumJyugatsuZenki.toString());
					// 当期(1月)の合計
					jsekiAriaHigashiGokeiRecord.setIchigatsuToki(sumIchigatsuToki.toString());
					// 前期(1月)の合計
					jsekiAriaHigashiGokeiRecord.setIchigatsuZenki(sumIchigatsuZenki.toString());

					// 東日本計合計行を合計用リストに追加
					jisekiAriaGokeiList.add(createDispRecord(jsekiAriaHigashiGokeiRecord));

					break;
				// 西日本の場合
				case AREA_KBN_NISHINIHON :
					// 西日本計合計行を作成
					jisekiAriaNishiGokeiRecord.setAriaNm(NISHI_NIHON_KEI);
					// 日計の合計
					jisekiAriaNishiGokeiRecord.setNikei(sumNikei.toString());
					// 当月累計の合計
					jisekiAriaNishiGokeiRecord.setTogetsuRuikei(sumTogetsuRuikei.toString());
					// 前年同日の合計
					jisekiAriaNishiGokeiRecord.setZennenDojitsu(sumZennenDojitsu.toString());
					// 前年月末の合計
					jisekiAriaNishiGokeiRecord.setZennenGetsumatsu(sumZennenGetsumatsu.toString());
					// 前々年月末の合計
					jisekiAriaNishiGokeiRecord.setZenzennenGetsumatsu(sumZenzennenGetsumatsu.toString());
					// 当期(10月)の合計
					jisekiAriaNishiGokeiRecord.setJyugatsuToki(sumJyugatsuToki.toString());
					// 前期(10月)の合計
					jisekiAriaNishiGokeiRecord.setJyugatsuZenki(sumJyugatsuZenki.toString());
					// 当期(1月)の合計
					jisekiAriaNishiGokeiRecord.setIchigatsuToki(sumIchigatsuToki.toString());
					// 前期(1月)の合計
					jisekiAriaNishiGokeiRecord.setIchigatsuZenki(sumIchigatsuZenki.toString());

					// 支店営業合計行を作成
					jisekiAriaSitenGokeiRecord.setAriaNm(SHITEN_GOKEI);
					// 日計の合計
					jisekiAriaSitenGokeiRecord.setNikei(sumNikei.add(new BigDecimal(jsekiAriaHigashiGokeiRecord.getNikei())).toString());
					// 当月累計の合計
					jisekiAriaSitenGokeiRecord.setTogetsuRuikei(sumTogetsuRuikei.add(new BigDecimal(jsekiAriaHigashiGokeiRecord.getTogetsuRuikei())).toString());
					// 前年同日の合計
					jisekiAriaSitenGokeiRecord.setZennenDojitsu(sumZennenDojitsu.add(new BigDecimal(jsekiAriaHigashiGokeiRecord.getZennenDojitsu())).toString());
					// 前年月末の合計
					jisekiAriaSitenGokeiRecord.setZennenGetsumatsu(sumZennenGetsumatsu.add(new BigDecimal(jsekiAriaHigashiGokeiRecord.getZennenGetsumatsu())).toString());
					// 前々年月末の合計
					jisekiAriaSitenGokeiRecord.setZenzennenGetsumatsu(sumZenzennenGetsumatsu.add(new BigDecimal(jsekiAriaHigashiGokeiRecord.getZenzennenGetsumatsu())).toString());
					// 当期(10月)の合計
					jisekiAriaSitenGokeiRecord.setJyugatsuToki(sumJyugatsuToki.add(new BigDecimal(jsekiAriaHigashiGokeiRecord.getJyugatsuToki())).toString());
					// 前期(10月)の合計
					jisekiAriaSitenGokeiRecord.setJyugatsuZenki(sumJyugatsuZenki.add(new BigDecimal(jsekiAriaHigashiGokeiRecord.getJyugatsuZenki())).toString());
					// 当期(1月)の合計
					jisekiAriaSitenGokeiRecord.setIchigatsuToki(sumIchigatsuToki.add(new BigDecimal(jsekiAriaHigashiGokeiRecord.getIchigatsuToki())).toString());
					// 前期(1月)の合計
					jisekiAriaSitenGokeiRecord.setIchigatsuZenki(sumIchigatsuZenki.add(new BigDecimal(jsekiAriaHigashiGokeiRecord.getIchigatsuZenki())).toString());

					// 西日本計合計行を合計用リストに追加
					jisekiAriaGokeiList.add(createDispRecord(jisekiAriaNishiGokeiRecord));
					// 支店営業合計行を合計用リストに追加
					jisekiAriaGokeiList.add(createDispRecord(jisekiAriaSitenGokeiRecord));

					break;
				// 小売の場合
				case AREA_KBN_KOURI :
					// 小売合計行を作成
					jisekiAriaGokeiRecord.setAriaNm(GOKEI);
					// 日計の合計
					jisekiAriaGokeiRecord.setNikei(sumNikei.add(new BigDecimal(jisekiAriaSitenGokeiRecord.getNikei())).toString());
					// 当月累計の合計
					jisekiAriaGokeiRecord.setTogetsuRuikei(sumTogetsuRuikei.add(new BigDecimal(jisekiAriaSitenGokeiRecord.getTogetsuRuikei())).toString());
					// 前年同日の合計
					jisekiAriaGokeiRecord.setZennenDojitsu(sumZennenDojitsu.add(new BigDecimal(jisekiAriaSitenGokeiRecord.getZennenDojitsu())).toString());
					// 前年月末の合計
					jisekiAriaGokeiRecord.setZennenGetsumatsu(sumZennenGetsumatsu.add(new BigDecimal(jisekiAriaSitenGokeiRecord.getZennenGetsumatsu())).toString());
					// 前々年月末の合計
					jisekiAriaGokeiRecord.setZenzennenGetsumatsu(sumZenzennenGetsumatsu.add(new BigDecimal(jisekiAriaSitenGokeiRecord.getZenzennenGetsumatsu())).toString());
					// 当期(10月)の合計
					jisekiAriaGokeiRecord.setJyugatsuToki(sumJyugatsuToki.add(new BigDecimal(jisekiAriaSitenGokeiRecord.getJyugatsuToki())).toString());
					// 前期(10月)の合計
					jisekiAriaGokeiRecord.setJyugatsuZenki(sumJyugatsuZenki.add(new BigDecimal(jisekiAriaSitenGokeiRecord.getJyugatsuZenki())).toString());
					// 当期(1月)の合計
					jisekiAriaGokeiRecord.setIchigatsuToki(sumIchigatsuToki.add(new BigDecimal(jisekiAriaSitenGokeiRecord.getIchigatsuToki())).toString());
					// 前期(1月)の合計
					jisekiAriaGokeiRecord.setIchigatsuZenki(sumIchigatsuZenki.add(new BigDecimal(jisekiAriaSitenGokeiRecord.getIchigatsuZenki())).toString());

					// 小売合計行を合計用リストに追加
					jisekiAriaGokeiList.add(createDispRecord(jisekiAriaGokeiRecord));

					break;
			}

			// 累計用変数のクリア
			sumNikei = BigDecimal.ZERO;					// 日計(累計用)
			sumTogetsuRuikei = BigDecimal.ZERO;			// 当月累計(累計用)
			sumZennenDojitsu = BigDecimal.ZERO;			// 前年同日(累計用)
			sumZennenGetsumatsu = BigDecimal.ZERO;		// 前年月末(累計用)
			sumZenzennenGetsumatsu = BigDecimal.ZERO;	// 前々年月末(累計用)
			sumJyugatsuToki = BigDecimal.ZERO;			// 当期(10月)(累計用)
			sumJyugatsuZenki = BigDecimal.ZERO;			// 前期(10月)(累計用)
			sumIchigatsuToki = BigDecimal.ZERO;			// 当期(1月)(累計用)
			sumIchigatsuZenki = BigDecimal.ZERO;		// 前期(1月)(累計用)
		}
	}
		// 最後のレコード
		JisekiAriaListRecord jisekiAriaLastRecord = (JisekiAriaListRecord)jisekiAriaList.get(jisekiAriaList.size() - 1);
		// 合計用リストに追加
		jisekiAriaGokeiList.add(createDispRecord(jisekiAriaLastRecord));

		// 未納税の場合
		if (AREA_KBN_MINOZEI.equals(jisekiAriaLastRecord.getBikouB())) {
			// 最後分のレコードを累加
			// 日計を累計
			sumNikei = sumNikei.add(new BigDecimal(jisekiAriaLastRecord.getNikei()));
			// 当月累計を累計
			sumTogetsuRuikei = sumTogetsuRuikei.add(new BigDecimal(jisekiAriaLastRecord.getTogetsuRuikei()));
			// 前年同日を累計
			sumZennenDojitsu = sumZennenDojitsu.add(new BigDecimal(jisekiAriaLastRecord.getZennenDojitsu()));
			// 前年月末を累計
			sumZennenGetsumatsu = sumZennenGetsumatsu.add(new BigDecimal(jisekiAriaLastRecord.getZennenGetsumatsu()));
			// 前々年月末を累計
			sumZenzennenGetsumatsu = sumZenzennenGetsumatsu.add(new BigDecimal(jisekiAriaLastRecord.getZenzennenGetsumatsu()));
			// 当期(10月)を累計
			sumJyugatsuToki = sumJyugatsuToki.add(new BigDecimal(jisekiAriaLastRecord.getJyugatsuToki()));
			// 前期(10月)を累計
			sumJyugatsuZenki = sumJyugatsuZenki.add(new BigDecimal(jisekiAriaLastRecord.getJyugatsuZenki()));
			// 当期(1月)を累計
			sumIchigatsuToki = sumIchigatsuToki.add(new BigDecimal(jisekiAriaLastRecord.getIchigatsuToki()));
			// 前期(1月)を累計
			sumIchigatsuZenki = sumIchigatsuZenki.add(new BigDecimal(jisekiAriaLastRecord.getIchigatsuZenki()));

			// 総合行を作成
			jisekiAriaSoGokeiRecord.setAriaNm(SOGOKEI);
			// 日計の合計の合計
			jisekiAriaSoGokeiRecord.setNikei(sumNikei.add(new BigDecimal(jisekiAriaGokeiRecord.getNikei())).toString());
			// 当月累計の合計
			jisekiAriaSoGokeiRecord.setTogetsuRuikei(sumTogetsuRuikei.add(new BigDecimal(jisekiAriaGokeiRecord.getTogetsuRuikei())).toString());
			// 前年同日の合計
			jisekiAriaSoGokeiRecord.setZennenDojitsu(sumZennenDojitsu.add(new BigDecimal(jisekiAriaGokeiRecord.getZennenDojitsu())).toString());
			// 前年月末の合計
			jisekiAriaSoGokeiRecord.setZennenGetsumatsu(sumZennenGetsumatsu.add(new BigDecimal(jisekiAriaGokeiRecord.getZennenGetsumatsu())).toString());
			// 前々年月末の合計
			jisekiAriaSoGokeiRecord.setZenzennenGetsumatsu(sumZenzennenGetsumatsu.add(new BigDecimal(jisekiAriaGokeiRecord.getZenzennenGetsumatsu())).toString());
			// 当期(10月)の合計
			jisekiAriaSoGokeiRecord.setJyugatsuToki(sumJyugatsuToki.add(new BigDecimal(jisekiAriaGokeiRecord.getJyugatsuToki())).toString());
			// 前期(10月)の合計
			jisekiAriaSoGokeiRecord.setJyugatsuZenki(sumJyugatsuZenki.add(new BigDecimal(jisekiAriaGokeiRecord.getJyugatsuZenki())).toString());
			// 当期(1月)の合計
			jisekiAriaSoGokeiRecord.setIchigatsuToki(sumIchigatsuToki.add(new BigDecimal(jisekiAriaGokeiRecord.getIchigatsuToki())).toString());
			// 前期(1月)の合計
			jisekiAriaSoGokeiRecord.setIchigatsuZenki(sumIchigatsuZenki.add(new BigDecimal(jisekiAriaGokeiRecord.getIchigatsuZenki())).toString());

			// 総合行を合計用リストに追加
			jisekiAriaGokeiList.add(createDispRecord(jisekiAriaSoGokeiRecord));
		}
		return jisekiAriaGokeiList;

	}

	/**
	 * DBから取得したエリア課情報から表示用のリストを変換します。
	 *
	 * @param jisekiAriaList DBから取得した実績情報リスト
	 * @return jisekiAriaList 表示用リスト
	 */
	public JisekiAriaList convertToKaDispList(JisekiAriaList jisekiAriaKaList) {
		JisekiAriaList jisekiAriaKaDispList = new JisekiAriaList();
		// エリア課リスト分の情報を繰り返し
		for (PbsRecord jisekiAriaKaRecord : jisekiAriaKaList) {
			// 表示用のリストに追加
			jisekiAriaKaDispList.add(createDispRecord((JisekiAriaListRecord)jisekiAriaKaRecord));
		}
		return jisekiAriaKaDispList;
	}

	/**
	 * DBから取得したレコードを表示用のレコードに変換します。
	 *
	 * @param jisekiAriaRecord DBから取得した実績情報レコード
	 * @return jisekiAriaDispRecord 画面表示用の実績情報レコード
	 */
	public JisekiAriaListRecord createDispRecord(JisekiAriaListRecord jisekiAriaRecord) {
		JisekiAriaListRecord jisekiAriaDispRecord = new JisekiAriaListRecord();

		// エリア名
		jisekiAriaDispRecord.setAriaNm(jisekiAriaRecord.getAriaNm());
		// 日計(小数点切り捨て)
		jisekiAriaDispRecord.setNikei(new BigDecimal(jisekiAriaRecord.getNikei()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 当月累計(小数点切り捨て)
		jisekiAriaDispRecord.setTogetsuRuikei(new BigDecimal(jisekiAriaRecord.getTogetsuRuikei()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 前年同日(小数点切り捨て)
		jisekiAriaDispRecord.setZennenDojitsu(new BigDecimal(jisekiAriaRecord.getZennenDojitsu()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 前年月末(小数点切り捨て)
		jisekiAriaDispRecord.setZennenGetsumatsu(new BigDecimal(jisekiAriaRecord.getZennenGetsumatsu()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 前々年月末(小数点切り捨て)
		jisekiAriaDispRecord.setZenzennenGetsumatsu(new BigDecimal(jisekiAriaRecord.getZenzennenGetsumatsu()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 当期(10月)(小数点切り捨て)
		jisekiAriaDispRecord.setJyugatsuToki(new BigDecimal(jisekiAriaRecord.getJyugatsuToki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 前期(10月)(小数点切り捨て)
		jisekiAriaDispRecord.setJyugatsuZenki(new BigDecimal(jisekiAriaRecord.getJyugatsuZenki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 当期(1月)(小数点切り捨て)
		jisekiAriaDispRecord.setIchigatsuToki(new BigDecimal(jisekiAriaRecord.getIchigatsuToki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 前期(1月)(小数点切り捨て)
		jisekiAriaDispRecord.setIchigatsuZenki(new BigDecimal(jisekiAriaRecord.getIchigatsuZenki()).setScale(0, BigDecimal.ROUND_DOWN).toString());

		// 同日比
		// 前年同日がゼロ以外の場合
		if (!PbsUtil.isZero(jisekiAriaDispRecord.getZennenDojitsu())) {
			// 当月累計 / 前年同日  * 100
			BigDecimal Dojitsuhi = new BigDecimal(jisekiAriaDispRecord.getTogetsuRuikei()).multiply(new BigDecimal("100")).divide(new BigDecimal(jisekiAriaDispRecord.getZennenDojitsu()), 1, BigDecimal.ROUND_DOWN);
			// 小数点１桁（切り捨て）
			jisekiAriaDispRecord.setDoujitsuhi(Dojitsuhi.toString());
		} else {
			jisekiAriaDispRecord.setDoujitsuhi(new BigDecimal("0").setScale(1, BigDecimal.ROUND_DOWN).toString());
		}
		// 当月進捗率
		// 前年月末がゼロ以外の場合
		if (!PbsUtil.isZero(jisekiAriaDispRecord.getZennenGetsumatsu())) {
			// 当月累計 / 前年同日  * 100
			BigDecimal shincyokuTogetsu = new BigDecimal(jisekiAriaDispRecord.getTogetsuRuikei()).multiply(new BigDecimal("100")).divide(new BigDecimal(jisekiAriaDispRecord.getZennenGetsumatsu()), 1, BigDecimal.ROUND_DOWN);
			// 小数点１桁（切り捨て）
			jisekiAriaDispRecord.setTogetsuShincyoku(shincyokuTogetsu.toString());
		} else {
			jisekiAriaDispRecord.setTogetsuShincyoku(new BigDecimal("0").setScale(1, BigDecimal.ROUND_DOWN).toString());
		}

		// 10月進捗率
		// 10月前期がゼロ以外の場合
		if (!PbsUtil.isZero(jisekiAriaDispRecord.getJyugatsuZenki())) {
			// 10月当期 / 10月前期
			BigDecimal shincyokuJyugatsu = new BigDecimal(jisekiAriaDispRecord.getJyugatsuToki()).multiply(new BigDecimal("100")).divide(new BigDecimal(jisekiAriaDispRecord.getJyugatsuZenki()), 1, BigDecimal.ROUND_DOWN);
			// 小数点１桁（切り捨て）
			jisekiAriaDispRecord.setJyugatsuShincyoku(shincyokuJyugatsu.toString());
		} else {
			jisekiAriaDispRecord.setJyugatsuShincyoku(new BigDecimal("0").setScale(1, BigDecimal.ROUND_DOWN).toString());
		}

		// 1月進捗率
		// 1月前期がゼロ以外の場合
		if (!PbsUtil.isZero(jisekiAriaDispRecord.getIchigatsuZenki())) {
			// 1月当期 / 1月前期
			BigDecimal shincyokuIchigatsu = new BigDecimal(jisekiAriaDispRecord.getIchigatsuToki()).multiply(new BigDecimal("100")).divide(new BigDecimal(jisekiAriaDispRecord.getIchigatsuZenki()), 1, BigDecimal.ROUND_DOWN);
			// 小数点１桁（切り捨て）
			jisekiAriaDispRecord.setIchigatsuShincyoku(shincyokuIchigatsu.toString());
		} else {
			jisekiAriaDispRecord.setIchigatsuShincyoku(new BigDecimal("0").setScale(1, BigDecimal.ROUND_DOWN).toString());
		}

		return jisekiAriaDispRecord;

	}
}
