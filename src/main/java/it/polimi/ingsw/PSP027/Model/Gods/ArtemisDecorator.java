package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Controller.MovePhase;

/**
 * @author danielecarta
 */
public class ArtemisDecorator extends GodPowerDecorator {

    private boolean powerUsed = false;

    public ArtemisDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * used to get a new candidate cells list after the first move is performed
     */
    @Override
    public void evalCandidateCells() {
        if(powerUsed){
            this.getCandidateCells().clear();
            Cell startingCell = this.getDecoratedPhase().getWorker().getWorkerPosition();
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
    public void performActionOnCell(Cell chosenCell) {
//        if(powerUsed){
//            MovePhase movePhase = (MovePhase) this.getDecoratedPhase();     //using movePhase to get startChosenWorkerLvl
//            this.getWorker().changePosition(chosenCell);
//            if(movePhase.getStartChosenWorkerLvl()==2 && this.getWorker().getWorkerPosition().getLevel()==3){
//                this.getWorker().getWorkerOwner().setHasWon(true);
//            }
//        }
//        if (!powerUsed) {
//            MovePhase movePhase = (MovePhase) this.getDecoratedPhase();     //using movePhase to get startChosenWorkerLvl
//            Cell startingCell=this.getWorker().getWorkerPosition();
//            this.getWorker().changePosition(chosenCell);
//            if(movePhase.getStartChosenWorkerLvl()==2 && this.getWorker().getWorkerPosition().getLevel()==3){    //check if the win conditions are verified
//                this.getWorker().getWorkerOwner().setHasWon(true);
//            }
//            this.setPowerUsed(true);
//            this.changeCandidateCells();
//            this.getCandidateCells().remove(startingCell);
//        }
    }

    public void setPowerUsed(boolean powerUsed) {
        this.powerUsed = powerUsed;
    }


}
