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
// C00031    2007/06/15    Atsuko.Sashimura   CCプルダウンソート変更
// C00057    2008/01/21    Atsuko.Sashimura   Siebel移行
////////////////////////////////////////////////////////////////////////////////
package contents;

import java.util.Hashtable ;
import java.util.Calendar;
import java.net.URL ;
import javax.servlet.http.HttpServletRequest ;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

/**
 * Project検索画面
 *
 *
 *
 */
public class Project_Search extends ContentsBase
{

	/**
	 * クエリー取得メソッド.
	 *
	 *
	 */
	protected Hashtable getQuery( HttpServletRequest request ) 
	{
		Hashtable querys = new Hashtable();
		
		querys.put("ORG",getQuery_Org(request));
		querys.put("WORK_STATUS",getQuery_WorkStatus(request));
		querys.put("BOOK_STATUS",getQueryBookStatus(request));
		querys.put("DATE",getQuery_Date(request));
		querys.put("CONT_TYPE",getQuery_ContType(request));
		querys.put("CLASSIFICATION",getQuery_Classification(request));
		querys.put("EXCEL",getQuery_Excel(request));
		
		return querys ;
	}
	
	
	/**
	 * コストセンタ組織SQL
	 *
	 *
	 */
	private String getQuery_Org( HttpServletRequest request ) 
	{
		int fy = 0;
		
		//指定fyが無い時(開始時)
		if((request.getParameter("c_fy") == null) || request.getParameter("c_fy").equals(""))
		{
			//6月以降の場合は(FYに合わせるため)年に1加える
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			
			//現在FY
			if(month >=6)
			{
				fy = year + 1;
			}
			else
			{
				fy = year;
			}
		}
		//指定FYがある時
		else
		{
			fy = Integer.parseInt(request.getParameter("c_fy"));
		}
		
		//組織
		String sql =
				"SELECT distinct"
					+ "\n CC.org_id ORG_ID"
					+ "\n,CC.org_level ORG_LEVEL"
					+ "\n,CC.org_name ORG_NAME"
					+ "\n,NVL(CC.level2,'0') LEVEL2"	//ソート用
					+ "\n,NVL(CC.level3,'0') LEVEL3"	//ソート用
					+ "\n,NVL(CC.level4,'0') LEVEL4"	//ソート用
					+ "\n,NVL(CC.level5,'0') LEVEL5"	//ソート用
					+ "\n,CC.SORT_NUMBER "
					+ "\n,DECODE(CC.org_end_date,null,0,1) DEL_FLG"	//終了しているかどうか
				+ "\n FROM"
					+ "\n cost_center_list CC"
					+ "\n,const_list CONST"
					+ "\n WHERE 1=1"
					+ getOrgPeriod(fy,"CC")
					+ "\n and CONST.name = 'CONSUL_ORG_ID'"
					+ "\n and CONST.value = CC.level2";
		
		if(fy >= 2002 && fy < 2004)
		{
			sql = sql + "\n and substr(CC.org_id,1,8) in (select value from const_list where name='CONSUL_ORG_ID_P')";
		}
					
		sql = sql + "\n AND CC.org_start_date = (select max(org_start_date) from cost_center_list where org_id = CC.org_id"
					+ getOrgPeriod(fy,"")
					+ "\n )"
					+ "\n ORDER by sort_number nulls last, level2, level3, level4, level5  "
					;
		
		return sql;
	}
	
	/**
	 * WorkStatus SQL
	 *
	 *
	 */
	private String getQuery_WorkStatus( HttpServletRequest request ) 
	{
		String sql =
				"SELECT"
					+ " status_id"
					+ ",s_code"
					+ ",status_name"
				+ " FROM"
					+ " work_status_list";
		return sql;
	}
	
	/**
	 * BookingStatus SQL
	 * 
	 */
	private String getQueryBookStatus(HttpServletRequest request){
		String sql = "SELECT STATUS_ID, STATUS_NAME FROM FCST_STATUS_LIST"
			+ "\n ORDER BY SORT_NUMBER";
		return sql;
	}
	
	/**
	 * 現在年(FYを(現在年+2)まで表示するため) SQL
	 *
	 *
	 */
	private String getQuery_Date( HttpServletRequest request ) 
	{
		String sql =
				"SELECT"
					+ " to_char(sysdate,'yyyy') PRESENT_YEAR"
				+ " FROM"
					+ " dual";
		return sql;
	}
	
	/**
	 * 契約形態 SQL
	 *
	 *
	 */
	private String getQuery_ContType( HttpServletRequest request ) 
	{
		String sql =
				"SELECT"
					+ " contract_id"
					+ ",contract_name"
				+ " FROM"
					+ " contract_list"
				+ " ORDER BY"
						+ " sort_number";
		return sql;
	}
	
	/**
	 * Classification SQL
	 *
	 *
	 */
	private String getQuery_Classification( HttpServletRequest request ) 
	{
		String sql =
				"SELECT"
					+ " desc_id"
					+ ",description"
				+ " FROM"
					+ " gbl_class_description"
				+ " WHERE"
					+ " category_id = 8";	//8はclassificationを表している
		return sql;
	}
	
	/**
	 * CADM以上とPM(エクセル表示できるか権限用) SQL
	 *
	 *
	 */
	private String getQuery_Excel( HttpServletRequest request ) 
	{
		String sql =
				"SELECT "
					+ " decode(count(position_id),0,'FALSE','TRUE') AUTH"
				+ " FROM"
					+ " position_list"
				+ " WHERE "
					+ getCookie("AUTH_CODE") + " in (1,2,5,8)";//1:VM,GM,M 2:UM 5:CADM 8:PM
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
		// Project検索
		return getClass().getResource( "/xsl/Project_Search.xsl" ) ;
	}
	
	/**
	 * メニューコード取得メソッド.
	 *
	 * @return メニューコード
	 */
	protected String getMenuCode( HttpServletRequest request )
	{
		// Project検索
		return "10201";
	}
	
	/**
	 * DOM取得後処理メソッド.
	 *
	 *
	 */
	protected void actionAfterGetDOM( String key , Element rowset , Document x_doc , HttpServletRequest request)
		throws Exception 
	{
		if(key.equals("header"))
		{
			//検索タイプ
			appendTextElement( x_doc, rowset, "forecast_type" , getParameter(request,"forecast_type","title_standard"));
			
			//指定FY,指定開始月//6月以降の場合は(FYに合わせるため)表示FYに1加える
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;

			if(month > 5)
			{
				year = year + 1;
			}
			
				//指定FY(ない場合は現在のFY)
			appendTextElement( x_doc, rowset, "c_fy" , getParameter(request,"c_fy",""+year));
				//c_org_id
			appendTextElement( x_doc, rowset, "c_org_id" , getParameter(request,"c_org_id",""));
				//c_work_status
			appendTextElement( x_doc, rowset, "c_work_status" , getParameter(request,"c_work_status",""));
				//c_book_status(forecast_status)
			appendTextElement( x_doc, rowset, "c_book_status" , getParameter(request,"c_book_status",""));
				//c_proj_code
			appendTextElement( x_doc, rowset, "c_proj_code" , getParameter(request,"c_proj_code",""));
				//c_gsi_proj_code
			appendTextElement( x_doc, rowset, "c_gsi_proj_code" , getParameter(request,"c_gsi_proj_code",""));
				//c_proj_name
			appendTextElement( x_doc, rowset, "c_proj_name" , getParameter(request,"c_proj_name",""));
				//c_proj_owner_name
			appendTextElement( x_doc, rowset, "c_proj_owner_name" , getParameter(request,"c_proj_owner_name",""));
				//c_proj_mgr_name
			appendTextElement( x_doc, rowset, "c_proj_mgr_name" , getParameter(request,"c_proj_mgr_name",""));
				//c_end_user_name
			appendTextElement( x_doc, rowset, "c_end_user_name" , getParameter(request,"c_end_user_name",""));
				//c_client_name
			appendTextElement( x_doc, rowset, "c_client_name" , getParameter(request,"c_client_name",""));
				//c_contract_id
			appendTextElement( x_doc, rowset, "c_contract_id" , getParameter(request,"c_contract_id",""));
				//c_classification
			appendTextElement( x_doc, rowset, "c_classification" , getParameter(request,"c_classification",""));
				//c_sort1
			appendTextElement( x_doc, rowset, "c_sort1" , getParameter(request,"c_sort1","Work Status"));
				//c_sort2
			appendTextElement( x_doc, rowset, "c_sort2" , getParameter(request,"c_sort2","Booking Status"));
				//c_sort3
			appendTextElement( x_doc, rowset, "c_sort3" , getParameter(request,"c_sort3","Temp Number"));
			
			
				//Temp Number
			appendTextElement( x_doc, rowset, "o_proj_code" , getParameter(request,"o_proj_code","true"));
				//Project Number
			appendTextElement( x_doc, rowset, "o_gsi_proj_code" , getParameter(request,"o_gsi_proj_code","true"));
				//Opportunity Number
			appendTextElement( x_doc, rowset, "o_oppo_number" , getParameter(request,"o_oppo_number","true"));
				//Project Name
			appendTextElement( x_doc, rowset, "o_proj_name" , getParameter(request,"o_proj_name","true"));
				//契約先名
			appendTextElement( x_doc, rowset, "o_client_name" , getParameter(request,"o_client_name","true"));
				//エンドユーザー名
			appendTextElement( x_doc, rowset, "o_end_user_name" , getParameter(request,"o_end_user_name","true"));
				//プロジェクトオーナー
			appendTextElement( x_doc, rowset, "o_proj_owner_name" , getParameter(request,"o_proj_owner_name","true"));
				//プロジェクトマネージャー
			appendTextElement( x_doc, rowset, "o_proj_mgr_name" , getParameter(request,"o_proj_mgr_name","true"));
				//CLASSIFICATION
			appendTextElement( x_doc, rowset, "o_classification" , getParameter(request,"o_classification","true"));
			//CLASSIFICATION_2
			appendTextElement( x_doc, rowset, "o_classification_2" , getParameter(request,"o_classification_2",""));
			//CLASSIFICATION_3
			appendTextElement( x_doc, rowset, "o_classification_3" , getParameter(request,"o_classification_3",""));
			//CLASSIFICATION_4
			appendTextElement( x_doc, rowset, "o_classification_4" , getParameter(request,"o_classification_4",""));
				//Forecast Status
			appendTextElement( x_doc, rowset, "o_book_status" , getParameter(request,"o_book_status","true"));
			//Forecast Status
			appendTextElement( x_doc, rowset, "o_rev_status" , getParameter(request,"o_rev_status","true"));
				//Work Status
			appendTextElement( x_doc, rowset, "o_work_status" , getParameter(request,"o_work_status","true"));
				//組織名
			appendTextElement( x_doc, rowset, "o_org_name" , getParameter(request,"o_org_name",""));
				//契約金額
			appendTextElement( x_doc, rowset, "o_cont_amount" , getParameter(request,"o_cont_amount",""));
				//契約形態
			appendTextElement( x_doc, rowset, "o_contract_type" , getParameter(request,"o_contract_type",""));

			//win_prob
			appendTextElement( x_doc, rowset, "o_win_prob" , getParameter(request,"o_win_prob",""));
			//primarySales
			appendTextElement( x_doc, rowset, "o_sales" , getParameter(request,"o_sales",""));
			//proj_Start
			appendTextElement( x_doc, rowset, "o_proj_start" , getParameter(request,"o_proj_start",""));
			//proj_end
			appendTextElement( x_doc, rowset, "o_proj_end" , getParameter(request,"o_proj_end",""));						
				//契約終結予定日
			appendTextElement( x_doc, rowset, "o_contract_date" , getParameter(request,"o_contract_date",""));
				//コンサルティング開始日
			appendTextElement( x_doc, rowset, "o_cons_start" , getParameter(request,"o_cons_start",""));
				//コンサルティング終了日
			appendTextElement( x_doc, rowset, "o_cons_end" , getParameter(request,"o_cons_end",""));
				//CopyButton
			appendTextElement( x_doc, rowset, "o_copy_button" , getParameter(request,"o_copy_button","true"));
			
			//ソート用
			String[] sort_item = {
									"組織"
									,"Booking Status"
									,"Revenue Status"
									,"Work Status"
									,"Temp Number"
									,"Project Number"
									,"Project Name"
									,"エンドユーザー"
									,"契約先社名"
									,"PO"
									,"PM"
									,"契約金額"
									,"契約形態"
									,"契約締結日"
									,"PJ開始日"
									,"PJ終了日"
								};
			
			Element sortset = appendElement( x_doc, rowset, "SORTSET");
			
			for(int cnt = 0;cnt<sort_item.length;cnt++)
			{
				Element sort = appendElement( x_doc, sortset, "SORT");
				appendTextElement( x_doc, sort, "ITEM" , sort_item[cnt] );
			}
		}
	}
}


