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

    public enum PhaseType {

        Undefined,
        Move,
        Build
    }

    private List<Cell> candidateCells = null;
    private Worker chosenWorker = null;
    private Board gameBoard = null;
    private PhaseType phaseType = PhaseType.Undefined;

    public Phase(){

    }

    public void SetType(PhaseType phaseType)
    {
        this.phaseType = phaseType;
    }

    public void Init(Worker chosenWorker, Board gameBoard)
    {
        candidateCells = new ArrayList<Cell>();
        this.phaseType = phaseType;
        this.chosenWorker = chosenWorker;
        this.gameBoard = gameBoard;
    }

    public boolean IsABuildPhase() {

        return (phaseType == PhaseType.Build);
    }

    public boolean IsAMovePhase() {

        return (phaseType == PhaseType.Move);
    }

    public List<Cell> getCandidateCells() {

        return candidateCells;
    }

    public Board getGameBoard() {

        return gameBoard;
    }

    public Player getPlayingPlayer() {

        return (chosenWorker != null) ? chosenWorker.getWorkerOwner() : null;
    }

    public Worker getWorker() {

        return chosenWorker;
    }

    /**
     * Changes the candidate cells list of each phase according to its type
     */

    public void evalCandidateCells() {}

    /**
     * Updates the the board of each phase according to its type and the action performed on the given cell
     * @param chosenCell
     */

    public void performActionOnCell(Cell chosenCell) {}

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

    /**
     * Method to call when creating the move phase in order to trigger the start of the phase and the communication with the client
     */

    public void startPhase() {
    }

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
