package com.smm.payCenter.bo;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.TrCashApply;
import com.smm.payCenter.domain.TrRecordCompanyInfo;



public interface TrRecordBO {
	
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
	public Map<String, Object> update(Map<String, Object> map);
	
	/**
	 * 得到审查信息
	 * @param param
	 * @return
	 */
	public TrCashApply getObject(Integer id);
	
	public Map<String, Object> updateUPC(Map<String, Object> map);
	
	public Map<String, Object> updateUA(Map<String, Object> map);
	
	/**
	 * 查询公司对应金额信息
	 */
	public TrRecordCompanyInfo queryTrRecordInfoByUserId(Map<String, Object> map);
	
	
}
