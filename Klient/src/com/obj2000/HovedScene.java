package com.obj2000;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class HovedScene {

    Scene scene;
    BorderPane bp = new BorderPane();

    Pane pane = new Pane();
    Text matcher = new Text("Matcher"), besøkende = new Text("Besøkende");
    Button bt1 = new Button("Matcher"), bt2 = new Button("Besøkende"),
            bt3 = new Button("Match-kriterier");

    HBox logo = new HBox();
    HBox knapper = new HBox();
    VBox top = new VBox();

    Image image  = new Image(getClass().getResourceAsStream(
            "NettMatch3.png"));

    MatchPane matchPane = new MatchPane();
    MatchKriteriePane mkp = new MatchKriteriePane();
    BesøkendePane besøkendePane = new BesøkendePane();

    public Scene getScene() {
        ImageView lugo = new ImageView(image);

        pane.setStyle("-fx-background-color: blue");
        bp.setPadding(new Insets(20,20,20,20));
        bp.setTop(top);
        bp.setCenter(matchPane);
        logo.getChildren().add(lugo);
        top.getChildren().addAll(logo, knapper);
        top.setStyle("-fx-border-color: black;-fx-border-width: 0 0 3 0; -fx-padding-bottom: 5px;-fx-background-color: lightgrey;");
        knapper.getChildren().addAll(bt3,bt1, bt2);
        knapper.setSpacing(20);
        knapper.setPadding(new Insets(10, 0, 10,45));


        bt3.setOnAction(e -> {bp.setCenter(mkp);});
        bt2.setOnAction(event -> {bp.setCenter(besøkendePane);});
        bt1.setOnAction(event -> {bp.setCenter(matchPane);});


        scene = new Scene(bp, 400, 500);

        return scene;
    }
}
