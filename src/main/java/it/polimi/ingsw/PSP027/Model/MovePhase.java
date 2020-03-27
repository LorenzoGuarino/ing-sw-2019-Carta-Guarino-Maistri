package it.polimi.ingsw.PSP027.Model;

import java.util.ArrayList;
import java.util.List;

public class MovePhase extends Phase {
    private List<Cell> movableOntoCells= null ;

    public List<Cell> getMovableOntoCells() {
        return movableOntoCells;
    }

    public void setMovableOntoCells(List<Cell> movableOntoCells) {
        this.movableOntoCells = movableOntoCells;
    }

    public void setMovableOntoCells(Cell actualCell) {
        this.movableOntoCells= new ArrayList<Cell>();
    }
}
