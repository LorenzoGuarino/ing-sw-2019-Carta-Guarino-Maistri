package it.polimi.ingsw.PSP027.Model.Game;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * @author Elisa Maistri
 */

public class GodCardTest {

    GodCard ApolloGodCard;
    GodCard ArtemisGodCard;
    GodCard PrometheusGodCard;
    GodCard DemeterGodCard;
    GodCard PoseidonGodCard;


    @Before
    public void setUp() {
        ApolloGodCard = new GodCard(GodCard.GodsType.Apollo, GodCard.WhereToApply.Move, GodCard.ToWhomIsApplied.Owner);
        ArtemisGodCard = new GodCard(GodCard.GodsType.Artemis, GodCard.WhereToApply.ExtraMove, GodCard.ToWhomIsApplied.Owner);
        PrometheusGodCard = new GodCard(GodCard.GodsType.Prometheus, GodCard.WhereToApply.Move, GodCard.ToWhomIsApplied.Owner);
        DemeterGodCard = new GodCard(GodCard.GodsType.Demeter,  GodCard.WhereToApply.ExtraBuild, GodCard.ToWhomIsApplied.Owner);
        PoseidonGodCard = new GodCard(GodCard.GodsType.Poseidon, GodCard.WhereToApply.EndTurn, GodCard.ToWhomIsApplied.Owner);
    }

    @Test
    public void getGodType() {
        GodCard.GodsType godTypeExpected = GodCard.GodsType.Apollo;
        GodCard.GodsType apolloGodType = ApolloGodCard.getGodType();
        assertEquals(godTypeExpected, apolloGodType);
    }

    @Test
    public void getGodNameForApolloShouldReturnApollo() {
        String godNameExpected = "Apollo";
        String godName = ApolloGodCard.getGodName();
        assertEquals(godNameExpected, godName);
    }

    @Test
    public void getDescriptionForApolloShouldReturnApollosDescription() {
        String godDescriptionExpected = "Your Move: Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated.";
        String godDescription = ApolloGodCard.getDescription();
        assertEquals(godDescriptionExpected, godDescription);
    }

    @Test
    public void getToWhomIsAppliedForApolloShouldReturnOwner() {
        GodCard.ToWhomIsApplied toWhomIsAppliedExpected = GodCard.ToWhomIsApplied.Owner;
        GodCard.ToWhomIsApplied toWhomIsApplied = ApolloGodCard.getToWhomIsApplied();
        assertEquals(toWhomIsAppliedExpected, toWhomIsApplied);
    }

    @Test
    public void getWhereToApplyForApolloShouldReturnMove() {
        GodCard.WhereToApply whereToApplyExpected = GodCard.WhereToApply.Move;
        GodCard.WhereToApply whereToApply = ApolloGodCard.getWhereToApply();
        assertEquals(whereToApplyExpected, whereToApply);
    }

    @Test
    public void allowExtraMoveShouldReturnFalseForApollo() {
        Boolean allowExtraMoveExpected = false;
        Boolean allowExtraMove = ApolloGodCard.AllowExtraMove();
        assertEquals(allowExtraMoveExpected, allowExtraMove);
    }

    @Test
    public void allowExtraMoveShouldReturnTrueForArtemis() {
        Boolean allowExtraMoveExpected = true;
        Boolean allowExtraMove = ArtemisGodCard.AllowExtraMove();
        assertEquals(allowExtraMoveExpected, allowExtraMove);
    }

    @Test
    public void allowExtraBuildBeforeMoveForArtemisShouldReturnFalse() {
        Boolean allowExtraBuildBeforeMoveExpected = false;
        Boolean allowExtraBuildBeforeMove = ArtemisGodCard.AllowExtraBuildBeforeMove();
        assertEquals(allowExtraBuildBeforeMoveExpected, allowExtraBuildBeforeMove);
    }

    @Test
    public void allowExtraBuildBeforeMoveForPrometheusShouldReturnTrue() {
        Boolean allowExtraBuildBeforeMoveExpected = true;
        Boolean allowExtraBuildBeforeMove = PrometheusGodCard.AllowExtraBuildBeforeMove();
        assertEquals(allowExtraBuildBeforeMoveExpected, allowExtraBuildBeforeMove);
    }

    @Test
    public void allowExtraBuildAfterMoveForDemeterShouldReturnTrue() {
        Boolean allowExtraBuildAfterMoveExpected = true;
        Boolean allowExtraBuildAfterMove = DemeterGodCard.AllowExtraBuildAfterMove();
        assertEquals(allowExtraBuildAfterMoveExpected, allowExtraBuildAfterMove);
    }

    @Test
    public void allowExtraBuildAfterMoveForApolloShouldReturnFalse() {
        Boolean allowExtraBuildAfterMoveExpected = false;
        Boolean allowExtraBuildAfterMove = ApolloGodCard.AllowExtraBuildAfterMove();
        assertEquals(allowExtraBuildAfterMoveExpected, allowExtraBuildAfterMove);
    }

    @Test
    public void allowExtraEndForApolloShouldReturnFalse() {
        Boolean allowExtraEndExpected = false;
        Boolean allowExtraEnd = ApolloGodCard.AllowExtraEnd();
        assertEquals(allowExtraEndExpected, allowExtraEnd);
    }

    @Test
    public void allowExtraEndForPoseidonShouldReturnTrue() {
        Boolean allowExtraEndExpected = true;
        Boolean allowExtraEnd = PoseidonGodCard.AllowExtraEnd();
        assertEquals(allowExtraEndExpected, allowExtraEnd);
    }

}