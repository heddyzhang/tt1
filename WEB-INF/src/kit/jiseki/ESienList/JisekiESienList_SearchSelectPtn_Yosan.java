package kit.jiseki.ESienList;

import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_1000YEN;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_KL;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_KOKU;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_L;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_YEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_HONNEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_HONNEN_YOSAN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_RUIKEI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_SINCYOKURITU;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_ZENNEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_ZENNENHI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_HONNEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_RUIKEI_HONNEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_RUIKEI_HONNEN_YOSAN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_RUIKEI_TAIHI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_RUIKEI_YOSANHI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_RUIKEI_ZENNEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TAIHI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_ZENNEN;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fb.inf.pbs.PbsUtil;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.ColInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.DateInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.DateRange;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.HonnenZennenDateRange;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.KeyCol;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.TranTable;

/**
 * 予算対比セレクトパターン
 * @author t_kimura
 *
 */
public class JisekiESienList_SearchSelectPtn_Yosan implements IJisekiESienList_SearchSelectPtn {

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#isHeader1Line()
	 */
	@Override
	public boolean isHeader1Line() {
		return false;
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#isRuikeiHeader1Line()
	 */
	@Override
	public boolean isRuikeiHeader1Line() {
		return false;
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#getRuikeiHeader(kit.jiseki.ESienList.JisekiESienList_SearchHelper, kit.jiseki.ESienList.JisekiESienList_SearchForm)
	 */
	@Override
	public ColInfo getRuikeiHeader(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm) {
		ColInfo ruikeiHeader = searchHelper.new ColInfo();
		// 累計
		ruikeiHeader.setRow1Col(HEADER_NM_RUIKEI);
		// 本年・前年・前年比・本年予算・進捗率
		ruikeiHeader.getRow2ColList().add(HEADER_NM_HONNEN);
		ruikeiHeader.getRow2ColList().add(HEADER_NM_ZENNEN);
		ruikeiHeader.getRow2ColList().add(HEADER_NM_ZENNENHI);
		ruikeiHeader.getRow2ColList().add(HEADER_NM_HONNEN_YOSAN);
		ruikeiHeader.getRow2ColList().add(HEADER_NM_SINCYOKURITU);
		return ruikeiHeader;
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#getDateHeaderList(kit.jiseki.ESienList.JisekiESienList_SearchHelper, kit.jiseki.ESienList.JisekiESienList_SearchForm, java.util.List)
	 */
	@Override
	public List<ColInfo> getDateHeaderList(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, List<HonnenZennenDateRange> dateList) {
		List<ColInfo> dateHeaderList = new ArrayList<ColInfo>();
		String taisyoHyojiKikan = searchForm.getTaisyoHyojiKikan();
		for (DateRange range : dateList) {
			ColInfo colInfo = searchHelper.new ColInfo();
			// 日付
			colInfo.setRow1Col(searchHelper.getFormatDate(taisyoHyojiKikan, range.getDispDt()));
			// 本年・前年・対比
			colInfo.getRow2ColList().add(HEADER_NM_HONNEN);
			colInfo.getRow2ColList().add(HEADER_NM_ZENNEN);
			colInfo.getRow2ColList().add(HEADER_NM_ZENNENHI);
			dateHeaderList.add(colInfo);
		}
		return dateHeaderList;
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#setupRecord(kit.jiseki.ESienList.JisekiESienList_SearchHelper, kit.jiseki.ESienList.JisekiESienList_SearchForm, kit.jiseki.ESienList.JisekiESienListRecord)
	 */
	@Override
	public void setupRecord(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, JisekiESienListRecord record) {

		// 事業所毎の予算
		Map<String, String> jigyosyoYosan = searchHelper.getJigyosyoYosanMap();

		// 日付範囲リストのサイズ
		int dateSize = searchHelper.getDateInfo().getDateRange().size();

		// 本年累計
		BigDecimal honnenRuikei = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + SELECT_COL_NM_RUIKEI_HONNEN), record.getValue(TranTable.SYUKA.withAliasForColNm() + SELECT_COL_NM_RUIKEI_HONNEN), record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_RUIKEI_HONNEN));
		// 前年累計
		BigDecimal zennenDecimal = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + SELECT_COL_NM_RUIKEI_ZENNEN), record.getValue(TranTable.SYUKA.withAliasForColNm() + SELECT_COL_NM_RUIKEI_ZENNEN), record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_RUIKEI_HONNEN));

		// 予算取得
		// 事業所コード
		BigDecimal yosanDecimal = searchHelper.convertBigDecimal(jigyosyoYosan.get(record.get(KeyCol.JIGYOSYO_CD.getCol())));
		// 表示単位
		String hyojiTani = searchForm.getHyojiTani();

		// 単位変換
		if(PbsUtil.isEqual(hyojiTani,JSK_HYOJI_TANI_KB_YEN)) {
			// 円
			yosanDecimal = yosanDecimal.setScale(0, BigDecimal.ROUND_DOWN);
		} else if(PbsUtil.isEqual(hyojiTani,JSK_HYOJI_TANI_KB_1000YEN)) {
			// 千円
			yosanDecimal = yosanDecimal.divide(new BigDecimal("1000"), 0, BigDecimal.ROUND_DOWN);
		} else if(PbsUtil.isEqual(hyojiTani,JSK_HYOJI_TANI_KB_KOKU)) {
			// 石数
			yosanDecimal = yosanDecimal.setScale(3, BigDecimal.ROUND_DOWN);
		} else if(PbsUtil.isEqual(hyojiTani,JSK_HYOJI_TANI_KB_L)) {
			// L数
			yosanDecimal = yosanDecimal.multiply(new BigDecimal("180")).setScale(3);
		} else if(PbsUtil.isEqual(hyojiTani,JSK_HYOJI_TANI_KB_KL)) {
			// KL数
			yosanDecimal = yosanDecimal.multiply(new BigDecimal("180")).divide(new BigDecimal("1000"), 3, BigDecimal.ROUND_DOWN);
		} else {
			throw new IllegalArgumentException();
		}

		// 累計_本年
		record.setRuikeiColInfo(SELECT_COL_NM_RUIKEI_HONNEN, searchHelper.round(honnenRuikei).toString());
		// 累計_前年
		record.setRuikeiColInfo(SELECT_COL_NM_RUIKEI_ZENNEN, searchHelper.round(zennenDecimal).toString());
		// 累計_前年比
		record.setRuikeiColInfo(SELECT_COL_NM_RUIKEI_TAIHI, searchHelper.calcRoundAndTaihi(honnenRuikei, zennenDecimal).toString());
		// 累計_本年予算
		record.setRuikeiColInfo(SELECT_COL_NM_RUIKEI_HONNEN_YOSAN, yosanDecimal.toString());
		// 累計_進捗率
		record.setRuikeiColInfo(SELECT_COL_NM_RUIKEI_YOSANHI, searchHelper.calcTaihi(searchHelper.round(honnenRuikei), yosanDecimal).toString());

		for (int i = 0; i < dateSize; i++) {
			// 本年
			String honnenCol = String.format(SELECT_COL_NM_HONNEN, i+1);
			// 前年
			String zennenCol = String.format(SELECT_COL_NM_ZENNEN, i+1);
			// 計算
			BigDecimal honnen = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + honnenCol), record.getValue(TranTable.SYUKA.withAliasForColNm() + honnenCol), record.getValue(TranTable.REINYU.withAliasForColNm() + honnenCol));
			BigDecimal zennen = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + zennenCol), record.getValue(TranTable.SYUKA.withAliasForColNm() + zennenCol), record.getValue(TranTable.REINYU.withAliasForColNm() + zennenCol));
			// 丸めた値をレコードへ保存
			record.setDateColInfo(honnenCol, searchHelper.round(honnen).toString());
			record.setDateColInfo(zennenCol, searchHelper.round(zennen).toString());
			// 対比
			record.setDateColInfo(String.format(SELECT_COL_NM_TAIHI, i+1), searchHelper.calcRoundAndTaihi(honnen,zennen).toString());
		}
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#getSelectColList(kit.jiseki.ESienList.JisekiESienList_SearchForm, int)
	 */
	@Override
	public List<String> getSelectColList(JisekiESienList_SearchForm searchForm, int dateListSize) {
		List<String> colList = new ArrayList<String>();
		// 累計_本年
		colList.add(SELECT_COL_NM_RUIKEI_HONNEN);
		// 累計_本年
		colList.add(SELECT_COL_NM_RUIKEI_ZENNEN);

		for (int i = 0; i < dateListSize; i++) {
			// 本年・前年
			colList.add(String.format(SELECT_COL_NM_HONNEN, i+1));
			colList.add(String.format(SELECT_COL_NM_ZENNEN, i+1));
		}
		return colList;
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#getTranSelectSql(kit.jiseki.ESienList.JisekiESienList_SearchForm, kit.jiseki.ESienList.JisekiESienList_SearchHelper.DateInfo, kit.jiseki.ESienList.JisekiESienList_SearchHelper.TranTable, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public String getTranSelectSql(JisekiESienList_SearchForm searchForm, DateInfo dateInfo, TranTable tranTable, String syukeiCol, String keisanDateKijunCol, List<String> bindList) {
		StringBuilder sql = new StringBuilder();
		// 本年累計
		sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_RUIKEI_HONNEN).append(" \n");
		bindList.add(dateInfo.getRuikeiDateRange().getFrom());
		bindList.add(dateInfo.getRuikeiDateRange().getTo());
		// 前年累計
		sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_RUIKEI_ZENNEN).append(" \n");
		bindList.add(dateInfo.getRuikeiDateRange().getZennenFrom());
		bindList.add(dateInfo.getRuikeiDateRange().getZennenTo());

		List<HonnenZennenDateRange> dateRangeList = dateInfo.getDateRange();
		int index = 0;
		for (HonnenZennenDateRange dateRange : dateRangeList) {
			index++;
			if(dateRange.hasHonnen()) {
				// 本年
				sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(String.format(SELECT_COL_NM_HONNEN, index)).append(" \n");
				bindList.add(dateRange.getFrom());
				bindList.add(dateRange.getTo());
			} else {
				sql.append("        ,0 AS ").append(String.format(SELECT_COL_NM_HONNEN, index)).append(" \n");
			}
			if(dateRange.hasZennen()) {
				// 前年
				sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(String.format(SELECT_COL_NM_ZENNEN, index)).append(" \n");
				bindList.add(dateRange.getZennenFrom());
				bindList.add(dateRange.getZennenTo());
			} else {
				sql.append("        ,0 AS ").append(String.format(SELECT_COL_NM_ZENNEN, index)).append(" \n");
			}
		}
		return sql.toString();
	}
}
