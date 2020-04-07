package it.polimi.ingsw.PSP027.Model.TurnsManagement;

import java.util.ArrayList;

/**
 * @author danielecarta
 */

public abstract class Turn {
    public ArrayList<Phase> phaseList;

    public ArrayList<Phase> getPhaseList() {
        return phaseList;
    }

    public void setPhaseList(ArrayList<Phase> phaseList) {
        this.phaseList = phaseList;
    }

    public abstract void applyPower();
}
