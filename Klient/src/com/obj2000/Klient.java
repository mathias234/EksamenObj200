package com.obj2000;

import java.net.*;
import java.io.*;

public class Klient {
    private static String ipAddress;
    private static int port;

    private static Socket socket;

    /**
     * Sender en melding til tjeneren, bruk "receiveMessage()" for å se svaret
     * @param message
     * @throws UnknownHostException
     * @throws IOException
     */
    public static void sendMessage(String message) throws UnknownHostException, IOException {
        socket = new Socket(ipAddress, port);

        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(message);
    }

    /**
     * Sjekker om tjeneren svarte på den siste "sendMessage"
     * @return
     * @throws IOException
     */
    public static String receiveMessage() throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        String message = in.readUTF();
        socket.close();
        return message;
    }

    public static void setIp(String ip) {
        ipAddress = ip;
    }

    public static void setPort(int p) {
        port = p;
    }
}
