package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Controller.BuildPhase;
import it.polimi.ingsw.PSP027.Controller.EndPhase;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Model.Game.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Elisa Maistri
 */

public class GodPowerDecoratorTest {

    private Phase decoratedPhase ;
    private boolean bActAsOpponentGod;
    Player player;
    Worker worker1,worker2;
    ZeusDecorator zeusDecoratedPhase;
    Board gameBoard;
    boolean bCanPerformPhase;

    @Before
    public void setUp() throws Exception {
        gameBoard = new Board();
        player = new Player();
        worker1= player.getPlayerWorkers().get(0);
        worker2 = player.getPlayerWorkers().get(1);
    }

    /**
     * Test should assert that if the list of candidate cells is empty startPhase returns false
     */

    @Test
    public void startPhaseShouldReturnFalseWhenCandidateCellsListIsEmpty() {
        //test done with Zeus
        Cell A1 = gameBoard.getCell(0);
        Cell A2 = gameBoard.getCell(1);
        Cell B1 = gameBoard.getCell(5);
        Cell B2 = gameBoard.getCell(6);
        A1.addLevel(true);
        A1.addLevel(true);
        A1.addLevel(true);
        A2.addDome();
        B1.addDome();
        B2.addDome();
        worker1.changePosition(A1);
        BuildPhase buildPhase = new BuildPhase();
        buildPhase.Init(worker1, gameBoard, true);
        zeusDecoratedPhase = new ZeusDecorator(buildPhase, false);
        bCanPerformPhase = zeusDecoratedPhase.startPhase();
        assertEquals(false, bCanPerformPhase);
    }

}