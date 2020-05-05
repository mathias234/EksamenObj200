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

public class Person {

    Scene scene;
    Label lbAlder = new Label("Alder:"), lbBosted = new Label("Bosted:"), lbInteresser = new Label("Interesser:"), lbNavn = new Label("Navn:"), lbTelefon = new Label("Telefon:");
    Button btNavnOgNr = new Button("Få navn og telefon"), btOk = new Button("Ok");
    TextField tfAlder = new TextField(), tfBosted = new TextField(), tfInteresser = new TextField(), tfNavn = new TextField(), tfTelefon = new TextField();
    HBox hbox = new HBox();
    Image image = new Image(getClass().getResourceAsStream(
            "NettMatch3.png"));

    public Scene getScene() throws Exception {

        //Logo
        ImageView lugo = new ImageView(image);
        lugo.setFitHeight(100);
        lugo.setFitWidth(300);
        hbox.getChildren().add(lugo);
        hbox.setAlignment(Pos.CENTER);
        hbox.setMaxHeight(50);

        BorderPane bp = new BorderPane();
        GridPane gp = new GridPane();

        gp.add(lbAlder, 0, 1);
        gp.add(tfAlder, 1, 1);
        gp.add(lbBosted, 0, 2);
        gp.add(tfBosted, 1, 2);
        gp.add(lbInteresser, 0, 3);
        gp.add(tfInteresser, 1, 3);
        gp.add(lbNavn, 0, 4);
        gp.add(tfNavn, 1, 4);
        gp.add(lbTelefon, 0, 5);
        gp.add(tfTelefon, 1, 5);
        btNavnOgNr.setMaxSize(150, 200);
        btOk.setMaxSize(100, 200);
        gp.add(btNavnOgNr, 1, 6);
        gp.add(btOk, 0, 6);

        gp.setAlignment(Pos.CENTER);
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(0, 20, 10, 10));

        bp.setCenter(gp);
        bp.setTop(hbox);

        scene = new Scene(bp, 400, 500);

        return scene;
    }
}