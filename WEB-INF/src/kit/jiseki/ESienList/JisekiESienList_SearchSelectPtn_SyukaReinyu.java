package kit.jiseki.ESienList;

import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_REINYU;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_RUIKEI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_SYUKA;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.HEADER_NM_TAIHI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_REINYU;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_RUIKEI_HONNEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_RUIKEI_REINYU;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_RUIKEI_SYUKA;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_RUIKEI_TAIHI;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_RUIKEI_ZENNEN;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_SYUKA;
import static kit.jiseki.ESienList.JisekiESienList_SearchHelper.SELECT_COL_NM_TAIHI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import kit.jiseki.ESienList.JisekiESienList_SearchHelper.ColInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.DateInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.DateRange;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.HonnenZennenDateRange;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.TranTable;

/**
 * 出荷・戻入用セレクトパターン
 * @author t_kimura
 *
 */
public class JisekiESienList_SearchSelectPtn_SyukaReinyu implements IJisekiESienList_SearchSelectPtn {

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
		// 出荷・戻入・対比
		ruikeiHeader.getRow2ColList().add(HEADER_NM_SYUKA);
		ruikeiHeader.getRow2ColList().add(HEADER_NM_REINYU);
		ruikeiHeader.getRow2ColList().add(HEADER_NM_TAIHI);
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
			// 出荷・戻入・対比
			colInfo.getRow2ColList().add(HEADER_NM_SYUKA);
			colInfo.getRow2ColList().add(HEADER_NM_REINYU);
			colInfo.getRow2ColList().add(HEADER_NM_TAIHI);
			dateHeaderList.add(colInfo);
		}
		return dateHeaderList;

	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#setupRecord(kit.jiseki.ESienList.JisekiESienList_SearchHelper, kit.jiseki.ESienList.JisekiESienList_SearchForm, kit.jiseki.ESienList.JisekiESienListRecord)
	 */
	@Override
	public void setupRecord(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, JisekiESienListRecord record) {
		// 日付範囲リストのサイズ
		int dateSize = searchHelper.getDateInfo().getDateRange().size();

		// 累計の出荷・戻入・対比
		BigDecimal syukaRuikei = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + SELECT_COL_NM_RUIKEI_SYUKA), record.getValue(TranTable.SYUKA.withAliasForColNm() + SELECT_COL_NM_RUIKEI_SYUKA), record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_RUIKEI_SYUKA));
		BigDecimal reinyuRuikei = searchHelper.convertBigDecimal(record.getValue(TranTable.REINYU.withAliasForColNm() + SELECT_COL_NM_RUIKEI_REINYU));
		record.setRuikeiColInfo(SELECT_COL_NM_RUIKEI_HONNEN, searchHelper.round(syukaRuikei).toString());
		record.setRuikeiColInfo(SELECT_COL_NM_RUIKEI_ZENNEN, searchHelper.round(reinyuRuikei).toString());
		record.setRuikeiColInfo(SELECT_COL_NM_RUIKEI_TAIHI, searchHelper.calcRoundAndTaihi(syukaRuikei, reinyuRuikei).toString());

		//// 出荷累計
		//BigDecimal syukaRuikei = BigDecimal.ZERO;
		//// 戻入累計
		//BigDecimal reinyuRuikei = BigDecimal.ZERO;
		for (int i = 0; i < dateSize; i++) {
			// 出荷
			String syukaCol = String.format(SELECT_COL_NM_SYUKA, i+1);
			// 戻入
			String reinyuCol = String.format(SELECT_COL_NM_REINYU, i+1);
			// 計算
			BigDecimal syuka = searchHelper.calc(record.getValue(TranTable.JYUCYU.withAliasForColNm() + syukaCol), record.getValue(TranTable.SYUKA.withAliasForColNm() + syukaCol), record.getValue(TranTable.REINYU.withAliasForColNm() + syukaCol));
			BigDecimal reinyu = searchHelper.convertBigDecimal(record.getValue(TranTable.REINYU.withAliasForColNm() + reinyuCol));
			//// 累計へ加算
			//syukaRuikei = syukaRuikei.add(syuka);
			//reinyuRuikei = reinyuRuikei.add(reinyu);
			// 丸めた値をレコードへ保存
			record.setDateColInfo(syukaCol, searchHelper.round(syuka).toString());
			record.setDateColInfo(reinyuCol, searchHelper.round(reinyu).toString());
			// 対比計算
			record.setDateColInfo(String.format(SELECT_COL_NM_TAIHI, i+1), searchHelper.calcRoundAndTaihi(syuka,reinyu).toString());
		}

	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#getSelectColList(kit.jiseki.ESienList.JisekiESienList_SearchForm, int)
	 */
	@Override
	public List<String> getSelectColList(JisekiESienList_SearchForm searchForm, int dateListSize) {
		List<String> colList = new ArrayList<String>();
		// 累計_本年
		colList.add(SELECT_COL_NM_RUIKEI_SYUKA);
		// 累計_本年
		colList.add(SELECT_COL_NM_RUIKEI_REINYU);
		for (int i = 0; i < dateListSize; i++) {
			// 出荷・戻入
			colList.add(String.format(SELECT_COL_NM_SYUKA, i+1));
			colList.add(String.format(SELECT_COL_NM_REINYU, i+1));
		}
		return colList;
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.IJisekiESienList_SearchSelectPtn#getTranSelectSql(kit.jiseki.ESienList.JisekiESienList_SearchForm, kit.jiseki.ESienList.JisekiESienList_SearchHelper.DateInfo, kit.jiseki.ESienList.JisekiESienList_SearchHelper.TranTable, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public String getTranSelectSql(JisekiESienList_SearchForm searchForm, DateInfo dateInfo, TranTable tranTable, String syukeiCol, String keisanDateKijunCol, List<String> bindList) {
		StringBuilder sql = new StringBuilder();

		if(tranTable != TranTable.REINYU) {
			// 出荷の場合
			// 出荷累計
			sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_RUIKEI_SYUKA).append(" \n");
			// 戻入累計
			sql.append("        ,0 AS ").append(SELECT_COL_NM_RUIKEI_REINYU).append(" \n");
		} else {
			// 戻入の場合
			// 出荷累計
			sql.append("        ,0 AS ").append(SELECT_COL_NM_RUIKEI_SYUKA).append(" \n");
			// 戻入累計
			sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(SELECT_COL_NM_RUIKEI_REINYU).append(" \n");
		}
		bindList.add(dateInfo.getRuikeiDateRange().getFrom());
		bindList.add(dateInfo.getRuikeiDateRange().getTo());

		List<HonnenZennenDateRange> dateRangeList = dateInfo.getDateRange();
		int index = 0;
		for (HonnenZennenDateRange dateRange : dateRangeList) {
			index++;
			if(tranTable != TranTable.REINYU) {
				// 出荷の場合
				if(dateRange.hasHonnen()) {
					// 出荷
					sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(String.format(SELECT_COL_NM_SYUKA, index)).append(" \n");
					bindList.add(dateRange.getFrom());
					bindList.add(dateRange.getTo());
				} else {
					sql.append("        ,0 AS ").append(String.format(SELECT_COL_NM_SYUKA, index)).append(" \n");
				}
				sql.append("        ,0 AS ").append(String.format(SELECT_COL_NM_REINYU, index)).append(" \n");
			} else {
				// 戻入の場合
				sql.append("        ,0 AS ").append(String.format(SELECT_COL_NM_SYUKA, index)).append(" \n");
				if(dateRange.hasHonnen()) {
					// 戻入
					sql.append("        ,SUM(CASE WHEN ").append(keisanDateKijunCol).append(" BETWEEN ? AND ? THEN ").append(syukeiCol).append(" ELSE 0 END ) AS ").append(String.format(SELECT_COL_NM_REINYU, index)).append(" \n");
					bindList.add(dateRange.getFrom());
					bindList.add(dateRange.getTo());
				} else {
					sql.append("        ,0 AS ").append(String.format(SELECT_COL_NM_REINYU, index)).append(" \n");
				}
			}
		}
		return sql.toString();
	}

}
