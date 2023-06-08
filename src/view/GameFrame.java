package view;

import controller.GameEngine;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{
    public GameFrame(UIManager uiManager)
    {
        super("Game");
        this.add(uiManager);
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
