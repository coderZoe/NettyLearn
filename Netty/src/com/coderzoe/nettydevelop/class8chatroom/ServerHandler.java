package com.coderzoe.nettydevelop.class8chatroom;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: yhs
 * @date: 2021/1/12 20:39
 */
public class ServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * GlobalEventExecutor.INSTANCE 是全局的事件执行器是一个单例
     * Netty提供的channelGroup 很好用，直接对你的channel组进行了管理
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //需求:
        //服务器接收客户端发送的各个消息，将消息转发给其他的客户端
        Channel channel = ctx.channel();
        System.out.println(simpleDateFormat.format(new Date())+"  收到客户:"+ channel.remoteAddress()+"的消息："+msg);
        System.out.println(simpleDateFormat.format(new Date())+"  准备转发给其他客户");
        //去除发送客户的本人
        channelGroup.forEach((ch) ->{
            if(!ch.equals(channel)){
                ch.writeAndFlush(simpleDateFormat.format(new Date())+"  客户"+ channel.remoteAddress()+"说:"+msg+"\n");
            }
        });
    }

    /**
     * 表示当连接建立，一旦连接，会被第一个执行
     * 将当前Channel加入到ChannelGroup中
     * 代表一个客户上线了，此时需要推送给其他在线的客户
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.add(channel);
        //channelGroup管理了所有的Channel
        //writeAndFlush方法会自身遍历管理的channel发送消息
        channelGroup.writeAndFlush(simpleDateFormat.format(new Date())+"  客户:"+channel.remoteAddress()+" 加入群聊");
    }

    /**
     * 表示channel处于活动的状态 上线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(simpleDateFormat.format(new Date())+"  客户"+ctx.channel().remoteAddress()+"上线");
    }

    /**
     * channel处于非活动状态 离线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(simpleDateFormat.format(new Date())+"  客户"+ctx.channel().remoteAddress()+"离线");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //不需要remove channelGroup会自己去掉
        //channelGroup.remove(channel);
        channelGroup.writeAndFlush(simpleDateFormat.format(new Date())+"  客户:"+channel.remoteAddress()+"离开了群聊");
    }

    /**
     * 异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
