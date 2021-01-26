package com.coderzoe;

/**
 * @author: yhs
 * @date: 2020/12/19 10:46
 */
public class Class2Netty1 {
    /**
     * 笔记
     * Netty简化版:
     * 1.BossGroup线程:维护Selector，只关心Accept事件 类似于主从Reactor的mainReactor
     * 2.当接收到accept事件后，就会获取到对应的socketChannel
     * 3.将socketChannel封装成NioSocketChannel并注册到worker线程(事件循环线程)，并进行维护 (worker类似于主从Reactor模型中的subReactor)
     * 4.当worker线程监听到selector中的通道发生感兴趣的事件后就进行处理，交给Handler处理(Handler已经加入到了通道)
     *
     * Netty进阶版:
     * 对于上面说的BossGroup可以是多个(多个线程，每个线程对应一个NioEventLoop，多个线程叫做bossGroup,只监听连接事件)
     * bossGroup监听到事件后，创建SocketChannel,封装为NioSocketChannel,注册到workerGroup上的NioEvent(workerGroup里的每个NioEvent类似于上面说的worker)
     * 也即可以看到,对于Reactor模型，Netty的mainReactor也是多线程的
     *
     * Netty详细版:
     * 1.Netty抽象出两组线程池:BossGroup WorkerGroup
     * 2.BossGroup专门负责客户端的连接,WorkerGroup专门负责网络的读写
     * BossGroup和WorkerGroup都是NioEventLoopGroup类
     * 3.NioEventLoopGroup相当于一个事件循环组，这个组中含有多个事件循环，而每一个事件循环都是NioEventLoop
     * 4.NioEventLoop表示一个不断循环的执行处理任务的线程，每一个NioEventLoop都有一个Selector,用于监听绑定在其上的Socket
     * 5.NioEventLoopGroup可以有多个线程，即可以含有多个NioEventLoop
     * 6.每个BossGroup的NioEventLoop执行的步骤有三步:(循环执行)
     * 1).首先轮询accept事件
     * 2).如果得到accept事件，与client建立连接,生成一个NioSocketChannel,并将其注册到某个worker NioEventLoop上的selector
     * 3).处理任务队列的任务
     * 7.每个WorkerGroup的NioEventLoop执行步骤:(循环执行)
     * 1).轮询 read和write事件
     * 2).处理IO事件(即read,write事件)
     * 3).处理任务队列的其他任务
     * 8.每个WorkerGroup的NioEventLoopGroup处理业务时会使用到pipeline
     */
}
