package it.polimi.ingsw.PSP027.Model.Game;

/**
 * @author Elisa Maistri
 */

public class Cell {

    private int level;
    private boolean dome;
    private Worker workerOccupying;
    private int index;

    /**
     * Constructor: creates a new cell therefore making it unoccupied and setting its index
     * @param index of the cell that is being created given by the board that creates the cell
     */

    public Cell(int index)
    {
        workerOccupying = null;
        level = 0;
        dome = false;
        this.index = index;
    }

    /**
     * Method to get the index of a cell
     * @return the index
     */

    public int getCellIndex() { return index; }

    public boolean isAPerimeterCell()
    {
        int Ri = index/5;
        int Ci = index%5;

        return ((Ri==0) || (Ri==4) || (Ci==0) || (Ci==4));
    }

    /**
     * Method that checks if the cell is occupied by a worker
     * @return true if the cell has a worker on it, otherwise false
     */

    public boolean isOccupiedByWorker(){
        return workerOccupying != null;
    }

    /**
     * Method used the cell is occupied by an opponent player's worker. It checks if this cell's worker has a different owner
     * from the one given to the function which would be the opponent Player.
     * @param opponentPlayer the player that needs to be compared to this cell's owner
     * @return true if the player occupying this cell is different from the player given to the function
     */

    public boolean isOccupiedByOpponentWorker(Player opponentPlayer) {
        if (isOccupiedByWorker()) {
            return workerOccupying.getWorkerOwner() != opponentPlayer;
        }
        return false;
    }

    /**
     * Method to get the worker occupying this cell
     * @return the worker occupying the cell
     */

    public Worker getOccupyingWorker() { return workerOccupying; }

    public boolean canALevelBeRemoved()
    {
        return ((level > 0) && !isOccupiedByWorker() && !dome);
    }

    /**
     * Method that checks if a level can be added on this cell
     * @return true if it is possible to build a block, otherwise false
     */

    public boolean canALevelBeAdded()
    {
        return canALevelBeAdded(false);
    }

    public boolean canALevelBeAdded(boolean bIgnoreWorker)
    {
        return ((level < 3) && (!isOccupiedByWorker() || bIgnoreWorker) && !dome);
    }

    public boolean removeLevel()
    {
        if (canALevelBeRemoved())
        {
            level--;
            return true;
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
        return addLevel(false);
    }

    public boolean addLevel(boolean bIgnoreWorker)
    {
        if (canALevelBeAdded(bIgnoreWorker))
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
     * Method that adds a dome to the cell
     * @return true if it was possible to add the dome, and was therefore added, otherwise false
     */

    public boolean addDome()
    {
        if (!isOccupiedByWorker()&&!checkDome())
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
        workerOccupying = null;
        level = 0;
        dome = false;
    }

    /**
     * Method that sets a given worker as the occupying worker of the cell
     * @param workerOccupying worker that is needed to be set as the occupier of the cell
     */
    public void setWorkerOccupying(Worker workerOccupying) {
        this.workerOccupying = workerOccupying;
    }
}
