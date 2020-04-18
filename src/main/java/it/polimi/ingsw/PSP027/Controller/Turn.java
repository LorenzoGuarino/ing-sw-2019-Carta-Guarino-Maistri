package it.polimi.ingsw.PSP027.Controller;

import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Network.ProtocolTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 *
 */

public class Turn {
    private List<Phase> phaseList;
    private Worker chosenWorker;
    private Player playingPlayer;
    private SantoriniMatch santoriniMatch;
    private boolean bCompleted;
    private MovePhase movephase;

    /**
     * Constructor: it is called by santorini match when the setup of the game is done and the turn of the first player can start.
     * It is given the player who is going to play the turn and the santorini match that called it.
     * With the constructor begins question-answer cmmunication with the client that one by one triggers the instantiation of each phase with the data
     * received form the client
     *@param playingPlayer player that plays this turn created by the match
     */

    public Turn(Player playingPlayer, SantoriniMatch santoriniMatch) {
        this.santoriniMatch = santoriniMatch;
        this.playingPlayer = playingPlayer;
        this.phaseList = new ArrayList<>();
        this.bCompleted = false;

        //First communication with the client done by the Turn, asking to choose a worker to play the turn with.
        this.santoriniMatch.SendCurrentBoardToPlayerWithGivenCommand(playingPlayer, ProtocolTypes.protocolCommand.srv_ChooseWorker);

    }

    /* ********************************* UTILITY METHODS OF THE TURN ************************************ */

    /**
     * Method to get the player that is playing the turn
     * @return the playing player
     */

    public Player getPlayingPlayer() {
        return playingPlayer;
    }

    /**
     * Method to get the worker chosen by the user at the instantiation of the turn
     * @return the chosen worker
     */

    public Worker getChosenWorker() {
        return chosenWorker;
    }

    /**
     * Method to get the match who created the turn
     * @return the match
     */

    public SantoriniMatch getSantoriniMatch() {
        return santoriniMatch;
    }

    /**
     * Method to get the phase list of the turn
     * @return the phase list
     */

    public List<Phase> getPhaseList() {
        return phaseList;
    }

    /**
     * Method to set the turn's phase list with a phase list
     * @param phaseList phase list to set as this turn's list
     */

    public void setPhaseList(List<Phase> phaseList) {
        this.phaseList = phaseList;
    }

    /**
     * Method to query in order to tell if the turn is completed
     * @return true if it is completed, otherwise false
     */

    public boolean IsCompleted() {

        return bCompleted;
    }

    /**
     * Method that checks if the player playing this turn has won
     * @return true if it has won, otherwise false
     */

    public boolean CurrentPlayerHasWon() {

        return playingPlayer.HasWon();
    }


    /* ***************************************************************************************************************
     *                METHODS THAT RECEIVE THE CLIENT RESPONSE AND TRIGGER AN ACTION OF THE TURN                     *
     *                          RESULTING IN ANOTHER COMMUNICATION WITH THE CLIENT                                   *
     * ***************************************************************************************************************/

    /**
     * Method that sets the chosen worker by the user that will play the turn and instantiates the move phase
     * @param chosenWorker worker chosen by the user
     */

    public void setChosenWorker(Worker chosenWorker) {
        this.chosenWorker = chosenWorker;
        //there stops the cmd call by cli
        movephase = new MovePhase(this.chosenWorker, this.santoriniMatch.getGameBoard());
        this.phaseList.add(movephase);
    }

    /**
     * Method that update the board with the new position of the worker
     * @param chosenCellIndex cell where the worker is moving onto
     * @TODO create build phase
     */
    public void setCandidateMove(int chosenCellIndex){
        this.movephase.setCandidateMove(chosenCellIndex);
    }

}