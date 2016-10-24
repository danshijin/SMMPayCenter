package com.smm.payCenter.bo.useraccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.TrCashApply;
import com.smm.payCenter.domain.TrRecord;
import com.smm.payCenter.domain.UserAccount;
import com.smm.payCenter.domain.UserAccountBanEntity;
import com.smm.payCenter.domain.UserAccountEntity;
import com.smm.payCenter.domain.UserPayChannel;
import com.smmpay.inter.dto.res.ResOpenAccountDTO;

/**
 * 会员审核
 * @author tantaigen
 *
 */
public interface UserAccountBo {
	/**
	 * 会员列表
	 * @return
	 */
	public  List<UserAccountEntity> vipLsit(UserAccountEntity useraccount);
	/**
	 * 会员详情
	 * @param userId
	 * @return
	 */
	public UserAccountEntity  vipDetaials(Integer userId);
	
	/**
	 * 会员银行信息
	 * @param userId
	 * @return
	 */
	public  List<UserAccountBanEntity> useraccountBank(Integer userId);
	/**
	 * 会员审核
	 * @param useraccout( 状态   会员ID) 
	 */
	public void  useraccoutshenhe(UserAccountEntity useraccout);

	
	/**
	 * 会员列表总记录数
	 * @param useraccount
	 * @return
	 */
	public Integer getSize(UserAccountEntity useraccount);
	/**
	 * 会员列表
	 * @param useraccount
	 * @return
	 */
	public  List<UserAccountEntity> getList(UserAccountEntity useraccount);
	
	/**
	 * 修改user_account 会员账号表的 可用余额
	 * @param map
	 * @return
	 */
	public Integer updateUserAccount(Map<String, Object> map);
	/**
	 * 修改会员支付通道表 user_pay_channel 的 可用金额
	 * @param map
	 * @return
	 */
	public Integer updateUserPayChannel(Map<String, Object> map);
	/**
	 * 插入tr_record	交易记录表 新增一条记录
	 * @param map
	 * @return
	 */
	public Integer insertTrRecord(TrRecord t);
	
	/**
	 * 根据id获取记录
	 * @param userId
	 * @return
	 */
	public UserAccountEntity getById(Integer userId);
	
	/**
	 * 平台出金列表
	 * @param useraccount
	 * @return
	 */
	public  List<UserAccountEntity> getDebitsList(UserAccountEntity useraccount);
	
	/**
	 * 获取会员绑定的银行卡列表
	 * @param userId
	 * @return
	 */
	public List<UserAccountBanEntity> getUserBindBank(Integer userId);
	/**
	 * 根据userid获取账号信息
	 * @param userId
	 * @return
	 */
	public UserAccountEntity getUserAccountById(Integer userId);
	
	/**
	 * 会员支付账户写入
	 * 
	 * @param userchannel
	 */
	public  void addUserPayChannel(UserPayChannel userchannel);
	
	
	/**
	 * 会员审核接口调用
	 * @param channel
	 * @return
	 */
	public ResOpenAccountDTO smmpayUser(UserAccountEntity accountEntity);
	
	/**
	 * 平台出金
	 * @return
	 */
	public Map<String, String> addDebitsDetil(Map<String,String> map,int verify);
	
	/**
	 * 根据主键得到用户选择的绑定账户的信息
	 * @param bindId
	 * @return
	 */
	public UserAccountBanEntity getBindBankById(Integer bindId);
	
	public Map<String, BigDecimal> getBankMoney(String bankAccountNo);
	/** 查询会员信息
	 * @Title: queryUserAccountByParam 
	 * @Description: TODO
	 * @param put
	 * @return
	 * @return: UserAccount
	 */
	public UserAccount queryUserAccountByParam(Map<String, Object> paramMap);
	
	//修改用户状态为关闭
	public void updateUserStatus(Map<String,Object> map);
	//根据用户ID查询支付渠道信息
	public UserPayChannel queryUserPayChannelByUserId(String userid);
	//修改用户支付渠道状态为关闭
	public void updateUserPayChannelStatus(Integer channelid);
	//查询用户银行卡信息
	public List<UserAccountBanEntity> queryUserBank(String userid);
	//修改银行卡状态为关闭
	public void updateUserBankStatus(Integer bankid);
	
	/**
	 * 插入 TrCashApply
	 * @param t
	 * @return
	 */
	public Integer insertTrCashApply(TrCashApply t);
	
	
	/**
	 * 修改会员信息
	 * @param useraccount
	 * @return
	 */
	public Integer saveUserAccount(UserAccountEntity useraccount);

}
