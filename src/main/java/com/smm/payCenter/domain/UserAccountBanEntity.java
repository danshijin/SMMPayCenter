package com.smm.payCenter.domain;

import java.math.BigDecimal;

/***
 * 用户银行卡信息
 * 
 * @author tantaigen
 *
 */
public class UserAccountBanEntity {

	private Integer bindId;// 绑卡ID
	private Integer userId;// 用户ID
	private Integer bankId;
	private String bankName;// 银行名称
	private BigDecimal drawMoney;//划款金额
	private String bankTypeId;
	private String banktype;//所属银行
	private String bankAccountNo;// 银行账号
	private Integer auditStatus;// 状态
	private String cnaps;//支行联行号
	private Integer isPayment;//是否划款 0 未划款 1 已划款
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getBindId() {
		return bindId;
	}
	public void setBindId(Integer bindId) {
		this.bindId = bindId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccountNo() {
		return bankAccountNo;
	}
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public BigDecimal getDrawMoney() {
		return drawMoney;
	}
	public void setDrawMoney(BigDecimal drawMoney) {
		this.drawMoney = drawMoney;
	}
	public String getBanktype() {
		return banktype;
	}
	public void setBanktype(String banktype) {
		this.banktype = banktype;
	}
	public Integer getBankId() {
		return bankId;
	}
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
	public String getBankTypeId() {
		return bankTypeId;
	}
	public void setBankTypeId(String bankTypeId) {
		this.bankTypeId = bankTypeId;
	}
	public String getCnaps() {
		return cnaps;
	}
	public void setCnaps(String cnaps) {
		this.cnaps = cnaps;
	}
	public Integer getIsPayment() {
		return isPayment;
	}
	public void setIsPayment(Integer isPayment) {
		this.isPayment = isPayment;
	}
}
