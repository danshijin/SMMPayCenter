package com.smm.payCenter.bo.payFreeze;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.PaymentRecord;
import com.smm.payCenter.domain.TransactionDetail;


/**
 * 
 * @author hece
 *
 */
public interface PayFreezeBO {

	//冻结列表
	List<PaymentRecord> payFreezeList(Map<String,Object> paymap);
	//冻结详情
	TransactionDetail payFreezeDetail(Integer paymentid);
	//查询总条数
	int queryPayFreezeNum(Map<String,Object> paymap);
	//关闭订单
	void closeOrder(Integer paymentid);
}
