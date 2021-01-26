package com.coderzoe.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: yhs
 * @date: 2020/12/21 10:43
 */
public class BIOServer1 {
    private final int port = 7777;


    public static void main(String[] args) throws IOException {
        new BIOServer1().start();
    }
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true){
            Socket clientSocket = serverSocket.accept();
            new Thread(()->{BIOServer1.handler(clientSocket);}).start();

        }
    }

    public static void handler(Socket socket) {
        byte[] buffer = new byte[1024];
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            int len = -1;
            while ((len = inputStream.read(buffer))!=-1){
                System.out.println("线程:"+Thread.currentThread().getName());
                System.out.println("客户端:"+socket.getRemoteSocketAddress()+"发送数据");
                System.out.println(new String(buffer,0,len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("关闭客户端连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
