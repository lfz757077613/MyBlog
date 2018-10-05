package com.lfz.dao;

import com.lfz.model.po.BlogPo;

import java.util.List;

public interface BlogDao {

    void addBlog(BlogPo blogPo);

    List<BlogPo> queryAllBlog();

    BlogPo queryBlogById(int id);

    void delBlogByIds(int[] ids);

    void updateBlog(BlogPo blogPo);

}
