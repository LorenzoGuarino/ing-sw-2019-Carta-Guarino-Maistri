package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 */

public class MinotaurDecorator extends GodPowerDecorator {
    /**
     * super Constructor
     * @param decoratedTurn
     */
    public MinotaurDecorator(ConcreteTurn decoratedTurn) {
        super(decoratedTurn);
    }

    @Override
    public void applyPower() {
        //useless for this decorator
    }

    /**
     * Method called by a decorated turn's movePhase, it makes it possible to move in an enemy occupied cell
     * @param startingCell
     * @return a list of cells which you can move onto
     */
    @Override
    public List<Cell> changeMoveConditions(Cell startingCell) {
        List<Cell> candidateMoves = new ArrayList<>();
        for (Cell candidateCell : startingCell.getNeighbouringCells()) {    //for each candidate cell in neighbouringCells if
            if ((candidateCell.getLevel() <= startingCell.getLevel() + 1) &&//the lv i want to get to is higher less than one
                    (!candidateCell.isOccupiedByEnemyWorker()) &&           //it is occupied by ENEMY worker
                    (!candidateCell.checkDome())) {                         //it is not occupied by a dome
                candidateMoves.add(candidateCell);                          //then add the cell to candidateMoves
            }
        }
    }
}

