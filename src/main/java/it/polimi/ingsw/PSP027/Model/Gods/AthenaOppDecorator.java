package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 */

//@TODO transfer this in Athena Decorator as it is not necessary

public class AthenaOppDecorator extends GodPowerDecorator {

    public AthenaOppDecorator(Phase decoratedPhase, boolean bActAsOpponentGod)
    {
        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * it removes from a movePhase candidate cells list any cell that is higher than the starting level's chosenCell
     */
    @Override
    public void evalCandidateCells() {
        Cell startingCell = this.getDecoratedPhase().getWorker().getWorkerPosition();
        List<Cell> candidateRemovesList = new ArrayList<>();
        for(Cell candidateCell : this.getDecoratedPhase().getCandidateCells()){
            if(candidateCell.getLevel() > startingCell.getLevel()){
                candidateRemovesList.add(candidateCell);
            }
        }
        this.getDecoratedPhase().getCandidateCells().removeAll(candidateRemovesList);
    }

    /**
     * standard move on the chosenCell
     * @param chosenCell the chosenCell i'm moving onto
     */
    @Override
    public void performActionOnCell(Cell chosenCell) { this.getWorker().changePosition(chosenCell); }

}
