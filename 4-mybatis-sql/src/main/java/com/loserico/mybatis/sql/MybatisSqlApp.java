package com.loserico.mybatis.sql;

import com.loserico.mybatis.sql.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Copyright: (C), 2021-01-17 14:47
 * <p>
 * <p>
 * Company: Information & Data Security Solutions Co., Ltd.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Slf4j
public class MybatisSqlApp {
	
	public static void main(String[] args) {
		String resource = "mybatis-config.xml";
		try {
			//将XML配置文件构建为Configuration配置类
			Reader reader = Resources.getResourceAsReader(resource);
			//通过加载配置文件流构建一个SqlSessionFactory DefaultSqlSessionFactory
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			//数据源执行器 DefaultSqlSession
			SqlSession sqlSession = sqlSessionFactory.openSession();
			try {
				log.info("执行查询");
				List<String> usernames = new ArrayList<>();
				usernames.add("ricoyu");
				usernames.add("tom");
				User user = sqlSession.selectOne("com.loserico.mybatis.sql.mapper.UserMapper.selectByUsernames", usernames);
			} catch (Exception e) {
				log.error("", e);
			} finally {
				sqlSession.close();
			}
		} catch (IOException e) {
			log.error("", e);
		}
	}
}
