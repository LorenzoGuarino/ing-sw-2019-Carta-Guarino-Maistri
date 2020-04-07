package it.polimi.ingsw.PSP027.Model;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Model.Gods.ApolloDecorator;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.ConcreteTurn;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.MovePhase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author danielecarta
 */

public class ApolloDecoratorTest {
    SantoriniMatch santoriniMatch;
    ConcreteTurn turn;
    Player player1,player2;
    Worker worker11,worker21;
    ApolloDecorator apolloDecoratoratedTurn;
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

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void applyPower_withNoMovePhaseToDecorate_shouldKeepPowerToggledFalse() {
        apolloDecoratoratedTurn=new ApolloDecorator(turn);
        turn.applyPower();
        assertFalse(apolloDecoratoratedTurn.powerToggled);
    }

    @Test
    public void applyPower_withMovePhaseToDecorate_shouldTurnPowerToggledOnAndSetItselfAsActualDecorator(){
        apolloDecoratoratedTurn=new ApolloDecorator(turn);
        MovePhase movePhase= new MovePhase(worker11,gameBoard);
        turn.getPhaseList().add(movePhase);
        apolloDecoratoratedTurn.applyPower();
        assertTrue(apolloDecoratoratedTurn.powerToggled);
        assertEquals(apolloDecoratoratedTurn,movePhase.getActualDecorator());
    }

    @Test
    public void changeCandidateMoves_calledFromADecoratedMovePhaseWithAnOpponentWorkerNeighboringMe(){
        apolloDecoratoratedTurn=new ApolloDecorator(turn);
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
        apolloDecoratoratedTurn.applyPower();
        assertTrue(expectedList.containsAll(movePhase.getCandidateMoves())&&movePhase.getCandidateMoves().containsAll(expectedList));
    }

    @Test
    public void updateBoard_calledFromTheDecorator(){
        apolloDecoratoratedTurn=new ApolloDecorator(turn);
        Cell x21 = gameBoard.getCell(1);//opponentWorkerInNeighboringCell,i should be able to move there
        worker21.setPosition(gameBoard.getCell(1));
        Cell x11 = gameBoard.getCell(0);

        apolloDecoratoratedTurn.updateBoard(worker11,gameBoard,x21);
        assertTrue(x11.isOccupiedByWorker());
        assertEquals(worker11.getWorkerPosition(), x21);
        assertEquals(worker21.getWorkerPosition(), x11);
    }

    @Test
    public void updateBoard_calledFromAMovePhaseWhereAMoveHasToBePerformed(){
        apolloDecoratoratedTurn=new ApolloDecorator(turn);
        Cell x21 = gameBoard.getCell(1);//opponentWorkerInNeighboringCell,i should be able to move there
        worker21.setPosition(gameBoard.getCell(1));
        Cell x11 = gameBoard.getCell(0);

        MovePhase movePhase= new MovePhase(worker11,gameBoard);
        turn.getPhaseList().add(movePhase);
        apolloDecoratoratedTurn.applyPower();
        movePhase.updateBoard(movePhase.getChosenWorker(),movePhase.getGameBoard(),x21);
        assertTrue(x11.isOccupiedByWorker());
        assertEquals(worker11.getWorkerPosition(), x21);
        assertEquals(worker21.getWorkerPosition(), x11);
    }
}