package com.smm.payCenter.domain;

public class TrFreezeRecordEntity {
	private Integer paymentId;
	private Double freezeMoney;
	private String freezeClientId;
	private String applyTime;
	private String verifyCode;
	private Integer freezeStatus;
	
	public Integer getFreezeStatus() {
		return freezeStatus;
	}
	public void setFreezeStatus(Integer freezeStatus) {
		this.freezeStatus = freezeStatus;
	}
	public Integer getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	public Double getFreezeMoney() {
		return freezeMoney;
	}
	public void setFreezeMoney(Double freezeMoney) {
		this.freezeMoney = freezeMoney;
	}

	public String getFreezeClientId() {
		return freezeClientId;
	}
	public void setFreezeClientId(String freezeClientId) {
		this.freezeClientId = freezeClientId;
	}
	
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	
	

}
