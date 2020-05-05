package com.obj2000;

public class Bruker {

    private String navn;
    private int alder;
    private char kjønn;
    private String interesser;
    private String telefonnummer;
    private String bosted;
    private int id;

    public Bruker (String navn, int alder, char kjønn, String interesser, String telefonnummer, String bosted) {
        this.navn = navn;
        this.alder = alder;
        this.kjønn = kjønn;
        this.interesser = interesser;
        this.telefonnummer = telefonnummer;
        this.bosted = bosted;
    }

    public String getNavn() {
        return navn;
    }

    public int getAlder() {
        return alder;
    }

    public char getKjønn() {
        return kjønn;
    }

    public String getInteresser() {
        return interesser;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public String getBosted() {
        return bosted;
    }

}