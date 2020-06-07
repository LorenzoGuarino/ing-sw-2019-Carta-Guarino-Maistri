package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;

/**
 * @author Elisa Maistri
 */

public class AtlasDecorator extends GodPowerDecorator {

    /**
     * Constructor : sets the phase the decorator is decorating and a boolean that if it is set as true tells
     * that the decorator acts when it is played by an opponent of the god card's owner
     * @param decoratedPhase phase the decorator is going to decorate
     * @param bActAsOpponentGod true if the god card will act only when it is being played as an opponent god card, otherwise it is false
     */
    public AtlasDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);

    }

    /**
     * Method used by Atlas to set the candidate cells on which its player can build on.
     * It's the standard method of checking if the neighbouring cell is not occupied by another worker
     * and has not already a dome
     */

    @Override
    public void evalCandidateCells() {
        // call nested phase evalCandidateCells
        super.evalCandidateCells();
    }

    /**
     * This method performs the action described by the god's power on the cell chosen by the player
     * Atlas builds a dome if the HasToBuildADomeOnNextBuildPhase is true, otherwise builds in the standard way
     * (level/block depending on the level already present on the cell)
     * @param chosenCell the Cell the worker wants to build on
     */

    @Override
    public void performActionOnCell(Cell chosenCell) {

        if(IsABuildPhase()) {
            if (getWorker().HasToBuildADomeOnNextBuildPhase()) {
                chosenCell.addDome();
            } else {
                super.performActionOnCell(chosenCell);
            }
        }
        else
            super.performActionOnCell(chosenCell);
    }
}
