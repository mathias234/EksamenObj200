package com.obj2000;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MatchKriteriePane {

    Scene scene;

    Label txt = new Label("Kjønn "), txt3 = new Label("Aldersgruppe");
    Text txt2 = new Text("Finn en match");
    Slider slider = new Slider(18, 80, 1.0);
    Button finn = new Button("Finn matcher");
    RadioButton m = new RadioButton(), d = new RadioButton();


    public Scene getScene() {

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(10);//Lager litt mellomrom rundt tingene, så de ikke er
        gp.setVgap(10);//klin på hverandre
        gp.setPadding(new Insets(20,20,20,20));
        slider.setMaxWidth(200);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        finn.setMaxSize(250, 100);
        ToggleGroup tg = new ToggleGroup();
        m.setToggleGroup(tg);
        d.setToggleGroup(tg);

        scene = new Scene(gp, 400, 500);

        txt2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        m.setText("Mann");
        m.setAlignment(Pos.CENTER);
        d.setText("Dame");
        d.setAlignment(Pos.CENTER);


        gp.add(txt2, 0, 1);
        //txt2.columnSpan(2);
        //txt2.setMaxHeight(100);
        gp.add(txt3, 0,2 );
        gp.add(slider, 0, 3);
        gp.add(txt, 0, 4);
        gp.add(m, 0,5);
        gp.add(d,0, 6);
        gp.add(finn, 0, 7);

        HovedScene match = new HovedScene();

        //finn.setOnAction(e -> scene.setScene(match.getScene()));

        return scene;


    }



}
