package com.qunar.lfz.model.vo;

import com.qunar.lfz.model.po.BlogPo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.format.DateTimeFormatter;

/**
 * Author: fuzhi.lai
 * Date: 2018/5/5 下午1:22
 * Create by Intellij idea
 */
/*
                       _oo0oo_
                      o8888888o
                      88" . "88
                      (| -_- |)
                      0\  =  /0
                    ___/`---'\___
                  .' \\|     |// '.
                 / \\|||  :  |||// \
                / _||||| -:- |||||- \
               |   | \\\  -  /// |   |
               | \_|  ''\---/''  |_/ |
               \  .-\__  '-'  ___/-. /
             ___'. .'  /--.--\  `. .'___
          ."" '<  `.___\_<|>_/___.' >' "".
         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
         \  \ `_.   \_ __\ /__ _/   .-` /  /
     =====`-.____`.___ \_____/___.-`___.-'=====
                       `=---='
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

               佛祖保佑         永无BUG
*/
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class BlogDesc {
    private int id;
    private String title;
    private String createTime;
    private String updateTime;

    public BlogDesc(@NonNull BlogPo blogPo) {
        this.id = blogPo.getId();
        this.title = blogPo.getTitle();
        this.createTime = blogPo.getCreateTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.updateTime = blogPo.getUpdateTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
