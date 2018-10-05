package com.lfz.assist;

import com.lfz.model.ThreadLocalParam;

/**
 * Author: fuzhi.lai
 * Date: 2018/7/18 下午9:42
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
public final class MyThreadLocal {

    private static final InheritableThreadLocal<ThreadLocalParam> local = new InheritableThreadLocal<>();

    public static ThreadLocalParam get() {
        return local.get();
    }

    public static void set(ThreadLocalParam param) {
        local.set(param);
    }

    public static void remove() {
        local.remove();
    }
}
