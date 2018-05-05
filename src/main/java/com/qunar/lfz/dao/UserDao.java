package com.qunar.lfz.dao;

import com.qunar.lfz.model.po.UserPo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    void addUser(UserPo userPo);

    UserPo queryUserByName(String name);

    void deleteUserByName(String name);

    void updateUser(UserPo userPo);
}
