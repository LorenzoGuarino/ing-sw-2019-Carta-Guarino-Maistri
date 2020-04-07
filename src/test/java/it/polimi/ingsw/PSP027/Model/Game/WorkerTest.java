package it.polimi.ingsw.PSP027.Model.Game;

import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import org.junit.After;
import org.junit.Before;

public class WorkerTest {

    @Before
    public void setUp() throws Exception {
        Player player = new Player();
        Worker worker0 = new Worker(player, 0);
        Worker worker1 = new Worker(player, 1);
    }

    @After
    public void tearDown() throws Exception {
    }
}