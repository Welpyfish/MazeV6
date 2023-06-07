package view;

import controller.GameEngine;
import model.weapon.ProjectileType;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JLayeredPane.POPUP_LAYER;

public class UIManager extends JPanel {
    public GameScreen gameScreen;
    private InventoryUI inventoryUI;
    private PauseScreen pauseScreen;
    private GameEngine gameEngine;

    public UIManager(GameEngine gameEngine){
        this.gameEngine = gameEngine;
        inventoryUI = new InventoryUI(gameEngine);
        this.add(inventoryUI);

        pauseScreen = new PauseScreen(gameEngine);
        gameScreen = new GameScreen(gameEngine.mapManager.map, gameEngine.mapManager.camera, pauseScreen);
        gameScreen.addMouseListener(gameEngine.getMouseController());
        this.add(gameScreen);
    }

    public void update(){
        switch (gameEngine.getGameState()){
            case GAME -> {
                gameScreen.setPauseScreen(false);
                inventoryUI.update();
            }
            case PAUSE -> {
                gameScreen.setPauseScreen(true);
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
