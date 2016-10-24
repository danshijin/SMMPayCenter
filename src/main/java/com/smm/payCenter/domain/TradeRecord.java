package com.smm.payCenter.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/** 交易记录表
 * @ClassName: TradeRecord 
 * @Description: TODO
 * @author: zhangnan
 * @date: 2015年11月12日 上午10:39:45  
 */
public class TradeRecord implements Serializable{
   
    /**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer trId;//交易ID

    private BigDecimal trMoney;//交易金额

    private Integer trType;//交易类型 0 转入 1 转出 2 出金 3 入金 4 入金退款

    private String trApplyTime;//交易请求时间

    private Integer trApplyStatus;//交易请求状态 0 请求中，1 成功，2 失败

    private BigDecimal trCharge;//交易手续费

    private String trWaterId;//交易流水号(支付系统交易流水号，可用来做client_id)

    private BigDecimal userMoney;//本交易执行后用户账户可用余额

    private Integer userId;//支付用户ID

    private Integer userPayChannelId;//用户支付渠道ID

    private String userCompanyName;//用户公司名称

    private Integer oppositUserId;//对方支付用户ID

    private Integer oppositUserPayChannelId;//对方用户支付渠道ID

    private String oppositCompanyName;//对方用户公司名称

    private String outcomeBankName;//出金银行名称

    private String outcomeBankAccountNo;//出金银行卡号

    private String outcomeBankCnaps;//出金银行支行联行号

    private String printCheckCode;//打印校验码

    private Integer payChannelId;//支付渠道ID

    private String note;//摘要

    private String replayTime;//反馈时间

    private String payChannelTrTime;//

    private String payChannelTrNo;//支付渠道反馈的交易时间

    private Integer auditStatus;//支付渠道返回的柜员交易号

    private Integer auditUserId;//本交易执行成功后用户账户冻结金额
    
    private String trTypes;// 交易类型
    
    
    
    private String otherAccountNo;//交易对方账号
    

	public Integer getTrId() {
		return trId;
	}

	public void setTrId(Integer trId) {
		this.trId = trId;
	}

	public BigDecimal getTrMoney() {
		return trMoney;
	}

	public void setTrMoney(BigDecimal trMoney) {
		this.trMoney = trMoney;
	}

	public Integer getTrType() {
		return trType;
	}

	public void setTrType(Integer trType) {
		this.trType = trType;
	}

	public String getTrApplyTime() {
		return trApplyTime;
	}

	public void setTrApplyTime(String trApplyTime) {
		this.trApplyTime = trApplyTime;
	}

	public Integer getTrApplyStatus() {
		return trApplyStatus;
	}

	public void setTrApplyStatus(Integer trApplyStatus) {
		this.trApplyStatus = trApplyStatus;
	}

	public BigDecimal getTrCharge() {
		return trCharge;
	}

	public void setTrCharge(BigDecimal trCharge) {
		this.trCharge = trCharge;
	}

	public String getTrWaterId() {
		return trWaterId;
	}

	public void setTrWaterId(String trWaterId) {
		this.trWaterId = trWaterId;
	}

	public BigDecimal getUserMoney() {
		return userMoney;
	}

	public void setUserMoney(BigDecimal userMoney) {
		this.userMoney = userMoney;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserPayChannelId() {
		return userPayChannelId;
	}

	public void setUserPayChannelId(Integer userPayChannelId) {
		this.userPayChannelId = userPayChannelId;
	}

	public String getUserCompanyName() {
		return userCompanyName;
	}

	public void setUserCompanyName(String userCompanyName) {
		this.userCompanyName = userCompanyName;
	}

	public Integer getOppositUserId() {
		return oppositUserId;
	}

	public void setOppositUserId(Integer oppositUserId) {
		this.oppositUserId = oppositUserId;
	}

	public Integer getOppositUserPayChannelId() {
		return oppositUserPayChannelId;
	}

	public void setOppositUserPayChannelId(Integer oppositUserPayChannelId) {
		this.oppositUserPayChannelId = oppositUserPayChannelId;
	}

	public String getOppositCompanyName() {
		return oppositCompanyName;
	}

	public void setOppositCompanyName(String oppositCompanyName) {
		this.oppositCompanyName = oppositCompanyName;
	}

	public String getOutcomeBankName() {
		return outcomeBankName;
	}

	public void setOutcomeBankName(String outcomeBankName) {
		this.outcomeBankName = outcomeBankName;
	}

	public String getOutcomeBankAccountNo() {
		return outcomeBankAccountNo;
	}

	public void setOutcomeBankAccountNo(String outcomeBankAccountNo) {
		this.outcomeBankAccountNo = outcomeBankAccountNo;
	}

	public String getOutcomeBankCnaps() {
		return outcomeBankCnaps;
	}

	public void setOutcomeBankCnaps(String outcomeBankCnaps) {
		this.outcomeBankCnaps = outcomeBankCnaps;
	}

	public String getPrintCheckCode() {
		return printCheckCode;
	}

	public void setPrintCheckCode(String printCheckCode) {
		this.printCheckCode = printCheckCode;
	}

	public Integer getPayChannelId() {
		return payChannelId;
	}

	public void setPayChannelId(Integer payChannelId) {
		this.payChannelId = payChannelId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getReplayTime() {
		return replayTime;
	}

	public void setReplayTime(String replayTime) {
		this.replayTime = replayTime;
	}

	public String getPayChannelTrTime() {
		return payChannelTrTime;
	}

	public void setPayChannelTrTime(String payChannelTrTime) {
		this.payChannelTrTime = payChannelTrTime;
	}

	public String getPayChannelTrNo() {
		return payChannelTrNo;
	}

	public void setPayChannelTrNo(String payChannelTrNo) {
		this.payChannelTrNo = payChannelTrNo;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(Integer auditUserId) {
		this.auditUserId = auditUserId;
	}

	public String getOtherAccountNo() {
		return otherAccountNo;
	}

	public void setOtherAccountNo(String otherAccountNo) {
		this.otherAccountNo = otherAccountNo;
	}

	public void setTrMoney(String string) {
		// TODO Auto-generated method stub
		
	}

	public String getTrTypes() {
		return trTypes;
	}

	public void setTrTypes(String trTypes) {
		this.trTypes = trTypes;
	}
    
    

   
}