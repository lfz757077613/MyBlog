package com.lfz.service;

import com.alibaba.fastjson.JSON;
import com.lfz.dao.BlogDao;
import com.lfz.model.param.AddBlogParam;
import com.lfz.model.param.ModifyBlogParam;
import com.lfz.model.po.BlogPo;
import com.lfz.model.vo.BlogDesc;
import com.lfz.model.vo.BlogView;
import com.lfz.redis.MyRedisClient;
import com.lfz.redis.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class BlogService {

    @Resource
    private BlogDao blogDao;

    @Resource
    private MyRedisClient redisClient;

    public boolean addBlog(AddBlogParam param) {
        try {
            if (param == null
                    || StringUtils.isAnyBlank(param.getTitle(), param.getRealContent(), param.getShowContent())) {
                return false;
            }
            BlogPo po = new BlogPo();
            BeanUtils.copyProperties(param, po);
            blogDao.addBlog(po);
            //插入新博客后，删除博客介绍缓存，强制下次查询走库
            redisClient.delete(RedisKey.ALL_BLOG_DESC);
            return true;
        } catch (Exception e) {
            log.error("add blogPo error, param:{}", JSON.toJSONString(param), e);
            return false;
        }
    }

    public boolean modifyBlog(ModifyBlogParam param) {
        try {
            if (param == null
                    || param.getId() == null
                    || StringUtils.isAnyBlank(param.getTitle(), param.getRealContent(), param.getShowContent())) {
                return false;
            }
            BlogPo po = new BlogPo();
            BeanUtils.copyProperties(param, po);
            blogDao.updateBlog(po);
            //更新博客后，删除博客缓存，强制下次查询走库
            redisClient.delete(RedisKey.ALL_BLOG_DESC);
            redisClient.delete(RedisKey.getKey(RedisKey.BLOG_SHOW_PRE, String.valueOf(po.getId())));
            return true;
        } catch (Exception e) {
            log.error("update blogPo error, blogPo:{}", JSON.toJSONString(param), e);
            return false;
        }
    }

    public List<BlogDesc> queryAllBlogDesc() {
        try {
            String allBlogDescJson = redisClient.get(RedisKey.ALL_BLOG_DESC);
            if (StringUtils.isNotBlank(allBlogDescJson)) {
                return JSON.parseArray(allBlogDescJson, BlogDesc.class);
            }
            List<BlogDesc> blogDescList = blogDao.queryAllBlog()
                    .stream()
                    .map(blogPo -> {
                        BlogDesc blogDesc = new BlogDesc();
                        BeanUtils.copyProperties(blogPo, blogDesc);
                        blogDesc.setCreateTime(blogPo.getCreateTime().format(DateTimeFormatter.ISO_LOCAL_DATE));
                        blogDesc.setUpdateTime(blogPo.getUpdateTime().format(DateTimeFormatter.ISO_LOCAL_DATE));
                        return blogDesc;
                    })
                    .collect(toList());
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
            if (blogPo == null) {
                return null;
            }
            BlogView blogView = new BlogView();
            BeanUtils.copyProperties(blogPo, blogView);
            blogView.setCreateTime(blogPo.getCreateTime().format(DateTimeFormatter.ISO_LOCAL_DATE));
            redisClient.setEx(
                    RedisKey.getKey(RedisKey.BLOG_SHOW_PRE, String.valueOf(id)),
                    JSON.toJSONString(blogView),
                    60 * 60);
            return blogView;
        } catch (Exception e) {
            log.error("query blogPo by id error, id:{}", id, e);
            return null;
        }
    }

    public boolean delMultiBlogById(int[] ids) {
        try {
            if (ArrayUtils.isEmpty(ids)) {
                return true;
            }
            Arrays.stream(ids)
                    .forEach(id -> redisClient.delete(RedisKey.getKey(RedisKey.BLOG_SHOW_PRE, String.valueOf(id))));
            redisClient.delete(RedisKey.ALL_BLOG_DESC);
            blogDao.delBlogByIds(ids);
            return true;
        } catch (Exception e) {
            log.error("delete multi blog by id error, ids:{}", JSON.toJSONString(ids), e);
            return false;
        }
    }
}
