package com.smm.payCenter.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/** 异常交易信息
 * @ClassName: MoneyException 
 * @Description: TODO
 * @author: zhangnan
 * @date: 2015年11月20日 上午9:17:28  
 */
public class MoneyException implements Serializable{
    
    /**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;//

    private String createTime;//异常发生时间

    private Integer userId;//账户编号

    private String userCompanyName;//账户公司名称

    private Integer operate;//发生异常的操作。1，支付冻结，2，查询余额，3，每日结算

    private Integer paymentId;//支付冻结异常对应支付记录编号

    private Integer payChannelId;//支付通道编号

    private String userPayChannelAccount;//会员在支付通道中的账户编号

    private BigDecimal userPayChannelMoney;//会员在支付通道中的可用余额

    private BigDecimal userMoney;//会员在支付系统中的可用余额

    private Integer auditUserId;//处理人id

    private String auditTime;//处理时间

    private Integer auditStatus;//处理状态。0 未处理， 1 已处理
    
    //拓展属性
    private  BigDecimal dealMoney;//交易金额
    
    private String userAccountNo;//支付账号
    
    private String paymentNo;//支付编码

    
    
	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserCompanyName() {
		return userCompanyName;
	}

	public void setUserCompanyName(String userCompanyName) {
		this.userCompanyName = userCompanyName;
	}

	public Integer getOperate() {
		return operate;
	}

	public void setOperate(Integer operate) {
		this.operate = operate;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Integer getPayChannelId() {
		return payChannelId;
	}

	public void setPayChannelId(Integer payChannelId) {
		this.payChannelId = payChannelId;
	}

	public String getUserPayChannelAccount() {
		return userPayChannelAccount;
	}

	public void setUserPayChannelAccount(String userPayChannelAccount) {
		this.userPayChannelAccount = userPayChannelAccount;
	}

	public BigDecimal getUserPayChannelMoney() {
		return userPayChannelMoney;
	}

	public void setUserPayChannelMoney(BigDecimal userPayChannelMoney) {
		this.userPayChannelMoney = userPayChannelMoney;
	}

	public BigDecimal getUserMoney() {
		return userMoney;
	}

	public void setUserMoney(BigDecimal userMoney) {
		this.userMoney = userMoney;
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

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public BigDecimal getDealMoney() {
		return dealMoney;
	}

	public void setDealMoney(BigDecimal dealMoney) {
		this.dealMoney = dealMoney;
	}

	public String getUserAccountNo() {
		return userAccountNo;
	}

	public void setUserAccountNo(String userAccountNo) {
		this.userAccountNo = userAccountNo;
	}
}