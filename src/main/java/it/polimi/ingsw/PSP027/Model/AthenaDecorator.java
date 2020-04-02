package it.polimi.ingsw.PSP027.Model;

public class AthenaDecorator extends GodPowerDecorator {


    /**
     * Constructor
     *
     * @param decoratedTurn
     */
    public AthenaDecorator(ConcreteTurn decoratedTurn) {
        super(decoratedTurn);
    }



    @Override
    public void applyPower() {

        MovePhase movePhase= (MovePhase)this.getDecoratedTurn().getPhaseList().get(0);//movephase nella quale sono salito?
        //movePhase.getStartChosenWorkerLvl()<movePhase.getChosenWorker().getWorkerPosition().getLevel()
    }
}
