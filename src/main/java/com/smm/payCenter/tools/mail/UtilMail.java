package com.smm.payCenter.tools.mail;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

public class UtilMail {
    /**
     * Message对象将存储我们实际发送的电子邮件信息，
     * Message对象被作为一个MimeMessage对象来创建并且需要知道应当选择哪一个JavaMail session。
     */
    private MimeMessage message;

    /**
     * Session类代表JavaMail中的一个邮件会话。
     * 每一个基于JavaMail的应用程序至少有一个Session（可以有任意多的Session）。
     * JavaMail需要Properties来创建一个session对象。 寻找"mail.smtp.host" 属性值就是发送邮件的主机
     * 寻找"mail.smtp.auth" 身份验证，目前免费邮件服务器都需要这一项
     */
    private Session     session;

    /***
     * 邮件是既可以被发送也可以被受到。JavaMail使用了两个不同的类来完成这两个功能：Transport 和 Store。 Transport
     * 是用来发送信息的，而Store用来收信。对于这的教程我们只需要用到Transport对象。
     */
    private Transport   transport;

    private String      mailHost        = "";
    private String      sender_username = "";
    private String      sender_password = "";

    private Properties  properties      = new Properties();

    /*
     * 初始化方法
     */
    public UtilMail(boolean debug) {
        String path = System.getProperty("smm.SMMPayCenter") + "/WEB-INF/classes/MailServer.properties";
        try {
            InputStream in = new FileInputStream(path);
            properties.load(in);
            this.mailHost = properties.getProperty("mail.smtp.host");
            this.sender_username = properties.getProperty("mail.sender.username");
            this.sender_password = properties.getProperty("mail.sender.password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        session = Session.getInstance(properties);
        session.setDebug(debug);//开启后有调试信息
        message = new MimeMessage(session);
    }

    /**
     * 发送邮件
     * 
     * @param subject 邮件主题
     * @param sendHtml 邮件内容
     * @param receiveUser 收件人地址
     */
    public void doSendHtmlEmail(String subject, String sendHtml, String receiveUser) {
        try {
            // 发件人
            //InternetAddress from = new InternetAddress(sender_username);
            // 下面这个是设置发送人的Nick name
            InternetAddress from = new InternetAddress(MimeUtility.encodeWord("上海有色网") + "<"+sender_username+">");
            message.setFrom(from);

            // 收件人
            InternetAddress to = new InternetAddress(receiveUser);
            message.setRecipient(Message.RecipientType.TO, to);//还可以有CC、BCC

            // 邮件主题
            message.setSubject(subject);

            String content = sendHtml.toString();
            // 邮件内容,也可以使纯文本"text/plain"
            message.setContent(content, "text/html;charset=UTF-8");

            // 保存邮件
            message.saveChanges();

            transport = session.getTransport("smtps");
            // smtp验证，就是你用来发邮件的邮箱用户名密码
            transport.connect(mailHost, sender_username, sender_password);

            // 发送
            transport.sendMessage(message, message.getAllRecipients());
            //System.out.println("send success!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 发送带图片的邮件
     * 
     * @param subject 邮件主题
     * @param sendHtml 邮件内容
     * @param receiveUser 收件人地址
     * @param imgpath 图片路径
     */
    public void doSendHtmlEmail(String subject, String sendHtml, String receiveUser, String[] imgpath) {
        try {
            // 发件人
            //InternetAddress from = new InternetAddress(sender_username);
            // 下面这个是设置发送人的Nick name
            InternetAddress from = new InternetAddress(MimeUtility.encodeWord("上海有色网") + "<"+sender_username+">");
            message.setFrom(from);

            // 收件人
            InternetAddress to = new InternetAddress(receiveUser);
            message.setRecipient(Message.RecipientType.TO, to);//还可以有CC、BCC

            // 邮件主题
            message.setSubject(subject);

            // 新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个)
            MimeMultipart mm = new MimeMultipart();
            // 新建一个存放信件内容的BodyPart对象
            BodyPart mdp = new MimeBodyPart();
            // 给BodyPart对象设置内容和格式/编码方式
            // mdp.setContent(content.toString(), "text/html;charset=GBK");
            mdp.setContent(sendHtml, "text/html;charset=utf-8");
            // 这句很重要，千万不要忘了
            mm.setSubType("related");

            mm.addBodyPart(mdp);

            // add the attachments
            for (int i = 0; i < imgpath.length; i++) {
                // 新建一个存放附件的BodyPart
                mdp = new MimeBodyPart();
                DataHandler dh = new DataHandler(
                        new ByteArrayDataSource(getImages(imgpath[i]), "application/octet-stream"));
                mdp.setDataHandler(dh);
                // 加上这句将作为附件发送,否则将作为信件的文本内容
                //mdp.setFileName(new Integer(i).toString() + ".gif");
                mdp.setHeader("Content-ID", "IMG" + new Integer(i).toString());
                System.out.println("----------------------------IMG" + new Integer(i).toString());
                // 将含有附件的BodyPart加入到MimeMultipart对象中
                mm.addBodyPart(mdp);
            }

            // 把mm作为消息对象的内容
            message.setContent(mm);
            // 保存邮件
            message.saveChanges();

            transport = session.getTransport("smtps");
            // smtp验证，就是你用来发邮件的邮箱用户名密码
            transport.connect(mailHost, sender_username, sender_password);

            // 发送
            transport.sendMessage(message, message.getAllRecipients());
            //System.out.println("send success!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static byte[] getImages(String path) throws Exception {
        ByteArrayOutputStream bo = null;

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
        bo = new ByteArrayOutputStream();
        int ii;
        while ((ii = in.read()) != -1) {
            bo.write(ii);
        }
        in.close();
        bo.close();

        return bo.toByteArray();
    }

    public static void main(String[] args) {
        UtilMail se = new UtilMail(false);
        se.doSendHtmlEmail("标题", "<a href='http://mail.qq.com'>33333</a>", "406701239@qq.com");
    }

}
