package it.polimi.ingsw.PSP027.Model.Game;

import it.polimi.ingsw.PSP027.Controller.MovePhase;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Lorenzo Guarino
 */
public class WorkerTest {
    Player player;
    Worker workertest, workerreset;
    Board gameboard;
    Cell cella, cella2, cella3;

    @Before
    public void setUp() throws Exception {
        player = new Player();
        workerreset = player.getPlayerWorkers().get(1);
        workertest = player.getPlayerWorkers().get(0);
        gameboard = new Board();
        cella = gameboard.getCell(5);
        cella2 = gameboard.getCell(6);
        cella3 = gameboard.getCell(12);
        workertest.changePosition(cella2);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testChangePosition(){
        workertest.changePosition(cella);
        assertEquals(workertest.getWorkerPosition().getCellIndex(), cella.getCellIndex());
        assertEquals(workertest.getWorkerPrevPosition().getCellIndex(), cella2.getCellIndex());
    }

    @Test
    public void testResetWorker(){
        workerreset.IncrementBuildCounter();
        workerreset.IncrementMoveCounter();
        assertEquals(1, workerreset.getBuildCounter());
        assertEquals(1, workerreset.getMoveCounter());
        workerreset.ResetWorkerTurnVars();
        assertEquals(0, workerreset.getMoveCounter());
        assertEquals(0, workerreset.getBuildCounter());
    }
    @Test
    public void testOldBuildCell(){
        workertest.setOldBuiltCell(cella);
        assertEquals(cella.getCellIndex(), workertest.getLastBuiltCell().getCellIndex());
    }

    @Test
    public void testWorkerOwnerAAndIndex(){
        assertEquals(0, workertest.getWorkerIndex());
        assertEquals(1, workerreset.getWorkerIndex());
        assertEquals(player.getNickname(), workertest.getWorkerOwner().getNickname());
        assertEquals(player.getNickname(), workerreset.getWorkerOwner().getNickname());
    }

}