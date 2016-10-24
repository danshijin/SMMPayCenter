package com.smm.payCenter.bo.base.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.base.PrivilegeBo;
import com.smm.payCenter.dao.Account.AccountDao;
import com.smm.payCenter.domain.RoleRelation;

@Service
public class PrivilegeBoImpl implements PrivilegeBo{

	@Resource
	private AccountDao accountDao;
	
	@Override
	public List<RoleRelation> menuData(Map<String, Object> param) {
		List<RoleRelation> list= this.accountDao.menuData(param);
		return list;
	}

}
