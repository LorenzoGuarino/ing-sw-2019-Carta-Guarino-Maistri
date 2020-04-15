package it.polimi.ingsw.PSP027.Network.Server;

import it.polimi.ingsw.PSP027.Model.Game.Gamer;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Controller.SantoriniMatch;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Elisa Maistri
 */

public class Lobby{

    private ReentrantLock GamersLock = new ReentrantLock();

    /**
     * List of the matches created by the Lobby. A new one is created when a gamer is added to
     * the lobbyGamers and all the previously matches instantiated have already reached the maximum
     * number of players (3) or have already started with just 2 players.
     */
    private ArrayList<SantoriniMatch> Matches;

    /**
     * List of the gamers that are currently in the lobby, wither waiting for a game to play or already playing a match
     */
    private ArrayList<Gamer> lobbyGamers = new ArrayList<Gamer>();

    /**
     * Constructor: where the application start. It instantiates an empty list of matches that will then be filled as the players
     * enter the lobby
     */

    public Lobby() {
        Matches = new ArrayList<SantoriniMatch>();
    }

    /**
     * Method that creates a Match with its separate thread and adds it to the list of matches of the lobby
     * @return the match created
     */

    public SantoriniMatch createMatch(int playersCount) {

        SantoriniMatch santoriniMatch = new SantoriniMatch();
        santoriniMatch.SetRequiredNumberOfPlayers(playersCount);
        Matches.add(santoriniMatch);
        Thread santoriniMatchThread = new Thread(santoriniMatch);
        santoriniMatchThread.start();

        return santoriniMatch;
    }

    /**
     * Method that checks if a gamer with the same nickname and the same IP adress has already entered the lobby
     * @param client identifying the gamer
     * @return 1 if the gamer is already in the lobby, 0 if not present, -1 if there is another gamer using the give nickname
     */

    public int checkIfAGamerIsAlreadyRegistered (ClientHandler client) {
        int iRet = 0;

        System.out.println("checkIfAGamerIsAlreadyRegistered " + client.getNickname());

        try{
            while(true) {

                if (GamersLock.tryLock(2L, TimeUnit.SECONDS)) {
                    for (Gamer gamerInLobby : lobbyGamers) {

                        if (gamerInLobby.client.getNickname().equals(client.getNickname())) {
                            iRet = -1;

                            if(gamerInLobby.client.getAddress().equals(client.getAddress()))
                                iRet = 1;

                            break;
                        }
                    }

                    break;

                }
                else {
                    TimeUnit.MILLISECONDS.sleep(200);
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            GamersLock.unlock();
        }

        System.out.println("checkIfAGamerIsAlreadyRegistered " + client.getNickname() + " returns " + Integer.toString(iRet));

        return iRet;
    }

    /**
     * Method that registers a new player in the lobby if possible
     * @param client identifying the gamer to add to the lobby gamers
     * @return the gamer object added to the lobby if any or null
     */

    public boolean registerNewPlayer(ClientHandler client) {

        System.out.println("registerNewPlayer " + client.getNickname());
        boolean bRet = false;

        try {
            while (true) {

                if (GamersLock.tryLock(2L, TimeUnit.SECONDS)) {

                    int iRet = checkIfAGamerIsAlreadyRegistered(client);

                    if(iRet == 0) {
                        Gamer gamer = new Gamer();
                        gamer.client = client;
                        lobbyGamers.add(gamer);
                    }

                    if(iRet >= 0)
                    {
                        bRet = true;
                        System.out.println("registerNewPlayer " + client.getNickname() + " done");
                    }

                    break;
                }
                else {
                    TimeUnit.MILLISECONDS.sleep(200);
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            GamersLock.unlock();
        }

        return bRet;
    }

    /**
     * Method that deregister a player removing it from the gamers and from its match's players
     * @param client identifying the gamer to deregister
     */

    public void deregisterPlayer(ClientHandler client) {

        System.out.println("deregisterPlayer " + client.getNickname());

        try {
            while (true) {

                if (GamersLock.tryLock(2L, TimeUnit.SECONDS)) {

                    int iRet = 0; // gamer not found

                    for (Gamer gamerInLobby : lobbyGamers) {

                        if (gamerInLobby.client.getNickname().equals(client.getNickname()) &&
                            gamerInLobby.client.getAddress().equals(client.getAddress())) {

                            for (SantoriniMatch match : Matches) {
                                if (gamerInLobby.matchAssociated == match.getMatchId()) {

                                    List<Player> matchPlayers = match.getPlayers();

                                    for(Player player : matchPlayers) {
                                        if(player.getNickname().equals(gamerInLobby.client.getNickname())) {
                                            match.removePlayer(player);
                                            break;
                                        }
                                    }

                                    break;
                                }
                            }

                            lobbyGamers.remove(gamerInLobby);

                            System.out.println("deregisterPlayer " + client.getNickname() + " done");

                            break;
                        }
                    }

                    break;
                }
                else {
                    TimeUnit.MILLISECONDS.sleep(200);
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            GamersLock.unlock();
        }

    }

    /**
     * Method that searches for a Match for the gamer that entered the lobby keeping in mind the number of players with which
     * the gamer wants to play with
     * @param client identifying the gamer that wants to enter a match
     * @param playersCount number of players of the type of match the gamer wants to play (2 or 3)
     */

    public void searchMatch(ClientHandler client, int playersCount) {
        System.out.println("searchMatch for " + client.getNickname() + " with " + Integer.toString(playersCount) + " players");

        boolean matchFound = false;

        try {
            while (true) {

                if (GamersLock.tryLock(2L, TimeUnit.SECONDS)) {

                    for (Gamer gamerInLobby : lobbyGamers) {

                        if ((gamerInLobby.client.getNickname().equals(client.getNickname())) &&
                                (gamerInLobby.client.getAddress().equals(client.getAddress()))
                        ) {

                            for (SantoriniMatch match : Matches) {
                                if ((match.GetRequiredNumberOfPlayers() == playersCount) && !match.isStarted()) {

                                    gamerInLobby.matchAssociated = match.getMatchId();

                                    Player player = new Player();
                                    player.setGamer(gamerInLobby);
                                    match.addPlayer(player);

                                    matchFound = true;
                                    break;
                                }
                            }

                            if (!matchFound) {
                                SantoriniMatch match = createMatch(playersCount);
                                if (match != null) {

                                    gamerInLobby.matchAssociated = match.getMatchId();

                                    Player player = new Player();
                                    player.setGamer(gamerInLobby);
                                    match.addPlayer(player);

                                    matchFound = true;

                                    System.out.println("searchMatch set match " + match.getMatchId().toString() + " for " + client.getNickname());
                                }
                            }

                            break;
                        }
                    }

                    break;
                }
                else {
                    TimeUnit.MILLISECONDS.sleep(200);
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            GamersLock.unlock();
        }
    }

    /**
     * Method that gets the chosen gods by the client (chosen by the first gamer that enetred the match) and asks the match to save them
     * @param client identifying the gamer that chose the gods
     * @param chosengods list of the names of the gods that are going to be used in the match
     */

    public void SetChosenGodsOnMatch(ClientHandler client, List<String> chosengods) {

        try {
            while (true) {

                if (GamersLock.tryLock(2L, TimeUnit.SECONDS)) {

                    for (Gamer gamerInLobby : lobbyGamers) {

                        if ((gamerInLobby.client.getNickname().equals(client.getNickname())) &&
                                (gamerInLobby.client.getAddress().equals(client.getAddress()))
                        ) {

                            for (SantoriniMatch match : Matches) {
                                if (match.getMatchId() == gamerInLobby.matchAssociated) {
                                    match.setGodCardsInUse(chosengods);
                                    break;
                                }
                            }

                            break;
                        }
                    }

                    break;
                }
                else {
                    TimeUnit.MILLISECONDS.sleep(200);
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            GamersLock.unlock();
        }
    }

    /**
     * Method that gets the chosen god by a player among the chosen gods that the first gamer that entered the match chose
     * and sets it as the player's god card
     * @param client identifying the gamer that chose the god
     * @param chosengod name of the god that the player wants to have as a god card
     */

    public void SetChosenGodOfPlayer(ClientHandler client, String chosengod) {

        try {
            while (true) {

                if (GamersLock.tryLock(2L, TimeUnit.SECONDS)) {

                    for (Gamer gamerInLobby : lobbyGamers) {

                        if ((gamerInLobby.client.getNickname().equals(client.getNickname())) &&
                                (gamerInLobby.client.getAddress().equals(client.getAddress()))
                        ) {

                            for (SantoriniMatch match : Matches) {
                                if (match.getMatchId() == gamerInLobby.matchAssociated) {

                                    List<Player> matchPlayers = match.getPlayers();

                                    for(Player player : matchPlayers) {
                                        if(player.getNickname().equals(gamerInLobby.client.getNickname())) {
                                            match.setGodCardForPlayer(player, chosengod);
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }

                            break;
                        }
                    }

                    break;
                }
                else {
                    TimeUnit.MILLISECONDS.sleep(200);
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            GamersLock.unlock();
        }
    }

    /**
     * Method that gets the chosen player by the first gamer that enetered the match among the players in the match itself
     * and sets it as the first player of the list of players
     * @param client identifying the gamer that chose the god
     * @param chosenplayer nickname of the player that will be the first to place its workers and then start the game
     */

    public void SetFirstPlayerOnMatch(ClientHandler client, String chosenplayer) {

        try {
            while (true) {

                if (GamersLock.tryLock(2L, TimeUnit.SECONDS)) {

                    for (Gamer gamerInLobby : lobbyGamers) {

                        if ((gamerInLobby.client.getNickname().equals(client.getNickname())) &&
                                (gamerInLobby.client.getAddress().equals(client.getAddress()))
                        ) {

                            for (SantoriniMatch match : Matches) {
                                if (match.getMatchId() == gamerInLobby.matchAssociated) {
                                    match.setFirstPlayer(chosenplayer);

                                    break;
                                }
                            }

                            break;
                        }
                    }

                    break;
                }
                else {
                    TimeUnit.MILLISECONDS.sleep(200);
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            GamersLock.unlock();
        }
    }

    /**
     * Method that gets the chosen workers' starting positions by the player and places them on the board, updating it.
     * @param client identifying the gamer that chose the positions
     * @param chosenpositions chosen positions where to place the player's 2 workers on the board before starting the game
     */

    public void SetChosenWorkersFirstPosition(ClientHandler client, List<String> chosenpositions) {

        try {
            while (true) {

                if (GamersLock.tryLock(2L, TimeUnit.SECONDS)) {

                    for (Gamer gamerInLobby : lobbyGamers) {

                        if ((gamerInLobby.client.getNickname().equals(client.getNickname())) &&
                                (gamerInLobby.client.getAddress().equals(client.getAddress()))
                        ) {

                            for (SantoriniMatch match : Matches) {
                                if (match.getMatchId() == gamerInLobby.matchAssociated) {

                                    List<Player> matchPlayers = match.getPlayers();

                                    for(Player player : matchPlayers) {
                                        if(player.getNickname().equals(gamerInLobby.client.getNickname())) {
                                            match.setWorkersStartPosition(player, chosenpositions);
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }

                            break;
                        }
                    }

                    break;
                }
                else {
                    TimeUnit.MILLISECONDS.sleep(200);
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            GamersLock.unlock();
        }
    }

}
