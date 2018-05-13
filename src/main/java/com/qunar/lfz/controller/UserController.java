package com.qunar.lfz.controller;

import com.qunar.lfz.assist.ParamCheck;
import com.qunar.lfz.model.MyResponse;
import com.qunar.lfz.model.ResponseEnum;
import com.qunar.lfz.model.RoleEnum;
import com.qunar.lfz.model.po.UserPo;
import com.qunar.lfz.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;

@Slf4j
@Controller
@RequestMapping("api")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("login")
    @ResponseBody
    public MyResponse login(String username, String password, boolean rememberMe) {
        try {
            //shiro通过SecurityUtils.getSubject()获得主体，主体可以理解为客户端实例，原理在后面讲
            Subject subject = SecurityUtils.getSubject();
            //已经认证过，也就是该客户端已经登陆过
            if (subject.isAuthenticated()) {
                return MyResponse.createResponse(ResponseEnum.ALREADY_LOGIN);
            }
            if (StringUtils.isAnyBlank(username, password)) {
                return MyResponse.createResponse(ResponseEnum.FAIL);
            }
            //一般都使用UsernamePasswordToken，shiro的token中有Principal和Credentials的概念
            //Principal代表当前客户端要登录的用户，Credentials代表证明该用户身份的凭证
            //UsernamePasswordToken将username作为Principal，password作为Credentials
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //rememberMe功能后面讲
            token.setRememberMe(rememberMe);
            subject.login(token);
            return MyResponse.createResponse(ResponseEnum.SUCC);
        } catch (AuthenticationException e) {
            //登录失败则跳转到登录失败页面，可能是用户名或密码错误
            return MyResponse.createResponse(ResponseEnum.FAIL);
        }
    }

    @PostMapping("isLogin")
    @ResponseBody
    public MyResponse<String> isLogin() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            return MyResponse.createResponse(ResponseEnum.ALREADY_LOGIN, SecurityUtils.getSubject().getPrincipal().toString());
        }
        return MyResponse.createResponse(ResponseEnum.SUCC);
    }

    @PostMapping("register")
    @ResponseBody
    public MyResponse register(String username, String password) {
        if (StringUtils.isAnyBlank(username, password) || ParamCheck.hasSpecialChar(username)) {
            return MyResponse.createResponse(ResponseEnum.FAIL);
        }
        String securityPassword = new Md5Hash(password, username, 5).toString();
        if (userService.queryUserByName(username) != null) {
            return MyResponse.createResponse(ResponseEnum.USER_EXIST);
        }
        if (userService.addCommonUser(new UserPo(username, securityPassword, RoleEnum.COMMON.getRoleName()))) {
            return MyResponse.createResponse(ResponseEnum.SUCC);
        }
        return MyResponse.createResponse(ResponseEnum.UNKNOWN_ERROR);
    }

    @PostMapping("file")
    public void uploadFile(MultipartFile source, HttpServletResponse resp) {
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(source.getInputStream()));

            resp.reset();
            resp.setHeader("Content-disposition", "attachment;filename*=utf-8''" + URLEncoder.encode("批量导入模版v1.xlsx", "UTF-8"));
            resp.setContentType("application/octet-stream;charset=utf-8");
            OutputStream os = resp.getOutputStream();
            os.write(FileUtils.readFileToByteArray(new File(
                    getClass().getResource(File.separator).getPath() + "批量导入模版v1.xlsx")));
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("uploadFile error", e);
        }
    }

    @GetMapping("test1")
    @ResponseBody
    public String test1(HttpServletRequest request) {
        return "test1";
    }

    @GetMapping("test2")
    @ResponseBody
    public String test2(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
//        Session session1 = SecurityUtils.getSubject().getSession(false);
        return SecurityUtils.getSubject().getPrincipal().toString();
    }

}
