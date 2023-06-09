package controller;

import model.*;
import view.GameFrame;
import view.UIManager;

import java.awt.*;
import java.util.Timer;

public class GameEngine implements Runnable{
    // Thread that runs the game
    private Thread thread;
    // Helper objects
    public MapManager mapManager;
    private KeyBindings actionManager;
    private MouseController mouseController;
    private Map map;
    private GameFrame gameFrame;
    private UIManager uiManager;

    private static GameState gameState;

    public GameEngine(){
        gameState = GameState.HOME;

        //map = new Map(level);
        map = new Map();
        // Create new MapManager using map
        mapManager = new MapManager(map);
        mapManager.generateMap();
        mouseController = new MouseController(map);
        uiManager = new UIManager(this);
        // Create JFrame to hold graphics
        gameFrame = new GameFrame(uiManager);
        // Create scrolling camera
        actionManager = new KeyBindings(map, uiManager, this);

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
                if(gameState == GameState.GAME) {
                    gameLoop();
                }else if(gameState == GameState.QUIT){
                    System.exit(0);
                }else if(gameState == GameState.STARTGAME){
                    setGameState(GameState.GAME);
                    mapManager.generateMap();
                };
                delta--;
            }
            // Update the screen each frame
            uiManager.update();
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
                                uiManager.gameScreen.getLocationOnScreen().x,
                MouseInfo.getPointerInfo().getLocation().y-
                        uiManager.gameScreen.getLocationOnScreen().y);
    }

    public MouseController getMouseController() {
        return mouseController;
    }

    public static void setGameState(GameState gameState) {
        switch (gameState) {
            case PAUSE -> {
                if (GameEngine.gameState == GameState.GAME) {
                    GameEngine.gameState = gameState;
                } else if (GameEngine.gameState == GameState.PAUSE) {
                    setGameState(GameState.GAME);
                }
            }
            case GAME -> {
                if (GameEngine.gameState != GameState.WIN && GameEngine.gameState != GameState.LOSE) {
                    GameEngine.gameState = gameState;
                }
            }
            //temporary
            case LOSE -> GameEngine.setGameState(GameState.QUIT);
            default -> GameEngine.gameState = gameState;
        }
    }

    public GameState getGameState(){
        return gameState;
    }
}
