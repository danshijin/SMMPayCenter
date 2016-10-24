package com.smm.payCenter.bo.sysManager;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.OperationLog;

public interface OperationLogBo {

	List<OperationLog> queryLog(Map<String, String> map);
	
	int queryAccount(Map<String, String> map);

}
