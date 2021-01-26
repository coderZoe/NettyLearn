package com.coderzoe;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

/**
 * @author: yhs
 * @date: 2020/12/13 16:40
 */
public class Class6Selector {
    /**
     * 选择器
     * Java NIO用一个线程处理多个客户端的连接 这样就会使用Selector
     *
     * Selector能够检测多个注册的通道上是否有事件发生(可以看到多个Channel以事件的方式可以注册到同一个Selector)
     * 如果有事件发生，便获取事件然后针对每个事件进行相应的处理，这样就可以一个线程管理多个通道，也就是管理多个连接请求
     *
     * 只有在通道真正有读写数据发生时，才会进行读写，这样就大大减少了系统的开销，并且不必为每个连接请求创建一个线程，不用去维护多个线程
     * 避免了多线程之间的上下文切换导致的开销
     *
     * 当线程从某客户端Socket通道读写数据时，若无数据可用，则该线程可以进行其他任务，
     * 线程通常将非阻塞IO的空闲时间用于其他通道上执行IO操作，所以单独的线程可以管理多个输入和输出通道
     * 由于读写操作都是非阻塞的，这就可以充分提升IO线程的运行效率，避免由于频繁IO阻塞导致的线程挂起
     *
     * Select选择器会调用select()方法，这个方法的作用是查询到所有的有事件响应的通道。
     * 也即select方法其实是查询现在所注册的所有通道里，有哪些通道现在有需要处理的事件，如果有，就加入到集合，
     * 函数返回所有待处理的Channel,但不同的是，select()返回的不是Channel的集合，而是SelectionKey的集合，
     * 这个SelectionKey可以认为是对Channel的一层封装，其中聚合了Channel
     * public int select(long timeout)  //监控所有注册的通道，当其中有IO操作进行时，
     * 将对应的SelectionKey加入到集合中并返回，参数用来设置超时时间
     * public int select() 无参 会一直阻塞知道监听到至少一个可执行的通道
     * public int selectNow() 不阻塞 直接返回 有就是有 没有就是没有
     *
     * public Set<SelectionKey> selectedKeys() 得到所有注册的Channel(无论是否有事件发生)
     * public Selector wakeup() 当调用select()后一直阻塞时，可以在别处调用selector.wakeup()唤醒selector
     *
     *
     * NIO网络编程：
     * 1. 当客户端连接时，通过ServerSocketChannel得到SocketChannel(一个客户端通道)
     * 2. 将SocketChannel注册到Selector上,register(Selector selector,int ops) 每注册一次后返回一个SelectionKey
     * 3. Selector此时可以进行监听 select()，得到发生事件的通道个数
     * 其中所关心的事件，事件共分为四种：
     * read,write,connect(连接创建好了),accept(接收到了一个连接)
     * 4. 当进行完select()后，可以再得到有事件发生的SelectionKey,然后通过SelectionKey得到Channel本身
     * 5. 然后通过得到的Channel完成通道的业务处理(读写等操作)
     *
     *
     */

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        selector.select(300);
        int select = selector.select();
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
    }
}
