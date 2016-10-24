package com.smm.payCenter.domain;

import java.math.BigDecimal;
import java.security.Timestamp;

/**
 * 
 * @author hece
 *  每日结算表
 */
public class DayBalance {

	private Integer id;
	private Integer account;//用户账号,平台为0
	private BigDecimal useMoney;//可用余额
	private BigDecimal freezeMoney;//冻结金额
	private BigDecimal totalMoney;//总金额
	private BigDecimal payMoney;//支付
	private BigDecimal recvMoney;//收款
	private BigDecimal incomeMoney;//入金
	private BigDecimal outcomeMoney;//出金
	private Timestamp createTime;//创建时间
	
	private BigDecimal yesterdayFreezeMoney;//昨日冻结金额
	private BigDecimal yesterdayUseMoney;//昨日可用余额
	private BigDecimal yesterdayTotalMoney;//昨日总余额
	private String companyName;//公司名称
	private String userName;//用户登陆名
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigDecimal getYesterdayTotalMoney() {
		return yesterdayTotalMoney;
	}
	public void setYesterdayTotalMoney(BigDecimal yesterdayTotalMoney) {
		this.yesterdayTotalMoney = yesterdayTotalMoney;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public BigDecimal getYesterdayUseMoney() {
		return yesterdayUseMoney;
	}
	public void setYesterdayUseMoney(BigDecimal yesterdayUseMoney) {
		this.yesterdayUseMoney = yesterdayUseMoney;
	}
	public BigDecimal getYesterdayFreezeMoney() {
		return yesterdayFreezeMoney;
	}
	public void setYesterdayFreezeMoney(BigDecimal yesterdayFreezeMoney) {
		this.yesterdayFreezeMoney = yesterdayFreezeMoney;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAccount() {
		return account;
	}
	public void setAccount(Integer account) {
		this.account = account;
	}
	public BigDecimal getUseMoney() {
		return useMoney;
	}
	public void setUseMoney(BigDecimal useMoney) {
		this.useMoney = useMoney;
	}
	public BigDecimal getFreezeMoney() {
		return freezeMoney;
	}
	public void setFreezeMoney(BigDecimal freezeMoney) {
		this.freezeMoney = freezeMoney;
	}
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	public BigDecimal getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}
	public BigDecimal getRecvMoney() {
		return recvMoney;
	}
	public void setRecvMoney(BigDecimal recvMoney) {
		this.recvMoney = recvMoney;
	}
	public BigDecimal getIncomeMoney() {
		return incomeMoney;
	}
	public void setIncomeMoney(BigDecimal incomeMoney) {
		this.incomeMoney = incomeMoney;
	}
	public BigDecimal getOutcomeMoney() {
		return outcomeMoney;
	}
	public void setOutcomeMoney(BigDecimal outcomeMoney) {
		this.outcomeMoney = outcomeMoney;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
	
}
