package com.battcn.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;


/**
 * websocket demo
 *
 * @author Levin
 * @since 2018/6/26 0026
 */
@AllArgsConstructor
@Slf4j
@ServerEndpoint("/levin/wechat/{ticket}")
public class WeChatServerEndpoint {

    private static final String WEB_SOCKET_SESSION = "ksudi:websocket:session";

    @OnOpen
    public void openSession(@PathParam("ticket") String ticket, Session session) {
        String message = "[" + ticket + "] 建立了一个 WebSocket 链接....";
        log.info(message);
        // 将 ticket 和 session 存储到 redis 去
    }

    @OnMessage
    public void onMessage(@PathParam("ticket") String ticket, String message, Session session) {
        log.info(message);
        // 给指定 session 发送请求
        try {
            final RemoteEndpoint.Basic basic = session.getBasicRemote();
            basic.sendText(ticket + "我收到你的请求了");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(@PathParam("ticket") String ticket, Session session) {
        //当前的Session 移除
        log.info(session.toString() + "断开了连接");
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }

}
