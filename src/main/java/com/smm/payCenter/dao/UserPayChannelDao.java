package com.smm.payCenter.dao;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.UserPayChannel;

public interface UserPayChannelDao {
	List<UserPayChannel> getUserPayChannel(Map<String, Object> map);

	List<UserPayChannel> getUserPayChannelByUserId(Map<String, Object> map);

}
