package com.smm.payCenter.bo.payFreeze.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.payFreeze.PayFreezeBO;
import com.smm.payCenter.dao.PayFreezeDAO;
import com.smm.payCenter.domain.PaymentRecord;
import com.smm.payCenter.domain.TransactionDetail;

/**
 * 
 * @author hece
 *
 */
@Service
public class PayFreezeBOImpl implements PayFreezeBO {

	@Resource
	private PayFreezeDAO payFreezedao;

	@Override
	public List<PaymentRecord> payFreezeList(Map<String, Object> paymap) {
		List<PaymentRecord> paymentList = payFreezedao.queryPayFreezeList(paymap);
		return paymentList;
	}

	@Override
	public TransactionDetail payFreezeDetail(Integer paymentid) {
		TransactionDetail tsdetail = payFreezedao.payFreezeDetail(paymentid);
		return tsdetail;
	}

	@Override
	public int queryPayFreezeNum(Map<String, Object> paymap) {
		int count = payFreezedao.queryPayFreezeNum(paymap);
		return count;
	}

	@Override
	public void closeOrder(Integer paymentid) {
		payFreezedao.closeOrder(paymentid);
	}

}
