package com.ruoyi.framework.config;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.ruoyi.common.utils.SerializeUtil;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

/**
 * 参考http://www.mamicode.com/info-detail-2227097.html
 * @author liusheng
 *
 */
@Component
public class RedisSessionDAO extends AbstractSessionDAO {
	private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

	@Autowired
	private ShardingRedisAPI shardingRedisAPI;
	
	private static final String DEFAULT_SESSION_KEY_PREFIX = "shirosession:";

	private String keyPrefix = DEFAULT_SESSION_KEY_PREFIX;

	private long expireTime = 120000;

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
	
	public RedisSessionDAO() {
		super();
	}

	public RedisSessionDAO(long expireTime) {
		super();
		this.expireTime = expireTime;
	}

	private String getKey(Session session) {
		return this.keyPrefix + String.valueOf(session.getId());
	}

	private String getSessionIdKey(String sessionId) {
		return this.keyPrefix + String.valueOf(sessionId);
	}

	@Override // 删除session
	public void delete(Session session) {
		System.out.println("===============delete================");
		if (null == session) {
			return;
		}
		shardingRedisAPI.delete(getKey(session));
	}

	@Override
	// 获取活跃的session，可以用来统计在线人数，如果要实现这个功能，可以在将session加入redis时指定一个session前缀，统计的时候则使用keys("session-prefix*")的方式来模糊查找redis中所有的session集合
	public Collection<Session> getActiveSessions() {
		 System.out.println("==============getActiveSessions=================");
//		 return redisTemplate.keys("*");
		return CollectionUtils.EMPTY_COLLECTION;
	}

	
	@Override // 加入session
	protected Serializable doCreate(Session session) {
		System.out.println("===============doCreate================");
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);

//		redisTemplate.opsForValue().set(getKey(session), SerializeUtil.toByteArray(session), expireTime, TimeUnit.MILLISECONDS);
//		redisTemplate.opsForValue().set(getKey(session), session, expireTime, TimeUnit.MILLISECONDS);
		shardingRedisAPI.set(getKey(session), session, expireTime);
		return sessionId;
	}
	
	@Override // 更新session
	public void update(Session session) throws UnknownSessionException {
		System.out.println("===============update================");
		if (session == null || session.getId() == null) {
			return;
		}
		session.setTimeout(expireTime);
		shardingRedisAPI.set(getKey(session), session, expireTime);
	}
	

	@Override // 读取session
	protected Session doReadSession(Serializable sessionId) {
		System.out.println("==============doReadSession================="+sessionId);
		if (sessionId == null) {
			return null;
		}
//		System.out.println("sessionId:"+getSessionIdKey(String.valueOf(sessionId)));
//		System.out.println("redis String:"+template.opsForValue().get(getSessionIdKey(String.valueOf(sessionId))));
//		System.out.println(redisTemplate.opsForValue().get(getSessionIdKey(String.valueOf(sessionId))));
//		return (Session) redisTemplate.opsForValue().get(getSessionIdKey(String.valueOf(sessionId)));
//		return (Session) SerializeUtil.toObject((byte[])redisTemplate.opsForValue().get(getSessionIdKey(String.valueOf(sessionId))));
		
		return (Session) shardingRedisAPI.get(getSessionIdKey(String.valueOf(sessionId)));
	}

	
}