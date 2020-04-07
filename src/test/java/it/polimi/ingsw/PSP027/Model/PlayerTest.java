package it.polimi.ingsw.PSP027.Model;

import it.polimi.ingsw.PSP027.Model.Game.GodCard;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    Player player = null;
    @Before
    public void setUp() throws Exception {
        player = new Player();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addOpponentGodCard() {
        GodCard Apollo = new GodCard("Apollo", "Descrizione", 1);
        player.addOpponentGodCard(Apollo);
        assertEquals(Apollo, player.getOpponentsGodCards().get(0));
    }

    @Test
    public void addGodCardTest(){
        GodCard Apollo = new GodCard("Apollo", "Descrizione", 1);
        player.setPlayerGodCard(Apollo);
        assertEquals(Apollo.getGodName(), player.getPlayerGod().getGodName());
        assertEquals(Apollo.getDescription(), player.getPlayerGod().getDescription());
        assertEquals(Apollo.getGodId(), player.getPlayerGod().getGodId());
    }

    @Test
    public void removeOpponentGodCards(){
    }

}