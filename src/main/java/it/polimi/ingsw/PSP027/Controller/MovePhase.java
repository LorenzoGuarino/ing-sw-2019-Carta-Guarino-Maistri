package it.polimi.ingsw.PSP027.Controller;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Network.ProtocolTypes;

import java.util.ArrayList;

/**
 * @author danielecarta
 */

public class MovePhase extends ConcretePhase {

    private int startChosenWorkerLvl;
    private Player playingPlayer;
    /**
     * Constructor, builds a standard MovePhase with a standard candidateMoves list
     * @param chosenWorker the worker i'm moving
     * @param gameBoard the board it's moving on
     */

    public MovePhase(Worker chosenWorker,Board gameBoard) {
        this.playingPlayer = chosenWorker.getWorkerOwner();
        this.setGameBoard(gameBoard);
        this.setChosenWorker(chosenWorker);
        this.setStartChosenWorkerLvl(chosenWorker.getWorkerPosition().getLevel());
        this.setCandidateCells(new ArrayList<Cell>());
        changeCandidateCells();

        SendCandidateMoves(playingPlayer, ProtocolTypes.protocolCommand.srv_CandidateCellsForMove);
    }

    /**
     * Standard candidate cells list for any movePhase of any player and worker
     */

    public void changeCandidateCells(){
        Cell startingCell = this.getChosenWorker().getWorkerPosition();
        for(Cell candidateCell : this.getGameBoard().getNeighbouringCells(startingCell)){   //for each candidate cell in neighbouringCells if
        if(     (candidateCell.getLevel() <= startingCell.getLevel() + 1) &&                //the lv i want to get to is higher less than one
                (!candidateCell.isOccupiedByWorker()) &&                                    //it is not occupied by a worker
                (!candidateCell.checkDome())) {                                             //it is not occupied by a dome
            this.getCandidateCells().add(candidateCell);                                    //then add the cell to candidateCells
            }
        }
    }

    /**
     * Standard update for a standard movePhase action
     * @param chosenCell the cell the worker is going to step onto
     */

    public void updateBoard(Cell chosenCell){
            this.getChosenWorker().changePosition(chosenCell);
            if(startChosenWorkerLvl==2 && this.getChosenWorker().getWorkerPosition().getLevel()==3){
                this.getChosenWorker().getWorkerOwner().setHasWon(true);
            }
    }

    public int getStartChosenWorkerLvl() {
        return startChosenWorkerLvl;
    }

    public void setStartChosenWorkerLvl(int startChosenWorkerLvl) {
        this.startChosenWorkerLvl = startChosenWorkerLvl;
    }

    /**
     * Method that send the cmd to send the candidate cells for the move phase
     * @param player player that will choose the move to do
     * @param command string to send at the Client
     */
    public void SendCandidateMoves(Player player, ProtocolTypes.protocolCommand command){
        String cmd = "<cmd><id>" + command.toString()  + "</id><data><candidates>";
        for(int i=0; i<getCandidateCells().size(); i++){
            cmd += "<cell id=\"" + Integer.toString(getCandidateCells().get(i).getCellIndex());
            cmd += "\" />";
        }
        cmd += "</candidates></data></cmd>";
        player.SendCommand(cmd);
    }
    public void setCandidateMove(int index){
        updateBoard(getGameBoard().getCell(index));
    }

}
