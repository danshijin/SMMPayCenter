package com.smm.payCenter.dao;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.PaymentRecord;
import com.smm.payCenter.domain.TransactionDetail;

/**
 * 
 * @author hece
 *
 */
public interface PayFreezeDAO {

	List<PaymentRecord> queryPayFreezeList(Map<String, Object> paymap);
	TransactionDetail payFreezeDetail(Integer paymentid);
	int queryPayFreezeNum(Map<String, Object> paymap);
	void closeOrder(Integer paymentid);
}
