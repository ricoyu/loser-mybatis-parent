package com.loserico.mybatis.cache;

import com.loserico.mybatis.cache.entity.*;
import lombok.extern.slf4j.*;
import org.apache.ibatis.io.*;
import org.apache.ibatis.session.*;

import java.io.*;

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
public class MybatisCacheApp {
	
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
				long begin = System.currentTimeMillis();
				for (int i = 0; i < 1000; i++) {
					//执行查询
					User user = sqlSession.selectOne("com.loserico.mybatis.cache.mapper.UserMapper.selectById", 1);
				}
				long end = System.currentTimeMillis();
				System.out.println("查询时间: " + (end - begin) / 1000);
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
