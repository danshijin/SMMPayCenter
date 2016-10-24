package com.smm.payCenter.bo;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.DayBalance;
import com.smm.payCenter.domain.QueryEntry;
import com.smm.payCenter.domain.UserAccount;

public interface QueryAccessBO {

	//查询平台资金
	public DayBalance queryPingtaimoney(String replaytime);
	//查询企业资金
	public List<DayBalance> queryQiyemoney(Map<String,Object> map);
	//查询总记录数
	public int queryTotalRecord(String username,String companyname,String replaytime);
	
	
	//冻结查询   平台资金
	public DayBalance queryDongJiePingTai(String replaytime);
	//冻结查询  企业资金
	public List<DayBalance> queryDongJieQiYe(Map<String,Object> map);
	
	//可用余额  平台资金
	public DayBalance queryYuEPingTai(String replaytime);
	//可用余额  企业资金
	public List<DayBalance> queryYuEQiYe(Map<String,Object> map);
	
	//每日结算添加
	public void insertDayBalance(Map<String,Object> map);
	//查询所有会员信息
	public List<UserAccount> queryUserAccount(Map<String,Object> map);
}
