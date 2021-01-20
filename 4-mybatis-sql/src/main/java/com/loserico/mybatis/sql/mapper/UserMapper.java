package com.loserico.mybatis.sql.mapper;

import com.loserico.mybatis.sql.entity.User;

import java.util.List;

/**
 *  
 * <p>
 * Copyright: Copyright (c) 2021-01-09 20:48
 * <p>
 * Company: Information & Data Security Solutions Co., Ltd.
 * <p>
 
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
public interface UserMapper {
    
	User selectById(Long id);
	
	List<User> selectByUsernames(List<String> usernames);
}
