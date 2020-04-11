package it.polimi.ingsw.PSP027.Model.TurnsManagement;

import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Controller.SantoriniMatch;
import org.junit.After;
import org.junit.Before;

public class ConcreteTurnTest {
    SantoriniMatch santoriniMatch;
    Turn turn;
    Player player1,player2,player3;

    @Before
    public void setUp() throws Exception {
        santoriniMatch=new SantoriniMatch();
        player1=new Player();
        player2=new Player();
        player3=new Player();
        santoriniMatch.addPlayer(player1);
        santoriniMatch.addPlayer(player2);
        santoriniMatch.addPlayer(player3);
        turn=santoriniMatch.newTurn();
    }

    @After
    public void tearDown() throws Exception {
    }
}