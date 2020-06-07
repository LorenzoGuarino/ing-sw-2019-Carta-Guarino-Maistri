package it.polimi.ingsw.PSP027.Model.Game;

/**
 * @author Elisa Maistri
 */

public class Worker {

    private int index;
    private Cell oldPosition;
    private Cell oldBuiltCell;
    private Cell currentPosition;
    private Player owner;
    private int movecount;
    private int buildcount;
    private boolean buildDomeOnNextBuild;

    /**
     * Constructor: creates a new worker, storing who is its owner and its index, setting its position to null
     *
     * @param owner is the player who owns the worker
     * @param index index that tells which one of the two workers of said player this worker is
     */

    public Worker(Player owner, int index) {
        this.owner = owner;
        this.index = index;
        oldPosition = null;
        currentPosition = null;
        oldBuiltCell = null;
        movecount = 0;
        buildcount = 0;
        buildDomeOnNextBuild = false;
    }

    /**
     * Method that removes the worker from its owner workers
     */

    public void removeWorker() {
        this.getWorkerOwner().getPlayerWorkers().remove(this);
        this.changePosition(null);
    }

    /**
     * Method that resets the worker's utility variables used during the turn life cycle
     */

    public void ResetWorkerTurnVars()
    {
        buildDomeOnNextBuild = false;
        oldBuiltCell = null;
        movecount = 0;
        buildcount = 0;
    }

    /**
     * Method that sets the variable that indicates if the worker has to build a block or a dome
     * @param buildDomeOnNextBuild true if the worker has to build a dome, false if it has to build a block
     *                             (note: if this is false and the build is triggered on a cell already occupied by a level 3 block,
     *                             it still will be added a dome to the cell)
     */

    public void setBuildDomeOnNextBuild(boolean buildDomeOnNextBuild) {
        this.buildDomeOnNextBuild = buildDomeOnNextBuild;
    }

    /**
     * Method to call before building, it indicates if the worker has to build a block or a dome
     * @return buildDomeOnNextBuild value
     */

    public boolean HasToBuildADomeOnNextBuildPhase()
    {
        return buildDomeOnNextBuild;
    }

    /**
     * Method that tells which was the last built cell by the worker
     * @return the last built cell
     */

    public Cell getLastBuiltCell()
    {
        return oldBuiltCell;
    }

    /**
     * Method that sets the last built cell
     * @param oldBuiltCell cell to set as the last built by the worker
     */

    public void setOldBuiltCell(Cell oldBuiltCell) {
        this.oldBuiltCell = oldBuiltCell;
    }

    /**
     * Method that increments the counter that count the moves done in a single turn
     */

    public void IncrementMoveCounter()
    {
        movecount++;
    }

    /**
     * Method that increments the counter that count the builds done in a single turn
     */

    public void IncrementBuildCounter()
    {
        buildcount++;
    }

    /**
     * Method to call to know how many moves the worker has done in the turn life cycle
     * @return the moves count
     */

    public int getMoveCounter()
    {
        return movecount;
    }

    /**
     * Method to call to know how many builds the worker has done in the turn life cycle
     * @return the builds count
     */

    public int getBuildCounter()
    {
        return buildcount;
    }

    /**
     * Method used to get the previous position of the worker on the board, before the last call to changePosition
     *
     * @return the cell that the worker was occupying
     */

    public Cell getWorkerPrevPosition() {
        return oldPosition;
    }

    /**
     * Method used to get the position of the worker on the board
     *
     * @return the cell that the worker is occupying
     */

    public Cell getWorkerPosition() {
        return currentPosition;
    }

    /**
     * Method used to get the index of the worker, which can be 1 or 2
     *
     * @return the index
     */

    public int getWorkerIndex() {
        return index;
    }

    /**
     * Method that gets the worker's owner
     *
     * @return the Player, owner of the worker
     */

    public Player getWorkerOwner() {
        return owner;
    }

    /**
     * This method sets the current position as the previous one in order to keep track of it, and then changes the worker's position
     * @param position new position to set for the worker
     */
    public void changePosition(Cell position) {

        oldPosition = currentPosition;

        if((this.currentPosition != null) && this.currentPosition.getOccupyingWorker().equals(this)){
            this.currentPosition.setWorkerOccupying(null);
        }

        this.currentPosition = position;

        if(this.currentPosition != null)
            this.currentPosition.setWorkerOccupying(this);
    }
}

