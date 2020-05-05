package com.obj2000;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class RegisteringsPane extends GridPane {
    Label navn, alder, interesser, tlf, bosted, kjønn;
    TextField txtNavn, txtAlder, txtTlf, txtBosted;
    RadioButton m, d;
    TextArea txtInteresser;
    Button registrer;

    public RegisteringsPane(){
        fyllPane();
    }

    protected void fyllPane(){
        //Labels
        navn = new Label("Navn: ");
        alder = new Label("Alder: ");
        interesser = new Label("Interesser: ");
        tlf = new Label("Tlf: ");
        bosted = new Label("Bosted: ");
        kjønn = new Label("Kjønn: ");

        //Text
        txtNavn = new TextField();
        txtAlder = new TextField();
        txtTlf = new TextField();
        txtBosted = new TextField();
        txtInteresser = new TextArea();

        //Buttons
        m = new RadioButton();
        d = new RadioButton();
        registrer = new Button(" Registrer ");

        setAlignment(Pos.CENTER);
        setHgap(10);//Lager litt mellomrom rundt tingene, så de ikke er
        setVgap(10);//klin på hverandre
        setPadding(new Insets(20,20,20,20));
        m.setText("Mann");
        d.setText("Dame");
        txtInteresser.setMaxWidth(250);
        txtInteresser.setMaxHeight(100);
        ToggleGroup tg = new ToggleGroup();
        m.setToggleGroup(tg);
        d.setToggleGroup(tg);

        add(navn, 0, 1);
        add(txtNavn, 1, 1);
        add(alder, 0, 2);
        add(txtAlder, 1, 2);
        add(bosted ,0, 3);
        add(txtBosted, 1, 3);
        add(tlf, 0, 4);
        add(txtTlf, 1, 4);
        add(interesser, 0, 5);
        add(txtInteresser, 1, 5);
        add(kjønn,0, 6);
        add(m, 0, 7);
        add(d, 1, 7);
        add(registrer,0, 8, 2, 1);
        registrer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }
}
