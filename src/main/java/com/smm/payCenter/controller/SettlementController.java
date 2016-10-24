package com.smm.payCenter.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.IMarketStatusBO;
import com.smm.payCenter.bo.QueryAccessBO;
import com.smm.payCenter.bo.SendMailBO;
import com.smm.payCenter.bo.SettlementBO;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.TrMarketLog;
import com.smmpay.inter.AuthorService;
import com.smmpay.inter.smmpay.CallEveryDaySettlementService;

import freemarker.log.Logger;

/**
 * 
 * @author hece
 * 每日结算
 */
@Controller
@RequestMapping("/everyDaySettlement")
public class SettlementController {

	@Resource
	private AuthorService authorService;
	@Resource
	private QueryAccessBO queryAccess;
	@Resource
	private CallEveryDaySettlementService callererydayService;
	@Resource
	private SettlementBO settle;
	
	@Resource
	IMarketStatusBO iMarketStatusBO;
	
	Map<String, Object> map = new HashMap<String, Object>();
	
	private static Logger logger = Logger.getLogger(SettlementController.class.getName());

	
	@RequestMapping("/settlement")
	public ModelAndView settlement(){
		ModelAndView modelview =new ModelAndView("/capitalEntry/settlement");
		logger.info("------------每日结算");
		String date = this.currentDate();
		modelview.addObject("date",date);
		return modelview;
	}
	
	/**
	 * 每日结算
	 * 
	 * String jsonStr = "{\"data:[{\"paymentId\":\"11111\",\"date\":\""+date+"\"}]\"}";
	 * */
	@RequestMapping("/settle")
	public synchronized ModelAndView settle(String date, HttpServletRequest request){
		ModelAndView modelview = new ModelAndView("capitalEntry/settlement");
		if(!iMarketStatusBO.isMarketOpen()){
			modelview.addObject("date",date);
			return modelview.addObject("isMarketClose", Boolean.TRUE);
		}
		logger.info("手动闭市开始");
		iMarketStatusBO.closeMarket();
		logger.info("手动闭市成功");
		logger.info("手动每日结算开始");
		map = settle.settlement(date);
		logger.info("手动每日结算结束");
		Account account = (Account) request.getSession().getAttribute("userInfo");
		//写入闭市日志
		TrMarketLog log = new TrMarketLog();
		log.setOperator(account.getId());
		log.setOperType(0);
		iMarketStatusBO.insertLog(log);
		
		modelview.addObject("upcList",map.get("upcList"));
		modelview.addObject("sysMoney",map.get("sysMoney"));
		modelview.addObject("synCount",map.get("synCount"));
		modelview.addObject("skipCount",map.get("skipCount"));
		modelview.addObject("sysSJAMT",map.get("sysSJAMT"));
		modelview.addObject("excelist",map.get("excelist"));
		modelview.addObject("excenum",map.get("excenum"));
		modelview.addObject("block","block");
		modelview.addObject("date",date);
		return modelview;
	}
	
	
	/**
	 * 返回当前日期
	 * */
	public String currentDate(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String str = sdf.format(date);
		return str;
	}
}