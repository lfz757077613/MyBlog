package com.qunar.lfz.service;

import com.alibaba.fastjson.JSON;
import com.qunar.lfz.dao.BlogDao;
import com.qunar.lfz.model.Blog;
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

    public void addBlog(Blog blog) {
        try {
            blogDao.addBlog(blog);
        } catch (Exception e) {
            log.error("add blog error,blog:{}", JSON.toJSONString(blog), e);
        }
    }

    public List<Blog> queryAllBlog() {
        try {
            return blogDao.queryAllBlog();
        } catch (Exception e) {
            log.error("query all blog error", e);
        }
        return Collections.emptyList();
    }

}
