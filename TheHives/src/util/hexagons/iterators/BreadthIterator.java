/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.hexagons.iterators;

import java.util.ArrayDeque;
import util.hexagons.Hexagon;
import util.hexagons.HexagonSide;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Iterates over the graph from a hexagon in breadth search
 * and by looking at a predicate that elements in hexagon must verify
 * (the graph must be circled by elements that does not verify the predicate)
 * @author Thomas
 * @param <E>
 */
public class BreadthIterator<E> implements Iterator<Hexagon<E>>
{
    Queue<Hexagon<E>> queue;
    Set<Hexagon<E>> seen;
    Predicate<Hexagon<E>> predicate;
    
    public BreadthIterator(Hexagon<E> center, Predicate<Hexagon<E>> predicate)
    {
        this.queue = new ArrayDeque<>();
        this.seen = new HashSet<>();
        this.predicate = predicate;
        
        if(predicate.test(center))
        {
            queue.add(center);
            seen.add(center);
        }
    }

    @Override
    public boolean hasNext()
    {
        return !queue.isEmpty();
    }

    @Override
    public Hexagon<E> next()
    {
        assert hasNext();
        
        Hexagon<E> h = queue.remove();
        
        for(HexagonSide side : HexagonSide.values())
        {
            Hexagon<E> neighbor = h.getNeighbor(side);
            assert neighbor != null;
            if(!seen.contains(neighbor) && predicate.test(neighbor))
            {
                queue.add(neighbor);
                seen.add(neighbor);
            }
        }
        return h;
    }
}
