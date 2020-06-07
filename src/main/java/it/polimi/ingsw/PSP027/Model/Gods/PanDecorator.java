package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Controller.MovePhase;

public class PanDecorator extends GodPowerDecorator {

    /**
     * Constructor : sets the phase the decorator is decorating and a boolean that if it is set as true tells
     * that the decorator acts when it is played by an opponent of the god card's owner
     * @param decoratedPhase phase the decorator is going to decorate
     * @param bActAsOpponentGod true if the god card will act only when it is being played as an opponent god card, otherwise it is false
     */
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