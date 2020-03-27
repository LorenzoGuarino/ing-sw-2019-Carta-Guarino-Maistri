package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 */

public class MovePhase extends Phase {
    private List<Cell> candidateMoves;
    private ConcreteTurn decoratedTurn;


    public MovePhase(Cell startingCell) {
        setCandidateMoves(startingCell);
    }

    public void setCandidateMoves(Cell startingCell){
        this.candidateMoves = new ArrayList<>();
        for(Cell candidateCell : startingCell.getNeighbouringCells()){
            if(     (candidateCell.getLevel()<=startingCell.getLevel()+1)&& //the lv i want to get to is higher less than one
                    (!candidateCell.isOccupiedByWorker())&&                 //it is not occupied by a worker
                    (!candidateCell.checkDome())){                          //it is not occupied by a dome
                candidateMoves.add(candidateCell);
            }
        }
        //then call actual decorator setCandidateMoves

    }

    public List<Cell> getCandidateMoves() {
        return candidateMoves;
    }

    public void changeCandidateMovesAccordingToDecorator(){
        this.decoratedTurn.phaseList.
    }
}
