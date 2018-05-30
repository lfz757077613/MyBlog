package com.qunar.lfz.model.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class UserPo {
    private int id;
    private String userName;
    private String password;
    private String roles;
    private Date createTime;
    private Date updateTime;

    public UserPo(String userName, String password, String roles) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }
}
