package kit.jiseki.ESienList;

import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_HONNEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_NIKKEI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_TAIHI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_TOGETU_10_RUIKEI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_TOGETU_1_RUIKEI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_TOGETU_RUIKEI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_ZENNEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_NIKKEI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_10_HONNEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_10_TAIHI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_10_ZENNEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_1_HONNEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_1_TAIHI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_1_ZENNEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_HONNEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_TAIHI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_ZENNEN;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import kit.jiseki.ESienList.JisekiESienList_SearchHelper.ColInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.DateInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.HonnenZennenDateRange;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.TranTable;

/**
 * 当月・10～当月・1～当月用セレクトパターン
 * @author t_kimura
 *
 */
public class JisekiESienList_SearchSelectPtn_Tougetu implements IJisekiESienList_SearchSelectPtn {

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
		return true;
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#getRuikeiHeader(kit.jiseki.ESienList.JisekiESienList_SearchHelper, kit.jiseki.ESienList.JisekiESienList_SearchForm)
	 */
	@Override
	public ColInfo getRuikeiHeader(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm) {
		ColInfo ruikeiHeader = searchHelper.new ColInfo();
		// 日計
		ruikeiHeader.setRow1Col(HEADER_NM_NIKKEI);
		return ruikeiHeader;
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#getDateHeaderList(kit.jiseki.ESienList.JisekiESienList_SearchHelper, kit.jiseki.ESienList.JisekiESienList_SearchForm, java.util.List)
	 */
	@Override
	public List<ColInfo> getDateHeaderList(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, List<HonnenZennenDateRange> dateList) {
		List<ColInfo> dateHeaderList = new ArrayList<ColInfo>();
		ColInfo togetu = searchHelper.new ColInfo();
		// 当月累計
		togetu.setRow1Col(HEADER_NM_TOGETU_RUIKEI);
		// 本年・前年・対比
		togetu.getRow2ColList().add(HEADER_NM_HONNEN);
		togetu.getRow2ColList().add(HEADER_NM_ZENNEN);
		togetu.getRow2ColList().add(HEADER_NM_TAIHI);

		ColInfo togetu10 = searchHelper.new ColInfo();
		// 10～当月累計
		togetu10.setRow1Col(HEADER_NM_TOGETU_10_RUIKEI);
		// 本年・前年・対比
		togetu10.getRow2ColList().add(HEADER_NM_HONNEN);
		togetu10.getRow2ColList().add(HEADER_NM_ZENNEN);
		togetu10.getRow2ColList().add(HEADER_NM_TAIHI);

		ColInfo togetu1 = searchHelper.new ColInfo();
		// 1～当月累計
		togetu1.setRow1Col(HEADER_NM_TOGETU_1_RUIKEI);
		// 本年・前年・対比
		togetu1.getRow2ColList().add(HEADER_NM_HONNEN);
		togetu1.getRow2ColList().add(HEADER_NM_ZENNEN);
		togetu1.getRow2ColList().add(HEADER_NM_TAIHI);

		dateHeaderList.add(togetu);
		dateHeaderList.add(togetu10);
		dateHeaderList.add(togetu1);
		return dateHeaderList;
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#setupRecord(kit.jiseki.ESienList.JisekiESienList_SearchHelper, kit.jiseki.ESienList.JisekiESienList_SearchForm, kit.jiseki.ESienList.JisekiESienListRecord)
	 */
	@Override
	public void setupRecord(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, JisekiESienListRecord record) {

		// 日計
		String nikkei = SELECT_COL_NM_NIKKEI;
		record.setRuikeiColInfo(nikkei, searchHelper.round(searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + nikkei), record.getValue(TranTable.SYUKA.withAliasForColNm() + nikkei), record.getValue(TranTable.REINYU.withAliasForColNm() + nikkei))).toString());

		// 当月累計
		// 本年
		BigDecimal honnen1 = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_HONNEN), record.getValue(TranTable.SYUKA.withAliasForColNm() + SELECT_COL_NM_TOGETU_HONNEN), record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_HONNEN));
		// 前年
		BigDecimal zennen1 = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_ZENNEN), record.getValue(TranTable.SYUKA.withAliasForColNm() + SELECT_COL_NM_TOGETU_ZENNEN), record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_ZENNEN));
		// 丸めた値をレコードへ保存
		record.setDateColInfo(SELECT_COL_NM_TOGETU_HONNEN, searchHelper.round(honnen1).toString());
		record.setDateColInfo(SELECT_COL_NM_TOGETU_ZENNEN, searchHelper.round(zennen1).toString());
		// 対比計算
		record.setDateColInfo(SELECT_COL_NM_TOGETU_TAIHI, searchHelper.calcRoundAndTaihi(honnen1,zennen1).toString());

		// 10～当月累計
		// 本年
		BigDecimal honnen2 = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_10_HONNEN), record.getValue(TranTable.SYUKA.withAliasForColNm() + SELECT_COL_NM_TOGETU_10_HONNEN), record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_10_HONNEN));
		// 前年
		BigDecimal zennen2 = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_10_ZENNEN), record.getValue(TranTable.SYUKA.withAliasForColNm() + SELECT_COL_NM_TOGETU_10_ZENNEN), record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_10_ZENNEN));
		// 丸めた値をレコードへ保存
		record.setDateColInfo(SELECT_COL_NM_TOGETU_10_HONNEN, searchHelper.round(honnen2).toString());
		record.setDateColInfo(SELECT_COL_NM_TOGETU_10_ZENNEN, searchHelper.round(zennen2).toString());
		// 対比計算
		record.setDateColInfo(SELECT_COL_NM_TOGETU_10_TAIHI, searchHelper.calcRoundAndTaihi(honnen2,zennen2).toString());

		// 1～当月累計
		// 本年
		BigDecimal honnen3 = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_1_HONNEN), record.getValue(TranTable.SYUKA.withAliasForColNm() + SELECT_COL_NM_TOGETU_1_HONNEN), record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_1_HONNEN));
		// 前年
		BigDecimal zennen3 = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_1_ZENNEN), record.getValue(TranTable.SYUKA.withAliasForColNm() + SELECT_COL_NM_TOGETU_1_ZENNEN), record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_1_ZENNEN));
		// 丸めた値をレコードへ保存
		record.setDateColInfo(SELECT_COL_NM_TOGETU_1_HONNEN, searchHelper.round(honnen3).toString());
		record.setDateColInfo(SELECT_COL_NM_TOGETU_1_ZENNEN, searchHelper.round(zennen3).toString());
		// 対比計算
		record.setDateColInfo(SELECT_COL_NM_TOGETU_1_TAIHI, searchHelper.calcRoundAndTaihi(honnen3,zennen3).toString());
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#getSelectColList(kit.jiseki.ESienList.JisekiESienList_SearchForm, int)
	 */
	@Override
	public List<String> getSelectColList(JisekiESienList_SearchForm searchForm, int dateListSize) {
		List<String> colList = new ArrayList<String>();
		// 日計
		colList.add(SELECT_COL_NM_NIKKEI);

		// 当月累計本年・前年
		colList.add(SELECT_COL_NM_TOGETU_HONNEN);
		colList.add(SELECT_COL_NM_TOGETU_ZENNEN);
		// 10～当月累計本年・前年
		colList.add(SELECT_COL_NM_TOGETU_10_HONNEN);
		colList.add(SELECT_COL_NM_TOGETU_10_ZENNEN);
		// 1～当月累計本年・前年
		colList.add(SELECT_COL_NM_TOGETU_1_HONNEN);
		colList.add(SELECT_COL_NM_TOGETU_1_ZENNEN);
		return colList;
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#getTranSelectSql(kit.jiseki.ESienList.JisekiESienList_SearchForm, kit.jiseki.ESienList.JisekiESienList_SearchHelper.DateInfo, kit.jiseki.ESienList.JisekiESienList_SearchHelper.TranTable, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public String getTranSelectSql(JisekiESienList_SearchForm searchForm, DateInfo dateInfo, TranTable tranTable, String syukeiCol, String keisanDateKijunCol, List<String> bindList) {
		List<HonnenZennenDateRange> dateRangeList = dateInfo.getDateRange();
		StringBuilder sql = new StringBuilder();
		// 日計
		sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" = ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_NIKKEI).append(" \n");
		bindList.add(searchForm.getTaisyoDate());

		// 当月累計本年・前年
		sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_TOGETU_HONNEN).append(" \n");
		sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_TOGETU_ZENNEN).append(" \n");
		bindList.add(dateRangeList.get(0).getFrom());
		bindList.add(dateRangeList.get(0).getTo());
		bindList.add(dateRangeList.get(0).getZennenFrom());
		bindList.add(dateRangeList.get(0).getZennenTo());
		// 10～当月累計本年・前年
		sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_TOGETU_10_HONNEN).append(" \n");
		sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_TOGETU_10_ZENNEN).append(" \n");
		bindList.add(dateRangeList.get(1).getFrom());
		bindList.add(dateRangeList.get(1).getTo());
		bindList.add(dateRangeList.get(1).getZennenFrom());
		bindList.add(dateRangeList.get(1).getZennenTo());
		// 1～当月累計本年・前年
		sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_TOGETU_1_HONNEN).append(" \n");
		sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_TOGETU_1_ZENNEN).append(" \n");
		bindList.add(dateRangeList.get(2).getFrom());
		bindList.add(dateRangeList.get(2).getTo());
		bindList.add(dateRangeList.get(2).getZennenFrom());
		bindList.add(dateRangeList.get(2).getZennenTo());
		return sql.toString();
	}
}
