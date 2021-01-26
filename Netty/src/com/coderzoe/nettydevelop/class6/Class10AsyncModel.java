package com.coderzoe.nettydevelop.class6;

/**
 * 异步模型
 * @author: yhs
 * @date: 2021/1/5 19:53
 */
public class Class10AsyncModel {
    /**
     * 笔记
     * 异步的概念与同步是相对的，当一个异步调用过程调用发出后，调用者不能立刻得到返回结果。
     * 实际处理这个调用的组件的在完成后，通过状态，通知和回调来通知调用者。
     * Netty中的IO操作是异步的，包括Bind,Write,Connect等操作会简单的返回一个ChannelFuture
     * 调用者并不能立刻获得结果，而是通过Future-Listener机制，用户可以方便的主动获取或者通过通知机制获得IO操作结果
     * Netty的异步模型是建立在Future和Callback之上的，callback就是回调。
     * 至于Future，它的核心思想是：假设一个方法Fun，计算过程可能非常耗时，等待fun返回显然不合适。那么可以在调用fun的时候，
     * 立马返回一个Future，后续可以通过Future去监控方法fun的处理过程
     *
     * Future说明：
     * 表示异步的执行结果，可以通过它提供的方法来检测执行是否完成，比如检索计算等等
     * ChannelFuture是个接口，我们可以添加监听器，当监听的事件发生时，就会通知到监听器
     * 在使用Netty进行编程时，拦截操作和转换出入站的数据只需要您提供callback或利用future即可。
     * 这使得链式操作简单高效，并有助于编写可重用的通用的代码。
     * Netty框架的目标就是让你的业务逻辑从网络基础应用编码中分离出来，解脱出来
     *
     * Future-Listener机制 : Future 表示当前不知道结果 , 在未来的某个时刻才知道结果 , Listener 表示监听操作 , 监听返回的结果;
     * 1.当Future对象刚刚创建的时候，处于非完成状态，调用者可以通过返回的ChannelFuture来获取操作执行的状态，注册监听函数来执行完成后的操作
     * 2.常见操作如下:
     * 通过isDone方法来判断当前操作是否完成
     * 通过isSuccess方法来判断已完成的当前操作是否成功
     * 通过getCause方法来获得已完成的当前操作失败的原因
     * 通过isCancelled方法来判断已完成的当前操作是否被取消
     * 通过addListener方法来注册监听器，当操作已经完成，将会通知指令监听器。
     */

    public static void main(String[] args) {
    }
}
