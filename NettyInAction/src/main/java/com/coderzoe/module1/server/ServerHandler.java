package com.coderzoe.module1.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 因为服务器会响应传入的消息，所以需要实现ChannelInboundHandler接口，用来定义响应入站事件的方法，
 * ChannelInboundHandlerAdapter类提供了ChannelInboundHandler接口的默认实现
 * @author: yhs
 * @date: 2020/10/16 13:54
 */
@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 对于每个传入的消息都需要调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        //将客户端发送的消息打印到控制台
        System.out.println("服务端接收到了"+buf.toString(CharsetUtil.UTF_8));
        //再将接收到的消息写给发送者
        ctx.write(buf);
    }

    /**
     * 通知ChannelInboundHandler最后一次对channelRead()的调用是当前批量读取的最后一条消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将未决消息冲刷到远程节点，并且关闭Channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 读取操作异常时的调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
