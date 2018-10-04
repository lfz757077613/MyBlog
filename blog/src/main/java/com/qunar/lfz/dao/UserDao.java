package com.qunar.lfz.dao;

import com.qunar.lfz.model.po.UserPo;

import java.util.List;

public interface UserDao {

    void addUser(UserPo userPo);

    UserPo queryUserByName(String name);

    void delUserByIds(int[] ids);

    List<UserPo> queryAllUser();
}
