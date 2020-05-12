package it.polimi.ingsw.PSP027.View.Controllers;

import it.polimi.ingsw.PSP027.View.GUI;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class BoardPageController {
    private GUI gui;
    private ArrayList<ImageView> cellList = new ArrayList<>();

    @FXML
    public ImageView A1;
    public ImageView A2;
    public ImageView A3;
    public ImageView A4;
    public ImageView A5;
    public ImageView B1;
    public ImageView B2;
    public ImageView B3;
    public ImageView B4;
    public ImageView B5;
    public ImageView C1;
    public ImageView C2;
    public ImageView C3;
    public ImageView C4;
    public ImageView C5;
    public ImageView D1;
    public ImageView D2;
    public ImageView D3;
    public ImageView D4;
    public ImageView D5;
    public ImageView E1;
    public ImageView E2;
    public ImageView E3;
    public ImageView E4;
    public ImageView E5;

    public Image level1Image = new Image("images/Board/Level1_Board.png");
    public Image level2Image = new Image("images/Board/Level2_Board.png");
    public Image level3Image = new Image("images/Board/Level1_Board.png");

    /**
     * Constructor, takes care of initializing the imageView list
     */
    public BoardPageController(){
        initCellList();
    }

    /**
     * This method creates a dependency between the cell index = the list index
     * and the corresponding ImageView in the gridPane displaying the board
     */
    public void initCellList() {
        cellList.add(this.A1);
        cellList.add(this.A2);
        cellList.add(this.A3);
        cellList.add(this.A4);
        cellList.add(this.A5);
        cellList.add(this.B1);
        cellList.add(this.B2);
        cellList.add(this.B3);
        cellList.add(this.B4);
        cellList.add(this.B5);
        cellList.add(this.C1);
        cellList.add(this.C2);
        cellList.add(this.C3);
        cellList.add(this.C4);
        cellList.add(this.C5);
        cellList.add(this.D1);
        cellList.add(this.D2);
        cellList.add(this.D3);
        cellList.add(this.D4);
        cellList.add(this.D5);
        cellList.add(this.E1);
        cellList.add(this.E2);
        cellList.add(this.E3);
        cellList.add(this.E4);
        cellList.add(this.E5);
    }

    /**
     * Given a cell index and a level ,this method sets the corresponding level height on the corresponding gridPane cell
     * @param index the index of the cell to set the level of
     * @param level the level displayed on the given cell after the method call
     */
    public void setLevel(int index,int level){
        switch(level){
            case 1: cellList.get(index).setImage(this.level1Image);
            case 2: cellList.get(index).setImage(this.level2Image);
            case 3: cellList.get(index).setImage(this.level3Image);
            default: return;
        }
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
