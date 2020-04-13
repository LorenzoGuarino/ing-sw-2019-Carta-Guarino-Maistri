package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.GodCard;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.ConcretePhase;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.MovePhase;

/**
 * @author danielecarta
 */

public class AthenaDecorator extends GodPowerDecorator {

    public AthenaDecorator(ConcretePhase decoratedPhase) {
        super(decoratedPhase);
    }

    @Override
    public void changeCandidateCells() { }

    /**
     * standard move action that then checks for athena's power conditions to be applied, and in case it applies it
     * @param chosenCell the cell im moving onto
     */
    @Override
    public void updateBoard(Cell chosenCell) {
        this.getChosenWorker().changePosition(chosenCell);
        MovePhase movePhase = (MovePhase)this.getDecoratedPhase();
        if (movePhase.getStartChosenWorkerLvl()<this.getDecoratedPhase().getChosenWorker().getWorkerPosition().getLevel()) {
            applyPowerOnOtherPlayers();
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
                    if (player != this.getDecoratedPhase().getChosenWorker().getWorkerOwner() && otherPlayer1 == null) {
                        otherPlayer1 = player;
                    }
                    if (player != this.getDecoratedPhase().getChosenWorker().getWorkerOwner() && otherPlayer1 != null && otherPlayer1 != player) {
                        otherPlayer2 = player;
                    }
                }
        }
        otherPlayer1.addOpponentGodCard(new GodCard(GodCard.GodsType.AthenaOpp, "You can't move up this turn"));
        otherPlayer2.addOpponentGodCard(new GodCard(GodCard.GodsType.AthenaOpp, "You can't move up this turn"));
    }
}
