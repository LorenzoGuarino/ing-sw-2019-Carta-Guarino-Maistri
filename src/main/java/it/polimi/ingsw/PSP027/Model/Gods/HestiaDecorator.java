package it.polimi.ingsw.PSP027.Model.Gods;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Model.Game.Cell;

/**
 * @author Elisa Maistri
 */

public class HestiaDecorator extends GodPowerDecorator {

    public HestiaDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();

        if(IsABuildPhase()) {
            // Hestia overrides only (second) build phase
            if(this.getWorker().getBuildCounter() == 1) {

                // Hestia excludes the cells on the perimeter from build
                Cell startingCell = this.getWorker().getWorkerPosition();

                for (Cell candidateCell : this.getGameBoard().getNeighbouringCells(startingCell)) {

                    if(candidateCell.isAPerimeterCell()) {
                        System.out.println("HESTIA: evalCandidateCells discarding cell " + candidateCell.getCellIndex() + " (l=" +
                                candidateCell.getLevel() + ", w=" + candidateCell.isOccupiedByWorker() + ", d=" + candidateCell.checkDome());
                        this.getCandidateCells().remove(candidateCell);
                    }
                }
            }
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

        super.performActionOnCell(chosenCell);
    }
}