package com.loserico.mybatis.plugin.mapper;

import com.loserico.mybatis.plugin.entity.User;

import java.util.List;

/**
 * <p>
 * Copyright: Copyright (c) 2021-01-09 20:48
 * <p>
 * Company: Information & Data Security Solutions Co., Ltd.
 * <p>
 *
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
public interface UserMapper {
	
	List<User> selectByUsernames(List<String> usernames);
	
	List<User> selectIn(List<String> usernames);
}
