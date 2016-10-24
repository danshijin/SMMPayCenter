package com.smm.payCenter.bo.sysManager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.sysManager.SysManagerBo;
import com.smm.payCenter.dao.MenuDao;
import com.smm.payCenter.dao.MoneyExceptionDao;
import com.smm.payCenter.dao.PaymentLogDao;
import com.smm.payCenter.dao.RoleDao;
import com.smm.payCenter.dao.Account.AccountDao;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.MoneyException;
import com.smm.payCenter.domain.PaymentLog;
import com.smm.payCenter.domain.Role;
import com.smm.payCenter.domain.TreeNodeVO;
import com.smm.payCenter.util.DateUtil;
import com.smmpay.common.encrypt.MD5;

/** 系统管理模块
 * @ClassName: SysManagerBoImpl 
 * @Description: TODO
 * @author: zhangnan
 * @date: 2015年11月20日 上午9:37:00  
 */
@Service
public class SysManagerBoImpl implements SysManagerBo {
	
	@Resource
	private AccountDao accountDao;
	
	@Resource
	private MenuDao menuDao;
	
	@Resource
	private RoleDao roloDao;
	
	@Resource
	private MoneyExceptionDao moneyExceptionDao;
	
	@Resource
	private PaymentLogDao payMentLogDao; 
	
	@Override
	public List<Account> querySysAccount(Map<String, Object> map) {
		return this.accountDao.querySysAccountList(map);
	}

	@Override
	public int querySysAccountCount(Map<String, Object> map) {
		return this.accountDao.querySysAccountCount(map);
	}

	@Override
	public void updateAccountStatus(Map<String, Object> map) {
		this.accountDao.updateAccountStatus(map);
	}

	@Override
	public Map<String, Object> addAccount(Map<String, Object> map) {
		map.put("createdAt", DateUtil.doFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		map.put("updatedAt", DateUtil.doFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		map.put("isEnabled", 0);
		map.put("isLocked", 0);
		
		//默认密码暂时设置为12345678 MD5
		map.put("pwd", MD5.md5((String) map.get("password")));
		//明文密码，邮件形式发送给用户
		map.put("expressPassword", (String) map.get("password"));
		
		this.accountDao.addAccount(map);
		map.put("code", "succ");
		
		return map;
	}

	@Override
	public Integer updateAccountPassword(Map<String, Object> map) {
		
		return this.accountDao.updateAccountPassword(map);
	}

	@Override
	public List<TreeNodeVO> getTreeNode(Map<String, Object> param) {
		return this.menuDao.getTreeNode(param);
	}

	/* (non Javadoc) 新增角色权限
	 * @Title: addRoleAndAuthorization
	 * @Description: TODO
	 * @param map 
	 * @see com.smm.payCenter.bo.sysManager.SysManagerBo#addRoleAndAuthorization(java.util.Map) 
	 */
	@Override
	public void addRoleAndAuthorization(Map<String, Object> map) {
		
		Role role = new Role();
		role.setRoleName((String) map.get("roleName"));
		role.setDescription((String) map.get("roleString"));
		role.setCreateTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		role.setRoleStatus(1);//1：正常 2：关闭
		this.roloDao.addRole(role);
		map.put("roleId", role.getId());
		
		this.AndAuthorization(map);
		
//		String temp = (String) map.get("menuIds");
//		String[] menuIds = temp.split(",");
//		
//		for (int i = 0; i < menuIds.length; i++) {
//			Map<String,Object> param = new HashMap<String, Object>();
//			int menuId = Integer.parseInt(menuIds[i]);
//			param.put("roleId", role.getId());
//			param.put("menuId", menuId);
//			this.menuDao.addRoleRelation(param);
//		}
	}

	@Override
	public int queryRoleCount(Map<String, Object> map) {
		return this.roloDao.queryRoleCount(map);
	}

	@Override
	public List<Role> queryRoleList(Map<String, Object> map) {
		return this.roloDao.queryRoleList(map);
	}

	@Override
	public int deleteAuthorization(Map<String, Object> map) {
		return this.roloDao.deleteAuthorization(map);
	}

	/** 增加权限
	 * @Title: AndAuthorization 
	 * @Description: TODO
	 * @return: void
	 */
	@Override
	public void AndAuthorization(Map<String, Object> map){
		String temp = (String) map.get("menuIds");
		if (!temp.trim().equals("")) {
			String[] menuIds = temp.split(",");
			for (int i = 0; i < menuIds.length; i++) {
				Map<String,Object> param = new HashMap<String, Object>();
				int menuId = Integer.parseInt(menuIds[i]);
				param.put("roleId", map.get("roleId"));
				param.put("menuId", menuId);
				this.menuDao.addRoleRelation(param);
			}
		}else {
			System.out.println("没有为本角色分配权限");
		}
	}

	@Override
	public int queryMoneyExceptionCount(Map<String, Object> map) {
		return this.moneyExceptionDao.queryMoneyExceptionCount(map);
	}

	@Override
	public List<MoneyException> queryMoneyExceptionList(Map<String, Object> map) {
		return this.moneyExceptionDao.queryMoneyExceptionList(map);
	}

	@Override
	public int queryPaymentLogCount(Map<String, Object> map) {
		return this.payMentLogDao.queryPaymentLogCount(map);
	}

	@Override
	public List<PaymentLog> queryPaymentLogData(Map<String, Object> map) {
		return this.payMentLogDao.queryPaymentLogData(map);
	}

	@Override
	public Role queryRoleById(Map<String, Object> map) {
		return this.roloDao.queryRoleById(map);
	}

	@Override
	public String checkLoginName(String loginName) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("loginName", loginName);
		List<Account> list = this.accountDao.queryFnlAccount(map);
		if (list.size() > 0) {
			return "ERROR";
		}else {
			return "";
		}
	}

	@Override
	public String updateMoneyException(Map<String, Object> map) {
		String flag = "";
		map.put("auditTime", DateUtil.doFormatDate(new Date(), ""));
		Integer temp = this.moneyExceptionDao.updateMoneyException(map);
		if (temp > 0) {
			flag = "OK";
		}else {
			flag = "ERROR";
		}
		return flag;
	}
}
