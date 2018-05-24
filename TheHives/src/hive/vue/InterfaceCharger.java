/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.vue;

import hive.controller.Controller;
import java.util.logging.Level;
import java.util.logging.Logger;
import hive.model.game.Game;
import java.awt.Dimension;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
 import javafx.scene.control.ListCell;
 import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 *
 * @author Adeline
 */
public class InterfaceCharger extends Interface {


    public InterfaceCharger(Stage primaryStage, Controller controller, CacheImage c) throws IOException {
        super(primaryStage, controller, c);

        AnchorPane pane = new AnchorPane();
        pane.prefWidthProperty().bind(primaryStage.widthProperty());
        pane.prefHeightProperty().bind(primaryStage.heightProperty());

        AnchorPane.setRightAnchor(boutonPreference, (double) tailleDeCase / 2 * 1.07 + 15);
        AnchorPane.setTopAnchor(boutonPreference, (double) 5);
        pane.getChildren().add(boutonPreference);

        AnchorPane.setRightAnchor(boutonPleinEcran, (double) 10);
        AnchorPane.setTopAnchor(boutonPleinEcran, (double) 5);
        pane.getChildren().add(boutonPleinEcran);

        AnchorPane.setLeftAnchor(boutonRetourMenu, (double) 5);
        AnchorPane.setTopAnchor(boutonRetourMenu, (double) 5);
        pane.getChildren().add(boutonRetourMenu);


        Label choix = new Label(); // Scegliere partita salvata, Gespeichertes Spiel wählen
        Button valider = new Button();

        valider.setText(controller.gestionnaireLangage.getText("text_valider"));
        choix.setText(controller.gestionnaireLangage.getText("text_choisir_partie"));

        choix.setFont(new Font(police, tailleDeCase *0.3));
        choix.setAlignment(Pos.CENTER);
        choix.setTextFill(Color.web("#fbe5b5"));
        
        StackPane sp = new StackPane();
        Image pancarte = c.getImage("plusDeBoutons/plusDeBoutons/Pancarte.png");
        ImageView pancarteIm = new ImageView(pancarte);
        pancarteIm.setFitHeight(tailleDeCase * 0.8);
        pancarteIm.setFitWidth(tailleDeCase * 0.8 * 5.09);
        sp.getChildren().add(pancarteIm);
        sp.getChildren().add(choix);
        AnchorPane.setTopAnchor(sp, (double) height / 40);
        AnchorPane.setLeftAnchor(sp, (double) tailleDeCase * 2);
        AnchorPane.setRightAnchor(sp, (double) tailleDeCase * 2);
        pane.getChildren().add(sp);

        
        double flecheLargeur = tailleDeCase * 4 - 30;
        double flecheHauteur = flecheLargeur / 7.24;
        
        StackPane parties_sp = new StackPane();
        Image fleche = c.getImage("Design/MenuPrincipaux/FlecheDuMenuDansHexagone.png");
        ImageView partiesIm = new ImageView(fleche);
        partiesIm.setFitHeight(flecheHauteur);
        partiesIm.setFitWidth(flecheLargeur);
        //parties_sp.getChildren().add(partiesIm);
        final ComboBox parties = new ComboBox();
        StackPane text_sp = new StackPane();
        Label text = new Label();
        text.setText("Choissisez la partie à charger");
        text.setTextFill(Color.web("#fbe5b5"));
        text.setFont(new Font(police, tailleDeCase * 0.23));
            ImageView fleche_Im = new ImageView(fleche);
            fleche_Im.setFitHeight(flecheHauteur);
            fleche_Im.setFitWidth(flecheLargeur);
        text_sp.getChildren().add(fleche_Im);
        text_sp.getChildren().add(text);
        //parties.getItems().add(text_sp);
        for (int j = 0; j < 20; j++) {
            ImageView flecheIm = new ImageView(fleche);
            flecheIm.setFitHeight(flecheHauteur*0.5);
            flecheIm.setFitWidth(flecheLargeur);
            Label label = new Label("aaaa");
            label.setTextFill(Color.web("#fbe5b5"));
            label.setFont(new Font(police, tailleDeCase * 0.15));
            StackPane x = new StackPane();
            x.getChildren().add(flecheIm);
            x.getChildren().add(label);
            //x.setMaxSize(flecheLargeur, flecheHauteur);
            //x.setMinSize(flecheLargeur, flecheHauteur);
            parties.getItems().add( x );
        }
        parties.setValue(text_sp);
        parties.setBackground(Background.EMPTY);
        
        
        //parties.setMaxSize(flecheLargeur, flecheHauteur);
        //parties.setMinSize(flecheLargeur, flecheHauteur);
        
        parties_sp.getChildren().add(parties);

        AnchorPane.setTopAnchor(parties_sp, (double) height/40 + tailleDeCase*0.9);
        AnchorPane.setLeftAnchor(parties_sp, (double) tailleDeCase * 2);
        AnchorPane.setRightAnchor(parties_sp, (double) tailleDeCase * 2);
        //AnchorPane.setBottomAnchor(parties, (double) height/1.3);
        pane.getChildren().add(parties_sp);
        
        
        StackPane valider_sp = new StackPane();
        Image val = c.getImage("Design/MenuPrincipaux/FlecheDuMenuDansHexagone.png");
        ImageView valIm = new ImageView(val);
        valIm.setFitHeight(flecheHauteur);
        valIm.setFitWidth(flecheLargeur);
        valider_sp.getChildren().add(valIm);
        valider.setFont(new Font(police, tailleDeCase * 0.23));
        valider.setTextFill(Color.web("#fbe5b5"));
        valider.setBackground(Background.EMPTY);
        valider_sp.getChildren().add(valider);
        valider_sp.setMaxSize(tailleDeCase, 40);
        valider_sp.setMinSize(tailleDeCase, 40);
        valider_sp.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(parties.getValue());
                Game game = controller.chargerGame("test.xml");
                controller.goToPlateau(game);
            }
        });
        if (height == max_screen_height) {
            AnchorPane.setBottomAnchor(valider_sp, (double) tailleDeCase * 1.5);
        } else {
            AnchorPane.setBottomAnchor(valider_sp, (double) tailleDeCase);
        }
        //AnchorPane.setTopAnchor(valider, (double) height - 50);
        AnchorPane.setLeftAnchor(valider_sp, (double) width / 2 - tailleDeCase);
        AnchorPane.setRightAnchor(valider_sp, (double) width / 2 - tailleDeCase);
        pane.getChildren().add(valider_sp);
        this.panePrincipale.getChildren().add(pane);

    }

    public void majRetourPreference() {
    }

}
