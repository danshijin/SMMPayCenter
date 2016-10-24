package com.smm.payCenter.bo.tradeRecord.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.tradeRecord.TradeRecordBo;
import com.smm.payCenter.dao.UserPayChannelDao;
import com.smm.payCenter.dao.tradeRecord.TradeRecordDao;
import com.smm.payCenter.domain.TradeRecord;
import com.smm.payCenter.domain.UserPayChannel;
import com.smm.payCenter.util.StringUtil;

@Service
public class TradeRecordBoImpl implements TradeRecordBo {
	
	@Resource 
	private TradeRecordDao tradeRecordDao;
	
	@Resource
	private UserPayChannelDao userPayChannelDao;

	@Override
	public int queryTradeRecordCount(Map<String, Object> map) {
		return this.tradeRecordDao.queryTradeRecordCount(map);
	}

	@Override
	public List<TradeRecord> queryTradeRecord(Map<String, Object> map) {
		return this.tradeRecordDao.queryTradeRecord(map);
	}

	@Override
	public List<UserPayChannel> getUserPayChannel(Map<String, Object> map) {
		return this.userPayChannelDao.getUserPayChannel(map);
	}

	@Override
	public List<UserPayChannel> getUserPayChannelByUserId(
			Map<String, Object> map) {
		return this.userPayChannelDao.getUserPayChannelByUserId(map);
	}

}
