package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Model.Game.Cell;

/**
 * @author danielecarta
 * @author Elisa Maistri
 * */
public class ApolloDecorator extends GodPowerDecorator {

    /**
     * Constructor : sets the phase the decorator is decorating and a boolean that if it is set as true tells
     * that the decorator acts when it is played by an opponent of the god card's owner
     * @param decoratedPhase phase the decorator is going to decorate
     * @param bActAsOpponentGod true if the god card will act only when it is being played as an opponent god card, otherwise it is false
     */
    public ApolloDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {
        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method called by a decorated turn's movePhase, it adds cells to a list of candidate cells according to this god's power
     * It adds the standard candidate cells and also the neighbouring cells occupied by opponent's workers
     */

    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();

        // Apollo override only move phase
        if(IsAMovePhase()) {

            // Apollo adds cells to the current CandidateCells list, including the ones that are occupied
            // by other workers

            Cell startingCell = this.getWorker().getWorkerPosition();

            for (Cell candidateCell : this.getGameBoard().getNeighbouringCells(startingCell)) {

                    if(this.getCandidateCells().indexOf(candidateCell) == -1) {                 //for each candidate cell in neighbouringCells not already within the list , if
                        if ((candidateCell.getLevel() <= startingCell.getLevel() + 1) &&        //the lv i want to get to is higher less than one
                                (!candidateCell.checkDome())&&
                                candidateCell.isOccupiedByOpponentWorker(this.getPlayingPlayer())) {                                 //it is not occupied by a dome
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
     * This method performs the action described by the god's power on the cell chosen by the player
     * @param chosenCell the Cell the worker wants to move to
     */

    @Override
    public void performActionOnCell(Cell chosenCell) {

        // apollo can swap workers within move phase
        if(IsAMovePhase()) {

            if (chosenCell.isOccupiedByOpponentWorker(getWorker().getWorkerOwner())) {

                Cell oldCell = getWorker().getWorkerPosition();

                chosenCell.getOccupyingWorker().changePosition(oldCell);
            }

        }

        super.performActionOnCell(chosenCell);
    }

}
