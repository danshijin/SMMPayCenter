/**
 * 
 */
package com.smm.payCenter.domain;

import java.math.BigDecimal;

/**
 * TrCashApply
 *
 * Copyright 2015 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2015年11月13日
 */
public class TrCashApply {
	
	private Integer userId;
	
	private Integer bankId;

	private Integer id;	  //记录编号
	
	private String email;	//邮箱
	
	private String companyName;	   //公司名
	
	private BigDecimal cashMoney;	//出金金额
	
	private String bankName;	//出金银行
	
	private String bankNo;	  //银行帐号
	
	private BigDecimal useMoney;   //余额
	
	private Integer replayStatus;	//状态
	
	private String applyTime;//申请时间
	private String replayTime;//审核时间
	private BigDecimal smmFreezeMoney;//待出金金额
	
	private Integer cash_bank_id;//用户银行卡绑定记录编号
	private BigDecimal counter_fee;//手续费
	private BigDecimal cash_money;//出金金额
	private String cash_bank_no;//出金银行卡号
	private String cash_bank_name;//出金银行名称（支行）
	private String cash_bank_cnaps;//行连号
	private Integer cash_type;//出金方式 1：实时出金
	private Integer user_id;//会员编号
	private String user_email;//会员登陆Email
	private String user_company_name;//会员企业名称
	private BigDecimal user_use_money;//会员当前可用余额
	private BigDecimal user_freeze_money;//会员当前冻结金额
	private String apply_time;//申请时间
	private String replay_time;//审核时间
	private String apply_remark;//出金备注
	private Integer apply_status;//出金状态 0 待出金 1 已出金 2 出金失败
	private Integer replay_status;//审核状态 0 待审核 1 已通过 2 已关闭
	private Integer replay_user_id;//审核人
	private Integer pay_channel_id;//支付渠道编号
	private String pay_channel_user_account;//支付渠道会员账号
	private String cashNo;//交易编号

	public BigDecimal getSmmFreezeMoney() {
		return smmFreezeMoney;
	}
	public void setSmmFreezeMoney(BigDecimal smmFreezeMoney) {
		this.smmFreezeMoney = smmFreezeMoney;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getReplayTime() {
		return replayTime;
	}

	public void setReplayTime(String replayTime) {
		this.replayTime = replayTime;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the cashMoney
	 */
	public BigDecimal getCashMoney() {
		return cashMoney;
	}

	/**
	 * @param cashMoney the cashMoney to set
	 */
	public void setCashMoney(BigDecimal cashMoney) {
		this.cashMoney = cashMoney;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankNo
	 */
	public String getBankNo() {
		return bankNo;
	}

	/**
	 * @param bankNo the bankNo to set
	 */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	/**
	 * @return the replayStatus
	 */
	public Integer getReplayStatus() {
		return replayStatus;
	}

	/**
	 * @param replayStatus the replayStatus to set
	 */
	public void setReplayStatus(Integer replayStatus) {
		this.replayStatus = replayStatus;
	}

	/**
	 * @return the useMoney
	 */
	public BigDecimal getUseMoney() {
		return useMoney;
	}

	/**
	 * @param useMoney the useMoney to set
	 */
	public void setUseMoney(BigDecimal useMoney) {
		this.useMoney = useMoney;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the bankId
	 */
	public Integer getBankId() {
		return bankId;
	}

	/**
	 * @param bankId the bankId to set
	 */
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public Integer getCash_bank_id() {
		return cash_bank_id;
	}

	public void setCash_bank_id(Integer cash_bank_id) {
		this.cash_bank_id = cash_bank_id;
	}

	public BigDecimal getCounter_fee() {
		return counter_fee;
	}

	public void setCounter_fee(BigDecimal counter_fee) {
		this.counter_fee = counter_fee;
	}

	public BigDecimal getCash_money() {
		return cash_money;
	}

	public void setCash_money(BigDecimal cash_money) {
		this.cash_money = cash_money;
	}

	public String getCash_bank_no() {
		return cash_bank_no;
	}

	public void setCash_bank_no(String cash_bank_no) {
		this.cash_bank_no = cash_bank_no;
	}

	public String getCash_bank_name() {
		return cash_bank_name;
	}

	public void setCash_bank_name(String cash_bank_name) {
		this.cash_bank_name = cash_bank_name;
	}

	public String getCash_bank_cnaps() {
		return cash_bank_cnaps;
	}

	public void setCash_bank_cnaps(String cash_bank_cnaps) {
		this.cash_bank_cnaps = cash_bank_cnaps;
	}

	public Integer getCash_type() {
		return cash_type;
	}

	public void setCash_type(Integer cash_type) {
		this.cash_type = cash_type;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_company_name() {
		return user_company_name;
	}

	public void setUser_company_name(String user_company_name) {
		this.user_company_name = user_company_name;
	}

	public BigDecimal getUser_use_money() {
		return user_use_money;
	}

	public void setUser_use_money(BigDecimal user_use_money) {
		this.user_use_money = user_use_money;
	}

	public BigDecimal getUser_freeze_money() {
		return user_freeze_money;
	}

	public void setUser_freeze_money(BigDecimal user_freeze_money) {
		this.user_freeze_money = user_freeze_money;
	}

	public String getApply_time() {
		return apply_time;
	}

	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}

	public String getReplay_time() {
		return replay_time;
	}

	public void setReplay_time(String replay_time) {
		this.replay_time = replay_time;
	}

	public String getApply_remark() {
		return apply_remark;
	}

	public void setApply_remark(String apply_remark) {
		this.apply_remark = apply_remark;
	}

	public Integer getApply_status() {
		return apply_status;
	}

	public void setApply_status(Integer apply_status) {
		this.apply_status = apply_status;
	}

	public Integer getReplay_status() {
		return replay_status;
	}

	public void setReplay_status(Integer replay_status) {
		this.replay_status = replay_status;
	}

	public Integer getReplay_user_id() {
		return replay_user_id;
	}

	public void setReplay_user_id(Integer replay_user_id) {
		this.replay_user_id = replay_user_id;
	}

	public Integer getPay_channel_id() {
		return pay_channel_id;
	}

	public void setPay_channel_id(Integer pay_channel_id) {
		this.pay_channel_id = pay_channel_id;
	}

	public String getPay_channel_user_account() {
		return pay_channel_user_account;
	}

	public void setPay_channel_user_account(String pay_channel_user_account) {
		this.pay_channel_user_account = pay_channel_user_account;
	}
	public String getCashNo() {
		return cashNo;
	}
	public void setCashNo(String cashNo) {
		this.cashNo = cashNo;
	}
	
}
