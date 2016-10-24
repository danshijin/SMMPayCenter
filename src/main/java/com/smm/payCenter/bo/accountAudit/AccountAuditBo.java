package com.smm.payCenter.bo.accountAudit;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.UserAccount;
import com.smm.payCenter.domain.UserAccountBanEntity;

public interface AccountAuditBo {

	List<UserAccount> queryData(Map<String, Object> map);

	int queryDataCount(Map<String, Object> map);

	void updateBankAuditStatus(Map<String, Object> map);

	List<UserAccount> queryUserAccountList(Map<String, Object> map);

	UserAccount queryUserAccountByUserId(Map<String, Object> map);

	//通过用户ID查询银行卡信息
	List<UserAccountBanEntity> queryUserBindBnak(Integer userid);
}
