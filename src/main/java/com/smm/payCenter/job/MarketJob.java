package com.smm.payCenter.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.smm.payCenter.bo.IMarketStatusBO;
import com.smm.payCenter.bo.SettlementBO;
import com.smm.payCenter.domain.TrMarketLog;

/**
* @author  zhaoyutao
* @version 2015年12月10日 下午2:05:11
* 定时开市
*/

public class MarketJob {
	
	Logger logger = Logger.getLogger(MarketJob.class);
	
	@Resource
	IMarketStatusBO iMarketStatusBO;
	
	@Resource
	private SettlementBO settle;
	
	private boolean needOpenMarket(){
		boolean need = Boolean.TRUE;
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if(dayOfWeek == 1 || dayOfWeek == 7){
			logger.info("周六日不开市，已跳过");
			need = Boolean.FALSE;
		}
		return need;
	}
		
	public void open(){
		
		// 判断是否需要开市
		if(!needOpenMarket()){
			logger.info("已开市，无需重新开市");
			return;
		}
		logger.info("Job 开市开始");
		int count = iMarketStatusBO.opentMarket();
		
		if(count > 0){
			logger.info("Job 开市成功");
			
			//写入开市日志
			TrMarketLog log = new TrMarketLog();
			log.setOperator(0);
			log.setOperType(1);
			iMarketStatusBO.insertLog(log);
			
		} else {
			logger.info("Job 开市失败");
		}
		
	}
	Map<String, Object> map = null;
	public void close(){
		if(iMarketStatusBO.isMarketOpen()){
			logger.info("Job 闭市开始");
			iMarketStatusBO.closeMarket();
			logger.info("Job 闭市成功");
			
			logger.info("Job 每日结算开始");
			//写入闭市日志
			TrMarketLog log = new TrMarketLog();
			log.setOperator(0);
			log.setOperType(0);
			iMarketStatusBO.insertLog(log);
			// 调用日结程序
			map = settle.settlement(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			logger.info("Job 每日结算结束");
		} else {
			logger.info("已闭市，自动日结取消");
		}
		
	}
	
}
