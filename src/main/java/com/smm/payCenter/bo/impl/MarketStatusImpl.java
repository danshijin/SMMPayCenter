package com.smm.payCenter.bo.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.IMarketStatusBO;
import com.smm.payCenter.dao.IMarketStatusDao;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.TrMarketLog;

/**
* @author  zhaoyutao
* @version 2015年12月8日 下午5:19:16
* 开闭市
*/
@Service
public class MarketStatusImpl implements IMarketStatusBO {

	@Resource
	IMarketStatusDao marketStatusDao;
	
	private Logger logger = Logger.getLogger(MarketStatusImpl.class);
	
	@Override
	public boolean isMarketOpen() {
		return marketStatusDao.getMarketStatus() == 1;
	}
	
	@Override
	public int opentMarket(){
		return marketStatusDao.opentMarket();
	}
	
	@Override
	public int closeMarket(){
		return marketStatusDao.closeMarket();
	}
	
	@Override
	public int insertLog(TrMarketLog log){
		return marketStatusDao.insertLog(log);
	}
	

	@Override
	public Map<String, Object> updateMarketOpeningTime(String startTime, String endTime, Account account) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String regex = "{2}[012]\\d[0-6]\\d[0-6]\\d";
		
		Pattern pattern = Pattern.compile(regex);
		
		if((startTime.length() == 6) && (endTime.length() == 6) && pattern.matcher(startTime).matches() && pattern.matcher(endTime).matches()){
			try {
				startTime = getPropertyTime(startTime);
				
				endTime = getPropertyTime(endTime);
				
				updateTradeTimePropertiesFile(startTime, endTime, account);
				map.put("status", "ok");
				map.put("msg", "更新成功");
				map.put("data", "");
			} catch (IOException e) {
				e.printStackTrace();
				map.put("status", "falild");
				map.put("msg", "更新失败:"+e.getMessage());
				map.put("data", "");
			}
		} else {
			map.put("status", "falild");
			map.put("msg", "参数格式错误：startTime : " + startTime +", endTime : " + endTime);
			map.put("data", "");
		}
		
		return map;
	}
	
	private String getPropertyTime(String time){
		return time.substring(4) + " " + time.substring(2, 4) + " " + time.substring(0, 2);
	}
	
	private void updateTradeTimePropertiesFile(String startTime, String endTime, Account account) throws IOException{
		
		String path = System.getProperty("smm.SMMPayCenter") + "/WEB-INF/classes/tradeTime.properties";
		
		Properties properties = new Properties();
		
		logger.info("更改前，startTime : " + properties.getProperty("startTime")
				+", closeTime : " + properties.getProperty("closeTime"));
		
		FileInputStream fis = new FileInputStream(path); 
		
		properties.load(fis);
		
		properties.setProperty("startTime", startTime);
		
		properties.setProperty("endTime", endTime);
		
		logger.info("更改后，startTime : " + properties.getProperty("startTime")
		+", closeTime : " + properties.getProperty("closeTime"));
		
		fis.close(); //将更改写入文件前应将读入流关闭
		
		FileOutputStream fos = new FileOutputStream(path);
		
		properties.store(fos, "id:" +account.getId()+",account:"+account.getAccount());
		
		fos.close();
	}
	
}
