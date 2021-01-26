package com.coderzoe.nettydevelop.class1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author: yhs
 * @date: 2020/12/19 16:02
 */
public class NettyServer {
    public static void main(String[] args)  {

        /**
         * 创建两个线程组，线程组里包含若干个NioEventLoop
         * bossGroup下的NioEventLoop等同于Reactor模型的mainReactor 只处理连接请求
         * workerGroup下的NioEventLoop等同于Reactor模型的subReactor 处理读写请求
         *
         * bossGroup和workerGroup含有的子线程(NioEventLoop)的个数，默认是CPU的核数*2
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //创建服务器端启动的脚手架
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)   //设置线程队列等待连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)   //设置连接保持活动连接状态
                    .localAddress(7777)
                    .childHandler(new ChannelInitializer<SocketChannel>() { //设置workerGroup的EventLoop的对应管道的处理器(handler)
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    });

            //启动服务器
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                bossGroup.shutdownGracefully().sync();
                workerGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
