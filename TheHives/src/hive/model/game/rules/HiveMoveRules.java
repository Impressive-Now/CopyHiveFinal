/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.model.game.rules;

import hive.model.board.Cell;
import hive.model.game.GameState;
import hive.model.insects.InsectType;
import hive.model.insects.InsectsBehaviors;
import java.io.Serializable;
import java.util.function.Consumer;

/**
 *
 * @author Thomas
 */
public class HiveMoveRules implements MoveRules, Serializable
{
    public final InsectsBehaviors behaviors;
    
    public HiveMoveRules()
    {
        this(new InsectsBehaviors(InsectType.default_insects));
    }
    
    public HiveMoveRules(InsectsBehaviors behaviors)
    {
        this.behaviors = behaviors;
    }
    
    @Override
    public void consumeDestinations(GameState state, Cell cell, Consumer<Cell> consumer)
    {
        behaviors.get(cell.getTile().type).consumeDestinations(state, cell, consumer);
    }
    
    @Override
    public boolean isFree(GameState state, Cell cell)
    {
        return behaviors.get(cell.getTile().type).isFree(state, cell);
    }
}
