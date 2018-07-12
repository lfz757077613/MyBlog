package com.qunar.lfz.model.vo;

import com.qunar.lfz.model.po.UserPo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.format.DateTimeFormatter;

/**
 * Author: fuzhi.lai
 * Date: 2018/5/14 下午2:56
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
@Accessors(chain = true)
@NoArgsConstructor
public class UserDesc {
    private int id;
    private String userName;
    private String createTime;
    private String updateTime;

    public UserDesc(@NonNull UserPo userPo) {
        this.id = userPo.getId();
        this.userName = userPo.getUserName();
        this.createTime = userPo.getCreateTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.updateTime = userPo.getUpdateTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
