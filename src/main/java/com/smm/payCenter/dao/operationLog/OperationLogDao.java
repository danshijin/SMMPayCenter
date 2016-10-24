/**
 * 
 */
package com.smm.payCenter.dao.operationLog;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.OperationLog;

/**
 * OperationLogDao
 *
 * Copyright 2015 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2015年11月16日
 */
public interface OperationLogDao {

		List<OperationLog> queryLog(Map<String, String> map);
		
		int queryAccount(Map<String, String> map);
}
