package com.obj2000;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MatchPane extends BorderPane {
    Label test;
    Button bt3, bt4;
    HBox antMatch;
    TextField txt = new TextField("10");
    Label mtch = new Label("Matcher");

    public MatchPane(){
        bt3 = new Button("Opp");
        bt4 = new Button("Ned");
        antMatch = new HBox();
        antMatch.getChildren().addAll(bt3, bt4, txt, mtch);
        setTop(antMatch);
    }


}
