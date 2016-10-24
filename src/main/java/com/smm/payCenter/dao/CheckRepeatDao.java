package com.smm.payCenter.dao;

import java.util.Map;

import com.smm.payCenter.domain.TrFreezeRecordEntity;
import com.smm.payCenter.domain.TransactionDetail;

/**
 * 验证是否重复操作
 * @author tantaigen
 *
 */

public interface CheckRepeatDao {
	/**
	 * 会员审核检查
	 * @param map
	 * @return
	 */
	public Integer  checkUserAccount(Map<String, String> map);
	
	/**
	 * 出金审核检查
	 * @param map
	 * @return
	 */
	public Integer  checkCashApply(Map<String, String> map);
	
	//查询是否存在有效的解冻记录
	public Integer checkUnfreezeRecore(Integer paymentId);
	
	//查询是否存在有效的冻结记录
	public Integer checkFreezeRecord(Integer paymentId);
	
	/**
	 * 检查是手动退款是否重复
	 * @param map
	 * @return
	 */
	public Integer  checkTransferRecord(Map<String, String> map);
	
	/**
	 * 查询冻结
	 * @param paymentId
	 * @return
	 */
	public TrFreezeRecordEntity queryFreezeRecord(Integer paymentId);

	/**
	 *根据Id查询详情
	 * @param id
	 * @return
	 */
	public TransactionDetail queryDetailById(Integer id);
	
	/**
	 * 查询出金指令
	 * @param userId
	 * @return
	 */
	public  Integer queryCashDetailCount(Integer userId);

}
