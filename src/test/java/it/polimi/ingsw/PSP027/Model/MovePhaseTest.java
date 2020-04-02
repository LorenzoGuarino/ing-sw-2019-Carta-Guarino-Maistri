package it.polimi.ingsw.PSP027.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author danielecarta
 */

import static org.junit.Assert.*;

public class MovePhaseTest {
    Board gameBoard;
    Player player1;
    Player player2;
    Worker worker11;
    Worker worker12;
    Worker worker21;
    Worker worker22;

    @Before
    public void setUp() throws Exception {
        gameBoard = new Board();
        player1 = new Player();
        player2 = new Player();
        worker11 = player1.getPlayerWorkers().get(0);
        worker12 = player1.getPlayerWorkers().get(1);
        worker21 = player2.getPlayerWorkers().get(0);
        worker22 = player2.getPlayerWorkers().get(1);
    }

    @Test
    public void changeCandidateMoves_workerCorneredInTopLetfWith4MovesPossible() {
        Cell x15 = gameBoard.getCell(20);
        Cell x14 = gameBoard.getCell(15);
        Cell x25 = gameBoard.getCell(21);
        Cell x24 = gameBoard.getCell(16);
        worker11.setPosition(x15);
        MovePhase movePhase = new MovePhase(worker11,gameBoard);
        ArrayList<Cell> expectedList = new ArrayList<>();
        expectedList.add(x14);
        expectedList.add(x25);
        expectedList.add(x24);
        movePhase.changeCandidateMoves();
        assertEquals(expectedList,movePhase.getCandidateMoves());
    }

    @After
    public void tearDown() throws Exception {
    }
}