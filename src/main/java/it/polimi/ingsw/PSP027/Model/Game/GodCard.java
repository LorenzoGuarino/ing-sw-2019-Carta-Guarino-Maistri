package it.polimi.ingsw.PSP027.Model.Game;

/**
 * @author Lorenzo Guarino
 * */

public class GodCard {

    private String godName;
    private String description;

    /**
     * Constructor: create a god with its name, description and id
     * @param godName name of the god of the card
     * @param description description of this god ability
     */

    public GodCard(String godName, String description) {
        this.godName = godName;
        this.description = description;
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

}
