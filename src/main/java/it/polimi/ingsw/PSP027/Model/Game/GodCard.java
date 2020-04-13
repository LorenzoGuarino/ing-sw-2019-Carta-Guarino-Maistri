package it.polimi.ingsw.PSP027.Model.Game;

/**
 * @author Elisa Maistri
 * @author Lorenzo Guarino
 * */

public class GodCard {

    public enum GodsType {
        Apollo,
        Artemis,
        Athena,
        Atlas,
        Demeter,
        Hephaestus,
        Minotaur,
        Pan,
        Prometheus,
        AthenaOpp
    }

    private GodsType godType;
    private String description;

    public static final String APOLLO_D     = "Your Move: Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated.";
    public static final String ARTEMIS_D    = "Your Move: Your Worker may move one additional time, but not back to its initial space.";
    public static final String ATHENA_D     = "Opponent's Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";
    public static final String ATLAS_D      = "Your Build: Your Worker may build a dome at any level.";
    public static final String DEMETER_D    = "Your Build: Your Worker may build one additional time, but not on the same space.";
    public static final String HEPHAESTUS_D = "Your Build: Your Worker may build one additional block (not dome) on top of your first block.";
    public static final String MINOTAUR_D   = "Your Move: Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.";
    public static final String PAN_D        = "Win Condition: You also win if your Worker moves down two or more levels.";
    public static final String PROMETHEUS_D = "Your Turn: If your Worker does not move up, it may build both before and after moving.";

    public static final String APOLLO      = "Apollo";
    public static final String ARTEMIS     = "Artemis";
    public static final String ATHENA      = "Athena";
    public static final String ATLAS       = "Atlas";
    public static final String DEMETER     = "Demeter";
    public static final String HEPHAESTUS  = "Hephaestus";
    public static final String MINOTAUR    = "Minotaur";
    public static final String PAN         = "Pan";
    public static final String PROMETHEUS  = "Prometheus";

    /**
     * Constructor: create a god with its name, description and id
     * @param godType name of the god of the card
     * @param description description of this god ability
     */

    public GodCard(GodsType godType, String description) {
        this.godType = godType;
        this.description = description;
    }

    public GodsType getGodType() {
        return godType;
    }

    /**
     * Method to get the god's name
     * @return the name of the GodCard
     * */

    public String getGodName() {

        switch(godType)
        {
            case Apollo:
                return APOLLO;
            case Artemis:
                return ARTEMIS;
            case Athena:
                return ATHENA;
            case Atlas:
                return ATLAS;
            case Demeter:
                return DEMETER;
            case Hephaestus:
                return HEPHAESTUS;
            case Minotaur:
                return MINOTAUR;
            case Pan:
                return PAN;
            case Prometheus:
                return PROMETHEUS;
            default:
                return "";
        }
    }

    /**
     * Method to get the god's description
     * @return the description of the GodCard
     * */

    public String getDescription() {
        return description;
    }

}
