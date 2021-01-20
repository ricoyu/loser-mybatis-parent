package com.loserico.mybatis.cache.cacheImpl;

import com.loserico.cache.JedisUtils;
import com.loserico.common.lang.utils.FstUtils;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheException;

import static com.loserico.cache.utils.ByteUtils.toBytes;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 基于Redis的一/二级缓存
 * <p>
 * Copyright: (C), 2021-01-17 14:57
 * <p>
 * <p>
 * Company: Information & Data Security Solutions Co., Ltd.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class RedisCache implements Cache {
	
	private static final String CACHE_KEY = "mybatis-cache";
	private static final byte[] CACHE_KEY_BYTES = CACHE_KEY.getBytes(UTF_8);
	
	/**
	 * Mybatis 缓存的对象是一个List, 这里保存一下List里面的element对象类型
	 */
	private Class type;
	
	private String id;
	
	/**
	 * Mybatis的Cache实现需要有一个带id参数的构造函数
	 * id实际传入的就是Mapper的全限定类名
	 *
	 * @param id
	 */
	public RedisCache(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return this.id;
	}
	
	@Override
	public void putObject(Object key, Object value) {
		if (value == null) {
			return;
		}
		
		JedisUtils.HASH.hset(CACHE_KEY_BYTES, toBytes(key), FstUtils.serialize(value));
	}
	
	@Override
	public Object getObject(Object key) {
		byte[] bytes = JedisUtils.HASH.hget(CACHE_KEY_BYTES, toBytes(key));
		return FstUtils.deserialize(bytes);
	}
	
	@Override
	public Object removeObject(Object key) {
		return JedisUtils.HASH.hdelGet(CACHE_KEY, key);
	}
	
	@Override
	public void clear() {
		JedisUtils.del(CACHE_KEY_BYTES);
	}
	
	@Override
	public int getSize() {
		Long len = JedisUtils.HASH.hlen(CACHE_KEY);
		return len == null ? 0 : len.intValue();
	}
	
	@Override
	public boolean equals(Object o) {
		if (getId() == null) {
			throw new CacheException("Cache instances require an ID.");
		}
		if (this == o) {
			return true;
		}
		if (!(o instanceof Cache)) {
			return false;
		}
		
		Cache otherCache = (Cache) o;
		return getId().equals(otherCache.getId());
	}
	
}
