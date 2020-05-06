package com.obj2000;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class MatchPane extends BorderPane {
    Button oppdaterBtn;
    HBox antMatch;
    TextField txt;
    Text mtch;
    VBox container;
    ScrollPane sPane;
    MatchHBox boks;

    public MatchPane(){
        txt = new TextField("10");
        oppdaterBtn = new Button("Oppdater");
        mtch = new Text("Antall matcher: ");
        container = new VBox(5);
        txt.setPrefColumnCount(3);
        mtch.setStyle("-fx-font-size: 13px;-fx-font-weight:bold;-fx-padding:10px;");
        sPane = new ScrollPane();
        sPane.setContent(container);
        setCenter(sPane);

        antMatch = new HBox();
        antMatch.setSpacing(10);
        antMatch.setPadding(new Insets(10, 0,10,65));
        antMatch.setStyle("-fx-border-color: black;-fx-border-width: 0 0 3 0;");
        antMatch.getChildren().addAll(mtch, txt, oppdaterBtn);
        setTop(antMatch);
    }

    public void visMatcher(String[] data){
        String kjønn;
        String alder;
        String id;
        String interesser;
        String bosted;

        for(int i = 0; i < data.length; i++){
            String[] matchData = data[i].split("!");
            id = matchData[0];
            alder = matchData[1];
            kjønn = matchData[2];
            interesser = matchData[3];
            bosted = matchData[4];
            boks = new MatchHBox(id, alder, kjønn, interesser, bosted);
            container.getChildren().add(boks);
        }
    }
}
