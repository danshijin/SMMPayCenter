package com.smm.payCenter.applicationListener;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.smm.payCenter.dao.FnlRoleRelationDao;

/**
* @author  zhaoyutao
* @version 2015年12月14日 下午4:07:16
* spring初始化完成后将数据加载进程序中
*/

public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {
	@Resource
	FnlRoleRelationDao fnlRoleRelationDao;
	
	private static List<String> menuUrlList;
	
    @Override  
    public void onApplicationEvent(ContextRefreshedEvent event) {  
    	if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大.  
    		menuUrlList = fnlRoleRelationDao.getAllMenuUrl();
    	}  
	}

	/** 获取所有需要权限控制的url
	 * @return
	 */
	public static List<String> getMenuUrlList() {
		return menuUrlList;
	}
}  
