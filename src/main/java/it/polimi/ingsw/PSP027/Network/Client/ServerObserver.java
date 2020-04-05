package it.polimi.ingsw.PSP027.Network.Client;

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
    void onSearchMatch();
    void onLeaveMatch();
    void onChosenGods();
    void onChooseGod();
    void onWorkerStartPositionChosen();
    void onChooseWorker();
    void onMoveWorker();
    void onBuild();
    void onUseGodPower();

}
