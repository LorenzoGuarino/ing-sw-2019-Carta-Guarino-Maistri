package it.polimi.ingsw.PSP027.Model;

/**
 * @author danielecarta
 */
public class ArtemideDecorator extends GodPowerDecorator {

    boolean powerToggled = false;

    public ArtemideDecorator(ConcreteTurn decoratedTurn) {
        super(decoratedTurn);
    }

    /**
     * That creates another MovePhase copying the already existing and eventually decorated one
     */
    @Override
    public void applyPower() {
        if (!powerToggled) {
            powerToggled=true;
            MovePhase addedMovePhase = new MovePhase(this.getDecoratedTurn().getChosenWorker(),this.getDecoratedTurn().getSantoriniMatch().getGameBoard());
            this.getDecoratedTurn().getPhaseList().add(addedMovePhase);
        }
    }
}
