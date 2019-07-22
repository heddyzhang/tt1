/*
 * contents.erms.detail.ErmsResourceDetaill.java 2004/11/18
 *
 *  更新履歴
 *  Ver.     日付          担当者         変更内容
 *  1.0      2004/11/18    指村 敦子      新規作成
 *           2005/07/05    指村 敦子      リソースにデフォルトの会社を持たせるように修正
 *           2015/05/12    鈴木 麻里　　　項目追加
 *           2015/12/02    Minako Uenaka　　　JOB NAMEを追加
 */
package contents.erms.detail;

import java.net.URL;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import contents.ContentsBase;

/**
 * ERMSリソース詳細画面生成クラス
 *
 * @author Atsuko Sashimura
 */
public class ErmsResourceDetail extends ContentsBase
{
	// リソースID
	private String resourceId = null;

	/**
	 * クエリー取得メソッド.
	 *
	 * @param request サーブレットリクエスト
	 * @return クエリ文字列のHashtable
	 */
	protected Hashtable getQuery( HttpServletRequest request )
	{
		// 対象リソースIDを取得
		resourceId = getParameter(request, "c_resource_id", "");

		Hashtable querys = new Hashtable();
		// ユーザ情報取得
		querys.put("MANAGE_AUTH", getQueryManage(request));
		// 基本的なリソース情報を取得するSQL
		querys.put("RESOURCE", getSelectSqlResource());

		// 過去のアサインの会社に関する情報を取得するSQL
		querys.put("ASSIGN_COMPANY", getSelectSqlAssignCompany(request));
		// 最新アサインの情報を取得するSQL
		querys.put("LATEST_ASSIGN", getSelectSqlLatestAssign(request));
		// 平均点を取得するSQL
		querys.put("RESOURCE_POINT", getSelectSqlResourcePoint(request));

		return querys ;
	}

	/**
	 * MANAGE_AUTH クエリ文字列作成メソッド.
	 *
	 * @param request サーブレットリクエスト
	 *  @return クエリ文字列
	 */
	private String getQueryManage(HttpServletRequest request)
	{
		String sql = "SELECT DECODE(COUNT(ROWID), 0, 'false', 'true') manage_auth"
			+ "\n FROM const_list"
			+ "\n WHERE name = 'ERMS_MANAGE'"
			+ "\n AND end_date IS NULL"
			+ "\n AND value = '" + getCookie("USERID") + "'"
			;

		return sql;
	}

	/**
	 * 基本的なリソース情報を取得するSQL.
	 *
	 */
	private String getSelectSqlResource()  {

		// 基本的なリソース情報を取得するSQL
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT");
		sql.append("\n r.resource_id");
		sql.append("\n, r.PERSON_ID");
		sql.append("\n, r.EMP_ID");
		sql.append("\n, r.resource_name_sei");
		sql.append("\n, r.resource_name_mei");
		sql.append("\n, r.resource_name_sei_kana");
		sql.append("\n, r.resource_name_mei_kana");
		sql.append("\n, r.resource_name_sei_e");
		sql.append("\n, r.resource_name_mei_e");
		sql.append("\n, r.sex");
		sql.append("\n, r.resource_memo");
		sql.append("\n, r.company_id");
		sql.append("\n, c.company_name");
		sql.append("\n, sll.skill_level");
		sql.append("\n, r.STATUS");
		sql.append("\n, TO_CHAR(r.HR_START_DATE,'YYYY/MM/DD') HR_START_DATE");
		sql.append("\n, TO_CHAR(r.HR_END_DATE,'YYYY/MM/DD') HR_END_DATE");
		sql.append("\n, r.GUID");
		sql.append("\n, el.GUID short_guid");
		sql.append("\n, TO_CHAR(r.NDA_GET_DATE,'YYYY/MM/DD') NDA_GET_DATE");
		sql.append("\n, TO_CHAR(r.RE_COMMISION_GET_DATE,'YYYY/MM/DD') RE_COMMISION_GET_DATE");
		sql.append("\n, TO_CHAR(r.BILL_CARD_START_DATE,'YYYY/MM/DD') BILL_CARD_START_DATE");
		sql.append("\n, bc.CARD_NAME BILL_CARD_TYPE");
		sql.append("\n, TO_CHAR(r.EMP_ID_CARD_START_DATE,'YYYY/MM/DD') EMP_ID_CARD_START_DATE");
		sql.append("\n, TO_CHAR(r.EMP_ID_CARD_EXPIRATION_DATE,'YYYY/MM/DD') EMP_ID_CARD_EXPIRATION_DATE");
		sql.append("\n, TO_CHAR(r.PC_RENT_START_DATE,'YYYY/MM/DD') PC_RENT_START_DATE");
		sql.append("\n, TO_CHAR(r.PC_SCHEDULED_RETURN_DATE,'YYYY/MM/DD') PC_SCHEDULED_RETURN_DATE");
		sql.append("\n, TO_CHAR(r.CARD_RETURN_DATE,'YYYY/MM/DD') CARD_RETURN_DATE");
		sql.append("\n, TO_CHAR(r.PC_RETURN_DATE,'YYYY/MM/DD') PC_RETURN_DATE");
		sql.append("\n, TO_CHAR(greatest(r.update_date, NVL(r.skill_update_date, TO_DATE('19000101','YYYYMMDD'))) ,'YYYY/MM/DD') update_date");
		sql.append("\n, TO_CHAR(r.register_start_date,'YYYY/MM/DD') register_start_date");
		sql.append("\n, e.emp_name update_person");
		sql.append("\n, r.JOB_NAME JOB_NAME");
		sql.append("\n FROM ");
		sql.append("\n erms_resource r, erms_skill_level_list sll, emp_list e");
		sql.append(", (SELECT GUID, PERSON_NUMBER, EMP_ID FROM EMP_LIST WHERE CATEGORY_ID = 57 AND DEL_FLG = 0) EL");
		sql.append("\n, erms_company c, ERMS_BILL_CARD_TYPE bc");
		sql.append("\n WHERE ");
		sql.append("\n r.skill_level_id = sll.skill_level_id(+)");
		sql.append("\n AND r.update_person = e.emp_id(+)");
		sql.append("\n AND r.del_flg(+) != 1");
		sql.append("\n AND r.resource_id = '");
		sql.append(resourceId);
		sql.append("'");
		sql.append("\n AND r.BILL_CARD_TYPE = bc.CARD_ID(+)");
		sql.append("\n AND r.PERSON_ID = NVL(EL.PERSON_NUMBER(+), EL.EMP_ID(+))");
		sql.append("\n AND r.company_id = c.company_id(+)");

		return sql.toString();
	}

	/**
	 * 過去のアサインの会社に関する情報を取得するSQL.
	 *
	 */
	private String getSelectSqlAssignCompany(HttpServletRequest request)  {

		// 過去のアサインの会社に関する情報を取得するSQL
		String sql =
			"SELECT "
			+ "\n "
			+	"a.resource_id"
			+ "\n, c.company_name"
			+ "\n, bc.company_name belong_company_name"
			+ "\n, TO_CHAR(a.start_date, 'YYYY/MM/DD') start_date"
			+ "\n, TO_CHAR(a.end_date, 'YYYY/MM/DD') end_date"
			+ "\n, req_no"
			+ "\n FROM "
			+ "\n erms_assign a"
			+ "\n, erms_company c"
			+ "\n, erms_company bc"
			+ "\n WHERE "
			+ "\n  a.company_id = c.company_id"
			+ "\n AND a.belong_company_id = bc.company_id(+)"
			+ "\n AND a.del_flg != 1"
			+ "\n AND c.del_flg(+) != 1"
			+ "\n AND bc.del_flg(+) != 1"
			+ "\n AND a.resource_id = '" + resourceId + "' "
			+ "\n ORDER BY a.start_date, req_no"
			;

		return sql;
	}

	/**
	 * 最新アサインの情報を取得するSQL.
	 *
	 */
	private String getSelectSqlLatestAssign(HttpServletRequest request)  {

		// 最新アサインの情報を取得するSQL
		String sql =
			"SELECT "
			+ "\n "
			+	"total_price"
			+ "\n, unit_price_m"
			+ "\n, unit_price_h"
			+ "\n FROM "
			+ "\n erms_assign"
			+ "\n WHERE "
			+ "\n assign_id = (SELECT MAX(assign_id) FROM erms_assign"
			+ "		 \n WHERE"
			+ "		 \n start_date = (SELECT MAX(start_date) FROM erms_assign "
			+ "		 		\n WHERE resource_id = '" + resourceId + "'"
			+ "      		\n AND del_flg != 1  )"
			+ "             \n AND  resource_id = '" + resourceId + "'"
			+ "		 \n AND  del_flg != 1 )"
			;

		return sql;
	}

	/**
	 * リソースポイントの平均を取得するSQL.
	 *
	 */
	private String getSelectSqlResourcePoint(HttpServletRequest request)  {

		// リソースポイントの平均を取得するSQLROUND(AVG(resource_point), 1)
		String sql =
			"SELECT "
			+ "\n "
			+	"ROUND(AVG(a.resource_point), 1) avg_point"
			+ "\n FROM "
			+ "\n erms_assign a, erms_resource r"
			+ "\n WHERE "
			+ "\n  r.resource_id = a.resource_id(+)"
			+ "\n AND a.del_flg(+) != 1"
			+ "\n AND r.del_flg(+) != 1"
			+ "\n AND a.resource_id = '" + resourceId + "'"
			;

		return sql;
	}

	/**
	 * スタイルシートURL取得メソッド.
	 *
	 *
	 */
	protected URL getXSLURL( HttpServletRequest request )
	throws Exception
	{
		return getClass().getResource( "/xsl/erms/detail/ErmsResourceDetail.xsl" ) ;
	}

	/**
	 * メニューコード取得メソッド.
	 *
	 * @return メニューコード
	 */
	protected String getMenuCode( HttpServletRequest request )
	{
		//ERMS
		return "70100";
	}

	/**
	 * 画面独自に許されるユーザーがauth_code以外にある場合
	 *
	 * @param request サーブレットリクエスト
	 * @return '権限名' :const_listのnameが'権限名'に対応するvalue(emp_id)の人も許す
	 * 単価等特別な情報が入っているためauth_codeだけでは面倒見切れないのでERMSでは
	 * ここだけで権限を管理する
	 */
	protected String getMenuNameForGuests( HttpServletRequest request )
	{
		return "ERMS_USER";
	}

	/**
	 * DOM取得後処理メソッド.
	 *
	 *
	 */
	protected void actionAfterGetDOM( String key , Element rowset , Document x_doc , HttpServletRequest request)
	throws Exception
	{
		if ( key.equals("header") )
		{
			// HELP HTMLに使用するコンテキストパス取得
			appendTextElement( x_doc, rowset, "context_path", request.getContextPath() );
		}
	}
}
