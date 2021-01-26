package com.coderzoe;

import java.nio.channels.SelectionKey;

/**
 * @author: yhs
 * @date: 2020/12/17 15:01
 */
public class Class8SelectionKey {
    /**
     * 笔记:
     * SelectionKey表示Selector和网络通道的注册关系，共四种：
     * Read 1<<0    1
     * Write 1<<2   4
     * Connect 1<<4 8
     * Accept 1<<8  16
     * API:
     * Selector selector()   //得到与之关联的Selector对象
     * selectableChanel channel()   //得到与之关联的通道
     * Object attachment() //得到与之关联的共享数据
     * SelectionKey interestOps(int ops)    //设置或改变监听事件
     * isAcceptable()   //是否是Accept事件
     * isReadable()     //是否是可读事件
     * isWritable()     //是否是可写事件
     */

    public static void main(String[] args) {
    }
}
