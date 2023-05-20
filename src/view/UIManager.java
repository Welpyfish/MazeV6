package view;

import controller.GameEngine;
import model.ImageLoader;

import javax.swing.*;

public class UIManager extends JPanel {
    public GameScreen gameScreen;
    private GameUI gameUI;

    public UIManager(GameEngine gameEngine){
        gameUI = new GameUI(gameEngine);
        this.add(gameUI);
        gameScreen = new GameScreen(gameEngine.mapManager.map, gameEngine.mapManager.camera);
        gameScreen.addMouseListener(gameEngine.getMouseController());
        this.add(gameScreen);
    }

    public void update(){
        gameUI.update();
        revalidate();
        repaint();
    }

    public JButton getButton(UIButton button){
        return gameUI.getButton(button);
    }
}
