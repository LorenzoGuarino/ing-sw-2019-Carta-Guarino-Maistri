package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.GodCard;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Controller.MovePhase;

/**
 * @author danielecarta
 */

public class AthenaDecorator extends GodPowerDecorator {

    public AthenaDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        super(decoratedPhase, bActAsOpponentGod);
    }

    @Override
    public void evalCandidateCells() {

        // call nested phase evalCandidateCells
        super.evalCandidateCells();


        // Athena override only opponent move phase
        if(getDecoratedPhase().IsAMovePhase() && IsActingOnOpponentPlayer()) {

            // Athena when applied remove cells from the current CandidateCells list that has a level > current worker level

            Cell startingCell = this.getWorker().getWorkerPosition();

            for (Cell candidateCell : this.getGameBoard().getNeighbouringCells(startingCell)) {

                if(this.getCandidateCells().indexOf(candidateCell) != -1) {       //for each candidate cell in neighbouringCells already within the list,
                    if (candidateCell.getLevel() > startingCell.getLevel())       // if the lv is higher than current one, remove it
                    {
                        System.out.println("ATHENA: evalCandidateCells removing cell " + candidateCell.getCellIndex());
                        this.getCandidateCells().remove(candidateCell);
                    }
                }
            }
        }
    }

    /**
     * standard move action that then checks for athena's power conditions to be applied, and in case it applies it
     * @param chosenCell the cell im moving onto
     */
    @Override
    public void performActionOnCell(Cell chosenCell) {

        super.performActionOnCell(chosenCell);

        if(getDecoratedPhase().IsAMovePhase() && !IsActingOnOpponentPlayer()) {
//        MovePhase movePhase = (MovePhase)this.getDecoratedPhase();
//        this.getWorker().changePosition(chosenCell);
//        if(movePhase.getStartChosenWorkerLvl()==2 && this.getWorker().getWorkerPosition().getLevel()==3){    //check if the win conditions are verified
//            this.getWorker().getWorkerOwner().setHasWon(true);
//        }
//        if (movePhase.getStartChosenWorkerLvl()<this.getDecoratedPhase().getWorker().getWorkerPosition().getLevel()) {
//            applyPowerOnOtherPlayers();
//        }
        }
    }

    /**
     * after athena's move is performed i call this method to check if she has moved up and can then apply her power on the opponent players
     */
    public void applyPowerOnOtherPlayers() {
        Player otherPlayer1 = null, otherPlayer2 = null;
        for (int i = 0; i < 25 && otherPlayer2==null; i++) {
                Cell cell = this.getDecoratedPhase().getGameBoard().getCell(i);
                if (cell.isOccupiedByWorker()) {
                    Player player = cell.getOccupyingWorker().getWorkerOwner();
                    if (player != this.getDecoratedPhase().getWorker().getWorkerOwner() && otherPlayer1 == null) {
                        otherPlayer1 = player;
                    }
                    if (player != this.getDecoratedPhase().getWorker().getWorkerOwner() && otherPlayer1 != null && otherPlayer1 != player) {
                        otherPlayer2 = player;
                    }
                }
        }
        //otherPlayer1.addOpponentGodCard(new GodCard(GodCard.GodsType.AthenaOpp, "You can't move up this turn"));
        //otherPlayer2.addOpponentGodCard(new GodCard(GodCard.GodsType.AthenaOpp, "You can't move up this turn"));
    }

}
