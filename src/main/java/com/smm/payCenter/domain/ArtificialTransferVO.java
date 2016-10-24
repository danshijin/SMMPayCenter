package com.smm.payCenter.domain;

/**
 * 
 * @author hece
 *	人工转账
 */
public class ArtificialTransferVO {
	
	private String createTime;//退款时间
	private Integer paymentId;//支付ID
	private String paymentNo;//支付编号
	private String mallOrderId;//订单编号
	private String outUserCompanyName;//退款方企业名称
	private String userAccountNo;//企业账号
	private String userChannelId;//企业账号
	private Double dealMoney;//交易金额
	private String reason;//失败原因
//	private Integer paymentStatus;//支付状态
	private Integer status;//支付状态
	
	
	
	public String getUserAccountNo() {
		return userAccountNo;
	}
	public void setUserAccountNo(String userAccountNo) {
		this.userAccountNo = userAccountNo;
	}
	/**
	 * @return the orderCreateTime
	 */
	/**
	 * @return the paymentId
	 */
	public Integer getPaymentId() {
		return paymentId;
	}
	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	/**
	 * @return the paymentNo
	 */
	public String getPaymentNo() {
		return paymentNo;
	}
	/**
	 * @param paymentNo the paymentNo to set
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	/**
	 * @return the outUserCompanyName
	 */
	public String getOutUserCompanyName() {
		return outUserCompanyName;
	}
	/**
	 * @param outUserCompanyName the outUserCompanyName to set
	 */
	public void setOutUserCompanyName(String outUserCompanyName) {
		this.outUserCompanyName = outUserCompanyName;
	}
	/**
	 * @return the userChannelId
	 */
	public String getUserChannelId() {
		return userChannelId;
	}
	/**
	 * @param userChannelId the userChannelId to set
	 */
	public void setUserChannelId(String userChannelId) {
		this.userChannelId = userChannelId;
	}
	/**
	 * @return the dealMoney
	 */
	public Double getDealMoney() {
		return dealMoney;
	}
	/**
	 * @param dealMoney the dealMoney to set
	 */
	public void setDealMoney(Double dealMoney) {
		this.dealMoney = dealMoney;
	}
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMallOrderId() {
		return mallOrderId;
	}
	public void setMallOrderId(String mallOrderId) {
		this.mallOrderId = mallOrderId;
	}
	

	
	
}
