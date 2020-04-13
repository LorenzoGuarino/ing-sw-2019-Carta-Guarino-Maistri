package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.*;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.MovePhase;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 * @author danielecarta
 */

public class AthenaDecoratorTest {

    Player player1,player2,player3;
    Worker worker11,worker12,worker21,worker22,worker31,worker32;
    Board gameBoard;

    @Before
    public void setUp(){
        player1=new Player();player2=new Player();player3=new Player();
        gameBoard=new Board();
        worker11=new Worker(player1,0);worker12=new Worker(player1,1);
        worker21=new Worker(player2,0);worker22=new Worker(player2,1);
        worker31=new Worker(player3,0);worker32=new Worker(player3,1);
    }

    /**
     * asserts that athena moves normally
     */
    @Test
    public void updateBoard_checkingMovement(){
        Cell x12 = gameBoard.getCell(5);worker11.changePosition(x12);//w11 pos
        Cell x22 = gameBoard.getCell(6);

        MovePhase movePhase = new MovePhase(worker11,gameBoard);
        AthenaDecorator athenaDecoratedPhase = new AthenaDecorator(movePhase);
        athenaDecoratedPhase.updateBoard(x22);
        assertTrue(worker11.getWorkerPosition().equals(x22) && x12.getOccupyingWorker()==null);
    }

    /**
     * asserts that performing a move onto an upper level allows athena to add "athenaOpp" god kind at each opponents' players' godsLists
     */
    @Test
    public void applyPowerOnOtherPlayers_effectivelyActivatedMovingUp() {
        Cell x12 = gameBoard.getCell(5);worker11.changePosition(x12);//w11 pos
        Cell x13 = gameBoard.getCell(10);worker21.changePosition(x13);//w21 pos
        Cell x11 = gameBoard.getCell(0);worker31.changePosition(x11);//w31 pos
        Cell x25 = gameBoard.getCell(21);worker12.changePosition(x25);//w12 pos
        Cell x35 = gameBoard.getCell(22);worker22.changePosition(x35);//w22 pos
        Cell x34 = gameBoard.getCell(17);worker32.changePosition(x34);//w32 pos
        Cell x22 = gameBoard.getCell(6);x22.addLevel();

        MovePhase movePhase = new MovePhase(worker11,gameBoard);
        AthenaDecorator athenaDecoratedPhase = new AthenaDecorator(movePhase);
        athenaDecoratedPhase.updateBoard(x22);
        assertTrue(player2.getOpponentsGodCards().get(0).getGodType().equals(GodCard.GodsType.AthenaOpp));
        assertTrue(player3.getOpponentsGodCards().get(0).getGodType().equals(GodCard.GodsType.AthenaOpp));
    }

    /**
     * asserts that when athena does not move on an upper level she does nothing relating to her power
     */
    @Test
    public void applyPowerOnOtherPlayers_notMovingUpSoItDoesNothing() {
        Cell x12 = gameBoard.getCell(5);worker11.changePosition(x12);//w11 pos
        Cell x13 = gameBoard.getCell(10);worker21.changePosition(x13);//w21 pos
        Cell x11 = gameBoard.getCell(0);worker31.changePosition(x11);//w31 pos
        Cell x25 = gameBoard.getCell(21);worker12.changePosition(x25);//w12 pos
        Cell x35 = gameBoard.getCell(22);worker22.changePosition(x35);//w22 pos
        Cell x34 = gameBoard.getCell(17);worker32.changePosition(x34);//w32 pos
        Cell x22 = gameBoard.getCell(6);

        MovePhase movePhase = new MovePhase(worker11,gameBoard);
        AthenaDecorator athenaDecoratedPhase = new AthenaDecorator(movePhase);
        athenaDecoratedPhase.updateBoard(x22);
        assertEquals(0, player2.getOpponentsGodCards().size());
        assertEquals(0, player3.getOpponentsGodCards().size());
    }
}