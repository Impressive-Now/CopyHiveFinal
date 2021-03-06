/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.vue;

import hive.model.insects.InsectType;
import hive.model.players.TeamColor;
import static java.lang.Math.sqrt;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

/**
 *
 * @author jonathan
 */
public class NodePion extends Parent {

    public static int LONGUEUR = 40;
    public static int LARGEUR = (int) (LONGUEUR / 1.4);
    public Polygon hexagon;

    public NodePion(TeamColor couleur, InsectType typePions, CacheImage c) {

        Group g = new Group();
        hexagon = createHexagon(LONGUEUR);
        hexagon.setStrokeWidth(5);
        ImagePattern img = createImage(typePions, LONGUEUR, c, couleur);
        if (img != null) {
            hexagon.setFill(img);

        } else {
            hexagon.setFill(Color.TRANSPARENT);
        }
        g.getChildren().add(hexagon);

        this.getChildren().add(g);
    }

    public NodePion(TeamColor couleur, InsectType typePions, CacheImage c, int longueur) {
        Group g = new Group();
        hexagon = createHexagon(longueur);
        hexagon.setStrokeWidth(5);
        ImagePattern img = createImage(typePions, longueur, c, couleur);
        if (img != null) {
            hexagon.setFill(img);
        } else {
            hexagon.setFill(Color.TRANSPARENT);
        }

        g.getChildren().add(hexagon);

        this.getChildren().add(g);
    }

    private Polygon createHexagon(double side) {
        double center = ((sqrt(3) / 2) * side);
        double hauteur = sqrt(-Math.pow(center, 2) + Math.pow(side, 2));
        Polygon hexagon = new Polygon();

        //Adding coordinates to the polygon 
        hexagon.getPoints().addAll(new Double[]{
            0.0, center,
            hauteur, 2 * center,
            hauteur + side, 2 * center,
            side * 2, center,
            side + hauteur, 0.0,
            hauteur, 0.0,});
        hexagon.setStroke(Color.TRANSPARENT);

        return hexagon;
    }

    private ImagePattern createImage(InsectType type, int longueur, CacheImage c, TeamColor couleur) {
        ImagePattern v = null;
        Image i = null;
        if (type != null) {
            switch (type) {
                case QUEEN_BEE:
                    if (couleur == TeamColor.BLACK) {
                        i = c.getImage("FenetrePlateau/pionQueenB.png");
                    } else {
                        i = c.getImage("FenetrePlateau/pionQueenW.png");
                    }

                    v = new ImagePattern(i, 0, 0, 1, 1, true);
                    break;
                case GRASSHOPPER:
                    if (couleur == TeamColor.BLACK) {
                        i = c.getImage("FenetrePlateau/pionSauterelleB.png");
                    } else {
                        i = c.getImage("FenetrePlateau/pionSauterelleW.png");
                    }
                    v = new ImagePattern(i, 0, 0, 1, 1, true);
                    break;
                case BEETLE:
                    if (couleur == TeamColor.BLACK) {
                        i = c.getImage("FenetrePlateau/pionScarabeB.png");
                    } else {
                        i = c.getImage("FenetrePlateau/pionScarabeW.png");
                    }
                    v = new ImagePattern(i, 1, 1, 1, 1, true);

                    break;
                case SPIDER:
                    if (couleur == TeamColor.BLACK) {
                        i = c.getImage("FenetrePlateau/pionAraigneeB.png");
                    } else {
                        i = c.getImage("FenetrePlateau/pionAraigneeW.png");
                    }
                    v = new ImagePattern(i, 1, 1, 1, 1, true);

                    break;
                case SOLDIER_ANT:
                    if (couleur == TeamColor.BLACK) {
                        i = c.getImage("FenetrePlateau/pionFourmiB.png");
                    } else {
                        i = c.getImage("FenetrePlateau/pionFourmiW.png");
                    }
                    v = new ImagePattern(i, 1, 1, 1, 1, true);
                    break;

                default:
                    break;
            }
        }
        return v;
    }
}
