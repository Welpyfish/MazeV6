package view;

import controller.GameState;
import controller.action.SetState;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JPanel {
    private JLabel title;
    private JButton play;
    private JButton quit;

    public HomeScreen() {
        super();
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1300, 720));

        title = new JLabel("Maze");
        this.add(title, new ConstraintBuilder(0, 0).width(3));

        play = new JButton(new SetState(GameState.STARTGAME));
        play.setText("Play");
        this.add(play, new ConstraintBuilder(1, 1));

        quit = new JButton(new SetState(GameState.QUIT));
        quit.setText("Quit");
        this.add(quit, new ConstraintBuilder(1, 2));
    }
}
