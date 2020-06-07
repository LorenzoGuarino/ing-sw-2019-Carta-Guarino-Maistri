package it.polimi.ingsw.PSP027.Model.Gods;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;

/**
 * @author danielecarta
 */

public class MedusaDecorator extends GodPowerDecorator {

    /**
     * Constructor : sets the phase the decorator is decorating and a boolean that if it is set as true tells
     * that the decorator acts when it is played by an opponent of the god card's owner
     * @param decoratedPhase phase the decorator is going to decorate
     * @param bActAsOpponentGod true if the god card will act only when it is being played as an opponent god card, otherwise it is false
     */
    public MedusaDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method called by a decorated turn's EndPhase, it adds cells to a list of candidate cells according to this god's power
     * it checks if the power can be applied, checking if medusa's workers have opponent's workers as neighbours and occupying lower levels.
     */
    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells

        super.evalCandidateCells();

        //if is an endPhase the evaluation of the candidate cells takes place for each of the medusa's workers
        //and then it automatically performs (if possible) the consequent actions

        if (IsAnEndPhase()) {
            Worker worker1 = null;
            Worker worker2 = null;
            if(this.getPlayingPlayer().getPlayerWorkers().get(0) != null) {
                worker1 = this.getPlayingPlayer().getPlayerWorkers().get(0);
            }
            if(this.getPlayingPlayer().getPlayerWorkers().get(1) != null) {
                worker2 = this.getPlayingPlayer().getPlayerWorkers().get(1);
            }
            getCandidateCells().clear();

            if (worker1 != null) {
                Cell startingCell1 = worker1.getWorkerPosition();
                for (Cell candidateCell : getGameBoard().getNeighbouringCells(startingCell1)) {
                    if (candidateCell.getLevel() < startingCell1.getLevel() && candidateCell.isOccupiedByOpponentWorker(this.getPlayingPlayer()))
                        getCandidateCells().add(candidateCell);
                }
            }
            if (worker2 != null) {
                Cell startingCell1=worker2.getWorkerPosition();
                for (Cell candidateCell : getGameBoard().getNeighbouringCells(startingCell1)) {
                    if (candidateCell.getLevel() < startingCell1.getLevel() && candidateCell.isOccupiedByOpponentWorker(this.getPlayingPlayer()))
                        getCandidateCells().add(candidateCell);
                }
            }
            for(Cell candidateCell:this.getDecoratedPhase().getCandidateCells()){
                Player oppPlayer = candidateCell.getOccupyingWorker().getWorkerOwner();
                candidateCell.getOccupyingWorker().removeWorker();
                candidateCell.addLevel();
                if(oppPlayer.getPlayerWorkers().size() == 0){
                    oppPlayer.setHasLost(true);
                }
            }
        }
    }

    /**
     * This method is automatically called, if medusa's power can be applied and so if a list of candidate cells was evaluated, it automatically
     * removes the workers occupying those candidate cells and replaces them with blocks.
     */
    @Override
    public boolean startPhase() {
        if(!IsAnEndPhase()) {
            return super.startPhase();
        }
        else
            evalCandidateCells();
            return false;
    }
}
