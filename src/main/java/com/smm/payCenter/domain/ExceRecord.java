package com.smm.payCenter.domain;

import java.math.BigDecimal;

public class ExceRecord {

	private Integer rownum;//序号
	private String useraccountname;//企业名称
	private String useraccountno;//附属账号
	private Integer userpaychannelid;//用户支付渠道ID
	private BigDecimal usermoney;//系统账户余额
	private BigDecimal usersjamt;//银行实际账户余额
	public Integer getRownum() {
		return rownum;
	}
	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}
	public String getUseraccountname() {
		return useraccountname;
	}
	public void setUseraccountname(String useraccountname) {
		this.useraccountname = useraccountname;
	}
	public String getUseraccountno() {
		return useraccountno;
	}
	public void setUseraccountno(String useraccountno) {
		this.useraccountno = useraccountno;
	}
	public Integer getUserpaychannelid() {
		return userpaychannelid;
	}
	public void setUserpaychannelid(Integer userpaychannelid) {
		this.userpaychannelid = userpaychannelid;
	}
	public BigDecimal getUsermoney() {
		return usermoney;
	}
	public void setUsermoney(BigDecimal usermoney) {
		this.usermoney = usermoney;
	}
	public BigDecimal getUsersjamt() {
		return usersjamt;
	}
	public void setUsersjamt(BigDecimal usersjamt) {
		this.usersjamt = usersjamt;
	}
	
	
}
