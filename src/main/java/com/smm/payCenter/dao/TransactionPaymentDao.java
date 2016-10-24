package com.smm.payCenter.dao;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.TransactionDetail;
import com.smm.payCenter.domain.TransactionPayment;

public interface TransactionPaymentDao {
	//查询交易付款审核列表
	public List<TransactionPayment> query(Map<String, Object> map);
	//查询交易付款审核记录数
	public Integer queryCount(Map<String, Object> map);
	//根据Id查询详情
	public TransactionDetail queryDetailById(Integer id);
	//修改商城审核状态为通过
	public void updateConfirmStatus(Map<String,Object> map);
	//审核成功加入日志表
	public void insertPaymentLog(Map<String,Object> resultmap);
	
	//查看审核记录
	public Integer queryConfirmStatus(Integer ConfirmId );
	
	//查询人工转账详情
	public TransactionDetail queryById(Integer id);
}
