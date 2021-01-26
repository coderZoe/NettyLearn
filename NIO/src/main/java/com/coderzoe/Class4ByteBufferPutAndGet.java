package com.coderzoe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ByteBuffer的put和get操作
 * @author: yhs
 * @date: 2020/12/13 9:20
 */
public class Class4ByteBufferPutAndGet {
    public static void main(String[] args) throws IOException {
        //test1();
        //test2();
        test3();
    }

    public static void test1(){

        //put要和get的类型保持一致
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putInt(100);
        buffer.putLong(9L);
        buffer.putChar('y');
        buffer.putShort((short) 256);

        buffer.flip();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
//        System.out.println(buffer.getLong());
        System.out.println(buffer.getShort());
    }

    //只读Buffer
    public static void test2(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        for(int i = 0; i < 1024; i++){
            buffer.put((byte) i);
        }

        buffer.flip();
        //变为只读Buffer java.nio.HeapByteBufferR
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
        //如果往里put数据 会报异常
        //java.nio.ReadOnlyBufferException
        readOnlyBuffer.put((byte) 1);
    }

    //MappedReadBuffer 可以让文件直接在内存中(堆外的内存)修改 操作系统不需要拷贝一次
    public static void test3() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("abc.txt", "rw");
        //获取对应的文件通道
        FileChannel channel = randomAccessFile.getChannel();

        //map()函数说明:
        //MapMode: FileChannel.MapMode.READ_WRITE 读写模式
        //position: 0 起始位置
        //size: 映射到内存的大小(字节),即可修改文件的大小
        //MappedByteBuffer实际类型为 DirectByteBuffer
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
        mappedByteBuffer.putChar('H');
        mappedByteBuffer.putChar('E');
        mappedByteBuffer.putChar('L');
        mappedByteBuffer.putInt(100);

        randomAccessFile.close();
    }
}
