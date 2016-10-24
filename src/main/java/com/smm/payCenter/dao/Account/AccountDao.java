package com.smm.payCenter.dao.Account;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.RoleRelation;
import com.smm.payCenter.domain.UserAccount;
import com.smm.payCenter.domain.UserAccountBanEntity;

public interface AccountDao {

    List<RoleRelation> menuData(Map<String, Object> param);

    List<UserAccount> queryAccountData(Map<String, Object> map);

    int queryAccountDataCount(Map<String, Object> map);

    void updateBankAuditStatus(Map<String, Object> map);

    int querySysAccountCount(Map<String, Object> map);
    /**
     * 匹配登录
     */
    List<Account> userLogin(Map<String, String> paramMap);


    void updateLastLoginTime(Map<String, Object> temp);

    List<Account> querySysAccountList(Map<String, Object> map);

    void updateAccountStatus(Map<String, Object> map);

    void addAccount(Map<String, Object> map);

    List<UserAccount> queryUserAccountList(Map<String, Object> map);

	Integer updateAccountPassword(Map<String, Object> map);

	UserAccount queryUserAccountByUserId(Map<String, Object> map);

	List<Account> queryFnlAccount(Map<String, Object> map);

	//通过用户ID查询银行卡信息
	List<UserAccountBanEntity> queryUserBindBnak(Integer userid);
}
