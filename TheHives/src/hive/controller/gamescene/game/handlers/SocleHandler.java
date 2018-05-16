/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.controller.gamescene.game.handlers;

import hive.controller.gamescene.game.GameController;
import hive.model.board.Cell;
import hive.model.game.Game;
import hive.model.players.actions.Action;
import hive.model.players.decisions.Decision;
import hive.model.players.decisions.HumanDecision;
import hive.vue.InterfacePlateau;
import hive.vue.InterfaceRuche;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import util.Vector2i;

/**
 * Appelé lorsque l'on clique sur une cellule du plateau vide
 *
 * @author Thomas
 */
public class SocleHandler implements EventHandler<MouseEvent>
{

    GameController controller;
    Game game;
    Cell cell;
    InterfaceRuche uiRuche;

    public SocleHandler(GameController controller, InterfacePlateau uiPlateau, Vector2i pos)
    {
        this.controller = controller;
        this.game = controller.progress.game;
        this.cell = new Cell(game.state.board.getHexagon(pos), 0);
        this.uiRuche = uiPlateau.ruche;
    }

    @Override
    public void handle(MouseEvent event)
    {
        System.out.println("--- SOCLE ---");

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
        {
            Decision decision = game.state.turn.getCurrent().decision;
            if (decision instanceof HumanDecision)
            {
                HumanDecision human_decision = (HumanDecision) decision;

                switch (controller.builder.getState())
                {
                    case BEGIN:
                        System.err.println("Impossible : vous devez selectionner une case contenant au moins une tile");
                        break;
                    case SOURCE_SELECTED:
                        if (cell != controller.builder.source) //si on ne clique pas sur la cellule deja selectionnée
                        {
                            System.out.println("Destination selectionnée");
                            HandlersUtils.moveOnBoard(controller, human_decision, cell, uiRuche);
                        } else
                        {
                            System.err.println("Aucun changement : source = destination");
                        }
                        break;
                    case TILE_SELECTED:
                        System.out.println("Placement selectionné");
                        HandlersUtils.putOnBoard(controller, human_decision, cell, uiRuche);
                        break;
                }
            }
        } else if (true) // autre evenement ? mouseOver ?
        {
            // information about the tile ? IA or not
            // etc
        }
    }
}
