package com.lfz.controller;

import com.lfz.model.MyResponse;
import com.lfz.model.ResponseEnum;
import com.lfz.model.param.AddBlogParam;
import com.lfz.model.param.ModifyBlogParam;
import com.lfz.model.vo.BlogDesc;
import com.lfz.model.vo.BlogView;
import com.lfz.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import static java.util.stream.Collectors.*;

@Slf4j
@Validated
@RestController
@RequestMapping("api")
public class BlogController {

    @Resource
    private BlogService blogService;

    @PostMapping("addBlog")
    public MyResponse addBlog(@Validated AddBlogParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MyResponse.createResponse(ResponseEnum.ILLEGAL_PARAM,
                    bindingResult.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(toList()));
        }
        if (blogService.addBlog(param)) {
            return MyResponse.createResponse(ResponseEnum.SUCC);
        }
        return MyResponse.createResponse(ResponseEnum.FAIL);
    }

    @PostMapping("modifyBlog")
    public MyResponse modifyBlog(@Validated ModifyBlogParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MyResponse.createResponse(ResponseEnum.ILLEGAL_PARAM,
                    bindingResult.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(toList()));
        }
        if (blogService.modifyBlog(param)) {
            return MyResponse.createResponse(ResponseEnum.SUCC);
        }
        return MyResponse.createResponse(ResponseEnum.FAIL);
    }

    @PostMapping("blogList")
    public MyResponse<List<BlogDesc>> queryAllBlogDesc() {
        return MyResponse.createResponse(ResponseEnum.SUCC, blogService.queryAllBlogDesc());
    }

    //路径中的参数一定不会为null，如果不传，直接就404了
    @PostMapping("blog/{id}")
    public MyResponse<BlogView> queryBlogById(@Min(1) @PathVariable Integer id) {
        return MyResponse.createResponse(ResponseEnum.SUCC, blogService.queryBlogShowById(id));
    }

    @PostMapping("delBlogs")
    public MyResponse delBlogByIds(@NotEmpty @RequestBody(required = false) int[] ids) {
        if (blogService.delMultiBlogById(ids)) {
            return MyResponse.createResponse(ResponseEnum.SUCC);
        }
        return MyResponse.createResponse(ResponseEnum.UNKNOWN_ERROR);
    }
}
