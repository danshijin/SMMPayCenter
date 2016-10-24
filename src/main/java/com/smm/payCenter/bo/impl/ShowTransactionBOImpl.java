package com.smm.payCenter.bo.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.ShowTransactionBO;
import com.smm.payCenter.dao.ShowTransactionDao;
import com.smm.payCenter.domain.PaymentRecord;

@Service
public class ShowTransactionBOImpl implements ShowTransactionBO {
	@Resource
	private ShowTransactionDao transactionDao;

	//查询列表
	@Override
	public List<PaymentRecord> queryList(Map<String, Object> map) {
		List<PaymentRecord> list =null;
		try {
			list = transactionDao.queryList(map);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//根据ID修改状态为已关闭
	@Override
	public Integer updateCloseById(Integer paymentId) {
		Integer count = transactionDao.updateCloseById(paymentId);
		return count;
	}
	
}
