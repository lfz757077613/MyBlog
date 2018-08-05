package com.qunar.lfz.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Author: fuzhi.lai
 * Date: 2018/7/13 下午3:26
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
@Slf4j
@Component
public class MailSender {
    @Resource(name = "javaMailSender")
    private JavaMailSender sender;

    public void send(String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("757077613@qq.com");
            message.setTo("fuzhi.lai@qunar.com");
            message.setSubject("新用户注册：" + content);
            message.setText("欢迎：" + content);
            //发送邮件
            sender.send(message);
        } catch (Exception e) {
            log.error("send email error, content:{}", content, e);
        }

    }
}
