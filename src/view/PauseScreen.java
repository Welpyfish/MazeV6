package view;

import controller.GameState;
import controller.action.SetState;

import javax.swing.*;
import java.awt.*;

public class PauseScreen extends JPanel {
    private JPanel menu;
    private JLabel title;
    private JButton resume;
    private JButton retry;
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
        menu.add(title, new ConstraintBuilder(0, 0).width(3));

        resume = new JButton(new SetState(GameState.GAME));
        resume.setText("Resume");
        resume.setFont(UIConstants.menuButtonFont);
        menu.add(resume, new ConstraintBuilder(1, 1));

        retry = new JButton(new SetState(GameState.STARTGAME));
        retry.setText("Retry");
        retry.setFont(UIConstants.menuButtonFont);
        menu.add(retry, new ConstraintBuilder(1, 2));

        home = new JButton(new SetState(GameState.HOME));
        home.setText("Back to home");
        home.setFont(UIConstants.menuButtonFont);
        menu.add(home, new ConstraintBuilder(1, 3));

        this.add(Box.createHorizontalStrut(1), new ConstraintBuilder(0, 5).weightx(0.3));
        this.add(Box.createHorizontalStrut(1), new ConstraintBuilder(2, 5).weightx(0.3));
    }

    @Override
    protected void paintComponent(Graphics g){
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
