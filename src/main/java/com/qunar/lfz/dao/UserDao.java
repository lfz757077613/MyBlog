package com.qunar.lfz.dao;

import com.qunar.lfz.model.userInfo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    void addUser(User user);

    User queryUserByName(String name);

    void deleteUserByName(String name);

    void updateUser(User user);
}
