package com.qunar.lfz.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.qunar.lfz.dao.BlogDao;
import com.qunar.lfz.model.po.BlogPo;
import com.qunar.lfz.model.vo.BlogDesc;
import com.qunar.lfz.model.vo.BlogView;
import com.qunar.lfz.redis.RedisClient;
import com.qunar.lfz.redis.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class BlogService {

    @Resource
    private BlogDao blogDao;

    @Resource
    private RedisClient redisClient;

    public boolean addBlog(BlogPo blogPo) {
        try {
            blogDao.addBlog(blogPo);
            //插入新博客后，删除博客介绍缓存，强制下次查询走库
            redisClient.delete(RedisKey.ALL_BLOG_DESC);
            return true;
        } catch (Exception e) {
            log.error("add blogPo error, blogPo:{}", JSON.toJSONString(blogPo), e);
            return false;
        }
    }

    public List<BlogDesc> queryAllBlogDesc() {
        try {
            String allBlogDescJson = redisClient.get(RedisKey.ALL_BLOG_DESC);
            if (StringUtils.isNotBlank(allBlogDescJson)) {
                return JSON.parseArray(allBlogDescJson, BlogDesc.class);
            }
            List<BlogDesc> blogDescList = Lists.transform(blogDao.queryAllBlog(), new Function<BlogPo, BlogDesc>() {
                @Override
                public BlogDesc apply(BlogPo input) {
                    return new BlogDesc(input);
                }
            });
            if (CollectionUtils.isNotEmpty(blogDescList)) {
                redisClient.setEx(RedisKey.ALL_BLOG_DESC, JSON.toJSONString(blogDescList), 60 * 60);
            }
            return blogDescList;
        } catch (Exception e) {
            log.error("query all blogPo error", e);
            return Collections.emptyList();
        }
    }

    public BlogView queryBlogShowById(int id) {
        try {
            String blogShowJson = redisClient.get(RedisKey.getKey(RedisKey.BLOG_SHOW_PRE, String.valueOf(id)));
            if (StringUtils.isNotBlank(blogShowJson)) {
                return JSON.parseObject(blogShowJson, BlogView.class);
            }
            BlogPo blogPo = blogDao.queryBlogById(id);
            BlogView blogView = null;
            if (blogPo != null) {
                blogView = new BlogView(blogPo);
                redisClient.setEx(
                        RedisKey.getKey(RedisKey.BLOG_SHOW_PRE, String.valueOf(id)),
                        JSON.toJSONString(blogView),
                        60 * 60);
            }
            return blogView;
        } catch (Exception e) {
            log.error("query blogPo by id error, id:{}", id, e);
            return null;
        }
    }
}
