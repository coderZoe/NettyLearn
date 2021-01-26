package com.coderzoe;

/**
 * @author: yhs
 * @date: 2020/12/17 15:28
 */
public class Class9ServerSocketChannelAndSocketChannel {
    /**
     * 笔记
     * 1.ServerSocketChannel API:
     * ServerSocketChannel open()   //得到一个SocketChannel通道
     * ServerSocketChannel bind(SocketAddress address)  //设置服务器的端口信息
     * configureBlocking(boolean block)    //设置是否阻塞 false为非阻塞
     * accept() //接受一个连接，返回代表这个连接的通道对象(SocketChannel)
     * register(Selector selector,int ops)  //注册一个选择器，并选择监听事件
     *
     * 2.SocketChannel API:
     * SocketChannel open() //得到一个SocketChannel通道
     * SelectableChannel configureBlocking(boolean block)   //设置是否阻塞 false为非阻塞
     * boolean connect(SocketAddress address)   //连接服务器
     * boolean finishConnect()  //如果上面的方法连接失败。接下来就要通过该方法完成连接操作
     * int write(ByteBuffer src)    //往通道里写数据
     * int read(ByteBuffer dst)     //从通道中读出数据
     * SelectionKey register(Selector selector,int ops,Object att); //注册一个选择器并选择监听事件，最后可以设置一个共享的数据(类似于Session的setAttribute())
     * close()  //关闭通道
     */

    public static void main(String[] args) {

    }
}
