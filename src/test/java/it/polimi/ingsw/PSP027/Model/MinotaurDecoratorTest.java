package it.polimi.ingsw.PSP027.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MinotaurDecoratorTest {
    SantoriniMatch santoriniMatch;
    ConcreteTurn turn;
    Player player1,player2;
    Worker worker11,worker21;
    MinotaurDecorator minotaurDecoratoratedTurn;
    Board gameBoard;
    @Before
    public void setUp() throws Exception {
        santoriniMatch= new SantoriniMatch();
        gameBoard= santoriniMatch.getGameBoard();
        player1= new Player();
        player2= new Player();
        santoriniMatch.addPlayer(player1);
        santoriniMatch.addPlayer(player2);
        worker11= player1.getPlayerWorkers().get(0);
        worker11.setPosition(gameBoard.getCell(0));
        worker21= player2.getPlayerWorkers().get(0);
        turn= new ConcreteTurn(player1,santoriniMatch);

    }

    @Test
    public void applyPower_withNoMovePhaseToDecorate_shouldKeepPowerToggledFalse(){
        minotaurDecoratoratedTurn=new MinotaurDecorator(turn);
        turn.applyPower();
        assertFalse(minotaurDecoratoratedTurn.powerToggled);
    }

    @Test
    public void applyPower_withMovePhaseToDecorate_shouldTurnPowerToggledOnAndSetItselfAsActualDecorator(){
        minotaurDecoratoratedTurn=new MinotaurDecorator(turn);
        MovePhase movePhase= new MovePhase(worker11,gameBoard);
        turn.getPhaseList().add(movePhase);
        minotaurDecoratoratedTurn.applyPower();
        assertTrue(minotaurDecoratoratedTurn.powerToggled);
        assertEquals(minotaurDecoratoratedTurn,movePhase.getActualDecorator());
    }

    @Test
    public void changeCandidateMoves_calledFromADecoratedMovePhaseWithAnOpponentWorkerNeighboringMe(){
        minotaurDecoratoratedTurn=new MinotaurDecorator(turn);
        Cell x21 = gameBoard.getCell(1);//opponentWorkerInNeighboringCell,i should be able to move there
        worker21.setPosition(gameBoard.getCell(1));
        Cell x12 = gameBoard.getCell(5);
        Cell x22 = gameBoard.getCell(6);
        x22.addLevel();x22.addLevel();
        MovePhase movePhase= new MovePhase(worker11,gameBoard);
        turn.getPhaseList().add(movePhase);
        ArrayList<Cell> expectedList= new ArrayList<Cell>();
        expectedList.add(x12);
        expectedList.add(x21);
        minotaurDecoratoratedTurn.applyPower();
        assertTrue(expectedList.containsAll(movePhase.getCandidateMoves())&&movePhase.getCandidateMoves().containsAll(expectedList));
    }

    @Test
    public void updateBoard_calledFromTheDecorator(){
        minotaurDecoratoratedTurn=new MinotaurDecorator(turn);
        Cell x21 = gameBoard.getCell(1);//opponentWorkerInNeighboringCell,i should be able to move there
        worker21.setPosition(gameBoard.getCell(1));
        Cell x31 = gameBoard.getCell(2);//will i be able to force the worker Onto a lv2 Cell?
        x31.addLevel();x31.addLevel();
        Cell x11 = gameBoard.getCell(0);

        minotaurDecoratoratedTurn.updateBoard(worker11,gameBoard,x21);
        assertFalse(x11.isOccupiedByWorker());
        assertEquals(worker11.getWorkerPosition(), x21);
        assertEquals(worker21.getWorkerPosition(), x31);
    }

    @Test
    public void updateBoard_calledFromAMovePhaseWhereAMoveHasToBePerformed(){
        minotaurDecoratoratedTurn=new MinotaurDecorator(turn);
        Cell x21 = gameBoard.getCell(1);//opponentWorkerInNeighboringCell,i should be able to move there
        worker21.setPosition(gameBoard.getCell(1));
        Cell x31 = gameBoard.getCell(2);//will i be able to force the worker Onto a lv2 Cell?
        x31.addLevel();x31.addLevel();
        Cell x11 = gameBoard.getCell(0);

        MovePhase movePhase= new MovePhase(worker11,gameBoard);
        turn.getPhaseList().add(movePhase);
        minotaurDecoratoratedTurn.applyPower();
        movePhase.updateBoard(movePhase.getChosenWorker(),movePhase.getGameBoard(),x21);
        assertFalse(x11.isOccupiedByWorker());
        assertEquals(worker11.getWorkerPosition(), x21);
        assertEquals(worker21.getWorkerPosition(), x31);
    }

    @After
    public void tearDown() throws Exception {
    }
}