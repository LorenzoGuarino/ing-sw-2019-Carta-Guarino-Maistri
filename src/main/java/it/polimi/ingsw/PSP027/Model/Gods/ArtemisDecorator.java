package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;

/**
 * @author Elisa Maistri
 */
public class ArtemisDecorator extends GodPowerDecorator {

    /**
     * Constructor : sets the phase the decorator is decorating and a boolean that if it is set as true tells
     * that the decorator acts when it is played by an opponent of the god card's owner
     * @param decoratedPhase phase the decorator is going to decorate
     * @param bActAsOpponentGod true if the god card will act only when it is being played as an opponent god card, otherwise it is false
     */
    public ArtemisDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method used to get a new set of candidate cells list for the move phase after the first move is performed,
     * removing the cell from which it started the turn
     */

    @Override
    public void evalCandidateCells() {

        this.getCandidateCells().clear();
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
                        this.getCandidateCells().remove(candidateCell);
                    }
                }
            }
        }
    }

    /**
     * This method performs the move in a standard way
     * @param chosenCell the Cell the worker wants to move to
     */

    @Override
    public void performActionOnCell(Cell chosenCell) {

        super.performActionOnCell(chosenCell);
    }
}
