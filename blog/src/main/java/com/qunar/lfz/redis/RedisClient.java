package com.qunar.lfz.redis;

import com.qunar.lfz.assist.StringAssist;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Component
@Slf4j
public class RedisClient {
    private static final int LOCK_SECOND = 60;
    private static final String UNLOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    private static final String LOCK_SUCC = "OK";
    private static final Long UNLOCK_SUCC = 1L;

    @Resource(name = "jedisSinglePool")
    private JedisPool pool;

    public void setEx(byte[] key, byte[] value, int expire) {
        if (ArrayUtils.isEmpty(key) || ArrayUtils.isEmpty(value)) {
            return;
        }
        try (Jedis jedis = pool.getResource()) {
            jedis.setex(key, expire, value);
        } catch (Exception e) {
            log.error("setEx error, key:{}", new String(key), e);
        }
    }

    public void setEx(String key, String value, int expire) {
        if (StringUtils.isAnyBlank(key, value)) {
            return;
        }
        try (Jedis jedis = pool.getResource()) {
            jedis.setex(key, expire, value);
        } catch (Exception e) {
            log.error("setEx error, key:{}, value:{}", key, value, e);
        }
    }

    public byte[] get(byte[] keyByte) {
        if (ArrayUtils.isEmpty(keyByte)) {
            return null;
        }
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(keyByte);
        } catch (Exception e) {
            log.error("get error, key:{}", new String(keyByte), e);
            return null;
        }
    }

    public String get(String key) {
        if (StringUtils.isBlank(key)) {
            return StringUtils.EMPTY;
        }
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(key);
        } catch (Exception e) {
            log.error("get error, key:{}", key, e);
            return null;
        }
    }

    public void delete(byte[] keyByte) {
        if (ArrayUtils.isEmpty(keyByte)) {
            return;
        }
        try (Jedis jedis = pool.getResource()) {
            jedis.del(keyByte);
        } catch (Exception e) {
            log.error("delete error, key:{}", new String(keyByte), e);
        }
    }

    public void delete(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        try (Jedis jedis = pool.getResource()) {
            jedis.del(key);
        } catch (Exception e) {
            log.error("delete error, key:{}", key, e);
        }
    }

    //尽量避免使用，性能有问题
    public Set<byte[]> keys(byte[] keyPatternByte) {
        if (ArrayUtils.isEmpty(keyPatternByte)) {
            return Collections.emptySet();
        }
        try (Jedis jedis = pool.getResource()) {
            return jedis.keys(keyPatternByte);
        } catch (Exception e) {
            log.error("delete error, key:{}", new String(keyPatternByte), e);
            return Collections.emptySet();
        }
    }

    // 使用redis实现的分布式锁，真正想要分布式锁还是用zk做好，redis的实现还是有风险，简单用用还可以
    public String lock(String key) {
        if (StringUtils.isBlank(key)) {
            return StringUtils.EMPTY;
        }
        key = StringAssist.joinUnderline(RedisKey.DISTRIBUTED_LOCK_PRE, key);
        String uuid = UUID.randomUUID().toString();
        try (Jedis jedis = pool.getResource()) {
            String setRet = jedis.set(key, uuid, "NX", "EX", LOCK_SECOND);
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
        key = StringAssist.joinUnderline(RedisKey.DISTRIBUTED_LOCK_PRE, key);
        try (Jedis jedis = pool.getResource()) {
            Object result = jedis.eval(UNLOCK_SCRIPT, Collections.singletonList(key), Collections.singletonList(uuid));
            if (UNLOCK_SUCC.equals(result)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("unlock error, key:{}", key, e);
            return false;
        }
    }
}
