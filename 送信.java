/*
 * contents.project.registRequest.PjrsMailSend.java 2005/6/17
 *
 *  更新履歴
 *  Ver.     日付          担当者         変更内容
 *  1.0      2005/06/17    指村 敦子      新規作成
 *           2005/12/12    指村 敦子      fromアドレスを起票者にする
 *           2014/03/18    鈴木 麻里      システム概要メニューSaaS追加
 *           2017/03/03    鈴木 麻里      システム概要メニューCloud/On Premise Deal追加
 */
package contents.project.registRequest;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import contents.ContentsBase;
import contents.ContentsException;
import contents.Mail_Configure;
/**
 * PJRMail送信クラス
 *
 * メールを送信して、完了画面を表示します。
 *
 * @author Atsuko Sashimura
 */
public class PjrMailSend extends ContentsBase
{

	private static final String subject = "プロジェクト登録依頼";
	private static final String beforeCmnt = "さんからプロジェクトの登録依頼です。"
		+CRLF + "PFMへ、以下の情報を登録してください。";
	private static final String afterCmnt = CRLF + CRLF +"以上 ";

	public void execute(HttpServletRequest request,HttpServletResponse response,Connection con) throws Exception
	{
		if (DEBUG)
		{
			log("PJRMailSend: execute");
		}

		try
		{
			// PM,PO,入力者のアドレスを取得する
			String addToAddress = getPmMailAddress(request, con) + ","
				+ getPSMailAddress(request, con)  + ','
				 + getUserMailAddress(request,con);

			// コンテンツの作成
			String content = getContent(request, con);

			Mail_Configure mail = new Mail_Configure(con,request);
			mail.setAddToAddress(addToAddress);
			mail.setSubject(subject+"/"+getParameter(request,"c_opo_no","")+"/"+getParameter(request,"c_client_name",""));
			mail.setContent(content);
			mail.setMailCase("プロジェクト管理-代行登録依頼");
			mail.setFromAddress(getUserMailAddress(request,con));
			mail.send();
			if (DEBUG)
			{
				log("PJRMailSend execute:OK");
			}
		}
		catch(ContentsException ce)
		{
			log("PJRMailSend execute:NG");
			throw ce;
		}
		catch(Exception e)
		{
			log("PJRMailSend execute:NG");
			throw new ContentsException(ERROR_MAIL_MSG, e);
		}

		super.execute(request,response,con);
	}

	/**
	 * 送信本文の文字列を組み立てる
	 *
	 * @param request
	 * @return 送信contents文字列
	 */
	private String getContent(HttpServletRequest request, Connection con) throws  SQLException,ContentsException
	{

		// 文字列の作成
		StringBuffer content = new StringBuffer();

		content.append(CRLF + CRLF + getUserName(request, con) + beforeCmnt + CRLF + CRLF);
		content.append("新規/継続 = " + getParameter(request,"c_continue_type","") + "," + CRLF);
		content.append("Opportunity No = " + getParameter(request,"c_opo_no","") + "," + CRLF);
		content.append("Deal ID = " + getParameter(request,"c_deal_id","") + "," + CRLF);
		content.append("ProjectName(正式) = " + getParameter(request,"c_proj_name_seishiki","") + "," + CRLF);
		content.append("ProjectName(略) = " + getParameter(request,"c_proj_name_ryaku","") + "," + CRLF);
		content.append("プロジェクト番号(継続の場合)  = " + getParameter(request,"c_project_number","") + "," + CRLF);
		content.append("エンドユーザ名(正式社名) = " + getParameter(request,"c_end_user_name","") + "," + CRLF);
		content.append("契約先社名(正式社名) = " + getParameter(request,"c_client_name","") + "," + CRLF);
		content.append("請求書送付先郵便番号 = ");
		String zipCode1 = getParameter(request,"c_billing_zip_code1");
		if(zipCode1 != null && !zipCode1.equals("")){
			content.append(zipCode1 + " - " + getParameter(request, "c_billing_zip_code2"));
		}
		content.append("," + CRLF);
		content.append("請求書送付先住所 = " + getParameter(request,"c_billing_address","") + "," + CRLF);
		content.append("請求書送付先担当者 = " + getParameter(request,"c_billing_person","") + "," + CRLF);
		content.append("プロジェクト組織 = " + getParameter(request,"c_cost_center","") + "," + CRLF);
		content.append("契約金額 = " + getParameter(request,"c_contract_amount","") + "," + CRLF);
		content.append("契約形態 = " + getParameter(request,"c_contract_type","") + "," + CRLF);
		content.append("Resellable assets = " + getParameter(request,"c_fpauto","") + "," + CRLF);
//		content.append("Bidマネージャ = " + getParameter(request,"c_bid_mgr_name","") + "," + CRLF);
		content.append("Bidマネージャ = " + getParameter(request,"c_bid_mgr_id","") + HALF_SPC + getParameter(request,"c_bid_mgr_name","") + "," + CRLF);
//		content.append("Primary Sales/Credit receiver = " + getParameter(request,"c_primary_sales_name","") + "," + CRLF);
		content.append("Primary Sales/Credit receiver = " + getParameter(request,"c_primary_sales_id","") + HALF_SPC + getParameter(request,"c_primary_sales_name","") + "," + CRLF);
//		content.append("プロジェクトマネージャ = " + getParameter(request,"c_pm_name","") + "," + CRLF);
		content.append("プロジェクトマネージャ = " + getParameter(request,"c_pm_id","") + HALF_SPC + getParameter(request,"c_pm_name","") + "," + CRLF);
		String probability = getParameter(request,"c_probability","");
		content.append("Probability% = " + probability);
		content.append("," + CRLF);
		content.append("ワークステータス = " + getParameter(request,"c_work_status","") + "," + CRLF);
		content.append("契約締結予定日 = " + getParameter(request,"c_contract_date","") + "," + CRLF);
		content.append("プロジェクト期間 = ");
		String projStartDate = getParameter(request,"c_proj_start_date");
		if(projStartDate != null && !projStartDate.equals("")){
			content.append(projStartDate + " - " + getParameter(request, "c_proj_end_date"));
		}
		content.append(CRLF + CRLF);

		// その他特記事項
		String comment = getParameter(request,"c_comment");
		if(comment != null && !comment.equals("")){
			content.append("[その他特記事項]" + CRLF + comment + CRLF + CRLF);
		}

		// システム概要メニュー
		content.append("[システム概要メニュー]" + CRLF);
		content.append("Project Classification Level = " + getParameter(request,"c_proj_class_lev","") + "," + CRLF);
		content.append("Deployment Model = " + getParameter(request,"c_deploy_model","") + "," + CRLF);
		content.append("Implementation Method  = " + getParameter(request,"c_implement_method","") + "," + CRLF);
		content.append("Cloud/On Premise Deal = " + getParameter(request, "c_cloud_onpremise_deal", "") + CRLF);


		if(getParameter(request, "c_free_message") != null){
			content.append(CRLF + CRLF + "[フリーメッセージ]");
			content.append(CRLF + getParameter(request, "c_free_message", ""));
		}
		content.append(CRLF + CRLF + afterCmnt);

		return content.toString();
	}

	/**
	 * 入力者の名前を取得する
	 *
	 * @param value
	 * @return sql文字列
	 */
	protected String getUserName(HttpServletRequest request, Connection con) throws  SQLException,ContentsException{
		String userId = getCookie("USERID");
		String sql = "SELECT e.emp_name"
			+ "\n FROM emp_list e"
			+ "\n WHERE e.emp_id = " + userId;
		String userName = doSelect("emp_name", sql, con);
		return userName;
//		return userId + HALF_SPC + userName;
	}

	/**
	 * 入力者のメールアドレスを取得する
	 *
	 * @param value
	 * @return sql文字列
	 */
	protected String getUserMailAddress(HttpServletRequest request, Connection con) throws  SQLException,ContentsException{
		String sql = "SELECT e.mail_address || '@' || c.value mail_address"
			+ "\n FROM emp_list e, const_list c"
			+ "\n WHERE e.emp_id = " + getCookie("USERID")
			+ "\n AND c.name = 'MAIL_DOMAIN'"
			+ "\n AND c.end_date is null";
		return doSelect("mail_address", sql, con);
	}

	/**
	 * Primary Salesのメールアドレスを取得する
	 *
	 * @param value
	 * @return sql文字列
	 */
	protected String getPSMailAddress(HttpServletRequest request, Connection con) throws  SQLException,ContentsException{
		String sql = "SELECT e.mail_address || '@' || c.value mail_address"
			+ "\n FROM emp_list e, const_list c"
			+ "\n WHERE e.emp_id = " + request.getParameter("c_primary_sales_id")
			+ "\n AND c.name = 'MAIL_DOMAIN'"
			+ "\n AND c.end_date is null";
		return doSelect("mail_address", sql, con);
	}

	/**
	 * PMのメールアドレスを取得する
	 *
	 * @param value
	 * @return sql文字列
	 */
	protected String getPmMailAddress(HttpServletRequest request, Connection con) throws SQLException,ContentsException{
		String sql = "SELECT e.mail_address || '@' || c.value mail_address"
			+ "\n FROM emp_list e, const_list c"
			+ "\n WHERE e.emp_id = " + request.getParameter("c_pm_id")
			+ "\n AND c.name = 'MAIL_DOMAIN'"
			+ "\n AND c.end_date is null";
		return doSelect("mail_address", sql, con);
	}

	/**
	 * 引数sqlで渡されたSQLSELECT文を実行する。
	 * SELECTの結果は1件だけ返ってくることを想定している。
	 * 結果からitemNameのカラムを取得しreturnする。
	 *
	 * @param itemName
	 * @param sql
	 * @param con
	 * @return
	 * @throws Exception
	 */
	private String doSelect(String itemName, String sql, Connection con) throws SQLException,ContentsException{
		Statement stmt = null;
		String rsltName = null;
		if(DEBUG){
			log("ProjectRegisterRequest SELECT SQL:" + CRLF + sql);
		}
		try{
			stmt = con.createStatement();
			ResultSet rsltSet = stmt.executeQuery(sql);
			while(rsltSet.next()){
				rsltName = rsltSet.getString(itemName);
			}
		}catch(Exception e){
			con.rollback();
			throw new ContentsException(ERROR_SELECT_MSG, e);
		} finally {
			con.commit();
			stmt.close();
		}

		return rsltName;
	}

	/**
	 * クエリー取得メソッド.
	 *
	 *
	 */
	protected Hashtable getQuery( HttpServletRequest request )
	{
		Hashtable querys = new Hashtable();
		return querys ;
	}

	/**
	 * スタイルシートURL取得メソッド.
	 *
	 *
	 */
	protected URL getXSLURL( HttpServletRequest request )
		throws Exception
	{
		return getClass().getResource( "/xsl/project/registRequest/PjrMailSend.xsl" ) ;
	}


	/**
	 * メニューコード取得メソッド.
	 *
	 * @return メニューコード
	 */
	protected String getMenuCode( HttpServletRequest request )
	{
		// PRR_PRJ登録依頼
		return "10402";
	}


	/**
	 * DOM取得後処理メソッド.
	 *
	 *
	 */
	protected void actionAfterGetDOM( String key , Element rowset , Document x_doc , HttpServletRequest request)
		throws Exception
	{

	}
}


