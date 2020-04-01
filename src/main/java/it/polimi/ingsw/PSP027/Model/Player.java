package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lorenzo Guarino
 * */

public class Player {

    private String nickname;
    private List<Worker> playerWorkers;
    private GodCard playerGod;
    private List<GodCard> opponentsGodCards;

    /**
     * Constructor: creates a player, creating its workers
     */

    public Player() {
        playerWorkers = new ArrayList<Worker>();

        for (int i = 0; i < 2; i++) playerWorkers.add(new Worker(this, i));
    }

    public void resetOpponentsGodCards(){
        this.getOpponentsGodCards().clear();
    }

    public GodCard getPlayerGod() {
        return playerGod;
    }

    /**
     * Method to get the player's nickname
     * @return player's nickname
     * */

    public String getNickname() {
        return nickname;
    }

    /**
     * Method to set the player's nickname
     * @param nickname player nickname to set
     */

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Method to get the list of the player's workers
     * @return the player's workers
     */

    public List<Worker> getPlayerWorkers() {
        return playerWorkers;
    }


    /**
     * Method that starts the turn played by this player
     * */

    public void playTurn() {

    }

    public List<GodCard> getOpponentsGodCards() {
        return opponentsGodCards;
    }
}