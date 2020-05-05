package com.obj2000;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class HovedScene {

    Scene scene;
    BorderPane bp = new BorderPane();

    Pane pane = new Pane();
    Text matcher = new Text("Matcher"), besøkende = new Text("Besøkende");
    Button bt1 = new Button("Matcher"), bt2 = new Button("Besøkende");

    HBox logo = new HBox();
    HBox knapper = new HBox();
    VBox top = new VBox();

    MatchPane matchPane = new MatchPane();

    public Scene getScene() {
        pane.setStyle("-fx-background-color: blue");
        bp.setPadding(new Insets(25,25,25,25));
        bp.setTop(top);
        bp.setCenter(matchPane);
        top.getChildren().addAll(logo, knapper);
        knapper.getChildren().addAll(bt1, bt2);

        bt2.setOnAction(event -> {bp.setCenter(new BesøkendePane());});
        bt1.setOnAction(event -> {bp.setCenter(matchPane);});

        scene = new Scene(bp, 400, 500);

        return scene;
    }
}
