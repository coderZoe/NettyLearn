package com.coderzoe;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO Server端
 * @author: yhs
 * @date: 2020/12/13 18:42
 */
public class Class7NIOServer {
    public static void main(String[] args) throws IOException {
        //创建服务端
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
        serverSocketChannel.socket().bind(inetSocketAddress);
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //ServerSocketChanel也需要注册到Selector上
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端的连接
        while (true){
            //注:因为ServerSocketChannel也注册到了selector里，所以如果有客户端接入也会被监听到
            if(selector.select(1000)==0){   //无任何事件发生
                // do nothing
            }else {
                //获取关注事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                System.out.println("当前事件数:"+selectionKeys.size());
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                while (selectionKeyIterator.hasNext()){
                    SelectionKey selectionKey = selectionKeyIterator.next();
                    if(selectionKey.isAcceptable()){    //有新的客户端连接  即刚才我们监听的服务端事件
                        //因为已经确定了有客户端连接，所以不会阻塞
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        //注册到Selector上，同时给该Channel关联一个Buffer上去
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ);
                    }else if(selectionKey.isReadable()){    //读事件 针对服务器端而言的读事件
                        try {
                            SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            socketChannel.read(buffer);
                            System.out.println("客户端发送一条数据:"+new String(buffer.array()));
                        }catch (Exception e){
                            System.out.println("客户端断开连接");
                            selectionKey.cancel();
                            selectionKey.channel().close();
                        }
                    }
                    selectionKeyIterator.remove();
                }
            }
        }


    }
}
