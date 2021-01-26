package com.coderzoe.nettydevelop.class6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import sun.nio.cs.ext.GBK;

/** HttpObject表示客户端和服务端相互通讯的数据被封装成HttpObject 在上一步HttpServerCodec完成的
 * @author: yhs
 * @date: 2021/1/5 20:35
 */
public class Class11HttpHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //HttpRequest请求
        if(msg instanceof HttpRequest){

            //过滤个别请求
            String uri = ((HttpRequest) msg).uri();
            if("/favicon.ico".equals(uri)){
                System.out.println("请求了favicon.ico不做处理");
                return;
            }
            System.out.println("msg类型:"+msg.getClass());
            System.out.println("客户端地址:"+ctx.channel().remoteAddress());
            //回复信息给浏览器(Http协议),构造HttpResponse
            ByteBuf buf = Unpooled.copiedBuffer("hello,我是服务器", new GBK());
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,buf.readableBytes());
            //将构建好的response返回
            ctx.writeAndFlush(response);
        }
    }
}
