package com.smm.payCenter.dao;

import java.util.List;
import java.util.Map;
import com.smm.payCenter.domain.AccountChannelEntity;

public interface BankBalanceDAO {
	//根据前台选择信息获得企业编号
	public List<AccountChannelEntity> getCompanyAccountNO(Map<String, Object> param);
	
	//获得数据总量
	public int getCompanyAccountNOCount(Map<String, Object> param);
}
