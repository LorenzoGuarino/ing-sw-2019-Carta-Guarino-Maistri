package it.polimi.ingsw.PSP027.Model;

public class MinotaurDecorator extends GodPowerDecorator {

    public MinotaurDecorator(ConcreteTurn decoratedTurn) {
        super(decoratedTurn);
    }

    @Override
    public void applyPower() {
        MovePhase tempPhase = (MovePhase)this.getDecoratedTurn().getPhaseList().get(1);//indice della fase da modificare
        //tempPhase.setMovableOntoCells();
    }
}
