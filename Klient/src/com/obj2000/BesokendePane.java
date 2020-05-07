package com.obj2000;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

public class BesokendePane extends BorderPane {
    Button oppdaterBtn;
    VBox resultaterVBox;

    public BesokendePane() {
        oppdaterBtn = new Button("Vis besøk");

        BorderPane loggHbox = new BorderPane();
        loggHbox.setPadding(new Insets(10, 0, 10, 0));
        loggHbox.setStyle("-fx-border-color: black;-fx-border-width: 0 0 3 0;");
        loggHbox.setCenter(oppdaterBtn);
        setTop(loggHbox);

        resultaterVBox = new VBox();
        ScrollPane sPane = new ScrollPane();
        sPane.setContent(resultaterVBox);
        sPane.setFitToWidth(true);
        setCenter(sPane);

        oppdaterBtn.setOnAction(e -> {
            try {
                Klient.sendMessage("vislogg!" + Main.minId);
                String msg = Klient.receiveMessage();
                String[] loggData = msg.split("#");
                visResultater(loggData);
            }
            catch (IOException ex) {
                System.out.println("Oppdatering feilet\n" + ex);
            }
        });
    }

    /** Metode som vil oppdatere resultatene
     * @param data en liste men strings som beskriver brukeren, eksempel [dadd090e-27f7-46d0-a742-4213c417bb1e!TestBruker3!22!Kongsberg]
     */
    public void visResultater(String[] data) {
        String alder;
        String navn;
        String bosted;


        resultaterVBox.getChildren().clear();

        for (String bruker : data) {
            String[] matchData = bruker.split("!");
            navn = matchData[1];
            alder = matchData[2];
            bosted = matchData[3];

            HBox resultat = new HBox(30);
            resultat.setAlignment(Pos.CENTER);
            resultat.setStyle("-fx-background-color: #E8E8E8;" + "-fx-border-style: solid inside;" + "-fx-font-size: 15px;");
            resultat.setPadding(new Insets(15, 15, 15, 15));

            Label lbAlder = new Label(alder);
            Label lbNavn = new Label(navn);
            Label lbBosted = new Label(bosted);


            resultat.getChildren().addAll(lbAlder, lbNavn, lbBosted);

            resultaterVBox.getChildren().add(resultat);
        }
    }
}