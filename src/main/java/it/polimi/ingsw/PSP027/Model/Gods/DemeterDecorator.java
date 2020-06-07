package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;

/**
 * @author danielecarta
 */

public class DemeterDecorator extends GodPowerDecorator {

    /**
     * Constructor : sets the phase the decorator is decorating and a boolean that if it is set as true tells
     * that the decorator acts when it is played by an opponent of the god card's owner
     * @param decoratedPhase phase the decorator is going to decorate
     * @param bActAsOpponentGod true if the god card will act only when it is being played as an opponent god card, otherwise it is false
     */
    public DemeterDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method used to get a new set of candidate cells list for the build phase after the first build is performed,
     * removing the previous cell on which the worker has built on
     */

    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();

        if(IsABuildPhase()) {
            // Demeter overrides only (second) build phase
            if(this.getWorker().getBuildCounter() == 1) {

                // Demeter excludes the cell it built before

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
     * This method performs the action described by the god's power on the cell chosen by the player
     * @param chosenCell the Cell on which the worker wants to build onto
     */
    @Override
    public void performActionOnCell(Cell chosenCell) {

        super.performActionOnCell(chosenCell);
    }
}
