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

    public Board() {
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

    /**
     * Method that return the cell of the board of a given index
     * @param index of the cell that need to be returned
     * @return the cell
     */

    public Cell getCell(int index) {
        return Board.get(index);
    }

    /**
     * Method that finds the neighbouring cells of a cell
     * @return a list containing the indexes of the neighbouring cells of the given cell
     */

    public List<Cell> getNeighbouringCells(Cell cell) {

        List<Cell> NeighbouringCells = new ArrayList<>();

        int[] Neighbours = {-6, -5, -4, -1, 1, 4, 5, 6};

        /* i = index of current cell
         *  j = index of neighbouring cell
         *  Ri = row of current cell
         *  Rj = row of neighbouring cell
         *  Ci = column of current cell
         *  Cj = column of neighbouring cell
         */

        int index = cell.getCellIndex();

        int j;
        boolean IsNeighbour;
        int Ri = index/5;
        int Rj;
        int Ci = index%5;
        int Cj;

        for (int k = 0; k < 8; k++) {
            j = index + Neighbours[k];
            IsNeighbour = false;

            if (j >= 0 && j < 25) {
                Rj = j/5;
                Cj = j%5;

                if((Ci == 0) || (Ci == 4)) {
                    if (Ri != Rj) {
                        IsNeighbour = ((Cj >= Ci-1) && (Cj <= (Ci+1)));
                    }
                    else {
                        if(Ci == 0 && (Cj <= (Ci+1))) {
                            IsNeighbour = true;
                        }
                        else if (Ci == 4 && (Cj >= (Ci-1))) {
                            IsNeighbour = true;
                        }
                    }
                }
                else {
                    IsNeighbour = true;
                }
            }

            if (IsNeighbour) {
                NeighbouringCells.add(getCell(index));
            }

            j = 0;
        }

        return NeighbouringCells;
    }

}
