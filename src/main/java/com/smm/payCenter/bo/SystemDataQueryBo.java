package com.smm.payCenter.bo;

import java.util.List;
import java.util.Map;

/**
 * 类SystemDataQueryBo.java的实现描述：TODO 类实现描述
 * 
 * @author hanfeihu 2015年11月13日 上午9:17:26
 */
public interface SystemDataQueryBo {

    List<com.smm.payCenter.domain.SystemData> querySystemDataList(Map<String, String> paymap);

    Integer querySystemDataListTotalRecords(Map<String, String> paymap);

}
