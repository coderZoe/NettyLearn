package com.coderzoe;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: yhs
 * @date: 2020/12/17 15:41
 */
public class Class10ChatRoomServer {
    /**
     * 群聊系统
     * 服务器和客户端的数据通讯
     * 多人群聊
     * 服务器端可以监测用户上线，离线，并实现消息转发
     * 客户端可以无阻塞发送消息给其他所有用户，同时可以接受其他用户发送的消息
     */
    private static final int port = 7777;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    //构造器初始化
    public Class10ChatRoomServer() {
        try {
            this.serverSocketChannel = ServerSocketChannel.open();
            this.serverSocketChannel.bind(new InetSocketAddress(Class10ChatRoomServer.port));
            this.serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            System.out.println("服务器初始化失败");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Class10ChatRoomServer().listen();
    }

    //监听客户端
    private void listen(){
        try {
            //循环监听
            while (true){
                if(selector.select(2000)>0){  //有需要处理的事件
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
//                    System.out.println("得到需要处理的SelectionKey,数量为："+selectionKeys.size());
                    Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                    while (selectionKeyIterator.hasNext()){
                        SelectionKey selectionKey = selectionKeyIterator.next();
                        if(selectionKey.isAcceptable()){    //接受事件
                            SocketChannel socketChannel = this.serverSocketChannel.accept();
                            System.out.println("客户端:"+socketChannel.getRemoteAddress()+" 上线");
                            socketChannel.configureBlocking(false);
                            socketChannel.register(this.selector,SelectionKey.OP_READ);
                        }else if(selectionKey.isWritable()){
                            System.out.println("写事件");
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        }else if(selectionKey.isReadable()){
                            readEvent(selectionKey);
                        }else {
                            System.out.println(selectionKey.interestOps());
                        }
                        selectionKeyIterator.remove();
                    }
                }
            }
        }catch (IOException e){
            System.out.println("处理用户的监听出现问题:"+e.getMessage());
            e.printStackTrace();
        }
    }

    private void readEvent(SelectionKey key){
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            while (true){
                if (socketChannel.read(byteBuffer) <= 0) break;
                // do nohing
            }
            byteBuffer.flip();
            String message = new String(byteBuffer.array());
            System.out.println("接收到客户端发送的数据，数据内容为："+message);
            //向其他客户端转发消息
            sendMessageToClient(message,socketChannel);

        }catch (IOException e){
            try {
                System.out.println("用户"+socketChannel.getRemoteAddress()+"离线");
                key.cancel();
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
//            e.printStackTrace();
        }
    }

    private void sendMessageToClient(String message,SocketChannel self) throws IOException {
        Set<SelectionKey> selectionKeySet = this.selector.keys();
        //遍历发送消息
        for(SelectionKey selectionKey:selectionKeySet){
            SelectableChannel channel = selectionKey.channel();
            if(channel instanceof SocketChannel &&!channel.equals(self)){
                System.out.println("准备将"+self.getRemoteAddress()+"的"+message+"的消息发送给其他客户端");
                SocketChannel socketChannel = (SocketChannel) channel;
                socketChannel.write(ByteBuffer.wrap(message.getBytes()));
            }
        }
    }
}
