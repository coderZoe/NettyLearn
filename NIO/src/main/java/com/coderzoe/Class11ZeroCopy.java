package com.coderzoe;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author: yhs
 * @date: 2020/12/18 11:39
 */
public class Class11ZeroCopy {
    /**
     * 零拷贝
     * memoryMap(内存映射)
     * sendFile
     */

    public static void main(String[] args) throws IOException {
//       new Thread(() ->{
//           try {
//               BIOServerCopy();
//           } catch (IOException e) {
//               e.printStackTrace();
//           }
//       }).start();
//       new Thread(() ->{
//           try {
//               BIOClientCopy();
//           } catch (IOException e) {
//               e.printStackTrace();
//           }
//       }).start();

       new Thread(() ->{
           try {
               NIOServerCopy();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }).start();

       new Thread(() ->{
           try {
               NIOClientCopy();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }).start();
    }


    public static void BIOServerCopy() throws IOException {
        ServerSocket serverSocket = new ServerSocket(7111);
        while (true){
            Socket socket = serverSocket.accept();
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            byte[] buffer = new byte[10240];
            while (inputStream.read(buffer)>0){
                // do nothing
            }
        }
    }

    public static void BIOClientCopy() throws IOException {
        Socket socket = new Socket("127.0.0.1",7111);
        FileInputStream inputStream = new FileInputStream("D:/1.txt");
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[10240];
        int len = -1;
        long count = 0;
        long startTime = System.currentTimeMillis();
        while ((len = inputStream.read(buffer)) >0){
            count+=len;
            dataOutputStream.write(buffer);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("字节数:"+count);
        System.out.println("BIO用时:"+(endTime-startTime));
        inputStream.close();
    }

    public static void NIOServerCopy() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(7112));

        ByteBuffer byteBuffer = ByteBuffer.allocate(10240);
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            long count = 0;
            int len = -1;
            while ((len = socketChannel.read(byteBuffer))>0){
                count+=len;
                byteBuffer.rewind();
            }
            System.out.println("服务器字节数"+count);
        }
    }

    public static void NIOClientCopy() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",7112));
        FileInputStream fileInputStream = new FileInputStream("D:/1.txt");
        FileChannel channel = fileInputStream.getChannel();
        long startTime = System.currentTimeMillis();
        //在Linux下 一个transferTo()方法即可完成传输
        //在Windows下一次调用transferTo()只能发送8M的文件 分段发送
        //transferTo()底层实现零拷贝
        int oneTimeCount = 8388608;
        int times = (int) (channel.size()/oneTimeCount);
        long count = 0;
        for(int i = 0; i < times; i++){
            count += channel.transferTo((long) (i)*oneTimeCount, channel.size(), socketChannel);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("NIO用时:"+(endTime-startTime));
        System.out.println("字节数:"+count);
        channel.close();
    }
}
