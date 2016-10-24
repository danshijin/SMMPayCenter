package com.smm.payCenter.bo.sysManager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.sysManager.TransferRecordBo;
import com.smm.payCenter.dao.TransferRecordDao;
import com.smm.payCenter.domain.TransferRecord;

@Service
public class TransferRecordBoImpl implements TransferRecordBo {
	
	@Resource
	private TransferRecordDao transferRecordDao;

	@Override
	public List<TransferRecord> queryList() {
		return this.transferRecordDao.queryList();
	}

}
