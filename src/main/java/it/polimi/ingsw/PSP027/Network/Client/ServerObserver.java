package it.polimi.ingsw.PSP027.Network.Client;

import org.w3c.dom.Node;

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
    void onChooseWorkerStartPosition(Node node);
    void onChooseWorker(Node node);
    void onCandidateCellsForMove(Node node);
    void onMoveWorker();
    void onBuild();
    void onUseGodPower();
}
