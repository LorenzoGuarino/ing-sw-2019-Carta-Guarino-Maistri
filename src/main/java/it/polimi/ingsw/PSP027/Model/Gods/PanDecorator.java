package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Controller.MovePhase;

public class PanDecorator extends GodPowerDecorator {

    public PanDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    @Override
    public void evalCandidateCells() {

    }

    /**
     * Method that update the board and check at the same time if the Pan win conditions are verified
     * @param chosenCell cell where to move
     */
    @Override
    public void performActionOnCell(Cell chosenCell) {
//        MovePhase movePhase = (MovePhase) this.getDecoratedPhase();     //using movePhase to get startChosenWorkerLvl
//        this.getDecoratedPhase().getWorker().changePosition(chosenCell);
//        if(movePhase.getStartChosenWorkerLvl()==2 && this.getWorker().getWorkerPosition().getLevel()==3){     //check standard win conditions
//            this.getWorker().getWorkerOwner().setHasWon(true);
//        }else if(movePhase.getStartChosenWorkerLvl()-this.getWorker().getWorkerPosition().getLevel()>=2){     //check Pan win conditions
//            this.getWorker().getWorkerOwner().setHasWon(true);
//        }
    }
}