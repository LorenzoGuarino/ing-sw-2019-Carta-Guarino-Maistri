package it.polimi.ingsw.PSP027.Model.TurnsManagement;

import it.polimi.ingsw.PSP027.Model.Game.GodCard;
import it.polimi.ingsw.PSP027.Model.Gods.AthenaOppDecorator;
import it.polimi.ingsw.PSP027.Model.Gods.GodPowerDecorator;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.SantoriniMatch;
import it.polimi.ingsw.PSP027.Model.Game.Worker;

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
        this.getPhaseList().add(phase1);
    }
    //???
    public void updateTurnAfterPhase(){
        Phase lastPhase= this.getPhaseList().get(this.getPhaseList().size()-1);
        MovePhase m = new MovePhase();
        if (lastPhase.getClass().equals(m.getClass())) {

        }
    }

    public void updateTurnAfterStartPhase(){
        StartPhase s=(StartPhase)this.getPhaseList().get(0);
        this.chosenWorker=s.getChosenWorker();
    }
    /**
     * adds a MovePhase according to the turn chosenWorker
     */
    public void addMovePhase(){
        int phaseListSize = this.getPhaseList().size();
        if(this.getPhaseList().get(phaseListSize-1).isDone()) {//if the last phase isDone i can add the movePhase
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
            switch (opponentGod.getGodType()){
                case Athena: return new AthenaOppDecorator(turn);
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