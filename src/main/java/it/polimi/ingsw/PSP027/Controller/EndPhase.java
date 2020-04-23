package it.polimi.ingsw.PSP027.Controller;

import it.polimi.ingsw.PSP027.Model.Game.Cell;

public class EndPhase extends Phase {
    /**
     * Constructor: when creating the move phase it sets the phase's type as phase type "End"
     */

    public EndPhase() {
        SetType(PhaseType.End);
    }

    /**
     * That's not a standard phase, so it does not evaluate cells
     */

    @Override
    public void evalCandidateCells(){

    }

    /**
     * That's not a standard phase, so it does not perform a standard action on cell
     */

    @Override
    public void performActionOnCell(Cell chosenCell){
    }
}
