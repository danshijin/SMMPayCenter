package com.smm.payCenter.util;

import java.util.Properties;
import java.io.UnsupportedEncodingException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	public static final String defaultSMTPHost = "webmail.smm.cn";
	public static final String defaultMailFromName = "services@smm.cn";
	public static final String defaultMailFromPassword = "password";

	public static Integer send(String mailTos, String mailSubject,
			String mailText) {
		// check params
		if (mailTos == null || mailSubject == null || mailText == null)
			return 0;

		// encoding params
		String encoding = "iso-8859-1";
		try {
			// 标题不需要编码,编码后为乱码
			// 正文需要编码
			mailText = new String(mailText.getBytes(), encoding);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Fail to encoding mail text to " + encoding);
		}

		Properties props = new Properties();
		props.put("mail.smtp.host", defaultSMTPHost);// 邮件服务器地址
		//props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.user", "上海有色网");// 发送方的发送名;
		//props.put("mail.smtp.port", "994"); //google使用465或587端口
		props.put("mail.smtp.from", defaultMailFromName);// 发送邮箱地址;
		props.put("mail.debug", "true");// 不打印构建发送者信息

		// 构建发送者
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(defaultMailFromName,
						defaultMailFromPassword);
			}
		};

		Session session = Session.getInstance(props, auth);
		session.setDebug(false);// 不打印发送信息

		Message msg = new MimeMessage(session);
		int sendNum = 0;// 发送的数量

		for (String mailTo : mailTos.split(",")) {
			try {
				InternetAddress[] addresses = { new InternetAddress(mailTo) };
				msg.setRecipients(Message.RecipientType.TO, addresses);// 设置邮件接收地址集
				msg.setSentDate(new java.util.Date());// 设置邮件发送日期
				msg.setSubject(mailSubject);// 设置邮件的标题
				// msg.setText(mailText);// 设置邮件的内容(文本)
				msg.setContent(mailText, "text/html");// 设置邮件的内容
				Transport.send(msg);// 发送邮件
				sendNum++;// 发送记数
				System.out.println("Success to send email to " + mailTo);
			} catch (MessagingException e) {
				System.out.println("Fail to send email for:" + e);
			}
		}
		return sendNum;
	}

	public static void main(String[] args) {
		int i = SendMail.send("hece@smm.cn,952502937@qq.com", "测试", "第三方的手法撒旦发射");
		System.out.println(i);
	}
}