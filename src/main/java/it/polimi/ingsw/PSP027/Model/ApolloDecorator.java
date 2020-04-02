package it.polimi.ingsw.PSP027.Model;
/**
 * @author Lorenzo Guarino
 * */
public class ApolloDecorator extends GodPowerDecorator{

    boolean powerToggled;
    /**
     * Constructor
     *
     * @param decoratedTurn
     */
    public ApolloDecorator(ConcreteTurn decoratedTurn) {
        super(decoratedTurn);
    }

    @Override
    public void applyPower() {

    }
}
