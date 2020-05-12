package it.polimi.ingsw.PSP027.View.Controllers;

import it.polimi.ingsw.PSP027.View.GUI;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class BoardPageController {
    private GUI gui;
    private ArrayList<ImageView> cellLevelList = new ArrayList<>();
    private ArrayList<ImageView> cellDomeList = new ArrayList<>();

    @FXML

    //cellLevelImageViews
    public ImageView A1L;
    public ImageView A2L;
    public ImageView A3L;
    public ImageView A4L;
    public ImageView A5L;
    public ImageView B1L;
    public ImageView B2L;
    public ImageView B3L;
    public ImageView B4L;
    public ImageView B5L;
    public ImageView C1L;
    public ImageView C2L;
    public ImageView C3L;
    public ImageView C4L;
    public ImageView C5L;
    public ImageView D1L;
    public ImageView D2L;
    public ImageView D3L;
    public ImageView D4L;
    public ImageView D5L;
    public ImageView E1L;
    public ImageView E2L;
    public ImageView E3L;
    public ImageView E4L;
    public ImageView E5L;

    //cellDomeImageViews
    public ImageView A1D;
    public ImageView A2D;
    public ImageView A3D;
    public ImageView A4D;
    public ImageView A5D;
    public ImageView B1D;
    public ImageView B2D;
    public ImageView B3D;
    public ImageView B4D;
    public ImageView B5D;
    public ImageView C1D;
    public ImageView C2D;
    public ImageView C3D;
    public ImageView C4D;
    public ImageView C5D;
    public ImageView D1D;
    public ImageView D2D;
    public ImageView D3D;
    public ImageView D4D;
    public ImageView D5D;
    public ImageView E1D;
    public ImageView E2D;
    public ImageView E3D;
    public ImageView E4D;
    public ImageView E5D;


    public Image level1Image = new Image("images/Board/Level1_Board.png");
    public Image level2Image = new Image("images/Board/Level2_Board.png");
    public Image level3Image = new Image("images/Board/Level1_Board.png");
    public Image domeImage = new Image("images/Board/Dome_Board.png");

    /**
     * Constructor
     */
    public BoardPageController(){
    }

    /**
     * This method creates a dependency between the level of the cell index = the list index
     * and the corresponding ImageView in the gridPane displaying the board
     */
    public void initCellLevelList() {
        cellLevelList.add(this.A1L);
        cellLevelList.add(this.A2L);
        cellLevelList.add(this.A3L);
        cellLevelList.add(this.A4L);
        cellLevelList.add(this.A5L);
        cellLevelList.add(this.B1L);
        cellLevelList.add(this.B2L);
        cellLevelList.add(this.B3L);
        cellLevelList.add(this.B4L);
        cellLevelList.add(this.B5L);
        cellLevelList.add(this.C1L);
        cellLevelList.add(this.C2L);
        cellLevelList.add(this.C3L);
        cellLevelList.add(this.C4L);
        cellLevelList.add(this.C5L);
        cellLevelList.add(this.D1L);
        cellLevelList.add(this.D2L);
        cellLevelList.add(this.D3L);
        cellLevelList.add(this.D4L);
        cellLevelList.add(this.D5L);
        cellLevelList.add(this.E1L);
        cellLevelList.add(this.E2L);
        cellLevelList.add(this.E3L);
        cellLevelList.add(this.E4L);
        cellLevelList.add(this.E5L);
    }
    /**
     * This method creates a dependency between the dome of the cell index = the list index
     * and the corresponding ImageView in the gridPane displaying the board
     */
    public void initCellDomeList() {
        cellLevelList.add(this.A1D);
        cellLevelList.add(this.A2D);
        cellLevelList.add(this.A3D);
        cellLevelList.add(this.A4D);
        cellLevelList.add(this.A5D);
        cellLevelList.add(this.B1D);
        cellLevelList.add(this.B2D);
        cellLevelList.add(this.B3D);
        cellLevelList.add(this.B4D);
        cellLevelList.add(this.B5D);
        cellLevelList.add(this.C1D);
        cellLevelList.add(this.C2D);
        cellLevelList.add(this.C3D);
        cellLevelList.add(this.C4D);
        cellLevelList.add(this.C5D);
        cellLevelList.add(this.D1D);
        cellLevelList.add(this.D2D);
        cellLevelList.add(this.D3D);
        cellLevelList.add(this.D4D);
        cellLevelList.add(this.D5D);
        cellLevelList.add(this.E1D);
        cellLevelList.add(this.E2D);
        cellLevelList.add(this.E3D);
        cellLevelList.add(this.E4D);
        cellLevelList.add(this.E5D);
    }

    /**
     * Given a cell index and a level ,this method sets the corresponding level height on the corresponding gridPane cell
     * @param index the index of the cell to set the level of
     * @param level the level displayed on the given cell after the method call
     */
    public void setLevel(int index,int level){
        initCellLevelList();
        switch(level){
            case 1: this.cellLevelList.get(index).setImage(this.level1Image);
            case 2: this.cellLevelList.get(index).setImage(this.level2Image);
            case 3: this.cellLevelList.get(index).setImage(this.level3Image);
            default: return;
        }
    }

    public void setDome(int index){
        initCellDomeList();
        this.cellLevelList.get(index).setImage(this.domeImage);
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
