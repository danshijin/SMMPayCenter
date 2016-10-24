package com.smm.payCenter.bo.sysManager;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.MoneyException;
import com.smm.payCenter.domain.PaymentLog;
import com.smm.payCenter.domain.Role;
import com.smm.payCenter.domain.TreeNodeVO;

public interface SysManagerBo {

	List<Account> querySysAccount(Map<String, Object> map);

	int querySysAccountCount(Map<String, Object> map);

	void updateAccountStatus(Map<String, Object> map);

	Map<String, Object> addAccount(Map<String, Object> map);

	Integer updateAccountPassword(Map<String, Object> map);

	List<TreeNodeVO> getTreeNode(Map<String, Object> param);

	void addRoleAndAuthorization(Map<String, Object> map);

	int queryRoleCount(Map<String, Object> map);

	List<Role> queryRoleList(Map<String, Object> map);

	/** 修改角色的时候，首先删除原有权限
	 * @Title: deleteAuthorization 
	 * @Description: TODO
	 * @param map
	 * @return: void
	 */
	int deleteAuthorization(Map<String, Object> map);

	void AndAuthorization(Map<String, Object> map);

	/** 交易记录异常信息条数
	 * @Title: queryMoneyExceptionCount 
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: int
	 */
	int queryMoneyExceptionCount(Map<String, Object> map);

	/** 交易记录异常信息
	 * @Title: queryMoneyExceptionList 
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: List<MoneyException>
	 */
	List<MoneyException> queryMoneyExceptionList(Map<String, Object> map);

	/** 交易记录个数
	 * @Title: queryPaymentLogCount 
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: int
	 */
	int queryPaymentLogCount(Map<String, Object> map);

	/** 交易记录
	 * @Title: queryPaymentLogData 
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: List<PaymentLog>
	 */
	List<PaymentLog> queryPaymentLogData(Map<String, Object> map);

	Role queryRoleById(Map<String, Object> map);

	/** 检验邮箱是否被注册
	 * @Title: checkLoginName 
	 * @Description: TODO
	 * @param loginName
	 * @return
	 * @return: String
	 */
	String checkLoginName(String loginName);

	/** 处理异常交易信息
	 * @Title: updateMoneyException 
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: String
	 */
	String updateMoneyException(Map<String, Object> map);

}
