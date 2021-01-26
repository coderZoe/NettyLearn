package com.coderzoe;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.stream.Stream;

/**
 * @author: yhs
 * @date: 2020/12/13 10:04
 */
public class Class5GatherAndScatter {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
        //绑定端口
        serverSocketChannel.socket().bind(inetSocketAddress);

        int buffer1Length = 4;
        int buffer2Length = 4;
        int maxLength = buffer1Length+buffer2Length;
        ByteBuffer buffer1 = ByteBuffer.allocate(buffer1Length);
        ByteBuffer buffer2 = ByteBuffer.allocate(buffer2Length);

        ByteBuffer[] buffer = {buffer1,buffer2};
        //等待客户端连接
        System.out.println("等待客户端连接");
        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true){
            //将客户端发来的数据拷贝到buffer里 这里就用了buffer的scatter 将一个Channel的数据写到多个Buffer里
            while (socketChannel.read(buffer)<maxLength){
                //将数据回显客户端
                Stream.of(buffer).forEach(Buffer::flip);

                Stream.of(buffer).forEach(bufferx-> {
                    System.out.println("position:"+bufferx.position()+"\n"+
                            "limit:"+bufferx.limit()+"\n"+
                            "bufferx:"+new String(bufferx.array(),0,bufferx.limit())+"\n");
                });

                //这里用到了buffer的gather操作 将多个buffer的数据写入一个channel中
                socketChannel.write(buffer);

                Stream.of(buffer).forEach(Buffer::clear);
            }

        }
    }
}
