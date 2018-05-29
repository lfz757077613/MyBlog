package com.qunar.lfz.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.qunar.lfz.assist.StringAssist;
import com.qunar.lfz.dao.UserDao;
import com.qunar.lfz.model.po.UserPo;
import com.qunar.lfz.model.vo.UserDesc;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
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
    public UserPo queryUserByName(String name) {
        try {
            if (StringUtils.isBlank(name)) {
                return null;
            }
            return userDao.queryUserByName(name);
        } catch (Exception e) {
            log.error("db error when query username:{}", name, e);
            return null;
        }
    }

    //根据用户名获得用户的所有角色
    public Set<String> queryUserRole(String userName) {
        UserPo userPo = queryUserByName(userName);
        if (userPo == null) {
            return Collections.emptySet();
        }
        List<String> roleList = StringAssist.splitComma(userPo.getRoles());
        return Sets.newHashSet(roleList);
    }

    public boolean addCommonUser(UserPo userPo) {
        try {
            if (userPo == null || StringUtils.isAnyBlank(
                    userPo.getUserName(), userPo.getPassword(), userPo.getRoles())) {
                return false;
            }
            userDao.addUser(userPo);
            return true;
        } catch (Exception e) {
            log.error("db error when add user, userPo:{}", JSON.toJSONString(userPo), e);
            return false;
        }
    }

    public List<UserDesc> queryAllUserDesc() {
        try {
            return Lists.transform(userDao.queryAllUser(), new Function<UserPo, UserDesc>() {
                @Override
                public UserDesc apply(UserPo input) {
                    return new UserDesc(input);
                }
            });
        } catch (Exception e) {
            log.error("query all user error", e);
            return Collections.emptyList();
        }
    }

    public boolean delMultiUserById(int[] ids) {
        try {
            if (ArrayUtils.isEmpty(ids)) {
                return true;
            }
            userDao.delUserByIds(ids);
            return true;
        } catch (Exception e) {
            log.error("delete multi user by id error, ids:{}", JSON.toJSONString(ids), e);
            return false;
        }
    }
}
