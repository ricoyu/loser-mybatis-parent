package com.loserico.mybatis.jdbc;

import com.loserico.common.lang.utils.DateUtils;
import com.loserico.mybatis.eden.entity.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * Copyright: (C), 2021-01-09 14:15
 * <p>
 * <p>
 * Company: Information & Data Security Solutions Co., Ltd.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class JdbcTest {
	
	@Test
	public void testJdbcOps() {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// 1 加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 2 创建连接
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis_example", "mybatis", "123456");
			String sql = "select id,user_name,create_time from t_user where id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			
			//执行查询
			//ResultSet rs = stmt.executeQuery();
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			
			rs.next();
			User user = new User();
			user.setId(rs.getLong("id"));
			user.setUserName(rs.getString("user_name"));
			user.setCreateTime(DateUtils.toDate(rs.getDate("create_time")));
			
			System.out.println(user);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
