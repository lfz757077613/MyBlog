package com.lfz.websocket;

import com.lfz.assist.SpringAssist;
import org.springframework.web.socket.server.standard.SpringConfigurator;

/**
 * Author: fuzhi.lai
 * Date: 2018/9/13 下午9:20
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
public class MySpringConfigurator extends SpringConfigurator {
    @Override
    public <T> T getEndpointInstance(Class<T> clazz) {
        return SpringAssist.getBean(clazz);
    }
}
