////////////////////////////////////////////////////////////////////////////////
//
//
// create 2002-03-29
//
////////////////////////////////////////////////////////////////////////////////
// 更新履歴
//  Ver.     日付          担当者          変更内容
//  1.0      2002/03/29    竹村正義        新規作成
//           2005/10/31    指村敦子　　　　メール送信時に文字コードを指定するなど変更
//           2005/12/09    指村敦子        メールfromを設定できるように変更
// C00036    2007/08/27    指村敦子        メールサーバ変更対応
////////////////////////////////////////////////////////////////////////////////
package contents;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

/**
 * メール送信用項目取得クラス
 * メールの送信に必要な項目を取得するクラスです。
 *
 */
public class Mail_Configure extends Mail
{

	String localhost;	//ローカルホストアドレス
	int port;			//ポート
	String subject;		//題名
	String content;		//本文
	String domain;		//メールドメイン
	String ccAddress;	//CC
	String addAddress = "";	//追加Addアドレス
	String fromAddress = "";	//fromアドレス
	String kankyo = "";
	String testAdrs = "";
	String mailCase;	//メールを送信するケース(Mail_Case_List)
	String authMailAddress = "";
	String authMailPassword = "";

	//DB用
	Connection con;
	HttpServletRequest request;
	Statement stmt;
	public Mail_Configure(Connection con,HttpServletRequest request) throws MailSendException,Exception
	{
		this.con = con;
		this.request = request;
		this.stmt = con.createStatement();

		this.localhost = InetAddress.getLocalHost().getHostName();
		this.port = 465;
		this.ccAddress = "";

		//メールドメイン取得用
		String _sql = "SELECT"
						+ " value domain_name"
					+ " FROM"
						+ " const_list"
					+ " WHERE"
						+ " name = 'MAIL_DOMAIN'";

		ResultSet rs = stmt.executeQuery(_sql);

		rs.next();
		domain = "@" + rs.getString("domain_name");

		if(domain.equals("@")) throw new MailSendException("メールドメインがありません");
		this.kankyo = setKankyo(con);
		this.testAdrs = setTestAdrs(con);
		this.authMailAddress = setAuthMailAddress(con);
		this.authMailPassword = setAuthMailPassword(con);
		rs.close();
	}

	/**
	 * 項目取得メソッド.
	 *
	 * SMTPサーバ
	 *
	 */
	protected String getSmtp() throws MailSendException,Exception
	{
		return "stbeehive.oracle.com";
	}

	/**
	 * 項目取得メソッド.
	 *
	 * ポート番号
	 *
	 */
	protected int getPort() throws MailSendException,Exception
	{
		return port;
	}

	/**
	 * 項目取得メソッド.
	 *
	 * 送信元サーバ
	 *
	 */
	protected String getHelo() throws MailSendException,Exception
	{
		return localhost;
	}

	/**
	 * 項目取得メソッド.
	 *
	 * 送信元アドレス
	 *
	 */
	protected String getFromAddress() throws MailSendException,Exception
	{

		if(this.fromAddress == null || this.fromAddress.equals("")){

			//送信元アドレス取得用
			String sql = "SELECT"
						+ " mail_address"
					+ " FROM"
						+ " emp_list"
						+ ",mail_recipient_list"
						+ ",mail_case_list"
					+ " WHERE"
						+ " emp_list.emp_id = mail_recipient_list.emp_id"
						+ " and mail_case_list.mail_case_id = mail_recipient_list.mail_case_id"
						+ " and mail_case_list.mail_case_name = 'システム管理者'";

			ResultSet rs = stmt.executeQuery(sql);

			rs.next();
			this.fromAddress = rs.getString("mail_address") + domain;

			if(this.fromAddress.equals("")) throw new MailSendException("送信元メールアドレスがありません");

			rs.close();
		}
		return this.fromAddress;
	}

	/**
	 * セッター.
	 *
	 * 送信元アドレス
	 *
	 */
	public void setFromAddress(String fromAddress){
		this.fromAddress = fromAddress;
	}

	/**
	 * 項目取得メソッド.
	 *
	 * 送信先アドレス
	 *
	 */
	protected String getToAddress() throws SQLException, MailSendException
	{
		String mailAddress = "";

		//送信アドレス取得用
		String sql = "SELECT"
						+ " mail_address"
					+ " FROM"
						+ " emp_list"
						+ ",mail_recipient_list"
						+ ",mail_case_list"
					+ " WHERE"
						+ " emp_list.emp_id = mail_recipient_list.emp_id"
						+ " and mail_case_list.mail_case_id = mail_recipient_list.mail_case_id"
						+ " and mail_case_list.mail_case_name = '" + mailCase + "'";

		ResultSet rs = stmt.executeQuery(sql);

		while(rs.next())
		{
			if(mailAddress.equals(""))
			{
				mailAddress = mailAddress + rs.getString("mail_address") + domain;
			}
			else
			{
				mailAddress = mailAddress + "," + rs.getString("mail_address") + domain;
			}
		}

		// 追加送信アドレス取得用
		mailAddress = mailAddress + addAddress;

		if(mailAddress.equals("")) throw new MailSendException("送信メールアドレスがありません");

		rs.close();

		return mailAddress;
	}

	/**
	 * 項目取得メソッド.
	 *
	 * 送信先アドレス
	 *
	 */
	public String getAddToAddress()
	{
		return addAddress;
	}


	/**
	 * 項目取得メソッド.
	 *
	 * 送信先アドレス
	 *
	 */
	public void setAddToAddress(String addAddress)
	{
		if(!addAddress.equals("") && addAddress != null){
			addAddress = "," + addAddress;
		}
		this.addAddress = addAddress;
	}

	/**
	 * 項目取得メソッド.
	 *
	 * CCアドレス
	 *
	 */
	protected String getCcAddress() throws MailSendException,Exception
	{
		return ccAddress;
	}

	/**
	 * セッター.
	 *
	 * CCアドレス
	 *
	 */
	public void setCcAddress(String ccAddress) throws MailSendException,Exception
	{
		this.ccAddress = ccAddress;
	}

	/**
	 * 項目取得メソッド.
	 *
	 * 題名
	 *
	 */
	protected String getSubject() throws MailSendException,Exception
	{
		return subject;
	}

	/**
	 * セッター.
	 *
	 * 題名
	 *
	 */
	public void setSubject(String subject) throws MailSendException,Exception
	{
		this.subject = subject;
	}

	/**
	 * 項目取得メソッド.
	 *
	 * 内容
	 *
	 */
	protected String getContent() throws MailSendException,Exception
	{
		return content;
	}

	/**
	 * セッター.
	 *
	 * 題名
	 *
	 */
	public void setContent(String content) throws MailSendException,Exception
	{
		this.content = content;
	}


	/**
	 * メールケースセットメソッド.
	 *
	 * Mail_Case_List用
	 *
	 */
	public void setMailCase(String mailCase) throws MailSendException,Exception
	{
		this.mailCase = mailCase;
	}

	/**
	 * テスト用送信先メールアドレスセットメソッド
	 *
	 */
	private String setTestAdrs(Connection con) throws Exception
	{
		//SQL
		String sql = "select e.mail_address from mail_case_list mc, mail_recipient_list mr, emp_list e"
			+ " where mc.mail_case_id = mr.mail_case_id"
			+ " and   mr.emp_id = e.emp_id"
			+ " and   mc.mail_case_name = 'テスト用送信先' "
		;

		ResultSet rs = stmt.executeQuery(sql);

		rs.next();
		return rs.getString("mail_address") + this.domain;
	}

	/**
	 * テスト用送信先メールアドレスゲットメソッド
	 */
	protected String getTestAdrs() throws Exception
	{
		return this.testAdrs;
	}

	/**
	 * 環境セットメソッド
	 *
	 */
	private String setKankyo(Connection con) throws Exception
	{
		//SQL
		String sql = "select value from const_list where name = '環境' "
		;

		ResultSet rs = stmt.executeQuery(sql);

		rs.next();
		return rs.getString("value");
	}

	/**
	 * 環境ゲットメソッド
	 */
	protected String getKankyo() throws Exception
	{
		return this.kankyo;
	}


	/**
	 * AUTHメールアドレスセットメソッド
	 *
	 */
	private String setAuthMailAddress(Connection con) throws Exception
	{
		//SQL
		String sql = "select value from const_list where name = 'MAIL_AUTH_ADDRESS' "
		;

		ResultSet rs = stmt.executeQuery(sql);

		rs.next();
		return rs.getString("value");
	}

	/**
	 * AUTHメールアドレスゲットメソッド
	 */
	protected String getAuthMailAddress() throws Exception
	{
		return this.authMailAddress;
	}


	/**
	 * AUTHメールパスワードセットメソッド
	 *
	 */
	private String setAuthMailPassword(Connection con) throws Exception
	{
		//SQL
		String sql = "select value from const_list where name = 'MAIL_AUTH_PASSWORD' "
		;

		ResultSet rs = stmt.executeQuery(sql);

		rs.next();
		return rs.getString("value");
	}

	/**
	 * AUTHメールパスワードゲットメソッド
	 */
	protected String getAuthMailPassword() throws Exception
	{
		return this.authMailPassword;
	}
}
