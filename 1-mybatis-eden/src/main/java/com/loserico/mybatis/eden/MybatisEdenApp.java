package com.loserico.mybatis.eden;

import com.loserico.mybatis.eden.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * https://mybatis.org/mybatis-3/configuration.html
 * <p>
 * Copyright: (C), 2021-01-09 20:36
 * <p>
 * <p>
 * Company: Information & Data Security Solutions Co., Ltd.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Slf4j
public class MybatisEdenApp {
	
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
				//执行查询
				User user = sqlSession.selectOne("com.loserico.mybatis.mapper.UserMapper.selectById", 1);
				System.out.println(user.getUserName());
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
