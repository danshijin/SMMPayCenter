package com.smm.payCenter.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.CheckRepeatBo;
import com.smm.payCenter.bo.FnlLogBO;
import com.smm.payCenter.bo.TransactionPaymentBO;
import com.smm.payCenter.bo.capitalEntry.CapitalEntryBO;
import com.smm.payCenter.bo.useraccount.UserAccountBo;
import com.smm.payCenter.controller.PageUtils.PageBean;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.FnlLog;
import com.smm.payCenter.domain.TrFreezeRecordEntity;
import com.smm.payCenter.domain.TransactionDetail;
import com.smm.payCenter.domain.TransactionPayment;
import com.smm.payCenter.util.DateUtil;
import com.smm.payCenter.util.IpUtil;
import com.smmpay.common.author.Authory;
import com.smmpay.common.encrypt.Base64;
import com.smmpay.common.encrypt.DesCrypt;
import com.smmpay.common.encrypt.MD5;
import com.smmpay.common.request.RequestDataProxy;
import com.smmpay.inter.AuthorService;
import com.smmpay.inter.dto.res.ReturnDTO;
import com.smmpay.inter.smmpay.CallManualUNFreezeService;
import com.smmpay.inter.smmpay.CallThawPayService;

import freemarker.log.Logger;

@Controller
@RequestMapping("/transactionPayment")
public class TransactionPaymentController {
	@Resource
	private TransactionPaymentBO transactionPaymentBO;
	@Resource
	private AuthorService authorService;
	@Resource
	private CallThawPayService thawpayService;
	@Resource
	private CapitalEntryBO capitalentry;
	@Resource 
	private CallManualUNFreezeService callfreeService;
	@Resource
	private FnlLogBO fnlLogBO;
	@Resource
	private CheckRepeatBo checkRepeatBo;
	@Resource
	private UserAccountBo userAccountBo;
	
	private static Logger logger = Logger.getLogger(TransactionPaymentController.class.getName());
	
	//查询列表
	@RequestMapping("/list.do")
	public ModelAndView query(String dstatus,String startTime,String endTime,Integer pno){
		Map<String,Object> map = new HashMap<String, Object>();

		if(dstatus==null){
			dstatus="-1";
		}
		map.put("dstatus", dstatus);
		
		if(startTime!=null&&!startTime.equals("")){
			map.put("startTime",DateUtil.doFormatDate(DateUtil.startOfTodDay(startTime), "yyyy-MM-dd HH:mm:ss"));
		}
		if(endTime!=null&&!endTime.equals("")){
			map.put("endTime",DateUtil.doFormatDate(DateUtil.endOfTodDay(endTime), "yyyy-MM-dd HH:mm:ss"));
		}else{
			//当前日期
			//endTime = sdf.format(date);
		}
		
		//int count = this.transactionPaymentBO.queryCount(map);
		List<TransactionPayment> payNumList = transactionPaymentBO.query(map);
        if (pno == null) {
            pno = 1;
        }
        PageBean page = new PageBean(10, pno, payNumList.size());
        int startNum = page.getStartNum();
        int endNum = page.getEndNum();

        map.put("startNum", "" + startNum);
        map.put("endNum", "" + endNum);
		List<TransactionPayment> list = transactionPaymentBO.query(map);
	    ModelAndView view = new ModelAndView("/audit/transactionPayment");
	    view.addObject("tplist",list);
	    view.addObject("dstatus", dstatus);
	    view.addObject("startTime", startTime);
	    view.addObject("endTime", endTime);
	    
	    view.addObject("totalRecords", payNumList.size());// 总条数
        view.addObject("totalPage", page.getTotalPages());// 总页数
		return view;
	}
	
	//根据Id查询详情
	@RequestMapping("/detail.do")
	public ModelAndView queryDetail(String id){
		Integer payId =  Integer.valueOf(id);
		TransactionDetail td =  transactionPaymentBO.queryDetailById(payId);
		System.out.println("productNum:"+td.getProductNum());
		ModelAndView view = new ModelAndView("/audit/transactionDetail");
		view.addObject("td", td);
		return view;
	}
	
	/**
	 *  解冻支付
	 *  
	 *  String jsonStr = "{\"data:[{\"paymentId\":\"11111\",\"paymentId\":\"11111\",\"paymentId\":\"11111\",\"paymentId\":\"11111\"}]\"}";
			if(Authory.token == null) RequestDataProxy.getAccessToken(authorService);
			Map<String,String> mapParam = RequestDataProxy.getRequestParam(jsonStr, "1111122223333");
	 *  
	 */
	@RequestMapping(value="/thaw.do",produces="application/json")
	@ResponseBody
	public Map<String,Object> thaw(Integer confirmid,Integer paymentid,String companyName,Double price,HttpServletRequest request) {
		synchronized (this) {
			Map<String,Object> resultmap = new HashMap<String, Object>();
		//检查是否重复解冻支付
		Integer num=checkRepeatBo.checkUnfreezeRecore(paymentid);
		Integer status=transactionPaymentBO.queryConfirmStatus(confirmid);
		
		if(status!=null&&status!=0){
			resultmap.put("result", "error");
			resultmap.put("message", "失败,交易审核已通过或者已拒绝");
			return resultmap;
		}
		if(num!=null&&num>0){
			resultmap.put("result", "error");
			resultmap.put("message", "失败,已存在有效的解冻支付记录");
			return resultmap;
		}
		TrFreezeRecordEntity tran=checkRepeatBo.queryFreezeRecord(paymentid);
		if(tran==null){
			resultmap.put("result", "error");
			resultmap.put("message", "失败，对应的冻结记录不存在");
			return resultmap;
		}else if(tran.getFreezeStatus()==0){
			resultmap.put("result", "error");
			resultmap.put("message", "失败，对应的冻结请求正在请求中");
			return resultmap;
			
		}else if (tran.getFreezeStatus()==2){
			resultmap.put("result", "error");
			resultmap.put("message", "失败，对应的冻结失败");
			return resultmap;
		}
		DecimalFormat df=new DecimalFormat("######0.00");
		String md=String.valueOf(tran.getPaymentId())+String.valueOf(df.format(tran.getFreezeMoney()))+tran.getFreezeClientId()+tran.getApplyTime();

		try {
			byte[]  desColums = DesCrypt.encryptMode(md.getBytes("utf-8"));
			String desColumsStr = Base64.getBase64(desColums);
			logger.info("加密前："+md+"加密值：desColumsStr："+desColumsStr+",数据库加密字段值："+tran.getVerifyCode());
			if(!desColumsStr.equals(tran.getVerifyCode())){
				resultmap.put("result", "error");
				resultmap.put("message", "失败，交易信息涉嫌被篡改！");
				logger.info("加密比对失败");
				return resultmap;
			}	
		} catch (UnsupportedEncodingException e1) {	
			e1.printStackTrace();
			resultmap.put("result", "error");
			resultmap.put("message", "失败，加密字段验证异常！");
			return resultmap;
		}
//		UserAccountEntity userAccountm=userAccountBo.getUserAccountById(Integer.valueOf(tran.getFreezeClientId()));
//		if(userAccountm.getUseMoney()==null||Double.valueOf(userAccountm.getUseMoney())<=tran.getFreezeMoney()){
//			resultmap.put("result", "error");
//			resultmap.put("message", "失败,买家余额不足");
//			return resultmap;
//		}
		
		DateUtil date = new DateUtil();
		Map<String, Object> map = new HashMap<>();
		Account account = (Account) request.getSession().getAttribute("userInfo");
		String sdate = date.currentDate();
		map.put("auditUserId", account.getId());
		map.put("auditTime", sdate);
		map.put("confirmId", confirmid);
		map.put("paymentId", paymentid);
		try {
			//修改审核状态为通过
			transactionPaymentBO.updateConfirmStatus(map);
			//写入日志表
			transactionPaymentBO.insertPaymentLog(paymentid,"后台交易审核","审核通过");
			
			//加入管理员操作日志
			FnlLog log = new FnlLog();
			log.setFnlAccount(account.getId());
			log.setType("审核支付");
			log.setContent("解冻支付-交易编号:"+paymentid);
			String stdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			log.setCreatedAt(stdate);
			log.setUserCompanyName(companyName);
			log.setTransferMoney(price);
			log.setIp(IpUtil.getIpAddr());
			fnlLogBO.addFnlLog(log);
			
			
			String jsonStr = "{\"data\":[{\"auditUserId\":\""+account.getId()+"\",\"auditTime\":\""+sdate+
					"\",\"paymentId\":\""+paymentid+"\",\"confirmId\":\""+confirmid+"\"}]}";
			logger.info("解冻支付入参============"+jsonStr);
			if(Authory.token == null) RequestDataProxy.getAccessToken(authorService);
			String str = String.valueOf(account.getId())+sdate+String.valueOf(paymentid)+String.valueOf(confirmid);
			Map<String, String> mapParam = RequestDataProxy.getRequestParam(jsonStr,str);
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.putAll(mapParam);
			//解冻支付接口
			ReturnDTO thaw = thawpayService.autoTthawPayHandle(paramMap);
			logger.info("解冻支付返回状态="+thaw.getStatus());
			if(thaw.getStatus().equals("000000")){
				logger.info("解冻支付成功>>>>>>>>>>>>>>"+thaw.getMsg());
				resultmap.put("result", "success");
				resultmap.put("message", thaw.getMsg()!=null?thaw.getMsg():"解冻支付成功");
				return resultmap;
			}else{
				logger.info("解冻支付失败>>>>>>>>>>>>>>"+thaw.getMsg());
				//写入日志表
				//transactionPaymentBO.insertPaymentLog(paymentid,"失败",thaw.getMsg()!=null?thaw.getMsg():"");
				resultmap.put("result", "error");
				resultmap.put("message", thaw.getMsg()!=null?thaw.getMsg():"解冻支付失败");
				return resultmap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultmap.put("result", "success");
		resultmap.put("message", "");
		return resultmap;
		}
	}	
	
	

	/**
	 *  资金解冻(审核)
	 */
	@RequestMapping(value="/moneythaw",produces="application/json")
	@ResponseBody
	public Map<String,Object> moneythaw(Integer confirmid,Integer paymentid,String companyName,Double price,HttpServletRequest request) {
		synchronized (this) {
			
			
		Map<String, Object> resultmap = new HashMap<>();
		//检查是否重复解冻支付
		Integer num=checkRepeatBo.checkUnfreezeRecore(paymentid);
		if(num!=null&&num>0){
			resultmap.put("result", "error");
			resultmap.put("message", "失败,已存在有效的解冻记录");
			return resultmap;
		}
		Integer status=transactionPaymentBO.queryConfirmStatus(confirmid);
		if(status!=null&&status!=0){
			resultmap.put("result", "error");
			resultmap.put("message", "交易审核已通过或者已拒绝");
			return resultmap;
		}
		TrFreezeRecordEntity tran=checkRepeatBo.queryFreezeRecord(paymentid);
		if(tran==null){
			resultmap.put("result", "error");
			resultmap.put("message", "失败，对应的冻结记录不存在");
			return resultmap;
		}else if(tran.getFreezeStatus()==0){
			resultmap.put("result", "error");
			resultmap.put("message", "失败，对应的冻结请求正在请求中");
			return resultmap;
			
		}else if (tran.getFreezeStatus()==2){
			resultmap.put("result", "error");
			resultmap.put("message", "失败，对应的冻结失败");
			return resultmap;
		}
		
		DateUtil date = new DateUtil();
		Map<String, Object> map = new HashMap<>();
		Account account = (Account) request.getSession().getAttribute("userInfo");
		
		//是否有补价订单
		try {
			String jsonPaymentid = "{\"data\":[{\"paymentId\":\""+paymentid+"\"}]}";
			logger.info("是否有补价订单入参============"+jsonPaymentid);
			if(Authory.token == null) RequestDataProxy.getAccessToken(authorService);
			Map<String, String> pmap = RequestDataProxy.getRequestParam(jsonPaymentid,String.valueOf(paymentid));
			Map<String,Object> bujiamap = new HashMap<String, Object>();
			bujiamap.putAll(pmap);
			ReturnDTO bujia = callfreeService.whetherBuJiaOrder(bujiamap);
			if(bujia.getStatus().equals("000000")){
				logger.info("是否有补价订单成功>>>>>>>>>>>>>>"+bujia.getMsg());
				
				map.put("auditUserId", account.getId());
				map.put("auditTime", date.currentDate());
				map.put("confirmId", confirmid);
				//修改审核状态为已拒绝
				capitalentry.updateConfirmStatus(map);
			}else{
				resultmap.put("result", "error");
				resultmap.put("message", bujia.getMsg()+",交易编号:"+((Map<String,String>)bujia.getData()).get("paymentNo"));
				return resultmap;
			}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		DecimalFormat df=new DecimalFormat("######0.00");
		String md=String.valueOf(tran.getPaymentId())+String.valueOf(df.format(tran.getFreezeMoney()))+tran.getFreezeClientId()+tran.getApplyTime();
				byte[] desColums;
		try {
			desColums = DesCrypt.encryptMode(md.getBytes("utf-8"));
			String desColumsStr = Base64.getBase64(desColums);
			logger.info("加密前："+md+"加密值：desColumsStr："+desColumsStr+",数据库加密字段值："+tran.getVerifyCode());
			if(!desColumsStr.equals(tran.getVerifyCode())){
				resultmap.put("result", "error");
				resultmap.put("message", "失败，交易信息涉嫌被篡改！");
				logger.info("加密比对失败");
				return resultmap;
			}	
		} catch (UnsupportedEncodingException e1) {	
			e1.printStackTrace();
			resultmap.put("result", "error");
			resultmap.put("message", "失败，加密字段验证异常！");
			return resultmap;
		}
		
		
		//加入管理员操作日志
		FnlLog log = new FnlLog();
		log.setFnlAccount(account.getId());
		log.setType("审核支付");
		log.setContent("资金解冻-交易编号:"+paymentid);
		String stdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		log.setCreatedAt(stdate);
		log.setUserCompanyName(companyName);
		log.setTransferMoney(price);
		log.setIp(IpUtil.getIpAddr());
		fnlLogBO.addFnlLog(log);
		
		try {
			String jsonStr = "{\"data\":[{\"confirmId\":\""+confirmid+"\",\"paymentId\":\""+paymentid+"\"}]}";
			logger.info("资金解冻入参============"+jsonStr);
			String str =String.valueOf(confirmid) + String.valueOf(paymentid);
			if(Authory.token == null) RequestDataProxy.getAccessToken(authorService);
			Map<String, String> mapParam = RequestDataProxy.getRequestParam(jsonStr,str);
			Map<String,Object> parammap = new HashMap<String, Object>();
			parammap.putAll(mapParam);
			//资金解冻接口
			ReturnDTO thaw=callfreeService.manualUNFreezeHandle(parammap);
			logger.info("资金解冻返回状态="+thaw.getStatus());
			if(thaw.getStatus().equals("000000")){
				logger.info("资金解冻成功>>>>>>>>>>>>>>"+thaw.getMsg());
				resultmap.put("result", "success");
				resultmap.put("message", thaw.getMsg()!=null?thaw.getMsg():"资金解冻成功");
				return resultmap;
			}else{
				logger.info("资金解冻失败>>>>>>>>>>>>>>"+thaw.getMsg());
				resultmap.put("result", "error");
				resultmap.put("message", thaw.getMsg()!=null?thaw.getMsg():"资金解冻失败");
				return resultmap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultmap.put("result", "success");
		resultmap.put("message", "");
		return resultmap;
	}}
	
	
	
	
}