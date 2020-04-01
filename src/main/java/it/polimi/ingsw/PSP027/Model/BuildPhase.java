package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Elisa Maistri
 */

public class BuildPhase extends Phase {
    private List<Cell> candidateBuildingCells;
    private GodPowerDecorator actualDecorator;
    private Worker chosenWorker;
    private Board gameBoard;

    /**
     * emptyConstructor
     */
    public BuildPhase(){
        super();
    }

    /**
     * Constructor, builds a standard BuildPhase with a standard candidateBuildingCells list starting from the
     * chosenWorker moved in the MovePhase and the board of the current turn
     * @param chosenWorker
     */

    public BuildPhase(Worker chosenWorker, Board gameBoard) {
        this.gameBoard = gameBoard;
        this.chosenWorker = chosenWorker;
        Cell CurrentUpdatedWorkerPosition = chosenWorker.getWorkerPosition();
        this.candidateBuildingCells = new ArrayList<>();

        for(Cell candidateCell : gameBoard.getNeighbouringCells(CurrentUpdatedWorkerPosition)) {
            if((!candidateCell.isOccupiedByWorker()) && (!candidateCell.checkDome())) {
                candidateBuildingCells.add(candidateCell);
            }
        }
    }

    /**
     * Copy Constructor
     * @param buildPhase
     */

    public BuildPhase(BuildPhase buildPhase){
        this.chosenWorker = buildPhase.chosenWorker;
        this.actualDecorator = buildPhase.actualDecorator;
        this.chosenWorker = buildPhase.chosenWorker;
        this.gameBoard = buildPhase.gameBoard;
    }

    /**
     * Method that modifies the actual candidateBuildingCells list according to the GodPowerDecorator decorating the BuildPhase's turn
     * If the decorator doesn't modify BuildConditions returns without doing anything
     */

    public void changeCandidateBuildingCellsAccordingToDecorator() {
        List<Cell> candidateDecoratedBuildingCells = this.actualDecorator.changeBuildConditions(chosenWorker, gameBoard);
        if (candidateDecoratedBuildingCells == null) return;
        else this.candidateBuildingCells = candidateDecoratedBuildingCells;
    }

    /**
     * Method that modifies the way a player can build
     * If the decorator doesn't modify BuildMode returns without doing anything
     */

    public void changeBuildModeAccordingToDecorator() {

    }

}