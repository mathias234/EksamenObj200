package com.obj2000;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    private RegisteringsPane registeringsPane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        vindu = primaryStage;
        Klient.setIp("127.0.0.1");
        Klient.setPort(5000);

        registeringsPane = new RegisteringsPane();
        HovedScene hovedScene = new HovedScene();
        BesøkendePane besøkendePane = new BesøkendePane();

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
                System.out.println("Feil i registrering, prøv igjen\n"+ex);
            }
        });

        hovedScene.matchPane.oppdaterBtn.setOnAction(e -> {
            try {
                String fraAlder = "" + (int)hovedScene.mkp.slider.getValue();
                String tilAlder = "" + (int)hovedScene.mkp.slider2.getValue();

                String kjønn = "mann";
                if(hovedScene.mkp.kjønnToggleGroup.getSelectedToggle() != null)
                    kjønn = (String)hovedScene.mkp.kjønnToggleGroup.getSelectedToggle().getUserData();

                Klient.sendMessage("matcher!" + minId + "!" +
                        fraAlder + "!" +
                        tilAlder + "!" +
                        kjønn);

                String msg = Klient.receiveMessage();
                System.out.println(msg);
                String[] matchData = msg.split("#");
                System.out.println(Arrays.toString(matchData));
                hovedScene.matchPane.visMatcher(matchData);
            } catch(IOException ex) {
                System.out.println("Oppdatering feilet\n"+ex);
            }
        });

        hovedScene.besøkendePane.oppdaterBtn.setOnAction(e -> {
            try {
                Klient.sendMessage("vislogg!" + minId);
                String msg = Klient.receiveMessage();
                String[] loggData = msg.split("#");
                hovedScene.besøkendePane.visResultater(loggData);
            }
            catch (IOException ex) {
                System.out.println("Oppdatering feilet\n" + ex);
            }
        });

        //hovedScene.matchPane.boks.visMatch.setOnAction(event -> {
            //MatchPopUp pop = new MatchPopUp();
        //});

        vindu.setTitle("NettMatch");

        if(idFile.exists()) {
            Scanner idScanner = new Scanner(idFile);
            if(idScanner.hasNextLine()) {
                minId = idScanner.nextLine();
            }

            // Spør serveren om id er valid
            Klient.sendMessage("sjekkid!" + minId);

            String svar = Klient.receiveMessage();
            System.out.println(svar);

            if(svar.equals("1")) {
                // Åpne hoved scenen
                vindu.setScene(hovedScene.getScene());
            }
            else {
                // Iden finnes ikke, så brukeren må lage en ny
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Lagret bruker finnes ikke i databasen");
                alert.setContentText("Lagret bruker finnes ikke i databasen, du må lage en ny");
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
