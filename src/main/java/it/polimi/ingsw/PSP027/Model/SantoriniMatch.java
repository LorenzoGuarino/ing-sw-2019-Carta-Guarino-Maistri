package it.polimi.ingsw.PSP027.Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Elisa Maistri
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
    private List<GodCard> godsInUse;

    public static final String APOLLO      = "Apollo";
    public static final String ARTEMIS     = "Artemis";
    public static final String ATHENA      = "Athena";
    public static final String ATLAS       = "Atlas";
    public static final String DEMETER     = "Demeter";
    public static final String HEPHAESTUS  = "Hephaestus";
    public static final String MINOTAUR    = "Minotaur";
    public static final String PAN         = "Pan";
    public static final String PROMETHEUS  = "Prometheus";

    public static final String APOLLO_D     = "Your Move: Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated.";
    public static final String ARTEMIS_D    = "Your Move: Your Worker may move one additional time, but not back to its initial space.";
    public static final String ATHENA_D     = "Opponent's Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";
    public static final String ATLAS_D      = "Your Build: Your Worker may build a dome at any level.";
    public static final String DEMETER_D    = "Your Build: Your Worker may build one additional time, but not on the same space.";
    public static final String HEPHAESTUS_D = "Your Build: Your Worker may build one additional block (not dome) on top of your first block.";
    public static final String MINOTAUR_D   = "Your Move: Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.";
    public static final String PAN_D        = "Win Condition: You also win if your Worker moves down two or more levels.";
    public static final String PROMETHEUS_D = "Your Turn: If your Worker does not move up, it may build both before and after moving.";

    public static final int APOLLO_id      = 1;
    public static final int ARTEMIS_id     = 2;
    public static final int ATHENA_id      = 3;
    public static final int ATLAS_id       = 4;
    public static final int DEMETER_id     = 5;
    public static final int HEPHAESTUS_id  = 6;
    public static final int MINOTAUR_id    = 8;
    public static final int PAN_id         = 9;
    public static final int PROMETHEUS_id  = 10;



    /**
     * Constructor: this creates a new match, creating a list for the players that will the be filled as the players are added
     * the same for the list of turns, the gods that will be used in the match and the list of all gods
     */

    public SantoriniMatch() {
        players = new ArrayList<Player>();
        playedTurns = new ArrayList<Turn>();
        godsList = new ArrayList<GodCard>();

        godsList.add(new GodCard(APOLLO, APOLLO_D, APOLLO_id));
        godsList.add(new GodCard(ARTEMIS, ARTEMIS_D, ARTEMIS_id));
        godsList.add(new GodCard(ATHENA, ATHENA_D, ATHENA_id));
        godsList.add(new GodCard(ATLAS, ATLAS_D, ATLAS_id));
        godsList.add(new GodCard(DEMETER, DEMETER_D, DEMETER_id));
        godsList.add(new GodCard(HEPHAESTUS, HEPHAESTUS_D, HEPHAESTUS_id));
        godsList.add(new GodCard(MINOTAUR, MINOTAUR_D, MINOTAUR_id));
        godsList.add(new GodCard(PAN, PAN_D, PAN_id));
        godsList.add(new GodCard(PROMETHEUS, PROMETHEUS_D, PROMETHEUS_id));

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

    /**
     * Method that do the setup of the game
     * */

    public void startGame(){


    }

    /**
     * Method that end the game if the win conditions are verified
     * */

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
