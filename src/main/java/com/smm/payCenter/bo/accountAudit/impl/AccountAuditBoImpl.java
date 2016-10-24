package com.smm.payCenter.bo.accountAudit.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.accountAudit.AccountAuditBo;
import com.smm.payCenter.dao.Account.AccountDao;
import com.smm.payCenter.domain.UserAccount;
import com.smm.payCenter.domain.UserAccountBanEntity;
import com.smm.payCenter.util.DateUtil;

@Service
public class AccountAuditBoImpl implements AccountAuditBo {

	@Resource
	private AccountDao accountDao;

	@Override
	public int queryDataCount(Map<String, Object> map) {
		return this.accountDao.queryAccountDataCount(map);
	}

	@Override
	public List<UserAccount> queryData(Map<String, Object> map) {
		return this.accountDao.queryAccountData(map);
	}

	@Override
	public void updateBankAuditStatus(Map<String, Object> map) {
		int flag = (int) map.get("flag");
		String bindTime = DateUtil.doFormatDate(new Date(),
				"yyyy-MM-dd HH:mm:ss");
		if (flag == 0) {
			// 划款
			map.put("is_payment", 1);
			map.put("auditStatus", 0);
			map.put("bindTime", null);
		} else if (flag == 1) {
			// 银行卡绑定时间是当前时间
			map.put("bindTime", bindTime);
			map.put("auditStatus", 1);
		} else if (flag == 2) {
			// 撤销
			map.put("drawMoney", this.random());
			map.put("is_payment", 0);
			map.put("bindTime", null);
			map.put("auditStatus", 0);
		} else if (flag == 3) {
			//关闭
			map.put("bindTime", null);
			map.put("auditStatus", 2);
		}

		this.accountDao.updateBankAuditStatus(map);
	}

	
	/** 随机生成0-1之间的两位小数
	 * @Title: random 
	 * @Description: TODO
	 * @return
	 * @return: double
	 */
	private double random() {
		Random rand = new Random();
		double num = rand.nextDouble();
		BigDecimal b = new BigDecimal(num);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	@Override
	public List<UserAccount> queryUserAccountList(Map<String, Object> map) {
		return this.accountDao.queryUserAccountList(map);
	}

	@Override
	public UserAccount queryUserAccountByUserId(Map<String, Object> map) {
		return this.accountDao.queryUserAccountByUserId(map);
	}

	/**
	 * 通过用户ID查询银行卡信息
	 */
	@Override
	public List<UserAccountBanEntity> queryUserBindBnak(Integer userid) {
		return this.accountDao.queryUserBindBnak(userid);
	}
}
