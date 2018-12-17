package com.zxl.mina;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by 张先磊 on 2018/3/22.
 */
public class CTClient {
    public static void main(String[] args){
        // 第一步，建立一个connecter
        NioSocketConnector connecter = new NioSocketConnector();
        // 第二步，设置消息处理的Handler
        connecter.setHandler(new CTClientHandler());
        // 第三步骤，设置过滤器
        connecter.getFilterChain().addLast("minaClinet", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("utf-8"))));
        // 第四步骤，连接服务器
        //ConnectFuture future = connecter.connect(new InetSocketAddress("223.2.197.240",8431));
        ConnectFuture future = connecter.connect(new InetSocketAddress("127.0.0.1",8431));
        //心跳设置
        KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageClient();
        KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory, IdleStatus.READER_IDLE, KeepAliveRequestTimeoutHandler.CLOSE);
        //设置的处理时间 超过240秒(4分钟则断开)则断开连接
        // 还有一个细节: 就是服务器每隔1分钟(60)秒就会 Ping 客户端哦,
        // 合起来就是300秒 5分钟了
//        heartBeat.setRequestTimeout(240);
        heartBeat.setRequestTimeout(40);
        // 设置拦截器 设置自带的拦截器
         connecter.getFilterChain().addLast("heartbeat", heartBeat);
        // 阻塞等待连接创建
        future.awaitUninterruptibly();
        //sendMsg(future.getSession(),"3.CONTROL:00001,1;20180520");
        sendMsg(future.getSession(),"GET_DEVICE_NOW_WITH_ELE;1500009");
    }
    public static void sendMsg(IoSession session , String msg){
        if(session != null){
            session.write(msg);
        }
    }
}
