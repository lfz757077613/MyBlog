package com.qunar.lfz.controller;

import com.qunar.lfz.model.po.BlogPo;
import com.qunar.lfz.model.MyResponse;
import com.qunar.lfz.model.ResponseEnum;
import com.qunar.lfz.model.vo.BlogDesc;
import com.qunar.lfz.model.vo.BlogView;
import com.qunar.lfz.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api")
public class BlogController {

    @Resource
    private BlogService blogService;

    @PostMapping("addBlog")
    public MyResponse addBlog(BlogPo blogPo) {
        if (StringUtils.isAnyBlank(blogPo.getTitle(), blogPo.getShowContent(), blogPo.getRealContent())) {
            return MyResponse.createResponse(ResponseEnum.FAIL);
        }
        if (blogService.addBlog(blogPo)) {
            return MyResponse.createResponse(ResponseEnum.SUCC);
        }
        return MyResponse.createResponse(ResponseEnum.FAIL);
    }

    @PostMapping("modifyBlog")
    public MyResponse modifyBlog(BlogPo blogPo) {
        if (StringUtils.isAnyBlank(blogPo.getTitle(), blogPo.getShowContent(), blogPo.getRealContent())) {
            return MyResponse.createResponse(ResponseEnum.FAIL);
        }
        if (blogService.modifyBlog(blogPo)) {
            return MyResponse.createResponse(ResponseEnum.SUCC);
        }
        return MyResponse.createResponse(ResponseEnum.FAIL);
    }

    @PostMapping("blogList")
    public MyResponse<List<BlogDesc>> queryAllBlogDesc() {
        return MyResponse.createResponse(ResponseEnum.SUCC, blogService.queryAllBlogDesc());
    }

    @PostMapping("blog/{id}")
    public MyResponse<BlogView> queryBlogById(@PathVariable int id) {
        return MyResponse.createResponse(ResponseEnum.SUCC, blogService.queryBlogShowById(id));
    }

    @PostMapping("delBlogs")
    public MyResponse delBlogByIds(@RequestBody(required = false) int[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return MyResponse.createResponse(ResponseEnum.SUCC);
        }
        if (blogService.delMultiBlogById(ids)) {
            return MyResponse.createResponse(ResponseEnum.SUCC);
        }
        return MyResponse.createResponse(ResponseEnum.UNKNOWN_ERROR);

    }
}
