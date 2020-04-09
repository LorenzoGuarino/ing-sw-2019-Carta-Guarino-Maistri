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

    @Override
    public void updateBoard(Cell chosenCell) {

    }


    /*public PanDecorator(ConcreteTurn decoratedTurn) {
        super(decoratedTurn);
    }

    @Override
    public void applyPower() {
            if(this.getDecoratedTurn().getPhaseList().get(1)!=null){
                MovePhase modifiedPhase = (MovePhase) this.getDecoratedTurn().getPhaseList().get(1);
                modifiedPhase.setActualDecorator(this);
            }
    }

    /*@Override
    public void checkWinCondition(){

    }*/

}