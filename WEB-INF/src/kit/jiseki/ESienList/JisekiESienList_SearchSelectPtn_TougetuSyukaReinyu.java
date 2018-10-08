package kit.jiseki.ESienList;

import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_NIKKEI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_REINYU;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_SYUKA;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_TAIHI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_TOGETU_10_RUIKEI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_TOGETU_1_RUIKEI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_TOGETU_RUIKEI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_NIKKEI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_10_REINYU;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_10_SYUKA;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_10_TAIHI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_1_REINYU;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_1_SYUKA;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_1_TAIHI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_REINYU;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_SYUKA;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TOGETU_TAIHI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import kit.jiseki.ESienList.JisekiESienList_SearchHelper.ColInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.DateInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.HonnenZennenDateRange;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.TranTable;

/**
 * 当月・10～当月・1～当月(出荷・戻入対比)用セレクトパターン
 * @author t_kimura
 *
 */
public class JisekiESienList_SearchSelectPtn_TougetuSyukaReinyu implements IJisekiESienList_SearchSelectPtn {
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
		togetu.getRow2ColList().add(HEADER_NM_SYUKA);
		togetu.getRow2ColList().add(HEADER_NM_REINYU);
		togetu.getRow2ColList().add(HEADER_NM_TAIHI);

		ColInfo togetu10 = searchHelper.new ColInfo();
		// 10～当月累計
		togetu10.setRow1Col(HEADER_NM_TOGETU_10_RUIKEI);
		// 本年・前年・対比
		togetu10.getRow2ColList().add(HEADER_NM_SYUKA);
		togetu10.getRow2ColList().add(HEADER_NM_REINYU);
		togetu10.getRow2ColList().add(HEADER_NM_TAIHI);

		ColInfo togetu1 = searchHelper.new ColInfo();
		// 1～当月累計
		togetu1.setRow1Col(HEADER_NM_TOGETU_1_RUIKEI);
		// 本年・前年・対比
		togetu1.getRow2ColList().add(HEADER_NM_SYUKA);
		togetu1.getRow2ColList().add(HEADER_NM_REINYU);
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
		// 出荷
		BigDecimal syuka1 = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_SYUKA), record.getValue(TranTable.SYUKA.withAliasForColNm() + SELECT_COL_NM_TOGETU_SYUKA), record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_SYUKA));
		// 戻入
		BigDecimal reinyu1 = searchHelper.convertBigDecimal(record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_REINYU));
		// 丸めた値をレコードへ保存
		record.setDateColInfo(SELECT_COL_NM_TOGETU_SYUKA, searchHelper.round(syuka1).toString());
		record.setDateColInfo(SELECT_COL_NM_TOGETU_REINYU, searchHelper.round(reinyu1).toString());
		// 対比計算
		record.setDateColInfo(SELECT_COL_NM_TOGETU_TAIHI, searchHelper.calcRoundAndTaihi(syuka1,reinyu1).toString());

		// 10～当月累計
		// 出荷
		BigDecimal syuka2 = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_10_SYUKA), record.getValue(TranTable.SYUKA.withAliasForColNm() + SELECT_COL_NM_TOGETU_10_SYUKA), record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_10_SYUKA));
		// 戻入
		BigDecimal reinyu2 = searchHelper.convertBigDecimal(record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_10_REINYU));
		// 丸めた値をレコードへ保存
		record.setDateColInfo(SELECT_COL_NM_TOGETU_10_SYUKA, searchHelper.round(syuka2).toString());
		record.setDateColInfo(SELECT_COL_NM_TOGETU_10_REINYU, searchHelper.round(reinyu2).toString());
		// 対比計算
		record.setDateColInfo(SELECT_COL_NM_TOGETU_10_TAIHI, searchHelper.calcRoundAndTaihi(syuka2,reinyu2).toString());

		// 1～当月累計
		// 出荷
		BigDecimal syuka3 = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_1_SYUKA), record.getValue(TranTable.SYUKA.withAliasForColNm() + SELECT_COL_NM_TOGETU_1_SYUKA), record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_1_SYUKA));
		// 戻入
		BigDecimal reinyu3 = searchHelper.convertBigDecimal(record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_TOGETU_1_REINYU));
		// 丸めた値をレコードへ保存
		record.setDateColInfo(SELECT_COL_NM_TOGETU_1_SYUKA, searchHelper.round(syuka3).toString());
		record.setDateColInfo(SELECT_COL_NM_TOGETU_1_REINYU, searchHelper.round(reinyu3).toString());
		// 対比計算
		record.setDateColInfo(SELECT_COL_NM_TOGETU_1_TAIHI, searchHelper.calcRoundAndTaihi(syuka3,reinyu3).toString());
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#getSelectColList(kit.jiseki.ESienList.JisekiESienList_SearchForm, int)
	 */
	@Override
	public List<String> getSelectColList(JisekiESienList_SearchForm searchForm, int dateListSize) {
		List<String> colList = new ArrayList<String>();
		// 日計
		colList.add(SELECT_COL_NM_NIKKEI);

		// 当月累計出荷・戻入
		colList.add(SELECT_COL_NM_TOGETU_SYUKA);
		colList.add(SELECT_COL_NM_TOGETU_REINYU);
		// 10～当月累計出荷・戻入
		colList.add(SELECT_COL_NM_TOGETU_10_SYUKA);
		colList.add(SELECT_COL_NM_TOGETU_10_REINYU);
		// 1～当月累計出荷・戻入
		colList.add(SELECT_COL_NM_TOGETU_1_SYUKA);
		colList.add(SELECT_COL_NM_TOGETU_1_REINYU);
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
		if(tranTable != TranTable.REINYU) {
			sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" = ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_NIKKEI).append(" \n");
			bindList.add(searchForm.getTaisyoDate());
		} else {
			sql.append("        ,0 AS ").append(SELECT_COL_NM_NIKKEI).append(" \n");
		}

		// 当月累計本年・前年
		if(tranTable != TranTable.REINYU) {
			sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_TOGETU_SYUKA).append(" \n");
			sql.append("        ,0 AS ").append(SELECT_COL_NM_TOGETU_REINYU).append(" \n");
		} else {
			sql.append("        ,0 AS ").append(SELECT_COL_NM_TOGETU_SYUKA).append(" \n");
			sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_TOGETU_REINYU).append(" \n");
		}
		bindList.add(dateRangeList.get(0).getFrom());
		bindList.add(dateRangeList.get(0).getTo());

		// 10～当月累計本年・前年
		if(tranTable != TranTable.REINYU) {
			sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_TOGETU_10_SYUKA).append(" \n");
			sql.append("        ,0 AS ").append(SELECT_COL_NM_TOGETU_10_REINYU).append(" \n");
		} else {
			sql.append("        ,0 AS ").append(SELECT_COL_NM_TOGETU_10_SYUKA).append(" \n");
			sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_TOGETU_10_REINYU).append(" \n");
		}
		bindList.add(dateRangeList.get(1).getFrom());
		bindList.add(dateRangeList.get(1).getTo());

		// 1～当月累計本年・前年
		if(tranTable != TranTable.REINYU) {
			sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_TOGETU_1_SYUKA).append(" \n");
			sql.append("        ,0 AS ").append(SELECT_COL_NM_TOGETU_1_REINYU).append(" \n");
		} else {
			sql.append("        ,0 AS ").append(SELECT_COL_NM_TOGETU_1_SYUKA).append(" \n");
			sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_TOGETU_1_REINYU).append(" \n");
		}
		bindList.add(dateRangeList.get(2).getFrom());
		bindList.add(dateRangeList.get(2).getTo());
		return sql.toString();
	}
}
