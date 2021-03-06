/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.model.board;

import java.io.Serializable;
import util.Matrix;
import util.Vector2i;
import util.hexagons.CircularHexagonsGraph;

/**
 *     A
 *     _
 * F /   \ B
 * E \ _ / C
 *     D
 * 
 *   _       _
 * /   \ _ /   \ _  . . .
 * \ _ /   \ _ /    . . .
 * /   \ _ /   \ _  . . .
 * \ _ /   \ _ /    . . .
 * /   \ _ /   \    . . .
 *  . . . . . . . . . . .
 *  . . . . . . . . . . .
 *  . . . . . . . . . . .
 * 
 * @author Thomas
 */
public class Board extends CircularHexagonsGraph<TilesStack, Honeycomb> implements Serializable
{   
    public Board() {} // for serialization
    
    public Board(Matrix<TilesStack> matrix)
    {
        super(matrix, new HiveNeighborsShifter(), (x, y) -> new Honeycomb(new Vector2i(x, y)));
    }
    
    // to string
    @Override
    public String toString()
    {
        String res = "";
        res += toStringLine("  _______  ", "       ");
        res += "\n";
        for (int y = 0; y < getData().getData().length; y++)
        {
            res += toStringLine(" /       \\ ", "       ");
            res += "\n";
            res += toStringThirdLine(y);
            res += "\n";
            res += toStringLine("\\         /", "       ");
            res += "\n";
            res += toStringFifthLine(y);
            res += "\n";
        }
        res += "         ";
        res += toStringLine("\\         / ", "      ");
        res += "\n";
        res += "          ";
        res += toStringLine("\\_______/ ", "        ");

        return res;
    }

    private String toStringLine(String isCaseStr, String isNotCaseStr)
    {
        String res = "";
        boolean isCase = true;
        for (Object item : getData().getData()[0])
        {
            res += isCase ? isCaseStr : isNotCaseStr;
            isCase = !isCase;
        }
        return res;
    }

    private String toStringThirdLine(int y)
    {
        String res = "";
        boolean isCase = true;
        for (Object item : getData().getData()[y])
        {
            String str = item != null && !item.toString().equals("[]") ? displayStack((TilesStack)item) : "     ";
            res += isCase ? "/  " + str + "  \\" : "_______";
            isCase = !isCase;
        }
        return res;
    }

    private String toStringFifthLine(int y)
    {
        String res = "";
        boolean isCase = true;
        for (Object item : getData().getData()[y])
        {
            String str = item != null && !item.toString().equals("[]") ? displayStack((TilesStack)item) : "     ";
            res += isCase ? " \\_______/ " : " " + str + " ";
            isCase = !isCase;
        }
        return res;
    }
    
    private String displayStack(TilesStack stack)
    {
        assert !stack.isEmpty();
        if(stack.size() == 1)
            return " " + stack.peek() + " ";
        else
        {
            return stack.peek() + " " + stack.size();
        }
    }
}
