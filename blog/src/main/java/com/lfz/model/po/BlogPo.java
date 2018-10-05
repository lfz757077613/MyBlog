package com.lfz.model.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Setter
@Getter
@Accessors(chain = true)
public class BlogPo {
    private Integer id;
    private String title;
    private String realContent;
    private String showContent;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
