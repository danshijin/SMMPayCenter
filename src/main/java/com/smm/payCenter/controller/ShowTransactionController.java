package com.smm.payCenter.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.CheckRepeatBo;
import com.smm.payCenter.bo.ShowTransactionBO;
import com.smm.payCenter.bo.capitalEntry.CapitalEntryBO;
import com.smm.payCenter.controller.PageUtils.PageBean;
import com.smm.payCenter.domain.PaymentRecord;
import com.smm.payCenter.domain.TransactionDetail;

@Controller
@RequestMapping("/showTransaction")
public class ShowTransactionController {
	@Resource
	private ShowTransactionBO transactionBO;
	@Resource
	private CapitalEntryBO capitalEntryBO;
	@Resource
	private CheckRepeatBo checkRepeatBo;
	
	//查询列表
	@RequestMapping("/list")
	public ModelAndView query(Integer dstatus,Integer pno,String startTime,String endTime,String companyName){
		ModelAndView modelview =new ModelAndView("/audit/transactionList");
		Map<String,Object> map = new HashMap<String, Object>();
		if(dstatus==null){
			dstatus=-1;
		}
		
		map.put("dstatus", dstatus);
		if(companyName!=null&&!companyName.equals("")){
			map.put("companyName", companyName);
		}
		if(startTime!=null&&!startTime.equals("")){
			map.put("startTime",startTime);
		}
		if(endTime!=null&&!endTime.equals("")){
			map.put("endTime",endTime+" 23:59:59");
		}else{
//			当前日期
			endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			map.put("endTime",endTime+" 23:59:59");
		}
		List<PaymentRecord> listCount = transactionBO.queryList(map);
		 if (pno == null) {
	            pno = 1;
        }
        PageBean page = new PageBean(20, pno, listCount.size());
        int startNum = page.getStartNum();
        int endNum = page.getEndNum();

        map.put("startNum", "" + startNum);
        map.put("endNum", "" + endNum);
        List<PaymentRecord> list = transactionBO.queryList(map);
        
		modelview.addObject("list", list);
		modelview.addObject("dstatus", dstatus);
		modelview.addObject("startTime", startTime);
		modelview.addObject("endTime", endTime);
		modelview.addObject("companyName", companyName);

		modelview.addObject("totalRecords", listCount.size());// 总条数
		modelview.addObject("totalPage", page.getTotalPages());// 总页数
		return modelview;
	}
	
	@RequestMapping("/updateCloseById")
	@ResponseBody
	public Map<String,Object> updateCloseById(Integer paymentId){
		Map<String,  Object> map = new HashMap<String, Object>();
		Integer countt = checkRepeatBo.checkFreezeRecord(paymentId);
		if(countt!=null&&countt>0){
			map.put("result", "error");
			map.put("message", "存在有效的冻结记录，关闭失败！");
			return map;
		}
		Integer count = transactionBO.updateCloseById(paymentId);
		if(count!=null&&count>0){
			map.put("result", "success");
			map.put("message", "关闭成功！");
		}else{
			map.put("result", "error");
			map.put("message", "关闭失败！");
		}
		return map;
	}
	
	/**
	 * 交易详情
	 * */
	@RequestMapping("/detail")
	public ModelAndView detail(Integer paymentid) {
		ModelAndView modelview =new ModelAndView("/audit/transactionListDetail");
		TransactionDetail trans = capitalEntryBO.paymentDetail(paymentid);
		modelview.addObject("td",trans);
		return modelview;
	}
}
