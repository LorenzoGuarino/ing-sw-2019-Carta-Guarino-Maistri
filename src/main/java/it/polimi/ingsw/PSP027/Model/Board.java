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
     * @return the cell, or null if the index doesn't exists
     */

    public Cell getCell(int index) {
        if (index >= 0 && index <= 24) {
            return Board.get(index);
        }
        else {
            return null;
        }
    }

    /**
     * Method that, given two neighbouring cells calculates the next cell along the path made by the first two
     * @param firstPathCell the first cell from which the path is calculated
     * @param secondPathCell the second cell defining the path
     * @return the cell along the path, otherwise null if the cells given to the method were not neighbours
     */

    public Cell getNextCellAlongThePath(Cell firstPathCell, Cell secondPathCell)
    {
        Cell NextCell = null;

        int start = firstPathCell.getCellIndex();
        int end = secondPathCell.getCellIndex();
        int delta = end - start;
        int nextCellIndex = end + delta;

        if((nextCellIndex>=0) && (nextCellIndex<25))
        {
            int rn = nextCellIndex/5;
            int cn = nextCellIndex%5;
            int re = end/5;
            int ce = end%5;

            switch(delta)
            {
                case -1:
                case 1:
                    // same row
                    if(re == rn)
                        NextCell = getCell(nextCellIndex);
                    break;
                case -5:
                case 5:
                    // same column
                    if(ce == cn)
                        NextCell = getCell(nextCellIndex);
                    break;
                case -6:
                    if((cn == (ce-1)) && (rn == (re-1)))
                        NextCell = getCell(nextCellIndex);
                    break;
                case 6:
                    if((cn == (ce+1)) && (rn == (re+1)))
                        NextCell = getCell(nextCellIndex);
                    break;
                case -4:
                    if((cn == (ce+1)) && (rn == (re-1)))
                        NextCell = getCell(nextCellIndex);
                    break;
                case 4:
                    if((cn == (ce-1)) && (rn == (re+1)))
                        NextCell = getCell(nextCellIndex);
                    break;
            }
        }

        return NextCell;
    }

    /**
     * Method that finds the neighbouring cells of a cell
     * @return a list containing the indexes of the neighbouring cells of the given cell
     */

    public List<Cell> getNeighbouringCells(Cell cell)
    {
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

        for (int k = 0; k < 8; k++)
        {
            j = index + Neighbours[k];
            IsNeighbour = false;

            if (j >= 0 && j < 25)
            {
                Rj = j/5;
                Cj = j%5;

                if((Ci == 0) || (Ci == 4))
                {
                    if (Ri != Rj)
                    {
                        IsNeighbour = ((Cj >= Ci-1) && (Cj <= (Ci+1)));
                    }
                    else
                    {
                        if(Ci == 0 && (Cj <= (Ci+1)))
                        {
                            IsNeighbour = true;
                        }
                        else if (Ci == 4 && (Cj >= (Ci-1)))
                        {
                            IsNeighbour = true;
                        }
                    }
                }
                else
                {
                    IsNeighbour = true;
                }
            }

            if (IsNeighbour)
            {
                NeighbouringCells.add(getCell(j));
            }

            j = 0;
        }

        return NeighbouringCells;
    }

}
