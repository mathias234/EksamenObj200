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
        if(tokens[0].equals("register")) {
            parseRegister(tokens);
        }
        else if(tokens[0].equals("matcher")) {
            parseMatcher(tokens);
        }
        else if(tokens[0].equals("takontakt")) {
            parseTaKontakt(tokens);
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

        respondToClient(uuid.toString());
    }

    private void parseMatcher(String[] argumenter) throws IOException {
        String fraBrukerId = argumenter[1];
        String fraAlder = argumenter[2];
        String tilAlder = argumenter[3];
        String kjønn = argumenter[4];

        // Magisk system som matcher basert på parameterene
        String matches = "matcher-id-dame-22-ingen-bosted-id-dame-23--ingen-bosted2";

        respondToClient(matches);
    }

    private void parseTaKontakt(String[] argumenter) throws IOException {
        String fraBrukerId = argumenter[1];
        String tilBrukerId = argumenter[2];

        respondToClient("");
    }

    private void respondToClient(String msg) throws IOException {
        DataOutputStream out = new DataOutputStream(connectedSocket.getOutputStream());
        out.writeUTF(msg);
    }
}
