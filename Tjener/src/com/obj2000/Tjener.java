package com.obj2000;

import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

public class Tjener {
    private DatabaseKontroller dbKontroller;
    private ServerSocket server;
    private Socket connectedSocket;

    /**
     * Setter opp tjeneren vill sitte og vente på klienter
     * @param port
     */
    public Tjener(int port) {
        try {
            dbKontroller = new DatabaseKontroller("jdbc:sqlite:C:/sqlite3/database.db");
            server = new ServerSocket(port);

            while (true) {
                connectedSocket = server.accept();

                DataInputStream in = new DataInputStream(new BufferedInputStream(connectedSocket.getInputStream()));

                String line = in.readUTF();
                System.out.println("Fikk melding: " + line);
                handleMessage(line, in);


                connectedSocket.close();
                in.close();
            }
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    private void handleMessage(String msg, DataInputStream stream) throws IOException {
        String[] tokens = msg.split("!");
        if(tokens[0].equals("register")) {
            int length = stream.readInt();
            byte[] bytes = new byte[length];
            stream.read(bytes);
            parseRegister(tokens, bytes);
        }
        else if(tokens[0].equals("matcher")) {
            parseMatcher(tokens);
        }
        else if(tokens[0].equals("takontakt")) {
            parseTaKontakt(tokens);
        }
        else if(tokens[0].equals("sjekkid")) {
            parseSjekkId(tokens);
        }
        else if(tokens[0].equals("vislogg")) {
            parseLogg(tokens);
        }
        else if(tokens[0].equals("hentNavnTlf")){
            parseNavnTlf(tokens);
        }
        else if(tokens[0].equals("hentBilde")) {
            parseHentBilde(tokens);
        }
    }

    private void parseHentBilde(String[] argumenter) throws IOException {
        String idTilBilde = argumenter[1];
        respondToClient(dbKontroller.hentBilde(idTilBilde));
    }

    private void parseNavnTlf(String[] argumenter) throws IOException {
        String brukerId = argumenter[1];

        String data = dbKontroller.hentNavnOgTlf(brukerId);

        respondToClient(data);
    }

    private void parseRegister(String[] argumenter, byte[] bilde) throws IOException {
        UUID uuid = UUID.randomUUID();
        String bNr = uuid.toString();
        String navn =  argumenter[1];
        String kjønn = argumenter[2];
        String alder = argumenter[3];
        String interesser = argumenter[4];
        String bosted = argumenter[5];
        String tlf = argumenter[6];
        dbKontroller.opprettBruker(bNr, navn, kjønn, alder, interesser, bosted, tlf, bilde);

        respondToClient(uuid.toString());
    }

    private void parseMatcher(String[] argumenter) throws IOException {
        String fraBrukerId = argumenter[1];
        String fraAlder = argumenter[2];
        String tilAlder = argumenter[3];
        String kjønn = argumenter[4];


        ArrayList<String> data = dbKontroller.finnMatcher(fraAlder, tilAlder, kjønn);
        String tilKlient = finnBesteMatcher(data, fraBrukerId);
        respondToClient(tilKlient);
    }

    private void parseTaKontakt(String[] argumenter) {
        String fraBrukerId = argumenter[1];
        String tilBrukerId = argumenter[2];

        dbKontroller.loggDataForespørsel(fraBrukerId, tilBrukerId);

        System.out.println("Logget i DB");
    }

    private void parseSjekkId(String[] argumenter) throws IOException {
        String id = argumenter[1];

        String bruker = dbKontroller.hentEnBruker(id);

        if(bruker.equals(""))
            respondToClient("0");
        else
            respondToClient("1");
    }

    private void parseLogg(String[] argumenter) throws IOException {
        String fraBrukerId = argumenter[1];
        ArrayList<String> brukereSomHarInfo = dbKontroller.hvemHarInfoOmMeg(fraBrukerId);

        String ut = "";
        for(String bruker : brukereSomHarInfo) {
            ut += bruker + "#";
        }

        respondToClient(ut);
    }

    private void respondToClient(String msg) throws IOException {
        DataOutputStream out = new DataOutputStream(connectedSocket.getOutputStream());
        out.writeUTF(msg);
    }

    private void respondToClient(byte[] bilde) throws IOException {
        DataOutputStream out = new DataOutputStream(connectedSocket.getOutputStream());
        out.write(bilde.length);
        out.write(bilde);
    }

    private String finnBesteMatcher(ArrayList<String> matchData, String brukerId){

        ArrayList<Bruker> matcher = parseMatchListe(matchData);

        String brukerData = dbKontroller.hentEnBruker(brukerId);

        Bruker bruker = stringTilBruker(brukerData); //bruke string[] istedenfor object?

        for(Bruker match : matcher){
            regnMatchScore(match, bruker);
        }

        matcher.sort(Comparator.comparingDouble(Bruker::getScore));
        System.out.println(matcher.toString());

        if(matcher.size() > 10)
            matcher.subList(10, matcher.size()).clear();


        String ut = "";
        for(Bruker b : matcher)
            ut += b.toString() + "#";

        return ut;
    }

    private void regnMatchScore(Bruker match, Bruker bruker){
        double score = 0;
        double aldersVekt = 0.1;
        double bostedVekt = 10;

        double bAlder = Double.parseDouble(bruker.getAlder());
        String[] brukerInteresser = bruker.getInteresser().split("\\s*(,|\\s)\\s*");

        String[] matchInteresser = match.getInteresser().split("\\s*(,|\\s)\\s*");
        score += interesseMatch(matchInteresser, brukerInteresser);

        double mAlder = Double.parseDouble(match.getAlder());
        score -= Math.abs(mAlder - bAlder) * aldersVekt;

        if(match.getBosted().equals(bruker.getBosted()))
            score += bostedVekt;

        match.setScore(score);

    }

    private double interesseMatch(String[] matchInteresser, String[] brukerInteresser){
        double score = 0;
        double interesseVekt = 15;

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
