/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The WinScreen class displays the win screen
 */

package view;

import controller.GameState;
import controller.action.SetState;

import javax.swing.*;
import java.awt.*;

public class WinScreen extends JPanel {
    private JLabel title;
    private JButton restart;
    private JButton home;

    public WinScreen() {
        super();
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1300, 720));

        title = new JLabel("You Win", SwingConstants.CENTER);
        title.setFont(UIConstants.menuTitleFont);
        this.add(title, new GBCB(0, 0).width(3));

        restart = new JButton(new SetState(GameState.RESTART));
        restart.setText("Play Again");
        restart.setFont(UIConstants.menuButtonFont);
        this.add(restart, new GBCB(1, 1));

        home = new JButton(new SetState(GameState.HOME));
        home.setText("Back to home");
        home.setFont(UIConstants.menuButtonFont);
        this.add(home, new GBCB(1, 3));

        this.add(Box.createHorizontalStrut(1), new GBCB(0, 5).weightx(0.3));
        this.add(Box.createHorizontalStrut(1), new GBCB(2, 5).weightx(0.3));
    }
}
