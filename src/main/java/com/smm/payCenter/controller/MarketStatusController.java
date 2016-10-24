package com.smm.payCenter.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smm.payCenter.bo.IMarketStatusBO;
import com.smm.payCenter.domain.Account;

/**
* @author  zhaoyutao
* @version 2015年12月8日 下午5:14:24
* 类说明
*/

@Controller
@RequestMapping(value="marketStatus")
public class MarketStatusController {
	
	@Resource
	IMarketStatusBO marketStatusBo;
	
	@RequestMapping(value="setOpeningTime")
	@ResponseBody
	public Map<String, Object> setOpeningTime(String startTime, String endTime, HttpServletRequest request){
		
		Account account = (Account) request.getSession().getAttribute("userInfo");
		
		return marketStatusBo.updateMarketOpeningTime(startTime, endTime, account);
	}
	
	@RequestMapping(value="isMarketOpen")
	@ResponseBody
	public Map<String, Object> getMarketStatus(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "ok");
		map.put("msg", "");
		map.put("data", marketStatusBo.isMarketOpen());
		
		return map;
	}
	
}
