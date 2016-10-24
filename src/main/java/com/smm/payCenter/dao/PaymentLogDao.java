package com.smm.payCenter.dao;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.PaymentLog;

public interface PaymentLogDao {

	int queryPaymentLogCount(Map<String, Object> map);

	List<PaymentLog> queryPaymentLogData(Map<String, Object> map);

}
