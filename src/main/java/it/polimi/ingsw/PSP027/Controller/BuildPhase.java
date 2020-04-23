package it.polimi.ingsw.PSP027.Controller;

import it.polimi.ingsw.PSP027.Model.Game.Cell;

/**
 * @author Elisa Maistri
 * @author danielecarta
 */

public class BuildPhase extends Phase {

    /**
     * Constructor: when creating the build phase it sets the phase's type as phase type "Build"
     */

    public BuildPhase() {
        SetType(PhaseType.Build);
    }

    /**
     * Updates the candidateBuildingCells in a standard way, that allows the chosen worker to build in any adjacent cell that
     * is not occupied by any worker and has not a dome
     */

    @Override
    public void evalCandidateCells(){
        Cell CurrentUpdatedWorkerPosition = this.getWorker().getWorkerPosition();
        for(Cell candidateCell : this.getGameBoard().getNeighbouringCells(CurrentUpdatedWorkerPosition)) {
            if((!candidateCell.isOccupiedByWorker()) && (!candidateCell.checkDome())) {
                System.out.println("BUILD: evalCandidateCells inserting cell " + candidateCell.getCellIndex());
                this.getCandidateCells().add(candidateCell);                                    //then add the cell to candidateCells
            }
            else
            {
                System.out.println("BUILD: changeCandidateCells discarding cell " + candidateCell.getCellIndex() + " (l=" +
                        candidateCell.getLevel() + ", w=" + candidateCell.isOccupiedByWorker() + ", d=" + candidateCell.checkDome());
            }
        }
    }

    /**
     * Updates the board after a standard build has been performed onto the chosenCell
     * @param chosenCell cell chosen by the user on which the build will happen
     */

    @Override
    public void performActionOnCell(Cell chosenCell) {
        if(chosenCell.getLevel() < 3){
            chosenCell.addLevel();
        }
        else{
            chosenCell.addDome();
        }
        this.getWorker().setOldBuiltCell(chosenCell);
        this.getWorker().IncrementBuildCounter();

    }
}