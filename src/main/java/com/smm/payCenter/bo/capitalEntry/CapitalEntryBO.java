package com.smm.payCenter.bo.capitalEntry;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.PaymentRecord;
import com.smm.payCenter.domain.TransactionDetail;

/**
 * 
 * @author hece
 *
 */
public interface CapitalEntryBO {

	//转账查询列表
	List<PaymentRecord> paymentList(Map<String,Object> paymap);
	//转账详情
	TransactionDetail paymentDetail(Integer paymentid);
	//查询总条数
	int queryPaymentNum(Map<String,Object> paymap);
	//修改审核状态为已拒绝
	public void updateConfirmStatus(Map<String,Object> map);
	//查询退款列表
	List<PaymentRecord> queryRefundList(Map<String, Object> paymap);
	
	Integer queryRefundAccount(Map<String, Object> paymap);
}
