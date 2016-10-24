package com.smm.payCenter.controller;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.CashApplyBo;
import com.smm.payCenter.bo.CheckRepeatBo;
import com.smm.payCenter.bo.FnlLogBO;
import com.smm.payCenter.bo.SystemDataQueryBo;
import com.smm.payCenter.bo.TrRecordBO;
import com.smm.payCenter.bo.useraccount.UserAccountBo;
import com.smm.payCenter.controller.PageUtils.PageBean;
import com.smm.payCenter.dao.UserAccountDao;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.CashApply;
import com.smm.payCenter.domain.FnlLog;
import com.smm.payCenter.domain.SystemData;
import com.smm.payCenter.domain.TrCashApply;
import com.smm.payCenter.domain.TrRecordCompanyInfo;
import com.smm.payCenter.domain.UserAccountBanEntity;
import com.smm.payCenter.domain.UserAccountEntity;
import com.smm.payCenter.util.IpUtil;
import com.smm.payCenter.util.JSONUtil;
import com.smm.payCenter.util.StringUtil;
import com.smmpay.common.author.Authory;
import com.smmpay.common.encrypt.Base64;
import com.smmpay.common.encrypt.DesCrypt;
import com.smmpay.common.request.RequestDataProxy;
import com.smmpay.inter.AuthorService;
import com.smmpay.inter.dto.res.ReturnDTO;
import com.smmpay.inter.smmpay.PlatformDebitsService;

/**
 * @author
 */

@Controller
@RequestMapping("/TrRecord")
public class TrRecordController {

	@Resource
	private UserAccountBo uBo;
	@Resource
	private SystemDataQueryBo systemDataQueryBo;
	private static Logger logger = Logger.getLogger(TrRecordController.class.getName());

	@Resource
	private TrRecordBO tBo;
	@Resource
	private FnlLogBO fnlLogBO;
	@Resource
	private CheckRepeatBo checkRepeatB;
	@Resource
	CashApplyBo CashApplyBo;
	@Resource
	private UserAccountBo userAccountBo;
	
	//平台出金
		@Autowired
		private PlatformDebitsService plaDebitsService;

		@Resource
		private UserAccountDao userAccountDao;

		@Autowired
		private AuthorService authorService;

	@RequestMapping("debitsVettedList")
	public ModelAndView getList(Integer pno, Integer dstatus, String startTime, String endTime) {
		ModelAndView mav = new ModelAndView();
		// 返回页面处理
		mav.setViewName("/home/debitsVetted");
		Map<String, Object> map = new HashMap<String, Object>();
		if (dstatus == null) {
			dstatus = 0;
		}
		if (StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)) {
			// startTime=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		map.put("dstatus", dstatus);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		//int totalRecords = this.tBo.getSize(map);
		List<TrCashApply> totalRecords = this.tBo.getList(map);
		// 分页开始
		if (pno == null) {
			pno = 1;
		}
		PageBean page = new PageBean(10, pno, totalRecords.size());
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		List<TrCashApply> rlist = this.tBo.getList(map);

		mav.addObject("totalRecords", totalRecords.size());// 总条数
		mav.addObject("totalPage", page.getTotalPages());// 总页数
		mav.addObject("startTime", startTime);
		mav.addObject("endTime", endTime);
		mav.addObject("rlist", rlist);
		mav.addObject("dstatus", dstatus);
		return mav;
	}

	/**
	 * 去确认/取消页面
	 * 
	 * @param trid
	 * @param trstatus
	 * @return
	 */
	@RequestMapping("goupdate")
	public ModelAndView goUpdatePage(Integer trid, Integer trstatus, String companyName, String money) {
		ModelAndView mav = new ModelAndView();
		// 返回页面处理
		mav.setViewName("/home/debitsconfirm");

		mav.addObject("trid", trid);
		mav.addObject("trstatus", trstatus);
		mav.addObject("companyName", companyName);
		mav.addObject("money", money);
		return mav;
	}

	/**
	 * 确认/取消出金请求
	 * 
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	@Transactional
	public Map<String, String> update(Integer trid, Integer trstatus, String companyName, String money,
			HttpServletRequest request) {
		
		synchronized (this) {
			Map<String, String> result = new HashMap<>();
			CashApply cash = CashApplyBo.queryCashApplyId(trid);

			logger.info("出金审核开始》》》》》》》》》》》》》》》》》》》》》》》》》");			
			// 验证信息是否正确
			if (cash == null) {
				result.put("code", "error");
				result.put("msg", "失败,出金记录不存在");
				logger.info("失败,出金记录不存在");
				return result;
			} else if (cash.getReplayStatus() == 1) {
				result.put("code", "error");
				result.put("msg", "失败,审核已通过");
				logger.info("失败,审核已通过");
				return result;
			} else if (cash.getApplyStatus() == 2) {
				result.put("code", "error");
				result.put("msg", "失败，审核已关闭");
				logger.info("失败,审核已关闭");
				return result;
			}else  {
				Integer bo=checkRepeatB.queryCashDetailCount(Integer.valueOf(cash.getUserId()));//验证用户下是否有未完成的 出金指令
				if(bo>0){
					result.put("code", "error");
					result.put("msg", "失败,该用户下有出金请求未处理完成！");
					logger.info("失败,该用户下有出金请求未处理完成！");
					return result;
				}
				DecimalFormat df=new DecimalFormat("######0.00");
				String md=String.valueOf(cash.getUserId()) +String.valueOf(df.format(cash.getCashMoney()))+ cash.getApplyTime();
				byte[] desColums;
				try {
					
					desColums = DesCrypt.encryptMode(md.getBytes("utf-8"));
					String desColumsStr = Base64.getBase64(desColums);
					logger.info("加密前："+md+"加密值：desColumsStr："+desColumsStr+",数据库加密字段值："+cash.getVerifyCode());
					if(!desColumsStr.equals(cash.getVerifyCode())){
					result.put("code", "error");
					result.put("msg", "失败,出金记录涉嫌被更改！");
					logger.info("加密比对失败");
					logger.info("失败,出金记录涉嫌被更改！");
					return result;
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					result.put("code", "error");
					result.put("msg", "失败,验证加密异常！");
					logger.info("失败,验证加密异常！");
					return result;
				}
				
			}

			Map<String, Object> map = new HashMap<>(); // 此map用于update
														// tr_cash_apply表格中的apply_status
			Map<String, String> map1 = new HashMap<>(); // 此map用于向调用的接口中传递数据
			Map<String, String> map2 = new HashMap<>(); // 此map用于接受接口的返回值
			TrCashApply trCashApply = tBo.getObject(trid);
			// 出金审核人是当前登录人
			Account a = (Account) request.getSession().getAttribute("userInfo");
			// 加入管理员操作日志
			FnlLog log = new FnlLog();
			log.setFnlAccount(a.getId());
			log.setType("审核出金");

			if (trstatus == 1) {
				//验证余额是否充足
//				UserAccountEntity userAccountm=userAccountBo.getUserAccountById(Integer.valueOf(cash.getUserId()));
//				if(userAccountm.getUseMoney()==null||Double.valueOf(userAccountm.getUseMoney())<=cash.getCashMoney()){
//					result.put("code", "error");
//					result.put("msg", "失败,余额不足！");
//					logger.info("失败,余额不足！");
//					return result;
//				}
				log.setContent("通过");
				// 判断出金操作是否重复
			/*	Map<String, String> checkmap = new HashMap<>();
				checkmap.put("cashApplyId", trid + "");
				Integer num = checkRepeatB.checkCashApply(checkmap);
				if (num >= 1) {
					result.put("code", "error");
					result.put("msg", "出金操作已完成或者真正出金中,请勿重复操作");
					return result;
				}*/
			} else if (trstatus == 2) {
				log.setContent("关闭");
			}
			String stdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			log.setCreatedAt(stdate);
			log.setUserCompanyName(companyName);
			log.setTransferMoney(Double.valueOf(money));
			log.setIp(IpUtil.getIpAddr());
			fnlLogBO.addFnlLog(log);

			if (trstatus == 2) { // 关闭审核
				map.put("trid", trid);
				map.put("trstatus", trstatus);
				if (a != null && a.getId() != null) {
					map.put("replay_time", new Date());
					map.put("replay_user_id", a.getId());
					// 未添加 具体修改的列
					this.tBo.update(map);

					map.put("totalMoney", trCashApply.getCashMoney().add(trCashApply.getCounter_fee()));
					map.put("userId", trCashApply.getUserId());
					this.tBo.updateUPC(map);
					this.tBo.updateUA(map);

					result.put("code", "succ");
				} else {
					result.put("code", "error");
					result.put("msg", "系统提示，操作失败");
				}
				return result;
			} else { // 审核通过
				map.put("trid", trid);
				map.put("trstatus", trstatus);
				if (a != null && a.getId() != null) {
					map.put("replay_time", new Date());
					map.put("replay_user_id", a.getId());
					// 未添加 具体修改的列
					//map = this.tBo.update(map);
				} else {
					result.put("code", "error");
					result.put("msg", "系统提示，操作失败");
					return result;
				}
			}
			map1.put("payChannelId", "10001");
			map1.put("userId", trCashApply.getUserId() + "");
			if (trCashApply.getUserId() != null) {
				UserAccountEntity u = this.uBo.getById(trCashApply.getUserId());
				if (u != null) {
					map1.put("userPayChannelId", u.getUserPayChannelId() + "");
					map1.put("payChannelUserAccount", u.getUserAccountNo());
					map1.put("userCompanyName", u.getCompanyName());
					map1.put("userEmail", u.getUserName());
					map1.put("userUseMoney", u.getUseMoney()+"");
					map1.put("userFreezeMoney", u.getFreezeMoney()+"");
					map1.put("accountNo", u.getUserAccountNo());// 出金账户
					map1.put("recvAccNm", u.getCompanyName());// 收款账户名称
				}
			}

			// 根据 user_bind_bank 表主键查询信息
			if (trCashApply.getBankId() != null) {
				UserAccountBanEntity userAccount = this.uBo.getBindBankById(trCashApply.getBankId());
				if (userAccount != null) {
					map1.put("counterFee", trCashApply.getCounter_fee() + "");
					map1.put("cashType", "1");
					map1.put("recvAccNo", userAccount.getBankAccountNo());// 收款账户
					map1.put("tranAmt", trCashApply.getCashMoney() + "");// 交易金额
					if (StringUtils.isNoneBlank(userAccount.getBankTypeId())
							&& "108".equals(userAccount.getBankTypeId())) {
						map1.put("sameBank", "0");// 中信标识
					} else {
						map1.put("sameBank", "1");
					}
					map1.put("recvTgfi", userAccount.getCnaps());// 收款账户开户行支付联行号
					map1.put("recvBankNm", userAccount.getBankName());// 收款账户开户行名

					map1.put("cashApplyId", trCashApply.getId() + "");// tr_cash_apply
																		// 表的主键
					map1.put("cashBankId", trCashApply.getBankId() + "");
				}
			}
				
			map2 = addDebitsDetil(map1, 1);
			logger.info("出金审核开结束》》》》》》》》》》》》》》》》》》》》》》》》》"+map2.get("code")+"   msg: "+map2.get("msg"));			
			return map2;
		}
	}

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
			//looger.info("出金接口返回returnDTO:"+returnDTO!=null?"Status:"+returnDTO.getStatus()+"Msg:"+returnDTO.getMsg():"");

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



	/**
	 * 
	 * 查询公司交易明细
	 */
	@RequestMapping("/companyDetail")
	public ModelAndView companyDetail(Integer pno, String userId,String payChannelId) {
		ModelAndView mav = new ModelAndView();
		try {
			// 参数
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("payChannelId", payChannelId);
			TrRecordCompanyInfo rc = tBo.queryTrRecordInfoByUserId(map);
			mav.addObject("rc", rc);
			mav.setViewName("/home/companyDetail");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return mav;
	}
}