package com.coderzoe.nettydevelop.class1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;


/**
 * @author: yhs
 * @date: 2020/12/19 16:56
 */
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("服务器地址:"+ctx.channel().remoteAddress());
        System.out.println("收到服务器接收的消息"+msg.toString(CharsetUtil.UTF_8));
    }

    /**
     * 当通道就绪时触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 服务端", CharsetUtil.UTF_8));
    }
}
