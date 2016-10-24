package com.smm.payCenter.domain;

import java.util.Date;


/**
 * 
 * @author hece
 *
 *	支付记录
 */
public class PaymentRecord {

	private Integer paymentid;//支付ID
	private Double dealmoney;//交易金额
	private String dealtype;//交易类型
	private String buyercompanyname;//买家公司名称
	private String buyermallusername;//买家商城ID
	private Integer buyerpaychannelid;//买家用户支付渠道ID
	private String buyeruserid;//买家支付用户ID
	private String sellercompanyname;//卖家公司名称
	private String sellermallusername;//卖家商城ID
	private Integer sellerpaychannelid;//卖家用户支付渠道ID
	private String selleruserid;//卖家支付用户ID
	private String mallorderid;//商城订单号
	private String productname;//商品名称
	private Integer productnum;//商品数量
	private String productnumunit;//商品数量单位
	private String productdetail;//商品明细
	private String paymentcode;//支付码
	private Integer paychannelid;//支付渠道ID
	private Integer ishandler;//是否手动处理 0 否，1 是
	private String freezetime;//冻结时间
	private String donetime;//完成时间
	private String createtime;//交易记录创建时间
	private String paymenttime;//订单付款时间
	private String invoice;//发票说明
	private String paymenttype;//付款方式
	private String settlementtype;//交收方式
	private Integer paymentstatus;//支付状态 0 待付款 1 已冻结 2 已完成 3 已关闭
	private String ordercreatetime;//订单创建时间
	private Double freezemoney;//冻结金额
	private Double usemoney;//可用金额
	private Date audittime;//审核时间
	private String paymentNo;//交易编号
	
	private String userName; // 用户账号
	
	private String desc; //错误说明
	
	private String freezeNote;  //冻结备注
	
	
	public String getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	public Double getFreezemoney() {
		return freezemoney;
	}
	public void setFreezemoney(Double freezemoney) {
		this.freezemoney = freezemoney;
	}
	public Date getAudittime() {
		return audittime;
	}
	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}
	public Integer getPaymentid() {
		return paymentid;
	}
	public void setPaymentid(Integer paymentid) {
		this.paymentid = paymentid;
	}
	public Double getDealmoney() {
		return dealmoney;
	}
	public void setDealmoney(Double dealmoney) {
		this.dealmoney = dealmoney;
	}
	public String getDealtype() {
		return dealtype;
	}
	public void setDealtype(String dealtype) {
		this.dealtype = dealtype;
	}
	public String getBuyercompanyname() {
		return buyercompanyname;
	}
	public void setBuyercompanyname(String buyercompanyname) {
		this.buyercompanyname = buyercompanyname;
	}

	public Integer getBuyerpaychannelid() {
		return buyerpaychannelid;
	}
	public void setBuyerpaychannelid(Integer buyerpaychannelid) {
		this.buyerpaychannelid = buyerpaychannelid;
	}
	public String getBuyeruserid() {
		return buyeruserid;
	}
	public void setBuyeruserid(String buyeruserid) {
		this.buyeruserid = buyeruserid;
	}
	public String getSellercompanyname() {
		return sellercompanyname;
	}
	public void setSellercompanyname(String sellercompanyname) {
		this.sellercompanyname = sellercompanyname;
	}

	public Integer getSellerpaychannelid() {
		return sellerpaychannelid;
	}
	public void setSellerpaychannelid(Integer sellerpaychannelid) {
		this.sellerpaychannelid = sellerpaychannelid;
	}
	public String getSelleruserid() {
		return selleruserid;
	}
	public void setSelleruserid(String selleruserid) {
		this.selleruserid = selleruserid;
	}

	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public Integer getProductnum() {
		return productnum;
	}
	public void setProductnum(Integer productnum) {
		this.productnum = productnum;
	}
	public String getProductnumunit() {
		return productnumunit;
	}
	public void setProductnumunit(String productnumunit) {
		this.productnumunit = productnumunit;
	}
	public String getProductdetail() {
		return productdetail;
	}
	public void setProductdetail(String productdetail) {
		this.productdetail = productdetail;
	}
	public String getPaymentcode() {
		return paymentcode;
	}
	public void setPaymentcode(String paymentcode) {
		this.paymentcode = paymentcode;
	}
	public Integer getPaychannelid() {
		return paychannelid;
	}
	public void setPaychannelid(Integer paychannelid) {
		this.paychannelid = paychannelid;
	}
	public Integer getIshandler() {
		return ishandler;
	}
	public void setIshandler(Integer ishandler) {
		this.ishandler = ishandler;
	}

	public String getFreezetime() {
		return freezetime;
	}
	public void setFreezetime(String freezetime) {
		this.freezetime = freezetime;
	}
	public String getDonetime() {
		return donetime;
	}
	public void setDonetime(String donetime) {
		this.donetime = donetime;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getPaymenttime() {
		return paymenttime;
	}
	public void setPaymenttime(String paymenttime) {
		this.paymenttime = paymenttime;
	}
	public String getOrdercreatetime() {
		return ordercreatetime;
	}
	public void setOrdercreatetime(String ordercreatetime) {
		this.ordercreatetime = ordercreatetime;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getSettlementtype() {
		return settlementtype;
	}
	public void setSettlementtype(String settlementtype) {
		this.settlementtype = settlementtype;
	}
	public Integer getPaymentstatus() {
		return paymentstatus;
	}
	public void setPaymentstatus(Integer paymentstatus) {
		this.paymentstatus = paymentstatus;
	}
	public String getBuyermallusername() {
		return buyermallusername;
	}
	public void setBuyermallusername(String buyermallusername) {
		this.buyermallusername = buyermallusername;
	}

	public String getSellermallusername() {
		return sellermallusername;
	}
	public void setSellermallusername(String sellermallusername) {
		this.sellermallusername = sellermallusername;
	}
	public String getMallorderid() {
		return mallorderid;
	}
	public void setMallorderid(String mallorderid) {
		this.mallorderid = mallorderid;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return the usemoney
	 */
	public Double getUsemoney() {
		return usemoney;
	}
	/**
	 * @param usemoney the usemoney to set
	 */
	public void setUsemoney(Double usemoney) {
		this.usemoney = usemoney;
	}
	/**
	 * @return the freezeNote
	 */
	public String getFreezeNote() {
		return freezeNote;
	}
	/**
	 * @param freezeNote the freezeNote to set
	 */
	public void setFreezeNote(String freezeNote) {
		this.freezeNote = freezeNote;
	}
	/**
	 * @return the paymengNo
	 */
	public String getPaymengNo() {
		return paymentNo;
	}
	/**
	 * @param paymengNo the paymengNo to set
	 */
	public void setPaymengNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	
	
}
