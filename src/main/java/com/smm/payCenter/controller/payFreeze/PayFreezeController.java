package com.smm.payCenter.controller.payFreeze;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.CheckRepeatBo;
import com.smm.payCenter.bo.FnlLogBO;
import com.smm.payCenter.bo.TransactionPaymentBO;
import com.smm.payCenter.bo.payFreeze.PayFreezeBO;
import com.smm.payCenter.bo.useraccount.UserAccountBo;
import com.smm.payCenter.controller.PageUtils.PageBean;
import com.smm.payCenter.controller.vipmanage.vipManageController;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.FnlLog;
import com.smm.payCenter.domain.PaymentRecord;
import com.smm.payCenter.domain.TransactionDetail;
import com.smm.payCenter.domain.UserAccountEntity;
import com.smm.payCenter.util.DateUtil;
import com.smm.payCenter.util.IpUtil;
import com.smm.payCenter.util.JSONUtil;
import com.smmpay.common.author.Authory;
import com.smmpay.common.encrypt.Base64;
import com.smmpay.common.encrypt.DesCrypt;
import com.smmpay.common.request.RequestDataProxy;
import com.smmpay.inter.AuthorService;
import com.smmpay.inter.dto.res.ReturnDTO;
import com.smmpay.inter.smmpay.CallManualFreezeService;

import freemarker.log.Logger;


/**
 * 
 * @author hece
 *
 * 支付冻结
 */

@Controller
@RequestMapping("/payFreeze")
public class PayFreezeController {
	
	@Resource
	private AuthorService authorService;
	@Autowired
	private CallManualFreezeService callManualFreezeService;
	@Resource
	private PayFreezeBO payfreeze;
	@Resource
	private FnlLogBO fnlLogBO;
	@Resource
	private CheckRepeatBo checkRepeatBo;
	@Resource
	private UserAccountBo userAccountBo;
	
	@Resource
	private TransactionPaymentBO transactionPaymentBO;
	private static Logger logger = Logger.getLogger(vipManageController.class.getName());

	/**
	 * 冻结列表,关闭订单
	 * */
	@RequestMapping("/freezeList")
	public ModelAndView accountList(String prisename,String mallorderid,String startTime,String endTime,Integer pno,Integer dstatus,Integer paymentid,String companyName,String money,HttpServletRequest request) {
		if (paymentid != null) {
			Account account = (Account) request.getSession().getAttribute("userInfo");
			//加入管理员操作日志
			FnlLog log = new FnlLog();
			log.setFnlAccount(account.getId());
			log.setType("强制冻结");
			log.setContent("关闭-交易编号: "+paymentid);
			String stdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			log.setCreatedAt(stdate);
			log.setUserCompanyName(companyName);
			log.setTransferMoney(Double.valueOf(money));
			log.setIp(IpUtil.getIpAddr());
			fnlLogBO.addFnlLog(log);
			
			payfreeze.closeOrder(paymentid); // 关闭订单
		}
		ModelAndView modelview =new ModelAndView("/payFreeze/payFreeze");
		Map<String,Object> paymap = new HashMap<String, Object>();
		if (dstatus == null) {
			dstatus = 0;
		}
		paymap.put("dstatus",dstatus);
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
		}
		//查询记录总条数
		int paynum = payfreeze.queryPayFreezeNum(paymap);
		if (pno == null) { 
			pno = 1;
		}
		PageBean page = new PageBean(20, pno, paynum);
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		paymap.put("startNum", startNum);
		paymap.put("endNum", endNum);
		
		List<PaymentRecord> payList = payfreeze.payFreezeList(paymap);
		modelview.addObject("paylist",payList);
		modelview.addObject("totalRecords",paynum);//总条数
		modelview.addObject("totalPage",page.getTotalPages());//总页数
		modelview.addObject("startTime",startTime);
		modelview.addObject("endTime",endTime);
		modelview.addObject("dstatus",dstatus);
		modelview.addObject("prisename",prisename);
		modelview.addObject("mallorderid",mallorderid);
		return modelview;
	}
	
	/**
	 * 详情
	 * */
	@RequestMapping("/freezeDetail")
	public ModelAndView accountDetail(Integer paymentid,Integer status) {
		ModelAndView modelview =new ModelAndView("/payFreeze/freezeDetail");
		TransactionDetail trans = payfreeze.payFreezeDetail(paymentid);
		modelview.addObject("status",status);
		modelview.addObject("td",trans);
		modelview.addObject("paymentid",paymentid);
		modelview.addObject("hidden",0); // 不隐藏确认冻结按钮
		return modelview;
	}
	
	/**
	 * 转账失败详情
	 * */
	@RequestMapping("/refundDetail")
	public ModelAndView refundDetail(Integer paymentid,Integer status) {
		ModelAndView modelview =new ModelAndView("/payFreeze/freezeDetail");
		TransactionDetail trans = payfreeze.payFreezeDetail(paymentid);
		modelview.addObject("status",status);
		modelview.addObject("td",trans);
		modelview.addObject("paymentid",paymentid);
		modelview.addObject("hidden",1); // 隐藏确认冻结按钮
		return modelview;
	}
	
	/**
     * 去确认/取消页面
     * @param trid
     * @param trstatus
     * @return
     */
    @RequestMapping("goupdate")
	public ModelAndView goUpdatePage(Integer payid,Integer trstatus,String companyName,String money) {
		ModelAndView mav=new ModelAndView();
		//返回页面处理
		mav.setViewName("/home/debitsconfirm");

		mav.addObject("payid", payid);
		mav.addObject("trstatus", trstatus);
		mav.addObject("companyName", companyName);
		mav.addObject("money", money);
		return mav;
	}
	
	
	
	@RequestMapping("/freezeMoney")
	@ResponseBody
	public Map<String, String> freezeMoney(HttpServletRequest request) {
		synchronized (this) {
			
		String paymentid = request.getParameter("paymentid");
		String companyName =request.getParameter("companyName");
		String money =request.getParameter("money");
		Map<String, String> result = new HashMap<>();
		
		Integer count = checkRepeatBo.checkFreezeRecord(Integer.valueOf(paymentid));
		if(count!=null&&count>0){
			result.put("info", "已存在有效的冻结记录!资金冻结失败");
			return result;
		}
		
		 TransactionDetail tran=checkRepeatBo.queryDetailById(Integer.valueOf(paymentid));
		 if(tran==null){
			 result.put("info", "失败,对应的交易记录不存在");
				return result; 
		 }else if(tran.getPaymentStatus()!=0) {
			 result.put("info", "失败,对应的交易记录冻结状态错误");
				return result; 
		}
		DecimalFormat df=new DecimalFormat("######0.00");
		 String str=String.valueOf(tran.getBuyerUserId())+tran.getBuyerMallUserName()+
				    String.valueOf(tran.getSellerUserId())+String.valueOf(tran.getSellerMallUserName())+
				    String.valueOf(tran.getMallOrderId())+String.valueOf(df.format(tran.getDealMoney()))+tran.getOrderCreateTime();
		 byte[] desColums;
		try {
			desColums = DesCrypt.encryptMode(str.getBytes("utf-8"));
			String desColumsStr = Base64.getBase64(desColums);
			logger.info("加密前："+str+"加密值：desColumsStr："+desColumsStr+",数据库加密字段值："+tran.getVerifyCode());
			 if(!desColumsStr.equals(tran.getVerifyCode())){
				 result.put("info", "失败,交易信息涉嫌被篡改");
				 logger.info("失败,交易信息涉嫌被篡改");
				 logger.info("加密比对失败");
				 return result; 
			 }
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
			
		UserAccountEntity userAccountm=userAccountBo.getUserAccountById(Integer.valueOf(tran.getBuyerUserId()));
		if(userAccountm.getUseMoney()==null||userAccountm.getUseMoney()<=tran.getDealMoney()){
			result.put("info", "失败,买家余额不足");
			 logger.info("失败,买家余额不足");

			return result;
		}
		
		Account account = (Account) request.getSession().getAttribute("userInfo");
		//加入管理员操作日志
		FnlLog log = new FnlLog();
		log.setFnlAccount(account.getId());
		log.setType("强制冻结");
		log.setContent("确定冻结-交易编号: "+paymentid);
		String stdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		log.setCreatedAt(stdate);
		log.setUserCompanyName(companyName);
		log.setTransferMoney(Double.parseDouble(money));
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
			ReturnDTO dto = callManualFreezeService.manualFreezeHandle(map);
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
	
	public String formatMoney(String money){
		if (money.indexOf(".") != -1) {
			String prefix = money.substring(0,money.indexOf("."));
			// + money.substring(money.indexOf("."),money.indexOf(".")+3);
			String suffix = money.substring(money.indexOf("."));
			if (suffix.length() > 3) {
				suffix = suffix.substring(0,3);
			}
			return prefix + suffix;
		}
		return money;
	}
}
