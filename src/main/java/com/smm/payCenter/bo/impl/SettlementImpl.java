package com.smm.payCenter.bo.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smm.payCenter.bo.ISettlementBO;
import com.smm.payCenter.dao.IMarketStatusDao;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.TrMarketLog;

/**
* @author  zhaoyutao
* @version 2015年12月9日 下午5:17:01
* 类说明
*/
@Service
public class SettlementImpl implements ISettlementBO {
	
	@Resource
	IMarketStatusDao marketStatusDao;
	
	
	/**
	 * @param account  操作人账户信息，自动结算是应为0
	 * @return
	 */
	@Transactional
	public String closeMarket(Account account){
		
		TrMarketLog log = new TrMarketLog();
		
		log.setOperator(account.getId());
		
		log.setOperType(1); // 1表示闭市
		
		marketStatusDao.insertLog(log);
		
		marketStatusDao.closeMarket();
				
		return "闭市成功";
	}
	
	
}
