package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Worker;

public class AresDecorator extends GodPowerDecorator {

    /**
     * Constructor : sets the phase the decorator is decorating and a boolean that if it is set as true tells
     * that the decorator acts when it is played by an opponent of the god card's owner
     * @param decoratedPhase phase the decorator is going to decorate
     * @param bActAsOpponentGod true if the god card will act only when it is being played as an opponent god card, otherwise it is false
     */
    public AresDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method called by a decorated turn's OptEndPhase, it sets a list of candidate cells that for Ares are cells
     * that have blocks on them (not domes) and are unoccupied
     */

    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells

        super.evalCandidateCells();

        // Ares can remove a building block not occupied by a worker neighbouring his unmoved worker

        if (IsAnEndPhase()) {

            getCandidateCells().clear();

            if(getPlayingPlayer().getPlayerWorkers().size()>1) {

                Worker worker = null;
                if (getWorker().getWorkerIndex() == 0)
                    worker = getWorker().getWorkerOwner().getPlayerWorkers().get(1);
                else
                    worker = getWorker().getWorkerOwner().getPlayerWorkers().get(0);

                Cell startingCell = worker.getWorkerPosition();

                for (Cell candidateCell : getGameBoard().getNeighbouringCells(startingCell)) {

                    if (candidateCell.canALevelBeRemoved())
                        getCandidateCells().add(candidateCell);

                }
            }
        }
    }

    /**
     * This method performs the action described by the god's power on the cell chosen by the player
     * @param chosenCell the Cell on which the worker wants to remove a block
     */
    @Override
    public void performActionOnCell(Cell chosenCell) {

        if (IsAnEndPhase() && (chosenCell != null)) {
            chosenCell.removeLevel();
        }

        super.performActionOnCell(chosenCell);
    }
}