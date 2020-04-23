package it.polimi.ingsw.PSP027.Controller;

import it.polimi.ingsw.PSP027.Model.Game.Cell;



public class EndPhase extends Phase {
    /**
     * Constructor: when creating the move phase it sets the phase's type as phase type "End"
     */

    public EndPhase() {
        SetType(Phase.PhaseType.End);
    }

    /**
     * Standard candidate cells list for any EndPhase of any player and worker.
     */

    @Override
    public void evalCandidateCells(){
    }

    /**
     * Standard update for a standard EndPhase action.
     * @param chosenCell
     * @TODO FIX
     */

    @Override
    public void performActionOnCell(Cell chosenCell){
        this.getWorker().getWorkerOwner().removeOpponentGodCards();
    }

}
