package com.coderzoe.nettydevelop.class8chatroom2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: yhs
 * @date: 2021/1/12 20:39
 */
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    /**
     * map Key所连接的Channel,value为这个Channel对应的用户
     * 用于标识用户登录成功
     */
    private static Map<Channel,User> userMap = new HashMap<>();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        if(userMap.get(channel)==null&&msg.startsWith("注册:")){
            String name = msg.substring(3,msg.indexOf(","));
            String password = msg.substring(msg.indexOf(","));
            if(UserDataBase.getUserByName(name).isPresent()){
                channel.writeAndFlush(simpleDateFormat.format(new Date())+" 抱歉，该用户名已被注册，请选择其他用户名");
            }else {
                User user = new User(name,password);
                UserDataBase.signUp(user);
                channel.writeAndFlush(simpleDateFormat.format(new Date())+" 注册成功，请重新登录");
            }
        }else if(userMap.get(channel)==null&&msg.startsWith("登录:")){
            String name = msg.substring(3,msg.indexOf(","));
            String password = msg.substring(msg.indexOf(","));
            Optional<User> user = UserDataBase.getUserByNameAndPassword(name,password);
            //用户不存在
            if(!user.isPresent()){
                channel.writeAndFlush(simpleDateFormat.format(new Date())+" 抱歉，不存在该用户，登陆失败\n"+
                        "请选择重新登录或注册\n");
            }else {
                User user1 = user.get();
                this.userMap.put(channel,user1);
                channel.writeAndFlush(simpleDateFormat.format(new Date())+" 恭喜您"+user1.getName()+"登录成功\n"+
                        "与其他用户私聊请选择@用户名:私聊内容\n"+
                        "公告频道聊天直接输入即可");
                for(Map.Entry<Channel,User> userEntry:userMap.entrySet()){
                    if(!userEntry.getKey().equals(channel)){
                        userEntry.getKey().writeAndFlush(simpleDateFormat.format(new Date())+"  客户"+ user1.getName()+"上线");
                    }
                }

            }
        } else if(userMap.get(channel)==null){
            channel.writeAndFlush(simpleDateFormat.format(new Date())+" 您尚未注册或登录\n"+
                    "如果登录请输入 登录:用户名,密码\n"+
                    "如果注册请输入 注册:用户名,密码\n");
        }else { //用户已经登录
            //私聊
            if(msg.startsWith("@")){
                String userName = msg.substring(1,msg.indexOf(":"));
                String message = msg.substring(msg.indexOf(":")+1);
                handleSingleChat(channel,userName,message);
            }else { //群聊
                for(Map.Entry<Channel,User> userEntry:userMap.entrySet()){
                    if(!userEntry.getKey().equals(channel)){
                        userEntry.getKey().writeAndFlush(simpleDateFormat.format(new Date())+"  客户"+ userMap.get(channel).getName()+"对大家说:"+msg+"\n");
                    }
                }
            }
        }

    }

    public void handleSingleChat(Channel self,String userName,String msg){
        Optional<User> userOptional = UserDataBase.getUserByName(userName);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            Channel dstChannel = null;
            for(Map.Entry<Channel,User> userEntry:userMap.entrySet()){
                if(userEntry.getValue().equals(user)){
                    dstChannel = userEntry.getKey();
                    break;
                }
            }
            if(dstChannel!=null){
                dstChannel.writeAndFlush(simpleDateFormat.format(new Date())+" 用户"+userMap.get(self).getName()+"对您私聊:\n"+
                        msg);
            }else {
                self.writeAndFlush(simpleDateFormat.format(new Date())+" 抱歉您私聊的用户不在线");
            }
        }else {
            self.writeAndFlush(simpleDateFormat.format(new Date())+" 抱歉您私聊的用户不存在");
        }
    }


    /**
     * 表示channel处于活动的状态 上线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(simpleDateFormat.format(new Date())+"  客户"+ctx.channel().remoteAddress()+"上线");
    }

    /**
     * channel处于非活动状态 离线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(simpleDateFormat.format(new Date())+"  客户"+ctx.channel().remoteAddress()+"离线");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channel.writeAndFlush(simpleDateFormat.format(new Date())+" 连接成功，请选择登录或注册\n"+
                "如果登录请输入 登录:用户名,密码\n"+
                "如果注册请输入 注册:用户名,密码\n");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        userMap.remove(channel);
        for(Map.Entry<Channel,User> userEntry:userMap.entrySet()){
            userEntry.getKey().writeAndFlush(simpleDateFormat.format(new Date())+"  客户"+ userMap.get(channel).getName()+"离开了群聊\n");
        }
    }

    /**
     * 异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
