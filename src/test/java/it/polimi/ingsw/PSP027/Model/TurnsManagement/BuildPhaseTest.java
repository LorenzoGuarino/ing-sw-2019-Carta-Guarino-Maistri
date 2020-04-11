package it.polimi.ingsw.PSP027.Model.TurnsManagement;

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

public class BuildPhaseTest {
    Board gameBoard;
    Player player1;
    Player player2;
    Worker worker11;
    Worker worker21;

    @Before
    public void setUp() {
        gameBoard = new Board();
        player1 = new Player();
        player2 = new Player();
        worker11 = player1.getPlayerWorkers().get(0);
        worker21 = player2.getPlayerWorkers().get(0);
    }

    @Test
    public void changeCandidateCells() {
        Cell x15 = gameBoard.getCell(20);
        Cell x14 = gameBoard.getCell(15);//lv2
        x14.addLevel();x14.addLevel();
        Cell x25 = gameBoard.getCell(21);//workerPos
        worker11.changePosition(x25);
        Cell x24 = gameBoard.getCell(16);//lv3dome
        x24.addLevel();x24.addLevel();x24.addLevel();x24.addDome();
        Cell x34 = gameBoard.getCell(17);
        Cell x35 = gameBoard.getCell(22);//enemyWorker
        worker21.changePosition(x35);
        ArrayList<Cell> expectedList = new ArrayList<>();
        expectedList.add(x15);
        expectedList.add(x14);
        expectedList.add(x34);
        BuildPhase buildPhase = new BuildPhase(worker11,gameBoard);
        assertTrue(expectedList.containsAll(buildPhase.getCandidateCells())&&buildPhase.getCandidateCells().containsAll(expectedList));
    }

    @Test
    public void updateBoard_buildingABlock() {
        Cell x14 = gameBoard.getCell(15);//lv2 im building onto
        x14.addLevel();x14.addLevel();
        Cell x25 = gameBoard.getCell(21);//workerPos
        worker11.changePosition(x25);
        BuildPhase buildPhase = new BuildPhase(worker11,gameBoard);
        buildPhase.updateBoard(x14);
        assertEquals(3, x14.getLevel());
    }

    @Test
    public void updateBoard_buildingADome() {
        Cell x14 = gameBoard.getCell(15);//lv3 im putting a dome onto
        x14.addLevel();x14.addLevel();x14.addLevel();
        Cell x25 = gameBoard.getCell(21);//workerPos
        worker11.changePosition(x25);
        BuildPhase buildPhase = new BuildPhase(worker11,gameBoard);
        buildPhase.updateBoard(x14);
        assertTrue(x14.checkDome());
    }
}