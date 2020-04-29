package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Controller.BuildPhase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author danielecarta
 */

public class DemeterDecoratorTest {
    Board gameBoard;
    Player player1;
    Player player2;
    Worker worker11;
    Worker worker21;
    DemeterDecorator demeterDecoratedPhase;

    @Before
    public void setUp() {
        gameBoard = new Board();
        player1 = new Player();
        player2 = new Player();
        worker11 = player1.getPlayerWorkers().get(0);
        worker21 = player2.getPlayerWorkers().get(0);
    }

    /**
     * asserts that the candidateCells of the buildPhase is ok
     */

    @Test
    public void evalCandidateCells() {
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
        BuildPhase buildPhase = new BuildPhase();
        buildPhase.Init(worker11,gameBoard, true);
        demeterDecoratedPhase = new DemeterDecorator(buildPhase, false);
        demeterDecoratedPhase.evalCandidateCells();
        assertTrue(expectedList.containsAll(buildPhase.getCandidateCells()) && buildPhase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * asserts that the first call of updateBoard is a standard build action
     */

    @Test
    public void updateBoard_once() {
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
        BuildPhase buildPhase = new BuildPhase();
        buildPhase.Init(worker11,gameBoard, true);
        demeterDecoratedPhase = new DemeterDecorator(buildPhase, false);
        demeterDecoratedPhase.performActionOnCell(x14);
        assertTrue(x14.getLevel()==3);
    }

    /**
     * asserts that the second call of updateBoard on the same cell doesn't let the player build on the same cell i
     * just built onto before
     */

    @Test
    public void updateBoard_twiceOnSameCell() {
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
        BuildPhase buildPhase = new BuildPhase();
        buildPhase.Init(worker11,gameBoard, true);
        demeterDecoratedPhase = new DemeterDecorator(buildPhase, false);
        demeterDecoratedPhase.performActionOnCell(x14);
        demeterDecoratedPhase.evalCandidateCells();
        System.out.println(worker11.getBuildCounter());
        assertTrue(x14.getLevel()==3 && !demeterDecoratedPhase.getCandidateCells().contains(x14));
    }

    /**
     * asserts that the second call of updateBoard on a different cell i just built onto is allowed and performed
     */

    @Test
    public void updateBoard_twiceOnDifferentCell() {
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
        BuildPhase buildPhase = new BuildPhase();
        buildPhase.Init(worker11,gameBoard, true);
        demeterDecoratedPhase = new DemeterDecorator(buildPhase, false);
        demeterDecoratedPhase.performActionOnCell(x34);
        demeterDecoratedPhase.performActionOnCell(x14);
        assertTrue(x14.getLevel()==3 && x34.getLevel()==1);
    }
}