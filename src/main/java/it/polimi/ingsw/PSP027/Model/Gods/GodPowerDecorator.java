package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.TurnsManagement.ConcretePhase;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.Phase;

/**
 * @author danielecarta
 */

public abstract class GodPowerDecorator extends Phase {

    /**
     * The concrete phase the decorator goes to decorate
     */
    private ConcretePhase decoratedPhase;

    public GodPowerDecorator(ConcretePhase decoratedPhase) {
        this.decoratedPhase = decoratedPhase;
    }

    public ConcretePhase getDecoratedPhase() {
        return decoratedPhase;
    }

    public void setDecoratedPhase(ConcretePhase decoratedPhase) {
        this.decoratedPhase = decoratedPhase;
    }
}
