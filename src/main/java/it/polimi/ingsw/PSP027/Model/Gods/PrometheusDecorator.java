package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Controller.MovePhase;

/**
 * @author danielecarta
 */

public class PrometheusDecorator extends GodPowerDecorator {

    public boolean powerUsed = false;

    public PrometheusDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * first call changes the candidate cells list like the actual phase is a buildPhase, giving a list of candidate cells
     * which i can build onto
     * second call changes the candidate cells list in a standard movePhase candidate moves list
     */

    @Override
    public void evalCandidateCells() {
        if(!powerUsed) {
            this.getCandidateCells().clear();
            Cell CurrentUpdatedWorkerPosition = this.getWorker().getWorkerPosition();
            for (Cell candidateCell : this.getGameBoard().getNeighbouringCells(CurrentUpdatedWorkerPosition)) {
                if (!this.getCandidateCells().contains(candidateCell)) {
                    if ((!candidateCell.isOccupiedByWorker()) && (!candidateCell.checkDome())) {
                        this.getCandidateCells().add(candidateCell);
                    }
                }
            }
        }
        if(powerUsed){
            this.getCandidateCells().clear();
            Cell startingCell = this.getDecoratedPhase().getWorker().getWorkerPosition();
            for(Cell candidateCell : this.getGameBoard().getNeighbouringCells(startingCell)){
                if(candidateCell.getLevel() <= startingCell.getLevel() && (!candidateCell.isOccupiedByWorker()) && (!candidateCell.checkDome())){
                    this.getCandidateCells().add(candidateCell);
                }
            }
        }
    }

    /**
     * first call performs a build action
     * second call performs a move action
     * @param chosenCell the cell im performing the action
     */

    @Override
    public void performActionOnCell(Cell chosenCell) {
//        if(powerUsed){
//            MovePhase movePhase = (MovePhase) this.getDecoratedPhase();     //using movePhase to get startChosenWorkerLvl
//            this.getWorker().changePosition(chosenCell);
//            if(movePhase.getStartChosenWorkerLvl()==2 && this.getWorker().getWorkerPosition().getLevel()==3){     //check if the win conditions are verified
//                this.getWorker().getWorkerOwner().setHasWon(true);
//            }
//            this.getDecoratedPhase().setDone(true);
//        }
//        if (!powerUsed) {
//            if (chosenCell.getLevel() < 3) {
//                chosenCell.addLevel();
//            } else {
//                chosenCell.addDome();
//            }
//        }
//        this.setPowerUsed(true);
//        this.changeCandidateCells();
    }

    public void setPowerUsed(boolean powerUsed) {
        this.powerUsed = powerUsed;
    }
}