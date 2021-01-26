package com.coderzoe.nettydevelop.class7bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author: yhs
 * @date: 2021/1/12 20:05
 */
public class Class1NettyByteBuf {
    public static void main(String[] args) {
        //创建ByteBuf对象，申请了一个大小为10Byte空间的内存
        int capacity = 10;
        ByteBuf byteBuf = Unpooled.buffer(capacity);
        for(int i= 0; i < capacity; i++ ){
            byteBuf.writeByte(i);
        }
        while (byteBuf.isReadable()){
            System.out.println(byteBuf.readByte());
        }

        //创建ByteBuffer
        ByteBuf buffer = Unpooled.copiedBuffer("hello,world", CharsetUtil.UTF_8);
        CharSequence charSequence = buffer.getCharSequence(0, 4, CharsetUtil.UTF_8);
        System.out.println(buffer);

        /**
         * ByteBuf的API
         * hasArray
         * array() 返回byte数组
         * CharSequence getCharSequence(int index,int length,Charset charset)
         */
    }
}
