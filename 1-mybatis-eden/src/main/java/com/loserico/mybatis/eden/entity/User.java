package com.loserico.mybatis.eden.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Copyright: (C), 2021-01-09 14:22
 * <p>
 * <p>
 * Company: Information & Data Security Solutions Co., Ltd.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Data
public class User implements Serializable {
	
	private Long id;
	private String userName;
	private Date createTime;
	
	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", userName='" + userName + '\'' +
				", createTime=" + createTime +
				'}';
	}
}
