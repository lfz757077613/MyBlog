package com.qunar.lfz.controller;

import com.qunar.lfz.assist.ParamCheck;
import com.qunar.lfz.model.MyResponse;
import com.qunar.lfz.model.ResponseEnum;
import com.qunar.lfz.model.userInfo.RoleEnum;
import com.qunar.lfz.model.userInfo.User;
import com.qunar.lfz.service.UserService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
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

@Controller
@RequestMapping("api")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("login")
    public String login(HttpServletRequest request, String username, String password, boolean rememberMe) {
        try {
            if (StringUtils.isAnyBlank(username, password)) {
                return "/static/html/loginError.html";
            }
            //shiro通过SecurityUtils.getSubject()获得主体，主体可以理解为客户端实例，原理在后面讲
            Subject subject = SecurityUtils.getSubject();
            //已经认证过，也就是该客户端已经登陆过
            if (subject.isAuthenticated()) {
                return "redirect:/";
            }
            //一般都使用UsernamePasswordToken，shiro的token中有Principal和Credentials的概念
            //Principal代表当前客户端要登录的用户，Credentials代表证明该用户身份的凭证
            //UsernamePasswordToken将username作为Principal，password作为Credentials
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //rememberMe功能后面讲
            token.setRememberMe(rememberMe);
            subject.login(token);
            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
            if (savedRequest == null || StringUtils.isBlank(savedRequest.getRequestUrl())) {
                return "redirect:/";
            }
            return "redirect:" + savedRequest.getRequestUrl();
        } catch (AuthenticationException e) {
            //登录失败则跳转到登录失败页面，可能是用户名或密码错误
            return "/static/html/loginError.html";
        }
    }
    @PostMapping("isLogin")
    @ResponseBody
    public MyResponse<String> isLogin() {
        try {
            Subject subject = SecurityUtils.getSubject();
            if (subject.isAuthenticated() || subject.isRemembered()) {
                MyResponse<String> response = MyResponse.createResponse(ResponseEnum.ALREAD_LOGIN);
                response.setDate(SecurityUtils.getSubject().getPrincipal().toString());
                return response;
            }
        } catch (Exception e) {
            return MyResponse.createResponse(ResponseEnum.UNKNOWN_ERROR);
        }
        return MyResponse.createResponse(ResponseEnum.SUCC);
    }

    @PostMapping("register")
    public String register(String username,String password) {
        try {
            if (StringUtils.isAnyBlank(username, password) || ParamCheck.hasSpecialChar(username)) {
                return "redirect:/static/html/registerError.html";
            }
            String securityPassword = new Md5Hash(password, username, 5).toString();
            userService.addCommonUser(new User(username, securityPassword, RoleEnum.COMMON.getRoleName()));
        } catch (Exception e) {
            return "redirect:/static/html/registerError.html";
        }
        return "redirect:/";
    }

    @PostMapping("file")
    public void uploadFile(MultipartFile source, HttpServletResponse resp) throws Exception {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(source.getInputStream()));

        resp.reset();
        resp.setHeader("Content-disposition", "attachment;filename*=utf-8''" + URLEncoder.encode("批量导入模版v1.xlsx", "UTF-8"));
        resp.setContentType("application/octet-stream;charset=utf-8");
        OutputStream os = resp.getOutputStream();
        os.write(FileUtils.readFileToByteArray(new File(
                getClass().getResource(File.separator).getPath() + "批量导入模版v1.xlsx")));
        os.flush();
        os.close();
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
