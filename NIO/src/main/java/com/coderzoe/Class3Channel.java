package com.coderzoe;

/**
 * @author: yhs
 * @date: 2020/12/12 17:07
 */
public class Class3Channel {
    /**
     * 笔记
     * 通道可以同时进行读写 流只能读或只能写
     * 通道可以实现异步读写数据
     * 通道可以从缓存中读取数据也可以将数据写入缓存
     *
     * 就像流一样，流分为输入/输出 文件/数组等等 Channel也是一样
     * Channel是一个接口其中他的比较重要的子类/子接口包括：
     * FileChannel DatagramChannel(UDP) SeverSocketChannel(TCP Server) SocketChannel(TCP Client)
     * ServerSocketChannel为抽象类 真正的类型是ServerSocketChannelImpl
     * 同理SocketChannel也是抽象类 真正的类型是SocketChannelImpl
     *
     * 1. FileChannel
     * public int read(ByteBuffer dst) 从通道中读取数据写到Buffer里
     * public int write(ByteBuffer src) 将缓冲区数据读取出来写到Channel里
     * public long transferForm(ReadableByteChannel src,long position,long count) 从目标通道中复制数据到当前通道
     * public long transferTo(long position,long count,WriteableByteChannel dst) 将数据从当前通道复制到目标通道中。
     */

}
