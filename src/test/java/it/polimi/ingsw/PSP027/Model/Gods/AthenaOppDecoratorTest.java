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

public class AthenaOppDecoratorTest {
    Player player1,player2;
    Worker worker11,worker21;
    Board gameBoard;
    AthenaOppDecorator athenaOppDecoratedPhase;
    ApolloDecorator apolloDecoratedPhase;

    @Before
    public void setUp(){
        player1= new Player();player2=new Player();
        worker11= new Worker(player1,0); worker21= new Worker(player2,0);
        gameBoard=new Board();
    }

    /**
     * evalCandidateCells on a phase that has not been decorated yet,it asserts that any move that allowed me to go
     * 1 lv higher is no longer in the candidateCells list
     */
    @Test
    public void evalCandidateCells_onAPhaseUnmodified() {
        Cell x11 = gameBoard.getCell(0);//myPos
        worker11.changePosition(x11);
        Cell x21 = gameBoard.getCell(1);//opponentWorkerPos
        worker21.changePosition(x21);
        Cell x12 = gameBoard.getCell(5);//stdCell
        Cell x22 = gameBoard.getCell(6);//lv1 cell, i cant move there
        x22.addLevel();
        MovePhase movePhase= new MovePhase();
        movePhase.Init(worker11,gameBoard);
        athenaOppDecoratedPhase = new AthenaOppDecorator(movePhase, true);
        athenaOppDecoratedPhase.evalCandidateCells();
    }

    /**
     * evalCandidateCells on a phase that has been decorated by an apollo decorator,it asserts that i get the candidate cells list
     * according both to athena and apollo s decorators, in the case im able to swap position with an enemy worker
     */
    @Test
    public void evalCandidateCells_onAPhaseModifiedByApolloThatIsAbleToUseHisPower() {
        Cell x11 = gameBoard.getCell(0);//myPos
        worker11.changePosition(x11);
        Cell x21 = gameBoard.getCell(1);//opponentWorkerPosBut i have apollo
        worker21.changePosition(x21);
        Cell x12 = gameBoard.getCell(5);//stdCell
        Cell x22 = gameBoard.getCell(6);//lv1 cell, i cant move there
        x22.addLevel();
        MovePhase movePhase= new MovePhase();
        movePhase.Init(worker11,gameBoard);
        apolloDecoratedPhase = new ApolloDecorator(movePhase, true);
        apolloDecoratedPhase.evalCandidateCells();
        athenaOppDecoratedPhase = new AthenaOppDecorator(movePhase, true);
        athenaOppDecoratedPhase.evalCandidateCells();
        ArrayList<Cell> expectedList= new ArrayList<Cell>();
        expectedList.add(x12);
        expectedList.add(x21);
        assertTrue(expectedList.containsAll(movePhase.getCandidateCells())&&movePhase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * evalCandidateCells on a phase that has been decorated by an apollo decorator,it asserts that i get the candidate cells list
     * according both to athena and apollo s decorators, in the case im able to swap position with an enemy worker but im not able to
     * because he's 1 lv higher
     */
    @Test
    public void evalCandidateCells_onAPhaseModifiedByApolloThatsUnableToUseHisPower() {
        Cell x11 = gameBoard.getCell(0);//myPos
        worker11.changePosition(x11);
        Cell x21 = gameBoard.getCell(1);//opponentWorkerPosBut i have apollo but Athena stops me anyway
        x21.addLevel();
        worker21.changePosition(x21);
        Cell x12 = gameBoard.getCell(5);//stdCell
        Cell x22 = gameBoard.getCell(6);//lv1 cell, i cant move there
        x22.addLevel();
        MovePhase movePhase= new MovePhase();
        movePhase.Init(worker11,gameBoard);
        apolloDecoratedPhase = new ApolloDecorator(movePhase, true);
        apolloDecoratedPhase.evalCandidateCells();
        athenaOppDecoratedPhase = new AthenaOppDecorator(movePhase, true);
        athenaOppDecoratedPhase.evalCandidateCells();
        ArrayList<Cell> expectedList= new ArrayList<Cell>();
        expectedList.add(x12);
        assertTrue(expectedList.containsAll(movePhase.getCandidateCells())&&movePhase.getCandidateCells().containsAll(expectedList));
    }

    @Test
    public void updateBoard_performsMoveCorrectly() {
        Cell x11 = gameBoard.getCell(0);//myPos
        worker11.changePosition(x11);
        Cell x21 = gameBoard.getCell(1);//opponentWorkerPosBut i have apollo but Athena stops me anyway
        x21.addLevel();
        worker21.changePosition(x21);
        Cell x12 = gameBoard.getCell(5);//stdCell
        Cell x22 = gameBoard.getCell(6);//lv1 cell, i cant move there
        x22.addLevel();
        MovePhase movePhase= new MovePhase();
        movePhase.Init(worker11,gameBoard);
        apolloDecoratedPhase = new ApolloDecorator(movePhase, true);
        apolloDecoratedPhase.evalCandidateCells();
        athenaOppDecoratedPhase = new AthenaOppDecorator(movePhase, true);
        movePhase.performActionOnCell(x12);
        apolloDecoratedPhase.performActionOnCell(x12);
        assertTrue(worker11.getWorkerPosition().equals(x12));
    }

}