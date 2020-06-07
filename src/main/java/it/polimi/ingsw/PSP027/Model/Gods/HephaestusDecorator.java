package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;

public class HephaestusDecorator extends GodPowerDecorator {

    /**
     * Constructor : sets the phase the decorator is decorating and a boolean that if it is set as true tells
     * that the decorator acts when it is played by an opponent of the god card's owner
     * @param decoratedPhase phase the decorator is going to decorate
     * @param bActAsOpponentGod true if the god card will act only when it is being played as an opponent god card, otherwise it is false
     */
    public HephaestusDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method used to get a new set of candidate cells list for the build phase after the first build is performed,
     * it contains only the cell on which the worker has just built
     */
    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();

        if(IsABuildPhase()) {

            // Hephaestus overrides only (second) build phase
            if(this.getWorker().getBuildCounter() == 1) {

                // Hephaestus allow build only on the cell it built before
                this.getCandidateCells().clear();
                this.getCandidateCells().add(this.getWorker().getLastBuiltCell());
            }
        }
    }

    /**
     * This method performs the action described by the god's power on the cell chosen by the player
     * @param chosenCell the Cell on which the worker wants to build onto
     */
    @Override
    public void performActionOnCell(Cell chosenCell) {

        super.performActionOnCell(chosenCell);
    }
}