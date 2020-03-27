package it.polimi.ingsw.PSP027.Model;

import com.sun.tools.javac.util.List;

public class MinotaurDecorator extends GodPowerDecorator {

    public MinotaurDecorator(ConcreteTurn decoratedTurn) {
        super(decoratedTurn);
    }

    public void setCandidateMoves(){
        List<Cell> candidateMoves;

    }

    @Override
    public void applyPower() {
        MovePhase tempPhase = (MovePhase)this.getDecoratedTurn().getPhaseList().get(1);//indice della fase da modificare
        //tempPhase.setMovableOntoCells();
    }
}
