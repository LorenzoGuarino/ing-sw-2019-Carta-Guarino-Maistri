package it.polimi.ingsw.PSP027.Model;

import it.polimi.ingsw.PSP027.Model.ArtemideDecorator;
import it.polimi.ingsw.PSP027.Model.ConcreteTurn;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Player player = new Player();
        ConcreteTurn triaLturn = new ConcreteTurn(player);
        GodPowerDecorator dec = new ArtemideDecorator(triaLturn);

        dec.applyPower();

    }

}
