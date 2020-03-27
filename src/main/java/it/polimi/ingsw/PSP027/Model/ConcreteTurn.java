package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;

/**
 * @author danielecarta
 */

public class ConcreteTurn extends Turn {

    /**
     * Constructor, given the player instantiates a standard phaseList
     *@param playerPlaying
     */
    public ConcreteTurn(Player playerPlaying) {
        this.phaseList = new ArrayList<>();
        StartPhase phase1=new StartPhase(playerPlaying.getWorkers());
        this.phaseList.add(phase1);
        MovePhase phase2= new MovePhase(phase1.getChosenWorker().getWorkerPosition());
        this.phaseList.add(phase2);
        phaseList.add(new BuildPhase());
        phaseList.add(new EndPhase());
    }

    @Override
    public void applyPower() {

    }

}