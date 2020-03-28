package it.polimi.ingsw.PSP027.Model;

public class ArtemideDecorator extends GodPowerDecorator {

    public ArtemideDecorator(ConcreteTurn decoratedTurn) {
        super(decoratedTurn);
    }

    /**
     * That creates another MovePhase copying the already existing and eventually decorated one
     */
    @Override
    public void applyPower() {
        int index=2;
        MovePhase  a = new MovePhase((MovePhase)this.getPhaseList().get(1));
        this.getDecoratedTurn().getPhaseList().add(index,a);
    }
}
