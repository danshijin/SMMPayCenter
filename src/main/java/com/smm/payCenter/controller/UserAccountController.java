package com.smm.payCenter.controller;

import java.math.BigDecimal;
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

import com.smm.payCenter.bo.FnlLogBO;
import com.smm.payCenter.bo.useraccount.UserAccountBo;
import com.smm.payCenter.controller.PageUtils.PageBean;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.FnlLog;
import com.smm.payCenter.domain.TrRecord;
import com.smm.payCenter.domain.UserAccountBanEntity;
import com.smm.payCenter.domain.UserAccountEntity;
import com.smm.payCenter.util.IpUtil;
import com.smm.payCenter.util.StringUtil;



/**
 * @author 
 */
@Controller
@RequestMapping("/UserAccount")
public class UserAccountController {

	@Resource
	private UserAccountBo uBo;
	@Resource
	private FnlLogBO fnlLogBO;
	
	@RequestMapping("getUserList")  
	public ModelAndView getList(Integer pno,UserAccountEntity useraccount) {
		ModelAndView mav=new ModelAndView();
		//返回页面处理
		mav.setViewName("/home/platformDebits");
		if (pno == null) { 
			pno = 1;
		}
		mav.addObject("totalRecords",0);//总条数
		mav.addObject("totalPage",1);//总页数
		return mav;
	}
    @RequestMapping("getSelList")  
	public ModelAndView getSelList(Integer pno,UserAccountEntity useraccount) {
		ModelAndView mav=new ModelAndView();
		//返回页面处理
		mav.setViewName("/home/platformDebits");
		if(StringUtils.isNoneBlank(useraccount.getCompanyName())){
			useraccount.setCompanyName(StringUtil.doDecoder(useraccount.getCompanyName()));
		}
		int totalRecords =this.uBo.getSize(useraccount);
		// 分页开始
		if (pno == null) { 
			pno = 1;
		}
		PageBean page = new PageBean(10, pno, totalRecords);
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		useraccount.setStartNum(startNum);
		useraccount.setEndNum(endNum);
		List<UserAccountEntity> rlist=this.uBo.getList(useraccount);
		for(UserAccountEntity u: rlist){
			Map<String, BigDecimal> returnmap=this.uBo.getBankMoney(u.getUserAccountNo());
			u.setBankFactMoney(returnmap.get("SJAMT"));
		 	u.setBankUseMoney(returnmap.get("KYAMT")); 
		}
		
		mav.addObject("totalRecords",totalRecords);//总条数
		mav.addObject("totalPage",page.getTotalPages());//总页数
		mav.addObject("rlist",rlist);
		mav.addObject("useraccount",useraccount);
		return mav;
	}
	
    /**
     * 去余额调整页面
     * @param trid
     * @param trstatus
     * @return
     */
    @RequestMapping("goUpdate")
	public ModelAndView goUpdatePage(Integer userId,String countMoney,String bankFactMoney,String companyName) {
		ModelAndView mav=new ModelAndView();
		//返回页面处理
		mav.setViewName("/home/platformUpate");
		mav.addObject("userId", userId);
		mav.addObject("countMoney", countMoney);
		mav.addObject("bankFactMoney", bankFactMoney);
		mav.addObject("companyName", companyName);
		return mav;
	}
    
	/**
	 * 确认调整系统账户余额
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public Map<String, Object> update(Integer userId,BigDecimal bankFactMoney,BigDecimal countMoney,BigDecimal countMoneyOld,String companyName,HttpServletRequest request) {
		Account account = (Account) request.getSession().getAttribute("userInfo");
		//加入管理员操作日志
		FnlLog log = new FnlLog();
		log.setFnlAccount(account.getId());
		log.setType("手动调整账户余额");
		log.setContent("系统账户余额由:"+countMoneyOld+"调整为:"+countMoney);
		String stdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		log.setCreatedAt(stdate);
		log.setUserCompanyName(companyName);
		log.setTransferMoney(Math.abs(countMoneyOld.subtract(countMoney).doubleValue()));
		log.setIp(IpUtil.getIpAddr());
		fnlLogBO.addFnlLog(log);
		
		Map<String, Object> map = new HashMap<>();
		map.put("code", "error");
		map.put("msg", "系统提示，操作失败");	
		
		map.put("userId", userId);
		TrRecord t=new TrRecord();
		t.setTr_money(countMoney.subtract(countMoneyOld));
		t.setTr_type(0);
		t.setTr_apply_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		t.setTr_apply_status(1);
		t.setUser_money(countMoney);
		t.setUser_id(userId);
		UserAccountEntity t2=this.uBo.getById(userId);
		//余额调整 是指 当前调整的余额-冻结金额=可用金额  具体修改的是可用金额
		if(StringUtils.isNoneBlank(t2.getFreezeMoney()+"")){
			BigDecimal freezeMoney=new BigDecimal(t2.getFreezeMoney());
			countMoney=countMoney.subtract(freezeMoney);
		}
		if(countMoney.signum()==-1){
			map.put("msg", "系统提示,调整后可用余额小于0");	
			return map;
		}
		map.put("countMoney", countMoney);
		if(t2.getUserPayChannelId()!=null && StringUtils.isNoneBlank(t2.getCompanyName())){
			t.setUser_pay_channel_id(t2.getUserPayChannelId());
			t.setUser_company_name(t2.getCompanyName());
			t.setNote("后台手动处理");
			try {
				int count1=this.uBo.updateUserAccount(map);
				int count2=this.uBo.updateUserPayChannel(map);
				int count3=this.uBo.insertTrRecord(t);
				if(count1>0 && count2>0 && count3>0){
					map.put("code", "succ");	
					map.put("msg", "系统提示，操作成功");	
				}else{
					return map;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return map;
			}
		}else{
			return map;
		}
		return map;
	}
	
	
	@RequestMapping("goSelPage")  
	public ModelAndView goSelPage(UserAccountEntity useraccount) {
		ModelAndView mav=new ModelAndView();
		//返回页面处理
		mav.setViewName("/home/balanceAlter");
		
		return mav;
	}
	
	
	@RequestMapping("getDebitsList")
	@ResponseBody
	public Map<String, Object> getDebitsList(UserAccountEntity user) {
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("user", user);
		List<UserAccountEntity> ulist=this.uBo.getDebitsList(user);
		if(ulist.size()>20){
			map.put("code", "error");
			map.put("msg", "系统提示，操作失败");	
		}else{
			map.put("code", "succ");
			map.put("ulist", ulist);
		}
		return map;
	}                                     
	
    /**
     * 去确认出金页面
     * @param trid
     * @param trstatus
     * @return
     */
    @RequestMapping("goDebitsPage")
	public ModelAndView goDebitsPage(Integer userId,String userCompanyName) {
		ModelAndView mav=new ModelAndView();
		//返回页面处理
		mav.setViewName("/home/DebitsUpate");
		mav.addObject("userId", userId);
		List<UserAccountBanEntity> ulist=this.uBo.getUserBindBank(userId);
		UserAccountEntity user=this.uBo.getUserAccountById(userId);
		mav.addObject("ulist", ulist);
		mav.addObject("user", user);
		mav.addObject("userCompanyName", userCompanyName);
		return mav;
	}
    /**
	 * 根据bindId查询 判断是否是中信
	 * @return
	 */
	@RequestMapping("iszxBank")
	@ResponseBody
    public Map<String, String> iszxBank(Integer bindId) {
		Map<String, String> map = new HashMap<>();
		map.put("code", "succ");	
		map.put("msg", "1");	//不是中信
		UserAccountBanEntity userAccount=this.uBo.getBindBankById(bindId);
		if(userAccount !=null && StringUtils.isNoneBlank(userAccount.getBankTypeId())){
			if("108".equals(userAccount.getBankTypeId())){
				map.put("msg", "0");//是中信
			}
		}
		return map;
    }
    
    
    /**
	 * 写出金记录表，调用出金接口
	 * @return
	 */
	@RequestMapping("addDebitsDetil")
	@ResponseBody
	public Map<String, String> addDebitsDetil(HttpServletRequest request,Integer userId,Integer bindId, BigDecimal drawMoney,BigDecimal smoney,String userCompanyName) {
		Map<String, String> map = new HashMap<>();
		Map<String, String> map2 = new HashMap<>();
		Account account = (Account) request.getSession().getAttribute("userInfo");
		// 加入管理员操作日志
		FnlLog log = new FnlLog();
		log.setFnlAccount(account.getId());
		log.setType("平台出金");
		log.setContent("出金金额为："+drawMoney+"  手续费为："+smoney);
		String stdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		log.setUserCompanyName(userCompanyName);
		log.setTransferMoney(drawMoney.add(smoney).doubleValue());
		log.setCreatedAt(stdate);
		log.setIp(IpUtil.getIpAddr());
		fnlLogBO.addFnlLog(log);
		
		map2.put("code", "error");
		map2.put("msg", "系统提示，操作失败");
		
		String payChannelId="10001";
		map.put("payChannelId", payChannelId);
		map.put("userId", userId+"");
		try{
			if(userId!=null){
				UserAccountEntity u=this.uBo.getById(userId);
				if(u !=null){
					map.put("userName",u.getUserName());
					map.put("cashType", "1");
					map.put("cashBankId",bindId+"");
			        map.put("cashMoney",drawMoney+"");
			    	map2=this.uBo.addDebitsDetil(map,0);
				}
			}
		} catch (Exception e) {
			return map2;
		}
		return map2;
	}
	
}
