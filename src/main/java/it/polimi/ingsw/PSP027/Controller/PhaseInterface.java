package it.polimi.ingsw.PSP027.Controller;

import it.polimi.ingsw.PSP027.Model.Game.Cell;

public interface PhaseInterface {
    /**
     * Modifies the list of candidateCells utility for each kind of Phase
     */

    public void changeCandidateCells();

    /**
     * Given a chosen cell, it updates the board according to the action taken in the specific Phase
     * @param chosenCell
     */

    public void updateBoard(Cell chosenCell);
}
