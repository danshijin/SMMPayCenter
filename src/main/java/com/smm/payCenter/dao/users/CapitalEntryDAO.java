package com.smm.payCenter.dao.users;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.PaymentRecord;
import com.smm.payCenter.domain.TransactionDetail;

/**
 * 
 * @author hece
 *
 *	
 */

public interface CapitalEntryDAO {

	List<PaymentRecord> queryCapitalEntryList(Map<String, Object> paymap);
	TransactionDetail capitalEntryDetail(Integer paymentid);
	int selectPaymentNum(Map<String, Object> paymap);
	void updateConfirmStatus(Map<String, Object> map);
	
	List<PaymentRecord> queryRefundList(Map<String, Object> paymap);
	
	Integer queryRefundAccount(Map<String, Object> paymap);
}
