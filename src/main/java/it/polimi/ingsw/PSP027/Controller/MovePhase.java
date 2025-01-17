package it.polimi.ingsw.PSP027.Controller;

import it.polimi.ingsw.PSP027.Model.Game.Cell;

/**
 * @author danielecarta
 * @author Lorenzo Guarino
 * @author Elisa Maistri
 */

public class MovePhase extends Phase {

    /**
     * Constructor: when creating the move phase it sets the phase's type as phase type "Move"
     */

    public MovePhase() {
        SetType(PhaseType.Move);
    }

    /**
     * Method that evaluates the candidate cells for a standard movePhase of any player and worker, collecting them in a list
     */

    @Override
    public void evalCandidateCells(){
        Cell startingCell = this.getWorker().getWorkerPosition();

        for(Cell candidateCell : this.getGameBoard().getNeighbouringCells(startingCell)){   //for each candidate cell in neighbouringCells if
            if(     (candidateCell.getLevel() <= startingCell.getLevel() + 1) &&   //the lv i want to get to is higher less than one
                    (!candidateCell.isOccupiedByWorker()) &&  //it is not occupied by a worker
                    (!candidateCell.checkDome())) {  //it is not occupied by a dome
                System.out.println("MOVE: evalCandidateCells inserting cell " + candidateCell.getCellIndex());
                this.getCandidateCells().add(candidateCell);  //then add the cell to candidateCells
                }
            else
            {
                System.out.println("MOVE: evalCandidateCells discarding cell " + candidateCell.getCellIndex() + " (l=" +
                        candidateCell.getLevel() + ", w=" + candidateCell.isOccupiedByWorker() + ", d=" + candidateCell.checkDome());
            }
        }
    }

    /**
     * Method that updates the board for a standard movePhase, performing the action of moving a worker on the board
     * @param chosenCell the cell the worker is going to step onto
     */

    @Override
    public void performActionOnCell(Cell chosenCell){
        this.getWorker().changePosition(chosenCell);
        this.getWorker().IncrementMoveCounter();

    }
}
