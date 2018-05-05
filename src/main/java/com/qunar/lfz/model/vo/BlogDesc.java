package com.qunar.lfz.model.vo;

import com.qunar.lfz.assist.DateTimeUtil;
import com.qunar.lfz.model.po.BlogPo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joda.time.DateTimeUtils;

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

    public BlogDesc(BlogPo blogPo) {
        this.id = blogPo.getId();
        this.title = blogPo.getTitle();
        this.createTime = DateTimeUtil.formatDate(blogPo.getCreateTime(), DateTimeUtil.FORMAT_yyyy_MM_dd);
    }
}
