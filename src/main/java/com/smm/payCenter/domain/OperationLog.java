/**
 * 
 */
package com.smm.payCenter.domain;

import java.math.BigDecimal;

/**
 * OperationLog
 *
 * Copyright 2015 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2015年11月16日
 */
public class OperationLog {

	private int id;
	
	private int fnlAccount;
	
	private String account;
	
	private String type;
	
	private String content;
	
	private String createdAt;
	
	private String companyName;
	
	private BigDecimal trmoney;
	
	private String ip;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the fnlAccount
	 */
	public int getFnlAccount() {
		return fnlAccount;
	}

	/**
	 * @param fnlAccount the fnlAccount to set
	 */
	public void setFnlAccount(int fnlAccount) {
		this.fnlAccount = fnlAccount;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return the trmoney
	 */
	public BigDecimal getTrmoney() {
		return trmoney;
	}

	/**
	 * @param trmoney the trmoney to set
	 */
	public void setTrmoney(BigDecimal trmoney) {
		this.trmoney = trmoney;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	
	
		
}
