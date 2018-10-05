package com.lfz.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.lfz.assist.StringAssist;
import com.lfz.dao.UserDao;
import com.lfz.model.po.UserPo;
import com.lfz.model.vo.UserDesc;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class UserService {

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
            return userDao.queryAllUser()
                    .stream()
                    .map(userPo -> {
                        UserDesc userDesc = new UserDesc();
                        BeanUtils.copyProperties(userPo, userDesc);
                        userDesc.setCreateTime(userPo.getCreateTime().format(DateTimeFormatter.ISO_LOCAL_DATE));
                        userDesc.setUpdateTime(userPo.getUpdateTime().format(DateTimeFormatter.ISO_LOCAL_DATE));
                        return userDesc;
                    })
                    .collect(toList());
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
