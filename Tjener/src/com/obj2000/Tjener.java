package com.obj2000;

import java.net.*;
import java.io.*;
import java.util.UUID;

public class Tjener {
    private DatabaseKontroller dbKontroller;
    private ServerSocket server;
    private Socket connectedSocket;

    public Tjener(int port) {
        try {
            dbKontroller = new DatabaseKontroller("jdbc:sqlite:C:/sqlite3/database.db");
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
        String[] tokens = msg.split("-");
        System.out.println("handleMessage");
        if(tokens[0].equals("register")) {
            System.out.println("register");
            parseRegister(tokens);
        }
        else if(tokens[0].equals("matcher")) {
            parseMatcher(tokens);
        }
    }

    private void parseRegister(String[] argumenter) throws IOException {
        // String bNr, String navn, String kjønn, String alder, String interesser, String bosted, String tlf)
        UUID uuid = UUID.randomUUID();
        String bNr = uuid.toString();
        String navn =  argumenter[1];
        String kjønn = argumenter[2];
        String alder = argumenter[3];
        String interesser = argumenter[4];
        String bosted = argumenter[5];
        String tlf = argumenter[6];
        dbKontroller.opprettBruker(bNr, navn, kjønn, alder, interesser, bosted, tlf);
        System.out.println("parseRegister");
        respondToClient(uuid.toString());

    }

    private void parseMatcher(String[] argumenter) throws IOException {

    }

    private void respondToClient(String msg) throws IOException {
        DataOutputStream out = new DataOutputStream(connectedSocket.getOutputStream());
        out.writeUTF(msg);
    }
}
