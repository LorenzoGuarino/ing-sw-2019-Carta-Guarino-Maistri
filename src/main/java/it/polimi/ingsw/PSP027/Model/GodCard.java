package it.polimi.ingsw.PSP027.Model;

public class GodCard {
    /**
     * @author Lorenzo Guarino
     * Every GodCard has a Name, a Description and an ID
     * */
    private String godName;
    private String description;
    private int godId;

    /**
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
