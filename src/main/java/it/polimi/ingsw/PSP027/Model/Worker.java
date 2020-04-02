package it.polimi.ingsw.PSP027.Model;

/**
 * @author Elisa Maistri
 */

public class Worker {

    private int index;
    private Cell position;
    private Player owner;

    /**
     * Constructor: creates a new worker, storing who is its owner and its index, setting its position to null
     * @param owner is the player who owns the worker
     * @param index index that tells which one of the two workers of said player this worker is
     */

    public Worker(Player owner, int index)
    {
        this.owner = owner;
        this.index = index;
        position = null;
    }

    /**
     * Method used to get the position of the worker on the board
     * @return the cell that the worker is occupying
     */

    public Cell getWorkerPosition ()
    {
        return position;
    }

    /**
     * Method used to get the index of the worker, which can be 1 or 2
     * @return the index
     */

    public int getWorkerIndex ()
    {
        return index;
    }

    /**
     * Method that gets the worker's owner
     * @return the Player, owner of the worker
     */

    public Player getWorkerOwner ()
    {
        return owner;
    }

    public void setPosition(Cell position) {
        this.position = position;
        position.setWorkerOccupying(this);
    }
}
