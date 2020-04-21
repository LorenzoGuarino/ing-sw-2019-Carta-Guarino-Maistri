package it.polimi.ingsw.PSP027.Model.Gods;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Controller.Phase;
import it.polimi.ingsw.PSP027.Network.ProtocolTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 */

public abstract class GodPowerDecorator extends Phase {

    /**
     * The concrete phase the decorator goes to decorate
     */
    private Phase decoratedPhase;
    private boolean bActAsOpponentGod;

    public GodPowerDecorator(Phase decoratedPhase, boolean bActAsOpponentGod) {

        this.decoratedPhase = decoratedPhase;
        this.bActAsOpponentGod = bActAsOpponentGod;
    }

    public Phase getDecoratedPhase() {

        return decoratedPhase;
    }

    public boolean IsActingOnOpponentPlayer() {

        return bActAsOpponentGod;
    }

    @Override
    public void SetType(PhaseType phaseType)
    {
        this.decoratedPhase.SetType(phaseType);
    }

    @Override
    public void Init(Worker chosenWorker, Board gameBoard) {

        decoratedPhase.Init(chosenWorker, gameBoard);
    }

    @Override
    public List<Cell> getCandidateCells() {

        return decoratedPhase.getCandidateCells();
    }

    @Override
    public Worker getWorker() {
        return this.decoratedPhase.getWorker();
    }

    @Override
    public Board getGameBoard() {

        return this.decoratedPhase.getGameBoard();
    }

    @Override
    public boolean IsABuildPhase() {

        return decoratedPhase.IsABuildPhase();
    }

    @Override
    public boolean IsAMovePhase() {

        return decoratedPhase.IsAMovePhase();
    }


    @Override
    public Player getPlayingPlayer() {

        return decoratedPhase.getPlayingPlayer();
    }

    /**
     * Method to call when creating the move phase in order to trigger the start of the phase and the communication with the client
     */

    @Override
    public void startPhase() {

        evalCandidateCells();

        ProtocolTypes.protocolCommand cmd = ProtocolTypes.protocolCommand.undefined;

        if(decoratedPhase.IsAMovePhase())
            cmd = ProtocolTypes.protocolCommand.srv_CandidateCellsForMove;
        else if(decoratedPhase.IsABuildPhase())
            cmd = ProtocolTypes.protocolCommand.srv_CandidateCellsForBuild;

        if(cmd != ProtocolTypes.protocolCommand.undefined)
            decoratedPhase.SendCandidateCells(cmd);
    }

    @Override
    public void SendCandidateCells(ProtocolTypes.protocolCommand command){

        decoratedPhase.SendCandidateCells(command);
    }

    @Override
    public void evalCandidateCells() {

        decoratedPhase.evalCandidateCells();
    }

    @Override
    public void performActionOnCell(Cell chosenCell) {

        decoratedPhase.performActionOnCell(chosenCell);
    }

    @Override
    public boolean PlayerHasWon(){

        return decoratedPhase.PlayerHasWon();
    }
}
