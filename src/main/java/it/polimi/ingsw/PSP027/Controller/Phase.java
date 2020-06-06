package it.polimi.ingsw.PSP027.Controller;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Network.ProtocolTypes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 * @author Elisa Maistri
 */

public class Phase {

    // enum that indicates the type of phase the decorated actual phase will be

    public enum PhaseType {
        Undefined,
        Move,
        Build,
        End
    }

    private List<Cell> candidateCells = null;
    private Worker chosenWorker = null;
    private Board gameBoard = null;
    private PhaseType phaseType = PhaseType.Undefined;
    private boolean bMandatory = false;


    /**
     * Empty constructor
     */

    public Phase(){

    }

    /**
     * Method that sets the phase's type as the one it is given. Call this method in order to change the
     * type of the phase from Undefined (set when the phase is created) to Build/Move/End
     * @param phaseType phase type to set as this phase type
     */

    public void SetType(PhaseType phaseType)
    {
        this.phaseType = phaseType;
    }


    /**
     * Method to call when creating a phase. It creates an empty list of candidate cells that will then be filled at the right moment,
     * it sets the phase type as undefined for the time being, until it will be changed to the right type, it sets the chosen worker
     * and the game board that will be used for the phase
     * @param chosenWorker worker chosen to play the turn (therefore the phase) with
     * @param gameBoard the currently updated board on which the player has to play the phase
     * @param bMandatory boolean that indicates if this phase is a mandatory one (Build, Move) or an optional one (OptBuild, OptMove, OptEnd).
     *                   This parameter will tell if the player looses (mandatory = true) or not (mandatory = false) when not being able to perform the action of the phase
     */

    public void Init(Worker chosenWorker, Board gameBoard, boolean bMandatory)
    {
        candidateCells = new ArrayList<Cell>();
        this.chosenWorker = chosenWorker;
        this.gameBoard = gameBoard;
        this.bMandatory = bMandatory;
    }

    /**
     * Method to call when creating a phase, defined in the god power decorator so when the decorated phase will call the start phase
     * the action will be executed by the decorator who extends the phase and therefore will override this method
     */

    public boolean startPhase() {
        // method that is overridden by the god power decorator that extends the phase
        // in order to create a decorated phase that will be the one to play the turn with
        return true;
    }

    /**
     * Method that tells if this phase is a build
     * @return true if it is, otherwise false
     */

    public boolean IsABuildPhase() {

        return (phaseType == PhaseType.Build);
    }

    /**
     * Method that tells if this phase is a move
     * @return true if it is, otherwise false
     */

    public boolean IsAMovePhase() {

        return (phaseType == PhaseType.Move);
    }

    /**
     * Method that tells if this phase is an end
     * @return true if it is, otherwise false
     */

    public boolean IsAnEndPhase() {

        return (phaseType == PhaseType.End);
    }

    /**
     * Method that tells if the phase is mandatory or optional
     * @return true if it's mandatory, false if it's optional
     */

    public boolean IsMandatory() {
        return bMandatory;
    }

    /**
     * Method to get the phase's candidate cells
     * @return the list of candidate cells
     */

    public List<Cell> getCandidateCells() {

        return candidateCells;
    }

    /**
     * Method to get the game board on which the phase is being played
     * @return the game board
     */

    public Board getGameBoard() {

        return gameBoard;
    }

    /**
     * Method to get the player who is playing the phase
     * @return the chosenworker's, otherwise null
     */

    public Player getPlayingPlayer() {

        return (chosenWorker != null) ? chosenWorker.getWorkerOwner() : null;
    }

    /**
     * Method to get the chosen worker the player is playing the phase with
     * @return the chosenworker
     */

    public Worker getWorker() {

        return chosenWorker;
    }

    /**
     * Changes the candidate cells list of each phase according to its type.
     * Empty method, defined instead in each phase type.
     */

    public void evalCandidateCells() {}

    /**
     * Updates the board of each phase according to its type and the action performed on the given cell.
     * Empty method, defined instead in each phase type.
     * @param chosenCell chosen cell on which to perform the action
     */

    public void performActionOnCell(Cell chosenCell) {}

    /**
     * Method that checks if a player has won, if yes it sets the boolean stating so in the player info, and then return true, otherwise false
     * @return true if the player has won, otherwise false
     */

    public boolean PlayerHasWon()
    {
        if(chosenWorker == null)
            return false;

        if((chosenWorker.getWorkerPrevPosition().getLevel()==2) && (chosenWorker.getWorkerPosition().getLevel()==3)){
            chosenWorker.getWorkerOwner().setHasWon(true);
            return true;
        }

        return false;
    }



    /* ********************************************* PHASE UTILITY METHODS ****************************************** */

    /**
     * Method used to send the board with the candidate cells to the client by the phase
     * @param command protocol command to send with the board and the candidate cells
     */

    public void SendCandidateCells(ProtocolTypes.protocolCommand command){

        if(getPlayingPlayer() == null)
            return;

        String cmd = "<cmd><id>" + command.toString()  + "</id><data>";

        cmd += gameBoard.boardToXMLString();

        cmd += "<candidates>";

        for(int i=0; i < getCandidateCells().size(); i++){
            cmd += "<cell id=\"" + Integer.toString(getCandidateCells().get(i).getCellIndex());
            cmd += "\" />";
        }

        cmd += "</candidates></data></cmd>";

        getPlayingPlayer().SendCommand(cmd);
    }
}
