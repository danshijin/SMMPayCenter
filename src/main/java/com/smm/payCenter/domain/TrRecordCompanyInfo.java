package com.smm.payCenter.domain;

import java.io.Serializable;

public class TrRecordCompanyInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8889750731714415182L;
	
	private Double totalMoney;//昨日总额
	private Double money0;//0 转入总额
	private Double money1;//1 转出总额
	private Double money2;//2 出金总额
	private Double money3;//3 入金总额
	private Double smmFreezeMoney;//待出金金额
	
	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Double getMoney0() {
		return money0;
	}
	public void setMoney0(Double money0) {
		this.money0 = money0;
	}
	public Double getMoney1() {
		return money1;
	}
	public void setMoney1(Double money1) {
		this.money1 = money1;
	}
	public Double getMoney2() {
		return money2;
	}
	public void setMoney2(Double money2) {
		this.money2 = money2;
	}
	public Double getMoney3() {
		return money3;
	}
	public void setMoney3(Double money3) {
		this.money3 = money3;
	}
	public Double getSmmFreezeMoney() {
		return smmFreezeMoney;
	}
	public void setSmmFreezeMoney(Double smmFreezeMoney) {
		this.smmFreezeMoney = smmFreezeMoney;
	}
	
	
	

}
