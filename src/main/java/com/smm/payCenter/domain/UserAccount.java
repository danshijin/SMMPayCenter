package com.smm.payCenter.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/** 商城用户   申请审核 、支付
 * @ClassName: UserAccount 
 * @Description: TODO
 * @author: zhangnan
 * @date: 2015年11月10日 上午10:09:26  
 */
public class UserAccount implements Serializable{
    
    /**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 7881463679978176589L;

	private Integer userId;

    private String userName;

    private String password;

    private String idCardUrl;

    private String companyAddr;

    private String certificateNo;

    private String certificateUrl;

    private String registeryCertificateUrl;

    private String companyPostCode;

    private String companyName;

    private String phone;

    private String mobilePhone;

    private String contactName;

    private String mallUserName;

    private Integer auditId;

    private String auditTime;

    private Integer auditStatus;//审核状态

    private BigDecimal freezeMoney;

    private BigDecimal useMoney;

    private String registerIp;

    private String registerTime;//注册时间

    private String lastLoginIp;

    private String lastLoginTime;
    
    private String userAccountNo;

    private BigDecimal shoukuan;//收款
    private BigDecimal zhifu;//支付
    private BigDecimal chujin;//出金
    private BigDecimal rujin;//入金
    
    

	public BigDecimal getShoukuan() {
		return shoukuan;
	}

	public void setShoukuan(BigDecimal shoukuan) {
		this.shoukuan = shoukuan;
	}

	public BigDecimal getZhifu() {
		return zhifu;
	}

	public void setZhifu(BigDecimal zhifu) {
		this.zhifu = zhifu;
	}

	public BigDecimal getChujin() {
		return chujin;
	}

	public void setChujin(BigDecimal chujin) {
		this.chujin = chujin;
	}

	public BigDecimal getRujin() {
		return rujin;
	}

	public void setRujin(BigDecimal rujin) {
		this.rujin = rujin;
	}

	public String getUserAccountNo() {
		return userAccountNo;
	}

	public void setUserAccountNo(String userAccountNo) {
		this.userAccountNo = userAccountNo;
	}

	//拓展字段
    private String bankAccountNo;//绑定银行号码
    
    private String bankName;//绑定银行名称
    
    private BigDecimal drawMoney;//划款金额
    
    private String bindTime;//绑定时间
    
    private Integer auditBankStatus;//银行卡绑定审核状态
    
    private String createTime;//申请验证时间
    
    private Integer bindId;//绑定银行卡的主键
    
    private Integer isPayment;//是否划款
    

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BigDecimal getDrawMoney() {
		return drawMoney;
	}

	public void setDrawMoney(BigDecimal drawMoney) {
		this.drawMoney = drawMoney;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdCardUrl() {
		return idCardUrl;
	}

	public void setIdCardUrl(String idCardUrl) {
		this.idCardUrl = idCardUrl;
	}

	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getCertificateUrl() {
		return certificateUrl;
	}

	public void setCertificateUrl(String certificateUrl) {
		this.certificateUrl = certificateUrl;
	}

	public String getRegisteryCertificateUrl() {
		return registeryCertificateUrl;
	}

	public void setRegisteryCertificateUrl(String registeryCertificateUrl) {
		this.registeryCertificateUrl = registeryCertificateUrl;
	}

	public String getCompanyPostCode() {
		return companyPostCode;
	}

	public void setCompanyPostCode(String companyPostCode) {
		this.companyPostCode = companyPostCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getMallUserName() {
		return mallUserName;
	}

	public void setMallUserName(String mallUserName) {
		this.mallUserName = mallUserName;
	}

	public Integer getAuditId() {
		return auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public BigDecimal getFreezeMoney() {
		return freezeMoney;
	}

	public void setFreezeMoney(BigDecimal freezeMoney) {
		this.freezeMoney = freezeMoney;
	}

	public BigDecimal getUseMoney() {
		return useMoney;
	}

	public void setUseMoney(BigDecimal useMoney) {
		this.useMoney = useMoney;
	}

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getBindTime() {
		return bindTime;
	}

	public void setBindTime(String bindTime) {
		this.bindTime = bindTime;
	}

	public Integer getAuditBankStatus() {
		return auditBankStatus;
	}

	public void setAuditBankStatus(Integer auditBankStatus) {
		this.auditBankStatus = auditBankStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getBindId() {
		return bindId;
	}

	public void setBindId(Integer bindId) {
		this.bindId = bindId;
	}

	public Integer getIsPayment() {
		return isPayment;
	}

	public void setIsPayment(Integer isPayment) {
		this.isPayment = isPayment;
	}
    
    

   
}