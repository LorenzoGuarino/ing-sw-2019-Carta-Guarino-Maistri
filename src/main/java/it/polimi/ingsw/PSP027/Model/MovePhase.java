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
        List<Cell> candidateDecoratedMoves = this.getActualDecorator().changeCandidateMoves(this.getChosenWorker(),this.getGameBoard());//player's good decorator
        if(candidateDecoratedMoves==null)return;
        else this.setCandidateMoves(candidateDecoratedMoves);
    }

    /**
     * given the parameters it updates the gameBoard according to the move choice
     * @param chosenWorker the worker i choose to move
     * @param board the board that's going to be modified
     * @param chosenCell the cell the worker is going to step onto
     */
    public void updateBoardAfterMove(Worker chosenWorker,Board board,Cell chosenCell){
        Board gameBoard = this.getGameBoard();
        if(this.getActualDecorator()==null) {                                           //if i haven't a decorator that changed my moveConditions
            this.getChosenWorker().getWorkerPosition().setOccupiedByWorker(false);      //old cell is no longer occupied
            this.getChosenWorker().setPosition(chosenCell);                             //worker's position update, cell is now occupiedByWorker
        }
        else{                                                                           //if i have a decorator that changed my moveConditions
            this.getActualDecorator().updateBoard(chosenWorker,board,chosenCell);       //then i update the Board according to this dec
        }
    }

    public void setCandidateMoves(List<Cell> candidateMoves) {
        this.candidateMoves = candidateMoves;
    }

    public GodPowerDecorator getActualDecorator() {
        return actualDecorator;
    }

    public void setActualDecorator(GodPowerDecorator actualDecorator) {
        this.actualDecorator = actualDecorator;
    }

    public Worker getChosenWorker() {
        return chosenWorker;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

}
