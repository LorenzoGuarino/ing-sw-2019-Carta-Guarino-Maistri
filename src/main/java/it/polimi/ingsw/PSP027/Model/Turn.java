package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;

/**
 * @author danielecarta
 */

public abstract class Turn {
    private ArrayList<Phase> phaseList;

    public Turn() {
        this.phaseList = new ArrayList<>();
        phaseList.add(new StartPhase());
        phaseList.add(new MovePhase());
        phaseList.add(new BuildPhase());
        phaseList.add(new EndPhase());
    }

    public abstract void applyPower();

    public ArrayList<Phase> getPhaseList() {
        return phaseList;
    }

    public void setPhaseList(ArrayList<Phase> phaseList) {
        this.phaseList = phaseList;
    }
}
