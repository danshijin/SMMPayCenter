package com.smm.payCenter.dao.tradeRecord;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.TradeRecord;
import com.smm.payCenter.domain.UserPayChannel;

public interface TradeRecordDao {

	int queryTradeRecordCount(Map<String, Object> map);

	List<TradeRecord> queryTradeRecord(Map<String, Object> map);

	
}
