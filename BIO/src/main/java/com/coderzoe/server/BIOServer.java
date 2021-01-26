package com.coderzoe.server;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: yhs
 * @date: 2020/10/15 19:57
 */
public class BIOServer {
    /**
     * 笔记
     * Java BIO
     * 服务器启动一个SeverSocket
     * 客户端启动Socket对服务器进行通信，默认情况下服务器端需要对每个客户建立一个线程与之通信
     * 客户端发出请求后，先咨询服务器是否有线程响应，如果没有则会等待，或者被拒绝
     * 如果有响应 客户端线程会等待请求结束后才继续执行(阻塞)
     *
     * BIO的问题：
     * 每个请求都需要创建独立的线程与对应的客户端进行通信
     * 当并发数较大时，需要创建大量线程来处理连接，系统资源占用较大
     * 连接创建以后，若当前线程暂时没有数据可读，则线程就阻塞在Read操作上，造成线程资源的浪费
     */
    public static void main(String[] args) throws IOException {
        //创建一个线程池
        //如果有客户端连接，就创建线程与之通信
        ExecutorService service = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动");

        while (true){
            //监听,等待客户端连接
            //阻塞处1
            System.out.println("等待客户端的连接");
            final Socket clientSocket = serverSocket.accept();
            System.out.println("一个客户端连接进来");
            service.execute(new Runnable() {
                public void run() {
                    BIOServer.handler(clientSocket);
                }
            });
        }

    }

    //编写有个handler方法和客户端通信
    public static void handler(Socket socket){
        try {
            System.out.println("线程id:"+Thread.currentThread().getName());
            byte[] buffer = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            int len = -1;

            //阻塞处2
            System.out.println("等待读取Socket信息");
            while ((len = inputStream.read(buffer))!=-1){
                System.out.println("线程id:"+Thread.currentThread().getName());
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
