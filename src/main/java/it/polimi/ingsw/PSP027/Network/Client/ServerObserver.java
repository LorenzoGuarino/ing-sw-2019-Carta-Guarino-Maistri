package it.polimi.ingsw.PSP027.Network.Client;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * @author Elisa Maistri
 */

public interface ServerObserver
{
    void onConnected();
    void onDisconnected();
    void onHello();
    void onRegister();
    void onDeregister();
    void onRegistrationError(String error);
    void onChooseMatchType();
    void onEnteringMatch(List<String> players);
    void onEnteredMatch(List<String> players);
    void onWinner(String nickname);
    void onLeftMatch(String nickname);
    void onLoser();
    void onChooseGods(int requiredgods, List<String> gods);
    void onChooseGod(List<String> chosengods);
    void onChooseFirstPlayer(List<String> players);
    void onChooseWorkerStartPosition(NodeList node);
    void onChooseWorker(Node node);
    void onCandidateCellsForMove(NodeList nodes);
    void onCandidateCellsForOptMove(NodeList nodes);
    void onCandidateCellsForBuild(NodeList nodes);
    void onCandidateCellsForOptBuild(NodeList nodes);
    void onCandidateCellsForEnd(NodeList nodes);
    void onCandidateCellsForOptEnd(NodeList nodes);
    void onMoveWorker();
    void onBuild();
    void onUseGodPower();
}
