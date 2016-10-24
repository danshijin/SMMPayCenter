package com.smm.payCenter.controller.capitalEntry;

import java.io.UnsupportedEncodingException;
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
import com.smm.payCenter.bo.capitalEntry.CapitalEntryBO;
import com.smm.payCenter.controller.PageUtils.PageBean;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.FnlLog;
import com.smm.payCenter.domain.PaymentRecord;
import com.smm.payCenter.domain.TrFreezeRecordEntity;
import com.smm.payCenter.domain.TransactionDetail;
import com.smm.payCenter.util.DateUtil;
import com.smm.payCenter.util.IpUtil;
import com.smmpay.common.author.Authory;
import com.smmpay.common.encrypt.Base64;
import com.smmpay.common.encrypt.DesCrypt;
import com.smmpay.common.request.RequestDataProxy;
import com.smmpay.inter.AuthorService;
import com.smmpay.inter.dto.res.ReturnDTO;
import com.smmpay.inter.smmpay.CallManualUNFreezeService;
import com.smmpay.inter.smmpay.CallThawPayService;

import freemarker.log.Logger;

/**
 * 
 * @author hece
 *
 *	强制转账
 */
@Controller
@RequestMapping("/transferAccount")
public class TransferAccountController {
	
	private static Logger logger = Logger.getLogger(TransferAccountController.class.getName());
	@Resource
	private CallThawPayService thawpayService;
	@Resource
	private CapitalEntryBO capitalentry;
	@Resource
	private AuthorService authorService;
	@Resource 
	private CallManualUNFreezeService callfreeService;
	@Resource
	private FnlLogBO fnlLogBO;
	@Resource
	private CheckRepeatBo checkRepeatBo;
	
	/**
	 * 转账列表
	 * */
	@RequestMapping("/transferList")
	public ModelAndView accountList(String mail,String prisename,String mallorderid,String startTime,String endTime,Integer pno) {
		ModelAndView modelview =new ModelAndView("/capitalEntry/compulsoryTransfer");
		Map<String,Object> paymap = new HashMap<String, Object>();
		DateUtil dateutil = new DateUtil();
		paymap.put("mail",mail);
		paymap.put("prisename",prisename);
		paymap.put("mallorderid",mallorderid);
		
		if (startTime != null && startTime !="") {
			paymap.put("startTime", DateUtil.startOfTodDay(startTime));
		}else{
			paymap.put("startTime",null);
		}
		if (endTime != null && endTime != "") {
			paymap.put("endTime",  DateUtil.endOfTodDay(endTime));
		}else{
			paymap.put("endTime", null);
			//endTime = dateutil.currentDate();
		}
		//查询记录总条数
		//int paynum = capitalentry.queryPaymentNum(paymap);
		List<PaymentRecord> payNumList = capitalentry.paymentList(paymap);
		if (pno == null) { 
			pno = 1;
		}
		PageBean page = new PageBean(20, pno, payNumList.size());
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		paymap.put("startNum", startNum);
		paymap.put("endNum", endNum);
		
		List<PaymentRecord> payList = capitalentry.paymentList(paymap);

		modelview.addObject("paylist",payList);
		modelview.addObject("totalRecords",payNumList.size());//总条数
		modelview.addObject("totalPage",page.getTotalPages());//总页数
		modelview.addObject("startTime",startTime);
		modelview.addObject("endTime",endTime);
		modelview.addObject("mail",mail);
		modelview.addObject("prisename",prisename);
		modelview.addObject("mallorderid",mallorderid);
		return modelview;
	}
	
	/**
	 * 转账详情
	 * */
	@RequestMapping("/transferDetail")
	public ModelAndView accountDetail(Integer paymentid) {
		ModelAndView modelview =new ModelAndView("/capitalEntry/transferDetail");
		TransactionDetail trans = capitalentry.paymentDetail(paymentid);
		modelview.addObject("td",trans);
		return modelview;
	}
	
	/**
	 * 退款列表
	 * */
	@RequestMapping("/refundlist")
	public ModelAndView queryRefundList(Integer pno,String companyName,String tradeId,String startTime,String endTime) {
		ModelAndView modelview =new ModelAndView("/capitalEntry/refundlist");
		Map<String, Object> paymap = new HashMap<>();
		paymap.put("companyName", companyName);
		paymap.put("tradeId", tradeId);
		paymap.put("startTime", startTime);
		paymap.put("endTime", endTime);
		if (companyName != null && !companyName.equals("")) {
			paymap.put("companyName", companyName);
		}
		if (tradeId != null && !tradeId.equals("")) {
			paymap.put("tradeId", tradeId);
		}
		if (startTime != null && !startTime.equals("")) {
			paymap.put("startTime", startTime);
		}
		if (endTime != null && !endTime.equals("")) {
			paymap.put("endTime", endTime);
		}
		//查询记录总条数
		int paynum = capitalentry.queryRefundAccount(paymap);
		if (pno == null) { 
			pno = 1;
		}
		PageBean page = new PageBean(20, pno, paynum);
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		paymap.put("startNum", startNum);
		paymap.put("endNum", endNum);
		List<PaymentRecord> list = capitalentry.queryRefundList(paymap);
		modelview.addObject("list", list);
		return modelview;
	}
	
	/**
	 *  确认转账
	 */
	@RequestMapping(value="/confirm",produces="application/json")
	@ResponseBody
	public Map<String,Object> confirm(Integer paymentid,String companyName,Double money,HttpServletRequest request) {
		synchronized (this) {
			
		Map<String,Object> resultmap = new HashMap<String, Object>();
		Integer count = checkRepeatBo.checkUnfreezeRecore(paymentid);
		if(count!=null&&count>0){
			logger.info("确认转账失败>>>>>>>>>>>>>>已存在有效的解冻记录!");
			resultmap.put("result", "error");
			resultmap.put("message", "已存在有效的解冻记录!  确认转账失败");
			return resultmap;
		}	
		TrFreezeRecordEntity tran=checkRepeatBo.queryFreezeRecord(paymentid);
		if(tran==null){
			resultmap.put("result", "error");
			resultmap.put("message", "失败，对应的冻结记录不存在");
			return resultmap;
		}else if(tran.getFreezeStatus()==0){
			resultmap.put("result", "error");
			resultmap.put("message", "失败，对应的冻结正在请求中");
			return resultmap;
		}else if(tran.getFreezeStatus()==2){
			resultmap.put("result", "error");
			resultmap.put("message", "失败，对应的冻结请求失败");
			return resultmap;
		} 

		DecimalFormat df=new DecimalFormat("######0.00");

		String md=String.valueOf(tran.getPaymentId())+String.valueOf(df.format(tran.getFreezeMoney()))
		+tran.getFreezeClientId()+tran.getApplyTime();

		byte[] desColums;
		try {
			desColums = DesCrypt.encryptMode(md.getBytes("utf-8"));
			String desColumsStr = Base64.getBase64(desColums);
			logger.info("加密前："+md+"加密值：desColumsStr："+desColumsStr+",数据库加密字段值："+tran.getVerifyCode());
			if(!desColumsStr.equals(tran.getVerifyCode())){
				resultmap.put("result", "error");
				resultmap.put("message", "失败，交易信息涉嫌被篡改！");
				return resultmap;
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			resultmap.put("result", "error");
			resultmap.put("message", "失败，加密字段验证异常！");
			return resultmap;
		}
		
		
		Account account = (Account) request.getSession().getAttribute("userInfo");
		//加入管理员操作日志
		FnlLog log = new FnlLog();
		log.setFnlAccount(account.getId());
		log.setType("强制支付");
		log.setContent("交易编号: "+paymentid);
		String stdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		log.setCreatedAt(stdate);
		log.setUserCompanyName(companyName);
		log.setTransferMoney(money);
		log.setIp(IpUtil.getIpAddr());
		fnlLogBO.addFnlLog(log);
		
		
		try {
			String jsonStr = "{\"data\":[{\"paymentId\":\""+paymentid+"\"}]}";
			logger.info("确认转账入参============"+jsonStr);
			if(Authory.token == null) RequestDataProxy.getAccessToken(authorService);
			Map<String, String> mapParam = RequestDataProxy.getRequestParam(jsonStr,String.valueOf(paymentid));
			Map<String,Object> parammap = new HashMap<String, Object>();
			parammap.putAll(mapParam);
			//确认转账接口
			ReturnDTO confirm = thawpayService.manualThawPayHandle(parammap);
			logger.info("确认转账返回状态="+confirm.getStatus());
			if(confirm.getStatus().equals("000000")){
				logger.info("确认转账成功>>>>>>>>>>>>>>"+confirm.getMsg());
				resultmap.put("result", "success");
				resultmap.put("message", confirm.getMsg()!=null?confirm.getMsg():"确认转账成功");
				return resultmap;
			}else{
				logger.info("确认转账失败>>>>>>>>>>>>>>"+confirm.getMsg());
				resultmap.put("result", "error");
				resultmap.put("message", confirm.getMsg()!=null?confirm.getMsg():"确认转账失败");
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
	 *  资金解冻(转账)
	 */
	@RequestMapping(value="/thaw",produces="application/json")
	@ResponseBody
	public Map<String,Object> thaw(Integer paymentid,String companyName,Double money,HttpServletRequest request) {
		synchronized (this) {
		
		Map<String, Object> resultmap = new HashMap<>();
		Integer count = checkRepeatBo.checkUnfreezeRecore(paymentid);
		if(count!=null&&count>0){
			logger.info("资金解冻失败>>>>>>>>>>>>>>已存在有效的解冻记录!");
			resultmap.put("result", "error");
			resultmap.put("message", "已存在有效的解冻记录!  资金解冻失败");
			return resultmap;
		}
		
		TrFreezeRecordEntity tran=checkRepeatBo.queryFreezeRecord(paymentid);
		if(tran==null){
			resultmap.put("result", "error");
			resultmap.put("message", "失败，对应的冻结记录不存在");
			return resultmap;
		}else if(tran.getFreezeStatus()==0){
			resultmap.put("result", "error");
			resultmap.put("message", "失败，对应的冻结正在请求中");
			return resultmap;
		}else if(tran.getFreezeStatus()==2){
			resultmap.put("result", "error");
			resultmap.put("message", "失败，对应的冻结请求失败");
			return resultmap;
		} 
		DecimalFormat df=new DecimalFormat("######0.00");

		String md=String.valueOf(tran.getPaymentId())+String.valueOf(df.format(tran.getFreezeMoney()))
		+tran.getFreezeClientId()+tran.getApplyTime();


		byte[] desColums;
		try {
			desColums = DesCrypt.encryptMode(md.getBytes("utf-8"));
			String desColumsStr = Base64.getBase64(desColums);
			logger.info("加密前："+md+"加密值：desColumsStr："+desColumsStr+",数据库加密字段值："+tran.getVerifyCode());
			if(!desColumsStr.equals(tran.getVerifyCode())){
				resultmap.put("result", "error");
				resultmap.put("message", "失败，交易信息涉嫌被篡改！");
				return resultmap;
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			resultmap.put("result", "error");
			resultmap.put("message", "失败，加密字段验证异常！");
			return resultmap;
		}
		
		Account account = (Account) request.getSession().getAttribute("userInfo");
		//加入管理员操作日志
		FnlLog log = new FnlLog();
		log.setFnlAccount(account.getId());
		log.setType("强制解冻");
		log.setContent("交易编号: "+paymentid);
		String stdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		log.setCreatedAt(stdate);
		log.setUserCompanyName(companyName);
		log.setTransferMoney(money);
		log.setIp(IpUtil.getIpAddr());
		fnlLogBO.addFnlLog(log);
		
		try {
			String jsonStr = "{\"data\":[{\"paymentId\":\""+paymentid+"\"}]}";
			logger.info("资金解冻入参============"+jsonStr);
			if(Authory.token == null) RequestDataProxy.getAccessToken(authorService);
			Map<String, String> mapParam = RequestDataProxy.getRequestParam(jsonStr,String.valueOf(paymentid));
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
				String paymentno = "";
				if(thaw.getData() != null){
					Map<String,String> map = (Map<String,String>)thaw.getData();
					paymentno = map.get("paymentNo");
				}
				resultmap.put("paymentno", paymentno);
				resultmap.put("message", thaw.getMsg()!=null?thaw.getMsg():"资金解冻失败");
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


}