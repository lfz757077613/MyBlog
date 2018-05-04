package com.qunar.lfz.controller;

import com.qunar.lfz.model.Blog;
import com.qunar.lfz.model.MyResponse;
import com.qunar.lfz.model.ResponseEnum;
import com.qunar.lfz.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("api")
public class BlogController {

    @Resource
    private BlogService blogservice;

    @PostMapping("addBlog")
    @ResponseBody
    public MyResponse<String> addBlog(Blog blog) {
        blogservice.addBlog(blog);
        return MyResponse.createResponse(ResponseEnum.SUCC);

    }

    @RequestMapping("queryAllBlog")
    public String selectAllBlog(HttpServletRequest request) {

        try {
            List<Blog> blogs;
            blogs = this.blogservice.queryAllBlog();
            request.setAttribute("blogs", blogs);
            return "index";
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
