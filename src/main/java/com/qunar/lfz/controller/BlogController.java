package com.qunar.lfz.controller;

import com.qunar.lfz.assist.ParamCheck;
import com.qunar.lfz.model.po.BlogPo;
import com.qunar.lfz.model.MyResponse;
import com.qunar.lfz.model.ResponseEnum;
import com.qunar.lfz.model.vo.BlogDesc;
import com.qunar.lfz.model.vo.BlogView;
import com.qunar.lfz.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("api")
public class BlogController {

    @Resource
    private BlogService blogservice;

    @PostMapping("addBlog")
    @ResponseBody
    public MyResponse<String> addBlog(BlogPo blogPo) {
        if (StringUtils.isAnyBlank(blogPo.getTitle(), blogPo.getShowContent(), blogPo.getRealContent())) {
            return MyResponse.createResponse(ResponseEnum.FAIL);
        }
        if (blogservice.addBlog(blogPo)) {
            return MyResponse.createResponse(ResponseEnum.SUCC);
        }
        return MyResponse.createResponse(ResponseEnum.FAIL);
    }

    @PostMapping("blogList")
    @ResponseBody
    public MyResponse<List<BlogDesc>> queryAllBlogDesc() {
        return MyResponse.createResponse(ResponseEnum.SUCC, blogservice.queryAllBlogDesc());
    }

    @RequestMapping("blog/{id}")
    @ResponseBody
    public MyResponse<BlogView> queryBlogById(@PathVariable int id) {
        return MyResponse.createResponse(ResponseEnum.SUCC, blogservice.queryBlogShowById(id));
    }
}
