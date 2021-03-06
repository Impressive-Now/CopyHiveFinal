/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.controller.plateau.handlers.mousehandlers;

import hive.controller.plateau.PlateauController;
import hive.model.board.Tile;
import hive.model.game.rules.HiveUtil;
import hive.model.insects.InsectType;
import hive.model.players.TeamColor;
import hive.vue.NodePions;
import hive.vue.NodePlateauMain;
import javafx.event.Event;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 * Appelé lorque que l'on clique sur une tile d'une des pioches du joueur
 *
 * @author jonathan
 */
public class TileMainHandler extends PlateauHandler
{

    TeamColor color;
    NodePlateauMain uiMain;
    Tile tileClicked;
    NodePions source;

    public TileMainHandler(PlateauController controller, TeamColor color, InsectType insectType, NodePions source)
    {
        super(controller);
        tileClicked = new Tile(insectType, color);
        this.color = color;
        this.source = source;
    }

    @Override
    public void handlePlateau(Event event)
    {
        System.out.println("--- TILE MAIN ---");

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED || event.getEventType() == MouseEvent.DRAG_DETECTED)
        {
            if(event.getEventType() == MouseEvent.DRAG_DETECTED){
                Dragboard db = source.startDragAndDrop(TransferMode.ANY);
          
                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString("coucou");
                db.setContent(content);
          
                event.consume();
            }
            
            uiMain = uiPlateau.getInterfacePlateauMain(color);

            if (tileClicked.color != game.state.turn.getCurrent().color)
            {
                System.err.println("Vous n'avez pas selectionné un pion de votre couleur");
                return;
            }
            if (game.rules.queenMustBePut(game.state) && tileClicked.type != InsectType.QUEEN_BEE)
            {
                uiPlateau.message("Attention", "Vous devez posez votre reine");
            }
            switch (controller.builder.getState())
            {
                case BEGIN:
                    System.out.println("Tile selectionnée");
                    setTileAndPlacements();

                    event.consume();
                    break;
                case SOURCE_SELECTED:
                    System.out.println("Tile selectionnée");
                    uiPlateau.ruche.deselectCell(controller.builder.source.comb.pos);
                    uiPlateau.ruche.desurlignerDestinationsPossibles(controller.builder.possibleDestinations);
                    setTileAndPlacements();

                    event.consume();
                    break;
                case TILE_SELECTED:
                    if (tileClicked.type != controller.builder.tile.type)
                    {
                        System.out.println("Changement de tile");
                        uiMain.desurlignerTile(controller.builder.tile);
                        uiPlateau.ruche.desurlignerDestinationsPossibles(controller.builder.possibleDestinations);
                        setTileAndPlacements();
                    } else
                    {
                        System.err.println("Annuler la selection de la tile");
                        uiMain.desurlignerTile(tileClicked);
                        uiPlateau.ruche.desurlignerDestinationsPossibles(controller.builder.possibleDestinations);
                        controller.builder.setBegin();
                    }
                    event.consume();
                    break;
            }
        }
    }

    private void setTileAndPlacements()
    {
        controller.builder.setTile(tileClicked);
        controller.builder.setDestinations(HiveUtil.getPlacements(game, tileClicked.type));

        uiMain.surlignerTile(controller.builder.tile);
        uiPlateau.surlignerDestinationsPossibles(controller.builder.possibleDestinations);
    }
}
