package com.lfz.activemq;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * Author: fuzhi.lai
 * Date: 2018/5/4 下午4:37
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
public class MqSender {
    private Queue queue = new ActiveMQQueue("com.lfz.queue.mail");
    private Topic topic = new ActiveMQTopic("com.lfz.topic.ws");
    @Resource
    private JmsMessagingTemplate template;

    public void send2queue(String message) {
        try {
            template.convertAndSend(queue, message);
        } catch (Exception e) {
            log.error("send queue mq error, message:[{}]", message, e);
        }
    }
    public void send2topic(String message) {
        try {
            template.convertAndSend(topic, message);
        } catch (Exception e) {
            log.error("send topic mq error, message:[{}]", message, e);
        }
    }
}