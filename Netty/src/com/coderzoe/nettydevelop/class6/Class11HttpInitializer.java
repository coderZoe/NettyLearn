package com.coderzoe.nettydevelop.class6;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author: yhs
 * @date: 2021/1/5 20:31
 */
public class Class11HttpInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //解码 Netty提供的基于Http的编解码器
        ch.pipeline().addLast(new HttpServerCodec());
        ch.pipeline().addLast(new Class11HttpHandler());
    }
}
