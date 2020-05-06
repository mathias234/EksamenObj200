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
    Label lbKjønn = null;
    Label lbAlder = null;
    String navn;
    String tlf;
    String kjønn;
    String alder;
    String interesser;
    String bosted;
    String matchId;
    Button visMatch;
    Node foreldreNode;
    ImageView profilBilde;

    public MatchHBox(String id, String alder, String kjønn, String interesser, String bosted, Node foreldreNode){

        super(20);
        matchId = id;
        this.foreldreNode = foreldreNode;
        this.interesser = interesser;
        this.bosted = bosted;
        this.kjønn = kjønn;
        this.alder = alder;

        lbKjønn = new Label(kjønn);
        lbAlder = new Label(alder);
        setStyle("-fx-background-color: #E8E8E8;" + "-fx-border-style: solid inside;" + "-fx-font-size: 15px;");
        setAlignment(Pos.CENTER);
        setPadding(new Insets(25, 12, 25, 150));
        visMatch = new Button("Få detaljer");
        visMatch.setOnAction(event -> {visMatchInfo();});
        getChildren().addAll(lbKjønn,lbAlder, visMatch);
    }

    public void visMatchInfo(){
        try{
            Klient.sendMessage("hentNavnTlf!" + matchId);
            String melding = Klient.receiveMessage();
            String[] data = melding.split("!");
            hentBilde();
            MatchPopUp pop = new MatchPopUp(profilBilde, data[0], data[1], kjønn, alder, interesser, bosted);
            pop.show(foreldreNode, 970, 94);
            logg();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logg(){
        try{
            Klient.sendMessage("takontakt!" + minId + "!" + matchId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
