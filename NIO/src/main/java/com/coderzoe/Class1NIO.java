package com.coderzoe;

/**
 * @author: yhs
 * @date: 2020/12/12 15:57
 */
public class Class1NIO {
    /**
     * NIO三大核心：Channel,Buffer,Selector
     * NIO是同步非阻塞的，这里的非阻塞就是指BIO里第二步阻塞(阻塞等待可读数据)
     * 对于NIO而言，如果当前通道无可读数据，就什么都不做，而不是保持线程阻塞
     *
     * BIO与NIO比较
     * (1).BIO以流的方式处理数据,NIO以块(缓存区/Buffer)的方式处理数据，块IO效率比流IO高很多
     * (2).BIO是阻塞的,NIO是非阻塞的
     * (3).BIO基于字节流和字符流进行操作，而NIO基于Channel和Buffer进行操作，数据总是从Channel读取到Buffer中
     *     或从Buffer写到Channel中 Selector用于监听多个Channel事件(连接请求，数据到达等)，因此使用单个线程可以监听多个客户端的连接
     *
     * Selector Buffer Channel三者之间的关系
     * (1) 每一个Channel对应一个Buffer
     * (2) 一个Selector对应一个线程 一个线程对应多个Channel
     * (3) 多个Channel可以注册到一个Selector上
     * (4) 程序切换到哪个Channel是由事件(Event)决定的
     * (5) Selector会根据不同的事件在各个通道上切换
     * (6) Buffer就是一个内存块，底层是有一个数组的 真实数据是放在数组里的
     * (7) 数据的读取和写入是通过Buffer的,BIO中是输入或输出流而不能双向的，但NIO的buffer是可以读也可以写的(flip切换)
     * (8) Channel是双向的
     *
     */
}
