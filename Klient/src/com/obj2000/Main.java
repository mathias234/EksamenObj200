package com.obj2000;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
        MatchKriteriePane matchKriteriePane = new MatchKriteriePane();
        HovedScene hovedScene = new HovedScene();
        BesøkendePane besøkendePane = new BesøkendePane();

        scene1 = new Scene(registeringsPane, 400, 500);

        registeringsPane.registrer.setOnAction(e -> {
            try {
                register(registeringsPane);
                vindu.setScene(hovedScene.getScene());
            } catch (IOException ex) {
                System.out.println("Feil i registrering, prøv igjen\n"+ex);
            }
        });

        //matchKriteriePane.finn.setOnAction(e -> vindu.setScene(hovedScene.getScene()));
        hovedScene.bt2.setOnAction(e -> vindu.setScene(besøkendePane.getScene()));


        hovedScene.matchPane.oppdaterBtn.setOnAction(e -> {
            try {
                klient.sendMessage("matcher!" + minId + "!" +
                        (int)hovedScene.mkp.slider.getValue() + "!" +
                        (int)hovedScene.mkp.slider2.getValue() + "!" +
                        (String)hovedScene.mkp.kjønnToggleGroup.getSelectedToggle().getUserData());
            } catch(IOException ex) {
                System.out.println("Oppdatering feilet\n"+ex);
            }
        });

        vindu.setTitle("NettMatch");
        vindu.setScene(scene1);
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
        // Skriv denne iden til tekst filen
    }


    public static void main(String[] args) {
        launch(args);
    }
}
