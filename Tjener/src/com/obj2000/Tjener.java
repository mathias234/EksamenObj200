package com.obj2000;

import java.net.*;
import java.io.*;

public class Tjener {
    private ServerSocket server;
    private Socket connectedSocket;

    public Tjener(int port) {
        try {
            server = new ServerSocket(port);

            while (true) {
                connectedSocket = server.accept();

                DataInputStream in = new DataInputStream(new BufferedInputStream(connectedSocket.getInputStream()));

                String line = in.readUTF();
                handleMessage(line);


                connectedSocket.close();
                in.close();
            }
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    private void handleMessage(String msg) throws IOException {
        System.out.println("Msg: " + msg);
        respondToClient("Server heard: " + msg);
    }

    private void respondToClient(String msg) throws IOException {
        DataOutputStream out = new DataOutputStream(connectedSocket.getOutputStream());
        out.writeUTF(msg);
    }
}
