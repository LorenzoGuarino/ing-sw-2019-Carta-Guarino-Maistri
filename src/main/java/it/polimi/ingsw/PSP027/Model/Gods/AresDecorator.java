package it.polimi.ingsw.PSP027.Model.Gods;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Worker;

public class AresDecorator extends GodPowerDecorator {

    public AresDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method called by a decorated turn's movePhase, it makes it possible to move in an enemy occupied cell swapping places with that enemy worker
     * @return a list of cells which you can move onto
     */

    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();

        // Ares can remove a building block not occupied by a worker neighbouring his unmoved worker
        if (IsAnEndPhase()) {

            getCandidateCells().clear();

            //@TODO check that the player has 2 workers

            Worker worker = null;
            if (getWorker().getWorkerIndex() == 0)
                worker = getWorker().getWorkerOwner().getPlayerWorkers().get(1);
            else
                worker = getWorker().getWorkerOwner().getPlayerWorkers().get(0);

            Cell startingCell = worker.getWorkerPosition();

            for (Cell candidateCell : this.getGameBoard().getNeighbouringCells(startingCell)) {

                if(candidateCell.canALevelBeRemoved())
                    getCandidateCells().add(candidateCell);
            }
        }
    }

    @Override
    public void performActionOnCell(Cell chosenCell) {

        if (IsAnEndPhase() && (chosenCell != null)) {
            chosenCell.removeLevel();
        }

        super.performActionOnCell(chosenCell);
    }
}