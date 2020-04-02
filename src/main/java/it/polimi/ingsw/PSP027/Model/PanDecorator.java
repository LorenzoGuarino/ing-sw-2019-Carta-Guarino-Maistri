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


        public boolean checkMoveWinConditions(Worker worker, int startLevel){
            if(startLevel==2 && worker.getWorkerPosition().getLevel()==3){
                return true;
            }else{
                return false;
            }
        }



    }


}