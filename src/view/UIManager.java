package view;

import controller.GameEngine;
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
    private GameEngine gameEngine;

    public UIManager(GameEngine gameEngine){
        super();
        this.gameEngine = gameEngine;

        this.setLayout(new CardLayout());

        game = new JPanel();
        inventoryUI = new InventoryUI(gameEngine);
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

        update();
    }

    public void update(){
        switch (gameEngine.getGameState()){
            case HOME -> {
                ((CardLayout)getLayout()).show(this, "Home");
            }
            case GAME -> {
                ((CardLayout)getLayout()).show(this, "Game");
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
            default -> {
                ((CardLayout)getLayout()).show(this, "Loading");
            }
        }
        revalidate();
        repaint();
    }

    public JButton getButton(ProjectileType button){
        System.out.println(inventoryUI.getButton(button));
        return inventoryUI.getButton(button);
    }
}
