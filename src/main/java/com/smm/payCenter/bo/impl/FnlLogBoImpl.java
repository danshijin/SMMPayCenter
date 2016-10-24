package com.smm.payCenter.bo.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.FnlLogBO;
import com.smm.payCenter.dao.FnlLogDao;
import com.smm.payCenter.domain.FnlLog;

@Service
public class FnlLogBoImpl implements FnlLogBO{
	@Resource
	private FnlLogDao fnlLogDao;
	//新增管理员操作日志
	@Override
	public void addFnlLog(FnlLog log) {
		fnlLogDao.addFnlLog(log);
	}

}
