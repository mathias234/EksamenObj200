package com.obj2000;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Arrays;

public class MatchPane extends BorderPane {
    Button oppdaterBtn;
    HBox antMatch;
    VBox container;
    ScrollPane sPane;
    MatchHBox boks;

    public MatchPane(HovedScene hovedScene){
        oppdaterBtn = new Button("Vis matcher");
        container = new VBox(5);
        sPane = new ScrollPane();
        sPane.setContent(container);
        setCenter(sPane);

        antMatch = new HBox();
        antMatch.setSpacing(10);
        antMatch.setPadding(new Insets(10, 0,10,65));
        antMatch.setStyle("-fx-border-color: black;-fx-border-width: 0 0 3 0;");
        antMatch.getChildren().addAll(oppdaterBtn);
        antMatch.setAlignment(Pos.BASELINE_RIGHT);
        setTop(antMatch);

        oppdaterBtn.setOnAction(e -> {
            container.getChildren().clear();
            try {
                String fraAlder = "" + (int)hovedScene.mkp.slider.getValue();
                String tilAlder = "" + (int)hovedScene.mkp.slider2.getValue();

                String kjønn = "mann";
                if(hovedScene.mkp.kjønnToggleGroup.getSelectedToggle() != null)
                    kjønn = (String)hovedScene.mkp.kjønnToggleGroup.getSelectedToggle().getUserData();

                Klient.sendMessage("matcher!" + Main.minId + "!" +
                        fraAlder + "!" +
                        tilAlder + "!" +
                        kjønn);

                String msg = Klient.receiveMessage();
                System.out.println(msg);
                String[] matchData = msg.split("#");
                System.out.println(Arrays.toString(matchData));
                visMatcher(matchData);
            } catch(IOException ex) {
                System.out.println("Oppdatering feilet\n"+ex);
            }
        });
    }

    public void visMatcher(String[] data){
        String kjønn;
        String alder;
        String id;
        String interesser;
        String bosted;

        for(int i = 0; i < data.length; i++){
            String[] matchData = data[i].split("!");
            id = matchData[0];
            alder = matchData[1];
            kjønn = matchData[2];
            interesser = matchData[3];
            bosted = matchData[4];
            boks = new MatchHBox(id, alder, kjønn, interesser, bosted, this);
            container.getChildren().add(boks);
        }
    }
}
