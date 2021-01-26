package com.coderzoe.nettydevelop.class5;

/**
 * @author: yhs
 * @date: 2020/12/23 11:36
 */
public class BootStrapAndServerBootStrap {
    /**
     * 笔记
     * BootStrap是引导的意思 一个Netty应用通常由一个BootStrap开始 主要作用是配置整个Netty程序，串联各种组件
     * 常见方法：
     * group()      //设置EventLoopGroup
     * channel()    //声明服务器端的通道的实现(类型)
     * option()     //用来给ServerChannel添加配置
     * childOption()//用来给接收到的通道添加配置
     * childHandler()   //用来给接收到的通道设置业务处理类
     * bind()       //用来设置服务器启动端口
     * connect()    //用来设置连接服务器
     */
}
