package com.coderzoe.nettydevelop.class4;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author: yhs
 * @date: 2020/12/23 10:47
 */
public class HttpInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //作用说明：Netty提供的对于Http的编解码器
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpServerHandler());
    }
}
