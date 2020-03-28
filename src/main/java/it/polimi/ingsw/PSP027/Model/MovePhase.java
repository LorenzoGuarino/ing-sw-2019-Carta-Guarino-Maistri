package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 */

public class MovePhase extends Phase {
    private List<Cell> candidateMoves;
    private GodPowerDecorator actualDecorator;
    private Worker chosenWorker;
    private Board gameBoard;

    /**
     * emptyConstructor
     */
    public MovePhase(){
        super();
    }
    /**
     * Constructor, builds a standard MovePhase with a standard candidateMoves list starting from a chosenWorker and the board he moves in
     * @param chosenWorker
     */

    public MovePhase(Worker chosenWorker,Board gameBoard) {
        this.gameBoard=gameBoard;
        this.chosenWorker=chosenWorker;
        Cell startingCell = chosenWorker.getWorkerPosition();


        this.candidateMoves = new ArrayList<>();
        for(Cell candidateCell : gameBoard.getNeighbouringCells(startingCell)){     //for each candidate cell in neighbouringCells if
            if(     (candidateCell.getLevel()<=startingCell.getLevel()+1)&&         //the lv i want to get to is higher less than one
                    (!candidateCell.isOccupiedByWorker())&&                         //it is not occupied by a worker
                    (!candidateCell.checkDome())){                                  //it is not occupied by a dome
                candidateMoves.add(candidateCell);                                  //then add the cell to candidateMoves
            }
        }
        //applyPower now works like an arbitrary choice of power apply
        //if(this.actualDecorator!=null) {                //if i have a decorator on this turn's movePhase
        //    changeCandidateMovesAccordingToDecorator(); //then change moveConditions according to this decorator
    }

    /**
     * Copy Constructor
     * @param movePhase
     */
    public MovePhase(MovePhase movePhase){
        this.chosenWorker=movePhase.chosenWorker;
        this.actualDecorator=movePhase.actualDecorator;
        this.chosenWorker=movePhase.chosenWorker;
        this.gameBoard=movePhase.gameBoard;
    }

    /**
     * Method that modifies the actual candidateMoves list according to the GodPowerDecorator decorating the movePhase's turn
     * If the decorator doesn't modify moveConditions returns without doing anything
     */

    public void changeCandidateMovesAccordingToDecorator(){
        List<Cell> candidateDecoratedMoves = this.actualDecorator.changeMoveConditions(chosenWorker,gameBoard);
        if(candidateDecoratedMoves==null)return;
        else this.candidateMoves=candidateDecoratedMoves;
    }
}
