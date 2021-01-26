package com.coderzoe.nettydevelop.class1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author: yhs
 * @date: 2020/12/19 16:24
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    //read事件
    //ChannelHandlerContext 上下文对象 包含: pipeline channel 地址等
    //Object msg 发送的数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx:"+ctx);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端地址:"+ctx.channel().remoteAddress());
        System.out.println("客户端发送消息是:"+byteBuf.toString(CharsetUtil.UTF_8));

        //当我们有一个非常耗时的业务，我们需要异步执行，提交该channel对应的NioEventLoop的taskQueue
//        Thread.sleep(10*1000);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端2",CharsetUtil.UTF_8));
//        System.out.println("读取完毕");
        //解决方案1: 普通任务
//        ctx.channel().eventLoop().execute(() ->{
//            try {
//                Thread.sleep(10*1000);
//                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端2",CharsetUtil.UTF_8));
//                System.out.println("读取完毕");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

//        //2. 用户自定义定时任务 该任务被提交到scheduleTaskQueue，不再是taskQueue中  延迟发送
//        ctx.channel().eventLoop().schedule(() ->{
//            try {
//                Thread.sleep(5*1000);  //模拟耗时
//                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端，这是定时耗时的一句话",CharsetUtil.UTF_8));
//                System.out.println("发送完毕");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },5, TimeUnit.SECONDS); //5s后执行
    }


    //数据读取完毕后的操作
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓冲并刷新 返给客户端
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
