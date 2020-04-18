package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Controller.ConcretePhase;
import it.polimi.ingsw.PSP027.Controller.Phase;

import java.util.List;

/**
 * @author danielecarta
 */

public abstract class GodPowerDecorator extends Phase {

    /**
     * The concrete phase the decorator goes to decorate
     */
    private ConcretePhase decoratedPhase;

    public GodPowerDecorator(ConcretePhase decoratedPhase) {
        this.decoratedPhase = decoratedPhase;
    }

    public ConcretePhase getDecoratedPhase() {
        return decoratedPhase;
    }

    public void setDecoratedPhase(ConcretePhase decoratedPhase) {
        this.decoratedPhase = decoratedPhase;
    }

    @Override
    public void setDone(boolean done) {
        this.getDecoratedPhase().setDone(done);
    }

    @Override
    public List<Cell> getCandidateCells() {
        return this.getDecoratedPhase().getCandidateCells();
    }

    @Override
    public void setCandidateCells(List<Cell> candidateCells) {
        this.getDecoratedPhase().setCandidateCells(candidateCells);
    }

    @Override
    public Worker getChosenWorker() {
        return this.getDecoratedPhase().getChosenWorker();
    }

    @Override
    public void setChosenWorker(Worker chosenWorker) {
        this.setChosenWorker(chosenWorker);
    }

    @Override
    public Board getGameBoard() {
        return this.getDecoratedPhase().getGameBoard();
    }

    @Override
    public void setGameBoard(Board gameBoard) {
        this.getDecoratedPhase().setGameBoard(gameBoard);
    }
}
