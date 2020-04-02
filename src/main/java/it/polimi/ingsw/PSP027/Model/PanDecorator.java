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


       /*
        Da mettere nel PanDecorator
        public void checkPanWinConditions(Worker worker, int startLevel){    //this are the win conditions that Pan require
            if((startLevel-worker.getWorkerPosition().getLevel())>=2){
                worker.setOwnerHasWin = 1;
            }else{
                    //non fare nulla
            }
        }

        */
    }


}