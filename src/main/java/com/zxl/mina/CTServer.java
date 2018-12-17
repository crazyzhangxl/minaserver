package com.zxl.mina;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by 张先磊 on 2018/3/22.
 */
public class CTServer {
    private static final int PORT = 8431;
    public static void main(String[] args) throws IOException {
        IoAcceptor acceptor = new NioSocketAcceptor();
        TextLineCodecFactory lineCodec = new TextLineCodecFactory(Charset.forName("UTF-8"));
        lineCodec.setDecoderMaxLineLength(1024*1024);
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(lineCodec));
        // 设置handler
        acceptor.setHandler(new CTServerHandler());
        acceptor.getSessionConfig().setReadBufferSize(2048);
        // 设置10没有读写进入空闲状态
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,10);
        //心跳设置
        KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl();
        KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,IdleStatus.BOTH_IDLE);
        //设置的处理时间 超过240秒(4分钟则断开)则断开连接
        // 还有一个细节: 就是服务器每隔1分钟(60)秒就会 Ping 客户端哦,
        // 合起来就是300秒 5分钟了
        heartBeat.setForwardEvent(true);
        heartBeat.setRequestInterval(100);
        heartBeat.setRequestTimeout(5);
        //heartBeat.setRequestTimeout(240);

        // 设置拦截器 设置自带的拦截器
        acceptor.getFilterChain().addLast("heartbeat", heartBeat);
        acceptor.bind(new InetSocketAddress(PORT));
        System.out.println("Listening on the Port:" + PORT);
    }

}
