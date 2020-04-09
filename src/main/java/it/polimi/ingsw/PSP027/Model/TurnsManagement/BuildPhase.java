package it.polimi.ingsw.PSP027.Model.TurnsManagement;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Gods.GodPowerDecorator;
import it.polimi.ingsw.PSP027.Model.Game.Worker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Elisa Maistri
 * @author danielecarta
 */

public class BuildPhase extends ConcretePhase {

    /**
     * Constructor, builds a standard BuildPhase with a standard candidateBuildingCells list starting from the
     * chosenWorker moved in the MovePhase and the board of the current turn
     * @param chosenWorker worker that the player is playing the turn with
     */

    public BuildPhase(Worker chosenWorker, Board gameBoard) {
        this.setGameBoard(gameBoard);
        this.setChosenWorker(chosenWorker);
        this.setCandidateCells(new ArrayList<Cell>());
        changeCandidateCells();
    }

    /**
     * Updates the candidateBuildingCells in a standard way, that allows the chosen worker to build in any adjacent cell that
     * is not occupied by any worker and has not a dome
     */

    public void changeCandidateCells(){
        Cell CurrentUpdatedWorkerPosition = this.getChosenWorker().getWorkerPosition();
        for(Cell candidateCell : this.getGameBoard().getNeighbouringCells(CurrentUpdatedWorkerPosition)) {
            if((!candidateCell.isOccupiedByWorker()) && (!candidateCell.checkDome())) {
                this.getCandidateCells().add(candidateCell);
            }
        }
    }

    /**
     * Updates the board after a standard build has been performed onto the chosenCell
     * @param chosenCell cell chosen by the user on which the build will happen
     */

    @Override
    public void updateBoard(Cell chosenCell) {
        if(chosenCell.getLevel() < 3){
            chosenCell.addLevel();
        }
        else{
            chosenCell.addDome();
        }
    }
}