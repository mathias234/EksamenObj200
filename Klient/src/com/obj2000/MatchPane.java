package com.obj2000;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MatchPane extends BorderPane {
    Button bt3, bt4;
    HBox antMatch;
    HBox logo;
    TextField txt = new TextField("10");
    Text mtch = new Text("Antall matcher: ");

    public MatchPane(){

        bt3 = new Button("Oppdater");

        txt.setPrefColumnCount(3);
        mtch.setStyle("-fx-font-size: 13px;-fx-font-weight:bold;-fx-padding:10px;");

        antMatch = new HBox();
        antMatch.setSpacing(10);
        antMatch.setPadding(new Insets(10, 0,10,65));
        antMatch.setStyle("-fx-border-color: black;-fx-border-width: 0 0 3 0;");
        antMatch.getChildren().addAll(mtch, txt, bt3);
        setTop(antMatch);
    }


}
