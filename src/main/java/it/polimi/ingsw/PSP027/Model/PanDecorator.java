package it.polimi.ingsw.PSP027.Model;

public class PanDecorator extends GodPowerDecorator {

    /**
     * Constructor
     *
     * @param decoratedTurn
     */
    public PanDecorator(ConcreteTurn decoratedTurn) {
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