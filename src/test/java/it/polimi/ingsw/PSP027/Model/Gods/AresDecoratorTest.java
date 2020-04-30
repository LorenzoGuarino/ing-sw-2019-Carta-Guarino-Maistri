package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Controller.BuildPhase;
import it.polimi.ingsw.PSP027.Controller.EndPhase;
import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Elisa Maistri
 */

public class AresDecoratorTest {

    Player player1, player2;
    Worker worker1_1,worker1_2, worker2_1,worker2_2;
    AresDecorator aresDecoratedPhase;
    Board gameBoard;

    @Before
    public void setUp() throws Exception {
        gameBoard = new Board();
        player1 = new Player();
        player2 = new Player();
        worker1_1= player1.getPlayerWorkers().get(0);
        worker1_2 = player1.getPlayerWorkers().get(1);
        worker2_1= player2.getPlayerWorkers().get(0);
        worker2_2 = player2.getPlayerWorkers().get(1);
    }

    /**
     * Test should assert that the list of candidate cells contains unoccupied
     * cells with a level >= 1 but nod with a dome, neighbouring the player's
     * second worker if it has moved the first
     */

    @Test
    public void evalCandidateCellsShouldReturnListContainingUnoccupiedBlocks_CaseWorker1_1() {
        Cell A3 = gameBoard.getCell(2);
        Cell A2 = gameBoard.getCell(1);
        Cell A4 = gameBoard.getCell(3);
        Cell B2 = gameBoard.getCell(6);
        Cell B3 = gameBoard.getCell(7); //7 NO
        Cell B4 = gameBoard.getCell(8);
        Cell E3 = gameBoard.getCell(22);
        worker1_1.changePosition(A3);
        worker1_2.changePosition(E3);
        B2.addDome(); //6 NO
        B4.addLevel(); //8
        worker2_1.changePosition(B4); //8 NO
        A2.addLevel(); //1 SI
        A4.addLevel(); //3 SI
        EndPhase endPhase = new EndPhase();
        endPhase.Init(worker1_2, gameBoard, false);
        aresDecoratedPhase = new AresDecorator(endPhase, false);
        aresDecoratedPhase.evalCandidateCells();
        ArrayList<Cell> expectedList = new ArrayList<Cell>();
        expectedList.add(gameBoard.getCell(1));
        expectedList.add(gameBoard.getCell(3));

        assertTrue(expectedList.containsAll(endPhase.getCandidateCells()) && endPhase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * Test should assert that the list of candidate cells contains unoccupied
     * cells with a level >= 1 but nod with a dome, neighbouring the player's
     * first worker if it has moved the second
     */

    @Test
    public void evalCandidateCellsShouldReturnListContainingUnoccupiedBlocks_CaseWorker1_2() {
        Cell A3 = gameBoard.getCell(2);
        Cell A2 = gameBoard.getCell(1);
        Cell A4 = gameBoard.getCell(3);
        Cell B2 = gameBoard.getCell(6);
        Cell B3 = gameBoard.getCell(7); //7 NO
        Cell B4 = gameBoard.getCell(8);
        Cell E3 = gameBoard.getCell(22);
        worker1_2.changePosition(A3);
        worker1_1.changePosition(E3);
        B2.addDome(); //6 NO
        B4.addLevel(); //8
        worker2_1.changePosition(B4); //8 NO
        A2.addLevel(); //1 SI
        A4.addLevel(); //3 SI
        EndPhase endPhase = new EndPhase();
        endPhase.Init(worker1_1, gameBoard, false);
        aresDecoratedPhase = new AresDecorator(endPhase, false);
        aresDecoratedPhase.evalCandidateCells();
        ArrayList<Cell> expectedList = new ArrayList<Cell>();
        expectedList.add(gameBoard.getCell(1));
        expectedList.add(gameBoard.getCell(3));

        assertTrue(expectedList.containsAll(endPhase.getCandidateCells()) && endPhase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * Test should assert that Ares removes a level on the chosen cell
     */

    @Test
    public void performActionOnCellShouldRemoveALevel() {
        Cell A2 = gameBoard.getCell(1);
        A2.addLevel();
        EndPhase endPhase = new EndPhase();
        endPhase.Init(worker1_1, gameBoard, false);
        aresDecoratedPhase = new AresDecorator(endPhase, false);
        aresDecoratedPhase.performActionOnCell(A2);
        assertEquals(0, A2.getLevel());
    }
}