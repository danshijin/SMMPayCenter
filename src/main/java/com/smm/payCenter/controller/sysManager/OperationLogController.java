/**
 * 
 */
package com.smm.payCenter.controller.sysManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.sysManager.OperationLogBo;
import com.smm.payCenter.controller.PageUtils.PageBean;
import com.smm.payCenter.domain.OperationLog;

/**002658
 * OperationLogController
 *
 * Copyright 2015 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2015年11月16日
 */

@Controller
@RequestMapping("/operationLog")
public class OperationLogController {

	@Resource
	private OperationLogBo operationLogBo;
	
	private final String STAR_FIX = " 00:00:00.0";
	
	private final String END_FIX = " 23:59:59.999";

	@RequestMapping("/list")
	public ModelAndView queryLoglist(Integer pno,String account, String companyName,String startTime,String endTime){
		ModelAndView model = new ModelAndView("/sysManager/loglist");
		model.addObject("startTime",startTime);
		model.addObject("endTime",endTime);
		Map<String, String> map = new HashMap<String, String>();
		if (account != null && account != "") {
			map.put("account", account);
		}
		if (companyName != null && companyName != "") {
			map.put("companyName", companyName);
		}
		if ((startTime != null && startTime != "")) {
			map.put("startTime", startTime + STAR_FIX);
		}
		if (endTime != null && endTime != "") {
			map.put("endTime", endTime + END_FIX);
		}
		int totalRecords = operationLogBo.queryAccount(map);
		if (pno == null) {
			pno = 1;
		}
		PageBean page = new PageBean(20, pno, totalRecords);
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		map.put("startNum", startNum+"");
		map.put("endNum", endNum+"");
		List<OperationLog> loglist = operationLogBo.queryLog(map);
		model.addObject("loglist", loglist);
		model.addObject("totalRecords", totalRecords);
		model.addObject("totalPage",page.getTotalPages());// 总页数
		model.addObject("account",account);
		model.addObject("companyName",companyName);
		return model;
		
	}
}
