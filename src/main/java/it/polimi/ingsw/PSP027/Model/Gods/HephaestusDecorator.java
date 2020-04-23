package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;

public class HephaestusDecorator extends GodPowerDecorator {

    public HephaestusDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method used by Hephaestus to set the candidate cells on which its player can build on.
     * It's the standard method of checking if the neighbouring cell is not occupied by another worker
     * and has not already a dome
     */
    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();

        if(IsABuildPhase()) {

            // Hephaestus overrides only (second) build phase
            if(this.getWorker().getBuildCounter() == 1) {

                // Hephaestus allow build only on the cell it built before
                this.getCandidateCells().clear();
                this.getCandidateCells().add(this.getWorker().getLastBuiltCell());
            }
        }
    }

    /**
     * If the power hasn't been used yet, it updates the board with a standard building on a chosenCell,and then removes
     * the candidate cell from the candidate cells list, in order to let the player update the board again, but not onto
     * the same cell
     * @param chosenCell the Cell the worker wants to build onto
     */
    @Override
    public void performActionOnCell(Cell chosenCell) {

        super.performActionOnCell(chosenCell);
    }
}