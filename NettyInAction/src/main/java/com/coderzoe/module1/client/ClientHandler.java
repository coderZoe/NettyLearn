package com.coderzoe.module1.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author: yhs
 * @date: 2020/10/16 17:06
 */

@Sharable
/**
 * 这里我们继承的是SimpleChannelInboundHandler而不是类似于服务端的ChannelInboundHandlerAdapter类
 * 这是因为在客户端中，当ChannelRead0()方法完成时，你已经处理完了ByteBuf里的返回信息，此时SimpleChannelInboundHandler就会释放ByteBuf里的内存
 * 而在服务端中，channelRead()方法内我们使用了异步的write()方法，这就导致channelRead()方法即使返回，但write()方法也不一定完成，
 * 但ChannelInboundHandlerAdapter类并不会在channelRead()结束后释放ByteBuf的内存，ByteBuf里的消息将会在channelReadComplete()方法中，当writeAndFlush()方法被调用时被释放
 */
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {


    /**
     * 被通知Channel活跃的时候，发送一条信息
     * 所谓Channel活跃即与服务器的连接已经建立
     * @author: yhs
     * @date: 2020/10/16 17:12
     * @param:@param ctx ctx
     * @return:
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Nettty rocks!".getBytes()));
    }

    /**
     * 在从服务器接收到一条消息时被调用
     * @param byteBuf 字节缓冲区
     * @author: yhs
     * @date: 2020/10/16 17:09
     * @param:@param channelHandlerContext 通道处理程序上下文x
     * @return:
     */
    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("收到服务器消息"+byteBuf.toString(CharsetUtil.UTF_8));

    }


    /**
     *
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