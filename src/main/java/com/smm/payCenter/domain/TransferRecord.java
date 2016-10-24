/**
 * 
 */
package com.smm.payCenter.domain;

/**
 * TransferRecord
 *
 * Copyright 2015 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2015年12月14日
 */
public class TransferRecord {
//	id 						 	int(11)
//	client_id  		 			varchar(20)  	client_id
//	payment_id  	 			int(11) 	        支付记录编号
//	pay_channel_id   			int(11)  		支付渠道编号
//	out_user_id		 			int(11)			转出会员id
//	out_user_company_name		varchar(50)		转出会员企业名称
//	out_user_channel_id			int(11)			转出会员渠道关联编号
//	in_user_id					int(11)			转入会员id
//	in_user_company_name		varchar(50)		转入会员企业名称
//	in_user_channel_id			int(11)			转入会员渠道关联编号
//	apply_time					varchar(20)		请求时间
//	response_time				varchar(20)		反馈时间
//	status						int(2)			交易状态。0 请求中，1 成功， 2 失败
//	desc						varchar(200)	转账备注及失败原因
	private int id;
	
	private String  clientId;
	
	private int paymentId;
	
	private int payChannelId;
	
	private int outUserId;
	
	private String outUserCompanyName;
	
	private int outUserChannelId;
	
	private int inUserId;
	
	private String inUserCompanyName;
	
	private int inUserChannelId;
	
	private String applyTime;
	
	private String responseTime;
	
	private int status;
	
	private String desc;

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
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the paymentId
	 */
	public int getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * @return the payChannelId
	 */
	public int getPayChannelId() {
		return payChannelId;
	}

	/**
	 * @param payChannelId the payChannelId to set
	 */
	public void setPayChannelId(int payChannelId) {
		this.payChannelId = payChannelId;
	}

	/**
	 * @return the outUserId
	 */
	public int getOutUserId() {
		return outUserId;
	}

	/**
	 * @param outUserId the outUserId to set
	 */
	public void setOutUserId(int outUserId) {
		this.outUserId = outUserId;
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
	 * @return the outUserChannelId
	 */
	public int getOutUserChannelId() {
		return outUserChannelId;
	}

	/**
	 * @param outUserChannelId the outUserChannelId to set
	 */
	public void setOutUserChannelId(int outUserChannelId) {
		this.outUserChannelId = outUserChannelId;
	}

	/**
	 * @return the inUserId
	 */
	public int getInUserId() {
		return inUserId;
	}

	/**
	 * @param inUserId the inUserId to set
	 */
	public void setInUserId(int inUserId) {
		this.inUserId = inUserId;
	}

	/**
	 * @return the inUserCompanyName
	 */
	public String getInUserCompanyName() {
		return inUserCompanyName;
	}

	/**
	 * @param inUserCompanyName the inUserCompanyName to set
	 */
	public void setInUserCompanyName(String inUserCompanyName) {
		this.inUserCompanyName = inUserCompanyName;
	}

	/**
	 * @return the inUserChannelId
	 */
	public int getInUserChannelId() {
		return inUserChannelId;
	}

	/**
	 * @param inUserChannelId the inUserChannelId to set
	 */
	public void setInUserChannelId(int inUserChannelId) {
		this.inUserChannelId = inUserChannelId;
	}

	/**
	 * @return the applyTime
	 */
	public String getApplyTime() {
		return applyTime;
	}

	/**
	 * @param applyTime the applyTime to set
	 */
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	/**
	 * @return the responseTime
	 */
	public String getResponseTime() {
		return responseTime;
	}

	/**
	 * @param responseTime the responseTime to set
	 */
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
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
	
	
	
	
}
