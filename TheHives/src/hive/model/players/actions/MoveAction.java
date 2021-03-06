/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.model.players.actions;

import hive.model.board.Cell;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Thomas
 */
public class MoveAction implements Action, Serializable
{
    public Cell source;
    public Cell destination;
    
    public MoveAction() {} // for serialization
    
    public MoveAction(Cell source, Cell destination)
    {
        this.source = source;
        this.destination = destination;
    }
    
    @Override
    public void accept(ActionVisitor visitor)
    {
        visitor.visit(this);
    }
    
    @Override
    public String toString()
    {
        return "(" + source + " move to " + destination + ")";
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.source);
        hash = 83 * hash + Objects.hashCode(this.destination);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final MoveAction other = (MoveAction) obj;
        if (!Objects.equals(this.source, other.source))
        {
            return false;
        }
        if (!Objects.equals(this.destination, other.destination))
        {
            return false;
        }
        return true;
    }
}
