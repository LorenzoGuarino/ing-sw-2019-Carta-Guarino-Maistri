package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Worker;

/**
 * @author danielecarta
 * @author Elisa Maistri
 * */
public class ApolloDecorator extends GodPowerDecorator {

    public ApolloDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {
        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method called by a decorated turn's movePhase, it makes it possible to move in an enemy occupied cell swapping places with that enemy worker
     * @return a list of cells which you can move onto
     */

    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();

        // Apollo override only move phase
        if(getDecoratedPhase().IsAMovePhase()) {

            // Apollo adds cells to the current CandidateCells list, including the ones that are occupied
            // by other workers

            Cell startingCell = this.getWorker().getWorkerPosition();

            for (Cell candidateCell : this.getGameBoard().getNeighbouringCells(startingCell)) {

                if(this.getCandidateCells().indexOf(candidateCell) == -1) {                 //for each candidate cell in neighbouringCells not already within the list , if
                    if ((candidateCell.getLevel() <= startingCell.getLevel() + 1) &&        //the lv i want to get to is higher less than one
                            (!candidateCell.checkDome())) {                                 //it is not occupied by a dome
                        System.out.println("APOLLO: evalCandidateCells inserting cell " + candidateCell.getCellIndex());
                        this.getCandidateCells().add(candidateCell);                        //then add the cell to candidateCells
                    } else {
                        System.out.println("APOLLO: evalCandidateCells discarding cell " + candidateCell.getCellIndex() + " (l=" +
                                candidateCell.getLevel() + ", w=" + candidateCell.isOccupiedByWorker() + ", d=" + candidateCell.checkDome());
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

        // apollo can swap workers within move phase
        if(getDecoratedPhase().IsAMovePhase()) {

            if (chosenCell.isOccupiedByOpponentWorker(this.getDecoratedPhase().getWorker().getWorkerOwner())) {

                Cell oldCell = getDecoratedPhase().getWorker().getWorkerPosition();

                chosenCell.getOccupyingWorker().changePosition(oldCell);
            }

        }

        super.performActionOnCell(chosenCell);
    }

}
