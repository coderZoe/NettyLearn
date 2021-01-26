package com.coderzoe.nettydevelop.class8chatroom2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author: yhs
 * @date: 2021/1/12 21:03
 */
public class CharSetClient {
    private final String host = "127.0.0.1";
    private final int port = 8082;

    public static void main(String[] args) {
        new CharSetClient().start();
    }

    private void start(){
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(this.host,this.port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("decoder",new StringDecoder());
                            ch.pipeline().addLast("encoder",new StringEncoder());
                            ch.pipeline().addLast("clientHandler",new ClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect().sync();
            //得到连接成功后的channel
            //然后循环读取消息发送到服务器
            Channel channel = channelFuture.channel();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg);
            }
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                workerGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
