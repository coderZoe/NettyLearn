package com.coderzoe;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author: yhs
 * @date: 2020/12/18 10:37
 */
public class Class10_2ChatRoomClient1 {
    private final String host = "127.0.0.1";
    private final int port = 7777;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    public Class10_2ChatRoomClient1() {
        try {
            this.socketChannel = SocketChannel.open(new InetSocketAddress(host,port));
            this.socketChannel.configureBlocking(false);
            this.selector = Selector.open();
            this.socketChannel.register(this.selector, SelectionKey.OP_READ);
            this.userName = this.socketChannel.getLocalAddress().toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Class10_2ChatRoomClient1 client = new Class10_2ChatRoomClient1();
        //开线程 不断读数据
        new Thread(() ->{
            try {
                client.readMessage();
            } catch (IOException e) {
                System.out.println("读取其他客户端消息出现错误"+e.getMessage());
                e.printStackTrace();
            }
        }).start();

        //发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.nextLine();
            client.sendMessage(message);
        }


    }

    private void sendMessage(String message){
        message = this.userName+"说:"+message;
        try {
            this.socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        } catch (IOException e) {
            System.out.println("客户端发送给服务器的消息发送失败"+e.getMessage());
            e.printStackTrace();
        }
    }

    private void readMessage() throws IOException {
        while (true){
            if(selector.select(20)>0){
                Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                while (selectionKeyIterator.hasNext()){
                    SelectionKey selectionKey = selectionKeyIterator.next();
                    if(selectionKey.isReadable()){
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        channel.read(byteBuffer);
                        System.out.println("得到其他客户端的消息:"+new String(byteBuffer.array()));
                    }
                    selectionKeyIterator.remove();
                }
            }
        }
    }
}
