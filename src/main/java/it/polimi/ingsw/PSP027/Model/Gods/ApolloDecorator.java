package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Controller.ConcretePhase;
import it.polimi.ingsw.PSP027.Controller.MovePhase;

/**
 * @author danielecarta
 * @author Elisa Maistri
 * */
public class ApolloDecorator extends GodPowerDecorator {

    public ApolloDecorator(ConcretePhase decoratedPhase) {
        super(decoratedPhase);
    }

    /**
     * Method called by a decorated turn's movePhase, it makes it possible to move in an enemy occupied cell swapping places with that enemy worker
     * @return a list of cells which you can move onto
     */

    @Override
    public void changeCandidateCells() {
        Cell startingCell = this.getDecoratedPhase().getChosenWorker().getWorkerPosition();

        for(int i = 0; i < this.getDecoratedPhase().getGameBoard().getBoard().size(); i++) {
            if(this.getDecoratedPhase().getGameBoard().getCell(i).isOccupiedByWorker()) {
                if(this.getDecoratedPhase().getGameBoard().getCell(i).getOccupyingWorker().getWorkerOwner() != this.getDecoratedPhase().getPlayingPlayer()) {
                    this.getDecoratedPhase().getCandidateCells().add(this.getDecoratedPhase().getGameBoard().getCell(i));
                }
            }
        }

        for (Cell candidateCell : this.getDecoratedPhase().getGameBoard().getNeighbouringCells(startingCell)) {
            if ((candidateCell.getLevel() <= startingCell.getLevel() + 1) &&
                    (!candidateCell.checkDome()) && !this.getDecoratedPhase().getCandidateCells().contains(candidateCell)) {
                if (!candidateCell.isOccupiedByWorker() || candidateCell.isOccupiedByOpponentWorker(this.getDecoratedPhase().getChosenWorker().getWorkerOwner())) {
                    this.getDecoratedPhase().getCandidateCells().add(candidateCell);
                }
            }
        }
    }

    /**
     * This method is called in a movePhase in order to update the board according to the decorator,in case it modifies the board itself
     * @param chosenCell the cell it is stepping onto
     */

    @Override
    public void updateBoard(Cell chosenCell) {
        MovePhase movePhase = (MovePhase) this.getDecoratedPhase();     //using movePhase to get startChosenWorkerLvl
        if (chosenCell.isOccupiedByOpponentWorker(this.getDecoratedPhase().getChosenWorker().getWorkerOwner())) {
            Worker opponentWorker = chosenCell.getOccupyingWorker();
            opponentWorker.changePosition(this.getDecoratedPhase().getChosenWorker().getWorkerPosition());
        }

        this.getDecoratedPhase().getChosenWorker().changePosition(chosenCell);
        if(movePhase.getStartChosenWorkerLvl()==2 && this.getChosenWorker().getWorkerPosition().getLevel()==3){     //check if the win conditions are verified
            this.getChosenWorker().getWorkerOwner().setHasWon(true);
        }
    }

}
