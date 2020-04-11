package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.ConcretePhase;

public class HephaestusDecorator extends GodPowerDecorator{

    private boolean AskUserToUsePower = true;
    private boolean UserWantsToUsePower;

    public HephaestusDecorator(ConcretePhase decoratedPhase) {
        super(decoratedPhase);
    }

    /**
     * Method used by Hephaestus to set the candidate cells on which its player can build on.
     * It's the standard method of checking if the neighbouring cell is not occupied by another worker
     * and has not already a dome
     */
    @Override
    public void changeCandidateCells() {
        Cell startingCell = this.getDecoratedPhase().getChosenWorker().getWorkerPosition();
        for (Cell candidateCell : this.getDecoratedPhase().getGameBoard().getNeighbouringCells(startingCell)) {
            if (!candidateCell.isOccupiedByWorker() && (!candidateCell.checkDome())) {
                this.getDecoratedPhase().getCandidateCells().add(candidateCell);
            }
        }
    }
    /**
     * Method used by Hephaestus to add a second block on his first if it's not a dome, if the player checked yes to
     * using its God Card, otherwise builds a block/dome with the standard method of adding a level if there's a 0, 1
     * or 2 level and adding a dome if there's a level 3 on the cell.
     * @param chosenCell cell chosen by the player on which it wants to build
     */
    @Override
    public void updateBoard(Cell chosenCell) {
        if(UserWantsToUsePower){
            if(chosenCell.getLevel() < 2){
                chosenCell.addLevel();
                chosenCell.addLevel();
            }else if(chosenCell.getLevel()==2){
                chosenCell.addLevel();
            }else if(chosenCell.getLevel()==3){
                chosenCell.addDome();
            }
        }else{
            if(chosenCell.getLevel() < 3){
                chosenCell.addLevel();
            }
            else{
                chosenCell.addDome();
            }
        }
    }
}