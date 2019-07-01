/*
 * contents.project.detail.FcstUpdate.java 2007/2/5
 *
 *  更新履歴
 *  Ver.     日付          担当者         変更内容
 *  1.0      2007/01/31    指村 敦子      新規作成
 *  C00057   2008/01/21    指村 敦子      Siebel対応
 *           2013/06/04    鈴木 麻里      OPPO_TYPEカラム分割対応
 *           2013/06/10    鈴木 麻里      終了した組織に紐づいているプロジェクトが表示されない不具合対応
 *           2013/07/12    鈴木 麻里      案件総額、SAのテキストボックス追加
 *           2013/11/15    鈴木 麻里      サービス分類（Tech）のプルダウン追加
 *           2014/03/18    鈴木 麻里      組織プルダウンに組織が表示されない不具合対応
 *           2014/03/26    鈴木 麻里      Booking集計対象外カラム追加
 *           2014/05/30    鈴木麻里       ORG_LEVEL6に対応
 * 			 2015/05/27        鈴木 麻里      コストセンタのプルダウンにID（下4桁）を表示
 *           2015/07/08    鈴木 麻里      コストセンタ、プロジェクト管理のプルダウンにコンサル期間に存在する最新の組織を表示するように修正
 *           2016/07/06    鈴木 麻里      Booking管理組織の仕様変更
 */
package contents.project.detail;

import java.net.URL;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import contents.ContentsBase;

/**
 * プロジェクト情報 プロジェクト更新画面
 *
 *
 *
 */
public class ProjUpdate extends ContentsBase
{
	private String projCode = "";
	private static String gsi_proj_code_keta = "9"; // gsi_proj_codeがこの桁の場合はPAマスタ、値がないならOSOマスタ、それ以外は枝番

	/**
	 * クエリー取得メソッド.
	 *
	 * @param request サーブレットリクエスト
	 * @return クエリ文字列のHashtable
	 */
	protected Hashtable getQuery( HttpServletRequest request )
	{
		Hashtable querys = new Hashtable();
		// proj_code
		projCode = getParameter(request, "c_proj_code", "");
		// プロジェクト情報取得SQL
		querys.put("PROJECT", getSelectSqlProject(request));
		// ACTIVITY取得SQL
		querys.put("ACTIVITY", getSelectProjActivity(request));
		// システムアウトライン取得SQL
		querys.put("SYSTEM_OUTLINE", getSelectSystemOutline(request));
		// GBL_SYSTEM_OUTLINE取得SQL
		querys.put("GBL_SYSTEM_OUTLINE", getSelectGblSystemOutline(request));
		// GBL_SYSTEM_MENU取得SQL
		querys.put("GBL_SYSTEM_MENU", getSelectGblSystemMenu(request));
		// FORECAST_STATUS_LIST取得SQL
		querys.put("FCST_STATUS_LIST", getSelectFcstStatusList(request));
		// WORK_STATUS_LIST取得SQL
		querys.put("WORK_STATUS_LIST", getSelectWorkStatusList(request));
		// CONTRACT_LIST取得SQL
		querys.put("CONTRACT_LIST", getSelectContractList(request));
		// TYPE_LIST取得SQL
		querys.put("TYPE_LIST", getSelectTypeList(request));
		// SERVICE_CLASSIFICATION取得SQL
		querys.put("SERVICE_CLASSIFICATION", getSelectServiceClassification(request));
		// COST_CENTER_LIST取得SQL
		querys.put("COST_CENTER_LIST", getSelectCostCenterList(request));
		// PRD組織取得SQL
		querys.put("PRD_ORG_LIST", getSelectPrdOrgList(request));
		// 組織履歴取得SQL
		querys.put("PROJ_ORG_LIST", getSelectPrjOrgList(request));
		// CreditReceiver
		querys.put("CREDITRECEIVER", getQueryCreditreceiver());
		querys.put("CREDITRECEIVER_SHOW",getQueryCreditReceiverShow(request));
		// Booking管理組織取得
		querys.put("BOOKING_ORG", getBookingOrg(request));
		// SUPER_USERかどうか
		querys.put("SUPER_USER",getQuerySuperUser());

		return querys ;
	}

	/**
	 * プロジェクト情報SQL取得メソッド.
	 *
	 */
	private String getSelectSqlProject(HttpServletRequest request)
	{

		String sql = "select "
			+ "\n "
			+	"p.proj_code"
			+ "\n, p.gsi_proj_code"
			+ "\n, decode(p.gsi_proj_code, null, 'true', 'false') oppo_flg" // 9桁なら更新不可
			+ "\n, p.data_upd_type"
			+ "\n, p.oppo_number"
			+ "\n, p.oso_number"
			+ "\n, p.proj_name"
			+ "\n, p.end_user_name"
			+ "\n, p.key_acnt"
			+ "\n, p.tl_acnt"
			+ "\n, p.client_name"
			+ "\n, cont.contract_name"
			+ "\n, p.contract_id"
			+ "\n, (p.cont_amount/1000000) cont_amount"
			+ "\n, to_char(p.contract_date,'YYYY/MM/DD') contract_date"
			+ "\n, decode(SUBSTR(cc.org_id,0,6),'CC0000',SUBSTR(cc.org_id,7),SUBSTR(cc.org_id,5))||'_'||cc.org_name cc_name"
			+ "\n, PJORG.CC_ORG_ID unit_id"
			+ "\n, p.pa_cc_id"
			+ "\n, p.fpauto"
			+ "\n, p.admi"
			+ "\n, decode(cc.org_end_date,null,0,1) cc_end_flg" //対象コストセンタが現在有効かのフラグ
			+ "\n, p.book_status"
			+ "\n, bst.status_name book_status_name"
			+ "\n, p.rev_status"
			+ "\n, rst.status_name rev_status_name"
			+ "\n, p.no_upd_rev_status"
			+ "\n, p.work_status"
			+ "\n, po.emp_name po"
			+ "\n, pm.emp_name pm"
			+ "\n, p.proj_owner_id po_id"
			+ "\n, p.proj_mgr_id pm_id"
			+ "\n, to_char(p.proj_start,'YYYY/MM/DD') proj_start"
			+ "\n, to_char(p.proj_end,'YYYY/MM/DD') proj_end"
			+ "\n, to_char(p.cons_start,'YYYY/MM/DD') cons_start"
			+ "\n, to_char(p.cons_end,'YYYY/MM/DD') cons_end"
			+ "\n, p.classification_4"
			+ "\n, p.project_total"
			+ "\n, p.sa_info"
			+ "\n, NVL(p.SERVICE_CLASSIFICATION,9999) SERVICE_CLASSIFICATION"
			+ "\n, p.comments"
			+ "\n, p.bid"
			+ "\n, p.status"
			+ "\n, p.solution_owner"
			+ "\n, p.progress"
			+ "\n, p.win_prob"
			+ "\n, p.consul_sales"
			+ "\n, p.bunrui_code"
			+ "\n, ob.bunrui_name bunrui_name"
			+ "\n, p.bunrui_name proj_bunrui_name"
			+ "\n, p.module"
			+ "\n, p.prime_flg"
			+ "\n, p.industry"
			+ "\n, p.consulting_type"
			+ "\n, p.revenue_type"
			+ "\n, p.NOT_BOOK_SUM"
			+ "\n, P.BOOKING_ORG"
			+ "\n, P.SKU_NO"
			+ "\n, P.SKU_SPAN"
			+ "\n, P.SKU_REV_REC_RULE"
			+ "\n, RPAD(PRD_ORG.ORG_NAME_SHORT,12,' ')||'：'||PRD_ORG.ORG_NAME PRD_ORG_NAME"
			+ "\n, PJORG.PROD_ORG_ID PRD_ORG_ID "
			+ "\n from project_list_v p" // , emp_list sales"
			+ ", emp_list po, emp_list pm, fcst_status_list bst, fcst_status_list rst"
			+ "\n , contract_list cont, cost_center_list cc, proj_oppo_bunrui_list ob"
			+ "\n , proj_org_list PJORG, product_org_list PRD_ORG "
			+ "\n where " //p.sales_id = sales.emp_id(+)"
			+ "\n   p.proj_mgr_id = pm.emp_id(+)"
			+ "\n and   p.proj_owner_id = po.emp_id(+)"
			+ "\n and   p.contract_id = cont.contract_id(+)"
			+ "\n and   p.book_status = bst.status_id(+)"
			+ "\n and   p.rev_status = rst.status_id(+)"
			+ "\n and   p.bunrui_code = ob.bunrui_code(+)"
			+ "\n and   p.proj_code = '" + projCode + "' "
			+ "\n and PJORG.CC_ORG_ID = cc.org_id"
			+ "\n and  cc.org_start_date = (select nvl((select max(org_start_date)"
			+ "\n from cost_center_list where org_id = cc.org_id"
			+ "\n and  org_start_date <= p.cons_end),"
			+ "\n (select max(org_start_date)  "
			+ "\n from cost_center_list where org_id = cc.org_id)"
			+ "\n ) from dual)"
			+ "\n and p.PROJ_CODE = PJORG.PROJ_CODE(+) "
			+ "\n and PJORG.PROD_ORG_ID = PRD_ORG.ORG_ID(+) "
			+ "\n AND PJORG.START_DATE = (SELECT MAX(START_DATE) FROM PROJ_ORG_LIST WHERE PROJ_CODE = '" + projCode + "')"
			+ "\n and  PRD_ORG.org_start_date = (select nvl((select max(org_start_date) "
			+ "\n from PRODUCT_ORG_LIST where org_id = PRD_ORG.org_id "
			+ "\n and  org_start_date <= p.cons_end), "
			+ "\n (select max(org_start_date)   "
			+ "\n from PRODUCT_ORG_LIST where org_id = PRD_ORG.org_id) "
			+ "\n ) from dual) "
			;

		return sql;
	}

	/**
	 * プロジェクトACTIVITY情報SQL取得メソッド.
	 *
	 */
	private String getSelectProjActivity(HttpServletRequest request)
	{

		String sql = "select a.activity_name, to_char(a.activity_date,'YYYY/MM/DD HH24:MI:SS') activity_date, e.emp_name"
			+ "\n from activity_list a, emp_list e"
			+ "\n where a.update_person = e.emp_id"
			+ "\n and   a.proj_code = '" + projCode + "' "
			;

		return sql;
	}

	/**
	 * プロジェクトシステムアウトライン情報SQL取得メソッド.
	 *
	 */
	private String getSelectSystemOutline(HttpServletRequest request)
	{

		String sql = "select so.comment1, so.comment2, so.comment3, to_char(so.update_date,'YYYY/MM/DD HH24:MI:SS') update_date, e.emp_name"
			+ "\n from system_outline so, emp_list e"
			+ "\n where so.proj_code = '" + projCode + "' "
			+ "\n and   so.update_person = e.emp_id(+)"
			;

		return sql;
	}

	/**
	 * システム概要メニュー(GBL_SYSTEM_OUTLINE)情報SQL取得メソッド.
	 * end_flgは終っている項目なら1が返り。現在有効な値なら0が返る。
	 *
	 */
	private String getSelectGblSystemOutline(HttpServletRequest request)
	{

		String sql = "select o.proj_code, d.category_id, d.desc_id,d.description "
			+ ", decode(d.end_date,null,'0','1') end_flg "
			+ "\n from gbl_system_outline o, gbl_class_description d"
			+ "\n where d.category_id = o.category_id"
			+ "\n and   d.desc_id = o.desc_id"
			+ "\n and   o.proj_code = '" + projCode + "' "
			;

		return sql;
	}

	/**
	 * システム概要メニューのSELECTを作成するための情報取得メソッド.
	 *
	 */
	private String getSelectGblSystemMenu(HttpServletRequest request)
	{

		String sql = "select cc.category_id, cd.desc_id, cd.description "
			+ "\n from gbl_class_category cc, gbl_class_description cd"
			+ "\n where cc.category_id = cd.category_id"
			+ "\n and   cd.end_date is null"
			+ "\n and   cc.category_id in (2,8)"
			+ "\n order by cc.category_id, cd.desc_id"
			;

		return sql;
	}

	/**
	 * STATUS_LIST情報SQL取得メソッド.
	 *
	 */
	private String getSelectFcstStatusList(HttpServletRequest request)
	{

		String sql = "select st.status_id, st.status_name"
			+ "\n from fcst_status_list st"
			+ "\n order by sort_number "
			;

		return sql;
	}

	/**
	 * WORK_STATUS_LIST情報SQL取得メソッド.
	 *
	 */
	private String getSelectWorkStatusList(HttpServletRequest request)
	{

		String sql = "select st.status_id, st.s_code||':'||st.status_name status"
			+ "\n from work_status_list st"
			+ "\n order by status_id "
			;

		return sql;
	}

	/**
	 * CONTRACT_LIST情報SQL取得メソッド.
	 *
	 */
	private String getSelectContractList(HttpServletRequest request)
	{

		String sql = "select contract_id, contract_name"
			+ "\n from contract_list"
			+ "\n order by sort_number "
			;

		return sql;
	}

	/**
	 * TYPE_LIST情報SQL取得メソッド.
	 *
	 */
	private String getSelectTypeList(HttpServletRequest request)
	{

		String sql = "select type_id, type_name"
			+ "\n from type_list"
			+ "\n order by type_id "
			;

		return sql;
	}

	/**
	 * SERVICE_CLASSIFICATION情報SQL取得メソッド
	 */
	private String getSelectServiceClassification(HttpServletRequest request)
	{

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID,SERVICE_NAME");
		sql.append("\n  FROM SERVICE_CLASSIFICATION S,PROJECT_LIST P");
		sql.append("\n WHERE P.PROJ_CODE = '");
		sql.append(projCode);
		sql.append("'");
		sql.append("\n   AND P.SERVICE_CLASSIFICATION_ID = S.ID");
		sql.append("\n   AND S.DEL_FLG = 1");
		sql.append("\n UNION");
		sql.append("\n SELECT 9999 ID, SERVICE_PORTFOLIO");
		sql.append("\n  FROM PROJECT_LIST_V");
		sql.append("\n WHERE SERVICE_CLASSIFICATION IS NULL");
		sql.append("\n   AND SERVICE_PORTFOLIO IS NOT NULL");
		sql.append("\n   AND PROJ_CODE = '");
		sql.append(projCode);
		sql.append("'");
		sql.append("\n UNION");
		sql.append("\n SELECT ID,SERVICE_NAME");
		sql.append("\n  FROM SERVICE_CLASSIFICATION S");
		sql.append("\n WHERE DEL_FLG = 0");
		sql.append("\n   AND SERVICE_NAME != (SELECT DECODE(SERVICE_CLASSIFICATION,NULL,NVL(SERVICE_PORTFOLIO,' '),' ')");
		sql.append("\n                          FROM PROJECT_LIST_V WHERE PROJ_CODE = '");
		sql.append(projCode);
		sql.append("')");
		sql.append("\n ORDER BY ID");

		return sql.toString();
	}

	/**
	 * COST_CENTER_LIST情報SQL取得メソッド.
	 * 選択されたプロジェクトのcons_start,cons_endの間に存在する最新のコストセンタ一覧を取得
	 * cons_startが未来のFYの期間の場合には現FYのコストセンター一覧を取得
	 *
	 */
	private String getSelectCostCenterList(HttpServletRequest request)
	{

		StringBuffer sql = new StringBuffer();
		sql.append("select decode(org_level,2,'',3,'　',4,'　　',5,'　　　',null)|| decode(SUBSTR(cc.org_id,0,6),'CC0000',SUBSTR(cc.org_id,7),SUBSTR(cc.org_id,5)) || '_' ||cc.org_name org_name, cc.org_id, cc.org_end_date");
		sql.append("\n  from cost_center_list cc");
		sql.append("\n,(select start_date, end_date from fy_term ff where ff.start_date = (select max(org_start_date) from cost_center_list)) max1");
		sql.append("\n,(select case when max_org.start_date < t.start_date then max_org.start_date else t.start_date end start_date");
		sql.append("\n, case when max_org.end_date < t.end_date then max_org.end_date else t.end_date end end_date");
		sql.append("\n from (select start_date, end_date from fy_term ff where ff.start_date = (select max(org_start_date) from cost_center_list)) max_org");
		sql.append("\n,(select max(f.start_date)  start_date,max(f.end_date) end_date");
		sql.append("\n     from project_list_v p");
		sql.append("\n         ,(select min(start_date)start_date,max(end_date) end_date,fy from fy_term group by fy) f");
		sql.append("\n    where p.proj_code = '");
		sql.append(projCode);
		sql.append("'");
		sql.append("\n      and p.cons_end between f.start_date and f.end_date) t) cons");
		sql.append("\n where (nvl(cc.org_end_date,max1.end_date) between cons.start_date and cons.end_date");
		sql.append("\n       or cc.org_start_date between cons.start_date and cons.end_date)");
		sql.append("\n order by cc.sort_number,cc.level1,cc.level2,nvl(cc.level3,'0'),nvl(cc.level4,'0'),nvl(cc.level5,'0')");

		return sql.toString();
	}


	/**
	 * PRD組織情報SQL取得メソッド.
	 * 選択されたプロジェクトのcons_start,cons_endの間に存在する最新のPRD組織一覧を取得
	 * cons_startが未来のFYの期間の場合には現FYのPRD組織一覧を取得
	 */
	private String getSelectPrdOrgList(HttpServletRequest request)
	{

		StringBuffer sql = new StringBuffer();

		sql.append("select decode(org_level,2,'',3,'　',4,'　　',5,'　　　',6,'　　　　',null)||RPAD(ORG.org_name_short,12,' ')||'：'||ORG.ORG_NAME org_name, ORG.org_id, ORG.org_end_date");
		sql.append("\n from PRODUCT_ORG_LIST ORG");
		sql.append("\n,(select start_date, end_date from fy_term ff where ff.start_date = (select max(org_start_date) from PRODUCT_ORG_LIST)) max1");
		sql.append("\n,(select case when max_org.start_date < t.start_date then max_org.start_date else t.start_date end start_date");
		sql.append("\n, case when max_org.end_date < t.end_date then max_org.end_date else t.end_date end end_date");
		sql.append("\n from (select start_date, end_date from fy_term ff where ff.start_date = (select max(org_start_date) from PRODUCT_ORG_LIST)) max_org");
		sql.append("\n,(select max(f.start_date)  start_date,max(f.end_date) end_date");
		sql.append("\n     from project_list_v p");
		sql.append("\n         ,(select min(start_date)start_date,max(end_date) end_date,fy from fy_term group by fy) f");
		sql.append("\n where p.proj_code = '");
		sql.append(projCode);
		sql.append("'");
		sql.append("\n      and p.cons_end between f.start_date and f.end_date) t) cons");
		sql.append("\n where (nvl(org.org_end_date,max1.end_date) between cons.start_date and cons.end_date");
		sql.append("\n       or org.org_start_date between cons.start_date and cons.end_date)");
		sql.append("\n order by ORG.sort_number,ORG.level1,ORG.level2,nvl(ORG.level3,'0'),nvl(ORG.level4,'0'),nvl(ORG.level5,'0')");

		return sql.toString();
	}

	/**
	 * 過去組織情報SQL取得メソッド.
	 * PROJ_ORG_LIST.END_DATEの時のPRODUCT_ORG_LIST.ORG_NAME_SHORT.なければ最新のORG_NAME_SHORT
	 *
	 */
	private String getSelectPrjOrgList(HttpServletRequest request)
	{

		String sql = "SELECT DECODE(TO_CHAR(PORG.START_DATE,'YYYY/MM/DD'),'1900/01/01','-',TO_CHAR(PORG.START_DATE,'YYYY/MM/DD')) START_DATE"
			+ ", NVL(TO_CHAR(PORG.END_DATE,'YYYY/MM/DD'),'-') END_DATE"
			+ ", PORG.PROD_ORG_ID, NVL(PD.ORG_NAME_SHORT, LATESTNAME.ORG_NAME_SHORT) ORG_NAME_SHORT"
			+ ", decode(SUBSTR(PORG.cc_org_id,0,6),'CC0000',SUBSTR(PORG.cc_org_id,7),SUBSTR(PORG.cc_org_id,5)) CC_ORG_ID, NVL(CC.ORG_NAME,LATESTNAME_CCORG.ORG_NAME) ORG_NAME "
			+ "\n FROM   PROJ_ORG_LIST PORG, PRODUCT_ORG_LIST PD, COST_CENTER_LIST CC "
			// PRODUCT_ORG_LISTのIDのうち、最新のものの名前一覧
			+ "\n ,(SELECT PDORGIN.ORG_ID, PDORGIN.ORG_START_DATE, PDORGIN.ORG_NAME_SHORT "
			+ "\n 	FROM PRODUCT_ORG_LIST PDORGIN "
			+ "\n 	,(SELECT ORG_ID, MAX(ORG_START_DATE) ORG_START_DATE "
			+ "\n 	FROM   PRODUCT_ORG_LIST "
			+ "\n 	GROUP BY ORG_ID "
			+ "\n 	)PDORGMAX "
			+ "\n 	WHERE PDORGIN.ORG_ID = PDORGMAX.ORG_ID "
			+ "\n 	AND   PDORGIN.ORG_START_DATE = PDORGMAX.ORG_START_DATE "
			+ "\n 	) LATESTNAME"

			// COST_CENTER_LISTのIDのうち、最新のものの名前一覧
			+ "\n ,(SELECT CCIN.ORG_ID, CCIN.ORG_START_DATE, CCIN.ORG_NAME "
			+ "\n		 	FROM COST_CENTER_LIST CCIN "
			+ "\n		 	,(SELECT ORG_ID, MAX(ORG_START_DATE) ORG_START_DATE "
			+ "\n		 	FROM   COST_CENTER_LIST "
			+ "\n		 	GROUP BY ORG_ID "
			+ "\n		 	)CCORGMAX "
			+ "\n		 	WHERE CCIN.ORG_ID = CCORGMAX .ORG_ID "
			+ "\n		 	AND   CCIN.ORG_START_DATE = CCORGMAX.ORG_START_DATE "
			+ "\n		 	) LATESTNAME_CCORG"

			+ "\n WHERE  PORG.PROD_ORG_ID = PD.ORG_ID(+) "
			+ "\n AND    PORG.CC_ORG_ID = CC.ORG_ID(+) "
			+ "\n AND    NVL(PORG.END_DATE,TO_DATE('29991231','YYYYMMDD')) BETWEEN PD.ORG_START_DATE(+) AND NVL(PD.ORG_END_DATE(+),TO_DATE('29991231','YYYYMMDD')) "
			+ "\n AND    NVL(PORG.END_DATE,TO_DATE('29991231','YYYYMMDD')) BETWEEN CC.ORG_START_DATE(+) AND NVL(CC.ORG_END_DATE(+),TO_DATE('29991231','YYYYMMDD')) "
			+ "\n AND    PORG.PROD_ORG_ID = LATESTNAME.ORG_ID(+)"
			+ "\n AND    PORG.CC_ORG_ID = LATESTNAME_CCORG.ORG_ID(+)"
			+ "\n AND    PORG.PROJ_CODE(+) = '"+ getParameter(request, "c_proj_code","") + "' "
			+ "\n ORDER  BY PORG.START_DATE"
			;

		return sql;
	}

	/**
	 * PROJ_CREDITRECEITER取得クエリ文字列作成メソッド.
	 *
	 */
	private String getQueryCreditreceiver()
	{
		String sql = "SELECT CR.GSI_PROJ_CODE, CR.EMP_ID, DECODE(E.EMP_ID,551900,'(退職者等)',551901,'(退職者等)',E.EMP_NAME) EMP_NAME, CR.PERCENT"
			+ "\n FROM PROJECT_LIST_V P, PROJ_CREDITRECEIVER CR, EMP_LIST E"
			+ "\n WHERE SUBSTR(P.GSI_PROJ_CODE,1,9) = CR.GSI_PROJ_CODE"
			+ "\n AND   CR.EMP_ID = E.EMP_ID"
			+ "\n AND   P.PROJ_CODE = '" + projCode + "'"
			+ "\n ORDER BY CR.PERCENT DESC ";
		return sql;
	}

	/**
	 * Booking管理組織取得メソッド.
	 *
	 */
	private String getBookingOrg(HttpServletRequest request)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT BOOKING_ORG, SORT_NUM FROM (");
		sql.append("\n SELECT BOOKING_ORG, SORT_NUM");
		sql.append("\n FROM BOOKING_ORG");
		// プルダウンに存在しない値を表示するためにunionで追加する
		sql.append("\n UNION");
		sql.append("\n SELECT BOOKING_ORG, 99 SORT_NUM FROM PROJECT_LIST_V P");
		sql.append("\n WHERE NOT EXISTS (SELECT 1 FROM BOOKING_ORG O WHERE P.BOOKING_ORG = O.BOOKING_ORG)");
		sql.append("\n AND PROJ_CODE = '");
		sql.append(projCode);
		sql.append("'");
		sql.append("\n AND BOOKING_ORG IS NOT NULL)");
		sql.append("\n ORDER BY SORT_NUM");
		return sql.toString();
	}

	// creditreceiverを表示できるかどうか。VP/DM LM PM CADM SALES のみ参照可
	private String getQueryCreditReceiverShow( HttpServletRequest request )
	{
		String sql =
			"SELECT DECODE(NVL(POSITION_ID,0),3,'true',4,'true',6,'true',7,'true',8,'true','false') CR_SHOW "
			+ " FROM EMP_LIST WHERE EMP_ID = " + getCookie("USERID") ;
		return sql;
	}


	/**
	 * SUPER_AUTH クエリ文字列作成メソッド.
	 *
	 * @param request サーブレットリクエスト
	 *  @return クエリ文字列
	 */
	private String getQuerySuperUser()
	{
		String sql = "SELECT DECODE(COUNT(ROWID), 0, 'false', 'true') super_user"
			+ "\n FROM const_list"
			+ "\n WHERE name = 'SUPER_USER'"
			+ "\n AND end_date IS NULL"
			+ "\n AND value = '" + getCookie("USERID") + "'"
		;
		return sql;
	}

	/**
	 * スタイルシートURL取得メソッド.
	 *
	 *
	 */
	protected URL getXSLURL( HttpServletRequest request ) throws Exception
	{
		return getClass().getResource( "/xsl/project/detail/ProjUpdate.xsl" ) ;
	}


	/**
	 * メニューコード取得メソッド.
	 *
	 * @return メニューコード
	 */
	protected String getMenuCode( HttpServletRequest request )
	{
		//プロジェクト登録更新
		return "10101";
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
			// copy_to
			appendTextElement( x_doc, rowset, "c_copy_to", getParameter(request, "c_copy_to", ""));
		}
	}
}
