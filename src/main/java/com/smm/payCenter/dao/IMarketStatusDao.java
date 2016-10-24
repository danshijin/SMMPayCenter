package com.smm.payCenter.dao;

import com.smm.payCenter.domain.TrMarketLog;

/**
* @author  zhaoyutao
* @version 2015年12月8日 下午5:20:58
* 类说明
*/

public interface IMarketStatusDao {
	public int getMarketStatus();
	
	public int opentMarket();
	
	public int closeMarket();
	
	public int insertLog(TrMarketLog log);
}
