package com.smm.payCenter.bo.tradeRecord;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.TradeRecord;
import com.smm.payCenter.domain.UserPayChannel;

public interface TradeRecordBo {

	int queryTradeRecordCount(Map<String, Object> map);

	List<TradeRecord> queryTradeRecord(Map<String, Object> map);

	List<UserPayChannel> getUserPayChannel(Map<String, Object> map);

	List<UserPayChannel> getUserPayChannelByUserId(Map<String, Object> map);

}
