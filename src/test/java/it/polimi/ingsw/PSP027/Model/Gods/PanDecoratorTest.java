package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Controller.MovePhase;
import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PanDecoratorTest {
    Board gameBoard;
    Player player, player2;
    Worker worker1, worker2, worker21;
    Cell cella1, cella2, cella3, cella4, cella5, cella6;
    PanDecorator panDecoratedPhase;
    @Before
    public void setUp() throws Exception {
        gameBoard = new Board();
        player = new Player();
        player2 = new Player();
        worker21 = player2.getPlayerWorkers().get(0);
        worker1 = player.getPlayerWorkers().get(0);
        worker2 = player.getPlayerWorkers().get(1);
        cella1 = gameBoard.getCell(12);
        cella2 = gameBoard.getCell(13);
        cella3 = gameBoard.getCell(2);
        cella4 = gameBoard.getCell(3);
        cella5 = gameBoard.getCell(22);
        cella6 = gameBoard.getCell(23);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testplayerHasWon() {
        cella1.addLevel();
        cella1.addLevel();
        worker1.changePosition(cella1);
        worker1.changePosition(cella2);
        MovePhase movePhase = new MovePhase();
        movePhase.Init(worker1, gameBoard, true);
        panDecoratedPhase = new PanDecorator(movePhase, false);
        assertTrue(panDecoratedPhase.PlayerHasWon());
    }

    @Test
    public void testPlayerHasNotWon(){
        cella3.addLevel();
        cella4.addLevel();
        cella3.addLevel();
        cella3.addLevel();
        cella4.addLevel();
        worker2.changePosition(cella3);
        worker2.changePosition(cella4);
        MovePhase movePhase = new MovePhase();
        movePhase.Init(worker2, gameBoard, true);
        panDecoratedPhase = new PanDecorator(movePhase, false);
        assertFalse(panDecoratedPhase.PlayerHasWon());
    }

    @Test
    public void testNormalWin(){
        cella5.addLevel();
        cella5.addLevel();
        cella6.addLevel();
        cella6.addLevel();
        cella6.addLevel();
        worker21.changePosition(cella5);
        worker21.changePosition(cella6);
        MovePhase movePhase = new MovePhase();
        movePhase.Init(worker21, gameBoard, true);
        panDecoratedPhase = new PanDecorator(movePhase, false);
        assertTrue(panDecoratedPhase.PlayerHasWon());
    }
}