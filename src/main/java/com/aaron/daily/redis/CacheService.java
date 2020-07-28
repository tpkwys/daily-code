package com.aaron.daily.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author yuantongqin
 * desc:
 * 2020-05-26
 */
@Service
public class CacheService {

    private static final String PREFIX = "ffc:budget:";
    private static final Long SUCCESS = 1L;
    private static Logger logger = LoggerFactory.getLogger(CacheService.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 获取分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 单位秒
     * @param waitTimeout 单位毫秒
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, String requestId, int expireTime,long waitTimeout) {

        lockKey = wrapperPrefix(lockKey);
        // 当前时间
        long nanoTime = System.nanoTime();
        try{
            String script = "if redis.call('setNx',KEYS[1],ARGV[1]) then if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end end";

            logger.info("开始获取分布式锁-key[{}]",lockKey);
            int count = 0;
            do{
                RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);

                logger.debug("尝试获取分布式锁-key[{}]requestId[{}]count[{}]",lockKey,requestId,count);
                Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey),requestId,expireTime);

                if(SUCCESS.equals(result)) {
                    logger.debug("尝试获取分布式锁-key[{}]成功",lockKey);
                    return true;
                }

                Thread.sleep(500L);//休眠500毫秒
                count++;
            }while ((System.nanoTime() - nanoTime) < TimeUnit.MILLISECONDS.toNanos(waitTimeout));

        }catch(Exception e){
            logger.error("尝试获取分布式锁-key["+lockKey+"]异常",e);
        }

        logger.warn("获取分布式锁-key[{}]失败",lockKey);

        return false;
    }

    /**
     * 释放锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseLock(String lockKey, String requestId) {

        lockKey = wrapperPrefix(lockKey);
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);

        Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), requestId);
        if (SUCCESS.equals(result)) {
            logger.info("释放分布式锁-key[{}]成功",lockKey);
            return true;
        }

        logger.error("释放分布式锁-key[{}]失败",lockKey);
        return false;

    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(wrapperPrefix(key));
    }

    /**
     * 包装前缀 reconciliation
     * @param key
     * @return
     */
    private String wrapperPrefix(String key){
        return PREFIX + key;
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(wrapperPrefix(key), value);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(wrapperPrefix(key), value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public void del(String key) {
        redisTemplate.delete(wrapperPrefix(key));
    }

}
