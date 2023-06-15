package view;

import model.ImageLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Stack;

public class HealthBar extends JPanel {
    GridBagLayout gridBagLayout;
    private final Stack<JLabel> hearts;

    public HealthBar(){
        gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        hearts = new Stack<>();
        setVisible(true);
    }

    public void update(int h){
        while(hearts.size()<h){
            hearts.push(new JLabel(ImageLoader.getIcon("heart")));
            this.add(hearts.peek(), new ConstraintBuilder((hearts.size()-1)%10, (hearts.size()-1)/10));
        }
        while(hearts.size()>h){
            this.remove(hearts.pop());
        }
    }
}
