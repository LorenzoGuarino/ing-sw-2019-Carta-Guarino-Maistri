package it.polimi.ingsw.PSP027.Model;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

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