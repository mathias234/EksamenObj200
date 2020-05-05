package com.obj2000;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    Stage vindu;
    Scene scene1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        vindu = primaryStage;

        RegisteringsPane registeringsPane = new RegisteringsPane();
        MatchKriteriePane matchKriteriePane = new MatchKriteriePane();
        HovedScene hovedScene = new HovedScene();
        BesøkendePane besøkendePane = new BesøkendePane();

        scene1 = new Scene(registeringsPane, 400, 500);


        registeringsPane.registrer.setOnAction(e -> vindu.setScene(hovedScene.getScene()));
        //matchKriteriePane.finn.setOnAction(e -> vindu.setScene(hovedScene.getScene()));
        hovedScene.bt2.setOnAction(e -> vindu.setScene(besøkendePane.getScene()));


        vindu.setTitle("NettMatch");
        vindu.setScene(scene1);
        vindu.show();
    }


    public static void main(String[] args) {
        launch(args);
    }



}
