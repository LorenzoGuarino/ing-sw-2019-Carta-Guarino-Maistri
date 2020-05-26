package it.polimi.ingsw.PSP027.Network.Client;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * @author Elisa Maistri
 */

public interface ClientObserver {

    void OnConnected();
    void OnDisconnected();
    void OnConnectionError();
    void OnRegistered();
    void OnDeregistered();
    void OnServerConnectionClosed();
    void OnServerHasDied();
    void OnChooseMatchType();
    void OnEnteringMatch(List<String> players);
    void OnEnteredMatch(List<String> players);
    void OnRegistrationError(String error);
    void OnLeftMatch(String nickname);
    void OnChooseGods(int requiredgods, List<String> gods);
    void OnChooseGod(List<String> chosengods);
    void OnChooseFirstPlayer(List<String> players);
    void OnChooseWorkerStartPosition(NodeList board);
    void OnWinner(String nickname);
    void OnLoser();
    void OnChooseWorker(Node board);
    void OnCandidateCellsForMove(NodeList nodes);
    void OnCandidateCellsForOptMove(NodeList nodes);
    void OnCandidateCellsForBuild(NodeList nodes);
    void OnCandidateCellsForOptBuild(NodeList nodes);
    void OnCandidateCellsForOptEnd(NodeList nodes);
    void OnPrintUpdatedBoard(NodeList nodes);
}
