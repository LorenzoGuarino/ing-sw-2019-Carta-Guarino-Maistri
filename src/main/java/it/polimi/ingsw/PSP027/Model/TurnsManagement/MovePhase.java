package it.polimi.ingsw.PSP027.Model.TurnsManagement;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import java.util.ArrayList;

/**
 * @author danielecarta
 */

public class MovePhase extends ConcretePhase {

    private int startChosenWorkerLvl;

    /**
     * Constructor, builds a standard MovePhase with a standard candidateMoves list
     * @param chosenWorker the worker i'm moving
     * @param gameBoard the board it's moving on
     */

    public MovePhase(Worker chosenWorker,Board gameBoard) {
        this.setGameBoard(gameBoard);
        this.setChosenWorker(chosenWorker);
        this.setStartChosenWorkerLvl(chosenWorker.getWorkerPosition().getLevel());
        this.setCandidateCells(new ArrayList<Cell>());
        changeCandidateCells();
    }

    /**
     * Standard candidate cells list for any movePhase of any player and worker
     */

    public void changeCandidateCells(){
        Cell startingCell = this.getChosenWorker().getWorkerPosition();
        for(Cell candidateCell : this.getGameBoard().getNeighbouringCells(startingCell)){   //for each candidate cell in neighbouringCells if
        if(     (candidateCell.getLevel() <= startingCell.getLevel() + 1) &&                //the lv i want to get to is higher less than one
                (!candidateCell.isOccupiedByWorker()) &&                                    //it is not occupied by a worker
                (!candidateCell.checkDome())) {                                             //it is not occupied by a dome
            this.getCandidateCells().add(candidateCell);                                    //then add the cell to candidateCells
            }
        }
    }

    /**
     * Standard update for a standard movePhase action
     * @param chosenCell the cell the worker is going to step onto
     */

    public void updateBoard(Cell chosenCell){
            this.getChosenWorker().changePosition(chosenCell);
    }

    public int getStartChosenWorkerLvl() {
        return startChosenWorkerLvl;
    }

    public void setStartChosenWorkerLvl(int startChosenWorkerLvl) {
        this.startChosenWorkerLvl = startChosenWorkerLvl;
    }
}
