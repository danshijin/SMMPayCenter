package com.smm.payCenter.domain;

import java.math.BigDecimal;

/**
 * 
 * 会员
 * 
 * @author tantaigen
 *
 */
public class UserAccountEntity {
	private Integer userId;// 用户ID
	private String userName;// 用户名称
	private String idCardUrl;// 身份证图片URL
	private String companyAddr;// 公司地址
	private String certificateNo;// 营业执照编号
	private String certificateUrl;// 营业执照图片URL
	private String registeryCertificateUrl;// 税务登记证图片URL
	private String companyPostCode;// 公司所在地邮政编码
	private String companyName;// 公司名称
	private String activeCode;// 激活码
	private String phone;// 座机电话
	private String mobilePhone;// 手机
	private String contactName;// 联系人
	private String mallId;// 商城会员ID
	private String auditId;// 审核人ID
	private String auditTime;// 审核时间
	private String auditStatus;// 审核状态
	private String isActive;// 是否激活
	private Double freezeMoney;// 冻结金额
	private Double useMoney;// 可用余额
	private String registerIp;// 注册IP
	private String registerTime;// 注册时间
	private String lastLoginIp;// 上次登录IP
	private String lastLoginTime;// 上次登录时间
	private String authorizeUrl;//管理员授权委托书访问url
	private Double sumMoney;//资金
	///查询使用
	private String  statusDate;//开始时间
	
	private String endDate;//结束时间
	
	private  String  bankNO; //银行卡号
	
	private String verifyCode;//md5验证
	private Integer loginNum;//登录次数
	
	private String threeCertificatesUrl;
	private String bankOpenUrl;
	private Integer isCommon;
	
	
	
	public String getThreeCertificatesUrl() {
		return threeCertificatesUrl;
	}

	public void setThreeCertificatesUrl(String threeCertificatesUrl) {
		this.threeCertificatesUrl = threeCertificatesUrl;
	}

	public String getBankOpenUrl() {
		return bankOpenUrl;
	}

	public void setBankOpenUrl(String bankOpenUrl) {
		this.bankOpenUrl = bankOpenUrl;
	}

	public Integer getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(Integer isCommon) {
		this.isCommon = isCommon;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	private BigDecimal bankFactMoney;//银行实际余额
	private BigDecimal bankUseMoney;//银行可用余额
	
	private double countMoney;//余额 （可用余额+冻结金额）
	private double duseMoney;// 可用余额
	
	private Integer startNum;//开始
	
	private Integer endNum;//结束
	
	private String mallUserName;//商城会员ID
	
	private String bankAccountNo;//银行账号
	
	private BigDecimal userPayChannelId;
	
	private String userAccountNo;//支付渠道账号
	
	
	public double getDuseMoney() {
		return duseMoney;
	}

	public void setDuseMoney(double duseMoney) {
		this.duseMoney = duseMoney;
	}

	public BigDecimal getBankUseMoney() {
		return bankUseMoney;
	}

	public void setBankUseMoney(BigDecimal bankUseMoney) {
		this.bankUseMoney = bankUseMoney;
	}

	public String getAuthorizeUrl() {
		return authorizeUrl;
	}

	public void setAuthorizeUrl(String authorizeUrl) {
		this.authorizeUrl = authorizeUrl;
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

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
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



	public String getMallId() {
		return mallId;
	}

	public void setMallId(String mallId) {
		this.mallId = mallId;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public String getBankNO() {
		return bankNO;
	}

	public void setBankNO(String bankNO) {
		this.bankNO = bankNO;
	}

	public String getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public BigDecimal getBankFactMoney() {
		return bankFactMoney;
	}

	public void setBankFactMoney(BigDecimal bankFactMoney) {
		this.bankFactMoney = bankFactMoney;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getEndNum() {
		return endNum;
	}

	public void setEndNum(Integer endNum) {
		this.endNum = endNum;
	}

	public double getCountMoney() {
		return countMoney;
	}

	public void setCountMoney(double countMoney) {
		this.countMoney = countMoney;
	}

	public String getMallUserName() {
		return mallUserName;
	}

	public void setMallUserName(String mallUserName) {
		this.mallUserName = mallUserName;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public BigDecimal getUserPayChannelId() {
		return userPayChannelId;
	}

	public void setUserPayChannelId(BigDecimal userPayChannelId) {
		this.userPayChannelId = userPayChannelId;
	}

	public String getUserAccountNo() {
		return userAccountNo;
	}

	public void setUserAccountNo(String userAccountNo) {
		this.userAccountNo = userAccountNo;
	}



	

	public Double getFreezeMoney() {
		return freezeMoney;
	}

	public void setFreezeMoney(Double freezeMoney) {
		this.freezeMoney = freezeMoney;
	}

	public Double getUseMoney() {
		return useMoney;
	}

	public void setUseMoney(Double useMoney) {
		this.useMoney = useMoney;
	}

	public Double getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(Double sumMoney) {
		this.sumMoney = sumMoney;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}
	
}
