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
    public void addLevel() {
        cell.addLevel();
        cell.addLevel();
        cell.addLevel();
        assertTrue(cell.getLevel()==3);
    }

    @Test
    public void addDome() {
        cell.addLevel();
        cell.addLevel();
        cell.addLevel();
        cell.addDome();
        assertTrue(cell.getLevel()==3&&cell.checkDome());
    }
}