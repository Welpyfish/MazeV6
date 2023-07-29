/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The UIManager class contains and manages the game view
 */

package view;

import controller.GameEngine;
import controller.GameState;
import controller.action.SetState;
import model.weapon.ProjectileType;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JLayeredPane.POPUP_LAYER;

public class UIManager extends JPanel {
    public JPanel game;
    public GameScreen gameScreen;
    private PauseScreen pauseScreen;
    private InventoryUI inventoryUI;
    private HomeScreen homeScreen;
    private JPanel loadingScreen;
    private LoseScreen loseScreen;
    private WinScreen winScreen;
    private GameEngine gameEngine;

    public UIManager(GameEngine gameEngine){
        super();
        this.gameEngine = gameEngine;

        // CardLayout allows multiple JPanels in the same space
        this.setLayout(new CardLayout());

        game = new JPanel();
        inventoryUI = new InventoryUI(gameEngine.mapManager.map.player);
        game.add(inventoryUI);

        pauseScreen = new PauseScreen();
        gameScreen = new GameScreen(gameEngine.mapManager.map, gameEngine.mapManager.camera, pauseScreen);
        gameScreen.addMouseListener(gameEngine.getMouseController());
        game.add(gameScreen);
        game.setFocusable(true);
        this.add(game, "Game");

        homeScreen = new HomeScreen();
        this.add(homeScreen, "Home");

        loadingScreen = new JPanel();
        this.add(loadingScreen, "Loading");

        loseScreen = new LoseScreen();
        this.add(loseScreen, "Lose");
        winScreen = new WinScreen();
        this.add(winScreen, "Win");

        update();
    }

    // Show the appropriate JPanel based on the game state
    public void update(){
        switch (gameEngine.getGameState()){
            case HOME -> {
                ((CardLayout)getLayout()).show(this, "Home");
            }
            case GAME -> {
                ((CardLayout)getLayout()).show(this, "Game");
                // Focus required for key bindings to work
                game.requestFocusInWindow();
                gameScreen.setPauseScreen(false);
                inventoryUI.update();
            }
            case PAUSE -> {
                gameScreen.setPauseScreen(true);
            }
            case GAMEOVER -> {
                inventoryUI.update();
            }
            case LOSE -> {
                ((CardLayout)getLayout()).show(this, "Lose");
            }
            case WIN -> {
                ((CardLayout)getLayout()).show(this, "Win");
            }
            default -> {
                ((CardLayout)getLayout()).show(this, "Loading");
            }
        }
        revalidate();
        repaint();
    }

    // Return a JPanel for key bindings
    public JPanel getPanel(GameState gameState){
        JPanel result = null;
        switch (gameState){
            case GAME -> result = game;
            case PAUSE -> result = pauseScreen;
            case HOME -> result = homeScreen;
        }
        return result;
    }
}
