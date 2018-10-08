package kit.jiseki.ESienList;

import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_RUIKEI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_HONNEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_RUIKEI_HONNEN;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import kit.jiseki.ESienList.JisekiESienList_SearchHelper.ColInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.DateInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.DateRange;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.HonnenZennenDateRange;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.TranTable;

/**
 * 対比なし用セレクトパターン
 * @author t_kimura
 *
 */
public class JisekiESienList_SearchSelectPtn_TaihiNasi implements IJisekiESienList_SearchSelectPtn {

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#isHeader1Line()
	 */
	@Override
	public boolean isHeader1Line() {
		return true;
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#isRuikeiHeader1Line()
	 */
	@Override
	public boolean isRuikeiHeader1Line() {
		return true;
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#getRuikeiHeader(kit.jiseki.ESienList.JisekiESienList_SearchHelper, kit.jiseki.ESienList.JisekiESienList_SearchForm)
	 */
	@Override
	public ColInfo getRuikeiHeader(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm) {
		ColInfo ruikeiHeader = searchHelper.new ColInfo();
		// 累計
		ruikeiHeader.setRow1Col(HEADER_NM_RUIKEI);
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
			dateHeaderList.add(colInfo);
		}
		return dateHeaderList;
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#setupRecord(kit.jiseki.ESienList.JisekiESienList_SearchHelper, kit.jiseki.ESienList.JisekiESienList_SearchForm, kit.jiseki.ESienList.JisekiESienListRecord)
	 */
	@Override
	public void setupRecord(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, JisekiESienListRecord record) {

		// 累計の本年
		BigDecimal honnenRuikei = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + SELECT_COL_NM_RUIKEI_HONNEN), record.getValue(TranTable.SYUKA.withAliasForColNm() + SELECT_COL_NM_RUIKEI_HONNEN), record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_RUIKEI_HONNEN));
		record.setRuikeiColInfo(SELECT_COL_NM_RUIKEI_HONNEN, searchHelper.round(honnenRuikei).toString());

		// 日付範囲リストのサイズ
		int dateSize = searchHelper.getDateInfo().getDateRange().size();
		//BigDecimal ruikei = BigDecimal.ZERO;
		for (int i = 0; i < dateSize; i++) {
			// 本年
			String honnenCol = String.format(SELECT_COL_NM_HONNEN, i+1);
			BigDecimal honnen = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + honnenCol), record.getValue(TranTable.SYUKA.withAliasForColNm() + honnenCol), record.getValue(TranTable.REINYU.withAliasForColNm() + honnenCol));
			//ruikei = ruikei.add(honnen);
			record.setDateColInfo(honnenCol, searchHelper.round(honnen).toString());
		}
		// 累計
		//record.setRuikeiColInfo(SELECT_COL_NM_RUIKEI, searchHelper.round(ruikei).toString());
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#getSelectColList(kit.jiseki.ESienList.JisekiESienList_SearchForm, int)
	 */
	@Override
	public List<String> getSelectColList(JisekiESienList_SearchForm searchForm, int dateListSize) {
		List<String> colList = new ArrayList<String>();
		// 累計_本年
		colList.add(SELECT_COL_NM_RUIKEI_HONNEN);

		for (int i = 0; i < dateListSize; i++) {
			// 本年
			colList.add(String.format(SELECT_COL_NM_HONNEN, i+1));
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

		List<HonnenZennenDateRange> dateRangeList = dateInfo.getDateRange();
		int index = 0;
		for (HonnenZennenDateRange dateRange : dateRangeList) {
			index++;
			// 本年
			if(dateRange.hasHonnen()) {
				sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(String.format(SELECT_COL_NM_HONNEN, index)).append(" \n");
				bindList.add(dateRange.getFrom());
				bindList.add(dateRange.getTo());
			} else {
				sql.append("        ,0 AS ").append(String.format(SELECT_COL_NM_HONNEN, index)).append(" \n");
			}
		}
		return sql.toString();
	}
}
