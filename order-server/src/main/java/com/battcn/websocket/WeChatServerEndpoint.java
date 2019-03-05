package com.battcn.websocket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import static com.battcn.websocket.WebSocketUtils.LIVING_SESSIONS_CACHE;


/**
 * websocket demo
 *
 * @author Levin
 * @since 2018/6/26 0026
 */
@RestController
@AllArgsConstructor
@Slf4j
@ServerEndpoint("/wechat/{ticket}")
public class WeChatServerEndpoint {

    @OnOpen
    public void openSession(@PathParam("ticket") String ticket, Session session) {
        WebSocketUtils.LIVING_SESSIONS_CACHE.put(ticket, session);
        String message = "[" + ticket + "] open WebSocket ....";
        log.info(message);
        WebSocketUtils.sendMessageAll(message);
        // 将 ticket 和 session 存储到 redis 去
    }

    @OnMessage
    public void onMessage(@PathParam("ticket") String ticket, String message, Session session) {
        log.info(message);
        // 给指定 session 发送请求
        WebSocketUtils.sendMessageAll("user [" + ticket + "] : " + message);
    }

    @OnClose
    public void onClose(@PathParam("ticket") String ticket, Session session) {
        //当前的Session 移除
        WebSocketUtils.LIVING_SESSIONS_CACHE.remove(ticket);
        log.info(session.toString() + "close");
        WebSocketUtils.sendMessageAll("user [" + ticket + "] exit room！");
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
