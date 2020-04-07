package it.polimi.ingsw.PSP027.Model.TurnsManagement;

/**
 * @author danielecarta
 */

public abstract class Phase {
    private boolean done;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Phase() {
        this.done = false;
    }
}
