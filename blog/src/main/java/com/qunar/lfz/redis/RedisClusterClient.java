package com.qunar.lfz.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/**
 * Author: fuzhi.lai
 * Date: 2018/7/16 下午6:04
 * Create by Intellij idea
 */
/*
                       _oo0oo_
                      o8888888o
                      88" . "88
                      (| -_- |)
                      0\  =  /0
                    ___/`---'\___
                  .' \\|     |// '.
                 / \\|||  :  |||// \
                / _||||| -:- |||||- \
               |   | \\\  -  /// |   |
               | \_|  ''\---/''  |_/ |
               \  .-\__  '-'  ___/-. /
             ___'. .'  /--.--\  `. .'___
          ."" '<  `.___\_<|>_/___.' >' "".
         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
         \  \ `_.   \_ __\ /__ _/   .-` /  /
     =====`-.____`.___ \_____/___.-`___.-'=====
                       `=---='
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

               佛祖保佑         永无BUG
*/
@Slf4j
@Component("redisClusterClient")
// redis集群client
public final class RedisClusterClient {
    private static final int LOCK_SECOND = 60;
    private static final String UNLOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    private static final String LOCK_SUCC = "OK";
    private static final Long UNLOCK_SUCC = 1L;

    @Resource(name = "jedisCluster")
    private JedisCluster cluster;


    public void setEx(byte[] key, byte[] value, int expire) {
        if (ArrayUtils.isEmpty(key) || ArrayUtils.isEmpty(value)) {
            return;
        }
        try {
            cluster.setex(key, expire, value);
        } catch (Exception e) {
            log.error("setEx error, key:{}", new String(key), e);
        }
    }

    public void setEx(String key, String value, int expire) {
        if (StringUtils.isAnyBlank(key, value)) {
            return;
        }
        try {
            cluster.setex(key, expire, value);
        } catch (Exception e) {
            log.error("setEx error, key:{}, value:{}", key, value, e);
        }
    }

    public byte[] get(byte[] keyByte) {
        if (ArrayUtils.isEmpty(keyByte)) {
            return null;
        }
        try {
            return cluster.get(keyByte);
        } catch (Exception e) {
            log.error("get error, key:{}", new String(keyByte), e);
            return null;
        }
    }

    public String get(String key) {
        if (StringUtils.isBlank(key)) {
            return StringUtils.EMPTY;
        }
        try {
            return cluster.get(key);
        } catch (Exception e) {
            log.error("get error, key:{}", key, e);
            return null;
        }
    }

    public void delete(byte[] keyByte) {
        if (ArrayUtils.isEmpty(keyByte)) {
            return;
        }
        try {
            cluster.del(keyByte);
        } catch (Exception e) {
            log.error("delete error, key:{}", new String(keyByte), e);
        }
    }

    public void delete(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        try {
            cluster.del(key);
        } catch (Exception e) {
            log.error("delete error, key:{}", key, e);
        }
    }

    // redis cluster没有keys方法
    public Set<byte[]> keys(byte[] keyPatternByte) {
        throw new RuntimeException("redis cluster no keys method");
    }

    // 使用redis实现的分布式锁，真正想要分布式锁还是用zk做好，redis的实现还是有风险，简单用用还可以
    public String lock(String key) {
        if (StringUtils.isBlank(key)) {
            return StringUtils.EMPTY;
        }
        key = RedisKey.getKey(RedisKey.DISTRIBUTED_LOCK_PRE, key);
        String uuid = UUID.randomUUID().toString();
        try {
            String setRet = cluster.set(key, uuid, "NX", "EX", LOCK_SECOND);
            if (StringUtils.equals(setRet, LOCK_SUCC)) { // 设置成功
                return uuid;
            }
            // 设置失败
            return StringUtils.EMPTY;
        } catch (Exception e) {
            log.error("lock error, key:{}", key, e);
            return StringUtils.EMPTY;
        }
    }

    // redis分布式锁解锁
    public boolean unlock(String key, String uuid) {
        if (StringUtils.isAnyBlank(key, uuid)) {
            return false;
        }
        key = RedisKey.getKey(RedisKey.DISTRIBUTED_LOCK_PRE, key);
        try {
            Object result = cluster.eval(UNLOCK_SCRIPT, Collections.singletonList(key), Collections.singletonList(uuid));
            if (UNLOCK_SUCC.equals(result)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("unlock error, key:{}, uuid:{}", key, uuid, e);
            return false;
        }
    }
}
