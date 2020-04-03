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
        assertTrue(expectedList.containsAll(movePhase.getCandidateMoves())
                && movePhase.getCandidateMoves().containsAll(expectedList));
    }

    @Test
    public void changeCandidateMoves_workerAtTheCentreCorneredByAFewWorkersAndALv2Cell() {
        Cell x22 = gameBoard.getCell(6);//lv1cell
        x22.addLevel();
        Cell x23 = gameBoard.getCell(11);
        Cell x24 = gameBoard.getCell(16);//CellOccupiedByMyOtherWorker
        worker12.setPosition(x24);
        Cell x32 = gameBoard.getCell(7);//lv2Cell
        x32.addLevel();x32.addLevel();
        Cell x33 = gameBoard.getCell(12);//workerPosition
        worker11.setPosition(x33);
        Cell x34 = gameBoard.getCell(17);
        Cell x42 = gameBoard.getCell(8);//occupiedByEnemyWorkerCell
        worker21.setPosition(x42);
        Cell x43 = gameBoard.getCell(13);//lv1CellWithADomeOnTop
        x43.addLevel();x43.addDome();
        Cell x44 = gameBoard.getCell(18);

        MovePhase movePhase = new MovePhase(worker11,gameBoard);
        ArrayList<Cell> expectedList = new ArrayList<>();
        expectedList.add(x22);
        expectedList.add(x23);
        expectedList.add(x34);
        expectedList.add(x44);
        movePhase.changeCandidateMoves();
        assertTrue(expectedList.containsAll(movePhase.getCandidateMoves())
                && movePhase.getCandidateMoves().containsAll(expectedList));
    }

    @Test
    public void updateBoardAfterMove_validInput(){
        Cell x11 = gameBoard.getCell(0);
        worker11.setPosition(x11);
        Cell x12 = gameBoard.getCell(1);
        x12.addLevel();
        MovePhase movePhase = new MovePhase(worker11,gameBoard);
        movePhase.updateBoard(worker11,gameBoard,x12);
        assertTrue(!x11.isOccupiedByWorker() && worker11.getWorkerPosition().equals(x12));
    }

    @After
    public void tearDown() throws Exception {
    }
}