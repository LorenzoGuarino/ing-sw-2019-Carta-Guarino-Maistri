package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Elisa Maistri
 */

public class Board {

    private List<Cell> Board;

    /**
     * Constructor: creates a new Board with new cells
     */

    public Board()
    {
        Board = new ArrayList<Cell>();

        for (int i = 0; i < 25; i++) Board.add(new Cell(i));
    }

    /**
     * Method that resets an already existing board
     */

    public void resetBoard()
    {
        for (Cell cellToReset : Board) cellToReset.resetCell();
    }

    /**
     * Method used to to get the Board
     * @return the board as a list of cells
     */

    public List<Cell> getBoard()
    {
        return Board;
    }

}
