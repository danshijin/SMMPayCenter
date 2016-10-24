package com.smm.payCenter.domain;//

import java.math.BigDecimal;

/** 交易记录表
 * @ClassName: TrRecord 
 * @Description: TODO
 * @author: 
 * @date: 2015年11月12日 上午10:27:53  
 */
public class TrRecord {

	private Integer tr_id;// 交易ID
	private BigDecimal tr_money;// 交易金额
	private Integer tr_type;// 交易类型 0 转入 1 转出 2 出金 3 入金 4 入金退款
	private String tr_apply_time;// 交易请求时间
	private Integer tr_apply_status;// 交易请求状态 0 请求中，1 成功，2 失败
	private String user_name;// 用户名
	private BigDecimal user_money;// 本交易执行后用户账户余额
	private Integer user_id;// 支付用户ID
	private BigDecimal user_pay_channel_id;// 用户支付渠道ID
	private String user_company_name;// 公司名称
	private String outcome_bank_name;// 出金银行名称
	private String outcome_bank_account_no;// 出金银行卡号
	private String use_money;// 余额
	private Integer audit_status;// 出金审核状态
	private String note;// 摘要
	
	

	public Integer getTr_id() {
		return tr_id;
	}

	public void setTr_id(Integer tr_id) {
		this.tr_id = tr_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_company_name() {
		return user_company_name;
	}

	public void setUser_company_name(String user_company_name) {
		this.user_company_name = user_company_name;
	}

	public BigDecimal getTr_money() {
		return tr_money;
	}

	public void setTr_money(BigDecimal tr_money) {
		this.tr_money = tr_money;
	}

	public String getOutcome_bank_name() {
		return outcome_bank_name;
	}

	public void setOutcome_bank_name(String outcome_bank_name) {
		this.outcome_bank_name = outcome_bank_name;
	}

	public String getOutcome_bank_account_no() {
		return outcome_bank_account_no;
	}

	public void setOutcome_bank_account_no(String outcome_bank_account_no) {
		this.outcome_bank_account_no = outcome_bank_account_no;
	}

	public String getUse_money() {
		return use_money;
	}

	public void setUse_money(String use_money) {
		this.use_money = use_money;
	}

	public Integer getAudit_status() {
		return audit_status;
	}

	public void setAudit_status(Integer audit_status) {
		this.audit_status = audit_status;
	}

	public Integer getTr_type() {
		return tr_type;
	}

	public void setTr_type(Integer tr_type) {
		this.tr_type = tr_type;
	}

	public String getTr_apply_time() {
		return tr_apply_time;
	}

	public void setTr_apply_time(String tr_apply_time) {
		this.tr_apply_time = tr_apply_time;
	}

	public Integer getTr_apply_status() {
		return tr_apply_status;
	}

	public void setTr_apply_status(Integer tr_apply_status) {
		this.tr_apply_status = tr_apply_status;
	}

	public BigDecimal getUser_money() {
		return user_money;
	}

	public void setUser_money(BigDecimal user_money) {
		this.user_money = user_money;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public BigDecimal getUser_pay_channel_id() {
		return user_pay_channel_id;
	}

	public void setUser_pay_channel_id(BigDecimal user_pay_channel_id) {
		this.user_pay_channel_id = user_pay_channel_id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
