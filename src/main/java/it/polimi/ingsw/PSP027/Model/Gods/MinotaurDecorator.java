package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.ConcretePhase;

/**
 * @author danielecarta
 * @author Elisa Maistri
 */

public class MinotaurDecorator extends GodPowerDecorator {

    public MinotaurDecorator(ConcretePhase decoratedPhase) {
        super(decoratedPhase);
    }

    /**
     * Method called by a decorated turn's movePhase, it makes it possible to move in an enemy occupied cell
     * if the next space is not occupied
     */

    @Override
    public void changeCandidateCells() {
        Cell startingCell = this.getDecoratedPhase().getChosenWorker().getWorkerPosition();
        for (Cell candidateCell : this.getDecoratedPhase().getGameBoard().getNeighbouringCells(startingCell)) {
            if ((candidateCell.getLevel() <= startingCell.getLevel() + 1) &&
                    (!candidateCell.checkDome())) {
                if (!candidateCell.isOccupiedByWorker())
                    this.getDecoratedPhase().getCandidateCells().add(candidateCell);
                else {

                    if (candidateCell.isOccupiedByOpponentWorker(this.getDecoratedPhase().getChosenWorker().getWorkerOwner())) {

                        Cell potentialTargetCellForOpponentWorker = this.getDecoratedPhase().getGameBoard().getNextCellAlongThePath(startingCell, candidateCell);

                        if(potentialTargetCellForOpponentWorker != null)
                        {
                            if(!potentialTargetCellForOpponentWorker.checkDome() && !potentialTargetCellForOpponentWorker.isOccupiedByWorker())
                                this.getDecoratedPhase().getCandidateCells().add(candidateCell);
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
    public void updateBoard(Cell chosenCell) {
        Worker myWorker = this.getDecoratedPhase().getChosenWorker();

        if (chosenCell.isOccupiedByOpponentWorker(myWorker.getWorkerOwner())) {
            Worker opponentWorker=chosenCell.getOccupyingWorker();

            opponentWorker.changePosition(this.getDecoratedPhase().getGameBoard().getNextCellAlongThePath(myWorker.getWorkerPosition(),chosenCell));
        }
        myWorker.changePosition(chosenCell);
    }
}

