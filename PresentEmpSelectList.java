/*
 * contents.common.PresentEmpSelectList.java 2005/06/28
 *
 *  更新履歴
 *  Ver.     日付          担当者         変更内容
 *  1.0      2005/06/28    指村 敦子      新規作成
 */
package contents.common;

import java.net.URL;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import contents.ContentsBase;

/**
 * 従業員選択、検索結果画面生成クラス。 現在の従業員を検索する
 *
 * @author Atsuko Sashimura
 */
public class PresentEmpSelectList extends ContentsBase {

	/**
	 * クエリー取得メソッド.
	 *
	 * @param request
	 *            サーブレットリクエスト
	 * @return クエリ文字列のHashtable
	 */
	protected Hashtable getQuery(HttpServletRequest request) {
		Hashtable querys = new Hashtable();
		// 従業員検索SQL取得
		querys.put("EMP", getSelectSqlEmp(request));
		return querys;
	}

	/**
	 * リソース検索用SQL取得メソッド.
	 *
	 */
	private String getSelectSqlEmp(HttpServletRequest request) {
		// 一覧
		String selectSql = "SELECT " + " emp.emp_id emp_id" + ",emp.emp_name emp_name" + ",org.org_id org_id"
				+ ",org.org_name org_name" + ",org1.org_name level1" + ",org3.org_name level3" + ",org4.org_name level4"
				+ ",org5.org_name level5";
		String fromSql = " FROM"
				+ " emp_list emp"
				+ ",org_list org"
				+ ",org_list org1"
				+ ",org_list org3"
				+ ",org_list org4"
				+ ",org_list org5"
				+ ",CONST_LIST CNS"
				+ ",COST_CENTER_LIST COS";

		String whereSql = " WHERE" + " emp.org_id = org.org_id(+)" + " and emp.del_flg != 1"
				+ " and org.level1 = org1.org_id(+)"
				+ " and org.level3 = org3.org_id(+)"
				+ " and org.level4 = org4.org_id(+)"
				+ " and org.level5 = org5.org_id(+)"
				+ " and org.org_end_date is null"
				+ " and org1.org_end_date is null"
				+ " and org3.org_end_date is null"
				+ " and org4.org_end_date is null"
				+ " and org5.org_end_date is null"
				+ " and emp.CATEGORY_ID = 56 "
				+ "and CNS.NAME = 'CONSUL_ORG_ID' "
				+ "and (emp.COST_CENTER_ID = COS.ORG_ID or (emp.COST_CENTER_ID = 'CC00506265' and CNS.VALUE = COS.ORG_ID))"
				+ "AND COS.ORG_END_DATE is null "
				+ "AND COS.LEVEL2 = CNS.VALUE ";

		// 名前指定
		String empName = setFormatLikeSqlString(getParameter(request, "c_emp_name", ""));
		if (!empName.equals("")) {
			// 全部小文字で統一する
			empName = empName.toLowerCase();
			// 全角空白は半角空白に置換する
			empName = empName.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String empNameArr[] = empName.split("\\s");
			for (int i = 0; i < empNameArr.length; i++) {
				whereSql = whereSql
						+ " AND (common_utl.translate_to_half(LOWER(REPLACE(emp.emp_name,' '))) LIKE common_utl.translate_to_half('%"
						+ empNameArr[i] + "%') " + SQL_ESC_STR
						+ " OR common_utl.translate_to_half(LOWER(REPLACE(emp.emp_name_kana,' '))) LIKE common_utl.translate_to_half('%"
						+ empNameArr[i] + "%') " + SQL_ESC_STR + ")";
			}
		}

		// 組織指定
		String orgName = setFormatLikeSqlString(getParameter(request, "c_org_name", ""));
		if (!orgName.equals("")) {
			// 全部小文字で統一する
			orgName = orgName.toLowerCase();
			// 全角空白は半角空白に置換する
			orgName = orgName.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String orgNameArr[] = orgName.split("\\s");
			for (int i = 0; i < orgNameArr.length; i++) {
				whereSql = whereSql
						+ " AND (common_utl.translate_to_half(LOWER(org.org_name)) LIKE common_utl.translate_to_half('%"
						+ orgNameArr[i] + "%') "
						+ " OR common_utl.translate_to_half(LOWER(org1.org_name)) LIKE common_utl.translate_to_half('%"
						+ orgNameArr[i] + "%') "
						+ " OR common_utl.translate_to_half(LOWER(org3.org_name)) LIKE common_utl.translate_to_half('%"
						+ orgNameArr[i] + "%') "
						+ " OR common_utl.translate_to_half(LOWER(org4.org_name)) LIKE common_utl.translate_to_half('%"
						+ orgNameArr[i] + "%') "
						+ " OR common_utl.translate_to_half(LOWER(org5.org_name)) LIKE common_utl.translate_to_half('%"
						+ orgNameArr[i] + "%'))";
			}
		}

		// コンサルフラグ条件
		String consul_flg = getParameter(request, "consul_flg", "");
		if (consul_flg.equals("yes")) {
			fromSql = fromSql + ",const_list CONST" + ",const_list CONST2";

			whereSql = whereSql + " and CONST.name = 'CONSUL_HR_ORG_ID'" + " and CONST2.name = 'CONSUL_HR_ORG_LEVEL'"
					+ " and DECODE(CONST2.value,'1',ORG.level1,'2',ORG.level2,'3',ORG.level3,'4',ORG.level4,'5',ORG.level5) = CONST.value";
		}

		// 名前順
		whereSql = whereSql + " ORDER BY EMP.emp_name_kana";

		return selectSql + fromSql + whereSql;
	}

	/**
	 * スタイルシートURL取得メソッド.
	 *
	 *
	 */
	protected URL getXSLURL(HttpServletRequest request) throws Exception {
		return getClass().getResource("/xsl/common/PresentEmpSelectList.xsl");
	}

	/**
	 * メニューコード取得メソッド.
	 *
	 * @return メニューコード
	 */
	protected String getMenuCode(HttpServletRequest request) {
		// 社員選択
		return "90110";
	}

	/**
	 * DOM取得後処理メソッド.
	 *
	 *
	 */
	protected void actionAfterGetDOM(String key, Element rowset, Document x_doc, HttpServletRequest request)
			throws Exception {

	}
}
