package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 */

public class MovePhase extends Phase {
    private List<Cell> candidateMoves;
    private GodPowerDecorator actualDecorator;
    private Cell startingCell;

    /**
     * Constructor, builds a standard MovePhase with a standard candidateMoves list
     * @param startingCell
     */

    public MovePhase(Cell startingCell) {
        this.startingCell = startingCell;
        this.candidateMoves = new ArrayList<>();
        for(Cell candidateCell : startingCell.getNeighbouringCells()){      //for each candidate cell in neighbouringCells if
            if(     (candidateCell.getLevel()<=startingCell.getLevel()+1)&& //the lv i want to get to is higher less than one
                    (!candidateCell.isOccupiedByWorker())&&                 //it is not occupied by a worker
                    (!candidateCell.checkDome())){                          //it is not occupied by a dome
                candidateMoves.add(candidateCell);                          //then add the cell to candidateMoves
            }
        }
        changeCandidateMovesAccordingToDecorator(); //mandatory if possible(?)
    }

    /**
     * Method that modifies the actual candidateMoves list according to the GodPowerDecorator decorating the movePhase's turn
     * If the decorator doesn't modify moveConditions returns without doing anything
     */

    public void changeCandidateMovesAccordingToDecorator(){
        List<Cell> candidateDecoratedMoves = this.actualDecorator.changeMoveConditions(startingCell);
        if(candidateDecoratedMoves==null)return;
        else this.candidateMoves=candidateDecoratedMoves;
    }
}
