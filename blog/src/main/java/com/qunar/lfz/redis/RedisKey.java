package com.qunar.lfz.redis;

import com.qunar.lfz.assist.StringAssist;
import org.apache.commons.lang3.StringUtils;

public final class  RedisKey {
    public static final String SESSION_PRE = "session_pre";
    public static final String ALL_BLOG_DESC = "all_blog_desc";
    public static final String BLOG_SHOW_PRE = "blog_show_pre";
    public static final String DISTRIBUTED_LOCK_PRE = "distributed_lock_pre";

    public static String getKey(String keyPre, String key) {
        if (StringUtils.isAnyBlank(keyPre, key)) {
            throw new NullPointerException("keyPre or key is null");
        }
        return StringAssist.joinUnderline(keyPre, key);
    }
    public static byte[] getKeyByte(String keyPre, String key) {
        String keyStr = getKey(keyPre, key);
        if (StringUtils.isBlank(keyPre)) {
            return null;
        }
        return keyStr.getBytes();
    }
}
