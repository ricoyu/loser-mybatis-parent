package com.loserico.mybatis.plugin.plugins;

import com.loserico.common.lang.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Signature注解指定要拦截的组件是哪个, 拦截的哪个方法(方法名, 参数类型)
 * 
 * <p>
 * Copyright: Copyright (c) 2021-01-31 15:35
 * <p>
 * Company: Information & Data Security Solutions Co., Ltd.
 * <p>
 
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "query", args = {
		MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class
})})
public class ExecutorPlugin implements Interceptor {
	
	private static final Pattern pattern = Pattern.compile(".*\\n*in\\s*\\n*\\s+(\\?){1}");
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile(".*\\n*in\\s*\\n*\\s+(\\?){1}");
		String sql = "select * from t_user where user_name in ?";
		Matcher matcher = pattern.matcher(sql);
		if (matcher.matches()) {
			String group = matcher.group(1);
			int index = matcher.start(1);
			System.out.println(index);
			System.out.println(group);
		}
	}
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		log.info("代理");
		Object[] args = invocation.getArgs();
		MappedStatement ms = (MappedStatement) args[0];
		SqlSource sqlSource = ms.getSqlSource();
		//((RawSqlSource)sqlSource)
		if (sqlSource instanceof RawSqlSource) {
			BoundSql boundSql = ((RawSqlSource) sqlSource).getBoundSql(null);
			String sql = ReflectionUtils.getFieldValue("sql", boundSql);
			
			Matcher matcher = pattern.matcher(sql);
			if (matcher.matches()) {
				List<SqlNode> contents = new ArrayList<>();
				
				String group = matcher.group(1);
				int index = matcher.start(1);
				
				String staticSql = sql.substring(0, index);
				contents.add(new StaticTextSqlNode(staticSql));
				
				ForEachSqlNode forEachSqlNode = new ForEachSqlNode(ms.getConfiguration(), 
						new StaticTextSqlNode("#{usernames}"), 
						"list", 
						"i", 
						"item", 
						"(", 
						")", 
						",");
				contents.add(forEachSqlNode);
				
				if (index < sql.length()) {
					String lastStaticSql = sql.substring(index + 1);
					contents.add(new StaticTextSqlNode(lastStaticSql));
				}
				MixedSqlNode rootSqlNode = new MixedSqlNode(contents);
				
				DynamicSqlSource dynamicSqlSource = new DynamicSqlSource(ms.getConfiguration(), rootSqlNode);
				ReflectionUtils.setField("sqlSource", ms, dynamicSqlSource);
			}
		}
		return invocation.proceed();
	}
	
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}
	
	/**
	 * 加载的时候调用, 设置属性初始化
	 * @param properties
	 */
	@Override
	public void setProperties(Properties properties) {
		log.info("properties {}", properties);
	}
}
