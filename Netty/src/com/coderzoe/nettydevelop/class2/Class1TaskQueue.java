package com.coderzoe.nettydevelop.class2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author: yhs
 * @date: 2020/12/23 9:20
 */
public class Class1TaskQueue {
    /**
     * 笔记
     * 在讲Netty线程模型时NioEventLoop里除了selector还有一个是taskQueue
     * 任务队列里taskQueue有三种典型使用场景
     * 1. 用户程序自定义的普通任务
     * 2. 用户自定义的定时任务
     * 3.非当前Reactor线程调用Channel的各种方法
     * 第三种情况举例：在推送系统的业务线程里，根据用户的标识，找到对应的Channel引用，然后调用Write类方法向该用户推送消息，就会进入到这种场景。
     * 最终的Write会提交到任务队列后被异步消费。
     */

    public static void main(String[] args) {

    }

    /**
     * 第一种情况，假设我们当前有一个非常耗时的任务
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //当我们有一个非常耗时的业务，如果我们正常执行时，会非常耗时，且在执行完这个操作后才能执行channelReadComplete()函数，这样返回给客户端的消息就会很耗时
        Thread.sleep(10*1000);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端，这是耗时的一句话",CharsetUtil.UTF_8));
        System.out.println("发送完毕");


        //1: 普通任务 我们需要异步执行，提交到该channel对应的NioEventLoop的taskQueue
        //注：如果将多个耗时任务交给TaskQueue，TaskQueue并不会异步执行他们，而是会顺序排队执行(因为这些超时任务都在一个线程里执行)
        ctx.channel().eventLoop().execute(() ->{
            try {
                Thread.sleep(10*1000);  //模拟耗时
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端，这是耗时的一句话",CharsetUtil.UTF_8));
                System.out.println("发送完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //2. 用户自定义定时任务 该任务被提交到scheduleTaskQueue，不再是taskQueue中  延迟发送
        ctx.channel().eventLoop().schedule(() ->{
            try {
                Thread.sleep(5*1000);  //模拟耗时
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端，这是定时耗时的一句话",CharsetUtil.UTF_8));
                System.out.println("发送完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },5, TimeUnit.SECONDS); //5s后执行
    }
}
