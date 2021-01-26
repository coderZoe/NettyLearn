package com.coderzoe.nettydevelop.class1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author: yhs
 * @date: 2020/12/19 16:49
 */
public class NettyClient {
    private final String host = "127.0.0.1";
    private final int port = 7777;

    public static void main(String[] args) {
        new NettyClient().start();
    }

    private void  start(){
        //客户端只需要一个事件循环组
        NioEventLoopGroup group = new NioEventLoopGroup();
        //创建启动脚手架
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(this.host,this.port))
                    .handler(new ChannelInitializer<SocketChannel>() {      //无法使用lambda表达式 因为接口方法不唯一
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
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
