////////////////////////////////////////////////////////////////////////////////
//
//
// CRLFeate 2002-05-20
//
////////////////////////////////////////////////////////////////////////////////
// 更新履歴
//  Ver.     日付                      担当者                            変更内容
//  1.0      2002/05/20    坪内 章太郎                     新規作成
//  1.1      2003/01/07    Masayoshi.Takemura ReqNoが同じものをまとめる
//  1.2      2004/03/17    Masayoshi.Takemura iProの検収期間の都合のため業務委託だけは2ヶ月前以上でも入力できるように修正
//           2013/10/07    Mari.Suzuki        実績値の入力ロック対応
//           2016/02/15    Mari.Suzuki        表示期間18ヶ月対応
////////////////////////////////////////////////////////////////////////////////
package contents;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

// XML classes
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 社員一覧の詳細
 *
 *
 *
 */
public class Forecast_Detail extends ContentsBase
{

	/**
	 * クエリー取得メソッド.
	 *
	 */
	protected Hashtable getQuery( HttpServletRequest request )
	{
		Hashtable querys = new Hashtable();

		querys.put("FORECAST_DETAIL",getQuery_ForecastDetail(request));
		querys.put("SHOW_MONTH",getQuery_ShowMonth(request));
		querys.put("SHOW_ORG", getQueryShowOrg(request));
		querys.put("ACCESS_LEVEL",getQuery_AccessLevel(request));
		querys.put("TITLE",getQuery_Title(request));
		querys.put("DATE",getQuery_Date(request));
		querys.put("INPUT_FORECAST_FLG", getQueryInputForecastFlg(request));

		return querys ;
	}


	/**
	 * ForecastDetail SQL
	 *
	 * @param proj_code    プロジェクトコード
	 * @param ex_cont_id   業務委託コード
	 * @param fy           FY
	 * @param start_month  表示開始
	 * @param show_length  表示期間
	 */
	private String getQuery_ForecastDetail( HttpServletRequest request )
	{
		//TEMP Number
		String proj_code = getParameter(request,"PROJ_CODE","");
		//業務委託ID
		String ex_cont_id = getParameter(request,"EX_CONT_ID","");

		//フォーマット
		DecimalFormat YYYY = new DecimalFormat("0000");
		DecimalFormat MM = new DecimalFormat("00");

		//SELECT
		StringBuffer select = new StringBuffer
		(
			"SELECT to_char(f.target_month,'yyyymmdd') TARGET_MONTH"
				+", f.last_forecast FORECAST"												//Forecast
				+", f.commit_flag COMMIT_FLAG"												//コミットフラグ
				+", f.forecast ACTUAL"														//実績
				+", e.emp_name UPDATE_PERSON"												//更新者
				+", TO_CHAR(f.update_date, 'YYYY/MM/DD') UPDATE_DATE"						//更新日
		);

		//FROM
		StringBuffer from = new StringBuffer
		(
			"\n FROM project_list pr, forecast f, emp_list e"
		);

		//WHERE
		StringBuffer where = new StringBuffer
		(
			"\n WHERE pr.proj_code = '" + proj_code + "'"
			+"\n AND f.update_person = e.emp_id(+)"
			+"\n AND f.update_date(+) > to_date('00010101','yyyymmdd')"
		);


		//fy
		int target_year = Integer.parseInt(request.getParameter("FY"));
		//表示月
		int target_month = Integer.parseInt(request.getParameter("START_MONTH"));
		//表示期間
		int show_length = Integer.parseInt(request.getParameter("SHOW_LENGTH"));

		int forecastYear = target_year;
		int forecastMonth = target_month;
		where.append("\n\t and (");
		for (int i=0; i < show_length; i++)
		{
			if (12 < forecastMonth)
			{
				forecastMonth = forecastMonth - 12;
			}
			if (0 < i && forecastMonth == 6)
			{
				forecastYear = forecastYear + 1;
			}
			if (0 < i)
			{
				where.append("\n\t or ");
			}
			where.append("f.target_month = to_date('");
			where.append(YYYY.format(forecastYear));
			where.append(MM.format(forecastMonth));
			where.append("01', 'YYYYMMDD')");
			forecastMonth++;
		}
		where.append(")");

		//PROJECTの場合
		if ( ex_cont_id.equals("") ){
			where.append( CRLF+TAB + "AND f.proj_code(+) = pr.proj_code" );
			where.append( CRLF + "ORDER BY f.target_month" );
		}
		//業務委託の場合
		else{
			select.append( ","+CRLF+TAB + "ex.reference_no" );
			select.append( ","+CRLF+TAB + "f.req_line_no" );
			from.append( ","+CRLF+TAB + "ex_contract_list ex" );
			where.append( CRLF+TAB + "AND pr.proj_code = ex.proj_code" );
			where.append( CRLF+TAB + "AND ex.ex_cont_id = '" + ex_cont_id + "'");
			where.append( CRLF+TAB + "AND f.proj_code(+) = ex.ex_cont_id" );
			where.append( CRLF + "ORDER BY f.req_line_no" );
		}

		String sql = select.toString() + from.toString() + where.toString();


		return sql;
	}


	/**
	 * ShowMonth SQL
	 *
	 *
	 */
	private String getQuery_ShowMonth( HttpServletRequest request )
	{
		//フォーマット
		DecimalFormat MM = new DecimalFormat("00");

		//fy
		int target_year = Integer.parseInt(request.getParameter("FY"));
		//表示月
		int target_month = Integer.parseInt(request.getParameter("START_MONTH"));
		//表示期間
		int show_length = Integer.parseInt(request.getParameter("SHOW_LENGTH"));

		StringBuffer sql = new StringBuffer("SELECT null");

		int fy_year = target_year;
		int real_year;
		//現実年
		if(target_month >= 6){
			real_year = target_year - 1;
		}else{
			real_year = target_year;
		}

		int month = target_month;
		for(int i=0; i < show_length; i++)
		{
			//13になったら現実年1増加
			if(month == 13)
			{
				real_year++;
			}
			//1月に戻す
			if(month > 12)
			{
				month = month - 12;
			}
			//6になったらFY1増加
			if(i != 0 && month == 6)
			{
				fy_year++;
			}
			sql.append("\n,TRIM(to_char('" + real_year + MM.format(month) + "01')) REAL_DATE");
			sql.append("\n,TRIM(to_char('" + fy_year + MM.format(month) + "01')) FY_DATE");
			month++;
		}
		sql.append("\n FROM dual");

		return sql.toString();
	}

	/**
	 * showOrg SQL
	 *
	 *
	 */
	private String getQueryShowOrg( HttpServletRequest request )
	{
		//フォーマット
		DecimalFormat MM = new DecimalFormat("00");
		int target_year = Integer.parseInt(request.getParameter("FY"));	// fy
		int target_month = Integer.parseInt(request.getParameter("START_MONTH"));	// 表示月
		int show_length = Integer.parseInt(request.getParameter("SHOW_LENGTH"));	// 表示期間

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT CAL.FY_DATE, decode(SUBSTR(PORG.CC_ORG_ID,1,6),'CC0000',SUBSTR(PORG.CC_ORG_ID,7,4),SUBSTR(PORG.CC_ORG_ID,5,6)) CC_ORG_ID, PDORG.ORG_NAME_SHORT");
		sql.append("\n FROM   PROJ_ORG_LIST PORG, PRODUCT_ORG_LIST PDORG");
		sql.append("\n ,(");

		int real_year;
		//現実年
		if(target_month >= 6){
			real_year = target_year - 1;
		}else{
			real_year = target_year;
		}

		int month = target_month;
		for(int cnt=0; cnt < show_length; cnt++)
		{
			//13になったら現実年1増加
			if(month == 13)
			{
				real_year ++;
			}
			//1月に戻す
			if(month > 12)
			{
				month = month - 12;
			}
			if(cnt > 0)
			{
				sql.append("\n UNION ");
			}
			sql.append("\n SELECT ");
			sql.append(cnt);
			sql.append(" SORT_NUM, TO_DATE('");
			sql.append(real_year);
			sql.append(MM.format(month));
			sql.append("01') FY_DATE FROM DUAL");
			month++;
		}
		sql.append("\n) CAL");
		sql.append("\n WHERE  PORG.PROJ_CODE = '");
		sql.append(getParameter(request,"PROJ_CODE",""));
		sql.append("'");
		sql.append("\n AND    CAL.FY_DATE BETWEEN PORG.START_DATE AND NVL(PORG.END_DATE,TO_DATE('29991231','YYYYMMDD'))");
		sql.append("\n AND    PORG.PROD_ORG_ID = PDORG.ORG_ID(+)");
		sql.append("\n ORDER BY CAL.SORT_NUM ");

		return sql.toString();
	}

	/**
	 * AccessLevel SQL
	 *
	 *
	 */
	private String getQuery_AccessLevel( HttpServletRequest request )
	{
		//職責(更新権限チェック)
		String sql = "SELECT"
			+CRLF+TAB + "f.access_level FORECAST"
		+","+CRLF+TAB + "a.access_level ACTUAL"
			+CRLF + "FROM"
			+CRLF+TAB + "menu_auth_list f"
		+","+CRLF+TAB + "menu_auth_list a"
			+CRLF + "WHERE"
			+CRLF+TAB+TAB + "a.auth_code(+) = f.auth_code"
			+CRLF+TAB + "AND f.auth_code = " + getCookie("AUTH_CODE")
			+CRLF+TAB + "AND a.menu_code(+) = 20303"
			+CRLF+TAB + "AND f.menu_code = 20302"
		;

		return sql;
	}


	/**
	 * Title SQL
	 *
	 *
	 */
	private String getQuery_Title( HttpServletRequest request )
	{
		//TEMP Number
		String proj_code = getParameter(request,"PROJ_CODE","");
		//業務委託ID
		String ex_cont_id = getParameter(request,"EX_CONT_ID","");

		String sql = "";

		//PROJECTの場合
		if ( ex_cont_id.equals("") )
		{
			sql = "SELECT proj_code TARGET_CODE,proj_name TARGET_NAME,trim('forecast') which"

				+CRLF+TAB + ",to_char(cons_start,'yyyymmdd') cons_start"
				+CRLF+TAB + ",(to_char(add_months(cons_end,1),'yyyymm' ) || '01') cons_end"
				+CRLF+TAB + "FROM project_list_v"
				+CRLF+TAB + "WHERE proj_code = '" + proj_code + "'";
		}
		//業務委託の場合
		else
		{
			sql = "SELECT e.ex_cont_id TARGET_CODE,e.ex_cont_name TARGET_NAME,trim('ex_cont') which"

				+CRLF+TAB + ",to_char(pr.cons_start,'yyyymmdd') cons_start"
				+CRLF+TAB + ",(to_char(add_months(pr.cons_end,1),'yyyymm' ) || '01') cons_end"
				+CRLF+TAB + "FROM ex_contract_list e,project_list_v pr"
				+CRLF+TAB + "WHERE e.ex_cont_id = '" + ex_cont_id + "'"
				+CRLF+TAB + "AND e.proj_code = pr.proj_code";
		}

		return sql;
	}


	/**
	 * (現在年月日-1ヶ月)SQL
	 * 入力の許可を1ヶ月前までさせるため
	 *
	 */
	private String getQuery_Date( HttpServletRequest request )
	{
		String sql =
				"SELECT"
					+ " to_char(add_months(sysdate,-1),'yyyymmdd') MONTH_AGO"
				+ " FROM"
					+ " dual";
		return sql;
	}

	/**
	 * 実績値入力フラグ取得SQL
	 */
	private String getQueryInputForecastFlg(HttpServletRequest request)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT VALUE");
		sql.append("\n FROM CONST_LIST");
		sql.append("\n WHERE NAME = 'INPUT_FORECAST_FLG'");

		return sql.toString();

	}


	/**
	 * スタイルシートURL取得メソッド.
	 *
	 *
	 */
	protected URL getXSLURL( HttpServletRequest request )
		throws Exception
	{
		return getClass().getResource( "/xsl/Forecast_Detail.xsl" ) ;
	}


	/**
	 * メニューコード取得メソッド.
	 *
	 * @return メニューコード
	 */
	protected String getMenuCode( HttpServletRequest request )
	{
		// Forecast詳細
		return "20301";
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
			appendTextElement( x_doc, rowset, "start_month", request.getParameter("START_MONTH") );
			appendTextElement( x_doc, rowset, "show_length", request.getParameter("SHOW_LENGTH") );
		}
	}
}


