package view;

import controller.GameEngine;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{
    public UIManager uiManager;
    public GameFrame(GameEngine gameEngine)
    {
        super("Game");
        uiManager = new UIManager(gameEngine);
        this.add(uiManager);
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void update(){
        uiManager.update();
    }

}
