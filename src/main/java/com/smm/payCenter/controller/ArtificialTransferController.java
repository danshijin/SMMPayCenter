package com.smm.payCenter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.ArtificialTransferBO;
import com.smm.payCenter.bo.TransactionPaymentBO;
import com.smm.payCenter.controller.PageUtils.PageBean;
import com.smm.payCenter.domain.ArtificialTransferVO;
import com.smm.payCenter.domain.TransactionDetail;
import com.smm.payCenter.util.DateUtil;

import freemarker.log.Logger;

/**
 * 
 * @author hece
 *	人工转账
 */

@Controller
@RequestMapping("/artificialTransfer")
public class ArtificialTransferController {

	private static Logger logger = Logger.getLogger(ArtificialTransferController.class.getName());
	
	@Resource 
	private ArtificialTransferBO transferBo;
	@Resource
	private TransactionPaymentBO transactionPaymentBO;
	
	/**
	 * 人工转账列表
	 * */
	@RequestMapping("/List")
	public ModelAndView accountList(String username,String paymentid,String startTime,String endTime,Integer pno,String dstatus) {
		logger.info("--------人工转账列表--------");
		ModelAndView modelview =new ModelAndView("/artificialTransfer/artificialTransfer");
		Map<String,Object> paymap = new HashMap<String, Object>();
		DateUtil dateutil = new DateUtil();
		if(dstatus==null){
			dstatus="-1";
		}
		if (startTime != null && startTime !="") {
			paymap.put("startTime", DateUtil.startOfTodDay(startTime));
		}else{
			paymap.put("startTime",null);
		}
		if (endTime != null && endTime != "") {
			paymap.put("endTime",  DateUtil.endOfTodDay(endTime));
		}else{
			paymap.put("endTime", null);
//			endTime = dateutil.currentDate();
		}
		paymap.put("username", username);
		paymap.put("paymentid", paymentid);
		paymap.put("dstatus", dstatus);
		//查询记录总条数
		List<ArtificialTransferVO> transferlist = transferBo.ArtificialTransferList(paymap);
		if (pno == null) { 
			pno = 1;
		}
		PageBean page = new PageBean(20, pno, transferlist.size());
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		paymap.put("startNum", startNum);
		paymap.put("endNum", endNum);
		//查询转账列表
		List<ArtificialTransferVO> trlist = transferBo.ArtificialTransferList(paymap);
		modelview.addObject("trlist",trlist);
		modelview.addObject("totalRecords",transferlist.size());//总条数
		modelview.addObject("totalPage",page.getTotalPages());//总页数
		modelview.addObject("startTime",startTime);
		modelview.addObject("endTime",endTime);
		modelview.addObject("username",username);
		modelview.addObject("paymentid",paymentid);
		modelview.addObject("dstatus",dstatus);
		return modelview;
	}
	
	
	/**
	 * 人工转账详情
	 * */
	@RequestMapping("/Detail")
	public ModelAndView accountDetail(Integer paymentid,Integer status) {
		logger.info("--------人工转账详情--------");
		ModelAndView modelview =new ModelAndView("/artificialTransfer/artificialTransferDetail");
		TransactionDetail trdetail =  transactionPaymentBO.queryById(paymentid);
		modelview.addObject("td",trdetail);
		modelview.addObject("status",status);
		return modelview;
	}
}