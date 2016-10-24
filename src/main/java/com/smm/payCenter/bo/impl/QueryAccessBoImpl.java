package com.smm.payCenter.bo.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.QueryAccessBO;
import com.smm.payCenter.dao.QueryAccessDAO;
import com.smm.payCenter.domain.DayBalance;
import com.smm.payCenter.domain.UserAccount;
import com.smm.payCenter.util.DateUtil;


@Service
public class QueryAccessBoImpl implements QueryAccessBO {

	@Resource
	private QueryAccessDAO queryAccess;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public DayBalance queryPingtaimoney(String replaytime) {
		Map<String,Object> map = new HashMap<String, Object>();
		//返回指定时间-1天的时间
		String yesterdaydate = this.currentTimeDown(replaytime);
		map.put("startTime", DateUtil.startOfTodDay(replaytime));
		map.put("endTime", DateUtil.endOfTodDay(replaytime));
		map.put("ystartTime", DateUtil.startOfTodDay(yesterdaydate));
		map.put("yendTime", DateUtil.endOfTodDay(yesterdaydate));
		DayBalance balance = queryAccess.queryPingtaimoney(map);
		return balance;
	}

	@Override
	public List<DayBalance> queryQiyemoney(Map<String,Object> map) {
		Map<String,Object> nmap = new HashMap<String, Object>();
		String yesterdaydate = this.currentTimeDown((String)map.get("replaytime"));
		nmap.put("startTime", DateUtil.startOfTodDay((String) map.get("replaytime")));
		nmap.put("endTime", DateUtil.endOfTodDay((String) map.get("replaytime")));
		nmap.put("ystartTime", DateUtil.startOfTodDay(yesterdaydate));
		nmap.put("yendTime", DateUtil.endOfTodDay(yesterdaydate));
		nmap.put("startNum", map.get("startNum"));
		nmap.put("endNum", map.get("endNum"));
		nmap.put("username", map.get("username"));
		nmap.put("companyname", map.get("companyname"));
		List<DayBalance> balancelist = queryAccess.queryQiyemoney(nmap);
		return balancelist;
	}

	/**
	 * 查询总记录数
	 */
	@Override
	public int queryTotalRecord(String username, String companyname,
			String replaytime) {
		Date starttime = new Date();
		Date endtime = new Date();
		if(replaytime != null && !replaytime.equals("")){
			String[] str = replaytime.split(" ");
			starttime = DateUtil.startOfTodDay(str[0]);
			endtime = DateUtil.endOfTodDay(str[0]);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("username",username);
		map.put("companyname",companyname);
		map.put("starttime",starttime);
		map.put("endtime",endtime);
		int i = queryAccess.queryTotalRecord(map);
		return i;
	}

	@Override
	public void insertDayBalance(Map<String, Object> map) {
		queryAccess.insertDayBalance(map);
	}

	/**
	 * 查询所有会员信息
	 * */
	@Override
	public List<UserAccount> queryUserAccount(Map<String, Object> map) {
		return queryAccess.queryUserAccount(map);
	}

	@Override
	public DayBalance queryDongJiePingTai(String replaytime) {
		Map<String,Object> map = new HashMap<String, Object>();
		//返回指定时间-1天的时间
		String yesterdaydate = this.currentTimeDown(replaytime);
		map.put("startTime", DateUtil.startOfTodDay(replaytime));
		map.put("endTime", DateUtil.endOfTodDay(replaytime));
		map.put("ystartTime", DateUtil.startOfTodDay(yesterdaydate));
		map.put("yendTime", DateUtil.endOfTodDay(yesterdaydate));
		DayBalance balance = queryAccess.queryDongJiePingTai(map);
		return balance;
	}

	@Override
	public List<DayBalance> queryDongJieQiYe(Map<String,Object> map) {
		Map<String,Object> nmap = new HashMap<String, Object>();
		String yesterdaydate = this.currentTimeDown((String)map.get("replaytime"));
		nmap.put("startTime", DateUtil.startOfTodDay((String) map.get("replaytime")));
		nmap.put("endTime", DateUtil.endOfTodDay((String) map.get("replaytime")));
		nmap.put("ystartTime", DateUtil.startOfTodDay(yesterdaydate));
		nmap.put("yendTime", DateUtil.endOfTodDay(yesterdaydate));
		nmap.put("startNum", map.get("startNum"));
		nmap.put("endNum", map.get("endNum"));
		nmap.put("username", map.get("username"));
		nmap.put("companyname", map.get("companyname"));
		List<DayBalance> balance = queryAccess.queryDongJieQiYe(nmap);
		return balance;
	}

	@Override
	public DayBalance queryYuEPingTai(String replaytime) {
		Map<String,Object> map = new HashMap<String, Object>();
		//返回指定时间-1天的时间
		String yesterdaydate = this.currentTimeDown(replaytime);
		map.put("startTime", DateUtil.startOfTodDay(replaytime));
		map.put("endTime", DateUtil.endOfTodDay(replaytime));
		map.put("ystartTime", DateUtil.startOfTodDay(yesterdaydate));
		map.put("yendTime", DateUtil.endOfTodDay(yesterdaydate));
		DayBalance balance = queryAccess.queryYuEPingTai(map);
		return balance;
	}

	@Override
	public List<DayBalance> queryYuEQiYe(Map<String,Object> map) {
		Map<String,Object> nmap = new HashMap<String, Object>();
		String yesterdaydate = this.currentTimeDown((String)map.get("replaytime"));
		nmap.put("startTime", DateUtil.startOfTodDay((String) map.get("replaytime")));
		nmap.put("endTime", DateUtil.endOfTodDay((String) map.get("replaytime")));
		nmap.put("ystartTime", DateUtil.startOfTodDay(yesterdaydate));
		nmap.put("yendTime", DateUtil.endOfTodDay(yesterdaydate));
		nmap.put("startNum", map.get("startNum"));
		nmap.put("endNum", map.get("endNum"));
		nmap.put("username", map.get("username"));
		nmap.put("companyname", map.get("companyname"));
		List<DayBalance> balance = queryAccess.queryYuEQiYe(nmap);
		return balance;
	}
	
	//返回指定时间-1天的时间
	public String currentTimeDown(String date){
		String datedown = "";
		try {
			Calendar calendar = Calendar.getInstance();  //得到日历
			calendar.setTime(sdf.parse(date));//把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
			Date yesterday = calendar.getTime();   //得到前一天的时间
			datedown = sdf.format(yesterday);    //格式化前一天
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return datedown;
	}
}