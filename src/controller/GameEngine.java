/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The GameEngine class runs the game and connects all the other classes
 */

package controller;

import model.*;
import view.GameFrame;
import view.UIManager;

import java.awt.*;

public class GameEngine implements Runnable{
    // Thread that runs the game
    private Thread thread;
    // Controller references
    public MapManager mapManager;
    private KeyBindings actionManager;
    private MouseController mouseController;
    // Model reference
    private Map map;
    // View references
    private GameFrame gameFrame;
    private UIManager uiManager;

    private static GameState gameState;

    public GameEngine(){
        gameState = GameState.HOME;

        // Initialize objects
        map = new Map();
        mapManager = new MapManager(map);
        // Generate the first level to initialize objects such as player
        mapManager.startGame();
        mouseController = new MouseController(map.player);
        uiManager = new UIManager(this);
        gameFrame = new GameFrame(uiManager);
        actionManager = new KeyBindings(map, uiManager);

        // Start game thread
        thread = new Thread(this);
        thread.start();
    }

    // From Runnable interface, runs the game loop
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
            // Update the game when the time passed is at least 1 frame
            while (delta >= 1) {
                if(gameState == GameState.GAME) {
                    gameLoop();
                }else if(gameState == GameState.QUIT){
                    System.exit(0);
                }else if(gameState == GameState.STARTGAME){
                    setGameState(GameState.GAME);
                    mapManager.startGame();
                }
                else if(gameState == GameState.RESTART){
                    setGameState(GameState.GAME);
                    //mapManager.setLevel(1);
                    mapManager.setLevel(0);
                    mapManager.startGame();
                }
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
        // Update model through mapManager
        mapManager.update();
    }

    // Find the mouse position in the game JPanel
    private void updateMousePos(){
        mapManager.setMousePos(MouseInfo.getPointerInfo().getLocation().x-
                                uiManager.gameScreen.getLocationOnScreen().x,
                MouseInfo.getPointerInfo().getLocation().y-
                        uiManager.gameScreen.getLocationOnScreen().y);
    }

    public MouseController getMouseController() {
        return mouseController;
    }

    // Set the game state
    public static void setGameState(GameState newState) {
        switch (newState) {
            case PAUSE -> {
                if (gameState == GameState.GAME) {
                    gameState = newState;
                }
            }
            case GAME -> {
                if (gameState != GameState.GAMEOVER) {
                    gameState = newState;
                }
            }
            case WIN, LOSE -> {
                if(gameState == GameState.GAMEOVER){
                    gameState = newState;
                }
            }
            default -> gameState = newState;
        }
    }

    public GameState getGameState(){
        return gameState;
    }
}
