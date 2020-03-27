package it.polimi.ingsw.PSP027.Model;

/**
 * @author danielecarta
 */

public class ConcreteTurn extends Turn {

    public ConcreteTurn() {
        super();
    }

    void printPhases(){
        for(Phase phase:this.getPhaseList()){
            System.out.println(phase.toString());
        }
    }
    
    @Override
    public void applyPower() {
        int i=0;
    }
}
