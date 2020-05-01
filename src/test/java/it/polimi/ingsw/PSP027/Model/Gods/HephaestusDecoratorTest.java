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

public class HephaestusDecoratorTest {
    Board gameBoard;
    Player player1;
    Player player2;
    Worker worker11;
    Worker worker21;
    HephaestusDecorator hephaestusDecoratedPhase;

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
    public void evalCandidateCellsShouldPerformAStandardBuildTheFirstTime() {
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
        hephaestusDecoratedPhase = new HephaestusDecorator(buildPhase, false);
        hephaestusDecoratedPhase.evalCandidateCells();
        assertTrue(expectedList.containsAll(buildPhase.getCandidateCells()) && buildPhase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * Test should assert that hephaestus can only build where it built before if this is the second optional build
     */
    @Test
    public void evalCandidateCellsShouldReturnOnlyThePreviousBuiltCell() {
        Cell B3 = gameBoard.getCell(7);
        Cell B4 = gameBoard.getCell(8);
        worker11.changePosition(B3);
        BuildPhase buildPhase1 = new BuildPhase();
        buildPhase1.Init(worker11,gameBoard, true);
        hephaestusDecoratedPhase = new HephaestusDecorator(buildPhase1, false);
        hephaestusDecoratedPhase.performActionOnCell(B4);
        BuildPhase buildPhase2 = new BuildPhase();
        buildPhase2.Init(worker11,gameBoard, false);
        hephaestusDecoratedPhase = new HephaestusDecorator(buildPhase2, false);
        hephaestusDecoratedPhase.evalCandidateCells();
        ArrayList<Cell> expectedList = new ArrayList<>();
        expectedList.add(B4);
        assertTrue(expectedList.containsAll(buildPhase2.getCandidateCells()) && buildPhase2.getCandidateCells().containsAll(expectedList));
    }

    /**
     * asserts that if hephaestus builds on a lv2 building it increases the lv once and is not able to put a dome onto it
     */

    @Test
    public void updateBoard_onALv2Building() {
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
        hephaestusDecoratedPhase = new HephaestusDecorator(buildPhase, false);
        hephaestusDecoratedPhase.performActionOnCell(x14);
        assertTrue(x14.getLevel()==3 && !x14.checkDome());
    }

    /**
     * asserts that if hephaestus builds on a lv1 building it increases the lv twice
     */

    @Test
    public void updateBoard_onALv1Building() {
        Cell x15 = gameBoard.getCell(20);
        Cell x14 = gameBoard.getCell(15);//lv1
        x14.addLevel();
        Cell x25 = gameBoard.getCell(21);//workerPos
        worker11.changePosition(x25);
        Cell x24 = gameBoard.getCell(16);//lv3dome
        x24.addLevel();x24.addLevel();x24.addLevel();x24.addDome();
        Cell x34 = gameBoard.getCell(17);
        Cell x35 = gameBoard.getCell(22);//enemyWorker
        worker21.changePosition(x35);
        BuildPhase buildPhase = new BuildPhase();
        buildPhase.Init(worker11, gameBoard, true);
        hephaestusDecoratedPhase = new HephaestusDecorator(buildPhase, false);
        hephaestusDecoratedPhase.performActionOnCell(x14);
        assertTrue(x14.getLevel()==2 && !x14.checkDome());
    }

    /**
     * asserts that if hephaestus builds on a lv1 building it increases the lv twice
     */

    @Test
    public void updateBoard_onALv3Building() {
        Cell x15 = gameBoard.getCell(20);
        Cell x14 = gameBoard.getCell(15);//lv3
        x14.addLevel();x14.addLevel();x14.addLevel();
        Cell x25 = gameBoard.getCell(21);//workerPos
        worker11.changePosition(x25);
        Cell x24 = gameBoard.getCell(16);//lv3dome
        x24.addLevel();x24.addLevel();x24.addLevel();x24.addDome();
        Cell x34 = gameBoard.getCell(17);
        Cell x35 = gameBoard.getCell(22);//enemyWorker
        worker21.changePosition(x35);
        BuildPhase buildPhase = new BuildPhase();
        buildPhase.Init(worker11,gameBoard, true);
        hephaestusDecoratedPhase = new HephaestusDecorator(buildPhase, false);
        hephaestusDecoratedPhase.performActionOnCell(x14);
        assertTrue(x14.getLevel()==3 && x14.checkDome());
    }
}