package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Controller.BuildPhase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author danielecarta
 */

public class AtlasDecoratorTest {

    Board gameBoard;
    Player player1;
    Worker worker11;
    AtlasDecorator atlasDecoratedPhase;

    @Before
    public void setUp(){
        gameBoard = new Board();
        player1 = new Player();
        worker11 = player1.getPlayerWorkers().get(0);
    }

    /**
     * asserts that Atlas gets a standard candidate list of building cells given by the standard buildPhase
     */
    @Test
    public void changeCandidateCells() {
        Cell x15 = gameBoard.getCell(20);
        Cell x14 = gameBoard.getCell(15);//lv2
        x14.addLevel();x14.addLevel();
        Cell x25 = gameBoard.getCell(21);//workerPos
        worker11.changePosition(x25);
        Cell x24 = gameBoard.getCell(16);//lv3dome
        x24.addLevel();x24.addLevel();x24.addLevel();x24.addDome();
        Cell x34 = gameBoard.getCell(17);
        Cell x35 = gameBoard.getCell(22);
        x35.addDome();
        ArrayList<Cell> expectedList = new ArrayList<>();
        expectedList.add(x15);
        expectedList.add(x14);
        expectedList.add(x34);
        BuildPhase buildPhase = new BuildPhase(worker11,gameBoard);
        atlasDecoratedPhase = new AtlasDecorator(buildPhase);
        atlasDecoratedPhase.changeCandidateCells();
        assertTrue(expectedList.containsAll(buildPhase.getCandidateCells()) && buildPhase.getCandidateCells().containsAll(expectedList));
    }

    /**
     * asserts that Atlas is able to put a dome on a lv2 building
     */
    @Test
    public void updateBoard_buildingADomeOnLv2(){
        Cell x14 = gameBoard.getCell(15);//lv2
        x14.addLevel();x14.addLevel();
        Cell x25 = gameBoard.getCell(21);//workerPos
        worker11.changePosition(x25);

        BuildPhase buildPhase = new BuildPhase(worker11,gameBoard);
        atlasDecoratedPhase = new AtlasDecorator(buildPhase);
        atlasDecoratedPhase.updateBoard(x14);
        assertTrue(x14.getLevel()==2 && x14.checkDome());
    }

    /**
     * asserts that Atlas is able to put a dome on a lv1 building
     */
    @Test
    public void updateBoard_buildingADomeOnLv1(){
        Cell x14 = gameBoard.getCell(15);//lv1
        x14.addLevel();
        Cell x25 = gameBoard.getCell(21);//workerPos
        worker11.changePosition(x25);

        BuildPhase buildPhase = new BuildPhase(worker11,gameBoard);
        atlasDecoratedPhase = new AtlasDecorator(buildPhase);
        atlasDecoratedPhase.updateBoard(x14);
        assertTrue(x14.getLevel()==1 && x14.checkDome());
    }

    /**
     * asserts that Atlas is able to put a dome on a lv0 building
     */
    @Test
    public void updateBoard_buildingADomeOnLv0(){
        Cell x14 = gameBoard.getCell(15);//lv0
        Cell x25 = gameBoard.getCell(21);//workerPos
        worker11.changePosition(x25);

        BuildPhase buildPhase = new BuildPhase(worker11,gameBoard);
        atlasDecoratedPhase = new AtlasDecorator(buildPhase);
        atlasDecoratedPhase.updateBoard(x14);
        assertTrue(x14.getLevel()==0 && x14.checkDome());
    }
}