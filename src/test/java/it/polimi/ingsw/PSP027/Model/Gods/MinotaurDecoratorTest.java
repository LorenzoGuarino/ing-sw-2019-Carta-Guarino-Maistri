package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Controller.MovePhase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author danielecarta
 */

public class MinotaurDecoratorTest {
    Player player1,player2;
    Worker worker11,worker21;
    MinotaurDecorator minotaurDecoratedPhase;
    Board gameBoard;

    @Before
    public void setUp() throws Exception {
        player1= new Player();
        player2= new Player();
        worker11= player1.getPlayerWorkers().get(0);
        worker11.changePosition(gameBoard.getCell(0));
        worker21= player2.getPlayerWorkers().get(0);

    }

    /**
     * Test performed in order to see if the  minotaur decorated movePhase overridden changeCandidateCells
     * works properly
     */

    @Test
    public void changeCandidateMoves_withAnOpponentWorkerNeighboring(){
        Cell x21 = gameBoard.getCell(1);//opponentWorkerInNeighboringCell,i should be able to move there
        worker21.changePosition(gameBoard.getCell(1));
        Cell x12 = gameBoard.getCell(5);
        Cell x22 = gameBoard.getCell(6);
        x22.addLevel();x22.addLevel();
        MovePhase movePhase= new MovePhase(worker11,gameBoard);
        minotaurDecoratedPhase =new MinotaurDecorator(movePhase);
        minotaurDecoratedPhase.changeCandidateCells();
        ArrayList<Cell> expectedList= new ArrayList<Cell>();
        expectedList.add(x12);
        expectedList.add(x21);
        assertTrue(expectedList.containsAll(movePhase.getCandidateCells())&&movePhase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * Test performed in order to see if the  minotaur decorated movePhase overridden updateBoard
     * works properly
     */

    @Test
    public void updateBoard_pushingAnEnemyWorker(){

        Cell x21 = gameBoard.getCell(1);//opponentWorkerInNeighboringCell,i should be able to move there
        worker21.changePosition(gameBoard.getCell(1));
        Cell x31 = gameBoard.getCell(2);//will i be able to force the worker Onto a lv2 Cell?
        x31.addLevel();x31.addLevel();
        Cell x11 = gameBoard.getCell(0);

        MovePhase movePhase= new MovePhase(worker11,gameBoard);
        minotaurDecoratedPhase =new MinotaurDecorator(movePhase);
        minotaurDecoratedPhase.updateBoard(x21);
        assertFalse(x11.isOccupiedByWorker());
        assertEquals(worker11.getWorkerPosition(), x21);
        assertEquals(worker21.getWorkerPosition(), x31);
    }

    @After
    public void tearDown() throws Exception {
    }
}