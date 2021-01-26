package com.coderzoe.nettydevelop.class4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import sun.nio.cs.ext.GBK;

import java.net.URI;

/**
 * @author: yhs
 * @date: 2020/12/23 10:46
 */

/**
 * HttpObject表示客户端和服务器端 相互通讯的数据被封装成HttpObject类型，这里的转换是在HttpServerCodec实现的
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest){ //HttpRequest请求

            //资源拦截
            URI uri = new URI(((HttpRequest) msg).uri());
            if(uri.getPath().equals("/favicon.ico")){
                System.out.println("请求了/favicon.ico 不做响应");
                return;
            }


            System.out.println("收到来自："+ctx.channel().remoteAddress()+"Http请求");
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,我是服务器", new GBK());
            //构造HttpResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
            //返回
            ctx.writeAndFlush(response);
        }
    }
}
