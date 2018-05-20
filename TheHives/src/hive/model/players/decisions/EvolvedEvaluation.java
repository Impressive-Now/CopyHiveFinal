/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.model.players.decisions;

import hive.model.HiveInterfaceIA;
import hive.model.board.Tile;
import hive.model.game.Game;
import hive.model.players.Player;
import java.util.ArrayList;

/**
 *
 * @author Coralie
 */
public class EvolvedEvaluation {
    //ajout atribut
    static int evolvedEvaluation(Game state){
        HiveInterfaceIA hia = new HiveInterfaceIA();
        Player current = hia.currentPlayer(state);
        Player opponent = hia.opponentPlayer(state);
        int value;
        if(hia.winOpponent(state)){
            return -10000;
        }
        else if(hia.winCurrent(state)){
            return 10000;
        }
        else{
            ArrayList<Tile> currentFreeTile = hia.freeTiles(state, current);
            value = EvolvedInsectsValue(currentFreeTile);
            ArrayList<Tile> opponentFreeTile = hia.freeTiles(state, opponent);
            value -= EvolvedInsectsValue(opponentFreeTile);
            ArrayList<Tile> currentBlocTile = hia.blockedTiles(current, state);
            value -= evolvedInsectsValueBloc(currentBlocTile);
            ArrayList<Tile> opponentBlocTile = hia.blockedTiles(opponent, state);
            value += evolvedInsectsValueBloc(opponentBlocTile);
            value += evolvedEvalQueen(state);
            value += evolvedValueNeighboursQueen(state);
        }
        return value;
    }
   
    
    static int evolvedEvalQueen( Game state){
        HiveInterfaceIA hia = new HiveInterfaceIA();
        int values=0;
        Player opponent = hia.opponentPlayer(state);
        Player current = hia.currentPlayer(state);
        int queenOpponentPossibilities = hia.nbPossibilitiesQueen(state, opponent);
        int queenCurrentPossibilities = hia.nbPossibilitiesQueen(state, current);
        switch (queenOpponentPossibilities) {
            case 0:
                values+= 50;
                break;
            case 1:
                values+= 50;
                break;
            default:
                values-= 50;
                break;
        }
        switch (queenCurrentPossibilities) {
            case 0:
                values-= 100;
                break;
            case 1:
                values-= 50;
                break;
            default:
                values+= 50;
                break;
        }
        
        
        return values;
    }
    
    static int evolvedValueNeighboursQueen( Game state){
        int value = 0;
        HiveInterfaceIA hia = new HiveInterfaceIA();
        Player opponent = hia.opponentPlayer(state);
        Player current = hia.currentPlayer(state);
        ArrayList<Tile> neighboursOpponent = hia.queenNeighbours(opponent, state);
        ArrayList<Tile> neighboursCurrent = hia.queenNeighbours(current, state);
        Tile tuile;
        while (!neighboursOpponent.isEmpty()) {
            tuile = neighboursOpponent.remove(0);
            value += evolvedInsectsValueNeighboursQueenOpponent(tuile, state);

        }
        while (!neighboursCurrent.isEmpty()) {
            tuile = neighboursCurrent.remove(0);
            value -= evolvedInsectsValueNeighboursQueenCurrent(tuile, state);

        }
        return value;
    }
    
    static int EvolvedInsectsValue(ArrayList<Tile> freeTile){
        Tile currentTile;
        int value = 0;
            while(!freeTile.isEmpty()){
                currentTile = freeTile.remove(0);
                switch (currentTile.type) 
                {
                    case  QUEEN_BEE:
                        value += 100;
                        break;
                    case  GRASSHOPPER:
                        value += 20;
                        break;
                    case  SOLDIER_ANT:
                        value += 50;
                        break;
                    case  SPIDER:
                        value += 5;
                        break;
                    case  BEETLE:
                        value += 10;
                        break;
                }
                
            }
            return value;
    }
    
    static int evolvedInsectsValueBloc(ArrayList<Tile> blocTile){
        Tile currentTile;
        int value = 0;
            while(!blocTile.isEmpty()){
                currentTile = blocTile.remove(0);
                switch (currentTile.type) 
                {
                    case  QUEEN_BEE:
                        value += 100;
                        break;
                    case  GRASSHOPPER:
                        value += 80;
                        break;
                    case  SOLDIER_ANT:
                        value += 90;
                        break;
                    case  SPIDER:
                        value += 80;
                        break;
                    case  BEETLE:
                        value += 80;
                        break;
                }
                
            }
            return value;
    }
    
    static int evolvedInsectsValueNeighboursQueenCurrent(Tile tile, Game state){
        HiveInterfaceIA hia = new HiveInterfaceIA();
        if (tile.color == hia.currentPlayer(state).color) {
            switch (tile.type) {
                case QUEEN_BEE:
                    return 0;
                default:
                    return 100;

            }
        }else{
            switch (tile.type) {
                case QUEEN_BEE:
                    return 0;
                case BEETLE:
                    return 200;
                default:
                    return 120;

            }
        }
    }
    static int evolvedInsectsValueNeighboursQueenOpponent(Tile freeTile, Game state){
            HiveInterfaceIA hia = new HiveInterfaceIA();
            if (freeTile.color == hia.currentPlayer(state).color) {
                switch (freeTile.type) {
                    case QUEEN_BEE:
                        return 0;
                    case GRASSHOPPER:
                        return 175;
                    case SOLDIER_ANT:
                        return 100;
                    case SPIDER:
                        return 160;
                    case BEETLE:
                        return 150;
                    default:
                        return 100;

                }
            }else{
                return 0;
            }

    }
    
}