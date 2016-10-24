package com.smm.payCenter.bo.impl;

import java.util.List;

import javax.annotation.Resource;

import com.smm.payCenter.bo.FnlRoleRelationBo;
import com.smm.payCenter.dao.FnlRoleRelationDao;
import com.smm.payCenter.domain.Menu;

public class FnlRoleRelationBoImpl implements FnlRoleRelationBo {

    @Resource
    private FnlRoleRelationDao fnlRoleRelationDao;

    @Override
    public List<Menu> getFnlRoleRelationById(Integer id) {
        // TODO Auto-generated method stub
        return fnlRoleRelationDao.getFnlMenuByRoleId(id);
    }

}
