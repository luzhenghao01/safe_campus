package com.lzh.electricfence;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Class: Test
 * @Description: 简单websocket demo
 * @author 九风萍舟
 */
@Slf4j
@Component
@ServerEndpoint(value="/websocket/{userId}")
public class Test {

  private static String userId;

  //连接时执行
  @OnOpen
  public void onOpen(@PathParam("userId") String userId,Session session) throws IOException{
    Test.userId = userId;
    log.info("新连接：{}",userId);
  }

  //关闭时执行
  @OnClose
  public void onClose(){
    log.info("连接：{} 关闭",this.userId);
  }

  //收到消息时执行
  @OnMessage
  public void onMessage(String message, Session session) throws IOException {
    log.info("收到用户{}的消息{}", userId,message);
    session.getBasicRemote().sendText("收到 "+this.userId+" 的消息 "); //回复用户
  }

  //连接错误时执行
  @OnError
  public void OnError(Session session, Throwable error){
    log.info("用户id为：{}的连接发送错误", userId);
    error.printStackTrace();
  }

}