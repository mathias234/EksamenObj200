package com.obj2000;

public class Bruker {

    private String id;
    private String alder;
    private String kjønn;
    private String interesser;
    private String bosted;
    private double score;


    public Bruker () {

        }

        public String getAlder() {
            return alder;
        }

        public String getKjønn() {
            return kjønn;
        }

        public String getInteresser() {
            return interesser;
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

        public void setKjønn(String kjønn) {
            this.kjønn = kjønn;
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
        return  id + "!"
                + alder + "!"
                + kjønn + "!"
                + interesser + "!"
                + bosted + "!"
                + score;
    }
}