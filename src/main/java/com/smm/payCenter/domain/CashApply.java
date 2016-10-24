package com.smm.payCenter.domain;

/**
 * 出金记录
 * 
 * @author tantaigen
 *
 */
public class CashApply {
	private Integer id; // ID
	private Float cashMoney; // 出金金额
	private String cashBankName; // 银行名称
	private String userId; // 会员编号
	private Float userUseMoney; // 会员当前可用余额
	private Float userFreezeMoney; // 会员当前冻结金额
	private Integer applyStatus; // 出金状态 0 待出金 1 已出金 2 出金失败
	private String payChannelUserAccount; // 支付渠道会员账号
	private String applyTime; // 申请时间
	private String counterFee;
	private String cashBankNo;
	private String cashBankCnaps;
	private String cashType;
	private String replayTime;
	private String applyRemark;
	private Integer replayStatus;
	private Integer replayUserId;
	private Integer payChannelId;
	private String userCompanyName;
	private String cashBankId;
	private Float smmFreezeMoney;//待出金金额
	private  String bankTypeId;//
	private  String cashNo;// 
	
	private  String  verifyCode;//MD5验证
	private  Integer   userPayChannelId;//支付渠道会员Id
	private String   userAccountNo;
	public String getUserAccountNo() {
		return userAccountNo;
	}

	public void setUserAccountNo(String userAccountNo) {
		this.userAccountNo = userAccountNo;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getBankTypeId() {
		return bankTypeId;
	}

	public void setBankTypeId(String bankTypeId) {
		this.bankTypeId = bankTypeId;
	}

	// 分页
	private Integer startNum;
	private Integer endNum;

	private String companyName;
	private String startTime;
	private String endTime;
	private String tradeId;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	private String userEmail;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getCashBankId() {
		return cashBankId;
	}

	public void setCashBankId(String cashBankId) {
		this.cashBankId = cashBankId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getCashMoney() {
		return cashMoney;
	}

	public void setCashMoney(Float cashMoney) {
		this.cashMoney = cashMoney;
	}

	public String getCashBankName() {
		return cashBankName;
	}

	public void setCashBankName(String cashBankName) {
		this.cashBankName = cashBankName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Float getUserUseMoney() {
		return userUseMoney;
	}

	public void setUserUseMoney(Float userUseMoney) {
		this.userUseMoney = userUseMoney;
	}

	public Float getUserFreezeMoney() {
		return userFreezeMoney;
	}

	public void setUserFreezeMoney(Float userFreezeMoney) {
		this.userFreezeMoney = userFreezeMoney;
	}

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getPayChannelUserAccount() {
		return payChannelUserAccount;
	}

	public void setPayChannelUserAccount(String payChannelUserAccount) {
		this.payChannelUserAccount = payChannelUserAccount;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getCounterFee() {
		return counterFee;
	}

	public void setCounterFee(String counterFee) {
		this.counterFee = counterFee;
	}

	public String getCashBankNo() {
		return cashBankNo;
	}

	public void setCashBankNo(String cashBankNo) {
		this.cashBankNo = cashBankNo;
	}

	public String getCashBankCnaps() {
		return cashBankCnaps;
	}

	public void setCashBankCnaps(String cashBankCnaps) {
		this.cashBankCnaps = cashBankCnaps;
	}

	public String getCashType() {
		return cashType;
	}

	public void setCashType(String cashType) {
		this.cashType = cashType;
	}

	public String getReplayTime() {
		return replayTime;
	}

	public void setReplayTime(String replayTime) {
		this.replayTime = replayTime;
	}

	public String getApplyRemark() {
		return applyRemark;
	}

	public void setApplyRemark(String applyRemark) {
		this.applyRemark = applyRemark;
	}

	public Integer getReplayStatus() {
		return replayStatus;
	}

	public void setReplayStatus(Integer replayStatus) {
		this.replayStatus = replayStatus;
	}

	public Integer getReplayUserId() {
		return replayUserId;
	}

	public void setReplayUserId(Integer replayUserId) {
		this.replayUserId = replayUserId;
	}

	public Integer getPayChannelId() {
		return payChannelId;
	}

	public void setPayChannelId(Integer payChannelId) {
		this.payChannelId = payChannelId;
	}

	public String getUserCompanyName() {
		return userCompanyName;
	}

	public void setUserCompanyName(String userCompanyName) {
		this.userCompanyName = userCompanyName;
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

	public Float getSmmFreezeMoney() {
		return smmFreezeMoney;
	}

	public void setSmmFreezeMoney(Float smmFreezeMoney) {
		this.smmFreezeMoney = smmFreezeMoney;
	}

	public String getCashNo() {
		return cashNo;
	}

	public void setCashNo(String cashNo) {
		this.cashNo = cashNo;
	}

	public Integer getUserPayChannelId() {
		return userPayChannelId;
	}

	public void setUserPayChannelId(Integer userPayChannelId) {
		this.userPayChannelId = userPayChannelId;
	}


	

}
