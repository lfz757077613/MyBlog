package com.qunar.lfz.shiro;

import com.google.common.collect.Sets;
import com.qunar.lfz.redis.RedisClusterClient;
import com.qunar.lfz.redis.RedisKey;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public class MyRedisSessionDao extends AbstractSessionDAO {
    //redis中session过期时间
    private static final int SESSION_EXPIRE = 60 * 30;
//    @Resource
//    private RedisClient client;
    @Resource(name = "redisClusterClient")
    private RedisClusterClient client;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        byte[] keyByte = RedisKey.getKeyByte(RedisKey.SESSION_PRE, sessionId.toString());
        byte[] sessionByte = SerializationUtils.serialize(session);
        client.setEx(keyByte, sessionByte, SESSION_EXPIRE);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            return null;
        }
        byte[] keyByte = RedisKey.getKeyByte(RedisKey.SESSION_PRE, sessionId.toString());
        byte[] sessionByte = client.get(keyByte);
        return (Session) SerializationUtils.deserialize(sessionByte);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        byte[] keyByte = RedisKey.getKeyByte(RedisKey.SESSION_PRE, session.getId().toString());
        byte[] sessionByte = SerializationUtils.serialize(session);
        client.setEx(keyByte, sessionByte, SESSION_EXPIRE);
    }

    @Override
    public void delete(Session session) {
        byte[] keyByte = RedisKey.getKeyByte(RedisKey.SESSION_PRE, session.getId().toString());
        client.delete(keyByte);
    }

    // 关闭了shiro自动检查session，所以该方法用不到，redis的keys命令也确实不应该用在生产环境，性能太低
    @Override
    public Collection<Session> getActiveSessions() {
        Set<byte[]> keyByteSet = client.keys((RedisKey.SESSION_PRE + "*").getBytes());
        Set<Session> sessionSet = Sets.newHashSet();
        for (byte[] keyByte : keyByteSet) {
            byte[] sessionByte = client.get(keyByte);
            Session session = (Session) SerializationUtils.deserialize(sessionByte);
            if (session != null) {
                sessionSet.add(session);
            }
        }
        return sessionSet;
    }
}
