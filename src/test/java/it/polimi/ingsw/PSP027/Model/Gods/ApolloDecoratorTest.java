package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Controller.SantoriniMatch;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.MovePhase;
import it.polimi.ingsw.PSP027.Controller.Turn;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author danielecarta
 */

public class ApolloDecoratorTest {
    SantoriniMatch santoriniMatch;
    Turn turn;
    Player player1,player2;
    Worker worker11,worker21;
    ApolloDecorator apolloDecoratedPhase;
    Board gameBoard;

    @Before
    public void setUp() {
        santoriniMatch= new SantoriniMatch();
        gameBoard= santoriniMatch.getGameBoard();
        player1= new Player();
        player2= new Player();
        santoriniMatch.addPlayer(player1);
        santoriniMatch.addPlayer(player2);
        worker11= player1.getPlayerWorkers().get(0);
        worker11.changePosition(gameBoard.getCell(0));
        worker21= player2.getPlayerWorkers().get(0);
        turn= new Turn(player1,santoriniMatch);
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
        apolloDecoratedPhase =new ApolloDecorator(movePhase);
        apolloDecoratedPhase.changeCandidateCells();
        ArrayList<Cell> expectedList= new ArrayList<Cell>();
        expectedList.add(x12);
        expectedList.add(x21);
        assertTrue(expectedList.containsAll(movePhase.getCandidateCells())&&movePhase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * Test performed in order to see if the  apollo decorated movePhase overridden updateBoard
     * works properly
     */

    @Test
    public void updateBoard_swappingPositionWithNeighbor(){
        Cell x21 = gameBoard.getCell(1);//opponentWorkerInNeighboringCell,i should be able to move there
        worker21.changePosition(gameBoard.getCell(1));
        Cell x11 = gameBoard.getCell(0);

        MovePhase movePhase= new MovePhase(worker11,gameBoard);
        apolloDecoratedPhase =new ApolloDecorator(movePhase);
        apolloDecoratedPhase =new ApolloDecorator(movePhase);
        apolloDecoratedPhase.updateBoard(x21);
        assertTrue(x11.isOccupiedByWorker());
        assertTrue(x21.isOccupiedByWorker());
        assertEquals(worker11.getWorkerPosition(), x21);
        assertEquals(worker21.getWorkerPosition(), x11);
    }
}