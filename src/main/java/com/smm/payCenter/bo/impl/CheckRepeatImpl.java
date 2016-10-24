package com.smm.payCenter.bo.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.CheckRepeatBo;
import com.smm.payCenter.dao.CheckRepeatDao;
import com.smm.payCenter.domain.TrFreezeRecordEntity;
import com.smm.payCenter.domain.TransactionDetail;

@Service
public class CheckRepeatImpl implements CheckRepeatBo {
	@Resource
	private CheckRepeatDao dao;
	@Override
	public Integer checkUserAccount(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.checkUserAccount(map);
	}
	@Override
	public Integer checkCashApply(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.checkCashApply(map);
	}
	
	//查询是否存在有效的解冻记录
	@Override
	public Integer checkUnfreezeRecore(Integer paymentId) {
		return dao.checkUnfreezeRecore(paymentId);
	}
	
	//查询是否存在有效的冻结记录
	@Override
	public Integer checkFreezeRecord(Integer paymentId) {
		return dao.checkFreezeRecord(paymentId);
	}
	@Override
	public Integer checkTransferRecord(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.checkTransferRecord(map);   
	}
	@Override
	public TrFreezeRecordEntity queryFreezeRecord(Integer paymentId) {
		// TODO Auto-generated method stub
		return dao.queryFreezeRecord(paymentId);
	}
	@Override
	public TransactionDetail queryDetailById(Integer id) {
		// TODO Auto-generated method stub
		return dao.queryDetailById(id);
	}
	@Override
	public Integer queryCashDetailCount(Integer userId) {
		// TODO Auto-generated method stub
		return dao.queryCashDetailCount(userId);
	}
}
