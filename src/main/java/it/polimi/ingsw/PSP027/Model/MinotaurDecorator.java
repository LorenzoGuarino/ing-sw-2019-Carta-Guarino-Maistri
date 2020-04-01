package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 * @author Elisa Maistri
 */

public class MinotaurDecorator extends GodPowerDecorator {

    /**
     * super Constructor
     *
     * @param decoratedTurn
     */

    public MinotaurDecorator(ConcreteTurn decoratedTurn) {
        super(decoratedTurn);
    }

    /**
     * When called by a turn it runs through that turns' movePhases and change their moveConditions according to the changeMoveConditions method below
     */

    @Override
    public void applyPower() {
        System.out.println("applyPowerMinotaur called");
        MovePhase modifiedPhase = (MovePhase) this.getDecoratedTurn().getPhaseList().get(1);//@TODO indice della fase che voglio modificare
        modifiedPhase.changeCandidateMovesAccordingToDecorator();
    }

    /**
     * Method called by a decorated turn's movePhase, it makes it possible to move in an enemy occupied cell
     *
     * @param chosenWorker
     * @return a list of cells which you can move onto
     */

    @Override
    public List<Cell> changeMoveConditions(Worker chosenWorker, Board board) {
        Cell startingCell = chosenWorker.getWorkerPosition();
        List<Cell> candidateMoves = new ArrayList<>();

        for (Cell candidateCell : board.getNeighbouringCells(startingCell)) {    //for each candidate cell in neighbouringCells if
            if ((candidateCell.getLevel() <= startingCell.getLevel() + 1) &&    //the lv i want to get to is higher less than one
                (!candidateCell.checkDome())) {
                if (!candidateCell.isOccupiedByWorker())
                    candidateMoves.add(candidateCell);
                else {

                    if (candidateCell.isOccupiedByOpponentWorker(chosenWorker.getWorkerOwner())) {

                        Cell potentialTargetCellForOpponentWorker = board.getNextCellAlongThePath(startingCell, candidateCell);

                        if(potentialTargetCellForOpponentWorker != null)
                        {
                            if(!potentialTargetCellForOpponentWorker.checkDome() && !potentialTargetCellForOpponentWorker.isOccupiedByWorker())
                                candidateMoves.add(candidateCell);
                        }

                    }
                }
            }
        }

        return candidateMoves;
    }
}

