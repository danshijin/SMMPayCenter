package com.smm.payCenter.bo.base;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.RoleRelation;

public interface PrivilegeBo {

	List<RoleRelation> menuData(Map<String, Object> param);

}
