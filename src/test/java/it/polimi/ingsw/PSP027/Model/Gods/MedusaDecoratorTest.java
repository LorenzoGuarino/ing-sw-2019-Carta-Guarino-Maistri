package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Controller.EndPhase;
import it.polimi.ingsw.PSP027.Controller.MovePhase;
import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author danielecarta
 */

public class MedusaDecoratorTest {
    Player player1,player2;
    Worker worker11,worker12,worker21,worker22;
    Board gameBoard;

    @Before
    public void setUp() {
        gameBoard = new Board();
        player1= new Player();
        player2= new Player();
        worker11= player1.getPlayerWorkers().get(0);
        worker12 = player1.getPlayerWorkers().get(1);
        worker21=player2.getPlayerWorkers().get(0);
        worker22= player2.getPlayerWorkers().get(1);
    }
    @Test
    public void evalCandidateCells_onAMovePhase() {
        Cell x11 = gameBoard.getCell(0);
        worker11.changePosition(x11);
        Cell x21 = gameBoard.getCell(1);
        worker21.changePosition(x21);
        Cell x12 = gameBoard.getCell(5);
        Cell x22 = gameBoard.getCell(6);
        x22.addLevel();worker12.changePosition(x22);
        MovePhase movePhase= new MovePhase();
        movePhase.Init(worker11,gameBoard, true);
        MedusaDecorator medusaDecoratedPhase = new MedusaDecorator(movePhase, false);
        medusaDecoratedPhase.evalCandidateCells();
        ArrayList<Cell> expectedList= new ArrayList<Cell>();
        expectedList.add(x12);
        assertTrue(expectedList.containsAll(movePhase.getCandidateCells())&&movePhase.getCandidateCells().containsAll(expectedList));
    }

    @Test
    public void evalCandidateCells_onAnEndPhaseThatRemovesBothPlayer2sWorkersAndMakesHimLose() {
        Cell x11 = gameBoard.getCell(0);
        x11.addLevel();
        worker11.changePosition(x11);
        Cell x21 = gameBoard.getCell(1);
        worker21.changePosition(x21);
        Cell x42 = gameBoard.getCell(8);
        x42.addLevel();
        worker12.changePosition(x42);
        Cell x52 = gameBoard.getCell(9);
        worker22.changePosition(x52);
        EndPhase endPhase = new EndPhase();
        endPhase.Init(worker11,gameBoard,false);
        MedusaDecorator medusaDecoratedPhase = new MedusaDecorator(endPhase, false);
        medusaDecoratedPhase.startPhase();
        ArrayList<Cell> expectedList= new ArrayList<Cell>();
        expectedList.add(x21);
        expectedList.add(x52);
        assertTrue(expectedList.containsAll(endPhase.getCandidateCells())&&endPhase.getCandidateCells().containsAll(expectedList));
        assertEquals(0, player2.getPlayerWorkers().size());
        assertTrue(player2.HasLost());
    }
}