package view;

import controller.GameEngine;
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
    private JButton quit;

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

        title = new JLabel("Paused");
        menu.add(title, new ConstraintBuilder(0, 0).width(3));

        resume = new JButton(new SetState(GameState.GAME));
        resume.setText("Resume");
        menu.add(resume, new ConstraintBuilder(1, 1));

        restart = new JButton(new SetState(GameState.STARTGAME));
        restart.setText("Restart");
        menu.add(restart, new ConstraintBuilder(1, 2));

        home = new JButton(new SetState(GameState.HOME));
        home.setText("Back to home");
        menu.add(home, new ConstraintBuilder(1, 3));

        quit = new JButton(new SetState(GameState.QUIT));
        quit.setText("Quit");
        menu.add(quit, new ConstraintBuilder(1, 4));
    }

    @Override
    protected void paintComponent(Graphics g){
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
