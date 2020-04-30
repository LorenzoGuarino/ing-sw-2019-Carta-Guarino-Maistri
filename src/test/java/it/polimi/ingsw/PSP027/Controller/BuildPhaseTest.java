package it.polimi.ingsw.PSP027.Controller;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Model.Gods.ZeusDecorator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Elisa Maistri
 */

public class BuildPhaseTest {

    Player player1, player2;
    Worker worker1_1,worker1_2, worker2_1, worker2_2;
    BuildPhase build;
    Board gameBoard;

    @Before
    public void setUp() throws Exception {
        gameBoard = new Board();
        player1 = new Player();
        player2 = new Player();
        worker1_1 = player1.getPlayerWorkers().get(0);
        worker1_2 = player1.getPlayerWorkers().get(1);
        worker2_1 = player2.getPlayerWorkers().get(0);
        worker2_2 = player2.getPlayerWorkers().get(1);
    }

    @Test
    public void evalCandidateCells() {
        Cell A3 = gameBoard.getCell(2); //not candidate cell
        worker1_1.changePosition(A3);
        Cell A2 = gameBoard.getCell(1); //candidate
        Cell A4 = gameBoard.getCell(3); //candidate
        Cell B2 = gameBoard.getCell(6); //candidate
        B2.addLevel();
        B2.addLevel();
        Cell B3 = gameBoard.getCell(7); //not candidate cell
        worker2_1.changePosition(B3);
        Cell B4 = gameBoard.getCell(8); //not candidate cell
        B4.addDome();

        build = new BuildPhase();
        build.Init(worker1_1,gameBoard, true);
        build.evalCandidateCells();
        ArrayList<Cell> expectedList = new ArrayList<Cell>();
        expectedList.add(A2);
        expectedList.add(A4);
        expectedList.add(B2);
        assertTrue(expectedList.containsAll(build.getCandidateCells()) && build.getCandidateCells().containsAll(expectedList));
    }

    @Test
    public void performActionOnCellShouldAddALevel() {
        Cell A3 = gameBoard.getCell(2);
        worker1_1.changePosition(A3);
        Cell A4 = gameBoard.getCell(3);
        A4.addLevel(true);
        A4.addLevel(true);
        build = new BuildPhase();
        build.Init(worker1_1, gameBoard, true);
        build.performActionOnCell(A4);
        assertEquals(3, A4.getLevel());
    }

    @Test
    public void performActionOnCellShouldAddADome() {
        Cell A3 = gameBoard.getCell(2);
        worker1_1.changePosition(A3);
        Cell A4 = gameBoard.getCell(3);
        A4.addLevel(true);
        A4.addLevel(true);
        A4.addLevel(true);
        build = new BuildPhase();
        build.Init(worker1_1, gameBoard, true);
        build.performActionOnCell(A4);
        boolean A4dome = A4.checkDome();
        assertEquals(true, A4dome);
    }
}