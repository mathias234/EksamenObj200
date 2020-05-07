package com.obj2000;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.Match;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MatchKriteriePane extends GridPane {

    Label txt = new Label(" Kjønn:  "), txt3 = new Label("   Laveste ønsket  alder:  "),
    txt4 = new Label("   Høyeste ønsket alder:  "),
    txtOppe = new Label(), txtNede = new Label();
    Text txt2 = new Text("  Finn en match");
    Slider slider = new Slider(18, 80, 1.0), slider2 = new Slider(18, 80, 1.0);
    RadioButton m = new RadioButton(), d = new RadioButton();
    HBox oppe = new HBox(), nede = new HBox(), kjonn = new HBox();
    ToggleGroup kjonnToggleGroup;

    public MatchKriteriePane() {
        opprettPane();
    }

    /**
     * metode for å opprette innholdet i pane'et
     */
    private void opprettPane() {

        oppe.getChildren().addAll(txt3, txtOppe);
        nede.getChildren().addAll(txt4, txtNede);
        kjonn.getChildren().addAll(txt, m,d);

        //Legger ut verdi fra slidere i label
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                txtOppe.textProperty().setValue(String.valueOf(newValue.intValue()));
            }
        });
        slider2.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                txtNede.textProperty().setValue(String.valueOf(newValue.intValue()));
            }
        });

        setAlignment(Pos.TOP_CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(20,20,20,20));
        slider.setMaxWidth(180);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(10f);

        slider2.setShowTickMarks(true);
        slider2.setShowTickLabels(true);
        slider2.setMajorTickUnit(10f);

        kjonnToggleGroup = new ToggleGroup();
        m.setToggleGroup(kjonnToggleGroup);
        d.setToggleGroup(kjonnToggleGroup);
        m.setUserData("mann");
        d.setUserData("dame");

        txt2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        txt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
        m.setText("Mann  ");
        m.setAlignment(Pos.CENTER);
        d.setText("Dame");
        d.setAlignment(Pos.CENTER);

        add(txt2, 0, 2);
        add(oppe, 0,3 );
        add(slider, 0, 4);
        add(nede,0,5);
        add(slider2,0,6);
        add(kjonn, 0, 7);

    }

}
