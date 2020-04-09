package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.ConcretePhase;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.Turn;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.MovePhase;

/**
 * @author danielecarta
 */
public class ArtemideDecorator extends GodPowerDecorator {


    public ArtemideDecorator(ConcretePhase decoratedPhase) {
        super(decoratedPhase);
    }

    @Override
    public void changeCandidateCells() {

    }

    @Override
    public void updateBoard(Cell chosenCell) {

    }
}
