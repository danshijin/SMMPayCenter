package com.smm.payCenter.bo.capitalEntry.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.capitalEntry.CapitalEntryBO;
import com.smm.payCenter.dao.users.CapitalEntryDAO;
import com.smm.payCenter.domain.PaymentRecord;
import com.smm.payCenter.domain.TransactionDetail;

/**
 * 
 * @author hece
 *
 *	强制转账
 */
@Service
public class CapitalEntryBOImpl implements CapitalEntryBO {
	
	@Resource
	private CapitalEntryDAO capitaldao;

	/**
	 * 转账列表
	 */
	@Override
	public List<PaymentRecord> paymentList(Map<String, Object> paymap) {
		List<PaymentRecord> paymentList = new ArrayList<PaymentRecord>();
		paymentList = capitaldao.queryCapitalEntryList(paymap);
		return paymentList;
	}

	/**
	 * 转账详情
	 * */
	@Override
	public TransactionDetail paymentDetail(Integer paymentid) {
		TransactionDetail tdetail = capitaldao.capitalEntryDetail(paymentid);
		return tdetail;
	}

	/**
	 * 查询记录总条数
	 */
	@Override
	public int queryPaymentNum(Map<String, Object> paymap) {
		int paynum = capitaldao.selectPaymentNum(paymap);
		return paynum;
	}

	/**\
	 * 修改审核状态为已拒绝
	 * */
	@Override
	public void updateConfirmStatus(Map<String, Object> map) {
		capitaldao.updateConfirmStatus(map);
	}

	/* (non-Javadoc)
	 * @see com.smm.payCenter.bo.capitalEntry.CapitalEntryBO#queryRefundList(java.util.Map)
	 */
	@Override
	public List<PaymentRecord> queryRefundList(Map<String, Object> paymap) {
		// TODO Auto-generated method stub
		return capitaldao.queryRefundList(paymap);
	}

	/* (non-Javadoc)
	 * @see com.smm.payCenter.bo.capitalEntry.CapitalEntryBO#queryRefundAccount()
	 */
	@Override
	public Integer queryRefundAccount(Map<String, Object> paymap) {
		// TODO Auto-generated method stub
		return capitaldao.queryRefundAccount(paymap);
	}

	
}
