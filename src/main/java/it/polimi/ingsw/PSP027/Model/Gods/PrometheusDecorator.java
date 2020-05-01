package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Controller.MovePhase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 */

public class PrometheusDecorator extends GodPowerDecorator {

    public boolean powerUsed = false;

    public PrometheusDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * first call changes the candidate cells list like the actual phase is a buildPhase, giving a list of candidate cells
     * which i can build onto
     * second call changes the candidate cells list in a standard movePhase candidate moves list
     */

    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();

        // Prometheus override only move phase made after a first optional build
        if(IsAMovePhase() && (getWorker().getBuildCounter() > 0)) {

            // Prometheus force to move at the same level if built before moving

            Cell startingCell = this.getWorker().getWorkerPosition();

            for(int i =0; i<getCandidateCells().size(); i++){
                Cell candidateCell = getCandidateCells().get(i);
                if(candidateCell.getLevel() != startingCell.getLevel()){
                    System.out.println("PROMETHEUS: evalCandidateCells discarding cell " + candidateCell.getCellIndex());
                    this.getCandidateCells().remove(i);
                }
            }

//            for (Cell candidateCell : this.getCandidateCells()) {
//
//                if (candidateCell.getLevel() != startingCell.getLevel()) {
//                    System.out.println("PROMETHEUS: evalCandidateCells discarding cell " + candidateCell.getCellIndex());
//                    this.getCandidateCells().remove(candidateCell);
//                }
//            }
        }
    }

    /**
     * first call performs a build action
     * second call performs a move action
     * @param chosenCell the cell im performing the action
     */

    @Override
    public void performActionOnCell(Cell chosenCell) {
        super.performActionOnCell(chosenCell);
    }
}