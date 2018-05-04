package com.qunar.lfz.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Blog {
    private int id;
    private String title;
    private String realContent;
    private String showContent;
    private Date createTime;
    private Date updateTime;
}
