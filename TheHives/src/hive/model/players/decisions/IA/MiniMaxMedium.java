/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.model.players.decisions.IA;

import hive.model.HiveInterfaceIA;
import hive.model.board.Tile;
import hive.model.game.Game;
import hive.model.players.actions.Action;
import hive.model.players.actions.NoAction;
import static java.lang.Integer.max;
import static java.lang.Integer.min;
import java.util.ArrayList;
import static hive.model.players.decisions.IA.EvaluationMedium.evaluationMedium;

/**
 *
 * @author Coralie
 */
public class MiniMaxMedium{
    static ArrayList<Tile> neighboursBlock = new ArrayList(22);
    static ArrayList<Tile> neighboursFree = new ArrayList(22) ;
    
    static public int miniMaxCurrentPlayer(Game state, int depth, int min, ArrayList<Action>[] actionList){
        HiveInterfaceIA hia = new HiveInterfaceIA();
        if(depth == 0 || hia.winCurrent(state) || hia.winOpponent(state)){
            return evaluationMedium(state);
        }
        else{
            int vMax = -5000000;
            hia.currentPlayerPossibilities(state,actionList[depth], Heuristic.insects_min);
            int nbCoup = actionList[depth].size();
            int tmp;
            Action currentAction;
            if(actionList[depth].isEmpty()){
                hia.doAction(state, new NoAction());
                vMax=max(miniMaxOpponent(state, depth-1, vMax,actionList),vMax);
                hia.undoAction(state);
                
            }
            else{
                while(!actionList[depth].isEmpty()){
                    currentAction = actionList[depth].remove(actionList[depth].size()-1);
                    assert currentAction!=null;
                    hia.doAction(state,currentAction);
                    tmp = miniMaxOpponent(state, depth-1, vMax,actionList);
                    hia.undoAction(state);
                    vMax = max(tmp,vMax);
                    if(vMax > min){
                        actionList[depth].clear();
                        return vMax;
                    }
                }
            }
            return vMax;
        }
    }
    static public int miniMaxOpponent(Game state, int depth, int max,ArrayList<Action>[] actionList){
        HiveInterfaceIA hia = new HiveInterfaceIA();
        if(depth == 0 || hia.winCurrent(state)|| hia.winOpponent(state)){
            return -(evaluationMedium(state));
        }
        else{
            int vMin = 5000000;
            hia.currentPlayerPossibilities(state,actionList[depth], Heuristic.insects_max);
            int tmp;
            Action currentAction;
            if(actionList[depth].isEmpty()){
                hia.doAction(state, new NoAction());
                vMin=min(miniMaxCurrentPlayer(state, depth-1, vMin, actionList),vMin);
                hia.undoAction(state);
                
            }
            else{
                while(!actionList[depth].isEmpty()){
                    currentAction = actionList[depth].remove(actionList[depth].size()-1);
                    assert currentAction!=null;
                    hia.doAction(state,currentAction);
                    tmp = miniMaxCurrentPlayer(state, depth-1, vMin,actionList);
                    hia.undoAction(state);
                    vMin = min(tmp,vMin);
                    if(vMin < max){
                        actionList[depth].clear();
                        return vMin;
                    }
                }
            }
            return vMin;
        }
    }
 
}
