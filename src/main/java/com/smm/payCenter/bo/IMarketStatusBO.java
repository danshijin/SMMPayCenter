package com.smm.payCenter.bo;

import java.util.Map;

import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.TrMarketLog;

/**
* @author  zhaoyutao
* @version 2015年12月8日 下午5:18:27
* 类说明
*/

public interface IMarketStatusBO {
	public boolean isMarketOpen();
	
	public Map<String, Object> updateMarketOpeningTime(String startTime, String endTime, Account account);

	public int opentMarket();

	public int closeMarket();

	public int insertLog(TrMarketLog log);
}
