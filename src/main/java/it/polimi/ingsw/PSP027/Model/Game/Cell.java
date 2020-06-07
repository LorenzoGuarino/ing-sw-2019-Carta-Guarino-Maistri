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

    /**
     * Method that tells if this cell is a perimeter cell
     * @return true if it is a perimeter cell, otherwise false
     */

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
     * Method used to tell if the cell is occupied by an opponent player's worker.
     * It checks if this cell's worker has a different owner
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

    /**
     * Method to call to tell if a level on this cell can be removed
     * @return true if a level can be removed (unoccupied cell and a level (not dome) is present),
     * false otherwise (occupied cell / no level on the cell / occupied by a dome)
     */

    public boolean canALevelBeRemoved()
    {
        return ((level > 0) && !isOccupiedByWorker() && !dome);
    }

    /**
     * Method that checks if a level can be added on this cell. When it is called it calls canALevelBeAdded(false),
     * which will do the check in the standard way
     * @return true if it is possible to build a block, otherwise false
     */

    public boolean canALevelBeAdded()
    {
        return canALevelBeAdded(false);
    }

    /**
     * Method that checks if a level can be added on this cell.
     * @param bIgnoreWorker boolean that tells the method to make the check ignoring the worker who might occupy the cell or not
     *                      (the standard check returns false if the cell is occupied by a worker. Ignoring the worker would make the check return true)
     * @return true if it is possible to build a block, otherwise false
     */

    public boolean canALevelBeAdded(boolean bIgnoreWorker) {
        return ((level < 3) && (!isOccupiedByWorker() || bIgnoreWorker) && !dome);
    }

    /**
     * Method that removes a level on this cell if possible
     * @return true if the level was removed, otherwise false
     */

    public boolean removeLevel() {
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
     * Method that adds a block to the cell if possible. When it is called it calls addLevel(false),
     * which will add a level in the standard way (only if the cell isn't occupied by a worker or has a dome)
     * @return a boolean that if it is true declares that the level could be added and was in fact added, otherwise it was not possible to add it
     */

    public boolean addLevel()
    {
        return addLevel(false);
    }

    /**
     * Method that checks if a level can be added on this cell.
     * @param bIgnoreWorker boolean that tells the method to make the check ignoring the worker who might occupy the cell or not
     *                      (the standard check returns false if the cell is occupied by a worker. Ignoring the worker would make the check return true)
     * @return true if it is possible to build a block, otherwise false
     */

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
