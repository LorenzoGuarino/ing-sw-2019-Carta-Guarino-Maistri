package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 */

public class AthenaOppDecorator extends GodPowerDecorator{

    /**
     * Constructor
     *
     * @param decoratedTurn
     */
    public AthenaOppDecorator(ConcreteTurn decoratedTurn) {
        super(decoratedTurn);
    }

    /**
     * given a chosenWorker's candidateMoves List it removes the moves that make the worker go up one level
     * @param chosenWorker
     * @param candidateMoves
     * @return
     */

    public List<Cell> changeCandidateMoves(Worker chosenWorker, List<Cell> candidateMoves) {
        Cell startingCell = chosenWorker.getWorkerPosition();
        candidateMoves = new ArrayList<>();
        for(Cell candidateCell : candidateMoves){     //for each candidate cell in neighbouringCells if
            if(candidateCell.getLevel()>startingCell.getLevel()){
                candidateMoves.remove(candidateCell);
            }
        }
        return candidateMoves;
    }

    @Override
    public void applyPower() {

    }
}
