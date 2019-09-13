////////////////////////////////////////////////////////////////////////////////
//
//
// create 2002-06-17
//
////////////////////////////////////////////////////////////////////////////////
// 更新履歴
//  Ver.     日付          担当者          変更内容
//  1.0      2002/06/17    竹村正義        新規作成
// C00012    2006/02/16    指村敦子        表示列選択項目変更のため、Listのカラムも変更
// C00016   2006/06/08    Atsuko Sashimura   プロジェクト組織の表示をとりあえずコード表示に
// C00057    2008/01/21    Atsuko.Sashimura   Siebel移行
////////////////////////////////////////////////////////////////////////////////
package contents;

import java.net.URL ;
import java.util.Hashtable ;

import javax.servlet.http.HttpServletRequest ;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * プロジェクト一覧
 *
 *
 *
 */
public class Project_List extends ContentsBase
{

	/**
	 * クエリー取得メソッド.
	 *
	 *
	 */
	protected Hashtable getQuery( HttpServletRequest request )
	{
		Hashtable querys = new Hashtable();

		querys.put("PROJECT",getQuery_Project(request));
		querys.put("PROJECT_HEADER",getQuery_ProjHeader(request));
		querys.put("RMS_URL",getQuery_RmsUrl(request));
		querys.put("ZOO_URL",getQuery_ZooUrl(request));
		// SUPER_USERかどうか
		querys.put("SUPER_USER",getQuerySuperUser());

		return querys ;
	}


	/**
	 * プロジェクト SQL
	 *
	 *
	 */
	private String getQuery_Project( HttpServletRequest request )
	{
		//条件項目
		String c_proj_code = request.getParameter("c_proj_code");
		String c_gsi_proj_code = request.getParameter("c_gsi_proj_code");
		String c_proj_name = request.getParameter("c_proj_name");
		String c_end_user_name = request.getParameter("c_end_user_name");
		String c_client_name = request.getParameter("c_client_name");
		String c_org_id = request.getParameter("c_org_id");
		String c_contract_id = request.getParameter("c_contract_id");
		String c_oppo_number = request.getParameter("c_oppo_number");
		String c_proj_owner_name = request.getParameter("c_proj_owner_name");
		String c_proj_mgr_name = request.getParameter("c_proj_mgr_name");
		String c_book_status = request.getParameter("c_book_status");
		String c_work_status = request.getParameter("c_work_status");
		String c_classification = request.getParameter("c_classification");
		String c_fy = request.getParameter("c_fy");


		//表示項目
		String o_proj_code = request.getParameter("o_proj_code");
		String o_gsi_proj_code = request.getParameter("o_gsi_proj_code");
		String o_oppo_number = request.getParameter("o_oppo_number");
		String o_proj_name = request.getParameter("o_proj_name");
		String o_end_user_name = request.getParameter("o_end_user_name");
		String o_client_name = request.getParameter("o_client_name");
		String o_org_name = request.getParameter("o_org_name");
		String o_cont_amount = request.getParameter("o_cont_amount");
		String o_contract_type = request.getParameter("o_contract_type");
		String o_proj_owner_name = request.getParameter("o_proj_owner_name");
		String o_proj_mgr_name = request.getParameter("o_proj_mgr_name");
		String o_book_status = request.getParameter("o_book_status");
		String o_rev_status = request.getParameter("o_rev_status");
		String o_work_status = request.getParameter("o_work_status");
		String o_win_prob = request.getParameter("o_win_prob");
		String o_sales = request.getParameter("o_sales");
		String o_proj_start = request.getParameter("o_proj_start");
		String o_proj_end = request.getParameter("o_proj_end");
		String o_contract_date = request.getParameter("o_contract_date");
		String o_cons_start = request.getParameter("o_cons_start");
		String o_cons_end = request.getParameter("o_cons_end");
		String o_classification = request.getParameter("o_classification");
		String o_classification_2 = request.getParameter("o_classification_2");
		String o_classification_3 = request.getParameter("o_classification_3");
		String o_classification_4 = request.getParameter("o_classification_4");

		//プロジェクト用
		StringBuffer select = new StringBuffer("SELECT NULL");
		StringBuffer from = new StringBuffer("\n FROM PROJECT_LIST_V PJT ");
		StringBuffer where = new StringBuffer("\n WHERE 1=1 ");
		StringBuffer groupBy = new StringBuffer("\n GROUP BY ");
		StringBuffer orderby = new StringBuffer("\n ORDER BY ");


		//リンク用にPROJ_CODEを必ず取得しておく
		select.append("\n,nvl(PJT.proj_code,' ') PROJ_CODE_FOR_LINK");
		groupBy.append("\n PJT.PROJ_CODE ");

		//コピーボタンを表示するか否か用にGSI_PROJ_CODEを必ず取得しておく
		select.append("\n,(case when gsi_proj_code is not null and oppo_number is not null then 'true' else 'false' end) GSI_PROJ_CODE_FOR_COPY ");
		groupBy.append(", PJT.GSI_PROJ_CODE, PJT.OPPO_NUMBER ");

		//proj_code
		if(o_proj_code != null){
			select.append("\n,PJT.proj_code PROJ_CODE");
		}
		if(c_proj_code != null && !c_proj_code.equals("")){
			// 全角空白は半角空白に置換する
			c_proj_code = c_proj_code.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String projCodeArr[] = c_proj_code.split("\\s");
			for(int i=0; i<projCodeArr.length; i++){
				where.append("\n AND LOWER(PJT.PROJ_CODE) LIKE '%' || LOWER('" + projCodeArr[i] + "') || '%' " + SQL_ESC_STR);
			}
		}
		//gsi_proj_code
		if(o_gsi_proj_code != null){
			select.append("\n,nvl(PJT.gsi_proj_code,' ') GSI_PROJ_CODE");
		}
		if(c_gsi_proj_code != null && !c_gsi_proj_code.equals("")){
			// 全角空白は半角空白に置換する
			c_gsi_proj_code = c_gsi_proj_code.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String gsiProjCodeArr[] = c_gsi_proj_code.split("\\s");
			for(int i=0; i<gsiProjCodeArr.length; i++){
				where.append("\n AND LOWER(PJT.GSI_PROJ_CODE) LIKE '%' || LOWER('" + gsiProjCodeArr[i] + "') || '%' " + SQL_ESC_STR);
			}
		}

		//Opportunity Number
		if(o_oppo_number != null){
			select.append("\n,nvl(PJT.oppo_number,' ') OPPO_NUMBER");
		}
		// オポチュニティ番号
		if((c_oppo_number != null) && (!c_oppo_number.equals(""))){
			// 全角空白は半角空白に置換する
			c_oppo_number = c_oppo_number.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String oppoNumberArr[] = c_oppo_number.split("\\s");
			for(int i=0; i<oppoNumberArr.length; i++){
				where.append("\n AND LOWER(PJT.OPPO_NUMBER) LIKE '%' || LOWER('" + oppoNumberArr[i] + "') || '%' " + SQL_ESC_STR);
			}
		}

		//プロジェクト名
		if(o_proj_name != null){
			select.append("\n,nvl(PJT.proj_name,' ') PROJ_NAME");
			groupBy.append(", PJT.PROJ_NAME");
		}
		if(c_proj_name != null && !c_proj_name.equals("")){
			// 全角空白は半角空白に置換する
			c_proj_name = c_proj_name.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String pjNameArr[] = c_proj_name.split("\\s");
			for(int i=0; i<pjNameArr.length; i++){
				where.append("\n AND LOWER(PJT.PROJ_NAME) LIKE '%' || LOWER('" + pjNameArr[i] + "') || '%' " + SQL_ESC_STR);
			}
		}

		//エンドユーザ
		if(o_end_user_name != null){
			select.append("\n,nvl(PJT.end_user_name,' ') END_USER_NAME");
			groupBy.append(",PJT.END_USER_NAME");
		}
		if(c_end_user_name != null && !c_end_user_name.equals("")){
			// 全角空白は半角空白に置換する
			c_end_user_name = c_end_user_name.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String euNameArr[] = c_end_user_name.split("\\s");
			for(int i=0; i<euNameArr.length; i++){
				where.append("\n AND LOWER(PJT.END_USER_NAME) LIKE '%' || LOWER('" + euNameArr[i] + "') || '%' " + SQL_ESC_STR);
			}
		}

		//契約先社名
		if(o_client_name != null){
			select.append("\n,nvl(PJT.client_name,' ') CLIENT_NAME");
			groupBy.append(", PJT.CLIENT_NAME");
		}
		if(c_client_name != null && !c_client_name.equals("")){
			// 全角空白は半角空白に置換する
			c_client_name = c_client_name.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String clientNameArr[] = c_client_name.split("\\s");
			for(int i=0; i<clientNameArr.length; i++){
				where.append("\n AND LOWER(PJT.CLIENT_NAME) LIKE '%' || LOWER('" + clientNameArr[i] + "') || '%' " + SQL_ESC_STR);
			}
		}

		//組織名
		if(o_org_name != null){
			select.append("\n, PROD.ORG_NAME_SHORT ORG_NAME");
			groupBy.append(", PROD.ORG_NAME_SHORT");

		}
		// 指定されていなくても結合する
		from.append("\n,(select PROG_SUB.* from (select PROJ_CODE,MAX(START_DATE) START_DATE from PROJ_ORG_LIST group by PROJ_CODE) PROG_MAX,PROJ_ORG_LIST PROG_SUB where PROG_SUB.START_DATE = PROG_MAX.START_DATE AND PROG_SUB.PROJ_CODE = PROG_MAX.PROJ_CODE) PORG ");
		from.append("\n, PRODUCT_ORG_LIST PROD");
		where.append("\n AND PJT.PROJ_CODE = PORG.PROJ_CODE \n AND PORG.PROD_ORG_ID = PROD.ORG_ID");

		int fy = Integer.parseInt(request.getParameter("c_fy"));

		if((c_org_id != null) && (!c_org_id.equals("")))
		{
			from.append("\n,cost_center_list CC2");
			where.append("\n and PORG.CC_ORG_ID = CC2.org_id(+)");
			where.append("\n and DECODE((SELECT distinct org_level FROM cost_center_list where org_id = '" + c_org_id + "'" + getOrgPeriod(fy, ""));
			where.append("\n ),1,CC2.level1,2,CC2.level2,3,CC2.level3,4,CC2.level4,5,CC2.level5) = '" + c_org_id + "'");
			where.append("\n and CC2.org_start_date = (select max(org_start_date) from cost_center_list where org_id = CC2.org_id");
			where.append(getOrgPeriod2(fy, ""));
			where.append("\n )");
		}

		//契約金額
		if(o_cont_amount != null){
			select.append("\n,nvl(PJT.cont_amount,-1) CONTRACT_AMOUNT");
			groupBy.append(",PJT.CONT_AMOUNT");
			select.append(",NVL(CONTORG.CONT_AMOUNT,-1) OWNCONTRACT_AMOUNT");
			from.append("\n,(SELECT P.PROJ_CODE, P.CONT_AMOUNT, PO.CC_ORG_ID, PO.PROD_ORG_ID "
					+ "\n FROM PROJECT_LIST_V P, PROJ_ORG_LIST PO "
					+ "\n WHERE P.PROJ_CODE = PO.PROJ_CODE "
					+ "\n AND   P.CONTRACT_DATE BETWEEN PO.START_DATE AND NVL(END_DATE,TO_DATE('2999/12/31','YYYY/MM/DD')) "
					+ ") CONTORG"
					);
			where.append("\n AND PORG.PROJ_CODE = CONTORG.PROJ_CODE(+) "
				+ "\n AND PORG.CC_ORG_ID = CONTORG.CC_ORG_ID(+) "
				+ "\n AND PORG.PROD_ORG_ID = CONTORG.PROD_ORG_ID(+) "
				);
			groupBy.append(", CONTORG.CONT_AMOUNT");
		}

		//契約形態
		if(o_contract_type != null){
			select.append("\n,nvl(CONTRACT.contract_name,' ') CONTRACT_TYPE");
			from.append("\n,contract_list CONTRACT");
			where.append("\n and PJT.contract_id = CONTRACT.contract_id(+)");
			groupBy.append(",CONTRACT.CONTRACT_NAME");
		}
		if((c_contract_id != null) && (!c_contract_id.equals(""))){
			where.append("\n and PJT.contract_id = " + c_contract_id);
		}

		//PO
		if(o_proj_owner_name != null){
			select.append("\n,nvl(PO.emp_name,' ') PO_NAME");
			from.append("\n,emp_list PO");
			where.append("\n and PJT.proj_owner_id = PO.emp_id(+)");
			groupBy.append(",PO.EMP_NAME");
		}
		if((c_proj_owner_name != null) && (!c_proj_owner_name.equals(""))){
			from.append("\n,emp_list PO2");
			where.append("\n and PJT.proj_owner_id = PO2.emp_id(+)");

			// 全角空白は半角空白に置換する
			c_proj_owner_name = c_proj_owner_name.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String poNameArr[] = c_proj_owner_name.split("\\s");
			for(int i=0; i<poNameArr.length; i++){
				where.append("\n AND (PJT.PROJ_OWNER_ID IN (SELECT EMP_ID FROM EMP_LIST INE WHERE ");
				where.append("\n  (LOWER(PO2.EMP_NAME) LIKE '%' || LOWER('" + poNameArr[i] + "') || '%' " + SQL_ESC_STR);
				where.append("\n OR LOWER(PO2.EMP_NAME_KANA) LIKE '%' || LOWER('" + poNameArr[i] + "') || '%' " + SQL_ESC_STR + ")))" );
			}
		}

		//PM
		if(o_proj_mgr_name != null){
			select.append("\n,nvl(PM.emp_name,' ') PM_NAME");
			from.append("\n,emp_list PM");
			where.append("\n and PJT.proj_mgr_id = PM.emp_id(+)");
			groupBy.append(",PM.EMP_NAME");
		}
		if((c_proj_mgr_name != null) && (!c_proj_mgr_name.equals(""))){
			from.append("\n,emp_list PM2");
			where.append("\n and PJT.proj_mgr_id = PM2.emp_id(+)");

			// 全角空白は半角空白に置換する
			c_proj_mgr_name = c_proj_mgr_name.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String pmNameArr[] = c_proj_mgr_name.split("\\s");
			for(int i=0; i<pmNameArr.length; i++){
				where.append("\n AND (PJT.PROJ_MGR_ID IN (SELECT EMP_ID FROM EMP_LIST INE WHERE ");
				where.append("\n  (LOWER(PM2.EMP_NAME) LIKE '%' || LOWER('" + pmNameArr[i] + "') || '%' " + SQL_ESC_STR);
				where.append("\n OR LOWER(PM2.EMP_NAME_KANA) LIKE '%' || LOWER('" + pmNameArr[i] + "') || '%' " + SQL_ESC_STR + ")))" );
			}
		}

		//フォアキャストステータス(Book_status)
		if(o_book_status != null){
			select.append("\n,nvl(BST.status_name,' ') BOOK_STATUS");
			from.append("\n,fcst_status_list BST");
			where.append("\n and PJT.book_status = BST.status_id(+)");
			groupBy.append(",BST.STATUS_NAME");
		}
		//フォアキャストステータス(Rev_status)
		if(o_rev_status != null){
			select.append("\n,nvl(RST.status_name,' ') REV_STATUS");
			from.append("\n,fcst_status_list RST");
			where.append("\n and PJT.rev_status = RST.status_id(+)");
			groupBy.append(",RST.STATUS_NAME");
		}
		if((c_book_status != null) && (!c_book_status.equals(""))){
			where.append("\n and PJT.book_status = " + c_book_status);
		}

		//ワークステータス
		if(o_work_status != null){
			select.append("\n,nvl(WORK_STATUS.s_code,' ') WORK_STATUS");
			from.append("\n,work_status_list WORK_STATUS");
			where.append("\n and PJT.work_status = WORK_STATUS.status_id(+)");
			groupBy.append(",WORK_STATUS.S_CODE");
		}
		if((c_work_status != null) && (!c_work_status.equals(""))){
			where.append("\n and PJT.work_status = " + c_work_status);
		}

		//契約終結予定日
		if(o_contract_date != null){
			select.append("\n,nvl(to_char(PJT.contract_date,'yyyy/mm/dd'),' ') CONTRACT_DATE");
			groupBy.append(",PJT.CONTRACT_DATE");
		}

		//winProb
		if(o_win_prob != null){
			select.append("\n,nvl(PJT.win_prob,-1) WIN_PROB");
			groupBy.append(",PJT.WIN_PROB");
		}
		//PrimarySales
		if(o_sales != null){
			select.append("\n,nvl(PJT.consul_sales,' ') SALES");
			groupBy.append(",PJT.CONSUL_SALES");
		}
		//proj_start
		if(o_proj_start != null){
			select.append("\n,nvl(to_char(PJT.proj_start,'yyyy/mm/dd'),' ') PROJ_START");
			groupBy.append(",PJT.PROJ_START");
		}
		//proj_end
		if(o_proj_end != null){
			select.append("\n,nvl(to_char(PJT.proj_end,'yyyy/mm/dd'),' ') PROJ_END");
			groupBy.append(",PJT.PROJ_END");
		}

		//コンサルティング期間開始
		if(o_cons_start != null){
			select.append("\n,nvl(to_char(PJT.cons_start,'yyyy/mm/dd'),' ') CONS_START");
			groupBy.append(",PJT.CONS_START");
		}

		//コンサルティング期間終了
		if(o_cons_end != null){
			select.append("\n,nvl(to_char(PJT.cons_end,'yyyy/mm/dd'),' ') CONS_END");
			groupBy.append(",PJT.CONS_END");
		}

		//Classification_2
		if(o_classification_2 != null){
			select.append("\n,nvl(PJT.SA_INFO,' ') SA_INFO");
			groupBy.append(",PJT.SA_INFO");
		}

		//Classification_3
		if(o_classification_3 != null){
			select.append("\n,nvl(PJT.PROJECT_TOTAL, -1) PROJECT_TOTAL");
			groupBy.append(",PJT.PROJECT_TOTAL");
		}

		//Classification
		if(o_classification != null){
			select.append("\n,nvl(CLS.classification,' ') CLASSIFICATION");
			groupBy.append(",CLS.CLASSIFICATION");
		}

		from.append("\n,(select PJT.proj_code PROJ_CODE,CLS_DESC.desc_id, CLS_DESC.description CLASSIFICATION"
			+ " from gbl_class_description CLS_DESC,gbl_class_category CLS_CATEGORY"
			+ ",gbl_system_outline SYS_OUTLINE,project_list_v PJT"
			+ " where SYS_OUTLINE.proj_code(+) = PJT.proj_code"
			+ " and SYS_OUTLINE.category_id = CLS_DESC.category_id"
			+ " and SYS_OUTLINE.desc_id = CLS_DESC.desc_id"
			+ " and SYS_OUTLINE.category_id = CLS_CATEGORY.category_id"
			+ " and CLS_CATEGORY.category = 'CLASSIFICATION') CLS");
		where.append("\n and PJT.proj_code = CLS.proj_code(+)");

		if((c_classification != null) && (!c_classification.equals(""))){
			from.append("\n,gbl_system_outline SYS_OUTLINE2");
			where.append("\n and PJT.proj_code = SYS_OUTLINE2.proj_code(+)");
			where.append("\n and SYS_OUTLINE2.category_id = 8");
			where.append("\n and SYS_OUTLINE2.desc_id = " + c_classification);
		}

		// Classification_4
		if(o_classification_4 != null){
			select.append("\n,nvl(PJT.classification_4,' ') CLASSIFICATION_4");
			groupBy.append(",PJT.CLASSIFICATION_4");
		}

		//ソート
		Hashtable sort = new Hashtable();
		sort.put("組織","PJT.unit_id");
		sort.put("Booking Status","PJT.book_status");
		sort.put("Revenue Status","PJT.rev_status");
		sort.put("Work Status","PJT.work_status");
		sort.put("Temp Number","PJT.proj_code");
		sort.put("Project Number","PJT.gsi_proj_code");
		sort.put("Project Name","PJT.proj_name");
		sort.put("エンドユーザー","PJT.end_user_name");
		sort.put("契約先社名","PJT.client_name");
		sort.put("PO","PJT.proj_owner_id");
		sort.put("PM","PJT.proj_mgr_id");
		sort.put("契約金額","PJT.cont_amount");
		sort.put("契約形態","PJT.contract_id");
		sort.put("契約締結日","PJT.contract_date");
		sort.put("PJ開始日","PJT.proj_start");
		sort.put("PJ終了日","PJT.proj_end");

		String sort1 = (String)sort.get(getParameter(request,"c_sort1"));
		String sort2 = (String)sort.get(getParameter(request,"c_sort2"));
		String sort3 = (String)sort.get(getParameter(request,"c_sort3"));
		groupBy.append("," + sort1 + "," + sort2 + "," + sort3);

		orderby.append(
			  "\n " + sort1
			+ "\n," + sort2
			+ "\n," + sort3
			);


		//SQL作成
		StringBuffer sql = new StringBuffer();
		sql.append(select.toString());
		sql.append('\n');
		sql.append(from.toString());
		sql.append('\n');
		sql.append(where.toString());
		sql.append('\n');
		sql.append(groupBy.toString());
		sql.append(orderby.toString());
		sql.append('\n');


		return sql.toString();
	}


	/**
	 * プロジェクトヘッダ SQL
	 *
	 *
	 */
	private String getQuery_ProjHeader( HttpServletRequest request )
	{
		//表示項目を指定
		String o_proj_code = request.getParameter("o_proj_code");
		String o_gsi_proj_code = request.getParameter("o_gsi_proj_code");
		String o_oppo_number = request.getParameter("o_oppo_number");
		String o_proj_name = request.getParameter("o_proj_name");
		String o_end_user_name = request.getParameter("o_end_user_name");
		String o_client_name = request.getParameter("o_client_name");
		String o_org_name = request.getParameter("o_org_name");
		String o_cont_amount = request.getParameter("o_cont_amount");
		String o_contract_type = request.getParameter("o_contract_type");
		String o_proj_owner_name = request.getParameter("o_proj_owner_name");
		String o_proj_mgr_name = request.getParameter("o_proj_mgr_name");
		String o_book_status = request.getParameter("o_book_status");
		String o_rev_status = request.getParameter("o_rev_status");
		String o_win_prob = request.getParameter("o_win_prob");
		String o_sales = request.getParameter("o_sales");
		String o_proj_start = request.getParameter("o_proj_start");
		String o_proj_end = request.getParameter("o_proj_end");
		String o_work_status = request.getParameter("o_work_status");
		String o_contract_date = request.getParameter("o_contract_date");
		String o_cons_start = request.getParameter("o_cons_start");
		String o_cons_end = request.getParameter("o_cons_end");
		String o_classification = request.getParameter("o_classification");
		String o_classification_2 = request.getParameter("o_classification_2");
		String o_classification_3 = request.getParameter("o_classification_3");
		String o_classification_4 = request.getParameter("o_classification_4");

		//ヘッダ用
		StringBuffer select = new StringBuffer("SELECT NULL");
		StringBuffer from = new StringBuffer(" FROM dual");


		//proj_code
		if(o_proj_code != null){
			select.append("\n,trim('Temp Number') PROJ_CODE");
		}

		//gsi_proj_code
		if(o_gsi_proj_code != null){
			select.append("\n,trim('Project Number') GSI_PROJ_CODE");
		}

		// Opportunity Number
		if(o_oppo_number != null){
			select.append("\n,trim('Opportunity Number') OPPO_NUMBER");
		}

		//プロジェクト名
		if(o_proj_name != null){
			select.append("\n,trim('Project Name') PROJ_NAME");
		}

		//エンドユーザ
		if(o_end_user_name != null){
			select.append("\n,trim('エンド ユーザー') END_USER_NAME");
		}

		//契約先社名
		if(o_client_name != null){
			select.append("\n,trim('契約先') CLIENT_NAME");
		}

		//組織
		if(o_org_name != null){
			select.append("\n,trim('組織') ORG_NAME");
		}

		//契約金額
		if(o_cont_amount != null){
			select.append("\n,trim('契約 金額') CONTRACT_AMOUNT");
			select.append("\n,trim('自組織契約金額') OWNCONTRACT_AMOUNT");
		}

		//契約形態
		if(o_contract_type != null){
			select.append("\n,trim('契約 形態') CONTRACT_TYPE");
		}

		//PO
		if(o_proj_owner_name != null){
			select.append("\n,trim('PO') PO");
		}

		//PM
		if(o_proj_mgr_name != null){
			select.append("\n,trim('PM') PM");
		}

		//フォアキャストステータス(book_status)
		if(o_book_status != null){
			select.append("\n,trim('Book') BOOK_STATUS");
		}

		//フォアキャストステータス(rev_status)
		if(o_rev_status != null){
			select.append("\n,trim('Rev') REV_STATUS");
		}

		//ワークステータス
		if(o_work_status != null){
			select.append("\n,trim('W') WORK_STATUS");
		}

		//win_prob
		if(o_win_prob != null){
			select.append("\n,trim('P') WIN_PROB");
		}
		//PrimarySales
		if(o_sales != null){
			select.append("\n,trim('PrimarySales') SALES");
		}
		//プロジェクト開始日
		if(o_proj_start != null){
			select.append("\n,trim('プロジェクト開始日') PROJ_START");
		}
		//プロジェクト終了日
		if(o_proj_end != null){
			select.append("\n,trim('プロジェクト終了日') PROJ_END");
		}

		//契約終結予定日
		if(o_contract_date != null){
			select.append("\n,trim('契約締結 予定日') CONTRACT_DATE");
		}

		//コンサルティング期間開始
		if(o_cons_start != null){
			select.append("\n,trim('開始日') CONS_START");
		}

		//コンサルティング期間終了
		if(o_cons_end != null){
			select.append("\n,trim('終了日') CONS_END");
		}

		//Classification
		if(o_classification != null){
			select.append("\n,trim('Classification') CLASSIFICATION");
		}

		//Classification_2
		if(o_classification_2 != null){
			select.append("\n,trim('Booking管理組織（Appsのみ）') SA_INFO");
		}

		//Classification_3
		if(o_classification_3 != null){
			select.append("\n,trim('案件総額（Appsのみ）') PROJECT_TOTAL");
		}

		//Classification_4
		if(o_classification_4 != null){
			select.append("\n,trim('Classification_4') CLASSIFICATION_4");
		}

		//SQL作成
		StringBuffer sql = new StringBuffer();
		sql.append(select.toString());
		sql.append('\n');
		sql.append(from.toString());
		sql.append('\n');

		return sql.toString();
	}

	/**
	 * MANAGE_AUTH クエリ文字列作成メソッド.
	 *
	 * @param request サーブレットリクエスト
	 *  @return クエリ文字列
	 */
	private String getQuerySuperUser(){
		String sql = "SELECT DECODE(COUNT(ROWID), 0, 'false', 'true') super_user"
			+ "\n FROM const_list"
			+ "\n WHERE name = 'SUPER_USER'"
			+ "\n AND end_date IS NULL"
			+ "\n AND value = '" + getCookie("USERID") + "'"
		;
		return sql;
	}

	/**
	 * rms url(TempNumberリンク用) SQL
	 *
	 *
	 */
	private String getQuery_RmsUrl( HttpServletRequest request )
	{
		String sql = "SELECT VALUE URL"
				+ " FROM CONST_LIST"
				+ " WHERE NAME = 'RMSURL'"
			;
		return sql;
	}


	/**
	 * zoo url(担当営業リンク用) SQL
	 *
	 *
	 */
	private String getQuery_ZooUrl( HttpServletRequest request )
	{
		String sql = "SELECT VALUE URL"
				+ " FROM CONST_LIST"
				+ " WHERE NAME = 'ZOO_URL'"
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
		return getClass().getResource( "/xsl/Project_List.xsl" ) ;
	}


	/**
	 * メニューコード取得メソッド.
	 *
	 * @return メニューコード
	 */
	protected String getMenuCode( HttpServletRequest request )
	{
		// Project一覧
		return "10202";
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
				//CopyButton
			appendTextElement( x_doc, rowset, "copy_button" , getParameter(request,"o_copy_button","","true"));
		}
	}
}


