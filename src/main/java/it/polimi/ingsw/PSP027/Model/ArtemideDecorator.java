package it.polimi.ingsw.PSP027.Model;

public class ArtemideDecorator extends GodPowerDecorator {

    public ArtemideDecorator(ConcreteTurn decoratedTurn) {
        super(decoratedTurn);
    }

    @Override
    public void applyPower() {
        int index=1; //l indice a cui voglio metere la fase
        this.getDecoratedTurn().getPhaseList().add(index,new MovePhase());
    }
}
