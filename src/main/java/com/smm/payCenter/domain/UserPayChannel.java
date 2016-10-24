package com.smm.payCenter.domain;

import java.io.Serializable;

/**
 * 会员支付帐号信息
 * @author tantaigen
 *
 */

public class UserPayChannel implements Serializable{
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	private Integer userPayChannelId;//用户支付渠道ID
	private Integer userId;//用户支付ID
	private Integer payChannelId;//支付渠道ID
	private String userAccountNo;//支付渠道账号
	private String userAccountName;//附属账户名称
	private Integer userAccountStatus;//账户状态 0 正常， 1 关闭
	private String freezeMoney;////冻结金额
	private String useMoney;//可用金额
	private String createTime;//开通时间
	public Integer getUserPayChannelId() {
		return userPayChannelId;
	}
	public void setUserPayChannelId(Integer userPayChannelId) {
		this.userPayChannelId = userPayChannelId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getPayChannelId() {
		return payChannelId;
	}
	public void setPayChannelId(Integer payChannelId) {
		this.payChannelId = payChannelId;
	}
	public String getUserAccountNo() {
		return userAccountNo;
	}
	public void setUserAccountNo(String userAccountNo) {
		this.userAccountNo = userAccountNo;
	}
	public String getUserAccountName() {
		return userAccountName;
	}
	public void setUserAccountName(String userAccountName) {
		this.userAccountName = userAccountName;
	}
	public Integer getUserAccountStatus() {
		return userAccountStatus;
	}
	public void setUserAccountStatus(Integer userAccountStatus) {
		this.userAccountStatus = userAccountStatus;
	}
	public String getFreezeMoney() {
		return freezeMoney;
	}
	public void setFreezeMoney(String freezeMoney) {
		this.freezeMoney = freezeMoney;
	}
	public String getUseMoney() {
		return useMoney;
	}
	public void setUseMoney(String useMoney) {
		this.useMoney = useMoney;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	

}
