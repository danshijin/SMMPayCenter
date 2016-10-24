package com.smm.payCenter.dao.trrecord;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.TrCashApply;
import com.smm.payCenter.domain.TrRecordCompanyInfo;



public interface TrRecordDAO {

	/**
	 * 用户出金列表
	 * @param param
	 * @return
	 */
	public List<TrCashApply> getList(Map<String, Object> map);
	
	/**
	 * 用户出金总记录数
	 * @param map
	 * @return
	 */
	public Integer getSize(Map<String, Object> map);
	/**
	 * 用户出金审核
	 * @param param
	 * @return
	 */
	public Integer update(Map<String, Object> map);
	
	/**
	 * 用户出金审核
	 * @param param
	 * @return
	 */
	public TrCashApply getObject(Integer id);
	
	
	public Integer updateUPC(Map<String, Object> map);
	
	public Integer updateUA(Map<String, Object> map);
	
	/**
	 * 查询公司对应金额信息
	 */
	public TrRecordCompanyInfo queryTrRecordInfoByUserId(Map<String, Object> map);//查询今日 转入总额 转出总额 出金总额 入金总额
	public Double queryTotalMoneyByUserId(Map<String, Object> map);//查询昨日总额
	public Double querySmmFreezeMoneyByUserId(Map<String, Object> map);//查询待出金金额
}
