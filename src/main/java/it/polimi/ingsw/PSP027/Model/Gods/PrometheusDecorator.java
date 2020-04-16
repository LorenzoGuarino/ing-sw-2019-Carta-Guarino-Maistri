package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.ConcretePhase;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.MovePhase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 */

public class PrometheusDecorator extends GodPowerDecorator{

    public boolean powerUsed = false;

    public PrometheusDecorator(ConcretePhase decoratedPhase) {
        super(decoratedPhase);
    }

    /**
     * first call changes the candidate cells list like the actual phase is a buildPhase, giving a list of candidate cells
     * which i can build onto
     * second call changes the candidate cells list in a standard movePhase candidate moves list
     */

    @Override
    public void changeCandidateCells() {
        if(!powerUsed) {
            this.getCandidateCells().clear();
            Cell CurrentUpdatedWorkerPosition = this.getChosenWorker().getWorkerPosition();
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
            Cell startingCell = this.getDecoratedPhase().getChosenWorker().getWorkerPosition();
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
    public void updateBoard(Cell chosenCell) {
        if(powerUsed){
            MovePhase movePhase = (MovePhase) this.getDecoratedPhase();     //using movePhase to get startChosenWorkerLvl
            this.getChosenWorker().changePosition(chosenCell);
            if(movePhase.getStartChosenWorkerLvl()==2 && this.getChosenWorker().getWorkerPosition().getLevel()==3){     //check if the win conditions are verified
                this.getChosenWorker().getWorkerOwner().setHasWon(true);
            }
            this.getDecoratedPhase().setDone(true);
        }
        if (!powerUsed) {
            if (chosenCell.getLevel() < 3) {
                chosenCell.addLevel();
            } else {
                chosenCell.addDome();
            }
        }
        this.setPowerUsed(true);
        this.changeCandidateCells();
    }

    public void setPowerUsed(boolean powerUsed) {
        this.powerUsed = powerUsed;
    }
}