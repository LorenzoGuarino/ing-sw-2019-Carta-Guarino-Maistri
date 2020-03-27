package it.polimi.ingsw.PSP027.Model;

import it.polimi.ingsw.PSP027.Model.ConcreteTurn;
import it.polimi.ingsw.PSP027.Model.Turn;

/**
 * @author danielecarta
 */

public abstract class GodPowerDecorator extends Turn {

    private ConcreteTurn decoratedTurn;

    public ConcreteTurn getDecoratedTurn() {
        return decoratedTurn;
    }

    public void setDecoratedTurn(ConcreteTurn decoratedTurn) {
        this.decoratedTurn = decoratedTurn;
    }

    public GodPowerDecorator(ConcreteTurn decoratedTurn) {
        this.decoratedTurn = decoratedTurn;
    }

    public abstract void applyPower();
}
