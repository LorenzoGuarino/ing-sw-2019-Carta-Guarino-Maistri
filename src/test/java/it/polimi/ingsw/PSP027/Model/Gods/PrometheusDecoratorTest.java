package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Controller.BuildPhase;
import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Controller.MovePhase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author danielecarta
 * @author Lorenzo Guarino
 */

public class PrometheusDecoratorTest {

    Board gameBoard;
    Player player1;
    Player player2;
    Worker worker11;
    Worker worker21;
    PrometheusDecorator prometheusDecoratedPhase, prometheusDecoratedPhase2;

    @Before
    public void setUp() {
        gameBoard = new Board();
        player1 = new Player();
        player2 = new Player();
        worker11 = player1.getPlayerWorkers().get(0);
        worker21 = player2.getPlayerWorkers().get(0);
    }

    /**
     * asserts that the first evalCandidateCells call gives a candidateCells list of candidateBuildingCells
     * even if that's a movePhase
     */

    @Test
    public void evalCandidateCells_givesMovingCandidates() {
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
        expectedList.add(x34);
        MovePhase movephase = new MovePhase();
        movephase.Init(worker11,gameBoard, true);
        prometheusDecoratedPhase = new PrometheusDecorator(movephase, false);
        prometheusDecoratedPhase.evalCandidateCells();
        assertTrue(expectedList.containsAll(movephase.getCandidateCells()) && movephase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * Asserts that the first board update is a building operation on the chosenCell
     */

    @Test
    public void performActionOnCell_buildingABlockBeforeMove() {
        Cell x14 = gameBoard.getCell(15);//lv2 im building onto
        x14.addLevel();x14.addLevel();
        Cell x25 = gameBoard.getCell(21);//workerPos
        worker11.changePosition(x25);
        BuildPhase buildPhase = new BuildPhase();
        buildPhase.Init(worker11, gameBoard, false); //buildphase before move
        prometheusDecoratedPhase = new PrometheusDecorator(buildPhase, false);
        prometheusDecoratedPhase.evalCandidateCells();
        prometheusDecoratedPhase.performActionOnCell(x14);
        assertEquals(3, x14.getLevel());
    }

    /**
     * asserts that the second evalCandidateCells call gives a candidateCells list of candidateMoveCells
     */

    @Test
    public void evalCandidateCells_givesMoveCandidatesAfterFirstBuild() {
        Cell x15 = gameBoard.getCell(20);
        Cell x14 = gameBoard.getCell(15);//lv0 im building onto, i cant move there
        x14.addLevel(); //simulating buildphase
        Cell x25 = gameBoard.getCell(21);//workerPos
        worker11.changePosition(x25);
        Cell x24 = gameBoard.getCell(16);//lv3dome
        x24.addLevel();x24.addLevel();x24.addLevel();x24.addDome();
        Cell x34 = gameBoard.getCell(17);
        Cell x35 = gameBoard.getCell(22);//enemyWorker
        worker21.changePosition(x35);
        ArrayList<Cell> expectedList = new ArrayList<>();
        expectedList.add(x15);
        expectedList.add(x34);
        worker11.IncrementBuildCounter();   //simulating buildphase
        MovePhase movephase = new MovePhase();
        movephase.Init(worker11, gameBoard, true);
        prometheusDecoratedPhase = new PrometheusDecorator(movephase, false);
        prometheusDecoratedPhase.evalCandidateCells();
        assertTrue(expectedList.containsAll(movephase.getCandidateCells()) && movephase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * Asserts that the second board update is a move operation on the chosenCell
     */

    @Test
    public void updateBoard_SecondIterationMoveOntoCell() {
        Cell x14 = gameBoard.getCell(15);//lv2 im building onto
        x14.addLevel();x14.addLevel();
        Cell x25 = gameBoard.getCell(21);//workerPos
        Cell x15 = gameBoard.getCell(20);
        worker11.changePosition(x25);
        MovePhase movephase = new MovePhase();
        movephase.Init(worker11,gameBoard, true);
        prometheusDecoratedPhase = new PrometheusDecorator(movephase, false);
        prometheusDecoratedPhase.evalCandidateCells();
        prometheusDecoratedPhase.performActionOnCell(x15);
        assertEquals(x15.getOccupyingWorker(), worker11);
    }
}