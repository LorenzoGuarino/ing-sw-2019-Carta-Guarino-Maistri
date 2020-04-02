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


       /* Da mettere in MovePhase dopo aver mosso il worker, prima che finisca la phase
       public void checkMoveWinConditions(Worker worker, int startLevel){ //this are the standard win conditions to win a game
            if(startLevel==2 && worker.getWorkerPosition().getLevel()==3){
                worker.setOwnerHasWin = 1;
            }else{
                    //non fare nulla
            }
        }
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