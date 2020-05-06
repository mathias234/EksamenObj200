package com.obj2000;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class BesøkendePane extends BorderPane {
    Button oppdaterBtn;
    VBox resultaterVBox;

    public BesøkendePane() {
        oppdaterBtn = new Button("Oppdater");

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
    }

    /** Metode som vil oppdatere resultatene
     * @param data en liste men strings som beskriver brukeren, eksempel [dadd090e-27f7-46d0-a742-4213c417bb1e!TestBruker3!22!Kongsberg]
     */
    public void visResultater(String[] data) {
        String id;
        String alder;
        String navn;
        String bosted;


        resultaterVBox.getChildren().clear();

        for (String bruker : data) {
            String[] matchData = bruker.split("!");
            id = matchData[0];
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