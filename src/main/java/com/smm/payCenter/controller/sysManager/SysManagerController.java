package com.smm.payCenter.controller.sysManager;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.SendMailBO;
import com.smm.payCenter.bo.sysManager.SysManagerBo;
import com.smm.payCenter.controller.PageUtils.PageBean;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.MoneyException;
import com.smm.payCenter.domain.PaymentLog;
import com.smm.payCenter.domain.Role;
import com.smm.payCenter.domain.TreeNodeVO;
import com.smm.payCenter.tools.Message.ResultMessage;
import com.smm.payCenter.util.DateUtil;
import com.smmpay.common.encrypt.MD5;


/** 管理员,操作员
 * @ClassName: sysManagerController 
 * @Description: TODO
 * @author: zhangnan
 * @date: 2015年11月6日 下午1:47:56  
 */
@Controller
@RequestMapping("/sysManager")
public class SysManagerController implements Serializable{

	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(SysManagerController.class.getName());

	@Resource
	private SysManagerBo sysManagerBo;
	
	@Resource
	private SendMailBO sendMailBO;
	
	
	/** 管理员列表
	 * @Title: accountList 
	 * @Description: TODO
	 * @param account
	 * @param pno
	 * @return
	 * @author zhangnan/张楠
	 * @return: ModelAndView
	 * @createTime 2015年12月17日上午11:16:24
	 */
	@RequestMapping("/accountList")
	public ModelAndView accountList(String account,Integer pno) {
		ModelAndView mav=new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		map.put("account", account);
		
		List<Account> accountlist;
		try {
			
			// 分页
			int totalRecords = this.sysManagerBo.querySysAccountCount(map);
			if (pno == null) {
				pno = 1;
			}
			PageBean page = new PageBean(10, pno, totalRecords);
			int startNum = page.getStartNum();
			int endNum = page.getEndNum();
			
			map.put("startNum", startNum);
			map.put("endNum", endNum);
						
			
			accountlist = this.sysManagerBo.querySysAccount(map);
			
			mav.addObject("account", account);
			mav.addObject("accountlist", accountlist);
			mav.addObject("totalRecords", totalRecords);
			mav.addObject("totalPage",page.getTotalPages());// 总页数
			
			
			mav.setViewName("/sysManager/userManager");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		return mav;

	}

	/** 打开新增管理员dialog
	 * @Title: showAddAccount 
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	@RequestMapping("/showAddAccount")
	public ModelAndView showAddAccount() {
		System.out.println("新增管理员");
		Map<String,Object> map = new HashMap<String,Object>();
		//
		List<Role> roleList = this.sysManagerBo.queryRoleList(map);
		
		ModelAndView mav=new ModelAndView();
		mav.addObject("roleList", roleList);
		mav.setViewName("/dialog/addAccount");
		return mav;
	}
	
	/** 新增管理员
	 * @Title: addAccount 
	 * @Description: TODO
	 * @param accountName
	 * @param loginName
	 * @param roleId
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping("/addAccount")
	@ResponseBody
	public Map<String,Object> addAccount(String accountName,String loginName,Integer roleId,HttpServletRequest request,String accessAddress) {
		Map<String, Object> map = new HashMap<>();
		loginName = loginName+"@smm.cn";
		map.put("accountName", accountName);
		map.put("loginName", loginName);
		map.put("roleId", roleId);
		map.put("password", this.getPassword());
		Account account = (Account) request.getSession().getAttribute("userInfo");
		map.put("createdBy", account.getId());
		map.put("updatedBy", account.getId());
		try {
			map = this.sysManagerBo.addAccount(map);
			//开通管理员后，系统自动发送密码邮件到管理员邮箱
			System.out.println("开通管理员后，系统自动发送密码邮件到管理员邮箱");
			Map<String,String> param = new HashMap<String, String>();
			
			Role role = this.sysManagerBo.queryRoleById(map);
			
			param.put("accountName", accountName);
			param.put("loginName", loginName);
			param.put("password", (String) map.get("password"));
			param.put("roleName",role.getRoleName());
			
			param.put("accessAddress", accessAddress);//访问地址
			this.sendMailBO.sendHtmlMail(param, loginName, "openAccountSuccess", accountName+"开通支付后台服务");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return map;
	}
	
	/** 启用或者关闭管理员
	 * @Title: updateAccountStatus 
	 * @Description: TODO
	 * @param isenabled
	 * @param accountId
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/updateAccountStatus")
	@ResponseBody
	public Object updateAccountStatus(Integer isenabled,Integer accountId,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Account account = (Account) request.getSession().getAttribute("userInfo");
		map.put("updatedBy", account.getId());
		if (isenabled == 1) {
			map.put("isenabled", 0);
		}
		if (isenabled == 0) {
			map.put("isenabled", 1);
		}
		map.put("accountId", accountId);
		map.put("updatedat", DateUtil.doFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		try {
			this.sysManagerBo.updateAccountStatus(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return ResultMessage.TRANSFER_MONEY_SUCCESS;
	}
	
	//修改人员密码信息
	@RequestMapping("/updateAccount")
	public ModelAndView updateAccount(HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();
		System.out.println("修改人员信息");
		Account account = (Account) request.getSession().getAttribute("userInfo");
		System.out.println(account.toString());
		
		
		
		mav.addObject("accountId", account.getId());
		mav.setViewName("/sysManager/updateAccount");
		return mav;
	}
	
	/** 密码比对
	 * @Title: validatePassword 
	 * @Description: TODO
	 * @param request
	 * @return: void
	 */
	@RequestMapping("/validatePassword")
	@ResponseBody
	public Object validatePassword(HttpServletRequest request,String password) {
		Account account = (Account) request.getSession().getAttribute("userInfo");
		try {
			if (StringUtils.isEmpty(password)) {
				return ResultMessage.COMPARE_PASSWORD_FAIL;
			}
			password = MD5.md5(password);
			if (password.equals(account.getPwd())) {
				//匹配正确
				return ResultMessage.COMPARE_PASSWORD_SUCCESS;
			}else{
				//匹配错误
				return ResultMessage.COMPARE_PASSWORD_FAIL;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return ResultMessage.COMPARE_PASSWORD_FAIL;
	}
	
	
	/** 修改管理员密码
	 * @Title: updatePassword 
	 * @Description: TODO
	 * @param request
	 * @param newPassword
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/updatePassword")
	@ResponseBody
	public Object updatePassword(HttpServletRequest request,String newPassword) {
		Account account = (Account) request.getSession().getAttribute("userInfo");
		Map<String, Object> map = new HashMap<>();
		try {
			account.setPwd(MD5.md5(newPassword));
			map.put("pwd", MD5.md5(newPassword));
			map.put("accountId",account.getId());
			
			Integer userId = this.sysManagerBo.updateAccountPassword(map);
			System.out.println(userId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	/** 角色管理
	 * @Title: roleList 
	 * @Description: TODO
	 * @return
	 * @return: ModelAndView
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/roleList")
	public ModelAndView roleList(String roleNameParam,Integer pno) throws UnsupportedEncodingException {
		ModelAndView mav=new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();
		if (roleNameParam != null && !roleNameParam.equals("")) {
			roleNameParam = URLDecoder.decode( roleNameParam ,"utf-8");
		}
		
		map.put("roleName", roleNameParam);
		List<Role> roleList;
		try {
			// 分页
			int totalRecords = this.sysManagerBo.queryRoleCount(map);
			if (pno == null) {
				pno = 1;
			}
			PageBean page = new PageBean(10, pno, totalRecords);
			int startNum = page.getStartNum();
			int endNum = page.getEndNum();
			
			map.put("startNum", startNum);
			map.put("endNum", endNum);
			
			roleList = this.sysManagerBo.queryRoleList(map);
			
			mav.addObject("roleList", roleList);
			mav.addObject("roleNameParam", roleNameParam);
			mav.addObject("totalRecords", totalRecords);
			mav.addObject("totalPage",page.getTotalPages());// 总页数
			
			mav.setViewName("/sysManager/role");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return mav;
	}
	/** 菜单以树的形式展示在前台页面
	 * @Title: getTreeNode 
	 * @Description: TODO
	 * @return
	 * @return: List<TreeNodeVO>
	 */
	@RequestMapping("/getTreeNode")
	@ResponseBody
	public List<TreeNodeVO> getTreeNode() {
		Map<String,Object> param = new HashMap<String, Object>();
		List<TreeNodeVO> menuList = this.sysManagerBo.getTreeNode(param);
//		JSONArray jsonArray = JSONArray.fromObject(menuList);
//		System.out.println(jsonArray);
		return menuList;
	}
	
	/** 新增角色
	 * @Title: addRole 
	 * @Description: TODO
	 * @return
	 * @return: ModelAndView
	 */
	@RequestMapping("/addRole")
	public ModelAndView addRole() {
		ModelAndView mav=new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		try {
			mav.setViewName("/dialog/addRole");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return mav;
	}
	
	/** 新增角色和权限
	 * @Title: addRoleAndAuthorization 
	 * @Description: TODO
	 * @param roleName
	 * @param roleString
	 * @param menuIds
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/addRoleAndAuthorization")
	@ResponseBody
	public Object addRoleAndAuthorization(String roleName,String roleString,String menuIds) {
		Map<String, Object> map = new HashMap<>();
		map.put("roleName", roleName);
		map.put("roleString", roleString);
		map.put("menuIds", menuIds);
		try {
			this.sysManagerBo.addRoleAndAuthorization(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	/** 角色管理（ 重新分配权限）
	 * @Title: updaeRole 
	 * @Description: TODO
	 * @return
	 * @return: ModelAndView
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/updaeRole")
	public ModelAndView updaeRole(String roleId,String roleName) throws UnsupportedEncodingException {
		roleName = URLDecoder.decode( roleName ,"utf-8");
		ModelAndView mav=new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		map.put("roleId", roleId);
		//选中的节点id
		String ids = "";
		String treeNodeNames ="";
		try {
			//选中的节点
			List<TreeNodeVO> menuListChecked = this.sysManagerBo.getTreeNode(map);
			if (menuListChecked != null && menuListChecked.size() > 0) {
				for (int i = 0; i < menuListChecked.size(); i++) {
					ids += menuListChecked.get(i).getId()+",";
					treeNodeNames += menuListChecked.get(i).getName()+",";
				}
			}
			if (!ids.equals("")) {
				ids = ids.substring(0, ids.length()-1);
				treeNodeNames = treeNodeNames.substring(0, treeNodeNames.length()-1);
			}
			
			
			mav.addObject("ids", ids);
			mav.addObject("treeNodeNames", treeNodeNames);
			mav.addObject("roleId", roleId);
			mav.addObject("roleName",roleName);
			mav.setViewName("/dialog/updateRole");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return mav;

	}
	
	/** zTree树初始化
	 * @Title: getTreeNodeChecked 
	 * @Description: TODO
	 * @param roleId
	 * @return
	 * @return: List<TreeNodeVO>
	 */
	@RequestMapping("/getTreeNodeChecked")
	@ResponseBody
	public List<TreeNodeVO> getTreeNodeChecked(String roleId) {
		Map<String,Object> param = new HashMap<String, Object>();
		
		List<TreeNodeVO> menuList = this.sysManagerBo.getTreeNode(param);
		
		param.put("roleId", roleId);
		//选中的节点
		List<TreeNodeVO> menuListChecked = this.sysManagerBo.getTreeNode(param);
		if (menuListChecked != null && menuListChecked.size() > 0) {
			
			for (int i = 0; i < menuListChecked.size(); i++) {
				for (int j = 0; j < menuList.size(); j++) {
					if (menuListChecked.get(i).getId() == menuList.get(j).getId()) {//代表选中
						menuList.get(j).setChecked("true");
					}
				}
			}
		}
		
		System.out.println(menuListChecked.toString());
		return menuList;
	}
	
	/** 更新角色的权限
	 * @Title: updRoleAndAuthorization 
	 * @Description: TODO
	 * @param roleId
	 * @param menuIds
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/updRoleAndAuthorization")
	@ResponseBody
	public Object updRoleAndAuthorization(String roleId,String menuIds) {
		Map<String, Object> map = new HashMap<>();
		map.put("roleId", roleId);
		map.put("menuIds", menuIds);
		try {
			int flag = this.sysManagerBo.deleteAuthorization(map);
			if (flag >=0) {
				this.sysManagerBo.AndAuthorization(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	
	
	/**异常交易信息*/
	
	@RequestMapping("/unusualTransactionList")
	public ModelAndView unusualTransactionList(String startTime,String endTime,Integer pno) {
		ModelAndView mav = new ModelAndView();
		Map<String,Object> map = new HashMap<String, Object>();
		List<MoneyException> moneyExceptionList;
		
		//时间处理 一天的开始时间
		if (startTime != null && !startTime.equals("")) {
			String startTime1 = DateUtil.doFormatDate(DateUtil.startOfTodDay(startTime), "yyyy-MM-dd HH:mm:ss");
			map.put("startTime", startTime1);
		}else {
			map.put("startTime", null);
		}
		
		//时间处理  一天的结束时间
		if (endTime != null && !endTime.equals("")) {
			String endTime1 = DateUtil.doFormatDate(DateUtil.endOfTodDay(endTime), "yyyy-MM-dd HH:mm:ss");
			map.put("endTime", endTime1);
		}else {
			map.put("endTime", null);
		}
//		map.put("startTime", startTime);
//		map.put("endTime", endTime);
		try {
			
			// 分页
			int totalRecords = this.sysManagerBo.queryMoneyExceptionCount(map);
			if (pno == null) {
				pno = 1;
			}
			PageBean page = new PageBean(10, pno, totalRecords);
			int startNum = page.getStartNum();
			int endNum = page.getEndNum();
			
			map.put("startNum", startNum);
			map.put("endNum", endNum);
									
			moneyExceptionList = this.sysManagerBo.queryMoneyExceptionList(map);
			
			mav.addObject("startTime",startTime);
			mav.addObject("endTime",endTime);
			mav.addObject("moneyExceptionList", moneyExceptionList);
			mav.addObject("totalRecords", totalRecords);
			mav.addObject("totalPage",page.getTotalPages());// 总页数
			
			mav.setViewName("/sysManager/unusualTransactionList");;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		return mav;
	}
	
	/** 交易记录
	 * @Title: transactionRecords 
	 * @Description: TODO
	 * @param paymentId
	 * @param pno
	 * @return
	 * @return: ModelAndView
	 */
	@RequestMapping("/transactionRecords")
	public ModelAndView transactionRecords() {
		ModelAndView mav=new ModelAndView();
		try {
			PageBean page = new PageBean(10, 1, 0);
			mav.addObject("totalRecords", 0);
			mav.addObject("totalPage",page.getTotalPages());// 总页数
			mav.setViewName("/sysManager/transactionRecords");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return mav;
	}
	
	/** 点击查询交易记录
	 * @Title: transactionRecord 
	 * @Description: TODO
	 * @param paymentId
	 * @param pno
	 * @return
	 * @return: ModelAndView
	 */
	@RequestMapping("/transactionRecord")
	public ModelAndView transactionRecord(String paymentId,Integer pno) {
		ModelAndView mav=new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		map.put("paymentId", paymentId);
		List<PaymentLog> list;
		try {
			// 分页
			int totalRecords = this.sysManagerBo.queryPaymentLogCount(map);
			if (pno == null) {
				pno = 1;
			}
			PageBean page = new PageBean(10, pno, totalRecords);
			int startNum = page.getStartNum();
			int endNum = page.getEndNum();
			
			map.put("startNum", startNum);
			map.put("endNum", endNum);
			
			list = this.sysManagerBo.queryPaymentLogData(map);
			
			mav.addObject("paymentLogList", list);
			mav.addObject("totalRecords", totalRecords);
			mav.addObject("totalPage",page.getTotalPages());// 总页数
			mav.addObject("paymentId", paymentId);
			
			mav.setViewName("/sysManager/transactionRecords");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return mav;

	}
	
	/** 打开修改权限的dialog
	 * @Title: updateAccountByDiallog 
	 * @Description: TODO
	 * @return
	 * @return: ModelAndView
	 */
	@RequestMapping("/updateAccountByDiallog")
	public ModelAndView updateAccountByDiallog() {
		ModelAndView mav  = new ModelAndView();
		try {
			mav.setViewName("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return mav;
	}
	
	/** 检验登录（账户名）是否重复
	 * @Title: checkLoginName 
	 * @Description: TODO
	 * @param loginName
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/checkLoginName")
	@ResponseBody
	public Object checkLoginName(String loginName) {
		Map<String, Object> map = new HashMap<>();
		loginName = loginName+"@smm.cn";
		try {
			String flag = this.sysManagerBo.checkLoginName(loginName);
			if (flag.equals("ERROR")) {
				return ResultMessage.UPDATE_SUCCESS_RESULT;
			}else {
				return ResultMessage.UPDATE_FAIL_RESULT;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return ResultMessage.UPDATE_FAIL_RESULT;
	}
	
	/** 处理异常交易信息
	 * @Title: updateMoneyException 
	 * @Description: TODO
	 * @param moneyExceptionId
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/updateMoneyException")
	@ResponseBody
	public Object updateMoneyException(Integer moneyExceptionId) {
		Map<String, Object> map = new HashMap<>();
		map.put("moneyExceptionId", moneyExceptionId);
		try {
			String flag = this.sysManagerBo.updateMoneyException(map);
			if (flag.equals("OK")) {
				return ResultMessage.UPDATE_SUCCESS_RESULT;
			}else {
				return ResultMessage.UPDATE_FAIL_RESULT;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return ResultMessage.UPDATE_FAIL_RESULT;
	}
	
	public static void main(String[] args) {
		SysManagerController sys = new SysManagerController();
		sys.getPassword();
	}
	/** 随机生成6位数的字符串
	 * @Title: getPassword 
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	private String getPassword() {
		Random r = new Random(); 
		Double d = r.nextDouble(); 
		System.out.println(d); 
		String s = d + ""; 
		s=s.substring(3,3+6); 
		System.out.println(s); 
		return s;
	}
}
