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

    @Test
    public void evalCandidateCells_ShouldNotReturnTheCellOccupiedByZeusIfLevelIs3OrDome() {
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
}