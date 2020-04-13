package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.ConcretePhase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 */

public class AthenaOppDecorator extends GodPowerDecorator{

    public AthenaOppDecorator(ConcretePhase decoratedPhase) {
        super(decoratedPhase);
    }

    /**
     * it removes from a movePhase candidate cells list any cell that is higher than the starting level's chosenCell
     */
    @Override
    public void changeCandidateCells() {
        Cell startingCell = this.getDecoratedPhase().getChosenWorker().getWorkerPosition();
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
    public void updateBoard(Cell chosenCell) { this.getChosenWorker().changePosition(chosenCell); }
}
