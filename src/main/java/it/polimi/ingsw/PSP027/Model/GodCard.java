package it.polimi.ingsw.PSP027.Model;

/**
 * @author Lorenzo Guarino
 * */

public class GodCard {

    private String godName;
    private String description;
    private int godId;

    /**
     * Constructor: create a god with its name, description and id
     * @param godName name of the god of the card
     * @param description description of this god ability
     * @param godId id identifying the god
     */

    public GodCard(String godName, String description, int godId) {
        this.godName = godName;
        this.description = description;
        this.godId = godId;
    }

    /**
     * Method to get the god's name
     * @return the name of the GodCard
     * */

    public String getGodName() {
        return godName;
    }

    /**
     * Method to get the god's description
     * @return the description of the GodCard
     * */

    public String getDescription() {
        return description;
    }

    /**
     * Method to get the god's id number
     * @return the Id of the GodCard
     * */

    public int getGodId() {
        return godId;
    }

}
