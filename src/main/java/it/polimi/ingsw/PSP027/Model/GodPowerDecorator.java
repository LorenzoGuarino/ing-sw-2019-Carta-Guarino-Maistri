package it.polimi.ingsw.PSP027.Model;

import java.util.List;

/**
 * @author danielecarta
 */

public abstract class GodPowerDecorator extends Turn {
    /**
     * The concrete turn the decorator goes to decorate
     */
    private ConcreteTurn decoratedTurn;

    /**
     * Constructor
     * @param decoratedTurn
     */
    public GodPowerDecorator(ConcreteTurn decoratedTurn) {
        this.decoratedTurn = decoratedTurn;
    }

    /**
     * Getter
     * @return decoratedTurn
     */
    public ConcreteTurn getDecoratedTurn() {
        return decoratedTurn;
    }

    /**
     * Standard changeMoveCondition method
     * @param chosenWorker
     * @return null
     */
    public List<Cell> changeMoveConditions(Worker chosenWorker,Board gameBoard){
        return null;
    }

    /**
     * Standard changeBuildCondition method
     * @param chosenWorker
     * @return null
     */
    public List<Cell> changeBuildConditions(Worker chosenWorker,Board gameBoard){
        return null;
    }

    public abstract void applyPower();
}
