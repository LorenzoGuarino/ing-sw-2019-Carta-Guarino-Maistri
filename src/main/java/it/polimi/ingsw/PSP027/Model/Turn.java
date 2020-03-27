package it.polimi.ingsw.PSP027.Model;

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
