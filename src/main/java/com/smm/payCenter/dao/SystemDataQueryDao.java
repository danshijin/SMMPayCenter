package com.smm.payCenter.dao;

import java.util.List;
import java.util.Map;

/**
 * @author hanfeihu
 */
public interface SystemDataQueryDao {

    List<com.smm.payCenter.domain.SystemData> querySystemDataList(Map<String, String> paymap);

    Integer querySystemDataListTotalRecords(Map<String, String> paymap);

}
