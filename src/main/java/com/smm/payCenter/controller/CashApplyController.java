package com.smm.payCenter.controller;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.CashApplyBo;
import com.smm.payCenter.bo.CheckRepeatBo;
import com.smm.payCenter.bo.FnlLogBO;
import com.smm.payCenter.bo.TrRecordBO;
import com.smm.payCenter.bo.useraccount.UserAccountBo;
import com.smm.payCenter.controller.PageUtils.PageBean;
import com.smm.payCenter.controller.vipmanage.vipManageController;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.CashApply;
import com.smm.payCenter.domain.FnlLog;
import com.smm.payCenter.util.IpUtil;
import com.smm.payCenter.util.StringUtil;
import com.smmpay.common.encrypt.Base64;
import com.smmpay.common.encrypt.DesCrypt;

import freemarker.log.Logger;

/**
 * 出金记录
 * 
 * @author tantaigen
 *
 */
@Controller
@RequestMapping("/CashApply")
public class CashApplyController {
	@Resource
	CashApplyBo CashApplyBo;
	@Resource
	TrRecordBO tBo;

	@Resource
	private UserAccountBo uBo;
	@Resource
	private FnlLogBO fnlLogBO;
	@Resource
	private CheckRepeatBo checkRepeatB;
	private static Logger logger = Logger.getLogger(vipManageController.class.getName());
	@Resource
	private UserAccountBo userAccountBo;
	/**
	 * 查询出金记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("queryCashApplyList")
	public ModelAndView queryCashApplyList(HttpServletRequest request, Integer pno, String companyName,
			String startTime, String endTime, String tradeId) {
		ModelAndView view = new ModelAndView("CashApply/cashApplyList");
		CashApply cash = new CashApply();

		cash.setStartTime(startTime);
		if (companyName != null && !"".equals(companyName)) {
			cash.setCompanyName(StringUtil.doDecoder(companyName));

		}
		cash.setEndTime(endTime);
		cash.setTradeId(tradeId);
		Integer num = CashApplyBo.queryCashApplycount(cash);// 查询总记录数
		if (pno == null) {
			pno = 1;
		}
		PageBean page = new PageBean(20, pno, num);
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		cash.setStartNum(startNum);
		cash.setEndNum(endNum);
		List<CashApply> list = CashApplyBo.queryCashApply(cash);
		view.addObject("list", list);
		view.addObject("cash", cash);
		view.addObject("totalRecords", num);// 总条数
		view.addObject("totalPage", page.getTotalPages());// 总页数
		return view;

	}

	@RequestMapping("queryCashApplycount")
	@ResponseBody
	public Integer queryCashApplycount(HttpServletRequest request, Integer pno, String companyName, String startTime,
			String endTime, String tradeId) {

		CashApply cash = new CashApply();

		cash.setStartTime(startTime);
		if (companyName != null && !"".equals(companyName)) {
			cash.setCompanyName(StringUtil.doDecoder(companyName));
		}
		cash.setEndTime(endTime);
		cash.setTradeId(tradeId);
		Integer num = CashApplyBo.queryCashApplycount(cash);// 查询总记录数

		return num;

	}

	/**
	 * 退回
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("returnCashApply")
	@ResponseBody
	public Map<String, Object> returnCashApply(HttpServletRequest request, Integer id) {

		Map<String, Object> map = new HashMap<>();
		if (id != null) {
			CashApply cash = CashApplyBo.queryCashApplyId(id);
			// 验证信息是否正确
			if (cash == null) {
				map.put("result", "fail");
				map.put("msg", "退回失败失败,出金记录不存在");
				logger.info("退回失败失败,出金记录不存在");
				return map;
			}else if (cash.getApplyStatus() == 1) {
				map.put("result", "fail");
				map.put("msg", "失败,出金已经成功");
				logger.info("失败,出金已经成功");
				return map;
			} else if(cash.getApplyStatus() == 0){
				map.put("result", "fail");
				map.put("msg", "失败,出金正在请求中");
				logger.info("失败,出金正在请求中");
				return map;
			} else {		
				DecimalFormat df=new DecimalFormat("######0.00");
				String md=String.valueOf(cash.getUserId()) + String.valueOf(df.format(cash.getCashMoney())) + cash.getApplyTime();
				byte[] desColums;
				try {					
					desColums = DesCrypt.encryptMode(md.getBytes("utf-8"));
					String desColumsStr = Base64.getBase64(desColums);
					logger.info("加密前："+md+"加密值：desColumsStr："+desColumsStr+",数据库加密字段值："+cash.getVerifyCode());
					if(!desColumsStr.equals(cash.getVerifyCode())){
						map.put("result", "fail");
						map.put("msg", "失败,出金记录涉嫌被更改！");
						logger.info("失败,出金记录涉嫌被更改！");
						return map;
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					map.put("result", "fail");
					map.put("msg", "失败,出金记录涉嫌被更改！");
					logger.info("失败,出金记录涉嫌被更改！");
					return map;
				}
			}
			cash.setApplyStatus(3);
			int b = CashApplyBo.updateapplyStatus(cash);
			CashApplyBo.updateUserPayChannel(cash);
			CashApplyBo.updateUserAccount(cash);
			if (b < 0) {
				map.put("result", "fail");
				map.put("msg", "退回失败！");
				logger.info("手动退回出金失败！");
				return map;

			}
			map.put("result", "ok");
			logger.info("手动退回出金成功！");
			map.put("msg", "退回成功！");

			Account account = (Account) request.getSession().getAttribute("userInfo");
			// 加入管理员操作日志
			FnlLog log = new FnlLog();
			log.setFnlAccount(account.getId());
			log.setType("手动退还出金");
			log.setContent("");
			String stdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			log.setUserCompanyName(cash.getUserCompanyName());
			log.setTransferMoney(cash.getCashMoney());
			log.setCreatedAt(stdate);
			log.setIp(IpUtil.getIpAddr());
			fnlLogBO.addFnlLog(log);

			return map;

		} else {
			map.put("result", "fail");
			map.put("msg", "参数错误！");
			return map;

		}

	}

	/**
	 * 重新出金
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("againCashApply")
	@ResponseBody
	public Map<String, Object> againCashApply(HttpServletRequest request, Integer id) {

		synchronized (this) {
			Map<String, Object> map = new HashMap<>();
			if (id != null) {
				CashApply cash = CashApplyBo.queryCashApplyId(id);
				// 验证信息是否正确
				if (cash == null) {
					map.put("result", "fail");
					map.put("msg", "失败,出金记录不存在");
					logger.info("失败,出金记录不存在");
					return map;
				} else if (cash.getApplyStatus() == 1) {
					map.put("result", "fail");
					map.put("msg", "失败,出金已经成功");
					logger.info("失败,出金已经成功");
					return map;
				} else if(cash.getApplyStatus() == 0){
					map.put("result", "fail");
					map.put("msg", "失败,出金正在请求中");
					logger.info("失败,出金正在请求中");
					return map;
				}else  {
					Integer bo=checkRepeatB.queryCashDetailCount(Integer.valueOf(cash.getUserId()));//验证用户下是否有未完成的 出金指令
					if(bo>0){
						map.put("result", "fail");
						map.put("msg", "失败,该用户下有出金请求未处理完成！");
						logger.info("失败,该用户下有出金请求未处理完成！");
						return map;
					}
					DecimalFormat df=new DecimalFormat("######0.00");
					String md=String.valueOf(cash.getUserId()) + String.valueOf(df.format(cash.getCashMoney())) + cash.getApplyTime();
					byte[] desColums;
					try {
						
						 desColums = DesCrypt.encryptMode(md.getBytes("utf-8"));
						String desColumsStr = Base64.getBase64(desColums);
						logger.info("加密前："+md+"加密值：desColumsStr："+desColumsStr+",数据库加密字段值："+cash.getVerifyCode());
						if(!desColumsStr.equals(cash.getVerifyCode())){
							map.put("result", "fail");
							map.put("msg", "失败,出金记录涉嫌被更改！");
							logger.info("加密值比对错误");
							logger.info("失败,出金记录涉嫌被更改！");
							return map;
						}
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						map.put("result", "fail");
						map.put("msg", "失败,出金记录涉嫌被更改！");
						logger.info("失败,出金记录涉嫌被更改！");
						return map;
					}
					
				}

				/*// 判断出金操作是否重复
				Map<String, String> checkmap = new HashMap<>();
				checkmap.put("cashApplyId", id + "");
				Integer num = checkRepeatB.checkCashApply(checkmap);
				if (num >= 1) {
					map.put("result", "fail");
					map.put("msg", "出金操作已完成或者真正出金中,请勿重复操作");
					logger.info("手动重复出金失败，重复操作！");
					return map;
				}*/
//				UserAccountEntity userAccountm=userAccountBo.getUserAccountById(Integer.valueOf(cash.getUserId()));
//				if(userAccountm.getUseMoney()==null||Double.valueOf(userAccountm.getUseMoney())<=cash.getCashMoney()){
//					map.put("code", "error");
//					map.put("msg", "失败,余额不足！");
//					logger.info("失败,余额不足！");
//					return map;
//				}
				cash.setApplyStatus(0);// 状态改为0
				Map<String, String> mapDate = new HashMap<>();
				mapDate.put("payChannelId",cash.getPayChannelId() +"");
				mapDate.put("userId", cash.getUserId());
				mapDate.put("userPayChannelId", cash.getUserPayChannelId()+ "");
				mapDate.put("payChannelUserAccount", cash.getUserAccountNo());
				mapDate.put("userCompanyName", cash.getUserCompanyName());
				mapDate.put("userEmail", cash.getUserEmail());
				mapDate.put("userUseMoney", cash.getUserUseMoney() + "");
				mapDate.put("userFreezeMoney", cash.getUserFreezeMoney() + "");
				mapDate.put("accountNo", cash.getUserAccountNo());// 出金账户
				mapDate.put("recvAccNm", cash.getCashBankName());// 收款账户名称
				mapDate.put("counterFee", cash.getCounterFee());
				mapDate.put("cashType", "1");
				mapDate.put("recvAccNo", cash.getCashBankNo());// 收款账户
				mapDate.put("tranAmt", cash.getCashMoney() + "");// 交易金额
				if (StringUtils.isNoneBlank(cash.getBankTypeId()) && "108".equals(cash.getBankTypeId())) {
					mapDate.put("sameBank", "0");// 中信标识
				} else {
					mapDate.put("sameBank", "1");
				}
				mapDate.put("recvTgfi", cash.getCashBankCnaps());// 收款账户开户行支付联行号
				mapDate.put("recvBankNm", cash.getCashBankName());// 收款账户开户行名

				mapDate.put("cashApplyId", cash.getId() + "");// tr_cash_apply
																// 表的主键
				mapDate.put("cashBankId", cash.getCashBankId());

				Map<String, String> retmap = uBo.addDebitsDetil(mapDate, 1);/// 调用申请出金接口
				if (retmap == null || retmap.get("code").equals("error")) {
					map.put("result", "fail");
					map.put("msg", retmap.get("msg"));
					logger.info("手动重复出金失败，调用接口出错：" + retmap.get("msg"));

					return map;

				}
				int b = CashApplyBo.updateapplyStatus(cash);// 状态改为待出金
				if (b < 0) {
					map.put("result", "fail");
					map.put("msg", "出金申请失败！");
					return map;

				}
				map.put("result", "ok");
				map.put("msg", "出金申请成功！");

				Account account = (Account) request.getSession().getAttribute("userInfo");
				// 加入管理员操作日志
				FnlLog log = new FnlLog();
				log.setFnlAccount(account.getId());
				log.setType("手动重发出金");
				log.setContent("");
				String stdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				log.setUserCompanyName(cash.getUserCompanyName());
				log.setTransferMoney(cash.getCashMoney());
				log.setCreatedAt(stdate);
				log.setIp(IpUtil.getIpAddr());
				fnlLogBO.addFnlLog(log);

				return map;

			} else {
				map.put("result", "fail");
				map.put("msg", "参数错误！");
				return map;

			}
		}
	}


}
