package it.polimi.ingsw.PSP027.Network.Client;

/**
 * @author Elisa Maistri
 */

public interface ClientObserver {

    void OnConnected();
    void OnDisconnected();
    void OnConnectionError();
    void OnRegistered();
    void OnDeregistered();

}
