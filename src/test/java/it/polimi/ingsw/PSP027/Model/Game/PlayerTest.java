package it.polimi.ingsw.PSP027.Model.Game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * @author Elisa Maistri
 */

public class PlayerTest {

    Player player;
    GodCard ApolloGodCard;
    GodCard ArtemisGodCard;
    GodCard PrometheusGodCard;

    @Before
    public void setUp() throws Exception {
        player = new Player();
        ApolloGodCard = new GodCard(GodCard.GodsType.Apollo, GodCard.WhereToApply.Move, GodCard.ToWhomIsApplied.Owner);
        ArtemisGodCard = new GodCard(GodCard.GodsType.Artemis, GodCard.WhereToApply.ExtraMove, GodCard.ToWhomIsApplied.Owner);
        PrometheusGodCard = new GodCard(GodCard.GodsType.Prometheus, GodCard.WhereToApply.Move, GodCard.ToWhomIsApplied.Owner);
    }

    @After
    public void tearDown() throws Exception {
    }

    /*@Test
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
    }*/


    @Test
    public void HasWonShouldReturnFalse() {
        boolean hasWonExpected = false;
        boolean hasWon = player.HasWon();
        assertTrue(hasWonExpected == hasWon);
    }


    @Test
    public void HasWonShouldReturnTrue() {
        player.setHasWon(true);
        boolean hasWonExpected = true;
        boolean hasWon = player.HasWon();
        assertTrue(hasWonExpected == hasWon);
    }

    @Test
    public void HasLostShouldReturnFalse() {
        boolean hasLostExpected = false;
        boolean hasLost = player.HasLost();
        assertTrue(hasLostExpected == hasLost);
    }

    @Test
    public void HasLostShouldReturnTrue() {
        player.setHasLost(true);
        boolean hasLostExpected = true;
        boolean hasLost = player.HasLost();
        assertTrue(hasLostExpected == hasLost);
    }

    @Test
    public void setHasWonShouldSetHasWonAsFalse() {
        boolean HasWonExpected = false;
        player.setHasWon(false);
        boolean HasWon = player.HasWon();
        assertTrue(HasWonExpected == HasWon);
    }

    @Test
    public void setHasWonShouldSetHasWonAsTrue() {
        boolean HasWonExpected = true;
        player.setHasWon(true);
        boolean HasWon = player.HasWon();
        assertTrue(HasWonExpected == HasWon);
    }

    @Test
    public void setHasLostShouldSetHasWonAsFalse() {
        boolean HasLostExpected = false;
        player.setHasLost(false);
        boolean HasLost = player.HasLost();
        assertTrue(HasLostExpected == HasLost);
    }

    @Test
    public void setHasLostShouldSetHasWonAsTrue() {
        boolean HasLostExpected = true;
        player.setHasLost(true);
        boolean HasLost = player.HasLost();
        assertTrue(HasLostExpected == HasLost);
    }

    @Test
    public void setPlayerGodCardShouldSetPlayersGodAsApollo() {
        player.setPlayerGodCard(ApolloGodCard);
        assertEquals(ApolloGodCard, player.getPlayerGod());
    }

    @Test
    public void getPlayerGodShouldReturnApolloWhenPlayersGodIsApollo() {
        player.setPlayerGodCard(ApolloGodCard);
        GodCard playerGodCard = player.getPlayerGod();
        assertEquals(ApolloGodCard, playerGodCard);
    }

    @Test
    public void getNicknameShouldReturnEmptyStringWhenGamerIsNull() {
        player.setGamer(null);
        String nicknameExpected = "";
        String nickname = player.getNickname();
        assertEquals(nicknameExpected, nickname);
    }

    @Test
    public void getNicknameShouldReturnGamerNicknameIfNotNull() {
    }

    @Test
    public void setGamer() {

    }

    @Test
    public void getPlayerWorkersShouldReturnTheListOfWorkers() {
        List<Worker> playerWorkersExpected = new ArrayList<Worker>();
        playerWorkersExpected.add(player.getPlayerWorkers().get(0));
        playerWorkersExpected.add(player.getPlayerWorkers().get(1));
        assertTrue(playerWorkersExpected.containsAll(player.getPlayerWorkers()) && player.getPlayerWorkers().containsAll(playerWorkersExpected));
    }

    @Test
    public void addOpponentGodCardShouldAddApolloInTheListOfOpponentGods() {
        List<GodCard> expectedOpponentGodCardList = new ArrayList<GodCard>();
        expectedOpponentGodCardList.add(ApolloGodCard);
        player.addOpponentGodCard(ApolloGodCard);
        assertTrue(expectedOpponentGodCardList.containsAll(player.getOpponentsGodCards()) && player.getOpponentsGodCards().containsAll(expectedOpponentGodCardList));
    }

    @Test
    public void removeOpponentGodCardsShouldMakeOpponentGodCardsEmpty() {
        List<GodCard> expectedOpponentGodCardList = new ArrayList<GodCard>();
        player.addOpponentGodCard(ArtemisGodCard);
        player.removeOpponentGodCards();
        assertTrue(expectedOpponentGodCardList.containsAll(player.getOpponentsGodCards()) && player.getOpponentsGodCards().containsAll(expectedOpponentGodCardList));
    }

    @Test
    public void getOpponentsGodCardsShouldReturnTheListOfOpponentGodCards_caseWithArtemisAndApollo() {
        List<GodCard> expectedOpponentGodCardList = new ArrayList<GodCard>();
        expectedOpponentGodCardList.add(ArtemisGodCard);
        expectedOpponentGodCardList.add(ApolloGodCard);
        player.addOpponentGodCard(ArtemisGodCard);
        player.addOpponentGodCard(ApolloGodCard);
        assertTrue(expectedOpponentGodCardList.containsAll(player.getOpponentsGodCards()) && player.getOpponentsGodCards().containsAll(expectedOpponentGodCardList));
    }




}