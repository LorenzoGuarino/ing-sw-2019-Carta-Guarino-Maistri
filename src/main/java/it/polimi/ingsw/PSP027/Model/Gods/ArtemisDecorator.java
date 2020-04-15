package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.ConcretePhase;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.Turn;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.MovePhase;

/**
 * @author danielecarta
 */
public class ArtemisDecorator extends GodPowerDecorator {

    private boolean powerUsed=false;

    public ArtemisDecorator(ConcretePhase decoratedPhase) {
        super(decoratedPhase);
    }

    /**
     * used to get a new candidate cells list after the first move is performed
     */
    @Override
    public void changeCandidateCells() {
        if(powerUsed){
            this.getCandidateCells().clear();
            Cell startingCell = this.getDecoratedPhase().getChosenWorker().getWorkerPosition();
            for(Cell candidateCell : this.getGameBoard().getNeighbouringCells(startingCell)){
                if((candidateCell.getLevel() <= startingCell.getLevel() + 1) && (!candidateCell.isOccupiedByWorker()) && (!candidateCell.checkDome())){
                    this.getCandidateCells().add(candidateCell);
                }
            }
        }
    }

    /**
     * allow the player to move again but not on the same cell he s moved from the first time
     * @param chosenCell the cell im performing the action
     */

    @Override
    public void updateBoard(Cell chosenCell) {
        if(powerUsed){
            this.getChosenWorker().changePosition(chosenCell);
        }
        if (!powerUsed) {
            Cell startingCell=this.getChosenWorker().getWorkerPosition();
            this.getChosenWorker().changePosition(chosenCell);
            this.setPowerUsed(true);
            this.changeCandidateCells();
            this.getCandidateCells().remove(startingCell);
        }
    }

    public void setPowerUsed(boolean powerUsed) {
        this.powerUsed = powerUsed;
    }
}
