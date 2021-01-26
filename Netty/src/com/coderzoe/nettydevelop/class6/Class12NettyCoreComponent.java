package com.coderzoe.nettydevelop.class6;

/**
 * Netty核心组件
 * @author: yhs
 * @date: 2021/1/5 21:40
 */
public class Class12NettyCoreComponent {
    /**
     * 笔记
     * BootStrap 客户端启动引导类
     * ServerBootStrap 服务端启动引导类
     * 主要是配置整个Netty的程序，串联各个组件
     *
     * ChannelPipeline是一个Handler的集合，它负责处理和拦截入站或出站事件，相当于一个贯穿Netty的链
     * 也可以这样理解:ChannelPipeline是保存ChanelHandler的List集合，用于处理和拦截Channel的入站事件和出站事件
     * ChannelPipeline实现了一种高级形式的拦截过滤器模式，使用户可以完全控制事件的处理方式，以及Channel中各个的ChannelHandler如何交互
     *
     * 在Netty中每个Channel都有且仅有一个ChannelPipeline与之对应，他们组成关系如下：
     * 一个Channel包含了一个ChannelPipeline而ChannelPipeline中又维护一个由ChannelHandlerContext组成的双向链表，
     * 并且每个ChannelHandlerContext中又关联着一个ChannelHandler
     * 入站事件和出站事件在一个双向链表中，入站事件会从链表头往后传递到最后一个入站的handler，出站事件会从尾往前传递到最后一个出站的handler，两种类型的handler互不干扰
     *
     * ChannelHandlerContext
     * 保存Channel相关的上下文信息，同时关联一个ChannelHandler对象
     * 即ChanelHandlerContext中包含一个具体的事件处理器ChannelHandler，同时ChannelHandlerContext中也绑定了对于的pipeline和Channel信息
     * 方便对ChannelHandler的调用
     *
     * ChannelPipeline中的元素并不是ChannelHandler，而是ChannelHandlerContext，ChannelHandlerContext内有对应的handler
     * 除此以外ChannelHandlerContext内还有其所在的pipeLine等信息
     * ChannelHandlerContext本身是一个双向链表，可以向上一个或下一个索引，这样在执行channelHandler时就是链式结构
     * ChannelHandlerContext内部会有inBound和outBound这两个flag值来标识当前handler是入站事件处理器还是出站事件处理器
     * ChannelHandlerContext常用API:
     * ChannelFuture close() 关闭通道
     * ChannelOutboundInvoker flush() 刷新
     * ChannelFuture writeAndFlush(Object msg) 将数据写入到ChannelPipeline中当前ChannelHandler的下一个ChannelHandler开始处理(出站)
     *
     * ChannelOption
     * Netty在创建Channel实例后，一般都需要设置ChannelOption参数
     * ChannelOption.SO_BACKLOG
     * 对应TCP/IP协议listen函数中的backlog参数，用来初始化服务器可连接队列的大小，服务器处理客户端连接请求是顺序处理的
     * 所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务器将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
     * ChannelOption.SO_KEEPALIVE
     * 一直保持连接活跃状态
     *
     * EventLoopGroup
     * EventLoopGroup是一组EventLoop的抽象，Netty为了更好的利用多核CPU资源，一般会有多个EventLoop同时工作，
     * 每个EventLoop维护着一个Selector实例
     * EventLoopGroup提供next接口，可以从组里面按照一定规则获取其中一个EventLoop来处理任务，在Netty服务器端编程中，
     * 我们一般需要提供两个EventLoopGroup:bossGroup,workerGroup
     * 通常一个服务器端口即一个ServerSocketChanel对应一个Selector和一个EventLoop线程，
     * BossEventLoop负责接收客户端的连接并将SocketChannel交给workerEventLoopGroup来做IO处理
     * 解释：
     * BossEventLoopGroup通常是一个单线程的EventLoop,
     * EventLoop里维护着一个注册了的ServerSocketChannel的selector实例,BossEventLoopGroup线程池里的EventLoop不断轮询Selector将连接事件分离出来
     * 通常是OP_ACCEPT事件。然后将接收到的SocketChannel交给WorkerEventLoopGroup,
     * WorkerEventLoopGroup会由next选择其中一个EventLoop来将这个SocketChanel注册到其维护的Selector并对其后续的IO事件进行处理
     * 常用方法:
     * 构造方法，默认构造方法生成的线程数是CPU数*2
     * shutdownGracefully()
     *
     * Unpooled类 非池化
     * Netty提供了一个专门操作缓冲区(ByteBuf)的工具类
     * copiedBuffer
     */

}
