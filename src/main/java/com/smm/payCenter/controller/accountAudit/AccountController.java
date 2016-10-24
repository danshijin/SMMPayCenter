package com.smm.payCenter.controller.accountAudit;

import java.io.Serializable;




import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.FnlLogBO;
import com.smm.payCenter.bo.accountAudit.AccountAuditBo;
import com.smm.payCenter.controller.PageUtils.PageBean;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.FnlLog;
import com.smm.payCenter.domain.UserAccount;
import com.smm.payCenter.domain.UserAccountBanEntity;
import com.smm.payCenter.tools.Message.ResultMessage;
import com.smm.payCenter.util.DateUtil;
import com.smm.payCenter.util.IpUtil;


/**
 * @author zhangnan
 * 账户审核
 */
@Controller
@RequestMapping("/accountAudit")
public class AccountController implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(AccountController.class.getName());
	
	@Resource
	private AccountAuditBo accountAuditBo;
	@Resource
	private FnlLogBO fnlLogBO;
	
	/** 会员绑定账户审核列表
	 * @Title: accountList 
	 * @Description: TODO
	 * @param pno
	 * @param userName
	 * @param companyName
	 * @param mobilePhone
	 * @param bankAccountNo
	 * @param startTime
	 * @param endTime
	 * @return
	 * @return: ModelAndView
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/accountList")
	public ModelAndView accountList(Integer pno,String userNames,String companyName,String mobilePhone,String bankAccountNo,
			String startTime,String endTime,Integer auditStatus) throws UnsupportedEncodingException {
		ModelAndView mav=new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		List<UserAccount> accountList;
		if (companyName != null ) {
			companyName = URLDecoder.decode( companyName ,"utf-8");
		}
		if (auditStatus == null || auditStatus == 9) {
			//全部
			auditStatus = 9;
			map.put("auditStatus", null);
		}else {
			map.put("auditStatus", auditStatus);
		}
		
		//划款
		if (auditStatus == 3) {
			map.put("is_payment", 1);//已经划款
			map.put("auditStatus", null);//已经划款
		}else {
			map.put("is_payment", null);
		}
		map.put("userName", userNames);
		map.put("companyName", companyName);
		map.put("mobilePhone", mobilePhone);
		map.put("bankAccountNo", bankAccountNo);
		if (startTime != null && !startTime.equals("")) {
			String startTime1 = DateUtil.doFormatDate(DateUtil.startOfTodDay(startTime), "yyyy-MM-dd HH:mm:ss");
			map.put("startTime", startTime1);
		}else {
			map.put("startTime", null);
		}
		
		if (endTime != null && !endTime.equals("")) {
			String endTime1 = DateUtil.doFormatDate(DateUtil.endOfTodDay(endTime), "yyyy-MM-dd HH:mm:ss");
			map.put("endTime", endTime1);
		}else {
			map.put("endTime", null);
		}
		
		
//		if (startTime != null && startTime !="") {
//			map.put("startTime", DateUtil.startOfTodDay(startTime));
//		}else{
//			map.put("startTime",null);
//		}
//		if (endTime != null && endTime != "") {
//			map.put("endTime",  DateUtil.endOfTodDay(endTime));
//		}else{
//			map.put("endTime", null);
//		}
//		
		
		try {
			
			// 分页
			int totalRecords = this.accountAuditBo.queryDataCount(map);
			if (pno == null) {
				pno = 1;
			}
			PageBean page = new PageBean(20, pno, totalRecords);
			int startNum = page.getStartNum();
			int endNum = page.getEndNum();
			
			map.put("startNum", startNum);
			map.put("endNum", endNum);
			
			
			accountList = this.accountAuditBo.queryData(map);
			
			mav.addObject("totalRecords",totalRecords);
			mav.addObject("totalPage",page.getTotalPages());// 总页数
			mav.addObject("accountList",accountList);
			
			//搜素条件
			mav.addObject("userNames", userNames);
			mav.addObject("companyName", companyName);
			mav.addObject("mobilePhone", mobilePhone);
			mav.addObject("bankAccountNo", bankAccountNo);
			mav.addObject("auditStatus", auditStatus);
			mav.addObject("startTime", startTime);
			mav.addObject("endTime", endTime);
			
			mav.setViewName("/audit/accountAudit");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		return mav;

	}
	
	
	/** 划款
	 * @Title: transferMoney 
	 * @Description: TODO
	 * @param drawMoney
	 * @param bankAccountNo
	 * @return
	 * @return: Object
	 */
	@RequestMapping(value="/transferMoney",produces="application/json")
	@ResponseBody
	public Object transferMoney(BigDecimal drawMoney,String bankAccountNo,Integer bindId) {
		Map<String, Object> map = new HashMap<>();
		map.put("drawMoney", drawMoney);
		map.put("bankAccountNo", bankAccountNo);
		map.put("bindId", bindId);
		map.put("flag", 0);
		try {
			//划款
			this.accountAuditBo.updateBankAuditStatus(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return ResultMessage.TRANSFER_MONEY_SUCCESS;
	}
	
	
	/** 审核通过
	 * @Title: auditPass 
	 * @Description: TODO
	 * @param drawMoney
	 * @param bankAccountNo
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/auditPass")
	@ResponseBody
	public Object auditPass(BigDecimal drawMoney,String bankAccountNo,Integer bindId,String companyName,HttpServletRequest request) {
		Account account = (Account) request.getSession().getAttribute("userInfo");
		Map<String, Object> map = new HashMap<>();
		map.put("bankAccountNo", bankAccountNo);
		map.put("drawMoney", drawMoney);
		map.put("bindId", bindId);
		map.put("flag", 1);
		try {
			this.accountAuditBo.updateBankAuditStatus(map);
			FnlLog log = new FnlLog();
			log.setFnlAccount(account.getId());
			log.setType("审核绑定银行卡");
			log.setContent("通过");
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			log.setCreatedAt(date);
			log.setUserCompanyName(companyName);
			log.setIp(IpUtil.getIpAddr());
			fnlLogBO.addFnlLog(log);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return ResultMessage.CHECK_SUCCESS_RESULT;
	}
	
	/** 撤销银行卡绑定
	 * @Title: cancelBind 
	 * @Description: TODO
	 * @param bankAccountNo
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/cancelBind")
	@ResponseBody
	public Object cancelBind(String bankAccountNo,Integer bindId) {
		Map<String, Object> map = new HashMap<>();
		map.put("bankAccountNo", bankAccountNo);
		map.put("bindId", bindId);
		map.put("flag", 2);
		try {
			this.accountAuditBo.updateBankAuditStatus(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return ResultMessage.CHECK_SUCCESS_RESULT;
	}
	
	/** 关闭银行卡绑定
	 * @Title: closeBind 
	 * @Description: TODO
	 * @param drawMoney
	 * @param bankAccountNo
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/closeBind")
	@ResponseBody
	public Object closeBind(BigDecimal drawMoney,String bankAccountNo,Integer bindId,String companyName,Integer userId,HttpServletRequest request) {
		Account account = (Account) request.getSession().getAttribute("userInfo");
		Map<String, Object> map = new HashMap<>();
		map.put("bankAccountNo", bankAccountNo);
		map.put("drawMoney", drawMoney);
		map.put("bindId", bindId);
		map.put("flag", 3);
		List<UserAccountBanEntity> userbanklist = new ArrayList<UserAccountBanEntity>();
		try {
			if(userId != null){
				//通过用户ID查询银行卡信息
				userbanklist = accountAuditBo.queryUserBindBnak(userId);
			}
//			int availableBankCount = 0;
//			for(UserAccountBanEntity abe : userbanklist){
//				if(abe.getAuditStatus() == 1){
//					availableBankCount++;
//				}
//			}
			if(userbanklist.size() > 1){
				this.accountAuditBo.updateBankAuditStatus(map);
				//加入管理员操作日志
				FnlLog log = new FnlLog();
				log.setFnlAccount(account.getId());
				log.setType("审核绑定银行卡");
				log.setContent("关闭");
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				log.setCreatedAt(date);
				log.setUserCompanyName(companyName);
				log.setIp(IpUtil.getIpAddr());
				fnlLogBO.addFnlLog(log);
			}else{
				return ResultMessage.REJECT_ERROR_RESULT;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return ResultMessage.GUANBI_SUCCESS_RESULT;
	}
	
	/** 公司列表
	 * @Title: queryUserAccountList 
	 * @Description: TODO
	 * @param companyName
	 * @return
	 * @return: List<UserAccount>
	 */
	@RequestMapping("/queryUserAccountList")
	@ResponseBody
	public List<UserAccount> queryUserAccountList(String companyName) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("companyName", companyName);
		List<UserAccount> list = null;
		try {
			list = this.accountAuditBo.queryUserAccountList(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return list;
	}
	
	@RequestMapping("/showCompany")
	public ModelAndView showCompany(Integer userId) {
		ModelAndView mav = new ModelAndView();
		Map<String,Object> map =  new HashMap<String, Object>();
		map.put("userId", userId);
		UserAccount userAccount;
		try {
			userAccount = this.accountAuditBo.queryUserAccountByUserId(map);
			mav.addObject("userAccount", userAccount);
			mav.setViewName("/dialog/showCompany");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
	}
}
