package it.polimi.ingsw.PSP027.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void resettingBoardShouldResetTheBoard() {

        Board actualboard = new Board();
        actualboard.getCell(0).AddDome();
        actualboard.getCell(1).addLevel();
        actualboard.getCell(2).addLevel();
        actualboard.getCell(3).AddDome();
        actualboard.getCell(4).AddDome();
        actualboard.getCell(5).AddDome();
        actualboard.getCell(6).addLevel();
        actualboard.getCell(7).AddDome();
        actualboard.getCell(8).AddDome();
        actualboard.getCell(9).addLevel();
        actualboard.getCell(10).AddDome();
        actualboard.getCell(11).AddDome();
        actualboard.getCell(12).AddDome();
        actualboard.getCell(13).addLevel();
        actualboard.getCell(14).AddDome();
        actualboard.getCell(15).AddDome();
        actualboard.getCell(16).addLevel();
        actualboard.getCell(17).addLevel();
        actualboard.getCell(18).AddDome();
        actualboard.getCell(19).addLevel();
        actualboard.getCell(20).addLevel();
        actualboard.getCell(21).addLevel();
        actualboard.getCell(22).AddDome();
        actualboard.getCell(23).addLevel();
        actualboard.getCell(24).AddDome();
        actualboard.resetBoard();

        Board expectedboard = new Board();

        assertSame(expectedboard, actualboard);
    }

    @Test
    public void getCell() {
    }

    @Test
    public void getNextCellAlongThePath() {
    }

    @Test
    public void getNeighbouringCells() {
    }
}