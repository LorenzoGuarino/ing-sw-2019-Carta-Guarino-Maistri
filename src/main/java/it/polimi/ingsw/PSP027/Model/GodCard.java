package it.polimi.ingsw.PSP027.Model;

/**
 * @author Lorenzo Guarino
 * */

public class GodCard {

    private String godName;
    private String description;
    private int godId;

    /**
     * Method to get the god's name
     * @return the name of the GodCard
     * */

    public String getGodName() {
        return godName;
    }

    /**
     * @param godName God Name to set
     * */

    public void setGodName(String godName) {
        this.godName = godName;
    }

    /**
     * Method to get the god's description
     * @return the description of the GodCard
     * */

    public String getDescription() {
        return description;
    }

    /**
     * @param description God Description to set
     * */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method to get the god's id number
     * @return the Id of the GodCard
     * */

    public int getGodId() {
        return godId;
    }

    /**
     * @param godId God Id to set
     */

    public void setGodId(int godId) {
        this.godId = godId;
    }

}
