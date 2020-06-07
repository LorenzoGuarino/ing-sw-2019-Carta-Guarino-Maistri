package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Network.ProtocolTypes;
import java.util.List;

/**
 * @author danielecarta
 */

public abstract class GodPowerDecorator extends Phase {

    private Phase decoratedPhase;
    private boolean bActAsOpponentGod;

    /**
     * Constructor : sets the decorated phase it is going to decorate and if the decorator will act as the owner decorator or as an opponent
     * @param decoratedPhase phase to decorate
     * @param bActAsOpponentGod boolean that tells if the decorator will act when the card is used by an opponent or when it is used by the owner
     */

    public GodPowerDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        this.decoratedPhase = decoratedPhase;
        this.bActAsOpponentGod = bActAsOpponentGod;
    }

    /**
     * Method to get the decorated phase
     * @return the decorated phase
     */

    public Phase getDecoratedPhase() {

        return decoratedPhase;
    }

    /**
     * Method that tells if the decorator acts as an opponent decorator
     * @return true if its power is valid when it is an opponent decorator, false if it influences the owner's phase
     */

    public boolean IsActingOnOpponentPlayer() {

        return bActAsOpponentGod;
    }

    /**
     * Method that sets the type of the phase
     * @param phaseType phase type to set as this phase type
     */

    @Override
    public void SetType(PhaseType phaseType)
    {
        this.decoratedPhase.SetType(phaseType);
    }

    /**
     * Method that sets the initial decorated phase with the chosenworker and the game board,
     * setting the phase's type as Undefined (that will have to be changed)
     * @param chosenWorker worker chosen to play the turn (so the phase) with
     * @param gameBoard the currently updated board on which the player has to play the phase
     * @param bMandatory boolean that tells if the phase is mandatory or optional
     */

    @Override
    public void Init(Worker chosenWorker, Board gameBoard, boolean bMandatory) {
        // this should do nothing to not alter base phase
    }

    /**
     * Method to get the candidate cells of the decorated phase
     * @return the list of candidate cells
     */

    @Override
    public List<Cell> getCandidateCells() {

        return decoratedPhase.getCandidateCells();
    }

    /**
     * Method to get the worker playing the decorated phase
     * @return the worker
     */

    @Override
    public Worker getWorker() {
        return this.decoratedPhase.getWorker();
    }

    /**
     * Method to get the game board with which the decorated phase is playing
     * @return the game board
     */

    @Override
    public Board getGameBoard() {

        return decoratedPhase.getGameBoard();
    }

    /**
     * Method that tells if the decorated phase is a build phase
     * @return true if it is, otherwise false
     */

    @Override
    public boolean IsABuildPhase() {

        return decoratedPhase.IsABuildPhase();
    }

    /**
     * Method that tells if the decorated phase is a move phase
     * @return true if it is, otherwise false
     */

    @Override
    public boolean IsAMovePhase() {

        return decoratedPhase.IsAMovePhase();
    }

    /**
     * Method that tells if the decorated phase is an end phase
     * @return true if it is, otherwise false
     */

    @Override
    public boolean IsAnEndPhase() {

        return decoratedPhase.IsAnEndPhase();
    }

    /**
     * Method that tells if the phase is mandatory or optional
     * @return true if it's mandatory, false if it's optional
     */

    @Override
    public boolean IsMandatory() {

        return decoratedPhase.IsMandatory() ;
    }


    /**
     * Method to get the player who is playing the decorated phase
     * @return the player
     */

    @Override
    public Player getPlayingPlayer() {

        return decoratedPhase.getPlayingPlayer();
    }

    /**
     * Method to call when creating the phase in order to trigger the start of the phase and the communication with the client
     * @return true if the phase can be performed, otherwise false
     */

    @Override
    public boolean startPhase() {

        evalCandidateCells();

        ProtocolTypes.protocolCommand cmd = ProtocolTypes.protocolCommand.undefined;

        // if there are candidate cells
        if(decoratedPhase.getCandidateCells().size()>0) {

            if (decoratedPhase.IsAMovePhase()) {
                if(decoratedPhase.IsMandatory()) {
                    cmd = ProtocolTypes.protocolCommand.srv_CandidateCellsForMove;
                }
                else {
                    cmd = ProtocolTypes.protocolCommand.srv_CandidateCellsForOptMove;
                }
            }
            else if (decoratedPhase.IsABuildPhase()) {
                if(decoratedPhase.IsMandatory()) {
                    cmd = ProtocolTypes.protocolCommand.srv_CandidateCellsForBuild;
                }
                else {
                    cmd = ProtocolTypes.protocolCommand.srv_CandidateCellsForOptBuild;
                }
            }
            else if (decoratedPhase.IsAnEndPhase()) {
                if(!decoratedPhase.IsMandatory()) {
                    cmd = ProtocolTypes.protocolCommand.srv_CandidateCellsForOptEnd;
                }
            }
            if (cmd != ProtocolTypes.protocolCommand.undefined)
            {
                decoratedPhase.SendCandidateCells(cmd);
                return true;
            }
        }

        return false;
    }

    /**
     * Method that sends the candidate cells evaluated by the decorated phase along with the board to the client
     * @param command protocol command to send with the board and the candidate cells
     */

    @Override
    public void SendCandidateCells(ProtocolTypes.protocolCommand command){

        decoratedPhase.SendCandidateCells(command);
    }

    /**
     * Method that evaluates the candidate cells for the decorated phase
     */

    @Override
    public void evalCandidateCells() {

        decoratedPhase.evalCandidateCells();
    }

    /**
     * Method that performs the action the decorated phase has to make
     * (if the phase if a move it will be to move the chosenworker, if it is a build it will be to build a level/dome,
     * if it is an end it will perform a particular action that can be also a move or a build depending on the god power)
     * @param chosenCell chosen cell on which to perform the action
     */

    @Override
    public void performActionOnCell(Cell chosenCell) {

        decoratedPhase.performActionOnCell(chosenCell);
    }

    /**
     * Method that tells if the player who is playing the phase has won
     * @return true if the player has won, otherwise false
     */

    @Override
    public boolean PlayerHasWon(){

        return decoratedPhase.PlayerHasWon();
    }
}
