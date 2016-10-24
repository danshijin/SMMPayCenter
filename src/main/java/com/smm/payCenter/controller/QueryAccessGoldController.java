package com.smm.payCenter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.QueryAccessBO;
import com.smm.payCenter.controller.PageUtils.PageBean;
import com.smm.payCenter.domain.DayBalance;
import com.smm.payCenter.domain.QueryEntry;
import com.smm.payCenter.domain.UserAccount;
import com.smm.payCenter.util.DateUtil;

import freemarker.log.Logger;

/**
 * 
 * @author hece
 *  出入金查询
 */

@Controller
@RequestMapping("/queryAccessGold")
public class QueryAccessGoldController {

	private static Logger logger = Logger.getLogger(QueryAccessGoldController.class.getName());
	@Resource
	private QueryAccessBO queryAccess;
	
	@RequestMapping("/query")
	public ModelAndView query(String dstatus,String replaytime,String username,String companyname,Integer pno){
		ModelAndView modelview = new ModelAndView("/capitalEntry/queryAccessGold");
		logger.info(">>>>>>>>>>"+dstatus);
		DateUtil dateutil = new DateUtil();
		//余额查询
		if(dstatus == null || dstatus.equals("") || dstatus.equals("-1")){
			dstatus = "-1";
			Map<String,Object> map = new HashMap<String, Object>();
			if(replaytime == null || replaytime.equals("")){
				replaytime = dateutil.currentDate();
				map.put("replaytime", replaytime);
			}else{
				map.put("replaytime", replaytime);
			}
			map.put("username", username);
			map.put("companyname", companyname);
			//查询记录总条数
			List<DayBalance> queryQiyeNum = queryAccess.queryQiyemoney(map);
			if (pno == null) { 
				pno = 1;
			}
			PageBean page = new PageBean(10, pno, queryQiyeNum.size());
			int startNum = page.getStartNum();
			int endNum = page.getEndNum();
			map.put("startNum", startNum);
			map.put("endNum", endNum);
			
			//查询平台资金
			DayBalance balance = queryAccess.queryPingtaimoney(replaytime);
			//查询企业资金
			List<DayBalance> queryQiye = queryAccess.queryQiyemoney(map);
			modelview.addObject("totalRecords",queryQiyeNum.size());//总条数
			modelview.addObject("totalPage",page.getTotalPages());//总页数
			modelview.addObject("dstatus",dstatus);
			modelview.addObject("replaytime",replaytime);
			modelview.addObject("queryPingtai",balance);
			modelview.addObject("queryQiye",queryQiye);
			modelview.addObject("username",username);
			modelview.addObject("companyname",companyname);
			return modelview;
		}
		//冻结查询
		if(dstatus != null && !dstatus.equals("") && dstatus.equals("0")){
			Map<String,Object> map = new HashMap<String, Object>();
			if(replaytime == null || replaytime.equals("")){
				replaytime = dateutil.currentDate();
				map.put("replaytime", replaytime);
			}else{
				map.put("replaytime", replaytime);
			}
			map.put("username", username);
			map.put("companyname", companyname);
			//查询记录总条数
			List<DayBalance> balancelist = queryAccess.queryDongJieQiYe(map);
			if (pno == null) { 
				pno = 1;
			}
			PageBean page = new PageBean(10, pno, balancelist.size());
			int startNum = page.getStartNum();
			int endNum = page.getEndNum();
			map.put("startNum", startNum);
			map.put("endNum", endNum);
			//冻结查询平台资金
			DayBalance balance = queryAccess.queryDongJiePingTai(replaytime);
			//冻结查询企业资金
			List<DayBalance> alist = queryAccess.queryDongJieQiYe(map);
			System.out.println("----------------------"+alist.size());
			modelview.addObject("totalRecords",balancelist.size());//总条数
			modelview.addObject("totalPage",page.getTotalPages());//总页数
			modelview.addObject("dstatus",dstatus);
			modelview.addObject("replaytime",replaytime);
			modelview.addObject("queryPingtai",balance);
			modelview.addObject("queryQiye",alist);
			modelview.addObject("username",username);
			modelview.addObject("companyname",companyname);
			return modelview;
		}
		//可用余额
		if(dstatus != null && !dstatus.equals("") && dstatus.equals("1")){
			Map<String,Object> map = new HashMap<String, Object>();
			if(replaytime == null || replaytime.equals("")){
				replaytime = dateutil.currentDate();
				map.put("replaytime", replaytime);
			}else{
				map.put("replaytime", replaytime);
			}
			map.put("username", username);
			map.put("companyname", companyname);
			//查询记录总条数
			List<DayBalance> accountlist = queryAccess.queryYuEQiYe(map);
			if (pno == null) { 
				pno = 1;
			}
			PageBean page = new PageBean(10, pno, accountlist.size());
			int startNum = page.getStartNum();
			int endNum = page.getEndNum();
			map.put("startNum", startNum);
			map.put("endNum", endNum);
			//冻结查询平台资金
			DayBalance balance = queryAccess.queryYuEPingTai(replaytime);
			//冻结查询企业资金
			List<DayBalance> alist = queryAccess.queryYuEQiYe(map);
			modelview.addObject("totalRecords",accountlist.size());//总条数
			modelview.addObject("totalPage",page.getTotalPages());//总页数
			modelview.addObject("dstatus",dstatus);
			modelview.addObject("replaytime",replaytime);
			modelview.addObject("queryPingtai",balance);
			modelview.addObject("queryQiye",alist);
			modelview.addObject("username",username);
			modelview.addObject("companyname",companyname);
			return modelview;
		}
		return modelview;
	}
}