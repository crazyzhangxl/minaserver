package com.zxl.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by db on 17-1-10.
 */
public  class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory {
    // 服务端向客户端做出的回应
    private static final String HEARTBEATREQUEST = "ping";
    // 服务端检测客户端信息-----
    private static final String HEARTBEATRESPONSE = "pong";

    // 判断是否是客户端发送过来的心跳请求包,是的话返回true
    public boolean isRequest(IoSession session, Object message){
        if (message.equals(HEARTBEATRESPONSE)){
            System.out.println("isRequest   "+HEARTBEATRESPONSE);
            return true;
        }
        return false;
    }

    public boolean isResponse(IoSession session, Object message){
        return false;
    }

    // 被动心跳
    public Object getRequest(IoSession session){
        return null;
    }

    /**
     * 做出的回应是ping
     * @param session
     * @param request
     * @return
     */
    public Object getResponse(IoSession session, Object request){
        System.out.println("服务端根据客户端做出了回应 = "+HEARTBEATREQUEST);
        return  HEARTBEATREQUEST;
    }
}
