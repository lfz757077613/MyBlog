package com.qunar.lfz.model.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class BlogPo {
    private int id;
    private String title;
    private String realContent;
    private String showContent;
    private Date createTime;
    private Date updateTime;
}
