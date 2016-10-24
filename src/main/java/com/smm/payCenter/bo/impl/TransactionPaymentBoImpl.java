package com.smm.payCenter.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.TransactionPaymentBO;
import com.smm.payCenter.dao.TransactionPaymentDao;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.TransactionDetail;
import com.smm.payCenter.domain.TransactionPayment;
import com.smm.payCenter.util.DateUtil;

@Service
public class TransactionPaymentBoImpl  implements TransactionPaymentBO{
	@Resource
	private TransactionPaymentDao transactionPaymentDao;
	
	//查询列表
	@Override
	public List<TransactionPayment> query(Map<String, Object> map) {
		List<TransactionPayment> list = transactionPaymentDao.query(map);
		return list;
	}

	//根据Id查询详情
	@Override
	public TransactionDetail queryDetailById(Integer id) {
		TransactionDetail td =  transactionPaymentDao.queryDetailById(id);
		return td;
	}
	//查询交易付款审核记录数
	@Override
	public Integer queryCount(Map<String, Object> map) {
		Integer count = -1;
		count = transactionPaymentDao.queryCount(map);
		return count;
	}

	/**
	 * 修改商城审核状态为通过
	 * */
	@Override
	public void updateConfirmStatus(Map<String, Object> map) {
		transactionPaymentDao.updateConfirmStatus(map);
	}

	@Override
	public void insertPaymentLog(Integer paymentId, String msg,
			String resultMessage) {
		DateUtil date = new DateUtil();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("paymentId",paymentId);
		map.put("operationname","审核");
		map.put("operationtime",date.currentDate());
		map.put("operationresult",msg);
		map.put("desc",resultMessage);
		map.put("createtime",date.currentDate());
		transactionPaymentDao.insertPaymentLog(map);
	}

	@Override
	public Integer queryConfirmStatus(Integer ConfirmId) {
		// TODO Auto-generated method stub
		return transactionPaymentDao.queryConfirmStatus(ConfirmId);
	}

	/* (non-Javadoc)
	 * @see com.smm.payCenter.bo.TransactionPaymentBO#queryById(java.lang.Integer)
	 */
	@Override
	public TransactionDetail queryById(Integer id) {
		//查询人工转账详情
		return transactionPaymentDao.queryById(id);
	}

}
