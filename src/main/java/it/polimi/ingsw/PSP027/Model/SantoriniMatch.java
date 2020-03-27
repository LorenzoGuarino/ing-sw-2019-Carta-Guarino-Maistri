package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lorenzo Guarino
 * */

public class SantoriniMatch {

    /**
     * This class manages the game's match. It has the board of the game, the list of players,
     * the turns played in order to save them, the list of the gods and a godsInUse list used by the first
     * player to choose the gods they will play with (from godsList) and from which each player will choose
     * their own god card.
     */

    private Board gameBoard;
    private List<Player> players;
    private List<Turn> playedTurns;
    private List<GodCard> godsList;
    private  List<GodCard> godsInUse;

    /**
     * Constructor: this creates a new match, creating a list for the players that will the be filled as the players are added
     * the same for the list of turns, the gods that will be used in the match and the list of all gods
     */

    public SantoriniMatch() {
        players = new ArrayList<Player>();
        playedTurns = new ArrayList<Turn>();
        godsList = new ArrayList<GodCard>();
        godsInUse = new ArrayList<GodCard>();
        gameBoard = new Board();
    }

    /**
     * Method to get the list of players
     * @return the list of players
     */

    public List<Player> getPlayers() { return players; }

    /**
     * Method used to add a player to the game
     * @param player is the player that is to be added to the list of players
     */

    public void addPlayer(Player player) { players.add(player); }

    /**
     * Method that saves the last turn in playedTurns
     * @param playedTurns
     * @param currentTurn
     */

    public void saveGame(List<Turn> playedTurns, Turn currentTurn) {


    }

    /**
     * Method that resumes the game by restarting at last turn saved
     * @param playedTurns
     * @param currentTurn
     */

    public void resumeGame(List<Turn> playedTurns, Turn currentTurn) {


    }


    public void endGame() {

    }

    /**
     * Method to use in order to check if there are no players in the match
     * @return
     */

    public boolean hasPlayers() { return !players.isEmpty(); }

    /**
     * Method to get the first player of the list of players
     * @return the first player
     */

    public Player getFirstPlayer() { return players.get(0); }

}
