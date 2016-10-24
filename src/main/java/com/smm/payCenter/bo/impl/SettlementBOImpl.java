package com.smm.payCenter.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.QueryAccessBO;
import com.smm.payCenter.bo.SendMailBO;
import com.smm.payCenter.bo.SettlementBO;
import com.smm.payCenter.controller.SettlementController;
import com.smm.payCenter.domain.ExceRecord;
import com.smm.payCenter.domain.UserAccount;
import com.smm.payCenter.util.DateUtil;
import com.smmpay.common.author.Authory;
import com.smmpay.common.request.RequestDataProxy;
import com.smmpay.inter.AuthorService;
import com.smmpay.inter.dto.res.ExceRecordDTO;
import com.smmpay.inter.dto.res.ReturnDTO;
import com.smmpay.inter.smmpay.CallEveryDaySettlementService;

import freemarker.log.Logger;

/**
 * 
 * @author hece
 *  每日结算
 */
@Service
public class SettlementBOImpl implements SettlementBO {

	private static Logger logger = Logger.getLogger(SettlementController.class.getName());
	
	@Resource
	private QueryAccessBO queryAccess;
	@Resource
	private AuthorService authorService;
	@Resource
	private CallEveryDaySettlementService callererydayService;
	
	@Resource
	private SendMailBO sendMailBO;
	
	@Value("#{tradeTime['mail']}")
	private String mailAddress;
	
	@Override
	public Map<String,Object> settlement(String date) {
		final Map<String,Object> map = new HashMap<String, Object>();
		//date = "20160413";
		//String date2 = "2016-04-13";
		Map<String,Object> insertmap = new HashMap<String, Object>();
		DateUtil sdate = new DateUtil();
		BigDecimal countUseMoney = new BigDecimal("0");
		BigDecimal countFreezeMoney = new BigDecimal("0");
		BigDecimal countTotalMoney = new BigDecimal("0");
		BigDecimal countzhifu = new BigDecimal("0");
		BigDecimal countshoukuan = new BigDecimal("0");
		BigDecimal countrujin = new BigDecimal("0");
		BigDecimal countchujin = new BigDecimal("0");
		
		Map<String,Object> countmap = new HashMap<String, Object>();
		
		Map<String,Object> amap = new HashMap<String, Object>();
		//amap.put("startTime",DateUtil.startOfTodDay(date2) );
		//amap.put("endTime",DateUtil.endOfTodDay(date2) );
		amap.put("startTime",DateUtil.startOfTodDay(sdate.currentDate()) );
		amap.put("endTime",DateUtil.endOfTodDay(sdate.currentDate()) );
		try {
			//查询所有会员信息
			List<UserAccount> useraccount = queryAccess.queryUserAccount(amap);
			
			String jsonStr = "{\"data\":[{\"startDate\":\""+date+"\"}]}";
			logger.info("每日结算入参============"+jsonStr);
			if(Authory.token == null) RequestDataProxy.getAccessToken(authorService);
			Map<String, String> mapParam = RequestDataProxy.getRequestParam(jsonStr,date);
			Map<String,Object> parammap = new HashMap<String, Object>();
			parammap.putAll(mapParam);
			//每日结算接口
			ReturnDTO settle = callererydayService.settlement(parammap);
			logger.info("每日结算返回============"+settle.getStatus()+"============="+settle.getData());
			if(settle.getStatus().equals("000000")){
				logger.info("每日结算成功>>>>>>>>>>>>>>"+settle.getMsg());
				@SuppressWarnings("unchecked")
				Map<String,Object> resultmap = (Map<String, Object>) settle.getData();
				@SuppressWarnings("unchecked")
				List<ExceRecordDTO> resultlist = (List<ExceRecordDTO>) resultmap.get("exceList");
				//异常记录
				List<ExceRecord> excelist = new ArrayList<ExceRecord>();
				int rowNum = 1;
				for(int i = 0;i < resultlist.size();i ++){
					ExceRecord exce = new ExceRecord();
					ExceRecordDTO obj = resultlist.get(i);
					exce.setRownum(rowNum++);
					exce.setUseraccountname(obj.getUserAccountName());
					exce.setUseraccountno(obj.getUserAccountNo());
					exce.setUserpaychannelid(obj.getUserPayChannelId());
					exce.setUsermoney(obj.getUserMoney());
					exce.setUsersjamt(obj.getUserSJAMT());
					excelist.add(exce);
				}
				
				
//					for(int j=0;j<useraccount.size();j++){
//						UserAccount uc = useraccount.get(j);
//						boolean bool = true;
//						if(uc != null){
//							if(resultlist != null && resultlist.size() > 0){
//								for(int i = 0;i < resultlist.size();i ++){
//									ExceRecordDTO obj = resultlist.get(i);
//									Integer userid = obj.getUserId();
//									
//									if(userid == uc.getUserId()){
//										bool = false;
//										break;
//									}
//								}
//								if(bool){
//									if(uc.getUserName() != null && uc.getUseMoney() != null && uc.getFreezeMoney() != null){
//										insertmap.put("username", uc.getUserId());
//										insertmap.put("usemoney", uc.getUseMoney());
//										insertmap.put("freezemoney", uc.getFreezeMoney());
//										insertmap.put("totalmoney", uc.getFreezeMoney().add(uc.getUseMoney()));
//										insertmap.put("paymoney", uc.getZhifu() != null ? uc.getZhifu() : new BigDecimal("0.000"));
//										insertmap.put("recvmoney", uc.getShoukuan() != null ? uc.getShoukuan() : new BigDecimal("0.000"));
//										insertmap.put("incomemoney", uc.getRujin() != null ? uc.getRujin() : new BigDecimal("0.000"));
//										insertmap.put("outcomemoney", uc.getChujin() != null ? uc.getChujin() : new BigDecimal("0.000"));
//										insertmap.put("createtime", sdate.currentDate());
//										
//										countUseMoney = countUseMoney.add(uc.getUseMoney());
//										countFreezeMoney = countFreezeMoney.add(uc.getFreezeMoney());
//										countTotalMoney = countTotalMoney.add(uc.getFreezeMoney().add(uc.getUseMoney()));
//										countzhifu = countzhifu.add(uc.getZhifu() != null ? uc.getZhifu() : new BigDecimal("0.000"));
//										countshoukuan = countshoukuan.add(uc.getShoukuan() != null ? uc.getShoukuan() : new BigDecimal("0.000"));
//										countrujin = countrujin.add(uc.getRujin() != null ? uc.getRujin() : new BigDecimal("0.000"));
//										countchujin = countchujin.add(uc.getChujin() != null ? uc.getChujin() : new BigDecimal("0.000"));
//										
//										//添加企业
//										queryAccess.insertDayBalance(insertmap);
//									}
//								}
//							}
//						}
//					}
				if(useraccount != null && useraccount.size() > 0){
					//没有异常记录,添加所有用户信息
					//if(resultlist == null || resultlist.size() <= 0){
						if(useraccount != null && useraccount.size() > 0){
							for(int k = 0;k<useraccount.size();k++){
								UserAccount uac = useraccount.get(k);
								if(uac != null){
									//if(uac.getUserName() != null && uac.getUseMoney() != null && uac.getFreezeMoney() != null){
										insertmap.put("username", uac.getUserId());
										insertmap.put("usemoney", uac.getUseMoney());
										insertmap.put("freezemoney", uac.getFreezeMoney());
										insertmap.put("totalmoney", uac.getFreezeMoney().add(uac.getUseMoney()));
										insertmap.put("paymoney", uac.getZhifu() != null ? uac.getZhifu() : new BigDecimal("0.000"));
										insertmap.put("recvmoney", uac.getShoukuan() != null ? uac.getShoukuan() : new BigDecimal("0.000"));
										insertmap.put("incomemoney", uac.getRujin() != null ? uac.getRujin() : new BigDecimal("0.000"));
										insertmap.put("outcomemoney", uac.getChujin() != null ? uac.getChujin() : new BigDecimal("0.000"));
										insertmap.put("createtime", sdate.currentDate());
										
										countUseMoney = countUseMoney.add(uac.getUseMoney());
										countFreezeMoney = countFreezeMoney.add(uac.getFreezeMoney());
										countTotalMoney = countTotalMoney.add(uac.getFreezeMoney().add(uac.getUseMoney()));
										countzhifu = countzhifu.add(uac.getZhifu() != null ? uac.getZhifu() : new BigDecimal("0.000"));
										countshoukuan = countshoukuan.add(uac.getShoukuan() != null ? uac.getShoukuan() : new BigDecimal("0.000"));
										countrujin = countrujin.add(uac.getRujin() != null ? uac.getRujin() : new BigDecimal("0.000"));
										countchujin = countchujin.add(uac.getChujin() != null ? uac.getChujin() : new BigDecimal("0.000"));
										//添加
										queryAccess.insertDayBalance(insertmap);
									//}
								}
							}
						}
					//}
				}
				
				countmap.put("username", 0);
				countmap.put("usemoney", countUseMoney);
				countmap.put("freezemoney", countFreezeMoney);
				countmap.put("totalmoney", countTotalMoney);
				countmap.put("paymoney", countzhifu);
				countmap.put("recvmoney", countshoukuan);
				countmap.put("incomemoney", countrujin);
				countmap.put("outcomemoney", countchujin);
				countmap.put("createtime", sdate.currentDate());
				//添加
				queryAccess.insertDayBalance(countmap);
				
				map.put("upcList",resultmap.get("upcList"));
				map.put("sysMoney",resultmap.get("sysMoney"));
				map.put("synCount",resultmap.get("synCount"));
				map.put("skipCount",resultmap.get("skipCount"));
				map.put("sysSJAMT",resultmap.get("sysSJAMT"));
				map.put("excelist",excelist);
				map.put("excenum",excelist.size());
				map.put("block","block");
			}else{
				logger.info("每日结算失败>>>>>>>>>>>>>>"+settle.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 异步自动结算结果邮件
		Thread th = new Thread(new Runnable() {
			
			public void run() {
				try {
					Thread.sleep(10 * 1000);
					sendMailBO.sendMailDaySettlement(map, mailAddress, "dailySettlement", "每日结算处理结果");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});
		th.start();
		return map;
	}
}