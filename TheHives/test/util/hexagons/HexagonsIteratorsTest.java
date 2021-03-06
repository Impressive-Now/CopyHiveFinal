/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.hexagons;

import hive.model.board.HiveNeighborsShifter;
import java.util.function.Predicate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import util.Matrix;
import util.Vector2i;
import util.hexagons.CircularHexagonsGraph;
import util.hexagons.Hexagon;
import util.hexagons.HexagonSide;
import util.hexagons.iterators.BreadthIterator;
import util.hexagons.iterators.LineAtSideIterator;
import util.hexagons.iterators.NeighborsIterator;

/**
 *
 * @author lucas
 */
public class HexagonsIteratorsTest
{

    private Matrix<Integer> m;  
    private CircularHexagonsGraph<Integer, Hexagon<Integer>> c;
    
    public HexagonsIteratorsTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    private Integer[] setConsecutives(Integer[] tab, int start)
    {
        for(int i = 0; i < tab.length; ++i)
        {
            tab[i] = new Integer(start++);
        }
        return tab;
    }
    
    private Integer[][] getConsecutives2D(int start, int sizeX, int sizeY)
    {
        Integer[][] tab = new Integer[sizeY][sizeX];
        
        int start_inc = start;
        for(Integer[] line : tab)
        {
            setConsecutives(line, start_inc);
            start_inc += line.length;
        }
        return tab;
    }
    
    @Before
    public void setUp()
    {
        Integer[][] tab = 
        {
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,16}
        };
        HiveNeighborsShifter getter = new HiveNeighborsShifter();
        
        m = new Matrix<>(tab);
        c = new CircularHexagonsGraph(m, getter, (x, y) -> new Hexagon());
    }
    
    @After
    public void tearDown()
    {
    }
    
    public void testLineAtSideIteratorForSide(HexagonSide side, int nbElem, String chaineAttendu)
    {
        LineAtSideIterator<Integer> lineIterator = new LineAtSideIterator(c.getHexagon(new Vector2i(0,0)), side);
        String chaineObtenue = "";
        int i = 0;
        while (lineIterator.hasNext() && i++ < nbElem)
        {
            chaineObtenue += lineIterator.next().value().toString() + " ";
        }
        assert chaineObtenue.equals(chaineAttendu);
    }

    /**
     * Test of next method, of class LineAtSideIterator.
     */
    @Test
    public void testLineAtSideIterator()
    {
        testLineAtSideIteratorForSide(HexagonSide.A, 4, "13 9 5 1 ");
        testLineAtSideIteratorForSide(HexagonSide.B, 4, "14 15 12 9 ");
        testLineAtSideIteratorForSide(HexagonSide.C, 4, "2 7 8 9 ");
        testLineAtSideIteratorForSide(HexagonSide.D, 4, "5 9 13 1 ");
        testLineAtSideIteratorForSide(HexagonSide.E, 5, "4 7 6 9 12 ");
        testLineAtSideIteratorForSide(HexagonSide.F, 5, "16 15 10 9 8 ");
    }
    
    @Test
    public void testNeighborsIterator()
    {
        NeighborsIterator<Integer> neighIterator = new NeighborsIterator<>(c.getHexagon(new Vector2i(1,1)));
        
        String chaineAttendue = "2 7 11 10 9 5 ";
        String chaineObtenue = "";
        
        while (neighIterator.hasNext())
        {
            chaineObtenue += neighIterator.next().hexagon.value().toString() + " ";
        }
        
        Assert.assertEquals(chaineAttendue, chaineObtenue);
    }
    
    @Test
    public void testBreadthIterator()
    {
        Predicate<Hexagon<Integer>> p = (h) -> h.value() > 0;
        
        BreadthIterator<Integer> breadthIterator = new BreadthIterator<>(c.getHexagon(new Vector2i(1,1)), p);
        
        String chaineAttendue = "6 2 7 11 10 9 5 14 3 1 4 8 12 15 13 16 ";
        String chaineObtenue = "";
        
        while (breadthIterator.hasNext())
        {
            Hexagon<Integer> hexagon = breadthIterator.next();
            chaineObtenue += hexagon.value().toString() + " ";
        }
        
        Assert.assertEquals(chaineAttendue, chaineObtenue);
    }
}
