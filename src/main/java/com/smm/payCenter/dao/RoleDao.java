package com.smm.payCenter.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.smm.payCenter.domain.Role;

public interface RoleDao {

    public Role getRoleById(@Param(value = "roleId") Integer roleId);
    
    int addRole(Role role);

	public Integer queryRoleCount(Map<String, Object> map);
	

	public List<Role> queryRoleList(Map<String, Object> map);

	public int deleteAuthorization(Map<String, Object> map);

	public Role queryRoleById(Map<String, Object> map);
}
