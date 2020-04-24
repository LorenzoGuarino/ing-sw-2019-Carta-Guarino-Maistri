package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Controller.MovePhase;

public class PanDecorator extends GodPowerDecorator {

    public PanDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method that tells if the player who is playing the phase has won
     * @return true if the player has won, otherwise false
     */

    @Override
    public boolean PlayerHasWon(){

        if(!super.PlayerHasWon())
        {
            int oldlevel = getWorker().getWorkerPrevPosition().getLevel();
            int newlevel = getWorker().getWorkerPosition().getLevel();

            if((oldlevel-newlevel)>=2){
                getWorker().getWorkerOwner().setHasWon(true);
                return true;
            }

            return false;
        }
        else
            return true;
    }
}