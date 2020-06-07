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

    /**
     * Constructor : sets the phase the decorator is decorating and a boolean that if it is set as true tells
     * that the decorator acts when it is played by an opponent of the god card's owner
     * @param decoratedPhase phase the decorator is going to decorate
     * @param bActAsOpponentGod true if the god card will act only when it is being played as an opponent god card, otherwise it is false
     */
    public PrometheusDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    /**
     * Method used to get a standard set of candidate cells list for the move phase removing the ones that make
     * the player move up if the worker had already built a cell before moving
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
                if(candidateCell.getLevel() > startingCell.getLevel()){//prometheus can't move up
                    System.out.println("PROMETHEUS: evalCandidateCells discarding cell " + candidateCell.getCellIndex());
                    this.getCandidateCells().remove(i);
                }
            }
        }
    }

    /**
     * This method performs the action described by the god's power on the cell chosen by the player
     * @param chosenCell the Cell on which the worker wants to perform the action
     */
    @Override
    public void performActionOnCell(Cell chosenCell) {
        super.performActionOnCell(chosenCell);
    }
}