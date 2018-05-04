package com.qunar.lfz.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;

@Component
@Slf4j
public class RedisClient {
    @Resource(name = "jedisSinglePool")
    private JedisPool pool;

    public void setEx(byte[] key, byte[] value, int expire) {
        if (ArrayUtils.isEmpty(key) || ArrayUtils.isEmpty(value)) {
            return;
        }
        try (Jedis jedis = pool.getResource()) {
            jedis.setex(key, expire, value);
        } catch (Exception e) {
            log.error("setEx error,key:{}", new String(key), e);
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
}
