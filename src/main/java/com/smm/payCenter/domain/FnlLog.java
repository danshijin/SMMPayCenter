package com.smm.payCenter.domain;

import java.io.Serializable;

public class FnlLog implements Serializable{
	/**
	 *   管理员操作日志
	 */
	private static final long serialVersionUID = 7553776860981726348L;
	private Integer id;
	private Integer fnlAccount;//财务人员账号
	private String type;//操作类型：审核会员注册、审核支付、审核出金、审核绑定银行卡、强制支付、强制冻结、强制解冻、手动退款、手动重发出金、手动退还出金、手动调整账户余额
	private String content;//内容
	private String createdAt;//操作时间
	private String userCompanyName;//被操作会员企业名称
	private double transferMoney;//操作影响的金额
	private String ip;//管理员客户端ip
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFnlAccount() {
		return fnlAccount;
	}
	public void setFnlAccount(Integer fnlAccount) {
		this.fnlAccount = fnlAccount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUserCompanyName() {
		return userCompanyName;
	}
	public void setUserCompanyName(String userCompanyName) {
		this.userCompanyName = userCompanyName;
	}
	public double getTransferMoney() {
		return transferMoney;
	}
	public void setTransferMoney(double transferMoney) {
		this.transferMoney = transferMoney;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
