package com.obj2000;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseKontroller {
    private String url;
    private Connection conn = null;

    public DatabaseKontroller(String url) {
        this.url = url;
        opprettDB();
    }

    /**
     * Metode for å lage nye tabeller i DB, samt opprette en om det ikke finnes en fra før
     */
    private void opprettDB() {

        String sql1 = "CREATE TABLE IF NOT EXISTS bruker ( \n"
                + "    bNr INTEGER PRIMARY KEY NOT NULL, \n"
                + "    navn varchar(25) NOT NULL, \n"
                + "    kjønn varchar(6) NOT NULL, \n"
                + "    alder INTEGER NOT NULL, \n"
                + "    interesser varchar(150) NOT NULL, \n"
                + "    bosted varchar(50) NOT NULL, \n"
                + "    tlf varchar(12) NOT NULL \n"
                + "    );";

        String sql2 = "CREATE TABLE IF NOT EXISTS logg (\n"
                + "    lNr INTEGER PRIMARY KEY NOT NULL,\n"
                + "    infoFraBruker_id INTEGER NOT NULL, \n"
                + "    infoTilBruker_id INTEGER NOT NULL \n"
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
     * @param bNr // unik Id
     * @param navn // bruker navn
     * @param kjønn // bruker sitt kjønn
     * @param alder // bruker sin alder
     * @param interesser // bruker sin adresse
     * @param bosted // bruker sitt bosted
     * @param tlf // bruker sitt telefonnummer
     */
    public void opprettBruker(String bNr, String navn, String kjønn, String alder, String interesser, String bosted, String tlf) {
        String sql = "INSERT INTO bruker(bNr, navn, kjønn, alder, interesser, bosted, tlf) \n"
                + "VALUES('" + bNr + "','" + navn + "','" + kjønn + "','" + alder + "','" + interesser + "', '" + bosted + "', '" + tlf + "');";
        try {
            conn = DriverManager.getConnection(this.url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

            System.out.println("bruker registrert");
            conn.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    /**
     * @param fraBruker // bruker som det blir gitt data om
     * @param tilBruker // bruker som spørr etter data
     */
    public void loggDataForespørsel(int fraBruker, int tilBruker){
        String sql = "INSERT INTO logg(infoFraBruker_id, infoTilBruker_id) \n"
                + "VALUES('" + fraBruker + "','" + tilBruker + "');";

        try {
            conn = DriverManager.getConnection(this.url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

            System.out.println("logget forespørsel om informasjon");
            conn.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    /**
     * @param id // id'en til brukeren som ønsker å vite hvem som har informasjon om vedkommende
     * @return // Arraylist med resultater
     */
    public ArrayList<String> hvemHarInfoOmMeg(int id) {
        ArrayList<String> resultater = new ArrayList<>();
        String sql = " SELECT navn, alder, bosted FROM bruker AS b, logg AS l \n"
                + " WHERE l.infoFraBruker_id = " + id
                + " AND bNr = l.infoTilBruker_id";

        try {
            conn = DriverManager.getConnection(this.url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String data = rs.getString("navn") + " "
                        + rs.getString("alder") + " "
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
     * @param fraAlder //laveste alder
     * @param tilAlder // høyeste alder
     * @param kjønn // kjønn (mann eller kvinne)
     * @return // ArrayList med resultater
     */
    public ArrayList<String> finnMatcher(int fraAlder, int tilAlder, String kjønn){

        ArrayList<String> matcher = new ArrayList<>();
        String sql =  "SELECT * FROM bruker \n"
                    + "WHERE (alder BETWEEN " + fraAlder + " AND " + tilAlder + ")"
                    + "AND kjønn = '" + kjønn + "'";

        try {
            conn = DriverManager.getConnection(this.url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String data = rs.getString("navn") + " "
                        + rs.getString("kjønn") + " "
                        + rs.getString("alder") + " "
                        + rs.getString("interesser") + " "
                        + rs.getString("bosted") + " "
                        + rs.getString("tlf");
                matcher.add(data);
            }

            conn.close();
        } catch (SQLException e) {
            e.getMessage();
        }

        return matcher;
    }

    /**
     * @return // Test som viser alle brukere i DB
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
                        + rs.getString("kjønn") + " "
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
