package com.smm.payCenter.domain;

import java.io.Serializable;

/**
 * 系统数据查询
 * 
 * @author hanfeihu
 */
public class SystemData implements Serializable {

    /**
     * @fieldName: serialVersionUID
     * @fieldType: long
     * @Description: TODO
     */
    private static final long serialVersionUID = 7238103894030507046L;

    private String            trDate;                                 //日期
    private String            note;                                   //交易备注
    private String            companyName;                            //企业名称
    private String            oppositCompanyName;                     //对方企业名称
    private Double            borrow;                                 //借 
    private Double            loan;                                   //贷
    private Double            freezeMoney;                            //交易后余额
    private Double            userMoney;                              //交易后余额
    private Double            totalMoney;                             //总余额
    private String            trType;                                 //类型
    private String            companyMail;                            //企业邮箱
    private String 			  userAccountNo;						  //支付渠道账号

    
    
    public String getUserAccountNo() {
		return userAccountNo;
	}

	public void setUserAccountNo(String userAccountNo) {
		this.userAccountNo = userAccountNo;
	}

	public String getTrDate() {
        return trDate;
    }

    public void setTrDate(String trDate) {
        this.trDate = trDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOppositCompanyName() {
        return oppositCompanyName;
    }

    public void setOppositCompanyName(String oppositCompanyName) {
        this.oppositCompanyName = oppositCompanyName;
    }

    public Double getBorrow() {
        return borrow;
    }

    public void setBorrow(Double borrow) {
        this.borrow = borrow;
    }

    public Double getLoan() {
        return loan;
    }

    public void setLoan(Double loan) {
        this.loan = loan;
    }

    public Double getFreezeMoney() {
        return freezeMoney;
    }

    public void setFreezeMoney(Double freezeMoney) {
        this.freezeMoney = freezeMoney;
    }

    public Double getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(Double userMoney) {
        this.userMoney = userMoney;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getTrType() {
        return trType;
    }

    public void setTrType(String trType) {
        this.trType = trType;
    }

    public String getCompanyMail() {
        return companyMail;
    }

    public void setCompanyMail(String companyMail) {
        this.companyMail = companyMail;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
