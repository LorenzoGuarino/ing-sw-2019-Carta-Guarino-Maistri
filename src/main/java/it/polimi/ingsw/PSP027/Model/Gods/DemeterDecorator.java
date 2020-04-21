package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;

/**
 * @author danielecarta
 */

public class DemeterDecorator extends GodPowerDecorator {

    private boolean powerUsed = false;

    public DemeterDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * If the power hasn't yet been used, it gives a standard candidate cells list to build on
     */

    @Override
    public void evalCandidateCells() {

    }

    /**
     * If the power hasn't been used yet, it updates the board with a standard building on a chosenCell,and then removes
     * the candidate cell from the candidate cells list, in order to let the player update the board again, but not onto
     * the same cell
     * @param chosenCell the Cell the worker wants to build onto
     */
    @Override
    public void performActionOnCell(Cell chosenCell) {
        if (this.getDecoratedPhase().getCandidateCells().contains(chosenCell)) {
            if (!powerUsed) {
                if (chosenCell.getLevel() < 3) {
                    chosenCell.addLevel();
                } else {
                    chosenCell.addDome();
                }
                this.getDecoratedPhase().getCandidateCells().remove(chosenCell);
            }
            if (powerUsed) {
                if (chosenCell.getLevel() < 3) {
                    chosenCell.addLevel();
                } else {
                    chosenCell.addDome();
                }
            }
        }
    }
}
