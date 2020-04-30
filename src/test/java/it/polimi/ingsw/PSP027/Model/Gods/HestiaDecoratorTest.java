package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Controller.BuildPhase;
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

public class HestiaDecoratorTest {

    Player player;
    Worker worker1,worker2;
    HestiaDecorator hestiaDecoratedPhase;
    Board gameBoard;

    @Before
    public void setUp() throws Exception {
        gameBoard = new Board();
        player = new Player();
        worker1= player.getPlayerWorkers().get(0);
        worker2 = player.getPlayerWorkers().get(1);
    }

    /**
     * Test should assert that the candidate cells don't inlcude a perimeter cell
     */

    @Test
    public void evalCandidateCellsShouldNotReturnPerimeterCells() {
        Cell B3 = gameBoard.getCell(7); //Player with Zeus should be able to build there -> worker1 is on C3
        worker1.changePosition(B3);
        worker1.IncrementBuildCounter();
        BuildPhase buildPhase = new BuildPhase();
        buildPhase.Init(worker1, gameBoard, false);
        hestiaDecoratedPhase = new HestiaDecorator(buildPhase, false);
        hestiaDecoratedPhase.evalCandidateCells();
        ArrayList<Cell> expectedList = new ArrayList<Cell>();
        expectedList.add(gameBoard.getCell(6));
        expectedList.add(gameBoard.getCell(8));
        expectedList.add(gameBoard.getCell(11));
        expectedList.add(gameBoard.getCell(12));
        expectedList.add(gameBoard.getCell(13));

        assertTrue(expectedList.containsAll(buildPhase.getCandidateCells()) && buildPhase.getCandidateCells().containsAll(expectedList));

    }

    /**
     * Test should assert the the build on a candidate cell (candidate cells of evalCandidateCells) should perform a standard build
     */

    @Test
    public void performActionOnCellShouldMakeAStandardBuild() {
        Cell B4 = gameBoard.getCell(8);
        Cell B3 = gameBoard.getCell(7);//workerPos
        worker1.changePosition(B3);
        Cell x34 = gameBoard.getCell(17);
        BuildPhase buildPhase = new BuildPhase();
        buildPhase.Init(worker1, gameBoard, false);
        hestiaDecoratedPhase = new HestiaDecorator(buildPhase, false);
        hestiaDecoratedPhase.performActionOnCell(B4);
        assertTrue(B4.getLevel()==1);
    }
}