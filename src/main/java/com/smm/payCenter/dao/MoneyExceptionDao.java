package com.smm.payCenter.dao;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.MoneyException;

public interface MoneyExceptionDao {

	int queryMoneyExceptionCount(Map<String, Object> map);

	List<MoneyException> queryMoneyExceptionList(Map<String, Object> map);

	Integer updateMoneyException(Map<String, Object> map);

}
