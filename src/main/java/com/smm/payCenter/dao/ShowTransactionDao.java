package com.smm.payCenter.dao;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.PaymentRecord;

public interface ShowTransactionDao {
	//查询列表
	public List<PaymentRecord> queryList(Map<String, Object> map);
	
	//根据ID修改状态为已关闭
	public Integer updateCloseById(Integer paymentId);
}
