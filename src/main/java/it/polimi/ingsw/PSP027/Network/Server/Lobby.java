package it.polimi.ingsw.PSP027.Network.Server;

import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.SantoriniMatch;

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

    public class Gamer {
        private String nickname;
        private String ipAddress;
        private UUID matchAssociated;
    }

    /**
     * List of the gamers that are currently in the lobby, wither waiting for a game to play or already playing a match
     */
    private ArrayList<Gamer> lobbyGamers = new ArrayList<Gamer>();

    /**
     * Constructor: where the application start
     */

    public Lobby() {
        Matches = new ArrayList<SantoriniMatch>();
    }

    public SantoriniMatch createMatch() {
        SantoriniMatch santoriniMatch = new SantoriniMatch();
        Matches.add(santoriniMatch);
        Thread santoriniMatchThread = new Thread(santoriniMatch);
        santoriniMatchThread.start();

        return santoriniMatch;
    }

    /**
     * Method that checks if a player with the same nickname and the same IP adress has already entered the lobby
     * @param nickname of the potential gamer to be added with another method if this method assures that is not already registered
     * @param ipaddress of the potential gamer to be added with another method if this method assures that is not already registered
     * @return 1 if the gamer is already in the lobby, 0 if not present, -1 if there is another gamer using the give nickname
     */

    public int checkIfAPlayerIsAlreadyRegistered (String nickname, String ipaddress) {
        int iRet = 0;

        try{
            while(true) {

                if (GamersLock.tryLock(2L, TimeUnit.SECONDS)) {
                    for (Gamer gamerInLobby : lobbyGamers) {

                        if (gamerInLobby.nickname.equals(nickname)) {
                            iRet = -1;

                            if(gamerInLobby.ipAddress.equals(ipaddress))
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

        return iRet;
    }

    public void deregisterPlayer(Gamer gamer) {

        try {
            while (true) {

                if (GamersLock.tryLock(2L, TimeUnit.SECONDS)) {

                    int iRet = 0; // gamer not found

                    for (Gamer gamerInLobby : lobbyGamers) {

                        if (gamerInLobby.nickname.equals(gamer.nickname)) {

                            for (SantoriniMatch match : Matches) {
                                if (gamer.matchAssociated == match.getMatchId()) {

                                    List<Player> matchPlayers = match.getPlayers();

                                    for(Player player : matchPlayers) {
                                        if(player.getNickname().equals(gamer.nickname)) {
                                            match.removePlayer(player);
                                            break;
                                        }
                                    }
                                    
                                    break;
                                }
                            }

                            lobbyGamers.remove(gamerInLobby);

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
     * Method that registers a new player in the lobby if possible
     * @param nickname of the gamer to add to the lobby gamers
     * @param ipaddress of the gamer to add to the lobby gamers
     * @return the gamer object added to the lobby if any or null
     */

    public Gamer registerNewPlayer(String nickname, String ipaddress) {

        Gamer gamer = null;

        try {
            while (true) {

                if (GamersLock.tryLock(2L, TimeUnit.SECONDS)) {

                    int iRet = 0; // gamer not found

                    for (Gamer gamerInLobby : lobbyGamers) {

                        if (gamerInLobby.nickname.equals(nickname)) {
                            iRet = -1; // nickname found

                            if(gamerInLobby.ipAddress.equals(ipaddress)) {
                                iRet = 1; // correct gamer found
                                gamer = gamerInLobby;
                            }

                            break;
                        }
                    }

                    if(iRet == 0) {
                        gamer = new Gamer();
                        gamer.nickname = nickname;
                        gamer.ipAddress = ipaddress;
                        lobbyGamers.add(gamer);

                        boolean matchFound = false;

                        for (SantoriniMatch match : Matches) {
                            if (match.numberOfPlayers() < 3 && !match.isStarted()) {

                                Player player = new Player();
                                player.setNickname(gamer.nickname);
                                match.addPlayer(player);
                                gamer.matchAssociated = match.getMatchId();
                                matchFound = true;
                                break;
                            }
                        }

                        if (!matchFound) {
                            SantoriniMatch match = createMatch();
                            if (match != null) {
                                Player player = new Player();
                                player.setNickname(gamer.nickname);
                                match.addPlayer(player);
                                gamer.matchAssociated = match.getMatchId();
                            }
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

        return gamer;
    }

}