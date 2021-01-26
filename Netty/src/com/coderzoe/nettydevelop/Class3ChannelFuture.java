package com.coderzoe.nettydevelop;

/**
 * @author: yhs
 * @date: 2020/12/23 10:01
 */
public class Class3ChannelFuture {
    /**
     * 笔记：
     * 异步模型：
     * 1. 异步的概念与同步是相对的，当一个异步调用发出后，调用者不能立刻得到返回。实际处理这个调用的组件在完成后，通过状态、通知和回调来通知调用者。
     * 2.Netty中的操作是异步的，包括Bind,Write,Connect等操作会简单的返回一个ChannelFuture
     * 3.调用者不能立刻得到结果，而是通过Future-Listener机制，用户可以方便的主动获取或者通过通知机制得到IO操作的结果
     * 4.Netty的异步模型是建立在future和callback之上的。举个例子：假设我们有一个function执行非常耗时，等待function的返回显然不合适，
     * 那么就可以在调用function时返回一个future，后续可以通过future监控发方法function的处理过程
     * Future说明：
     * 1. 表示异步执行结果，可以通过它提供的方法来检查执行是否完成，比如检索计算等
     * 2. ChannelFuture是一个接口，public interface ChannelFuture extends Future<Void>，我们可以添加监听器，
     * 当监听的事件发声时就会通知到监听器
     *
     * Netty中所有的IO操作都是异步的，不能立刻得知消息是否被正确的处理，但是可以过一会等他执行完成后或直接注册一个监听，
     * 具体的实现就是通过Future和ChannelFuture，他们可以注册一个监听，当操作成功或失败时监听器会自动触发注册的监听事件
     */
}
