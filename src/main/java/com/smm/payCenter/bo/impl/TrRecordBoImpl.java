package com.smm.payCenter.bo.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smm.payCenter.bo.TrRecordBO;
import com.smm.payCenter.dao.trrecord.TrRecordDAO;
import com.smm.payCenter.domain.TrCashApply;
import com.smm.payCenter.domain.TrRecordCompanyInfo;


@Service
public class TrRecordBoImpl implements TrRecordBO{

	 @Resource
	 private TrRecordDAO tDao;
	
	@Override
	public List<TrCashApply> getList(Map<String, Object> map) {
		
		return this.tDao.getList(map);
	}

	@Override
	@Transactional("cuohe")
	public Map<String, Object> update(Map<String, Object> map) {
		Integer count= this.tDao.update(map);
		if(count>0){
			map.put("code", "succ");	
			map.put("msg", "系统提示，操作成功");	
		}else{
			map.put("code", "error");
			map.put("msg", "系统提示，操作失败");	
			
		}
		return map;
	}

	@Override
	public Integer getSize(Map<String, Object> map) {
		return this.tDao.getSize(map);
	}

	/* (non-Javadoc)
	 * @see com.smm.payCenter.bo.TrRecordBO#getObject(java.util.Map)
	 */
	@Override
	public TrCashApply getObject(Integer id) {
		return this.tDao.getObject(id);
	}

	/* (non-Javadoc)
	 * @see com.smm.payCenter.bo.TrRecordBO#updateUPC(java.util.Map)
	 */
	@Override
	public Map<String, Object> updateUPC(Map<String, Object> map) {
		Integer count= this.tDao.updateUPC(map);
		if(count>0){
			map.put("code", "succ");	
			map.put("msg", "系统提示，操作成功");	
		}else{
			map.put("code", "error");
			map.put("msg", "系统提示，操作失败");	
			
		}
		return map;
	}

	/* (non-Javadoc)
	 * @see com.smm.payCenter.bo.TrRecordBO#updateUA(java.util.Map)
	 */
	@Override
	public Map<String, Object> updateUA(Map<String, Object> map) {
		Integer count= this.tDao.updateUA(map);
		if(count>0){
			map.put("code", "succ");	
			map.put("msg", "系统提示，操作成功");	
		}else{
			map.put("code", "error");
			map.put("msg", "系统提示，操作失败");	
			
		}
		return map;
	}

	@Override
	public TrRecordCompanyInfo queryTrRecordInfoByUserId(
			Map<String, Object> map) {
		TrRecordCompanyInfo rc = null;
		try {
			rc=this.tDao.queryTrRecordInfoByUserId(map);	
			if(rc==null){
				rc=new TrRecordCompanyInfo();
			}
			Double totalMoney = this.tDao.queryTotalMoneyByUserId(map);
			rc.setTotalMoney(totalMoney);
			Double smmFreezeMoney = this.tDao.querySmmFreezeMoneyByUserId(map);
			rc.setSmmFreezeMoney(smmFreezeMoney);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rc;
	}

	
}
