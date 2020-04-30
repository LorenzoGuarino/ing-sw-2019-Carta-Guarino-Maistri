package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Controller.BuildPhase;
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
 * @author Elisa Maistri
 */

public class ZeusDecoratorTest {

    Player player;
    Worker worker1,worker2;
    ZeusDecorator zeusDecoratedPhase;
    Board gameBoard;

    @Before
    public void setUp() throws Exception {
        gameBoard = new Board();
        player = new Player();
        worker1= player.getPlayerWorkers().get(0);
        worker2 = player.getPlayerWorkers().get(1);
    }

    /**
     * Test should assert that if the player's worker's cell has a level that is under 3 the cell will be in the candidate cells
     */

    @Test
    public void evalCandidateCells_ShouldReturnAlsoTheCellOccupiedByZeusIfLevelIsUnder3() {
        Cell C3 = gameBoard.getCell(12); //Player with Zeus should be able to build there -> worker1 is on C3
        worker1.changePosition(C3);
        C3.addLevel();
        C3.addLevel(); //C3 now has a level 2 built, Zeus should still be able to build there
        BuildPhase buildPhase = new BuildPhase();
        buildPhase.Init(worker1, gameBoard, true);
        zeusDecoratedPhase = new ZeusDecorator(buildPhase, false);
        zeusDecoratedPhase.evalCandidateCells();
        ArrayList<Cell> expectedList = new ArrayList<Cell>();
        expectedList.add(C3);
        expectedList.add(gameBoard.getCell(6));
        expectedList.add(gameBoard.getCell(7));
        expectedList.add(gameBoard.getCell(8));
        expectedList.add(gameBoard.getCell(11));
        expectedList.add(gameBoard.getCell(13));
        expectedList.add(gameBoard.getCell(16));
        expectedList.add(gameBoard.getCell(17));
        expectedList.add(gameBoard.getCell(18));

        assertTrue(expectedList.containsAll(buildPhase.getCandidateCells()) && buildPhase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * Test should assert that among the candidate cells there is not the cell occupied by the player's worker
     * if this is at level 3
     */

    @Test
    public void evalCandidateCells_ShouldNotReturnTheCellOccupiedByZeusIfLevelIs3() {
        Cell C3 = gameBoard.getCell(12); //Player with Zeus should be able to build there -> worker1 is on C3
        C3.addLevel();
        C3.addLevel();
        C3.addLevel();
        //C3 now has a level 3 built, Zeus should NOT be able to build there
        worker1.changePosition(C3);
        BuildPhase buildPhase = new BuildPhase();
        buildPhase.Init(worker1, gameBoard, true);
        zeusDecoratedPhase = new ZeusDecorator(buildPhase, false);
        zeusDecoratedPhase.evalCandidateCells();
        ArrayList<Cell> expectedList = new ArrayList<Cell>();
        expectedList.add(gameBoard.getCell(6));
        expectedList.add(gameBoard.getCell(7));
        expectedList.add(gameBoard.getCell(8));
        expectedList.add(gameBoard.getCell(11));
        expectedList.add(gameBoard.getCell(13));
        expectedList.add(gameBoard.getCell(16));
        expectedList.add(gameBoard.getCell(17));
        expectedList.add(gameBoard.getCell(18));

        assertTrue(expectedList.containsAll(buildPhase.getCandidateCells()) && buildPhase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * Test should assert that the player having Zeus can build under its worker
     */

    @Test
    public void performActionOnCellShouldBuildALevelUnderTheWorker() {
        Cell A3 = gameBoard.getCell(2);
        worker1.changePosition(A3);
        BuildPhase buildPhase = new BuildPhase();
        buildPhase.Init(worker1, gameBoard, true);
        zeusDecoratedPhase = new ZeusDecorator(buildPhase, false);
        zeusDecoratedPhase.performActionOnCell(A3);
        int A3Level = A3.getLevel();
        assertEquals(1, A3Level);
    }

    /**
     * Test should assert that the player having Zeus makes a standard build of level when choosing a different
     * cell from the one its worker is occupying
     */

    @Test
    public void performActionOnCellShouldMakeAStandardBuildOfLevel() {
        Cell A3 = gameBoard.getCell(2);
        worker1.changePosition(A3);
        Cell A4 = gameBoard.getCell(3);
        A4.addLevel(true);
        BuildPhase buildPhase = new BuildPhase();
        buildPhase.Init(worker1, gameBoard, true);
        zeusDecoratedPhase = new ZeusDecorator(buildPhase, false);
        zeusDecoratedPhase.performActionOnCell(A4);
        int A4Level = A4.getLevel();
        assertEquals(2, A4Level);
    }

    /**
     * Test should assert that the player having Zeus makes a standard build of dome when choosing a different
     * cell from the one its player is occupying with level 3 already built
     */

    @Test
    public void performActionOnCellShouldMakeAStandardBuildOfDome() {
        Cell A3 = gameBoard.getCell(2);
        worker1.changePosition(A3);
        Cell A4 = gameBoard.getCell(3);
        A4.addLevel(true);
        A4.addLevel(true);
        A4.addLevel(true);
        BuildPhase buildPhase = new BuildPhase();
        buildPhase.Init(worker1, gameBoard, true);
        zeusDecoratedPhase = new ZeusDecorator(buildPhase, false);
        zeusDecoratedPhase.performActionOnCell(A4);
        boolean A4dome = A4.checkDome();
        assertEquals(true, A4dome);
    }

}