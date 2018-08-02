package com.ruoyi.framework.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.SerializeUtil;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
@Scope("singleton")
public class ShardingRedisAPI {
	
	private static final Log logger = LogFactory.getLog(ShardingRedisAPI.class); 
	
	
	 @Value("${sharded.redis.maxTotal}")
	    private Integer maxTotal;
	    
	    @Value("${sharded.redis.maxIdle}")
	    private Integer maxIdle;
	    
	    @Value("${sharded.redis.maxWaitMillis}")
	    private Integer maxWaitMillis;
	    @Value("${sharded.redis.timeout}")
	    private Integer timeout;
	    
	    @Value("${sharded.redis.testOnBorrow}")
	    private String testOnBorrow;
	    
	    @Value("${sharded.redis0.host}")
	    private String host0;
	    @Value("${sharded.redis0.port}")
	    private Integer port0;

	    @Value("${sharded.redis1.host}")
	    private String host1;
	    @Value("${sharded.redis1.port}")
	    private Integer port1;
	    
	    @Value("${sharded.redis2.host}")
	    private String host2;
	    @Value("${sharded.redis2.port}")
	    private Integer port2;

	    @Value("${sharded.redis3.host}")
	    private String host3;
	    @Value("${sharded.redis3.port}")
	    private Integer port3;

		private static final String DEFAULT_SESSION_KEY_PREFIX = "shirosession:";

		private String keyPrefix = DEFAULT_SESSION_KEY_PREFIX;

		private long expireTime = 120000;

	    private ShardedJedisPool pool = null;
	    
		
	    private List<JedisShardInfo> getJedisShardInfos() {
	        final List<JedisShardInfo> jedisShardInfos = new ArrayList<JedisShardInfo>();

	        if (StringUtils.isNotBlank(host0) && port0 > 0) {
	            jedisShardInfos.add(new JedisShardInfo(host0, port0, timeout));
	        }
	        if (StringUtils.isNotBlank(host1) && port1 > 0) {
	            jedisShardInfos.add(new JedisShardInfo(host1, port1, timeout));
	        }
	        if (StringUtils.isNotBlank(host2) && port2 > 0) {
	            jedisShardInfos.add(new JedisShardInfo(host2, port2, timeout));
	        }
	        if (StringUtils.isNotBlank(host3) && port3 > 0) {
	            jedisShardInfos.add(new JedisShardInfo(host3, port3, timeout));
	        }
	        return jedisShardInfos;
	    }
	    
	    @PostConstruct
	    public void init() {
	        if(pool == null) {
	        	logger.info("init");
		        final JedisPoolConfig config = new JedisPoolConfig();
		        config.setMaxIdle(maxIdle);
		        config.setMaxTotal(maxTotal);
		        config.setMaxWaitMillis(maxWaitMillis);
		        config.setTestOnBorrow(Boolean.parseBoolean(testOnBorrow));
		        pool = new ShardedJedisPool(config, getJedisShardInfos());
	        }
	    }
	    
	    public void destroy() {
	        logger.info("destroy");
	        pool.destroy();
	        pool = null;
	    }
	    
	    /**
	     * 构建redis连接池
	     * @return JedisPool
	     */
	    public ShardedJedisPool getPool() {
	        return pool;
	    }
	    
	    
	    
	    
	    public Boolean set(String key, Object obj, Long expireTime) {
	    	ShardedJedisPool pool = null;
	        ShardedJedis jedis = null;
	        try {
	            pool = getPool();
	            jedis = pool.getResource();
	            jedis.set(SerializeUtil.toByteKey(key), SerializeUtil.toByteArray(obj));
	            if(expireTime > 0) {
	            	jedis.expire(key, expireTime.intValue());
	            }
	        } catch (final Exception e) {
	        	logger.error("set redis值出错 : " + e.getMessage());
	        } finally {
	        	closeRedis(pool, jedis);
	        }
	        return true;
	    }
	   
	    
	    /**
	     * 从redis得到值, 二进制
	     * @param key
	     * @return
	     */
	    public Object get(String key) {
	    	byte[] bytes = null;
			ShardedJedisPool pool = null;
			ShardedJedis jedis = null;
			try {
				pool = getPool();
				jedis = pool.getResource();
				bytes = jedis.get(SerializeUtil.toByteKey(key));
			} catch (final Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				closeRedis(pool, jedis);
			}
			return SerializeUtil.toObject(bytes);
	    }
	    
	    
	    public Boolean delete(String key) {
	        ShardedJedisPool pool = null;
	        ShardedJedis jedis = null;
	        try {
	            pool = getPool();
	            jedis = pool.getResource();
	            jedis.del(SerializeUtil.toByteKey(key));
	        } catch (final Exception e) {
	            logger.error(e.getMessage(), e);
	        } finally {
	        	closeRedis(pool, jedis);
	        }
	        return true;
	    }
	    
	    
	    /*
	     * 关闭连接池
	     */
	    private void closeRedis(ShardedJedisPool pool, ShardedJedis redis) {
	    	if (pool != null && redis != null) {
	    		redis.close();
	    	}
	    }
	    
	    

}
