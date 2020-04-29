package it.polimi.ingsw.PSP027.Model.Game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author danielecarta
 */

public class CellTest {
    Cell cell;
    @Before
    public void setUp(){
        cell = new Cell(0);
    }
    @Test
    public void addLevel_3TimesOnSameCell() {
        cell.addLevel();
        cell.addLevel();
        cell.addLevel();
        assertTrue(cell.getLevel()==3);
        cell.addLevel();
        assertFalse(cell.addLevel());
    }

    @Test
    public void isAPerimeterCell_onSomeCriticalCells() {
        assertTrue(new Cell(1).isAPerimeterCell());
        assertTrue(new Cell(2).isAPerimeterCell());
        assertTrue(new Cell(9).isAPerimeterCell());
        assertTrue(new Cell(24).isAPerimeterCell());
        assertFalse(new Cell(6).isAPerimeterCell());
        assertFalse(new Cell(12).isAPerimeterCell());
    }

    @Test
    public void isOccupiedByWorker_anyWorker() {
        assertFalse(cell.isOccupiedByWorker());
        Player player= new Player();
        Worker worker= new Worker(player,0);
        worker.changePosition(cell);
        assertTrue(cell.isOccupiedByWorker());
    }

    @Test
    public void isOccupiedByOpponentWorker() {
        Player player= new Player();
        Player player2 = new Player();
        assertFalse(cell.isOccupiedByOpponentWorker(player2));
        Worker worker= new Worker(player,0);
        worker.changePosition(cell);
        assertTrue(cell.isOccupiedByOpponentWorker(player2));
    }

    @Test
    public void canALevelBeRemoved_onDomeAndOccupiedAndZero() {
        assertFalse(cell.canALevelBeRemoved());
        cell.addLevel();
        assertTrue(cell.canALevelBeRemoved());
        Player player = new Player();
        Worker worker = new Worker(player,0);
        worker.changePosition(cell);
        assertFalse(cell.canALevelBeRemoved());
        worker.changePosition(null);
        cell.addDome();
        assertFalse(cell.canALevelBeRemoved());
    }

    @Test
    public void canALevelBeAdded_onDomeAndOccupiedAndZero() {
        assertTrue(cell.canALevelBeAdded());
        cell.addLevel();
        assertTrue(cell.canALevelBeAdded());
        Player player = new Player();
        Worker worker = new Worker(player,0);
        worker.changePosition(cell);
        assertFalse(cell.canALevelBeAdded());
        worker.changePosition(null);
        cell.addDome();
        assertFalse(cell.canALevelBeAdded());
    }

    @Test
    public void removeLevel_onZeroAndOneAndDome() {
        cell.removeLevel();
        assertEquals(0,cell.getLevel());
        cell.addLevel();cell.addLevel();
        assertEquals(2,cell.getLevel());
        cell.addDome();
        assertEquals(2,cell.getLevel());
    }

    @Test
    public void addDome() {
        cell.addLevel();
        cell.addLevel();
        cell.addLevel();
        cell.addDome();
        assertTrue(cell.getLevel()==3&&cell.checkDome());
        assertFalse(cell.addDome());
    }

    @Test
    public void resetCell_onAnOccupiedLv2Cell() {
        Player player = new Player();
        Worker worker = new Worker(player,0);
        cell.addLevel();cell.addLevel();worker.changePosition(cell);
        cell.resetCell();
        assertFalse(cell.checkDome());
        assertEquals(0,cell.getLevel());
        assertEquals(null,cell.getOccupyingWorker());
    }
}