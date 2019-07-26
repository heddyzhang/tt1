////////////////////////////////////////////////////////////////////////////////
//
//
// create 2002-05-22
//
////////////////////////////////////////////////////////////////////////////////
// 更新履歴
//  Ver.     日付          担当者          変更内容
//  1.0      2002/05/22    竹村正義        新規作成
//  1.1      2002/09/19    Masayoshi.Takemura 予算合計を表示するように変更
//  1.2      2003/08/25    Masayoshi.Takemura 発注表示のForecastList2遷移追加
//  1.3      2004/03/23    Masayoshi.Takemura ForecastStatus Vのみ表示対応(ソリューション推進部)
// C00012    2006/02/16    Atsuko.Sashimura   発注比較を削除
// C00019    2006/11/15    Atsuko.Sashimura   OracleSalesのデータを表示
// C00031    2007/06/15    Atsuko.Sashimura   CCプルダウンソート変更
// C00057    2008/01/21    Atsuko.Sashimura   Siebel移行
// C00062    2008/02/25    指村 敦子      PL管理表で履歴を表示するためのテーブル変更に対応
//           2013/05/22    Mari.Suzuki        OppoTypeカラム分割対応
//           2013/07/18    Mari.Suzuki        締結QTR追加
//           2014/03/26    Mari.Suzuki        Booking集計対象外追加
//           2014/10/22    Minako.Uenaka      項目名英語表記対応に伴う改修
////////////////////////////////////////////////////////////////////////////////
package contents;

import java.net.URL;
import java.util.Calendar;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Forecast検索画面
 *
 *
 *
 */
public class Forecast_Search extends ContentsBase
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
		querys.put("PROJ_STATUS",getQuery_ProjStatus(request));
		querys.put("DATE",getQuery_Date(request));
		querys.put("BUNRUI", getQueryBunrui(request));
		querys.put("PRD_ORG", getQueryProductOrg(request));
		querys.put("BOOKING_ORG", getQueryBookingOrg(request));
		querys.put("CONS_CATEGORYS", getQueryConsCategory(request));

		//Forecast検索時のみ必要
		if(!getParameter(request,"forecastReport_flg","").equals("yes"))
		{
			querys.put("CONT_TYPE",getQuery_ContType(request));
			querys.put("CLASSIFICATION",getQuery_Classification(request));
		}

		querys.put("CONT_MEISAI",getQueryContMeisai(request));
		querys.put("FORECAST_HISTORY",getQuery_ForecastHistory(request));
		querys.put("ORG_FSTATUS",getQuery_SpecialDefault(request));
		querys.put("POSITION_ID",getQueryPositionId(request));
		querys.put("SUPER_USER", getQuerySuperUser());

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
	 * ProjectStatus(ForecastStatus) SQL
	 *
	 *
	 */
	private String getQuery_ProjStatus( HttpServletRequest request )
	{
		String sql =
				"SELECT"
					+ " status_id"
					+ ",s_code"
					+ ",status_desc"
				+ " FROM"
					+ " status_list"
				+ " ORDER BY"
					+ " sort_number";
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
	 * Booking管理組織マスタを取得する
	 *
	 */
	private String getQueryBookingOrg(HttpServletRequest request)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT BOOKING_ORG");
		sql.append("\n FROM BOOKING_ORG");
		sql.append("\n ORDER BY SORT_NUM");

		return sql.toString();
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
					+ ",DECODE(end_date,null,0,1) DEL_FLG "
				+ " FROM"
					+ " contract_list"
				+ " WHERE"
					+ " END_DATE is null"
				+ " ORDER BY"
						+ " sort_number";
		return sql;
	}

	/**
	 * SUPER_AUTH クエリ文字列作成メソッド.
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


	//CADM以上(業務委託明細用か権限用)
	private String getQueryContMeisai( HttpServletRequest request )
	{
		String sql =
				"SELECT "
					+ " decode(count(position_id),0,'FALSE','TRUE') AUTH"
				+ " FROM"
					+ " position_list"
				+ " WHERE "
					+ getCookie("AUTH_CODE") + " in (5)";//1:VM,GM,M 2:UM 5:CADM
		return sql;
	}

	// PositionIDの取得 Vに変更一覧機能表示用
	private String getQueryPositionId( HttpServletRequest request )
	{
		String sql =
				"SELECT NVL(POSITION_ID,0) POSITION_ID FROM EMP_LIST WHERE EMP_ID = " + getCookie("USERID") ;
		return sql;
	}

	/**
	 * Forecast履歴 SQL
	 *
	 *
	 */
	private String getQuery_ForecastHistory( HttpServletRequest request )
	{
		String sql = "select history_id, to_char(history_date,'yyyy/mm/dd hh24:mi') history_date "
			+ "\n from history_list where (history_id like 'F%' or history_id like 'H%') and history_id not in ('F000124','F000136')"	// F124, F136はプロジェクト情報がないため
			+ "\n order by history_date desc "
			;
		return sql;
	}

	/**
	 * 特別デフォルト処理
	 *(ソリューション推進部のみForecastStatusのデフォルトをVのみ非表示にしたいため
	 *
	 */
	private String getQuery_SpecialDefault( HttpServletRequest request )
	{
		String sql =
				"SELECT"
					+ " value"
				+ " FROM"
					+ " const_list"
				+ " WHERE"
					+ " name='SpecialDefault'"
				+ " AND"
					+ " sysdate between start_date and nvl(end_date,sysdate+1)"
				;
		return sql;
	}

	/**
	 * BUNRUI SQL
     *
	 */
	private String getQueryBunrui( HttpServletRequest request )
	{
		String sql =
				"select"
					+ " bunrui_code"
					+ ",bunrui_name"
					+ ",end_date"
				+ " from"
					+ " proj_oppo_bunrui_list"
				+ " where end_date is null "
				+ " order by"
					+ " sort_number";
		return sql;
	}

	/**
	 * ConsCategory SQL
     *
	 */
	private String getQueryConsCategory( HttpServletRequest request )
	{
		String sql =
				"select"
					+ " category_id"
					+ ",category_name"
				+ " from"
					+ " cons_category"
				+ " where on_premis_flg is not null "
				+ " order by"
					+ " sort_number";
		return sql;
	}

	/**
	 * PRODUCT組織 SQL
     *
	 */
	private String getQueryProductOrg( HttpServletRequest request )
	{
		// PJ管理組織カットはFY12用のため固定
		int fy = 2012;

		if (getParameter(request,"fy12_flg","").equals("")) {
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
		}

		//組織
		String sql ="SELECT DISTINCT"
					+ "\n PRDO.ORG_ID ORG_ID"
					+ "\n,PRDO.ORG_LEVEL ORG_LEVEL"
					+ "\n,PRDO.ORG_NAME ORG_NAME"
					+ "\n,NVL(PRDO.LEVEL2,'0') LEVEL2"	//ソート用
					+ "\n,NVL(PRDO.LEVEL3,'0') LEVEL3"	//ソート用
					+ "\n,NVL(PRDO.LEVEL4,'0') LEVEL4"	//ソート用
					+ "\n,NVL(PRDO.LEVEL5,'0') LEVEL5"	//ソート用
					+ "\n,NVL(PRDO.LEVEL6,'0') LEVEL6"	//ソート用
					+ "\n,PRDO.SORT_NUMBER "
					+ "\n,DECODE(PRDO.ORG_END_DATE,NULL,0,1) DEL_FLG"	//終了しているかどうか
			    	+ "\n FROM"
					+ "\n PRODUCT_ORG_LIST PRDO"
					+ "\n,CONST_LIST CONST"
					+ "\n WHERE 1=1"
					+ getPrdOrgPeriod(fy,"PRDO")
					+ "\n AND CONST.NAME = 'CONSUL_PRODUCT_ORG_ID'"
					+ "\n AND CONST.VALUE = PRDO.LEVEL2";

		sql = sql + "\n AND PRDO.ORG_START_DATE = (SELECT MAX(ORG_START_DATE) FROM PRODUCT_ORG_LIST WHERE ORG_ID = PRDO.ORG_ID"
					+ getPrdOrgPeriod(fy,"")
					+ "\n )"
					+ "\n ORDER BY SORT_NUMBER NULLS LAST, LEVEL2, LEVEL3, LEVEL4, LEVEL5, LEVEL6  "
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
		URL url;

		if(getParameter(request,"forecastReport_flg","").equals("yes"))
		{
			// ForecastReport検索
			url = getClass().getResource( "/xsl/ForecastReport_Search.xsl" ) ;
		}
		else
		{
			// Forecast検索
			url = getClass().getResource( "/xsl/Forecast_Search.xsl" ) ;
		}

		return url;
	}


	/**
	 * メニューコード取得メソッド.
	 *
	 * @return メニューコード
	 */
	protected String getMenuCode( HttpServletRequest request )
	{
		String code = "";

		if(getParameter(request,"forecastReport_flg","").equals("yes"))
		{
			// ForecastReport検索
			code = "20601";
		}
		else
		{
			// Forecast検索
			code = "20101";
		}

		return code;
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
			if(getParameter(request,"forecastReport_flg","").equals("yes"))
			{
				// ForecastReport検索

			}
			else
			{
				// Forecast検索
				appendTextElement( x_doc, rowset, "forecast_type" , getParameter(request,"forecast_type","title_standard"));
			}
			//Revenue or CI
			appendTextElement( x_doc, rowset, "c_revenue_ci" , getParameter(request,"c_revenue_ci","0"));
			//Forecast履歴番号
			appendTextElement( x_doc, rowset, "c_history_id" , getParameter(request,"c_history_id",""));
			//Forecast履歴を表示するか否か(Report用)
			appendTextElement( x_doc, rowset, "history_flg" , getParameter(request,"history_flg",""));


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
			//指定開始月(ない場合は現在の月)
			appendTextElement( x_doc, rowset, "c_start_month" , getParameter(request,"c_start_month",""+month));
			//指定表示期間(ない場合は3ヶ月)
			appendTextElement( x_doc, rowset, "c_show_length" , getParameter(request,"c_show_length","3"));
			//c_org_id
			appendTextElement( x_doc, rowset, "c_org_id" , getParameter(request,"c_org_id",""));
			appendTextElement( x_doc, rowset, "c_prj_org" , getParameter(request,"c_prj_org",""));

			//c_work_status
			appendTextElement( x_doc, rowset, "c_work_status" , getParameter(request,"c_work_status",""));
			//c_proj_status(forecast_status)
			appendTextElement( x_doc, rowset, "c_proj_status" , getParameter(request,"c_proj_status",""));
			//c_oppo_number
			appendTextElement( x_doc, rowset, "c_oppo_number" , getParameter(request,"c_oppo_number",""));
			//c_bunrui_code
			appendTextElement( x_doc, rowset, "c_bunrui_code" , getParameter(request,"c_bunrui_code",""));
			//c_category_id
			appendTextElement( x_doc, rowset, "c_category_id" , getParameter(request,"c_category_id",""));
			//c_pa_cc_id
			appendTextElement( x_doc, rowset, "c_pa_cc_id" , getParameter(request,"c_pa_cc_id",""));
			//c_proj_code
			appendTextElement( x_doc, rowset, "c_proj_code" , getParameter(request,"c_proj_code",""));
			//c_gsi_proj_code
			appendTextElement( x_doc, rowset, "c_gsi_proj_code" , getParameter(request,"c_gsi_proj_code",""));
			//c_proj_name
			appendTextElement( x_doc, rowset, "c_proj_name" , getParameter(request,"c_proj_name",""));
			// c_sales
			appendTextElement( x_doc, rowset, "c_sales" , getParameter(request,"c_sales",""));
			//c_proj_owner_name
			appendTextElement( x_doc, rowset, "c_proj_owner_name" , getParameter(request,"c_proj_owner_name",""));
			//c_proj_mgr_name
			appendTextElement( x_doc, rowset, "c_proj_mgr_name" , getParameter(request,"c_proj_mgr_name",""));
			//c_admi
			appendTextElement( x_doc, rowset, "c_admi" , getParameter(request,"c_admi",""));
			//c_end_user_name
			appendTextElement( x_doc, rowset, "c_end_user_name" , getParameter(request,"c_end_user_name",""));
			//c_client_name
			appendTextElement( x_doc, rowset, "c_client_name" , getParameter(request,"c_client_name",""));
			//c_billing_company
			appendTextElement( x_doc, rowset, "c_billing_company" , getParameter(request,"c_billing_company",""));
			//c_sales_org_id
			appendTextElement( x_doc, rowset, "c_sales_org_id" , getParameter(request,"c_sales_org_id",""));
			//c_sales_name
			appendTextElement( x_doc, rowset, "c_sales_name" , getParameter(request,"c_sales_name",""));
			//c_contract_id
			appendTextElement( x_doc, rowset, "c_contract_id" , getParameter(request,"c_contract_id",""));
			//c_classification
			appendTextElement( x_doc, rowset, "c_classification" , getParameter(request,"c_classification",""));
			//c_booking_org
			appendTextElement( x_doc, rowset, "c_booking_org" , getParameter(request,"c_booking_org",""));
			appendTextElement( x_doc, rowset, "c_fstatus_winprob" , getParameter(request,"c_fstatus_winprob","1"));

			appendTextElement( x_doc, rowset, "c_win_prob_check0" , getParameter(request,"c_win_prob_check0",""));
			appendTextElement( x_doc, rowset, "c_win_prob_check10" , getParameter(request,"c_win_prob_check10","true"));
			appendTextElement( x_doc, rowset, "c_win_prob_check20" , getParameter(request,"c_win_prob_check20","true"));
			appendTextElement( x_doc, rowset, "c_win_prob_check30" , getParameter(request,"c_win_prob_check30","true"));
			appendTextElement( x_doc, rowset, "c_win_prob_check40" , getParameter(request,"c_win_prob_check40","true"));
			appendTextElement( x_doc, rowset, "c_win_prob_check50" , getParameter(request,"c_win_prob_check50","true"));
			appendTextElement( x_doc, rowset, "c_win_prob_check60" , getParameter(request,"c_win_prob_check60","true"));
			appendTextElement( x_doc, rowset, "c_win_prob_check70" , getParameter(request,"c_win_prob_check70","true"));
			appendTextElement( x_doc, rowset, "c_win_prob_check80" , getParameter(request,"c_win_prob_check80","true"));
			appendTextElement( x_doc, rowset, "c_win_prob_check90" , getParameter(request,"c_win_prob_check90","true"));
			appendTextElement( x_doc, rowset, "c_win_prob_check100" , getParameter(request,"c_win_prob_check100","true"));

			appendTextElement( x_doc, rowset, "c_book_status_check1" , getParameter(request,"c_book_status_check1","true"));
			appendTextElement( x_doc, rowset, "c_book_status_check2" , getParameter(request,"c_book_status_check2","true"));
			appendTextElement( x_doc, rowset, "c_book_status_check3" , getParameter(request,"c_book_status_check3","true"));
			appendTextElement( x_doc, rowset, "c_book_status_check4" , getParameter(request,"c_book_status_check4","true"));
			appendTextElement( x_doc, rowset, "c_book_status_check5" , getParameter(request,"c_book_status_check5","true"));
			appendTextElement( x_doc, rowset, "c_book_status_check99" , getParameter(request,"c_book_status_check99",""));

			appendTextElement( x_doc, rowset, "c_rev_status_check1" , getParameter(request,"c_rev_status_check1","true"));
			appendTextElement( x_doc, rowset, "c_rev_status_check2" , getParameter(request,"c_rev_status_check2","true"));
			appendTextElement( x_doc, rowset, "c_rev_status_check3" , getParameter(request,"c_rev_status_check3","true"));
			appendTextElement( x_doc, rowset, "c_rev_status_check4" , getParameter(request,"c_rev_status_check4","true"));
			appendTextElement( x_doc, rowset, "c_rev_status_check5" , getParameter(request,"c_rev_status_check5","true"));
			appendTextElement( x_doc, rowset, "c_rev_status_check99" , getParameter(request,"c_rev_status_check99",""));

			//上部のチェックボックス
			appendTextElement( x_doc, rowset, "c_csv_check" , getParameter(request,"csv",""));
			appendTextElement( x_doc, rowset, "c_full_check" , getParameter(request,"isFull",""));
			appendTextElement( x_doc, rowset, "c_meisai_check" , getParameter(request,"cont_meisai_flg",""));
			appendTextElement( x_doc, rowset, "c_done_check" , getParameter(request,"noDone",""));
			appendTextElement( x_doc, rowset, "c_rev_check" , getParameter(request,"noRev",""));

			//c_sort1、デフォルトの値="Work Status"
			appendTextElement( x_doc, rowset, "c_sort1" , getParameter(request,"c_sort1", "4" ));
			//c_sort2、デフォルトの値="Revenue Status"
			appendTextElement( x_doc, rowset, "c_sort2" , getParameter(request,"c_sort2", "3" ));
			//c_sort3、デフォルトの値="契約形態"
			appendTextElement( x_doc, rowset, "c_sort3" , getParameter(request,"c_sort3", "15" ));

			//下部チェックボックス(表示項目選択)
			//Temp Number
			appendTextElement( x_doc, rowset, "o_proj_code" , getParameter(request,"o_proj_code","true"));
			//Project Number
			appendTextElement( x_doc, rowset, "o_gsi_proj_code" , getParameter(request,"o_gsi_proj_code","true"));
			// Opportunity Number
			appendTextElement( x_doc, rowset, "o_oppo_number" , getParameter(request,"o_oppo_number","true"));
			//Project Name
			appendTextElement( x_doc, rowset, "o_proj_name" , getParameter(request,"o_proj_name","true"));
			//契約先名
			appendTextElement( x_doc, rowset, "o_client_name" , getParameter(request,"o_client_name","true"));
			//業務委託先
			appendTextElement( x_doc, rowset, "o_ex_cont" , getParameter(request,"o_ex_cont","true"));
			//REASON_FOR_CHANGE
			appendTextElement( x_doc, rowset, "o_reason_for_change" , getParameter(request,"o_reason_for_change",""));
			//エンドユーザー名
			appendTextElement( x_doc, rowset, "o_end_user_name" , getParameter(request,"o_end_user_name","true"));
			appendTextElement( x_doc, rowset, "o_eu_official_name" , getParameter(request,"o_eu_official_name",""));
			// KEYアカウント
			appendTextElement( x_doc, rowset, "o_key_acnt", getParameter(request, "o_key_acnt",""));
			//プロジェクトオーナー
			appendTextElement( x_doc, rowset, "o_proj_owner_name" , getParameter(request,"o_proj_owner_name","true"));
			//プロジェクトマネージャー
			appendTextElement( x_doc, rowset, "o_proj_mgr_name" , getParameter(request,"o_proj_mgr_name","true"));
				//CLASSIFICATION
			appendTextElement( x_doc, rowset, "o_classification" , getParameter(request,"o_classification",""));
			//Booking管理組織
			appendTextElement( x_doc, rowset, "o_booking_org" , getParameter(request,"o_booking_org",""));
			//CLASSIFICATION_4
			appendTextElement( x_doc, rowset, "o_classification_4" , getParameter(request,"o_classification_4",""));
			appendTextElement( x_doc, rowset, "o_project_total" , getParameter(request,"o_project_total",""));
			appendTextElement( x_doc, rowset, "o_sa_info" , getParameter(request,"o_sa_info",""));
			appendTextElement( x_doc, rowset, "o_service_classification", getParameter(request, "o_service_classification", ""));
			//Forecast Status
			appendTextElement( x_doc, rowset, "o_book_status" , getParameter(request,"o_book_status","true"));
			//Forecast Status
			appendTextElement( x_doc, rowset, "o_rev_status" , getParameter(request,"o_rev_status","true"));
			//Work Status
			appendTextElement( x_doc, rowset, "o_work_status" , getParameter(request,"o_work_status","true"));
			// 20190712 zhangt update start Forecast検索の表示列選択でMP組織をデフォルトチェックなしにする
			//組織名
			appendTextElement( x_doc, rowset, "o_org_name" , getParameter(request,"o_org_name",""));
			// 20190712 zhangt update end Forecast検索の表示列選択でMP組織をデフォルトチェックなしにする
			//PAコストセンタID
			appendTextElement( x_doc, rowset, "o_pa_cc_id" , getParameter(request,"o_pa_cc_id",""));
			//担当アドミ
			appendTextElement( x_doc, rowset, "o_admi" , getParameter(request,"o_admi",""));
			appendTextElement( x_doc, rowset, "c_admi_isnull", getParameter(request,"c_admi_isnull",""));
			appendTextElement( x_doc, rowset, "o_latest_mporg" , getParameter(request,"o_latest_mporg",""));

			//SKUフラグ
			appendTextElement( x_doc, rowset, "skuOnly", getParameter(request,"skuOnly",""));

			//PRD組織
			appendTextElement( x_doc, rowset, "o_prd_org_name" , getParameter(request,"o_prd_org_name",""));
			//FPAutoフラグ
			appendTextElement( x_doc, rowset, "o_fpa" , getParameter(request,"o_fpa",""));
			//担当営業部
			appendTextElement( x_doc, rowset, "o_sales_org_name" , getParameter(request,"o_sales_org_name",""));
			//担当営業
			appendTextElement( x_doc, rowset, "o_sales_name" , getParameter(request,"o_sales_name",""));
			//契約締結予定日
			appendTextElement( x_doc, rowset, "o_contract_date" , getParameter(request,"o_contract_date","true"));
			// 締結QTR
			appendTextElement( x_doc, rowset, "o_contract_quarter" , getParameter(request,"o_contract_quarter",""));
			// 案件登録日
			appendTextElement( x_doc, rowset, "o_opty_creation_date", getParameter(request, "o_opty_creation_date", ""));
			//WinProb
			appendTextElement( x_doc, rowset, "o_win_prob" , getParameter(request,"o_win_prob","true"));
			// Sales
			appendTextElement( x_doc, rowset, "o_sales" , getParameter(request,"o_sales","true"));
			// bid
			appendTextElement( x_doc, rowset, "o_bid" , getParameter(request,"o_bid",""));
			// Progress
			appendTextElement( x_doc, rowset, "o_oppo_status" , getParameter(request,"o_oppo_status",""));
			// Progress
			appendTextElement( x_doc, rowset, "o_progress" , getParameter(request,"o_progress","true"));
			// Module
			appendTextElement( x_doc, rowset, "o_module" , getParameter(request,"o_module",""));
			// ConsCategory
			appendTextElement(x_doc, rowset, "o_cons_category", getParameter(request, "o_cons_category","true"));
			// ProductName
			appendTextElement( x_doc, rowset, "o_product_name" , getParameter(request,"o_product_name",""));
			// 計上区分
			appendTextElement( x_doc, rowset, "o_cons_category", getParameter(request, "o_cons_category"));
			// インダストリ
			appendTextElement( x_doc, rowset, "o_industry" , getParameter(request,"o_industry",""));
			// 分類
			appendTextElement( x_doc, rowset, "o_bunrui" , getParameter(request,"o_bunrui","true"));
				//契約金額
			appendTextElement( x_doc, rowset, "o_cont_amount" , getParameter(request,"o_cont_amount",""));
			//契約金額
			appendTextElement( x_doc, rowset, "o_own_cont_amount" , getParameter(request,"o_own_cont_amount","true"));
				//契約形態
			appendTextElement( x_doc, rowset, "o_contract_type" , getParameter(request,"o_contract_type","true"));
			//プロジェクト開始日
			appendTextElement( x_doc, rowset, "o_proj_start" , getParameter(request,"o_proj_start",""));
			//プロジェクト終了日
			appendTextElement( x_doc, rowset, "o_proj_end" , getParameter(request,"o_proj_end",""));
				//UpdateButton
			appendTextElement( x_doc, rowset, "o_update_button" , getParameter(request,"o_update_button","true"));
				//予算合計
			appendTextElement( x_doc, rowset, "o_sum_budget" , getParameter(request,"o_sum_budget",""));
			appendTextElement( x_doc, rowset, "o_consul_type" , getParameter(request,"o_consul_type",""));
			appendTextElement( x_doc, rowset, "o_revenue_type" , getParameter(request,"o_revenue_type",""));
			// Booking集計対象外
			appendTextElement( x_doc, rowset, "o_not_book_sum" , getParameter(request, "o_not_book_sum",""));
			// LIC. OPPO ID
			appendTextElement( x_doc, rowset, "o_lic_oppo_id", getParameter(request, "o_lic_oppo_id"));
			// 業務委託ID
			appendTextElement( x_doc, rowset, "o_ex_cont_id", getParameter(request, "o_ex_cont_id"));
			// ContractNumber
			appendTextElement( x_doc, rowset, "o_contract_number", getParameter(request, "o_contract_number","true"));

			// SKU番号
			appendTextElement( x_doc, rowset, "o_sku_no", getParameter(request, "o_sku_no","true"));
			// SKU期間
			appendTextElement( x_doc, rowset, "o_sku_span", getParameter(request, "o_sku_span",""));
			// SKU_REV_REC_RULE
			appendTextElement( x_doc, rowset, "o_sku_rev_rec_rule", getParameter(request, "o_sku_rev_rec_rule",""));
			// to_v_list
			appendTextElement( x_doc, rowset, "to_v_list" , getParameter(request,"to_v_list",""));

			// fy12用インダストリーカット表示
			appendTextElement( x_doc, rowset, "fy12_flg" , getParameter(request,"fy12_flg",""));


		}
	}


}
