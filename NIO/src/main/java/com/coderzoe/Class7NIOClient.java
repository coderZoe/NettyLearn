package com.coderzoe;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.stream.Stream;

/**
 * NIO Client
 * @author: yhs
 * @date: 2020/12/13 18:42
 */
public class Class7NIOClient {
    public static void main(String[] args) throws IOException {

        //得到一个通道
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 6666);
        socketChannel.connect(socketAddress);
        while (!socketChannel.finishConnect()){
            // 为连接完成 这里可以做其他事
        }
        String str = "hello world";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(buffer);
        System.in.read();   //阻塞一下 避免程序关闭

    }
}
