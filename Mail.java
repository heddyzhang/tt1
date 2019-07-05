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
//
////////////////////////////////////////////////////////////////////////////////
package contents;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * メール送信クラス
 * メールの送信を行う抽象クラスです。
 * サブクラスにて各項目を定義します。
 *
 */
abstract class Mail{

	/* 送信時文字コード */
	private final String SEND_ENCODING = "iso-2022-jp";

	/**
	 * メール送信メソッド.
	 *
	 * @param smtp SMTPサーバ
	 * @param port ポート番号
	 * @param helo 送信元サーバ
	 * @param from 送信元アドレス
	 * @param to 送信先アドレス
	 * @param subject 題名
	 * @param content 内容
	 * @param testAdrs テスト環境の時の送信先
	 * @param kankyo 環境(テストor本番)
	 */
	protected void sendMail(String smtp,int port, String helo,String from,String to,String subject,String content,String cc,String testAdrs, String kankyo, String authMailAddress, String authMailPassword)
		throws MailSendException,Exception
	{
		Properties objPrp=new Properties();
// 旧環境のメール送信処理
//		objPrp.put("mail.smtp.host", smtp);
//		objPrp.put("mail.host", smtp);
//		objPrp.put("mail.smtp.port", String.valueOf(port));
//		objPrp.put("mail.smtp.ssl.enable", "true");
//		objPrp.setProperty("mail.smtp.auth", "true");
//		objPrp.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		objPrp.setProperty("mail.smtp.socketFactory.fallback", "false");
//		objPrp.setProperty("mail.smtp.socketFactory.port", String.valueOf(port));
//		final String authMailAdrs = authMailAddress;
//		final String authMailPass = authMailPassword;
//        Session session = Session.getInstance(objPrp, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(authMailAdrs, authMailPass);
//            }
//        });

		// 新環境ではstbeehive.oracle.comへの通信はfirewallで許可されていないため、
		// サーバのsendmailでメール送信を行う
		objPrp.put("mail.smtp.host", "localhost");
		Session session=Session.getDefaultInstance(objPrp);


		// 送信メッセージを生成
		MimeMessage objMsg=new MimeMessage(session);
		try {
			if(kankyo != null && kankyo.equals("本番")){
				objMsg.addFrom(InternetAddress.parse(from));
				objMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
				objMsg.setRecipients(Message.RecipientType.CC,InternetAddress.parse(cc));
				objMsg.setSubject(subject, SEND_ENCODING);
				objMsg.setText(content, SEND_ENCODING);
				Transport.send(objMsg);
			}else{
				objMsg.addFrom(InternetAddress.parse(testAdrs));
				objMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(testAdrs));
				objMsg.setSubject(subject + " TEST", SEND_ENCODING);
				objMsg.setText(content, SEND_ENCODING);
				Transport.send(objMsg);
			}
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * 処理実行メソッド.
	 *
	 * クライアント側はこのメソッドを呼んで実行することになる
	 *
	 */
	public void send() throws MailSendException,Exception
	{
		sendMail(getSmtp(),getPort(),getHelo(),getFromAddress(),getToAddress(),getSubject(),getContent(),getCcAddress(),getTestAdrs(),getKankyo(),getAuthMailAddress(),getAuthMailPassword());
	}


	/**
	 * 項目取得メソッド.
	 *
	 * これらクラスの具象化サブクラスにおいて項目を実装してください。
	 *
	 */
	//SMTPサーバ
	protected abstract String getSmtp() throws MailSendException,Exception;
	//ポート番号
	protected abstract int getPort() throws MailSendException,Exception;
	//送信元サーバ
	protected abstract String getHelo() throws MailSendException,Exception;
	//送信元アドレス
	protected abstract String getFromAddress() throws MailSendException,Exception;
	//送信先アドレス
	protected abstract String getToAddress() throws MailSendException,Exception;
	//CCアドレス
	protected abstract String getCcAddress() throws MailSendException,Exception;
	//題名
	protected abstract String getSubject() throws MailSendException,Exception;
	//内容
	protected abstract String getContent() throws MailSendException,Exception;
	//管理者アドレス
	protected abstract String getTestAdrs() throws MailSendException,Exception;
	//環境(本番、テスト)
	protected abstract String getKankyo() throws MailSendException,Exception;
	//認証メールアドレス
	protected abstract String getAuthMailAddress() throws MailSendException,Exception;
	//認証メールパスワード
	protected abstract String getAuthMailPassword() throws MailSendException,Exception;
}



