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

    public MinotaurDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method called by a decorated turn's movePhase, it makes it possible to move in an enemy occupied cell
     * if the next space is not occupied
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
     * This method is called in a movePhase in order to update the board according to the decorator,in case it modifies the board itself
     * @param chosenCell the cell it is stepping onto
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

