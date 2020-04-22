package it.polimi.ingsw.PSP027.Model.Gods;

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
 */
public class ArtemisDecoratorTest {

    Board gameBoard;
    Player player1;
    Player player2;
    Worker worker11;
    Worker worker21;
    ArtemisDecorator artemisDecoratedPhase;

    @Before
    public void setUp() {
        gameBoard = new Board();
        player1 = new Player();
        player2 = new Player();
        worker11 = player1.getPlayerWorkers().get(0);
        worker21 = player2.getPlayerWorkers().get(0);
    }

    /**
     * asserts that the player gets a standard candidate cells list to move
     */
    @Test
    public void evalCandidateCells_standard(){
        Cell x11 = gameBoard.getCell(0);
        worker11.changePosition(x11);
        Cell x21 = gameBoard.getCell(1);
        worker21.changePosition(x21);
        Cell x12 = gameBoard.getCell(5);
        Cell x22 = gameBoard.getCell(6);
        x22.addLevel();x22.addLevel();
        MovePhase movePhase= new MovePhase();
        movePhase.Init(worker11,gameBoard, true);
        artemisDecoratedPhase = new ArtemisDecorator(movePhase, false);
        artemisDecoratedPhase.evalCandidateCells();
        ArrayList<Cell> expectedList= new ArrayList<Cell>();
        expectedList.add(x12);
        assertTrue(expectedList.containsAll(movePhase.getCandidateCells())&&movePhase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * asserts that the first updateBoard call is a standard move action
     */
    @Test
    public void updateBoard_moveOnce(){
        Cell x11 = gameBoard.getCell(0);
        worker11.changePosition(x11);
        Cell x21 = gameBoard.getCell(1);
        worker21.changePosition(x21);
        Cell x12 = gameBoard.getCell(5);
        Cell x22 = gameBoard.getCell(6);
        x22.addLevel();x22.addLevel();
        MovePhase movePhase= new MovePhase();
        movePhase.Init(worker11,gameBoard, true);
        artemisDecoratedPhase = new ArtemisDecorator(movePhase, false);
        artemisDecoratedPhase.evalCandidateCells();
        artemisDecoratedPhase.performActionOnCell(x12);
        assertTrue(worker11.getWorkerPosition().equals(x12));
    }

    /**
     * asserts that after the first move my candidate cells list updates according to my move,allowing to make another one
     */
    @Test
    public void evalCandidateCells_afterFirstUpdate(){
        Cell x11 = gameBoard.getCell(0);
        worker11.changePosition(x11);
        Cell x21 = gameBoard.getCell(1);
        worker21.changePosition(x21);
        Cell x12 = gameBoard.getCell(5);
        Cell x22 = gameBoard.getCell(6);
        x22.addLevel();x22.addLevel();
        Cell x13 = gameBoard.getCell(10);
        Cell x23 = gameBoard.getCell(11);

        MovePhase movePhase= new MovePhase();
        movePhase.Init(worker11,gameBoard, true);
        artemisDecoratedPhase = new ArtemisDecorator(movePhase, false);
        artemisDecoratedPhase.evalCandidateCells();
        artemisDecoratedPhase.performActionOnCell(x12);
        ArrayList<Cell> expectedList= new ArrayList<Cell>();
        expectedList.add(x13);
        expectedList.add(x23);
        assertTrue(expectedList.containsAll(movePhase.getCandidateCells())&&movePhase.getCandidateCells().containsAll(expectedList));
    }
}