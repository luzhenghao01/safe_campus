package com.lzh.electricfence;

import com.alibaba.fastjson.JSONObject;
import com.lzh.electricfence.dto.MessageDto;
import java.io.IOException;
import java.util.Queue;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Class: Test
 * @Description: 简单websocket demo
 * @author 九风萍舟
 */
@Slf4j
@Component
@ServerEndpoint(value="/websocket/{userId}")
public class WebsocketReceiver {
  double a = 39.98795;
  double b = 116.320835;
  private static String userId;

  //连接时执行
  @OnOpen
  public void onOpen(@PathParam("userId") String userId,Session session) throws IOException{
    WebsocketReceiver.userId = userId;
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
    MessageDto o = JSONObject.parseObject(message,MessageDto.class);
    switch (o.getType()){
      case 1:
        log.info("接受定位："+o.getValue());
        a += 1.0;
        JSONObject value = new JSONObject().fluentPut("type",1)
            .fluentPut("value", "{latitude:"+a+",radius:"+b+"}");
        session.getBasicRemote().sendText(value.toJSONString()); //回复用户
        break;
    }

  }

  //连接错误时执行
  @OnError
  public void OnError(Session session, Throwable error){
    log.info("用户id为：{}的连接发送错误", userId);
    error.printStackTrace();
  }

  }