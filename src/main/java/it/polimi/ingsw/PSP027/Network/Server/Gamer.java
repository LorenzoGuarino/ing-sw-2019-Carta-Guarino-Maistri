package it.polimi.ingsw.PSP027.Network.Server;

import java.util.UUID;

/**
 * @author Elisa Maistri
 */

public class Gamer {

    //Client
    public ClientHandler client;
    public UUID matchAssociated;

    /**
     * Method to get the gamer's nickname
     * @return the nickname
     */
    public String getNickname() { return client.getNickname(); }

    /**
     * Method to get the gamer's address
     * @return the address
     */
    public String getAddress() { return client.getAddress(); }
}

