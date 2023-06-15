package view;

import controller.GameState;
import controller.action.SetState;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class HomeScreen extends JPanel {
    private JLabel title;
    private JButton play;
    private JButton quit;

    public HomeScreen() {
        super();
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1300, 720));

        title = new JLabel("Maze", SwingConstants.CENTER);
        title.setFont(UIConstants.mainTitleFont);
        this.add(title, new ConstraintBuilder(0, 0).width(3));

        play = new JButton(new SetState(GameState.RESTART));
        play.setText("Play");
        play.setFont(UIConstants.mainMenuButtonFont);
        this.add(play, new ConstraintBuilder(1, 1).weightx(0.1));

        quit = new JButton(new SetState(GameState.QUIT));
        quit.setText("Quit");
        quit.setFont(UIConstants.mainMenuButtonFont);
        this.add(quit, new ConstraintBuilder(1, 2));

        this.add(Box.createHorizontalStrut(1), new ConstraintBuilder(0, 5).weightx(0.3));
        this.add(Box.createHorizontalStrut(1), new ConstraintBuilder(2, 5).weightx(0.3));
    }
}
