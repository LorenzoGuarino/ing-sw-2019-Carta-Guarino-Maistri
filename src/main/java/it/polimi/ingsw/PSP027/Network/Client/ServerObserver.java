package it.polimi.ingsw.PSP027.Network.Client;

/**
 * @author Elisa Maistri
 */

public interface ServerObserver
{
    void onHello();
    void onRegistered();
    void onRegistrationError(String error);
    void onDeregistered();
}
