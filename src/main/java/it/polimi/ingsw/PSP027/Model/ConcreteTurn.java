package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;

/**
 * @author danielecarta
 */

public class ConcreteTurn extends Turn {

    private Board gameBoard;
    private Worker chosenWorker;
    private Player playingPlayer;

    /**
     * Constructor, given the player instantiates a standard phaseList
     *@param playingPlayer
     */
    public ConcreteTurn(Player playingPlayer) {
        this.playingPlayer=playingPlayer;

        this.phaseList = new ArrayList<>();
        StartPhase phase1=new StartPhase(playingPlayer.getPlayerWorkers());
        this.phaseList.add(phase1);
        MovePhase phase2= new MovePhase();
        this.phaseList.add(phase2);
        phaseList.add(new BuildPhase());
        phaseList.add(new EndPhase());
    }

    @Override
    public void applyPower() {

    }

}