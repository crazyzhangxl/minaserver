package com.zxl.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * description: 用于模拟客户端来进行测试的
 * Created by 张先磊 on 2018/3/22.
 *
 */
public class CTClientHandler extends IoHandlerAdapter {
    @Override
    public void messageReceived(IoSession session, Object message) {
        System.out.println("客户端2接受消息 = "+message.toString());


    }

    @Override
    public void messageSent(IoSession session, Object message) {
        System.out.println("客户端发送消息 = "+message.toString());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        System.out.println("客户端连接已经断开了---------------------");
    }
}
