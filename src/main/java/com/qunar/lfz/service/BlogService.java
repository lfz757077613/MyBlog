package com.qunar.lfz.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.qunar.lfz.dao.BlogDao;
import com.qunar.lfz.model.po.BlogPo;
import com.qunar.lfz.model.vo.BlogDesc;
import com.qunar.lfz.model.vo.BlogView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class BlogService {

    @Resource
    private BlogDao blogDao;

    public boolean addBlog(BlogPo blogPo) {
        try {
            blogDao.addBlog(blogPo);
            return true;
        } catch (Exception e) {
            log.error("add blogPo error, blogPo:{}", JSON.toJSONString(blogPo), e);
            return false;
        }
    }

    public List<BlogDesc> queryAllBlogDesc() {
        try {
            return Lists.transform(blogDao.queryAllBlog(), new Function<BlogPo, BlogDesc>() {
                @Override
                public BlogDesc apply(BlogPo input) {
                    return new BlogDesc(input);
                }
            });
        } catch (Exception e) {
            log.error("query all blogPo error", e);
            return Collections.emptyList();
        }
    }

    public BlogView queryBlogShowById(int id) {
        try {
            return new BlogView(blogDao.queryBlogById(id));
        } catch (Exception e) {
            log.error("query blogPo by id error, id:{}", id, e);
            return null;
        }
    }
}
