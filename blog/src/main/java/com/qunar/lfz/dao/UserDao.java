package com.qunar.lfz.dao;

import com.qunar.lfz.model.po.UserPo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    void addUser(UserPo userPo);

    UserPo queryUserByName(String name);

    void delUserByIds(int[] ids);

    List<UserPo> queryAllUser();
}
