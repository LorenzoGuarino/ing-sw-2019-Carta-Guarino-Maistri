package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;

/**
 * @author Elisa Maistri
 */
public class ArtemisDecorator extends GodPowerDecorator {

    public ArtemisDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * used to get a new candidate cells list after the first move is performed
     */

    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();

        if(IsAMovePhase()) {

            // Artemis overrides only (second) move phase
            if(this.getWorker().getMoveCounter() == 1) {

                // Artemis excludes the cell it started from from the new candidate moves

                Cell startingCell = this.getWorker().getWorkerPosition();

                for (Cell candidateCell : this.getGameBoard().getNeighbouringCells(startingCell)) {

                    if(this.getWorker().getWorkerPrevPosition().getCellIndex() == candidateCell.getCellIndex()) {
                        System.out.println("ARTEMIS: evalCandidateCells discarding cell " + candidateCell.getCellIndex() + " (l=" +
                                candidateCell.getLevel() + ", w=" + candidateCell.isOccupiedByWorker() + ", d=" + candidateCell.checkDome());
                        this.getCandidateCells().remove(candidateCell.getCellIndex());
                    }
                }
            }
        }
    }

    /**
     * Perform the move
     * @param chosenCell the cell to move to
     */

    @Override
    public void performActionOnCell(Cell chosenCell) {

        super.performActionOnCell(chosenCell);
    }
}
