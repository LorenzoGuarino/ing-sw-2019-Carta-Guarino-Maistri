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

    @Override
    public void changeCandidateCells() {
        Cell startingCell = this.getDecoratedPhase().getChosenWorker().getWorkerPosition();
        List<Cell> candidateMoves = new ArrayList<>();
        for(Cell candidateCell : candidateMoves){
            if(candidateCell.getLevel()>startingCell.getLevel()){
                candidateMoves.remove(candidateCell);
            }
        }
    }

    @Override
    public void updateBoard(Cell chosenCell) {

    }
}
