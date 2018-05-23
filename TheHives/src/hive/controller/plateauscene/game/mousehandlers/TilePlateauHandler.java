/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.controller.plateauscene.game.mousehandlers;

import hive.controller.plateauscene.game.GameController;
import hive.model.board.Cell;
import hive.model.players.decisions.HumanDecision;
import hive.vue.InterfacePlateau;
import javafx.scene.input.MouseEvent;

/**
 * Fait une action lorsque l'on selectionne la source
 *
 * @author Thomas
 */
public class TilePlateauHandler extends HandlerPlateau
{

    Cell cellClicked;

    public TilePlateauHandler(GameController gameController, InterfacePlateau uiPlateau, Cell cellClicked)
    {
        super(gameController, uiPlateau);
        this.cellClicked = cellClicked;
    }

    @Override
    public void handle(MouseEvent event)
    {
        System.out.println("--- TILE PLATEAU ---");
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
        {
            if (!(game.state.turn.getCurrent().decision instanceof HumanDecision))
            {
                return;
            }

            switch (controller.builder.getState())
            {
                case BEGIN:
                    if (cellClicked.getTile().color != controller.progress.game.state.turn.getCurrent().color)
                    {
                        return;
                    }
                    System.out.println("Source selectionnée");

                    controller.builder.setSource(cellClicked);
                    controller.builder.setPossibleDestinations(game.rules.getPossibleDestinations(game.state, cellClicked));

                    uiPlateau.ruche.selectCell(controller.builder.source.comb.pos);
                    uiPlateau.ruche.surlignerDestinationsPossibles(controller.builder.possibleDestinations);
                    event.consume();
                    break;
                case SOURCE_SELECTED:
                    if (cellClicked == controller.builder.source)
                    {
                        System.err.println("Même source : annulation de la selection");
                        uiPlateau.ruche.deselectCell(controller.builder.source.comb.pos);
                        uiPlateau.ruche.desurlignerDestinationsPossibles(controller.builder.possibleDestinations);
                        controller.builder.setBegin();
                        event.consume();
                    }
                    break;
                case TILE_SELECTED:
                    if (cellClicked.getTile().color == game.state.turn.getCurrent().color)
                    {
                        uiPlateau.getInterfacePlateauMain(game.state.turn.getCurrent().color).desurlignerTile(controller.builder.tile);
                        uiPlateau.ruche.desurlignerDestinationsPossibles(controller.builder.possibleDestinations);
                        controller.builder.setBegin();

                        controller.builder.setSource(cellClicked);
                        controller.builder.setPossibleDestinations(game.rules.getPossibleDestinations(game.state, cellClicked));

                        uiPlateau.ruche.selectCell(controller.builder.source.comb.pos);
                        uiPlateau.ruche.surlignerDestinationsPossibles(controller.builder.possibleDestinations);
                        System.out.println("Changement : on ne place pas, on selectionne une source");
                    }
                    event.consume();
                    break;
            }
        }
    }
}