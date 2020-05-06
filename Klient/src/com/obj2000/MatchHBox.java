package com.obj2000;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import static com.obj2000.Main.minId;

public class MatchHBox extends HBox {
    Label lbKjonn = null;
    Label lbAlder = null;
    String kjonn;
    String alder;
    String interesser;
    String bosted;
    String matchId;
    Button visMatch;
    Node foreldreNode;
    ImageView profilBilde;

    public MatchHBox(String id, String alder, String kjonn, String interesser, String bosted, Node foreldreNode){

        super(20);
        matchId = id;
        this.foreldreNode = foreldreNode;
        this.interesser = interesser;
        this.bosted = bosted;
        this.kjonn = kjonn;
        this.alder = alder;

        lbKjonn = new Label(kjonn);
        lbAlder = new Label(alder);
        setStyle("-fx-background-color: #E8E8E8;" + "-fx-border-style: solid inside;" + "-fx-font-size: 15px;");
        setAlignment(Pos.CENTER);
        setPadding(new Insets(25, 12, 25, 150));
        visMatch = new Button("Fa detaljer");
        visMatch.setOnAction(event -> {visMatchInfo();});
        getChildren().addAll(lbKjonn,lbAlder, visMatch);
    }

    /**
     * Metode som er ansvarlig for å opprette Popup vinduet til matchene, samt hente
     * navn og telefonnummeret til gitt match fra serveren.
     */
    public void visMatchInfo(){
        try{
            Klient.sendMessage("hentNavnTlf!" + matchId);
            String melding = Klient.receiveMessage();
            String[] data = melding.split("!");
            hentBilde();
            MatchPopUp pop = new MatchPopUp(profilBilde, data[0], data[1], kjonn, alder, interesser, bosted);
            pop.show(foreldreNode, 970, 94);
            logg();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metode som logger hvilken bruker som har fått data om en annen bruker.
     * dataen sendes til server og lagres i databasen.
     */
    public void logg(){
        try{
            Klient.sendMessage("takontakt!" + minId + "!" + matchId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * metode for å hente bilde fra serveren, legger dataen i et byte array og serealiserer dataene til
     * et helt bilde igjen.
     */
    private void hentBilde(){
        profilBilde = null;
        try{
            Klient.sendMessage("hentBilde!" + matchId);

            byte[] data = Klient.receiveBilde();

            Image img = new Image(new ByteArrayInputStream(data));
            profilBilde = new ImageView(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
