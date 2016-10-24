package com.smm.payCenter.controller.vipmanage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.CheckRepeatBo;
import com.smm.payCenter.bo.FnlLogBO;
import com.smm.payCenter.bo.SendMailBO;
import com.smm.payCenter.bo.useraccount.UserAccountBo;
import com.smm.payCenter.controller.PageUtils.PageBean;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.FnlLog;
import com.smm.payCenter.domain.UserAccountBanEntity;
import com.smm.payCenter.domain.UserAccountEntity;
import com.smm.payCenter.domain.UserPayChannel;
import com.smm.payCenter.util.DateUtil;
import com.smm.payCenter.util.IpUtil;
import com.smmpay.common.author.Authory;
import com.smmpay.common.request.RequestDataProxy;
import com.smmpay.inter.AuthorService;
import com.smmpay.inter.dto.res.ResOpenAccountDTO;
import com.smmpay.inter.dto.res.ReturnDTO;
import com.smmpay.inter.smmpay.CancelAccountService;

import freemarker.log.Logger;

@Controller
@RequestMapping("/vip")
public class vipManageController {

	@Resource
	private UserAccountBo userAccountBo;
	@Resource
	private SendMailBO sendMailBO;
	@Resource
	private AuthorService authorService;
	@Resource
	private CancelAccountService cancelAccount;
	@Resource
	private FnlLogBO fnlLogBO;
	@Resource CheckRepeatBo checkRepeatBo;
	
	
	@Value("#{tradeTime['frontHost']}")
	private String frontHost;
	private static Logger logger = Logger.getLogger(vipManageController.class.getName());

	/**
	 * 会员列表
	 */
	@RequestMapping("vipList")
	public ModelAndView viplist(HttpServletRequest req, Integer pno) {
		String auditStatus = req.getParameter("auditStatus");
		String userName = req.getParameter("userName");
		String companyName = req.getParameter("companyName");
		String mobilePhone = req.getParameter("mobilePhone");
		String bankNO = req.getParameter("bankNO");
		String statusDate = null;
		String endDate = null;
		if (req.getParameter("statusDate") != null && !req.getParameter("statusDate").equals("")) {
			statusDate = DateUtil.doFormatDate(DateUtil.startOfTodDay(req.getParameter("statusDate")),
					"yyyy-MM-dd HH:mm:ss");
		}
		if (req.getParameter("endDate") != null && !req.getParameter("endDate").equals("")) {
			endDate = DateUtil.doFormatDate(DateUtil.endOfTodDay(req.getParameter("endDate")), "yyyy-MM-dd HH:mm:ss");
		}

		UserAccountEntity useraccout = new UserAccountEntity();
		if (auditStatus != null && !auditStatus.equals("") && Integer.valueOf(auditStatus) != -1) {
			useraccout.setAuditStatus(auditStatus);

		} else {
			auditStatus = "-1";
		}
		useraccout.setUserName(userName);
		useraccout.setCompanyName(companyName);
		useraccout.setMobilePhone(mobilePhone);
		useraccout.setBankNO(bankNO);
		useraccout.setStatusDate(statusDate);
		useraccout.setEndDate(endDate);
		if (pno == null || pno.equals("")) {
			pno = 1;
		}
		List<UserAccountEntity> listmap = userAccountBo.vipLsit(useraccout);
		int totalRecords = 0;
		if (listmap != null) {
			totalRecords = listmap.size();
		}
		ModelAndView view = new ModelAndView("vipManage/vipList");
		PageBean page = new PageBean(10, pno, totalRecords);
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();

		useraccout.setStartNum(startNum);
		useraccout.setEndNum(endNum);
		List<UserAccountEntity> list = userAccountBo.vipLsit(useraccout);
		view.addObject("list", list);
		view.addObject("status", auditStatus);
		view.addObject("user", useraccout);

		view.addObject("statusDate", req.getParameter("statusDate"));
		view.addObject("endDate", req.getParameter("endDate"));
		view.addObject("totalRecords", totalRecords);
		view.addObject("totalPage", page.getTotalPages());// 总页数
		return view;
	}

	/**
	 * 会员详情
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("vipDetails")
	public ModelAndView vipDetails(Integer userId) {
		ModelAndView view = new ModelAndView("vipManage/vipDetails");
		UserAccountEntity useraccount = userAccountBo.vipDetaials(userId);// 会员信息
		view.addObject("vip", useraccount);
		List<UserAccountBanEntity> banklist = userAccountBo.useraccountBank(userId);// 会员银行信息
		view.addObject("banklist", banklist);
		logger.info("frontHost"+frontHost);
		view.addObject("frontHost", frontHost);
		return view;
	}

	/**
	 * 会员审核
	 * 
	 * @return 审核状态 id
	 */
	@RequestMapping("vipmanage")
	@ResponseBody
	public Map<String, Object> vipmanage(String auditStatus, Integer userId, HttpServletRequest request) {
		synchronized(this){
		Map<String, Object> map = new HashMap<>();
		Account account = (Account) request.getSession().getAttribute("userInfo");
		UserAccountEntity useraccout = new UserAccountEntity();
		useraccout.setAuditStatus(auditStatus);
		useraccout.setUserId(userId);
		useraccout.setAuditId(String.valueOf(account.getId()));
		Map<String, String> checkmap = new HashMap<>();
							checkmap.put("userId", userId+"");
		Integer num=checkRepeatBo.checkUserAccount(checkmap);//检查会员是否已经开户
		final UserAccountEntity useraccount = userAccountBo.vipDetaials(userId);// 获取会员信息
			if(useraccount==null){
				logger.info("审核记录不存在!");
				map.put("Result", "err");
				map.put("msg", "审核记录不存在!");
				return map;
			}else if(Integer.valueOf(useraccount.getAuditStatus())!=0){
				logger.info("审核记录已通过或者已关闭!");
				map.put("Result", "err");
				map.put("msg", "审核记录已通过或者已关闭!");
				return map;	
			}		
		if(num>=1){
			logger.info("会员审核重复操作,审核失败,此会员账号已开通!");
			map.put("Result", "err");
			map.put("msg", "审核失败,此会员账号已开通!");
			return map;
		}
	
		

		if (Integer.valueOf(auditStatus) == 1) {// 如果是审核通过 调用开户接口
			ResOpenAccountDTO data = userAccountBo.smmpayUser(useraccount);
			String status = data.getStatus();
			String statusText = data.getStatusText();
			final String subAccNm = data.getSubAccNm();
			final String subAccNo = data.getSubAccNo();
			if (!"AAAAAAA".equals(status)) {
				map.put("Result", "err");
				map.put("msg", statusText);
				return map;
			}
			
			UserPayChannel userpay = new UserPayChannel();
			userpay.setUserId(userId);
			userpay.setUserAccountName(subAccNm);
			userpay.setUserAccountNo(subAccNo);
			userpay.setUserAccountStatus(0);
			userAccountBo.addUserPayChannel(userpay);
			// 异步发送邮件
			Thread th = new Thread(new Runnable() {
				public void run() {
					try {
						sendMailBO.auditSuccessMail(useraccount.getContactName(), subAccNm, subAccNo,
								useraccount.getUserName());
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			});

			th.start();

		}

		userAccountBo.useraccoutshenhe(useraccout);

		//加入管理员操作日志
		FnlLog log = new FnlLog();
		log.setFnlAccount(account.getId());
		log.setType("审核会员注册");
		if(auditStatus.equals("1")){			
			log.setContent("已通过");
		}else if(auditStatus.equals("2")){
			log.setContent("已关闭");
		}
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		log.setCreatedAt(date);
		log.setIp(IpUtil.getIpAddr());
		log.setUserCompanyName(useraccount.getCompanyName());
		fnlLogBO.addFnlLog(log);
		
		map.put("msg", "");
		map.put("Result", "Success");

		return map;
		}
	}

	/**
	 * 注销用户
	 */
	@RequestMapping(value = "cancelUser", produces = "application/json")
	@ResponseBody
	public Map<String, Object> cancelUser(String userid, String userAccountNo,HttpServletRequest request) {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		try {
			String jsonStr = "{\"data\":[{\"subAccNo\":\"" + userAccountNo + "\"}]}";
			logger.info("注销用户入参============" + jsonStr);
			if (Authory.token == null)
				RequestDataProxy.getAccessToken(authorService);
			String str = userAccountNo;
			Map<String, String> mapParam = RequestDataProxy.getRequestParam(jsonStr, str);
			// 注销用户接口
			ReturnDTO cancel = cancelAccount.cancelAccount(mapParam);
			logger.info("注销用户返回状态=" + cancel.getStatus());
			if (cancel.getStatus().equals("000000")) {
				logger.info("注销用户成功>>>>>>>>>>>>>>" + cancel.getMsg());
				// 修改用户状态为关闭
				Map<String, Object> map = new HashMap<String, Object>();
				Account account = (Account) request.getSession().getAttribute("userInfo");
				map.put("userid", userid);
				map.put("accountId", account.getId());
				userAccountBo.updateUserStatus(map);
				//根据用户ID查询支付渠道信息
				UserPayChannel userpay = userAccountBo.queryUserPayChannelByUserId(userid);
				//修改用户支付渠道状态为关闭
				if(userpay != null && userpay.getUserPayChannelId() != null && !userpay.getUserPayChannelId().equals("")){
					userAccountBo.updateUserPayChannelStatus(userpay.getUserPayChannelId());
				}
				//查询用户银行卡信息
				List<UserAccountBanEntity> userbanklist = userAccountBo.queryUserBank(userid);
				for(int i=0;i<userbanklist.size();i++){
					UserAccountBanEntity ucbank = userbanklist.get(i);
					if(ucbank != null && ucbank.getBindId() != null && !ucbank.getBindId().equals("")){
						//修改银行卡状态为关闭
						userAccountBo.updateUserBankStatus(ucbank.getBindId());
					}
				}
				resultmap.put("result", "success");
				resultmap.put("message", cancel.getMsg());
				return resultmap;
			} else {
				logger.info("注销用户失败>>>>>>>>>>>>>>" + cancel.getMsg());
				resultmap.put("result", "error");
				resultmap.put("message", cancel.getMsg());
				return resultmap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultmap.put("result", "success");
		resultmap.put("message", "");
		return resultmap;
	}
	
	//修改会员信息 
	@RequestMapping("saveManage")
	@ResponseBody
	public Map<String, Object> saveManage( HttpServletRequest req ) {	
		Map<String, Object> map = new HashMap<>();
		UserAccountEntity useraccount=new UserAccountEntity();
		useraccount.setCompanyAddr(req.getParameter("companyAddr"));
		useraccount.setCertificateNo(req.getParameter("certificateNo"));
		useraccount.setContactName(req.getParameter("contactName"));
		useraccount.setMobilePhone(req.getParameter("mobilePhone"));
		useraccount.setCompanyPostCode(req.getParameter("companyPostCode"));
		useraccount.setPhone(req.getParameter("phone"));
		useraccount.setUserId(Integer.valueOf(req.getParameter("userId")));
		int a=userAccountBo.saveUserAccount(useraccount);
		if(a>0){
			map.put("code", "success");
			map.put("msg", "保存成功! ");	
		}else {
			map.put("code", "erron");
			map.put("msg", "保存失败！ ");
			
		}

		return map;

	}

	//关闭用户 
	@RequestMapping("auditStatusClose")
	@ResponseBody
	public Map<String, Object> auditStatusClose(HttpServletRequest request,String userid){
		Map<String, Object> map = new HashMap<>();
		try {
			Account account = (Account) request.getSession().getAttribute("userInfo");
			map.put("userid", userid);
			map.put("accountId", account.getId());
			userAccountBo.updateUserStatus(map);
			map.put("result", "success");
			map.put("message", "关闭成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "error");
			map.put("message", "关闭异常");
		}
		
		return map;
	}
	
}
