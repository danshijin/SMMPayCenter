package com.smm.payCenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smm.payCenter.domain.Menu;

public interface FnlRoleRelationDao {

    public List<Menu> getFnlMenuByRoleId(@Param(value = "roleId") Integer roleId);
    
    /** 获取所有进行权限控制的url
     * @return
     */
    public List<String> getAllMenuUrl();

}
