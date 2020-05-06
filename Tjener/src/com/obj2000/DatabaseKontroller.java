package com.obj2000;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseKontroller {
    private String url;
    private Connection conn = null;

    /**
     * Oppretter databasen
     * @param url SQLite URL
     */
    public DatabaseKontroller(String url) {
        this.url = url;
        opprettDB();
    }

    private void opprettDB() {

        String sql1 = "CREATE TABLE IF NOT EXISTS bruker ( \n"
                + "    bNr varchar(36) PRIMARY KEY NOT NULL, \n"
                + "    navn varchar(25) NOT NULL, \n"
                + "    kjonn varchar(6) NOT NULL, \n"
                + "    alder INTEGER NOT NULL, \n"
                + "    interesser varchar(150) NOT NULL, \n"
                + "    bosted varchar(50) NOT NULL, \n"
                + "    tlf varchar(12) NOT NULL, \n"
                + "    bilde BLOB \n"
                + "    );";

        String sql2 = "CREATE TABLE IF NOT EXISTS logg (\n"
                + "    lNr INTEGER PRIMARY KEY NOT NULL,\n"
                + "    infoFraBruker_id varchar(36) NOT NULL, \n"
                + "    infoTilBruker_id varchar(36) NOT NULL \n"
                + "    );";

        try {
            conn = DriverManager.getConnection(this.url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql1);
            stmt.execute(sql2);

            System.out.println("Koblet til database");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    /** metode for å sette inn ny bruker i DB
     * @param bNr unik Id
     * @param navn bruker navn
     * @param kjonn bruker sitt kjonn
     * @param alder bruker sin alder
     * @param interesser bruker sin adresse
     * @param bosted bruker sitt bosted
     * @param tlf bruker sitt telefonnummer
     */
    public void opprettBruker(String bNr, String navn, String kjonn, String alder, String interesser, String bosted, String tlf, byte[] bildeBlob) {
        String sql = "INSERT INTO bruker(bNr, navn, kjonn, alder, interesser, bosted, tlf, bilde) \n"
                + "VALUES(?,?,?,?,?,?,?,?);";
        try {
            conn = DriverManager.getConnection(this.url);
            PreparedStatement  stmt = conn.prepareStatement(sql);
            stmt.setString(1, bNr);
            stmt.setString(2, navn);
            stmt.setString(3, kjonn);
            stmt.setString(4, alder);
            stmt.setString(5, interesser);
            stmt.setString(6, bosted);
            stmt.setString(7, tlf);
            stmt.setBytes(8, bildeBlob);
            stmt.execute();

            System.out.println("bruker registrert");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public byte[] hentBilde(String id) {
        byte[] data = new byte[0];
        String sql =  "SELECT bilde FROM bruker \n"
                + "WHERE bNr = '" + id + "'";

        try {
            conn = DriverManager.getConnection(this.url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                data = rs.getBytes(1);
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.getMessage();
        }

        return data;
    }

    /**
     * @param fraBruker bruker som det blir gitt data om
     * @param tilBruker bruker som sporr etter data
     */
    public void loggDataForesporsel(String fraBruker, String tilBruker){
        String sql = "INSERT INTO logg(infoFraBruker_id, infoTilBruker_id) \n"
                + "VALUES('" + fraBruker + "','" + tilBruker + "');";

        try {
            conn = DriverManager.getConnection(this.url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

            System.out.println("logget foresporsel om informasjon");
            conn.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }
    
    /**
     * @param id id til bruker
     * @return String med bruker
     */
    public String hentEnBruker(String id){
            String data = "";
            String sql =  "SELECT * FROM bruker \n"
                    + "WHERE bNr = '" + id + "'";

            try {
                conn = DriverManager.getConnection(this.url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                        data = rs.getString("bNr") + "!"
                            + rs.getString("kjonn") + "!"
                            + rs.getString("alder") + "!"
                            + rs.getString("interesser") + "!"
                            + rs.getString("bosted");
                }

                conn.close();
            } catch (SQLException e) {
                e.getMessage();
            }
            return data;
        }
    
    /**
     * @param id id'en til brukeren som onsker å vite hvem som har informasjon om vedkommende
     * @return Arraylist med resultater
     */
    public ArrayList<String> hvemHarInfoOmMeg(String id) {
        ArrayList<String> resultater = new ArrayList<>();
        String sql = " SELECT bNr, navn, alder, bosted FROM bruker AS b, logg AS l \n"
                + " WHERE l.infoTilBruker_id = '" + id
                + "' AND bNr = l.infoFraBruker_id";

        try {
            conn = DriverManager.getConnection(this.url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String data = rs.getString("bNr") + "!"
                        + rs.getString("navn") + "!"
                        + rs.getString("alder") + "!"
                        + rs.getString("bosted");
                resultater.add(data);
            }

            conn.close();
        } catch (SQLException e) {
            e.getMessage();
        }
        return resultater;
    }

    /**
     * @param fraAlder laveste alder
     * @param tilAlder hoyeste alder
     * @param kjonn kjonn (mann eller kvinne)
     * @param brukerId brukeren sin id for å ikke returnere brukeren som skal ha matcher
     * @return ArrayList med resultater
     */
    public ArrayList<String> finnMatcher(String fraAlder, String tilAlder, String kjonn, String brukerId){
        int min = Integer.parseInt(fraAlder);
        int max = Integer.parseInt(tilAlder);
        ArrayList<String> matcher = new ArrayList<>();
        String sql =  "SELECT * FROM bruker \n"
                    + "WHERE (alder BETWEEN " + min + " AND " + max + ")"
                    + "AND kjonn = '" + kjonn + "'"
                    + "AND bNr NOT LIKE '" + brukerId + "'";

        try {
            conn = DriverManager.getConnection(this.url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String data = rs.getString("bNr") + "!"
                        + rs.getString("kjonn") + "!"
                        + rs.getString("alder") + "!"
                        + rs.getString("interesser") + "!"
                        + rs.getString("bosted");
                matcher.add(data);
            }

            conn.close();
        } catch (SQLException e) {
            e.getMessage();
        }

        return matcher;
    }

    /**
     * @param id bruker id
     * @return String med navn og tlf
     */
    public String hentNavnOgTlf(String id){
        String data = "";
        String sql =  "SELECT navn, tlf FROM bruker \n"
                + "WHERE bNr = '" + id + "'";

        try {
            conn = DriverManager.getConnection(this.url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                data = rs.getString("navn") + "!"
                        + rs.getString("tlf");
            }

            conn.close();
        } catch (SQLException e) {
            e.getMessage();
        }

        return data;
    }

    /**
     * @return Test som viser alle brukere i DB
     */
    public ArrayList<String> visAlleBrukere(){
        ArrayList<String> resultater = new ArrayList<>();
        String sql = "SELECT * FROM bruker";

        try {
            conn = DriverManager.getConnection(this.url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String data = rs.getString("navn") + " "
                        + rs.getString("alder") + " "
                        + rs.getString("bosted") + " "
                        + rs.getString("kjonn") + " "
                        + rs.getString("bNr");
                resultater.add(data);
            }

            conn.close();
        } catch (SQLException e) {
            e.getMessage();
        }
        return resultater;
    }

    /**
     * @return Test som viser alle logg rader i DB
     */
    public ArrayList<String> visLogg(){
        ArrayList<String> resultater = new ArrayList<>();
        String sql = "SELECT * FROM logg";

        try {
            conn = DriverManager.getConnection(this.url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String data = rs.getString("infoFraBruker_id") + " "
                        + rs.getString("infoTilBruker_id");
                resultater.add(data);
            }

            conn.close();
        } catch (SQLException e) {
            e.getMessage();
        }

        return resultater;
    }
}
