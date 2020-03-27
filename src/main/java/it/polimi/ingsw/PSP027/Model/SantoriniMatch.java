package it.polimi.ingsw.PSP027.Model;

import java.util.List;

public class SantoriniMatch {
    /**
     * @author Lorenzo Guarino
     *
     * */
    private Board gameBoard;
    private List<Player> players;
    private List<Turn> playedTurns;



    /**
     *
     * */
    public List<Player> getPlayers() {
        return players;
    }
    /**
     *
     * */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    /**
     * godList is the List of cards that are in the game
     * */
    private List<GodCard> godList;
    /**
     * godInUse is the List of cards that have been choose by the first player
     * */
    public  List<GodCard> godInUse;

    /**
     * initiating the board
     * */
    public Board buildBoard(){




        return gameBoard;
    }

    /**
     * save the last turn in playedTurns
     * */
    public void saveGame(List<Turn> playedTurns, Turn currentTurn){


    }

    /**
     * resume the game by restarting at last turn saved
     * */
    public void resumeGame(List<Turn> playedTurns, Turn currentTurn){


    }


    public void endGame(){}


    public void addPlayer(){}

    public Player getFirstPlayer(List<Player> players){

    }


}
