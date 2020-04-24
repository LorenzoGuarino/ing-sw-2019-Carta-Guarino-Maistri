package it.polimi.ingsw.PSP027.Model.Gods;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Worker;

/**
 * @author Elisa Maistri
 */

public class PoseidonDecorator extends GodPowerDecorator {

    public PoseidonDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();

        // Poseidon can build up to 3 times with his unmoved worker

        if (IsAnEndPhase()) {

            getCandidateCells().clear();

            if(getPlayingPlayer().getPlayerWorkers().size()>1) {

                Worker worker = null;
                if (getWorker().getWorkerIndex() == 0)
                    worker = getWorker().getWorkerOwner().getPlayerWorkers().get(1);
                else
                    worker = getWorker().getWorkerOwner().getPlayerWorkers().get(0);

                if (worker.getBuildCounter() < 3) {
                    Cell startingCell = worker.getWorkerPosition();

                    if (startingCell.getLevel() == 0) {
                        for (Cell candidateCell : getGameBoard().getNeighbouringCells(startingCell)) {

                            if (candidateCell.canALevelBeAdded())
                                getCandidateCells().add(candidateCell);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void performActionOnCell(Cell chosenCell) {

        if (IsAnEndPhase() && (chosenCell != null)) {
            if(chosenCell.getLevel() < 3){
                chosenCell.addLevel();
            }
            else{
                chosenCell.addDome();
            }
            this.getWorker().setOldBuiltCell(chosenCell);
            this.getWorker().IncrementBuildCounter();
        }

        super.performActionOnCell(chosenCell);
    }
}