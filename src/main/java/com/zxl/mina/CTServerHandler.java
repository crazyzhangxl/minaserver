package com.zxl.mina;


import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 张先磊 on 2018/3/22.
 */
public class CTServerHandler extends IoHandlerAdapter {
    public CTServerHandler() {
        super();
    }
    // 这里会存在整个项目中

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.println("MinaServerHandler---sessionCreated");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        System.out.println("MinaServerHandler---sessionIdle");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        cause.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession session, Object message) {

        String str = (String)message;
        String clientAddress = session.getRemoteAddress().toString().replace("/", "");
        System.out.println("接受来自 { "+clientAddress+"} 的信息:{"+str+"}\n");
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        session.write("接受到了啊");
    }

    // 关闭与客户端的连接会调动
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        System.out.println("MinaServerHandler---sessionClosed");
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("发送成功啦"+message.toString());
        super.messageSent(session, message);
    }

    @Override
    public void inputClosed(IoSession session) throws Exception {
        super.inputClosed(session);
    }

}
