package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.ConcretePhase;
import it.polimi.ingsw.PSP027.Controller.MovePhase;

/**
 * @author danielecarta
 */
public class ArtemisDecorator extends GodPowerDecorator {

    private boolean powerUsed = false;

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
            MovePhase movePhase = (MovePhase) this.getDecoratedPhase();     //using movePhase to get startChosenWorkerLvl
            this.getChosenWorker().changePosition(chosenCell);
            if(movePhase.getStartChosenWorkerLvl()==2 && this.getChosenWorker().getWorkerPosition().getLevel()==3){
                this.getChosenWorker().getWorkerOwner().setHasWon(true);
            }
        }
        if (!powerUsed) {
            MovePhase movePhase = (MovePhase) this.getDecoratedPhase();     //using movePhase to get startChosenWorkerLvl
            Cell startingCell=this.getChosenWorker().getWorkerPosition();
            this.getChosenWorker().changePosition(chosenCell);
            if(movePhase.getStartChosenWorkerLvl()==2 && this.getChosenWorker().getWorkerPosition().getLevel()==3){    //check if the win conditions are verified
                this.getChosenWorker().getWorkerOwner().setHasWon(true);
            }
            this.setPowerUsed(true);
            this.changeCandidateCells();
            this.getCandidateCells().remove(startingCell);
        }
    }

    public void setPowerUsed(boolean powerUsed) {
        this.powerUsed = powerUsed;
    }


}
