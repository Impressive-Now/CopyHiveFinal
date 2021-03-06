/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.model.game;

import hive.model.game.rules.HiveRules;
import hive.model.players.Player;
import hive.model.players.TeamColor;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Thomas
 */
public class Game implements Serializable
{
    public GameState state;
    public HiveRules rules;
    
    public Game() {} // for serialization
    
    public Game(GameState state, HiveRules rules)
    {
        this.state = state;
        this.rules = rules;
    }
    
    public Player getPlayer(TeamColor color)
    {
        return state.players.get(color == TeamColor.WHITE ? 0 : 1);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.state);
        hash = 59 * hash + Objects.hashCode(this.rules);
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
        final Game other = (Game) obj;
        if (!Objects.equals(this.state, other.state))
        {
            return false;
        }
        if (!Objects.equals(this.rules, other.rules))
        {
            return false;
        }
        return true;
    }
}
