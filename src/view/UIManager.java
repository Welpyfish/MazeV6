package view;

import controller.GameEngine;
import model.weapon.ProjectileType;

import javax.swing.*;

public class UIManager extends JPanel {
    public GameScreen gameScreen;
    private InventoryUI inventoryUI;

    public UIManager(GameEngine gameEngine){
        inventoryUI = new InventoryUI(gameEngine);
        this.add(inventoryUI);
        gameScreen = new GameScreen(gameEngine.mapManager.map, gameEngine.mapManager.camera);
        gameScreen.addMouseListener(gameEngine.getMouseController());
        this.add(gameScreen);
    }

    public void update(){
        inventoryUI.update();
        revalidate();
        repaint();
    }

    public JButton getButton(ProjectileType button){
        System.out.println(inventoryUI.getButton(button));
        return inventoryUI.getButton(button);
    }
}
