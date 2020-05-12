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
    private ArrayList<ImageView> cellWorkerList = new ArrayList<>();
    private ArrayList<ImageView> cellCandidateList = new ArrayList<>();

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

    //cellWorkerImageViews
    public ImageView A1W;
    public ImageView A2W;
    public ImageView A3W;
    public ImageView A4W;
    public ImageView A5W;
    public ImageView B1W;
    public ImageView B2W;
    public ImageView B3W;
    public ImageView B4W;
    public ImageView B5W;
    public ImageView C1W;
    public ImageView C2W;
    public ImageView C3W;
    public ImageView C4W;
    public ImageView C5W;
    public ImageView D1W;
    public ImageView D2W;
    public ImageView D3W;
    public ImageView D4W;
    public ImageView D5W;
    public ImageView E1W;
    public ImageView E2W;
    public ImageView E3W;
    public ImageView E4W;
    public ImageView E5W;

    //cellCandidateImageViews
    public ImageView A1C;
    public ImageView A2C;
    public ImageView A3C;
    public ImageView A4C;
    public ImageView A5C;
    public ImageView B1C;
    public ImageView B2C;
    public ImageView B3C;
    public ImageView B4C;
    public ImageView B5C;
    public ImageView C1C;
    public ImageView C2C;
    public ImageView C3C;
    public ImageView C4C;
    public ImageView C5C;
    public ImageView D1C;
    public ImageView D2C;
    public ImageView D3C;
    public ImageView D4C;
    public ImageView D5C;
    public ImageView E1C;
    public ImageView E2C;
    public ImageView E3C;
    public ImageView E4C;
    public ImageView E5C;


    public Image level1Image = new Image("images/Board/Level1_Board.png");
    public Image level2Image = new Image("images/Board/Level2_Board.png");
    public Image level3Image = new Image("images/Board/Level1_Board.png");
    public Image domeImage = new Image("images/Board/Dome_Board.png");
    public Image candidate = new Image("/images/Board/CandidateCell_board.png");

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
        cellDomeList.add(this.A1D);
        cellDomeList.add(this.A2D);
        cellDomeList.add(this.A3D);
        cellDomeList.add(this.A4D);
        cellDomeList.add(this.A5D);
        cellDomeList.add(this.B1D);
        cellDomeList.add(this.B2D);
        cellDomeList.add(this.B3D);
        cellDomeList.add(this.B4D);
        cellDomeList.add(this.B5D);
        cellDomeList.add(this.C1D);
        cellDomeList.add(this.C2D);
        cellDomeList.add(this.C3D);
        cellDomeList.add(this.C4D);
        cellDomeList.add(this.C5D);
        cellDomeList.add(this.D1D);
        cellDomeList.add(this.D2D);
        cellDomeList.add(this.D3D);
        cellDomeList.add(this.D4D);
        cellDomeList.add(this.D5D);
        cellDomeList.add(this.E1D);
        cellDomeList.add(this.E2D);
        cellDomeList.add(this.E3D);
        cellDomeList.add(this.E4D);
        cellDomeList.add(this.E5D);
    }

    /**
     * This method creates a dependency between the dome of the cell index = the list index
     * and the corresponding ImageView in the gridPane displaying the board
     */
    public void initCellWorkerList() {
        cellWorkerList.add(this.A1W);
        cellWorkerList.add(this.A2W);
        cellWorkerList.add(this.A3W);
        cellWorkerList.add(this.A4W);
        cellWorkerList.add(this.A5W);
        cellWorkerList.add(this.A5W);
        cellWorkerList.add(this.B1W);
        cellWorkerList.add(this.B2W);
        cellWorkerList.add(this.B3W);
        cellWorkerList.add(this.B4W);
        cellWorkerList.add(this.B5W);
        cellWorkerList.add(this.C1W);
        cellWorkerList.add(this.C2W);
        cellWorkerList.add(this.C3W);
        cellWorkerList.add(this.C4W);
        cellWorkerList.add(this.C5W);
        cellWorkerList.add(this.D1W);
        cellWorkerList.add(this.D2W);
        cellWorkerList.add(this.D3W);
        cellWorkerList.add(this.D4W);
        cellWorkerList.add(this.D5W);
        cellWorkerList.add(this.E1W);
        cellWorkerList.add(this.E2W);
        cellWorkerList.add(this.E3W);
        cellWorkerList.add(this.E4W);
        cellWorkerList.add(this.E5W);
    }

    /**
     * This method creates a dependency between the dome of the cell index = the list index
     * and the corresponding ImageView in the gridPane displaying the board
     */
    public void initCellCandidateList() {
        cellCandidateList.add(this.A1C);
        cellCandidateList.add(this.A2C);
        cellCandidateList.add(this.A3C);
        cellCandidateList.add(this.A4C);
        cellCandidateList.add(this.A5C);
        cellCandidateList.add(this.B1C);
        cellCandidateList.add(this.B2C);
        cellCandidateList.add(this.B3C);
        cellCandidateList.add(this.B4C);
        cellCandidateList.add(this.B5C);
        cellCandidateList.add(this.C1C);
        cellCandidateList.add(this.C2C);
        cellCandidateList.add(this.C3C);
        cellCandidateList.add(this.C4C);
        cellCandidateList.add(this.C5C);
        cellCandidateList.add(this.D1C);
        cellCandidateList.add(this.D2C);
        cellCandidateList.add(this.D3C);
        cellCandidateList.add(this.D4C);
        cellCandidateList.add(this.D5C);
        cellCandidateList.add(this.E1C);
        cellCandidateList.add(this.E2C);
        cellCandidateList.add(this.E3C);
        cellCandidateList.add(this.E4C);
        cellCandidateList.add(this.E5C);
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

    public void setWorker(int index,String url){
        Image worker = new Image(url);
        initCellWorkerList();
        this.cellWorkerList.get(index).setImage(worker);
    }

    public void setCandidate(int index){
        initCellCandidateList();
        this.cellCandidateList.get(index).setImage(this.candidate);
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
