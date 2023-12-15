package com.example.ly.nettychat;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends SimpleChannelInboundHandler<Object> {

    private Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    private WebSocketClientHandshaker handshaker;
    ChannelPromise handshakeFuture;

    /**
     * 当客户端主动链接服务端的链接后，调用此方法
     *
     * @param channelHandlerContext ChannelHandlerContext
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        handlerAdded(channelHandlerContext);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("连接出现问题：", cause);
        ctx.close();
    }

    public void setHandshaker(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public void handlerAdded(ChannelHandlerContext ctx) {
        this.handshakeFuture = ctx.newPromise();
    }

    public ChannelFuture handshakeFuture() {
        return this.handshakeFuture;
    }

    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        // 握手协议返回，设置结束握手
        if (!this.handshaker.isHandshakeComplete()) {
            FullHttpResponse response = (FullHttpResponse) o;
            this.handshaker.finishHandshake(ctx.channel(), response);
            this.handshakeFuture.setSuccess();
//            System.out.println("WebSocketClientHandler::channelRead0 HandshakeComplete...");
            return;
        } else if (o instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) o;
            logger.info("服务端回复：" + textFrame.text());
//            System.out.println("WebSocketClientHandler::channelRead0 textFrame: " + textFrame.text());
        } else if (o instanceof CloseWebSocketFrame) {
//            System.out.println("WebSocketClientHandler::channelRead0 CloseWebSocketFrame");
        }
    }
}
