package com.smm.payCenter.domain;

import java.math.BigDecimal;

/**
 * 
 * @author hece
 *
 *	出入金查询
 */
public class QueryEntry {

	private Integer userid;//用户ID
	private String username;//用户名称email
	private String company;//公司名称
	private BigDecimal usemoney;//余额
	private BigDecimal zhuanru;//转入
	private BigDecimal zhuanchu;//转出
	private BigDecimal chujin;//出金
	private BigDecimal rujin;//入金
	
	private BigDecimal totalmoney;//总余额

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public BigDecimal getUsemoney() {
		return usemoney;
	}

	public void setUsemoney(BigDecimal usemoney) {
		this.usemoney = usemoney;
	}

	public BigDecimal getZhuanru() {
		return zhuanru;
	}

	public void setZhuanru(BigDecimal zhuanru) {
		this.zhuanru = zhuanru;
	}

	public BigDecimal getZhuanchu() {
		return zhuanchu;
	}

	public void setZhuanchu(BigDecimal zhuanchu) {
		this.zhuanchu = zhuanchu;
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

	public BigDecimal getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(BigDecimal totalmoney) {
		this.totalmoney = totalmoney;
	}


}
