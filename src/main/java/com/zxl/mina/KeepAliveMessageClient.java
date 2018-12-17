package com.zxl.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class KeepAliveMessageClient implements KeepAliveMessageFactory {
    // 客户端接受的服务端的消息
    private static final String HEARTBEATREQUEST = "ping";
    // 客户端向服务端发送的请求
    private static final String HEARTBEATRESPONSE = "pong";

    // 如果发送的是心跳包pong
    public boolean isRequest(IoSession session, Object message){
        return false;
    }

    // 用于判断接受的消息
    public boolean isResponse(IoSession session, Object message){
        if (message.equals(HEARTBEATREQUEST))
        {
            System.out.println("客户端接受到的服务端验证的消息 = "+HEARTBEATREQUEST);
            return  true;
        }
        return false;
    }



    public Object getRequest(IoSession session){
        System.out.println("客户端向服务端发送了请求"+HEARTBEATRESPONSE);
        return HEARTBEATRESPONSE;
    }


    // 获得的信息
    public Object getResponse(IoSession session, Object request){
        return  null;
    }
}
