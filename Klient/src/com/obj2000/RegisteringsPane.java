package com.obj2000;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RegisteringsPane extends GridPane {
    Label navn, alder, interesser, tlf, bosted, kjønn, lbBilde;
    TextField txtNavn, txtAlder, txtTlf, txtBosted, tfBilde;
    RadioButton m, d;
    TextArea txtInteresser;
    Button registrer, btBilde;
    Image image = new Image(getClass().getResourceAsStream(
            "NettMatch3.png"));
    HBox kjonn = new HBox();
    ToggleGroup kjønnToggleGroup;
    FileChooser velgBilde;
    Image søk;
    byte[] bildeData;

    public RegisteringsPane() {
        fyllPane();
    }

    protected void fyllPane() {
        ImageView lugo = new ImageView(image);

        //Labels
        navn = new Label("Navn: ");
        alder = new Label("Alder: ");
        interesser = new Label("Interesser: ");
        tlf = new Label("Tlf: ");
        bosted = new Label("Bosted: ");
        kjønn = new Label("Kjønn: ");
        lbBilde = new Label("Velg bilde: ");

        //Styleing
        interesser.setStyle("-fx-font-size: 12px;-fx-font-weight:bold;");
        navn.setStyle("-fx-font-size: 12px;-fx-font-weight:bold;");
        alder.setStyle("-fx-font-size: 12px;-fx-font-weight:bold;");
        tlf.setStyle("-fx-font-size: 12px;-fx-font-weight:bold;");
        bosted.setStyle("-fx-font-size: 12px;-fx-font-weight:bold;");
        kjønn.setStyle("-fx-font-size: 12px;-fx-font-weight:bold;");
        lbBilde.setStyle("-fx-font-size: 12px;-fx-font-weight:bold;");

        //Text
        txtNavn = new TextField();
        txtAlder = new TextField();
        txtTlf = new TextField();
        txtBosted = new TextField();
        txtInteresser = new TextArea();
        tfBilde = new TextField();
        tfBilde.setDisable(true);

        søk = new Image("com/obj2000/søk.png", 17, 17, true, true);

        //Buttons
        m = new RadioButton();
        d = new RadioButton();
        registrer = new Button(" Registrer ");
        btBilde = new Button();
        btBilde.setGraphic(new ImageView(søk));

        //kjønn box
        kjonn.getChildren().addAll(m, d);

        //FileChooser
        velgBilde = new FileChooser();
        velgBilde.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        btBilde.setOnAction(event -> {
            File valgtFil = velgBilde.showOpenDialog(this.getScene().getWindow());
            tfBilde.setText(valgtFil.getPath());
            Path lokasjon = Paths.get(valgtFil.getPath());
            try {
                bildeData = Files.readAllBytes(lokasjon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        setAlignment(Pos.CENTER);
        setHgap(10);//Lager litt mellomrom rundt tingene
        setVgap(10);
        setPadding(new Insets(20, 20, 20, 20));
        m.setText("Mann    ");
        d.setText("Dame");
        m.setUserData("mann");
        d.setUserData("dame");
        txtInteresser.setMaxWidth(300);
        txtInteresser.setMaxHeight(100);
        txtInteresser.setWrapText(true);
        kjønnToggleGroup = new ToggleGroup();
        m.setToggleGroup(kjønnToggleGroup);
        d.setToggleGroup(kjønnToggleGroup);

        add(lugo, 0, 1, 2, 1);
        add(navn, 0, 2);
        add(txtNavn, 1, 2);
        add(alder, 0, 3);
        add(txtAlder, 1, 3);
        add(bosted, 0, 4);
        add(txtBosted, 1, 4);
        add(tlf, 0, 5);
        add(txtTlf, 1, 5);
        add(interesser, 0, 6);
        add(txtInteresser, 1, 6);
        add(kjønn, 0, 7);
        add(kjonn, 1, 7);
        add(lbBilde, 0, 8);
        add(tfBilde, 1, 8);
        add(btBilde, 2, 8);
        add(registrer, 0, 9, 2, 1);
        registrer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    }

    /**
     * Denne metoden vil sende registrerings request til tjeneren
     *
     * @throws IOException
     */
    public String registrer() throws IOException {
        String navn = txtNavn.getText();
        String kjønn = "";
        if (kjønnToggleGroup.getSelectedToggle() != null)
            kjønn = (String) kjønnToggleGroup.getSelectedToggle().getUserData();
        String alder = txtAlder.getText();
        String interesser = txtInteresser.getText();
        String bosted = txtBosted.getText();
        String tlf = txtTlf.getText();

        Klient.sendMessageMedBilde("register!" + navn + "!" + kjønn + "!" + alder + "!" + interesser + "!" + bosted + "!" + tlf, bildeData);

        return Klient.receiveMessage();
    }

}
