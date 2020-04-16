package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.ConcretePhase;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.Turn;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.MovePhase;

public class PanDecorator extends GodPowerDecorator {

    public PanDecorator(ConcretePhase decoratedPhase) {
        super(decoratedPhase);
    }

    @Override
    public void changeCandidateCells() {

    }

    /**
     * Method that update the board and check at the same time if the Pan win conditions are verified
     * @param chosenCell cell where to move
     */
    @Override
    public void updateBoard(Cell chosenCell) {
        MovePhase movePhase = (MovePhase) this.getDecoratedPhase();     //using movePhase to get startChosenWorkerLvl
        this.getDecoratedPhase().getChosenWorker().changePosition(chosenCell);
        if(movePhase.getStartChosenWorkerLvl()==2 && this.getChosenWorker().getWorkerPosition().getLevel()==3){     //check standard win conditions
            this.getChosenWorker().getWorkerOwner().setHasWon(true);
        }else if(movePhase.getStartChosenWorkerLvl()-this.getChosenWorker().getWorkerPosition().getLevel()>=2){     //check Pan win conditions
            this.getChosenWorker().getWorkerOwner().setHasWon(true);
        }
    }
}