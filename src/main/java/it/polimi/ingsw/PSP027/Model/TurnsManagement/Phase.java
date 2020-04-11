package it.polimi.ingsw.PSP027.Model.TurnsManagement;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Worker;

import java.util.List;

/**
 * @author danielecarta
 */

public  abstract class Phase {

    private boolean done;
    private List<Cell> candidateCells;
    private Worker chosenWorker;
    private Board gameBoard;

    /**
     * Changes the candidate cells list of each phase according to its type
     */

    public abstract void changeCandidateCells();

    /**
     * Updates the the board of each phase according to its type and the action performed on the given cell
     * @pa
     * ram chosenCell
     */
    public abstract void updateBoard(Cell chosenCell);

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public List<Cell> getCandidateCells() {
        return candidateCells;
    }

    public void setCandidateCells(List<Cell> candidateCells) {
        this.candidateCells = candidateCells;
    }

    public Worker getChosenWorker() {
        return chosenWorker;
    }

    public void setChosenWorker(Worker chosenWorker) {
        this.chosenWorker = chosenWorker;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }
}
