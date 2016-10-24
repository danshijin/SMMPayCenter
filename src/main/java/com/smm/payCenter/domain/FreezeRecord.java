package com.smm.payCenter.domain;

import java.security.Timestamp;

/**
 * 
 * @author hece
 *
 *  冻结记录
 */
public class FreezeRecord {
	
	private Integer freezeid;
	private String freezeno;
	private Double freezemoney ;
	private Timestamp applytime;
	private Timestamp replytime;
	private Integer freezestatus;
	private String freezenote;
	private Timestamp unfreezeapplytime;
	private Timestamp unfreezereplytime;
	private Integer unfreezestatus;
	private Integer userid;
	private String username;
	private String unfreezenote;
	private Integer paymentid;
	private Integer paychannelid;
	private Integer userpaychannelid;
	public Integer getFreezeid() {
		return freezeid;
	}
	public void setFreezeid(Integer freezeid) {
		this.freezeid = freezeid;
	}
	public String getFreezeno() {
		return freezeno;
	}
	public void setFreezeno(String freezeno) {
		this.freezeno = freezeno;
	}
	public Double getFreezemoney() {
		return freezemoney;
	}
	public void setFreezemoney(Double freezemoney) {
		this.freezemoney = freezemoney;
	}

	public Integer getFreezestatus() {
		return freezestatus;
	}
	public void setFreezestatus(Integer freezestatus) {
		this.freezestatus = freezestatus;
	}
	public String getFreezenote() {
		return freezenote;
	}
	public void setFreezenote(String freezenote) {
		this.freezenote = freezenote;
	}

	public Integer getUnfreezestatus() {
		return unfreezestatus;
	}
	public void setUnfreezestatus(Integer unfreezestatus) {
		this.unfreezestatus = unfreezestatus;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUnfreezenote() {
		return unfreezenote;
	}
	public void setUnfreezenote(String unfreezenote) {
		this.unfreezenote = unfreezenote;
	}
	public Integer getPaymentid() {
		return paymentid;
	}
	public void setPaymentid(Integer paymentid) {
		this.paymentid = paymentid;
	}
	public Integer getPaychannelid() {
		return paychannelid;
	}
	public void setPaychannelid(Integer paychannelid) {
		this.paychannelid = paychannelid;
	}
	public Integer getUserpaychannelid() {
		return userpaychannelid;
	}
	public void setUserpaychannelid(Integer userpaychannelid) {
		this.userpaychannelid = userpaychannelid;
	}
	public Timestamp getApplytime() {
		return applytime;
	}
	public void setApplytime(Timestamp applytime) {
		this.applytime = applytime;
	}
	public Timestamp getReplytime() {
		return replytime;
	}
	public void setReplytime(Timestamp replytime) {
		this.replytime = replytime;
	}
	public Timestamp getUnfreezeapplytime() {
		return unfreezeapplytime;
	}
	public void setUnfreezeapplytime(Timestamp unfreezeapplytime) {
		this.unfreezeapplytime = unfreezeapplytime;
	}
	public Timestamp getUnfreezereplytime() {
		return unfreezereplytime;
	}
	public void setUnfreezereplytime(Timestamp unfreezereplytime) {
		this.unfreezereplytime = unfreezereplytime;
	}
	
	
}
