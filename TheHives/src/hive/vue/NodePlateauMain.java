/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.vue;

import hive.controller.plateau.PlateauController;
import hive.controller.plateau.handlers.mousehandlers.TileMainHandler;
import hive.model.board.Tile;
import hive.model.insects.InsectType;
import hive.model.players.PlayerCollection;
import hive.model.players.TeamColor;
import java.util.EnumMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author jonathan
 */
public class NodePlateauMain extends Parent {

    public VBox pions;
    private final Label labelNomJoueur;
    public boolean isCourant;
    private final TeamColor couleur;
    public EnumMap<InsectType, NodePions> pilesPions;
    public ImageView afficheTour;
    private final ImageView panneau;
    private final StackPane affichageJoueur;
    private final PlayerCollection col;
    private final PlateauController plateauController;

    CacheImage c;

    public NodePlateauMain(PlayerCollection col, String nomJoueur, CacheImage c, PlateauController plateauController, InterfacePlateau plateau, TeamColor color) {
        pions = new VBox();
        this.c = c;
        this.couleur = color;
        this.col = col;
        this.plateauController = plateauController;
        affichageJoueur = new StackPane();

        labelNomJoueur = new Label(nomJoueur);

        pilesPions = new EnumMap<>(InsectType.class);
        for (InsectType type : InsectType.implemented_insects) {
            pilesPions.put(type, new NodePions(color, col.get(type), type, c));
            pions.getChildren().add(pilesPions.get(type));
            if (col.get(type) != 0) {
                pilesPions.get(type).addEventHandler(MouseEvent.MOUSE_CLICKED, new TileMainHandler(plateauController, couleur, type, pilesPions.get(type)));
                pilesPions.get(type).addEventHandler(MouseEvent.DRAG_DETECTED, new TileMainHandler(plateauController, couleur, type, pilesPions.get(type)));

            }
        }

        pions.setPadding(new Insets(50, 20, 20, 10));
        pions.setAlignment(Pos.TOP_CENTER);

        panneau = new ImageView(c.getImage("FenetrePlateau/nom.png"));
        afficheTour = new ImageView(c.getImage("FenetrePlateau/bee.png"));

        afficheTour.setFitWidth(30);
        afficheTour.setPreserveRatio(true);
        afficheTour.setSmooth(true);

        StackPane.setAlignment(afficheTour, Pos.TOP_LEFT);
        StackPane.setAlignment(panneau, Pos.CENTER);
        StackPane.setAlignment(labelNomJoueur, Pos.CENTER);

        panneau.setFitWidth(150);
        panneau.setFitHeight(60);
        panneau.setSmooth(true);
        panneau.setEffect(new DropShadow(10, Color.TRANSPARENT));

        labelNomJoueur.setTextFill(Color.web("#fbe5b5"));
        labelNomJoueur.setAlignment(Pos.CENTER);
        labelNomJoueur.setMaxWidth(150);
        labelNomJoueur.setMaxHeight(40);
        labelNomJoueur.setFont(new Font(20));

        affichageJoueur.getChildren().add(panneau);
        affichageJoueur.getChildren().add(labelNomJoueur);
        affichageJoueur.getChildren().add(afficheTour);
        affichageJoueur.setPadding(new Insets(30, 0, 30, 0));

        afficheTour.setVisible(false);

        pions.getChildren().add(affichageJoueur);
        this.getChildren().add(pions);
    }

    public void surlignerTile(Tile tile) {
        pilesPions.get(tile.type).setSelected(Color.rgb(246, 6, 189));
    }

    public void desurlignerTile(Tile tile) {
        pilesPions.get(tile.type).unsetSelected();
    }

    public void maj() {
        this.update(col);
    }

    public void setIsCourant(boolean c) {
        afficheTour.setVisible(c);
        if (c) {
            panneau.setEffect(new DropShadow(10, Color.GOLD));
        } else {
            panneau.setEffect(new DropShadow(10, Color.TRANSPARENT));
        }

    }

    public void update(PlayerCollection collection) {
        pions.getChildren().clear();
        pilesPions.clear();
        for (InsectType type : InsectType.implemented_insects) {
            pilesPions.put(type, new NodePions(couleur, collection.get(type), type, c));
            pions.getChildren().add(pilesPions.get(type));
            if (collection.get(type) != 0) {
                pilesPions.get(type).addEventHandler(MouseEvent.MOUSE_CLICKED, new TileMainHandler(plateauController, couleur, type, pilesPions.get(type)));
                pilesPions.get(type).addEventHandler(MouseEvent.DRAG_DETECTED, new TileMainHandler(plateauController, couleur, type, pilesPions.get(type)));
                pilesPions.get(type).addEventHandler(DragEvent.DRAG_DONE, new TileMainHandler(plateauController, couleur, type, pilesPions.get(type)));
            }
        }
        pions.getChildren().add(affichageJoueur);
    }
    
    public void changerNom(String nom){
        this.labelNomJoueur.setText(nom);
    }
}
