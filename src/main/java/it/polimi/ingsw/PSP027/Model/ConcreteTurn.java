package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;

/**
 * @author danielecarta
 */

public class ConcreteTurn extends Turn {

    private Worker chosenWorker;
    private Player playingPlayer;
    private SantoriniMatch santoriniMatch;

    /**
     * Constructor, given the player instantiates a standard phaseList
     *@param playingPlayer
     */
    public ConcreteTurn(Player playingPlayer,SantoriniMatch santoriniMatch) {
        this.santoriniMatch=santoriniMatch;
        this.playingPlayer=playingPlayer;
        this.phaseList = new ArrayList<>();
        StartPhase phase1=new StartPhase(playingPlayer.getPlayerWorkers());
    }

    public void addMovePhase(){
        //only if start has been done
        if(this.getPhaseList().get(0).isDone()) {//@TODO index della startPhase
            MovePhase movePhase = new MovePhase(this.getChosenWorker(), this.getSantoriniMatch().getGameBoard());
            this.getPhaseList().add(movePhase);
        }
    }

    /**
     * given a turn it applies the mandatory opponents' gods decorators
     * @param turn
     * @return
     */
    public GodPowerDecorator secondaryDecoration(ConcreteTurn turn){
        for(GodCard opponentGod:turn.getPlayingPlayer().getOpponentsGodCards()){
            switch (opponentGod.getGodId()){
                case 3: return new AthenaOppDecorator(turn);
            }
        }
        return null;
    }

    @Override
    public void applyPower() {

    }

    public Player getPlayingPlayer() {
        return playingPlayer;
    }

    public Worker getChosenWorker() {
        return chosenWorker;
    }

    public SantoriniMatch getSantoriniMatch() {
        return santoriniMatch;
    }
}