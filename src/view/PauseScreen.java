/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The PauseScreen class displays the pause screen
 */

package view;

import controller.GameState;
import controller.action.SetState;

import javax.swing.*;
import java.awt.*;

public class PauseScreen extends JPanel {
    private JPanel menu;
    private JLabel title;
    private JButton resume;
    private JButton restart;
    private JButton home;

    public PauseScreen(){
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(1000, 720));
        setOpaque(false);
        setBackground(new Color(150, 150, 150, 100));

        menu = new JPanel(new GridBagLayout());
        menu.setMaximumSize(new Dimension(400, 250));
        menu.setPreferredSize(new Dimension(400, 250));
        this.add(Box.createVerticalGlue());
        this.add(menu);
        this.add(Box.createVerticalGlue());

        title = new JLabel("Paused", SwingConstants.CENTER);
        title.setFont(UIConstants.menuTitleFont);
        menu.add(title, new GBCB(0, 0).width(3));

        resume = new JButton(new SetState(GameState.GAME));
        resume.setText("Resume");
        resume.setFont(UIConstants.menuButtonFont);
        menu.add(resume, new GBCB(1, 1));

        restart = new JButton(new SetState(GameState.STARTGAME));
        restart.setText("Restart Level");
        restart.setFont(UIConstants.menuButtonFont);
        menu.add(restart, new GBCB(1, 2));

        home = new JButton(new SetState(GameState.HOME));
        home.setText("Back to home");
        home.setFont(UIConstants.menuButtonFont);
        menu.add(home, new GBCB(1, 3));

        this.add(Box.createHorizontalStrut(1), new GBCB(0, 5).weightx(0.3));
        this.add(Box.createHorizontalStrut(1), new GBCB(2, 5).weightx(0.3));
    }

    // Override to draw a translucent background
    @Override
    protected void paintComponent(Graphics g){
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
