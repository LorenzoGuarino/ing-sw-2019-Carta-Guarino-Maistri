package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.*;
import it.polimi.ingsw.PSP027.Controller.MovePhase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author danielecarta
 * @author Elisa Maistri
 */

public class AthenaDecoratorTest {

    Player player1, player2, player3;
    Worker worker11, worker12, worker21, worker22, worker31, worker32;
    Board gameBoard;

    @Before
    public void setUp(){
        player1 = new Player(); player2=new Player(); player3=new Player();
        gameBoard = new Board();
        worker11 = new Worker(player1,0); worker12 = new Worker(player1,1);
        worker21 = new Worker(player2,0); worker22 = new Worker(player2,1);
        worker31 = new Worker(player3,0); worker32 = new Worker(player3,1);
    }

    /**
     * asserts that athena moves normally
     */
    @Test
    public void updateBoard_checkingMovement(){
        Cell B1 = gameBoard.getCell(5); worker11.changePosition(B1);//w11 pos
        Cell B2 = gameBoard.getCell(6);

        MovePhase movePhase = new MovePhase();
        movePhase.Init(worker11, gameBoard, true);
        AthenaDecorator athenaDecoratedPhase = new AthenaDecorator(movePhase, false);
        athenaDecoratedPhase.performActionOnCell(B2);
        assertTrue(worker11.getWorkerPosition().equals(B2) && B1.getOccupyingWorker() == null);
    }

    /**
     * asserts that performing a move onto an upper level allows athena to add "athenaOpp" god kind at each opponents' players' godsLists
     */
    @Test
    public void applyPowerOnOtherPlayers_effectivelyActivatedMovingUp() {
        Cell B1 = gameBoard.getCell(5); worker11.changePosition(B1);//w11 pos
        Cell E4 = gameBoard.getCell(23); worker11.changePosition(E4);//w12 pos
        Cell E2 = gameBoard.getCell(21); worker21.changePosition(E2);//w21 pos
        Cell C4 = gameBoard.getCell(13); worker22.changePosition(C4);//w22 pos
        Cell A3 = gameBoard.getCell(2); worker31.changePosition(A3);//w31 pos
        Cell B5 = gameBoard.getCell(9); worker32.changePosition(B5);//w32 pos

        Cell B2 = gameBoard.getCell(6); B2.addLevel();

        MovePhase movePhase = new MovePhase();
        movePhase.Init(worker11, gameBoard, true);
        AthenaDecorator athenaDecoratedPhase = new AthenaDecorator(movePhase, false);
        athenaDecoratedPhase.performActionOnCell(B2);
    }

    /**
     * asserts that when athena does not move on an upper level she does nothing relating to her power
     */
    @Test
    public void applyPowerOnOtherPlayers_notMovingUpSoItDoesNothing() {
        Cell B1 = gameBoard.getCell(5); worker11.changePosition(B1);//w11 pos
        Cell E4 = gameBoard.getCell(23); worker11.changePosition(E4);//w12 pos
        Cell E2 = gameBoard.getCell(21); worker21.changePosition(E2);//w21 pos
        Cell C4 = gameBoard.getCell(13); worker22.changePosition(C4);//w22 pos
        Cell A3 = gameBoard.getCell(2); worker31.changePosition(A3);//w31 pos
        Cell B5 = gameBoard.getCell(9); worker32.changePosition(B5);//w32 pos
        Cell B2 = gameBoard.getCell(6);

        MovePhase movePhase = new MovePhase();
        movePhase.Init(worker11, gameBoard, true);
        AthenaDecorator athenaDecoratedPhase = new AthenaDecorator(movePhase, false);
        athenaDecoratedPhase.performActionOnCell(B2);
        assertEquals(0, player2.getOpponentsGodCards().size());
        assertEquals(0, player3.getOpponentsGodCards().size());
    }

    /**
     * Test asserts that when Athena acts as an opponent god it forbids the opponent workers to move up a level
     */

    @Test
    public void evalCandidateCellsOnOpponentRemovesCellsThatWouldMoveWorkerOnHigherLevel() {
        Cell B1 = gameBoard.getCell(5); worker11.changePosition(B1);//w11 pos
        Cell E4 = gameBoard.getCell(23); worker11.changePosition(E4);//w12 pos
        Cell E2 = gameBoard.getCell(21); worker21.changePosition(E2);//w21 pos
        Cell C4 = gameBoard.getCell(13); worker22.changePosition(C4);//w22 pos
        Cell A3 = gameBoard.getCell(2); worker31.changePosition(A3);//w31 pos
        Cell B5 = gameBoard.getCell(9); worker32.changePosition(B5);//w32 pos
        Cell B2 = gameBoard.getCell(6);  B2.addLevel();
        Cell E3 = gameBoard.getCell(22);  E3.addLevel();
        Cell E1 = gameBoard.getCell(20);
        Cell D1 = gameBoard.getCell(15);
        Cell D2 = gameBoard.getCell(16);
        Cell D3 = gameBoard.getCell(17);


        MovePhase movePhaseP1 = new MovePhase();
        movePhaseP1.Init(worker11, gameBoard, true);
        AthenaDecorator athenaDecoratedPhaseP1 = new AthenaDecorator(movePhaseP1, false);
        athenaDecoratedPhaseP1.performActionOnCell(B2);

        MovePhase movePhaseP2 = new MovePhase();
        movePhaseP2.Init(worker21, gameBoard, true);
        AthenaDecorator athenaDecoratedPhaseP2 = new AthenaDecorator(movePhaseP2, true);
        athenaDecoratedPhaseP2.evalCandidateCells();
        ArrayList<Cell> expectedList = new ArrayList<Cell>();
        expectedList.add(E1);
        expectedList.add(D1);
        expectedList.add(D2);
        expectedList.add(D3);
        assertTrue(expectedList.containsAll(athenaDecoratedPhaseP2.getCandidateCells()) && athenaDecoratedPhaseP2.getCandidateCells().containsAll(expectedList));

    }
}