package it.polimi.ingsw.PSP027.Model.Gods;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Worker;

/**
 * @author Elisa Maistri
 */

public class PoseidonDecorator extends GodPowerDecorator {

    /**
     * Constructor : sets the phase the decorator is decorating and a boolean that if it is set as true tells
     * that the decorator acts when it is played by an opponent of the god card's owner
     * @param decoratedPhase phase the decorator is going to decorate
     * @param bActAsOpponentGod true if the god card will act only when it is being played as an opponent god card, otherwise it is false
     */
    public PoseidonDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method used to get a set of standard candidate build cells neighbouring the unmoved worker
     */
    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();

        // Poseidon can build up to 3 times with his unmoved worker

        if (IsAnEndPhase()) {
            System.out.println("Poseidon isAnEndPhase");
            getCandidateCells().clear();

            if(getPlayingPlayer().getPlayerWorkers().size()>1) {
                System.out.println("Poseidon workers>1");

                Worker worker = null;
                if (this.getWorker().getWorkerIndex() == 0) {
                    System.out.println("chosenWorker index=0 -> worker which does end=1");
                    worker = this.getWorker().getWorkerOwner().getPlayerWorkers().get(1);
                }
                else {
                    System.out.println("chosenWorker index=1 -> worker which does end=0");
                    worker = this.getWorker().getWorkerOwner().getPlayerWorkers().get(0);
                }
                System.out.println("worker= "+worker.getWorkerIndex()+" build counter= "+worker.getBuildCounter());
                if (worker.getBuildCounter() < 3) {
                    System.out.println("Poseidon worker build counter: "+worker.getBuildCounter());
                    Cell startingCell = worker.getWorkerPosition();

                    if (startingCell.getLevel() == 0) {
                        for (Cell candidateCell : getGameBoard().getNeighbouringCells(startingCell)) {
                            if((!candidateCell.isOccupiedByWorker()) && (!candidateCell.checkDome()))
                                getCandidateCells().add(candidateCell);
                        }
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
        if (IsAnEndPhase() && (chosenCell != null)) {
            Worker worker = null;
            if (this.getWorker().getWorkerIndex() == 0) {
                worker = this.getWorker().getWorkerOwner().getPlayerWorkers().get(1);
            }
            else {
                worker = this.getWorker().getWorkerOwner().getPlayerWorkers().get(0);
            }
            if(chosenCell.getLevel() < 3){
                chosenCell.addLevel();
            }
            else{
                chosenCell.addDome();
            }
            worker.setOldBuiltCell(chosenCell);
            worker.IncrementBuildCounter();
        }

        super.performActionOnCell(chosenCell);
    }
}