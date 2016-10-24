package com.smm.payCenter.bo.sysManager;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.OperationLog;
import com.smm.payCenter.domain.TransferRecord;

public interface TransferRecordBo {

	List<TransferRecord> queryList();
	
}
