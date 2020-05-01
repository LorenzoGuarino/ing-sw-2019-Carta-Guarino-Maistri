package it.polimi.ingsw.PSP027.Model.Gods;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Model.Game.Cell;

public class ZeusDecorator extends GodPowerDecorator {

    public ZeusDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method used by Hephaestus to set the candidate cells on which its player can build on.
     * It's the standard method of checking if the neighbouring cell is not occupied by another worker
     * and has not already a dome
     */
    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();

        if(IsABuildPhase()) {

            // Zeus allow build even on the cell under worker, if not at third level
            if(this.getWorker().getWorkerPosition().getLevel()<3)
                this.getCandidateCells().add(this.getWorker().getWorkerPosition());
        }
    }

    /**
     * If the power hasn't been used yet, it updates the board with a standard building on a chosenCell,and then removes
     * the candidate cell from the candidate cells list, in order to let the player update the board again, but not onto
     * the same cell
     * @param chosenCell the Cell the worker wants to build onto
     */
    @Override
    public void performActionOnCell(Cell chosenCell) {

        if(IsABuildPhase()) {
            if (chosenCell.getLevel() < 3) {
                chosenCell.addLevel(true);
            } else {
                if (!chosenCell.isOccupiedByWorker())
                    chosenCell.addDome();
            }

            this.getWorker().setOldBuiltCell(chosenCell);
            this.getWorker().IncrementBuildCounter();
        }
        else {
            super.performActionOnCell(chosenCell);
        }

    }
}