/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lfz.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.lfz.activemq.MqSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Echo messages by implementing a Spring {@link WebSocketHandler} abstraction.
 */
@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private static final Map<String, ConcurrentWebSocketSessionDecorator> sessionMap = Maps.newConcurrentMap();

    @Resource
    private MqSender mqSender;

    @Override
	public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        String username = (String) session.getAttributes().get("username");
        ConcurrentWebSocketSessionDecorator sessionDecorator = new ConcurrentWebSocketSessionDecorator(session, 1000, 5120);
        sessionMap.put(username, sessionDecorator);
        log.info("connect succ, username:[{}]", username);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "connect");
        jsonObject.put("username", username);
        sessionDecorator.sendMessage(new TextMessage(jsonObject.toJSONString()));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String username = StringUtils.defaultString((String) session.getAttributes().get("username"));
        String msg = StringUtils.defaultString(message.getPayload());
        JSONObject jsonObject = JSON.parseObject(msg);
        jsonObject.put("username", username);
        jsonObject.put("date", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        mqSender.send2topic(jsonObject.toJSONString());
    }

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws IOException {
        String username = (String) session.getAttributes().get("username");
        session.close(CloseStatus.SERVER_ERROR);
        log.error("transport error, username:[{}]", username, exception);
	}

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String username = StringUtils.defaultString((String) session.getAttributes().get("username"),"lfz");
        sessionMap.remove(username);
        log.info("connect closed, username:[{}]", username);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("action", "quit");
        mqSender.send2topic(jsonObject.toJSONString());
    }

    public void sendAllUser(String msg) {
        String username = StringUtils.EMPTY;
        try {
            for (Map.Entry<String, ConcurrentWebSocketSessionDecorator> entry : sessionMap.entrySet()) {
                username = entry.getKey();
                ConcurrentWebSocketSessionDecorator session = entry.getValue();
                if (!session.isOpen()) {
                    log.info("session close when send all user, username:[{}]", username);
                    continue;
                }
                session.sendMessage(new TextMessage(msg));
            }
        } catch (Exception e) {
            log.error("send all user error, username:[{}]", username, e);
        }
    }

    public boolean sendToUser(String username, TextMessage msg) {
        try {
            ConcurrentWebSocketSessionDecorator session = sessionMap.get(username);
            if (session == null || !session.isOpen()) {
                return false;
            }
            session.sendMessage(msg);
            return true;
        } catch (Exception e) {
            log.error("send to user error, username:[{}]", username, e);
            return false;
        }
    }
}
