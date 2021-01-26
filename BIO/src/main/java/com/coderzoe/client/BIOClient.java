package com.coderzoe.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author: yhs
 * @date: 2020/10/15 20:09
 */
public class BIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",6666);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF("hello");
        dataOutputStream.flush();
        dataOutputStream.close();
        socket.close();
    }
}
