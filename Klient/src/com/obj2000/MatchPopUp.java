package com.obj2000;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class MatchPopUp extends Popup {
    String navn, tlf, kjønn, alder, interesser, bosted;
    Label lbAlder = new Label("Alder:"), lbBosted = new Label("Bosted:"), lbInteresser = new Label("Interesser:"), lbNavn = new Label("Navn:"), lbTelefon = new Label("Telefon:");
    Button btLukk = new Button("Lukk vindu");
    TextField tfAlder = new TextField(), tfBosted = new TextField(), tfInteresser = new TextField(), tfNavn = new TextField(), tfTelefon = new TextField();
    HBox hbox = new HBox();

    public MatchPopUp(String navn, String tlf, String kjønn, String alder, String interesser, String bosted) {
        this.navn = navn;
        this.tlf = tlf;
        this.kjønn = kjønn;
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

    public BorderPane fyllInnhold() throws Exception {

        BorderPane bp = new BorderPane();
        GridPane gp = new GridPane();
        bp.setStyle("-fx-background-color: #E8E8E8;");

        tfNavn.setText(navn);
        tfNavn.setDisable(true);
        tfAlder.setText(alder);
        tfAlder.setDisable(true);
        tfBosted.setText(bosted);
        tfBosted.setDisable(true);
        tfInteresser.setText(interesser);
        tfInteresser.setDisable(true);
        tfTelefon.setText(tlf);
        tfTelefon.setDisable(true);

        gp.add(lbNavn, 0, 1);
        gp.add(tfNavn, 1, 1);
        gp.add(lbAlder, 0, 2);
        gp.add(tfAlder, 1, 2);
        gp.add(lbBosted, 0, 3);
        gp.add(tfBosted, 1, 3);
        gp.add(lbInteresser, 0, 4);
        gp.add(tfInteresser, 1, 4);
        gp.add(lbTelefon, 0, 5);
        gp.add(tfTelefon, 1, 5);
        btLukk.setMaxSize(100, 200);

        gp.setAlignment(Pos.CENTER);
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(0, 20, 10, 10));

        bp.setCenter(gp);
        bp.setTop(btLukk);

        return bp;
    }
}