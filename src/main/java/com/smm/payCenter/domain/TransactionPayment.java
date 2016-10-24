package com.smm.payCenter.domain;

import java.util.Date;

/**
 * 
 * @author haokang
 * 
 *交易付款审核查询
 */
public class TransactionPayment {
	private Integer paymentId;//支付Id
	private String productName;//商品名称
	private String sellerCompanyName;//卖家企业名称
	private String buyerCompanyName;//买家企业名称
	private Double dealMoney;//交易金额
	private Double freezeMoney;//冻结金额
	private String auditTime;//审核时间
	private Integer auditStatus;//审核状态 0 待审核， 1，通过， 2，已拒绝
	private Integer confirmId;
	private String createTime;//交易记录创建时间
	private String businessAuditUser;//商城审核人
	private String paymentNo;//支付编码
	private String mallAuditTime;//商城审核时间
	
	
	public String getMallAuditTime() {
		return mallAuditTime;
	}
	public void setMallAuditTime(String mallAuditTime) {
		this.mallAuditTime = mallAuditTime;
	}
	public String getBusinessAuditUser() {
		return businessAuditUser;
	}
	public void setBusinessAuditUser(String businessAuditUser) {
		this.businessAuditUser = businessAuditUser;
	}
	public String getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getConfirmId() {
		return confirmId;
	}
	public void setConfirmId(Integer confirmId) {
		this.confirmId = confirmId;
	}
	public Integer getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSellerCompanyName() {
		return sellerCompanyName;
	}
	public void setSellerCompanyName(String sellerCompanyName) {
		this.sellerCompanyName = sellerCompanyName;
	}
	public String getBuyerCompanyName() {
		return buyerCompanyName;
	}
	public void setBuyerCompanyName(String buyerCompanyName) {
		this.buyerCompanyName = buyerCompanyName;
	}
	public Double getDealMoney() {
		return dealMoney;
	}
	public void setDealMoney(Double dealMoney) {
		this.dealMoney = dealMoney;
	}
	public Double getFreezeMoney() {
		return freezeMoney;
	}
	public void setFreezeMoney(Double freezeMoney) {
		this.freezeMoney = freezeMoney;
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
	
	
}
