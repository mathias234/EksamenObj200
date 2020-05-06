package com.obj2000;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MatchHBox extends HBox {
    Label lbKjønn = null;
    Label lbAlder = null;
    String navn;
    String tlf;
    String kjønn;
    String alder;
    String interesser;
    String bosted;
    String matchId;
    Button visMatch;

    public MatchHBox(String id, String alder, String kjønn, String interesser, String bosted){

        super(20);
        matchId = id;
        this.interesser = interesser;
        this.bosted = bosted;
        this.kjønn = kjønn;
        this.alder = alder;

        lbKjønn = new Label(kjønn);
        lbAlder = new Label(alder);
        setStyle("-fx-background-color: #E8E8E8;" + "-fx-border-style: solid inside;" + "-fx-font-size: 15px;");
        setAlignment(Pos.CENTER);
        setPadding(new Insets(25, 12, 25, 150));
        visMatch = new Button("Få detaljer");
        getChildren().addAll(lbKjønn,lbAlder, visMatch);
    }

    public void hentNavnOgTlf(String data){
        String[] linje = data.split("!");
        this.navn = linje[0];
        this.tlf = linje[1];
    }

    public void logg(){

    }
}
