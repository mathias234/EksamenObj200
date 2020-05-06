package com.obj2000;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main extends Application {
    private Klient klient;
    private String minId;

    private Stage vindu;
    private Scene scene1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        vindu = primaryStage;
        klient = new Klient("127.0.0.1", 5000);

        RegisteringsPane registeringsPane = new RegisteringsPane();
        HovedScene hovedScene = new HovedScene();
        BesøkendePane besøkendePane = new BesøkendePane();

        scene1 = new Scene(registeringsPane, 400, 500);

        File idFile = new File("minid.txt");

        registeringsPane.registrer.setOnAction(e -> {
            try {
                register(registeringsPane);

                idFile.delete();

                if(idFile.createNewFile()) {
                    FileWriter writer = new FileWriter(idFile);
                    writer.write(minId);
                    writer.close();
                }

                vindu.setScene(hovedScene.getScene());
            } catch (IOException ex) {
                System.out.println("Feil i registrering, prøv igjen\n"+ex);
            }
        });

        hovedScene.bt2.setOnAction(e -> vindu.setScene(besøkendePane.getScene()));


        hovedScene.matchPane.oppdaterBtn.setOnAction(e -> {
            try {
                String fraAlder = "" + (int)hovedScene.mkp.slider.getValue();
                String tilAlder = "" + (int)hovedScene.mkp.slider2.getValue();

                String kjønn = "mann";
                if(hovedScene.mkp.kjønnToggleGroup.getSelectedToggle() != null)
                    kjønn = (String)hovedScene.mkp.kjønnToggleGroup.getSelectedToggle().getUserData();

                klient.sendMessage("matcher!" + minId + "!" +
                        fraAlder + "!" +
                        tilAlder + "!" +
                        kjønn);

                String msg = klient.receiveMessage();
                System.out.println(msg);
                String[] matchData = msg.split("#");
                System.out.println(Arrays.toString(matchData));
                hovedScene.matchPane.visMatcher(matchData);
            } catch(IOException ex) {
                System.out.println("Oppdatering feilet\n"+ex);
            }
        });

        vindu.setTitle("NettMatch");


        if(idFile.exists()) {
            Scanner idScanner = new Scanner(idFile);
            if(idScanner.hasNextLine()) {
                minId = idScanner.nextLine();
            }

            // Spør serveren om id er valid
            klient.sendMessage("sjekkid!" + minId);

            if(klient.receiveMessage().equals("1")) {
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

    private void register(RegisteringsPane pane) throws IOException {
        String navn = pane.txtNavn.getText();
        String kjønn = "";
        if(pane.kjønnToggleGroup.getSelectedToggle() != null)
            kjønn = (String)pane.kjønnToggleGroup.getSelectedToggle().getUserData();
        String alder = pane.txtAlder.getText();
        String interesser = pane.txtInteresser.getText();
        String bosted = pane.txtBosted.getText();
        String tlf = pane.txtTlf.getText();

        klient.sendMessage("register!" + navn + "!" + kjønn + "!" + alder + "!" + interesser + "!" + bosted + "!" + tlf);
        minId = klient.receiveMessage();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
