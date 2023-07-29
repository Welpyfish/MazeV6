/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The GameFrame class is the frame that holds the game
 */

package view;

import javax.swing.*;

public class GameFrame extends JFrame{
    public GameFrame(UIManager uiManager)
    {
        super("Maze - William Zhou");
        this.add(uiManager);
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
