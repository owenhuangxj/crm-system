package com.ss.util;

import com.alibaba.fastjson.JSONObject;
import com.ss.entity.Message;
import com.ss.entity.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint(value = "/websocket/{uname}", configurator = GetHttpSessionConfigurator.class)
//@ServerEndpoint(value = "/websocket")
@Component
public class Websocket {
    private static int onlineCount = 0;
    private static Map<String, Websocket> clients = new ConcurrentHashMap<>();
    //	private static Set<String> accounts = new HashSet<String>();
    private Session session;
    private String uname;
    private static int id = 0;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("uname") String uname) throws IOException {
        System.out.println("uname : " + uname);
        this.uname = uname;
//		admin.setMsgs(list);

        this.session = session;

        addOnlineCount();
        System.out.println("clients : " + clients);
        clients.put(uname, this);
//		accounts.add(user.getAdminAccount());
//		httpSession.setAttribute("accounts", accounts);
        System.out.println(clients);
        System.out.println("已连接");
    }

    @OnClose
    public void onClose() throws IOException {
        clients.remove(uname);
        subOnlineCount();
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        // 接收的message参数是json对象。
        System.out.println("message : "+message);
        Message msgpojo = JSONObject.parseObject(message, Message.class);
        sendMessageTo(message, msgpojo.getMsgTo());

    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessageTo(String message, String To) throws IOException {
        for (Websocket item : clients.values()) {
            if (item.uname.equals(To)){
                // 匹配到发送目标后，将消息对象和发送者id传给前端
                item.session.getAsyncRemote().sendText(message + "~~" + this.uname);
            }

        }
    }

    public void sendMessageAll(String message) throws IOException {
        for (Websocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        Websocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        Websocket.onlineCount--;
    }

    public static synchronized Map<String, Websocket> getClients() {
        return clients;
    }
}
