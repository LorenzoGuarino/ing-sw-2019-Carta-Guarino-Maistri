package it.polimi.ingsw.PSP027.Model.Gods;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Model.Game.Cell;

public class HestiaDecorator extends GodPowerDecorator {

    /**
     * Constructor : sets the phase the decorator is decorating and a boolean that if it is set as true tells
     * that the decorator acts when it is played by an opponent of the god card's owner
     * @param decoratedPhase phase the decorator is going to decorate
     * @param bActAsOpponentGod true if the god card will act only when it is being played as an opponent god card, otherwise it is false
     */
    public HestiaDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method used to get a new set of candidate cells list for the build phase after the first build is performed,
     * it removes the perimeter cells
     */
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
     * This method performs the action described by the god's power on the cell chosen by the player
     * @param chosenCell the Cell on which the worker wants to build onto
     */
    @Override
    public void performActionOnCell(Cell chosenCell) {

        super.performActionOnCell(chosenCell);
    }
}