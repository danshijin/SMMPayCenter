package com.smm.payCenter.domain;

import java.math.BigDecimal;

public class TransactionDetail {
	private String mallOrderId;//订单编号
	private String mallAuditStatus;//商城审核状态
	private String mallAuditReason;//商城审核失败原因
	private Integer auditUserId;//审核人
	private String auditTime;//审核时间
	private Integer paymentId;//支付记录ID
	private Integer confirmId;//审核ID
	private String createTime;//交易记录创建时间(付款申请时间)
	private String sellerCompanyName;//卖家公司名称
	private String sellUserAccountNo;//卖方帐号
	private String buyerCompanyName;//买家公司名称
	private String buyUserAccountNo;//买方帐号
	private String productName;//商品名称
	private BigDecimal productNum;//商品数量
	private String productNumUnit;//商品数量单位
	private String productDetail;//商品明细
	private Double dealMoney;//交易金额
	private String orderCreateTime;//订单创建时间(采购时间)
	private String settlementType;//交收方式
	private String dealType;//交易类型
	private String invoice;//发票说明
	private String trWaterId;//交易流水号（20为数字+字母）(交易编号,资金流水号)
	private String paymentType;//付款方式
	private String freezeTime;//冻结时间(付款时间)
	private String paymentCode;//支付码
	
	private Integer paymentStatus;//支付状态 0 待付款 1 已冻结 2 已完成 3 已关闭
	
	private String doneTime;//完成时间
	
	private Integer auditStatus;//审核状态
	private String paymentNo;//支付编码
	
	private String verifyCode;//MD5验证
	
	private Integer buyerUserId;//买家Id
	private Integer sellerUserId;//卖家Id
	
	private String sellerMallUserName;
	private String buyerMallUserName;
	
	private Integer respositoryStatus;//1 仓库未监管 2 仓库已确认 3 未过户 
	private String businessAduitUser;//业务审核人
	
	private Integer payType;//支付类型(0 商城订单 1 买家补款 2卖家退款)


	
	
	public BigDecimal getProductNum() {
		return productNum;
	}

	public void setProductNum(BigDecimal productNum) {
		this.productNum = productNum;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getRespositoryStatus() {
		return respositoryStatus;
	}

	public void setRespositoryStatus(Integer respositoryStatus) {
		this.respositoryStatus = respositoryStatus;
	}

	public String getBusinessAduitUser() {
		return businessAduitUser;
	}

	public void setBusinessAduitUser(String businessAduitUser) {
		this.businessAduitUser = businessAduitUser;
	}

	public String getSellerMallUserName() {
		return sellerMallUserName;
	}

	public void setSellerMallUserName(String sellerMallUserName) {
		this.sellerMallUserName = sellerMallUserName;
	}

	public String getBuyerMallUserName() {
		return buyerMallUserName;
	}

	public void setBuyerMallUserName(String buyerMallUserName) {
		this.buyerMallUserName = buyerMallUserName;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public Integer getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(Integer auditUserId) {
		this.auditUserId = auditUserId;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Integer getConfirmId() {
		return confirmId;
	}

	public void setConfirmId(Integer confirmId) {
		this.confirmId = confirmId;
	}

	public String getMallOrderId() {
		return mallOrderId;
	}

	public void setMallOrderId(String mallOrderId) {
		this.mallOrderId = mallOrderId;
	}

	public String getMallAuditStatus() {
		return mallAuditStatus;
	}

	public void setMallAuditStatus(String mallAuditStatus) {
		this.mallAuditStatus = mallAuditStatus;
	}

	public String getMallAuditReason() {
		return mallAuditReason;
	}

	public void setMallAuditReason(String mallAuditReason) {
		this.mallAuditReason = mallAuditReason;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSellerCompanyName() {
		return sellerCompanyName;
	}

	public void setSellerCompanyName(String sellerCompanyName) {
		this.sellerCompanyName = sellerCompanyName;
	}

	public String getSellUserAccountNo() {
		return sellUserAccountNo;
	}

	public void setSellUserAccountNo(String sellUserAccountNo) {
		this.sellUserAccountNo = sellUserAccountNo;
	}

	public String getBuyerCompanyName() {
		return buyerCompanyName;
	}

	public void setBuyerCompanyName(String buyerCompanyName) {
		this.buyerCompanyName = buyerCompanyName;
	}

	public String getBuyUserAccountNo() {
		return buyUserAccountNo;
	}

	public void setBuyUserAccountNo(String buyUserAccountNo) {
		this.buyUserAccountNo = buyUserAccountNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}



	public String getProductNumUnit() {
		return productNumUnit;
	}

	public void setProductNumUnit(String productNumUnit) {
		this.productNumUnit = productNumUnit;
	}

	public String getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}

	public Double getDealMoney() {
		return dealMoney;
	}

	public void setDealMoney(Double dealMoney) {
		this.dealMoney = dealMoney;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getSettlementType() {
		return settlementType;
	}

	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getTrWaterId() {
		return trWaterId;
	}

	public void setTrWaterId(String trWaterId) {
		this.trWaterId = trWaterId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getFreezeTime() {
		return freezeTime;
	}

	public void setFreezeTime(String freezeTime) {
		this.freezeTime = freezeTime;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(String doneTime) {
		this.doneTime = doneTime;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getBuyerUserId() {
		return buyerUserId;
	}

	public void setBuyerUserId(Integer buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	public Integer getSellerUserId() {
		return sellerUserId;
	}

	public void setSellerUserId(Integer sellerUserId) {
		this.sellerUserId = sellerUserId;
	}
	
	
	
}
