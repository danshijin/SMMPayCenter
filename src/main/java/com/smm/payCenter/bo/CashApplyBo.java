package com.smm.payCenter.bo;

import java.util.List;

import com.smm.payCenter.domain.CashApply;

public interface CashApplyBo {
	/**
	 * 出金记录
	 * @return
	 */
	List<CashApply>  queryCashApply(CashApply cashApplyDao);
	
	public Integer queryCashApplycount( CashApply cashApply);
	
	/**
	 * 修改出金状态
	 * @param cashApply
	 * @return
	 */
	public Integer updateapplyStatus(CashApply cashApply);
	

	/**
	 * 根据Id查询出金记录
	 * @param cashApply
	 * @return
	 */
	public  CashApply  queryCashApplyId( Integer  id);
	
	
	/**
	 * 出金退回 更新  支付渠道表
	 * @return
	 */
	public Integer  updateUserPayChannel(CashApply cashApply);
	
	/**
	 * 出金退回 更新会员表金额
	 * @return
	 */
	public Integer  updateUserAccount(CashApply cashApply);
}
