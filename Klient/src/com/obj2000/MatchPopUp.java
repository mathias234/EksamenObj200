package com.obj2000;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;

public class MatchPopUp extends Popup {
    String navn, tlf, kjonn, alder, interesser, bosted;
    Label lbAlder = new Label("Alder:"), lbBosted = new Label("Bosted:"), lbInteresser = new Label("Interesser:"), lbNavn = new Label("Navn:"), lbTelefon = new Label("Telefon:");
    Button btLukk = new Button("X");
    TextField tfAlder = new TextField(), tfBosted = new TextField(), tfNavn = new TextField(), tfTelefon = new TextField();
    TextArea taInteresser = new TextArea();
    HBox hbox = new HBox(5);
    ImageView profilbilde;
    HBox bildeRamme;
    BorderPane matchCon;

    public MatchPopUp(ImageView profilbilde, String navn, String tlf, String kjonn, String alder, String interesser, String bosted) {
        this.profilbilde = profilbilde;
        this.navn = navn;
        this.tlf = tlf;
        this.kjonn = kjonn;
        this.alder = alder;
        this.interesser = interesser;
        this.bosted = bosted;
        try {
            getContent().add(fyllInnhold());
        } catch (Exception e) {
            e.printStackTrace();
        }

         btLukk.setOnAction(e -> {this.hide();});
    }

    private BorderPane fyllInnhold() throws Exception {

        BorderPane bp = new BorderPane();
        GridPane gp = new GridPane();
        bp.setStyle("-fx-background-color: #E8E8E8;");

        profilbilde.setFitHeight(100);
        profilbilde.setFitWidth(100);
        bildeRamme = new HBox();
        bildeRamme.getChildren().add(profilbilde);
        bildeRamme.setAlignment(Pos.CENTER);

        tfNavn.setText(navn);
        tfNavn.setDisable(true);
        tfAlder.setText(alder);
        tfAlder.setDisable(true);
        tfBosted.setText(bosted);
        tfBosted.setDisable(true);
        tfTelefon.setText(tlf);
        tfTelefon.setDisable(true);

        taInteresser.setText(interesser);
        taInteresser.setDisable(true);
        taInteresser.setMaxWidth(150);
        taInteresser.setMaxHeight(100);
        taInteresser.setDisable(true);
        taInteresser.setWrapText(true);

        gp.add(lbNavn, 0, 0);
        gp.add(tfNavn, 1, 0);
        gp.add(lbAlder, 0, 1);
        gp.add(tfAlder, 1, 1);
        gp.add(lbBosted, 0, 2);
        gp.add(tfBosted, 1, 2);
        gp.add(lbInteresser, 0, 3);
        gp.add(taInteresser, 1, 3);
        gp.add(lbTelefon, 0, 4);
        gp.add(tfTelefon, 1, 4);
        btLukk.setMaxSize(100, 200);

        gp.setAlignment(Pos.CENTER);
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(0, 20, 10, 10));

        matchCon = new BorderPane();
        matchCon.setCenter(gp);
        matchCon.setTop(bildeRamme);
        BorderPane.setMargin(bildeRamme, new Insets(10));
        hbox.getChildren().add(btLukk);
        hbox.setAlignment(Pos.TOP_RIGHT);
        bp.setCenter(matchCon);
        bp.setTop(hbox);

        return bp;
    }
}