package com.qunar.lfz.service;

import com.google.common.collect.Sets;
import com.qunar.lfz.assist.StringAssist;
import com.qunar.lfz.dao.UserDao;
import com.qunar.lfz.model.userInfo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserService {
    //mybatis代理实现的dao
    @Resource
    private UserDao userDao;

    //根据用户名获得user对象
    public User queryUserByName(String name) {
        try {
            if (StringUtils.isBlank(name)) {
                return null;
            }
            return userDao.queryUserByName(name);
        } catch (Exception e) {
            log.error("db error when query user:{}", name, e);
        }
        return null;
    }

    //根据用户名获得用户的所有角色
    public Set<String> queryUserRole(String userName) {
        User user = queryUserByName(userName);
        if (user == null) {
            return Collections.emptySet();
        }
        List<String> roleList = StringAssist.splitComma(user.getRoles());
        return Sets.newHashSet(roleList);
    }

    public void addCommonUser(User user) {
        userDao.addUser(user);
    }
}
