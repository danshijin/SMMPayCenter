package com.smm.payCenter.bo.useraccount.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.useraccount.UserAccountBo;
import com.smm.payCenter.dao.UserAccountDao;
import com.smm.payCenter.domain.TrCashApply;
import com.smm.payCenter.domain.TrRecord;
import com.smm.payCenter.domain.UserAccount;
import com.smm.payCenter.domain.UserAccountBanEntity;
import com.smm.payCenter.domain.UserAccountEntity;
import com.smm.payCenter.domain.UserPayChannel;
import com.smm.payCenter.util.JSONUtil;
import com.smmpay.common.author.Authory;
import com.smmpay.common.request.RequestDataProxy;
import com.smmpay.inter.AuthorService;
import com.smmpay.inter.dto.res.ResOpenAccountDTO;
import com.smmpay.inter.dto.res.ResQueryAccountBalanceDTO;
import com.smmpay.inter.dto.res.ReturnDTO;
import com.smmpay.inter.smmpay.OpenAccountService;
import com.smmpay.inter.smmpay.PlatformDebitsService;
import com.smmpay.inter.smmpay.QueryAccountBalanceService;

@Service
public class UserAccountbo implements UserAccountBo {

	private Logger looger=Logger.getLogger(this.getClass());
	
	@Autowired
	private OpenAccountService openAccountService;
	//指定账户余额
	@Autowired
	private QueryAccountBalanceService queryAccBalanceService;
	//平台出金
	@Autowired
	private PlatformDebitsService plaDebitsService;

	@Resource
	private UserAccountDao userAccountDao;

	@Autowired
	private AuthorService authorService;

	@Override
	public List<UserAccountEntity> vipLsit(UserAccountEntity useraccount) {
		// TODO Auto-generated method stub
		//传附属银行卡号
		Map<String,String> map=new HashMap<String, String>();
		
		List<UserAccountEntity> rlist=userAccountDao.vipLsit(useraccount);
		for(UserAccountEntity u : rlist){
			if(StringUtils.isNoneBlank(u.getBankNO())){
				map.put("subAccNo", u.getBankNO());
				//调用接口 查询当前附属账户的实际余额
				ReturnDTO returnDTO =queryAccBalanceService.queryAccountBalance(map);
				List<ResQueryAccountBalanceDTO> returnList=(ArrayList<ResQueryAccountBalanceDTO>)returnDTO.getData();
				ResQueryAccountBalanceDTO r=returnList.get(0);
				u.setBankFactMoney(r.getSJAMT());//取返回值的银行卡余额 设置到当前对象中
			}
		}
		return rlist;
	}

	@Override
	public UserAccountEntity vipDetaials(Integer userId) {
		// TODO Auto-generated method stub
		return userAccountDao.vipDetaials(userId);
	}

	@Override
	public List<UserAccountBanEntity> useraccountBank(Integer userId) {
		// TODO Auto-generated method stub
		return userAccountDao.useraccountBank(userId);
	}

	@Override
	public void useraccoutshenhe(UserAccountEntity useraccout) {
		// TODO Auto-generated method stub
		userAccountDao.useraccoutshenhe(useraccout);
	}
	@Override
	public Integer getSize(UserAccountEntity useraccount) {
		
		return this.userAccountDao.getSize(useraccount);
	}
	@Override
	public List<UserAccountEntity> getList(UserAccountEntity useraccount) {
		// TODO Auto-generated method stub
		return this.userAccountDao.getList(useraccount);
	}
	@Override
	public Integer updateUserAccount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.userAccountDao.updateUserAccount(map);
	}
	@Override
	public Integer updateUserPayChannel(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.userAccountDao.updateUserPayChannel(map);
	}
	@Override
	public Integer insertTrRecord(TrRecord t) {
		// TODO Auto-generated method stub
		return this.userAccountDao.insertTrRecord(t);
	}
	@Override
	public UserAccountEntity getById(Integer userId) {
		// TODO Auto-generated method stub
		return this.userAccountDao.getById(userId);
	}
	@Override
	public List<UserAccountEntity> getDebitsList(UserAccountEntity useraccount) {
		// TODO Auto-generated method stub
		return this.userAccountDao.getDebitsList(useraccount);
	}
	@Override
	public List<UserAccountBanEntity> getUserBindBank(Integer userId) {
		// TODO Auto-generated method stub
		return this.userAccountDao.getUserBindBank(userId);
	}

	@Override
	public void addUserPayChannel(UserPayChannel userchannel) {
		userAccountDao.addUserPayChannel(userchannel);

	}

	@Override
	public ResOpenAccountDTO smmpayUser(UserAccountEntity UserAccountEntity) {
		
		String jsonStr = "{\"data\":[{\"subAccNm\":\""+UserAccountEntity.getCompanyName()+"\"}]}";
		if(Authory.token == null) RequestDataProxy.getAccessToken(authorService);
		Map<String, String> mapParam = RequestDataProxy.getRequestParam(jsonStr,UserAccountEntity.getCompanyName());
		ReturnDTO returnDTO = openAccountService.openAccount(mapParam);
		ResOpenAccountDTO data=(ResOpenAccountDTO) returnDTO.getData();
		return data;
	}
	
	/**
	 * 平台出金  调用平台出金接口
	 */
	@Override
	public Map<String, String> addDebitsDetil(Map<String,String> map,int verify) {
		try {
			String jsonStr = "{\"data\":["+JSONUtil.doConvertObject2Json(map)+"]}";
			System.out.println(jsonStr);
			if(Authory.token == null) RequestDataProxy.getAccessToken(authorService);
			Iterator iter = map.entrySet().iterator();
			String val="";
			while(iter.hasNext()){
				Map.Entry<String,String> entry=(Map.Entry<String,String>)iter.next();
				val =val+ entry.getValue();
			}
			Map<String, String> mapParam = RequestDataProxy.getRequestParam(jsonStr,val);
			ReturnDTO returnDTO = null;
			if (verify == 1) { //为1时是用户出金审核
				returnDTO =plaDebitsService.platformDebits(mapParam);
			}else{ // 为0时是平台出金
				returnDTO =plaDebitsService.platDebits(mapParam);
			}
			looger.info("出金接口返回returnDTO:"+returnDTO!=null?"Status:"+returnDTO.getStatus()+"Msg:"+returnDTO.getMsg():"");

			if(returnDTO!=null &&returnDTO.getMsg().equals("000000")){
				map.put("code", "succ");	
				map.put("msg", "系统提示，操作成功");	
			}else{
				map.put("code", "error");
				map.put("msg", "接口返回错误，请稍候！");
			}
		} catch (Exception e) {
			map.put("code", "error");
			map.put("msg", "异常错误发生！");
		}
		return map;
	}

	@Override
	public UserAccountBanEntity getBindBankById(Integer bindId) {
		// TODO Auto-generated method stub
		return this.userAccountDao.getBindBankById(bindId);
	}

	@Override
	public Map<String, BigDecimal> getBankMoney(String subAccNo) {
		Map<String, BigDecimal> returnmap = new HashMap<String, BigDecimal>();
		
		String jsonStr = "{\"data\":[{\"subAccNo\":\""+subAccNo+"\"}]}";
		if(Authory.token == null) RequestDataProxy.getAccessToken(authorService);
		Map<String, String> mapParam = RequestDataProxy.getRequestParam(jsonStr,subAccNo);
		ReturnDTO returnDTO = queryAccBalanceService.queryAccountBalance(mapParam);
		ResQueryAccountBalanceDTO resData=(ResQueryAccountBalanceDTO) returnDTO.getData();
		if(resData !=null){
			returnmap.put("SJAMT", resData.getSJAMT()); 
			returnmap.put("KYAMT", resData.getKYAMT());
		}
		return returnmap;
	}

	@Override
	public UserAccount queryUserAccountByParam(Map<String, Object> paramMap) {
		return this.userAccountDao.queryUserAccountByParam(paramMap);
	}

	@Override
	public void updateUserStatus(Map<String,Object> map) {
		userAccountDao.updateUserStatus(map);
	}

	@Override
	public UserAccountEntity getUserAccountById(Integer userId) {
		// TODO Auto-generated method stub
		return this.userAccountDao.getUserAccountById(userId);
	}

	@Override
	public Integer insertTrCashApply(TrCashApply t) {
		// TODO Auto-generated method stub
		return this.userAccountDao.insertTrCashApply(t);
	}

	/**
	 * 根据用户ID查询支付渠道信息
	 */
	@Override
	public UserPayChannel queryUserPayChannelByUserId(String userid) {
		return userAccountDao.queryUserPayChannelByUserId(userid);
	}

	/**
	 * 修改用户支付渠道状态为关闭
	 */
	@Override
	public void updateUserPayChannelStatus(Integer channelid) {
		userAccountDao.updateUserPayChannelStatus(channelid);
		
	}

	/**
	 * 查询用户银行卡信息
	 */
	@Override
	public List<UserAccountBanEntity> queryUserBank(String userid) {
		List<UserAccountBanEntity> userbank = userAccountDao.queryUserBank(userid);
		return userbank;
	}

	/**
	 * 修改银行卡状态为关闭
	 */
	@Override
	public void updateUserBankStatus(Integer bankid) {
		userAccountDao.updateUserBankStatus(bankid);
	}

	@Override
	public Integer saveUserAccount(UserAccountEntity useraccount) {
		// TODO Auto-generated method stub
		return userAccountDao.saveUserAccount(useraccount);
	}
}
