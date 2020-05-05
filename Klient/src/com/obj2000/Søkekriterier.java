package com.obj2000;


public class Søkekriterier {

    private int fraAlder;
    private int tilAlder;
    private char kjønn;

    public Søkekriterier(int fraAlder, int tilAlder, char kjønn) {
        this.fraAlder = fraAlder;
        this.tilAlder = tilAlder;
        this.kjønn = kjønn;
    }

    public int getFraAlder() {
        return fraAlder;
    }

    public int getTilAlder() {
        return tilAlder;
    }

    public char getKjønn() {
        return kjønn;
    }
}