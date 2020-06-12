package it.polimi.ingsw.PSP027.View;

/**
 * @author danielecarta
 * Class necessary for creating the jar, that must launch a class with just a main,
 * otherwise GUI thorws error containing both main and start method of Application
 */

public class GUILauncher {
    public static void main(String[] args) {
        GUI.main(args);
    }
}
