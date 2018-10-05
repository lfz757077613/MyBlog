package com.lfz.activemq;

import com.lfz.mail.MyMailSender;
import com.lfz.websocket.ChatWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Author: fuzhi.lai
 * Date: 2018/5/4 下午4:26
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
public class MqListener{
    @Resource
    private MyMailSender myMailSender;
    @Resource
    private ChatWebSocketHandler wsHandler;

    @JmsListener(destination = "com.lfz.queue.mail", containerFactory = "queueFactory")
    public void onQueueMessage(String username) {
        try {
            log.info("new user:{}", username);
            myMailSender.send(username);
        } catch (Exception e) {
            log.error("on queue message error", e);
        }
    }

    @JmsListener(destination = "com.lfz.topic.ws", containerFactory = "topicFactory")
    public void onTopicMessage(String message) {
        try {
            wsHandler.sendAllUser(message);
        } catch (Exception e) {
            log.error("on topic message error", e);
        }
    }
}