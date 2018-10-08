package kit.jiseki.ESienList;

import java.util.List;

import kit.jiseki.ESienList.JisekiESienList_SearchHelper.ColInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.DateInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.HonnenZennenDateRange;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.TranTable;

/**
 * 検索のセレクトパターンinterface
 * @author t_kimura
 *
 */
public interface IJisekiESienList_SearchSelectPtn {

	/**
	 * ヘッダ1行フラグ取得
	 * @return ヘッダ1行フラグ
	 */
	boolean isHeader1Line();

	/**
	 * 累計1行フラグ取得
	 * @return 累計1行フラグ
	 */
	boolean isRuikeiHeader1Line();

	/**
	 * 累計ヘッダ列情報取得
	 * @param searchHelper 検索Helper
	 * @param searchForm 検索Form
	 * @return 累計ヘッダ列情報
	 */
	ColInfo getRuikeiHeader(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm);

	/**
	 * 日付ヘッダ列情報取得
	 * @param search 検索Helper
	 * @param searchForm 検索Form
	 * @param dateList 日付リスト
	 * @return 日付ヘッダ列情報
	 */
	List<ColInfo> getDateHeaderList(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, List<HonnenZennenDateRange> dateList);

	/**
	 * レコード設定処理
	 * @param searchHelper 検索Helper
	 * @param searchForm 検索Form
	 * @param record レコード
	 */
	void setupRecord(JisekiESienList_SearchHelper searchHelper, JisekiESienList_SearchForm searchForm, JisekiESienListRecord record);

	/**
	 * セレクト列名取得
	 * @param searchForm 検索Form
	 * @param dateListSize 日付情報のサイズ
	 * @return セレクト列名リスト
	 */
	List<String> getSelectColList(JisekiESienList_SearchForm searchForm, int dateListSize);

	/**
	 * トランザクション検索sqlのSelect句取得
	 * @param searchForm 検索Form
	 * @param dateInfo 日付情報
	 * @param tranTable トランザクションTable
	 * @param syukeiCol 集計対象列
	 * @param keisanDateKijunCol 計算日付基準列
	 * @param bindList バインドリスト
	 * @return sql
	 */
	String getTranSelectSql(JisekiESienList_SearchForm searchForm, DateInfo dateInfo, TranTable tranTable, String syukeiCol, String keisanDateKijunCol, List<String> bindList);
}
