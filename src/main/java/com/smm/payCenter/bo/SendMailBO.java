package com.smm.payCenter.bo;

import java.util.Map;

public interface SendMailBO {
    /**
     * @author hanfeihu
     * @param userName 用户
     * @param accountName 账户名
     * @param account 账号
     * @param receiveMail 接收mail
     * @return 成功或是失败 success成功
     */
    public String auditSuccessMail(String userName, String accountName, String account, String receiveMail);

    /**
     * 每日结算
     * 
     * @return 成功或是失败
     */
     public String dailySettlement(Map<String, Object> map, String receiveMail, String mailTemplateName, String subject);

     /**
      * 每日结算
      * 
      * @return 成功或是失败
      */
      public String sendMailSettlement(Map<String, Object> map, String receiveMail, String mailTemplateName, String subject);
      
      /**
       * 每日结算
       * 
       * @return 成功或是失败
       */
       public String sendMailDaySettlement(Map<String, Object> map, String receiveMail, String mailTemplateName, String subject);
     
    /**
     * 开通支付后台服务
     * 
     * @param map 参数
     * @param receiveMail 接收邮箱
     * @param mailTemplateName 模板名字
     * @param subject 邮件主题
     * @return
     */
    public String sendHtmlMail(Map<String, String> map, String receiveMail, String mailTemplateName, String subject);

	

	

}
