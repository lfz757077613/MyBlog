package com.qunar.lfz.controller;

import com.qunar.lfz.model.Blog;
import com.qunar.lfz.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogservice;

    @RequestMapping("writeBlog")
    public String writeBlog(Blog blog) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        blog.setTime(dateString);
        this.blogservice.writeBlog(blog);
        return "success";
    }

    @RequestMapping("selectAllBlog")
    public String selectAllBlog(HttpServletRequest request) {

        try {
            List<Blog> blogs;
            blogs = this.blogservice.selectAllBlog();
            request.setAttribute("blogs", blogs);
            return "index";
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @RequestMapping("selectAllBlog2")
    public String selectAllBlog2(HttpServletRequest request) {

        try {
            List<Blog> blogs;
            blogs = this.blogservice.selectAllBlog();
            System.out.println("title:" + blogs.get(0).getBlogtitle());
            request.setAttribute("blogs", blogs);
            return "admin";
        } catch (Exception e) {
            System.out.println(e);
            return "admin";
        }
    }

    @RequestMapping("selectBlogById")
    public String selectBlogById(HttpServletRequest request) {
        try {
            String id = request.getParameter("blogid");
            int blogid = Integer.parseInt(id);
            List<Blog> blogs;
            blogs = this.blogservice.selectBlogById(blogid);
            request.setAttribute("blog", blogs.get(0));
            return "blog";
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @RequestMapping("adminblog")
    public String adminblog(HttpServletRequest request) {
        try {
            List<Blog> blogs;
            blogs = this.blogservice.selectAllBlog();
            request.setAttribute("blogs", blogs);
            return "adminblog";
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @RequestMapping("deleteBlogById")
    public String deleteBlogById(HttpServletRequest request) {
        try {
            String id = request.getParameter("blogid");
            int blogid = Integer.parseInt(id);
            this.blogservice.deleteBlogById(blogid);
            return "redirect:adminblog";
        } catch (Exception e) {
            return null;
        }
    }
}
