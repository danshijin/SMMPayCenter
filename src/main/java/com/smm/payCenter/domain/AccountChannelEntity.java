package com.smm.payCenter.domain;

import java.io.Serializable;
import java.util.Date;

public class AccountChannelEntity implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer user_id;
	private String user_name;//企业邮箱
	private String company_name;//企业名称
	private String user_account_no;//企业帐号
	private String user_account_status;//帐号状态
	private Date replay_time;//最后交易时间
	private String totalMoney;//可用帐户余额
	private String useMoney;//帐号余额
	private String freezeMoney;//冻结金额
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getUser_account_no() {
		return user_account_no;
	}
	public void setUser_account_no(String user_account_no) {
		this.user_account_no = user_account_no;
	}
	public String getUser_account_status() {
		return user_account_status;
	}
	public void setUser_account_status(String user_account_status) {
		this.user_account_status = user_account_status;
	}
	public Date getReplay_time() {
		return replay_time;
	}
	public void setReplay_time(Date replay_time) {
		this.replay_time = replay_time;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getUseMoney() {
		return useMoney;
	}
	public void setUseMoney(String useMoney) {
		this.useMoney = useMoney;
	}
	public String getFreezeMoney() {
		return freezeMoney;
	}
	public void setFreezeMoney(String freezeMoney) {
		this.freezeMoney = freezeMoney;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
}
