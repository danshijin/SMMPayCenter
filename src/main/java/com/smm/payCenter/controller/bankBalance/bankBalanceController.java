package com.smm.payCenter.controller.bankBalance;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.BankBalanceBO;
import com.smm.payCenter.controller.PageUtils.PageBean;
import com.smm.payCenter.domain.AccountChannelEntity;

/**
 * wangqingfei 银行余额查询
 */
@Controller
@RequestMapping("/bankBalance")
public class bankBalanceController implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(bankBalanceController.class.getName());

	@Resource
	private BankBalanceBO bankBalanceBO;

	@RequestMapping("/queryBankBalance")
	public ModelAndView bankBalanceList(Integer pno, String companyName, String companyEmail, String accountNo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/bankBalance/bankBalanceList");
		Map<String, Object> map = new HashMap<>();

		if(StringUtils.isBlank(companyName)&&StringUtils.isBlank(companyEmail)&&StringUtils.isBlank(accountNo)){
			mav.addObject("totalRecords",0);
			mav.addObject("totalPage",0);// 总页数
			return mav;
		}
		map.put("companyName", companyName);
		map.put("userName", companyEmail);
		map.put("userAccountNO", accountNo);

		try {
			Integer totalRecords = bankBalanceBO.getCompanyAccountNOCount(map);
			if (pno == null) {
				pno = 1;
			}
			PageBean page = new PageBean(20, pno, totalRecords);
			int startNum = page.getStartNum();
			int endNum = page.getEndNum();

			map.put("startNum", startNum);
			map.put("endNum", endNum);

			List<AccountChannelEntity> listData = bankBalanceBO.getCompanyAccountNO(map);
			mav.addObject("totalRecords",totalRecords);
			mav.addObject("totalPage",page.getTotalPages());// 总页数
			mav.addObject("listData",listData);//调用接口后要修改
			mav.addObject("companyName", companyName);
			mav.addObject("companyEmail", companyEmail);
			mav.addObject("accountNo", accountNo);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}

		return mav;

	}
}
