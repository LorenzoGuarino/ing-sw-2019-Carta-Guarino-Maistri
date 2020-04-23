package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;

/**
 * @author danielecarta
 */

public class DemeterDecorator extends GodPowerDecorator {

    private boolean powerUsed = false;

    public DemeterDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * If the power hasn't yet been used, it gives a standard candidate cells list to build on
     */

    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();

        if(IsABuildPhase()) {
            // Demeter overrides only (second) build phase
            if(this.getWorker().getBuildCounter() == 1) {

                // Demeter excludes the cell it builf before

                Cell startingCell = this.getWorker().getWorkerPosition();

                for (Cell candidateCell : this.getGameBoard().getNeighbouringCells(startingCell)) {

                    if(this.getWorker().getLastBuiltCell().getCellIndex() == candidateCell.getCellIndex()) {
                        System.out.println("DEMETER: evalCandidateCells discarding cell " + candidateCell.getCellIndex() + " (l=" +
                                candidateCell.getLevel() + ", w=" + candidateCell.isOccupiedByWorker() + ", d=" + candidateCell.checkDome());
                        this.getCandidateCells().remove(candidateCell);
                    }
                }
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
