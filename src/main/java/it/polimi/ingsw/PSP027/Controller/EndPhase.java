package it.polimi.ingsw.PSP027.Controller;

import it.polimi.ingsw.PSP027.Model.Game.Cell;

/**
 * @author Elisa Maistri
 */

public class EndPhase extends Phase {
    /**
     * Constructor: when creating the end phase it sets the phase's type as phase type "End"
     */

    public EndPhase() {
        SetType(PhaseType.End);
    }

    /**
     * An end phase does nothing when not decorated by a god power, so it does not evaluate candidate cells when standard
     * (empty function to be overridden when god power influences the end phase)
     */

    @Override
    public void evalCandidateCells(){
    }

    /**
     * An end phase does nothing when not decorated by a god power, so it does not perform any action on cells when standard
     * (empty function to be overridden when god power influences the end phase)
     */

    @Override
    public void performActionOnCell(Cell chosenCell){
    }
}
