package it.polimi.ingsw.PSP027.Controller;

import it.polimi.ingsw.PSP027.Model.Game.GodCard;
import it.polimi.ingsw.PSP027.Model.Gods.GodPowerDecorator;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.MovePhase;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.Phase;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.StartPhase;
import it.polimi.ingsw.PSP027.Network.ProtocolTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 */

public class Turn {
    private List<Phase> phaseList;
    private Worker chosenWorker;
    private Player playingPlayer;
    private SantoriniMatch santoriniMatch;
    private boolean bCompleted;
    /**
     * Constructor, given the player instantiates a standard phaseList
     *@param playingPlayer
     */
    public Turn(Player playingPlayer,SantoriniMatch santoriniMatch) {
        this.santoriniMatch = santoriniMatch;
        this.playingPlayer = playingPlayer;
        this.phaseList = new ArrayList<>();
        this.bCompleted = false;

        this.santoriniMatch.SendCurrentBoardToPlayerWithGivenCommand(playingPlayer, ProtocolTypes.protocolCommand.srv_ChooseWorker);

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

    public List<Phase> getPhaseList() {
        return phaseList;
    }

    public void setPhaseList(List<Phase> phaseList) {
        this.phaseList = phaseList;
    }

    public void setChosenWorker(Worker chosenWorker) {
        this.chosenWorker = chosenWorker;
        //there stops the cmd call by cli
    }

    public boolean IsCompleted() {

        return bCompleted;
    }

    public boolean CurrentPlayerHasWon() {

        return playingPlayer.HasWon();
    }

}