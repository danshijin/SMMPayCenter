package com.smm.payCenter.bo.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.SystemDataQueryBo;
import com.smm.payCenter.dao.SystemDataQueryDao;

@Service
public class SystemDataQueryBoImpl implements SystemDataQueryBo {

    @Resource
    private SystemDataQueryDao systemDataQueryDao;

    @Override
    public List<com.smm.payCenter.domain.SystemData> querySystemDataList(Map<String, String> paymap) {
        // TODO Auto-generated method stub
        return systemDataQueryDao.querySystemDataList(paymap);
    }

    @Override
    public Integer querySystemDataListTotalRecords(Map<String, String> paymap) {
        // TODO Auto-generated method stub
        return systemDataQueryDao.querySystemDataListTotalRecords(paymap);
    }

}
