package com.qunar.lfz.dao;

import com.qunar.lfz.model.Blog;

import java.util.List;

public interface BlogDao {

    void addBlog(Blog blog);

    List<Blog> queryAllBlog();

    Blog queryBlogById(int id);

    void deleteBlogById(int id);

    void updateBlog(Blog blog);

}
