package com.obj2000;

public class Bruker {

    private String id;
    private String alder;
    private String kjonn;
    private String interesser;
    private String bosted;
    private double score;


    public Bruker() {

    }

    public String getAlder() {
        return alder;
    }

    public String getKjonn() {
        return kjonn;
    }

    public String getInteresser() {
        return interesser;
    }

    public String getId() {
        return id;
    }

    public double getScore() {
        return score;
    }

    public String getBosted() {
        return bosted;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAlder(String alder) {
        this.alder = alder;
    }

    public void setKjonn(String kjonn) {
        this.kjonn = kjonn;
    }

    public void setInteresser(String interesser) {
        this.interesser = interesser;
    }

    public void setBosted(String bosted) {
        this.bosted = bosted;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return id + "!"
                + alder + "!"
                + kjonn + "!"
                + interesser + "!"
                + bosted + "!"
                + score;
    }
}