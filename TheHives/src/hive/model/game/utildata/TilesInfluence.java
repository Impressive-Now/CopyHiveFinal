/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.model.game.utildata;

import hive.model.board.Honeycomb;
import hive.model.board.TilesStack;
import java.util.HashMap;
import util.hexagons.iterators.NeighborsIterator;

/**
 * It "draws" a circle of influence around a tile
 * (a way to precalculate the neighbors and indirectly potential placements for put actions)
 * @author Thomas
 */
public class TilesInfluence extends HashMap<Honeycomb, Integer>
{
    private void addDeltaOccurences(Honeycomb comb, int delta)
    {
        assert get(comb) == null || (get(comb) != null && get(comb) > 0);
        
        Integer n = get(comb);
        if(n == null)
            put(comb, delta);
        else
        {
            put(comb, n + delta);
            if(get(comb) == 0)
                remove(comb);
        }
    }
    
    private void addDeltaInfluence(Honeycomb comb, int delta)
    {
        NeighborsIterator<TilesStack> neighbors = new NeighborsIterator<>(comb);
        while(neighbors.hasNext())
            addDeltaOccurences((Honeycomb)neighbors.next().hexagon, delta);
    }
    
    // specialization
    public void addInfluence(Honeycomb comb)
    {
        addDeltaInfluence(comb, 1);
    }
    
    // specialization
    public void removeInfluence(Honeycomb comb)
    {
        addDeltaInfluence(comb, -1);
    }
}
