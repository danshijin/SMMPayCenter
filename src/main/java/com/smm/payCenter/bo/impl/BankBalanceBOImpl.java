package com.smm.payCenter.bo.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.BankBalanceBO;
import com.smm.payCenter.dao.BankBalanceDAO;
import com.smm.payCenter.domain.AccountChannelEntity;
import com.smmpay.common.author.Authory;
import com.smmpay.common.request.RequestDataProxy;
import com.smmpay.inter.AuthorService;
import com.smmpay.inter.dto.res.ResQueryAccountBalanceDTO;
import com.smmpay.inter.dto.res.ReturnDTO;
import com.smmpay.inter.smmpay.QueryAccountBalanceService;

@Service
public class BankBalanceBOImpl implements BankBalanceBO {

	Logger logger=Logger.getLogger(BankBalanceBOImpl.class);


	@Autowired
	private AuthorService authorService;

	@Autowired
	private QueryAccountBalanceService queryAccountBalanceService;// 调用中信银行接口

	@Resource
	private BankBalanceDAO bankBalanceDAO;// 查询数据库中用户数据

	@Override
	public List<AccountChannelEntity> getCompanyAccountNO(Map<String, Object> param) {

		logger.info("----------------in BankBalanceBOImpl.getCompanyAccountNO");


		logger.info("----------------param="+param.toString());


		// 1.先获得筛选后企业帐号信息
		List<AccountChannelEntity> listData = bankBalanceDAO.getCompanyAccountNO(param);

		logger.info("----------------listData="+listData.size());

		for (AccountChannelEntity accountChannelEntity : listData) {


			Map<String, String> map;

			// map.put("userId", accountChannelEntity.getUser_id().toString());
			// map.put("userName", accountChannelEntity.getUser_name());
			// map.put("subAccNm", accountChannelEntity.getUser_account_no());
			String jsonStr = "{\"data\":[{\"subAccNo\":\"" + accountChannelEntity.getUser_account_no() + "\"}]}";

			logger.info("----------------Authory.token="+Authory.token);

			if (Authory.token == null) {
				RequestDataProxy.getAccessToken(authorService);
			}


			map = RequestDataProxy.getRequestParam(jsonStr, accountChannelEntity.getUser_account_no());

			logger.info("----------------调用查询账户余额接口：参数="+map.toString());

			ReturnDTO returnDTO = queryAccountBalanceService.queryAccountBalance(map);

			logger.info("----------------调用查询账户余额接口：返回="+returnDTO.toString());

			if (returnDTO.getMsg().equals("000000")) {// 接口调用成功
				try {
					ResQueryAccountBalanceDTO dto = (ResQueryAccountBalanceDTO) returnDTO.getData();
					accountChannelEntity.setUser_account_no(dto.getSubAccNo());// 更新实际银行帐号
					accountChannelEntity.setCompany_name(dto.getSUBACCNM());// 更新实际银行存储名称
					accountChannelEntity.setTotalMoney(dto.getSJAMT().toString());// 总额
					accountChannelEntity.setUseMoney(dto.getKYAMT().toString());// 可用
					accountChannelEntity.setFreezeMoney(dto.getDJAMT().toString());// 冻结
				} catch (Exception e) {
					// 接口调用失败的情况
					accountChannelEntity.setTotalMoney("接口调用失败");// 总额
					accountChannelEntity.setUseMoney("接口调用失败");// 可用
					accountChannelEntity.setFreezeMoney("接口调用失败");// 冻结
				}
			} else {
				// 接口调用失败的情况
				accountChannelEntity.setTotalMoney("接口调用失败");// 总额
				accountChannelEntity.setUseMoney("接口调用失败");// 可用
				accountChannelEntity.setFreezeMoney("接口调用失败");// 冻结
			}
		}

		// 2.获得接口数据 等待接口，返回值要进行修改
		return listData;
	}

	@Override
	public int getCompanyAccountNOCount(Map<String, Object> param) {
		Integer count = bankBalanceDAO.getCompanyAccountNOCount(param);

		return count;
	}

}
