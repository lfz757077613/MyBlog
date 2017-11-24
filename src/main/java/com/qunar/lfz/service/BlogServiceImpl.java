package com.qunar.lfz.service;

import com.qunar.lfz.dao.BlogDao;
import com.qunar.lfz.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

	@Autowired
	private BlogDao blogDao;
	
	@Override
	public void writeBlog(Blog blog) {
		System.out.println(blog.getArticle());
		blogDao.writeBlog(blog);
	}

	@Override
	public List<Blog> selectAllBlog() {
		List<Blog> blogs = this.blogDao.selectAllBlog();
		return blogs;
	}

	@Override
	public List<Blog> selectBlogById(int blogid) {
		List<Blog> blogs = this.blogDao.selectBlogById(blogid);
		return blogs;
	}

	@Override
	public void deleteBlogById(int blogid) {
		blogDao.deleteBlogById(blogid);
		
	}

}
