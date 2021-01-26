package com.coderzoe;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: yhs
 * @date: 2020/12/12 17:26
 */
public class Class3_1FileChannel {
    public static void main(String[] args) throws IOException {
        write();
        read();
        copy();
    }

    public static void write() throws IOException {
        String str = "hello world";
        FileOutputStream outputStream = new FileOutputStream("abc.txt");
        //真实类型是FileChannelImpl
        FileChannel fileChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(str.getBytes().length);
        buffer.put(str.getBytes());
        buffer.flip();  //记得反转
        fileChannel.write(buffer);
        outputStream.close();
    }

    public static void read() throws IOException {
        FileInputStream inputStream = new FileInputStream("abc.txt");
        FileChannel channel = inputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
        channel.read(buffer);

        System.out.println(new String(buffer.array()));
        inputStream.close();
    }

    public static void copy() throws IOException{
        FileInputStream inputStream = new FileInputStream("abc.txt");
        FileOutputStream outputStream = new FileOutputStream("xyz.txt");
        FileChannel inputStreamChannel = inputStream.getChannel();
        FileChannel outputStreamChannel = outputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int) inputStreamChannel.size());
        while (inputStreamChannel.read(buffer)!=-1){
            // do nothing
        }
        buffer.flip();
        outputStreamChannel.write(buffer);
    }
}
