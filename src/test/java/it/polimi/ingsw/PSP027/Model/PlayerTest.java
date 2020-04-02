package it.polimi.ingsw.PSP027.Model;

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
        player.addOpponentGodCard(new GodCard("Apollo", "Prova descrizione", 1));
    }

    @Test
    public void removeOpponentGodCards() {
    }

    @Test
    public void getOpponentsGodCards() {
    }
}