package com.obj2000;

import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
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
                System.out.println("Fikk melding: " + line);
                handleMessage(line);


                connectedSocket.close();
                in.close();
            }
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    private void handleMessage(String msg) throws IOException {
        String[] tokens = msg.split("!");
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
        String matches = "matcher-id-dame-22-ingen-bosted-id-dame-23-ingen-bosted2";

        respondToClient(matches);
    }

    private void parseTaKontakt(String[] argumenter) throws IOException {
        String fraBrukerId = argumenter[1];
        String tilBrukerId = argumenter[2];

        dbKontroller.loggDataForespørsel(fraBrukerId, tilBrukerId);

        String bruker = dbKontroller.hentEnBruker(tilBrukerId);

        respondToClient(bruker);
    }

    private void respondToClient(String msg) throws IOException {
        DataOutputStream out = new DataOutputStream(connectedSocket.getOutputStream());
        out.writeUTF(msg);
    }

    private void finnBesteMatcher(ArrayList<String> matchData, String brukerData){
        ArrayList<Bruker> matcher = parseMatchListe(matchData);
        Bruker bruker = stringTilBruker(brukerData);

        for(Bruker match : matcher){

        }

    }

    private double interesseMatch(String[] matchInteresser, String[] brukerInteresser){
        double score = 0;
        double interesseVekt = 5;

        for(int i = 0; i < brukerInteresser.length; i++)
            for(int j = 0; j < matchInteresser.length; j++){

                if(brukerInteresser[i].equals(matchInteresser[j]))
                    score += interesseVekt;
            }

        return score;
    }

    private ArrayList<Bruker> parseMatchListe(ArrayList<String> brukerData){
        ArrayList<Bruker> brukerListe = new ArrayList<>();
        for(String s : brukerData){
            brukerListe.add(stringTilBruker(s));
        }

        return brukerListe;
    }

    private Bruker stringTilBruker(String brukerData){
        Bruker bruker = new Bruker();
        String[] dataArr = brukerData.split("!");

        bruker.setId(dataArr[0]);
        bruker.setKjønn(dataArr[1]);
        bruker.setAlder(dataArr[2]);
        bruker.setInteresser(dataArr[3]);
        bruker.setBosted(dataArr[4]);

        return bruker;
    }

}
