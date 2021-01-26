package com.coderzoe;

/**
 * @author: yhs
 * @date: 2020/12/18 16:57
 */
public class Class1Reactor {
    /**
     * 针对传统阻塞IO服务模型的2个缺点，Reactor模型解决方案
     * 1. 基于IO复用模型: 多个连接共用一个阻塞对象，应用程序只需要在一个阻塞对象等待，无需阻塞等待所有连接。
     *    当某个连接有新的数据可以处理时，操作系统通知应用程序，线程从阻塞状态返回，开始进行业务处理。
     * 2. 基于线程池复用线程资源，不必再为每个连接创建线程，将连接完成后的业务处理任务分配给线程进行处理，一个线程可以处理多个连接的业务。
     *
     * Reactor模式:通过一个或多个输入同时传给服务器的模式(基于事件驱动)，服务器端程序处理传入的多个请求，并将它们同步分派到相应的线程，
     * 因此Reactor模式也叫Dispatcher模式(分发者模式),Reactor模式使用了IO复用监听事件，
     * 收到事件后，分发给某个线程执行，这点就是网络服务高并发处理的关键
     *
     * Reactor模式核心组成:
     * 1. Reactor:Reactor是在一个是在一个单独的线程中运行，负责监听和分发事件 举例：公司电话接线员接听电话，并将线路转给适当的联系人
     * 2. Handles:处理程序执行IO事件要完成的实际事件。类似于客户想要与之交谈中的公司业务员工,
     *    Reactor通过调度相应的程序来响应IO事件，处理程序执行非阻塞操作
     *
     * Reactor模式分类:
     * 1.单Reactor单线程
     * 2.单Reactor多线程
     * 3.主从Reactor多线程
     *
     * 单Reactor单线程：
     * 1. Select就是前面IO复用模型介绍的标准网络编程API，可以实现应用程序通过一个阻塞对象监听多路连接请求
     * 2. Reactor对象通过Select监听客户端请求事件，收到事件后通过Dispatch进行分发
     * 3. 如果是建立连接请求事件，则由Acceptor通过Accept处理连接请求，然后创建一个Handler对象处理完成连接完成后的业务处理
     * 4. 如果不是建立连接事件。则Reactor会分发调用连接对于的Handler来响应
     * 5. Handler会完成Read->业务处理->Send的完整业务流程
     * 问题:如果客户端连接数量过多，将无法支撑。前面的NIO群聊系统就是如此。
     *
     * 单Reactor多线程
     * 1. Reactor对象通过select监控客户端请求事件，收到事件后，通过Dispatch进行分发
     * 2. 如果是建立连接的请求，则由Acceptor通过Accept处理连接请求，然后创建一个Handler对象，处理完成连接后的业务处理
     * 3. 如果不是连接请求，则由Reactor分发调用连接对应的handler来处理
     * 4. handler只负责响应事件，不做具体的业务处理。通过read方法读取数据后，会分发给后面的worker线程池的某个线程来处理业务。(也就是说可以看到
     * dispatcher并不是起了一个线程来分发给handler处理事件，而是还在主线程里,dispatcher分发给handler后，handler来开启线程处理实际的业务。
     * 即线程在handler里开启，不在dispatcher时开启)
     * 5. worker线程池会分配一个独立的线程完成真正的业务，同时把业务处理过后的结果返回给Handler，并将结果返回。
     * 6. handler收到响应后，通过send将结果返回给client
     * 优点:可以充分的利用多核CPU的处理能力
     * 缺点:多线程数据共享和访问比较复杂 Reactor处理了所有的事件请求，而Reactor本身是在单线程运行的，这在高并发场景容易出现性能瓶颈。
     *
     * 主从Reactor多线程
     * Reactor在多线程中运行
     * 1.分为主从Reactor线程。Reactor主线程:mainReactor 通过select监听连接事件。收到事件后，通过Acceptor处理连接事件
     * 2.当Acceptor处理连接事件后,mainReactor会将连接分配给从Reactor(SubReactor,可能包含多个subReactor)
     * 3.subReactor将连接加入到连接队列进行监听，并创建Handler进行各种事件处理
     * 4.当有新事件发生时,subReactor会调用对应的handler进行处理
     * 5.handler读取数据，分发给后面的worker线程池，线程池会分配一个独立的线程进行业务处理
     * 6.handler收到响应的结果后，再通过send将结果返回client
     * 7.mainReactor可以对应多个subReactor
     * 优点:主从Reactor分工明确，数据交互简单，父线程只处理连接请求，从线程完成后续的业务处理
     * 缺点:编程复杂度高
     */


}
