/**
 * 
 */
package com.smm.payCenter.dao;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.TransferRecord;

/**
 * TransferRecordDao
 *
 * Copyright 2015 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2015年12月14日
 */
public interface TransferRecordDao {
	List<TransferRecord> queryList();
}
