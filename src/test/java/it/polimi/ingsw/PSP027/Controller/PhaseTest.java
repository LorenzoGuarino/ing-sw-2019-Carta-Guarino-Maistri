package it.polimi.ingsw.PSP027.Controller;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Elisa Maistri
 */

public class PhaseTest {

    Player player;
    Worker worker1,worker2;
    Phase phase;
    Board gameBoard;

    @Before
    public void setUp() throws Exception {
        gameBoard = new Board();
        player = new Player();
        worker1 = player.getPlayerWorkers().get(0);
        worker2 = player.getPlayerWorkers().get(1);
    }

    /**
     * Test should assert that a player cannot win if its chosenworker is null
     */

    @Test
    public void playerHasWonShouldReturnFalseWhenChosenWorkerIsNotNull() {
        worker1 = null;
        phase = new Phase();
        phase.Init(worker1, gameBoard, true);
        boolean playerHasWon = phase.PlayerHasWon();
        assertTrue(false == playerHasWon);
    }

    /**
     * Test should assert that the player wins if its worker moves from a level 2 to a level 3
     */

    @Test
    public void playerHasWonShouldReturnTrueWhenChosenWorkerMovedFromLevel2ToLevel3() {
        phase = new Phase();
        phase.Init(worker1, gameBoard, true);
        Cell A2 = gameBoard.getCell(1);
        A2.addLevel();
        A2.addLevel();
        phase.getWorker().changePosition(A2);
        Cell A3 = gameBoard.getCell(2);
        A3.addLevel();
        A3.addLevel();
        A3.addLevel();
        phase.getWorker().changePosition(A3);
        boolean playerHasWon = phase.PlayerHasWon();
        assertTrue(true == playerHasWon);
    }

    /**
     * Test should assert that the player doesn't win if its worker moves from a level 1 to a level 2
     */

    @Test
    public void playerHasWonShouldReturnFalseWhenChosenWorkerMovedFromLevel1ToLevel2() {
        phase = new Phase();
        phase.Init(worker1, gameBoard, true);
        Cell A2 = gameBoard.getCell(1);
        A2.addLevel();
        phase.getWorker().changePosition(A2);
        Cell A3 = gameBoard.getCell(2);
        A3.addLevel();
        A3.addLevel();
        phase.getWorker().changePosition(A3);
        boolean playerHasWon = phase.PlayerHasWon();
        assertTrue(false == playerHasWon);
    }
}