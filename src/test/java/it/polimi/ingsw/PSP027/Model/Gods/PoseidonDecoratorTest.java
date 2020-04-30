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

public class PoseidonDecoratorTest {

    Player player;
    Worker worker1,worker2;
    PoseidonDecorator poseidonDecoratedPhase;
    Board gameBoard;

    @Before
    public void setUp() throws Exception {
        gameBoard = new Board();
        player = new Player();
        worker1= player.getPlayerWorkers().get(0);
        worker2 = player.getPlayerWorkers().get(1);
    }

    /**
     * Test should assert that the candidate cells are the standard candidate cells for the build of the player's first worker
     * when it has moved the second
     */

    @Test
    public void evalCandidateCellsShouldReturnStandardBuildCandidateCellsForUnmovedWorkerIfItsAtGroundLevel_CaseWorker1() {
        Cell B3 = gameBoard.getCell(7); //Player with Zeus should be able to build there -> worker1 is on C3
        Cell E3 = gameBoard.getCell(22);
        worker1.changePosition(E3);
        worker2.changePosition(B3);
        EndPhase endPhase = new EndPhase();
        endPhase.Init(worker1, gameBoard, false);
        poseidonDecoratedPhase = new PoseidonDecorator(endPhase, false);
        poseidonDecoratedPhase.evalCandidateCells();
        ArrayList<Cell> expectedList = new ArrayList<Cell>();
        expectedList.add(gameBoard.getCell(6));
        expectedList.add(gameBoard.getCell(8));
        expectedList.add(gameBoard.getCell(11));
        expectedList.add(gameBoard.getCell(12));
        expectedList.add(gameBoard.getCell(13));
        expectedList.add(gameBoard.getCell(1));
        expectedList.add(gameBoard.getCell(2));
        expectedList.add(gameBoard.getCell(3));

        assertTrue(expectedList.containsAll(endPhase.getCandidateCells()) && endPhase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * Test should assert that the candidate cells are the standard candidate cells for the build of the player's second worker
     * when it has moved the first
     */

    @Test
    public void evalCandidateCellsShouldReturnStandardBuildCandidateCellsForUnmovedWorkerIfItsAtGroundLevel_CaseWorker2() {
        Cell B3 = gameBoard.getCell(7); //Player with Zeus should be able to build there -> worker1 is on C3
        Cell E3 = gameBoard.getCell(22);
        worker2.changePosition(E3);
        worker1.changePosition(B3);
        EndPhase endPhase = new EndPhase();
        endPhase.Init(worker2, gameBoard, false);
        poseidonDecoratedPhase = new PoseidonDecorator(endPhase, false);
        poseidonDecoratedPhase.evalCandidateCells();
        ArrayList<Cell> expectedList = new ArrayList<Cell>();
        expectedList.add(gameBoard.getCell(6));
        expectedList.add(gameBoard.getCell(8));
        expectedList.add(gameBoard.getCell(11));
        expectedList.add(gameBoard.getCell(12));
        expectedList.add(gameBoard.getCell(13));
        expectedList.add(gameBoard.getCell(1));
        expectedList.add(gameBoard.getCell(2));
        expectedList.add(gameBoard.getCell(3));

        assertTrue(expectedList.containsAll(endPhase.getCandidateCells()) && endPhase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * Test should assert that the candidate cells evalueted is an empty list if the worker unmoved is not at ground level
     */

    @Test
    public void evalCandidateCellsShouldReturnEmptyCandidateCellsListWhenUnmovedWorkerIsNotAtGroundLevel() {
        Cell B3 = gameBoard.getCell(7); //Player with Zeus should be able to build there -> worker1 is on C3
        Cell E3 = gameBoard.getCell(22);
        B3.addLevel();
        worker1.changePosition(E3);
        worker2.changePosition(B3);
        EndPhase endPhase = new EndPhase();
        endPhase.Init(worker1, gameBoard, false);
        poseidonDecoratedPhase = new PoseidonDecorator(endPhase, false);
        poseidonDecoratedPhase.evalCandidateCells();
        ArrayList<Cell> expectedList = new ArrayList<Cell>();

        assertTrue(expectedList.containsAll(endPhase.getCandidateCells()) && endPhase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * Test should assert that the candidate cells list evaluated is empty when the player with poseidon has only 1 worker
     */

    @Test
    public void evalCandidateCellsShouldBeEmptyIfThePlayerHasOnly1Worker() {
        player.getPlayerWorkers().remove(1);
        Cell B3 = gameBoard.getCell(7); //Player with Zeus should be able to build there -> worker1 is on C3
        worker1.changePosition(B3);
        EndPhase endPhase = new EndPhase();
        endPhase.Init(worker1, gameBoard, false);
        poseidonDecoratedPhase = new PoseidonDecorator(endPhase, false);
        poseidonDecoratedPhase.evalCandidateCells();
        ArrayList<Cell> expectedList = new ArrayList<Cell>();

        assertTrue(expectedList.containsAll(endPhase.getCandidateCells()) && endPhase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * Test should assert that Poseidon when building adds a standard level if the cell's level is < 3
     */

    @Test
    public void performActionOnCell_AddLevel() {
        Cell B4 = gameBoard.getCell(8);
        Cell B3 = gameBoard.getCell(7);//workerPos
        worker1.changePosition(B3);
        EndPhase endPhase = new EndPhase();
        endPhase.Init(worker1, gameBoard, false);
        poseidonDecoratedPhase = new PoseidonDecorator(endPhase, false);
        poseidonDecoratedPhase.performActionOnCell(B4);
        assertTrue(B4.getLevel()==1);
    }

    /**
     * Test should assert that Poseidon when building at level 3 adds a dome
     */

    @Test
    public void performActionOnCell_AddDome() {
        Cell B4 = gameBoard.getCell(8);
        Cell B3 = gameBoard.getCell(7);//workerPos
        B4.addLevel();
        B4.addLevel();
        B4.addLevel();
        worker1.changePosition(B3);
        EndPhase endPhase = new EndPhase();
        endPhase.Init(worker1, gameBoard, false);
        poseidonDecoratedPhase = new PoseidonDecorator(endPhase, false);
        poseidonDecoratedPhase.performActionOnCell(B4);
        assertTrue(B4.checkDome()==true);
    }
}