package com.loserico.mybatis.mapper;

import com.loserico.mybatis.entity.User;

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
}
