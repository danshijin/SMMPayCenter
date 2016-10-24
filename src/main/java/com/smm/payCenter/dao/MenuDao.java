package com.smm.payCenter.dao;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.Menu;
import com.smm.payCenter.domain.Role;
import com.smm.payCenter.domain.TreeNodeVO;

public interface MenuDao {

	List<Menu> queryParentMenu(Map<String, Object> param);

	List<Menu> queryChildMenu(Map<String, Object> map);
	
	List<TreeNodeVO> getTreeNode(Map<String, Object> param);


	void addRoleRelation(Map<String, Object> param);

}
