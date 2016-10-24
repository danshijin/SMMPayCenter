package com.smm.payCenter.controller.capitalData;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.accountAudit.AccountAuditBo;
import com.smm.payCenter.bo.tradeRecord.TradeRecordBo;
import com.smm.payCenter.bo.useraccount.UserAccountBo;
import com.smm.payCenter.domain.TradeRecord;
import com.smm.payCenter.domain.UserAccount;
import com.smm.payCenter.domain.UserPayChannel;
import com.smmpay.common.author.Authory;
import com.smmpay.common.request.RequestDataProxy;
import com.smmpay.inter.AuthorService;
import com.smmpay.inter.dto.res.ReturnDTO;
import com.smmpay.inter.smmpay.NoLoginPrintService;

import net.sf.json.JSONObject;


/** 电子回单
 * @ClassName: BillController 
 * @Description: TODO
 * @author: zhangnan
 * @date: 2015年11月12日 上午10:08:15  
 */
@Controller
@RequestMapping("/electric")
public class BillController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(BillController.class.getName());
	
	@Resource
	private TradeRecordBo tradeRecordBo;
	
	@Resource
	private AuthorService authorService;
	
	@Resource
	private UserAccountBo userAccountBo;
	
	@Resource
	private AccountAuditBo accountAuditBo;
	
	@Resource
	private NoLoginPrintService noLoginPrintService;//中信银行接口，查询交易记录
	
	/** 电子回单列表 跳转
	 * @Title: electricList 
	 * @Description: TODO
	 * @return
	 * @return: ModelAndView
	 */
	@RequestMapping("/electricList")
	public ModelAndView electricList(Integer userIds,String userName,String startTime,String endTime) {
		ModelAndView mav=new ModelAndView();
		try {
			mav.setViewName("/capitalData/electricFrom");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		return mav;
	}
	/** 调用中信银行前置服务器接口，查询交易记录
	 * @Title: electric 
	 * @Description: TODO
	 * @param userIds
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 * @return: ModelAndView
	 */
	@RequestMapping("/electric")
	public ModelAndView electric(Integer userIds,String userName,String startTime,String endTime,Integer num) {
		ModelAndView mav=new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		List<UserPayChannel> list;
		List<TradeRecord> tradeRecordList = new ArrayList<TradeRecord>();
		String startTimes = startTime;
		String endTimes = endTime;
		if(num==null){
			num=1;
		}
		if (startTime == null || startTime.equals("") ) {
			startTime = "19700101";
		}else {
			startTime = startTime.replace("-", "");
		}
		if (endTime == null || endTime.equals("") ) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			endTime = sdf.format(new Date());
		}else {
			endTime = endTime.replace("-", "");
		}
		if (userName == null) {
			userName = "";
		}
		try {
			if (userName.equals("")) {
				userName = null;
				map.put("userId", userIds);
				list = this.tradeRecordBo.getUserPayChannelByUserId(map);
			}else {
				map.put("userId", userIds);
				map.put("userName", userName);
				list = this.tradeRecordBo.getUserPayChannel(map);
			}
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					String jsonStr = "{\"data\":[{\"subAccNo\":\""+list.get(i).getUserAccountNo()+"\",\"startDate\":\""+startTime+"\",\"endDate\":\""+endTime+"\",\"page\":\""+num+"\"}]}";
					logger.info("确认转账入参============"+jsonStr);
					if(Authory.token == null) RequestDataProxy.getAccessToken(authorService);

					//签名
					Map<String, String> mapParam =  RequestDataProxy.getRequestParam(jsonStr,String.valueOf(list.get(i).getUserAccountNo())+""
							+String.valueOf(startTime)+""+String.valueOf(endTime)+String.valueOf(num));
					
					//查询交易接口
					ReturnDTO dto = this.noLoginPrintService.queryTrRecords(mapParam);
					@SuppressWarnings("unchecked")
					Map<String,Object> temp = (Map<String, Object>) dto.getData();
					Object dataobj = temp.get("list");
					List<Object> dataList =null;
					if(dataobj!=null){
						  dataList = (List<Object>) dataobj;
					}
					
					if (dataList != null && dataList.size() > 0) {
						for (int j = 0; j < dataList.size(); j++) {
							TradeRecord tradeRecord = new TradeRecord();
							Object obj = dataList.get(j);
							JSONObject  jasonObject = JSONObject.fromObject(obj);
							//json对象转换成map
							@SuppressWarnings("unchecked")
							Map<String,Object> mapData = (Map<String,Object>)jasonObject;
							
							//交易时间 
							//String tempDate = mapData.get("tranDate")+""+mapData.get("tranTime");
							String date = this.stringToDate(mapData);
//							//年
//							String tempDate1 = (mapData.get("tranDate")+""+mapData.get("tranTime")).substring(0, 4)+"-";
//							//月
//							String tempDate2 = (mapData.get("tranDate")+""+mapData.get("tranTime")).substring(4, 6)+"-";
//							//日
//							String tempDate3 = (mapData.get("tranDate")+""+mapData.get("tranTime")).substring(6, 8)+" ";
//							//时
//							String tempDate4 = (mapData.get("tranDate")+""+mapData.get("tranTime")).substring(8, 10)+":";
//							//分
//							String tempDate5 = (mapData.get("tranDate")+""+mapData.get("tranTime")).substring(10, 12)+":";
//							//秒
//							String tempDate6 = (mapData.get("tranDate")+""+mapData.get("tranTime")).substring(12, 14);
//							String date = tempDate1+""+tempDate2+""+tempDate3+""+tempDate4+""+tempDate5+""+tempDate6;
							tradeRecord.setReplayTime(date);
							//交易编号 (柜员交易号)
							tradeRecord.setTrWaterId((String) mapData.get("tellerNo"));
							//交易类型
							if (mapData.get("tranType").equals("23") && mapData.get("loanFlag").equals("C")) {
								tradeRecord.setTrTypes("入金");
							}else if(mapData.get("tranType").equals("23") && mapData.get("loanFlag").equals("D")){
								tradeRecord.setTrTypes("出金");
							}else if(mapData.get("tranType").equals("15") && mapData.get("loanFlag").equals("C")){
								tradeRecord.setTrTypes("解冻支付-转入");
							}else if(mapData.get("tranType").equals("15") && mapData.get("loanFlag").equals("D")){
								tradeRecord.setTrTypes("解冻支付-转出");
							}else if(mapData.get("tranType").equals("11") && mapData.get("loanFlag").equals("C")){
								tradeRecord.setTrTypes("普通转账-转入");
							}else if(mapData.get("tranType").equals("11") && mapData.get("loanFlag").equals("D")){
								tradeRecord.setTrTypes("普通转账-转出");
							}else {
								tradeRecord.setTrTypes("未知类型代码："+mapData.get("tranType")+"--"+mapData.get("loanFlag"));
							}
							
							//摘要
							tradeRecord.setNote((String) mapData.get("memo"));
							//交易对方账号
							tradeRecord.setOtherAccountNo((String) mapData.get("accountNo"));
							
							//交易对方信息
//							Map<String,Object> paramMap = new HashMap<String,Object>();
//							paramMap.put("userAccountNo", (String) mapData.get("accountNo"));
//							UserAccount userAccount = this.userAccountBo.queryUserAccountByParam(paramMap);
//							if (userAccount != null) {
//								tradeRecord.setOppositCompanyName(userAccount.getCompanyName());
//							}else {
//								tradeRecord.setOppositCompanyName("对方信息未知");
//							}
							//交易对方  就是开户账号
							tradeRecord.setOppositCompanyName((String) mapData.get("accountNm"));
							
							
							//交易金额
							tradeRecord.setTrMoney((BigDecimal) mapData.get("tranAmt"));
							//交易手续费
							tradeRecord.setTrCharge((BigDecimal) mapData.get("pdgAmt"));
							//交易后余额
							tradeRecord.setUserMoney((BigDecimal) mapData.get("accBalAmt"));
							//打印校验码
							tradeRecord.setPrintCheckCode((String) mapData.get("verifyCode"));
							
							tradeRecordList.add(tradeRecord);
							System.out.println(mapData);
						}
					}
				}
			}
			mav.addObject("endTime", endTimes);
			mav.addObject("startTime", startTimes);
			mav.addObject("userIds", userIds);
			mav.addObject("num", num);
			mav.addObject("listSize", tradeRecordList.size());
			mav.addObject("tradeRecordList", tradeRecordList);
			mav.setViewName("/capitalData/electricFrom");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		return mav;

	}
	
	//处理时间 格式    如 20151118181245转变为2015-11-18 18:12:45
	
	private String stringToDate(Map<String,Object> mapData) {
		String tempDate1 = (mapData.get("tranDate")+""+mapData.get("tranTime")).substring(0, 4)+"-";
		//月
		String tempDate2 = (mapData.get("tranDate")+""+mapData.get("tranTime")).substring(4, 6)+"-";
		//日
		String tempDate3 = (mapData.get("tranDate")+""+mapData.get("tranTime")).substring(6, 8)+" ";
		//时
		String tempDate4 = (mapData.get("tranDate")+""+mapData.get("tranTime")).substring(8, 10)+":";
		//分
		String tempDate5 = (mapData.get("tranDate")+""+mapData.get("tranTime")).substring(10, 12)+":";
		//秒
		String tempDate6 = (mapData.get("tranDate")+""+mapData.get("tranTime")).substring(12, 14);
		String date = tempDate1+""+tempDate2+""+tempDate3+""+tempDate4+""+tempDate5+""+tempDate6;
		return date;
	}
	
	@RequestMapping("/queryCompanyById")
	@ResponseBody
	public UserAccount queryCompanyById(String  userId) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		UserAccount userAccount = this.accountAuditBo.queryUserAccountByUserId(param);
		return userAccount;
	}
	
}
