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

        // choose 5 among the next gods
        Zeus,
        Triton,
        Hera,
        Limus,
        Hestia,
        Chronos,
        Medusa
    }

    public enum WhereToApply {
        StartTurn,
        Move,
        ExtraMove,
        Build,
        ExtraBuild,
        EndTurn,
        WinCondition
    }

    public enum ToWhomIsApplied {
        Owner,
        Opponent
    }

    private GodsType godType;
    private WhereToApply whereToApply;
    private ToWhomIsApplied toWhomIsApplied;

    public static final String APOLLO_D     = "Your Move: Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated.";
    public static final String ARTEMIS_D    = "Your Move: Your Worker may move one additional time, but not back to its initial space.";
    public static final String ATHENA_D     = "Opponent's Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";
    public static final String ATLAS_D      = "Your Build: Your Worker may build a dome at any level.";
    public static final String DEMETER_D    = "Your Build: Your Worker may build one additional time, but not on the same space.";
    public static final String HEPHAESTUS_D = "Your Build: Your Worker may build one additional block (not dome) on top of your first block.";
    public static final String MINOTAUR_D   = "Your Move: Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.";
    public static final String PAN_D        = "Win Condition: You also win if your Worker moves down two or more levels.";
    public static final String PROMETHEUS_D = "Your Turn: If your Worker does not move up, it may build both before and after moving.";
    public static final String ZEUS_D       = "Your Build: Your Worker may build under itself in its current space, forcing it up one level. You do not win by forcing yourself up to the third level.";
    public static final String TRITON_D     = "Your Move: Each time your Worker moves onto a perimeter space (ground or block), it may immediately move again.";
    public static final String HERA_D       = "Opponent's Turn: An opponent cannot win by moving on to a perimeter space.";
    public static final String LIMUS_D      = "Opponent's Turn: Opponent Workers cannot build on spaces neighbouring your Workers, unless building a dome to create a Complete Tower";
    public static final String HESTIA_D     = "Your Build: Your Worker may build one additional time. The additional build cannot be on a perimeter space";
    public static final String CHRONUS_D    = "Win Condition: You also win when there are at least five Complete Towers on the board.";
    public static final String MEDUSA_D     = "End of Your Turn: If any of your opponent's Workers occupy lower neighbouring spaces, replace them all with blocks and remove them from the game";

    public static final String APOLLO      = "Apollo";
    public static final String ARTEMIS     = "Artemis";
    public static final String ATHENA      = "Athena";
    public static final String ATLAS       = "Atlas";
    public static final String DEMETER     = "Demeter";
    public static final String HEPHAESTUS  = "Hephaestus";
    public static final String MINOTAUR    = "Minotaur";
    public static final String PAN         = "Pan";
    public static final String PROMETHEUS  = "Prometheus";
    public static final String ZEUS        = "Zeus";
    public static final String TRITON      = "Triton";
    public static final String HERA        = "Hera";
    public static final String LIMUS       = "Limus";
    public static final String HESTIA      = "Hestia";
    public static final String CHRONUS     = "Chronus";
    public static final String MEDUSA      = "Medusa";

    /**
     * Constructor: create a god with its name, description and id
     * @param godType name of the god of the card
     */

    public GodCard(GodsType godType, WhereToApply whereToApply, ToWhomIsApplied toWhomIsApplied) {
        this.godType = godType;
        this.whereToApply = whereToApply;
        this.toWhomIsApplied = toWhomIsApplied;
    }

    /**
     * Method to get the GodType of the god card
     * @return the god type form an enum
     */

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

// choose 5 among the next gods
            case Zeus:
                return ZEUS;
            case Triton:
                return TRITON;
            case Hera:
                return HERA;
            case Limus:
                return LIMUS;
            case Hestia:
                return HESTIA;
            case Chronos:
                return CHRONUS;
            case Medusa:
                return MEDUSA;
            default:
                return "";
        }
    }

    /**
     * Method to get the god's description
     * @return the description of the GodCard
     * */

    public String getDescription() {

        switch(godType)
        {
            case Apollo:
                return APOLLO_D;
            case Artemis:
                return ARTEMIS_D;
            case Athena:
                return ATHENA_D;
            case Atlas:
                return ATLAS_D;
            case Demeter:
                return DEMETER_D;
            case Hephaestus:
                return HEPHAESTUS_D;
            case Minotaur:
                return MINOTAUR_D;
            case Pan:
                return PAN_D;
            case Prometheus:
                return PROMETHEUS_D;

// choose 5 among the next gods

            case Zeus:
                return ZEUS_D;
            case Triton:
                return TRITON_D;
            case Hera:
                return HERA_D;
            case Limus:
                return LIMUS_D;
            case Hestia:
                return HESTIA_D;
            case Chronos:
                return CHRONUS_D;
            case Medusa:
                return MEDUSA_D;
            default:
                return "";
        }
    }

    /**
     * Method to know if the God card must be applied on card owner or opponent
     * @return to whom it must be applied
     */

    public ToWhomIsApplied getToWhomIsApplied() {
        return toWhomIsApplied;
    }

    /**
     * Method to query when the god card must be applied
     * @return where it must be applied or asked form an enum
     */

    public WhereToApply getWhereToApply() {
        return whereToApply;
    }

    /**
     * Method to call in order to know if the god card allows an extra move
     * @return true if it allows it, otherwise false
     */

    public boolean AllowExtraMove() {

        switch(godType)
        {
            case Artemis:
            case Triton:
                return true;
            default:
                return false;
        }
    }

    /**
     * Method to call in order to know if a godcard allows an extra build before the move.
     * @return true if it allows it, otherwise false
     */

    public boolean AllowExtraBuildBeforeMove() {

        switch(godType)
        {
            case Prometheus:
                return true;
            default:
                return false;
        }
    }

    /**
     * Method to call in order to knwo if a godcard allows an extra build after the move
     * @return true if ti allows it, otherwise false
     */

    public boolean AllowExtraBuildAfterMove() {

        switch(godType)
        {
            case Demeter:
            case Hephaestus:
            case Hestia:
                return true;
            default:
                return false;
        }
    }
}
