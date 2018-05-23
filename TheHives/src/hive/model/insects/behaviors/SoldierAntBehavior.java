/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.model.insects.behaviors;

import hive.model.board.Cell;
import hive.model.game.rules.HiveUtil;
import hive.model.board.Honeycomb;
import hive.model.board.Tile;
import hive.model.board.TilesStack;
import hive.model.game.GameState;
import hive.model.insects.InsectBehavior;
import java.util.ArrayList;
import util.hexagons.iterators.BreadthNeighborsIterator;

/**
 *
 * @author Thomas
 */
public class SoldierAntBehavior implements InsectBehavior
{

    @Override
    public ArrayList<Cell> getPossibleDestinations(GameState state, Cell cell)
    {
        assert cell.level == 0;
        
        ArrayList<Cell> list = new ArrayList<>();
        
        if(HiveUtil.isCrushed(cell) || !HiveUtil.isConnexWithout(state, cell))
            return list;
        
        Tile tmp = cell.comb.value().pop();
        
        // breadth first search on neighbors of the cell,
        // that have a wall next to it and free to access, from one to an other
        BreadthNeighborsIterator<TilesStack> iterator = new BreadthNeighborsIterator<>(
                cell.comb,
                neighbor -> HiveUtil.hasWallNextToAtSide(new Cell((Honeycomb)neighbor.origin, 0), neighbor.from) && HiveUtil.isFreeAtSide(new Cell((Honeycomb)neighbor.origin, 0), neighbor.from));
        
        while(iterator.hasNext())
            list.add(new Cell((Honeycomb)iterator.next()));
        
        cell.comb.value().push(tmp);
        
        return list;
    } 

    @Override
    public boolean isFree(GameState state, Cell cell)
    {
        assert cell.level == 0;
                
        if(HiveUtil.isCrushed(cell) || !HiveUtil.isConnexWithout(state, cell))
            return false;
        
        Tile tmp = cell.comb.value().pop();
        
        // breadth first search on neighbors of the cell,
        // that have a wall next to it and free to access, from one to an other
        BreadthNeighborsIterator<TilesStack> iterator = new BreadthNeighborsIterator<>(
                cell.comb,
                neighbor -> HiveUtil.hasWallNextToAtSide(new Cell((Honeycomb)neighbor.origin, 0), neighbor.from) && HiveUtil.isFreeAtSide(new Cell((Honeycomb)neighbor.origin, 0), neighbor.from));
        
        while(iterator.hasNext())
        {
            cell.comb.value().push(tmp);
            return true;
        }
        
        cell.comb.value().push(tmp);
        
        return false;
    }
    
}
