package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lorenzo Guarino
 * */
public class ApolloDecorator extends GodPowerDecorator{

    boolean powerToggled=false;
    /**
     * Constructor
     *@param decoratedTurn
     */

    public ApolloDecorator(ConcreteTurn decoratedTurn) {
        super(decoratedTurn);
    }

    /**
     * When called by a turn it runs through that turns' movePhases and change their moveConditions according to the changeMoveConditions method below
     */

    @Override
    public void applyPower() {
        if(this.getDecoratedTurn().getPhaseList().get(1)!=null)//if the MovePhase exists
        {
            powerToggled = true;
            MovePhase modifiedPhase = (MovePhase) this.getDecoratedTurn().getPhaseList().get(1);
            modifiedPhase.setActualDecorator(this); //allows the phase to know which kind of decorator is applied onto it
            modifiedPhase.changeCandidateMovesAccordingToDecorator();
        }
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
            Worker opponentWorker=chosenCell.getOccupyingWorker();
            opponentWorker.setPosition(chosenWorker.getWorkerPosition());
        }
        chosenWorker.setPosition(chosenCell);
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
                if (!candidateCell.isOccupiedByWorker() || candidateCell.isOccupiedByOpponentWorker(chosenWorker.getWorkerOwner())) {
                    candidateMoves.add(candidateCell);
                }
            }
        }
        return candidateMoves;
    }
}
