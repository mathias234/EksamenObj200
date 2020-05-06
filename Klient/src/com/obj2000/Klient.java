package com.obj2000;

import java.net.*;
import java.io.*;

public class Klient {
    private static String ipAddress;
    private static int port;

    private static Socket socket;

    /**
     * Sender en melding til tjeneren, bruk "receiveMessage()" for a se svaret
     * @param msg
     * @throws UnknownHostException
     * @throws IOException
     */
    public static void sendMessage(String msg) throws UnknownHostException, IOException {
        socket = new Socket(ipAddress, port);

        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(msg);
    }

    /**
     * Sender en melding og et bilde til tjeneren
     * @param msg meldingen som skal sendes
     * @param bilde bilde som skal sendes
     * @throws UnknownHostException
     * @throws IOException
     */
    public static void sendMessageMedBilde(String msg, byte[] bilde) throws UnknownHostException, IOException {
        socket = new Socket(ipAddress, port);

        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(msg+ "!" + bilde.length);

        out.write(bilde);
    }

    /**
     * Sjekker om tjeneren svarte pa den siste "sendMessage"
     * @return
     * @throws IOException
     */
    public static String receiveMessage() throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        String message = in.readUTF();
        socket.close();
        return message;
    }

    /**
     * Sjekker om tjeneren svartre pa den siste sendMessage, og at svarte med et bilde
     * @return
     * @throws IOException
     */
    public static byte[] receiveBilde() throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        int length = Integer.parseInt(in.readUTF());
        byte[] bytes = new byte[length];
        in.read(bytes);
        socket.close();
        return bytes;
    }

    public static void setIp(String ip) {
        ipAddress = ip;
    }

    public static void setPort(int p) {
        port = p;
    }
}
