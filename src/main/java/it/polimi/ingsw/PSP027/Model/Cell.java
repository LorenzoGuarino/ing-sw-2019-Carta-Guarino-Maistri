package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Elisa Maistri
 */

public class Cell {

    private int level;
    private boolean dome;
    private boolean OccupiedByWorker;
    private Worker WorkerOccupying;
    private int index;

    /**
     * Constructor: creates a new cell therefore making it unoccupied and setting its index
     * @param index of the cell that is being created given by the board that creates the cell
     */

    public Cell(int index)
    {
        WorkerOccupying = null;
        level = 0;
        dome = false;
        OccupiedByWorker = false;
        this.index = index;
    }

    /**
     * Method to get the index of a cell
     * @return the index
     */

    public int getCellIndex() { return index; }

    /**
     * Method that checks if the cell is occupied by a worker
     * @return true if the cell has a worker on it, otherwise false
     */

    public boolean isOccupiedByWorker()
    {
        return OccupiedByWorker;
    }

    /**
     * Method used the cell is occupied by an opponent player's worker. It checks if this cell's worker has a different owner
     * from the one given to the function which would be the opponent Player.
     * @param opponentPlayer the player that needs to be compared to this cell's owner
     * @return true if the player occupying this cell is different from the player given to the function
     */

    public boolean isOccupiedByOpponentWorker(Player opponentPlayer) {
        if (WorkerOccupying.getWorkerOwner() != opponentPlayer) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Method to get the worker occupying this cell
     * @return the worker occupying the cell
     */

    public Worker getOccupyingWorker() { return WorkerOccupying; }

    /**
     * Method that checks if this cell has a complete tower on it
     * @return true if in this cell there is a complete tower, otherwise false
     */

    public boolean isCompleteTower()
    {
        return (level == 3) && dome;
    }

    /**
     * Method that checks if a level can be added on this cell
     * @return true if it is possible to build a block, otherwise false
     */

    public boolean canALevelBeAdded()
    {
        return (level != 3) && !OccupiedByWorker;
    }

    /**
     * This method checks if a worker can be moved to this cell
     * @param WorkerToMove worker that needs to be moved
     * @return true if a worker can be moved to this cell, otherwise false
     */

    public boolean canAWorkerBeMovedHere(Worker WorkerToMove)
    {
        if (!dome && !OccupiedByWorker)
        {
            if (WorkerToMove.getWorkerPosition().level >= level)
            {
                return true;
            }
            else return WorkerToMove.getWorkerPosition().level == level - 1;
         }
        else
        {
            return false;
        }
    }

    /**
     * Method that adds a block to the cell if possible
     * @return a boolean that if it is true declares that the level could be added and was in fact added, otherwise it was not possible to add it
     */

    public boolean addLevel()
    {
        if (canALevelBeAdded())
        {
            level++;
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Method to get the level of this cell
     * @return the number of this cell's level
     */

    public int getLevel() { return level; }

    /**
     * Method that checks if there's a dome on the cell
     * @return true if there's a dome on this cell, otherwise false
     */

    public boolean checkDome() { return dome; }

    /**
     * Method that checks if on this cell can be added a dome
     * @return true if a dome can be added to a tower, otherwise false
     */

    public boolean canADomeBeAdded() { return level == 3 && (!dome); }

    /**
     * Method that adds a dome to the cell
     * @return true if it was possible to add the dome, and was therefore added, otherwise false
     */

    public boolean addDome()
    {
        if (canALevelBeAdded())
        {
            dome = true;
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Method used by the board to reset its cells
     */

    public void resetCell()
    {
        WorkerOccupying = null;
        level = 0;
        dome = false;
        OccupiedByWorker = false;
    }

    public void setOccupiedByWorker(boolean occupiedByWorker) {
        OccupiedByWorker = occupiedByWorker;
    }

    /**
     * given a worker it sets this worker as occupyingWorker of the cell
     * @param workerOccupying
     */
    public void setWorkerOccupying(Worker workerOccupying) {
        WorkerOccupying = workerOccupying;
    }
}
