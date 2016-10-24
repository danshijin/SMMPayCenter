package com.smm.payCenter.bo.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.smm.payCenter.bo.SendMailBO;
import com.smm.payCenter.util.SendMail;
import com.smm.payCenter.util.UtilMail;

import freemarker.template.Template;

@Service
public class SendMailBOImpl implements SendMailBO {

    private static Logger        logger = Logger.getLogger(SendMailBOImpl.class.getName());

    @Resource
    private JavaMailSender       mailSender;
    @Resource
    private FreeMarkerConfigurer freemarkerConfig;

    @Override
    public String auditSuccessMail(String userName, String accountName, String account, String receiveMail) {
        // TODO Auto-generated method stub

        String imgPath = System.getProperty("smm.SMMPayCenter") + "/Public/images/mail/";

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("userName", userName);
            map.put("account", account);
            map.put("registeredMail", receiveMail);
            map.put("accountName", accountName);

            Template tpl = freemarkerConfig.getConfiguration().getTemplate("mailTemplate/payment-mail.html");
            String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);

            MimeMessage mailMessage = mailSender.createMimeMessage();
            //false 是mulitpart类型 、true html格式
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");

            messageHelper.setFrom("上海有色网<no-replay@smm.cn>");
            messageHelper.setTo(receiveMail);

            messageHelper.setSubject("审核通过");
            // true 表示启动HTML格式的邮件
            messageHelper.setText(htmlText, true);

            FileSystemResource img = new FileSystemResource(new File(imgPath + "index-icon_1.png"));

            messageHelper.addInline("IMG0", img);//跟cid一致

            FileSystemResource img1 = new FileSystemResource(new File(imgPath + "index-icon_2.png"));

            messageHelper.addInline("IMG1", img1);//跟cid一致

            FileSystemResource img2 = new FileSystemResource(new File(imgPath + "index-icon_3.png"));

            messageHelper.addInline("IMG2", img2);//跟cid一致

            mailSender.send(mailMessage);
        } catch (Exception e) {
            logger.error("发送邮件：" + receiveMail + "失败");
            return "error";
        }
        logger.error("发送邮件：" + receiveMail + "成功");
        return "success";

    }

    /**财务操作人员支付后台功能开通后发送邮件
     * 发送html格式邮件
     * 
     * @param map 参数
     * @param receiveMail 接收邮箱
     * @param mailTemplateName 模板名字
     * @param subject 邮件主题
     * @return 成功success 失败 失败信息
     */
    @Override
    public String sendHtmlMail(Map<String, String> map, String receiveMail, String mailTemplateName, String subject) {
        // TODO Auto-generated method stub

        try {
            if (map == null) {

                map = new HashMap<>();
            }
            Template tpl = freemarkerConfig.getConfiguration()
                    .getTemplate("mailTemplate/" + mailTemplateName + ".html");
            String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);

            MimeMessage mailMessage = mailSender.createMimeMessage();
            //false 是mulitpart类型 、true html格式
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");

            messageHelper.setFrom("上海有色网<no-replay@smm.cn>");
            messageHelper.setTo(receiveMail);

            messageHelper.setSubject(subject);
            // true 表示启动HTML格式的邮件
            messageHelper.setText(htmlText, true);

            mailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println("发送邮件失败");
            e.printStackTrace();
            logger.error("发送邮件失败:" + e);
            return "error";
        }
        logger.error("发送邮件：" + receiveMail + "成功");
        return "success";

    }

    @Override
    public String dailySettlement(Map<String, Object> map, String receiveMail, String mailTemplateName, String subject) {
    	try {
            if (map == null) {

                map = new HashMap<>();
            }
            Template tpl = freemarkerConfig.getConfiguration()
            .getTemplate("mailTemplate/" + mailTemplateName + ".html");
            String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);

            logger.info("每日结算邮件:/n"+htmlText);
            MimeMessage mailMessage = mailSender.createMimeMessage();
            //false 是mulitpart类型 、true html格式
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");

            messageHelper.setFrom("上海有色网<no-replay@smm.cn>");
            messageHelper.setTo(receiveMail);

            messageHelper.setSubject(subject);
            // true 表示启动HTML格式的邮件
            messageHelper.setText(htmlText, true);

            mailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println("发送邮件失败");
            e.printStackTrace();
            logger.error("发送邮件失败:" + e);
            return "error";
        }
        logger.error("发送邮件：" + receiveMail + "成功");
        return "success";
    }
    
    /**
     * 每日结算发送邮件
     * 
     * */
    @Override
    public String sendMailSettlement(Map<String, Object> map, String receiveMail, String mailTemplateName, String subject) {
    	try {
            if (map == null) {
                map = new HashMap<>();
            }
            Template tpl = freemarkerConfig.getConfiguration().getTemplate("mailTemplate/" + mailTemplateName + ".html");
            String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);

            logger.info("每日结算邮件:/n"+htmlText);
            
            int i = SendMail.send(receiveMail, subject, htmlText);
            if(i == 0){
            	System.out.println("发送邮件失败,返回参数="+i);
                return "error";
            }
        } catch (Exception e) {
            System.out.println("发送邮件失败");
            e.printStackTrace();
            logger.error("发送邮件失败:" + e);
            return "error";
        }
        logger.error("发送邮件：" + receiveMail + "成功");
        return "success";
    }
    
    
    /**
     * 每日结算发送邮件
     * 
     * */
    @Override
    public String sendMailDaySettlement(Map<String, Object> map, String receiveMail, String mailTemplateName, String subject) {
    	try {
            if (map == null) {
                map = new HashMap<>();
            }
            Template tpl = freemarkerConfig.getConfiguration().getTemplate("mailTemplate/" + mailTemplateName + ".html");
            String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
            logger.info("每日结算邮件:/n"+htmlText);
            
            UtilMail se = new UtilMail(false);
            se.doSendHtmlEmail(subject, htmlText, receiveMail);
        } catch (Exception e) {
            System.out.println("发送邮件失败");
            e.printStackTrace();
            logger.error("发送邮件失败:" + e);
            return "error";
        }
        logger.info("发送邮件：" + receiveMail + "成功");
        return "success";
    }
}