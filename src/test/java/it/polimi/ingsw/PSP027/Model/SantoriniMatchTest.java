package it.polimi.ingsw.PSP027.Model;

import it.polimi.ingsw.PSP027.Controller.SantoriniMatch;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.TurnsManagement.Turn;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SantoriniMatchTest {
    public SantoriniMatch santoriniMatch;
    public Player player1,player2,player3;
    public Turn turn;

    @Before
    public void setUp(){
        santoriniMatch = new SantoriniMatch();
        player1 = new Player();
        player2 = new Player();
        santoriniMatch.addPlayer(player1);
        santoriniMatch.addPlayer(player2);
    }
    @Test
    public void getFirstPlayer_correctOutput() {
        Player actualPlayer = santoriniMatch.getFirstPlayer();
        Player expectedPlayer = santoriniMatch.getPlayers().get(0);
        assertEquals(expectedPlayer,actualPlayer);
    }
    @Test
    public void newTurn_havingAFirstPlayerToMakeTheTurnFor_shouldReturnTurnCorrectly() {
        Turn actualTurn = santoriniMatch.newTurn();
        Turn expectedTurn = new Turn(santoriniMatch.getFirstPlayer(),santoriniMatch);
        assertEquals(expectedTurn,actualTurn);
    }

    /*@Test
    public void decorateTurn() {
        player1.setPlayerGodCard(santoriniMatch.getGodCardsList().get(8));
        turn=santoriniMatch.newTurn();
        GodPowerDecorator dec = santoriniMatch.decorateTurn(turn);
        assertEquals(new MinotaurDecorator(turn),dec);//??????
    }*/
    @Test
    public void rotatePlayers_2playersCase() {
        ArrayList<Player> tempList = new ArrayList<>();
        tempList.add(player2);
        tempList.add(player1);
        santoriniMatch.rotatePlayers();
        assertEquals(tempList,santoriniMatch.getPlayers());
    }

    @Test
    public void rotatePlayer_3playersCase(){
        player3 = new Player();
        santoriniMatch.addPlayer(player3);
        ArrayList<Player> tempList = new ArrayList<>();
        tempList.add(player2);
        tempList.add(player3);
        tempList.add(player1);
        santoriniMatch.rotatePlayers();
        assertEquals(tempList,santoriniMatch.getPlayers());
    }
}