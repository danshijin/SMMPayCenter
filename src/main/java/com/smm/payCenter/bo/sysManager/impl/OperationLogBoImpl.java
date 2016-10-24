package com.smm.payCenter.bo.sysManager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.sysManager.OperationLogBo;
import com.smm.payCenter.dao.operationLog.OperationLogDao;
import com.smm.payCenter.domain.OperationLog;

@Service
public class OperationLogBoImpl implements OperationLogBo {
	
	@Resource
	private OperationLogDao operationLogDao;

	@Override
	public List<OperationLog> queryLog(Map<String, String> map) {
		return this.operationLogDao.queryLog(map);
	}

	/* (non-Javadoc)
	 * @see com.smm.payCenter.bo.sysManager.OperationLogBo#queryAccount(java.util.Map)
	 */
	@Override
	public int queryAccount(Map<String, String> map) {
		return operationLogDao.queryAccount(map);
	}


}
