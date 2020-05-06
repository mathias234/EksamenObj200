package com.obj2000;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main extends Application {
    public static String minId;

    private Stage vindu;
    private Scene scene1;


    @Override
    public void start(Stage primaryStage) throws Exception{
        vindu = primaryStage;
        Image icon  = new Image(getClass().getResourceAsStream(
                "index.jpg"));
        vindu.getIcons().add(icon);
        Klient.setIp("127.0.0.1");
        Klient.setPort(5000);

        RegisteringsPane registeringsPane = new RegisteringsPane();
        HovedScene hovedScene = new HovedScene();
        BesokendePane besokendePane = new BesokendePane();

        scene1 = new Scene(registeringsPane, 400, 500);

        File idFile = new File("minid.txt");

        registeringsPane.registrer.setOnAction(e -> {
            try {
                minId = registeringsPane.registrer();

                if(!idFile.exists()) {
                    if(!idFile.createNewFile()) {
                        System.out.println("Failed to create new file");
                    }
                }

                FileWriter writer = new FileWriter(idFile);
                writer.write(minId);
                writer.close();
                vindu.setScene(hovedScene.getScene());
            } catch (IOException ex) {
                System.out.println("Feil i registrering, prov igjen\n"+ex);
            }
        });

        vindu.setTitle("NettMatch");
        vindu.setResizable(false);

        if(idFile.exists()) {
            Scanner idScanner = new Scanner(idFile);
            if(idScanner.hasNextLine()) {
                minId = idScanner.nextLine();
            }

            // Spor serveren om id er valid
            Klient.sendMessage("sjekkid!" + minId);

            String svar = Klient.receiveMessage();
            System.out.println(svar);

            if(svar.equals("1")) {
                // apne hoved scenen
                vindu.setScene(hovedScene.getScene());
            }
            else {
                // Iden finnes ikke, sa brukeren ma lage en ny
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Lagret bruker finnes ikke i databasen");
                alert.setContentText("Lagret bruker finnes ikke i databasen, du ma lage en ny");
                alert.showAndWait();
                vindu.setScene(scene1);
            }
        }
        else {
            vindu.setScene(scene1);
        }

        vindu.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
