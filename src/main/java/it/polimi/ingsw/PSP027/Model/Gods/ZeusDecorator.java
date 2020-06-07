package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Model.Game.Cell;

public class ZeusDecorator extends GodPowerDecorator {

    /**
     * Constructor : sets the phase the decorator is decorating and a boolean that if it is set as true tells
     * that the decorator acts when it is played by an opponent of the god card's owner
     * @param decoratedPhase phase the decorator is going to decorate
     * @param bActAsOpponentGod true if the god card will act only when it is being played as an opponent god card, otherwise it is false
     */
    public ZeusDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method used to get a standard set of candidate cells list for the build phase,
     * adding the one occupied by the worker
     */
    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();

        if(IsABuildPhase()) {

            // Zeus allow build even on the cell under worker, if not at third level
            if(this.getWorker().getWorkerPosition().getLevel()<3)
                this.getCandidateCells().add(this.getWorker().getWorkerPosition());
        }
    }

    /**
     * This method performs the action described by the god's power on the cell chosen by the player
     * @param chosenCell the Cell on which the worker wants to build onto
     */
    @Override
    public void performActionOnCell(Cell chosenCell) {

        if(IsABuildPhase()) {
            if (chosenCell.getLevel() < 3) {
                chosenCell.addLevel(true);
            } else {
                if (!chosenCell.isOccupiedByWorker())
                    chosenCell.addDome();
            }

            this.getWorker().setOldBuiltCell(chosenCell);
            this.getWorker().IncrementBuildCounter();
        }
        else {
            super.performActionOnCell(chosenCell);
        }

    }
}