/**
 * 
 */
package com.smm.payCenter.controller.sysManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.CheckRepeatBo;
import com.smm.payCenter.bo.FnlLogBO;
import com.smm.payCenter.bo.sysManager.TransferRecordBo;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.FnlLog;
import com.smm.payCenter.domain.TransferRecord;
import com.smm.payCenter.util.IpUtil;
import com.smm.payCenter.util.JSONUtil;
import com.smmpay.common.author.Authory;
import com.smmpay.common.request.RequestDataProxy;
import com.smmpay.inter.AuthorService;
import com.smmpay.inter.dto.res.ReturnDTO;
import com.smmpay.inter.smmpay.CallManualFreezeService;

/**002658
 * OperationLogController
 *
 * Copyright 2015 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2015年11月16日
 */

@Controller
@RequestMapping("/transferRecord")
public class TransferRecordController {

	@Resource
	private TransferRecordBo transferRecordBo;
	
	@Resource
	private CallManualFreezeService callManualFreezeService;
	
	@Resource
	private AuthorService authorService;
	@Resource
	private CheckRepeatBo checkRepeatBo;
	@Resource
	private FnlLogBO fnlLogBO;
	
	
	@RequestMapping("/list")
	public ModelAndView querylist(){
		ModelAndView model = new ModelAndView("/sysManager/transferlist");
//		ModelAndView model = new ModelAndView("/artificialTransfer/artificialTransfer");
		List<TransferRecord> transferlist = transferRecordBo.queryList();
		model.addObject("transferlist", transferlist);
		return model;
		
	}
	
	@RequestMapping("/refund")
	@ResponseBody
	public Map<String, String> refund(HttpServletRequest request){
		synchronized (request) {
		Map<String, String> result = new HashMap<>();
		String paymentid = request.getParameter("paymentid");
		String companyName=request.getParameter("companyName");
		Map<String, String> checkMap = new HashMap<>();
		checkMap.put("paymentId", paymentid);
		Integer num=checkRepeatBo.checkTransferRecord(checkMap);
		if (num!=null&&num>=1) {
			result.put("info", "退款失败：退款已完成 或者正在退款中！");
			return result;
		}
		
		
		Account account = (Account) request.getSession().getAttribute("userInfo");
		//加入管理员操作日志
		FnlLog log = new FnlLog();
		log.setFnlAccount(account.getId());
		log.setType("手动退款");
		log.setContent("手动退款-交易编号: "+paymentid);
		String stdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		log.setCreatedAt(stdate);
		log.setUserCompanyName(companyName);
//		log.setTransferMoney(Double.parseDouble(money));
		log.setIp(IpUtil.getIpAddr());
		fnlLogBO.addFnlLog(log);

		Map<String, Object> map = new HashMap<>();
		map.put("paymentId", paymentid);
		String jsonStr = JSONUtil.doConvertObject2Json(map);
		jsonStr = "{\"data\":["+jsonStr+"]}";
		if(Authory.token == null) RequestDataProxy.getAccessToken(authorService);
		Map<String, String> mapParam = RequestDataProxy.getRequestParam(jsonStr, paymentid);
		map.clear();
		map = mapString2Object(mapParam);
		try {
			ReturnDTO dto = callManualFreezeService.manualTransferHandle(map);
			if (dto.getStatus().equals("000000")) {
				result.put("info", "success");
			}else {
				result.put("info", dto.getMsg());
			}
		} catch (Exception e) {
			result.put("info", "error");
			e.printStackTrace();
			return result;
		}
		return result;
		}
	}
	
	private Map<String, Object> mapString2Object(Map<String, String> map){
		Map<String, Object> result = new HashMap<>();
		Set<String> set = map.keySet();
		for (String string : set) {
			result.put(string, map.get(string));
		}
		return result;
	}
}
