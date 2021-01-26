package com.coderzoe.module1.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author: yhs
 * @date: 2020/10/16 14:08
 */
public class NettyServer {
    private final int port;
    public NettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new NettyServer(9876).start();
    }

    private void start(){
        /**                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A   A
         * 总结：
         * 创建ServerBootstrap，并对他进行一系列的配置
         * 1. 配置EventLoopGroup
         * 2. 配置Channel类型
         * 3. 配置服务器端口
         * 4. 配置处理channel的channelHandler
         */
        final ServerHandler serverHandler = new ServerHandler();
        //创建EventLoopGroup 进行事件的处理，如接收新的连接以及读写数据
        //因为是NIO的传输方式，所以创建的是NioEventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建ServerBootstrap实例用以引导和绑定服务器
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    //声名通道类型，因为用的NIO连接，所以是NioServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    //绑定端口监听连接请求
                    .localAddress(new InetSocketAddress(this.port))
                    //每有一个新连接到来，就会初始化一个新的Channel，这时就需要指定处理channel的channelHandler的方法
                    //将自己写的ServerHandler方法传入
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel socketChannel) throws Exception {
                            //将我们写的channelHandler实例添加到新的Channel的ChannelPipeline中
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });
            //异步绑定服务器 调用sync()方法阻塞等待直到绑定完成
            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
