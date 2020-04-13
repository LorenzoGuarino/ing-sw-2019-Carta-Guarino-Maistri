package it.polimi.ingsw.PSP027.Network.Client;

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
    void onChooseGod();
    void onWorkerStartPositionChosen();
    void onChooseWorker();
    void onMoveWorker();
    void onBuild();
    void onUseGodPower();

}
