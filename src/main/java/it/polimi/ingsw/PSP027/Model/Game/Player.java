package it.polimi.ingsw.PSP027.Model.Game;

import it.polimi.ingsw.PSP027.Network.Server.Gamer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lorenzo Guarino
 * @author Elisa Maistri
 * */

public class Player {

    private Gamer gamer;
    private List<Worker> playerWorkers;
    private GodCard playerGodCard;
    private List<GodCard> opponentsGodCards;
    private boolean hasWon;
    private boolean hasLost;

    /**
     * Constructor: creates a player, creating its workers
     */

    public Player() {
        hasWon = false;
        hasLost = false;

        playerWorkers = new ArrayList<Worker>();

        playerGodCard = null;

        opponentsGodCards = new ArrayList<GodCard>();

        for (int i = 0; i < 2; i++) playerWorkers.add(new Worker(this, i));
    }

    /**
     * Method that returns true if the player has won
     * @return hasWon value
     */
    public boolean HasWon() {
        return hasWon;
    }

    /**
     * Method that returns true if the player has lost
     * @return hasLost value
     */
    public boolean HasLost() {
        return hasLost;
    }

    /**
     * Method that sets HasWon
     * @param hasWon value to set
     */
    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    /**
     * Method that sets HasLost
     * @param hasLost value to set
     */
    public void setHasLost(boolean hasLost) {
        this.hasLost = hasLost;
    }

    /**
     * Method to get the player's GodCard
     * @return player's GodCard
     */

     public GodCard getPlayerGod() { return playerGodCard; }

    /**
     * Method to get the player's nickname
     * @return player's nickname
     * */

    public String getNickname() { return (gamer != null) ? gamer.getNickname() : ""; }

    /**
     * Method to set the player's gamer associated
     * @param gamer to set
     */

    public void setGamer(Gamer gamer) { this.gamer = gamer; }

    /**
     * Method to get the list of the player's workers
     * @return the player's workers
     */

    public List<Worker> getPlayerWorkers() { return playerWorkers; }

    /**
     * Method to add an opponent's god when it needs to decorate this player's next turn
     * @param opponentGod opponent god to add to the list (that will contain 0, 1 or 2 gods depending
     *                    on the number of players and if they influence their opponent's turn or not)
     */

    public void addOpponentGodCard(GodCard opponentGod) { opponentsGodCards.add(opponentGod); }

    /**
     * Method that clears the list of the opponent's gods.
     * It is going to be called when this player's turn is over in order to have next turn not
     * influenced by its opponent's gods unless they need to (in that case the owner of the god card
     * will be the one to re-add its godcard as an opponent for the other players
     */

    public void removeOpponentGodCards() { opponentsGodCards.clear(); }

    /**
     * Method that returns the list of opponent's gods that will influence this player's next turn
     * @return the list of opponent's gods. Can be null if no opponent player have a god card that influence
     * an opponent's turn
     */

    public List<GodCard> getOpponentsGodCards() { return opponentsGodCards; }

    /**
     * Method that sets the player's god card
     * @param playerGodCard god card to set for this player
     */

    public void setPlayerGodCard(GodCard playerGodCard) { this.playerGodCard = playerGodCard; }

    /**
     * Method used by Santorini Match and Turn to send the command that they generates to its players
     * @param cmd command to send
     */

    public void SendCommand(String cmd) {
        if(gamer != null)
            gamer.client.SendCommand(cmd);
    }

}