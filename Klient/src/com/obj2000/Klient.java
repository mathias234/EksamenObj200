package com.obj2000;

import java.net.*;
import java.io.*;

public class Klient {
    private String address;
    private int port;

    private Socket socket;

    // constructor to put ip address and port
    public Klient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void sendMessage(String message) throws UnknownHostException, IOException {
        socket = new Socket(address, port);

        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(message);
    }

    public String receiveMessage() throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        String message = in.readUTF();
        socket.close();
        return message;
    }


}
