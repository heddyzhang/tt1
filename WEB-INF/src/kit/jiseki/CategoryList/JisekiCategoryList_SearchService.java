package kit.jiseki.CategoryList;

import static fb.inf.pbs.IPbsConst.*;
import static kit.jiseki.CategoryList.IJisekiCategoryList.*;

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
public class JisekiCategoryList_SearchService extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = 6411688081205821496L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JisekiCategoryList_SearchService.class.getName();
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
	public JisekiCategoryList_SearchService(ComUserSession cus, PbsDatabase db_, boolean isTran,
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
	 * カテゴリ事業所実績リストを検索のSQL生成と実行.
	 *
	 * @param searchForm
	 *            検索フォーム
	 * @return 検索結果
	 * @throws DataNotFoundException
	 * @throws OverMaxDataException
	 */
	public PbsRecord[] executeShiten(JisekiCategoryList_SearchForm searchForm)
			throws DataNotFoundException {

		category__.info("カテゴリ事業所実績リスト 検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("カテゴリ事業所実績リスト >> 検索処理 >> SQL文生成");
		String searchSql = getSqlSearchSyukaByKbn(searchForm, bindList);

		category__.info("カテゴリ事業所実績リスト >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true,searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}


		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("カテゴリ事業所実績リスト >> execute  ");
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
		category__.info("カテゴリ事業所実績リスト 検索処理 終了");

		return records_;
	}

	/**
	 * カテゴリ課実績リストを検索のSQL生成と実行.
	 *
	 * @param searchForm
	 *            検索フォーム
	 * @return 検索結果
	 * @throws DataNotFoundException
	 * @throws OverMaxDataException
	 */
	public PbsRecord[] executeKa(JisekiCategoryList_SearchForm searchForm)
			throws DataNotFoundException {

		category__.info("カテゴリ課実績リスト 検索処理 開始");

		List<String> bindList = new ArrayList<String>();

		category__.info("カテゴリ課実績リスト >> 検索処理 >> SQL文生成");
		String searchSql = getSqlSearchSyukaByKbn(searchForm, bindList);

		category__.info("カテゴリ課実績リスト >> 検索処理 >> prepare");
		Integer iCc = db_.prepare(true, searchSql);

		int ii = 0;
		for (String binsStr : bindList){
			db_.setString(iCc,++ii, binsStr);
		}

		category__.debug("#### SQL ####");
		category__.debug(db_.getSql(iCc));

		PbsRecord[] records_ = null;

		try {

			category__.info("カテゴリ課実績リスト >> execute  ");
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
		category__.info("カテゴリ課実績リスト 検索処理 終了");

		return records_;
	}

	/**

	/**
	 * カテゴリ実績リストの検索SQLを取得
	 *
	 * @param searchForm
	 * @param bindList
	 * @return sSql
	 */
	private String getSqlSearchSyukaByKbn(JisekiCategoryList_SearchForm searchForm, List<String> bindList) {

		StringBuilder sSql = new StringBuilder(CHAR_LF);

		// 集計コラム
		sSql.append(" SELECT                                           \n");
		sSql.append("   A.CD_NM                                        \n");
		sSql.append(" , A.BIKOU_B                                      \n");
		sSql.append(" , A.CODE_CD                                      \n");

		// 集計カラムを取得
		String syukeiColumn = getSyukeiColumn(searchForm);
		// 前期条件を取得
		String syukeiCondZenki = getSyukeiCondZenki(searchForm);
		// 前期同日条件を取得
		String syukeiCondZenkiDoji = getSyukeiCondZenkiDoji(searchForm);
		// 当期条件を取得
		String syukeiCondToki = getSyukeiCondToki(searchForm);

		// 前期
		sSql.append(" , SUM(CASE WHEN " + syukeiCondZenki + " THEN " + syukeiColumn + " ELSE 0 END ) AS ZENKI                                                                                 \n");
		// 前期同日
		sSql.append(" , SUM(CASE WHEN " + syukeiCondZenkiDoji + " THEN " + syukeiColumn + " ELSE 0 END ) AS ZENKI_DOUJI                                                                           \n");
		// 当期
		sSql.append(" , SUM(CASE WHEN " + syukeiCondToki + " THEN " + syukeiColumn + " ELSE 0 END ) AS TOKI                                                                                  \n");
		// 当期(清酒)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondToki + " AND S.HANBAI_BUMON_CD = '02' THEN " + syukeiColumn + " ELSE 0 END ) AS SEISYU_TOKI                                              \n");
		// 前期(清酒)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondZenki + " AND S.HANBAI_BUMON_CD = '02' THEN " + syukeiColumn + " ELSE 0 END ) AS SEISYU_ZENKI                                             \n");
		// 当期(麦酒・発泡酒)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondToki + " AND S.HANBAI_BUMON_CD IN ('03','04') THEN " + syukeiColumn + " ELSE 0 END ) AS BIRU_TOKI                                        \n");
		// 前期(麦酒・発泡酒)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondZenki + " AND S.HANBAI_BUMON_CD IN ('03','04') THEN " + syukeiColumn + " ELSE 0 END ) AS BIRU_ZENKI                                       \n");
		// 当期(焼酎)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondToki + " AND S.HANBAI_BUMON_CD = '05' THEN " + syukeiColumn + " ELSE 0 END ) AS SYOCYU_TOKI                                              \n");
		// 前期(焼酎)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondZenki + " AND S.HANBAI_BUMON_CD = '05' THEN " + syukeiColumn + " ELSE 0 END ) AS SYOCYU_ZENKI                                             \n");
		// 当期(リキュール)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondToki + " AND S.HANBAI_BUMON_CD = '06' THEN " + syukeiColumn + " ELSE 0 END ) AS RIKYURU_TOKI                                             \n");
		// 前期(リキュール)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondZenki + " AND S.HANBAI_BUMON_CD = '06' THEN " + syukeiColumn + " ELSE 0 END ) AS RIKYURU_ZENKI                                            \n");
		// 当期(調味料)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondToki + " AND S.HANBAI_BUMON_CD = '07' THEN " + syukeiColumn + " ELSE 0 END ) AS CYOMIRYO_TOKI                                            \n");
		// 前期(調味料)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondZenki + " AND S.HANBAI_BUMON_CD = '07' THEN " + syukeiColumn + " ELSE 0 END ) AS CYOMIRYO_ZENKI                                           \n");
		// 当期(伏水・サイファー)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondToki + " AND S.HANBAI_BUMON_CD = '30' THEN " + syukeiColumn + " ELSE 0 END ) AS FUSUI_TOKI                                               \n");
		// 前期(伏水・サイファー)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondZenki + " AND S.HANBAI_BUMON_CD = '30' THEN " + syukeiColumn + " ELSE 0 END ) AS FUSUI_ZENKI                                              \n");
		// 当期(化粧品)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondToki + " AND S.HANBAI_BUMON_CD = '70' THEN " + syukeiColumn + " ELSE 0 END ) AS KESYOHIN_TOKI                                            \n");
		// 前期(化粧品)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondZenki + " AND S.HANBAI_BUMON_CD = '70' THEN " + syukeiColumn + " ELSE 0 END ) AS KESYOHIN_ZENKI                                           \n");
		// 当期(その他)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondToki + " AND S.HANBAI_BUMON_CD NOT IN ('02','03','04','05','06','07','30','70') THEN " + syukeiColumn + " ELSE 0 END ) AS SONOHOKA_TOKI  \n");
		// 前期(その他)
		sSql.append(" , SUM(CASE WHEN " + syukeiCondZenki + " AND S.HANBAI_BUMON_CD NOT IN ('02','03','04','05','06','07','30','70') THEN " + syukeiColumn + " ELSE 0 END ) AS SONOHOKA_ZENKI \n");

		// ****From句****
		sSql.append(" FROM M_CD_MEISYOU A                                                                           \n");
		// 事業所の場合
		if (SEARCH_KBN_SITEN.equals(searchForm.getSearchKbn())) {
			sSql.append("   LEFT JOIN M_KBN_JISEKI_TANTO B ON (A.CODE_CD = B.JIGYOSYO_CD)                           \n");
		} else {
			// 課の場合
			sSql.append("   LEFT JOIN M_KBN_JISEKI_TANTO B ON (A.CODE_CD = B.KA_CD)                                 \n");
		}
		//sSql.append("   LEFT JOIN T_SYUKA_HD C ON (B.TANTO_CD = C.TANTOSYA_CD)                                    \n");
		// 担当者CDはﾏｽﾀｰより再取得する
		sSql.append("  LEFT JOIN M_OROSISYOSAI_HD E ON (B.KAISYA_CD = E.KAISYA_CD AND B.TANTO_CD = E.TANTOSYA_CD)   \n");
		sSql.append("  LEFT JOIN T_SYUKA_HD C ON (E.KAISYA_CD = C.KAISYA_CD AND E.TATESN_CD = C.TATESN_CD)          \n");

		sSql.append("  LEFT JOIN T_SYUKA_JSKCTG D ON (C.KAISYA_CD = D.KAISYA_CD AND C.URIDEN_NO = D.URIDEN_NO)      \n");
		// 販売部門CDはﾏｽﾀｰより再取得する
		sSql.append("  LEFT JOIN M_SEIHIN F ON (D.KAISYA_CD = F.KAISYA_CD AND D.KTKSY_CD = F.KTKSY_CD AND D.SEIHIN_CD = F.SEIHIN_CD) \n");
		sSql.append("  LEFT JOIN M_KBN_TANE G ON (F.KAISYA_CD = G.KAISYA_CD AND F.TANE_CD = G.TANE_CD)                               \n");
		sSql.append("  LEFT JOIN M_KBN_HANBAI_BUNRUI H  ON (G.HANBAI_BUNRUI_CD = H.HANBAI_BUNRUI_CD)                                 \n");
		sSql.append("  LEFT JOIN M_KBN_HANBAI_SYUBETU S  ON (H.HANBAI_SYUBETU_CD = S.HANBAI_SYUBETU_CD)                              \n");

		// ****Where句****
		if (SEARCH_KBN_SITEN.equals(searchForm.getSearchKbn())) {
			// 事業所の場合
			sSql.append(" WHERE A.KBN_ID = 'KBN_HJS_SITEN'                                  \n");
		} else {
			// 課の場合
			sSql.append(" WHERE A.KBN_ID = 'KBN_HJS_KA' AND A.BIKOU_B = '09'                \n");
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
	private String getSyukeiColumn(JisekiCategoryList_SearchForm searchForm){
		String syukeiColumn = "";
		if (RDO_TANI_KINGAKU.equals(searchForm.getHyoujiTanyi())) {
			syukeiColumn = "D.HANBAI_KINGAKU";
		} else {
			syukeiColumn = "D.SYUKA_VOL";
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
	private String getSyukeiCondToki(JisekiCategoryList_SearchForm searchForm){
		// 画面から集計期間を取得
		String syukeiKiKan = searchForm.getSyukeiKikan();
		// 期間開始日
		String kikanStartDate = "";
		// 期間終了日(指定日を設定)
		String kikanEndDate = searchForm.getShiteibi();

		if (RDO_SYUKEI_KIKAN_TOGETSU.equals(syukeiKiKan)) {
			// 当月の場合、指定日月初を設定
			kikanStartDate = searchForm.getShiteibiGetsusyo();
		} else if (RDO_SYUKEI_KIKAN_JYUGATSU.equals(syukeiKiKan)) {
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
	private String getSyukeiCondZenki(JisekiCategoryList_SearchForm searchForm){
		// 画面から集計期間を取得
		String syukeiKiKan = searchForm.getSyukeiKikan();
		// 期間開始日
		String kikanStartDate = "";
		// 期間終了日(前年同日月末を設定)
		String kikanEndDate = searchForm.getZennenDojitsuGetsumatsu();

		if (RDO_SYUKEI_KIKAN_TOGETSU.equals(syukeiKiKan)) {
			// 当月の場合、前年同日月初を設定
			kikanStartDate = searchForm.getZennenDojitsuGetsusyo();
		} else if (RDO_SYUKEI_KIKAN_JYUGATSU.equals(syukeiKiKan)) {
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
	 * SQLで使う集計用期間の前期同日条件文字列を生成
	 *
	 * @param searchForm 検索条件用のフォーム
	 * @param searchForm 集計期間区分
	 * @return SQLで集計用期間の前期条件文字列
	 */
	private String getSyukeiCondZenkiDoji(JisekiCategoryList_SearchForm searchForm){
		// 画面から集計期間を取得
		String syukeiKiKan = searchForm.getSyukeiKikan();
		// 期間開始日
		String kikanStartDate = "";
		// 期間終了日(前年同日を設定)
		String kikanEndDate = searchForm.getZennenDojitsu();

		if (RDO_SYUKEI_KIKAN_TOGETSU.equals(syukeiKiKan)) {
			// 当月の場合、前年同日月初を設定
			kikanStartDate = searchForm.getZennenDojitsuGetsusyo();
		} else if (RDO_SYUKEI_KIKAN_JYUGATSU.equals(syukeiKiKan)) {
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
	 * @param jisekiCategoryList DBから取得した実績情報リスト
	 * @return jisekiCategoryGokeiList 実績用合計リスト
	 */
	public JisekiCategoryList convertToGokeiList(JisekiCategoryList jisekiCategoryList) {
		// 合計用リスト
		JisekiCategoryList jisekiCategoryGokeiList = new JisekiCategoryList();
		// 東日本計合計行
		JisekiCategoryListRecord jsekiCategoryHigashiGokeiRecord = new JisekiCategoryListRecord();
		// 西日本計合計行
		JisekiCategoryListRecord jisekiCategoryNishiGokeiRecord = new JisekiCategoryListRecord();
		// 支店営業合計行
		JisekiCategoryListRecord jisekiCategorySitenGokeiRecord = new JisekiCategoryListRecord();
		// 合計行
		JisekiCategoryListRecord jisekiCategoryGokeiRecord = new JisekiCategoryListRecord();
		// 総合計行
		JisekiCategoryListRecord jisekiCategorySoGokeiRecord = new JisekiCategoryListRecord();

		// 累計用変数の初期化
		BigDecimal sumZenki = BigDecimal.ZERO;				// 前期(累計用)
		BigDecimal sumZenkiDoji = BigDecimal.ZERO;			// 前期同日(累計用)
		BigDecimal sumToki = BigDecimal.ZERO;				// 当期(累計用)
		BigDecimal sumSeisyuToki = BigDecimal.ZERO;			// 当期清酒(累計用)
		BigDecimal sumSeisyuZenki = BigDecimal.ZERO;		// 前期清酒(累計用)
		BigDecimal sumBiruToki = BigDecimal.ZERO;			// 当期ビール、発泡酒(累計用)
		BigDecimal sumBiruZenki = BigDecimal.ZERO;			// 前期ビール、発泡酒(累計用)
		BigDecimal sumSyocyuToki = BigDecimal.ZERO;			// 当期焼酎(累計用)
		BigDecimal sumSyocyuZenki = BigDecimal.ZERO;		// 前期焼酎(累計用)
		BigDecimal sumRikyuruToki = BigDecimal.ZERO;		// 当期リキュール(累計用)
		BigDecimal sumRikyuruZenki = BigDecimal.ZERO;		// 前期リキュール(累計用)
		BigDecimal sumCyomiryoToki = BigDecimal.ZERO;		// 当期調味料(累計用)
		BigDecimal sumCyomiryoZenki = BigDecimal.ZERO;		// 前期調味料(累計用)
		BigDecimal sumFusuiToki = BigDecimal.ZERO;			// 当期伏水、サイファー(累計用)
		BigDecimal sumFusuiZenki = BigDecimal.ZERO;			// 前期伏水、サイファー(累計用)
		BigDecimal sumKesyohinToki = BigDecimal.ZERO;		// 当期化粧品(累計用)
		BigDecimal sumKesyohinZenki = BigDecimal.ZERO;		// 前期化粧品(累計用)
		BigDecimal sumSonohokaToki = BigDecimal.ZERO;		// 当期その他(累計用)
		BigDecimal sumSonohokaZenki = BigDecimal.ZERO;		// 前期その他(累計用)

		// カテゴリリスト分の情報を繰り返し
		for (int i = 0; i < jisekiCategoryList.size() - 1; i++) {
			// DBから取ったレコード
			JisekiCategoryListRecord jisekiCategorRecord = (JisekiCategoryListRecord)jisekiCategoryList.get(i);
			// エリア別の実績情報分を合計用リストに追加
			jisekiCategoryGokeiList.add(createDispRecord(jisekiCategorRecord));

			// 前期を累計
			sumZenki = sumZenki.add(new BigDecimal(jisekiCategorRecord.getZenki()));
			// 前期同日を累計
			sumZenkiDoji = sumZenkiDoji.add(new BigDecimal(jisekiCategorRecord.getZenkiDoji()));
			// 当期を累計
			sumToki = sumToki.add(new BigDecimal(jisekiCategorRecord.getToki()));
			// 当期清酒を累計
			sumSeisyuToki = sumSeisyuToki.add(new BigDecimal(jisekiCategorRecord.getSeisyuToki()));
			// 前期清酒を累計
			sumSeisyuZenki = sumSeisyuZenki.add(new BigDecimal(jisekiCategorRecord.getSeisyuZenki()));
			// 当期ビール、発泡酒を累計
			sumBiruToki = sumBiruToki.add(new BigDecimal(jisekiCategorRecord.getBiruToki()));
			// 前期ビール、発泡酒を累計
			sumBiruZenki = sumBiruZenki.add(new BigDecimal(jisekiCategorRecord.getBiruZenki()));
			// 当期焼酎を累計
			sumSyocyuToki = sumSyocyuToki.add(new BigDecimal(jisekiCategorRecord.getSyocyuToki()));
			// 前期焼酎を累計
			sumSyocyuZenki = sumSyocyuZenki.add(new BigDecimal(jisekiCategorRecord.getSyocyuZenki()));
			// 当期リキュールを累計
			sumRikyuruToki = sumRikyuruToki.add(new BigDecimal(jisekiCategorRecord.getRikyuruToki()));
			// 前期リキュールを累計
			sumRikyuruZenki = sumRikyuruZenki.add(new BigDecimal(jisekiCategorRecord.getRikyuruZenki()));
			// 当期調味料を累計
			sumCyomiryoToki = sumCyomiryoToki.add(new BigDecimal(jisekiCategorRecord.getCyomiryoToki()));
			// 前期調味料を累計
			sumCyomiryoZenki = sumCyomiryoZenki.add(new BigDecimal(jisekiCategorRecord.getCyomiryoZenki()));
			// 当期伏水、サイファーを累計
			sumFusuiToki = sumFusuiToki.add(new BigDecimal(jisekiCategorRecord.getFusuiToki()));
			// 前期伏水、サイファーを累計
			sumFusuiZenki = sumFusuiZenki.add(new BigDecimal(jisekiCategorRecord.getFusuiZenki()));
			// 当期化粧品を累計
			sumKesyohinToki = sumKesyohinToki.add(new BigDecimal(jisekiCategorRecord.getKesyohinToki()));
			// 前期化粧品を累計
			sumKesyohinZenki = sumKesyohinZenki.add(new BigDecimal(jisekiCategorRecord.getKesyohinZenki()));
			// 当期その他を累計
			sumSonohokaToki = sumSonohokaToki.add(new BigDecimal(jisekiCategorRecord.getSonohokaToki()));
			// 前期その他を累計
			sumSonohokaZenki = sumSonohokaZenki.add(new BigDecimal(jisekiCategorRecord.getSonohokaZenki()));

			// 該当レコードの備考B
			String bikouB1 = jisekiCategorRecord.getBikouB();
			// 直後レコートの備考B
			String bikouB2 = ((JisekiCategoryListRecord) jisekiCategoryList.get(i + 1)).getBikouB();

			// 該当レコードの備考Bと直後レコートの備考Bを比較
			if (!PbsUtil.isEqual(bikouB1, bikouB2)) {
				switch (bikouB1){
				// 東日本の場合
				case AREA_KBN_HIGASHINIHON :
					// エリア名
					jsekiCategoryHigashiGokeiRecord.setAriaNm(HIGASHI_NIHON_KEI);
					// 前期の合計
					jsekiCategoryHigashiGokeiRecord.setZenki(sumZenki.toString());
					// 前期同日の合計
					jsekiCategoryHigashiGokeiRecord.setZenkiDoji(sumZenkiDoji.toString());
					// 当期の合計
					jsekiCategoryHigashiGokeiRecord.setToki(sumToki.toString());
					// 当期清酒の合計
					jsekiCategoryHigashiGokeiRecord.setSeisyuToki(sumSeisyuToki.toString());
					// 前期清酒の合計
					jsekiCategoryHigashiGokeiRecord.setSeisyuZenki(sumSeisyuZenki.toString());
					// 当期ビール、発泡酒の合計
					jsekiCategoryHigashiGokeiRecord.setBiruToki(sumBiruToki.toString());
					// 前期ビール、発泡酒の合計
					jsekiCategoryHigashiGokeiRecord.setBiruZenki(sumBiruZenki.toString());
					// 当期焼酎の合計
					jsekiCategoryHigashiGokeiRecord.setSyocyuToki(sumSyocyuToki.toString());
					// 前期焼酎の合計
					jsekiCategoryHigashiGokeiRecord.setSyocyuZenki(sumSyocyuZenki.toString());
					// 当期リキュールの合計
					jsekiCategoryHigashiGokeiRecord.setRikyuruToki(sumRikyuruToki.toString());
					// 前期リキュールの合計
					jsekiCategoryHigashiGokeiRecord.setRikyuruZenki(sumRikyuruZenki.toString());
					// 当期調味料の合計
					jsekiCategoryHigashiGokeiRecord.setCyomiryoToki(sumCyomiryoToki.toString());
					// 前期調味料の合計
					jsekiCategoryHigashiGokeiRecord.setCyomiryoZenki(sumCyomiryoZenki.toString());
					// 当期伏水、サイファーの合計
					jsekiCategoryHigashiGokeiRecord.setFusuiToki(sumFusuiToki.toString());
					// 前期伏水、サイファーの合計
					jsekiCategoryHigashiGokeiRecord.setFusuiZenki(sumFusuiZenki.toString());
					// 当期化粧品の合計
					jsekiCategoryHigashiGokeiRecord.setKesyohinToki(sumKesyohinToki.toString());
					// 前期化粧品の合計
					jsekiCategoryHigashiGokeiRecord.setKesyohinZenki(sumKesyohinZenki.toString());
					// 当期その他の合計
					jsekiCategoryHigashiGokeiRecord.setSonohokaToki(sumSonohokaToki.toString());
					// 前期その他の合計
					jsekiCategoryHigashiGokeiRecord.setSonohokaZenki(sumSonohokaZenki.toString());

					// 東日本計合計行を合計用リストに追加
					jisekiCategoryGokeiList.add(createDispRecord(jsekiCategoryHigashiGokeiRecord));

					break;

				// 西日本の場合
				case AREA_KBN_NISHINIHON :
					// エリア名
					jisekiCategoryNishiGokeiRecord.setAriaNm(NISHI_NIHON_KEI);
					// 前期の合計
					jisekiCategoryNishiGokeiRecord.setZenki(sumZenki.toString());
					// 前期同日の合計
					jisekiCategoryNishiGokeiRecord.setZenkiDoji(sumZenkiDoji.toString());
					// 当期の合計
					jisekiCategoryNishiGokeiRecord.setToki(sumToki.toString());
					// 当期清酒の合計
					jisekiCategoryNishiGokeiRecord.setSeisyuToki(sumSeisyuToki.toString());
					// 前期清酒の合計
					jisekiCategoryNishiGokeiRecord.setSeisyuZenki(sumSeisyuZenki.toString());
					// 当期ビール、発泡酒の合計
					jisekiCategoryNishiGokeiRecord.setBiruToki(sumBiruToki.toString());
					// 前期ビール、発泡酒の合計
					jisekiCategoryNishiGokeiRecord.setBiruZenki(sumBiruZenki.toString());
					// 当期焼酎の合計
					jisekiCategoryNishiGokeiRecord.setSyocyuToki(sumSyocyuToki.toString());
					// 前期焼酎の合計
					jisekiCategoryNishiGokeiRecord.setSyocyuZenki(sumSyocyuZenki.toString());
					// 当期リキュールの合計
					jisekiCategoryNishiGokeiRecord.setRikyuruToki(sumRikyuruToki.toString());
					// 前期リキュールの合計
					jisekiCategoryNishiGokeiRecord.setRikyuruZenki(sumRikyuruZenki.toString());
					// 当期調味料の合計
					jisekiCategoryNishiGokeiRecord.setCyomiryoToki(sumCyomiryoToki.toString());
					// 前期調味料の合計
					jisekiCategoryNishiGokeiRecord.setCyomiryoZenki(sumCyomiryoZenki.toString());
					// 当期伏水、サイファーの合計
					jisekiCategoryNishiGokeiRecord.setFusuiToki(sumFusuiToki.toString());
					// 前期伏水、サイファーの合計
					jisekiCategoryNishiGokeiRecord.setFusuiZenki(sumFusuiZenki.toString());
					// 当期化粧品の合計
					jisekiCategoryNishiGokeiRecord.setKesyohinToki(sumKesyohinToki.toString());
					// 前期化粧品の合計
					jisekiCategoryNishiGokeiRecord.setKesyohinZenki(sumKesyohinZenki.toString());
					// 当期その他の合計
					jisekiCategoryNishiGokeiRecord.setSonohokaToki(sumSonohokaToki.toString());
					// 前期その他の合計
					jisekiCategoryNishiGokeiRecord.setSonohokaZenki(sumSonohokaZenki.toString());

					// 西日本計合計行を合計用リストに追加
					jisekiCategoryGokeiList.add(createDispRecord(jisekiCategoryNishiGokeiRecord));

					// 支店営業合計行を作成
					// エリア名
					jisekiCategorySitenGokeiRecord.setAriaNm(SHITEN_GOKEI);
					// 前期の合計
					jisekiCategorySitenGokeiRecord.setZenki(sumZenki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getZenki())).toString());
					// 前期同日の合計
					jisekiCategorySitenGokeiRecord.setZenkiDoji(sumZenkiDoji.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getZenkiDoji())).toString());
					// 当期の合計
					jisekiCategorySitenGokeiRecord.setToki(sumToki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getToki())).toString());
					// 当期清酒の合計
					jisekiCategorySitenGokeiRecord.setSeisyuToki(sumSeisyuToki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getSeisyuToki())).toString());
					// 前期清酒の合計
					jisekiCategorySitenGokeiRecord.setSeisyuZenki(sumSeisyuZenki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getSeisyuZenki())).toString());
					// 当期ビール、発泡酒の合計
					jisekiCategorySitenGokeiRecord.setBiruToki(sumBiruToki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getBiruToki())).toString());
					// 前期ビール、発泡酒の合計
					jisekiCategorySitenGokeiRecord.setBiruZenki(sumBiruZenki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getBiruZenki())).toString());
					// 当期焼酎の合計
					jisekiCategorySitenGokeiRecord.setSyocyuToki(sumSyocyuToki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getSyocyuToki())).toString());
					// 前期焼酎の合計
					jisekiCategorySitenGokeiRecord.setSyocyuZenki(sumSyocyuZenki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getSyocyuZenki())).toString());
					// 当期リキュールの合計
					jisekiCategorySitenGokeiRecord.setRikyuruToki(sumRikyuruToki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getRikyuruToki())).toString());
					// 前期リキュールの合計
					jisekiCategorySitenGokeiRecord.setRikyuruZenki(sumRikyuruZenki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getRikyuruZenki())).toString());
					// 当期調味料の合計
					jisekiCategorySitenGokeiRecord.setCyomiryoToki(sumCyomiryoToki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getCyomiryoToki())).toString());
					// 前期調味料の合計
					jisekiCategorySitenGokeiRecord.setCyomiryoZenki(sumCyomiryoZenki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getCyomiryoZenki())).toString());
					// 当期伏水、サイファーの合計
					jisekiCategorySitenGokeiRecord.setFusuiToki(sumFusuiToki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getFusuiToki())).toString());
					// 前期伏水、サイファーの合計
					jisekiCategorySitenGokeiRecord.setFusuiZenki(sumFusuiZenki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getFusuiZenki())).toString());
					// 当期化粧品の合計
					jisekiCategorySitenGokeiRecord.setKesyohinToki(sumKesyohinToki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getKesyohinToki())).toString());
					// 前期化粧品の合計
					jisekiCategorySitenGokeiRecord.setKesyohinZenki(sumKesyohinZenki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getKesyohinZenki())).toString());
					// 当期その他の合計
					jisekiCategorySitenGokeiRecord.setSonohokaToki(sumSonohokaToki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getSonohokaToki())).toString());
					// 前期その他の合計
					jisekiCategorySitenGokeiRecord.setSonohokaZenki(sumSonohokaZenki.add(new BigDecimal(jsekiCategoryHigashiGokeiRecord.getSonohokaZenki())).toString());

					// 支店営業合計行を合計用リストに追加
					jisekiCategoryGokeiList.add(createDispRecord(jisekiCategorySitenGokeiRecord));
					break;

					// 小売の場合
				case AREA_KBN_KOURI :
					// 合計行を作成
					jisekiCategoryGokeiRecord.setAriaNm(GOKEI);
					// 前期の合計
					jisekiCategoryGokeiRecord.setZenki(sumZenki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getZenki())).toString());
					// 前期同日の合計
					jisekiCategoryGokeiRecord.setZenkiDoji(sumZenkiDoji.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getZenkiDoji())).toString());
					// 当期の合計
					jisekiCategoryGokeiRecord.setToki(sumToki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getToki())).toString());
					// 当期清酒の合計
					jisekiCategoryGokeiRecord.setSeisyuToki(sumSeisyuToki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getSeisyuToki())).toString());
					// 前期清酒の合計
					jisekiCategoryGokeiRecord.setSeisyuZenki(sumSeisyuZenki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getSeisyuZenki())).toString());
					// 当期ビール、発泡酒の合計
					jisekiCategoryGokeiRecord.setBiruToki(sumBiruToki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getBiruToki())).toString());
					// 前期ビール、発泡酒の合計
					jisekiCategoryGokeiRecord.setBiruZenki(sumBiruZenki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getBiruZenki())).toString());
					// 当期焼酎の合計
					jisekiCategoryGokeiRecord.setSyocyuToki(sumSyocyuToki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getSyocyuToki())).toString());
					// 前期焼酎の合計
					jisekiCategoryGokeiRecord.setSyocyuZenki(sumSyocyuZenki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getSyocyuZenki())).toString());
					// 当期リキュールの合計
					jisekiCategoryGokeiRecord.setRikyuruToki(sumRikyuruToki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getRikyuruToki())).toString());
					// 前期リキュールの合計
					jisekiCategoryGokeiRecord.setRikyuruZenki(sumRikyuruZenki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getRikyuruZenki())).toString());
					// 当期調味料の合計
					jisekiCategoryGokeiRecord.setCyomiryoToki(sumCyomiryoToki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getCyomiryoToki())).toString());
					// 前期調味料の合計
					jisekiCategoryGokeiRecord.setCyomiryoZenki(sumCyomiryoZenki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getCyomiryoZenki())).toString());
					// 当期伏水、サイファーの合計
					jisekiCategoryGokeiRecord.setFusuiToki(sumFusuiToki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getFusuiToki())).toString());
					// 前期伏水、サイファーの合計
					jisekiCategoryGokeiRecord.setFusuiZenki(sumFusuiZenki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getFusuiZenki())).toString());
					// 当期化粧品の合計
					jisekiCategoryGokeiRecord.setKesyohinToki(sumKesyohinToki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getKesyohinToki())).toString());
					// 前期化粧品の合計
					jisekiCategoryGokeiRecord.setKesyohinZenki(sumKesyohinZenki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getKesyohinZenki())).toString());
					// 当期その他の合計
					jisekiCategoryGokeiRecord.setSonohokaToki(sumSonohokaToki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getSonohokaToki())).toString());
					// 前期その他の合計
					jisekiCategoryGokeiRecord.setSonohokaZenki(sumSonohokaZenki.add(new BigDecimal(jisekiCategorySitenGokeiRecord.getSonohokaZenki())).toString());

					// 合計行を合計用リストに追加
					jisekiCategoryGokeiList.add(createDispRecord(jisekiCategoryGokeiRecord));
					break;
				}

				// 累計用変数のクリア
				sumZenki = BigDecimal.ZERO;				// 前期
				sumZenkiDoji = BigDecimal.ZERO;			// 前期同日
				sumToki = BigDecimal.ZERO;				// 当期
				sumSeisyuToki = BigDecimal.ZERO;		// 当期清酒
				sumSeisyuZenki = BigDecimal.ZERO;		// 前期清酒
				sumBiruToki = BigDecimal.ZERO;			// 当期ビール、発泡酒
				sumBiruZenki = BigDecimal.ZERO;			// 前期ビール、発泡酒
				sumSyocyuToki = BigDecimal.ZERO;		// 当期焼酎
				sumSyocyuZenki = BigDecimal.ZERO;		// 前期焼酎
				sumRikyuruToki = BigDecimal.ZERO;		// 当期リキュール
				sumRikyuruZenki = BigDecimal.ZERO;		// 前期リキュール
				sumCyomiryoToki = BigDecimal.ZERO;		// 当期調味料
				sumCyomiryoZenki = BigDecimal.ZERO;		// 前期調味料
				sumFusuiToki = BigDecimal.ZERO;			// 当期伏水、サイファー
				sumFusuiZenki = BigDecimal.ZERO;		// 前期伏水、サイファー
				sumKesyohinToki = BigDecimal.ZERO;		// 当期化粧品
				sumKesyohinZenki = BigDecimal.ZERO;		// 前期化粧品
				sumSonohokaToki = BigDecimal.ZERO;		// 当期その他
				sumSonohokaZenki = BigDecimal.ZERO;		// 前期その他

			}
		}

		// 最後のレコード
		JisekiCategoryListRecord jisekiCategoryLastRecord = (JisekiCategoryListRecord)jisekiCategoryList.get(jisekiCategoryList.size() - 1);
		// 合計用リストに追加
		jisekiCategoryGokeiList.add(createDispRecord(jisekiCategoryLastRecord));

		// 未納税の場合
		if (AREA_KBN_MINOZEI.equals(jisekiCategoryLastRecord.getBikouB())) {
			// 最後分のレコードを累加
			// 前期を累計
			sumZenki = sumZenki.add(new BigDecimal(jisekiCategoryLastRecord.getZenki()));
			// 前期同日を累計
			sumZenkiDoji = sumZenkiDoji.add(new BigDecimal(jisekiCategoryLastRecord.getZenkiDoji()));
			// 当期を累計
			sumToki = sumToki.add(new BigDecimal(jisekiCategoryLastRecord.getToki()));
			// 当期清酒を累計
			sumSeisyuToki = sumSeisyuToki.add(new BigDecimal(jisekiCategoryLastRecord.getSeisyuToki()));
			// 前期清酒を累計
			sumSeisyuZenki = sumSeisyuZenki.add(new BigDecimal(jisekiCategoryLastRecord.getSeisyuZenki()));
			// 当期ビール、発泡酒を累計
			sumBiruToki = sumBiruToki.add(new BigDecimal(jisekiCategoryLastRecord.getBiruToki()));
			// 前期ビール、発泡酒を累計
			sumBiruZenki = sumBiruZenki.add(new BigDecimal(jisekiCategoryLastRecord.getBiruZenki()));
			// 当期焼酎を累計
			sumSyocyuToki = sumSyocyuToki.add(new BigDecimal(jisekiCategoryLastRecord.getSyocyuToki()));
			// 前期焼酎を累計
			sumSyocyuZenki = sumSyocyuZenki.add(new BigDecimal(jisekiCategoryLastRecord.getSyocyuZenki()));
			// 当期リキュールを累計
			sumRikyuruToki = sumRikyuruToki.add(new BigDecimal(jisekiCategoryLastRecord.getRikyuruToki()));
			// 前期リキュールを累計
			sumRikyuruZenki = sumRikyuruZenki.add(new BigDecimal(jisekiCategoryLastRecord.getRikyuruZenki()));
			// 当期調味料を累計
			sumCyomiryoToki = sumCyomiryoToki.add(new BigDecimal(jisekiCategoryLastRecord.getCyomiryoToki()));
			// 前期調味料を累計
			sumCyomiryoZenki = sumCyomiryoZenki.add(new BigDecimal(jisekiCategoryLastRecord.getCyomiryoZenki()));
			// 当期伏水、サイファーを累計
			sumFusuiToki = sumFusuiToki.add(new BigDecimal(jisekiCategoryLastRecord.getFusuiToki()));
			// 前期伏水、サイファーを累計
			sumFusuiZenki = sumFusuiZenki.add(new BigDecimal(jisekiCategoryLastRecord.getFusuiZenki()));
			// 当期化粧品を累計
			sumKesyohinToki = sumKesyohinToki.add(new BigDecimal(jisekiCategoryLastRecord.getKesyohinToki()));
			// 前期化粧品を累計
			sumKesyohinZenki = sumKesyohinZenki.add(new BigDecimal(jisekiCategoryLastRecord.getKesyohinZenki()));
			// 当期その他を累計
			sumSonohokaToki = sumSonohokaToki.add(new BigDecimal(jisekiCategoryLastRecord.getSonohokaToki()));
			// 前期その他を累計
			sumSonohokaZenki = sumSonohokaZenki.add(new BigDecimal(jisekiCategoryLastRecord.getSonohokaZenki()));

			// 総合行を作成
			jisekiCategorySoGokeiRecord.setAriaNm(SOGOKEI);
			// 前期の合計
			jisekiCategorySoGokeiRecord.setZenki(sumZenki.add(new BigDecimal(jisekiCategoryGokeiRecord.getZenki())).toString());
			// 前期同日の合計
			jisekiCategorySoGokeiRecord.setZenkiDoji(sumZenkiDoji.add(new BigDecimal(jisekiCategoryGokeiRecord.getZenkiDoji())).toString());
			// 当期の合計
			jisekiCategorySoGokeiRecord.setToki(sumToki.add(new BigDecimal(jisekiCategoryGokeiRecord.getToki())).toString());
			// 当期清酒の合計
			jisekiCategorySoGokeiRecord.setSeisyuToki(sumSeisyuToki.add(new BigDecimal(jisekiCategoryGokeiRecord.getSeisyuToki())).toString());
			// 前期清酒の合計
			jisekiCategorySoGokeiRecord.setSeisyuZenki(sumSeisyuZenki.add(new BigDecimal(jisekiCategoryGokeiRecord.getSeisyuZenki())).toString());
			// 当期ビール、発泡酒の合計
			jisekiCategorySoGokeiRecord.setBiruToki(sumBiruToki.add(new BigDecimal(jisekiCategoryGokeiRecord.getBiruToki())).toString());
			// 前期ビール、発泡酒の合計
			jisekiCategorySoGokeiRecord.setBiruZenki(sumBiruZenki.add(new BigDecimal(jisekiCategoryGokeiRecord.getBiruZenki())).toString());
			// 当期焼酎の合計
			jisekiCategorySoGokeiRecord.setSyocyuToki(sumSyocyuToki.add(new BigDecimal(jisekiCategoryGokeiRecord.getSyocyuToki())).toString());
			// 前期焼酎の合計
			jisekiCategorySoGokeiRecord.setSyocyuZenki(sumSyocyuZenki.add(new BigDecimal(jisekiCategoryGokeiRecord.getSyocyuZenki())).toString());
			// 当期リキュールの合計
			jisekiCategorySoGokeiRecord.setRikyuruToki(sumRikyuruToki.add(new BigDecimal(jisekiCategoryGokeiRecord.getRikyuruToki())).toString());
			// 前期リキュールの合計
			jisekiCategorySoGokeiRecord.setRikyuruZenki(sumRikyuruZenki.add(new BigDecimal(jisekiCategoryGokeiRecord.getRikyuruZenki())).toString());
			// 当期調味料の合計
			jisekiCategorySoGokeiRecord.setCyomiryoToki(sumCyomiryoToki.add(new BigDecimal(jisekiCategoryGokeiRecord.getCyomiryoToki())).toString());
			// 前期調味料の合計
			jisekiCategorySoGokeiRecord.setCyomiryoZenki(sumCyomiryoZenki.add(new BigDecimal(jisekiCategoryGokeiRecord.getCyomiryoZenki())).toString());
			// 当期伏水、サイファーの合計
			jisekiCategorySoGokeiRecord.setFusuiToki(sumFusuiToki.add(new BigDecimal(jisekiCategoryGokeiRecord.getFusuiToki())).toString());
			// 前期伏水、サイファーの合計
			jisekiCategorySoGokeiRecord.setFusuiZenki(sumFusuiZenki.add(new BigDecimal(jisekiCategoryGokeiRecord.getFusuiZenki())).toString());
			// 当期化粧品の合計
			jisekiCategorySoGokeiRecord.setKesyohinToki(sumKesyohinToki.add(new BigDecimal(jisekiCategoryGokeiRecord.getKesyohinToki())).toString());
			// 前期化粧品の合計
			jisekiCategorySoGokeiRecord.setKesyohinZenki(sumKesyohinZenki.add(new BigDecimal(jisekiCategoryGokeiRecord.getKesyohinZenki())).toString());
			// 当期その他の合計
			jisekiCategorySoGokeiRecord.setSonohokaToki(sumSonohokaToki.add(new BigDecimal(jisekiCategoryGokeiRecord.getSonohokaToki())).toString());
			// 前期その他の合計
			jisekiCategorySoGokeiRecord.setSonohokaZenki(sumSonohokaZenki.add(new BigDecimal(jisekiCategoryGokeiRecord.getSonohokaZenki())).toString());

			// 総合行を合計用リストに追加
			jisekiCategoryGokeiList.add(createDispRecord(jisekiCategorySoGokeiRecord));
		}
		return jisekiCategoryGokeiList;

	}

	/**
	 * DBから取得したカテゴリ課情報から表示用のリストを変換します。
	 *
	 * @param jisekiCagetoryKaList DBから取得した実績情報リスト
	 * @return jisekiCategoryKaDispList 表示用リスト
	 */
	public JisekiCategoryList convertToKaDispList(JisekiCategoryList jisekiCagetoryKaList) {
		JisekiCategoryList jisekiCategoryKaDispList = new JisekiCategoryList();
		// カテゴリリスト分の情報を繰り返し
		for (PbsRecord jisekiCategoryKaRecord : jisekiCagetoryKaList) {
			// 表示用のリストに追加
			jisekiCategoryKaDispList.add(createDispRecord((JisekiCategoryListRecord)jisekiCategoryKaRecord));
		}
		return jisekiCategoryKaDispList;
	}

	/**
	 * DBから取得したレコードを表示用のレコードに変換します。
	 *
	 * @param jisekiAriaRecord DBから取得した実績情報レコード
	 * @return jisekiAriaDispRecord 画面表示用の実績情報レコード
	 */
	public JisekiCategoryListRecord createDispRecord(JisekiCategoryListRecord jisekiCategoryRecord) {
		JisekiCategoryListRecord JisekiCategoryDispRecord = new JisekiCategoryListRecord();

		// エリア名
		JisekiCategoryDispRecord.setAriaNm(jisekiCategoryRecord.getAriaNm());
		// 前期
		JisekiCategoryDispRecord.setZenki(new BigDecimal(jisekiCategoryRecord.getZenki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 前期同日
		JisekiCategoryDispRecord.setZenkiDoji(new BigDecimal(jisekiCategoryRecord.getZenkiDoji()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 当期
		JisekiCategoryDispRecord.setToki(new BigDecimal(jisekiCategoryRecord.getToki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 当期清酒(小数点切り捨て)
		JisekiCategoryDispRecord.setSeisyuToki(new BigDecimal(jisekiCategoryRecord.getSeisyuToki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 前期清酒(小数点切り捨て)
		JisekiCategoryDispRecord.setSeisyuZenki(new BigDecimal(jisekiCategoryRecord.getSeisyuZenki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 当期ビール、発泡酒(小数点切り捨て)
		JisekiCategoryDispRecord.setBiruToki(new BigDecimal(jisekiCategoryRecord.getBiruToki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 前期ビール、発泡酒(小数点切り捨て)
		JisekiCategoryDispRecord.setBiruZenki(new BigDecimal(jisekiCategoryRecord.getBiruZenki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 当期焼酎(小数点切り捨て)
		JisekiCategoryDispRecord.setSyocyuToki(new BigDecimal(jisekiCategoryRecord.getSyocyuToki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 前期焼酎(小数点切り捨て)
		JisekiCategoryDispRecord.setSyocyuZenki(new BigDecimal(jisekiCategoryRecord.getSyocyuZenki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 当期リキュール(小数点切り捨て)
		JisekiCategoryDispRecord.setRikyuruToki(new BigDecimal(jisekiCategoryRecord.getRikyuruToki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 前期リキュール(小数点切り捨て)
		JisekiCategoryDispRecord.setRikyuruZenki(new BigDecimal(jisekiCategoryRecord.getRikyuruZenki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 当期調味料(小数点切り捨て)
		JisekiCategoryDispRecord.setCyomiryoToki(new BigDecimal(jisekiCategoryRecord.getCyomiryoToki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 前期調味料(小数点切り捨て)
		JisekiCategoryDispRecord.setCyomiryoZenki(new BigDecimal(jisekiCategoryRecord.getCyomiryoZenki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 当期伏水、サイファー(小数点切り捨て)
		JisekiCategoryDispRecord.setFusuiToki(new BigDecimal(jisekiCategoryRecord.getFusuiToki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 前期伏水、サイファー(小数点切り捨て)
		JisekiCategoryDispRecord.setFusuiZenki(new BigDecimal(jisekiCategoryRecord.getFusuiZenki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 当期化粧品(小数点切り捨て)
		JisekiCategoryDispRecord.setKesyohinToki(new BigDecimal(jisekiCategoryRecord.getKesyohinToki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 前期化粧品(小数点切り捨て)
		JisekiCategoryDispRecord.setKesyohinZenki(new BigDecimal(jisekiCategoryRecord.getKesyohinZenki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 当期その他(小数点切り捨て)
		JisekiCategoryDispRecord.setSonohokaToki(new BigDecimal(jisekiCategoryRecord.getSonohokaToki()).setScale(0, BigDecimal.ROUND_DOWN).toString());
		// 前期その他(小数点切り捨て)
		JisekiCategoryDispRecord.setSonohokaZenki(new BigDecimal(jisekiCategoryRecord.getSonohokaZenki()).setScale(0, BigDecimal.ROUND_DOWN).toString());

		// 同日比
		// 前期同日がゼロ以外の場合
		if (!PbsUtil.isZero(JisekiCategoryDispRecord.getZenkiDoji())) {
			// 当期 / 前期同日 * 100
			BigDecimal Dojitsuhi = new BigDecimal(JisekiCategoryDispRecord.getToki()).multiply(new BigDecimal("100")).divide(new BigDecimal(JisekiCategoryDispRecord.getZenkiDoji()), 1, BigDecimal.ROUND_DOWN);
			// 小数点１桁（切り捨て）
			JisekiCategoryDispRecord.setDojitsuhi(Dojitsuhi.toString());
		} else {
			JisekiCategoryDispRecord.setDojitsuhi(new BigDecimal("0").setScale(1, BigDecimal.ROUND_DOWN).toString());
		}
		// 進捗率
		// 前期がゼロ以外の場合
		if (!PbsUtil.isZero(JisekiCategoryDispRecord.getZenki())) {
			// 当期 / 前期  * 100
			BigDecimal shincyokuTogetsu = new BigDecimal(JisekiCategoryDispRecord.getToki()).multiply(new BigDecimal("100")).divide(new BigDecimal(JisekiCategoryDispRecord.getZenki()), 1, BigDecimal.ROUND_DOWN);
			// 小数点１桁（切り捨て）
			JisekiCategoryDispRecord.setShincyoku(shincyokuTogetsu.toString());
		} else {
			JisekiCategoryDispRecord.setShincyoku(new BigDecimal("0").setScale(1, BigDecimal.ROUND_DOWN).toString());
		}

		// 清酒対比
		// 清酒前期がゼロ以外の場合
		if (!PbsUtil.isZero(JisekiCategoryDispRecord.getSeisyuZenki())) {
			// 清酒当期 / 清酒前期  * 100
			BigDecimal seisyuTaihi = new BigDecimal(JisekiCategoryDispRecord.getSeisyuToki()).multiply(new BigDecimal("100")).divide(new BigDecimal(JisekiCategoryDispRecord.getSeisyuZenki()), 1, BigDecimal.ROUND_DOWN);
			// 小数点１桁（切り捨て）
			JisekiCategoryDispRecord.setSeisyuTaihi(seisyuTaihi.toString());
		} else {
			JisekiCategoryDispRecord.setSeisyuTaihi(new BigDecimal("0").setScale(1, BigDecimal.ROUND_DOWN).toString());
		}

		// 麦酒・発泡酒対比
		// 麦酒・発泡酒前期がゼロ以外の場合
		if (!PbsUtil.isZero(JisekiCategoryDispRecord.getBiruZenki())) {
			// 麦酒・発泡酒当期 / 麦酒・発泡酒前期  * 100
			BigDecimal biruTaihi = new BigDecimal(JisekiCategoryDispRecord.getBiruToki()).multiply(new BigDecimal("100")).divide(new BigDecimal(JisekiCategoryDispRecord.getBiruZenki()), 1, BigDecimal.ROUND_DOWN);
			// 小数点１桁（切り捨て）
			JisekiCategoryDispRecord.setBiruTaihi(biruTaihi.toString());
		} else {
			JisekiCategoryDispRecord.setBiruTaihi(new BigDecimal("0").setScale(1, BigDecimal.ROUND_DOWN).toString());
		}

		// 焼酎対比
		// 焼酎前期がゼロ以外の場合
		if (!PbsUtil.isZero(JisekiCategoryDispRecord.getSyocyuZenki())) {
			// 焼酎当期 / 焼酎前期  * 100
			BigDecimal syocyuTaihi = new BigDecimal(JisekiCategoryDispRecord.getSyocyuToki()).multiply(new BigDecimal("100")).divide(new BigDecimal(JisekiCategoryDispRecord.getSyocyuZenki()), 1, BigDecimal.ROUND_DOWN);
			// 小数点１桁（切り捨て）
			JisekiCategoryDispRecord.setSyocyuTaihi(syocyuTaihi.toString());
		} else {
			JisekiCategoryDispRecord.setSyocyuTaihi(new BigDecimal("0").setScale(1, BigDecimal.ROUND_DOWN).toString());
		}

		// リキュール対比
		// リキュール前期がゼロ以外の場合
		if (!PbsUtil.isZero(JisekiCategoryDispRecord.getRikyuruZenki())) {
			// リキュール当期 / リキュール前期  * 100
			BigDecimal rikyuruTaihi = new BigDecimal(JisekiCategoryDispRecord.getRikyuruToki()).multiply(new BigDecimal("100")).divide(new BigDecimal(JisekiCategoryDispRecord.getRikyuruZenki()), 1, BigDecimal.ROUND_DOWN);
			// 小数点１桁（切り捨て）
			JisekiCategoryDispRecord.setRikyuruTaihi(rikyuruTaihi.toString());
		} else {
			JisekiCategoryDispRecord.setRikyuruTaihi(new BigDecimal("0").setScale(1, BigDecimal.ROUND_DOWN).toString());
		}

		// 調味料対比
		// 調味料前期がゼロ以外の場合
		if (!PbsUtil.isZero(JisekiCategoryDispRecord.getCyomiryoZenki())) {
			// 調味料当期 / 調味料前期  * 100
			BigDecimal cyomiryoTaihi = new BigDecimal(JisekiCategoryDispRecord.getCyomiryoToki()).multiply(new BigDecimal("100")).divide(new BigDecimal(JisekiCategoryDispRecord.getCyomiryoZenki()), 1, BigDecimal.ROUND_DOWN);
			// 小数点１桁（切り捨て）
			JisekiCategoryDispRecord.setCyomiryoTaihi(cyomiryoTaihi.toString());
		} else {
			JisekiCategoryDispRecord.setCyomiryoTaihi(new BigDecimal("0").setScale(1, BigDecimal.ROUND_DOWN).toString());
		}

		// 伏水・サイファー対比
		// 伏水・サイファー前期がゼロ以外の場合
		if (!PbsUtil.isZero(JisekiCategoryDispRecord.getFusuiZenki())) {
			// 伏水・サイファー当期 / 伏水・サイファー前期  * 100
			BigDecimal fusuiTaihi = new BigDecimal(JisekiCategoryDispRecord.getFusuiToki()).multiply(new BigDecimal("100")).divide(new BigDecimal(JisekiCategoryDispRecord.getFusuiZenki()), 1, BigDecimal.ROUND_DOWN);
			// 小数点１桁（切り捨て）
			JisekiCategoryDispRecord.setFusuiTaihi(fusuiTaihi.toString());
		} else {
			JisekiCategoryDispRecord.setFusuiTaihi(new BigDecimal("0").setScale(1, BigDecimal.ROUND_DOWN).toString());
		}

		// 化粧品対比
		// 化粧品がゼロ以外の場合
		if (!PbsUtil.isZero(JisekiCategoryDispRecord.getKesyohinZenki())) {
			// 化粧品当期 / 化粧品前期  * 100
			BigDecimal kesyohinTaihi = new BigDecimal(JisekiCategoryDispRecord.getKesyohinToki()).multiply(new BigDecimal("100")).divide(new BigDecimal(JisekiCategoryDispRecord.getKesyohinZenki()), 1, BigDecimal.ROUND_DOWN);
			// 小数点１桁（切り捨て）
			JisekiCategoryDispRecord.setKesyohinTaihi(kesyohinTaihi.toString());
		} else {
			JisekiCategoryDispRecord.setKesyohinTaihi(new BigDecimal("0").setScale(1, BigDecimal.ROUND_DOWN).toString());
		}
		// その他対比
		// その他前期がゼロ以外の場合
		if (!PbsUtil.isZero(JisekiCategoryDispRecord.getSonohokaZenki())) {
			// その他当期 / その他前期  * 100
			BigDecimal sonohakaTaihi = new BigDecimal(JisekiCategoryDispRecord.getSonohokaToki()).multiply(new BigDecimal("100")).divide(new BigDecimal(JisekiCategoryDispRecord.getSonohokaZenki()), 1, BigDecimal.ROUND_DOWN);
			// 小数点１桁（切り捨て）
			JisekiCategoryDispRecord.setSonohokaTaihi(sonohakaTaihi.toString());
		} else {
			JisekiCategoryDispRecord.setSonohokaTaihi(new BigDecimal("0").setScale(1, BigDecimal.ROUND_DOWN).toString());
		}

		return JisekiCategoryDispRecord;
	}

}
