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

    public void removeWorker() {
        this.getWorkerOwner().getPlayerWorkers().remove(this);
        this.changePosition(null);
    }

    public void ResetWorkerTurnVars()
    {
        buildDomeOnNextBuild = false;
        oldBuiltCell = null;
        movecount = 0;
        buildcount = 0;
    }

    public void setBuildDomeOnNextBuild(boolean buildDomeOnNextBuild) {
        this.buildDomeOnNextBuild = buildDomeOnNextBuild;
    }

    public boolean HasToBuildADomeOnNextBuildPhase()
    {
        return buildDomeOnNextBuild;
    }

    public Cell getLastBuiltCell()
    {
        return oldBuiltCell;
    }

    public void setOldBuiltCell(Cell oldBuiltCell) {
        this.oldBuiltCell = oldBuiltCell;
    }

    public void IncrementMoveCounter()
    {
        movecount++;
    }

    public void IncrementBuildCounter()
    {
        buildcount++;
    }

    public int getMoveCounter()
    {
        return movecount;
    }

    public int getBuildCounter()
    {
        return buildcount;
    }

    /**
     * Method used to get the previous position of the worker on the board, before the last call to changePosition
     *
     * @return the cell that the worker is occupying
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
     * The setter of position tells to the old position it is no longer occupied by this worker, and the new one that it now is
     * @param position
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

