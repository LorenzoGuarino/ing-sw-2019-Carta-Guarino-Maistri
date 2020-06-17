package it.polimi.ingsw.PSP027.Network.Server;

import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Controller.SantoriniMatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Elisa Maistri
 */

public class Lobby{

    private ReentrantLock GamersLock = new ReentrantLock();
    private ReentrantLock MatchesLock = new ReentrantLock();

    /*
     * List of the matches created by the Lobby. A new one is created when a gamer is added to
     * the lobbyGamers and all the previously matches instantiated have already reached the maximum
     * number of players (3) or have already started with just 2 players.
     */

    private ArrayList<SantoriniMatch> Matches;

    /*
     * List of the gamers that are currently in the lobby, wither waiting for a game to play or already playing a match
     */

    private ArrayList<Gamer> lobbyGamers;

    /**
     * Constructor: where the application start. It instantiates an empty list of matches that will then be filled as the players
     * enter the lobby
     */

    public Lobby() {
        Matches = new ArrayList<SantoriniMatch>();
        lobbyGamers = new ArrayList<Gamer>();
    }

    /**
     * Method that creates a Match with its separate thread and adds it to the list of matches of the lobby
     * @param playersCount number of players that the match that is being created will require in order to be played
     *                     (it is chosen by the player who triggers this creation when there are no available matches with
     *                     the desired number of players, therefore creating one with the chosen parameter)
     * @return the match created
     */

    public SantoriniMatch createMatch(int playersCount) {

        SantoriniMatch santoriniMatch = null;
        try {
            while (true) {

                if (MatchesLock.tryLock(2L, TimeUnit.SECONDS)) {

                    santoriniMatch = new SantoriniMatch(this);
                    santoriniMatch.SetRequiredNumberOfPlayers(playersCount);
                    Matches.add(santoriniMatch);
                    Thread santoriniMatchThread = new Thread(santoriniMatch);
                    santoriniMatchThread.start();

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
            MatchesLock.unlock();
        }


        return santoriniMatch;
    }

    /**
     * Method that removes a match from the list of matches of the lobby
     * @param santoriniMatch match to remove
     */

    public void removeMatch(SantoriniMatch santoriniMatch) {

        try {
            while (true) {

                if (MatchesLock.tryLock(2L, TimeUnit.SECONDS)) {

                    Matches.remove(santoriniMatch);
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
            MatchesLock.unlock();
        }
    }

    /**
     * Method that checks if a gamer with the same nickname has already entered the lobby
     * @param client identifying the gamer
     * @return 0 if not present, -1 if there is another gamer using the given nickname
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
     * Method that deregisters a player, removing it from the gamers and from its match's players
     * If the match hadn't started yet, the lobby creates a new match for the remaining players
     * (as their match cannot start anymore, lacking a player) with their previous match's required number of players
     * @param client identifying the gamer to deregister
     */

    public void deregisterPlayer(ClientHandler client) {

        System.out.println("deregisterPlayer " + client.getNickname());

        try {
            while (true) {

                if (GamersLock.tryLock(2L, TimeUnit.SECONDS)) {

                    int iRet = 0; // gamer not found

                    for (Gamer gamerInLobby : lobbyGamers) {

                        if (gamerInLobby.client.getNickname().equals(client.getNickname())) {

                            for (SantoriniMatch match : Matches) {
                                if (gamerInLobby.matchAssociated == match.getMatchId()) {

                                    List<Player> matchPlayers = match.getPlayers();

                                    for(Player player : matchPlayers) {
                                        if(player.getNickname().equals(gamerInLobby.client.getNickname())) {
                                            match.removePlayer(player);
                                            break;
                                        }
                                    }

                                    if(!match.isStarted()) {
                                        SantoriniMatch newMatch = createMatch(match.GetRequiredNumberOfPlayers());

                                        for(Player player : match.getPlayers()) {
                                            Player newMatchPlayer = new Player();

                                            for(Gamer gamer : lobbyGamers) {
                                                if(gamer.getNickname().equals(player.getNickname())) {
                                                    newMatchPlayer.setGamer(gamer);
                                                    gamer.matchAssociated = newMatch.getMatchId();
                                                    break;
                                                }
                                            }

                                            newMatch.addPlayer(newMatchPlayer);
                                        }

                                        removeMatch(match);
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
                                if ((match.GetRequiredNumberOfPlayers() == playersCount) && !match.isStarted() && !match.isFull()) {

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
     * Method that gets the chosen gods by the client (chosen by the first gamer that entered the match) and asks the match to save them
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
     * Method that gets the chosen player by the first gamer that entered the match among the players in the match itself
     * and sets it as the first player of the list of players. This will be the first one to place its workers and to play a turn.
     * @param client identifying the gamer that chose the player
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
     * Method that gets the workers' starting positions chosen by the player and places them on the board, updating it.
     * @param client identifying the gamer that chose the positions
     * @param chosenpositions chosen positions where to place the player's 2 workers on the board before starting the game
     */

    public void SetWorkersFirstPosition(ClientHandler client, List<String> chosenpositions) {

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

    /**
     * Method that gets the chosen worker's cell id and sets it to the match of the player that has made the choice
     * @param client identifying the player that chose the worker
     * @param chosenWorker id of the chosen worker's cell
     */

    public void SetChosenWorker(ClientHandler client, String chosenWorker) {
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
                                            match.setChosenWorker(chosenWorker);
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
     * Method that gets the chosen cell id where the worker has to be moved and sends it to the match of the
     * player that has made the choice
     * @param client identifying the player that chose the cell
     * @param chosenCell id of the chosen cell where to move the worker
     */
    public void MoveWorkerOnGivenCell(ClientHandler client, String chosenCell) {
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
                                            match.MoveWorker(chosenCell);
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
     * Method that skips the move for the player that has made the choice
     * @param client identifying the player that wants to skip the move
     */
    public void passMove(ClientHandler client) {
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
                                            match.passMove();
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
     * Method that gets the chosen cell id on which to perform the build and sends it to the match
     * of the player that has made the choice
     * @param client identifying the player that chose the cell
     * @param chosenCell id of the chosen cell
     */
    public void BuildOnGivenCell(ClientHandler client, String chosenCell) {
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
                                            match.Build(chosenCell);
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
     * Method that gets the chosen cell id on which to perform the build and sends it to the match
     * of the player that has made the choice
     * @param client identifying the player that chose the cell
     * @param chosenCell id of the chosen cell
     * @param build_BorD string stating if on the cell the player wants to build a block (B) or a dome (D)
     */
    public void BuildOnGivenCellForAtlas(ClientHandler client, String chosenCell, String build_BorD) {
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
                                            match.BuildForAtlas(chosenCell, build_BorD);
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
     * Method that skips the build for the player that has made the choice
     * @param client identifying the player that wants to skip the build
     */
    public void passBuild(ClientHandler client) {
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
                                            match.passBuild();
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
     * Method that gets the chosen cell id on which to perform the end and sends it to the match
     * of the player that has made the choice
     * @param client identifying the player that chose the cell
     * @param chosenCell id of the chosen cell
     */
    public void doEndActionOnGivenCell(ClientHandler client, String chosenCell) {
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
                                            match.endAction(chosenCell);
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
     * Method that skips the end for the player that has made the choice
     * @param client identifying the player that wants to skip the end
     */
    public void passEnd(ClientHandler client) {
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
                                            match.passEnd();
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

