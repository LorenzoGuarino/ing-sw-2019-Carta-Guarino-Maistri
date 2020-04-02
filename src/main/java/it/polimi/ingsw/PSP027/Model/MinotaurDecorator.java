package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 * @author Elisa Maistri
 */

public class MinotaurDecorator extends GodPowerDecorator {

    boolean powerToggled;

    /**
     * super Constructor
     *
     * @param decoratedTurn the turn it is going to decorate
     */

    public MinotaurDecorator(ConcreteTurn decoratedTurn) {
        super(decoratedTurn);
    }

    /**
     * When called by a turn it runs through that turns' movePhases and change their moveConditions according to the changeMoveConditions method below
     */

    @Override
    public void applyPower() {
        //@TODO rendere eseguibile solo se si è istanziata la MovePhase
        powerToggled=true;
        MovePhase modifiedPhase = (MovePhase) this.getDecoratedTurn().getPhaseList().get(1);//@TODO indice della fase che voglio modificare
        modifiedPhase.setActualDecorator(this); //allows the phase to know which kind of decorator is applied onto it
        modifiedPhase.changeCandidateMovesAccordingToDecorator();
    }

    /**
     * This method is called in a movePhase in order to update the board according to the decorator,in case it modifies the board itself
     * @param chosenWorker the worker that's moved
     * @param gameBoard the board it is moving on
     * @param chosenCell the cell it is stepping onto
     */
    @Override
    public void updateBoard(Worker chosenWorker, Board gameBoard, Cell chosenCell) {
        if (chosenCell.isOccupiedByOpponentWorker(chosenWorker.getWorkerOwner())) {
            Worker opponentWorker=chosenWorker.getWorkerPosition().getOccupyingWorker();
            opponentWorker.setPosition(gameBoard.getNextCellAlongThePath(chosenWorker.getWorkerPosition(),chosenCell));
            opponentWorker.getWorkerPosition().setOccupiedByWorker(true);
        }
        chosenWorker.getWorkerPosition().setOccupiedByWorker(false);
        chosenWorker.setPosition(chosenCell);
        chosenCell.setOccupiedByWorker(true);
    }

    /**
     * Method called by a decorated turn's movePhase, it makes it possible to move in an enemy occupied cell
     * if the next space is not occupied
     * @param chosenWorker the worker that's moving
     * @param board the board it is moving on
     * @return a list of cells which you can move onto
     */

    @Override
    public List<Cell> changeCandidateMoves(Worker chosenWorker, Board board) {
        Cell startingCell = chosenWorker.getWorkerPosition();
        List<Cell> candidateMoves = new ArrayList<>();

        for (Cell candidateCell : board.getNeighbouringCells(startingCell)) {
            if ((candidateCell.getLevel() <= startingCell.getLevel() + 1) &&
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

