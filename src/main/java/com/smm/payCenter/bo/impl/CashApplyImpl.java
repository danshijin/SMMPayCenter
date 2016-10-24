package com.smm.payCenter.bo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.CashApplyBo;
import com.smm.payCenter.dao.CashApplyDao;
import com.smm.payCenter.domain.CashApply;

@Service
public class CashApplyImpl  implements CashApplyBo{

	@Resource
	CashApplyDao cashApplyDao;

	@Override
	public List<CashApply> queryCashApply(CashApply cashApply) {
		// TODO Auto-generated method stub
		return cashApplyDao.queryCashApply(cashApply);
	}

	@Override
	public Integer queryCashApplycount(CashApply cashApply) {
		// TODO Auto-generated method stub
		return cashApplyDao.queryCashApplycount( cashApply);
	}

	@Override
	public Integer updateapplyStatus(CashApply cashApply) {
		// TODO Auto-generated method stub
		return cashApplyDao.updateapplyStatus(cashApply);
	}



	@Override
	public CashApply queryCashApplyId(Integer id) {
		// TODO Auto-generated method stub
		return cashApplyDao.queryCashApplyId(id);
	}

	@Override
	public Integer updateUserPayChannel(CashApply cashApply) {
		// TODO Auto-generated method stub
		return cashApplyDao.updateUserPayChannel(cashApply);
	}

	@Override
	public Integer updateUserAccount(CashApply cashApply) {
		// TODO Auto-generated method stub
		return cashApplyDao.updateUserAccount(cashApply);
	}
	
	
}




