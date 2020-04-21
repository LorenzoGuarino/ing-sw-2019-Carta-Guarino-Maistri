package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;

public class HephaestusDecorator extends GodPowerDecorator {

    public HephaestusDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method used by Hephaestus to set the candidate cells on which its player can build on.
     * It's the standard method of checking if the neighbouring cell is not occupied by another worker
     * and has not already a dome
     */
    @Override
    public void evalCandidateCells() {
    }

    /**
     * Method used by Hephaestus to add a second block on his first if it's not a dome, if the player checked yes to
     * using its God Card, otherwise builds a block/dome with the standard method of adding a level if there's a 0, 1
     * or 2 level and adding a dome if there's a level 3 on the cell.
     *
     * @param chosenCell cell chosen by the player on which it wants to build
     */
    @Override
    public void performActionOnCell(Cell chosenCell) {
        if (chosenCell.getLevel() < 2) {
            chosenCell.addLevel();
            chosenCell.addLevel();
        } else if (chosenCell.getLevel() == 2) {
            chosenCell.addLevel();
        } else {
            chosenCell.addDome();
        }
    }
}