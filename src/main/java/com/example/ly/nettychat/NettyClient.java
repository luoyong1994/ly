package com.example.ly.nettychat;

import com.fasterxml.uuid.impl.UUIDUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class NettyClient implements Runnable {

    Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private String userId;

    private NettyClient(String userId) {
        this.userId = userId;
    }

    public void start() {
        EventLoopGroup workGroup = null;
        try {
            final ClientHandler handler = new ClientHandler();
            workGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new HttpClientCodec());
                    pipeline.addLast(new ChunkedWriteHandler());
                    pipeline.addLast(new HttpObjectAggregator(1024 * 64));
                    pipeline.addLast(handler);
                }
            });

            URI websocketURI = new URI("ws://192.168.248.128:8761/websocket/" + userId);
//            URI websocketURI = new URI("ws://127.0.0.1:8761/websocket/" + userId);
            DefaultHttpHeaders httpHeaders = new DefaultHttpHeaders();
            WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(websocketURI, WebSocketVersion.V13, (String) null, true, httpHeaders);
            final Channel channel = bootstrap.connect(websocketURI.getHost(), websocketURI.getPort()).sync().channel();
            handler.setHandshaker(handshaker);
            handshaker.handshake(channel);
            //阻塞等待是否握手成功
            handler.handshakeFuture().sync();

            //发送消息
            while (true) {
                Thread.sleep(50);
                channel.writeAndFlush(new TextWebSocketFrame("one-" + userId + "|#say：test" + atomicInteger.incrementAndGet() + "|" + userId));
            }
        } catch (Exception e) {
            logger.error("发生错误：", e);
            e.printStackTrace();
        } finally {
            if (workGroup != null) {
                workGroup.close();
            }
        }


    }

    @Override
    public void run() {
        this.start();
    }

    public static void main(String[] args) throws InterruptedException {
//        java -cp myjar.jar com.example.ly.nettychat.NettyClient
//        String threadstr = args[0];
//        if (StringUtils.isBlank(threadstr)) {
//            threadstr = "1";
//        }
//        int threadNum = Integer.parseInt(threadstr);
//        CopyOnWriteArrayList<Thread> threads = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 2000; i++) {
            Thread.sleep(1);
            String userId = UUID.randomUUID().toString().replace("-", "");
            Thread thread = new Thread(new NettyClient(userId));
            thread.start();
//            threads.add(thread);
        }
    }


}
