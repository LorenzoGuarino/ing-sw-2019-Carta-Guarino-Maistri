package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Controller.MovePhase;

/**
 * @author danielecarta
 * @author Elisa Maistri
 */

public class MinotaurDecorator extends GodPowerDecorator {

    /**
     * Constructor : sets the phase the decorator is decorating and a boolean that if it is set as true tells
     * that the decorator acts when it is played by an opponent of the god card's owner
     * @param decoratedPhase phase the decorator is going to decorate
     * @param bActAsOpponentGod true if the god card will act only when it is being played as an opponent god card, otherwise it is false
     */
    public MinotaurDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method called by a decorated turn's movePhase, adding to the list of candidate cells the standard ones and those occupied by
     * opponent's workers if is possible to move them in the movement's direction
     */
    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();

        // Minotaur override only move phase
        if(IsAMovePhase()) {

            Cell startingCell = this.getWorker().getWorkerPosition();

            for (Cell candidateCell : this.getGameBoard().getNeighbouringCells(startingCell)) {
                if (!this.getCandidateCells().contains(candidateCell)) {

                    if ((candidateCell.getLevel() <= startingCell.getLevel() + 1) && (!candidateCell.checkDome()))
                    {
                        // free cells must be already present within the list
                        // Minotaur add occupied cells if occupying opponent worker can be kicked forward
                        if (candidateCell.isOccupiedByOpponentWorker(this.getWorker().getWorkerOwner())) {
                            Cell potentialTargetCellForOpponentWorker = this.getGameBoard().getNextCellAlongThePath(startingCell, candidateCell);
                            if (potentialTargetCellForOpponentWorker != null) {
                                if (!potentialTargetCellForOpponentWorker.checkDome() && !potentialTargetCellForOpponentWorker.isOccupiedByWorker()) {
                                    System.out.println("MINOTAUR: evalCandidateCells inserting cell " + candidateCell.getCellIndex());
                                    this.getCandidateCells().add(candidateCell);
                                }
                                else {
                                    System.out.println("MINOTAUR: evalCandidateCells discarding cell " + candidateCell.getCellIndex() + " (l=" +
                                            candidateCell.getLevel() + ", w=" + candidateCell.isOccupiedByWorker() + ", d=" + candidateCell.checkDome());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method performs the action described by the god's power on the cell chosen by the player
     * @param chosenCell the Cell on which the worker wants to move onto
     */
    @Override
    public void performActionOnCell(Cell chosenCell) {
        // Minotaur can move in an opponent's space forcing it to move
        if(IsAMovePhase()) {
            if (chosenCell.isOccupiedByOpponentWorker(getWorker().getWorkerOwner())) {

                Worker opponentWorker = chosenCell.getOccupyingWorker();

                opponentWorker.changePosition(getGameBoard().getNextCellAlongThePath(getWorker().getWorkerPosition(),chosenCell));
            }
        }
        super.performActionOnCell(chosenCell);
    }
}

