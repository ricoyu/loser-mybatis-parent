package com.loserico.mybatis.sql;

import com.loserico.mybatis.sql.entity.User;
import com.loserico.mybatis.sql.mapper.UserMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * <p>
 * Copyright: (C), 2021-01-20 21:00
 * <p>
 * <p>
 * Company: Information & Data Security Solutions Co., Ltd.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Slf4j
public class SqlTest {
	
	private SqlSession sqlSession;
	private UserMapper userMapper;
	
	@Before
	@SneakyThrows
	public void setup() {
		String resource = "mybatis-config.xml";
		//将XML配置文件构建为Configuration配置类
		Reader reader = Resources.getResourceAsReader(resource);
		//通过加载配置文件流构建一个SqlSessionFactory DefaultSqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		//数据源执行器 DefaultSqlSession
		sqlSession = sqlSessionFactory.openSession();
		userMapper = sqlSession.getMapper(UserMapper.class);
	}
	
	@After
	public void tearDown() {
		if (sqlSession != null) {
			sqlSession.close();
			sqlSession = null;
		}
	}
	
	@Test
	public void testSelectById() {
		User user = userMapper.selectById(1L);
		System.out.println(user);
	}
	
	@Test
	public void testMybatisForEach() {
		List<String> usernames = asList("ricoyu", "jim");
		
		List<User> users = userMapper.selectByUsernames(usernames);
		users.forEach(System.out::println);
	}
}
