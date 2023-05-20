package controller;

import model.*;
import view.GameFrame;

import java.awt.*;

public class GameEngine implements Runnable{
    // Thread that runs the game
    private Thread thread;
    // Helper objects
    public MapManager mapManager;
    private KeyBindings actionManager;
    private MouseController mouseController;
    private Map map;
    private GameFrame gameFrame;

    // Level layout
    private String[] level = {
            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",//50
            "W    P     AAAA   W      AA  HH                  W",
            "WH                W                E             W",
            "WH                W                              W",
            "WH                W                            A W",
            "WH                W                            A W",
            "W                                                W",
            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW            W",
            "W            E          HHW  E                   W",
            "W            E          HHW  E                   W",
            "W            E          HHW                      W",
            "W            E          HHW                 E    W",
            "W            E          HHW                      W",
            "W            E          HHW                      W",
            "W            E          HHW      E               W",
            "W            E          HHW               E      W",
            "W           WWWWWWWWWWWWWWW                      W",
            "W                      W                         W",
            "W       E    E         W              E          W",
            "W                      W                         W",
            "W       E                            E           W",
            "W        HHH WWWWWW                              W",
            "W         E          E    W                   EEEW",
            "WAA                       W                   EAHW",
            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"//24
    };

    public GameEngine(){
        //map = new Map(level);
        map = new Map("media/leveltest.png");
        mouseController = new MouseController(map);
        // Create new MapManager using map
        mapManager = new MapManager(map);
        // Create JFrame to hold graphics
        gameFrame = new GameFrame(this);
        // Create scrolling camera
        actionManager = new KeyBindings(map, gameFrame.uiManager);

        // Start game thread
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        // Set frame count variables
        long lastTime = System.nanoTime();
        double nsFrame = 1000000000 / GameConstants.fps;
        double delta = 0;

        // Loop to run the game every frame (60 fps)
        while (!thread.isInterrupted()) {

            long now = System.nanoTime();
            delta += (now - lastTime) / nsFrame;
            lastTime = now;
            // Run the gameLoop when the time passed is at least 1 frame
            while (delta >= 1) {
                gameLoop();
                delta--;
            }
            // Update the screen each frame
            gameFrame.update();
        }
    }

    // Main game loop
    private void gameLoop(){
        // Send the mouse position to map manager
        updateMousePos();
        // Update model
        mapManager.updateMap();
    }

    // Find the mouse position in the game JPanel
    public void updateMousePos(){
        mapManager.setMousePos(MouseInfo.getPointerInfo().getLocation().x-
                                gameFrame.uiManager.gameScreen.getLocationOnScreen().x,
                MouseInfo.getPointerInfo().getLocation().y-
                        gameFrame.uiManager.gameScreen.getLocationOnScreen().y);
    }

    public MouseController getMouseController() {
        return mouseController;
    }
}
