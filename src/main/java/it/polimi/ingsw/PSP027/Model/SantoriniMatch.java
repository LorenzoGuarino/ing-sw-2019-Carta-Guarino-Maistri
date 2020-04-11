package it.polimi.ingsw.PSP027.Model;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.GodCard;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Model.Gods.GodPowerDecorator;
import it.polimi.ingsw.PSP027.Model.Gods.MinotaurDecorator;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.Turn;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.MovePhase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Elisa Maistri
 * @author Lorenzo Guarino
 * @author danielecarta
 * */

public class SantoriniMatch implements Runnable{

    /**
     * This class manages the game's match. It has the board of the game, the list of players,
     * the turns played in order to save them, the list of the gods and a godsInUse list used by the first
     * player to choose the gods they will play with (from godsList) and from which each player will choose
     * their own god card.
     */

    private Board gameBoard;
    private List<Player> players;
    private List<Turn> playedTurns;
    private UUID matchID;
    private List<GodCard> godCardsList;
    private List<GodCard> godCardsInUse;
    private boolean matchStarted;


    /**
     * Constructor: this creates a new match, creating a list for the players that will the be filled as the players are added
     * the same for the list of turns, the gods that will be used in the match and the list of all gods
     */

    public SantoriniMatch() {
        matchID = UUID.randomUUID();
        players = new ArrayList<Player>();
        playedTurns = new ArrayList<Turn>();
        godCardsList = new ArrayList<GodCard>();

        godCardsList.add(new GodCard(GodCard.GodsType.Apollo, GodCard.APOLLO_D));
        godCardsList.add(new GodCard(GodCard.GodsType.Artemis, GodCard.ARTEMIS_D));
        godCardsList.add(new GodCard(GodCard.GodsType.Athena, GodCard.ATHENA_D));
        godCardsList.add(new GodCard(GodCard.GodsType.Atlas, GodCard.ATLAS_D));
        godCardsList.add(new GodCard(GodCard.GodsType.Demeter, GodCard.DEMETER_D));
        godCardsList.add(new GodCard(GodCard.GodsType.Hephaestus, GodCard.HEPHAESTUS_D));
        godCardsList.add(new GodCard(GodCard.GodsType.Minotaur, GodCard.MINOTAUR_D));
        godCardsList.add(new GodCard(GodCard.GodsType.Pan, GodCard.PAN_D));
        godCardsList.add(new GodCard(GodCard.GodsType.Prometheus, GodCard.PROMETHEUS_D));

        godCardsInUse = new ArrayList<GodCard>();
        gameBoard = new Board();

        matchStarted = false;
    }

    @Override
    public void run() {
        // here goes what SantoriniMatch does, so the controller part
    }


    /**
     * create a new turn for the first player in the list
     * @return
     */

    public Turn newTurn(){
        return new Turn(this.getFirstPlayer(),this);
    }

    /**
     * decorates the players turn according to its GodCard
     * @return
     */

    public GodPowerDecorator decorateTurn(Turn turn) {
        switch (turn.getPlayingPlayer().getPlayerGod().getGodType()){
            //case Apollo: break;
            //case Artemis: break;
            //case Athena: break;
            //case Atlas: break;
            //case Demeter: break;
            //case Hephaestus: break;
            //case Minotaur: return null; //new MinotaurDecorator(turn);
            //case Pan: break;
            //case Prometheus: break;
        }
        return null;
    }

    /**
     * Method to make the old players list a new one that has as last player the old first player,as first player the old second player
     * and as (eventually) player in the middle the old last player
     */

    public void rotatePlayers(){
        ArrayList<Player> tempPlayers= new ArrayList<>();
        for(int i = 0; i < this.getPlayers().size() - 1; i++){
            tempPlayers.add(this.getPlayers().get(i + 1));
        }
        tempPlayers.add(this.getPlayers().get(0));
        this.setPlayers(tempPlayers);
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
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
     * Method that remove the playerToRemove and all his attributes from the game
     * @param playerToRemove
     */
    public void removePlayer(Player playerToRemove){
           for(int i = 0; i < playerToRemove.getPlayerWorkers().size(); i++){
               playerToRemove.getPlayerWorkers().get(i).changePosition(null);
           }
           GodCard cardToRemove = playerToRemove.getPlayerGod();
            for(int j = 1; j < this.getPlayers().size(); j++){
                Player tempPlayer = this.getPlayers().get(j);
                for(int p = 0; p < tempPlayer.getOpponentsGodCards().size(); p++){
                    if(tempPlayer.getOpponentsGodCards().get(p).getGodName().equals(cardToRemove.getGodName())){
                        tempPlayer.getOpponentsGodCards().remove(tempPlayer.getOpponentsGodCards().get(p));
                    }
                }
            }
            playerToRemove.removeOpponentGodCards();
        ArrayList<Player> tempPlayers= new ArrayList<>();
        for(int i = 0; i < this.getPlayers().size() - 1; i++){
            tempPlayers.add(this.getPlayers().get(i + 1));
        }
        this.setPlayers(tempPlayers);
        checkLoseCondition(this.getPlayers());
    }

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
     */

    public void startGame(){


    }

    /**
     * Method that ends the game if the win conditions are verified
     */

    public void endGame(Player playerWinner) {
        System.out.println("Player" + playerWinner.getNickname() + "has Won!");
    }

    /**
     * Method to use in order to check if there are no players in the match
     * @return
     */

    public boolean hasPlayers() { return !players.isEmpty(); }

    /**
     * Method to get the number of players currently in this match
     * @return the number of players in the match
     */

    public int numberOfPlayers() {
        int numberOfPlayers = 0;
        for (Player player : players) {
            numberOfPlayers++;
        }
        return numberOfPlayers;
    }

    /**
     * Method to get the id of this match
     * @return the match's id
     */

    public UUID getMatchId () {
        return matchID;
    }

    public boolean isStarted() {
        return matchStarted;
    }

    /**
     * Method to get the first player of the list of players
     * @return the first player
     */

    public Player getFirstPlayer() { return players.get(0); }

    public Board getGameBoard() {
        return gameBoard;
    }

    /**
     * Method that check is the Win condition are verified, in this case end the game
     * @param currentTurn the last ConcreteTurn, currently playing
     * @param lastMovePhase the last MovePhase, currently playing
     */

    public void checkWinCondition(Turn currentTurn, MovePhase lastMovePhase){
        if(lastMovePhase.getStartChosenWorkerLvl() == 2 && currentTurn.getChosenWorker().getWorkerPosition().getLevel() == 3){
            endGame(currentTurn.getPlayingPlayer());
        }
    }

    public void checkLoseCondition(List<Player> playersInGame){
        if(playersInGame.size() == 1){
            endGame(playersInGame.get(0));
        }
    }

    public List<GodCard> getGodCardsList() {
        return godCardsList;
    }
}
